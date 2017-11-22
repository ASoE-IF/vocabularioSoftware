package org.splab.vocabulary.extractor.processors;

import java.util.List;

import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

/**
 * This class is responsible for extracting informations from the method's body.
 * All the informations extract should be Statement type.
 * 
 * BodyProcessor extract informations such as:
 * Constructor and SuperConstructor invocation, 
 * method invocation and variable declaration.

 * @author Catharine Quintans
 * @since October 24, 2012.
 */
public class BodyProcessor {

	/**
	 * Identifier the instance of the body to extract all the information
	 * about the method's body.
	 * @param body The method's body that will be analyzed.
	 */
	public static void extractBody(Statement body) {
		if (body instanceof AssertStatement) 
			extractAssertStatement((AssertStatement) body);
		
		if (body instanceof Block) 
			extractBlock((Block) body);
		
		if (body instanceof BreakStatement)
			extractBreakStatemtn((BreakStatement) body);
			
		if (body instanceof ConstructorInvocation) 
			extractConstrutorInvocation((ConstructorInvocation) body);
		
		if (body instanceof ContinueStatement)
			extractContinueStatement((ContinueStatement) body);
		
		if (body instanceof DoStatement) 
			extractDoStatement((DoStatement) body);
		
		if (body instanceof ExpressionStatement) 
			extractExpressionStatement((ExpressionStatement) body);
		
		if (body instanceof ForStatement) 
			extractForStatement((ForStatement) body);
		
		if (body instanceof IfStatement) 
			extractIfStatement((IfStatement) body);
		
		if (body instanceof LabeledStatement) 
			extractLabeledStatement((LabeledStatement) body);
		
		if (body instanceof ReturnStatement) 
			extractReturnStatement((ReturnStatement) body);
		
		if (body instanceof SuperConstructorInvocation) 
			extractSuperConstrutorInvocation((SuperConstructorInvocation) body);
		
		if (body instanceof SwitchCase) 
			extractSwitchCase((SwitchCase) body);
		
		if (body instanceof SwitchStatement) 
			extractSwitchStatement((SwitchStatement) body);

		if (body instanceof SynchronizedStatement) 
			extractSynchronizedStatement((SynchronizedStatement) body);

		if (body instanceof ThrowStatement) 
			extractThrowStatement((ThrowStatement) body);

		if (body instanceof TryStatement) 
			extractTryStatement((TryStatement) body);

		if (body instanceof VariableDeclarationStatement)
			extractVariableDeclarationStatement((VariableDeclarationStatement) body);
		
		if (body instanceof WhileStatement) 
			extractWhileStatement((WhileStatement) body);
	}


	/**
	 * Extract the identifier presents in the assertions' expressions.
	 * @param assertStatement The assertions to be analyzed.
	 */
	private static void extractAssertStatement(AssertStatement assertStatement) {
		Expression exp = assertStatement.getExpression();
		if (exp != null) 
			ExpressionProcessor.extractExpression(exp);
		
		Expression msg = assertStatement.getMessage();
		if (msg!=null)
			ExpressionProcessor.extractExpression(msg);
	}

	/**
	 * Extract all the statements inside a block of code.
	 * @param block The block that contains all the statements to be analyzed.
	 */
	@SuppressWarnings("unchecked")
	private static void extractBlock(Block block) {
		List<Statement> lstStatement = block.statements();
		for (Statement statement : lstStatement) {
			if (statement != null)
				extractBody(statement);
		}
	}
	
	/**
	 * Extract the statements of the break.
	 * @param breakStatement The break statement.
	 */
	private static void extractBreakStatemtn(BreakStatement breakStatement) {
		MethodVocabularyManager.insertLocalVariable(ExpressionProcessor.extractSimpleName(breakStatement.getLabel()));
	}

