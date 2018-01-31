package org.splab.vocabulary.extractor.processors;

import java.util.LinkedList;

import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTEnumerationSpecifier;
import org.splab.vocabulary.extractor.util.LOCManager;
import org.splab.vocabulary.extractor.util.VxlManager;
import org.splab.vocabulary.extractor.vloccount.EntityLOCKeeper;
import org.splab.vocabulary.extractor.vloccount.EntityType;
import org.splab.vocabulary.extractor.vloccount.LOCCountPerEntity;
import org.splab.vocabulary.extractor.vloccount.LOCParameters;


public class EnumProcessor
{
	private StringBuffer vxlFragment;

	public EnumProcessor(CASTEnumerationSpecifier enumDeclaration, boolean scope, boolean isInner, int headerLines) {
		vxlFragment = new StringBuffer();

		LOCCountPerEntity locCounter = new LOCCountPerEntity((ASTNode) enumDeclaration,
				CompilationUnitProcessor.commentList, CompilationUnitProcessor.sourceCode, new LinkedList<ASTNode>(),
				0);
		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter, isInner);

		boolean inner = (isInner ? LOCManager.locParameters.contains(LOCParameters.INNER_FUNCTION) : true);

		if (LOCManager.locParameters.contains(LOCParameters.LOC) && inner) {
			LOCManager.appendEntityLOCData(enumDeclaration.getName().toString(), locKeeper, EntityType.ENUM);
		}

		// obtem um array com as constantes da enum
		IASTEnumerator[] enumerators = enumDeclaration.getEnumerators();

		vxlFragment.append(VxlManager.startEnum(enumDeclaration.getName().toString(), locKeeper.getLOC(), scope));
		vxlFragment.append((new CommentsProcessor((ASTNode) enumDeclaration)).getVxlFragment());

		// percorre as constantes da enum
		for (IASTEnumerator constEnumerators : enumerators) {
			// grava no vxlFragment as consantes enum
			vxlFragment.append(VxlManager.constant(constEnumerators.getName().toString(), scope));
		}

		vxlFragment.append(VxlManager.endEnum(scope));
	}

	public StringBuffer getVxlFragment() {
		return vxlFragment;
	}

	@Override
	public String toString() {
		return vxlFragment.toString();
	}
}
