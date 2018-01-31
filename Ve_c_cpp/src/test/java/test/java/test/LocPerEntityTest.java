package test.java.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.gnu.c.GCCLanguage;
import org.eclipse.cdt.core.parser.DefaultLogService;
import org.eclipse.cdt.core.parser.FileContent;
import org.eclipse.cdt.core.parser.IParserLogService;
import org.eclipse.cdt.core.parser.IScannerInfo;
import org.eclipse.cdt.core.parser.IncludeFileContentProvider;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTEnumerationSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclaration;
import org.eclipse.core.runtime.CoreException;
import org.splab.vocabulary.extractor.vloccount.LOCCountPerEntity;
import org.splab.vocabulary.extractor.vloccount.PhysicalLOCCount;

/**
 * Tests the LOCCountPerEntity class
 * @author Tercio de Melo
 */
public class LocPerEntityTest extends TestCase {
private static String testFilesDir = "./files/LocTests/locPerEntityTests";
	
	private static String simpleFile = testFilesDir + "/SimpleFile";
	private static String noBlankLinesFile = testFilesDir + "/NoBlankLinesFile";
	private static String noCommentsFile = testFilesDir + "/NoCommentsFile";
	private static String complexFile = testFilesDir + "/ComplexFile";
	
	@SuppressWarnings("unchecked")
	private static IASTTranslationUnit getASTTreeFromSourceCode(final char[] sourceCode) throws InvocationTargetException, InterruptedException, CoreException {

		FileContent fileContent = FileContent.create(testFilesDir, sourceCode);

		Map definedSymbols = new HashMap();
		String[] includePaths = new String[0];
		IScannerInfo info = new ScannerInfo(definedSymbols, includePaths);
		IParserLogService log = new DefaultLogService();

		IncludeFileContentProvider emptyIncludes = IncludeFileContentProvider.getEmptyFilesProvider();

		int opts = 8;
		IASTTranslationUnit translationUnit = GCCLanguage.getDefault().getASTTranslationUnit(fileContent, info,
				emptyIncludes, null, opts, log);
		
		return translationUnit;
	}
	
	@SuppressWarnings({ "unchecked" })
	private static boolean assertLocCountPerEntity(String file) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(file));
		String sourceCode = "", aux;

		while((aux = in.readLine()) != null) {
			sourceCode = String.format("%s\n%s", sourceCode, aux);
		}
		in.close();
		
		IASTTranslationUnit translationUnit = (IASTTranslationUnit) getASTTreeFromSourceCode(sourceCode.toCharArray());

		IASTDeclaration[] declarations = translationUnit.getDeclarations();
		List<ASTNode> listDeclarations = declarationList(declarations);
		int headerLines = translationUnit.getAllPreprocessorStatements().length;
		LOCCountPerEntity counter = new LOCCountPerEntity((ASTNode) translationUnit, Arrays.asList(translationUnit.getComments()), sourceCode, listDeclarations, headerLines);

		if(counter.getLOC() != 60)
		{
			return false;
		}
		
		for (IASTDeclaration declaration : declarations) {
			if (declaration instanceof IASTFunctionDefinition) {
				counter = new LOCCountPerEntity((ASTNode) declaration, Arrays.asList(translationUnit.getComments()), sourceCode, new LinkedList<ASTNode>(), 0);

				if(counter.getLOC() != 28)
				{
					return false;
				}
			}

			if (declaration instanceof IASTSimpleDeclaration) {
				CASTSimpleDeclaration node = (CASTSimpleDeclaration) declaration;

				IASTDeclSpecifier declarationSpecifier = node.getDeclSpecifier();

				if (declarationSpecifier instanceof CASTEnumerationSpecifier) {
					counter = new LOCCountPerEntity((ASTNode) declaration, Arrays.asList(translationUnit.getComments()), sourceCode, new LinkedList<ASTNode>(), 0);
					
					if(counter.getLOC() != 4)
					{
						return false;
					}
				}

				if (declarationSpecifier instanceof CASTCompositeTypeSpecifier) {
					counter = new LOCCountPerEntity((ASTNode) declaration, Arrays.asList(translationUnit.getComments()), sourceCode, new LinkedList<ASTNode>(), 0);
					
					if(counter.getLOC() != 6)
					{
						return false;
					}
				}
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
		
		IASTTranslationUnit translationUnit = (IASTTranslationUnit) getASTTreeFromSourceCode(sourceCode.toCharArray());

		IASTDeclaration[] declarations = translationUnit.getDeclarations();
		List<ASTNode> listDeclarations = declarationList(declarations);
		int headerLines = translationUnit.getAllPreprocessorStatements().length;
		
		LOCCountPerEntity count = new LOCCountPerEntity((ASTNode) translationUnit, Arrays.asList(translationUnit.getComments()), sourceCode, listDeclarations, headerLines);
		totalEntitiesLOC += count.getLOC() + headerLines;
		
		return totalEntitiesLOC;
	}
	
	@SuppressWarnings({ "unchecked" })
	private static int getPhysicalLocCount(String file) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(file));
		String sourceCode = "", aux;
		
		while((aux = in.readLine()) != null) {
			sourceCode = String.format("%s\n%s", sourceCode, aux);
		}
		in.close();
		IASTTranslationUnit translationUnit = (IASTTranslationUnit) getASTTreeFromSourceCode(sourceCode.toCharArray());

		return (new PhysicalLOCCount(Arrays.asList(translationUnit.getComments()), sourceCode, sourceCode.split("\n").length)).getLOC();
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

	public void testComplexFile() throws Exception {
		assertTrue(assertLocCountPerEntity(complexFile));
		assertTrue(getLocCountPerEntity(complexFile) == getPhysicalLocCount(complexFile));
	}
	
	private static List<ASTNode> declarationList(IASTDeclaration[] declarations) {
		List<ASTNode> fileDeclarations = new ArrayList<ASTNode>();

		for (IASTDeclaration declaration : declarations) {
			if (declaration instanceof IASTFunctionDefinition) {
				fileDeclarations.add((ASTNode) declaration);
			}

			if (declaration instanceof IASTSimpleDeclaration) {
				CASTSimpleDeclaration node = (CASTSimpleDeclaration) declaration;

				IASTDeclSpecifier declarationSpecifier = node.getDeclSpecifier();

				if (declarationSpecifier instanceof CASTEnumerationSpecifier) {
					CASTEnumerationSpecifier enumeration = (CASTEnumerationSpecifier) declarationSpecifier;
					fileDeclarations.add((ASTNode) enumeration);
				}

				if (declarationSpecifier instanceof CASTCompositeTypeSpecifier) {
					CASTCompositeTypeSpecifier composite = (CASTCompositeTypeSpecifier) declarationSpecifier;
					fileDeclarations.add((ASTNode) composite);
				}
			}
		}

		return fileDeclarations;
	}
}