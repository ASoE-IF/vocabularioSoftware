package org.splab.vocabulary.extractor.processors;

import org.eclipse.cdt.core.dom.ast.IASTFunctionStyleMacroParameter;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorErrorStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorFunctionStyleMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorPragmaStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorStatement;
import org.splab.vocabulary.extractor.util.VxlManager;

/**
 * This class is responsible for extracting informations from the directives
 * 
 * DirectiveProcessor extract informations such as: macro, pragma, error, includes
 * 
 * @author Israel Gomes de Lima
 * Baseado na BodyProcessor do extrator original
 * @since september 06, 2017.
 */

public class DirectivesProcessor
{
	private static StringBuffer vxlFragment;
	
	public static void extractDirectives(IASTPreprocessorStatement[] directives)
	{
		vxlFragment = new StringBuffer();
		for(IASTPreprocessorStatement directive : directives)
		{
			if(directive instanceof IASTPreprocessorMacroDefinition)
				macroDefinitionExtract((IASTPreprocessorMacroDefinition)directive);
			
			
			if(directive instanceof IASTPreprocessorErrorStatement)
				errorExtract((IASTPreprocessorErrorStatement) directive);
				
			
			if(directive instanceof IASTPreprocessorIncludeStatement)
			{
				includeExtract((IASTPreprocessorIncludeStatement) directive);
			}
			
			if(directive instanceof IASTPreprocessorPragmaStatement)
				pragamaExtract((IASTPreprocessorPragmaStatement) directive);
		}
	}
	
	private static void macroDefinitionExtract(IASTPreprocessorMacroDefinition macroDefinition)
	{
		if(macroDefinition instanceof IASTPreprocessorFunctionStyleMacroDefinition)
		{
			IASTPreprocessorFunctionStyleMacroDefinition functionStyleMacro = (IASTPreprocessorFunctionStyleMacroDefinition) macroDefinition;
			vxlFragment.append(VxlManager.startFunctionStyleMacro(functionStyleMacro.getName().toString()));
			
			IASTFunctionStyleMacroParameter[] macroParameters = functionStyleMacro.getParameters();
			for(IASTFunctionStyleMacroParameter parameter : macroParameters)
			{
				vxlFragment.append(VxlManager.macroParameter(parameter.getParameter()));
			}
			
			vxlFragment.append(VxlManager.endFunctionStyleMacro());
		}
		else
		{
			vxlFragment.append(VxlManager.macro(macroDefinition.getName().toString()));
		}
	}
	
	private static void errorExtract(IASTPreprocessorErrorStatement error)
	{
		char[] message = error.getMessage();
		if((message[0] == '\"') && (message[message.length - 1] == '\"'))
			vxlFragment.append(VxlManager.errorDirective(new String(error.getMessage())));
		else
			vxlFragment.append(VxlManager.errorDirective("\"" + new String(error.getMessage()) + "\""));
	}
	
	private static void includeExtract(IASTPreprocessorIncludeStatement include)
	{
		vxlFragment.append(VxlManager.includeDirective(include.getName().toString()));
	}

	private static void pragamaExtract(IASTPreprocessorPragmaStatement pragmaStatement)
	{
		char[] message = pragmaStatement.getMessage();
		if((message[0] == '\"') && (message[message.length - 1] == '\"'))
			vxlFragment.append(VxlManager.pragmaDirective(new String(pragmaStatement.getMessage())));
		else
			vxlFragment.append(VxlManager.pragmaDirective("\"" + new String(pragmaStatement.getMessage()) + "\""));
	}
	
	public static StringBuffer getVxlFragment()
	{
		return vxlFragment;
	}
}
