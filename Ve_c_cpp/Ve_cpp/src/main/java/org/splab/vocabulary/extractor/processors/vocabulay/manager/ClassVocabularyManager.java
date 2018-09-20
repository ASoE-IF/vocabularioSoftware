package org.splab.vocabulary.extractor.processors.vocabulay.manager;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * ClassVocabularyManager define onde os dados contabilizados das variáveis
 * globais que são usadas nas classes, dos usos de atributos e dos literáis
 * usados nessas classes são guardados e posteriormente usados para incremento
 * em seus respectivos valores de Uso.
 * 
 * @author Israel Gomes de Lima
 * @since May 14, 2018
 */
public class ClassVocabularyManager implements VocabularyManager {

	/**
	 * Mapas que guardam os dados das variáveis globais, usos e declarações de
	 * atributos e strings literais.
	 **/
	private Map<String, Integer> gvar;
	private Map<String, String> gvarStorage;
	private Map<String, String> gvarAccess;
	private Map<String, Integer> field;
	private Map<String, String> fieldStorage;
	private Map<String, String> fieldAccess;
	private Map<String, String> fieldVisibility;
	private Map<String, Integer> funcCall;
	private Map<String, Integer> literal;

	/**
	 * Construtor do vocabulário da classe
	 */
	public ClassVocabularyManager() {
		this.gvar = new TreeMap<String, Integer>();
		this.gvarStorage = new TreeMap<String, String>();
		this.gvarAccess = new TreeMap<String, String>();
		this.field = new TreeMap<String, Integer>();
		this.fieldStorage = new TreeMap<String, String>();
		this.fieldAccess = new TreeMap<String, String>();
		this.fieldVisibility = new TreeMap<String, String>();
		this.funcCall = new TreeMap<String, Integer>();
		this.literal = new TreeMap<String, Integer>();
	}

	/**
	 * Insere e contabiliza o uso de variáveis globais
	 * 
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
	 * Insere e contabiliza o uso dos atributos
	 * 
	 * @param name
	 * @param access
	 * @param storage
	 * @param visibility
	 */
	public void insertField(String name, String access, String storage, String visibility) {

		if (name != null && !name.equals("")) {
			if (field.containsKey(name)) {
				field.put(name, field.get(name) + 1);
			} else {
				field.put(name, 1);
				fieldAccess.put(name, access);
				fieldStorage.put(name, storage);
				fieldVisibility.put(name, visibility);
			}
		}
	}

	/**
	 * Insere as chamadas a funções e contabiliza
	 * 
	 * @param functionCall
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
	 * Insere e contabiliza o uso dos strings literais
	 * 
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
	 * Insere variaveis e atributos contabilizando no local correto
	 * 
	 * @param name
	 * @param access
	 * @param storage
	 */
	@Override
	public void insertVariable(String name, String access, String storage) {
		// Testa se a variável já foi inserida como local
		if (this.field.containsKey(name)) {
			String visibility = fieldVisibility.get(name);
			insertField(name, access, storage, visibility);
			return;
		}

		Iterator<VocabularyManager> it_vocabulary = hierarchyVocabularyList.descendingIterator();
		while (it_vocabulary.hasNext()) {

			VocabularyManager vocabulary = it_vocabulary.next();
			if (vocabulary instanceof ClassVocabularyManager) {
				ClassVocabularyManager classVocabulary = (ClassVocabularyManager) vocabulary;

				if (classVocabulary.getField().containsKey(name)) {
					access = classVocabulary.getFieldAccess().get(name);
					storage = classVocabulary.getFieldStorage().get(name);
					String visibility = classVocabulary.getFieldVisibility().get(name);

					insertField(name, access, storage, visibility);

					return;
				}
			}

			if (vocabulary instanceof FunctionVocabularyManager) {
				FunctionVocabularyManager functionVocabulary = (FunctionVocabularyManager) vocabulary;

				if (functionVocabulary.getLocalVar().containsKey(name)) {
					access = functionVocabulary.getLocalVarAccess().get(name);
					storage = functionVocabulary.getLocalVarStorage().get(name);

					insertGlobalVariable(name, access, storage);

					return;
				}
			}
		}
		insertGlobalVariable(name, access, storage);
	}

	/**
	 * Retorna o mapa contendo os atributos
	 * 
	 * @return
	 */
	public Map<String, Integer> getField() {
		return field;
	}

	/**
	 * Retorna o mapa contendo o armazenamento dos atributos
	 * 
	 * @return
	 */
	public Map<String, String> getFieldStorage() {
		return fieldStorage;
	}

	/**
	 * Retorna o mapa contendo o acesso dos atributos
	 * 
	 * @return
	 */
	public Map<String, String> getFieldAccess() {
		return fieldAccess;
	}

	/**
	 * Retorna o mapa contendo a visibilidade dos atributos
	 * 
	 * @return
	 */
	public Map<String, String> getFieldVisibility() {
		return fieldVisibility;
	}

	/**
	 * retorna o mapa contendo as variáveis globais
	 * 
	 * @return
	 */
	public Map<String, Integer> getGlobalVar() {
		return gvar;
	}

	/**
	 * Retorna o mapa contendo o armazenamento das variáveis globais
	 * 
	 * @return
	 */
	public Map<String, String> getGlobalVarStorage() {
		return gvarStorage;
	}

	/**
	 * Retorna o mapa contendo o acesso das variáveis globais
	 * 
	 * @return
	 */
	public Map<String, String> getGlobalVarAccess() {
		return gvarAccess;
	}

	/**
	 * Retorna o mapa contendo as chamadas a funções
	 * 
	 * @return
	 */
	public Map<String, Integer> getFunctionCall() {
		return funcCall;
	}

	/**
	 * Retorna o mapa contendo as strings literais
	 * 
	 * @return
	 */
	public Map<String, Integer> getLiterals() {
		return literal;
	}
}