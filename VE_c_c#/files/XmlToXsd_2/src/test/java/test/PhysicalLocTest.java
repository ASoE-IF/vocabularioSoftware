package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.splabs.vocabulary.vxl.vloccount.PhysicalLOCCount;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Tests the PhysicalLOCCount class
 * @author Tercio de Melo
 */
public class PhysicalLocTest extends TestCase{
	
	private static String testFilesDir = "./files/LocTests/physicalCountTests";
	
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
	private static int getPhysicalLocCount(String file) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(file));
		String sourceCode = "", aux;
		int count = 0;
		
		while((aux = in.readLine()) != null) {
			sourceCode = String.format("%s\n%s", sourceCode, aux);
		}
		
		CompilationUnit compilationUnit = (CompilationUnit) getASTTreeFromSourceCode(sourceCode.toCharArray());

		PhysicalLOCCount counter = new PhysicalLOCCount(
				compilationUnit.getCommentList(), sourceCode,
				sourceCode.split("\n").length);
		count += counter.getLOC();

		return count;
	}
	
	public void testSimpleFile() throws Exception {
		Assert.assertTrue(getPhysicalLocCount(simpleFile) == 17);
	}
	
	public void testNoBlankLinesFile() throws Exception {
		Assert.assertTrue(getPhysicalLocCount(noBlankLinesFile) == 17);
	}
	
	public void testNoCommentsFile() throws Exception {
		Assert.assertTrue(getPhysicalLocCount(noCommentsFile) == 17);
	}
	
	public void testNoAnnotationsFile() throws Exception {
		Assert.assertTrue(getPhysicalLocCount(noAnnotationsFile) == 15);
	}
	
	public void testComplexFile() throws Exception {
		Assert.assertTrue(getPhysicalLocCount(complexFile) == 17);
	}

}
