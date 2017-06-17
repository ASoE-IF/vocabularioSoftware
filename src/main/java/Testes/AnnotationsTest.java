package Testes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

import junit.framework.TestCase;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.gnu.c.GCCLanguage;
import org.eclipse.cdt.core.model.ILanguage;
import org.eclipse.cdt.core.parser.DefaultLogService;
import org.eclipse.cdt.core.parser.FileContent;
import org.eclipse.cdt.core.parser.IncludeFileContentProvider;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import Vocabulary.vloccount.AnnotationCounter;

/**
 * Tests the AnnotationCounter class
 * @author tercio
 */
public class AnnotationsTest extends TestCase{
	private static String testFilesDir = "./files/LocTests/annotationsTests";
	
	private static String oneLineAnnotationsFile = testFilesDir + "/OneLineAnnotations";
	private static String multipleLinesAnnotatinosFile = testFilesDir + "/MultipleLinesAnnotations";
	private static String AnnotationsAndJavadocsFile = testFilesDir + "/AnnotationsAndJavadocs";
	private static String countlessAnnotationsFile = testFilesDir + "/CountlessAnnotations";
	
	@SuppressWarnings("unchecked")
	private static ASTNode getASTTreeFromSourceCode(final char[] sourceCode) throws CoreException
	{
		FileContent reader = FileContent.create("", sourceCode);
	     
		//gera a AST de um codigo fonte em C
		IASTTranslationUnit translationUnit =  GCCLanguage.getDefault().getASTTranslationUnit(
				reader, new ScannerInfo(), IncludeFileContentProvider.getSavedFilesProvider(), 
				null, ILanguage.OPTION_IS_SOURCE_UNIT, new DefaultLogService());

		
		// setting java compilationUnit
		
		return (ASTNode)translationUnit;
		
	}
	
	@SuppressWarnings({ "unchecked" })
	private static int getAnnotCount(String file) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(file));
		String sourceCode = "", aux;
		int count = 0;
		
		int[][] limits = new int[0][0];
		
		while((aux = in.readLine()) != null) {
			sourceCode = String.format("%s\n%s", sourceCode, aux);
		}
		in.close();
		IASTTranslationUnit compilationUnit = (IASTTranslationUnit) getASTTreeFromSourceCode(sourceCode.toCharArray());
		
		IASTDeclaration[] decl = compilationUnit.getDeclarations();
		for(IASTDeclaration type : decl) {
			AnnotationCounter counter = new AnnotationCounter((ASTNode)type, limits, Arrays.asList(compilationUnit.getComments()), sourceCode);
			count += counter.getAnnotationLoc();
		}
		
		return count;
	}
	
	public void testOneLineAnnotationsFile() throws Exception {
		assertTrue(getAnnotCount(oneLineAnnotationsFile) == 5);
	}
	
	public void testMultipleLinesAnnotationsFile() throws Exception {
		assertTrue(getAnnotCount(multipleLinesAnnotatinosFile) == 22);
	}
	
	public void testAnnotationsAndJavadocsFile() throws Exception {
		assertTrue(getAnnotCount(AnnotationsAndJavadocsFile) == 17);
	}
	
	public void testCountlessAnnotationsFile() throws Exception {
		assertTrue(getAnnotCount(countlessAnnotationsFile) == 22);
	}
}