package org.splab.vocabulary.extractor.processors;

import java.util.Map;
import java.util.TreeMap;

/**
 * Manage the vocabulary of methods.
 * 
 * @author Catharine Quintans
 * @since November 22, 2012 Modificado por: Israel Gomes de Lima para
 *        funcionamento neste extrator
 */
public class FunctionVocabularyManager {
	//Dados das variaveis locais
	Map<String, Integer> lvar = new TreeMap<String, Integer>();
	Map<String, String> lvarStorage = new TreeMap<String, String>();
	Map<String, String> lvarAccess = new TreeMap<String, String>();

	//Dados das variaveis globais
	Map<String, Integer> gvar = new TreeMap<String, Integer>();
	Map<String, String> gvarStorage = new TreeMap<String, String>();
	Map<String, String> gvarAccess = new TreeMap<String, String>();

	//Dados dos protótipos de funções
	Map<String, String> funcPrttpStorage = new TreeMap<String, String>();
	Map<String, String> funcPrttpAccess = new TreeMap<String, String>();

	//Dados das chamadas de funções
	Map<String, Integer> funcCall = new TreeMap<String, Integer>();

	//Dados das literáis
	Map<String, Integer> literal = new TreeMap<String, Integer>();

	/**
	 * Insert the locals variables.
	 * 
	 * @param variable
	 *            The local variable.
	 */
	public void insertLocalVariable(String variable, String access, String storage) {
		if (variable != null && !variable.equals("")) {
			if (lvar.containsKey(variable)) {
				lvar.put(variable, lvar.get(variable) + 1);
			} else {
				lvar.put(variable, 1);
				lvarAccess.put(variable, access);
				lvarStorage.put(variable, storage);
			}
		}
	}

	/**
	 * Insert the globals variables.
	 * 
	 * @param variable
	 *            The global variable.
	 */
	public void insertGlobalVariable(String variable, String access, String storage) {

		if (variable != null && !variable.equals("")) {
			if (gvar.containsKey(variable)) {
				gvar.put(variable, gvar.get(variable) + 1);
				
				FileVocabularyManager.gvar.put(variable, FileVocabularyManager.gvar.get(variable) + 1);
			} else {
				gvar.put(variable, 1);
				gvarAccess.put(variable, access);
				gvarStorage.put(variable, storage);
				
				FileVocabularyManager.gvar.put(variable, FileVocabularyManager.gvar.get(variable) + 1);
			}
		}
	}

	/**
	 * Insert the functions variables.
	 * 
	 * @param variable
	 *            The functions variable.
	 */
	public void insertFunctionVariable(String variable, String access, String storage) {
		//Insere variáveis globais que são variáveis locais de funções mais são globais para as funções internas
		if (variable != null && !variable.equals("")) {
			if (gvar.containsKey(variable)) {
				gvar.put(variable, gvar.get(variable) + 1);
			} else {
				gvar.put(variable, 1);
				gvarAccess.put(variable, access);
				gvarStorage.put(variable, storage);
			}
		}
	}

	public void insertParameter(String parametro, String access,  String storage) {
		if (parametro != null && !parametro.equals("")) {
			if (lvar.containsKey(parametro)) {
				lvar.put(parametro, lvar.get(parametro) + 1);
			} else {
				lvar.put(parametro, 0);
				lvarAccess.put(parametro, access);
				lvarStorage.put(parametro, storage);
			}
		}
	}

	public void insertFuncPrototipo(String prototipo, String access, String storage) {
		if (prototipo != null && !prototipo.equals("")) {
			if (!funcPrttpAccess.containsKey(prototipo)) {
				funcPrttpAccess.put(prototipo, access);
				funcPrttpStorage.put(prototipo, storage);
			}
		}
	}

	/**
	 * Insert the methods invocations.
	 * 
	 * @param functionCall
	 *            The method invocation.
	 */
	public void insertFunctionInvocation(String functionCall) {
		if (functionCall != null && !functionCall.equals("")) {
			if (funcCall.containsKey(functionCall)) {
				funcCall.put(functionCall, funcCall.get(functionCall) + 1);
			} else {
				funcCall.put(functionCall, 1);
			}
		}
	}

	/**
	 * Insert the literals.
	 * 
	 * @param literalString
	 *            The literal.
	 */
	public void insertLiteral(String literalString) {
		if (literalString != null && !literalString.trim().equals("")) {
			if (literal.containsKey(literalString)) {
				literal.put(literalString, literal.get(literalString) + 1);
			} else {
				literal.put(literalString, 1);
			}
		}
	}
	/***
	 * Return the map contain all the locals variables of the method.
	 * 
	 * @return
	 */
	public Map<String, Integer> getLocalVar() {
		return lvar;
	}

	public Map<String, String> getLocalVarStorage() {
		return lvarStorage;
	}

	public Map<String, String> getLocalVarAccess() {
		return lvarAccess;
	}

	public Map<String, Integer> getGlobalVar() {
		return gvar;
	}

	public Map<String, String> getGlobalVarStorage() {
		return gvarStorage;
	}

	public Map<String, String> getGlobalVarAccess() {
		return gvarAccess;
	}

	public Map<String, String> getFunctionPrttpStorage() {
		return funcPrttpStorage;
	}

	public Map<String, String> getFunctionPrttpAccess() {
		return funcPrttpAccess;
	}
	
	/**
	 * Return the map contain all the locals variables of the method.
	 * 
	 * @return
	 */
	public Map<String, Integer> getFunctionCall() {
		return funcCall;
	}

	/**
	 * Return the map contain all the locals variables of the method.
	 * 
	 * @return
	 */
	public Map<String, Integer> getLiterals() {
		return literal;
	}
}