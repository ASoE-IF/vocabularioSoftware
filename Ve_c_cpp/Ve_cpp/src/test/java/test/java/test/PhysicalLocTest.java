package test.java.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage;
import org.eclipse.cdt.core.parser.DefaultLogService;
import org.eclipse.cdt.core.parser.FileContent;
import org.eclipse.cdt.core.parser.IParserLogService;
import org.eclipse.cdt.core.parser.IScannerInfo;
import org.eclipse.cdt.core.parser.IncludeFileContentProvider;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.core.runtime.CoreException;
import org.splab.vocabulary.extractor.vloccount.PhysicalLOCCount;

import junit.framework.TestCase;

/**
 * Tests the PhysicalLOCCount class
 * @author Tercio de Melo
 */
public class PhysicalLocTest extends TestCase { 
	
	private static String testFilesDir = "./files/LocTests/physicalCountTests";
	
	private static String simpleFile = testFilesDir + "/SimpleFile";
	private static String noBlankLinesFile = testFilesDir + "/NoBlankLinesFile";
	private static String noCommentsFile = testFilesDir + "/NoCommentsFile";
	private static String complexFile = testFilesDir + "/ComplexFile";
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static IASTTranslationUnit getASTTreeFromSourceCode(final char[] sourceCode) throws InvocationTargetException, InterruptedException, CoreException {

		FileContent fileContent = FileContent.create(testFilesDir, sourceCode);

		Map definedSymbols = new HashMap();
		String[] includePaths = new String[0];
		IScannerInfo info = new ScannerInfo(definedSymbols, includePaths);
		IParserLogService log = new DefaultLogService();

		IncludeFileContentProvider emptyIncludes = IncludeFileContentProvider.getEmptyFilesProvider();

		int opts = 8;
		IASTTranslationUnit translationUnit = GPPLanguage.getDefault().getASTTranslationUnit(fileContent, info,
				emptyIncludes, null, opts, log);
		
		return translationUnit;
	}
	
	private static int getPhysicalLocCount(String file) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(file));
		String sourceCode = "", aux;
		int count = 0;

		while((aux = in.readLine()) != null) {
			sourceCode = String.format("%s\n%s", sourceCode, aux);
		}
		in.close();
		
		IASTTranslationUnit translationUnit = (IASTTranslationUnit) getASTTreeFromSourceCode(sourceCode.toCharArray());

		PhysicalLOCCount counter = new PhysicalLOCCount(
				Arrays.asList(translationUnit.getComments()), sourceCode,
				sourceCode.split("\n").length);
		count += counter.getLOC();

		return count;
	}
	
	public void testSimpleFile() throws Exception {
		assertTrue(getPhysicalLocCount(simpleFile) == 124);
	}
	
	public void testNoBlankLinesFile() throws Exception {
		assertTrue(getPhysicalLocCount(noBlankLinesFile) == 124);
	}
	
	public void testNoCommentsFile() throws Exception {
		assertTrue(getPhysicalLocCount(noCommentsFile) == 124);
	}
	
	public void testComplexFile() throws Exception {
		assertTrue(getPhysicalLocCount(complexFile) == 124);
	}
}