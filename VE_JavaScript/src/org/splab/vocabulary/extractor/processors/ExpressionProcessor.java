package org.splab.vocabulary.extractor.processors;

import java.util.List;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

/**
 * This class is responsible for extracting expressions's information.
 * All the informations extract should be Expression type.
 * 
 * @author Catharine Quintans
 * @since October 24, 2012.
 */
public class ExpressionProcessor {
	
	/**
	 * This method will discover the instance of the Expression object,
	 * so, the Expression's informations will be extracted correctly.
	 * @param expression The expression that will be analyzed.
	 * @return 
	 */
	public static String extractExpression(Expression expression) {
		if (expression instanceof Annotation) 
			extractAnnotation((Annotation) expression);
		
		if (expression instanceof ArrayAccess) 
			extractArrayAccess((ArrayAccess) expression);

		if (expression instanceof ArrayCreation) 
			extractArrayCreation((ArrayCreation) expression);
	
		if (expression instanceof ArrayInitializer) 
			extractArrayInitializer((ArrayInitializer) expression);

		if (expression instanceof Assignment) 
			extractAssignment((Assignment) expression);
		
		if (expression instanceof CastExpression) 
			extractCastExpression((CastExpression) expression);
		
		if (expression instanceof CharacterLiteral) 
			extractCharacterLiteral((CharacterLiteral)expression);

		if (expression instanceof ClassInstanceCreation) 
			extractClassInstanceCreation((ClassInstanceCreation) expression);
		
		if (expression instanceof ConditionalExpression) 
			extractConditionalExpression((ConditionalExpression) expression);
		
		if (expression instanceof InfixExpression) 
			extractInfixExpression((InfixExpression) expression);
		
		if (expression instanceof InstanceofExpression) 
			extractInstanceofExpression((InstanceofExpression) expression);

		if (expression instanceof MethodInvocation) 
			extractMethodInvocation((MethodInvocation) expression);
		
		if (expression instanceof Name) 
			extractName((Name) expression);
		
		if (expression instanceof ParenthesizedExpression) 
			extractParenthesizedExpression((ParenthesizedExpression) expression);

		if (expression instanceof PostfixExpression) 
			extractPostfixExpression((PostfixExpression) expression);
		 
		if (expression instanceof PrefixExpression) 
			extractPrefixExpression((PrefixExpression)expression);
		
		if (expression instanceof SimpleName)
			return extractSimpleName((SimpleName) expression);
			
		if (expression instanceof StringLiteral) 
			extractStringLiteral((StringLiteral) expression);
		
		if (expression instanceof ThisExpression) 
			extractThisExpression((ThisExpression) expression);
		
		if (expression instanceof VariableDeclarationExpression) 
			extractVariableDeclarationExpression((VariableDeclarationExpression) expression);
		
		return "";
	}

	/**
	 * Extract the arrays' access expressions.
	 * @param arrayAccess The array access.
	 */
	private static void extractArrayAccess(ArrayAccess arrayAccess) {
		if (arrayAccess != null) {
			Expression index = arrayAccess.getIndex();
			if (index != null)
				extractExpression(index);
			
			Expression array = arrayAccess.getArray();
			if (array != null)
				MethodVocabularyManager.insertLocalVariable(extractExpression(array));
		}
	}

	/**
	 * Extract informations about the array creation.
	 * @param arrayCreation The array creation.
	 */
	private static void extractArrayCreation(ArrayCreation arrayCreation) {
		if (arrayCreation != null) {
			ArrayInitializer initializer = arrayCreation.getInitializer();
			if (initializer != null)
				extractArrayInitializer(initializer);
		}
	}

	/**
	 * Extract informations about the array initializer.
	 * @param arrayInitializer The array initializer.
	 */
	@SuppressWarnings("unchecked")
	private static void extractArrayInitializer(ArrayInitializer arrayInitializer) {
		if (arrayInitializer != null) {
			List<ArrayInitializer> array = arrayInitializer.expressions();
			for (int i = 0; i < array.size(); i++) {
				Object o = array.get(i);
				if (o instanceof Expression) {
					extractExpression((Expression) o);
				}
			}
		}
	}
	
	/**
	 * Extract the informations of the assignment.
	 * @param assignment The assignment.
	 */
	private static void extractAssignment(Assignment assignment) {
		if (assignment != null) {
			Expression expLeft = assignment.getLeftHandSide();
			if (expLeft != null)
				if (expLeft instanceof SimpleName) {
					MethodVocabularyManager.insertLocalVariable(extractSimpleName((SimpleName) expLeft));
				} else {
					extractExpression(expLeft);
				}
			
			Expression expRight = assignment.getRightHandSide();
			if (expRight != null) {
				if (expRight instanceof SimpleName) {
					MethodVocabularyManager.insertLocalVariable(extractSimpleName((SimpleName) expRight));
				} else {
					extractExpression(expRight);
				}
			}
		}
	}
	

