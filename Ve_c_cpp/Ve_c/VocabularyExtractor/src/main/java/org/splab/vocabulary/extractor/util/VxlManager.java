package org.splab.vocabulary.extractor.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * This class is responsible for managing the VXL file content.
 * 
 * @author Tercio de Melo, Catharine Quintans Modificado por: Israel Gomes de
 *         Lima Para funcionamento no presente extrator
 */
public abstract class VxlManager {
	static StringBuffer vxl = new StringBuffer();

	/**
	 * Append the VXL fragment given as parameters to the vxl file.
	 * 
	 * @param vxlFragment
	 *            The fragment that will be included at the VXL file.
	 */
	public static void appendVXLFragment(StringBuffer vxlFragment) {
		vxl.append(vxlFragment);
	}

	/**
	 * Append the VXL fragment given as parameters to the vxl file.
	 * 
	 * @param vxlFragment
	 *            The fragment that will be included at the VXL file.
	 */
	public static void appendVXLFragment(String vxlFragment) {
		vxl.append(vxlFragment);
	}

	/**
	 * Return the VXL fragment format the content that will be add at the VXL
	 * file.
	 * 
	 * @param projectName
	 *            The project name.
	 * @param projectRevision
	 *            The project revision.
	 * @return The content to add at the VXL file.
	 */
	public static String startProject(String projectName, String projectRevision) {
		return "<c-project id=\"default\" name=\"" + projectName + "\" revision=\"" + projectRevision + "\">" + "\n";
	}

	/**
	 * Return the end project fragment of the VXL file.
	 * 
	 * @return The end project fragment of the VXL file.
	 */
	public static String endProject() {
		return "</c-project>";
	}

	/**
	 * Return the start file fragment of the VXL file.
	 * 
	 * @param fileName
	 *            The file name.
	 * @param comment
	 *            All the file comments.
	 * @param sloc
	 *            The number of LOC of the file.
	 * @return The start file fragment of the VXL file.
	 */
	public static String startFile(String fileName, int sloc) {
		return "\t<c-file name=\"" + fileName + "\" sloc=\"" + sloc + "\">" + "\n";
	}

	/**
	 * Return the end file fragment of the VXL file.
	 * 
	 * @return The end file fragment of the VXL file.
	 */
	public static String endFile() {
		return "\t</c-file>" + "\n";
	}

	/**
	 * Return the macro fragment of the VXL file.
	 * 
	 * @param macroName
	 *            The macro.
	 * @return The macro fragment of the VXL file.
	 */
	public static String macro(String macroName) {
		return "\t\t<macro name=\"" + macroName + "\"/>" + "\n";
	}

	public static String startFunctionStyleMacro(String macroName) {
		return "\t\t<macro name=\"" + macroName + "\">" + "\n";
	}

	public static String endFunctionStyleMacro() {
		return "\t\t</macro>" + "\n";
	}

	/**
	 * Return the macroParameter fragment of the VXL file.
	 * 
	 * @param macro
	 *            parameter The macro parameter.
	 * @return The function style macro parameter fragment of the VXL file.
	 */
	public static String macroParameter(String macroParacmeterName) {
		return "\t\t\t<param name=\"" + macroParacmeterName + "\"/>" + "\n";
	}

	/**
	 * Return the error message members fragment of the VXL file.
	 * 
	 * @param message
	 *            The error menssage.
	 * @return The start error directive of the VXL file.
	 */
	public static String errorDirective(String error) {
		return "\t\t<error message=" + error + "/>" + "\n";
	}

	/**
	 * Return the include directives members fragment of the VXL file.
	 * 
	 * @param include
	 *            directives The include directives names.
	 * @return The include directive fragment of the VXL file.
	 */
	public static String includeDirective(String include) {
		return "\t\t<include name=\"" + include + "\"/>" + "\n";
	}

	/**
	 * Return the pragma directive message fragment of the VXL file.
	 * 
	 * @param membersName
	 *            The members name.
	 * @return The pragma directive fragment of the VXL file.
	 */
	public static String pragmaDirective(String pragma) {
		return "\t\t<pragma message=" + pragma + "/>" + "\n";
	}

	/**
	 * Return the comment fragment of the VXL file.
	 * 
	 * @param comment
	 *            The comment of the file.
	 * @param indentationLevel
	 *            The Comment VXL File Indentation Level.
	 * @return The comment fragment of the VXL file.
	 */
	public static String commentTag(CommentUnit comment, int indentationLevel) {
		String indentation = getIndentation(indentationLevel);
		return indentation + "<comm cntt=\"" + comment.toString() + "\"/>" + "\n";
	}

