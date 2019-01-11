package org.splab.vocabulary.iR.info;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.splab.vocabulary.iR.IRPropertyKeys;
import org.splab.vocabulary.iR.IRPropertyKeys.DistanceFunctionType;
import org.splab.vocabulary.iR.util.TermWeightTuple;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.doublealgo.Statistic;
import cern.colt.matrix.doublealgo.Statistic.VectorVectorFunction;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;

/**
 * This class is responsible for representing a map of term tuples in a
 * term-document matrix, and provides operations upon this matrix. The matrix
 * operations includes vector retrieval and distance calculation between two
 * documents in the vocabulary. So far, the distance functions implemented in
 * this class are: Cosine, Tanimoto, Canberra, Euclidean, and Bray-Curtis. The
 * last three are implemented from the Colt API.
 * 
 * Basically, the class iterates over the term tuples map and creates a matrix,
 * in which the columns represent the documents and the rows, the terms. Each
 * slot of this resulting matrix represents the weight of the term in the
 * document. Terms that did not occur in the document will be weight equal to
 * zero.
 * 
 * @author gustavojss
 */
public abstract class RetrievedInfo implements RetrievedInfoIF {

	protected Map<String, Integer> allTerms;
	protected Map<String, Integer> allDocumentIds;
	protected Map<String, List<TermWeightTuple>> tupleMap;

	protected DoubleMatrix2D termDocumentMatrix;
	private DistanceFunctionType distanceFunctionType;

	/**
	 * The constructor of the term-document matrix creation class. It requires a
	 * map in which each document is represented by an identifier, and it has a
	 * map of terms that occurred in the document. Thus, each term is
	 * represented by a tuple: the term and the weight of this term in the
	 * document. It also requires a properties file, containting the distance
	 * calculation options
	 * 
	 * @param tupleMap
	 *            a map representing the extracted voculary
	 * @param properties
	 *            a file containing the distance calculation options
	 * @see org.splab.vocabulary.iR.IRPropertyKeys.DistanceFunctionType
	 */
	public RetrievedInfo(Map<String, List<TermWeightTuple>> tupleMap, Properties props) {
		this.tupleMap = tupleMap;
		this.validateProperties(props);
		this.updateTermDocumentMatrix();
	}

	public DoubleMatrix2D getTermDocumentMatrix() {
		return this.termDocumentMatrix;
	}

	public List<String> getAllTerms() {
		String[] allTermsArray = new String[this.allTerms.keySet().size()];
		for (String term : this.allTerms.keySet())
			allTermsArray[this.allTerms.get(term)] = term;
		return Arrays.asList(allTermsArray);
	}

	public List<String> getAllDocumentIds() {
		String[] allDocIdsArray = new String[this.allDocumentIds.keySet().size()];
		for (String docId : this.allDocumentIds.keySet())
			allDocIdsArray[this.allDocumentIds.get(docId)] = docId;
		return Arrays.asList(allDocIdsArray);
	}

	public Map<String, Integer> getAllTermIdsMap() {
		return allTerms;
	}

	public Map<String, Integer> getAllDocumentIdsMap() {
		return allDocumentIds;
	}

	public DoubleMatrix1D getDocumentVector(String docId) {
		DoubleMatrix1D weightVector = this.termDocumentMatrix.viewColumn(allDocumentIds.get(docId));
		return getVectorInSpace(weightVector);
	}

	public DoubleMatrix1D getTermVector(String term) {
		DoubleMatrix1D termVector = this.termDocumentMatrix.viewRow(allTerms.get(term));
		return termVector;
	}

	/*
	 * Generates all utility data structures when a new map of term tuples is
	 * passed
	 */
	protected final void updateTermDocumentMatrix() {
		this.allTerms = generateAllTerms();
		this.allDocumentIds = new HashMap<String, Integer>();

		int documentCount = tupleMap.keySet().size();
		double[][] array2D = generateNewMap(this.allTerms.size(), documentCount);

		int docIndex = 0;
		for (String docId : this.tupleMap.keySet()) {

			this.allDocumentIds.put(docId, new Integer(docIndex));

			List<TermWeightTuple> tuples = tupleMap.get(docId);

			for (TermWeightTuple tuple : tuples) {
				array2D[allTerms.get(tuple.getTerm())][docIndex] = tuple.getWeight();
			}
			docIndex++;
		}

		this.termDocumentMatrix = new SparseDoubleMatrix2D(array2D);
	}

	/*
	 * Generates a map in which each term has a <code>Integer</code> identifier,
	 * corresponding to its row in the term-document matrix.
	 * 
	 * @return a mapping for all terms in the vocabulary
	 */
	private Map<String, Integer> generateAllTerms() {
		int termIndex = 0;
		Map<String, Integer> allTerms = new HashMap<String, Integer>();

		for (String docId : this.tupleMap.keySet())
			for (TermWeightTuple tuple : this.tupleMap.get(docId)) {
				String term = tuple.getTerm();
				if (!allTerms.containsKey(term)) {
					allTerms.put(term, new Integer(termIndex));
					termIndex++;
				}
			}
		return allTerms;
	}

