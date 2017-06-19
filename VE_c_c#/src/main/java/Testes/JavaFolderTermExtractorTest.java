package Testes;

import java.io.FileReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import Vocabulary.Extractors.VocabularyRunner;

public class JavaFolderTermExtractorTest extends TestCase {
        
	private VXLHandler vxlHandler;
	private Map<String, List<String>> entityProps;
	
	public JavaFolderTermExtractorTest() {
	}
	
	public void setUp() {
		try { 
			String[] args = {"-n", "xmltoxsd", "-d", "./files/XmlToXsd/",
					"-loc", "iah", "-vxl", "./files/XmlToXsd/xmltoxsd.vxl", 
					"-csv", "./files/XmlToXsd/xmltoxsd.csv"}; 
			
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
	
	public void testPackages() {
		assertFalse(this.entityProps.containsKey("com"));
		
		assertTrue(this.entityProps.containsKey("/src/:com.pacote2"));
		assertTrue(this.entityProps.containsKey("/src/:com.xml2xsd"));
		assertTrue(this.entityProps.get("/src/:com.pacote2").isEmpty());
		assertTrue(this.entityProps.get("/src/:com.xml2xsd").isEmpty());
	}
	
	public void testClasses() {
		
		assertTrue(this.entityProps.containsKey("/src/:com.pacote2.EmptyClass"));
		assertTrue(this.entityProps.containsKey("/src/:com.pacote2.EmptyMethod"));
		assertTrue(this.entityProps.containsKey("/src/:com.xml2xsd.GenericClass"));
		assertTrue(this.entityProps.containsKey("/src/:com.xml2xsd.GenericClass$Interna"));
		
		assertFalse(this.entityProps.get("/src/:com.pacote2.EmptyClass").isEmpty());
		assertFalse(this.entityProps.get("/src/:com.pacote2.EmptyMethod").isEmpty());
		assertFalse(this.entityProps.get("/src/:com.xml2xsd.GenericClass").isEmpty());
		assertFalse(this.entityProps.get("/src/:com.xml2xsd.GenericClass$Interna").isEmpty());
		
		List<String> classProps = this.entityProps.get("/src/:com.pacote2.EmptyClass");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("intfc"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("static"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		classProps = this.entityProps.get("/src/:com.pacote2.EmptyMethod");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("intfc"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("static"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		classProps = this.entityProps.get("/src/:com.xml2xsd.GenericClass");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("intfc"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("static"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("jdoc")) {
				assertTrue(attributeValue.equals("Javadoc da classe Generica @author katyusco"));
			}
		}
		
		classProps = this.entityProps.get("/src/:com.xml2xsd.GenericClass$Interna");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("intfc"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("static"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("y"));
			if (attributeName.equals("jdoc"))
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
			
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		attributeProps = this.entityProps.get("PRIVATE_CONSTANTE");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		attributeProps = this.entityProps.get("public_atributo");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("comment"))
				assertTrue(attributeValue.equals(""));
		}
		
		attributeProps = this.entityProps.get("private_atributo");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("comment"))
				assertTrue(attributeValue.equals(""));
		}
		
		attributeProps = this.entityProps.get("public_atributo_interna");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("comment"))
				assertTrue(attributeValue.equals(""));
		}
		
		attributeProps = this.entityProps.get("private_atributo__interna");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("comment"))
				assertTrue(attributeValue.equals(""));
		}
	}
	
	public void testMethods() {
		
		assertTrue(this.entityProps.containsKey("/src/:com.pacote2.EmptyMethod.methodWithNoBody()"));
		assertTrue(this.entityProps.containsKey("/src/:com.xml2xsd.GenericClass.public_metodo(String, boolean)"));
		assertTrue(this.entityProps.containsKey("/src/:com.xml2xsd.GenericClass.private_metodo(String, boolean)"));
		
		assertFalse(this.entityProps.get("/src/:com.pacote2.EmptyMethod.methodWithNoBody()").isEmpty());
		assertFalse(this.entityProps.get("/src/:com.xml2xsd.GenericClass.public_metodo(String, boolean)").isEmpty());
		assertFalse(this.entityProps.get("/src/:com.xml2xsd.GenericClass.private_metodo(String, boolean)").isEmpty());
		
		List<String> attributeProps = this.entityProps.get("/src/:com.pacote2.EmptyMethod.methodWithNoBody()");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("comment"))
				assertTrue(attributeValue.equals(""));
		}
		
		attributeProps = this.entityProps.get("/src/:com.xml2xsd.GenericClass.public_metodo(String, boolean)");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc")) {
				assertTrue(attributeValue.equals("Javadoc do método public_metodo @param parametro1 @param parametro2 @return"));
			}
		}
		
		attributeProps = this.entityProps.get("/src/:com.xml2xsd.GenericClass.private_metodo(String, boolean)");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
	}
	
	public void testParameters() {
		
		assertTrue(this.entityProps.containsKey("parametro1"));
		assertTrue(this.entityProps.containsKey("parametro2"));
		
		assertTrue(this.entityProps.get("parametro1").isEmpty());
		assertTrue(this.entityProps.get("parametro2").isEmpty());
	}
}