package org.splab.vocabulary.extractor.processors;

import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IVariable;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTAmbiguousExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTArraySubscriptExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTBinaryExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCastExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTConditionalExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTExpressionList;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFieldDesignator;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFieldReference;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionCallExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTIdExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTLiteralExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTUnaryExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CVariable;

public class ExpressionProcessor {
	private static FunctionVocabularyManager functionVocabulary;

	public static void setFunctionVocabulary(FunctionVocabularyManager manager) {
		functionVocabulary = manager;
	}

	public static String extractExpression(IASTExpression expression) {
		if (expression instanceof CASTAmbiguousExpression)
			extractAmbiguousExpression((CASTAmbiguousExpression) expression);

		if (expression instanceof CASTArraySubscriptExpression)
			extractArraySubscriptExpression((CASTArraySubscriptExpression) expression);

		if (expression instanceof CASTBinaryExpression)
			extractBinaryExpression((CASTBinaryExpression) expression);

		if (expression instanceof CASTCastExpression)
			extractCastExpression((CASTCastExpression) expression);

		if (expression instanceof CASTConditionalExpression)
			extractConditionalExpression((CASTConditionalExpression) expression);

		if (expression instanceof CASTFunctionCallExpression)
			extractFunctionCallExpression((CASTFunctionCallExpression) expression);

		if (expression instanceof CASTIdExpression)
			extractIdExpression((CASTIdExpression) expression);

		if (expression instanceof CASTLiteralExpression)
			extractLiteralExpression((CASTLiteralExpression) expression);

		if (expression instanceof CASTUnaryExpression)
			extractUnaryExpression((CASTUnaryExpression) expression);

		if (expression instanceof CASTExpressionList)
			extractExpressionList((CASTExpressionList) expression);

		if (expression instanceof CASTFieldReference)
			extractFieldReference((CASTFieldReference) expression);

		if (expression instanceof CASTFieldDesignator)
			extractFieldDesignator((CASTFieldDesignator) expression);

		return "";
	}

	private static void extractAmbiguousExpression(CASTAmbiguousExpression ambiguousExpression) {

	}

	private static void extractArraySubscriptExpression(CASTArraySubscriptExpression arraySubscriptExpression) {
		extractExpression(arraySubscriptExpression.getArrayExpression());
		extractExpression(arraySubscriptExpression.getSubscriptExpression());
	}

	private static void extractBinaryExpression(CASTBinaryExpression binaryExpression) {
		extractExpression(binaryExpression.getOperand1());
		extractExpression(binaryExpression.getOperand2());
	}

	private static void extractCastExpression(CASTCastExpression castExpression) {
		if (castExpression != null) {
			IASTExpression exp = castExpression.getOperand();

			if (exp != null)
				extractExpression(exp);
		}
	}

	private static void extractConditionalExpression(CASTConditionalExpression conditionalExpression) {
		if (conditionalExpression != null) {
			IASTExpression elseExp = conditionalExpression.getNegativeResultExpression();
			if (elseExp != null)
				;
			extractExpression(elseExp);

			IASTExpression exp = conditionalExpression.getLogicalConditionExpression();
			if (exp != null)
				;
			extractExpression(exp);

			IASTExpression thenExp = conditionalExpression.getPositiveResultExpression();
			if (thenExp != null)
				;
			extractExpression(thenExp);
		}
	}

	private static void extractFunctionCallExpression(CASTFunctionCallExpression functionCallExpression) {
		if (functionCallExpression != null) {
			IASTInitializerClause[] arguments = functionCallExpression.getArguments();

			for (IASTInitializerClause exp : arguments) {
				if (exp != null)
					if (exp instanceof CASTIdExpression) {
						extractIdExpression((CASTIdExpression) exp);
					} else {
						extractExpression((IASTExpression) exp);
					}
			}

			if (functionCallExpression.getFunctionNameExpression() instanceof CASTIdExpression) {
				CASTIdExpression idExp = (CASTIdExpression) functionCallExpression.getFunctionNameExpression();
				if (idExp != null) {
					String name = idExp.getName().toString();
					functionVocabulary.insertFunctionInvocation(name);
				}
			}
		}
	}

