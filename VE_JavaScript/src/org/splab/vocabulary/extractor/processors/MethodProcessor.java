package org.splab.vocabulary.extractor.processors;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.splab.vocabulary.extractor.util.LOCManager;
import org.splab.vocabulary.extractor.util.VxlManager;
import org.splab.vocabulary.extractor.vloccount.LOCParameters;

public class MethodProcessor {
	private StringBuffer vxlFragment;
	
	public MethodProcessor() {
		vxlFragment = new StringBuffer();
	}
	
	@SuppressWarnings("unchecked")
	public MethodProcessor(MethodDeclaration method, String signature) {
		String name = signature + method.getName().getIdentifier() + getParametersAssignatures(method);
		String javadoc = method.getJavadoc() == null ? "" : StringProcessor.processString(method.getJavadoc().toString());
		String visibility = extractVisibility(method);
		
		vxlFragment = new StringBuffer(VxlManager.startMethod(name, visibility, javadoc));
		VxlManager.methodComment(vxlFragment, CommentsProcessor.getMethodDeclarationComments(method));
		
		for (SingleVariableDeclaration parameter : (List<SingleVariableDeclaration>) method.parameters())
			VxlManager.parameter(vxlFragment, parameter.getName().getIdentifier());
		
		if (method.getBody() != null && LOCManager.locParameters.contains(LOCParameters.INTERN_VOCABULARY)) {
			for (Statement statement : (List<Statement>) method.getBody().statements()) {
				//extrai os vocabularios internos.
				BodyProcessor.extractBody(statement);
			}
			//recupera o vocabulario extraido.			
			storeInternVocabulary(MethodVocabularyManager.getLocalVariables(), MethodVocabularyManager.getMethodsInvocation(), MethodVocabularyManager.getLiterals());
			//limpa os mapas que armazenam os dados por metodos.
			MethodVocabularyManager.clear();
		}
		
		vxlFragment.append(VxlManager.endMethod());
	}
	
	/**
	 * Store the data about the method vocabulary. VXL format. 
	 * @param localVariables The locals variables.
	 * @param methodsInvocation The methods invocations.
	 * @param literals The literals.
	 */
	private void storeInternVocabulary(Map<String, Integer> localVariables,
			Map<String, Integer> methodsInvocation,
			Map<String, Integer> literals) {
	
			Set<String> lvar = localVariables.keySet();
			Iterator<String> it_lvar = lvar.iterator();
			while (it_lvar.hasNext()) {
				String identifier = it_lvar.next();
				vxlFragment.append(VxlManager.localVariable(identifier, localVariables.get(identifier)));
			}
			
			Set<String> mthInv = methodsInvocation.keySet();
			Iterator<String> it_mth = mthInv.iterator();
			while (it_mth.hasNext()) {
				String identifier = it_mth.next();
				vxlFragment.append(VxlManager.methodInvocation(identifier, methodsInvocation.get(identifier)));
			}
			
			Set<String> lit = literals.keySet();
			Iterator<String> it_lit = lit.iterator();
			while (it_lit.hasNext()) {
				String identifier = it_lit.next();
				vxlFragment.append(VxlManager.literal(identifier, literals.get(identifier)));
			}
	}

	
	/**
	 * Returns a representation of the parameter part of the given method
	 * @param method AST method information keeper
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static String getParametersAssignatures(MethodDeclaration method) {
		StringBuffer retorno = new StringBuffer("(");
		
		int indiceDoParametro = 1;
		int numeroDeParametros = ((List<SingleVariableDeclaration>) method.parameters()).size();
		for(SingleVariableDeclaration parameter : (List<SingleVariableDeclaration>) method.parameters())
			retorno.append(validaParametro(parameter.getType().toString()) + (indiceDoParametro++ == numeroDeParametros ? "" : ", "));
		retorno.append(")");
		
		return retorno.toString();
	}
	
	/**
	 * Returns the visibility from an entity in a String
	 * @param entityDeclaration AST representation of any structure which possesses a body
	 * @return
	 */
	private static String extractVisibility(BodyDeclaration entityDeclaration) {
		int mthMod = entityDeclaration.getModifiers();	
		return Modifier.isPrivate(mthMod) ? "priv" : Modifier.isProtected(mthMod) ? "prot" : "pub";
	}
	
	/**
	 * Returns a converted version of the parameter models related to Generic Programming
	 * @param parametro String representation of the parameter
	 * @return A converted version of the parameter models.
	 */
	private static String validaParametro(String parametro) {
		while(parametro.contains("<"))
			parametro = parametro.replace("<", ":");
		while(parametro.contains(", "))
			parametro = parametro.replace(", ", "-");
		while(parametro.contains(">"))
			parametro = parametro.replace(">", "");
		
		return parametro;
	}
	
	public StringBuffer getVxlFragment() {
		return vxlFragment;
	}
	
	@Override
	public String toString() {
		return vxlFragment.toString();
	}
}