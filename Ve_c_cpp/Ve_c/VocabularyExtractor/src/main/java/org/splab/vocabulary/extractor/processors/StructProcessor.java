package org.splab.vocabulary.extractor.processors;

import java.util.Arrays;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTEnumerationSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclaration;
import org.splab.vocabulary.extractor.processors.vocabulay.manager.VocabularyManager;
import org.splab.vocabulary.extractor.util.LOCManager;
import org.splab.vocabulary.extractor.util.VxlManager;
import org.splab.vocabulary.extractor.vloccount.EntityLOCKeeper;
import org.splab.vocabulary.extractor.vloccount.EntityType;
import org.splab.vocabulary.extractor.vloccount.LOCCountPerEntity;
import org.splab.vocabulary.extractor.vloccount.LOCParameters;

/**
 * StructProcessor é responsável por processar as estruturas
 * 
 * @author Israel Gomes de Lima
 *
 */
public class StructProcessor {
	/**
	 * Mantém o fragmento de vxl
	 */
	private StringBuffer vxlFragment;

	/**
	 * Construtor responsável por receber um estrutura e processar seu
	 * vocabulário
	 * 
	 * @param compositeType
	 * @param indentationLevel
	 */
	public StructProcessor(CASTCompositeTypeSpecifier compositeType, VocabularyManager vocabularyManager,
			int indentationLevel) {
		vxlFragment = new StringBuffer();

		// Efetua contagem de loc por entidade
		LOCCountPerEntity locCounter = new LOCCountPerEntity((ASTNode) compositeType,
				CompilationUnitProcessor.commentList, CompilationUnitProcessor.sourceCode);
		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter);
		
		

		if (LOCManager.locParameters.contains(LOCParameters.LOC))
			LOCManager.appendEntityLOCData(compositeType.getName().toString(), locKeeper, EntityType.STRUCT);

		// Cria lista de membros
		List<IASTDeclaration> members = Arrays.asList(compositeType.getMembers());

		vxlFragment.append(VxlManager.startStruct(StringProcessor.processString(compositeType.getName().toString()),
				locKeeper.getLOC(), indentationLevel));

		vxlFragment.append(new CommentsProcessor((ASTNode) compositeType, indentationLevel + 1));

		DeclarationProcessor declProcessor = new DeclarationProcessor(vocabularyManager);
		// Extrai os membros das structs
		for (IASTDeclaration membersSimple : members) {
			if (membersSimple instanceof CASTSimpleDeclaration) {
				CASTSimpleDeclaration simpleMembers = (CASTSimpleDeclaration) membersSimple;

				IASTDeclarator[] structMembers = simpleMembers.getDeclarators();

				if (structMembers.length != 0) {
					vxlFragment.append(
							VxlManager.field(StringProcessor.processString(structMembers[0].getName().toString()),
									indentationLevel + 1));
				}

				if (simpleMembers.getDeclSpecifier() instanceof CASTCompositeTypeSpecifier) {
					declProcessor.extractDeclaration(simpleMembers, indentationLevel);
				}

				if (simpleMembers.getDeclSpecifier() instanceof CASTEnumerationSpecifier) {
					declProcessor.extractDeclaration(simpleMembers, indentationLevel);
				}
			}
		}

		vxlFragment.append(VxlManager.endStruct(indentationLevel));
		vxlFragment.append(declProcessor.getVxlFragment());
	}

	/**
	 * Retorna o fragmento de vxl
	 * 
	 * @return
	 */
	public StringBuffer getVxlFragment() {
		return vxlFragment;
	}
}