	/**
	 * Extract the type and the expression of the cast expression.
	 * @param castExpression The cast expression.
	 */
	private static void extractCastExpression(CastExpression castExpression) {
		if (castExpression!=null) {
			Expression exp = castExpression.getExpression();
			if (exp != null)
				MethodVocabularyManager.insertLocalVariable(extractExpression(exp));
		}
	}

	/**
	 * Extract the value of the character literal.
	 * @param characterLiteral The character.
	 */
	private static void extractCharacterLiteral(CharacterLiteral characterLiteral) {
		if (characterLiteral != null) {
			String normalizedLiteral = StringProcessor.processString(characterLiteral.toString());
			MethodVocabularyManager.insertLiteral(normalizedLiteral);
		}
	}

	/**
	 * Extract the idnetifier's from the instance creation.
	 * @param classInstanceCreation The instance creation.
	 */
	@SuppressWarnings("unchecked")
	private static void extractClassInstanceCreation(
			ClassInstanceCreation classInstanceCreation) {
		if (classInstanceCreation != null) {
			List<Expression> arguments = classInstanceCreation.arguments();
			for (Expression exp : arguments) {
				if (exp != null) 
					if (exp instanceof SimpleName) {
						MethodVocabularyManager.insertLocalVariable(extractSimpleName( (SimpleName)exp));
					} 
					if (exp instanceof StringLiteral) {
						extractStringLiteral((StringLiteral) exp);
					}
					
			}
		}
	}
	
	/**
	 * Extract all the informations existing at the conditional expression.
	 * @param conditionalExpression The conditional expression.
	 */
	private static void extractConditionalExpression(
			ConditionalExpression conditionalExpression) {
		if (conditionalExpression != null) {
			Expression elseExp = conditionalExpression.getElseExpression();
			if (elseExp != null)
				MethodVocabularyManager.insertLocalVariable(extractExpression(elseExp));
					
			Expression exp = conditionalExpression.getExpression();
			if (exp != null) 
				extractExpression(exp);
			
			Expression thenExp = conditionalExpression.getThenExpression();
			if (thenExp != null)
				MethodVocabularyManager.insertLocalVariable(extractExpression(thenExp));
		}
	}
	
	/**
	 * Extract the information of the infix expression.
	 * @param infixExpression The infix expression.
	 */
	@SuppressWarnings("unchecked")
	private static void extractInfixExpression(InfixExpression infixExpression) {
		if (infixExpression != null) {
			List<Expression> extendedOperators = infixExpression.extendedOperands();
			for (Expression exp : extendedOperators) {
				if (exp != null)
					extractExpression(exp);
			}
			
			Expression leftExpression = infixExpression.getLeftOperand();
			if (leftExpression != null ) {
				if (leftExpression instanceof SimpleName) {
					MethodVocabularyManager.insertLocalVariable(extractSimpleName((SimpleName)leftExpression));
				} else {
					extractExpression(leftExpression);
				}
			}
			
			Expression rightExpression = infixExpression.getRightOperand();
			if (rightExpression != null) {
				if (rightExpression instanceof SimpleName) {
					MethodVocabularyManager.insertLocalVariable(extractSimpleName((SimpleName)rightExpression));
				} else {
					extractExpression(rightExpression);
				}
			}
		}
	}
	
	/**
	 * Extract the expressin of the instanceof statement.
	 * @param instanceofExpression The instanceof expression.
	 */
	private static void extractInstanceofExpression(InstanceofExpression instanceofExpression) {
		if (instanceofExpression != null) {
			Expression exp = instanceofExpression.getLeftOperand();
			if (exp != null)
				MethodVocabularyManager.insertLocalVariable(extractExpression(exp));
		}
	}
	
	/**
	 * Extract the identifier expressed at the method invocation.
	 * @param mthInvocation The method invocation.
	 */
	@SuppressWarnings("unchecked")
	private static void extractMethodInvocation(MethodInvocation mthInvocation) {
		if (mthInvocation != null) {
			List<Expression> arguments = mthInvocation.arguments();
			for (Expression exp : arguments) {
				if (exp != null) 
					if (exp instanceof SimpleName) {
						MethodVocabularyManager.insertLocalVariable(extractSimpleName((SimpleName) exp));
					}else {
						extractExpression(exp);
					}
			}
			
			Expression exp = mthInvocation.getExpression();
			if (exp != null) {
				String name = extractExpression(exp);
				MethodVocabularyManager.insertLocalVariable(name);
			}
		
			
			SimpleName smpName = mthInvocation.getName();
			if (smpName != null) {
				String name = extractSimpleName(smpName);
				MethodVocabularyManager.insertMethodInvocation(name);
			}
		}
	}
	
