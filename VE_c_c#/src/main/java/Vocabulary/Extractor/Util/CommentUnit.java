package Vocabulary.Extractor.Util;

import org.eclipse.cdt.core.dom.ast.IASTFileLocation;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;

import Vocabulary.Processors.StringProcessor;
/**
 * Stores useful information of a Comment such as it's contents, start and end position in a given source code
 * @author Tercio de Melo
 *
 */
public class CommentUnit {
	private String content;
	private ASTNode commentNode;
	
	/**
	 * Uses an ASTNode subclass Comment object and a sourceCode to extract useful information
	 * @param commentNode Comment which stores start position and length of a comment the source code
	 * @param sourceCode String which stores the source code to be parsed
	 */
	public CommentUnit(ASTNode commentNode, String sourceCode) {
		this.commentNode = commentNode;
		extractValue(sourceCode);
	}
	
	private void extractValue(String sourceCode) {
		IASTFileLocation location = commentNode.getFileLocation();
		int begin = location.getNodeOffset();
		int end = begin + location.getNodeLength();
	
		content = StringProcessor.processString(sourceCode.substring(begin, end));
	}
	
	/**
	 * Returns the start point in the source code
	 * @return
	 */
	public int getStartPosition() {
		return commentNode.getFileLocation().getNodeOffset();
	}
	
	/**
	 * Returns the end point in the source code
	 * @return
	 */
	public int getEndPosition() {
		return commentNode.getFileLocation().getNodeOffset() + commentNode.getFileLocation().getNodeLength();
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