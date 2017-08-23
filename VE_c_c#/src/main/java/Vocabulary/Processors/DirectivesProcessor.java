package Vocabulary.Processors;

import org.eclipse.cdt.core.dom.ast.IASTFunctionStyleMacroParameter;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorElifStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorElseStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorEndifStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorErrorStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorFunctionStyleMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIfdefStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIfndefStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorPragmaStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorUndefStatement;

import Vocabulary.Extractor.Util.VxlManager;

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
			
			if(directive instanceof IASTPreprocessorIfStatement)
			{
				ifExtract((IASTPreprocessorIfStatement) directive);
			}
			
			if(directive instanceof IASTPreprocessorElifStatement)
			{
				elifExtract((IASTPreprocessorElifStatement) directive);
			}
			
			if(directive instanceof IASTPreprocessorElseStatement)
			{
				elseExtract((IASTPreprocessorElseStatement) directive);
			}
			
			if(directive instanceof IASTPreprocessorEndifStatement)
			{
				endifExtract((IASTPreprocessorEndifStatement) directive);
			}
			
			if(directive instanceof IASTPreprocessorIfdefStatement)
				ifdefExtract((IASTPreprocessorIfdefStatement) directive);
				
			if(directive instanceof IASTPreprocessorIfndefStatement)
				ifndefExtract((IASTPreprocessorIfndefStatement) directive);
			
			if(directive instanceof IASTPreprocessorUndefStatement)
				undefExtract((IASTPreprocessorUndefStatement) directive);
			
			if(directive instanceof IASTPreprocessorPragmaStatement)
				pragamaExtract((IASTPreprocessorPragmaStatement) directive);
		}
	}
	
	private static void macroDefinitionExtract(IASTPreprocessorMacroDefinition macroDefinition)
	{
		if(macroDefinition instanceof IASTPreprocessorFunctionStyleMacroDefinition)
		{
			IASTPreprocessorFunctionStyleMacroDefinition functionStyleMacro = (IASTPreprocessorFunctionStyleMacroDefinition) macroDefinition;
			vxlFragment.append(VxlManager.macro(functionStyleMacro.getName().toString()));
			
			IASTFunctionStyleMacroParameter[] macroParameters = functionStyleMacro.getParameters();
			for(IASTFunctionStyleMacroParameter parameter : macroParameters)
			{
				vxlFragment.append(VxlManager.macroParameter(parameter.getParameter()));
			}
		}
		else
		{
			vxlFragment.append(VxlManager.macro(macroDefinition.getName().toString()));
		}
	}
	
	private static void errorExtract(IASTPreprocessorErrorStatement error)
	{
		vxlFragment.append(VxlManager.errorDirective(new String(error.getMessage())));
	}
	
	private static void includeExtract(IASTPreprocessorIncludeStatement include)
	{
		vxlFragment.append(VxlManager.includeDirective(include.getName().toString()));
	}

	private static void ifExtract(IASTPreprocessorIfStatement ifStatement)
	{
		System.out.println(ifStatement.getCondition());
	}
	
	private static void elifExtract(IASTPreprocessorElifStatement elifStatement)
	{
		System.out.println(elifStatement.getCondition());
	}
	
	private static void elseExtract(IASTPreprocessorElseStatement elseStatement)
	{
		System.out.println(elseStatement.getRawSignature());
	}
	
	private static void endifExtract(IASTPreprocessorEndifStatement endifStatement)
	{
		System.out.println(endifStatement.getRawSignature());
	}
	
	private static void ifdefExtract(IASTPreprocessorIfdefStatement ifdefStatement)
	{
		System.out.println(ifdefStatement.getCondition());
	}
	
	private static void ifndefExtract(IASTPreprocessorIfndefStatement ifndefStatement)
	{
		System.out.println(ifndefStatement.getCondition());
	}
	
	private static void undefExtract(IASTPreprocessorUndefStatement undefStatement)
	{
		System.out.println(undefStatement.getMacroName());
	}
	
	private static void pragamaExtract(IASTPreprocessorPragmaStatement pragmaStatement)
	{
		vxlFragment.append(VxlManager.pragmaDirective(new String(pragmaStatement.getMessage())));
	}
	
	public static StringBuffer getVxlFragment()
	{
		return vxlFragment;
	}
}
