package test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Properties;

import org.splab.vocabulary.vxl.VXLReader;
import org.splab.vocabulary.vxl.iterator.VXLClassesIterator;
import org.splab.vocabulary.vxl.iterator.VXLIterator;
import org.splab.vocabulary.vxl.iterator.javamodel.CPPClass;
import org.splab.vocabulary.vxl.iterator.javamodel.ContainerEntity;

import junit.framework.TestCase;

/**
 * Test the classes' informations.
 * 
 * @author Israel Gomes de Lima.
 * @since September 24, 2018.
 */
public class VXLClassesIteratorTest extends TestCase {

	private VXLIterator iterator;
	private String[] classNames = { "classIF1", "classIF2", "class1", "classIC1", "classIC2", "classIC3", "classIC4" };

	public void setUp() throws Exception {

		String propsFileName = "files/readerClasses.properties";
		String vxlFileName = "files/testproject.vxl";

		Properties props = new Properties();
		props.load(new BufferedInputStream(new FileInputStream(propsFileName)));

		VXLReader reader = new VXLReader(props, vxlFileName);
		this.iterator = reader.iterator();
	}

	public void testIterator() {

		assertTrue(this.iterator instanceof VXLClassesIterator);

		int classCount = 0;
		while (this.iterator.hasNext()) {
			this.iterator.next();
			classCount++;
		}

		assertTrue(classCount == classNames.length);
	}

