package org.splab.vocabulary.extractor.processors;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPField;
import org.splab.vocabulary.extractor.processors.vocabulay.manager.ClassVocabularyManager;

/**
 * FieldProcessor é responsável por receber um atributo de classe
 * e extrair suas informações.
 * 
 * @author Israel gomes de Lima
 *
 */
public class FieldProcessor {
	/**
	 * Construtor responsável por designar os responsáveis
	 * por extrair as informações dos atributos.
	 * 
	 * @param declSpecifier
	 * @param field
	 * @param indentationLevel
	 * @param vocabularymanager
	 */
	public FieldProcessor(IASTDeclSpecifier declSpecifier, ICPPField field, int indentationLevel, ClassVocabularyManager vocabularymanager) {
		String name = field.getName();
		String visibility = visibility(field.getVisibility());
		String storage = storageClass(field);
		String access = access(declSpecifier);
		
		vocabularymanager.insertField(name, access, storage, visibility);
	}

	/**
	 * Extrai a visibilidade do atributo
	 * 
	 * @param visibilityKey
	 * @return
	 */
	private static String visibility(int visibilityKey) {
		String visibilityField;

		switch (visibilityKey) {
		case 1:
			visibilityField = "pub";
			break;
		case 2:
			visibilityField = "prot";
			break;
		case 3:
			visibilityField = "priv";
			break;
		default:
			visibilityField = "pub";
			break;
		}

		return visibilityField;
	}

	/**
	 * Extrai a classe de armazenamento do atributo
	 * 
	 * @param field
	 * @return
	 */
	private static String storageClass(ICPPField field) {
		String storage;

		if (field.isAuto())
			storage = "auto";
		else if (field.isStatic())
			storage = "static";
		else if (field.isRegister())
			storage = "register";
		else if (field.isExtern())
			storage = "extern";
		else if (field.isMutable())
			storage = "mutable";
		else
			storage = "auto";

		return storage;
	}
	
	/**
	 * Extrai o tipo de acesso ao atributo
	 * 
	 * @param decl
	 * @return
	 */
	private static String access(IASTDeclSpecifier decl) {

		String acesso = "";

		if (decl.isConst())
			acesso = "const";
		else if (decl.isVolatile())
			acesso = "volatile";
		else if (decl.isInline())
			acesso = "inline";
		else if (decl.isRestrict())
			acesso = "restrict";

		return acesso;
	}
}
