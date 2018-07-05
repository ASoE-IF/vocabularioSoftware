package test.java.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage;
import org.eclipse.cdt.core.parser.DefaultLogService;
import org.eclipse.cdt.core.parser.FileContent;
import org.eclipse.cdt.core.parser.IParserLogService;
import org.eclipse.cdt.core.parser.IScannerInfo;
import org.eclipse.cdt.core.parser.IncludeFileContentProvider;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTEnumerationSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration;
import org.eclipse.core.runtime.CoreException;
import org.splab.vocabulary.extractor.processors.DirectivesProcessor;
import org.splab.vocabulary.extractor.vloccount.EntityLOCKeeper;
import org.splab.vocabulary.extractor.vloccount.LOCCountPerEntity;
import org.splab.vocabulary.extractor.vloccount.PhysicalLOCCount;

/**
 * Tests the LOCCountPerEntity class
 * 
 * @author Tercio de Melo
 */
public class LocPerEntityTest extends TestCase {
	private static String testFilesDir = "./files/LocTests/locPerEntityTests";

	private static String simpleFile = testFilesDir + "/SimpleFile";
	private static String noBlankLinesFile = testFilesDir + "/NoBlankLinesFile";
	private static String noCommentsFile = testFilesDir + "/NoCommentsFile";
	private static String complexFile = testFilesDir + "/ComplexFile";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static IASTTranslationUnit getASTTreeFromSourceCode(final char[] sourceCode)
			throws InvocationTargetException, InterruptedException, CoreException {

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

	private static boolean assertLocCountPerEntity(String file) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(file));
		String sourceCode = "", aux;

		while ((aux = in.readLine()) != null) {
			sourceCode = String.format("%s\n%s", sourceCode, aux);
		}
		in.close();

		IASTTranslationUnit translationUnit = (IASTTranslationUnit) getASTTreeFromSourceCode(sourceCode.toCharArray());
		IASTDeclaration[] declarations = translationUnit.getDeclarations();
		DirectivesProcessor.extractDirectives(translationUnit.getAllPreprocessorStatements());

		LOCCountPerEntity counter = new LOCCountPerEntity((ASTNode) translationUnit,
				Arrays.asList(translationUnit.getComments()), sourceCode);

		if (counter.getLOC() != 19) {
			return false;
		}

		for (IASTDeclaration declaration : declarations) {
			if (declaration instanceof IASTFunctionDefinition) {
				counter = new LOCCountPerEntity((ASTNode) declaration, Arrays.asList(translationUnit.getComments()),
						sourceCode);

				if (counter.getLOC() != 5) {
					return false;
				}
			}

			if (declaration instanceof IASTSimpleDeclaration) {
				CPPASTSimpleDeclaration node = (CPPASTSimpleDeclaration) declaration;

				IASTDeclSpecifier declarationSpecifier = node.getDeclSpecifier();

				if (declarationSpecifier instanceof CPPASTEnumerationSpecifier) {
					counter = new LOCCountPerEntity((ASTNode) declaration, Arrays.asList(translationUnit.getComments()),
							sourceCode);

					if (counter.getLOC() != 4) {
						return false;
					}
				}

				if (declarationSpecifier instanceof CPPASTCompositeTypeSpecifier) {
					counter = new LOCCountPerEntity((ASTNode) declaration, Arrays.asList(translationUnit.getComments()),
							sourceCode);

					if (counter.getLOC() != 6 && counter.getLOC() != 40) {
						return false;
					}
				}
			}

		}

		return true;
	}

	private static int getLocCountPerEntity(String file) throws Exception {
		int totalEntitiesLOC = 0;

		BufferedReader in = new BufferedReader(new FileReader(file));
		String sourceCode = "", aux;

		while ((aux = in.readLine()) != null) {
			sourceCode = String.format("%s\n%s", sourceCode, aux);
		}
		in.close();

		IASTTranslationUnit translationUnit = (IASTTranslationUnit) getASTTreeFromSourceCode(sourceCode.toCharArray());
		IASTDeclaration[] declarations = translationUnit.getDeclarations();
		DirectivesProcessor.extractDirectives(translationUnit.getAllPreprocessorStatements());

		LOCCountPerEntity counter = new LOCCountPerEntity((ASTNode) translationUnit,
				Arrays.asList(translationUnit.getComments()), sourceCode);
		EntityLOCKeeper locKeeper = new EntityLOCKeeper(counter);
		locKeeper.setHeadersLOC(translationUnit.getAllPreprocessorStatements().length, false, true);
		totalEntitiesLOC += counter.getLOC();

		for (IASTDeclaration declaration : declarations) {
			if (declaration instanceof IASTFunctionDefinition) {
				counter = new LOCCountPerEntity((ASTNode) declaration, Arrays.asList(translationUnit.getComments()),
						sourceCode);

				totalEntitiesLOC += counter.getLOC() + counter.getInner();
			}

			if (declaration instanceof IASTSimpleDeclaration) {
				CPPASTSimpleDeclaration node = (CPPASTSimpleDeclaration) declaration;

				IASTDeclSpecifier declarationSpecifier = node.getDeclSpecifier();

				if (declarationSpecifier instanceof CPPASTEnumerationSpecifier) {
					counter = new LOCCountPerEntity((ASTNode) declaration, Arrays.asList(translationUnit.getComments()),
							sourceCode);

					totalEntitiesLOC += counter.getLOC();
				}

				if (declarationSpecifier instanceof CPPASTCompositeTypeSpecifier) {
					counter = new LOCCountPerEntity((ASTNode) declaration, Arrays.asList(translationUnit.getComments()),
							sourceCode);

					totalEntitiesLOC += counter.getLOC();
				}
			}
		}
		return totalEntitiesLOC + locKeeper.getHeaderLOC();
	}

	private static int getPhysicalLocCount(String file) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(file));
		String sourceCode = "", aux;

		while ((aux = in.readLine()) != null) {
			sourceCode = String.format("%s\n%s", sourceCode, aux);
		}
		in.close();
		IASTTranslationUnit translationUnit = (IASTTranslationUnit) getASTTreeFromSourceCode(sourceCode.toCharArray());
		DirectivesProcessor.extractDirectives(translationUnit.getAllPreprocessorStatements());
		return (new PhysicalLOCCount(Arrays.asList(translationUnit.getComments()), sourceCode,
				sourceCode.split("\n").length)).getLOC();
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
}