package org.splab.vocabulary.extractor.processors;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTFunctionStyleMacroParameter;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorErrorStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorFunctionStyleMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorPragmaStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorStatement;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.splab.vocabulary.extractor.util.VxlManager;

/**
 * DirectivesProcessor é reponsável por receber uma lista de diretivas de
 * preprocessador e extrair os tipos específicos para então processá-los.
 * 
 * @author Israel Gomes de Lima
 *
 */
public class DirectivesProcessor {
	/**
	 * Mantém uma lista de nodos ast e um fragmento de vxl.
	 */
	private static List<ASTNode> preprocessorList;
	private static StringBuffer vxlFragment;

	/**
	 * Diferencia as diretivas e obtem suas informações.
	 * 
	 * @param directives
	 */
	public static void extractDirectives(IASTPreprocessorStatement[] directives) {
		vxlFragment = new StringBuffer();
		preprocessorList = new LinkedList<ASTNode>();

		for (IASTPreprocessorStatement directive : directives) {

			if (directive instanceof IASTPreprocessorMacroDefinition)
				macroDefinitionExtract((IASTPreprocessorMacroDefinition) directive);

			if (directive instanceof IASTPreprocessorErrorStatement)
				errorExtract((IASTPreprocessorErrorStatement) directive);

			if (directive instanceof IASTPreprocessorIncludeStatement) {
				includeExtract((IASTPreprocessorIncludeStatement) directive);
			}

			if (directive instanceof IASTPreprocessorPragmaStatement)
				pragmaExtract((IASTPreprocessorPragmaStatement) directive);

			// Adiciona o header ou directiva a lista de directivas
			preprocessorList.add((ASTNode) directive);
		}
	}

	/**
	 * Extrai definições de macros simples e semelhantes a funções.
	 * 
	 * @param macroDefinition
	 */
	private static void macroDefinitionExtract(IASTPreprocessorMacroDefinition macroDefinition) {

		if (macroDefinition instanceof IASTPreprocessorFunctionStyleMacroDefinition) {
			IASTPreprocessorFunctionStyleMacroDefinition functionStyleMacro = (IASTPreprocessorFunctionStyleMacroDefinition) macroDefinition;
			vxlFragment.append(VxlManager
					.startFunctionStyleMacro(StringProcessor.processString(functionStyleMacro.getName().toString())));

			IASTFunctionStyleMacroParameter[] macroParameters = functionStyleMacro.getParameters();
			for (IASTFunctionStyleMacroParameter parameter : macroParameters) {
				vxlFragment.append(VxlManager.macroParameter(StringProcessor.processString(parameter.getParameter())));
			}

			vxlFragment.append(VxlManager.endFunctionStyleMacro());
		} else {
			vxlFragment.append(VxlManager.macro(StringProcessor.processString(macroDefinition.getName().toString())));
		}
	}

	/**
	 * Extrai a mensagem em uma directiva de error
	 * 
	 * @param error
	 */
	private static void errorExtract(IASTPreprocessorErrorStatement error) {
		char[] message = error.getMessage();

		if (message.length >= 1) {
			if ((message[0] == '\"') && (message[message.length - 1] == '\"'))
				vxlFragment.append(VxlManager.errorDirective("\"" + StringProcessor.processString(
								new String(error.getMessage()).substring(1, error.getMessage().length - 1)) + "\""));
			else
				vxlFragment.append(VxlManager
						.errorDirective("\"" + StringProcessor.processString(new String(error.getMessage())) + "\""));
		}
	}

	/**
	 * Extrai a biblioteca em uma directiva include
	 * 
	 * @param include
	 */
	private static void includeExtract(IASTPreprocessorIncludeStatement include) {
		vxlFragment.append(VxlManager.includeDirective(StringProcessor.processString(include.getName().toString())));
	}

	/**
	 * Extrai a mensagem em uma directiva de pragma
	 * 
	 * @param pragmaStatement
	 */
	private static void pragmaExtract(IASTPreprocessorPragmaStatement pragmaStatement) {
		char[] message = pragmaStatement.getMessage();

		if (message.length >= 1) {
			if ((message[0] == '\"') && (message[message.length - 1] == '\"'))
				vxlFragment.append(VxlManager.pragmaDirective("\"" + StringProcessor.processString(
						new String(pragmaStatement.getMessage()).substring(1, pragmaStatement.getMessage().length - 1))
						+ "\""));
			else
				vxlFragment.append(VxlManager.pragmaDirective(
						"\"" + StringProcessor.processString(new String(pragmaStatement.getMessage())) + "\""));
		}
	}

	/**
	 * Retorna o fragmento de vxl
	 * 
	 * @return
	 */
	public static StringBuffer getVxlFragment() {
		return vxlFragment;
	}

	/**
	 * Retorna a lista de directivas
	 * 
	 * @return
	 */
	public static List<ASTNode> getPreprocessorList() {
		return preprocessorList;
	}

	/**
	 * Reinicia a lista e o vxl
	 */
	public static void reset() {
		vxlFragment = new StringBuffer();
		preprocessorList = new LinkedList<ASTNode>();
	}
}
