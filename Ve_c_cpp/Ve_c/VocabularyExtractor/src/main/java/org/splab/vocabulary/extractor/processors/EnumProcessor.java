package org.splab.vocabulary.extractor.processors;

import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTEnumerationSpecifier;
import org.splab.vocabulary.extractor.util.LOCManager;
import org.splab.vocabulary.extractor.util.VxlManager;
import org.splab.vocabulary.extractor.vloccount.EntityLOCKeeper;
import org.splab.vocabulary.extractor.vloccount.EntityType;
import org.splab.vocabulary.extractor.vloccount.LOCCountPerEntity;
import org.splab.vocabulary.extractor.vloccount.LOCParameters;

/**
 * EnumProcessor é responsável por receber um tipo enum e tratálo, obtendo suas
 * inforções, tais como seu nome e o de suas constantes.
 * 
 * @author Israel Gomes de Lima
 *
 */
public class EnumProcessor {
	/**
	 * Mantém um fragmento de vxl.
	 */
	private StringBuffer vxlFragment;

	/**
	 * Construtor responsável por processar a enum.
	 * 
	 * @param enumDeclaration
	 * @param indentationLevel
	 */
	public EnumProcessor(CASTEnumerationSpecifier enumDeclaration, int indentationLevel) {

		vxlFragment = new StringBuffer();

		LOCCountPerEntity locCounter = new LOCCountPerEntity((ASTNode) enumDeclaration,
				CompilationUnitProcessor.commentList, CompilationUnitProcessor.sourceCode);
		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter);

		if (LOCManager.locParameters.contains(LOCParameters.LOC)) {
			LOCManager.appendEntityLOCData(enumDeclaration.getName().toString(), locKeeper, EntityType.ENUM);
		}

		// obtem um array com as constantes da enum
		IASTEnumerator[] enumerators = enumDeclaration.getEnumerators();

		vxlFragment.append(VxlManager.startEnum(StringProcessor.processString(enumDeclaration.getName().toString()),
				locKeeper.getLOC(), indentationLevel));
		vxlFragment.append((new CommentsProcessor((ASTNode) enumDeclaration, indentationLevel + 1)).getVxlFragment());

		// percorre as constantes da enum
		for (IASTEnumerator constEnumerators : enumerators) {
			// grava no vxlFragment as consantes enum
			vxlFragment.append(VxlManager.constant(StringProcessor.processString(constEnumerators.getName().toString()),
					indentationLevel + 1));
		}

		vxlFragment.append(VxlManager.endEnum(indentationLevel));
	}

	/**
	 * Retorna o fragmento de vxl
	 * 
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