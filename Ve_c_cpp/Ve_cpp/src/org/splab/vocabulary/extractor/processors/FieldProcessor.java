package org.splab.vocabulary.extractor.processors;

import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IValue;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPField;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTEqualsInitializer;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDeclarator;
import org.splab.vocabulary.extractor.util.VxlManager;

public class FieldProcessor {
	private StringBuffer vxlFragment;

	@SuppressWarnings("unchecked")
	public FieldProcessor(IASTDeclarator fieldDeclarator, IASTDeclSpecifier declSpecifier, ICPPField field) {
		vxlFragment = new StringBuffer();

		String fieldName = field.getName();
		String fieldVisibility = visibility(field.getVisibility());
		String fieldStorageClass = storageClass(field);
		String access = access(declSpecifier);

		// extrai as inicializações na hora da declaração
		if (fieldDeclarator.getInitializer() != null)
			Declarations.extractEqualsInitializer((CPPASTEqualsInitializer) fieldDeclarator.getInitializer());
		
		vxlFragment.append(VxlManager.field(fieldName, fieldVisibility, access, fieldStorageClass));
	}

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

	public StringBuffer getVxlFragment() {
		return vxlFragment;
	}

	@Override
	public String toString() {
		return vxlFragment.toString();
	}
}
