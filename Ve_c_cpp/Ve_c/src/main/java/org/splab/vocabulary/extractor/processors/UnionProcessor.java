package org.splab.vocabulary.extractor.processors;

import java.util.Arrays;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclaration;
import org.splab.vocabulary.extractor.util.LOCManager;
import org.splab.vocabulary.extractor.util.VxlManager;
import org.splab.vocabulary.extractor.vloccount.EntityLOCKeeper;
import org.splab.vocabulary.extractor.vloccount.EntityType;
import org.splab.vocabulary.extractor.vloccount.LOCCountPerEntity;
import org.splab.vocabulary.extractor.vloccount.LOCParameters;

/**
 * This class is responsible for extracting informations from the union's body.
 * All the informations extract should be Statement type.
 * 
 * UnionProcessor extract informations such as: union
 * 
 * @author Israel Gomes de Lima
 */

public class UnionProcessor {
	private StringBuffer vxlFragment;

	public UnionProcessor(CASTCompositeTypeSpecifier compositeType, boolean scope) {
		vxlFragment = new StringBuffer();

		// Efetua contagem de loc por entidade
		LOCCountPerEntity locCounter = new LOCCountPerEntity((ASTNode) compositeType,
				CompilationUnitProcessor.commentList, CompilationUnitProcessor.sourceCode);
		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter);

		if (LOCManager.locParameters.contains(LOCParameters.LOC))
			LOCManager.appendEntityLOCData(compositeType.getName().toString(), locKeeper, EntityType.UNION);

		// Cria lista de membros
		List<IASTDeclaration> members = Arrays.asList(compositeType.getMembers());

		vxlFragment.append(VxlManager.startUnion(compositeType.getName().toString(), locKeeper.getLOC(), scope));

		vxlFragment.append(new CommentsProcessor((ASTNode) compositeType));

		// Extrai os membros das structs
		for (IASTDeclaration membersSimple : members) {
			if (membersSimple instanceof CASTSimpleDeclaration) {
				CASTSimpleDeclaration simpleMembers = (CASTSimpleDeclaration) membersSimple;

				IASTDeclarator[] unionMembers = simpleMembers.getDeclarators();
				if (unionMembers.length != 0) {
					vxlFragment.append(VxlManager.unionMembers(unionMembers[0].getName().toString(), scope));
				}
			}
		}

		vxlFragment.append(VxlManager.endUnion(scope));
	}

	public StringBuffer getVxlFragment() {
		return vxlFragment;
	}
}