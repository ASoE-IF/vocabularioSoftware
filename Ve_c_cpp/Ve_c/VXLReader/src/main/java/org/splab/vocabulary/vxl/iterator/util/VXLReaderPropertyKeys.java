package org.splab.vocabulary.vxl.iterator.util;

/**
 * A collection of valid properties for VXLReader
 * 
 * @author gustavojss
 * @author Israel Gomes de Lima
 * 
 *         Modificado por Israel Gomes de Lima a partir da classe original feita
 *         por: gustavojss
 */
public class VXLReaderPropertyKeys {

	public static final String CONTAINER_TYPE = "containerType";

	public enum ContainerType {
		FILE, FUNCTION
	}

	public static final String INCLUDE_FUNCTION = "includeFunction";
	public static final String INCLUDE_ENUM = "includeEnum";
	public static final String INCLUDE_UNION = "includeUnion";
	public static final String INCLUDE_STRUCT = "includeStruct";
	public static final String INCLUDE_PARAMETER = "includeParameter";
	public static final String INCLUDE_LOCAL_VARIABLE = "includeLocalVar";
	public static final String INCLUDE_GLOBAL_VARIABLE = "includeGlobalVar";
	public static final String INCLUDE_FUNCTION_CALL = "includeFuncCall";
	public static final String INCLUDE_PROTOTYPE = "includePrototype";
	public static final String INCLUDE_LITERALS = "includeLiterals";
	public static final String INCLUDE_FIELD = "includeField";
	public static final String INCLUDE_CONSTANT = "includeConstant";
	public static final String INCLUDE_PRAGMA = "includePragma";
	public static final String INCLUDE_DIRECTIVE = "includeDirective";
	public static final String INCLUDE_ERROR = "includeError";
	public static final String INCLUDE_MACRO = "includeMacro";
	public static final String INCLUDE_COMMENTS = "includeComment";
}