package org.splab.vocabulary.extractor.vloccount;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Comment;

/**
 * Algorithm to parse a source code and compute how many lines framework annotations of pieces of them
 * occupies.
 * 
 * @author Tercio de Melo
 */
public class AnnotationCounter {
	
	private int annotCnt;
	private int[][] limits;
	private char[] source;
	private List<Comment> comments;
	private List<AnnotationLimit> annotations;
	private String sourceCode;
	private AbstractTypeDeclaration type;
	
	/**
	 * Default class constructor in which receives as parameter the entity's abstractions which contains informations
	 * like it's range and others, as parameter there is also and array of limits representing inner entities range,
	 * a list of comments, and the source code kept in a String
	 * 
	 * @param type is a TypeDeclaration, comes from the analysis of the Eclipse API for building AST and represents
	 * classes or interfaces
	 * @param limits is a bidimensional arrays of int with inner entities delimiters
	 * @param comments is a list of comments delimiters
	 * @param sourceCode the source code
	 */
	public AnnotationCounter(AbstractTypeDeclaration type, int[][] limits, List<Comment> comments, String sourceCode) {
		this.type = type; this.limits = limits; this.comments = comments; this.sourceCode = sourceCode;
		this.source = sourceCode.toCharArray();
		this.annotations = new LinkedList<AnnotationLimit>();
		annotCnt = annotationCounter();
		
	}
	
	/**
	 * Returns the Annotation Total LOC
	 * @return
	 */
	public int getAnnotationLoc() {
		return annotCnt;
	}
	
	private boolean isIdentifierPart(char character) {
		String letters = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0123456789";
		return letters.contains(String.format("%c", character));
	}
	
	/**
	 * Computes the extension of the annotation which begins in the index given as the method's parameter
	 * @param index the beginning location of current analyzed annotation. 
	 * @return
	 */
	private int annotationLength(int index) {
		if(isInsideAnnotation(index)) return 0;
		
		int loc = 0, open = 0, close = 0, i;
		boolean inString = false;
		
		for(i = index+1; ; i++) {
			if(source[i] == '\"') inString = !inString;

			if(!isIdentifierPart(source[i]) && open == 0 && nextValidCharacter(i) != '(') {
				return isAfterBlank(index) && isBeforeBlank(i) ? 1 : 0;
			}
			else if(source[i] == '(' && !inString) open++;
			else if(source[i] == ')' && !inString) close++;
			else if(source[i] == '\n') loc++;
			
			if(open - close == 0 && open != 0) break;
		}
		
		annotations.add(new AnnotationLimit(index, i));
		if(isAfterBlank(index)) loc++;
		if(isBeforeBlank(i)) loc++;
		
		return loc;
	}
	
	/**
	 * Verifies if the given location is contained in the scope of any annotation.
	 * @param index
	 * @return
	 */
	private boolean isInsideAnnotation(int index) {
		if(annotations == null) return false;
		for(AnnotationLimit a : annotations)
			if(index > a.begin && index < a.end) {
				return true;
			}
		return false;
	}
	
	/**
	 * Performs a forward search for the next valid character in the source code from the indicated location.
	 * A valid character is defined as not being a blank space, a new line character or a tab character.
	 * @param index the starting point for the search
	 * @return
	 */
	private char nextValidCharacter(int index) {
		while(source[index] == ' ' || source[index] == '\t' || source[index] == '\n') index++;
		return source[index];
	}
	
	/**
	 * Verifies if from the given point until the end of the line there is only invalid characters
	 * @param index
	 * @return
	 */
	private boolean isBeforeBlank(int index) {
		for(int i = index; source[i] != '\n' ; i++)
			if(source[i] != ' ' && source[i] != '\t')
				return false;
		return true;
	}
	
	/**
	 * Verifies if from a given point until the beginning of the current line there is only invalid characters
	 * @param index
	 * @return
	 */
	private boolean isAfterBlank(int index) {
		for(int i = index-1; source[i] != '\n' && i >= 0; i--)
			if(source[i] != ' ' && source[i] != '\t')
				return false;
		return true;
	}
	
	/**
	 * Manages the annotation after annotation analysis
	 * @return
	 */
	private int annotationCounter() {
		int counter = 0;
		boolean inString = false;
		
		for(int i = 0; i < sourceCode.length(); i++) {
			if(source[i] == '\"') inString = !inString;
			if(inString) continue;
			if(source[i] == '@' && isInTypeScope(i))
				counter += annotationLength(i);
		}
		
		return counter;
	}
	
	/**
	 * Verifies if the given location is inside the body of the current analyzed entity
	 * @param index 
	 * @return
	 */
	private boolean isInTypeScope(int index) {
		int begin = type.getStartPosition(), end = begin + type.getLength();
		return !isInInnerEntity(index) && !isInComment(index) && index >= begin && index < end;
	}
	
	
	/**
	 * Verifies if the given location is inside the body of any inner entity
	 * @param index
	 * @return
	 */
	private boolean isInInnerEntity(int index) {
		for(int i = 0; i < limits.length; i++)
			if(index >= limits[i][0] && index < limits[i][1])
				return true;
		return false;
	}
	
	/**
	 * Verifies if the given location is inside the body of any comment that is inner the current entity
	 * @param index
	 * @return
	 */
	private boolean isInComment(int index) {
		for(Comment c : comments) {
			int begin = c.getStartPosition(), end = begin + c.getLength();
			if(index > begin && index < end)
				return true;
		}
		
		return false;
	}
	
	/**
	 * Abstraction of annotation delimiters
	 * @author Tercio de Melo
	 */
	private class AnnotationLimit {
		private int begin, end;
		
		/**
		 * Default constructor which specifies the range of the annotation
		 * @param begin begin
		 * @param end end
		 */
		public AnnotationLimit(int begin, int end)
			{ this.begin = begin; this.end = end; }
	}
}
