package org.splab.vocabulary.vxl.iterator;

import java.util.Properties;

import org.splab.vocabulary.vxl.iterator.javamodel.CFile;
import org.splab.vocabulary.vxl.iterator.javamodel.CProject;
import org.splab.vocabulary.vxl.iterator.javamodel.ContainerEntity;
import org.splab.vocabulary.vxl.iterator.javamodel.Func;
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
	public void setCProject(CProject cProject) {

		super.setCProject(cProject);

		while (this.entitiesToAdd.isEmpty() && this.fileIterator.hasNext())
			this.addContainingFunctions(this.fileIterator.next());
	}

	/**
	 * Retorna uma função e a remove do iterator
	 */
	public ContainerEntity next() {

		Func result = (Func) this.entitiesToAdd.remove(0);

		while ((this.entitiesToAdd.size() == 0) && this.fileIterator.hasNext()) {
			CFile newFile = this.fileIterator.next();
			this.addContainingFunctions(newFile);
		}

		Func filteredMethod = this.entityFilter.filterFunc(result);
		return IdentifierExtractor.generateContainer(filteredMethod);
	}

	/**
	 * Adiciona um função de algum arquivo
	 * 
	 * @param aFile
	 */
	private void addContainingFunctions(CFile aFile) {

		/**
		 * Adiciona funções externas
		 */
		for (Func aFunc : aFile.getFunc()) {
			addContainingFunctions(aFunc);
			this.entitiesToAdd.add(aFunc);
		}
	}

	/**
	 * Adds all methods from the class and inner classes
	 */
	private void addContainingFunctions(Func func) {

		/**
		 * Adiciona funções internas
		 */
		for (Func aFunc : func.getFunc()) {
			addContainingFunctions(aFunc);
			this.entitiesToAdd.add(aFunc);
		}
	}
}