	/**
	 * Extract the variable declarations and the block of code that are
	 * inside the 'Case' from 'SwitchCase Statement'.
	 * @param catchClause The catch clause.
	 */
	private static void extractCatchClause(CatchClause catchClause) {
		Block block = catchClause.getBody();
		if (block != null)
			extractBlock(block);
		
		SingleVariableDeclaration varDecl = catchClause.getException();
		if (varDecl != null) 
			extracSingleVariableDeclartion(varDecl);
	}
	
	/**
	 * Extract all the arguments of the constructor invocation.
	 * @param construtorInvocation The constructor invocation.
	 */
	@SuppressWarnings("unchecked")
	private static void extractConstrutorInvocation(ConstructorInvocation construtorInvocation) {
		List<Expression> arguments = construtorInvocation.arguments();
		for (Expression e : arguments)
			if (e != null)
				ExpressionProcessor.extractExpression(e);
	}

	/**
	 * Extract the identifier declared at the continue statement.
	 * @param continueStatement The continue statement.
	 */
	private static void extractContinueStatement(ContinueStatement continueStatement) {
		SimpleName smpName = continueStatement.getLabel();
		if (smpName != null)
			MethodVocabularyManager.insertLocalVariable(ExpressionProcessor.extractSimpleName(smpName));
	}
	
	/**
	 * Extract the body inside a 'Do Statement' and the expression that makes the
	 * 'Do Statement' to be executed.
	 * @param doStatement The Do Statement.
	 */
	private static void extractDoStatement(DoStatement doStatement) {
		Statement body = doStatement.getBody();
		if (body != null) 
			extractBody(body);
		
		Expression exp = doStatement.getExpression();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
	}

	/**
	 * 
	 * @param expressionStatement
	 */
	private static void extractExpressionStatement(ExpressionStatement expressionStatement) {
		Expression exp = expressionStatement.getExpression();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
	}

	/**
	 * Extract for informations.
	 * @param forStatement The for statement.
	 */
	@SuppressWarnings("unchecked")
	private static void extractForStatement(ForStatement forStatement) {
		Statement body = forStatement.getBody();
		if (body != null)
			extractBody(body);
		
		Expression exp = forStatement.getExpression();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
		
		List<Expression> initializers = forStatement.initializers();
		for (Expression e : initializers) {
			if (e != null) 
				ExpressionProcessor.extractExpression(e);
		}
		
		List<Expression> updaters = forStatement.updaters();
		for (Expression e : updaters) {
			if (e != null) 
				ExpressionProcessor.extractExpression(e);
		}
	}

	/**
	 * Extract all the information existing in a 'if else' statement.
	 * Extract the expression of the 'If' and the body of the 'If' and 'Else'
	 * statement.
	 * @param ifStatement The If/Else statement.
	 */
	private static void extractIfStatement(IfStatement ifStatement) {
		Statement elseBody = ifStatement.getElseStatement();
		if (elseBody != null)
			extractBody(elseBody);	
		
		Expression exp = ifStatement.getExpression();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
		
		Statement thenBody = ifStatement.getThenStatement();
		if (thenBody != null)
			extractBody(thenBody);
	}

	/**
	 * Extract information of the labeled statement. 
	 * @param labeledStatement
	 */
	private static void extractLabeledStatement(LabeledStatement labeledStatement) {
		Statement body = labeledStatement.getBody();
		if (body != null) 
			extractBody(body);
		
		SimpleName smpName = labeledStatement.getLabel();
		if (smpName != null)
			MethodVocabularyManager.insertLocalVariable(ExpressionProcessor.extractSimpleName(smpName));
	}

	/**
	 * Extract the expression of the return statement.
	 * @param returnStatement The return statement.
	 */
	private static void extractReturnStatement(ReturnStatement returnStatement) {
		Expression exp = returnStatement.getExpression();
		if (exp != null)
			MethodVocabularyManager.insertLocalVariable(ExpressionProcessor.extractExpression(exp));
	}
	
	/**
	 * Extract the type, the name and the expression that compounds the 
	 * variable declaration.
	 * @param varDecl The variable declaration.
	 */
	private static void extracSingleVariableDeclartion(
			SingleVariableDeclaration varDecl) {
		Expression exp = varDecl.getInitializer();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
		
		SimpleName smp = varDecl.getName();
		if (smp != null) 
			MethodVocabularyManager.insertLocalVariable(ExpressionProcessor.extractSimpleName(smp));
	}

