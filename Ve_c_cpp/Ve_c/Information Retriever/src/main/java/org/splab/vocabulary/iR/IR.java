package org.splab.vocabulary.iR;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.splab.vocabulary.iR.IRPropertyKeys.ScoreCalculatorType;
import org.splab.vocabulary.iR.util.TermWeightTuple;

/**
 * This class is responsible for weighting the terms of a vocabulary, which consists
 * in assigning a relevance value for each term of a document. So far, it computes a
 * total of three functions:
 * 
 * - term frequency: the amount of occurrences of one term in the document (absolute)
 * , or the the occurrence of the term in relation to the number of terms of the docu
 * ment (relative).
 * - inverse document frequency: calculates the log of the ratio between the number
 * of documents in which one term occurred and the total number of documents. Thus,
 * if a term occurs in most documents, its <i>idf</i> will be low.
 * - tf-idf: this metric consists in a combination of the two metrics above. Thereby,
 * a term's <i>tf-idf</i> will be high if it has high occurrence in the document and
 * also appears in few documents.
 * 
 * The weighting calculation options must be informed by a properties file. The pro
 * perty keys to this file are described in the class <code>IRPropertyKeys</code>.
 * 
 * @author Samuel de Medeiros Queiroz
 * @credits Roberto Almeida Bittencourt / Gustavo Jansen de Souza Santos / Catharine Quintans
 * @see org.splab.vocabulary.iR.IRPropertyKeys
 */
public class IR {
	private Map<String, Map<String, Integer>> documentTermMap;
	private ScoreCalculatorType calculatorType;
	private boolean isTFRelative;
	
	/**
	 * The constructor of the term weighting class. It requires a map in which each document
	 * is represented by an identifier, and it has a map of terms that occurred in the docu
	 * ment. Next, each term is represented by a <code>String</code> and an <code>Integer</code>
	 * representing the number of occurrenced of the term in the document. So that the term
	 * weighting does not depend on the vocabulary extraction.
	 * 
	 * It also requires a properties file, containting the weighting calculation options,
	 * such as the type of weighting function (tf, idf, or tf-idf), and which variation of
	 * term frequency (absolute or relative). 
	 * 
	 * @param documentTermMap a map representing the extracted voculary
	 * @param properties a file containing the weighting calculation options
	 */
	public IR(Map<String, Map<String, Integer>> documentTermMap, Properties properties) {
		this.documentTermMap = documentTermMap;
		loadProperties(properties);
	}
	
	/**
	 * This method is responsible for the calculation of weights for each term in each docu
	 * ment in the vocabulary. It delegates the <i>tf</i> and <i>idf</i> weighting functions
	 * to other methods, and eventually merges both results if the user chose the <i>tf-idf</i>
	 * weighting function. It returns a map in which every document has a list of tuples.
	 * A tuple is composed by the term and its weight in the document.
	 * 
	 * @return a map with the weights of each term in each document
	 */
	public Map<String, List<TermWeightTuple>> calculate() {
		Map<String, List<TermWeightTuple>> idfMap = calculateIDFMap(); 
		Map<String, List<TermWeightTuple>> tfMap = calculateTFMap();
		
		if (isCalculator(IRPropertyKeys.ScoreCalculatorType.TF)) return tfMap;
		else if (isCalculator(IRPropertyKeys.ScoreCalculatorType.IDF)) return idfMap;
		
		// if the user selected the tf-idf weighting function, then we will merge the maps
		Map<String, List<TermWeightTuple>> tfIdfMap = new HashMap<String, List<TermWeightTuple>>();
		
		// for each document, the list of terms is ordered
		for (String identifier : idfMap.keySet()) {
			List<TermWeightTuple> tfs = tfMap.get(identifier);
			List<TermWeightTuple> idfs = idfMap.get(identifier);
			List<TermWeightTuple> tfIdfs = new LinkedList<TermWeightTuple>();
			
			for (TermWeightTuple idfTuple : idfs) {
				TermWeightTuple tfTuple = getTuple(idfTuple.getTerm(), tfs);
				TermWeightTuple merged = new TermWeightTuple(idfTuple.getTerm(), tfTuple.getWeight() * idfTuple.getWeight());
				tfIdfs.add(merged);
			}
			
			tfIdfMap.put(identifier, tfIdfs);
		}
		
		return tfIdfMap;
	}
	
