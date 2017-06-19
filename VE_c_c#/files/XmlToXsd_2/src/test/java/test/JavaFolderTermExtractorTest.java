package test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.splabs.vocabulary.vxl.VocabularyExtractor;
import org.splabs.vocabulary.vxl.vloccount.LOCParameters;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import util.VXLHandler;

public class JavaFolderTermExtractorTest extends TestCase {
        
	private VXLHandler vxlHandler;
	private Map<String, List<String>> entityProps;
	
	public JavaFolderTermExtractorTest() {
	}
	
	public void setUp() {
		try {
			// deleting previously saved vocabulary
			File resultFile = new File("./files/XmlToXsd/xmltoxsd.vxl");
			if (resultFile.exists()) resultFile.delete();
			
			// Loc extration parameters
			List<LOCParameters> locParam = new LinkedList<LOCParameters>();
			locParam.add(LOCParameters.LOC); locParam.add(LOCParameters.ANNOTATIONS);
			locParam.add(LOCParameters.HEADERS); locParam.add(LOCParameters.INNER_CLASSES);
			locParam.add(LOCParameters.ONLY_PHYSICAL_LINES);
			
			// extracting vocabulary
			VocabularyExtractor vocExtractor = new VocabularyExtractor("xml2xsd", "1.0");
			vocExtractor.extractTermsFromJavaFolder("./files/XmlToXsd", "./files/XmlToXsd/xmltoxsd.vxl", "./files/XmlToXsd/xmltoxsd.csv", locParam);
			
			// parsing vxl file
			String vxlFileName = "./files/XmlToXsd/xmltoxsd.vxl";
			XMLReader xr = XMLReaderFactory.createXMLReader();
			
			this.vxlHandler = new VXLHandler();
			xr.setContentHandler(this.vxlHandler);
			xr.setErrorHandler(this.vxlHandler);
			
			FileReader r = new FileReader(vxlFileName);
			xr.parse(new InputSource(r));
			
			this.entityProps = this.vxlHandler.getEntityProps();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testPackages() {
		
		assertFalse(this.entityProps.containsKey("com"));
		assertTrue(this.entityProps.containsKey("com.pacote2"));
		assertTrue(this.entityProps.containsKey("com.xml2xsd"));
		
		assertTrue(this.entityProps.get("com.pacote2").isEmpty());
		assertTrue(this.entityProps.get("com.xml2xsd").isEmpty());
	}
	
	public void testClasses() {
		
		assertTrue(this.entityProps.containsKey("com.pacote2.EmptyClass"));
		assertTrue(this.entityProps.containsKey("com.pacote2.EmptyMethod"));
		assertTrue(this.entityProps.containsKey("com.xml2xsd.GenericClass"));
		assertTrue(this.entityProps.containsKey("com.xml2xsd.GenericClass$Interna"));
		
		assertFalse(this.entityProps.get("com.pacote2.EmptyClass").isEmpty());
		assertFalse(this.entityProps.get("com.pacote2.EmptyMethod").isEmpty());
		assertFalse(this.entityProps.get("com.xml2xsd.GenericClass").isEmpty());
		assertFalse(this.entityProps.get("com.xml2xsd.GenericClass$Interna").isEmpty());
		
		List<String> classProps = this.entityProps.get("com.pacote2.EmptyClass");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("interface"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("static"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inner"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("javadoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		classProps = this.entityProps.get("com.pacote2.EmptyMethod");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("interface"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("static"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inner"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("javadoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		classProps = this.entityProps.get("com.xml2xsd.GenericClass");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("interface"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("static"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inner"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("javadoc")) {
//				System.out.println(attributeValue);
				assertTrue(attributeValue.equals("Javadoc da classe Generica @author katyusco"));
			}
		}
		
		classProps = this.entityProps.get("com.xml2xsd.GenericClass$Interna");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("interface"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("static"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inner"))
				assertTrue(attributeValue.equals("y"));
			if (attributeName.equals("javadoc"))
				assertTrue(attributeValue.equals(""));
		}
	}
	
	public void testAttributes() {
		
		assertTrue(this.entityProps.containsKey("PUBLIC_CONSTANTE"));
		assertTrue(this.entityProps.containsKey("PRIVATE_CONSTANTE"));
		assertTrue(this.entityProps.containsKey("public_atributo"));
		assertTrue(this.entityProps.containsKey("private_atributo"));
		assertTrue(this.entityProps.containsKey("public_atributo_interna"));
		assertTrue(this.entityProps.containsKey("private_atributo__interna"));
		
		assertFalse(this.entityProps.get("PUBLIC_CONSTANTE").isEmpty());
		assertFalse(this.entityProps.get("PRIVATE_CONSTANTE").isEmpty());
		assertFalse(this.entityProps.get("public_atributo").isEmpty());
		assertFalse(this.entityProps.get("private_atributo").isEmpty());
		assertFalse(this.entityProps.get("public_atributo_interna").isEmpty());
		assertFalse(this.entityProps.get("private_atributo__interna").isEmpty());
		
		List<String> attributeProps = this.entityProps.get("PUBLIC_CONSTANTE");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("visibility"))
				assertTrue(attributeValue.equals("public"));
			if (attributeName.equals("javadoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		attributeProps = this.entityProps.get("PRIVATE_CONSTANTE");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("visibility"))
				assertTrue(attributeValue.equals("private"));
			if (attributeName.equals("javadoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		attributeProps = this.entityProps.get("public_atributo");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("visibility"))
				assertTrue(attributeValue.equals("public"));
			if (attributeName.equals("comment"))
				assertTrue(attributeValue.equals(""));
		}
		
		attributeProps = this.entityProps.get("private_atributo");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("visibility"))
				assertTrue(attributeValue.equals("private"));
			if (attributeName.equals("comment"))
				assertTrue(attributeValue.equals(""));
		}
		
		attributeProps = this.entityProps.get("public_atributo_interna");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("visibility"))
				assertTrue(attributeValue.equals("public"));
			if (attributeName.equals("comment"))
				assertTrue(attributeValue.equals(""));
		}
		
		attributeProps = this.entityProps.get("private_atributo__interna");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("visibility"))
				assertTrue(attributeValue.equals("private"));
			if (attributeName.equals("comment"))
				assertTrue(attributeValue.equals(""));
		}
	}
	
	public void testMethods() {
		
		assertTrue(this.entityProps.containsKey("com.pacote2.EmptyMethod.methodWithNoBody()"));
		assertTrue(this.entityProps.containsKey("com.xml2xsd.GenericClass.public_metodo(String, boolean)"));
		assertTrue(this.entityProps.containsKey("com.xml2xsd.GenericClass.private_metodo(String, boolean)"));
		
		assertFalse(this.entityProps.get("com.pacote2.EmptyMethod.methodWithNoBody()").isEmpty());
		assertFalse(this.entityProps.get("com.xml2xsd.GenericClass.public_metodo(String, boolean)").isEmpty());
		assertFalse(this.entityProps.get("com.xml2xsd.GenericClass.private_metodo(String, boolean)").isEmpty());
		
		List<String> attributeProps = this.entityProps.get("com.pacote2.EmptyMethod.methodWithNoBody()");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("visibility"))
				assertTrue(attributeValue.equals("public"));
			if (attributeName.equals("comment"))
				assertTrue(attributeValue.equals(""));
		}
		
		attributeProps = this.entityProps.get("com.xml2xsd.GenericClass.public_metodo(String, boolean)");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("visibility"))
				assertTrue(attributeValue.equals("public"));
			if (attributeName.equals("javadoc")) {
				assertTrue(attributeValue.equals("Javadoc do método public_metodo @param parametro1 @param parametro2 @return"));
			}
		}
		
		attributeProps = this.entityProps.get("com.xml2xsd.GenericClass.private_metodo(String, boolean)");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("visibility"))
				assertTrue(attributeValue.equals("private"));
			if (attributeName.equals("javadoc"))
				assertTrue(attributeValue.equals(""));
		}
	}
	
	public void testParameters() {
		
		assertTrue(this.entityProps.containsKey("parametro1"));
		assertTrue(this.entityProps.containsKey("parametro2"));
		
		assertTrue(this.entityProps.get("parametro1").isEmpty());
		assertTrue(this.entityProps.get("parametro2").isEmpty());
	}
	
	public void testLocalVariables() {
		
		assertTrue(this.entityProps.containsKey("local_variavel"));
		assertTrue(this.entityProps.get("local_variavel").isEmpty());
	}
}