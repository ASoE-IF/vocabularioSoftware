package org.splab.vocabulary.termscounter;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.JAXBException;

import org.splab.vocabulary.vxl.VXLReader;
import org.splab.vocabulary.vxl.iterator.VXLIterator;
import org.splab.vocabulary.vxl.iterator.javamodel.ContainerEntity;
import org.splab.vocabulary.filter.IdentifierFilter;
import org.splab.vocabulary.iR.IR;
import org.splab.vocabulary.iR.info.IRInfo;
import org.splab.vocabulary.iR.info.LSIInfo;
import org.splab.vocabulary.iR.info.RetrievedInfo;
import org.splab.vocabulary.measures.DispersionMeasures;

import ptstemmer.exceptions.PTStemmerException;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

/**
 * Instances of this class are objects that, from an archive .vxl and using
 * other objects (VXLReader, IR, IdentifierFilter, DispersionMeasures), is able
 * to perform calculations and display results in a .csv or .txt file. It all
 * depends of the parameters that are in .properties file.
 * @see /files/termsCounter.properties
 * 
 * @author Katyusco de Farias Santos / Samuel de Medeiros Queiroz / Catharine Quintans
 * 
 */
public class TermsCounter {
	// It will encapsulates an object DoubleMatrix2D, where are stored Term per Entity information.
	private RetrievedInfo retrievedInfo;
	
	// Properties that sets what will be done by Terms Counter.
	private Properties termsCounterProps;
	
	// Responsible for read the vocabulary from the .vxl file.
	private VXLReader vxlReader;
	
	// Responsible for do the filtering of identifiers, its results are terms.
	private IdentifierFilter identifierFilter;
	
	// Object responsible for calculate the dispersion measures, that are:
	// entropy vector, context coverage vector and HEEHCC vector.
	private DispersionMeasures dispersionMeasures;
	
	// If set as 'yes' in .properties file, the results will written in .csv file.
	private boolean generateCSV;
	
	// If set as 'yes' in .properties file, the results will written in .txt file.
	private boolean generateTXT;
	
	// If set as 'yes' in .properties file, the marginals are calculated from
	// the original Term x Entity matrix encapsulated in retrievedInfo object.
	private boolean createMarginals;
	
	// If set as 'yes' in .properties file, the dispersion measures are
	// calculated (entropy vector, context coverage vector and HEEHCC vector).
	private boolean calculateDispersionMeasures;
	
	// First vertical marginal.
	private DoubleMatrix1D mTermFrequency;
	
	// Second vertical marginal.
	private DoubleMatrix1D mEntityFrequency;

	// First horizontal marginal.
	private DoubleMatrix1D mEntityTermsFrequencies;
	
	// Second horizontal marginal.
	// For more details about the marginal of Term x Entity matrix, see createMarginals method.
	private DoubleMatrix1D mEntityDistinctTerms;
	
	// Getting line separator from System.
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	/**
	 * Constructor method of Terms Counters.
	 * @param termsCounterProperties The properties that defines that will calculate.
	 */
	public TermsCounter(Properties termsCounterProperties) {
		this.termsCounterProps = termsCounterProperties;
		
		// Instantiates the filter.
		this.identifierFilter = new IdentifierFilter(termsCounterProps);
		//this.identifierFilter = new IdentifierFilter(language, length);
		// Instantiates the .vxl reader.
		this.vxlReader = new VXLReader(termsCounterProps);

		// Read only the properties that are of the Terms Counter.
		readTermsCounterProps();
	}
	
	/**
	 * Read from Properties object, the Terms Counter properties, that are:
	 * calculates dispersion measures, generate .csv file with results, generate
	 * .txt file with results and create marginals to Term x Entity matix.
	 */
	private void readTermsCounterProps() {
		if (termsCounterProps.containsKey("dispersionMeasures") && termsCounterProps.get("dispersionMeasures").toString().equalsIgnoreCase("yes"))
			calculateDispersionMeasures = true;
		if (termsCounterProps.containsKey("generateCSV") && termsCounterProps.get("generateCSV").toString().equalsIgnoreCase("yes"))
			generateCSV = true;
		if (termsCounterProps.containsKey("generateTXT") && termsCounterProps.get("generateTXT").toString().equalsIgnoreCase("yes"))
			generateTXT = true;
		if (termsCounterProps.containsKey("createMarginals") && termsCounterProps.get("createMarginals").toString().equalsIgnoreCase("yes"))
			createMarginals = true;
	}

