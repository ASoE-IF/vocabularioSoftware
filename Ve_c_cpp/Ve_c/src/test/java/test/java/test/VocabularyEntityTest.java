package test.java.test;

import java.io.FileReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import test.java.util.VXLHandler;

import org.splab.vocabulary.extractor.VocabularyRunner;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class VocabularyEntityTest extends TestCase {
        
	private VXLHandler vxlHandler;
	private Map<String, List<String>> entityProps;
	
	public VocabularyEntityTest() {
	}
	
	public void setUp() {
		try { 
			String[] args = {"-n", "xmltoxsd", "-d", "./files/XmlToXsd/",
					"-loc", "ih", "-vxl", "./files/XmlToXsd/xmltoxsd.vxl", 
					"-csv", "./files/XmlToXsd/xmltoxsd.csv", "-func", "-file", "CH"}; 
			
			VocabularyRunner.main(args);
		
			// parsing vxl file
			String vxlFileName = "./files/XmlToXsd/xmltoxsd.vxl";
			XMLReader xr = XMLReaderFactory.createXMLReader();

			this.vxlHandler = new VXLHandler();
			xr.setContentHandler(this.vxlHandler);
			xr.setErrorHandler(this.vxlHandler);

			FileReader r = new FileReader(vxlFileName);
			xr.parse(new InputSource(r));
			
			this.entityProps = this.vxlHandler.getEntityProps();
			System.out.println(entityProps.keySet());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testFile() {

		assertTrue(this.entityProps.containsKey(".\\files\\XmlToXsd\\src\\GenericFile.c"));
		assertFalse(this.entityProps.get(".\\files\\XmlToXsd\\src\\GenericFile.c").isEmpty());
		
		List<String> fileProps = this.entityProps.get(".\\files\\XmlToXsd\\src\\GenericFile.c");
		for (Iterator<String> it = fileProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("607"));
		}
	}
	
	public void testFunctions() {
		assertTrue(this.entityProps.containsKey("funcao_principal"));
		assertTrue(this.entityProps.containsKey("funcaoInterna"));
		
		assertFalse(this.entityProps.get("funcao_principal").isEmpty());
		assertFalse(this.entityProps.get("funcaoInterna").isEmpty());

		List<String> attributeProps = this.entityProps.get("funcao_principal");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("volatile"));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("static"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("12"));
		}
		
		attributeProps = this.entityProps.get("funcaoInterna");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals(""));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("y"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("7"));
		}
	}
	
	public void testParameters() {
		assertTrue(this.entityProps.containsKey("paramEnum"));
		assertTrue(this.entityProps.containsKey("p1"));
		
		assertTrue(this.entityProps.get("paramEnum").isEmpty());
		assertTrue(this.entityProps.get("p1").isEmpty());
	}
	
	public void testStructs() {

		assertTrue(this.entityProps.containsKey("estrutura1"));
		assertTrue(this.entityProps.containsKey("estrutura3"));
		
		assertFalse(this.entityProps.get("estrutura1").isEmpty());
		assertFalse(this.entityProps.get("estrutura3").isEmpty());

		List<String> attributeProps = this.entityProps.get("estrutura1");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();

			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("6"));
		}
		
		attributeProps = this.entityProps.get("estrutura3");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();

			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("9"));
		}
	}
	
	public void testUnions() {

		assertTrue(this.entityProps.containsKey("union1"));
		assertTrue(this.entityProps.containsKey("union3"));
		
		assertFalse(this.entityProps.get("union1").isEmpty());
		assertFalse(this.entityProps.get("union3").isEmpty());

		List<String> attributeProps = this.entityProps.get("union1");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();

			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("6"));
		}
		
		attributeProps = this.entityProps.get("union3");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();

			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("9"));
		}
	}
	
	public void testEnumerations() {

		assertTrue(this.entityProps.containsKey("enumeration"));
		
		assertFalse(this.entityProps.get("enumeration").isEmpty());

		List<String> attributeProps = this.entityProps.get("enumeration");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();

			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("4"));
		}
	}
	
	/**Test members of struct or onion**/
	public void testMembers() {
		assertTrue(this.entityProps.containsKey("membroMatriz"));
		assertTrue(this.entityProps.containsKey("campoDeBits1"));
		
		assertTrue(this.entityProps.get("membroMatriz").isEmpty());
		assertTrue(this.entityProps.get("campoDeBits1").isEmpty());
	}
	
	/**Test constant of enum**/
	public void testConstantsEnums() {
		assertTrue(this.entityProps.containsKey("CONST1"));
		assertTrue(this.entityProps.containsKey("CONST2"));
	}
}