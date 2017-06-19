package Vocabulary.Processors;

import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTAmbiguousExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTArrayDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTArraySubscriptExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTBinaryExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCastExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompoundStatementExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTConditionalExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTContinueStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTDeclarationStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTExpressionList;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTExpressionStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFieldReference;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionCallExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTIdExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTInitializerExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTLiteralExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTProblemExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTTypeIdExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTTypeIdInitializerExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTUnaryExpression;
import org.eclipse.core.internal.expressions.ExpressionStatus;
import org.eclipse.core.internal.expressions.Expressions;

import Vocabulary.Extractor.Util.VxlManager;

public class ExpressionProcessor
{	
	public static String extractExpression(IASTExpression expression)
	{
		if(expression instanceof CASTBinaryExpression)
			extractBinaryExpression((CASTBinaryExpression)expression);
		
		if(expression instanceof CASTArraySubscriptExpression)
		{
			
		}
		
		if(expression instanceof CASTInitializerExpression)
			//System.out.println("inicializer");
		//System.out.println(getEdentacao() + "InitializerExpression: ");
		
		if(expression instanceof CASTFieldReference)
			//System.out.println("field reference");
		//CASTFieldReference e = (CASTFieldReference)expression;

		
		if(expression instanceof CASTIdExpression)
			//System.out.println("id");
		//CASTIdExpression e = (CASTIdExpression) expression;
		
		if(expression instanceof CASTTypeIdInitializerExpression)
			//System.out.println("typeid");
		//CASTTypeIdInitializerExpression e = (CASTTypeIdInitializerExpression) expression;
		
		
		if(expression instanceof CASTExpressionList)
			//System.out.println("list");
		//CASTExpressionList e = (CASTExpressionList) expression;
		
		/**************processa os tipos de expresões existentes em C**************/
		if(expression instanceof CASTAmbiguousExpression)
			//System.out.println("Ambiguos");
			//CASTAmbiguousExpression e = (CASTAmbiguousExpression)expression;
		
		if(expression instanceof CASTCompoundStatementExpression)
			//System.out.println("Compound Statement Expression: ");
		
		if(expression instanceof CASTProblemExpression)
			//System.out.println("Problem Expression: ");
		
		if(expression instanceof CASTArraySubscriptExpression)
		{
			CASTArraySubscriptExpression d = (CASTArraySubscriptExpression)expression;
			
			if(expression instanceof CASTFieldReference)
			{
				CASTFieldReference e = (CASTFieldReference)d.getArrayExpression();
				//System.err.println("field Array Subscript Expression: " + e.getFieldName());
			}
			
			if(expression instanceof CASTIdExpression)
			{
				CASTIdExpression e = (CASTIdExpression)d.getArrayExpression();

				//System.out.println("Array Subscript Expression: " + e.getName());
			}
		}
		
		if(expression instanceof CASTUnaryExpression)
		{
			CASTUnaryExpression e = (CASTUnaryExpression)expression;
			//System.out.println("Unary Expression: " + e.getRawSignature());
			//ExpressionStatement(e.getOperand());
		}
		if(expression instanceof CASTInitializerExpression)
		{
			//System.out.println("InitializerExpression: ");
		}
		
		if(expression instanceof CASTFieldReference)
		{
			CASTFieldReference e = (CASTFieldReference)expression;
			//System.err.println("field referencia: " + e.getFieldName() + " " + e.getFieldOwner().getRawSignature());
			//System.err.println(getEdentacao() + e.getRawSignature());
			//xpressionStatement(e.getFieldOwner());
		}
	
		
		if(expression instanceof CASTIdExpression)
		{
			CASTIdExpression e = (CASTIdExpression) expression;

			//System.out.println("Id expression: " + e.getName() + " ");

		}
		
		if(expression instanceof CASTTypeIdInitializerExpression)
		{
			CASTTypeIdInitializerExpression e = (CASTTypeIdInitializerExpression) expression;

			//System.out.println("inicializer expression: " + e.getInitializer().getRawSignature() + " ");

		}
		
		
		if(expression instanceof CASTExpressionList)
		{
			CASTExpressionList e = (CASTExpressionList) expression;

			//System.out.println("Lista de expressões: " + e.getExpressions());
		}
		/**************processa os tipos de expresões existentes em C**************/
		if(expression instanceof CASTAmbiguousExpression)
		{
			//System.out.println("Ambiguous Expression: ");
			CASTAmbiguousExpression e = (CASTAmbiguousExpression)expression;
		}
		
		if(expression instanceof CASTCompoundStatementExpression)
		{
			//System.out.println("Compound Statement Expression: ");
		}
		
		if(expression instanceof CASTProblemExpression)
		{
			//System.out.println("Problem Expression: ");
		}
		
		if(expression instanceof CASTCastExpression)
		{
			//System.out.println("cast");
			CASTCastExpression e = (CASTCastExpression)expression;
			//extractCastExpression((CastExpression) expression);
		}
		
		if (expression instanceof CASTLiteralExpression) 
			extractStringLiteral((CASTLiteralExpression) expression);

		if (expression instanceof CASTConditionalExpression); 
			//System.out.println("condicional");
			//extractConditionalExpression((ConditionalExpression) expression);

		if(expression instanceof CASTFunctionCallExpression)
		{
			
			extractFunctionCall((CASTFunctionCallExpression)expression);
			
		}
		
		return "";
	}
	
