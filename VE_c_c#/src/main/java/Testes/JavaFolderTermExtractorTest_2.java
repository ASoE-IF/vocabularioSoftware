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


/**
 * This class was created to test the VocabularyExtractor tool. The source code
 * used to test is the VocabularyExtractor source code (folders used: src/main
 * and src/test).
 * 
 * @author Catharine Quintans
 * @date Created at 17/08/2012
 */
public class JavaFolderTermExtractorTest_2 extends TestCase {

	private VXLHandler vxlHandler;
	private Map<String, List<String>> entityProps;

	public JavaFolderTermExtractorTest_2() {
	}

	public void setUp() {
		try { 
			String[] args = {"-n", "xmltoxsd_2", "-d", "./files/XmlToXsd_2/",
					"-loc", "iah", "-vxl", "./files/XmlToXsd_2/xmltoxsd_2.vxl", 
					"-csv", "./files/XmlToXsd_2/xmltoxsd_2.csv", "-mth"}; 
			
			VocabularyRunner.main(args);
		
			// parsing vxl file
			String vxlFileName = "./files/XmlToXsd_2/xmltoxsd_2.vxl";
			XMLReader xr = XMLReaderFactory.createXMLReader();

			this.vxlHandler = new VXLHandler();
			xr.setContentHandler(this.vxlHandler);
			xr.setErrorHandler(this.vxlHandler);

			FileReader r = new FileReader(vxlFileName);
			xr.parse(new InputSource(r));
			
			this.entityProps = this.vxlHandler.getEntityProps();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Test the packages.
	 */
	public void testPackages() {
		assertTrue(this.entityProps.containsKey("/src/test/java/:test"));
		assertTrue(this.entityProps.containsKey("/src/test/java/:util"));
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl"));
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor"));
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.vloccount"));
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.util"));
	}

	/**
	 * Test the class and their properties.
	 */
	public void testClasses() {
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.VocabularyExtractor"));
		List<String> classProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.VocabularyExtractor");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("intfc"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("abs"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("55"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("A main class for term extraction in Java Projects @author gustavojss Tercio de Melo"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.CommentUnit"));
		classProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.CommentUnit");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("intfc"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("abs"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("58"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Stores useful information of a Comment such as it&apos;s contents, start and end position in a given source code @author Tercio de Melo"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor"));
		classProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("intfc"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("abs"))
				assertTrue(attributeValue.equals("y"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("117"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("An abstract implementation of term extraction of a Java Project @author gustavojss Tercio de Melo"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ASTVocabularyExtractor"));
		classProps = this.entityProps.get("");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("intfc"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("abs"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("43"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("A term extractor for Eclipse&apos;s Java Project Specification @author gustavojss"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.JavaFolderVocabularyExtractor"));
		classProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.JavaFolderVocabularyExtractor");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("intfc"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("abs"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("83"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("A term extractor for Java Project Folders @author gustavojss"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.SourceCodeVocabularyExtractor"));
		classProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.SourceCodeVocabularyExtractor");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("intfc"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("abs"))
				assertTrue(attributeValue.equals("y"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("224"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("An abstract implementation of term extraction of Java Source Code @author gustavojss Tercio de Melo"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.util.FileUtil"));
		classProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.util.FileUtil");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("intfc"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("abs"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("20"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("A set of file utilities @author gustavojss Tercio de Melo"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.vloccount.EntityLOCKeeper"));
		classProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.vloccount.EntityLOCKeeper");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("intfc"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("abs"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("22"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("All this class does is keep LOC Counting information, no need for tests @author Tercio de Melo"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.vloccount.LOCCountPerEntity"));
		classProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.vloccount.LOCCountPerEntity");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("intfc"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("abs"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("61"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Manages physical loc extraction and annotations counting in a manner that constrains such metrics to an specific entity. @author Tercio de Melo"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.vloccount.AnnotationCounter"));
		classProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.vloccount.AnnotationCounter");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("intfc"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("abs"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("94"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Algorithm to parse a source code and compute how many lines framework annotations of pieces of them occupies. @author Tercio de Melo"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.vloccount.AnnotationCounter$AnnotationLimit"));
		classProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.vloccount.AnnotationCounter$AnnotationLimit");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("intfc"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("abs"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("y"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("5"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Abstraction of annotation delimiters @author Tercio de Melo"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.vloccount.PhysicalLOCCount"));
		classProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.vloccount.PhysicalLOCCount");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("intfc"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("abs"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("81"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Computes the number of physical lines of java code is there in a given scope @author Tercio de Melo"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/test/java/:test.AnnotationsTest"));
		classProps = this.entityProps.get("/src/test/java/:test.AnnotationsTest");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("intfc"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("abs"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("45"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Tests the AnnotationCounter class @author tercio"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/test/java/:test.LocPerEntityTest"));
		classProps = this.entityProps.get("/src/test/java/:test.LocPerEntityTest");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("intfc"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("abs"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("77"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Tests the LOCCountPerEntity class @author Tercio de Melo"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/test/java/:test.PhysicalLocTest"));
		classProps = this.entityProps.get("/src/test/java/:test.PhysicalLocTest");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("intfc"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("abs"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("48"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Tests the PhysicalLOCCount class @author Tercio de Melo"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/test/java/:test.JavaFolderTermExtractorTest"));
		classProps = this.entityProps.get("/src/test/java/:test.JavaFolderTermExtractorTest");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("intfc"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("abs"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("214"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/test/java/:util.VXLHandler"));
		classProps = this.entityProps.get("/src/test/java/:util.VXLHandler");
		for (Iterator<String> it = classProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("intfc"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("abs"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("inn"))
				assertTrue(attributeValue.equals("n"));
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("34"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
	}
	
	/**
	 * Test enum and their properties.
	 */
	public void testEnums() {
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.vloccount.EntityType"));
		List<String> enumProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.vloccount.EntityType");
		for (Iterator<String> it = enumProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("5"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.vloccount.LOCParameters"));
		enumProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.vloccount.LOCParameters");
		for (Iterator<String> it = enumProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("sloc"))
				assertTrue(attributeValue.equals("3"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Constants that will be used to manage tha LOC Extraction @author Tercio de Melo"));
		}
	}
	
	/**
	 * Test constants and their properties.
	 */
	public void testConst() {
		assertTrue(this.entityProps.containsKey("PROJECT_NAME"));
		List<String> constProps = this.entityProps.get("PROJECT_NAME");
		for (Iterator<String> it = constProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("PROJECT_PACKAGE"));
		constProps = this.entityProps.get("PROJECT_PACKAGE");
		for (Iterator<String> it = constProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("PROJECT_CLASS"));
		constProps = this.entityProps.get("PROJECT_CLASS");
		for (Iterator<String> it = constProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("PROJECT_ENUM"));
		constProps = this.entityProps.get("PROJECT_ENUM");
		for (Iterator<String> it = constProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("PROJECT_FIELD"));
		constProps = this.entityProps.get("PROJECT_FIELD");
		for (Iterator<String> it = constProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("PROJECT_METHOD"));
		constProps = this.entityProps.get("PROJECT_METHOD");
		for (Iterator<String> it = constProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("PROJECT_PARAMETER"));
		constProps = this.entityProps.get("PROJECT_PARAMETER");
		for (Iterator<String> it = constProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("PROJECT_LOCVARIABLE"));
		constProps = this.entityProps.get("PROJECT_LOCVARIABLE");
		for (Iterator<String> it = constProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("locParameters"));
		constProps = this.entityProps.get("locParameters");
		for (Iterator<String> it = constProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("LINE_SEPARATOR"));
		constProps = this.entityProps.get("LINE_SEPARATOR");
		for (Iterator<String> it = constProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("DEFAULT_PACKAGE"));
		constProps = this.entityProps.get("DEFAULT_PACKAGE");
		for (Iterator<String> it = constProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("CLASS"));
		constProps = this.entityProps.get("CLASS");
		for (Iterator<String> it = constProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("INNER_CLASS"));
		constProps = this.entityProps.get("INNER_CLASS");
		for (Iterator<String> it = constProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("ENUM"));
		constProps = this.entityProps.get("ENUM");
		for (Iterator<String> it = constProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("INTERFACE"));
		constProps = this.entityProps.get("INTERFACE");
		for (Iterator<String> it = constProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("LOC"));
		constProps = this.entityProps.get("LOC");
		for (Iterator<String> it = constProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("HEADERS"));
		constProps = this.entityProps.get("HEADERS");
		for (Iterator<String> it = constProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("ANNOTATIONS"));
		constProps = this.entityProps.get("ANNOTATIONS");
		for (Iterator<String> it = constProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("INNER_CLASSES"));
		constProps = this.entityProps.get("INNER_CLASSES");
		for (Iterator<String> it = constProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("ONLY_PHYSICAL_LINES"));
		constProps = this.entityProps.get("ONLY_PHYSICAL_LINES");
		for (Iterator<String> it = constProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
	}
	
	/**
	 * Test the comments.
	 */
//	public void testComm() {
//		assertTrue(this.entityProps.containsKey("Used to hold the output."));
//		assertTrue(this.entityProps.containsKey("Used to reference the current character."));
//		assertTrue(this.entityProps.containsKey("vacancy test."));
//		assertTrue(this.entityProps.containsKey("NOTE: No IndexOutOfBoundsException caught here; it should not happen."));
//		assertTrue(this.entityProps.containsKey("for all folders"));
//		assertTrue(this.entityProps.containsKey("extract AST tree from source code"));
//		assertTrue(this.entityProps.containsKey("extractTermsFromCompilationUnit(astCompilationUnit);"));
//		assertTrue(this.entityProps.containsKey("for all files in this project folder, add java components to the map"));
//		assertTrue(this.entityProps.containsKey("extract terms from the package hierarchy map"));
//		assertTrue(this.entityProps.containsKey("if it&apos;s a folder, search all containing files and subfolders"));
//		assertTrue(this.entityProps.containsKey("if it&apos;s a java file, extract AST tree and add to the hierarchy map"));
//		assertTrue(this.entityProps.containsKey("for all packages"));
//		assertTrue(this.entityProps.containsKey("for all compilation units in this package, extract terms"));
//		assertTrue(this.entityProps.containsKey("copying source code from .java file"));
//		assertTrue(this.entityProps.containsKey("extract AST tree from source code"));
//		assertTrue(this.entityProps.containsKey("extractCommentsFromCompilationUnit(compilationUnit);"));
//		assertTrue(this.entityProps.containsKey("adding the extracted compilation unit to package map"));
//		assertTrue(this.entityProps.containsKey("extracting package name"));
//		assertTrue(this.entityProps.containsKey("extracting terms from compilation unit"));
//		assertTrue(this.entityProps.containsKey("adding the compilation unit in the map"));
//		assertTrue(this.entityProps.containsKey("setting java compilationUnit"));
//		assertTrue(this.entityProps.containsKey("Seleciona os comentarios localizados antes de qualquer entidade de encapsulamento"));
//		assertTrue(this.entityProps.containsKey("Seleciona os comentarios localizados entre entidades de encapsulamento"));
//		assertTrue(this.entityProps.containsKey("Seleciona os comentarios localizados depois de todas as entidades de encapsulamento mas dentro do corpo da classe"));
//		assertTrue(this.entityProps.containsKey("physical loc count validation"));
//		assertTrue(this.entityProps.containsKey("for all types declared in this compilation unit"));
//		assertTrue(this.entityProps.containsKey("for classes"));
//		assertTrue(this.entityProps.containsKey("for enumerations"));
//		assertTrue(this.entityProps.containsKey("Validation and management of Enum LOC extraction"));
//		assertTrue(this.entityProps.containsKey("enum constants don&apos;t have javadoc associations"));
//		assertTrue(this.entityProps.containsKey("adding enum&apos;s info"));
//		assertTrue(this.entityProps.containsKey("adding identifier and modifiers&apos; info"));
//		assertTrue(this.entityProps.containsKey("Validation and management of Type LOC Extraction"));
//		assertTrue(this.entityProps.containsKey("adding fields&apos; info"));
//		assertTrue(this.entityProps.containsKey("adding methods&apos; info"));
//		assertTrue(this.entityProps.containsKey("adding enum info"));
//		assertTrue(this.entityProps.containsKey("adding inner classes&apos; info"));
//		assertTrue(this.entityProps.containsKey("checks if a field is a constant"));
//		assertTrue(this.entityProps.containsKey("adding method name"));
//		assertTrue(this.entityProps.containsKey("adding method parameter"));
//		assertTrue(this.entityProps.containsKey("adding local variables"));
//		assertTrue(this.entityProps.containsKey("finishing method"));
//		assertTrue(this.entityProps.containsKey("verifying method modifiers"));
//		assertTrue(this.entityProps.containsKey("if it isn&apos;t a class or an interface, it has no inner entity"));
//		assertTrue(this.entityProps.containsKey("The AST considers the beginning of an Javadoc as the beginning of the given entity, we don&apos;t. So, if there&apos;s any javadoc, it&apos;s end point will be the entity&apos;s beginning point"));
//		assertTrue(this.entityProps.containsKey("The end point is the beginnig point plus the entity&apos;s length"));
//		assertTrue(this.entityProps.containsKey("the temporary loc of the entity is it&apos;s physical loc count"));
//		assertTrue(this.entityProps.containsKey("Usado para debug"));
//		assertTrue(this.entityProps.containsKey("System.out.println(&quot;====&quot;+ allCommentsLOC(comments));"));
//		assertTrue(this.entityProps.containsKey("Retirando as linhas em branco presentes"));
//		assertTrue(this.entityProps.containsKey("num comentário de bloco"));
//		assertTrue(this.entityProps.containsKey("Testa se o comentario eh de bloco e ocupa uma unica linha e esta depois de codigo valido"));
//		assertTrue(this.entityProps.containsKey("para evitar que comentarios do tipo"));
//		assertTrue(this.entityProps.containsKey("codigoQualquer(tipo param, Comentario )"));
//		assertTrue(this.entityProps.containsKey("sejam contabilizados como contendo -1 linhas"));
//		assertTrue(this.entityProps.containsKey("Essa situacao acontece quando temos codigo valido apos o comentario: comment codigo valido"));
//		assertTrue(this.entityProps.containsKey("Include inner enums"));
//		assertTrue(this.entityProps.containsKey("Extern enums only"));
//		assertTrue(this.entityProps.containsKey("setting java compilationUnit"));
//		assertTrue(this.entityProps.containsKey("deleting previously saved vocabulary"));
//		assertTrue(this.entityProps.containsKey("Loc extration parameters"));
//		assertTrue(this.entityProps.containsKey("extracting vocabulary"));
//		assertTrue(this.entityProps.containsKey("parsing vxl file"));
//		assertTrue(this.entityProps.containsKey("System.out.println(attributeValue);"));
//		assertTrue(this.entityProps.containsKey("http: www.java2s.com Code JavaAPI org.xml.sax.helpers extendsDefaultHandler.htm"));
//		assertTrue(this.entityProps.containsKey("http: www.saxproject.org quickstart.html"));
//		assertTrue(this.entityProps.containsKey("http: tutorials.jenkov.com java-xml sax-defaulthandler.html"));
//	}
	
	public void testField() {
		assertTrue(this.entityProps.containsKey("projectName"));
		List<String> fieldProps = this.entityProps.get("projectName");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("projectRevision"));
		fieldProps = this.entityProps.get("projectRevision");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("locParameters"));
		fieldProps = this.entityProps.get("locParameters");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("content"));
		fieldProps = this.entityProps.get("content");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("commentNode"));
		fieldProps = this.entityProps.get("commentNode");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("packageCount"));
		fieldProps = this.entityProps.get("packageCount");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("classCount"));
		fieldProps = this.entityProps.get("classCount");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("enumCount"));
		fieldProps = this.entityProps.get("enumCount");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("fieldCount"));
		fieldProps = this.entityProps.get("fieldCount");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("methodCount"));
		fieldProps = this.entityProps.get("methodCount");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("parameterCount"));
		fieldProps = this.entityProps.get("parameterCount");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("locVariableCount"));
		fieldProps = this.entityProps.get("locVariableCount");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("vocabulary"));
		fieldProps = this.entityProps.get("vocabulary");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("The vocabulary content"));
		}
		
		assertTrue(this.entityProps.containsKey("entitiesLOCContent"));
		fieldProps = this.entityProps.get("entitiesLOCContent");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("compilationUnitLOCContent"));
		fieldProps = this.entityProps.get("compilationUnitLOCContent");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("totalLOC"));
		fieldProps = this.entityProps.get("totalLOC");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("totalHeader"));
		fieldProps = this.entityProps.get("totalHeader");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("totalAnnotation"));
		fieldProps = this.entityProps.get("totalAnnotation");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("totalInnerEntitiesLines"));
		fieldProps = this.entityProps.get("totalInnerEntitiesLines");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("totalCodeLines"));
		fieldProps = this.entityProps.get("totalCodeLines");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("compilationUnitLOC"));
		fieldProps = this.entityProps.get("compilationUnitLOC");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("javaProject"));
		fieldProps = this.entityProps.get("javaProject");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("projectPath"));
		fieldProps = this.entityProps.get("projectPath");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("packageHierarchy"));
		fieldProps = this.entityProps.get("packageHierarchy");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("astNode"));
		fieldProps = this.entityProps.get("astNode");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("sourceCodeComments"));
		fieldProps = this.entityProps.get("sourceCodeComments");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("sourceCode"));
		fieldProps = this.entityProps.get("sourceCode");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("packageName"));
		fieldProps = this.entityProps.get("packageName");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("commentList"));
		fieldProps = this.entityProps.get("commentList");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("loc"));
		fieldProps = this.entityProps.get("loc");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("&lt;b&gt;loc&lt; b&gt; represents the number of physical lines of java code &lt;b&gt;start&lt; b&gt; marks the begin of the analyzed scope &lt;b&gt;finall&lt;b&gt; marks the end of the analyzed scope"));
		}
		
		assertTrue(this.entityProps.containsKey("headers"));
		fieldProps = this.entityProps.get("headers");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("innerEntitiesLOC"));
		fieldProps = this.entityProps.get("innerEntitiesLOC");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("annotations"));
		fieldProps = this.entityProps.get("annotations");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("annot"));
		fieldProps = this.entityProps.get("annot");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("inner"));
		fieldProps = this.entityProps.get("inner");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("allComments"));
		fieldProps = this.entityProps.get("allComments");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("annotCnt"));
		fieldProps = this.entityProps.get("annotCnt");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("limits"));
		fieldProps = this.entityProps.get("limits");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("source"));
		fieldProps = this.entityProps.get("source");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("comments"));
		fieldProps = this.entityProps.get("comments");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("annotations"));
		fieldProps = this.entityProps.get("annotations");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("sourceCode"));
		fieldProps = this.entityProps.get("sourceCode");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("type"));
		fieldProps = this.entityProps.get("type");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("begin"));
		fieldProps = this.entityProps.get("begin");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("end"));
		fieldProps = this.entityProps.get("end");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("sourceCode"));
		fieldProps = this.entityProps.get("sourceCode");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("has the source code of a java file"));
		}
		
		assertTrue(this.entityProps.containsKey("loc"));
		fieldProps = this.entityProps.get("loc");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("&lt;b&gt;loc&lt; b&gt; represents the number of physical lines of java code &lt;b&gt;start&lt; b&gt; marks the begin of the analyzed scope &lt;b&gt;finall&lt;b&gt; marks the end of the analyzed scope"));
		}
		
		assertTrue(this.entityProps.containsKey("start"));
		fieldProps = this.entityProps.get("start");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("&lt;b&gt;loc&lt; b&gt; represents the number of physical lines of java code &lt;b&gt;start&lt; b&gt; marks the begin of the analyzed scope &lt;b&gt;finall&lt;b&gt; marks the end of the analyzed scope"));
		}
		
		assertTrue(this.entityProps.containsKey("finall"));
		fieldProps = this.entityProps.get("finall");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("&lt;b&gt;loc&lt; b&gt; represents the number of physical lines of java code &lt;b&gt;start&lt; b&gt; marks the begin of the analyzed scope &lt;b&gt;finall&lt;b&gt; marks the end of the analyzed scope"));
		}
		
		assertTrue(this.entityProps.containsKey("testFilesDir"));
		fieldProps = this.entityProps.get("testFilesDir");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("oneLineAnnotationsFile"));
		fieldProps = this.entityProps.get("oneLineAnnotationsFile");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("multipleLinesAnnotatinosFile"));
		fieldProps = this.entityProps.get("multipleLinesAnnotatinosFile");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("AnnotationsAndJavadocsFile"));
		fieldProps = this.entityProps.get("AnnotationsAndJavadocsFile");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("countlessAnnotationsFile"));
		fieldProps = this.entityProps.get("countlessAnnotationsFile");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("testFilesDir"));
		fieldProps = this.entityProps.get("testFilesDir");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("simpleFile"));
		fieldProps = this.entityProps.get("simpleFile");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("noBlankLinesFile"));
		fieldProps = this.entityProps.get("noBlankLinesFile");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("noCommentsFile"));
		fieldProps = this.entityProps.get("noCommentsFile");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("noAnnotationsFile"));
		fieldProps = this.entityProps.get("noAnnotationsFile");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("complexFile"));
		fieldProps = this.entityProps.get("complexFile");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("vxlHandler"));
		fieldProps = this.entityProps.get("vxlHandler");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("entityProps"));
		fieldProps = this.entityProps.get("entityProps");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("entities"));
		fieldProps = this.entityProps.get("entities");
		for (Iterator<String> it = fieldProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
	}
	
	/**
	 * Test methods and their properties.
	 */
	public void testMth() {
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.VocabularyExtractor.VocabularyExtractor(String, String)"));
		List<String> mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.VocabularyExtractor.VocabularyExtractor(String, String)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Creates a new extractor with the given name and revision of a project @param projectName the name of the project @param projectRevision the revision note of the project"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.VocabularyExtractor.extractTermsFromJavaFolder(String, String, String, List:LOCParameters)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.VocabularyExtractor.extractTermsFromJavaFolder(String, String, String, List:LOCParameters)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Extracts terms from a Java Project Folder @param projectPath the path for the project folder @param resultVXLFileName the result file where the vocabulary will be saved"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.VocabularyExtractor.isInArgs(String[], String)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.VocabularyExtractor.isInArgs(String[], String)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Verifies if a given String is contained in a given array of Strings @param args @param parameter @return"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.VocabularyExtractor.loadLOCParameters(String[])"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.VocabularyExtractor.loadLOCParameters(String[])");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Sets the LOC extraction configurations based on the information passed through the JVM arguments @param args @return"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.VocabularyExtractor.main(String[])"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.VocabularyExtractor.main(String[])");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Simple execution for term extraction on a Java Project Folder @param args USAGE: [project path] [project name] [project revision] [result .vxl file name]"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.CommentUnit.CommentUnit(Comment, String)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.CommentUnit.CommentUnit(Comment, String)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Uses an ASTNode subclass Comment object and a sourceCode to extract useful information @param commentNode Comment which stores start position and length of a comment the source code @param sourceCode String which stores the source code to be parsed"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.CommentUnit.extractValue(String)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.CommentUnit.extractValue(String)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.CommentUnit.getStartPosition()"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.CommentUnit.getStartPosition()");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Returns the start point in the source code @return"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.CommentUnit.getEndPosition()"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.CommentUnit.getEndPosition()");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Returns the end point in the source code @return"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.CommentUnit.getComment()"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.CommentUnit.getComment()");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Returns the extracted content of the comment @return"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.CommentUnit.processComment(String)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.CommentUnit.processComment(String)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Processes the comment, removing undesirable characters and returning the extracted content @param comment the comment with undesirable characters @return"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.CommentUnit.removeInvalidCharacters(String)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.CommentUnit.removeInvalidCharacters(String)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Removes invalid characters for UTF-8 encoding @param text @return"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.ProjectVocabularyExtractor(String, String, List:LOCParameters)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.ProjectVocabularyExtractor(String, String, List:LOCParameters)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Creates a new vocabulary for the given project @param projectName the project name @param projectRevision the project revision note"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.getVocabulary()"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.getVocabulary()");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Returns the vocabulary of the project @return the project&apos;s vocabulary"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.getLOCContent()"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.getLOCContent()");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.getCompilationUnitLOCContent()"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.getCompilationUnitLOCContent()");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.extractTermsFromProject()"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.extractTermsFromProject()");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Performs the term extraction of the specified project"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.startProject(String, String)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.startProject(String, String)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.endProject()"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.endProject()");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.startPackage(String)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.startPackage(String)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.endPackage()"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.endPackage()");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.startClass(StringBuffer, String, String, String, String, String)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.startClass(StringBuffer, String, String, String, String, String)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.classComment(StringBuffer, List:String)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.classComment(StringBuffer, List:String)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.endClass(StringBuffer)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.endClass(StringBuffer)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.startEnum(StringBuffer, String, String)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.startEnum(StringBuffer, String, String)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.enumComment(StringBuffer, List:String)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.enumComment(StringBuffer, List:String)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.endEnum(StringBuffer)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.endEnum(StringBuffer)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.constant(StringBuffer, String, String, String)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.constant(StringBuffer, String, String, String)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.field(StringBuffer, String, String, String)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.field(StringBuffer, String, String, String)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.startMethod(StringBuffer, String, String, String)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.startMethod(StringBuffer, String, String, String)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.methodComment(StringBuffer, List:String)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.methodComment(StringBuffer, List:String)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.parameter(StringBuffer, String)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.parameter(StringBuffer, String)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.localVariable(StringBuffer, String)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.localVariable(StringBuffer, String)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.endMethod(StringBuffer)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.endMethod(StringBuffer)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.encodeToUTF8(String)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ProjectVocabularyExtractor.encodeToUTF8(String)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ASTVocabularyExtractor.ASTVocabularyExtractor(IJavaProject, String, String, List:LOCParameters)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ASTVocabularyExtractor.ASTVocabularyExtractor(IJavaProject, String, String, List:LOCParameters)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Creates a new term extractor for Eclipse&apos;s Java Project Specification @param javaProject the Eclipse&apos;s project @param javaProjectName the project&apos;s name @param javaProjectRevision the project&apos;s revision name"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ASTVocabularyExtractor.extractTermsFromProject()"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ASTVocabularyExtractor.extractTermsFromProject()");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ASTVocabularyExtractor.extractTermsFromProjectFolder(IPackageFragmentRoot)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ASTVocabularyExtractor.extractTermsFromProjectFolder(IPackageFragmentRoot)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Extracts terms from the project folder @param packageFragmentRoot the folder specification @throws JavaModelException @throws InvocationTargetException @throws InterruptedException"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ASTVocabularyExtractor.extractTermsFromPackage(IPackageFragment)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ASTVocabularyExtractor.extractTermsFromPackage(IPackageFragment)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Extracts terms from the java package @param pckg the package specification @throws JavaModelException @throws InvocationTargetException @throws InterruptedException"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ASTVocabularyExtractor.extractTermsFromCompilationUnit(ICompilationUnit)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.ASTVocabularyExtractor.extractTermsFromCompilationUnit(ICompilationUnit)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Extracts terms from a .java file @param compilationUnit @throws JavaModelException @throws InvocationTargetException @throws InterruptedException"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.JavaFolderVocabularyExtractor.JavaFolderVocabularyExtractor(String, String, String, List:LOCParameters)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.JavaFolderVocabularyExtractor.JavaFolderVocabularyExtractor(String, String, String, List:LOCParameters)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Creates a new term extractor for Java Project Folders @param projectPath the path of the project folder @param projectName the project&apos;s name @param projectRevision the project&apos;s revision name"));
		}
	
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.JavaFolderVocabularyExtractor.extractTermsFromProject()"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.JavaFolderVocabularyExtractor.extractTermsFromProject()");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.JavaFolderVocabularyExtractor.extractAllFiles(File)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.JavaFolderVocabularyExtractor.extractAllFiles(File)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Extract all java files from a folder, and add to the hierarchy map @param file"));
		}
	
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.JavaFolderVocabularyExtractor.extractTermsFromHierarchyMap()"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.JavaFolderVocabularyExtractor.extractTermsFromHierarchyMap()");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Extract terms from all compilation units in the hierarchy map"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.JavaFolderVocabularyExtractor.extractTermsFromClass(File)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.JavaFolderVocabularyExtractor.extractTermsFromClass(File)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Extracts the java structure of a file, and adds to the hierarchy map @param classFile the .java file"));
		}

		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.JavaFolderVocabularyExtractor.addCompilationUnitToMap(CompilationUnit, String)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.JavaFolderVocabularyExtractor.addCompilationUnitToMap(CompilationUnit, String)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Adds the given compilation unit to the hierarchy map @param compilationUnit the compilation unit to be added"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.JavaFolderVocabularyExtractor.isJavaFile(String)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.JavaFolderVocabularyExtractor.isJavaFile(String)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("priv"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Verifies if the given file is a java file @param fileName @return"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.JavaFolderVocabularyExtractor.main(String[])"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.JavaFolderVocabularyExtractor.main(String[])");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals(""));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.SourceCodeVocabularyExtractor.SourceCodeVocabularyExtractor(String, String, List:LOCParameters)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.SourceCodeVocabularyExtractor.SourceCodeVocabularyExtractor(String, String, List:LOCParameters)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Creates an abstraction for term extraction in java source code @param projectName the project&apos;s name @param projectResivion the project&apos;s revision name"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.SourceCodeVocabularyExtractor.getASTTreeFromSourceCode(char[])"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.SourceCodeVocabularyExtractor.getASTTreeFromSourceCode(char[])");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("pub"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Extracts the AST tree from the source code of a .java file"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.SourceCodeVocabularyExtractor.extractCommentsFromCompilationUnit(CompilationUnit, String)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.SourceCodeVocabularyExtractor.extractCommentsFromCompilationUnit(CompilationUnit, String)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Stores all the Comments from the source code in a LinkedList&lt;CommentUnit&gt; @param compilationUnit @param sourceCode"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.SourceCodeVocabularyExtractor.notInInnerTypes(int, TypeDeclaration)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.SourceCodeVocabularyExtractor.notInInnerTypes(int, TypeDeclaration)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Checks if the given comment start point is not in a inner class @param commentStartPoint @param type @return"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.SourceCodeVocabularyExtractor.getTypeDeclarationComments(TypeDeclaration)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.SourceCodeVocabularyExtractor.getTypeDeclarationComments(TypeDeclaration)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Extracts every comment that belong to a given class @param type @return"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.SourceCodeVocabularyExtractor.getEnumDeclararionComments(EnumDeclaration)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.SourceCodeVocabularyExtractor.getEnumDeclararionComments(EnumDeclaration)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Extracts every comment that belongs to a given enum @param enumDeclaration @return"));
		}
		
		assertTrue(this.entityProps.containsKey("/src/main/java/:org.splabs.vocabulary.vxl.extractor.SourceCodeVocabularyExtractor.getMethodDeclarationComments(MethodDeclaration)"));
		mthProps = this.entityProps.get("/src/main/java/:org.splabs.vocabulary.vxl.extractor.SourceCodeVocabularyExtractor.getMethodDeclarationComments(MethodDeclaration)");
		for (Iterator<String> it = mthProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("access"))
				assertTrue(attributeValue.equals("prot"));
			if (attributeName.equals("jdoc"))
				assertTrue(attributeValue.equals("Extracts every comment that belongs to a given method @param method @return"));
		}
	}
	
	/**
	 * Test the param's name.
	 */
	public void testParam() {
		assertTrue(this.entityProps.containsKey("projectName"));
		assertTrue(this.entityProps.containsKey("projectRevision"));
		assertTrue(this.entityProps.containsKey("projectPath"));
		assertTrue(this.entityProps.containsKey("resultVXLFileName"));
		assertTrue(this.entityProps.containsKey("resultLOCFileName"));
		assertTrue(this.entityProps.containsKey("locParameters"));
		assertTrue(this.entityProps.containsKey("args"));
		assertTrue(this.entityProps.containsKey("parameters"));
		assertTrue(this.entityProps.containsKey("commentNode"));
		assertTrue(this.entityProps.containsKey("sourceCode"));
		assertTrue(this.entityProps.containsKey("comment"));
		assertTrue(this.entityProps.containsKey("className"));
		assertTrue(this.entityProps.containsKey("isInterface"));
		assertTrue(this.entityProps.containsKey("isAbstract"));
		assertTrue(this.entityProps.containsKey("isInner"));
		assertTrue(this.entityProps.containsKey("buffer"));
		assertTrue(this.entityProps.containsKey("enumName"));
		assertTrue(this.entityProps.containsKey("enumComment"));
		assertTrue(this.entityProps.containsKey("constantName"));
		assertTrue(this.entityProps.containsKey("visibility"));
		assertTrue(this.entityProps.containsKey("methodName"));
		assertTrue(this.entityProps.containsKey("parameterName"));
		assertTrue(this.entityProps.containsKey("localVariableName"));
		assertTrue(this.entityProps.containsKey("aString"));
		assertTrue(this.entityProps.containsKey("javaProject"));
		assertTrue(this.entityProps.containsKey("javaProjectName"));
		assertTrue(this.entityProps.containsKey("javaProjectRevision"));
		assertTrue(this.entityProps.containsKey("packageFragmentRoot"));
		assertTrue(this.entityProps.containsKey("pckg"));
		assertTrue(this.entityProps.containsKey("compilationUnit"));
		assertTrue(this.entityProps.containsKey("projectPath"));
		assertTrue(this.entityProps.containsKey("projectName"));
		assertTrue(this.entityProps.containsKey("projectRevision"));
		assertTrue(this.entityProps.containsKey("classFile"));
		assertTrue(this.entityProps.containsKey("fileName"));
		assertTrue(this.entityProps.containsKey("commentStartPoint"));
		assertTrue(this.entityProps.containsKey("type"));
		assertTrue(this.entityProps.containsKey("enumDeclaration"));
		assertTrue(this.entityProps.containsKey("method"));
		assertTrue(this.entityProps.containsKey("assignment"));
		assertTrue(this.entityProps.containsKey("anEnum"));
		assertTrue(this.entityProps.containsKey("headerLines"));
		assertTrue(this.entityProps.containsKey("typeName"));
		assertTrue(this.entityProps.containsKey("entity"));
		assertTrue(this.entityProps.containsKey("parametro"));
		assertTrue(this.entityProps.containsKey("assignature"));
		assertTrue(this.entityProps.containsKey("entityDeclaration"));
		assertTrue(this.entityProps.containsKey("fileContent"));
		assertTrue(this.entityProps.containsKey("vxlFileName"));
		assertTrue(this.entityProps.containsKey("locFileContent"));
		assertTrue(this.entityProps.containsKey("locFileName"));
		assertTrue(this.entityProps.containsKey("locCounter"));
		assertTrue(this.entityProps.containsKey("extractHeader"));
		assertTrue(this.entityProps.containsKey("character"));
		assertTrue(this.entityProps.containsKey("index"));
		assertTrue(this.entityProps.containsKey("begin"));
		assertTrue(this.entityProps.containsKey("end"));
		assertTrue(this.entityProps.containsKey("linesOfCode"));
		assertTrue(this.entityProps.containsKey("start"));
		assertTrue(this.entityProps.containsKey("finall"));
		assertTrue(this.entityProps.containsKey("line"));
		assertTrue(this.entityProps.containsKey("commentList"));
		assertTrue(this.entityProps.containsKey("file"));
		assertTrue(this.entityProps.containsKey("uri"));
		assertTrue(this.entityProps.containsKey("localName"));
		assertTrue(this.entityProps.containsKey("qName"));
		assertTrue(this.entityProps.containsKey("attributes"));
	}
	
	/**
	 * Test local variable's name and their frequency.
	 */
	public void testLvar() {
		assertTrue(this.entityProps.containsKey("projectName"));
		List<String> lvarProps = this.entityProps.get("projectName");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("projectRevision"));
		lvarProps = this.entityProps.get("projectRevision");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}

		assertTrue(this.entityProps.containsKey("extractor"));
		lvarProps = this.entityProps.get("extractor");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("3"));
		}

		assertTrue(this.entityProps.containsKey("args"));
		lvarProps = this.entityProps.get("args");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("7"));
		}
		
		assertTrue(this.entityProps.containsKey("ERROR_MESSAGE"));
		lvarProps = this.entityProps.get("ERROR_MESSAGE");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("2"));
		}
		
		assertTrue(this.entityProps.containsKey("LINESEPARATOR"));
		lvarProps = this.entityProps.get("LINESEPARATOR");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("2"));
		}
		
		assertTrue(this.entityProps.containsKey("System"));
		lvarProps = this.entityProps.get("System");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("args"));
		lvarProps = this.entityProps.get("args");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("6"));
		}
		
		assertTrue(this.entityProps.containsKey("i"));
		lvarProps = this.entityProps.get("i");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("5"));
		}
		
		assertTrue(this.entityProps.containsKey("vocExtractor"));
		lvarProps = this.entityProps.get("vocExtractor");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("2"));
		}
		
		assertTrue(this.entityProps.containsKey("out"));
		lvarProps = this.entityProps.get("out");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("3"));
		}
		
		assertTrue(this.entityProps.containsKey("text"));
		lvarProps = this.entityProps.get("text");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("4"));
		}
		
		assertTrue(this.entityProps.containsKey("PROJECT_NAME"));
		lvarProps = this.entityProps.get("PROJECT_NAME");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("PROJECT_PACKAGE"));
		lvarProps = this.entityProps.get("PROJECT_PACKAGE");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("PROJECT_CLASS"));
		lvarProps = this.entityProps.get("PROJECT_CLASS");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("PROJECT_ENUM"));
		lvarProps = this.entityProps.get("PROJECT_ENUM");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("PROJECT_FIELD"));
		lvarProps = this.entityProps.get("PROJECT_FIELD");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("PROJECT_METHOD"));
		lvarProps = this.entityProps.get("PROJECT_METHOD");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("PROJECT_PARAMETER"));
		lvarProps = this.entityProps.get("PROJECT_PARAMETER");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("PROJECT_LOCVARIABLE"));
		lvarProps = this.entityProps.get("PROJECT_LOCVARIABLE");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("aString"));
		lvarProps = this.entityProps.get("aString");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("bytes"));
		lvarProps = this.entityProps.get("bytes");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("2"));
		}
		
		assertTrue(this.entityProps.containsKey("DEFAULT_PACKAGE"));
		lvarProps = this.entityProps.get("DEFAULT_PACKAGE");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("ASTParser"));
		lvarProps = this.entityProps.get("ASTParser");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("NO_COMMENT"));
		lvarProps = this.entityProps.get("NO_COMMENT");
		for (Iterator<String> it = lvarProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
	}
	
	/**
	 * Test literals' name.
	 */