	/*
	 * This method is responsible for the <i>idf</i> calculation for each term of the voca
	 * bulary. The result of this process is a map of documents, and the terms of each do
	 * cument have their respective <i>idf</i> weights.
	 * 
	 * @return a map with the <i>idf</i> weights of each term in each document
	 */
	private Map<String, List<TermWeightTuple>> calculateIDFMap() {
		Map<String, List<TermWeightTuple>> idfMap = null;
		
		if (isCalculator(IRPropertyKeys.ScoreCalculatorType.IDF) || isCalculator(IRPropertyKeys.ScoreCalculatorType.TFIDF)) {
			int numDocuments = documentTermMap.size();
			idfMap = new HashMap<String, List<TermWeightTuple>>();
			Map<String, Double> idfTermsMap = new HashMap<String, Double>();
			
			// populating the idfTermsMap with all terms in the vocabulary.
			// the idf of a term is the same for all documents
			for (String documentId : documentTermMap.keySet())
				for (String term : documentTermMap.get(documentId).keySet())
					if (!idfTermsMap.containsKey(term))
						idfTermsMap.put(term, 0D);
			
			// calculating document frequency for each term
			for (String term : idfTermsMap.keySet()) {
				double documentFrequency = 0D;
				for (String documentId : documentTermMap.keySet())
					// if the document has the given term
					if (documentTermMap.get(documentId).containsKey(term))
						documentFrequency++;
				idfTermsMap.put(term, calculateIDF(documentFrequency, numDocuments));
			}
			
			// map back to term tuples
			for (String documentId : documentTermMap.keySet()) {
				List<TermWeightTuple> termWeights = new LinkedList<TermWeightTuple>();
				
				for (String term : documentTermMap.get(documentId).keySet())
					termWeights.add(new TermWeightTuple(term, idfTermsMap.get(term)));
				
				idfMap.put(documentId, termWeights);
			}
		}
		
		return idfMap;
	}
	
	/*
	 * Calculates the <i>idf</i> weight of a term, given the number of documents in which
	 * it occurred and the number of documents of the vocabulary.
	 * 
	 * This calculation was made the way Lucene API calculates idf. Thus, a term that occurs
	 * in all documents of the vocabulary will have low idf, but not zero.
	 * 
	 * @param documentFrequency the number of documents in which the term occurred
	 * @param numDocuments the number of terms in the vocabulary
	 * @return the <i>idf</i> weight of the term in the vocabulary
	 */
	private double calculateIDF(double documentFrequency, int numDocuments) {
		return (documentFrequency == 0) ? 0 : Math.log10(numDocuments / (documentFrequency + 1)) + 1;
	}
	