	public static String extractExpression2(IASTExpression expression)
	{
		if(expression instanceof CASTBinaryExpression)
			extractBinaryExpression((CASTBinaryExpression)expression);
		
		if(expression instanceof CASTArraySubscriptExpression)
		{
			
		}
		
		if(expression instanceof CASTInitializerExpression)
			System.out.println("inicializer");
		//System.out.println(getEdentacao() + "InitializerExpression: ");
		

		if(expression instanceof CASTTypeIdInitializerExpression)
		{
			CASTTypeIdInitializerExpression e = (CASTTypeIdInitializerExpression) expression;
			System.out.println("typeid " + e.getRawSignature());
		
		}
		
		
		if(expression instanceof CASTExpressionList)
		{
			CASTExpressionList e = (CASTExpressionList) expression;
			System.out.println("list " + e.getRawSignature());
		}
		
		
		/**************processa os tipos de expresões existentes em C**************/
		if(expression instanceof CASTAmbiguousExpression)
		{
			CASTAmbiguousExpression e = (CASTAmbiguousExpression)expression;
			System.out.println("Ambiguos " + e.getRawSignature());
			
		}
		
		if(expression instanceof CASTCompoundStatementExpression)
			System.out.println("Compound Statement Expression: ");
		
		if(expression instanceof CASTProblemExpression)
			System.out.println("Problem Expression: ");
		
		if(expression instanceof CASTArraySubscriptExpression)
		{
			CASTArraySubscriptExpression d = (CASTArraySubscriptExpression)expression;
			
			if(expression instanceof CASTFieldReference)
			{
				CASTFieldReference e = (CASTFieldReference)d.getArrayExpression();
				System.err.println("field Array Subscript Expression: " + e.getFieldName());
			}
			
			if(expression instanceof CASTIdExpression)
			{
				CASTIdExpression e = (CASTIdExpression)d.getArrayExpression();

				System.out.println("Array Subscript Expression: " + e.getName());
			}
		}
		
		if(expression instanceof CASTUnaryExpression)
		{
			CASTUnaryExpression e = (CASTUnaryExpression)expression;
			System.out.println("Unary Expression: " + e.getRawSignature());
			//ExpressionStatement(e.getOperand());
		}
		if(expression instanceof CASTInitializerExpression)
		{
			System.out.println("InitializerExpression: ");
		}
		
		if(expression instanceof CASTFieldReference)
		{
			CASTFieldReference e = (CASTFieldReference)expression;
			System.err.println("field referencia: " + e.getFieldName() + " " + e.getFieldOwner().getRawSignature());
			//System.err.println(getEdentacao() + e.getRawSignature());
			//xpressionStatement(e.getFieldOwner());
		}
	
		
		if(expression instanceof CASTIdExpression)
		{
			CASTIdExpression e = (CASTIdExpression) expression;

			System.out.println("Id expression: " + e.getName() + " ");

		}
		
		if(expression instanceof CASTTypeIdInitializerExpression)
		{
			CASTTypeIdInitializerExpression e = (CASTTypeIdInitializerExpression) expression;

			System.out.println("inicializer expression: " + e.getInitializer().getRawSignature() + " ");

		}
		
		
		if(expression instanceof CASTExpressionList)
		{
			CASTExpressionList e = (CASTExpressionList) expression;

			System.out.println("Lista de expressões: " + e.getExpressions());
		}
		/**************processa os tipos de expresões existentes em C**************/
		if(expression instanceof CASTAmbiguousExpression)
		{
			System.out.println("Ambiguous Expression: ");
			CASTAmbiguousExpression e = (CASTAmbiguousExpression)expression;
		}
		
		if(expression instanceof CASTCompoundStatementExpression)
		{
			System.out.println("Compound Statement Expression: ");
		}
		
		if(expression instanceof CASTProblemExpression)
		{
			System.out.println("Problem Expression: ");
		}
		
		if(expression instanceof CASTCastExpression)
		{
			CASTCastExpression e = (CASTCastExpression)expression;
			System.out.println("cast " + e.getRawSignature());
			//extractCastExpression((CastExpression) expression);
		}
		
		if (expression instanceof CASTLiteralExpression) 
		{
			CASTLiteralExpression e = (CASTLiteralExpression)expression;
			System.out.println("literal " + e);
			//extractCharacterLiteral((CharacterLiteral)expression);
		}
		if (expression instanceof CASTConditionalExpression) 
			System.out.println("condicional");
			//extractConditionalExpression((ConditionalExpression) expression);

		
		if(expression instanceof CASTFunctionCallExpression)
		{
			System.out.println("Function Call " + expression.getRawSignature());
			
			CASTFunctionCallExpression function = (CASTFunctionCallExpression) expression;
			extractFunctionCall((CASTFunctionCallExpression)expression);
			
			
//			for(IASTInitializerClause args : argumentos)
//				ExpressionStatement((IASTExpression)args);
			//VxlManager.appendVXLFragment(VxlManager.methodInvocation(function.getFunctionNameExpression().toString(), 5));
		}
		
		return "";
	}
	