	/**
	 * Extract the identifier's name.
	 * @param name The identifier's name.
	 * @return 
	 */
	private static String extractName(Name name) {
		if (name != null)
			return name.getFullyQualifiedName();
		return "";
	}

	/**
	 * Extract the parenthesized informations.
	 * @param parenthesizedExpression The parenthesized expression.
	 */
	private static void extractParenthesizedExpression(
			ParenthesizedExpression parenthesizedExpression) {
		if (parenthesizedExpression != null) {
			Expression exp = parenthesizedExpression.getExpression();
			if (exp != null) 
				extractExpression(exp);
		}	
	}
	
	/**
	 * Extract the information about the postfix expression.
	 * @param postfixExpression The postfix expression.
	 */
	private static void extractPostfixExpression(PostfixExpression postfixExpression) {
		if (postfixExpression != null) {
			Expression exp = postfixExpression.getOperand();
			if (exp != null)
				if (exp instanceof SimpleName) {
					MethodVocabularyManager.insertLocalVariable(extractSimpleName((SimpleName)exp));
				} else { 
					extractExpression(exp);
				}
		}
	}
	
	/**
	 * Extract the information about the prefix expression.
	 * @param prefixExpression The prefix expression.
	 */
	private static void extractPrefixExpression(PrefixExpression prefixExpression) {
		if (prefixExpression != null) {
			Expression exp = prefixExpression.getOperand();
			if (exp != null) 
				if (exp instanceof SimpleName) {
					MethodVocabularyManager.insertLocalVariable(extractSimpleName((SimpleName)exp));
				} else { 
					extractExpression(exp);
				}
		}
	}
	
	/**
	 * Extract infomartions abou the 'this' expression.
	 * @param thisExpression This expression.
	 */
	private static void extractThisExpression(ThisExpression thisExpression) {
		if (thisExpression != null) {
			Name name = thisExpression.getQualifier();
			if (name != null)
				extractName(name);
		}
	}
	
	/**
	 * Extract the information that compounds the variable declaration.
	 * @param varDeclExp The variable declaration.
	 */
	@SuppressWarnings("unchecked")
	private static void extractVariableDeclarationExpression(
			VariableDeclarationExpression varDeclExp) {
		if (varDeclExp != null) {
			List<VariableDeclarationFragment> fragments = varDeclExp.fragments();
			for (VariableDeclarationFragment v : fragments) {
				if (v != null)
					extractVariableDeclarationFragment(v);
			}
		}
	}
	
	/**
	 * Extract the name of the annotation.
	 * @param annotation The annotation.
	 */
	public static void extractAnnotation(Annotation annotation) {
		if (annotation != null) {
			Name name = annotation.getTypeName();
			if (name !=  null)
				extractName(name);
		}
	}
	
	/**
	 * Extract the simple name of the identifier.
	 * @param smpName The identifier's simple name.
	 * @return 
	 */
	public static String extractSimpleName(SimpleName smpName) {
		if (smpName != null)
			return smpName.getIdentifier();
		return "";
	}
	
	
	private static void extractStringLiteral(StringLiteral stringLiteral) {
		if (stringLiteral != null) {
			String normalizedLiteral = StringProcessor.processString(stringLiteral.getEscapedValue());
			MethodVocabularyManager.insertLiteral(normalizedLiteral);
		}
	}
		
	/**
	 * Extract the fragments of a variable declaration.
	 * @param v The variable declaration.
	 */
	public static void extractVariableDeclarationFragment(
			VariableDeclarationFragment v) {
		if (v != null) {
			//retorna a expressao que aparece apos o simbolo "="
			Expression exp = v.getInitializer();
			if (exp != null)
				extractExpression(exp);
			
			//retorna o nome da variavel que esta sendo declarada
			//exemplo: para 'int variavel;', ele retorna 'variavel'
			//         para 'int variavel = 0;', ele retorn 'variavel' 
			SimpleName smpName = v.getName();
			if (smpName != null) {
				MethodVocabularyManager.insertLocalVariable(extractSimpleName(smpName));
			}
		}
	}
}