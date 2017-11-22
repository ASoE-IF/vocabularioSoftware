package org.splab.vocabulary.extractor.processors;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.splab.vocabulary.extractor.util.CommentUnit;
import org.splab.vocabulary.extractor.util.LOCManager;
import org.splab.vocabulary.extractor.vloccount.LOCParameters;
import org.splab.vocabulary.extractor.vloccount.PhysicalLOCCount;

public class CompilationUnitProcessor {
	protected static List<Comment> commentList;
	protected static String sourceCode;
	protected static List<CommentUnit> sourceCodeComments;
	private StringBuffer vxlFragment;
	
	@SuppressWarnings("unchecked")
	public CompilationUnitProcessor(CompilationUnit compilationUnit, String fileName, String packName) {
		vxlFragment = new StringBuffer();
		
		int pack = compilationUnit.getPackage() == null ? 0 : 1;
		commentList = compilationUnit.getCommentList();
		
		if(LOCManager.locParameters.contains(LOCParameters.ONLY_PHYSICAL_LINES)) {
			if(fileName != null) {
				PhysicalLOCCount locCount = new PhysicalLOCCount(commentList, sourceCode, sourceCode.split("\n").length);
				LOCManager.appendCompilationUnitLOCData(fileName, locCount);
			}
		}
		
		int primeiraClasse = 0;
		
		for (AbstractTypeDeclaration type : (List<AbstractTypeDeclaration>) compilationUnit.types()) {
			
			if (type instanceof TypeDeclaration) {
				ClassProcessor classProcessor = new ClassProcessor(packName + ".", (TypeDeclaration) type, false,
						(primeiraClasse == 0 ? compilationUnit.imports().size() + pack : 0));
				primeiraClasse++;
				vxlFragment.append(classProcessor.getVxlFragment());
			} else if (type instanceof EnumDeclaration) {
				boolean innerEnum = !((EnumDeclaration)type).isPackageMemberTypeDeclaration();
				EnumProcessor enumProcessor = new EnumProcessor(packName + ".", (EnumDeclaration) type, innerEnum,
						!innerEnum ? compilationUnit.imports().size() + 1 : 0);
				vxlFragment.append(enumProcessor.getVxlFragment());
			}
		}
	}
	
	public static void reset() {
		sourceCodeComments = new LinkedList<CommentUnit>();
		commentList = new LinkedList<Comment>();
	}
	
	public static String getSourceCode() {
		return sourceCode;
	}
	
	public static void setSourceCode(String source) {
		sourceCode = source;
	}
	
	public static void addCommentUnit(CommentUnit comment) {
		sourceCodeComments.add(comment);
	}
	
	public StringBuffer getVxlFragment() {
		return vxlFragment;
	}
	
	@Override
	public String toString() {
		return vxlFragment.toString();
	}
}