	/**
	 * Return the global variable fragment of the VXL file.
	 * 
	 * @param variableName
	 *            Name The Global Variable Name.
	 * @param access
	 *            The Global Variable Access.
	 * @param storage
	 *            The Global Variable Storage Class.
	 * @param ocurrences
	 *            The Global Variable Occurrences.
	 * @param indentationLevel
	 *            The Global Variable VXL File Indentation Level.
	 * @return The global variable fragment of the VXL file.
	 */
	public static String globalVariable(String variableName, String access, String storage, int occurrences,
			int indentationLevel) {
		String indentation = getIndentation(indentationLevel);
		return indentation + "<gvar name=\"" + variableName + "\" access=\"" + access + "\" storage=\"" + storage
				+ "\" count=\"" + occurrences + "\"/>" + "\n";
	}

	/**
	 * Return the function prototype fragment of the VXL file.
	 * 
	 * @param prototypeName
	 *            Name The Function ProtoType Name.
	 * @param access
	 *            The Function Prototype Access.
	 * @param storage
	 *            The Function Prototype Storage Class.
	 * @param indentationLevel
	 *            The Function Prototype VXL File Indentation Level.
	 * @return The function prototype fragment of the VXL file.
	 */

	public static String funtionPrototype(String prototypeName, String access, String storage, int indentationLevel) {
		String indentation = getIndentation(indentationLevel);
		return indentation + "<prttp name=\"" + prototypeName + "\" access=\"" + access + "\" storage=\"" + storage
				+ "\"/>" + "\n";
	}

	/**
	 * Return the literal fragment of the VXL file.
	 * 
	 * @param literal
	 *            The literal content.
	 * @param occurrences
	 *            The frequency of the literal is used at the code.
	 * @param indentationLevel
	 *            The literal VXL File Indentation Level.
	 * @return The literal fragment of the VXL file.s
	 */
	public static String literal(String literal, int occurrences, int indentationLevel) {
		String indentation = getIndentation(indentationLevel);
		return indentation + "<lit cntt=\"" + literal + "\" count=\"" + occurrences + "\"/>" + "\n";
	}

	/**
	 * Return the start enum fragment of the VXL file.
	 * 
	 * @param enumName
	 *            The enum name.
	 * @param enumComment
	 *            All the enum comments.
	 * @param sloc
	 *            The total LOC of the enum.
	 * @param indentationLevel
	 *            The enum VXL File Indentation Level.
	 * @return The start enum fragment of the VXL file.
	 */
	public static String startEnum(String enumName, int sloc, int indentationLevel) {
		String indentation = getIndentation(indentationLevel);
		return indentation + "<enum name=\"" + enumName + "\" sloc=\"" + sloc + "\">" + "\n";
	}

	/**
	 * Return the enum fragment of the VXL file.
	 * 
	 * @param indentationLevel
	 *            The enum VXL File Indentation Level.
	 * @return The end enum fragment of the VXL file.
	 */
	public static String endEnum(int indentationLevel) {
		String indentation = getIndentation(indentationLevel);
		return indentation + "</enum>" + "\n";
	}

	/**
	 * Return the constant fragment of the VXL file.
	 * 
	 * @param constantName
	 *            The constant name.
	 * @param indentationLevel
	 *            The constant VXL File Indentation Level.
	 * @return
	 */
	public static String constant(String constantName, int indentationLevel) {
		String indentation = getIndentation(indentationLevel);
		return indentation + "<const name=\"" + constantName + "\"/>" + "\n";
	}

	/**
	 * Return the fields fragment of the VXL file.
	 * 
	 * @param fieldName
	 *            The members name.
	 * @param indentationLevel
	 *            The field VXL File Indentation Level.
	 * @return The field fragment of the VXL file.
	 */
	public static String field(String fieldName, int indentationLevel) {
		String indentation = getIndentation(indentationLevel);
		return indentation + "<field name=\"" + fieldName + "\"/>" + "\n";
	}

	/**
	 * Return the start struct fragment of the VXL file.
	 * 
	 * @param structName
	 *            The struct name.
	 * @param sloc
	 *            The total LOC of the struct.
	 * @param indentationLevel
	 *            The struct VXL File Indentation Level.
	 * @return The start struct fragment of the VXL file.
	 */
	public static String startStruct(String structName, int sloc, int indentationLevel) {
		String indentation = getIndentation(indentationLevel);
		return indentation + "<strt name=\"" + structName + "\" sloc=\"" + sloc + "\">" + "\n";
	}

	/**
	 * Return the struct fragment of the VXL file.
	 * 
	 * @param indentationLevel
	 *            The struct VXL File Indentation Level.
	 * @return The end struct fragment of the VXL file.
	 */
	public static String endStruct(int indentationLevel) {
		String indentation = getIndentation(indentationLevel);
		return indentation + "</strt>" + "\n";
	}

