package org.splab.vocabulary.extractor.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.List;

/**
 * This class is responsible for managing the VXL file content.
 * @author Tercio de Melo, Catharine Quintans
 */
public abstract class VxlManager {
	static StringBuffer vxl = new StringBuffer();
	
	/**
	 * Append the VXL fragment given as parameters to the vxl file.
	 * @param vxlFragment The fragment that will be included at the VXL file.
	 */
	public static void appendVXLFragment(StringBuffer vxlFragment) {
		vxl.append(vxlFragment);
	}
	
	/**
	 * Append the VXL fragment given as parameters to the vxl file.
	 * @param vxlFragment The fragment that will be included at the VXL file.
	 */
	public static void appendVXLFragment(String vxlFragment) {
		vxl.append(vxlFragment);
	}
	 
	/**
	 * Return the VXL fragment format the content that will be add at the VXL file.
	 * @param projectName The project name.
	 * @param projectRevision The project revision.
	 * @return The content to add at the VXL file.
	 */
	public static String startProject(String projectName, String projectRevision) {
		return "<java-project id=\"default\" name=\"" + projectName + "\" revision=\"" + projectRevision + "\">" + "\n";
	}
	
	/**
	 * Return the end project fragment of the VXL file.
	 * @return The end project fragment of the VXL file.
	 */
	public static String endProject() {
		return "</java-project>";
	}
	
	/**
	 * Return the start package fragment of the VXL file.
	 * @param packageName The package name.
	 * @return The start package fragment of the VXL file.
	 */
	public static String startPackage(String packageName) {
		return "\t<pkg name=\"" + packageName + "\">" + "\n";
	}
	
	/**
	 * Return the end project fragment of the VXL file.
	 * @return The end project fragment of the VXL file.
	 */
	public static String endPackage() {
		return "\t</pkg>" + "\n";
	}
	
	/**
	 * Return the start class fragment of the VXL file.
	 * @param className The class name.
	 * @param isInterface A string indicating if the class is interface or not. Use 'y' if it's and 'n' otherwise. 
	 * @param isAbstract A string indicating if the class is abstract or not. Use 'y' if it's and 'n' otherwise.
	 * @param isInner A string indicating if the class is inner or not. Use 'y' if it's and 'n' otherwise.
	 * @param comment All the class comments. 
	 * @param sloc The number of LOC of the class.
	 * @return The start class fragment of the VXL file.
	 */
	public static String startClass(String className, String isInterface, String isAbstract, String isInner, String comment, int sloc) {
		return "\t\t<class name=\"" + className + "\" intfc=\""+ isInterface + "\" abs=\"" + isAbstract + "\" inn=\"" + isInner + 
			"\" sloc=\"" + sloc + "\" jdoc=\"" + comment + "\">" + "\n";
	}
	
	/**
	 * Return the comment fragment of the VXL file.
	 * @param comment The comment of the class.
	 * @return The comment fragment of the VXL file.
	 */
	public static String commentTag(CommentUnit comment) {
		return "\t\t\t<comm cntt=\"" + comment.toString() + "\"/>\n";
	}
	
	/**
	 * Return the end class fragment of the VXL file.
	 * @return The end class fragment of the VXL file.
	 */
	public static String endClass() {
		return "\t\t</class>" + "\n";
	}
	
	/**
	 * Return the start enum fragment of the VXL file.
	 * @param enumName The enum name.
	 * @param enumComment All the enum comments.
	 * @param sloc The total LOC of the enum.
	 * @return The stat enum fragment of the VXL file.
	 */
	public static String startEnum(String enumName, String enumComment, int sloc) {
		return "\t\t<enum name=\"" + enumName + "\" sloc=\"" + sloc + "\" jdoc=\"" + enumComment + "\">" + "\n";
	}
	
	/**
	 * Return the enum fragment of the VXL file.
	 * @return The enum fragment of the VXL file.
	 */
	public static String endEnum() {
		return "\t\t</enum>" + "\n";
	}
	
