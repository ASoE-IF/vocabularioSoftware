package org.splab.vocabulary.extractor.processors;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCatchHandler;
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
import org.splab.vocabulary.extractor.processors.vocabulay.manager.FunctionVocabularyManager;
import org.splab.vocabulary.extractor.processors.vocabulay.manager.VocabularyManager;
import org.splab.vocabulary.extractor.vloccount.EntityType;

/**
 * Esta classe é responsável por extrair os statments e as
 * informações do corpo de uma função ou de um arquivo.
 * 
 * @author Israel Gomes de Lima
 *
 */
public class BodyProcessor {
	/**
	 * Fragmentos de vxl correspondentes a cada tipo de entidade extrutural do
	 * C++
	 **/
	private StringBuffer vxlFragment;
	private StringBuffer functionVXL;
	private int indentationLevel;

	/**
	 * Mantem o vocabulary manager do chamador
	 */
	private VocabularyManager vocabularyManager;

	/**
	 * Construtor para novo objeto do tipo BodyProcessor. Todo arquivo e toda
	 * função tem um corpo para extração de dados
	 */
	public BodyProcessor(VocabularyManager vocabularyManager, int indentationLevel) {
		this.vxlFragment = new StringBuffer();
		this.functionVXL = new StringBuffer();

		this.vocabularyManager = vocabularyManager;
		this.indentationLevel = indentationLevel;
	}

	/**
	 * Recebe um statement e identifica o seu tipo estrutural para poder
	 * designar o método adequado para processá-lo.
	 * 
	 * @param statement
	 */
	public void extractBody(IASTStatement statement) {
		
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
	 * Extrai o conteudo que está dentro de um "case" em um bloco de
	 * switch
	 * @param caseStatement
	 */
	private void extractCaseStatement(CPPASTCaseStatement caseStatement) {
		IASTExpression exp = caseStatement.getExpression();

		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
	}

	/**
	 * Recebe uma estrutura que possui corpo (como while, por exemplo),
	 * extrai seu corpo de designa o tratador.
	 *  
	 * @param compoundStatement
	 */
	private void extractCompoundStatement(CPPASTCompoundStatement compoundStatement) {
		for (IASTStatement state : compoundStatement.getStatements()) {
			if (state != null)
				extractBody(state);
		}
	}

	/**
	 * Extrai declarações simples ou declarações de funções internas
	 * 
	 * @param declarationStatement
	 */
	private void extractDeclarationStatement(CPPASTDeclarationStatement declarationStatement) {
		IASTDeclaration declaration = declarationStatement.getDeclaration();

		if (declaration != null && declaration instanceof CPPASTSimpleDeclaration) {
			extractSimpleDeclarationStatement((CPPASTSimpleDeclaration) declaration);
		}
		if (declaration instanceof CPPASTFunctionDefinition) {
			FunctionProcessor functions = new FunctionProcessor((CPPASTFunctionDefinition) declaration,
					EntityType.INNER_FUNCTION, indentationLevel);
			functionVXL.append(functions.getVxlFragment());
		}
	}

	/**
	 * Extrai declarações de variáveis, struct, union, enum, classes, etc.
	 * 
	 * @param simpleDeclarationStatement
	 */
	private void extractSimpleDeclarationStatement(CPPASTSimpleDeclaration simpleDeclarationStatement) {

		DeclarationProcessor declProcessor = new DeclarationProcessor(vocabularyManager);
		declProcessor.extractDeclaration(simpleDeclarationStatement, indentationLevel);
		vxlFragment.append(declProcessor.getVxlFragment());
	}

	/**
	 * Extrai o conteudo e a condição em um do/while
	 * 
	 * @param doStatement
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
	 * Extrai os participantes de uma expressão qualquer
	 * 
	 * @param expressionStatement
	 */
	private void extractExpressionStatement(CPPASTExpressionStatement expressionStatement) {
		IASTExpression exp = expressionStatement.getExpression();

		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
	}

	/**
	 * Extrai o corpo e a expressão condicional do for
	 * 
	 * @param forStatement
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
	 * Extrai uma expressão com goto assim como o seu label
	 * @param gotoStatement
	 */
	private void extractGotoStatement(CPPASTGotoStatement gotoStatement) {
		String identifier = gotoStatement.getName().toString();
		FunctionVocabularyManager funcVoc = (FunctionVocabularyManager) vocabularyManager;
		funcVoc.insertLocalVariable(identifier, "", "");
	}

	/**
	 * Trata um if/else e extrai a expressão condicional assim como
	 * também o conteudo de seu corpo.
	 * 
	 * @param ifStatement
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
	 * Recebe um label e designar o seu tratamento ou armazenamento.
	 * 
	 * @param labelStatement
	 */
	private void extractLabelStatement(CPPASTLabelStatement labelStatement) {
		IASTStatement statement = labelStatement.getNestedStatement();
		if (statement != null)
			extractBody(statement);

		String identifier = labelStatement.getName().toString();
		if (identifier != null) {
			FunctionVocabularyManager funcVoc = (FunctionVocabularyManager) vocabularyManager;
			funcVoc.insertLocalVariable(identifier, "", "");
		}
	}

	/**
	 * Extrai a expressão em um return.
	 * 
	 * @param returnStatement
	 */
	private void extractReturnStatement(CPPASTReturnStatement returnStatement) {
		IASTExpression exp = returnStatement.getReturnValue();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
	}

	/**
	 * Extrai a expressão em um switch e também o conteudo do seu corpo.
	 * 
	 * @param switchStatement
	 */
	private void extractSwitchStatement(CPPASTSwitchStatement switchStatement) {
		IASTExpression exp = switchStatement.getControllerExpression();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);

		IASTStatement body = switchStatement.getBody();
		if (body != null)
			extractBody(body);
	}

	/**
	 * Recupetra um block try e extrai o conteudo de seu corpo.
	 * 
	 * @param tryBlockStatement
	 */
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

	/**
	 * Extrai uma clausula catch de um try/catch tratando os seus
	 * parâmetros e também o conteudo de seu corpo.
	 * 
	 * @param catchHandler
	 */
	private void extractCatchClause(ICPPASTCatchHandler catchHandler) {

		IASTStatement body = catchHandler.getCatchBody();
		if (body != null)
			extractBody(body);

		CPPASTSimpleDeclaration varDecl = (CPPASTSimpleDeclaration) catchHandler.getDeclaration();
		if (varDecl != null)
			extractSimpleDeclarationStatement(varDecl);
	}

	/**
	 * Extrai o conteudo de um bloco while, tal como sua expressão
	 * condicional e o conteudo do seu corpo.
	 * 
	 * @param whileStatement
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
	 * Retorna o fragmento de vxl organizado.
	 * @return
	 */
	public StringBuffer getVxlFragment() {
		vxlFragment.append(functionVXL);

		return this.vxlFragment;
	}
}