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
 * An iterator over a class collection, given by a c project. <br>
 * All entity returned by this iterator will be a class specification, and will
 * be removed after returned
 * 
 * @author Israel Gomes de Lima
 */

public class VXLMethodsIterator extends VXLIterator {

	public VXLMethodsIterator(Properties props) {
		super(props);
	}

	@Override
	public void setCProject(CPPProject cppProject) {
		super.setCProject(cppProject);

		while (this.entitiesToAdd.isEmpty() && this.fileIterator.hasNext()) {
			this.addContainingMethods(this.fileIterator.next());
		}
	}

	/**
	 * Retorna uma função e a remove do iterator
	 */
	public ContainerEntity next() {

		Method result = (Method) this.entitiesToAdd.remove(0);

		while ((this.entitiesToAdd.size() == 0) && this.fileIterator.hasNext()) {
			CPPFile newFile = this.fileIterator.next();
			this.addContainingMethods(newFile);
		}

		Method filteredMethod = this.entityFilter.filterMethod(result);
		return IdentifierExtractor.generateContainer(filteredMethod);
	}

	/**
	 * Percorre funções e classes externas com o objetivo de identificar métodos
	 * em classes
	 * 
	 * @param aFile
	 */
	private void addContainingMethods(CPPFile aFile) {

		/**
		 * Percorre as funções procurando classes
		 */
		for (Func aFunc : aFile.getFunc()) {
			addContainingMethods(aFunc);
		}

		/**
		 * Percorre classes para adicionar os métodos
		 */
		for (CPPClass aClass : aFile.getClazz()) {
			addContainingMethods(aClass);
		}
	}

	/**
	 * Percorre funções procurando classes
	 *
	 * @param func
	 */
	private void addContainingMethods(Func func) {

		/**
		 * Percorre funções internas
		 */
		for (Func aFunc : func.getFunc()) {
			addContainingMethods(aFunc);
		}

		/**
		 * Percorre classes para adicionar os métodos
		 */
		for (CPPClass aClass : func.getClazz()) {
			addContainingMethods(aClass);
		}
	}

	/**
	 * Adiciona funções contidas em classes
	 *
	 * @param clazz
	 */
	private void addContainingMethods(CPPClass clazz) {

		/**
		 * Percorre funções das classes procurando mais classes
		 */
		for (Func aFunc : clazz.getFunc()) {
			addContainingMethods(aFunc);
		}

		/**
		 * Percorre classes internas para encontrar métodos
		 */
		for (CPPClass aClass : clazz.getClazz()) {
			addContainingMethods(aClass);
		}

		/**
		 * Percorre os métodos, adiciona esses métodos e procura novas classes
		 */
		for (Method aMethod : clazz.getMth()) {
			addContainingFunctions(aMethod);
			this.entitiesToAdd.add(aMethod);
		}
	}

	/**
	 * Adiciona métodos contidos em classes dentro de métodos
	 *
	 * @param method
	 */
	private void addContainingFunctions(Method method) {

		/**
		 * Percorre funções de métodos para encontrar classes e mais métodos
		 */
		for (Func aFunc : method.getFunc()) {
			addContainingMethods(aFunc);
		}

		/**
		 * Percorre classes para adicionar as funções, não métodos contidas nas
		 * classes
		 */
		for (CPPClass aClass : method.getClazz()) {
			addContainingMethods(aClass);
		}
	}
}