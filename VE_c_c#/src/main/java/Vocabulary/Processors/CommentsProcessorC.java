package Vocabulary.Processors;

import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTFileLocation;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;

import Vocabulary.Extractor.Util.CommentUnitC;
import Vocabulary.Extractor.Util.VxlManager;

public class CommentsProcessorC
{
	private StringBuffer vxlFragment;
	
	public CommentsProcessorC(ASTNode[] allDeclarations, ASTNode type, boolean function)
	{
		vxlFragment = new StringBuffer();

		List<CommentUnitC> sourceCodeComments = CompilationUnitProcessor.sourceCodeComments;
		
		// Seleciona os comentarios localizados antes de qualquer entidade de encapsulamento
		
		IASTFileLocation location = type.getFileLocation();
		
		for(CommentUnitC c : sourceCodeComments)
			if(allDeclarations.length != 0)
			{
				if(c.getStartPosition() >= location.getNodeOffset()  && c.getStartPosition() < allDeclarations[0].getFileLocation().getNodeOffset())
				{
					vxlFragment.append(VxlManager.commentTag(c, function));
					FileProcessor.sourceCodeComments.add(c);
				}
			}
			else
			{
				if(c.getStartPosition() >= location.getNodeOffset() && c.getEndPosition() < location.getNodeOffset() + location.getNodeLength())
				{
					vxlFragment.append(VxlManager.commentTag(c, function));
					FileProcessor.sourceCodeComments.add(c);
				}
			}
		
		// Seleciona os comentarios localizados entre entidades de encapsulamento
		for (int i = 1; i < allDeclarations.length; i++)
			for(CommentUnitC c : sourceCodeComments)
			{
				int endOfPreviousDeclaration = allDeclarations[i-1].getFileLocation().getNodeOffset() + allDeclarations[i-1].getFileLocation().getNodeLength();
				int beginOfCurrentDeclaration = allDeclarations[i].getFileLocation().getNodeOffset();
				if(c.getStartPosition() >= endOfPreviousDeclaration && c.getStartPosition() < beginOfCurrentDeclaration) {
					vxlFragment.append(VxlManager.commentTag(c, function));
					FileProcessor.sourceCodeComments.add(c);
				}
			}
		
		// Seleciona os comentarios localizados depois de todas as entidades de encapsulamento mas dentro do corpo da classe
		for(CommentUnitC c : sourceCodeComments)
			if(allDeclarations.length != 0)
			{
				int endOfPreviousDeclaration = allDeclarations[allDeclarations.length-1].getFileLocation().getNodeOffset() + allDeclarations[allDeclarations.length-1].getFileLocation().getNodeLength();
				int endOfFile = location.getNodeOffset() + location.getNodeLength();
				if(c.getStartPosition() > endOfPreviousDeclaration && c.getStartPosition() < endOfFile){
					vxlFragment.append(VxlManager.commentTag(c, function));
					FileProcessor.sourceCodeComments.add(c);
				}
			}
	}
	
	public CommentsProcessorC(ASTNode declaration)
	{
		vxlFragment = new StringBuffer();
		
		for(CommentUnitC c : CompilationUnitProcessor.sourceCodeComments)
			if(c.getStartPosition() > declaration.getFileLocation().getNodeOffset()
					&& c.getStartPosition() < (declaration.getFileLocation().getNodeOffset() + declaration.getFileLocation().getNodeLength()))
				vxlFragment.append(VxlManager.commentTag(c, false));
	}
	/**
	 * Extracts every comment that belongs to a given method
	 * @param method
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