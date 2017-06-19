package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.splabs.vocabulary.vxl.vloccount.LOCCountPerEntity;
import org.splabs.vocabulary.vxl.vloccount.PhysicalLOCCount;

import junit.framework.Assert;
import junit.framework.TestCase;

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
	private static ASTNode getASTTreeFromSourceCode(final char[] sourceCode) throws InvocationTargetException, InterruptedException {
		
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(sourceCode);
		
		// setting java compilationUnit
		Map<String, String> options = JavaCore.getOptions();
        options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_6);
        options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_6);
        options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_6);
		parser.setCompilerOptions(options);
		
		return parser.createAST(new NullProgressMonitor());
		
	}
	
	@SuppressWarnings("unchecked")
	private static boolean assertLocCountPerEntity(String file) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(file));
		String sourceCode = "", aux;
		
		while((aux = in.readLine()) != null) {
			sourceCode = String.format("%s\n%s", sourceCode, aux);
		}
		
		CompilationUnit compilationUnit = (CompilationUnit) getASTTreeFromSourceCode(sourceCode.toCharArray());

		for (AbstractTypeDeclaration type : (List<AbstractTypeDeclaration>) compilationUnit.types()) {
			LOCCountPerEntity counter = new LOCCountPerEntity(type,	compilationUnit.getCommentList(), sourceCode);
			if(counter.getLOC() != 14) {
				return false;
			}
		}

		return true;
	}
	
	@SuppressWarnings("unchecked")
	private static int getLocCountPerEntity(String file) throws Exception {
		int totalEntitiesLOC = 0;
		
		BufferedReader in = new BufferedReader(new FileReader(file));
		String sourceCode = "", aux;
		
		while((aux = in.readLine()) != null) {
			sourceCode = String.format("%s\n%s", sourceCode, aux);
		}
		CompilationUnit compilationUnit = (CompilationUnit) getASTTreeFromSourceCode(sourceCode.toCharArray());
		
		
		for(AbstractTypeDeclaration type : (List<AbstractTypeDeclaration>) compilationUnit.types()) {
			LOCCountPerEntity count = new LOCCountPerEntity(type, compilationUnit.getCommentList(), sourceCode);
			totalEntitiesLOC += count.getLOC() + count.getInner() + count.getAnnot();
		}

		return totalEntitiesLOC + compilationUnit.imports().size() + (compilationUnit.getPackage() == null ? 0 : 1);
	}
	
	@SuppressWarnings("unchecked")
	private static int getPhysicalLocCount(String file) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(file));
		String sourceCode = "", aux;
		
		while((aux = in.readLine()) != null) {
			sourceCode = String.format("%s\n%s", sourceCode, aux);
		}
		
		CompilationUnit compilationUnit = (CompilationUnit) getASTTreeFromSourceCode(sourceCode.toCharArray());

		return (new PhysicalLOCCount(compilationUnit.getCommentList(), sourceCode, sourceCode.split("\n").length)).getLOC();
	}
	
	public void testSimpleFile() throws Exception {
		Assert.assertTrue(assertLocCountPerEntity(simpleFile));
		Assert.assertTrue(getLocCountPerEntity(simpleFile) == getPhysicalLocCount(simpleFile));
	}
	
	public void testNoBlankLinesFile() throws Exception {
		Assert.assertTrue(assertLocCountPerEntity(noBlankLinesFile));
		Assert.assertTrue(getLocCountPerEntity(noBlankLinesFile) == getPhysicalLocCount(noBlankLinesFile));
	}
	
	public void testNoCommentsFile() throws Exception {
		Assert.assertTrue(assertLocCountPerEntity(noCommentsFile));
		Assert.assertTrue(getLocCountPerEntity(noCommentsFile) == getPhysicalLocCount(noCommentsFile));
	}
	
	public void testNoAnnotationsFile() throws Exception {
		Assert.assertTrue(assertLocCountPerEntity(noAnnotationsFile));
		Assert.assertTrue(getLocCountPerEntity(noAnnotationsFile) == getPhysicalLocCount(noAnnotationsFile));
	}
	
	public void testComplexFile() throws Exception {
		Assert.assertTrue(assertLocCountPerEntity(complexFile));
		Assert.assertTrue(getLocCountPerEntity(complexFile) == getPhysicalLocCount(complexFile));
	}
}
