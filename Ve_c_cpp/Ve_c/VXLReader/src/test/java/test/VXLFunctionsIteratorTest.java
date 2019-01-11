package test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Properties;

import junit.framework.TestCase;

import org.splab.vocabulary.vxl.VXLReader;
import org.splab.vocabulary.vxl.iterator.VXLIterator;
import org.splab.vocabulary.vxl.iterator.VXLFunctionsIterator;
import org.splab.vocabulary.vxl.iterator.javamodel.ContainerEntity;
import org.splab.vocabulary.vxl.iterator.javamodel.Func;

/**
 * Test the files' informations.
 * 
 * @author Israel Gomes de Lima.
 * @since September 12, 2018.
 */
public class VXLFunctionsIteratorTest extends TestCase {

	private VXLIterator iterator;
	private String[] functionNames = { "func1", "func1_interna" };

	public void setUp() throws Exception {

		String propsFileName = "files/readerFunctions.properties";
		String vxlFileName = "files/testproject.vxl";

		Properties props = new Properties();
		props.load(new BufferedInputStream(new FileInputStream(propsFileName)));

		VXLReader reader = new VXLReader(props, vxlFileName);
		this.iterator = reader.iterator();
	}

	public void testIterator() {

		assertTrue(this.iterator instanceof VXLFunctionsIterator);

		int functionCount = 0;
		while (this.iterator.hasNext()) {
			this.iterator.next();
			functionCount++;
		}

		assertTrue(functionCount == functionNames.length);
	}

	public void testFunction() {
		while (this.iterator.hasNext()) {
			ContainerEntity functionContainer = this.iterator.next();
			Func aFunction = (Func) functionContainer.getEntity();

			String functionName = functionContainer.getEntityIdentifier();

			if (functionName.equals("func1")) {
				assertEquals(aFunction.getSloc().toString(), "53");
				assertEquals(aFunction.getAccess(), "const");
				assertEquals(aFunction.getInn(), "n");
				assertEquals(aFunction.getStorage(), "auto");
				assertEquals(aFunction.getCall().size(), 2);
				assertEquals(aFunction.getComm().size(), 2);
				assertEquals(aFunction.getEnum().size(), 2);
				assertEquals(aFunction.getFunc().size(), 1);
				assertEquals(aFunction.getGvar().size(), 2);
				assertEquals(aFunction.getLit().size(), 2);
				assertEquals(aFunction.getLvar().size(), 2);
				assertEquals(aFunction.getParam().size(), 2);
				assertEquals(aFunction.getPrttp().size(), 2);
				assertEquals(aFunction.getStrt().size(), 2);
				assertEquals(aFunction.getUnion().size(), 2);
			}

			if (functionName.equals("func1_interna")) {
				assertEquals(aFunction.getSloc().toString(), "4");
				assertEquals(aFunction.getAccess(), "");
				assertEquals(aFunction.getInn(), "y");
				assertEquals(aFunction.getStorage(), "static");
				assertEquals(aFunction.getCall().size(), 1);
				assertEquals(aFunction.getComm().size(), 0);
				assertEquals(aFunction.getEnum().size(), 0);
				assertEquals(aFunction.getFunc().size(), 0);
				assertEquals(aFunction.getGvar().size(), 0);
				assertEquals(aFunction.getLit().size(), 0);
				assertEquals(aFunction.getLvar().size(), 0);
				assertEquals(aFunction.getParam().size(), 0);
				assertEquals(aFunction.getPrttp().size(), 0);
				assertEquals(aFunction.getStrt().size(), 0);
				assertEquals(aFunction.getUnion().size(), 0);
			}
		}
	}
}