	/**
	 * Extract all the arguments of the super constructor invocation.
	 * @param superConstInv The super constructor invocation
	 */
	@SuppressWarnings("unchecked")
	private static void extractSuperConstrutorInvocation(SuperConstructorInvocation superConstInv) {
		List<Expression> arguments = superConstInv.arguments();
		for (Expression expression: arguments) {
			if (expression != null)
				ExpressionProcessor.extractExpression(expression);
		}
		
		Expression exp = superConstInv.getExpression();
		if (exp !=null)
			ExpressionProcessor.extractExpression(exp);
	}

	/**
	 * Extract the expression of the 'Switch Case' statement.
	 * @param switchCase The switch case statement.
	 */
	private static void extractSwitchCase(SwitchCase switchCase) {
		Expression exp = switchCase.getExpression();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
	}

	/**
	 * Extract the statements that are declared inside the switch statement.
	 * @param switchStatement The switch statement.
	 */
	@SuppressWarnings("unchecked")
	private static void extractSwitchStatement(SwitchStatement switchStatement) {
		Expression exp = switchStatement.getExpression();
		if (exp != null)
			MethodVocabularyManager.insertLocalVariable(ExpressionProcessor.extractExpression(exp));
		 
		List<Statement> statements = switchStatement.statements();
		for (Statement s : statements) {
			if (s != null) 
				extractBody(s);
		}
	}

	/**
	 * Extract the body and the expression that compounds the synchronized 
	 * statement.
	 * @param synchronizedStatement The synchronized statement.
	 */
	private static void extractSynchronizedStatement(
			SynchronizedStatement synchronizedStatement) {
		Statement body = synchronizedStatement.getBody();
		if (body != null)
			extractBody(body);
		
		Expression exp = synchronizedStatement.getExpression();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
	}

	/**
	 * Extract the expression present at throw statement.
	 * @param throwStatement The throw statement.
	 */
	private static void extractThrowStatement(ThrowStatement throwStatement) {
		Expression exp = throwStatement.getExpression();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
	}

	/**
	 * Extract the information from the body of the 'try statement',
	 * the body of the 'catch statement' and the 'finally statement'.
	 * @param tryStatement The try statement.
	 */
	@SuppressWarnings("unchecked")
	private static void extractTryStatement(TryStatement tryStatement) {
		List<CatchClause> catchClause = tryStatement.catchClauses();
		for (CatchClause cc: catchClause ) {
			if (cc != null) 
				extractCatchClause(cc);
		}

		Statement body = tryStatement.getBody();
		if (body != null)
			extractBody(body);
		
		Statement finallyBody = tryStatement.getFinally();
		if (finallyBody != null)
			extractBody(finallyBody);
	}

	/**
	 * Extract type, name and expression that defines the variable declaration.
	 * @param variableDeclarationStatement The variable declaration.
	 */
	@SuppressWarnings("unchecked")
	private static void extractVariableDeclarationStatement(
			VariableDeclarationStatement variableDeclarationStatement) {
		//extrai todos os fragmentos que compoẽm a declaração de uma variável
		List<VariableDeclarationFragment> list = variableDeclarationStatement.fragments();
		for (VariableDeclarationFragment v : list) {
			if (v != null)
				ExpressionProcessor.extractVariableDeclarationFragment(v);
		}
	}

	/**
	 * Extract the expression and the body of the 'while statement'.
	 * @param whileStatement The while statement.
	 */
	private static void extractWhileStatement(WhileStatement whileStatement) {
		Statement body = whileStatement.getBody();
		if (body != null)
			extractBody(body);
		
		Expression exp = whileStatement.getExpression();
		if (exp != null) {
			MethodVocabularyManager.insertLocalVariable(ExpressionProcessor.extractExpression(exp));
		}
	}
}