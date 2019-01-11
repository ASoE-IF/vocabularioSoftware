package org.splab.vocabulary.vxl.iterator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.splab.vocabulary.vxl.iterator.javamodel.CFile;
import org.splab.vocabulary.vxl.iterator.javamodel.CProject;
import org.splab.vocabulary.vxl.iterator.javamodel.ContainerEntity;
import org.splab.vocabulary.vxl.iterator.util.EntityFilter;
import org.splab.vocabulary.vxl.iterator.util.VXLReaderPropertyKeys;

/**
 * An iterator over an entity collection, given by a java project. <br>
 * All entities are removed after returned by the iterator
 * 
 * @author gustavojss
 * @author Israel Gomes de Lima
 * 
 *         Modificado por Israel Gomes de Lima a partir da classe original feita
 *         por: gustavojss
 */

public abstract class VXLIterator implements Iterator<ContainerEntity> {

	protected CProject cProject;
	protected EntityFilter entityFilter;
	protected Iterator<CFile> fileIterator;

	protected List<Object> entitiesToAdd;

	/**
	 * Creates a new entity iterator with the given properties
	 * 
	 * @param props
	 *            a set of properties
	 */
	public VXLIterator(Properties props) {

		this.validateProperties(props);

		this.entityFilter = new EntityFilter();
		this.entityFilter.setProperties(includeFunction, includeEnum, includeUnion, includeStruct, includeParameter,
				includeLocalVar, includeGlobalVar, includeFuncCall, includePrototype, includeLiterals, includeField,
				includeConstant, includePragma, includeDirective, includeError, includeMacro, includeComment);
	}

	/**
	 * Creates a new iterator according to its container type, defined by the
	 * given properties
	 * 
	 * @param props
	 *            a set of properties
	 * @return a new iterator, according to its container type
	 */
	public static final VXLIterator createNewIterator(Properties props) {

		if (props.containsKey(VXLReaderPropertyKeys.CONTAINER_TYPE)) {

			String containerTypeProp = props.getProperty(VXLReaderPropertyKeys.CONTAINER_TYPE).toString();

			if (containerTypeProp.equalsIgnoreCase(VXLReaderPropertyKeys.ContainerType.FILE.toString())) {
				return new VXLFilesIterator(props);
			} else if (containerTypeProp.equalsIgnoreCase(VXLReaderPropertyKeys.ContainerType.FUNCTION.toString())) {
				return new VXLFunctionsIterator(props);
			}
		}

		throw new IllegalArgumentException("Container Type is not specified");
	}

	/**
	 * Sets the c project used by this iterator to obtain the project
	 * entities
	 * 
	 * @param newCProject
	 *            the c project
	 */
	public void setCProject(CProject newCProject) {

		this.cProject = newCProject;
		this.fileIterator = this.cProject.getCFile().iterator();

		this.entitiesToAdd = new LinkedList<Object>();
	}

	public boolean hasNext() {
		return this.fileIterator.hasNext() || (this.entitiesToAdd.size() > 0);
	}

	public void remove() {
	} // Do Nothing

	// ----------------------------- Information Extraction Properties
	// -----------------------------
	protected boolean includeFile = false, includeFunction = false, includeEnum = false, includeUnion = false,
			includeStruct = false, includeParameter = false, includeLocalVar = false, includeGlobalVar = false,
			includeFuncCall = false, includePrototype = false, includeLiterals = false, includeField = false,
			includeConstant = false, includePragma = false, includeDirective = false, includeError = false,
			includeMacro = false, includeComment = false;

	private boolean checkProperties(Properties props, String key) {
		return props.containsKey(key) ? props.getProperty(key).toString().equalsIgnoreCase(Boolean.TRUE.toString())
				: false;
	}

	/**
	 * Validate the reading configurations by checking the properties
	 * 
	 * @param props
	 *            Representation of a .properties file
	 */
	private void validateProperties(Properties props) {

		this.includeFunction = checkProperties(props, VXLReaderPropertyKeys.INCLUDE_FUNCTION);
		this.includeEnum = checkProperties(props, VXLReaderPropertyKeys.INCLUDE_ENUM);
		this.includeUnion = checkProperties(props, VXLReaderPropertyKeys.INCLUDE_UNION);
		this.includeStruct = checkProperties(props, VXLReaderPropertyKeys.INCLUDE_STRUCT);
		this.includeParameter = checkProperties(props, VXLReaderPropertyKeys.INCLUDE_PARAMETER);
		this.includeLocalVar = checkProperties(props, VXLReaderPropertyKeys.INCLUDE_LOCAL_VARIABLE);
		this.includeGlobalVar = checkProperties(props, VXLReaderPropertyKeys.INCLUDE_GLOBAL_VARIABLE);
		this.includeFuncCall = checkProperties(props, VXLReaderPropertyKeys.INCLUDE_FUNCTION_CALL);
		this.includePrototype = checkProperties(props, VXLReaderPropertyKeys.INCLUDE_PROTOTYPE);
		this.includeLiterals = checkProperties(props, VXLReaderPropertyKeys.INCLUDE_LITERALS);
		this.includeField = checkProperties(props, VXLReaderPropertyKeys.INCLUDE_FIELD);
		this.includeConstant = checkProperties(props, VXLReaderPropertyKeys.INCLUDE_CONSTANT);
		this.includePragma = checkProperties(props, VXLReaderPropertyKeys.INCLUDE_PRAGMA);
		this.includeDirective = checkProperties(props, VXLReaderPropertyKeys.INCLUDE_DIRECTIVE);
		this.includeError = checkProperties(props, VXLReaderPropertyKeys.INCLUDE_ERROR);
		this.includeMacro = checkProperties(props, VXLReaderPropertyKeys.INCLUDE_MACRO);
		this.includeComment = checkProperties(props, VXLReaderPropertyKeys.INCLUDE_COMMENTS);
	}
}