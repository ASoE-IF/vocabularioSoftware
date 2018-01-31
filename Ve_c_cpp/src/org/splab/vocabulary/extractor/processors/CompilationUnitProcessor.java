package org.splab.vocabulary.extractor.processors;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTComment;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.splab.vocabulary.extractor.util.CommentUnit;
import org.splab.vocabulary.extractor.util.LOCManager;
import org.splab.vocabulary.extractor.vloccount.LOCParameters;
import org.splab.vocabulary.extractor.vloccount.PhysicalLOCCount;

public class CompilationUnitProcessor {
	protected static List<IASTComment> commentList;
	protected static String sourceCode;
	protected static List<CommentUnit> sourceCodeComments;
	private StringBuffer vxlFragment;

	public CompilationUnitProcessor(IASTTranslationUnit translationUnit, String fileName) {
		vxlFragment = new StringBuffer();

		commentList = Arrays.asList(translationUnit.getComments());

		//Parametro p, é executado com o if abaixo
		if (LOCManager.locParameters.contains(LOCParameters.ONLY_PHYSICAL_LINES)) {
			if (fileName != null) {
				PhysicalLOCCount locCount = new PhysicalLOCCount(commentList, sourceCode, sourceCode.split("\n").length);
				LOCManager.appendCompilationUnitLOCData(fileName, locCount);
			}
		}

		FileProcessor fileProcessor = new FileProcessor(translationUnit, fileName,
				translationUnit.getAllPreprocessorStatements().length);
		vxlFragment.append(fileProcessor.getVxlFragment());
	}

	public static void reset() {
		sourceCodeComments = new LinkedList<CommentUnit>();
		commentList = new LinkedList<IASTComment>();
	}

	public static String getSourceCode() {
		return sourceCode;
	}

	public static void setSourceCode(String source) {
		sourceCode = source;
	}

	public static void addCommentUnit(CommentUnit comment) {
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