package Vocabulary.vloccount;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTComment;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;

/**
 * Manages physical loc extraction and annotations counting in a manner that constrains such metrics
 * to an specific entity.
 * 
 * @author Tercio de Melo
 */
public class LOCCountPerEntity {
	
	private int loc, annot, inner;
	private List<IASTComment> allComments;

	/**
	 * Default constructor, receives an abstraction of type entity, a comment
	 * @param type
	 * @param comments
	 * @param sourceCode
	 */
	public LOCCountPerEntity(ASTNode type, List<IASTComment> comments, String sourceCode) {
		this.allComments = comments;
		this.inner = 0;
		this.loc = locCount(type, getScopeComments(type, comments), sourceCode);
		//this.annot = (new AnnotationCounter(type, innerLimits(type), comments, sourceCode)).getAnnotationLoc();
		this.loc -= this.annot;
		
	}
	
	/**
	 * Returns the number of java code lines that belong to the given entity body but doesn't belong
	 * to any inner classes.
	 * @return
	 */
	public int getLOC() {
		return loc;
	}
	
	/**
	 * Returns the number of Annotations Line that belong to the given entity body but doesn't belong
	 * to any inner classes. 
	 * @return
	 */
	public int getAnnot() {
		return annot;
	}
	
	/**
	 * Returns the sum of the inner classes' LOC Count per Entity
	 * @return
	 */
	public int getInner() {
		return inner;
	}
	
	/**
	 * Creates an array of indices that delimit the begin and end of each inner class.
	 * @param type The given type which provides the necessary inner entities information.
	 * @return
	 */
	private int[][] innerLimits(ASTNode type) {
		// if it isn't a class or an interface, it has no inner entity
		
		int retorno[][] = new int[type.getLength()][2];
		

		return retorno;
	}
	
	
	/**
	 * This method is responsible for the management of Physical LOC counting, annotations counting and
	 * inner entities Physical LOC subtraction.
	 * @param type The given type to be analyzed.
	 * @param comments A list of selected comments by the getScopeComments() method
	 * @param sourceCode The String representation of the source file containing the given entity
	 * @return The number of Java Code lines contained in the given entity.
	 */
	private int locCount(ASTNode type, List<IASTComment> comments, String sourceCode) {
		/* 
		 * The AST considers the beginning of an Javadoc as the beginning of the given entity, we don't.
		 * So, if there's any javadoc, it's end point will be the entity's beginning point
		 */
		int begin = type.getOffset();
		// The end point is the beginnig point plus the entity's length
		int end = type.getOffset() + type.getLength();
		
		System.out.println(type.getContainingFilename());
		// the temporary loc of the entity is it's physical loc count
		
		int linesOfCode = sourceCode.substring(begin, end).split("\n").length;
		linesOfCode = (new PhysicalLOCCount(comments, sourceCode, linesOfCode, begin, end)).getLOC();

		return linesOfCode;
	}
	
	/**
	 * Verifies if the given index is inside the body declaration of the given type
	 * @param type Abstract type, which might be Enum, Class or Interface.
	 * @param index Given index
	 * @return
	 */
	private boolean isInScope(ASTNode type, int index) {
		int begin = type.getOffset(), end = begin + type.getLength();
		
		return index >= begin && index < end;
	}
	
	/**
	 * Generates a list of comments which are located within the given entity's body declaration.
	 * @param type Given entity which might be Enum, Class or Interface.
	 * @param comments List of comments, which includes all valid scope comments
	 * @return
	 */
	private List<IASTComment> getScopeComments(ASTNode type, List<IASTComment> comments) {
		List<IASTComment> comm = new LinkedList<IASTComment>();
		
		for(IASTComment c : comments) {
			ASTNode comment = (ASTNode) c;
			
			if(isInScope(type, comment.getOffset())) {
				comm.add(c);
			}
		}

		return comm;
	}
}