	/**
	 * Return the constant fragment of the VXL file.
	 * @param constantName The constant name.
	 * @param visibility The constant visibility,
	 * @param comment All the constant comments.
	 * @return
	 */
	public static String constant(String constantName, String visibility, String comment) {
		return "\t\t\t<const name=\"" + constantName 	+ "\" access=\"" + visibility + "\" jdoc=\"" + comment + "\"/>" + "\n";
	}
	
	/**
	 * Return the field fragment of the VXL file.
	 * @param fieldName The field name.
	 * @param visibility The field visibility.
	 * @param comment All the field comments.
	 * @return The field fragment of the VXL file.
	 */
	public static String field(String fieldName, String visibility, String comment) {
		return "\t\t\t<field name=\"" + fieldName	+ "\" access=\"" + visibility + "\" jdoc=\"" + comment + "\"/>" + "\n";
	}
	
	/**
	 * Return the start method fragment of the VXL file.
	 * @param methodName The method name.
	 * @param visibility The method visibility.
	 * @param comment The method comment.
	 * @return The start method fragment of the VXL file.
	 */
	public static String startMethod(String methodName, String visibility, String comment) {
		return "\t\t\t<mth name=\"" + methodName + "\" access=\"" + visibility + "\" jdoc=\"" + comment + "\">" + "\n";
	}
	
	/**
	 * Return the method's comment fragment of the VXL file.
	 * @param buffer The buffer containing all the comments of the method.
	 * @param comments The comments of the method.
	 */
	public static void methodComment(StringBuffer buffer, List<String> comments) {
		for(String comment : comments)
			buffer.append("\t\t\t\t<comm cntt=\"" + comment + "\"/>" + "\n");
	}
	
	/**
	 * Return the parameter fragment of the VXL file.
	 * @param buffer The buffer containing the parameters of the method.
	 * @param parameterName The parameter name.
	 */
	public static void parameter(StringBuffer buffer, String parameterName) {
		buffer.append("\t\t\t\t<param name=\"" + parameterName + "\"/>" + "\n");
	}
	
	/**
	 * Return the local variable fragment of the VXL file.
	 * @param localVariableName The local variable name.
	 * @param occurrences The frequency of occurrence of the variable.
	 * @return The local variable fragment of the VXL file.
	 */
	public static String localVariable(String localVariableName, int occurrences) {
		return "\t\t\t\t<lvar name=\"" + localVariableName +"\" count=\"" + occurrences +"\"/>" + "\n";
	}
	
	/**
	 * Return the method invocation fragment of the VXL file.
	 * @param mthInvocation The name of the method that is called. 
	 * @param occurrences The frequency of occurrence of the method invocation.
	 * @return
	 */
	public static String methodInvocation(String mthInvocation, int occurrences) {
		return "\t\t\t\t<mthinv name=\"" + mthInvocation +"\" count=\"" + occurrences +"\"/>" + "\n";
	}
	
	/**
	 * Return the literal fragment of the VXL file.
	 * @param literal The literal content.
	 * @param occurrences The frequency of the literal is used at the code. 
	 * @return The literal fragment of the VXL file.s
	 */
	public static String literal(String literal, int occurrences) {
		return "\t\t\t\t<lit cntt=\"" + literal + "\" count=\"" + occurrences +"\"/>" + "\n";
	}
	
	/**
	 * Return the end method fragment of the VXl file.
	 * @return The end method fragment of the VXL file.
	 */
	public static String endMethod() {
		return "\t\t\t</mth>" + "\n";
	}
	
	/**
	 * Clear the content of the VXL file.
	 */
	private static void clear() {
		vxl = new StringBuffer();
	}
		
	/**
	 * Save the VXL file. 
	 * @param vxlFileName The VXL file name.
	 */
	public static void save(String vxlFileName) {
		try {
			// PrintStream file = new PrintStream(new FileOutputStream(vxlFileName, false));
			// file.print(vxl.toString());
			// file.close();
			OutputStream file = new FileOutputStream(vxlFileName);
			OutputStreamWriter bufferedWriter = new OutputStreamWriter(file, "UTF8");
			bufferedWriter.write(vxl.toString());
			bufferedWriter.close();
			clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}