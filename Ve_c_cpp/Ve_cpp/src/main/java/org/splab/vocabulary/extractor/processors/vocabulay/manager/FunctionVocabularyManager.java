package org.splab.vocabulary.extractor.processors.vocabulay.manager;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * FunctionVocabularyManager define onde os dados contabilizados das variáveis
 * globais que são usadas nas funções, das variáveis locais, das chamadas a
 * funções e dos literáis usados nessas funções são guardados e posteriormente
 * usados para incremento em seus respectivos valores de Uso.
 * 
 * @author Israel Gomes de Lima
 * @since May 14, 2018
 */
public class FunctionVocabularyManager implements VocabularyManager {

	/**
	 * Mapas para guardar os dados das variáveis locais, usos de variáveis
	 * globais, chamadas a funções e strings literais
	 */
	private Map<String, Integer> lvar;
	private Map<String, String> lvarStorage;
	private Map<String, String> lvarAccess;
	private Map<String, Integer> gvar;
	private Map<String, String> gvarStorage;
	private Map<String, String> gvarAccess;
	private Map<String, Integer> funcCall;
	private Map<String, Integer> literal;
	private Map<String, Integer> field;
	private Map<String, String> fieldStorage;
	private Map<String, String> fieldAccess;
	private Map<String, String> fieldVisibility;

	/**
	 * Construtor do vocabulário da função
	 */
	public FunctionVocabularyManager() {
		this.lvar = new TreeMap<String, Integer>();
		this.lvarStorage = new TreeMap<String, String>();
		this.lvarAccess = new TreeMap<String, String>();
		this.gvar = new TreeMap<String, Integer>();
		this.gvarStorage = new TreeMap<String, String>();
		this.gvarAccess = new TreeMap<String, String>();
		this.funcCall = new TreeMap<String, Integer>();
		this.literal = new TreeMap<String, Integer>();
		this.field = new TreeMap<String, Integer>();
		this.fieldStorage = new TreeMap<String, String>();
		this.fieldAccess = new TreeMap<String, String>();
		this.fieldVisibility = new TreeMap<String, String>();
	}

	/**
	 * Insere variáveis locais e contabiliza
	 * 
	 * @param variable
	 * @param access
	 * @param storage
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
	 * Insere os usos de variáveis globais e contabiliza
	 * 
	 * @param variable
	 * @param access
	 * @param storage
	 */
	public void insertGlobalVariable(String variable, String access, String storage) {

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

	/**
	 * Insere o uso de um parâmetro e contabiliza Obs.: Quando um parâmetro é
	 * detectado ele é inserido com count 0, porquê seus escopo não está dentro
	 * do corpo da função (Delimitado por: {}) e, portanto, seus incrementos só
	 * começarão a partir de sua primeira ocorrência na função.
	 * 
	 * @param parametro
	 * @param access
	 * @param storage
	 */
	public void insertParameter(String parametro, String access, String storage) {
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
	 * Insere strings literais usadas na funções e contabiliza
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
	 * Insere os atributos e contabiliza seus usos
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

	@Override
	/**
	 * Insere variaveis contabilizando no local correto
	 * 
	 * @param name
	 * @param access
	 * @param storage
	 */
	public void insertVariable(String name, String access, String storage) {
		// Testa se a variável já foi inserida como local
		if (this.lvar.containsKey(name)) {
			insertLocalVariable(name, access, storage);

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

	/***
	 * Retorna o maps contendo as variáveis locais
	 * 
	 * @return
	 */
	public Map<String, Integer> getLocalVar() {
		return lvar;
	}

	/**
	 * Retorna o mapa contendo o armazenamento das variáveis locais
	 * 
	 * @return
	 */
	public Map<String, String> getLocalVarStorage() {
		return lvarStorage;
	}

	/**
	 * Retorna o mapa contendo o acesso das variáveis locais
	 * 
	 * @return
	 */
	public Map<String, String> getLocalVarAccess() {
		return lvarAccess;
	}

	/**
	 * Retorna o mapa contendo as variaveis globais
	 * 
	 * @return
	 */
	public Map<String, Integer> getGlobalVar() {
		return gvar;
	}

	/**
	 * Retorna o mapa contendo o armazenamento das variaveis globais
	 * 
	 * @return
	 */
	public Map<String, String> getGlobalVarStorage() {
		return gvarStorage;
	}

	/**
	 * Retorna o mapa contendo o acesso das variaveis globais
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

	/**
	 * Retorna o mapa contendo os atributos usados no métodos
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
}