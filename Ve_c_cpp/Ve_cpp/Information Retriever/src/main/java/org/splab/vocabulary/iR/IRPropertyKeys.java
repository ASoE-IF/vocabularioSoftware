package org.splab.vocabulary.iR;

public class IRPropertyKeys {
	
	public static final String IR_FUNCTION_TYPE = "irFunctionType";
	public enum IRFunctionType {
		IR,
		LSI
	}
	
	public static final String TERM_FREQUENCY_VARIANT_TYPE = "tfVariant";
	public enum TermFrequencyVariant {
		ABSOLUTE,
		RELATIVE
	}
	
	public static final String SCORE_CALCULATOR_TYPE = "scoreCalculator";
	public enum ScoreCalculatorType {
		TF,
		IDF,
		TFIDF
	}
	
	public static final String DISTANCE_FUNCTION = "distanceFunction";
	public enum DistanceFunctionType {
		COSINE,
		EUCLIDEAN,
		BRAY_CURTIS,
		CANBERRA,
		TANIMOTO
	}

	public static final String LSI_LOW_RANK_VALUE = "lowRankValue"; 

}