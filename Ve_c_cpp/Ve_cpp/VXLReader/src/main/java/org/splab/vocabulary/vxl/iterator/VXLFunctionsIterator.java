package org.splab.vocabulary.vxl.iterator;

import java.util.Properties;

import org.splab.vocabulary.vxl.iterator.javamodel.CPPClass;
import org.splab.vocabulary.vxl.iterator.javamodel.CPPFile;
import org.splab.vocabulary.vxl.iterator.javamodel.CPPProject;
import org.splab.vocabulary.vxl.iterator.javamodel.ContainerEntity;
import org.splab.vocabulary.vxl.iterator.javamodel.Func;
import org.splab.vocabulary.vxl.iterator.javamodel.Method;
import org.splab.vocabulary.vxl.iterator.util.IdentifierExtractor;

/**
 * An iterator over a method collection, given by a java project. <br>
 * All entity returned by this iterator will be a method specification, and will
 * be removed after returned
 * 
 * @author Israel Gomes de Lima
 */
public class VXLFunctionsIterator extends VXLIterator {

	public VXLFunctionsIterator(Properties props) {
		super(props);
	}

	@Override
	public void setCProject(CPPProject cppProject) {
		super.setCProject(cppProject);

		while (this.entitiesToAdd.isEmpty() && this.fileIterator.hasNext()) {
			this.addContainingFunctions(this.fileIterator.next());
		}
	}

	/**
	 * Retorna uma função e a remove do iterator
	 */
	public ContainerEntity next() {

		Func result = (Func) this.entitiesToAdd.remove(0);

		while ((this.entitiesToAdd.size() == 0) && this.fileIterator.hasNext()) {
			CPPFile newFile = this.fileIterator.next();
			this.addContainingFunctions(newFile);
		}

		Func filteredFunc = this.entityFilter.filterFunc(result);
		return IdentifierExtractor.generateContainer(filteredFunc);
	}

	/**
	 * Adiciona uma função de algum arquivo
	 * 
	 * @param aFile
	 */
	private void addContainingFunctions(CPPFile aFile) {

		/**
		 * Adiciona funções externas
		 */
		for (Func aFunc : aFile.getFunc()) {
			addContainingFunctions(aFunc);
			this.entitiesToAdd.add(aFunc);
		}

		/**
		 * Percorre classes para adicionar as funções, não métodos contidas nas
		 * classes
		 */
		for (CPPClass aClass : aFile.getClazz()) {
			addContainingFunctions(aClass);
		}
	}

	/**
	 * Adiciona funções contidas em funções
	 *
	 * @param func
	 */
	private void addContainingFunctions(Func func) {

		/**
		 * Adiciona funções internas
		 */
		for (Func aFunc : func.getFunc()) {
			addContainingFunctions(aFunc);
			this.entitiesToAdd.add(aFunc);
		}

		/**
		 * Percorre classes para adicionar as funções, não métodos contidas nas
		 * classes
		 */
		for (CPPClass aClass : func.getClazz()) {
			addContainingFunctions(aClass);
		}
	}

	/**
	 * Adiciona funções contidas em classes
	 *
	 * @param clazz
	 */
	private void addContainingFunctions(CPPClass clazz) {

		/**
		 * Adiciona funções internas a classes
		 */
		for (Func aFunc : clazz.getFunc()) {
			addContainingFunctions(aFunc);
			this.entitiesToAdd.add(aFunc);
		}

		/**
		 * Percorre classes para adicionar as funções, não métodos contidas nas
		 * classes
		 */
		for (CPPClass aClass : clazz.getClazz()) {
			addContainingFunctions(aClass);
		}

		/**
		 * Percorre os métodos e adiciona as funções internas desses métodos
		 */
		for (Method aMethod : clazz.getMth()) {
			addContainingFunctions(aMethod);
		}
	}

	/**
	 * Adiciona funções contidas em métodos
	 *
	 * @param method
	 */
	private void addContainingFunctions(Method method) {

		/**
		 * Adiciona funções internas a métodos
		 */
		for (Func aFunc : method.getFunc()) {
			addContainingFunctions(aFunc);
			this.entitiesToAdd.add(aFunc);
		}

		/**
		 * Percorre classes para adicionar as funções, não métodos contidas nas
		 * classes
		 */
		for (CPPClass aClass : method.getClazz()) {
			addContainingFunctions(aClass);
		}
	}
}