	private static void extractBinaryExpression(CASTBinaryExpression binaryExpression)
	{
		CASTBinaryExpression binary = (CASTBinaryExpression)binaryExpression;
		
		extractExpression(binary.getOperand1());
		extractExpression(binary.getOperand2());
	}
	
	private static void extractArraySubscriptExpression(CASTArraySubscriptExpression arraySubscriptExpression)
	{
		extractExpression(arraySubscriptExpression.getArrayExpression());
	}
	

	private static void extractFunctionCall(CASTFunctionCallExpression functionCall)
	{
		IASTInitializerClause[] arguments = functionCall.getArguments();

		for (IASTInitializerClause exp : arguments) {
				
			if (exp != null) 
			{
				extractExpression((IASTExpression) exp);
			}
		}

		String functionName = extractIdExpression((CASTIdExpression)functionCall.getFunctionNameExpression());
		
		if (functionName != null) {
			FunctionVocabularyManager.insertMethodInvocation(functionName);
		}
	}
	
	private static void extractStringLiteral(CASTLiteralExpression literalExpression)
	{
		if (literalExpression != null) {
			String normalizedLiteral = literalExpression.toString();
			
			FunctionVocabularyManager.insertLiteral(normalizedLiteral);
		}
	}
	
	public static String extractIdExpression(CASTIdExpression idExpression)
	{
		return idExpression.getName().toString();
	}

	/**
	 * Extract informations about the array creation.
	 * @param arrayCreation The array creation.
	 */


