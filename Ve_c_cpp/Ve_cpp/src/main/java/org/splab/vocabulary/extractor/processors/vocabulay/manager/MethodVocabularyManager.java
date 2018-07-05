package org.splab.vocabulary.extractor.processors.vocabulay.manager;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * MethodVocabularyManager define onde os dados contabilizados das
 * variáveis globais que são usadas nos métodos, das variáveis locais,
 * das chamadas a funções, das invocações de métodos, dos usos de 
 * atributos e dos literáis usados nesses métodos são guardados
 * e posteriormente usados para incremento em seus respectivos
 * valores de Uso.
 * 
 * @author Israel Gomes de Lima
 * @since May 14, 2018
 */
public class MethodVocabularyManager extends FunctionVocabularyManager{
	
	/**
	 * Mapas para guardar os usos de atributos no respectivo método
	 */
	private Map<String, Integer> field;
	private Map<String, String> fieldStorage;
	private Map<String, String> fieldAccess;
	private Map<String, String> fieldVisibility;
	
	/**
	 * Construtor do vocabulário do método
	 */
	public MethodVocabularyManager(){
		super();
		this.field = new TreeMap<String, Integer>();
		this.fieldStorage = new TreeMap<String, String>();
		this.fieldAccess = new TreeMap<String, String>();
		this.fieldVisibility = new TreeMap<String, String>();
	}
	
	/**
	 * Insere os atributos e contabiliza seus usos
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
	 * Insere variaveis e atributos contabilizando no local correto
	 * @param name
	 * @param access
	 * @param storage
	 */
	public void insertVariable(String name, String access, String storage) {

		// Testa se a variável já foi inserida como local
		if (this.getLocalVar().containsKey(name)) {
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

					classVocabulary.insertField(name, access, storage, visibility);
					insertField(name, access, storage, visibility);

					return;
				}
			}
			
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

	/**
	 * Retorna o mapa contendo os atributos usados no métodos
	 * @return
	 */
	public Map<String, Integer> getField() {
		return field;
	}

	/**
	 * Retorna o mapa contendo o armazenamento dos atributos
	 * @return
	 */
	public Map<String, String> getFieldStorage() {
		return fieldStorage;
	}

	/**
	 * Retorna o mapa contendo o acesso dos atributos
	 * @return
	 */
	public Map<String, String> getFieldAccess() {
		return fieldAccess;
	}

	/**
	 * Retorna o mapa contendo a visibilidade dos atributos
	 * @return
	 */
	public Map<String, String> getFieldVisibility() {
		return fieldVisibility;
	}
}
