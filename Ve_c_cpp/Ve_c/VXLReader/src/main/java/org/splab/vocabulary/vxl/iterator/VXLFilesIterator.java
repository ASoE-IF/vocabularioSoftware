package org.splab.vocabulary.vxl.iterator;

import java.util.Properties;

import org.splab.vocabulary.vxl.iterator.javamodel.CFile;
import org.splab.vocabulary.vxl.iterator.javamodel.CProject;
import org.splab.vocabulary.vxl.iterator.javamodel.ContainerEntity;
import org.splab.vocabulary.vxl.iterator.util.IdentifierExtractor;

/**
 * An iterator over a class collection, given by a c project. <br>
 * All entity returned by this iterator will be a file specification, and will
 * be removed after returned
 * 
 * @author Israel Gomes de Lima
 */
public class VXLFilesIterator extends VXLIterator {

	public VXLFilesIterator(Properties props) {
		super(props);
	}

	@Override
	public void setCProject(CProject cProject) {
		super.setCProject(cProject);

		while (this.entitiesToAdd.isEmpty() && this.fileIterator.hasNext()) {
			this.entitiesToAdd.add(this.fileIterator.next());
		}
	}

	/**
	 * Retorna o próximo arquivo e o remove do iterator
	 */
	public ContainerEntity next() {

		Object result = this.entitiesToAdd.remove(0);

		while (this.entitiesToAdd.isEmpty() && this.fileIterator.hasNext()) {
			this.entitiesToAdd.add(this.fileIterator.next());
		}

		CFile filteredFile = this.entityFilter.filterFile((CFile) result);

		return IdentifierExtractor.generateContainer(filteredFile);
	}
}