//	public void testLit() {
//	}
	
	/**
	 * Test the name of the methods invocated and their respectiv frequency.
	 */
	public void testMthinv() {
		assertTrue(this.entityProps.containsKey("getLOCContent"));
		List<String> mthinvProps = this.entityProps.get("getLOCContent");
		for (Iterator<String> it = mthinvProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("getVocabulary"));
		mthinvProps = this.entityProps.get("getVocabulary");
		for (Iterator<String> it = mthinvProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("saveLOCFile"));
		mthinvProps = this.entityProps.get("saveLOCFile");
		for (Iterator<String> it = mthinvProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("saveVXLFile"));
		mthinvProps = this.entityProps.get("saveVXLFile");
		for (Iterator<String> it = mthinvProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("size"));
		mthinvProps = this.entityProps.get("size");
		for (Iterator<String> it = mthinvProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("isInArgs"));
		mthinvProps = this.entityProps.get("isInArgs");
		for (Iterator<String> it = mthinvProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("7"));
		}
		
		assertTrue(this.entityProps.containsKey("extractTermsFromJavaFolder"));
		mthinvProps = this.entityProps.get("extractTermsFromJavaFolder");
		for (Iterator<String> it = mthinvProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("getProperty"));
		mthinvProps = this.entityProps.get("getProperty");
		for (Iterator<String> it = mthinvProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("loadLOCParameters"));
		mthinvProps = this.entityProps.get("loadLOCParameters");
		for (Iterator<String> it = mthinvProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("extractValue"));
		mthinvProps = this.entityProps.get("extractValue");
		for (Iterator<String> it = mthinvProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("getLength"));
		mthinvProps = this.entityProps.get("getLength");
		for (Iterator<String> it = mthinvProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("getStartPosition"));
		mthinvProps = this.entityProps.get("getStartPosition");
		for (Iterator<String> it = mthinvProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("processComment"));
		mthinvProps = this.entityProps.get("processComment");
		for (Iterator<String> it = mthinvProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("substring"));
		mthinvProps = this.entityProps.get("substring");
		for (Iterator<String> it = mthinvProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("removeInvalidCharacters"));
		mthinvProps = this.entityProps.get("removeInvalidCharacters");
		for (Iterator<String> it = mthinvProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("replaceAll"));
		mthinvProps = this.entityProps.get("replaceAll");
		for (Iterator<String> it = mthinvProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("trim"));
		mthinvProps = this.entityProps.get("trim");
		for (Iterator<String> it = mthinvProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("encodeToUTF8"));
		mthinvProps = this.entityProps.get("encodeToUTF8");
		for (Iterator<String> it = mthinvProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("extractTermsFromProject"));
		mthinvProps = this.entityProps.get("extractTermsFromProject");
		for (Iterator<String> it = mthinvProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("getASTTreeFromSourceCode"));
		mthinvProps = this.entityProps.get("getASTTreeFromSourceCode");
		for (Iterator<String> it = mthinvProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("extractTermsFromHierarchyMap"));
		mthinvProps = this.entityProps.get("extractTermsFromHierarchyMap");
		for (Iterator<String> it = mthinvProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
		
		assertTrue(this.entityProps.containsKey("extractTermsFromClass"));
		mthinvProps = this.entityProps.get("extractTermsFromClass");
		for (Iterator<String> it = mthinvProps.iterator(); it.hasNext();) {
			String attributeName = it.next();
			String attributeValue = it.next();
			if (attributeName.equals("count"))
				assertTrue(attributeValue.equals("1"));
		}
	}
}