package org.splab.vocabulary.extractor.processors.vocabulay.manager;

import java.util.LinkedList;

/**
 * VocabularyManager define uma interface ao qual todos os
 * VocabularysManagers implementaram para poderem ser tratados
 * de maneira igual e genérica.
 * 
 * @author Israel Gomes de Lima
 * @since May 14, 2018
 */
public interface VocabularyManager {
	/**
	 * Lista Encadeada que contem os Vocabularys Managers em uma
	 * hierarquia do mais interno para o mais externo.
	 */
	public static final LinkedList<VocabularyManager> hierarchyVocabularyList = new LinkedList<VocabularyManager>();
	
	/**
	 * Método padrão de interface para inserir um
	 * VocabularyManager na hierarquia
	 */
	public default void insertInHierarchy(){
		hierarchyVocabularyList.add(this);
	}
	
	/**
	 * Método padrão de interface para remover um
	 *  VocabularyManager da hierarquia
	 */
	public default void removeFromHierarchy(){
		hierarchyVocabularyList.removeLast();
	}
	
	/**
	 * Protótipo do método a ser implementado pelas
	 * classes VocabularysManagers
	 * @param name
	 * @param access
	 * @param storage
	 */
	public void insertVariable(String name, String access, String storage);
}
