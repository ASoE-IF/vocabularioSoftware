package org.splabs.vocabulary.vxl.extractor;

import org.eclipse.jdt.core.dom.Comment;

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
		
		content = processComment(sourceCode.substring(begin, end));
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
	
	
	/**
	 * Processes the comment, removing undesirable characters and returning the extracted content
	 * @param comment the comment with undesirable characters
	 * @return
	 */
	public static String processComment(String comment) {
		
		comment = removeInvalidCharacters(comment);
		

		while (comment.contains("/"))
			comment = comment.replace('/', ' ');
		
		while (comment.contains("*"))
			comment = comment.replace('*', ' ');
		
		while (comment.contains("\n"))
			comment = comment.replace('\n', ' ');
		
		while (comment.contains("\""))
			comment = comment.replace('\"', '\'');
		
		while (comment.contains("<"))
			comment = comment.replace('<', '[');
		
		while (comment.contains(">"))
			comment = comment.replace('>', ']');
		
		while (comment.contains("&"))
			comment = comment.replace('&', '^');
		
		
		comment = comment.trim();
		comment = comment.replaceAll("\\s+", " ");
		
		return comment;
	}
	
	/**
	 * Removes invalid characters for UTF-8 encoding
	 * @param text
	 * @return
	 */
	public static String removeInvalidCharacters(String text) {
		
		StringBuffer out = new StringBuffer(); // Used to hold the output.
        char current; // Used to reference the current character.

        if (text == null || ("".equals(text))) return ""; // vacancy test.
        for (int i = 0; i < text.length(); i++) {
            current = text.charAt(i); // NOTE: No IndexOutOfBoundsException caught here; it should not happen.
            if ((current == 0x9) ||
                (current == 0xA) ||
                (current == 0xD) ||
                ((current >= 0x20) && (current <= 0xD7FF)) ||
                ((current >= 0xE000) && (current <= 0xFFFD)) ||
                ((current >= 0x10000) && (current <= 0x10FFFF)))
                out.append(current);
        }
        return out.toString();
	}
}