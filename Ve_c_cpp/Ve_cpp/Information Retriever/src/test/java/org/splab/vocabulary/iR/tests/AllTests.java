package org.splab.vocabulary.iR.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Roberto A. Bittencourt
 * @version 1.0
 * This class runs all test classes.
 */
public class AllTests {

	public static Test suite() {
		
		TestSuite suite = new TestSuite("All tests for IRUtils");
		
		//$JUnit-BEGIN$
		suite.addTest(new TestSuite(IRAbsoluteTermFrequencyTest.class));
		suite.addTest(new TestSuite(IRRelativeTermFrequencyTest.class));
		suite.addTest(new TestSuite(IRInverseDocumentFrequencyTest.class));
		suite.addTest(new TestSuite(IRAbsoluteTFIDFTest.class));
		suite.addTest(new TestSuite(IRRelativeTFIDFTest.class));
		
//		suite.addTest(new TestSuite(IRTest.class));
//		suite.addTest(new TestSuite(LSIInfoTest.class));
		
		//$JUnit-END$
		return suite;
	}
}