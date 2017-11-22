package org.splab.vocabulary.extractor.processors;

import java.util.List;

import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.splab.vocabulary.extractor.util.VxlManager;

public class FieldProcessor {
	private StringBuffer vxlFragment;

	@SuppressWarnings("unchecked")
	public FieldProcessor(FieldDeclaration field) {
		vxlFragment = new StringBuffer();
		for (VariableDeclarationFragment vdf : (List<VariableDeclarationFragment>) field.fragments()) {
			
			String fieldName = vdf.getName().getIdentifier();
			String fieldComment = field.getJavadoc() == null ? "" : StringProcessor.processString(field.getJavadoc().toString());
			String fieldVisibility = visibility(field);
			
			// checks if a field is a constant
			int fieldModifiers = field.getModifiers();
			if (Modifier.isFinal(fieldModifiers)) {
				vxlFragment.append(VxlManager.constant(fieldName, fieldVisibility, fieldComment));
			} else {
				vxlFragment.append(VxlManager.field(fieldName, fieldVisibility, fieldComment));
			}
		}
	}
	
	private static String visibility(BodyDeclaration entityDeclaration) {
		int mthMod = entityDeclaration.getModifiers();	
		return Modifier.isPrivate(mthMod) ? "priv" : Modifier.isProtected(mthMod) ? "prot" : "pub";
	}
	
	public StringBuffer getVxlFragment() {
		return vxlFragment;
	}
	
	@Override
	public String toString() {
		return vxlFragment.toString();
	}
}