	/**
	 * Read the vocabulary contained in .vxl file.
	 * @param filepathVxl
	 *            The path of .vxl file that will be read.
	 */
	private void loadVXLFile(String filepathVxl) {
		try {
			vxlReader.load(filepathVxl);
		} catch (JAXBException e) {
			e.printStackTrace();
			System.out.println("loadVxlFile(): JAXBException Error");
		}
	}

	/**
	 * This method uses the VXLReader to obtain the identifiers from vocabulary.
	 * For each identifier, it uses the IdentifierFilter to extract all terms.
	 * Each identifier and its terms is stored in a map, that is passed to the
	 * IR, that releases the counting of frequencies and after, the
	 * RetrievedInfo object is created.
	 * @throws PTStemmerException 
	 */
	public void createIRInfoTermsPerEntity() throws PTStemmerException {
		// Create a Terms Entities map, that after will be used for IR, that will count the frequencies of the terms.
		Map<String, Map<String, Integer>> termsEntitiesMap = new HashMap<String, Map<String, Integer>>(); 
		
		VXLIterator iterator = vxlReader.iterator();
		while (iterator.hasNext()) {
			ContainerEntity container = iterator.next();
			
			Map<String, Integer> currentEntityTerms = new HashMap<String, Integer>();

			// For each identifier (yet not is term) of this entity, execute the filter and store the 
			//resultant terms in list of terms of current entity.
			for (String identifier : container.getIdentifiers().keySet()) {
				for (String term : identifierFilter.filterIdentifiers(new String[] {identifier})) {
					if (currentEntityTerms.containsKey(term)) {
						currentEntityTerms.put(term, currentEntityTerms.get(term) + container.getIdentifiers().get(identifier));
					} else {
						currentEntityTerms.put(term, container.getIdentifiers().get(identifier));
					}
				}
			}
			
			termsEntitiesMap.put(container.getEntityIdentifier(), currentEntityTerms);
		}
		
		// Create an InformationRetriever object with resultant map and the properties.
		IR ir = new IR(termsEntitiesMap, termsCounterProps);
		
		// If the dispersion measures will be calculate...
		if (calculateDispersionMeasures)
			// Create an LSIInfo.
			retrievedInfo = new LSIInfo(ir.calculate(), termsCounterProps);
		else
			// Create an IRInfo.
			retrievedInfo = new IRInfo(ir.calculate(), termsCounterProps);
		// Note: The creation of LSIInfo takes longer than creation of IRInfo. As the LSIInfo just is
		//necessary to calculate the dispersion measures, if it will not be created, an IRInfo is instantiated.
	}

	/**
	 * Create an object of DipersionMeasures type and calculates (internally)
	 * the dispersion measures (entropy vector, context coverage vector and
	 * HEEHCC vector).
	 */
	private void calculateDispersionMeasures() {
		dispersionMeasures = new DispersionMeasures((LSIInfo) retrievedInfo, termsCounterProps);
	}
	
	/**
	 * Create from Term x Entity (document) matrix of retrievedInfo object, the marginals of this matrix. Are they:
	 * MTermFrequency: Store, for each term, its frequency in software vocabulary.
	 * MEntityFrequency: Store, for each term, the number of entities where it's present.
	 * MEntityTermsFrequencies: Store, for each entity, the total number of terms.
	 * MEntityDistinctTerms: Store, for each entity, the number of distinct terms.
	 */
	private void createMarginals() {
		// Obtain the Term x Entity matrix.
		DoubleMatrix2D termDocumentMatrix = retrievedInfo.getTermDocumentMatrix();

		// Store the number of rows of the matrix.
		int rows = termDocumentMatrix.rows();
		
		// Create empty vertical marginals.
		mTermFrequency = termDocumentMatrix.like1D(rows);
		mEntityFrequency = termDocumentMatrix.like1D(rows);
		
		// For each row...
		for (int i = 0; i < rows; i++) {
			DoubleMatrix1D termOccurrence = termDocumentMatrix.viewRow(i).copy();
			
			// Obtain the sum of its.
			mTermFrequency.set(i, termOccurrence.zSum());
			// Obtain the number of non-zeros columns (number of entities where it's present).
			mEntityFrequency.set(i, termOccurrence.cardinality());
		}
		
		// Store the number of columns of the matrix.
		int columns = termDocumentMatrix.columns();
		
		// Create empty horizontal marginals.
		mEntityTermsFrequencies = termDocumentMatrix.like1D(columns);
		mEntityDistinctTerms = termDocumentMatrix.like1D(columns);
		
		// For each column...
		for (int i = 0; i < columns; i++) {
			DoubleMatrix1D termOccurrence = termDocumentMatrix.viewColumn(i).copy();
			
			// Obtain the sum of its.
			mEntityTermsFrequencies.set(i, termOccurrence.zSum());
			// Obtain the number of non-zeros rows (number of present terms).
			mEntityDistinctTerms.set(i, termOccurrence.cardinality());
		}
	}

