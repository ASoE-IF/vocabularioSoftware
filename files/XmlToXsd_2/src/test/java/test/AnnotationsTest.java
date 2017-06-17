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
import org.splabs.vocabulary.vxl.vloccount.AnnotationCounter;

import junit.framework.*;

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
	private static int getAnnotCount(String file) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(file));
		String sourceCode = "", aux;
		int count = 0;
		
		int[][] limits = new int[0][0];
		
		while((aux = in.readLine()) != null) {
			sourceCode = String.format("%s\n%s", sourceCode, aux);
		}
		
		CompilationUnit compilationUnit = (CompilationUnit) getASTTreeFromSourceCode(sourceCode.toCharArray());
		
		for(AbstractTypeDeclaration type : (List<AbstractTypeDeclaration>)compilationUnit.types()) {
			AnnotationCounter counter = new AnnotationCounter(type, limits, compilationUnit.getCommentList(), sourceCode);
			count += counter.getAnnotationLoc();
		}
		
		return count;
	}
	
	public void testOneLineAnnotationsFile() throws Exception {
		Assert.assertTrue(getAnnotCount(oneLineAnnotationsFile) == 5);
	}
	
	public void testMultipleLinesAnnotationsFile() throws Exception {
		Assert.assertTrue(getAnnotCount(multipleLinesAnnotatinosFile) == 22);
	}
	
	public void testAnnotationsAndJavadocsFile() throws Exception {
		Assert.assertTrue(getAnnotCount(AnnotationsAndJavadocsFile) == 17);
	}
	
	public void testCountlessAnnotationsFile() throws Exception {
		Assert.assertTrue(getAnnotCount(countlessAnnotationsFile) == 22);
	}
}
