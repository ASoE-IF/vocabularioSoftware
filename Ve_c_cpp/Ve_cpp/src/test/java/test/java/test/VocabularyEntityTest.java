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
					"-loc", "iah", "-vxl", "./files/XmlToXsd/xmltoxsd.vxl", 
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

		assertTrue(this.entityProps.containsKey("Diretivas.cpp"));
		assertFalse(this.entityProps.get("Diretivas.cpp").isEmpty());
		
		List<String> fileProps = this.entityProps.get("Diretivas.cpp");
		for (Iterator<String> it = fileProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("37"));
		}
		
		assertTrue(this.entityProps.containsKey("UsandoEstruturasDeControle.cpp"));
		assertFalse(this.entityProps.get("UsandoEstruturasDeControle.cpp").isEmpty());
		
		fileProps = this.entityProps.get("UsandoEstruturasDeControle.cpp");
		for (Iterator<String> it = fileProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("173"));
		}
		
		assertTrue(this.entityProps.containsKey("UsandoFuncoes.cpp"));
		assertFalse(this.entityProps.get("UsandoFuncoes.cpp").isEmpty());
		
		fileProps = this.entityProps.get("UsandoFuncoes.cpp");
		for (Iterator<String> it = fileProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("33"));
		}
		
		assertTrue(this.entityProps.containsKey("UsandoMatrizesEPonteiros.cpp"));
		assertFalse(this.entityProps.get("UsandoMatrizesEPonteiros.cpp").isEmpty());
		
		fileProps = this.entityProps.get("UsandoMatrizesEPonteiros.cpp");
		for (Iterator<String> it = fileProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("77"));
		}
		
		assertTrue(this.entityProps.containsKey("UsandoVariaveisGlobaisELocais.cpp"));
		assertFalse(this.entityProps.get("UsandoVariaveisGlobaisELocais.cpp").isEmpty());
		
		fileProps = this.entityProps.get("UsandoVariaveisGlobaisELocais.cpp");
		for (Iterator<String> it = fileProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("114"));
		}
		
		assertTrue(this.entityProps.containsKey("CLASSES1.CPP"));
		assertFalse(this.entityProps.get("CLASSES1.CPP").isEmpty());
		
		fileProps = this.entityProps.get("CLASSES1.CPP");
		for (Iterator<String> it = fileProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("136"));
		}
		
		assertTrue(this.entityProps.containsKey("EnumsStructsEUnions.cpp"));
		assertFalse(this.entityProps.get("EnumsStructsEUnions.cpp").isEmpty());
		
		fileProps = this.entityProps.get("EnumsStructsEUnions.cpp");
		for (Iterator<String> it = fileProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("149"));
		}
		
		assertTrue(this.entityProps.containsKey("TEMPLATE.CPP"));
		assertFalse(this.entityProps.get("TEMPLATE.CPP").isEmpty());
		
		fileProps = this.entityProps.get("TEMPLATE.CPP");
		for (Iterator<String> it = fileProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("12"));
		}
		
		assertTrue(this.entityProps.containsKey("TratamentoDeExcecoes.cpp"));
		assertFalse(this.entityProps.get("TratamentoDeExcecoes.cpp").isEmpty());
		
		fileProps = this.entityProps.get("TratamentoDeExcecoes.cpp");
		for (Iterator<String> it = fileProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("21"));
		}
		
		assertTrue(this.entityProps.containsKey("UsandoClassesEVariaveis.cpp"));
		assertFalse(this.entityProps.get("UsandoClassesEVariaveis.cpp").isEmpty());
		
		fileProps = this.entityProps.get("UsandoClassesEVariaveis.cpp");
		for (Iterator<String> it = fileProps.iterator(); it.hasNext();) {
			
			String attributeName = it.next();
			String attributeValue = it.next();
			
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("28"));
		}
	}
	
	public void testFunctions() {
		assertTrue(this.entityProps.containsKey("funcao_sem_parametros"));
		assertTrue(this.entityProps.containsKey("funcao1"));
		assertTrue(this.entityProps.containsKey("funcao2"));
		assertTrue(this.entityProps.containsKey("GenericClass2::getNome"));
		
		assertFalse(this.entityProps.get("funcao_sem_parametros").isEmpty());
		assertFalse(this.entityProps.get("funcao1").isEmpty());
		assertFalse(this.entityProps.get("funcao2").isEmpty());
		assertFalse(this.entityProps.get("GenericClass2::getNome").isEmpty());
		
		List<String> attributeProps = this.entityProps.get("funcao_sem_parametros");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("volatile"));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("static"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("5"));
		}
		
		attributeProps = this.entityProps.get("funcao1");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals(""));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("5"));
		}
		
		attributeProps = this.entityProps.get("funcao2");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("inline"));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("7"));
		}
		
		attributeProps = this.entityProps.get("GenericClass2::getNome");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals(""));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("4"));
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
	/**
	 * Test class
	 */
	public void testClass() {

		assertTrue(this.entityProps.containsKey("GenericClass6"));
		assertFalse(this.entityProps.get("GenericClass6").isEmpty());

		List<String> attributeProps = this.entityProps.get("GenericClass6");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();

			if (attributeName.equals("sloc")){
				assertTrue(attributeValue.equals("15"));
			}
		}
		
		assertTrue(this.entityProps.containsKey("classeGlobal"));
		assertFalse(this.entityProps.get("classeGlobal").isEmpty());

		attributeProps = this.entityProps.get("classeGlobal");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();

			if (attributeName.equals("sloc")){
				assertTrue(attributeValue.equals("19"));
			}
		}
	}
	
	/**Test methods**/
	public void testMethods() {
		assertTrue(this.entityProps.containsKey("printUniversal2"));
		assertTrue(this.entityProps.containsKey("metodoVirtualPura"));
		assertTrue(this.entityProps.containsKey("funcao2"));
		
		
		assertFalse(this.entityProps.get("printUniversal2").isEmpty());
		assertFalse(this.entityProps.get("metodoVirtualPura").isEmpty());
		assertFalse(this.entityProps.get("funcao2").isEmpty());

		List<String> attributeProps = this.entityProps.get("printUniversal2");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals(""));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("modifies"))
				assertTrue(attributeValue.equals("const"));
			if (attributeName.equals("visib"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("virtual"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("3"));
		}
		
		attributeProps = this.entityProps.get("metodoVirtualPura");
		for (Iterator<String> it = attributeProps.iterator(); it.hasNext();) {
	
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals(""));
			if (attributeName.equals("storage"))
				assertTrue(attributeValue.equals("auto"));
			if (attributeName.equals("modifies"))
				assertTrue(attributeValue.equals("const"));
			if (attributeName.equals("visib"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("pvirtual"))
				assertTrue(attributeValue.equals("y"));
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