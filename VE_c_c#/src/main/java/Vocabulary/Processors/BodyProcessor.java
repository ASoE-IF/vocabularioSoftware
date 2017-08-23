package Vocabulary.Processors;

import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTAmbiguousStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTBreakStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCaseStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompoundStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTContinueStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTDeclarationStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTDefaultStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTDoStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTExpressionStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTForStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTGotoStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTIfStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTLabelStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTNullStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTProblemStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTReturnStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSwitchStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTWhileStatement;
import org.eclipse.wst.jsdt.core.dom.ThisExpression;

public class BodyProcessor
{
	private static StringBuffer vxlFragment;
	
	public static void extractBody(IASTStatement statement)
	{
		if(statement instanceof CASTAmbiguousStatement);
		
		if(statement instanceof CASTBreakStatement)
			extractBreakStatement((CASTBreakStatement) statement);
		
		if(statement instanceof CASTCaseStatement)
			extractCaseStatement((CASTCaseStatement) statement);
		
		if(statement instanceof CASTCompoundStatement)
			extractCompoundStatement((CASTCompoundStatement) statement);
		
		if(statement instanceof CASTContinueStatement)
			extractContinueStatement((CASTContinueStatement) statement);
		
		if(statement instanceof CASTDeclarationStatement)
			extractDeclarationStatement((CASTDeclarationStatement) statement);
		
		if(statement instanceof CASTDefaultStatement)
			extractDefaultStatement((CASTDefaultStatement) statement);
		
		if(statement instanceof CASTDoStatement)
			extractDoStatement((CASTDoStatement) statement);
		
		if(statement instanceof CASTExpressionStatement)
			extractExpressionStatement((CASTExpressionStatement) statement);
		
		if(statement instanceof CASTForStatement)
			extractForStatement((CASTForStatement) statement);
		
		if(statement instanceof CASTGotoStatement)
			extractGotoStatement((CASTGotoStatement) statement);
		
		if(statement instanceof CASTIfStatement)
			extractIfStatement((CASTIfStatement) statement);
		
		if(statement instanceof CASTLabelStatement)
			extractLabelStatement((CASTLabelStatement) statement);
		
		if(statement instanceof CASTNullStatement)
			extractNullStatement((CASTNullStatement) statement);
		
		if(statement instanceof CASTProblemStatement)
			extractProblemStatement((CASTProblemStatement) statement);
		
		if(statement instanceof CASTReturnStatement)
			extractReturnStatement((CASTReturnStatement) statement);
		
		if(statement instanceof CASTSwitchStatement)
			extractSwitchStatement((CASTSwitchStatement) statement);
		
		if(statement instanceof CASTWhileStatement)
			extractWhileStatement((CASTWhileStatement) statement);
	}
	
	private static void extractBreakStatement(CASTBreakStatement breakStatement)
	{

	}
	
	private static void extractCaseStatement(CASTCaseStatement caseStatement)
	{
		IASTExpression exp = caseStatement.getExpression();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
	}
	
	private static void extractCompoundStatement(CASTCompoundStatement compoundStatement)
	{
		for (IASTStatement s : compoundStatement.getStatements())
		{
			if (s != null) 
				extractBody(s);
		}
	}
	
	private static void extractContinueStatement(CASTContinueStatement continueStatement)
	{
		
	}
	
	private static void extractDeclarationStatement(CASTDeclarationStatement declarationStatement)
	{
		IASTDeclaration declaration = declarationStatement.getDeclaration();
		if (declaration != null)
		{
			Declarations.declarations((CASTSimpleDeclaration) declaration, true);
			
			vxlFragment.append(Declarations.getVxlFragment());
		}
	}
	
	private static void extractDefaultStatement(CASTDefaultStatement defaultStatement)
	{
		
	}
	
	private static void extractDoStatement(CASTDoStatement doStatement)
	{
		IASTStatement body = doStatement.getBody();
		if (body != null) 
			extractBody(body);
		
		IASTExpression exp = doStatement.getCondition();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
	}
	
	private static void extractExpressionStatement(CASTExpressionStatement expressionStatement)
	{
		IASTExpression exp = expressionStatement.getExpression();

		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
	}

	private static void extractForStatement(CASTForStatement forStatement)
	{
		IASTStatement body = forStatement.getBody();
		if (body != null)
			extractBody(body);
		
		IASTExpression exp = forStatement.getConditionExpression();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
		
		IASTStatement initializers = forStatement.getInitializerStatement();
		if (initializers != null);
			BodyProcessor.extractBody(initializers);

		IASTExpression iteration = forStatement.getIterationExpression();
		if (iteration != null) 
			ExpressionProcessor.extractExpression(iteration);
	}
	
	private static void extractGotoStatement(CASTGotoStatement gotoStatement)
	{
		String identifier = gotoStatement.getName().toString();
		FunctionVocabularyManager.insertLocalVariable(identifier);
	}
	
	private static void extractIfStatement(CASTIfStatement ifStatement)
	{
		IASTStatement elseBody = ifStatement.getElseClause();
		if (elseBody != null)
			extractBody(elseBody);	
		
		IASTExpression exp = ifStatement.getConditionExpression();
		if (exp != null)
		{
			ExpressionProcessor.extractExpression(exp);
		}
		
		IASTStatement thenBody = ifStatement.getThenClause();
		if (thenBody != null)
			extractBody(thenBody);
	}
	
	private static void extractLabelStatement(CASTLabelStatement labelStatement)
	{
		IASTStatement statement = labelStatement.getNestedStatement();
		if(statement != null)
			BodyProcessor.extractBody(statement);
		
		String identifier = labelStatement.getName().toString();
		if(identifier != null)
			FunctionVocabularyManager.insertLocalVariable(identifier);
	}
	
	private static void extractNullStatement(CASTNullStatement nullStatement)
	{
		
	}
	
	private static void extractProblemStatement(CASTProblemStatement problemStatement)
	{
		System.out.println("Problema");
	}
	
	private static void extractReturnStatement(CASTReturnStatement returnStatement)
	{
		IASTExpression exp = returnStatement.getReturnValue();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
	}
	
	private static void extractSwitchStatement(CASTSwitchStatement switchStatement)
	{
		IASTExpression exp = switchStatement.getControllerExpression();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
		 
		IASTStatement body = switchStatement.getBody();
		if(body != null)
			extractBody(body);
	}
	
	private static void extractWhileStatement(CASTWhileStatement whileStatement)
	{
		IASTStatement body = whileStatement.getBody();
		if (body != null)
			extractBody(body);
		
		IASTExpression exp = whileStatement.getCondition();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
	}
	
	public static void setVxlFragment(StringBuffer vxl)
	{
		vxlFragment = vxl;
	}
}