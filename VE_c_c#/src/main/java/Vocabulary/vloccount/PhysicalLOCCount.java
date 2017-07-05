package Vocabulary.vloccount;

import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTComment;
import org.eclipse.cdt.core.dom.ast.IASTFileLocation;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
/**
 * Computes the number of physical lines of java code is there in a given scope
 * @author Tercio de Melo
 */
public class PhysicalLOCCount {
	
	/**
	 * has the source code of a java file
	 */
	private char[] sourceCode;
	
	/**
	 * <b>loc</b> represents the number of physical lines of java code
	 * <b>start</b> marks the begin of the analyzed scope
	 * <b>finall<b> marks the end of the analyzed scope
	 */
	private int loc, start, finall;
	
	/**
	 * Class constructor in which no scope is given. Thus, all the java file will
	 * be analyzed
	 * @param comments is a list of comments delimiters
	 * @param sourceCode is the entire source code of a java file
	 * @param linesOfCode is the number of written lines in the source code,
	 * note that it includes empty lines and comment lines as well.
	 */
	public PhysicalLOCCount(List<IASTComment> comments, String sourceCode, int linesOfCode) {
		this(comments, sourceCode, linesOfCode, 0, sourceCode.length());
	}
	
	/**
	 * Class constructor in which the scope of analysis is specified, is useful when the goal
	 * of the loc analysis is a specific entity inside the source code and the delimiters of this
	 * entity is known.
	 * @param comments is a list of comments delimiters
	 * @param sourceCode is the entire source code of a java file
	 * @param linesOfCode is the number of written lines in the source code, including
	 * empty and comments lines
	 * @param start is the begin delimiter of the analisis
	 * @param finall is the end delimiter of the analisis
	 */
	public PhysicalLOCCount(List<IASTComment> comments, String sourceCode, int linesOfCode, int start, int finall) {
		this.loc = linesOfCode;
		this.sourceCode = sourceCode.toCharArray();
		this.loc -= allCommentsLOC(comments);
		// Usado para debug
		this.start = start;
		this.finall = finall;
		this.loc -= numbeOfEmptyLines(sourceCode, comments);
	}
	
	/**
	 * Returns the number of Java Code Lines
	 * @return
	 */
	public int getLOC() {
		return loc;
	}
	
	/**
	 * Manages the line-by-line analysis for empty lines of the source Code
	 * @param sourceCode the entire source code
	 * @return
	 */
	private int numbeOfEmptyLines(String sourceCode, List<IASTComment> comments) {
		int emptyLines = 0;
		String scopo = sourceCode.substring(start, finall);
		
		for(String line : scopo.split("\n")) {
			if(isEmptyLine(line)) emptyLines++;
		}
		
		return emptyLines;
	}
	
	/**
	 * Verifies if a given line of code abstracted as a String is empty
	 * In this case, empty is understood by containing no other characters
	 * than blank spaces, tabs and form feed
	 * @param line
	 * @return
	 */
	private boolean isEmptyLine(String line) {
		for(char c : line.toCharArray())
			if(c != ' ' && c != '\t' && c != '\f')
				return false;
		return true;
	}
	
	/**
	 * Manages the comment-by-comment computation of the whole extension of 
	 * comments in the code
	 * @param commentList a list of comments delimiters
	 * @return
	 */
	private int allCommentsLOC(List<IASTComment> commentList) {
		
		int loc = 0;
		for(IASTComment c : commentList) {
			loc += commentLOC(c);
		}
				
		
		return loc;
	}
	
	/**
	 * Does a forward search for physical code from a given location until the end of the line
	 * @param begin index of character in the source code
	 * @return
	 */
	private boolean isAfterValidCode(int begin) {
		for(int i = begin-1; i >= 0 && sourceCode[i] != '\n'; i--)
			if(sourceCode[i] != ' ' && sourceCode[i] != '\t')
				return true;
		return false;
	}
	
	/**
	 * Does a backward search for physical code from a given location until the begin of the line
	 * @param end
	 * @return
	 */
	private boolean isBeforeValidCode(int end) {
		for(int i = end; i < sourceCode.length && sourceCode[i] != '\n'; i++) {
			if(sourceCode[i] == '/') return false;
			if(sourceCode[i] != ' ' && sourceCode[i] != '\t')
				return true;
		}
		return false;
	} 
	
	/**
	 * Computes the LOC of a given comment
	 * @param comment
	 * @return
	 */
	private int commentLOC(IASTComment comment) {
		ASTNode commentAST = (ASTNode)comment;
		
		IASTFileLocation location = comment.getFileLocation();

		int begin = location.getNodeOffset();
		int end = location.getNodeOffset() + location.getNodeLength();
		
		
		String currentComment = (new String(sourceCode)).substring(begin, end);

		String[] commentLines = currentComment.split("\n");
		int loc = commentLines.length;

		// Retirando as linhas em branco presentes
		// num comentário de bloco
		for(String line : commentLines) {
			if(isEmptyLine(line)) {
				loc--;
			}
		}

		
		if(isAfterValidCode(begin)) {
			loc--;
		}
		
		if(comment.isBlockComment()) {
			return loc;
		}
		
		if(isBeforeValidCode(end)) {
			 // Testa se o comentario eh de bloco e ocupa uma unica linha e esta depois de codigo valido
			 // para evitar que comentarios do tipo
			 // 
			 // codigoQualquer(tipo param, /*Comentario*/)
			 //
			 // sejam contabilizados como contendo -1 linhas
			if(commentLines.length == 1) {
				if(!isAfterValidCode(begin) && loc > 0) {
					// Essa situacao acontece quando temos codigo valido apos o comentario:  /* comment */ codigo valido
					loc--;
				}
			} else if(loc > 0) {
				loc--;
			}
		}
		 
		return loc;
	}
}
