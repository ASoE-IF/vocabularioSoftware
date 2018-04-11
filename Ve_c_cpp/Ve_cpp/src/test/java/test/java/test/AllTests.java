package test.java.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for VocabularyExtractor");
		
		suite.addTest(new TestSuite(VocabularyEntityTest.class));
		suite.addTest(new TestSuite(VocabularyBodyTest.class));
		suite.addTest(new TestSuite(LocPerEntityTest.class));
		suite.addTest(new TestSuite(PhysicalLocTest.class));
		return suite;
	}
}