package org.splab.vocabulary.extractor.processors;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTAmbiguousStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCaseStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompoundStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTDeclarationStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTDoStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTExpressionStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTForStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTGotoStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTIfStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTLabelStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTReturnStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSwitchStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTWhileStatement;
import org.splab.vocabulary.extractor.vloccount.EntityType;

/**
 * This class is responsible for extracting informations from the function's
 * body. All the informations extract should be Statement type.
 * 
 * BodyProcessor extract informations such as: function call and variable
 * declaration.
 * 
 * @author Israel Gomes de Lima
 * Baseado na BodyProcessor do extrator original
 * @since september 06, 2017.
 */
public class BodyProcessor {
	/**
	 * Fragmentos de vxl correspondentes a cada tipo de entidade extrutural do C
	 **/
	private StringBuffer vxlFragment;
	private StringBuffer functionVXL;
	private StringBuffer enumVXL;
	private StringBuffer structVXL;
	private StringBuffer unionVXL;

	private FunctionVocabularyManager functionVocabulary;

	/**
	 * Construtor para novo objeto do tipo BodyProcessor. Todo arquivo e toda
	 * função tem um corpo para extração de dados
	 */
	public BodyProcessor(FunctionVocabularyManager functionVocabulary) {
		this.vxlFragment = new StringBuffer();
		
		this.functionVXL = new StringBuffer();
		this.enumVXL = new StringBuffer();
		this.structVXL = new StringBuffer();
		this.unionVXL = new StringBuffer();
		
		this.functionVocabulary = functionVocabulary;
	}

	/**
	 * Identifier the instance of the body to extract all the information about
	 * the method's body.
	 * 
	 * @param body
	 *            The method's body that will be analyzed.
	 */
	public void extractBody(IASTStatement statement) {
		if (statement instanceof CASTAmbiguousStatement);

		if (statement instanceof CASTCaseStatement)
			extractCaseStatement((CASTCaseStatement) statement);

		if (statement instanceof CASTCompoundStatement)
			extractCompoundStatement((CASTCompoundStatement) statement);

		if (statement instanceof CASTDeclarationStatement)
			extractDeclarationStatement((CASTDeclarationStatement) statement);

		if (statement instanceof CASTDoStatement)
			extractDoStatement((CASTDoStatement) statement);

		if (statement instanceof CASTExpressionStatement)
			extractExpressionStatement((CASTExpressionStatement) statement);

		if (statement instanceof CASTForStatement)
			extractForStatement((CASTForStatement) statement);

		if (statement instanceof CASTGotoStatement)
			extractGotoStatement((CASTGotoStatement) statement);

		if (statement instanceof CASTIfStatement)
			extractIfStatement((CASTIfStatement) statement);

		if (statement instanceof CASTLabelStatement)
			extractLabelStatement((CASTLabelStatement) statement);

		if (statement instanceof CASTReturnStatement)
			extractReturnStatement((CASTReturnStatement) statement);

		if (statement instanceof CASTSwitchStatement)
			extractSwitchStatement((CASTSwitchStatement) statement);

		if (statement instanceof CASTWhileStatement)
			extractWhileStatement((CASTWhileStatement) statement);
	}
	
	/**
	 * Extract the variable declarations and the block of code that are
	 * inside the 'Case' from 'SwitchCase Statement'.
	 */
	private void extractCaseStatement(CASTCaseStatement caseStatement) {
		IASTExpression exp = caseStatement.getExpression();
		
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
	}

	/**
	 * Extract the compound statement.
	 */
	private void extractCompoundStatement(CASTCompoundStatement compoundStatement) {
		for (IASTStatement state : compoundStatement.getStatements()) {
			if (state != null)
				extractBody(state);
		}
	}


