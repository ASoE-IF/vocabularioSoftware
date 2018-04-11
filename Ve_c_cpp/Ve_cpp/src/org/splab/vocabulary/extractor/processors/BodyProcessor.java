package org.splab.vocabulary.extractor.processors;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCatchHandler;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTAmbiguousStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCaseStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompoundStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDeclarationStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDoStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTExpressionStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTForStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTGotoStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTIfStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLabelStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTReturnStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSwitchStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTryBlockStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTWhileStatement;
import org.splab.vocabulary.extractor.vloccount.EntityType;

/**
 * This class is responsible for extracting informations from the function's
 * body. All the informations extract should be Statement type.
 * 
 * BodyProcessor extract informations such as: function call and variable
 * declaration.
 * 
 * @author Israel Gomes de Lima Baseado na BodyProcessor do extrator original
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
	private StringBuffer simpleDeclarationVXL;

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
		this.simpleDeclarationVXL = new StringBuffer();

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
		if (statement instanceof CPPASTAmbiguousStatement)
			;

		if (statement instanceof CPPASTCaseStatement)
			extractCaseStatement((CPPASTCaseStatement) statement);

		if (statement instanceof CPPASTCompoundStatement)
			extractCompoundStatement((CPPASTCompoundStatement) statement);

		if (statement instanceof CPPASTDeclarationStatement)
			extractDeclarationStatement((CPPASTDeclarationStatement) statement);

		if (statement instanceof CPPASTDoStatement)
			extractDoStatement((CPPASTDoStatement) statement);

		if (statement instanceof CPPASTExpressionStatement)
			extractExpressionStatement((CPPASTExpressionStatement) statement);

		if (statement instanceof CPPASTForStatement)
			extractForStatement((CPPASTForStatement) statement);

		if (statement instanceof CPPASTGotoStatement)
			extractGotoStatement((CPPASTGotoStatement) statement);

		if (statement instanceof CPPASTIfStatement)
			extractIfStatement((CPPASTIfStatement) statement);

		if (statement instanceof CPPASTLabelStatement)
			extractLabelStatement((CPPASTLabelStatement) statement);

		if (statement instanceof CPPASTReturnStatement)
			extractReturnStatement((CPPASTReturnStatement) statement);

		if (statement instanceof CPPASTSwitchStatement)
			extractSwitchStatement((CPPASTSwitchStatement) statement);

		if (statement instanceof CPPASTTryBlockStatement)
			extractTryBlockStatement((CPPASTTryBlockStatement) statement);

		if (statement instanceof CPPASTWhileStatement)
			extractWhileStatement((CPPASTWhileStatement) statement);
	}

	/**
	 * Extract the variable declarations and the block of code that are inside
	 * the 'Case' from 'SwitchCase Statement'.
	 */
	private void extractCaseStatement(CPPASTCaseStatement caseStatement) {
		IASTExpression exp = caseStatement.getExpression();

		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
	}

	/**
	 * Extract the compound statement.
	 */
	private void extractCompoundStatement(CPPASTCompoundStatement compoundStatement) {
		for (IASTStatement state : compoundStatement.getStatements()) {
			if (state != null)
				extractBody(state);
		}
	}

	/**
	 * Extract the simples declarations statement. Extract the internal
	 * functions definitions.
	 */
	private void extractDeclarationStatement(CPPASTDeclarationStatement declarationStatement) {
		IASTDeclaration declaration = declarationStatement.getDeclaration();

		
		if (declaration != null && declaration instanceof CPPASTSimpleDeclaration) {
			extractSimpleDeclarationStatement((CPPASTSimpleDeclaration) declaration);
		}
		if (declaration instanceof CPPASTFunctionDefinition) {
			FunctionProcessor functions = new FunctionProcessor((CPPASTFunctionDefinition) declaration,
					EntityType.INNER_FUNCTION);
			functionVXL.append(functions.getVxlFragment());
		}
	}
	
	private void extractSimpleDeclarationStatement(CPPASTSimpleDeclaration simpleDeclarationStatement) {
		
		DeclarationsType tipoDeclaracao = Declarations.declarations(simpleDeclarationStatement, functionVocabulary, true);

		if (tipoDeclaracao == DeclarationsType.ENUM) {
			enumVXL.append(Declarations.getVxlFragment());
		} else {
			if (tipoDeclaracao == DeclarationsType.STRUCT) {
				structVXL.append(Declarations.getVxlFragment());
			} else {
				if (tipoDeclaracao == DeclarationsType.UNION) {
					unionVXL.append(Declarations.getVxlFragment());
				} else {
					simpleDeclarationVXL.append(Declarations.getVxlFragment());
				}
			}
		}
	}

	/**
	 * Extract the default statement.
	 */
	// private void extractDefaultStatement(CPPASTDefaultStatement
	// defaultStatement) {}

	/**
	 * Extract the body inside a 'Do Statement' and the expression that makes
	 * the 'Do Statement' to be executed.
	 * 
	 * @param doStatement
	 *            The Do Statement.
	 */
	private void extractDoStatement(CPPASTDoStatement doStatement) {
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
	private void extractExpressionStatement(CPPASTExpressionStatement expressionStatement) {
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
	private void extractForStatement(CPPASTForStatement forStatement) {
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
	private void extractGotoStatement(CPPASTGotoStatement gotoStatement) {
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
	private void extractIfStatement(CPPASTIfStatement ifStatement) {
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
	private void extractLabelStatement(CPPASTLabelStatement labelStatement) {
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
	private void extractReturnStatement(CPPASTReturnStatement returnStatement) {
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
	private void extractSwitchStatement(CPPASTSwitchStatement switchStatement) {
		IASTExpression exp = switchStatement.getControllerExpression();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);

		IASTStatement body = switchStatement.getBody();
		if (body != null)
			extractBody(body);
	}

	private void extractTryBlockStatement(CPPASTTryBlockStatement tryBlockStatement) {

		ICPPASTCatchHandler[] catchHandler = tryBlockStatement.getCatchHandlers();
		for (ICPPASTCatchHandler ch : catchHandler) {
			if (ch != null)
				extractCatchClause(ch);
		}

		IASTStatement body = tryBlockStatement.getTryBody();
		if (body != null)
			extractBody(body);
	}

	private void extractCatchClause(ICPPASTCatchHandler catchHandler) {
		
		IASTStatement body = catchHandler.getCatchBody();
		if (body != null)
			extractBody(body);

		CPPASTSimpleDeclaration varDecl = (CPPASTSimpleDeclaration )catchHandler.getDeclaration();
		if (varDecl != null)
			extractSimpleDeclarationStatement(varDecl);
	}

	/**
	 * Extract the expression and the body of the 'while statement'.
	 * 
	 * @param whileStatement
	 *            The while statement.
	 */
	private void extractWhileStatement(CPPASTWhileStatement whileStatement) {
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
		vxlFragment.append(simpleDeclarationVXL);
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