	/**
	 * Gives the number of entities in vocabulary.
	 * 
	 * @return The number of entities in vocabulary.
	 */
	public int getNumberOfEntities() {
		return retrievedInfo.getAllDocumentIds().size();
	}
	
	/**
	 * Gives the number of distinct terms in vocabulary.
	 * 
	 * @return The number of distinct terms in vocabulary.
	 */
	public int getNumberOfDistinctTerms() {
		return retrievedInfo.getAllTerms().size();
	}
	
	/**
	 * Gives the total number of terms in vocabulary.
	 * 
	 * @return The total number of terms in vocabulary.
	 */
	public int getTotalNumberOfTerms() {
		return (int) retrievedInfo.getTermDocumentMatrix().zSum();
	}
	
	/**
	 * Gives the Term x Entity (document) matrix.
	 * 
	 * @return The Term x Entity matrix.
	 */
	public DoubleMatrix2D getTermDocumentMatrix() {
		return retrievedInfo.getTermDocumentMatrix();
	}

	/**
	 * Gives the first vertical Term x Entity matrix's marginal that store, for
	 * each term, its frequency in software vocabulary.
	 * 
	 * @return The first vertical marginal of the matrix.
	 */
	public DoubleMatrix1D getMTermFrequency() {
		return mTermFrequency;
	}
	
	/**
	 * Gives the second vertical Term x Entity matrix's marginal that store, for
	 * each term, the number of entities where it's present.
	 * 
	 * @return The second vertical marginal of the matrix.
	 */
	public DoubleMatrix1D getMEntityFrequency() {
		return mEntityFrequency;
	}
	
	/**
	 * Gives the first horizontal Term x Entity matrix's marginal that store,
	 * for each entity, the total number of terms.
	 * 
	 * @return The first horizontal marginal of the matrix.
	 */
	public DoubleMatrix1D getMEntityTermsFrequencies() {
		return mEntityTermsFrequencies;
	}
	
	/**
	 * Gives the second horizontal Term x Entity matrix's marginal that store,
	 * for each entity, the number of distinct terms.
	 * 
	 * @return The second horizontal marginal of the matrix.
	 */
	public DoubleMatrix1D getMEntityDistinctTerms() {
		return mEntityDistinctTerms;
	}

