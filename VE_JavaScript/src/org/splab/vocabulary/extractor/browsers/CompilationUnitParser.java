package org.splab.vocabulary.extractor.browsers;

import java.util.List;
import java.util.Map;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.LineComment;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.splab.vocabulary.extractor.processors.CompilationUnitProcessor;
import org.splab.vocabulary.extractor.util.CommentUnit;

/**
 * 
 * @author Tercio de Melo
 *
 */
public abstract class CompilationUnitParser {
	protected static CompilationUnit astNode;
	
	@SuppressWarnings("unchecked")
	public static StringBuffer parse(char[] sourceCode, String fileName, String pathRoot) {
		CompilationUnitProcessor.setSourceCode(new String(sourceCode));
		
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(sourceCode);
		
		// setting java compilationUnit
		Map<String, String> options = JavaCore.getOptions();
        options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_6);
        options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_6);
        options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_6);
		parser.setCompilerOptions(options);
		
		astNode = (CompilationUnit) parser.createAST(new NullProgressMonitor());

		CompilationUnitProcessor.reset();
		setCompilationUnitComments(astNode, CompilationUnitProcessor.getSourceCode());
		String packageName = getPackage(pathRoot, fileName);
			
		return (new CompilationUnitProcessor(astNode, fileName, packageName)).getVxlFragment();
	}
	
	public static String getPackage(String pathRoot, String fileName) {
		PackageDeclaration pckg = astNode.getPackage();
		String packageName = pckg == null ? "default" : pckg.getName().getFullyQualifiedName();
		return  getDirPrefix(pathRoot, fileName, packageName) + packageName;
	}
	
	private static String getDirPrefix(String pathRoot, String fileName, String packageName) {
		int begin = pathRoot.length();
		String pack = packageName;
		pack = pack.replaceAll("\\.", "/");
		int end = fileName.lastIndexOf(pack);
		String prefix = end != -1 ? fileName.substring(begin, end) : fileName.substring(begin);
		return prefix + ":";
	}
	
	@SuppressWarnings("unchecked")
	private static void setCompilationUnitComments(CompilationUnit compilationUnit, String sourceCode) {
		for(Comment element : (List<Comment>) compilationUnit.getCommentList())
			if(element instanceof BlockComment || element instanceof LineComment) {
				CompilationUnitProcessor.addCommentUnit(new CommentUnit(element, sourceCode));
			}
	}
}