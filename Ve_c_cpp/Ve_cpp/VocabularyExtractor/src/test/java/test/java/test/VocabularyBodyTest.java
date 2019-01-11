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

public class VocabularyBodyTest extends TestCase {
        
	private VXLHandler vxlHandler;
	private Map<String, List<String>> entityProps;
	
	public VocabularyBodyTest() {
	}
	
	public void setUp() {
		try { 
			String[] args = {"-n", "xmltoxsd", "-d", "./files/XmlToXsd/",
					"-loc", "hi", "-vxl", "./files/XmlToXsd/xmltoxsd.vxl", 
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
	
	public void testDirectives() {
		assertTrue(this.entityProps.containsKey("iostream"));
		assertTrue(this.entityProps.containsKey("CONSTANTEDEFINE"));
		assertTrue(this.entityProps.containsKey("CONSTANTEDEFINECHAR"));
		assertTrue(this.entityProps.containsKey("macroSoma"));
		assertTrue(this.entityProps.containsKey("macroStr"));
		assertTrue(this.entityProps.containsKey("macroConcat"));
		
		
		assertTrue(this.entityProps.get("iostream").isEmpty());
		assertTrue(this.entityProps.get("CONSTANTEDEFINE").isEmpty());
		assertTrue(this.entityProps.get("CONSTANTEDEFINECHAR").isEmpty());
		assertTrue(this.entityProps.get("macroSoma").isEmpty());
		assertTrue(this.entityProps.get("macroStr").isEmpty());
		assertTrue(this.entityProps.get("macroConcat").isEmpty());
	}
	
	public void testLocalVars() {
		assertTrue(this.entityProps.containsKey("varVolatilLocal"));
		assertTrue(this.entityProps.containsKey("varRegisterLocal"));
		
		assertFalse(this.entityProps.get("varVolatilLocal").isEmpty());
		assertFalse(this.entityProps.get("varRegisterLocal").isEmpty());

		List<String> attributeProps = this.entityProps.get("varVolatilLocal");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("volatile"));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("2"));
		}
		
		attributeProps = this.entityProps.get("varRegisterLocal");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals(""));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("register"));
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("3"));
		}
	}
	
	public void testGlobalVars() {
		assertTrue(this.entityProps.containsKey("ptrGlobal"));
		assertTrue(this.entityProps.containsKey("ptrParaPtrGlobal"));
		
		assertFalse(this.entityProps.get("ptrGlobal").isEmpty());
		assertFalse(this.entityProps.get("ptrParaPtrGlobal").isEmpty());

		List<String> attributeProps = this.entityProps.get("ptrGlobal");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals(""));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		attributeProps = this.entityProps.get("ptrParaPtrGlobal");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {

			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals(""));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
	}
	
	public void testFields() {
		assertTrue(this.entityProps.containsKey("atributoDaClasseGlobal"));
		assertTrue(this.entityProps.containsKey("atributoDaClasseGlobal2"));
		assertTrue(this.entityProps.containsKey("atributoInterno"));
		
		assertFalse(this.entityProps.get("atributoDaClasseGlobal").isEmpty());
		assertFalse(this.entityProps.get("atributoDaClasseGlobal2").isEmpty());
		assertFalse(this.entityProps.get("atributoInterno").isEmpty());

		List<String> attributeProps = this.entityProps.get("atributoDaClasseGlobal");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals(""));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("visib"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("3"));
		}
		
		attributeProps = this.entityProps.get("atributoDaClasseGlobal2");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {

			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals(""));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("visib"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		attributeProps = this.entityProps.get("atributoInterno");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {

			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals(""));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("visib"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
	}
	
	public void testPointers() {
		assertTrue(this.entityProps.containsKey("ptr1"));
		assertTrue(this.entityProps.containsKey("ptr2"));
		assertTrue(this.entityProps.containsKey("ptr3"));
		assertTrue(this.entityProps.containsKey("ptr4"));
		assertTrue(this.entityProps.containsKey("ptr5"));
		
		assertFalse(this.entityProps.get("ptr1").isEmpty());
		assertFalse(this.entityProps.get("ptr2").isEmpty());
		assertFalse(this.entityProps.get("ptr3").isEmpty());
		assertFalse(this.entityProps.get("ptr4").isEmpty());
		assertFalse(this.entityProps.get("ptr5").isEmpty());

		List<String> attributeProps = this.entityProps.get("ptr1");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals(""));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		attributeProps = this.entityProps.get("ptr2");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals(""));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		attributeProps = this.entityProps.get("ptr3");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals(""));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		attributeProps = this.entityProps.get("ptr4");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("const"));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		attributeProps = this.entityProps.get("ptr5");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("const"));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
	}
	
	public void testPrttp() {
		assertTrue(this.entityProps.containsKey("prototipo1"));
		assertTrue(this.entityProps.containsKey("prototipo5"));
		
		assertFalse(this.entityProps.get("prototipo1").isEmpty());
		assertFalse(this.entityProps.get("prototipo5").isEmpty());

		List<String> attributeProps = this.entityProps.get("prototipo1");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals(""));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
		}
		
		attributeProps = this.entityProps.get("prototipo5");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {

			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals(""));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
		}
	}
	
	public void testOperadores() {
		assertTrue(this.entityProps.containsKey("varBit1"));
		assertTrue(this.entityProps.containsKey("varBit2"));
		assertTrue(this.entityProps.containsKey("varStruct1.membro1"));
		assertTrue(this.entityProps.containsKey("varStruct1"));
		assertTrue(this.entityProps.containsKey("tipo2"));
		assertTrue(this.entityProps.containsKey("obj1.nomePublico"));
		
		assertFalse(this.entityProps.get("varBit1").isEmpty());
		assertFalse(this.entityProps.get("varBit2").isEmpty());
		assertFalse(this.entityProps.get("varStruct1.membro1").isEmpty());
		assertFalse(this.entityProps.get("varStruct1").isEmpty());
		assertFalse(this.entityProps.get("tipo2").isEmpty());
		assertFalse(this.entityProps.get("obj1.nomePublico").isEmpty());

		List<String> attributeProps = this.entityProps.get("varBit1");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals(""));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("12"));
		}
		
		attributeProps = this.entityProps.get("varBit2");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {

			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals(""));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("10"));
		}
		
		attributeProps = this.entityProps.get("varStruct1.membro1");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {

			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals(""));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("6"));
		}
		
		attributeProps = this.entityProps.get("varStruct1");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {

			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals(""));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		
		attributeProps = this.entityProps.get("tipo2");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {

			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals(""));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("2"));
		}
		
		attributeProps = this.entityProps.get("obj1.nomePublico");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {

			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals(""));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
	}
	
	public void testFunctionsOrMethodsCall() {
		assertTrue(this.entityProps.containsKey("funcaoUsandoEnum"));
		assertTrue(this.entityProps.containsKey("obj1.setNome"));
		
		assertFalse(this.entityProps.get("funcaoUsandoEnum").isEmpty());
		assertFalse(this.entityProps.get("obj1.setNome").isEmpty());

		List<String> attributeProps = this.entityProps.get("funcaoUsandoEnum");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();

			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("2"));
		}
		
		attributeProps = this.entityProps.get("obj1.setNome");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {

			String attributeName = it.next();
			String attributeValue = it.next();

			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("2"));
		}
	}
}