package org.splab.vocabulary.iR.info;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.splab.vocabulary.iR.IRPropertyKeys;
import org.splab.vocabulary.iR.util.TermWeightTuple;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.colt.matrix.linalg.SingularValueDecomposition;

/**
 * This class is responsible for the reduction of the original term-document matrix in number of
 * terms. It uses Singular Value Decomposition of the original matrix, provided by the Colt API.
 * Next, it uses Low-Rank Approximation to reduce the matrix in k rows. The k value is given in
 * a properties file; when not properly given, a default operation is executed to generate a k
 * that depends on the number of terms and documents of the original matrix.
 * 
 * This class provides operations to retrieve the reduced matrix, and to transform any vector from
 * the original space to the reduced one.
 * 
 * @author admin
 * @see org.splab.vocabulary.iR.IRPropertyKeys.LSI_LOW_RANK_VALUE
 */
public class LSIInfo extends RetrievedInfo {
	
	private DoubleMatrix2D lsiTermDocumentMatrix;
	protected DoubleMatrix2D lsiTransform;
	private int lowRank = 0;
	
	/**
	 * The constructor of the term-document reduction class. It requires a map in which each
	 * document is represented by an identifier, and it has a map of terms that occurred in the
	 * document. Thus, each term is represented by a tuple: the term and the weight of this
	 * term in the document. It requires a properties file, containting the distance calculation
	 * options.
	 * 
	 * It also requires an additional parameter: the low-rank approximation. This value is used
	 * in the matrix reduction, and represents the number of terms of the reduced matrix. When
	 * not defined, a default value is used, depending on the number of terms and documents of
	 * the original matrix.
	 * 
	 * @param tupleMap a map representing the extracted voculary
	 * @param properties a file containing the distance calculation options
	 */
	public LSIInfo(Map<String, List<TermWeightTuple>> tupleMap, Properties props) {
		super(tupleMap, props);
		
		this.validateProperties(props);
		this.generateLowRankApproximation();
	}
	
	/**
	 * Returns the LSI-reduced matrix. This matrix is resulted from the reduction of the original
	 * term-document matrix in the number of terms. The weights of each term in each document in
	 * the vocabulary are recalculated.
	 * @return the term-document matrix reduced by the LSI procedure
	 */
	public DoubleMatrix2D getLsiTermDocumentMatrix() {
		return this.lsiTermDocumentMatrix;
	}
	
	/**
	 * Returns a matrix to map a vector from the original vector space (represented by the original
	 * term-document matrix) to the LSI vector space.
	 * @return the transformation matrix from the original space to the LSI space
	 */
	public DoubleMatrix2D getLsiTransformMatrix() {
		return this.lsiTransform;
	}
	
	@Override
	public DoubleMatrix1D getDocumentVector(String docId) {
		return this.lsiTermDocumentMatrix.viewColumn(allDocumentIds.get(docId));
	}
	
	@Override
	public DoubleMatrix1D getVectorInSpace(DoubleMatrix1D weightVector) {	
		Algebra matrixAlgebra = new Algebra();
		DoubleMatrix1D lsiWeightVector = matrixAlgebra.mult(lsiTransform, weightVector);
		return lsiWeightVector;
	}
	
	@Override
	protected double[][] generateNewMap(int termsCount, int documentsCount) {
		if (termsCount < documentsCount) {
			return new double[documentsCount][documentsCount];
		} else return new double[termsCount][documentsCount];
	}
	
	/*
	 * This method is responsible for the LSI-reduction process. It uses Singular Value
	 * Decomposition from Colt's API. This process results in two matrices: (i) the term-
	 * document reduced matrix, and (ii) the transformation matrix, that each vector from
	 * the original vector space must be mapped to the LSI space.
	 * More information in: http://nlp.stanford.edu/IR-book/pdf/18lsi.pdf
	 */
	private void generateLowRankApproximation() {
		DoubleMatrix2D termDocumentMatrix = this.ensureRectangular();
		SingularValueDecomposition svd = new SingularValueDecomposition(termDocumentMatrix);
		
		DoubleMatrix2D u = svd.getU();
		DoubleMatrix2D s = svd.getS();
		
		// when not defined, use a default parameter defined by Kuhn et al.'s "Semantic Clustering"
		if (this.lowRank == 0)
			this.lowRank = (int) Math.pow((this.termDocumentMatrix.rows() * this.termDocumentMatrix.columns()), 0.2);
				
		Algebra matrixOperations = new Algebra();
				
		DoubleMatrix2D uPrime = matrixOperations.subMatrix(u, 0, u.rows()-1, 0, lowRank-1);
		DoubleMatrix2D sPrime = matrixOperations.subMatrix(s, 0, lowRank-1, 0, lowRank-1);
		
		DoubleMatrix2D sPrimeInverse = matrixOperations.inverse(sPrime);
		DoubleMatrix2D uPrimeTranspose = matrixOperations.transpose(uPrime);
		
		// generating the transformation matrix 
		this.lsiTransform = matrixOperations.mult(sPrimeInverse, uPrimeTranspose);
		// create reduced LSI matrix
		this.lsiTermDocumentMatrix = matrixOperations.mult(lsiTransform, termDocumentMatrix);
	}
	
	/*
	 * Ensures that the given term-document matrix is rectangular, i.e., the number of
	 * terms must be equal or greater than the number of documents. If it is not, a new
	 * term-document matrix is generated with the right dimensions
	 * @return a term-document matrix with the number of documents at least equal to the number of terms
	 */
	private DoubleMatrix2D ensureRectangular() {
		if (this.termDocumentMatrix.rows() < this.termDocumentMatrix.columns()) {
			int bounds = this.termDocumentMatrix.columns();
			DoubleMatrix2D newMatrix = new SparseDoubleMatrix2D(bounds, bounds);
			
			for (int i = 0; i < this.termDocumentMatrix.rows(); i++)
				for (int j = 0; j < this.termDocumentMatrix.columns(); j++)
					newMatrix.set(i, j, this.termDocumentMatrix.get(i, j));
			
			return newMatrix;
		}
		
		return this.termDocumentMatrix;
	}
	
	/*
	 * Configures the specific parameters of LSI from a properties file
	 * @param properties the properties extracted from the properties file
	 */
	private void validateProperties(Properties props) {
		if (props.containsKey(IRPropertyKeys.LSI_LOW_RANK_VALUE)) {
			String lowRankProp = props.getProperty(IRPropertyKeys.LSI_LOW_RANK_VALUE);
			this.lowRank = Integer.parseInt(lowRankProp);
		}
	}
}