	private static void extractIdExpression(CASTIdExpression idExpression) {

		if (idExpression != null) {
			if (functionVocabulary.getLocalVar().containsKey(idExpression.getName().toString())) {
				functionVocabulary.insertLocalVariable(idExpression.getName().toString(), "", "");
			} else {
				boolean contains = false;

				for (int contador = FunctionProcessor.listFuncions.size() - 1; contador >= 0; contador--) {
					FunctionProcessor func = FunctionProcessor.listFuncions.get(contador);

					String name = idExpression.getName().toString();
					if (func.getFunctionVocabulary().getLocalVar().containsKey(name)) {
						func.getFunctionVocabulary().insertLocalVariable(name, "", "");

						String access = func.getFunctionVocabulary().getLocalVarAccess().get(name);
						String storage = func.getFunctionVocabulary().getLocalVarStorage().get(name);

						functionVocabulary.insertFunctionVariable(name, access, storage);

						contains = true;
						break;
					}
				}

				if (contains == false) {
					String name = idExpression.getName().toString();

					if (FileProcessor.gvar.containsKey(name)) {

						String access = FileProcessor.gvarAccess.get(name);
						String storage = FileProcessor.gvarStorage.get(name);

						functionVocabulary.insertGlobalVariable(name, access, storage);
					} else {
						String storage = storageClass(idExpression.getName());
						functionVocabulary.insertLocalVariable(idExpression.getName().toString(), "", storage);
					}
				}
			}
		}
	}

	private static void extractLiteralExpression(CASTLiteralExpression literalExpression) {
		if (literalExpression.getValueCategory().isGLValue() == true) {
			String normalizedLiteral = StringProcessor.processString(literalExpression.getRawSignature());
			functionVocabulary.insertLiteral(normalizedLiteral);
		}
	}

	private static void extractUnaryExpression(CASTUnaryExpression unaryExpression) {
		if (unaryExpression != null) {
			IASTExpression exp = unaryExpression.getOperand();
			if (exp != null)
				if (exp instanceof CASTIdExpression) {
					extractIdExpression((CASTIdExpression) exp);
				} else {
					extractExpression(exp);
				}
		}
	}

	private static void extractExpressionList(CASTExpressionList expressionList) {
		IASTExpression[] expressions = expressionList.getExpressions();

		for (IASTExpression expression : expressions) {
			extractExpression(expression);
		}
	}

	private static void extractFieldReference(CASTFieldReference fieldReference) {
		IASTExpression expression = fieldReference.getFieldOwner();

		if (expression != null)
			extractExpression(expression);

		if (fieldReference != null)
			functionVocabulary.insertLocalVariable(fieldReference.getRawSignature(), "",
					storageClass(fieldReference.getFieldName()));
	}

	private static String storageClass(IASTName variableName) {
		IVariable e = new CVariable(variableName);
		String storage;

		if (e.isAuto())
			storage = "auto";
		else if (e.isStatic())
			storage = "static";
		else if (e.isRegister())
			storage = "register";
		else if (e.isExtern())
			storage = "extern";
		else
			storage = "auto";

		return storage;
	}

	private static void extractFieldDesignator(CASTFieldDesignator fieldDesignator) {

		if (fieldDesignator != null) {

			if (functionVocabulary.getLocalVar().containsKey(fieldDesignator.getName().toString())) {
				functionVocabulary.insertLocalVariable(fieldDesignator.getName().toString(), "", "");
			} else {
				boolean contains = false;

				for (int contador = FunctionProcessor.listFuncions.size() - 1; contador >= 0; contador--) {
					FunctionProcessor func = FunctionProcessor.listFuncions.get(contador);

					String name = fieldDesignator.getName().toString();
					if (func.getFunctionVocabulary().getLocalVar().containsKey(name)) {
						func.getFunctionVocabulary().insertLocalVariable(name, "", "");

						String access = func.getFunctionVocabulary().getLocalVarAccess().get(name);
						String storage = func.getFunctionVocabulary().getLocalVarStorage().get(name);

						functionVocabulary.insertFunctionVariable(name, access, storage);

						contains = true;
						break;
					}
				}

				if (contains == false) {
					String name = fieldDesignator.getName().toString();

					if (FileProcessor.gvar.containsKey(name)) {

						String access = FileProcessor.gvarAccess.get(name);
						String storage = FileProcessor.gvarStorage.get(name);

						functionVocabulary.insertGlobalVariable(name, access, storage);
					} else {
						String storage = storageClass(fieldDesignator.getName());
						functionVocabulary.insertLocalVariable(name, "", storage);
					}
				}
			}
		}
	}
}