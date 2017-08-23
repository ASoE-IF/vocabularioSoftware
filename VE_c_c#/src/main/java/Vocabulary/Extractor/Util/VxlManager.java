package Vocabulary.Extractor.Util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public abstract class VxlManager
{
	static StringBuffer vxl = new StringBuffer();
	
	public static void appendVXLFragment(StringBuffer vxlFragment)
	{
		vxl.append(vxlFragment);
	}
	
	public static void appendVXLFragment(String vxlFragment)
	{
		vxl.append(vxlFragment);
	}
	
	public static String literal(String literal, int occurrences) {
		return "\t\t\t\t<lit cntt=\"" + literal + "\" count=\"" + occurrences +"\"/>" + "\n";
	}
	
	public static String startProject(String projectName, String projectRevision)
	{
		return "<C-Project id=\"default\" name=\"" + projectName + "\" revision=\"" + projectRevision + "\">" + "\n";
	}
	
	public static String endProject()
	{
		return "</C-Project>";
	}
	
	public static String commentTag(CommentUnit comment, boolean function)
	{
		return function ? "\t\t\t<comm cntt=\"" + comment.toString() + "\"/>\n"
				: "\t\t<comm cntt=\"" + comment.toString() + "\"/>\n";
	}
	
	public static String startFile(String fileName, int sloc)
	{
		return "\t<C-File name=\"" + fileName + "\" sloc=\"" + sloc + "\">" + "\n";
	}
	
	public static String endFile()
	{
		return "\t</C-File>" + "\n";
	}
	
	public static String globalVariables(String fieldName, String visibility, String comment)
	{
		return "\t\t<GlobalVar name=\"" + fieldName	+ "\" access=\"" + visibility + "\" jdoc=\"" + comment + "\"/>" + "\n";
	}
	
	public static String localVariable(String localVariableName, int occurrences)
	{
		return "\t\t\t<lvar name=\"" + localVariableName +"\" count=\"" + occurrences +"\"/>" + "\n";
	}
	
	public static String startFuntion(String methodName, String visibility)
	{
		return "\t\t<function name=\"" + methodName + "\" access=\"" + visibility + "\">" + "\n";
	}
	
	public static String endFuntion()
	{
		return "\t\t</function>" + "\n";
	}


	public static void parameter(StringBuffer buffer, String parameterName)
	{
		buffer.append("\t\t\t<param name=\"" + parameterName + "\"/>" + "\n");
	}
	
	public static String startStruct(String structName, String visibility, int sloc, boolean scopeLocal)
	{
		return scopeLocal == true ? "\t\t\t<struct name=\"" + structName + "\" access=\"" + visibility + "\" sloc=\"" + sloc + "\">" + "\n"
				: "\t\t<struct name=\"" + structName + "\" access=\"" + visibility + "\" sloc=\"" + sloc + "\">" + "\n";
	}
	
	public static String endStruct(boolean scopeLocal)
	{
		return scopeLocal == true ? "\t\t\t</struct>" + "\n" : "\t\t</struct>" + "\n";
	}
	
	public static String structMembers(String structMembersName, boolean scopeLocal)
	{
		return scopeLocal == true ? "\t\t\t\t<structMembers name=\"" + structMembersName +"\" count=\"" + 50 +"\"/>" + "\n"
				: "\t\t\t<structMembers name=\"" + structMembersName +"\" count=\"" + 50 +"\"/>" + "\n";
	}
	
	public static String startEnum(String enumName, String enumComment, int sloc, boolean scopeLocal)
	{
		return scopeLocal == true ? "\t\t\t<enum name=\"" + enumName + "\" sloc=\"" + sloc + "\" jdoc=\"" + enumComment + "\">" + "\n"
				: "\t\t<enum name=\"" + enumName + "\" sloc=\"" + sloc + "\" jdoc=\"" + enumComment + "\">" + "\n";
	}
		
	public static String endEnum(boolean scopeLocal)
	{
			return scopeLocal == true ? "\t\t\t</enum>" + "\n" : "\t\t</enum>" + "\n";
	}
	
	public static String constant(String constantName, String visibility, String comment, boolean scopeLocal)
	{
		return scopeLocal == true ? "\t\t\t\t<const name=\"" + constantName 	+ "\" access=\"" + visibility + "\" jdoc=\"" + comment + "\"/>" + "\n"
				: "\t\t\t<const name=\"" + constantName 	+ "\" access=\"" + visibility + "\" jdoc=\"" + comment + "\"/>" + "\n";
	}
	
	public static String startUnion(String structName, String visibility, String comment, boolean scopeLocal)
	{
		return scopeLocal == true ? "\t\t\t<union name=\"" + structName + "\" access=\"" + visibility + "\" jdoc=\"" + comment + "\">" + "\n"
				: "\t\t<union> name=\"" + structName + "\" access=\"" + visibility + "\" jdoc=\"" + comment + "\">" + "\n";
	}
	
	public static String endUnion(boolean scopeLocal)
	{
		return scopeLocal == true ? "\t\t\t</union>" + "\n" : "\t\t</union>" + "\n";
	}
	
	public static String unionMembers(String structMembersName, boolean scopeLocal)
	{
		return scopeLocal == true ? "\t\t\t\t<unionMembers name=\"" + structMembersName +"\" count=\"" + 50 +"\"/>" + "\n"
				: "\t\t\t<unionMembers name=\"" + structMembersName +"\" count=\"" + 50 +"\"/>" + "\n";
	}
	
	public static String functionCall(String mthInvocation, int occurrences)
	{
		return "\t\t\t<FunctionCall name=\"" + mthInvocation +"\" count=\"" + occurrences +"\"/>" + "\n";
	}
	
	
	
	private static void clear()
	{
		vxl = new StringBuffer();
	}
			
	public static void save(String vxlFileName)
	{
		try
		{
			PrintStream file = new PrintStream(new FileOutputStream(vxlFileName, false));
			file.print(vxl.toString());
			file.close();
			clear();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static String macro(String macroName)
	{
		return "\t\t<macro name=\"" + macroName + "\"/>" + "\n";
	}
	
	public static String macroParameter(String macroParacmeter)
	{
		return "\t\t\t<param name=\"" + macroParacmeter + "\"/>" + "\n";
	}
	
	public static String errorDirective(String error)
	{
		return "\t\t<error message=\"" + error + "\"/>" + "\n";
	}
	
	public static String includeDirective(String include)
	{
		return "\t\t<include name=\"" + include + "\"/>" + "\n";
	}
	
	public static String pragmaDirective(String pragma)
	{
		return "\t\t<pragma message=\"" + pragma + "\"/>" + "\n";
	}
}
