package org.splab.vocabulary.iR.info;

import java.util.List;
import java.util.Map;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

/**
 * General use interface for Information Retrieval operations in a term-document
 * matrix. It includes methods for vector extraction and vector distance calcula
 * tion
 * 
 * @author gustavojss
 */
public interface RetrievedInfoIF {

	/**
	 * Returns the original term-document matrix. Each columne represents a document
	 * of the vocabulary, and each row, a term. Thus, each slot has a <code>double</code>
	 * value that represents the weight of a term in a document.
	 * @return the original term-document matrix
	 */
	public DoubleMatrix2D getTermDocumentMatrix();
	
	/**
	 * Returns a list with all terms of the vocabulary
	 * @return all terms of the vocabulary
	 */
	public List<String> getAllTerms();
	
	/**
	 * Returns a list with all document identifiers of the vocabulary
	 * @return all documents of the vocabulary
	 */
	public List<String> getAllDocumentIds();
	
	/**
	 * Returns a map with all terms of the vocabulary. Each term has an <code>Integer</code>
	 * identifier to the line of the term-document matrix.
	 * @return all terms of the vocabulary
	 */
	public Map<String, Integer> getAllTermIdsMap();
	
	/**
	 * Returns a map with all document identifiers of the vocabulary. Each document
	 * has a <code>String</code> identifier and an <code>Integer</code> mapping to
	 * the column of the term-document matrix.
	 * @return all document identifiers of the vocabulary
	 */
	public Map<String, Integer> getAllDocumentIdsMap();
	
	/**
	 * Returns the vector representation of an existent document. Basically, it extracts
	 * the column of the matrix that corresponds to the given document. Each slot of the
	 * resulting vector consists in the weight of a term in this document.
	 * @param documentId the document identifier
	 * @return the vector representation of a document with the specified documentId
	 */
	public DoubleMatrix1D getDocumentVector(String documentId);
	
	/**
	 * Returns a vector representing the given term in the documents of the vocabulary.
	 * Each slot of the resulting vector consists in the weight of this term in each do
	 * cument of the vocabulary. Basically, it extracts the row of the matrix that
	 * corresponds to the given term.
	 * @param term the term which vector will be extrated
	 * @return the vector representation of the term in the documents of the vocabulary
	 */
	public DoubleMatrix1D getTermVector(String term);
	
	/**
	 * Calculates the distance between two documents in the vocabulary, given their
	 * respective representing vectors. The type of distance function must be defined
	 * in a properties file, when creating a RetrievedInfoIF implementing class.
	 * @param docVector1 the vector representing the first document
	 * @param docVector2 the vector representing the second document
	 * @see org.splab.vocabulary.iR.IRPropertyKeys.DistanceFunctionType
	 * @return the distance between two documents, represented by vectors
	 */
	public double getDistance(DoubleMatrix1D docVector1, DoubleMatrix1D docVector2);
	
}