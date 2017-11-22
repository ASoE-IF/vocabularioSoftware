package org.splab.vocabulary.extractor.processors;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.splab.vocabulary.extractor.util.CommentUnit;
import org.splab.vocabulary.extractor.util.VxlManager;

public class CommentsProcessor {
	private StringBuffer vxlFragment;
	
	public CommentsProcessor(TypeDeclaration type) {
		MethodDeclaration[] allMethods = type.getMethods();
		vxlFragment = new StringBuffer();
		List<CommentUnit> sourceCodeComments = CompilationUnitProcessor.sourceCodeComments;
		
		// Seleciona os comentarios localizados antes de qualquer entidade de encapsulamento
		for(CommentUnit c : sourceCodeComments)
			if(notInInnerTypes(c.getStartPosition(), type) && allMethods.length != 0)
				if(c.getStartPosition() > type.getStartPosition()  && c.getStartPosition() < allMethods[0].getStartPosition()) {
					vxlFragment.append(VxlManager.commentTag(c));
					ClassProcessor.sourceCodeComments.add(c);
				}
		
		// Seleciona os comentarios localizados entre entidades de encapsulamento
		for (int i = 1; i < allMethods.length; i++)
			for(CommentUnit c : sourceCodeComments)
				if(notInInnerTypes(c.getStartPosition(), type)) {
					int endOfPreviousMethod = allMethods[i-1].getStartPosition() + allMethods[i-1].getLength();
					int beginOfCurrentMethod = allMethods[i].getStartPosition();
						
					if(c.getStartPosition() > endOfPreviousMethod && c.getStartPosition() < beginOfCurrentMethod) {
						vxlFragment.append(VxlManager.commentTag(c));
						ClassProcessor.sourceCodeComments.add(c);
					}
				}
		
		// Seleciona os comentarios localizados depois de todas as entidades de encapsulamento mas dentro do corpo da classe
		for(CommentUnit c : sourceCodeComments)
			if(notInInnerTypes(c.getStartPosition(), type) && allMethods.length != 0) {
				int endOfPreviousMethod = allMethods[allMethods.length-1].getStartPosition() + allMethods[allMethods.length-1].getLength();
				int endOfClass = type.getStartPosition() + type.getLength();
			
				if(c.getStartPosition() > endOfPreviousMethod && c.getStartPosition() < endOfClass) {
					vxlFragment.append(VxlManager.commentTag(c));
					ClassProcessor.sourceCodeComments.add(c);
				}
			}
	}
	
	public CommentsProcessor(EnumDeclaration enumDeclaration) {
		vxlFragment = new StringBuffer();
		
		for(CommentUnit c : CompilationUnitProcessor.sourceCodeComments)
			if(c.getStartPosition() > enumDeclaration.getStartPosition()
					&& c.getStartPosition() < (enumDeclaration.getStartPosition() + enumDeclaration.getLength()))
				vxlFragment.append(VxlManager.commentTag(c));
	}
	/**
	 * Extracts every comment that belongs to a given method
	 * @param method
	 * @return
	 */
	protected static List<String> getMethodDeclarationComments(MethodDeclaration method) {
		List<String> comment = new LinkedList<String>();
		
		for(CommentUnit c : CompilationUnitProcessor.sourceCodeComments)
			if(c.getStartPosition() > method.getStartPosition()
					&& c.getStartPosition() < (method.getStartPosition() + method.getLength()))
				comment.add(c.getComment());
		return comment;
	}
	
	/**
	 * Checks if the given comment start point is not in a inner class
	 * @param commentStartPoint
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected boolean notInInnerTypes(int commentStartPoint, TypeDeclaration type) {
		for(TypeDeclaration t : type.getTypes())
			if(commentStartPoint > t.getStartPosition() && commentStartPoint < (t.getStartPosition() + t.getLength()))
				return false;
		
		for (BodyDeclaration t : (List<BodyDeclaration>) (((TypeDeclaration) type).bodyDeclarations())) {
			if (t instanceof EnumDeclaration) {
				if(commentStartPoint > t.getStartPosition() && commentStartPoint < (t.getStartPosition() + t.getLength()))
					return false;
			}
		}
		return true;
	}
	
	public StringBuffer getVxlFragment() {
		return vxlFragment;
	}
	
	@Override
	public String toString() {
		return vxlFragment.toString();
	}
}