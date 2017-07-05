package Vocabulary.Processors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IFunction;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompoundStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTEnumerationSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTProblemDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.c.CFunction;
import Vocabulary.Extractor.Util.LOCManager;
import Vocabulary.Extractor.Util.VxlManager;
import Vocabulary.vloccount.LOCParameters;

public class FunctionProcessor
{
	private StringBuffer vxlFragment;
	private String tipo;
	public static List<ASTNode> listDeclarations;

	public FunctionProcessor()
	{
		vxlFragment = new StringBuffer();
	}

	public FunctionProcessor(CASTFunctionDefinition functionDefinition)
	{
		String name = functionDefinition.getDeclarator().getName().toString();

		IFunction type = (IFunction) new CFunction(functionDefinition.getDeclarator());
		
		if (type.isAuto())
			tipo = "auto";
		else if (type.isStatic())
			tipo = "static";
		else if (type.isExtern())
			tipo = "extern";
		else if (type.isRegister())
			tipo = "register";
		else
			tipo = "auto";
	
		vxlFragment = new StringBuffer(VxlManager.startFuntion(name, tipo));
		BodyProcessor.setVxlFragment(vxlFragment);
	
		CASTCompoundStatement body = (CASTCompoundStatement) functionDefinition.getBody();
		vxlFragment.append(new CommentsProcessor(statementList(body.getStatements()), (ASTNode) functionDefinition, true).getVxlFragment());

		if (functionDefinition.getDeclarator() instanceof CASTFunctionDeclarator)
		{
			CASTFunctionDeclarator argumentsOfFunction = (CASTFunctionDeclarator) functionDefinition.getDeclarator();

			IASTParameterDeclaration[] arguments = argumentsOfFunction.getParameters();

			for (IASTParameterDeclaration argument : arguments)
				VxlManager.parameter(vxlFragment, argument.getDeclarator().getName().toString());
		}

		if (functionDefinition.getBody() instanceof CASTCompoundStatement)
		{
			IASTStatement[] statements = body.getStatements();
		
			for (IASTStatement statement : statements)
			{
				BodyProcessor.extractBody(statement);
			}
		
			storeInternVocabulary(FunctionVocabularyManager.getLocalVariables(),
					FunctionVocabularyManager.getMethodsInvocation(), FunctionVocabularyManager.getLiterals());
			FunctionVocabularyManager.clear();

		}
		vxlFragment.append(VxlManager.endFuntion());
	}
	
	private void storeInternVocabulary(Map<String, Integer> localVariables, Map<String, Integer> methodsInvocation,
			Map<String, Integer> literals)
	{
		Set<String> lvar = localVariables.keySet();
		Iterator<String> it_lvar = lvar.iterator();
		while (it_lvar.hasNext())
		{
			String identifier = it_lvar.next();
			vxlFragment.append(VxlManager.localVariable(identifier, localVariables.get(identifier)));
		}

		Set<String> mthInv = methodsInvocation.keySet();
		Iterator<String> it_mth = mthInv.iterator();
		while (it_mth.hasNext())
		{
			String identifier = it_mth.next();
			vxlFragment.append(VxlManager.functionCall(identifier, methodsInvocation.get(identifier)));
		}

		Set<String> lit = literals.keySet();
		Iterator<String> it_lit = lit.iterator();
		while (it_lit.hasNext())
		{
			String identifier = it_lit.next();
			vxlFragment.append(VxlManager.literal(identifier, literals.get(identifier)));
		}
	}

	public StringBuffer getVxlFragment()
	{
		return vxlFragment;
	}
	
	public static List<ASTNode> statementList(IASTStatement[] declarations)
	{
		List<ASTNode> fileDeclarations = new ArrayList<ASTNode>();
		
		for(IASTStatement declaration: declarations)
		{
			if(declaration instanceof IASTFunctionDefinition)
			{
				fileDeclarations.add((ASTNode)declaration);
			}
			
			if(declaration instanceof CASTProblemDeclaration)
			{
				CASTProblemDeclaration p = (CASTProblemDeclaration) declaration;
				fileDeclarations.add((ASTNode)declaration);
			}
			
			if(declaration instanceof IASTSimpleDeclaration)
			{	
				CASTSimpleDeclaration node = (CASTSimpleDeclaration)declaration;
				
				IASTDeclSpecifier declarationSpecifier = node.getDeclSpecifier();
				
				if((declarationSpecifier instanceof CASTEnumerationSpecifier) || (declarationSpecifier instanceof CASTCompositeTypeSpecifier))
				{
					fileDeclarations.add((ASTNode)declaration);
				}
			}
		}
		
		return fileDeclarations;
	}
}