	/**
	 * Return the start union fragment of the VXL file.
	 * 
	 * @param unionName
	 *            The union name.
	 * @param sloc
	 *            The total LOC of the union.
	 * @param indentationLevel
	 *            The union VXL File Indentation Level.
	 * @return The start union fragment of the VXL file.
	 */
	public static String startUnion(String unionName, int sloc, int indentationLevel) {
		String indentation = getIndentation(indentationLevel);
		return indentation + "<union name=\"" + unionName + "\" sloc=\"" + sloc + "\">" + "\n";
	}

	/**
	 * Return the union fragment of the VXL file.
	 * 
	 * @param indentationLevel
	 *            The union VXL File Indentation Level.
	 * @return The union fragment of the VXL file.
	 */
	public static String endUnion(int indentationLevel) {
		String indentation = getIndentation(indentationLevel);
		return indentation + "</union>" + "\n";
	}

	/**
	 * Return the start function fragment of the VXL file.
	 * 
	 * @param functionName
	 *            The function name.
	 * @param access
	 *            The function access.
	 * @param storage
	 *            The function class storage.
	 * @param sloc
	 *            The number of LOC of the function.
	 * @param indentationLevel
	 *            The function VXL File Indentation Level.
	 * @return The start function fragment of the VXL file.
	 */
	public static String startFunction(String functionName, String access, String storage, boolean inner, int sloc,
			int indentationLevel) {
		String indentation = getIndentation(indentationLevel);
		String interna = inner ? "y" : "n";

		return indentation + "<func name=\"" + functionName + "\" access=\"" + access + "\" storage=\"" + storage
				+ "\" inn=\"" + interna + "\" sloc=\"" + sloc + "\">" + "\n";
	}

	/**
	 * Return the end function fragment of the VXl file.
	 * 
	 * @param indentationLevel
	 *            The function VXL File Indentation Level.
	 * @return The end function fragment of the VXL file.
	 */
	public static String endFuntion(int indentationLevel) {
		String indentation = getIndentation(indentationLevel);
		return indentation + "</func>" + "\n";
	}

	/**
	 * Return the parameter fragment of the VXL file.
	 * 
	 * @param buffer
	 *            The buffer containing the parameters of the function.
	 * @param parameterName
	 *            The parameter name.
	 * @param indentationLevel
	 *            The function parameter VXL File Indentation Level.
	 */
	public static void parameter(StringBuffer buffer, String parameterName, int indentationLevel) {
		String indentation = getIndentation(indentationLevel);
		buffer.append(indentation + "<param name=\"" + parameterName + "\"/>" + "\n");
	}

	/**
	 * Return the local variable fragment of the VXL file.
	 * 
	 * @param variableName
	 *            Name The Global Variable Name.
	 * @param access
	 *            The local Variable Access.
	 * @param storage
	 *            The local Variable Storage Class.
	 * @param occurrences
	 *            The local Variable Occurrences.
	 * @param indentationLevel
	 *            The local Variable VXL File Indentation Level.
	 * @return The local variable fragment of the VXL file.
	 */
	public static String localVariable(String variableName, String access, String storage, int occurrences,
			int indentationLevel) {
		String indentation = getIndentation(indentationLevel);
		return indentation + "<lvar name=\"" + variableName + "\" access=\"" + access + "\" storage=\"" + storage
				+ "\" count=\"" + occurrences + "\"/>" + "\n";
	}

	/**
	 * Return the function call fragment of the VXL file.
	 * 
	 * @param funcCallName
	 *            call The name of the function that is called.
	 * @param occurrences
	 *            The frequency of occurrence of the function call.
	 * @param indentationLevel
	 *            The function call VXL File Indentation Level.
	 * @return
	 */
	public static String functionCall(String funcCallName, int occurrences, int indentationLevel) {
		String indentation = getIndentation(indentationLevel);
		return indentation + "<call name=\"" + funcCallName + "\" count=\"" + occurrences + "\"/>" + "\n";
	}

	/**
	 * Entity indentation
	 */
	private static String getIndentation(int indentationLevel) {

		String indentation = "";
		for (int countCharacter = 0; countCharacter < indentationLevel; countCharacter++) {
			indentation += '\t';
		}

		return indentation;
	}

	/**
	 * Clear the content of the VXL file.
	 */
	private static void clear() {
		vxl = new StringBuffer();
	}

	/**
	 * Save the VXL file.
	 * 
	 * @param vxlFileName
	 *            The VXL file name.
	 */
	public static void save(String vxlFileName) {
		try {
			PrintStream file = new PrintStream(new FileOutputStream(vxlFileName, false), true, "UTF-8");
			file.print(vxl.toString());
			file.close();
			clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
