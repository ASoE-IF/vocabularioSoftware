package org.splab.vocabulary.extractor.processors.vocabulay.manager;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * FunctionVocabularyManager define onde os dados contabilizados das
 * variáveis globais que são usadas nas funções, das variáveis locais,
 * das chamadas a funções e dos literáis usados nessas funções são
 * guardados e posteriormente usados para incremento em seus respectivos
 * valores de Uso.
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
	}

	/**
	 * Insere variáveis locais e contabiliza
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
	 * Insere o uso de um parâmetro e contabiliza
	 * Obs.: Quando um parâmetro é detectado ele é inserido com count 0,
	 * porquê seus escopo não está dentro do corpo da função
	 * (Delimitado por: {}) e, portanto, seus incrementos só começarão
	 * a partir de sua primeira ocorrência na função.
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
	

	@Override
	/**
	 * Insere variaveis contabilizando no local correto
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
			
			if (vocabulary instanceof FunctionVocabularyManager) {
				FunctionVocabularyManager functionVocabulary = (FunctionVocabularyManager) vocabulary;

				if (functionVocabulary.getLocalVar().containsKey(name)) {
					access = functionVocabulary.getLocalVarAccess().get(name);
					storage = functionVocabulary.getLocalVarStorage().get(name);

					functionVocabulary.insertLocalVariable(name, access, storage);
					insertGlobalVariable(name, access, storage);
					
					return;
				}
			}
			
			if (vocabulary instanceof FileVocabularyManager) {
				FileVocabularyManager fileVocabulary = (FileVocabularyManager) vocabulary;

				if (fileVocabulary.getGlobalVar().containsKey(name)) {
					access = fileVocabulary.getGlobalVarAccess().get(name);
					storage = fileVocabulary.getGlobalVarStorage().get(name);

					fileVocabulary.insertGlobalVariable(name, access, storage);	
					insertGlobalVariable(name, access, storage);
					return;
				}
			}
		}
		insertGlobalVariable(name, access, storage);
	}

	/***
	 * Retorna o maps contendo as variáveis locais
	 * @return
	 */
	public Map<String, Integer> getLocalVar() {
		return lvar;
	}

	/**
	 * Retorna o mapa contendo o armazenamento das variáveis locais
	 * @return
	 */
	public Map<String, String> getLocalVarStorage() {
		return lvarStorage;
	}

	/**
	 * Retorna o mapa contendo o acesso das variáveis locais
	 * @return
	 */
	public Map<String, String> getLocalVarAccess() {
		return lvarAccess;
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