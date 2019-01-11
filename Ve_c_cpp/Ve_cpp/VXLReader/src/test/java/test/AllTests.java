package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Execute the test to Files and Funtions.
 * @author Israel Gomes de Lima.
 * @since September 12, 2018.
 */
@RunWith(Suite.class)
@SuiteClasses({
	VXLFilesIteratorTest.class,
	VXLFunctionsIteratorTest.class,
	VXLClassesIteratorTest.class,
	VXLMethodsIteratorTest.class
})
public class AllTests{
	
}