package org.splab.vocabulary.extractor.processors;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.splab.vocabulary.extractor.util.CommentUnit;
import org.splab.vocabulary.extractor.util.LOCManager;
import org.splab.vocabulary.extractor.util.VxlManager;
import org.splab.vocabulary.extractor.vloccount.EntityLOCKeeper;
import org.splab.vocabulary.extractor.vloccount.EntityType;
import org.splab.vocabulary.extractor.vloccount.LOCCountPerEntity;
import org.splab.vocabulary.extractor.vloccount.LOCParameters;

public class ClassProcessor {
	protected static List<CommentUnit> sourceCodeComments;
	private StringBuffer vxlFragment;
	
	public ClassProcessor() {
		vxlFragment = new StringBuffer("");
	}
	
	@SuppressWarnings("unchecked")
	public ClassProcessor(String assignment, TypeDeclaration type, boolean isInner, int headerLines) {
		vxlFragment = new StringBuffer();
		sourceCodeComments = new LinkedList<CommentUnit>();
		String typeName = assignment + type.getName().getIdentifier();
		String typeJavadoc = type.getJavadoc() == null ? "" : StringProcessor.processString(type.getJavadoc().toString());
		String isInterface = type.isInterface() ? "y" : "n";
		String isInnerType = (isInner) ? "y" : "n";
		
		int typeModifiers = type.getModifiers();
		String isAbstract = (Modifier.isAbstract(typeModifiers)) ? "y" : "n";
		
		Integer enumLoc = 0;
		// adding enum info
		for (BodyDeclaration bodyDecl : (List<BodyDeclaration>) (((TypeDeclaration) type).bodyDeclarations())) {
			if (bodyDecl instanceof EnumDeclaration) {
				EnumProcessor enumProcessor = new EnumProcessor(typeName + "$", (EnumDeclaration) bodyDecl, true, 0);
				enumLoc += enumProcessor.getEnumLoc();
				vxlFragment.append(enumProcessor.toString());
			}
		}
		
		LOCCountPerEntity locCounter = new LOCCountPerEntity(type, CompilationUnitProcessor.commentList, CompilationUnitProcessor.sourceCode);
		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter, isInner);
		locKeeper.setHeadersLOC(headerLines, isInner, LOCManager.locParameters.contains(LOCParameters.HEADERS));
		locKeeper.increaseLOC(enumLoc);
		
		EntityType saveType = type.isInterface() ? EntityType.INTERFACE : isInner ? EntityType.INNER_CLASS : EntityType.CLASS;
		
		vxlFragment.append(VxlManager.startClass(typeName, isInterface, isAbstract, isInnerType, typeJavadoc, locKeeper.getLOC()));
		
		vxlFragment.append((new CommentsProcessor(type).getVxlFragment()));
		
		for (FieldDeclaration field : ((TypeDeclaration) type).getFields()) {
			FieldProcessor aField = new FieldProcessor(field);
			vxlFragment.append(aField.getVxlFragment());
		}
		
		for (MethodDeclaration method : ((TypeDeclaration) type).getMethods()) {
			MethodProcessor mth = new MethodProcessor(method, typeName + ".");
			vxlFragment.append(mth.getVxlFragment());
		}
		
		if(LOCManager.locParameters.contains(LOCParameters.LOC) &&
				(isInner ? LOCManager.locParameters.contains(LOCParameters.INNER_CLASSES) : true)) {
			LOCManager.appendEntityLOCData(typeName, locKeeper, saveType);
		}
		
		for (TypeDeclaration innerType : type.getTypes()) {
			vxlFragment.append((new ClassProcessor(typeName + "$", innerType, true, 0)).getVxlFragment());
		}
		
		vxlFragment.append(VxlManager.endClass());
	}
	
	public StringBuffer getVxlFragment() {
		return vxlFragment;
	}
		
	@Override
	public String toString() {
		return vxlFragment.toString();
	}
}