	/**
	 * Extract informations about the array initializer.
	 * @param arrayInitializer The array initializer.
	 */
//	@SuppressWarnings("unchecked")
//	private static void extractArrayInitializer(ArrayInitializer arrayInitializer) {
//		if (arrayInitializer != null) {
//			List<ArrayInitializer> array = arrayInitializer.expressions();
//			for (int i = 0; i < array.size(); i++) {
//				Object o = array.get(i);
//				if (o instanceof Expression) {
//					extractExpression((Expression) o);
//				}
//			}
//		}
//	}
//	
//	/**
//	 * Extract the informations of the assignment.
//	 * @param assignment The assignment.
//	 */
//	private static void extractAssignment(Assignment assignment) {
//		if (assignment != null) {
//			Expression expLeft = assignment.getLeftHandSide();
//			if (expLeft != null)
//				if (expLeft instanceof SimpleName) {
//					
//
//					VocabularyManager.insertLocalVariable(extractSimpleName((SimpleName) expLeft));
//				} else {
//					extractExpression(expLeft);
//				}
//			
//			Expression expRight = assignment.getRightHandSide();
//			if (expRight != null) {
//				if (expRight instanceof SimpleName) {
//					MethodVocabularyManager.insertLocalVariable(extractSimpleName((SimpleName) expRight));
//				} else {
//					extractExpression(expRight);
//				}
//			}
//		}
//	}
//	
//
//	/**
//	 * Extract the type and the expression of the cast expression.
//	 * @param castExpression The cast expression.
//	 */
//	private static void extractCastExpression(CastExpression castExpression) {
//		if (castExpression!=null) {
//			Expression exp = castExpression.getExpression();
//			if (exp != null)
//				MethodVocabularyManager.insertLocalVariable(extractExpression(exp));
//		}
//	}
//
//	/**
//	 * Extract the value of the character literal.
//	 * @param characterLiteral The character.
//	 */
//	private static void extractCharacterLiteral(CharacterLiteral characterLiteral) {
//		if (characterLiteral != null) {
//			String normalizedLiteral = StringProcessor.processString(characterLiteral.toString());
//			MethodVocabularyManager.insertLiteral(normalizedLiteral);
//		}
//	}
//
//	/**
//	 * Extract the idnetifier's from the instance creation.
//	 * @param classInstanceCreation The instance creation.
//	 */
//	@SuppressWarnings("unchecked")
//	private static void extractClassInstanceCreation(
//			ClassInstanceCreation classInstanceCreation) {
//		if (classInstanceCreation != null) {
//			List<Expression> arguments = classInstanceCreation.arguments();
//			for (Expression exp : arguments) {
//				if (exp != null) 
//					if (exp instanceof SimpleName) {
//						MethodVocabularyManager.insertLocalVariable(extractSimpleName( (SimpleName)exp));
//					} 
//					if (exp instanceof StringLiteral) {
//						extractStringLiteral((StringLiteral) exp);
//					}
//					
//			}
//		}
//	}
//	
//	/**
//	 * Extract all the informations existing at the conditional expression.
//	 * @param conditionalExpression The conditional expression.
//	 */
//	private static void extractConditionalExpression(
//			ConditionalExpression conditionalExpression) {
//		if (conditionalExpression != null) {
//			Expression elseExp = conditionalExpression.getElseExpression();
//			if (elseExp != null)
//				MethodVocabularyManager.insertLocalVariable(extractExpression(elseExp));
//					
//			Expression exp = conditionalExpression.getExpression();
//			if (exp != null) 
//				extractExpression(exp);
//			
//			Expression thenExp = conditionalExpression.getThenExpression();
//			if (thenExp != null)
//				MethodVocabularyManager.insertLocalVariable(extractExpression(thenExp));
//		}
//	}
//	
//	/**
//	 * Extract the information of the infix expression.
//	 * @param infixExpression The infix expression.
//	 */
//	@SuppressWarnings("unchecked")
//	private static void extractInfixExpression(InfixExpression infixExpression) {
//		if (infixExpression != null) {
//			List<Expression> extendedOperators = infixExpression.extendedOperands();
//			for (Expression exp : extendedOperators) {
//				if (exp != null)
//					extractExpression(exp);
//			}
//			
//			Expression leftExpression = infixExpression.getLeftOperand();
//			if (leftExpression != null ) {
//				if (leftExpression instanceof SimpleName) {
//					MethodVocabularyManager.insertLocalVariable(extractSimpleName((SimpleName)leftExpression));
//				} else {
//					extractExpression(leftExpression);
//				}
//			}
//			
//			Expression rightExpression = infixExpression.getRightOperand();
//			if (rightExpression != null) {
//				if (rightExpression instanceof SimpleName) {
//					MethodVocabularyManager.insertLocalVariable(extractSimpleName((SimpleName)rightExpression));
//				} else {
//					extractExpression(rightExpression);
//				}
//			}
//		}
//	}
	
	/**
	 * Extract the expressin of the instanceof statement.
	 * @param instanceofExpression The instanceof expression.
	 */
//	private static void extractInstanceofExpression(InstanceofExpression instanceofExpression) {
//		if (instanceofExpression != null) {
//			Expression exp = instanceofExpression.getLeftOperand();
//			if (exp != null)
//				MethodVocabularyManager.insertLocalVariable(extractExpression(exp));
//		}
//	}
	
	/**
	 * Extract the identifier expressed at the method invocation.
	 * @param mthInvocation The method invocation.
	 */
//	@SuppressWarnings("unchecked")
	
	
	