	/*
	 * This method is responsible for the <i>tf</i> calculation for each term of the voca
	 * bulary. The result of this process is a map of documents, and the terms of each do
	 * cument have their respective <i>tf</i> weights.
	 * 
	 * @return a map with the <i>tf</i> weights of each term in each document
	 */
	private Map<String, List<TermWeightTuple>> calculateTFMap() {
		Map<String, List<TermWeightTuple>> tfMap = null;
		
		if (isCalculator(IRPropertyKeys.ScoreCalculatorType.TF) || isCalculator(IRPropertyKeys.ScoreCalculatorType.TFIDF)) {
			tfMap = new HashMap<String, List<TermWeightTuple>>();
			
			for (String documentId : documentTermMap.keySet()) {
				List<TermWeightTuple> termWeights = new LinkedList<TermWeightTuple>();
				
				// calculating all term occurrences in a given document
				double allTermOccurrences = 0D;
				if (isTFRelative) {
					Map<String, Integer> termOccurrenceMap = documentTermMap.get(documentId);
					for (String term : termOccurrenceMap.keySet())
						allTermOccurrences += termOccurrenceMap.get(term);
				}
				
				// calculating tf for each term in the document. If the user chose the absolute
				// tf function, the result will be the term counting. Otherwise, the number of
				// occurrences will be divided by the number of term occurrences in the document
				Map<String, Integer> termOccurrenceMap = documentTermMap.get(documentId);
				for (String term : termOccurrenceMap.keySet())
					if (isTFRelative) {
						termWeights.add(new TermWeightTuple(term, ((double) termOccurrenceMap.get(term) / allTermOccurrences)));
					} else {
						termWeights.add(new TermWeightTuple(term, termOccurrenceMap.get(term)));
					}
				
				tfMap.put(documentId, termWeights);
			}
		}
		
		return tfMap;
	}

	/*
	 * Checks whether a list of term tuples contains a given term, and returns the
	 * corresponding tuple.
	 * 
	 * @param term the given term to be tested
	 * @param termWeights the list of term tuples
	 * @return the tuple corresponding to the given term if it occurs, <code>null</code> otherwise
	 */
	private TermWeightTuple getTuple(String term, List<TermWeightTuple> termWeights) {
		for (TermWeightTuple termWeight : termWeights)
			if (term.equals(termWeight.getTerm()))
				return termWeight;
		return null;
	}
	
	/*
	 * Checks whether a given weighting calculation function corresponds to
	 * the selected one or not  
	 * 
	 * @param calculatorType the weighting calculator to be tested
	 * @return <code>true</code> if the given function corresponds to the selected one,
	 *  <code>false</code> otherwise
	 */
	private boolean isCalculator(ScoreCalculatorType calculatorType) {
		return this.calculatorType.toString().equals(calculatorType.toString());
	}
	
	/*
	 * Configures the weighting calculation options, given a properties file
	 * 
	 * @param properties the properties extracted from the properties file
	 */
	private void loadProperties(Properties properties) {
		if (properties.containsKey(IRPropertyKeys.TERM_FREQUENCY_VARIANT_TYPE)) {
			String tfVariantProperty = properties.getProperty(IRPropertyKeys.TERM_FREQUENCY_VARIANT_TYPE);
			
			if (tfVariantProperty.equalsIgnoreCase(IRPropertyKeys.TermFrequencyVariant.ABSOLUTE.toString()))
				this.isTFRelative = false;
			else if (tfVariantProperty.equalsIgnoreCase(IRPropertyKeys.TermFrequencyVariant.RELATIVE.toString()))
				this.isTFRelative  = true;
		}
		
		if (properties.containsKey(IRPropertyKeys.SCORE_CALCULATOR_TYPE)) {
			String scoreCalcProperty = properties.getProperty(IRPropertyKeys.SCORE_CALCULATOR_TYPE);
			
			if (scoreCalcProperty.equalsIgnoreCase(IRPropertyKeys.ScoreCalculatorType.TF.toString()))
				this.calculatorType = IRPropertyKeys.ScoreCalculatorType.TF;
			else if (scoreCalcProperty.equalsIgnoreCase(IRPropertyKeys.ScoreCalculatorType.IDF.toString()))
				this.calculatorType = IRPropertyKeys.ScoreCalculatorType.IDF;
			else if (scoreCalcProperty.equalsIgnoreCase(IRPropertyKeys.ScoreCalculatorType.TFIDF.toString()))
				this.calculatorType = IRPropertyKeys.ScoreCalculatorType.TFIDF;
			else this.calculatorType = IRPropertyKeys.ScoreCalculatorType.TF;
		}
	}
}