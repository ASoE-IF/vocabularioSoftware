package Vocabulary.Processors;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTComment;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import Vocabulary.Extractor.Util.CommentUnitC;
import Vocabulary.Extractor.Util.LOCManager;
import Vocabulary.vloccount.LOCParameters;
import Vocabulary.vloccount.PhysicalLOCCount;

public class CompilationUnitProcessor {
	protected static List<IASTComment> commentList;
	protected static String sourceCode;
	protected static List<CommentUnitC> sourceCodeComments;
	private StringBuffer vxlFragment;
	
	@SuppressWarnings("unchecked")
	public CompilationUnitProcessor(IASTTranslationUnit translationUnit, String fileName) {
		vxlFragment = new StringBuffer();

		commentList = Arrays.asList(translationUnit.getComments());
		
		if(LOCManager.locParameters.contains(LOCParameters.ONLY_PHYSICAL_LINES)) {
			if(fileName != null) {
				PhysicalLOCCount locCount = new PhysicalLOCCount(commentList, sourceCode, sourceCode.split("\n").length);
				LOCManager.appendCompilationUnitLOCData(fileName, locCount);
			}
		}
		
		FileProcessor fileProcessor = new FileProcessor(translationUnit, fileName, false, translationUnit.getAllPreprocessorStatements().length);
		vxlFragment.append(fileProcessor.getVxlFragment());
	}
	
	public static void reset() {
		sourceCodeComments = new LinkedList<CommentUnitC>();
		commentList = new LinkedList<IASTComment>();
	}
	
	public static String getSourceCode() {
		return sourceCode;
	}
	
	public static void setSourceCode(String source) {
		sourceCode = source;
	}
	
	public static void addCommentUnitC(CommentUnitC comment) {
		sourceCodeComments.add(comment);
	}
	
	public StringBuffer getVxlFragment() {
		return vxlFragment;
	}
	
	@Override
	public String toString() {
		return vxlFragment.toString();
	}
}