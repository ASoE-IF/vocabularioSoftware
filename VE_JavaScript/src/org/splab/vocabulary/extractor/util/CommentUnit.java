package org.splab.vocabulary.extractor.util;

import org.eclipse.jdt.core.dom.Comment;
import org.splab.vocabulary.extractor.processors.StringProcessor;

/**
 * Stores useful information of a Comment such as it's contents, start and end position in a given source code
 * @author Tercio de Melo
 *
 */
public class CommentUnit {
	private String content;
	private Comment commentNode;
	
	/**
	 * Uses an ASTNode subclass Comment object and a sourceCode to extract useful information
	 * @param commentNode Comment which stores start position and length of a comment the source code
	 * @param sourceCode String which stores the source code to be parsed
	 */
	public CommentUnit(Comment commentNode, String sourceCode) {
		this.commentNode = commentNode;
		extractValue(sourceCode);
	}
	
	private void extractValue(String sourceCode) {
		int begin = commentNode.getStartPosition();
		int end = begin + commentNode.getLength();
		
		content = StringProcessor.processString(sourceCode.substring(begin, end));
	}
	
	/**
	 * Returns the start point in the source code
	 * @return
	 */
	public int getStartPosition() {
		return commentNode.getStartPosition();
	}
	
	/**
	 * Returns the end point in the source code
	 * @return
	 */
	public int getEndPosition() {
		return commentNode.getStartPosition() + commentNode.getLength();
	}
	
	/**
	 * Returns the extracted content of the comment
	 * @return
	 */
	public String getComment() {
		return content;
	}
	
	@Override
	public String toString() {
		return content;
	}
}