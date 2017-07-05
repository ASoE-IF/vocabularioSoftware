package Vocabulary.Processors;

import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTFileLocation;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;

import Vocabulary.Extractor.Util.CommentUnit;
import Vocabulary.Extractor.Util.VxlManager;

public class CommentsProcessor
{
	private StringBuffer vxlFragment;
	
	public CommentsProcessor(List<ASTNode> allDeclarations, ASTNode type, boolean function)
	{
		vxlFragment = new StringBuffer();
		List<CommentUnit> sourceCodeComments = CompilationUnitProcessor.sourceCodeComments;
		
		for(CommentUnit c : sourceCodeComments)
			if(allDeclarations.size() != 0)
			{
				if(c.getStartPosition() >= type.getOffset()  && c.getStartPosition() < allDeclarations.get(0).getFileLocation().getNodeOffset())
				{
					vxlFragment.append(VxlManager.commentTag(c, function));
					FileProcessor.sourceCodeComments.add(c);
				}
			}
			else
			{
				if(c.getStartPosition() >= type.getOffset() && c.getEndPosition() < type.getOffset() + type.getLength())
				{
					vxlFragment.append(VxlManager.commentTag(c, function));
					FileProcessor.sourceCodeComments.add(c);
				}
			}
		
		// Seleciona os comentarios localizados entre entidades de encapsulamento
		for (int i = 1; i < allDeclarations.size(); i++)
			for(CommentUnit c : sourceCodeComments)
			{
				int endOfPreviousDeclaration = allDeclarations.get(i-1).getFileLocation().getNodeOffset() + allDeclarations.get(i-1).getFileLocation().getNodeLength();
				int beginOfCurrentDeclaration = allDeclarations.get(i).getFileLocation().getNodeOffset();
				
				if(c.getStartPosition() >= endOfPreviousDeclaration && c.getStartPosition() < beginOfCurrentDeclaration)
				{
					vxlFragment.append(VxlManager.commentTag(c, function));
					FileProcessor.sourceCodeComments.add(c);
				}
			}
		
		// Seleciona os comentarios localizados depois de todas as entidades de encapsulamento mas dentro do corpo da classe
		for(CommentUnit c : sourceCodeComments)
			if(allDeclarations.size() != 0)
			{
				int endOfPreviousDeclaration = allDeclarations.get(allDeclarations.size()-1).getFileLocation().getNodeOffset() + allDeclarations.get(allDeclarations.size() - 1).getFileLocation().getNodeLength();
				int endOfFile = type.getOffset() + type.getLength();
				
				if(c.getStartPosition() > endOfPreviousDeclaration && c.getStartPosition() < endOfFile)
				{
					vxlFragment.append(VxlManager.commentTag(c, function));
					FileProcessor.sourceCodeComments.add(c);
				}
			}
	}
	
	public CommentsProcessor(ASTNode declaration)
	{
		vxlFragment = new StringBuffer();
		
		for(CommentUnit c : CompilationUnitProcessor.sourceCodeComments)
			if(c.getStartPosition() > declaration.getFileLocation().getNodeOffset()
					&& c.getStartPosition() < (declaration.getFileLocation().getNodeOffset() + declaration.getFileLocation().getNodeLength()))
				vxlFragment.append(VxlManager.commentTag(c, false));
	}
	
	public StringBuffer getVxlFragment()
	{
		return vxlFragment;
	}
	
	@Override
	public String toString()
	{
		return vxlFragment.toString();
	}
}