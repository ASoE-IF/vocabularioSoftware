package test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Properties;

import org.splab.vocabulary.vxl.VXLReader;
import org.splab.vocabulary.vxl.iterator.VXLIterator;
import org.splab.vocabulary.vxl.iterator.VXLMethodsIterator;
import org.splab.vocabulary.vxl.iterator.javamodel.ContainerEntity;
import org.splab.vocabulary.vxl.iterator.javamodel.Method;

import junit.framework.TestCase;

/**
 * Test the methods' informations.
 * 
 * @author Israel Gomes de Lima.
 * @since September 24, 2018.
 */
public class VXLMethodsIteratorTest extends TestCase {

	private VXLIterator iterator;
	private String[] methodsNames = { "methodo1", "methodo2" };

	public void setUp() throws Exception {

		String propsFileName = "files/readerMethods.properties";
		String vxlFileName = "files/testproject.vxl";

		Properties props = new Properties();
		props.load(new BufferedInputStream(new FileInputStream(propsFileName)));

		VXLReader reader = new VXLReader(props, vxlFileName);
		this.iterator = reader.iterator();
	}

	public void testIterator() {

		assertTrue(this.iterator instanceof VXLMethodsIterator);

		int methodsCount = 0;
		while (this.iterator.hasNext()) {
			this.iterator.next();
			methodsCount++;
		}

		assertTrue(methodsCount == methodsNames.length);
	}

	public void testMethods() {
		while (this.iterator.hasNext()) {
			ContainerEntity methodsContainer = this.iterator.next();
			Method aMethod = (Method) methodsContainer.getEntity();

			String methodName = methodsContainer.getEntityIdentifier();

			if (methodName.equals("methodo1")) {
				assertEquals(aMethod.getAccess().toString(), "const");
				assertEquals(aMethod.getModifies().toString(), "const");
				assertEquals(aMethod.getStorage().toString(), "auto");
				assertEquals(aMethod.getVisib().toString(), "pub");
				assertEquals(aMethod.getVirtual().toString(), "y");
				assertEquals(aMethod.getSloc().toString(), "9");
				assertEquals(aMethod.getCall().size(), 2);
				assertEquals(aMethod.getClazz().size(), 2);
				assertEquals(aMethod.getComm().size(), 2);
				assertEquals(aMethod.getEnum().size(), 2);
				assertEquals(aMethod.getField().size(), 2);
				assertEquals(aMethod.getFunc().size(), 2);
				assertEquals(aMethod.getGvar().size(), 2);
				assertEquals(aMethod.getLit().size(), 2);
				assertEquals(aMethod.getLvar().size(), 2);
				assertEquals(aMethod.getParam().size(), 2);
				assertEquals(aMethod.getPrttp().size(), 2);
				assertEquals(aMethod.getStrt().size(), 2);
				assertEquals(aMethod.getUnion().size(), 2);
			}
			
			if (methodName.equals("methodo2")) {
				assertEquals(aMethod.getAccess().toString(), "const");
				assertEquals(aMethod.getModifies().toString(), "const");
				assertEquals(aMethod.getStorage().toString(), "auto");
				assertEquals(aMethod.getVisib().toString(), "pub");
				assertEquals(aMethod.getVirtual().toString(), "y");
				assertEquals(aMethod.getSloc().toString(), "2");
				assertEquals(aMethod.getCall().size(), 0);
				assertEquals(aMethod.getClazz().size(), 0);
				assertEquals(aMethod.getComm().size(), 0);
				assertEquals(aMethod.getEnum().size(), 0);
				assertEquals(aMethod.getField().size(), 0);
				assertEquals(aMethod.getFunc().size(), 0);
				assertEquals(aMethod.getGvar().size(), 0);
				assertEquals(aMethod.getLit().size(), 0);
				assertEquals(aMethod.getLvar().size(), 0);
				assertEquals(aMethod.getParam().size(), 0);
				assertEquals(aMethod.getPrttp().size(), 0);
				assertEquals(aMethod.getStrt().size(), 0);
				assertEquals(aMethod.getUnion().size(), 0);
			}
		}
	}
}