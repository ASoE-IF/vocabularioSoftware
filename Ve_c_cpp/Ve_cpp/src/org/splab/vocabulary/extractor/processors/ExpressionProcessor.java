package org.splab.vocabulary.extractor.processors;

import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IVariable;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTAmbiguousExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTArraySubscriptExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTBinaryExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCastExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTConditionalExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTExpressionList;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFieldReference;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionCallExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTIdExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLiteralExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTUnaryExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPVariable;

public class ExpressionProcessor {
	private static FunctionVocabularyManager functionVocabulary;

	public static void setFunctionVocabulary(FunctionVocabularyManager manager) {
		functionVocabulary = manager;
	}

	public static String extractExpression(IASTExpression expression) {
		if (expression instanceof CPPASTAmbiguousExpression)
			extractAmbiguousExpression((CPPASTAmbiguousExpression) expression);

		if (expression instanceof CPPASTArraySubscriptExpression)
			extractArraySubscriptExpression((CPPASTArraySubscriptExpression) expression);

		if (expression instanceof CPPASTBinaryExpression)
			extractBinaryExpression((CPPASTBinaryExpression) expression);

		if (expression instanceof CPPASTCastExpression)
			extractCastExpression((CPPASTCastExpression) expression);

		if (expression instanceof CPPASTConditionalExpression)
			extractConditionalExpression((CPPASTConditionalExpression) expression);

		if (expression instanceof CPPASTFunctionCallExpression)
			extractFunctionCallExpression((CPPASTFunctionCallExpression) expression);

		if (expression instanceof CPPASTIdExpression)
			extractIdExpression((CPPASTIdExpression) expression);

		if (expression instanceof CPPASTLiteralExpression)
			extractLiteralExpression((CPPASTLiteralExpression) expression);

		if (expression instanceof CPPASTUnaryExpression)
			extractUnaryExpression((CPPASTUnaryExpression) expression);

		if (expression instanceof CPPASTExpressionList)
			extractExpressionList((CPPASTExpressionList) expression);

		if (expression instanceof CPPASTFieldReference)
			extractFieldReference((CPPASTFieldReference) expression);

		return "";
	}

	private static void extractAmbiguousExpression(CPPASTAmbiguousExpression ambiguousExpression) {

	}

	private static void extractArraySubscriptExpression(CPPASTArraySubscriptExpression arraySubscriptExpression) {
		extractExpression(arraySubscriptExpression.getArrayExpression());
		extractExpression(arraySubscriptExpression.getSubscriptExpression());
	}

	private static void extractBinaryExpression(CPPASTBinaryExpression binaryExpression) {
		extractExpression(binaryExpression.getOperand1());
		extractExpression(binaryExpression.getOperand2());
	}

	private static void extractCastExpression(CPPASTCastExpression CPPASTExpression) {
		if (CPPASTExpression != null) {
			IASTExpression exp = CPPASTExpression.getOperand();

			if (exp != null)
				extractExpression(exp);
		}
	}

	private static void extractConditionalExpression(CPPASTConditionalExpression conditionalExpression) {
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

	private static void extractFunctionCallExpression(CPPASTFunctionCallExpression functionCallExpression) {
		if (functionCallExpression != null) {
			IASTInitializerClause[] arguments = functionCallExpression.getArguments();

			for (IASTInitializerClause exp : arguments) {
				if (exp != null)
					if (exp instanceof CPPASTIdExpression) {
						extractIdExpression((CPPASTIdExpression) exp);
					} else {
						extractExpression((IASTExpression) exp);
					}
			}

			if (functionCallExpression.getFunctionNameExpression() instanceof CPPASTIdExpression) {
				CPPASTIdExpression idExp = (CPPASTIdExpression) functionCallExpression.getFunctionNameExpression();
				if (idExp != null) {
					String name = idExp.getName().toString();
					functionVocabulary.insertFunctionInvocation(name);
				}
			}
		}
	}

	private static void extractIdExpression(CPPASTIdExpression idExpression) {
		
		if(idExpression == null)
			return;
		try {
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
		} catch (NullPointerException e) {

			String name = idExpression.getName().toString();

			String access = FileProcessor.gvarAccess.get(name);
			String storage = FileProcessor.gvarStorage.get(name);

			FileProcessor.insertGlobalVariable(name, access, storage);

		}

	}

	private static void extractLiteralExpression(CPPASTLiteralExpression literalExpression) {
		if (literalExpression.getValueCategory().isGLValue() == true) {
			String normalizedLiteral = StringProcessor.processString(literalExpression.getRawSignature());
			if(functionVocabulary != null)
			functionVocabulary.insertLiteral(normalizedLiteral);
			else
				FileProcessor.insertLiteral(normalizedLiteral);
		}
	}

	private static void extractUnaryExpression(CPPASTUnaryExpression unaryExpression) {
		if (unaryExpression != null) {
			IASTExpression exp = unaryExpression.getOperand();
			if (exp != null)
				if (exp instanceof CPPASTIdExpression) {
					extractIdExpression((CPPASTIdExpression) exp);
				} else {
					extractExpression(exp);
				}
		}
	}

	private static void extractExpressionList(CPPASTExpressionList expressionList) {
		IASTExpression[] expressions = expressionList.getExpressions();

		for (IASTExpression expression : expressions) {
			extractExpression(expression);
		}
	}

	private static void extractFieldReference(CPPASTFieldReference fieldReference) {
		IASTExpression expression = fieldReference.getFieldOwner();

		if (expression != null)
			extractExpression(expression);

		if (fieldReference != null)
			functionVocabulary.insertLocalVariable(fieldReference.getRawSignature(), "",
					storageClass(fieldReference.getFieldName()));
	}

	private static String storageClass(IASTName variableName) {
		IVariable e = new CPPVariable(variableName);
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
}