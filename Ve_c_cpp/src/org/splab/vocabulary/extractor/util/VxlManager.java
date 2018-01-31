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
	 * Return the comment fragment of the VXL file.
	 * 
	 * @param comment
	 *            The comment of the file.
	 * @return The comment fragment of the VXL file.
	 */
	public static String commentTag(CommentUnit comment, boolean scope) {

		/** Se o comentário estiver em alguma estrutura de bloco **/
		if (scope == true)
			return "\t\t\t<comm cntt=\"" + comment.toString() + "\"/>" + "\n";

		/** se o comentário for global **/
		return "\t\t<comm cntt=\"" + comment.toString() + "\"/>" + "\n";
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
	 * @return The stat enum fragment of the VXL file.
	 */
	public static String startEnum(String enumName, int sloc, boolean scope) {

		/** Se a enum estiver dentro de uma estrutura de bloco **/
		if (scope == true)
			return "\t\t\t<enum name=\"" + enumName + "\" sloc=\"" + sloc + "\">" + "\n";

		/** se a enum for global **/
		return "\t\t<enum name=\"" + enumName + "\" sloc=\"" + sloc + "\">" + "\n";
	}

	/**
	 * Return the enum fragment of the VXL file.
	 * 
	 * @return The enum fragment of the VXL file.
	 */
	public static String endEnum(boolean scope) {

		/** Se a enum estiver dentro de uma estrutura de bloco **/
		if (scope == true)
			return "\t\t\t</enum>" + "\n";

		/** se a enum for global **/
		return "\t\t</enum>" + "\n";
	}

	/**
	 * Return the constant fragment of the VXL file.
	 * 
	 * @param constantName
	 *            The constant name.
	 * @return
	 */
	public static String constant(String constantName, boolean scope) {

		/**
		 * Se a enum que contem esta estiver dentro de uma estrutura de bloco
		 **/
		if (scope == true)
			return "\t\t\t\t<const name=\"" + constantName + "\"/>" + "\n";

		/** Se a enum que contem for global **/
		return "\t\t\t<const name=\"" + constantName + "\"/>" + "\n";
	}

	/**
	 * Return the field fragment of the VXL file.
	 * 
	 * @param Variable
	 *            Name The Global Variablen Name.
	 * @param access
	 *            The Global Variable visibility.
	 * @return The field fragment of the VXL file.
	 */
	public static String globalVariables(String fieldName, String access, String storage, int occurrences) {
		return "\t\t<gvar name=\"" + fieldName + "\" access=\"" + access + "\" storage=\"" + storage + "\" count=\""
				+ occurrences + "\"/>" + "\n";
	}

	public static String globalPrototip(String fieldName, String access, String storage) {
		return "\t\t<prttp name=\"" + fieldName + "\" access=\"" + access + "\" storage=\"" + storage + "\"/>" + "\n";
	}

	/**
	 * Return the start function fragment of the VXL file.
	 * 
	 * @param functionName
	 *            The function name.
	 * @param visibility
	 *            The function visibility.
	 * @param comment
	 *            The function comment.
	 * @return The start function fragment of the VXL file.
	 */
	public static String startFuntion(String functionName, String access, String storage, boolean inner, int sloc) {
		String interna = inner ? "y" : "n"; // se a função for interna é igual a
											// "y"

		return "\t\t<func name=\"" + functionName + "\" access=\"" + access + "\" storage=\"" + storage + "\" inn=\""
				+ interna + "\" sloc=\"" + sloc + "\">" + "\n";
	}

	/**
	 * Return the end function fragment of the VXl file.
	 * 
	 * @return The end function fragment of the VXL file.
	 */
	public static String endFuntion() {
		return "\t\t</func>" + "\n";
	}

	/**
	 * Return the parameter fragment of the VXL file.
	 * 
	 * @param buffer
	 *            The buffer containing the parameters of the function.
	 * @param parameterName
	 *            The parameter name.
	 */
	public static void parameter(StringBuffer buffer, String parameterName) {
		buffer.append("\t\t\t<param name=\"" + parameterName + "\"/>" + "\n");
	}

	/**
	 * Return the local variable fragment of the VXL file.
	 * 
	 * @param localVariableName
	 *            The local variable name.
	 * @param occurrences
	 *            The frequency of occurrence of the variable.
	 * @return The local variable fragment of the VXL file.
	 */
	public static String localVariables(String fieldName, String access, String storage, int occurrences) {
		return "\t\t<lvar name=\"" + fieldName + "\" access=\"" + access + "\" storage=\"" + storage + "\" count=\""
				+ occurrences + "\"/>" + "\n";
	}

	public static String localPrototip(String fieldName, String access, String storage) {
		return "\t\t\t<prttp name=\"" + fieldName + "\" access=\"" + access + "\" storage=\"" + storage + "\"/>" + "\n";
	}

	/**
	 * Return the function call fragment of the VXL file.
	 * 
	 * @param func
	 *            call The name of the function that is called.
	 * @param occurrences
	 *            The frequency of occurrence of the function call.
	 * @return
	 */
	public static String functionCall(String callName, int occurrences) {
		return "\t\t\t<call name=\"" + callName + "\" count=\"" + occurrences + "\"/>" + "\n";
	}

	/**
	 * Return the literal fragment of the VXL file.
	 * 
	 * @param literal
	 *            The literal content.
	 * @param occurrences
	 *            The frequency of the literal is used at the code.
	 * @return The literal fragment of the VXL file.s
	 */
	public static String literal(String literal, int occurrences) {
		return "\t\t\t\t<lit cntt=\"" + literal + "\" count=\"" + occurrences + "\"/>" + "\n";
	}

	/**
	 * Return the start struct fragment of the VXL file.
	 * 
	 * @param structName
	 *            The struct name.
	 * @param sloc
	 *            The total LOC of the struct.
	 * @return The start struct fragment of the VXL file.
	 */
	public static String startStruct(String structName, int sloc, boolean scopeLocal) {
		if (scopeLocal == true)
			return "\t\t\t<strt name=\"" + structName + "\" sloc=\"" + sloc + "\">"
					+ "\n";

		return "\t\t<strt name=\"" + structName + "\" sloc=\"" + sloc + "\">" + "\n";
	}

	/**
	 * Return the struct fragment of the VXL file.
	 * 
	 * @return The struct fragment of the VXL file.
	 */
	public static String endStruct(boolean scopeLocal) {
		
		if (scopeLocal == true)
			return "\t\t\t</strt>" + "\n";

		return "\t\t</strt>" + "\n";
	}

	/**
	 * Return the struct members fragment of the VXL file.
	 * 
	 * @param membersName
	 *            The members name.
	 * @return
	 */
	public static String structMembers(String structMembersName, boolean scopeLocal) {

		if (scopeLocal == true)
			return "\t\t\t\t<strtMemb name=\"" + structMembersName + "\"/>" + "\n";

		return "\t\t\t<strtMemb name=\"" + structMembersName + "\"/>" + "\n";
	}

	/**
	 * Return the start union fragment of the VXL file.
	 * 
	 * @param unionName
	 *            The union name.
	 * @param sloc
	 *            The total LOC of the union.
	 * @return The start union fragment of the VXL file.
	 */
	public static String startUnion(String unionName, int sloc, boolean scopeLocal) {
		if (scopeLocal == true)
			return "\t\t\t<union name=\"" + unionName + "\" sloc=\"" + sloc + "\">"
					+ "\n";

		return "\t\t<union name=\"" + unionName + "\" sloc=\"" + sloc + "\">" + "\n";
	}

	/**
	 * Return the union fragment of the VXL file.
	 * 
	 * @return The union fragment of the VXL file.
	 */
	public static String endUnion(boolean scopeLocal) {
		
		if (scopeLocal == true)
			return "\t\t\t</union>" + "\n";

		return "\t\t</union>" + "\n";
	}

	/**
	 * Return the union members fragment of the VXL file.
	 * 
	 * @param membersName
	 *            The members name.
	 * @return
	 */
	public static String unionMembers(String unionMembersName, boolean scopeLocal) {
		
		if (scopeLocal == true)
			return "\t\t\t\t<unionMemb name=\"" + unionMembersName + "\"/>" + "\n";

		return "\t\t\t<unionMemb name=\"" + unionMembersName + "\"/>" + "\n";
	}

	/**
	 * Return the macro fragment of the VXL file.
	 * 
	 * @param macroName
	 *            The macro.
	 * @return
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
	 * @return
	 */
	public static String macroParameter(String macroParacmeterName) {
		return "\t\t\t<param name=\"" + macroParacmeterName + "\"/>" + "\n";
	}

	/**
	 * Return the error message members fragment of the VXL file.
	 * 
	 * @param message
	 *            The error menssage.
	 * @return
	 */
	public static String errorDirective(String error) {
		return "\t\t<error message=\"" + error + "\"/>" + "\n";
	}

	/**
	 * Return the include directives members fragment of the VXL file.
	 * 
	 * @param include
	 *            directives The include directives names.
	 * @return
	 */
	public static String includeDirective(String include) {
		return "\t\t<include name=\"" + include + "\"/>" + "\n";
	}

	/**
	 * Return the pragma directive message fragment of the VXL file.
	 * 
	 * @param membersName
	 *            The members name.
	 * @return
	 */
	public static String pragmaDirective(String pragma) {
		return "\t\t<pragma message=" + pragma + "/>" + "\n";
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
			PrintStream file = new PrintStream(new FileOutputStream(vxlFileName, false));
			file.print(vxl.toString());
			file.close();
			clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