	/**
	 * Save the Headers, the Term x Entity matrix, and Name of Terms in a .csv file.
	 * 
	 * @param csvFilePath
	 *            The path of the .csv file where the matrix will be written.
	 * @throws IOException
	 *             When occurs any io error.
	 */
	private void writeResultsAndHeadersInCSV(String csvFilePath) throws IOException {
		PrintStream csvExit = new PrintStream(new FileOutputStream(csvFilePath));

		// Obtains the Term x Entity matrix from retrievedInfo object.
		DoubleMatrix2D matrixTermsEntities = retrievedInfo.getTermDocumentMatrix();
		
		int rows = matrixTermsEntities.rows();
		int columns = matrixTermsEntities.columns();
		
		// Writing the header of CSV file
		// To write the names of all Entities stored by RetrivedInfo Class
		String headerMatrix = "";
		String[] entitiesName = (String[]) (retrievedInfo.getAllDocumentIds()).toArray();
		if (entitiesName.length > 0) {
			headerMatrix = ","; // This first comma is necessary to shift list of entities
			int entitiesNameLen = entitiesName.length;
			for (int index = 0; index < entitiesNameLen;  index++) {
				// If isn't the last one, print comma
				if (index != entitiesNameLen - 1) {
					headerMatrix = headerMatrix + entitiesName[index] + ",";
				} else { // do not print the last comma
					headerMatrix = headerMatrix + entitiesName[index];
				}
			}
		}
		csvExit.println(headerMatrix);
		
		
		// Stores all terms in array 
		String[] termsName = (String[]) (retrievedInfo.getAllTerms()).toArray();
		if ( termsName.length != rows ) {
			csvExit.flush();
			csvExit.close();
			throw new IOException("Número termos menor que o número de Linhas da Matriz");
		}
		
		// For each row in the matrix...
		for (int row = 0; row < rows; row++) {
			// For each column in current row...
			// Insert the respectively term before its row
			csvExit.print((termsName[row] + ","));
			for (int column = 0; column < columns; column++) {
				// Print current row in .csv
				csvExit.print((int) matrixTermsEntities.get(row, column));
				// If isn't last column, print a comma.
				if (column != columns -1)
					csvExit.print(",");
				// Else, if the marginals of matrix were created, print them.
				else if (createMarginals)
					csvExit.print("," + (int) mTermFrequency.get(row) + "," + (int) mEntityFrequency.get(row) + LINE_SEPARATOR);
				// Else, ended of print this row and not create the marginals,
				// so, skip to the next line.
				else
					csvExit.print(LINE_SEPARATOR);
			}
		}
		// If the matrix's horizontal marginals were created, print them.
		if (createMarginals)
			// For each horizontal marginal...
			for (DoubleMatrix1D horizontalMarginal : new DoubleMatrix1D[] {mEntityTermsFrequencies, mEntityDistinctTerms}) {
				csvExit.print(","); // This first comma is necessary to shift the marginal values
				// For each column, print it.
				for (int column = 0; column < columns; column++) {
					// If is the last column, print and slip to next line.
					if (column == columns - 1)
						csvExit.println((int) horizontalMarginal.get(column));
					// Else, just print.
					else
						csvExit.print((int) horizontalMarginal.get(column) + ",");
				}
			}
		
		csvExit.flush();
		csvExit.close();
	}

	/**
	 * Save many results in a .txt file. Are they: the Term x Entity (document)
	 * matrix, entity map*, entropy vector*, context coverage vector*, HEEHCC
	 * vector*, the marginals**, all terms and them frequencies, number of
	 * entities, number of distinct terms and total number of terms. * Only if
	 * the dispersion measures were calculated. ** Only if them were calculated.
	 * 
	 * @param txtFilePath
	 *            The path of the .txt file where the results will be writen.
	 * @throws IOException
	 *             When occor some error in io.
	 */
	public void writeResultsInTXT(String txtFilePath) throws IOException {
		PrintStream txtExit = new PrintStream(new FileOutputStream(txtFilePath));
		
		// Obtains the Term x Entity matrix from retrievedInfo object.
		DoubleMatrix2D matrixTermsEntities = retrievedInfo.getTermDocumentMatrix();
		
		// Print the matrix in .txt.
		txtExit.println("----------- Terms x Entities -----------");
		txtExit.println(matrixTermsEntities.toString() + LINE_SEPARATOR);
		
		// If the dispersion measures were calculated, print them.
		if (calculateDispersionMeasures) {
			txtExit.println("------------- Entities Map -------------");
			txtExit.println(retrievedInfo.getAllDocumentIdsMap().toString() + LINE_SEPARATOR);
			
			txtExit.println("------------ Entropy Vector ------------");
			txtExit.println(dispersionMeasures.getEntropyMatrix().toString() + LINE_SEPARATOR);
			
			txtExit.println("-------- Context Coverage Vector --------");
			txtExit.println(dispersionMeasures.getContextCoverageMatrix().toString() + LINE_SEPARATOR);
			
			txtExit.println("------------- HEEHCC Vector -------------");
			txtExit.println(dispersionMeasures.getNumHEHCCMatrix().toString() + LINE_SEPARATOR);
		} 
		// If the marginals of Terms x Entities matrix were created, print them.
		if (createMarginals) {
			txtExit.println("-------- Term Frequency Marginal --------");
			txtExit.println(mTermFrequency + LINE_SEPARATOR);
			
			txtExit.println("------- Entity Frequency Marginal -------");
			txtExit.println(mEntityFrequency + LINE_SEPARATOR);
			
			txtExit.println("---- EntityTermsFrequencies Marginal ----");
			txtExit.println(mEntityTermsFrequencies + LINE_SEPARATOR);
			
			txtExit.println("--------- MEntityDistinctTerms ---------");
			txtExit.println(mEntityDistinctTerms + LINE_SEPARATOR);
		}
		
		// Obtain the list of all terms in vocabulary.
		List<String> vocabularyTerms = retrievedInfo.getAllTerms();
		
		txtExit.println("----------- Terms Frequencies -----------");
		DoubleMatrix1D row;
		// For each term in vocabularyTerms...
		for (String term: vocabularyTerms) {
			// Obtain its frequency and print them in .txt file.
			row = matrixTermsEntities.viewRow(retrievedInfo.getAllTermIdsMap().get(term));
			txtExit.println(term +": "+ row.zSum());
		}

		// Now, print a summary of the count. It has number of entities, number of distinct terms and total number of terms in vocabulary.
		txtExit.println("---------------- Summary ----------------");
		txtExit.print("Entities:" + getNumberOfEntities() + "\t");
		txtExit.print("Distinct_Terms:"  + getNumberOfDistinctTerms() + "\t");
		txtExit.println("Total_Terms:"  + getTotalNumberOfTerms());
		
		txtExit.flush();
		txtExit.close();
	}
	
