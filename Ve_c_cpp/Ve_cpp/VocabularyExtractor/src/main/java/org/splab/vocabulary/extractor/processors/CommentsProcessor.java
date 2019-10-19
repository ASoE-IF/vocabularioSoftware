package org.splab.vocabulary.extractor.processors;

import java.util.List;

import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.splab.vocabulary.extractor.util.CommentUnit;
import org.splab.vocabulary.extractor.util.VxlManager;

/**
 * Essa classe é responsável por extrair os comentários onde quer que eles
 * estejam no código fonte.
 * 
 * Obs.: Essa classe é baseada na CommentsProcessor do extrator original do
 * Java.
 * 
 * @author Israel Gomes de Lima
 */
public class CommentsProcessor {
	/**
	 * Mantém um fragmento de vxl
	 */
	private StringBuffer vxlFragment;

	/**
	 * Construtor responsável por processar os comentários corretamente.
	 * 
	 * @param allDeclarations
	 * @param type
	 * @param entityIndentationLevel
	 */
	public CommentsProcessor(List<ASTNode> allDeclarations, ASTNode type, int entityIndentationLevel) {
		vxlFragment = new StringBuffer();

		List<CommentUnit> sourceCodeComments = CompilationUnitProcessor.sourceCodeComments;

		for (CommentUnit c : sourceCodeComments)
			if (allDeclarations.size() != 0) {
				if (c.getStartPosition() >= type.getOffset()
						&& c.getStartPosition() < allDeclarations.get(0).getFileLocation().getNodeOffset()) {
					vxlFragment.append(VxlManager.commentTag(c, entityIndentationLevel));
				}
			} else {
				if (c.getStartPosition() >= type.getOffset()
						&& c.getEndPosition() < type.getOffset() + type.getLength()) {
					vxlFragment.append(VxlManager.commentTag(c, entityIndentationLevel));
				}
			}

		// Seleciona os comentarios localizados entre entidades de
		// encapsulamento
		for (int i = 1; i < allDeclarations.size(); i++)
			for (CommentUnit c : sourceCodeComments) {
				int endOfPreviousDeclaration = allDeclarations.get(i - 1).getFileLocation().getNodeOffset()
						+ allDeclarations.get(i - 1).getFileLocation().getNodeLength();
				int beginOfCurrentDeclaration = allDeclarations.get(i).getFileLocation().getNodeOffset();

				if (c.getStartPosition() >= endOfPreviousDeclaration
						&& c.getStartPosition() < beginOfCurrentDeclaration) {
					vxlFragment.append(VxlManager.commentTag(c, entityIndentationLevel));
				}
			}

		// Seleciona os comentarios localizados depois de todas as entidades de
		// encapsulamento mas dentro do corpo da classe
		for (CommentUnit c : sourceCodeComments)
			if (allDeclarations.size() != 0) {
				int endOfPreviousDeclaration = allDeclarations.get(allDeclarations.size() - 1).getFileLocation()
						.getNodeOffset()
						+ allDeclarations.get(allDeclarations.size() - 1).getFileLocation().getNodeLength();
				int endOfFile = type.getOffset() + type.getLength();

				if (c.getStartPosition() >= endOfPreviousDeclaration && c.getStartPosition() <= endOfFile) {
					vxlFragment.append(VxlManager.commentTag(c, entityIndentationLevel));
				}
			}
	}

	/**
	 * Construtor responsável por processar os comentários de entidades que não
	 * contem entidades internas com corpos.
	 * 
	 * @param declaration
	 * @param entityIndentationLevel
	 */
	public CommentsProcessor(ASTNode declaration, int entityIndentationLevel) {
		vxlFragment = new StringBuffer();

		for (CommentUnit c : CompilationUnitProcessor.sourceCodeComments)
			if (c.getStartPosition() > declaration.getFileLocation().getNodeOffset()
					&& c.getStartPosition() < (declaration.getFileLocation().getNodeOffset()
							+ declaration.getFileLocation().getNodeLength()))
				vxlFragment.append(VxlManager.commentTag(c, entityIndentationLevel));
	}

	/**
	 * Retorna o fragmento de vxl
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