package org.splab.vocabulary.extractor.processors.vocabulay.manager;

import java.util.Map;
import java.util.TreeMap;

/**
 * FileVocabularyManager define onde os dados contabilizados das
 * variáveis globais e literais usadoss fora do escopo de entidades
 * de armazenamento são guardados e posteriormente usados para
 * incremento em seus respectivos valores de Uso.
 * 
 * @author Israel Gomes de Lima
 * @since May 14, 2018
 */
public class FileVocabularyManager implements VocabularyManager {

	/**
	 * Variáveis que guardam os dados das variáveis globais e literais
	 * do arquivo
	 **/
	private Map<String, Integer> gvar;
	private Map<String, String> gvarStorage;
	private Map<String, String> gvarAccess;
	private Map<String, Integer> funcCall;
	private Map<String, Integer> literal;

	/**
	 * Construtor do Vocabulário do arquivo
	 */
	public FileVocabularyManager() {
		this.gvar = new TreeMap<String, Integer>();
		this.gvarStorage = new TreeMap<String, String>();
		this.gvarAccess = new TreeMap<String, String>();
		this.funcCall = new TreeMap<String, Integer>();
		this.literal = new TreeMap<String, Integer>();
	}

	/**
	 * Insere variáveis globais e contabiliza
	 * @param name
	 * @param access
	 * @param storage
	 */
	public void insertGlobalVariable(String name, String access, String storage) {

		if (name != null && !name.equals("")) {
			if (gvar.containsKey(name)) {
				gvar.put(name, gvar.get(name) + 1);
			} else {
				gvar.put(name, 1);
				gvarAccess.put(name, access);
				gvarStorage.put(name, storage);
			}
		}
	}

	/**
	 * Insere as chamadas a funções e contabiliza
	 * 
	 * @param functionCall
	 */
	public void insertFunctionCall(String functionCall) {
		if (functionCall != null && !functionCall.equals("")) {
			if (funcCall.containsKey(functionCall)) {
				funcCall.put(functionCall, funcCall.get(functionCall) + 1);
			} else {
				funcCall.put(functionCall, 1);
			}
		}
	}
	
	/**
	 * Insere strings literais e contabiliza
	 * @param literalString
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

	/**
	 * Insere variaveis contabilizando no local correto
	 * @param name
	 * @param access
	 * @param storage
	 */
	@Override
	public void insertVariable(String name, String access, String storage) {
		// Testa se a variável já foi inserida como global
		if (this.gvar.containsKey(name)) {
			insertGlobalVariable(name, "", "");

			return;
		}

		// Se a variável que está sendo usada não tiver sido delcarada
		insertGlobalVariable(name, access, storage);
	}

	/**
	 * Retorna o mapa contendo as variaveis globais
	 * @return
	 */
	public Map<String, Integer> getGlobalVar() {
		return gvar;
	}

	/**
	 * Retorna o mapa contendo o armazenamento das variaveis globais
	 * @return
	 */
	public Map<String, String> getGlobalVarStorage() {
		return gvarStorage;
	}

	/**
	 * Retorna o mapa contendo o acesso das variaveis globais
	 * @return
	 */
	public Map<String, String> getGlobalVarAccess() {
		return gvarAccess;
	}

	/**
	 * Retorna o mapa contendo as chamadas a funções
	 * @return
	 */
	public Map<String, Integer> getFunctionCall() {
		return funcCall;
	}
	
	/**
	 * Retorna o mapa contendo as strings literais
	 * @return
	 */
	public Map<String, Integer> getLiterals() {
		return literal;
	}
}