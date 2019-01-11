package org.splab.vocabulary.iR.info;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.splab.vocabulary.iR.util.TermWeightTuple;

import cern.colt.matrix.DoubleMatrix1D;

/**
 * This class provides operations to the original term-document matrix. No alteration is
 * made in the matrix extracted from term tuples map.
 * 
 * @author gustavojss
 */
public class IRInfo extends RetrievedInfo {
	
	/**
	 * The constructor of the term-document matrix creation class. It requires a map in which
	 * each document is represented by an identifier, and it has a map of terms that occurred
	 * in the document. Thus, each term is represented by a tuple: the term and the weight of
	 * this term in the document. It also requires a properties file, containting the distance
	 * calculation options.
	 * 
	 * @param tupleMap a map representing the extracted voculary
	 * @param properties a file containing the distance calculation options
	 * @see org.splab.vocabulary.iR.IRPropertyKeys.DistanceFunctionType
	 */
	public IRInfo(Map<String, List<TermWeightTuple>> tupleMap, Properties props) {
		super(tupleMap, props);
	}
	
	protected DoubleMatrix1D getVectorInSpace(DoubleMatrix1D vector) {
		return vector;
	}

	@Override
	protected double[][] generateNewMap(int termsCount, int documentsCount) {
		return new double[termsCount][documentsCount];
	}
}