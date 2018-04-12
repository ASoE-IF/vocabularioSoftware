package org.splab.vocabulary.extractor.nodelists;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompoundStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTDeclarationStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTEnumerationSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclaration;

/**
 * Esta classe é responsável por retornar listas de tipos de declarações,
 * para que as classes que necessitem usar listas de declarações internas
 * possam funcionar corretamente.
 * 
 * @author Israel Gomes de Lima
 */

public class DeclarationList{
	
	private DeclarationList(){}
	
	/**Retorna uma lista de declarações internas nas entidades
	 * passadas como argumento
	 * @param astNode
	 * @return lista de nodos ast
	 */
	public static List<ASTNode> getInnerTypes(ASTNode astNode) {
		
		//Lista de tipos de declarações internas
		List<ASTNode> typesDeclarations = new ArrayList<ASTNode>();

		if(astNode instanceof IASTTranslationUnit){
			/*Esse é o tipo principal e que representa todo o arquivo
			 * Aqui é recuperada uma lista das entidades internas
			 * no arquivo
			 */
			IASTTranslationUnit translationUnit = (IASTTranslationUnit)astNode;
			typesDeclarations = getTypes(translationUnit.getDeclarations());
		}
		
		if(astNode instanceof IASTFunctionDefinition){
			//Recupera uma lista de declarações internas nas funções
			CASTFunctionDefinition functionDefinition = (CASTFunctionDefinition) astNode;
			
			typesDeclarations = getTypes(((CASTCompoundStatement)functionDefinition.getBody()).getStatements());
		}
		
		if(astNode instanceof CASTCompositeTypeSpecifier){
			// Recupera uma lista de declarações internas nas structs e unions
			CASTCompositeTypeSpecifier compositeType = (CASTCompositeTypeSpecifier) astNode;
			
			typesDeclarations = getTypes(compositeType.getMembers());
		}

		return typesDeclarations;
	}
	
	/**Retorna uma lista de entidades entre um conjunto de declarações
	 * qualquer
	 * @param declarations
	 * @return lista de nodos ast
	 */
	public static List<ASTNode> getTypes(IASTStatement[] declarations) {
		
		//Lista de entidades 
		List<ASTNode> statementList = new ArrayList<ASTNode>();

		for (IASTStatement declaration : declarations) {
			if (declaration instanceof CASTDeclarationStatement) {

				CASTDeclarationStatement decl = (CASTDeclarationStatement) declaration;
				if (decl.getDeclaration() instanceof IASTFunctionDefinition) {
					//Recupera uma declaração de função
					statementList.add((ASTNode) decl.getDeclaration());
				}

				if (decl.getDeclaration() instanceof IASTSimpleDeclaration) {
					CASTSimpleDeclaration node = (CASTSimpleDeclaration) decl.getDeclaration();
					IASTDeclSpecifier declarationSpecifier = node.getDeclSpecifier();

					if ((declarationSpecifier instanceof CASTEnumerationSpecifier) || (declarationSpecifier instanceof CASTCompositeTypeSpecifier)) {
						//Recupera uma declaração de struct, union ou enum
						statementList.add((ASTNode) decl.getDeclaration());
					}
				}
			}
		}

		return statementList;
	}

	public static List<ASTNode> getTypes(IASTDeclaration[] declarations) {
		
		//Lista de entidades 
		List<ASTNode> statementList = new ArrayList<ASTNode>();

		for (IASTDeclaration declaration : declarations) {
			if (declaration instanceof IASTFunctionDefinition) {
				//Recupera uma declaração de função
				statementList.add((ASTNode) declaration);
			}

			if (declaration instanceof IASTSimpleDeclaration) {
				CASTSimpleDeclaration node = (CASTSimpleDeclaration) declaration;

				IASTDeclSpecifier declarationSpecifier = node.getDeclSpecifier();

				if (declarationSpecifier instanceof CASTEnumerationSpecifier) {
					//Recupera uma declaração de enum
					CASTEnumerationSpecifier enumeration = (CASTEnumerationSpecifier) declarationSpecifier;
					statementList.add((ASTNode) enumeration);
				}

				if (declarationSpecifier instanceof CASTCompositeTypeSpecifier) {
					//Recupera uma declaração de struct ou union
					CASTCompositeTypeSpecifier composite = (CASTCompositeTypeSpecifier) declarationSpecifier;
					statementList.add((ASTNode) composite);
				}
			}
		}

		return statementList;
	}
}