	/**
	 * Extract the identifier's name.
	 * @param name The identifier's name.
	 * @return 
	 */
//	private static String extractName(Name name) {
//		if (name != null)
//			return name.getFullyQualifiedName();
//		return "";
//	}
//
//	/**
//	 * Extract the parenthesized informations.
//	 * @param parenthesizedExpression The parenthesized expression.
//	 */
//	private static void extractParenthesizedExpression(
//			ParenthesizedExpression parenthesizedExpression) {
//		if (parenthesizedExpression != null) {
//			Expression exp = parenthesizedExpression.getExpression();
//			if (exp != null) 
//				extractExpression(exp);
//		}	
//	}
//	
//	/**
//	 * Extract the information about the postfix expression.
//	 * @param postfixExpression The postfix expression.
//	 */
//	private static void extractPostfixExpression(PostfixExpression postfixExpression) {
//		if (postfixExpression != null) {
//			Expression exp = postfixExpression.getOperand();
//			if (exp != null)
//				if (exp instanceof SimpleName) {
//					MethodVocabularyManager.insertLocalVariable(extractSimpleName((SimpleName)exp));
//				} else { 
//					extractExpression(exp);
//				}
//		}
//	}
//	
//	/**
//	 * Extract the information about the prefix expression.
//	 * @param prefixExpression The prefix expression.
//	 */
//	private static void extractPrefixExpression(PrefixExpression prefixExpression) {
//		if (prefixExpression != null) {
//			Expression exp = prefixExpression.getOperand();
//			if (exp != null) 
//				if (exp instanceof SimpleName) {
//					MethodVocabularyManager.insertLocalVariable(extractSimpleName((SimpleName)exp));
//				} else { 
//					extractExpression(exp);
//				}
//		}
//	}
//	
//	/**
//	 * Extract infomartions abou the 'this' expression.
//	 * @param thisExpression This expression.
//	 */
//	private static void extractThisExpression(ThisExpression thisExpression) {
//		if (thisExpression != null) {
//			Name name = thisExpression.getQualifier();
//			if (name != null)
//				extractName(name);
//		}
//	}
//	
//	/**
//	 * Extract the information that compounds the variable declaration.
//	 * @param varDeclExp The variable declaration.
//	 */
//	@SuppressWarnings("unchecked")
//	private static void extractVariableDeclarationExpression(
//			VariableDeclarationExpression varDeclExp) {
//		if (varDeclExp != null) {
//			List<VariableDeclarationFragment> fragments = varDeclExp.fragments();
//			for (VariableDeclarationFragment v : fragments) {
//				if (v != null)
//					extractVariableDeclarationFragment(v);
//			}
//		}
//	}
//	
//	/**
//	 * Extract the name of the annotation.
//	 * @param annotation The annotation.
//	 */
//	public static void extractAnnotation(Annotation annotation) {
//		if (annotation != null) {
//			Name name = annotation.getTypeName();
//			if (name !=  null)
//				extractName(name);
//		}
//	}
//	
//	/**
//	 * Extract the simple name of the identifier.
//	 * @param smpName The identifier's simple name.
//	 * @return 
//	 */
//	public static String extractSimpleName(SimpleName smpName) {
//		if (smpName != null)
//			return smpName.getIdentifier();
//		return "";
//	}
//	
//	
//	private static void extractStringLiteral(StringLiteral stringLiteral) {
//		if (stringLiteral != null) {
//			String normalizedLiteral = StringProcessor.processString(stringLiteral.getEscapedValue());
//			MethodVocabularyManager.insertLiteral(normalizedLiteral);
//		}
//	}
//		
//	/**
//	 * Extract the fragments of a variable declaration.
//	 * @param v The variable declaration.
//	 */
//	public static void extractVariableDeclarationFragment(
//			VariableDeclarationFragment v) {
//		if (v != null) {
//			//retorna a expressao que aparece apos o simbolo "="
//			Expression exp = v.getInitializer();
//			if (exp != null)
//				extractExpression(exp);
//			
//			//retorna o nome da variavel que esta sendo declarada
//			//exemplo: para 'int variavel;', ele retorna 'variavel'
//			//         para 'int variavel = 0;', ele retorn 'variavel' 
//			SimpleName smpName = v.getName();
//			if (smpName != null) {
//				MethodVocabularyManager.insertLocalVariable(extractSimpleName(smpName));
//			}
//		}
//	}
}