	/**
	 * Extract the simples declarations statement. Extract the internal
	 * functions definitions.
	 */
	private void extractDeclarationStatement(CASTDeclarationStatement declarationStatement) {
		IASTDeclaration declaration = declarationStatement.getDeclaration();
		
		DeclarationsType tipoDeclaracao;
		if (declaration != null && declaration instanceof CASTSimpleDeclaration) {
			tipoDeclaracao = Declarations.declarations((CASTSimpleDeclaration) declaration, functionVocabulary, true);

			if (tipoDeclaracao == DeclarationsType.ENUM) {
				enumVXL.append(Declarations.getVxlFragment());
			} else {
				if (tipoDeclaracao == DeclarationsType.STRUCT) {
					structVXL.append(Declarations.getVxlFragment());
				} else {
					if (tipoDeclaracao == DeclarationsType.UNION) {
						unionVXL.append(Declarations.getVxlFragment());
					}
				}
			}
		}
		if (declaration instanceof CASTFunctionDefinition) {
			FunctionProcessor functions = new FunctionProcessor((CASTFunctionDefinition) declaration, true, EntityType.INNER_FUNCTION);
			functionVXL.append(functions.getVxlFragment());
		}
	}
	/**
	 * Extract the body inside a 'Do Statement' and the expression that makes
	 * the 'Do Statement' to be executed.
	 * 
	 * @param doStatement
	 *            The Do Statement.
	 */
	private void extractDoStatement(CASTDoStatement doStatement) {
		IASTStatement body = doStatement.getBody();
		if (body != null)
			extractBody(body);

		IASTExpression exp = doStatement.getCondition();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
	}

	/**
	 * 
	 * @param expressionStatement
	 */
	private void extractExpressionStatement(CASTExpressionStatement expressionStatement) {
		IASTExpression exp = expressionStatement.getExpression();

		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
	}

	/**
	 * Extract for informations.
	 * 
	 * @param forStatement
	 *            The for statement.
	 */
	private void extractForStatement(CASTForStatement forStatement) {
		IASTStatement body = forStatement.getBody();
		if (body != null)
			extractBody(body);

		IASTExpression exp = forStatement.getConditionExpression();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);

		IASTStatement initializers = forStatement.getInitializerStatement();
		if (initializers != null)
			extractBody(initializers);

		IASTExpression iteration = forStatement.getIterationExpression();
		if (iteration != null)
			ExpressionProcessor.extractExpression(iteration);
	}

	/**
	 * Extract the goto statement.
	 */
	private void extractGotoStatement(CASTGotoStatement gotoStatement) {
		String identifier = gotoStatement.getName().toString();
		functionVocabulary.insertLocalVariable(identifier, "", "");
	}

	/**
	 * Extract all the information existing in a 'if else' statement. Extract
	 * the expression of the 'If' and the body of the 'If' and 'Else' statement.
	 * 
	 * @param ifStatement
	 *            The If/Else statement.
	 */
	private void extractIfStatement(CASTIfStatement ifStatement) {
		IASTStatement elseBody = ifStatement.getElseClause();
		if (elseBody != null)
			extractBody(elseBody);

		IASTExpression exp = ifStatement.getConditionExpression();
		if (exp != null) {
			ExpressionProcessor.extractExpression(exp);
		}

		IASTStatement thenBody = ifStatement.getThenClause();
		if (thenBody != null)
			extractBody(thenBody);
	}

	/**
	 * Extract information of the label statement.
	 * 
	 * @param labelStatement
	 */
	private void extractLabelStatement(CASTLabelStatement labelStatement) {
		IASTStatement statement = labelStatement.getNestedStatement();
		if (statement != null)
			extractBody(statement);

		String identifier = labelStatement.getName().toString();
		if (identifier != null)
			functionVocabulary.insertLocalVariable(identifier, "", "");
	}
	
	/**
	 * Extract the expression of the return statement.
	 * 
	 * @param returnStatement
	 *            The return statement.
	 */
	private void extractReturnStatement(CASTReturnStatement returnStatement) {
		IASTExpression exp = returnStatement.getReturnValue();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
	}

	/**
	 * Extract the expression of the 'Switch Case' statement.
	 * 
	 * @param switchCase
	 *            The switch case statement.
	 */
	private void extractSwitchStatement(CASTSwitchStatement switchStatement) {
		IASTExpression exp = switchStatement.getControllerExpression();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);

		IASTStatement body = switchStatement.getBody();
		if (body != null)
			extractBody(body);
	}

	/**
	 * Extract the expression and the body of the 'while statement'.
	 * 
	 * @param whileStatement
	 *            The while statement.
	 */
	private void extractWhileStatement(CASTWhileStatement whileStatement) {
		IASTStatement body = whileStatement.getBody();
		if (body != null)
			extractBody(body);

		IASTExpression exp = whileStatement.getCondition();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
	}

	/**
	 * Return the vxl fragment
	 */
	public StringBuffer getVxlFragment() {
		vxlFragment.append(enumVXL);
		vxlFragment.append(structVXL);
		vxlFragment.append(unionVXL);
		vxlFragment.append(functionVXL);

		return this.vxlFragment;
	}

	/**
	 * Return the function internal vocabulary
	 */
	public FunctionVocabularyManager getFunctionVocabulary() {
		return functionVocabulary;
	}
}