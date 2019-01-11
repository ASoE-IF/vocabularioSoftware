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
public class VXLClassesIterator extends VXLIterator {

	public VXLClassesIterator(Properties props) {
		super(props);
	}

	@Override
	public void setCProject(CPPProject cppProject) {
		super.setCProject(cppProject);

		while (this.entitiesToAdd.isEmpty() && this.fileIterator.hasNext()) {
			this.addContainingClass(this.fileIterator.next());
		}
	}

	/**
	 * Retorna uma classe e a remove do iterator
	 */
	public ContainerEntity next() {

		CPPClass result = (CPPClass) this.entitiesToAdd.remove(0);

		while ((this.entitiesToAdd.size() == 0) && this.fileIterator.hasNext()) {
			CPPFile newFile = this.fileIterator.next();
			this.addContainingClass(newFile);
		}

		CPPClass filteredClass = this.entityFilter.filterClass(result);
		return IdentifierExtractor.generateContainer(filteredClass);
	}

	/**
	 * Adiciona uma classe de algum arquivo
	 * 
	 * @param aFile
	 */
	private void addContainingClass(CPPFile aFile) {

		/**
		 * Adiciona classes externas
		 */
		for (CPPClass aClass : aFile.getClazz()) {
			addContainingClass(aClass);
			this.entitiesToAdd.add(aClass);
		}

		/**
		 * Adiciona classes contidas em funções
		 */
		for (Func aFunc : aFile.getFunc()) {
			addContainingClass(aFunc);
		}
	}

	/**
	 * Adiciona todas as classes internas
	 *
	 * @param clazz
	 */
	private void addContainingClass(CPPClass clazz) {

		/**
		 * Adiciona classes internas
		 */

		for (CPPClass aClass : clazz.getClazz()) {
			addContainingClass(aClass);
			this.entitiesToAdd.add(aClass);
		}

		/**
		 * Percorre métodos internos para recuperação das classes
		 */
		for (Method aMethod : clazz.getMth()) {
			addContainingClass(aMethod);
		}

		/**
		 * Percorre funções internas para recuperação das classes
		 */
		for (Func aFunc : clazz.getFunc()) {
			addContainingClass(aFunc);
		}
	}

	/**
	 * Adiciona todas as classes internas aos métodos
	 *
	 * @param method
	 */
	private void addContainingClass(Method method) {

		/**
		 * Adiciona classes contidas em métodos
		 */
		for (CPPClass aClass : method.getClazz()) {
			addContainingClass(aClass);
			this.entitiesToAdd.add(aClass);
		}

		/**
		 * Percorre funções internas para recuperação das classes
		 */
		for (Func aFunc : method.getFunc()) {
			addContainingClass(aFunc);
		}
	}

	/**
	 * Adiciona todas as classes internas as funções
	 *
	 * @param function
	 */
	private void addContainingClass(Func function) {

		/**
		 * Adiciona classes contidas em funções
		 */
		for (CPPClass aClass : function.getClazz()) {
			addContainingClass(aClass);
			this.entitiesToAdd.add(aClass);
		}

		/**
		 * Percorre funções internas para recuperação das classes
		 */
		for (Func aFunc : function.getFunc()) {
			addContainingClass(aFunc);
		}
	}
}