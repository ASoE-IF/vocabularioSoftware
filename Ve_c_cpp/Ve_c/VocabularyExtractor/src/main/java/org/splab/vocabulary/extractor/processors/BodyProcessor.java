package org.splab.vocabulary.extractor.processors;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
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
import org.splab.vocabulary.extractor.processors.vocabulay.manager.FunctionVocabularyManager;
import org.splab.vocabulary.extractor.processors.vocabulay.manager.VocabularyManager;
import org.splab.vocabulary.extractor.vloccount.EntityType;

/**
 * Esta classe é responsável por extrair os statments e as informações do corpo
 * de uma função ou de um arquivo.
 * 
 * @author Israel Gomes de Lima
 *
 */
public class BodyProcessor {
	/**
	 * Fragmentos de vxl correspondentes a cada tipo de entidade extrutural do C
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
	 * Extrai o conteudo que está dentro de um "case" em um bloco de switch
	 * 
	 * @param caseStatement
	 */
	private void extractCaseStatement(CASTCaseStatement caseStatement) {
		IASTExpression exp = caseStatement.getExpression();

		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
	}

	/**
	 * Recebe uma estrutura que possui corpo (como while, por exemplo), extrai
	 * seu corpo de designa o tratador.
	 * 
	 * @param compoundStatement
	 */
	private void extractCompoundStatement(CASTCompoundStatement compoundStatement) {
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
	private void extractDeclarationStatement(CASTDeclarationStatement declarationStatement) {
		IASTDeclaration declaration = declarationStatement.getDeclaration();

		if (declaration != null && declaration instanceof CASTSimpleDeclaration) {
			extractSimpleDeclarationStatement((CASTSimpleDeclaration) declaration);
		}
		if (declaration instanceof CASTFunctionDefinition) {
			FunctionProcessor functions = new FunctionProcessor((CASTFunctionDefinition) declaration, true,
					EntityType.INNER_FUNCTION, indentationLevel);
			functionVXL.append(functions.getVxlFragment());
		}
	}

	/**
	 * Extrai declarações de variáveis, struct, union, enum, classes, etc.
	 * 
	 * @param simpleDeclarationStatement
	 */
	private void extractSimpleDeclarationStatement(CASTSimpleDeclaration simpleDeclarationStatement) {

		DeclarationProcessor declProcessor = new DeclarationProcessor(vocabularyManager);
		declProcessor.extractDeclaration(simpleDeclarationStatement, indentationLevel);
		vxlFragment.append(declProcessor.getVxlFragment());
	}

	/**
	 * Extrai o conteudo e a condição em um do/while
	 * 
	 * @param doStatement
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
	 * Extrai os participantes de uma expressão qualquer
	 * 
	 * @param expressionStatement
	 */
	private void extractExpressionStatement(CASTExpressionStatement expressionStatement) {
		IASTExpression exp = expressionStatement.getExpression();

		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
	}

	/**
	 * Extrai o corpo e a expressão condicional do for
	 * 
	 * @param forStatement
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
	 * Extrai uma expressão com goto assim como o seu label
	 * 
	 * @param gotoStatement
	 */
	private void extractGotoStatement(CASTGotoStatement gotoStatement) {
		String identifier = gotoStatement.getName().toString();
		FunctionVocabularyManager funcVoc = (FunctionVocabularyManager) vocabularyManager;
		funcVoc.insertLocalVariable(identifier, "", "");
	}

	/**
	 * Trata um if/else e extrai a expressão condicional assim como também o
	 * conteudo de seu corpo.
	 * 
	 * @param ifStatement
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
	 * Recebe um label e designar o seu tratamento ou armazenamento.
	 * 
	 * @param labelStatement
	 */
	private void extractLabelStatement(CASTLabelStatement labelStatement) {
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
	private void extractReturnStatement(CASTReturnStatement returnStatement) {
		IASTExpression exp = returnStatement.getReturnValue();
		if (exp != null)
			ExpressionProcessor.extractExpression(exp);
	}

	/**
	 * Extrai a expressão em um switch e também o conteudo do seu corpo.
	 * 
	 * @param switchStatement
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
	 * Extrai o conteudo de um bloco while, tal como sua expressão condicional e
	 * o conteudo do seu corpo.
	 * 
	 * @param whileStatement
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
	 * Retorna o fragmento de vxl organizado.
	 * 
	 * @return
	 */
	public StringBuffer getVxlFragment() {
		vxlFragment.append(functionVXL);

		return this.vxlFragment;
	}
}