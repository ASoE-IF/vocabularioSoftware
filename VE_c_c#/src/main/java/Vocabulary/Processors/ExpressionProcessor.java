package Vocabulary.Processors;

import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTAmbiguousExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTArraySubscriptExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTBinaryExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCastExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompoundStatementExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTConditionalExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionCallExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTIdExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTInitializerExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTLiteralExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTProblemExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTTypeIdExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTTypeIdInitializerExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTUnaryExpression;

public class ExpressionProcessor
{
	public static String extractExpression(IASTExpression expression)
	{
		if(expression instanceof CASTAmbiguousExpression)
			extractAmbiguousExpression((CASTAmbiguousExpression) expression);
			
		if(expression instanceof CASTArraySubscriptExpression)
			extractArraySubscriptExpression((CASTArraySubscriptExpression) expression);
			
		if(expression instanceof CASTBinaryExpression)
			extractBinaryExpression((CASTBinaryExpression) expression);
				
		if(expression instanceof CASTCastExpression)
			extractCastExpression((CASTCastExpression) expression);
				
		if(expression instanceof CASTCompoundStatementExpression)
			extractCompoundStatementExpression((CASTCompoundStatementExpression) expression);
				
		if(expression instanceof CASTConditionalExpression)
			extractConditionalExpression((CASTConditionalExpression) expression);
			
		if(expression instanceof CASTFunctionCallExpression)
			extractFunctionCallExpression((CASTFunctionCallExpression) expression);
			
		if(expression instanceof CASTIdExpression)
			extractIdExpression((CASTIdExpression) expression);
				
		if(expression instanceof CASTInitializerExpression)
			extractInitializerExpression((CASTInitializerExpression) expression);
				
		if(expression instanceof CASTLiteralExpression)
			extractLiteralExpression((CASTLiteralExpression) expression);
				
		if(expression instanceof CASTProblemExpression)
			extractProblemExpression((CASTProblemExpression) expression);
			
		if(expression instanceof CASTTypeIdExpression)
			extractTypeIdExpression((CASTTypeIdExpression) expression);
			
		if(expression instanceof CASTTypeIdInitializerExpression)
			extractTypeIdInitializerExpression((CASTTypeIdInitializerExpression) expression);
				
		if(expression instanceof CASTUnaryExpression)
			extractUnaryExpression((CASTUnaryExpression) expression);
			
		return "";
	}
		
	private static void extractAmbiguousExpression(CASTAmbiguousExpression ambiguousExpression)
	{
		
	}
		
	private static void extractArraySubscriptExpression(CASTArraySubscriptExpression arraySubscriptExpression)
	{
			
	}
		
	private static void extractBinaryExpression(CASTBinaryExpression binaryExpression)
	{
		extractExpression(binaryExpression.getOperand1());
		extractExpression(binaryExpression.getOperand2());
	}
		
	private static void extractCastExpression(CASTCastExpression castExpression)
	{
			
	}
		
	private static void extractCompoundStatementExpression(CASTCompoundStatementExpression compoundStatementExpression)
	{
			
	}
		
	private static void extractConditionalExpression(CASTConditionalExpression conditionalExpression)
	{
		if (conditionalExpression != null)
		{
			IASTExpression elseExp = conditionalExpression.getNegativeResultExpression();
				if (elseExp != null);
					extractExpression(elseExp);
						
				IASTExpression exp = conditionalExpression.getLogicalConditionExpression();
				if (exp != null) ;
					extractExpression(exp);
				
				IASTExpression thenExp = conditionalExpression.getPositiveResultExpression();
				if (thenExp != null);
					extractExpression(thenExp);
			}
	}
		
	private static void extractFunctionCallExpression(CASTFunctionCallExpression functionCallExpression)
	{
		if (functionCallExpression != null)
		{
			IASTInitializerClause[] arguments = functionCallExpression.getArguments();

			for (IASTInitializerClause exp : arguments)
			{
				if (exp != null) 
					if (exp instanceof CASTIdExpression)
					{
						extractIdExpression((CASTIdExpression) exp);
					}
					else
					{
						extractExpression((IASTExpression) exp);
					}
			}
			
			CASTIdExpression idExp = (CASTIdExpression) functionCallExpression.getFunctionNameExpression();
			if (idExp != null) {
				String name = idExp.getName().toString();
				FunctionVocabularyManager.insertFunctionInvocation(name);
			}
		}
	}
		
	private static void extractIdExpression(CASTIdExpression idExpression)
	{
		FunctionVocabularyManager.insertLocalVariable(idExpression.getName().toString());
	}
		
	private static void extractInitializerExpression( CASTInitializerExpression initializerExpression)
	{
			
	}
		
	private static void extractLiteralExpression(CASTLiteralExpression literalExpression)
	{
		if (literalExpression.getValueCategory().isGLValue() == true)
		{
			String normalizedLiteral = StringProcessor.processString(literalExpression.getRawSignature());
			FunctionVocabularyManager.insertLiteral(normalizedLiteral);
		}
	}
		
	private static void extractProblemExpression(CASTProblemExpression problemExpression)
	{
			
	}
		
	private static void extractTypeIdExpression(CASTTypeIdExpression typeIdExpression)
	{
			
	}
		
	private static void extractTypeIdInitializerExpression(CASTTypeIdInitializerExpression typeIdInitializerExpression)
	{
			
	}
		
	private static void extractUnaryExpression(CASTUnaryExpression unaryExpression)
	{
		if (unaryExpression != null)
		{
			IASTExpression exp = unaryExpression.getOperand();
			if (exp != null)
				if (exp instanceof CASTIdExpression)
				{
					extractIdExpression((CASTIdExpression) exp);
				}
				else
				{ 
					extractExpression(exp);
				}
		}
	}
}