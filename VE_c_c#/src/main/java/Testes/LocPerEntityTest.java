package Testes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.gnu.c.GCCLanguage;
import org.eclipse.cdt.core.model.ILanguage;
import org.eclipse.cdt.core.parser.DefaultLogService;
import org.eclipse.cdt.core.parser.FileContent;
import org.eclipse.cdt.core.parser.IncludeFileContentProvider;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.model.TranslationUnit;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

import Vocabulary.vloccount.AnnotationCounter;
import Vocabulary.vloccount.LOCCountPerEntity;
import Vocabulary.vloccount.PhysicalLOCCount;

/**
 * Tests the LOCCountPerEntity class
 * @author Tercio de Melo
 */
public class LocPerEntityTest extends TestCase {
private static String testFilesDir = "./files/LocTests/locPerEntityTests";
	
	private static String simpleFile = testFilesDir + "/SimpleFile";
	private static String noBlankLinesFile = testFilesDir + "/NoBlankLinesFile";
	private static String noCommentsFile = testFilesDir + "/NoCommentsFile";
	private static String noAnnotationsFile = testFilesDir + "/NoAnnotationFile";
	private static String complexFile = testFilesDir + "/ComplexFile";
	
	@SuppressWarnings("unchecked")
	private static ASTNode getASTTreeFromSourceCode(final char[] sourceCode) throws CoreException {
		
		FileContent reader = FileContent.create("", sourceCode);
	     
		//gera a AST de um codigo fonte em C
		IASTTranslationUnit translationUnit =  GCCLanguage.getDefault().getASTTranslationUnit(
				reader, new ScannerInfo(), IncludeFileContentProvider.getSavedFilesProvider(), 
				null, ILanguage.OPTION_IS_SOURCE_UNIT, new DefaultLogService());
		
		// setting java compilationUnit
		
		return (ASTNode)translationUnit;
		
	}
	
	@SuppressWarnings({ "unchecked" })
	private static boolean assertLocCountPerEntity(String file) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(file));
		String sourceCode = "", aux;
		
		while((aux = in.readLine()) != null) {
			sourceCode = String.format("%s\n%s", sourceCode, aux);
		}
		in.close();
		
		IASTTranslationUnit compilationUnit = (IASTTranslationUnit) getASTTreeFromSourceCode(sourceCode.toCharArray());

		IASTDeclaration[] decl = compilationUnit.getDeclarations();
		for(IASTDeclaration type : decl) {
			LOCCountPerEntity counter = new LOCCountPerEntity((ASTNode)type, Arrays.asList(compilationUnit.getComments()), sourceCode.toString());
			if(counter.getLOC() != 14) {
				return false;
			}
		}
		
		return true;
	}
	
	@SuppressWarnings({ "unchecked" })
	private static int getLocCountPerEntity(String file) throws Exception {
		int totalEntitiesLOC = 0;
		
		BufferedReader in = new BufferedReader(new FileReader(file));
		String sourceCode = "", aux;
		
		while((aux = in.readLine()) != null) {
			sourceCode = String.format("%s\n%s", sourceCode, aux);
		}
		in.close();
		IASTTranslationUnit compilationUnit = (IASTTranslationUnit) getASTTreeFromSourceCode(sourceCode.toCharArray());
		
		IASTDeclaration[] decl = compilationUnit.getDeclarations();
		for(IASTDeclaration type : decl) {
			LOCCountPerEntity count = new LOCCountPerEntity((ASTNode)type, Arrays.asList(compilationUnit.getComments()), sourceCode);
			totalEntitiesLOC += count.getLOC() + count.getInner() + count.getAnnot();
		}

		return totalEntitiesLOC + compilationUnit.getIncludeDirectives().length;
	}
	
	@SuppressWarnings({ "unchecked" })
	private static int getPhysicalLocCount(String file) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(file));
		String sourceCode = "", aux;
		
		while((aux = in.readLine()) != null) {
			sourceCode = String.format("%s\n%s", sourceCode, aux);
		}
		in.close();
		IASTTranslationUnit compilationUnit = (IASTTranslationUnit) getASTTreeFromSourceCode(sourceCode.toCharArray());

		return (new PhysicalLOCCount(Arrays.asList(compilationUnit.getComments()), sourceCode, sourceCode.split("\n").length)).getLOC();
	}
	
	public void testSimpleFile() throws Exception {
		assertTrue(assertLocCountPerEntity(simpleFile));
		assertTrue(getLocCountPerEntity(simpleFile) == getPhysicalLocCount(simpleFile));
	}
	
	public void testNoBlankLinesFile() throws Exception {
		assertTrue(assertLocCountPerEntity(noBlankLinesFile));
		assertTrue(getLocCountPerEntity(noBlankLinesFile) == getPhysicalLocCount(noBlankLinesFile));
	}
	
	public void testNoCommentsFile() throws Exception {
		assertTrue(assertLocCountPerEntity(noCommentsFile));
		assertTrue(getLocCountPerEntity(noCommentsFile) == getPhysicalLocCount(noCommentsFile));
	}
	
	public void testNoAnnotationsFile() throws Exception {
		assertTrue(assertLocCountPerEntity(noAnnotationsFile));
		assertTrue(getLocCountPerEntity(noAnnotationsFile) == getPhysicalLocCount(noAnnotationsFile));
	}
	
	public void testComplexFile() throws Exception {
		assertTrue(assertLocCountPerEntity(complexFile));
		assertTrue(getLocCountPerEntity(complexFile) == getPhysicalLocCount(complexFile));
	}
}