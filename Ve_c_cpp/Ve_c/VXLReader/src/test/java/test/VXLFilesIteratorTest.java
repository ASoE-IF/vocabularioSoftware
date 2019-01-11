package test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;

import org.splab.vocabulary.vxl.VXLReader;
import org.splab.vocabulary.vxl.iterator.VXLFilesIterator;
import org.splab.vocabulary.vxl.iterator.VXLIterator;
import org.splab.vocabulary.vxl.iterator.javamodel.CFile;
import org.splab.vocabulary.vxl.iterator.javamodel.ContainerEntity;

/**
 * Test Files and their properties.
 * 
 * @author Israel Gomes de Lima.
 * @since September 12, 2018.
 */
public class VXLFilesIteratorTest extends TestCase {

	private VXLIterator iterator;
	private String[] filesNames = { "Arquivo Genérico.c" };

	private List<String> fileList = Arrays.asList(filesNames);

	public void setUp() throws Exception {

		String propsFileName = "files/readerFiles.properties";
		String vxlFileName = "files/testproject.vxl";

		Properties props = new Properties();
		props.load(new BufferedInputStream(new FileInputStream(propsFileName)));

		VXLReader reader = new VXLReader(props, vxlFileName);
		this.iterator = reader.iterator();
	}

	public void testIterator() {

		assertTrue(this.iterator instanceof VXLFilesIterator);

		int fileCount = 0;
		while (this.iterator.hasNext()) {
			this.iterator.next();
			fileCount++;
		}

		assertEquals(fileCount, filesNames.length);
	}

	public void testFile() {
		while (this.iterator.hasNext()) {
			ContainerEntity fileContainer = this.iterator.next();
			if (fileContainer.getEntity() instanceof CFile) {
				CFile aFile = (CFile) fileContainer.getEntity();

				String fileName = fileContainer.getEntityIdentifier();
				assertTrue(this.fileList.contains(fileName.split("/")[fileName.split("/").length - 1]));

				if (fileName.equals("Arquivo Genérico.c")) {
					assertEquals(aFile.getSloc().toString(), "656");
					assertEquals(aFile.getCall().size(), 1);
					assertEquals(aFile.getComm().size(), 2);
					assertEquals(aFile.getEnum().size(), 2);
					assertEquals(aFile.getError().size(), 2);
					assertEquals(aFile.getFunc().size(), 1);
					assertEquals(aFile.getGvar().size(), 2);
					assertEquals(aFile.getInclude().size(), 2);
					assertEquals(aFile.getLit().size(), 2);
					assertEquals(aFile.getMacro().size(), 4);
					assertEquals(aFile.getPragma().size(), 2);
					assertEquals(aFile.getPrttp().size(), 2);
					assertEquals(aFile.getStrt().size(), 2);
					assertEquals(aFile.getUnion().size(), 2);
				}
			}
		}
	}
}