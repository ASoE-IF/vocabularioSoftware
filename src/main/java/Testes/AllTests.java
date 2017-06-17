package Testes;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for VocabularyExtractor");
		
		suite.addTest(new TestSuite(AnnotationsTest.class));
		suite.addTest(new TestSuite(JavaFolderTermExtractorTest.class));
		suite.addTest(new TestSuite(JavaFolderTermExtractorTest_2.class));
		suite.addTest(new TestSuite(LocPerEntityTest.class));
		suite.addTest(new TestSuite(PhysicalLocTest.class));
		return suite;
	}
}