	public void testClass() {
		while (this.iterator.hasNext()) {
			ContainerEntity classContainer = this.iterator.next();
			CPPClass aClass = (CPPClass) classContainer.getEntity();

			String className = classContainer.getEntityIdentifier();

			if (className.equals("class1")) {
				assertEquals(aClass.getSloc().toString(), "52");
				assertEquals(aClass.getAbs().toString(), "y");
				assertEquals(aClass.getInn().toString(), "n");
				assertEquals(aClass.getCall().size(), 2);
				assertEquals(aClass.getClazz().size(), 2);
				assertEquals(aClass.getComm().size(), 2);
				assertEquals(aClass.getEnum().size(), 2);
				assertEquals(aClass.getField().size(), 2);
				assertEquals(aClass.getFunc().size(), 2);
				assertEquals(aClass.getGvar().size(), 2);
				assertEquals(aClass.getLit().size(), 2);
				assertEquals(aClass.getMth().size(), 2);
				assertEquals(aClass.getPrttp().size(), 2);
				assertEquals(aClass.getStrt().size(), 2);
				assertEquals(aClass.getUnion().size(), 2);
			}

			if (className.equals("classIF1")) {
				assertEquals(aClass.getSloc().toString(), "2");
				assertEquals(aClass.getAbs().toString(), "n");
				assertEquals(aClass.getInn().toString(), "n");
				assertEquals(aClass.getCall().size(), 0);
				assertEquals(aClass.getClazz().size(), 0);
				assertEquals(aClass.getComm().size(), 0);
				assertEquals(aClass.getEnum().size(), 0);
				assertEquals(aClass.getField().size(), 0);
				assertEquals(aClass.getFunc().size(), 0);
				assertEquals(aClass.getGvar().size(), 0);
				assertEquals(aClass.getLit().size(), 0);
				assertEquals(aClass.getMth().size(), 0);
				assertEquals(aClass.getPrttp().size(), 0);
				assertEquals(aClass.getStrt().size(), 0);
				assertEquals(aClass.getUnion().size(), 0);
			}

			if (className.equals("classIF2")) {
				assertEquals(aClass.getSloc().toString(), "2");
				assertEquals(aClass.getAbs().toString(), "n");
				assertEquals(aClass.getInn().toString(), "n");
				assertEquals(aClass.getCall().size(), 0);
				assertEquals(aClass.getClazz().size(), 0);
				assertEquals(aClass.getComm().size(), 0);
				assertEquals(aClass.getEnum().size(), 0);
				assertEquals(aClass.getField().size(), 0);
				assertEquals(aClass.getFunc().size(), 0);
				assertEquals(aClass.getGvar().size(), 0);
				assertEquals(aClass.getLit().size(), 0);
				assertEquals(aClass.getMth().size(), 0);
				assertEquals(aClass.getPrttp().size(), 0);
				assertEquals(aClass.getStrt().size(), 0);
				assertEquals(aClass.getUnion().size(), 0);
			}

			if (className.equals("classIC1")) {
				assertEquals(aClass.getSloc().toString(), "2");
				assertEquals(aClass.getAbs().toString(), "n");
				assertEquals(aClass.getInn().toString(), "y");
				assertEquals(aClass.getCall().size(), 0);
				assertEquals(aClass.getClazz().size(), 0);
				assertEquals(aClass.getComm().size(), 0);
				assertEquals(aClass.getEnum().size(), 0);
				assertEquals(aClass.getField().size(), 0);
				assertEquals(aClass.getFunc().size(), 0);
				assertEquals(aClass.getGvar().size(), 0);
				assertEquals(aClass.getLit().size(), 0);
				assertEquals(aClass.getMth().size(), 0);
				assertEquals(aClass.getPrttp().size(), 0);
				assertEquals(aClass.getStrt().size(), 0);
				assertEquals(aClass.getUnion().size(), 0);
			}

			if (className.equals("classIC2")) {
				assertEquals(aClass.getSloc().toString(), "2");
				assertEquals(aClass.getAbs().toString(), "n");
				assertEquals(aClass.getInn().toString(), "y");
				assertEquals(aClass.getCall().size(), 0);
				assertEquals(aClass.getClazz().size(), 0);
				assertEquals(aClass.getComm().size(), 0);
				assertEquals(aClass.getEnum().size(), 0);
				assertEquals(aClass.getField().size(), 0);
				assertEquals(aClass.getFunc().size(), 0);
				assertEquals(aClass.getGvar().size(), 0);
				assertEquals(aClass.getLit().size(), 0);
				assertEquals(aClass.getMth().size(), 0);
				assertEquals(aClass.getPrttp().size(), 0);
				assertEquals(aClass.getStrt().size(), 0);
				assertEquals(aClass.getUnion().size(), 0);
			}

			if (className.equals("classIC3")) {
				assertEquals(aClass.getSloc().toString(), "2");
				assertEquals(aClass.getAbs().toString(), "n");
				assertEquals(aClass.getInn().toString(), "n");
				assertEquals(aClass.getCall().size(), 0);
				assertEquals(aClass.getClazz().size(), 0);
				assertEquals(aClass.getComm().size(), 0);
				assertEquals(aClass.getEnum().size(), 0);
				assertEquals(aClass.getField().size(), 0);
				assertEquals(aClass.getFunc().size(), 0);
				assertEquals(aClass.getGvar().size(), 0);
				assertEquals(aClass.getLit().size(), 0);
				assertEquals(aClass.getMth().size(), 0);
				assertEquals(aClass.getPrttp().size(), 0);
				assertEquals(aClass.getStrt().size(), 0);
				assertEquals(aClass.getUnion().size(), 0);
			}

			if (className.equals("classIC4")) {
				assertEquals(aClass.getSloc().toString(), "2");
				assertEquals(aClass.getAbs().toString(), "n");
				assertEquals(aClass.getInn().toString(), "n");
				assertEquals(aClass.getCall().size(), 0);
				assertEquals(aClass.getClazz().size(), 0);
				assertEquals(aClass.getComm().size(), 0);
				assertEquals(aClass.getEnum().size(), 0);
				assertEquals(aClass.getField().size(), 0);
				assertEquals(aClass.getFunc().size(), 0);
				assertEquals(aClass.getGvar().size(), 0);
				assertEquals(aClass.getLit().size(), 0);
				assertEquals(aClass.getMth().size(), 0);
				assertEquals(aClass.getPrttp().size(), 0);
				assertEquals(aClass.getStrt().size(), 0);
				assertEquals(aClass.getUnion().size(), 0);
			}
		}
	}
}