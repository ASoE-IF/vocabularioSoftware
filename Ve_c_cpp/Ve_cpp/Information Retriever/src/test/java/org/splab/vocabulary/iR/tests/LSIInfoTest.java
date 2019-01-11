package org.splab.vocabulary.iR.tests;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;

import org.splab.vocabulary.iR.IR;
import org.splab.vocabulary.iR.info.LSIInfo;
import org.splab.vocabulary.iR.util.TermWeightTuple;
import org.splab.vocabulary.filter.IdentifierFilter;
import org.splab.vocabulary.vxl.VXLReader;
import org.splab.vocabulary.vxl.iterator.VXLIterator;
import org.splab.vocabulary.vxl.iterator.javamodel.ContainerEntity;

import ptstemmer.exceptions.PTStemmerException;

public class LSIInfoTest extends TestCase {
	
	private final String FILTER_PROPERTY_FILE_NAME = "files/identifierfilter.properties";
	private final String TERM_EXTRACTION_PROPERTY_FILE_NAME = "files/termscounter.properties";
	protected String IR_CALCULATOR_PROPERTY_FILE_NAME = ""; // extensible
	
	private final String VXL_FILE_NAME = "files/IRTest1.vxl";
	
	private IdentifierFilter identifierFilter;
	
	protected final String PERSON = "Person.c";
	protected final String OFFICE = "Office.c";
	protected final String EMPLOYEE = "Employee.c";
	protected final String HOUSE = "House.c";

	protected LSIInfo info;

	protected void setUp() throws Exception {
		super.setUp();
		
		Properties vxlReaderProps = validateProperties(TERM_EXTRACTION_PROPERTY_FILE_NAME);
		VXLReader vxlReader = new VXLReader(vxlReaderProps);
		vxlReader.load(VXL_FILE_NAME);
		
		Properties props = new Properties();
		props.load(new FileInputStream(FILTER_PROPERTY_FILE_NAME));
		
		this.identifierFilter = new IdentifierFilter(props);
		
		this.info = generateIRInfo(vxlReader);
	}
	
	private LSIInfo generateIRInfo(VXLReader vxlReader) throws PTStemmerException {
		
		Map<String, List<TermWeightTuple>> frequencyMap;
		Map<String, Map<String, Integer>> termsByEntity = new HashMap<String, Map<String, Integer>>();
		
		VXLIterator entityIterator = vxlReader.iterator();
		// for all entities, extract all identifiers and count terms
		while (entityIterator.hasNext()) {
			ContainerEntity entity = entityIterator.next();
			Map<String, Integer> identifiers = entity.getIdentifiers();
			
			Map<String, Integer> entityTerms = new HashMap<String, Integer>();
			for (String idtf : identifiers.keySet()) {
				int frequency = identifiers.get(idtf);
				String[] identifierArray = {idtf};
				String[] terms = this.identifierFilter.filterIdentifiers(identifierArray);
				
				for (String t : terms) {
					entityTerms.put(t, frequency);
				}
			}
			
			termsByEntity.put(entity.getEntityIdentifier(), entityTerms);
		}
		
		Properties irProps = validateProperties(IR_CALCULATOR_PROPERTY_FILE_NAME);
		IR ir = new IR(termsByEntity, irProps);
		
		frequencyMap = ir.calculate();
		return new LSIInfo(frequencyMap, irProps);
	}
	
	private Properties validateProperties(String propertyFileName) {
		
		Properties props = new Properties();
		try {
			props.load(new BufferedInputStream(new FileInputStream(propertyFileName)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return props;
	}
	
//	public void testBuildEquivalentVector() {
//		List<String> terms = new ArrayList<String>();
//		terms.add("address");
//		terms.add("cost");
//		terms.add("hous");
//		terms.add("hous");
//		DoubleMatrix1D queryVector = info.buildEquivalentVector(terms);
//		assertEquals(0.169199, queryVector.get(0), 0.001);
//		assertEquals(-0.982409, queryVector.get(1), 0.001);
//		
//		DoubleMatrix1D weightVector1 = info.getDocumentVector("class1000001");
//		DoubleMatrix1D weightVector2 = info.getDocumentVector("class1000002");
//		DoubleMatrix1D weightVector3 = info.getDocumentVector("class1000003");
//		DoubleMatrix1D weightVector4 = info.getDocumentVector("class1000004");
//		
//		double sim1 = info.getCosineSimilarity(queryVector, weightVector1);
//		double sim2 = info.getCosineSimilarity(queryVector, weightVector2);
//		double sim3 = info.getCosineSimilarity(queryVector, weightVector3);
//		double sim4 = info.getCosineSimilarity(queryVector, weightVector4);
//		assertTrue(sim1 > sim2);
//		assertTrue(sim1 > sim3);
//		assertTrue(sim1 > sim4);
//	}
}