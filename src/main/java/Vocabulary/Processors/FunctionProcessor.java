package Vocabulary.Processors;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IFunction;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompoundStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.c.CFunction;

import Vocabulary.Extractor.Util.LOCManager;
import Vocabulary.Extractor.Util.VxlManager;
import Vocabulary.vloccount.LOCParameters;

public class FunctionProcessor// processador de funções
{
	private StringBuffer vxlFragment;
	private String tipo;// tipo da função
	public static List<ASTNode> listDeclarations;

	public FunctionProcessor() {
		vxlFragment = new StringBuffer();
	}

	public FunctionProcessor(CASTFunctionDefinition functionDefinition)
	{
		String name = functionDefinition.getDeclarator().getName().toString();
		listDeclarations = new LinkedList<ASTNode>();

		// necessario pesquisar mais sobre os tipos de acesso de funções e sobre
		// as informações abaixo
		IFunction type = (IFunction) new CFunction(functionDefinition.getDeclarator());
		
		if (type.isAuto())// testa se função é auto
			tipo = "auto";
		else if (type.isStatic())// testa se a função é static
			tipo = "static";
		else if (type.isExtern())// testa se a função é extern
			tipo = "extern";
		else if (type.isRegister())
			tipo = "register";// testa se a função é register
		else
			tipo = "auto";// se não estiver explicito as informações acima ele
							// auto

		vxlFragment = new StringBuffer(VxlManager.startFuntion(name, tipo));// grava no vxlFragment
		vxlFragment.append(new CommentsProcessorC(listDeclarations.toArray(new ASTNode[listDeclarations.size()]), (ASTNode) functionDefinition, true).getVxlFragment());

		if (functionDefinition.getDeclarator() instanceof CASTFunctionDeclarator)// testa e declaração da função
		{
			CASTFunctionDeclarator argumentsOfFunction = (CASTFunctionDeclarator) functionDefinition.getDeclarator();

			IASTParameterDeclaration[] arguments = argumentsOfFunction.getParameters();

			for (IASTParameterDeclaration argument : arguments)
				VxlManager.parameter(vxlFragment, argument.getDeclarator().getName().toString());
		}

		if (functionDefinition.getBody() instanceof CASTCompoundStatement
				&& LOCManager.locParameters.contains(LOCParameters.INTERN_VOCABULARY))
		{
			BodyProcessor body = new BodyProcessor((CASTCompoundStatement) functionDefinition.getBody());
			vxlFragment.append(body.getVxlFragment());// grava no vxlFragment

			storeInternVocabulary(FunctionVocabularyManager.getLocalVariables(),
					FunctionVocabularyManager.getMethodsInvocation(), FunctionVocabularyManager.getLiterals());
			FunctionVocabularyManager.clear();

		}
		CASTCompoundStatement x = (CASTCompoundStatement) functionDefinition.getBody();

		vxlFragment.append(VxlManager.endFuntion());
	}

	private void storeInternVocabulary(Map<String, Integer> localVariables, Map<String, Integer> methodsInvocation,
			Map<String, Integer> literals) {

		/*
		 * Set<String> lvar = localVariables.keySet(); Iterator<String> it_lvar
		 * = lvar.iterator(); while (it_lvar.hasNext()) { String identifier =
		 * it_lvar.next();
		 * vxlFragment.append(VxlManager.localVariable(identifier,
		 * localVariables.get(identifier))); }
		 */

		Set<String> mthInv = methodsInvocation.keySet();
		Iterator<String> it_mth = mthInv.iterator();
		while (it_mth.hasNext()) {
			String identifier = it_mth.next();
			vxlFragment.append(VxlManager.functionCall(identifier, methodsInvocation.get(identifier)));
		}

		Set<String> lit = literals.keySet();
		Iterator<String> it_lit = lit.iterator();
		while (it_lit.hasNext()) {
			String identifier = it_lit.next();
			vxlFragment.append(VxlManager.literal(identifier, literals.get(identifier)));
		}
	}

	public StringBuffer getVxlFragment() {
		return vxlFragment;
	}
}
