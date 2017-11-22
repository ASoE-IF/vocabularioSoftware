package org.splab.vocabulary.extractor.processors;

import java.util.List;

import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.splab.vocabulary.extractor.util.LOCManager;
import org.splab.vocabulary.extractor.util.VxlManager;
import org.splab.vocabulary.extractor.vloccount.EntityLOCKeeper;
import org.splab.vocabulary.extractor.vloccount.EntityType;
import org.splab.vocabulary.extractor.vloccount.LOCCountPerEntity;
import org.splab.vocabulary.extractor.vloccount.LOCParameters;

public class EnumProcessor {
	private StringBuffer vxlFragment;
	private int enumLoc;
	
	@SuppressWarnings("unchecked")
	public EnumProcessor(String assignment, EnumDeclaration anEnum, boolean isInner, int headerLines) {
		String enumName = assignment + anEnum.getName().getIdentifier();
		String enumJavadoc = anEnum.getJavadoc() == null ? "" : StringProcessor.processString(anEnum.getJavadoc().toString());
		String enumVisibility = visibility(anEnum);

		LOCCountPerEntity locCounter = new LOCCountPerEntity(anEnum, CompilationUnitProcessor.commentList, CompilationUnitProcessor.sourceCode);
		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter, isInner);
		locKeeper.setHeadersLOC(headerLines, isInner, LOCManager.locParameters.contains(LOCParameters.HEADERS));

		boolean inner = (isInner ? LOCManager.locParameters.contains(LOCParameters.INNER_CLASSES) : true);
		
		if(LOCManager.locParameters.contains(LOCParameters.LOC) && inner) {
			if(isInner) {
				enumLoc = locKeeper.getLOC() + locKeeper.getAnnotationsLOC() + locKeeper.getInnerEntitiesLOC();
			} else {
				enumLoc = 0;
				LOCManager.appendEntityLOCData(enumName, locKeeper, EntityType.ENUM);
			}
		}
		final String NO_COMMENT = ""; // enum constants don't have javadoc associations

		vxlFragment = new StringBuffer();
		vxlFragment.append(VxlManager.startEnum(enumName, enumJavadoc, locKeeper.getLOC()));
		vxlFragment.append((new CommentsProcessor(anEnum)).getVxlFragment());

		for (EnumConstantDeclaration enumConstant : (List<EnumConstantDeclaration>) anEnum.enumConstants()) {
			String constantName = enumConstant.getName().getIdentifier();
			vxlFragment.append(VxlManager.constant(constantName, enumVisibility, NO_COMMENT));
		}
				
		vxlFragment.append(VxlManager.endEnum());
	}
	
	/**
	 * Returns the visibility from an entity in a String
	 * @param entityDeclaration AST representation of any structure which possesses a body
	 * @return
	 */
	private static String visibility(BodyDeclaration entityDeclaration) {
		int mthMod = entityDeclaration.getModifiers();	
		return Modifier.isPrivate(mthMod) ? "priv" : Modifier.isProtected(mthMod) ? "prot" : "pub";
	}
	
	public int getEnumLoc() {
		return enumLoc;
	}
	
	public StringBuffer getVxlFragment() {
		return vxlFragment;
	}
	
	@Override
	public String toString() {
		return vxlFragment.toString();
	}
}