	/*
	 * LSI operations require that the number of terms must be equal or greater
	 * than the number of documents. This method returns a square matrix when
	 * the number of terms is inferior to the number of documents.
	 * 
	 * @param termsCount the number of terms in the vocabulary
	 * 
	 * @param documentsCount the number of documents in the vocabulary
	 * 
	 * @return a matrix that suits LSI operations
	 */
	protected abstract double[][] generateNewMap(int termsCount, int documentsCount);

	/*
	 * Returns the vector representation in a given vector space. For example,
	 * the LSI vector space differs from the original in the number of terms.
	 * Thereby, a vector from the original vector space must be mapped to the
	 * LSI space for consulting or distance calculation
	 * 
	 * @param vector the vector to be transformed
	 * 
	 * @return a vector representation in a given vector space
	 */
	protected abstract DoubleMatrix1D getVectorInSpace(DoubleMatrix1D vector);

	// -------------------------------------- Distance Functions
	// --------------------------------------
	public double getDistance(DoubleMatrix1D docVector1, DoubleMatrix1D docVector2) {
		if (distanceFunctionType.toString().equals(IRPropertyKeys.DistanceFunctionType.BRAY_CURTIS.toString()))
			return this.getBrayCurtisDistance(docVector1, docVector2);
		else if (distanceFunctionType.toString().equals(IRPropertyKeys.DistanceFunctionType.CANBERRA.toString()))
			return this.getCanberraDistance(docVector1, docVector2);
		else if (distanceFunctionType.toString().equals(IRPropertyKeys.DistanceFunctionType.COSINE.toString()))
			return this.getCosineSimilarity(docVector1, docVector2);
		else if (distanceFunctionType.toString().equals(IRPropertyKeys.DistanceFunctionType.EUCLIDEAN.toString()))
			return this.getEuclideanDistance(docVector1, docVector2);
		else if (distanceFunctionType.toString().equals(IRPropertyKeys.DistanceFunctionType.TANIMOTO.toString()))
			return this.getTanimotoDistance(docVector1, docVector2);

		return 0;
	}

	private double getCosineSimilarity(DoubleMatrix1D docVector1, DoubleMatrix1D docVector2) {
		double cosineSimilarity = docVector1.zDotProduct(docVector2);
		cosineSimilarity /= Math.sqrt(docVector1.zDotProduct(docVector1) * docVector2.zDotProduct(docVector2));
		return cosineSimilarity;
	}

	private double getEuclideanDistance(DoubleMatrix1D docVector1, DoubleMatrix1D docVector2) {
		VectorVectorFunction euclideanFunction = Statistic.EUCLID;
		return euclideanFunction.apply(docVector1, docVector2);
	}

	private double getCanberraDistance(DoubleMatrix1D docVector1, DoubleMatrix1D docVector2) {
		VectorVectorFunction euclideanFunction = Statistic.CANBERRA;
		return euclideanFunction.apply(docVector1, docVector2);
	}

	private double getBrayCurtisDistance(DoubleMatrix1D docVector1, DoubleMatrix1D docVector2) {
		VectorVectorFunction euclideanFunction = Statistic.BRAY_CURTIS;
		return euclideanFunction.apply(docVector1, docVector2);
	}

	private double getTanimotoDistance(DoubleMatrix1D docVector1, DoubleMatrix1D docVector2) {
		double dotProduct = docVector1.zDotProduct(docVector2);

		Algebra algebra = new Algebra();
		double docVector1Magnitude = algebra.norm2(docVector1);
		double docVector2Magnitude = algebra.norm2(docVector2);

		return dotProduct / (Math.pow(docVector1Magnitude, 2) + Math.pow(docVector2Magnitude, 2) - dotProduct);
	}

	/*
	 * Configures the distance function options, given a properties file
	 * 
	 * @param properties the properties extracted from the properties file
	 */
	private void validateProperties(Properties props) {
		if (props.containsKey(IRPropertyKeys.DISTANCE_FUNCTION)) {
			String distanceFunctionTypeProp = props.getProperty(IRPropertyKeys.DISTANCE_FUNCTION);

			if (distanceFunctionTypeProp.equalsIgnoreCase(IRPropertyKeys.DistanceFunctionType.COSINE.toString()))
				this.distanceFunctionType = IRPropertyKeys.DistanceFunctionType.COSINE;
			else if (distanceFunctionTypeProp
					.equalsIgnoreCase(IRPropertyKeys.DistanceFunctionType.EUCLIDEAN.toString()))
				this.distanceFunctionType = IRPropertyKeys.DistanceFunctionType.EUCLIDEAN;
			else if (distanceFunctionTypeProp
					.equalsIgnoreCase(IRPropertyKeys.DistanceFunctionType.BRAY_CURTIS.toString()))
				this.distanceFunctionType = IRPropertyKeys.DistanceFunctionType.BRAY_CURTIS;
			else if (distanceFunctionTypeProp.equalsIgnoreCase(IRPropertyKeys.DistanceFunctionType.CANBERRA.toString()))
				this.distanceFunctionType = IRPropertyKeys.DistanceFunctionType.CANBERRA;
			else if (distanceFunctionTypeProp.equalsIgnoreCase(IRPropertyKeys.DistanceFunctionType.TANIMOTO.toString()))
				this.distanceFunctionType = IRPropertyKeys.DistanceFunctionType.TANIMOTO;
			else
				this.distanceFunctionType = IRPropertyKeys.DistanceFunctionType.COSINE;
		}
	}
}