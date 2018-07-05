package org.splab.vocabulary.extractor.vloccount;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTComment;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.splab.vocabulary.extractor.nodelists.DeclarationList;
import org.splab.vocabulary.extractor.processors.DirectivesProcessor;

/**
 * Manages physical loc extraction and annotations counting in a manner that
 * constrains such metrics to an specific entity.
 * 
 * @author Tercio de Melo
 */
public class LOCCountPerEntity {

	private int loc, inner;
	private List<IASTComment> allComments;

	/**
	 * Default constructor, receives an abstraction of type entity, a comment
	 * 
	 * @param type
	 * @param comments
	 * @param sourceCode
	 */
	public LOCCountPerEntity(ASTNode type, List<IASTComment> comments, String sourceCode) {
		this.allComments = comments;
		this.inner = 0;
		this.loc = locCount(type, getScopeComments(type, comments), sourceCode);
	}

	/**
	 * Returns the number of java code lines that belong to the given entity
	 * body but doesn't belong to any inner classes.
	 * 
	 * @return
	 */
	public int getLOC() {
		return loc;
	}

	/**
	 * Returns the sum of the inner classes' LOC Count per Entity
	 * 
	 * @return
	 */
	public int getInner() {
		return inner;
	}

	/**
	 * This method is responsible for the management of Physical LOC counting,
	 * annotations counting and inner entities Physical LOC subtraction.
	 * 
	 * @param type
	 *            The given type to be analyzed.
	 * @param comments
	 *            A list of selected comments by the getScopeComments() method
	 * @param sourceCode
	 *            The String representation of the source file containing the
	 *            given entity
	 * @return The number of Java Code lines contained in the given entity.
	 */
	private int locCount(ASTNode type, List<IASTComment> comments, String sourceCode) {
		/*
		 * The AST considers the beginning of an Javadoc as the beginning of the
		 * given entity, we don't. So, if there's any javadoc, it's end point
		 * will be the entity's beginning point
		 */
		int begin = type.getFileLocation().getNodeOffset();
		// The end point is the beginnig point plus the entity's length
		int end = type.getFileLocation().getNodeOffset() + type.getFileLocation().getNodeLength();

		// the temporary loc of the entity is it's physical loc count
		int linesOfCode = sourceCode.substring(begin, end).split("\n").length;
		linesOfCode = (new PhysicalLOCCount(comments, sourceCode, linesOfCode, begin, end)).getLOC();
		linesOfCode -= headersLOCDecrement(DeclarationList.getInnerTypes(type), type);

		if (!(type instanceof ASTNode))
			return linesOfCode;

		for (ASTNode t : DeclarationList.getInnerTypes(type)) {
			int start = t.getFileLocation().getNodeOffset(), finall = start + t.getFileLocation().getNodeLength();

			int innerloc = sourceCode.substring(start, finall).split("\n").length;
			int aInner = (new PhysicalLOCCount(getScopeComments(t, this.allComments), sourceCode, innerloc, start,
					finall)).getLOC();

			linesOfCode -= aInner;
			
			aInner -= headersLOCDecrement(t);
			inner += aInner;
		}
		return linesOfCode;
	}

	/**
	 * Verifies if the given index is inside the body declaration of the given
	 * type
	 * 
	 * @param type
	 *            Abstract type, which might be Enum, Class or Interface.
	 * @param index
	 *            Given index
	 * @return
	 */
	private boolean isInScope(ASTNode type, int index) {
		int begin = type.getFileLocation().getNodeOffset(), end = begin + type.getFileLocation().getNodeLength();

		begin += 0;

		return index >= begin && index < end;
	}

	/**
	 * Generates a list of comments which are located within the given entity's
	 * body declaration.
	 * 
	 * @param type
	 *            Given entity which might be Enum, Class or Interface.
	 * @param comments
	 *            List of comments, which includes all valid scope comments
	 */
	private List<IASTComment> getScopeComments(ASTNode type, List<IASTComment> comments) {
		List<IASTComment> comm = new LinkedList<IASTComment>();

		for (IASTComment c : comments) {
			if (isInScope(type, c.getFileLocation().getNodeOffset())) {
				comm.add(c);
			}
		}

		return comm;
	}

	private int headersLOCDecrement(ASTNode type) {

		if(DirectivesProcessor.getPreprocessorList() == null)
			return 0;
		
		int preprocessorLineCount = 0;

		int begin = type.getFileLocation().getNodeOffset();
		int end = begin + type.getFileLocation().getNodeLength();
		
		for (ASTNode preprocessor : DirectivesProcessor.getPreprocessorList()) {
			int beginDirective = preprocessor.getFileLocation().getNodeOffset();
			int endDirective = beginDirective + preprocessor.getFileLocation().getNodeLength();

			if (beginDirective > begin && endDirective < end) {
				preprocessorLineCount += preprocessor.getRawSignature().split("\n").length;
			}
		}

		return preprocessorLineCount;
	}

	private int headersLOCDecrement(List<ASTNode> allDeclarations, ASTNode type) {

		if(DirectivesProcessor.getPreprocessorList() == null)
			return 0;
		
		int preprocessorLineCount = 0;

		for (ASTNode directive : DirectivesProcessor.getPreprocessorList()) {

			int beginDirective = directive.getFileLocation().getNodeOffset();
			int endDirective = beginDirective + directive.getFileLocation().getNodeLength();

			int beginType = type.getFileLocation().getNodeOffset();
			int endType = beginType + type.getFileLocation().getNodeLength();

			if (allDeclarations.size() != 0) {
				if (beginDirective >= beginType
						&& beginDirective < allDeclarations.get(0).getFileLocation().getNodeOffset()) {
					preprocessorLineCount += 1;
				}
			} else {
				if (beginDirective >= beginType && endDirective < endType) {
					preprocessorLineCount += 1;
				}
			}
		}

		// Seleciona as directivas localizadas entre entidades de
		// encapsulamento
		for (int i = 1; i < allDeclarations.size(); i++)
			for (ASTNode directive : DirectivesProcessor.getPreprocessorList()) {

				int endOfPreviousDeclaration = allDeclarations.get(i - 1).getFileLocation().getNodeOffset()
						+ allDeclarations.get(i - 1).getFileLocation().getNodeLength();
				int beginOfCurrentDeclaration = allDeclarations.get(i).getFileLocation().getNodeOffset();
				int beginDirective = directive.getFileLocation().getNodeOffset();

				if (beginDirective >= endOfPreviousDeclaration && beginDirective < beginOfCurrentDeclaration) {
					preprocessorLineCount += 1;
				}
			}

		// Seleciona as directivas localizadas depois de todas as entidades de
		// encapsulamento mas dentro do corpo do arquivo
		for (ASTNode directive : DirectivesProcessor.getPreprocessorList()) {

			int startDirective = directive.getFileLocation().getNodeOffset();

			if (allDeclarations.size() != 0) {
				int endOfPreviousDeclaration = allDeclarations.get(allDeclarations.size() - 1).getFileLocation()
						.getNodeOffset()
						+ allDeclarations.get(allDeclarations.size() - 1).getFileLocation().getNodeLength();
				int endOfFile = type.getOffset() + type.getLength();

				if (startDirective > endOfPreviousDeclaration && startDirective < endOfFile) {
					preprocessorLineCount += 1;
				}
			}
		}

		return preprocessorLineCount;
	}
}