package org.splab.vocabulary.extractor.processors.vocabulay.manager;


/**
 * VocabularyManager define uma interface ao qual todos os VocabularysManagers
 * implementaram para poderem ser tratados de maneira igual e genérica.
 * 
 * @author Israel Gomes de Lima
 * @since May 14, 2018
 */
public interface VocabularyManager {
	/**
	 * Protótipo do método a ser implementado pelas classes VocabularysManagers
	 * 
	 * @param name
	 * @param access
	 * @param storage
	 */
	public void insertVariable(String name, String access, String storage);
}