	/**
	 * Main method, responsible for calling the other methods, depending to the properties.
	 * @param filePathVXL Path where must be located the .vxl file that contains the vocabulary that will analysed.
	 * @param txtFilePath The path of the .txt file where the results will be written.
	 * @param csvFilePath The path of the .csv file where the matrix will be written.
	 */
	public void run(String filePathVXL,	String txtFilePath, String csvFilePath) {
		// Load the vocabulary from .vxl file using the VXLReader object.
		loadVXLFile(filePathVXL);

		try {
			// Create the RetrievedInfo object.
			createIRInfoTermsPerEntity();
			
			// Calls some methods, if the calling of them is setted as 'yes' in properties.
			if (calculateDispersionMeasures)
				calculateDispersionMeasures();
			if (createMarginals)
				createMarginals();
			if (generateTXT)
				writeResultsInTXT(txtFilePath);
			if (generateCSV)
				writeResultsAndHeadersInCSV(csvFilePath);
		} catch (Exception e) {
			// If an exception occurred during the execution, displays the stack error.
			e.printStackTrace();
		}
	}
	
	/**
	 * The 'main' method, used when an user has the .jar of this project.
	 * @param args The necessary arguments to execution of TermsCounter.
	 */
	public static void main(String[] args) {
		final String MANUAL = "Invalid input. You must set the following options:"
				+ "\n\t-prop: the path/anem of the project properties"
				+ "\n\t-vxl: the path/name of the resulting VXL file"
				+ "\n\t-csv: the path/name of the resulting CSV loc file"
				+ "\n\t-txt: the path/name of the resulting TXT file"
				+ "\n\n\tEXAMPLE: -prop ~/termsCounter.properties -vxl ~/ProjectVXL.vxl -csv ~/ProjectCSV.csv -txt ;~/ProjectTXT.txt";


		try {
			String tcPropsFilePath = "", vxlFilePath = "", txtFilePath = "", csvFilePath = "";
			
			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("-vxl")) {
					vxlFilePath = args[++i];
				} else if (args[i].equals("-csv")) {
					csvFilePath = args[++i];
				} else if (args[i].equals("-prop")) {
					tcPropsFilePath = args[++i];
				} else if (args[i].equals("-txt")) {
					txtFilePath = args[++i];
				} 
			}
			
			// Read the Terms Counter properties.
			Properties tcProps = new Properties();
			try {
				tcProps.load(new BufferedInputStream(new FileInputStream(tcPropsFilePath)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Create an object of type TermsCounter.
			TermsCounter termsCounter = new TermsCounter(tcProps);
			// Calls the execution of TermsCounter, that starts counting.
			termsCounter.run(vxlFilePath, txtFilePath, csvFilePath);
			
			System.out.println("Terms Counter has finished.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(MANUAL);
		}
	}
}