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

/**
 * Essa classe é responsável por extrair algumas informações
 * da ast do código fonte em c.
 * 
 * Obs.: Essa classe é baseada na classe CompilationUnitProcessor
 * original do extrator do java.
 * 
 * @author Israel Gomes de Lima
 *
 */
public class CompilationUnitProcessor {
	/**
	 * Mantém listas com comentários
	 */
	protected static List<IASTComment> commentList;
	protected static String sourceCode;
	protected static List<CommentUnit> sourceCodeComments;
	
	/**
	 * Mantém o fragmento de vxl
	 */
	private StringBuffer vxlFragment;

	/**
	 * Recebe uma ast e extrai algumas informações
	 * 
	 * @param translationUnit
	 * @param fileName
	 */
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

	/**
	 * Apaga as informações salvas até o momento.
	 */
	public static void reset() {
		sourceCodeComments = new LinkedList<CommentUnit>();
		commentList = new LinkedList<IASTComment>();
	}

	/**
	 * Retorna o código fonte
	 * 
	 * @return
	 */
	public static String getSourceCode() {
		return sourceCode;
	}

	/**
	 * Insere o código fonte
	 * 
	 * @param source
	 */
	public static void setSourceCode(String source) {
		sourceCode = source;
	}

	/**
	 * Adiciona o comentário
	 * 
	 * @param comment
	 */
	public static void addCommentUnit(CommentUnit comment) {
		sourceCodeComments.add(comment);
	}

	/**
	 * Retorna o fragmento de vxl.
	 * 
	 * @return
	 */
	public StringBuffer getVxlFragment() {
		return vxlFragment;
	}

	@Override
	public String toString() {
		return vxlFragment.toString();
	}
}