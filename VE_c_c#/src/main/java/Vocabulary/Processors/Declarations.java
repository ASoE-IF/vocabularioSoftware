package Vocabulary.Processors;

import java.util.LinkedList;

import org.eclipse.cdt.core.dom.ast.IASTArrayDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause;
import org.eclipse.cdt.core.dom.ast.IASTInitializerList;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IVariable;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTBinaryExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTEnumerationSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTEqualsInitializer;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTTypedefNameSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CVariable;

import Vocabulary.Extractor.Util.VxlManager;

public class Declarations// processador de declarações
{
	private static StringBuffer vxlFragment;
	private int enumLoc = 0;

	public static String variablesTyes(IASTName variableName)
	{
		IVariable e = new CVariable(variableName);
		String permissao;

		if (e.isAuto())
			permissao = "auto";
		else if (e.isStatic())
			permissao = "static";
		else if (e.isRegister())
			permissao = "register";
		else
			permissao = "auto";

		return permissao;
	}
	
	private static void extractCompositeType(CASTCompositeTypeSpecifier compositeType, boolean scope)
	{
		switch (compositeType.getKey())
		{
		case 1:
			StructProcessor struct = new StructProcessor((CASTCompositeTypeSpecifier) compositeType, scope);
			vxlFragment.append(struct.getVxlFragment());
			break;
			
		case 2:
			UnionProcessor union = new UnionProcessor((CASTCompositeTypeSpecifier) compositeType, scope);
			vxlFragment.append(union.getVxlFragment());
			break;
		}
	}
	
	private static void extractTypedefName(CASTTypedefNameSpecifier typedefName, boolean scope)
	{
		if (scope == true)
		{
			FunctionVocabularyManager.insertLocalVariable(typedefName.getName().toString());
		}
		else
		{
			vxlFragment.append(VxlManager.globalVariables(typedefName.getName().toString(),
					variablesTyes(typedefName.getName()), "eu"));
		}
	}
	
	private static void extractSimpleDeclaration(CASTSimpleDeclaration declaration, boolean scope)
	{
		IASTDeclarator[] simpleDeclarations = declaration.getDeclarators();
		
		if (simpleDeclarations.length == 0)
		{
			return;
		}

		if (scope == true)
		{
			FunctionVocabularyManager.insertLocalVariable(simpleDeclarations[0].getName().toString());
		}
		else
		{
			vxlFragment.append(VxlManager.globalVariables(simpleDeclarations[0].getName().toString(),
					variablesTyes(simpleDeclarations[0].getName()), "eu"));
		}
		
		if(simpleDeclarations[0].getInitializer() != null)
			extractEqualsInitializer((CASTEqualsInitializer) simpleDeclarations[0].getInitializer());
	}

	private static void extractEqualsInitializer(CASTEqualsInitializer initializer)
	{
		IASTInitializerClause initializerClause = initializer.getInitializerClause();

		if(initializerClause instanceof IASTExpression)
		{
			ExpressionProcessor.extractExpression((IASTExpression)initializerClause);
		}
		else
		{
			if(initializerClause instanceof IASTInitializerList)
			{
				IASTInitializerList list = (IASTInitializerList) initializerClause;
				
				for(IASTInitializerClause clause : list.getClauses())
					ExpressionProcessor.extractExpression((IASTExpression) clause);
			}
		}
	}
	
	public static void declarations(CASTSimpleDeclaration simpleDeclaration, boolean scope)
	{
		vxlFragment = new StringBuffer();
		IASTDeclSpecifier declarations = simpleDeclaration.getDeclSpecifier();

		if (declarations instanceof CASTEnumerationSpecifier)
		{
			CASTEnumerationSpecifier enumeration = (CASTEnumerationSpecifier) declarations;
			EnumProcessor enumProcessor = new EnumProcessor(enumeration, scope, true, 0);
			
			vxlFragment.append(enumProcessor.getVxlFragment());
		}

		if (declarations instanceof CASTCompositeTypeSpecifier)
		{
			CASTCompositeTypeSpecifier compositeType = (CASTCompositeTypeSpecifier) declarations;
			extractCompositeType(compositeType, scope);
		}

		if (declarations instanceof CASTTypedefNameSpecifier)
		{
			CASTTypedefNameSpecifier typedefName = (CASTTypedefNameSpecifier) declarations;
			extractTypedefName(typedefName, scope);
		}

		if (declarations instanceof CASTSimpleDeclSpecifier)
		{
			extractSimpleDeclaration(simpleDeclaration, scope);
		}
	}

	public static StringBuffer getVxlFragment()// retorna o vxlFragment
	{
		return vxlFragment;
	}
}
