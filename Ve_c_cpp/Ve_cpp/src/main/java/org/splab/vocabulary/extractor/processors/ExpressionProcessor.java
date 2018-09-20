package org.splab.vocabulary.extractor.processors;

import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause;
import org.eclipse.cdt.core.dom.ast.IASTInitializerList;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IVariable;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTDesignator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTAmbiguousExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTArrayDesignator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTArrayRangeDesignator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTArraySubscriptExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTBinaryExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCastExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTConditionalExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTConstructorInitializer;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDeleteExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDesignatedInitializer;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTExpressionList;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFieldDesignator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFieldReference;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionCallExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTIdExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLiteralExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNewExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTUnaryExpression;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPVariable;
import org.splab.vocabulary.extractor.processors.vocabulay.manager.ClassVocabularyManager;
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

		if (expression instanceof CPPASTNewExpression)
			extractNewExpression((CPPASTNewExpression) expression);

		if (expression instanceof CPPASTDeleteExpression)
			extractDeleteExpression((CPPASTDeleteExpression) expression);

		return "";
	}

	/**
	 * Este método possui um corpo vázio pelo motivo de ainda não termos
	 * identificado nenhuma expressão desse tipo em códigos fontes.
	 * 
	 * @param ambiguousExpression
	 */
	private static void extractAmbiguousExpression(CPPASTAmbiguousExpression ambiguousExpression) {
		System.out.println("Uma expressão ambigua foi encontrada, por favor envie-nos o seguinte fragmento de código: "
				+ ambiguousExpression.getRawSignature());
		System.out.println("\nPara nos ajudar a implementar esse tipo de estrutura.\n");
	}

	/**
	 * Extrai expressões de array e expressões nos subscritos dos arrays
	 * 
	 * @param arraySubscriptExpression
	 */
	@SuppressWarnings("deprecation")
	private static void extractArraySubscriptExpression(CPPASTArraySubscriptExpression arraySubscriptExpression) {
		extractExpression(arraySubscriptExpression.getArrayExpression());
		extractExpression(arraySubscriptExpression.getSubscriptExpression());
	}

	/**
	 * Extrai expressões binarias
	 * 
	 * @param binaryExpression
	 */
	private static void extractBinaryExpression(CPPASTBinaryExpression binaryExpression) {
		extractExpression(binaryExpression.getOperand1());
		extractExpression(binaryExpression.getOperand2());
	}

	/**
	 * Extrai expressões de cast de tipos
	 * 
	 * @param CPPASTExpression
	 */
	private static void extractCastExpression(CPPASTCastExpression CPPASTExpression) {
		if (CPPASTExpression != null) {
			IASTExpression exp = CPPASTExpression.getOperand();

			if (exp != null)
				extractExpression(exp);
		}
	}

	/**
	 * Extrai expressões condicionais de ifs
	 * 
	 * @param conditionalExpression
	 */
	private static void extractConditionalExpression(CPPASTConditionalExpression conditionalExpression) {
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
	private static void extractFunctionCallExpression(CPPASTFunctionCallExpression functionCallExpression) {
		if (functionCallExpression != null) {
			IASTInitializerClause[] arguments = functionCallExpression.getArguments();

			for (IASTInitializerClause exp : arguments) {
				if (exp != null) {
					if (exp instanceof CPPASTIdExpression) {
						extractIdExpression((CPPASTIdExpression) exp);
					} else if (exp instanceof IASTInitializerList) {
						extractInitializerList((IASTInitializerList) exp);

					} else {
						extractExpression((IASTExpression) exp);
					}
				}
			}

			if (functionCallExpression.getFunctionNameExpression() instanceof CPPASTIdExpression) {
				CPPASTIdExpression idExp = (CPPASTIdExpression) functionCallExpression.getFunctionNameExpression();
				if (idExp != null) {
					String name = idExp.getName().toString();
					if (vocabularyManager instanceof FunctionVocabularyManager) {
						FunctionVocabularyManager functionVocabulary = (FunctionVocabularyManager) vocabularyManager;
						functionVocabulary.insertFunctionInvocation(name);
					} else if (vocabularyManager instanceof ClassVocabularyManager) {
						ClassVocabularyManager classVocabulary = (ClassVocabularyManager) vocabularyManager;
						classVocabulary.insertFunctionInvocation(name);
					} else if (vocabularyManager instanceof FileVocabularyManager) {
						FileVocabularyManager fileVocabulary = (FileVocabularyManager) vocabularyManager;
						fileVocabulary.insertFunctionInvocation(name);
					}
				}
			} else {
				if (functionCallExpression.getFunctionNameExpression() instanceof CPPASTFieldReference) {
					CPPASTFieldReference fieldReference = (CPPASTFieldReference) functionCallExpression
							.getFunctionNameExpression();
					extractExpression(fieldReference.getFieldOwner());

					if (fieldReference != null) {
						String name = fieldReference.getRawSignature();
						if (vocabularyManager instanceof FunctionVocabularyManager) {
							FunctionVocabularyManager functionVocabulary = (FunctionVocabularyManager) vocabularyManager;
							functionVocabulary.insertFunctionInvocation(name);
						} else if (vocabularyManager instanceof ClassVocabularyManager) {
							ClassVocabularyManager classVocabulary = (ClassVocabularyManager) vocabularyManager;
							classVocabulary.insertFunctionInvocation(name);
						} else if (vocabularyManager instanceof FileVocabularyManager) {
							FileVocabularyManager fileVocabulary = (FileVocabularyManager) vocabularyManager;
							fileVocabulary.insertFunctionInvocation(name);
						}
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
	private static void extractIdExpression(CPPASTIdExpression idExpression) {

		if (idExpression == null)
			return;
		String name = idExpression.getName().toString();
		vocabularyManager.insertVariable(name, "", "");
	}

	/**
	 * 
	 * @param literalExpression
	 */
	private static void extractLiteralExpression(CPPASTLiteralExpression literalExpression) {
		if (literalExpression.getValueCategory().isGLValue() == true) {
			String normalizedLiteral = StringProcessor.processString(literalExpression.getRawSignature());

			if (vocabularyManager != null) {

				if (vocabularyManager instanceof FunctionVocabularyManager) {
					FunctionVocabularyManager vocManager = (FunctionVocabularyManager) vocabularyManager;
					vocManager.insertLiteral(normalizedLiteral);
				} else {
					if (vocabularyManager instanceof ClassVocabularyManager) {
						ClassVocabularyManager vocManager = (ClassVocabularyManager) vocabularyManager;
						vocManager.insertLiteral(normalizedLiteral);
					} else {
						FileVocabularyManager vocManager = (FileVocabularyManager) vocabularyManager;
						vocManager.insertLiteral(normalizedLiteral);
					}
				}
			}
		}
	}

	/**
	 * Extrai expressões unarias
	 * 
	 * @param unaryExpression
	 */
	private static void extractUnaryExpression(CPPASTUnaryExpression unaryExpression) {
		if (unaryExpression != null) {
			IASTExpression exp = unaryExpression.getOperand();
			if (exp != null) {
				if (exp instanceof CPPASTIdExpression) {
					extractIdExpression((CPPASTIdExpression) exp);
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
	private static void extractExpressionList(CPPASTExpressionList expressionList) {
		IASTExpression[] expressions = expressionList.getExpressions();

		for (IASTExpression expression : expressions) {
			extractExpression(expression);
		}
	}

	/**
	 * Extrai listas de inicializações, no caso de arrays por exemplo
	 * 
	 * @param initializerList
	 */
	private static void extractInitializerList(IASTInitializerList initializerList) {
		IASTInitializerClause[] initializerClauses = initializerList.getClauses();

		for (IASTInitializerClause clause : initializerClauses) {
			if (clause instanceof IASTExpression)
				ExpressionProcessor.extractExpression((IASTExpression) clause);
			else {
				if (clause instanceof CPPASTDesignatedInitializer) {
					extractDesignatedInitializer((CPPASTDesignatedInitializer) clause);

				} else {
					extractInitializerList((IASTInitializerList) clause);
				}
			}
		}
	}

	/**
	 * Este também é mais um tipo de inicialização
	 * 
	 * @param designated
	 */
	private static void extractDesignatedInitializer(CPPASTDesignatedInitializer designated) {

		if (designated == null)
			return;

		for (ICPPASTDesignator designator : designated.getDesignators()) {
			extractDesignators(designator);
		}

		if (designated.getOperand() instanceof IASTExpression)
			extractExpression((IASTExpression) designated.getOperand());

		if (designated.getOperand() instanceof IASTInitializerList)
			extractInitializerList((IASTInitializerList) designated.getOperand());
	}

	/**
	 * Extrai as designators
	 * 
	 * @param designator
	 */
	private static void extractDesignators(ICPPASTDesignator designator) {

		if (designator instanceof CPPASTArrayDesignator) {
			CPPASTArrayDesignator arrayDesignator = (CPPASTArrayDesignator) designator;
			extractExpression(arrayDesignator.getSubscriptExpression());
		}

		if (designator instanceof CPPASTFieldDesignator)
			extractFieldDesignator((CPPASTFieldDesignator) designator);

		if (designator instanceof CPPASTArrayRangeDesignator) {
			CPPASTArrayRangeDesignator arrayRangeDesignator = (CPPASTArrayRangeDesignator) designator;
			extractArrayRangeDesignator(arrayRangeDesignator);
		}

		if (designator instanceof CPPASTDesignatedInitializer)
			extractDesignatedInitializer((CPPASTDesignatedInitializer) designator);
	}

	/**
	 * Extrai array designators
	 * 
	 * @param arrayRangeDesignator
	 */
	private static void extractArrayRangeDesignator(CPPASTArrayRangeDesignator arrayRangeDesignator) {

		if (arrayRangeDesignator.getRangeCeiling() instanceof IASTExpression)
			extractExpression(arrayRangeDesignator.getRangeCeiling());

		else if (arrayRangeDesignator.getRangeCeiling() instanceof CPPASTArrayDesignator)
			extractArrayRangeDesignator((CPPASTArrayRangeDesignator) arrayRangeDesignator.getRangeCeiling());

		if (arrayRangeDesignator.getRangeFloor() instanceof IASTExpression)
			extractExpression(arrayRangeDesignator.getRangeFloor());

		else if (arrayRangeDesignator.getRangeFloor() instanceof CPPASTArrayDesignator)
			extractArrayRangeDesignator((CPPASTArrayRangeDesignator) arrayRangeDesignator.getRangeFloor());
	}

	/**
	 * Extrai field designators
	 * 
	 * @param fieldDesignator
	 */
	private static void extractFieldDesignator(CPPASTFieldDesignator fieldDesignator) {

		String storage = storageClass(fieldDesignator.getName());
		String access = "";
		String name = fieldDesignator.getName().toString();

		if (vocabularyManager != null)
			vocabularyManager.insertVariable(name, access, storage);
	}

	/**
	 * Extrai referências a campos feitas por "." ou "->"
	 * 
	 * @param fieldReference
	 */
	private static void extractFieldReference(CPPASTFieldReference fieldReference) {
		IASTExpression expression = fieldReference.getFieldOwner();
		if (expression != null)
			extractExpression(expression);

		if (fieldReference != null) {
			if (vocabularyManager instanceof FunctionVocabularyManager) {
				FunctionVocabularyManager vocManager = (FunctionVocabularyManager) vocabularyManager;
				vocManager.insertLocalVariable(fieldReference.getRawSignature(), "",
						storageClass(fieldReference.getFieldName()));
			} else if (vocabularyManager instanceof FileVocabularyManager) {
				FileVocabularyManager vocManager = (FileVocabularyManager) vocabularyManager;
				vocManager.insertGlobalVariable(fieldReference.getRawSignature(), "",
						storageClass(fieldReference.getFieldName()));
			} else if (vocabularyManager instanceof ClassVocabularyManager) {
				ClassVocabularyManager vocManager = (ClassVocabularyManager) vocabularyManager;
				vocManager.insertGlobalVariable(fieldReference.getRawSignature(), "",
						storageClass(fieldReference.getFieldName()));
			}

		}
	}

	/**
	 * Extrai expressões de new objeto: new Objeto(...);
	 * 
	 * @param newExpression
	 */
	private static void extractNewExpression(CPPASTNewExpression newExpression) {
		if (newExpression.getInitializer() instanceof CPPASTConstructorInitializer) {
			CPPASTConstructorInitializer constructorInitializer = (CPPASTConstructorInitializer) newExpression
					.getInitializer();

			IASTInitializerClause[] arguments = constructorInitializer.getArguments();

			for (IASTInitializerClause exp : arguments) {
				if (exp != null) {
					if (exp instanceof CPPASTIdExpression) {
						extractIdExpression((CPPASTIdExpression) exp);
					} else {
						extractExpression((IASTExpression) exp);
					}
				}
			}
		}
	}

	/**
	 * Extrai expressões de deletar objetos;
	 * 
	 * @param deleteExpression
	 */
	private static void extractDeleteExpression(CPPASTDeleteExpression deleteExpression) {
		extractExpression(deleteExpression.getOperand());
	}

	/**
	 * Extrai a classe de armazenamento de alguma variável
	 * 
	 * @param variableName
	 * @return
	 */
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

	/**
	 * Recebe e mantem um vocabulary manager
	 * 
	 * @param manager
	 */
	public static void setVocabularyManager(VocabularyManager manager) {
		vocabularyManager = manager;
	}
}