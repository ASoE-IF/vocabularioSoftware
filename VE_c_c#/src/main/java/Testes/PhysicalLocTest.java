package Testes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.gnu.c.GCCLanguage;
import org.eclipse.cdt.core.model.ILanguage;
import org.eclipse.cdt.core.parser.DefaultLogService;
import org.eclipse.cdt.core.parser.FileContent;
import org.eclipse.cdt.core.parser.IncludeFileContentProvider;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

import Vocabulary.vloccount.PhysicalLOCCount;
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
	private static String noAnnotationsFile = testFilesDir + "/NoAnnotationFile";
	private static String complexFile = testFilesDir + "/ComplexFile";
	
	@SuppressWarnings("unchecked")
	private static ASTNode getASTTreeFromSourceCode(final char[] sourceCode) throws CoreException{
		FileContent reader = FileContent.create("", sourceCode);
	     
		//gera a AST de um codigo fonte em C
		IASTTranslationUnit translationUnit =  GCCLanguage.getDefault().getASTTranslationUnit(
				reader, new ScannerInfo(), IncludeFileContentProvider.getSavedFilesProvider(), 
				null, ILanguage.OPTION_IS_SOURCE_UNIT, new DefaultLogService());
		
		return (ASTNode)translationUnit;
		
	}
	
	@SuppressWarnings({ "unchecked" })
	private static int getPhysicalLocCount(String file) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(file));
		String sourceCode = "", aux;
		int count = 0;
		
		while((aux = in.readLine()) != null) {
			sourceCode = String.format("%s\n%s", sourceCode, aux);
		}
		in.close();
		IASTTranslationUnit compilationUnit = (IASTTranslationUnit) getASTTreeFromSourceCode(sourceCode.toCharArray());

		PhysicalLOCCount counter = new PhysicalLOCCount(Arrays.asList(compilationUnit.getComments()), sourceCode,
				sourceCode.split("\n").length);
		count += counter.getLOC();

		return count;
	}
	
	public void testSimpleFile() throws Exception {
		assertTrue(getPhysicalLocCount(simpleFile) == 17);
	}
	
	public void testNoBlankLinesFile() throws Exception {
		assertTrue(getPhysicalLocCount(noBlankLinesFile) == 17);
	}
	
	public void testNoCommentsFile() throws Exception {
		assertTrue(getPhysicalLocCount(noCommentsFile) == 17);
	}
	
	public void testNoAnnotationsFile() throws Exception {
		assertTrue(getPhysicalLocCount(noAnnotationsFile) == 15);
	}
	
	public void testComplexFile() throws Exception {
		assertTrue(getPhysicalLocCount(complexFile) == 17);
	}
}