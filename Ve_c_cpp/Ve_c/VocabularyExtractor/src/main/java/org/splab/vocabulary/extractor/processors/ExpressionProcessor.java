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
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFieldReference;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionCallExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTIdExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTLiteralExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTUnaryExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CVariable;
import org.splab.vocabulary.extractor.processors.vocabulay.manager.FileVocabularyManager;
import org.splab.vocabulary.extractor.processors.vocabulay.manager.FunctionVocabularyManager;
import org.splab.vocabulary.extractor.processors.vocabulay.manager.VocabularyManager;

/**
 * ExpressionProcessor é responsavel por receber um objeto de expressão
 * genérico, diferencialo e desmembralo até obter o pormenor específico de cada
 * uma das expressões.
 * 
 * @author Israel Gomes de Lima
 */
public class ExpressionProcessor {
	/**
	 * Mantem o vocabulary manager do chamador
	 */
	private static VocabularyManager vocabularyManager;

	/**
	 * Este método é esponsável por receber uma expressão e diferencia-la
	 * obtendo assim a sua declaração específica e designando o método
	 * responsável por processa-la.
	 * 
	 * @param expression
	 * @return
	 */
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
		return "";
	}

	/**
	 * Este método possui um corpo vázio pelo motivo de ainda não termos
	 * identificado nenhuma expressão desse tipo em códigos fontes.
	 * 
	 * @param ambiguousExpression
	 */
	private static void extractAmbiguousExpression(CASTAmbiguousExpression ambiguousExpression) {
		System.out.println("Uma expressão ambigua foi encontrada, por favor envie-nos o seguinte fragmento de código: "
				+ ambiguousExpression.getRawSignature());
		System.out.println("\nPara nos ajudar a implementar esse tipo de estrutura.\n");
	}

	/**
	 * Extrai expressões de array e expressões nos subscritos dos arrays
	 * 
	 * @param arraySubscriptExpression
	 */
	private static void extractArraySubscriptExpression(CASTArraySubscriptExpression arraySubscriptExpression) {
		extractExpression(arraySubscriptExpression.getArrayExpression());
		extractExpression(arraySubscriptExpression.getSubscriptExpression());
	}

	/**
	 * Extrai expressões binarias
	 * 
	 * @param binaryExpression
	 */
	private static void extractBinaryExpression(CASTBinaryExpression binaryExpression) {
		extractExpression(binaryExpression.getOperand1());
		extractExpression(binaryExpression.getOperand2());
	}

	/**
	 * Extrai expressões de cast de tipos
	 * 
	 * @param castExpression
	 */
	private static void extractCastExpression(CASTCastExpression castExpression) {
		if (castExpression != null) {
			IASTExpression exp = castExpression.getOperand();

			if (exp != null)
				extractExpression(exp);
		}
	}

	/**
	 * Extrai expressões condicionais de ifs
	 * 
	 * @param conditionalExpression
	 */
	private static void extractConditionalExpression(CASTConditionalExpression conditionalExpression) {
		if (conditionalExpression != null) {
			IASTExpression elseExp = conditionalExpression.getNegativeResultExpression();
			if (elseExp != null)
				extractExpression(elseExp);

			IASTExpression exp = conditionalExpression.getLogicalConditionExpression();
			if (exp != null)
				extractExpression(exp);

			IASTExpression thenExp = conditionalExpression.getPositiveResultExpression();
			if (thenExp != null)
				extractExpression(thenExp);
		}
	}

	/**
	 * Extrai chamadas a funções
	 * 
	 * @param functionCallExpression
	 */
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
					if (vocabularyManager instanceof FunctionVocabularyManager) {
						FunctionVocabularyManager functionVocabulary = (FunctionVocabularyManager) vocabularyManager;
						functionVocabulary.insertFunctionCall(name);
					} else if (vocabularyManager instanceof FileVocabularyManager) {
						FileVocabularyManager fileVocabulary = (FileVocabularyManager) vocabularyManager;
						fileVocabulary.insertFunctionCall(name);
					}
				}
			}
		}
	}

	/**
	 * Extrai todos os termos que são usados: variáveis, etc.
	 * 
	 * @param idExpression
	 */
	private static void extractIdExpression(CASTIdExpression idExpression) {
		if (idExpression == null)
			return;
		String name = idExpression.getName().toString();
		vocabularyManager.insertVariable(name, "", "");
	}

	/**
	 * 
	 * @param literalExpression
	 */
	private static void extractLiteralExpression(CASTLiteralExpression literalExpression) {
		if (literalExpression.getValueCategory().isGLValue() == true) {
			String normalizedLiteral = StringProcessor.processString(literalExpression.getRawSignature());
			if (vocabularyManager != null) {

				if (vocabularyManager instanceof FunctionVocabularyManager) {
					FunctionVocabularyManager vocManager = (FunctionVocabularyManager) vocabularyManager;
					vocManager.insertLiteral(normalizedLiteral);
				} else {
					FileVocabularyManager vocManager = (FileVocabularyManager) vocabularyManager;
					vocManager.insertLiteral(normalizedLiteral);
				}
			}
		}
	}

	/**
	 * Extrai expressões unarias
	 * 
	 * @param unaryExpression
	 */
	private static void extractUnaryExpression(CASTUnaryExpression unaryExpression) {
		if (unaryExpression != null) {
			IASTExpression exp = unaryExpression.getOperand();
			if (exp != null) {
				if (exp instanceof CASTIdExpression) {
					extractIdExpression((CASTIdExpression) exp);
				} else {
					extractExpression(exp);
				}
			}
		}
	}

	/**
	 * Extrai uma lista de expressões
	 * 
	 * @param expressionList
	 */
	private static void extractExpressionList(CASTExpressionList expressionList) {
		IASTExpression[] expressions = expressionList.getExpressions();

		for (IASTExpression expression : expressions) {
			extractExpression(expression);
		}
	}

	/**
	 * Extrai referências a campos feitas por "." ou "->"
	 * 
	 * @param fieldReference
	 */
	private static void extractFieldReference(CASTFieldReference fieldReference) {
		IASTExpression expression = fieldReference.getFieldOwner();

		if (expression != null && !(expression instanceof CASTIdExpression)
				&& !(expression instanceof CASTArraySubscriptExpression))
			extractExpression(expression);

		if (fieldReference != null) {
			if (vocabularyManager instanceof FunctionVocabularyManager) {
				FunctionVocabularyManager vocManager = (FunctionVocabularyManager) vocabularyManager;
				vocManager.insertLocalVariable(fieldReference.getRawSignature(), "",
						storageClass(fieldReference.getFieldName()));
			} else {
				if (vocabularyManager instanceof FileVocabularyManager) {
					FileVocabularyManager vocManager = (FileVocabularyManager) vocabularyManager;
					vocManager.insertGlobalVariable(fieldReference.getRawSignature(), "",
							storageClass(fieldReference.getFieldName()));
				}
			}
		}
	}

	/**
	 * Extrai a classe de armazenamento de alguma variável
	 * 
	 * @param variableName
	 * @return
	 */
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

	/**
	 * Recebe e mantem um vocabulary manager
	 * 
	 * @param manager
	 */
	public static void setVocabularyManager(VocabularyManager manager) {
		vocabularyManager = manager;
	}
}