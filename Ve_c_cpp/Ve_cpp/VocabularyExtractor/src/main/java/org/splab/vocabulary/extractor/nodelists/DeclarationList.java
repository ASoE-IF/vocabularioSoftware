package org.splab.vocabulary.extractor.nodelists;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTemplateDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompoundStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDeclarationStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTEnumerationSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTemplateDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTemplateSpecialization;

/**
 * Esta classe é responsável por retornar listas de tipos de declarações, para
 * que as classes que necessitem usar listas de declarações internas possam
 * funcionar corretamente.
 * 
 * @author Israel Gomes de Lima
 */

public class DeclarationList {

	private DeclarationList() {
	}

	/**
	 * Retorna uma lista de declarações internas nas entidades passadas como
	 * argumento
	 * 
	 * @param astNode
	 * @return lista de nodos ast
	 */
	public static List<ASTNode> getInnerTypes(ASTNode astNode) {

		// Lista de tipos de declarações internas
		List<ASTNode> typesDeclarations = new ArrayList<ASTNode>();

		if (astNode instanceof IASTTranslationUnit) {
			/*
			 * Esse é o tipo principal e que representa todo o arquivo Aqui é
			 * recuperada uma lista das entidades internas no arquivo
			 */
			IASTTranslationUnit translationUnit = (IASTTranslationUnit) astNode;
			typesDeclarations = getTypes(translationUnit.getDeclarations());
		}

		if (astNode instanceof ICPPASTFunctionDefinition) {
			// Recupera uma lista de declarações internas nas funções
			CPPASTFunctionDefinition functionDefinition = (CPPASTFunctionDefinition) astNode;

			typesDeclarations = getTypes(((CPPASTCompoundStatement) functionDefinition.getBody()).getStatements());
		}

		if (astNode instanceof CPPASTCompositeTypeSpecifier) {
			// Recupera uma lista de declarações internas nas classes, structs,
			// unions
			CPPASTCompositeTypeSpecifier compositeType = (CPPASTCompositeTypeSpecifier) astNode;

			typesDeclarations = getTypes(compositeType.getMembers());
		}

		return typesDeclarations;
	}

	/**
	 * Retorna uma lista de entidades entre um conjunto de declarações qualquer
	 * 
	 * @param declarations
	 * @return lista de nodos ast
	 */
	public static List<ASTNode> getTypes(IASTStatement[] declarations) {

		// Lista de entidades
		List<ASTNode> statementList = new ArrayList<ASTNode>();

		for (IASTStatement declaration : declarations) {
			if (declaration instanceof CPPASTDeclarationStatement) {

				CPPASTDeclarationStatement decl = (CPPASTDeclarationStatement) declaration;
				if (decl.getDeclaration() instanceof IASTFunctionDefinition) {
					// Recupera uma declaração de função
					statementList.add((ASTNode) decl.getDeclaration());
				}

				if (decl.getDeclaration() instanceof IASTSimpleDeclaration) {
					CPPASTSimpleDeclaration node = (CPPASTSimpleDeclaration) decl.getDeclaration();
					IASTDeclSpecifier declarationSpecifier = node.getDeclSpecifier();

					if (declarationSpecifier instanceof CPPASTEnumerationSpecifier) {
						// Recupera uma declaração de enum
						CPPASTEnumerationSpecifier enumeration = (CPPASTEnumerationSpecifier) declarationSpecifier;
						statementList.add((ASTNode) enumeration);
					}

					if (declarationSpecifier instanceof CPPASTCompositeTypeSpecifier) {
						// Recupera uma declaração de classe, struct ou union
						CPPASTCompositeTypeSpecifier composite = (CPPASTCompositeTypeSpecifier) declarationSpecifier;
						statementList.add((ASTNode) composite);
					}
				}
			}
		}

		return statementList;
	}

	public static List<ASTNode> getTypes(IASTDeclaration[] declarations) {

		// Lista de entidades
		List<ASTNode> statementList = new ArrayList<ASTNode>();

		for (IASTDeclaration declaration : declarations) {
			if (declaration instanceof IASTFunctionDefinition) {
				// Recupera uma declaração de função
				statementList.add((ASTNode) declaration);
			}

			if (declaration instanceof IASTSimpleDeclaration) {
				CPPASTSimpleDeclaration node = (CPPASTSimpleDeclaration) declaration;

				IASTDeclSpecifier declarationSpecifier = node.getDeclSpecifier();

				if (declarationSpecifier instanceof CPPASTEnumerationSpecifier) {
					// Recupera uma declaração de enum
					CPPASTEnumerationSpecifier enumeration = (CPPASTEnumerationSpecifier) declarationSpecifier;
					statementList.add((ASTNode) enumeration);
				}

				if (declarationSpecifier instanceof CPPASTCompositeTypeSpecifier) {
					// Recupera uma declaração de classe, struct ou union
					CPPASTCompositeTypeSpecifier composite = (CPPASTCompositeTypeSpecifier) declarationSpecifier;
					statementList.add((ASTNode) composite);
				}
			}

			// Extrai declarações de templates
			if (declaration instanceof ICPPASTTemplateDeclaration) {
				if (declaration instanceof CPPASTTemplateDeclaration) {
					CPPASTTemplateDeclaration templateDeclaration = (CPPASTTemplateDeclaration) declaration;

					if (templateDeclaration.getDeclaration() instanceof ICPPASTFunctionDefinition) {
						// Recupera uma declaração de função
						statementList.add((ASTNode) templateDeclaration.getDeclaration());
					}

					if (templateDeclaration.getDeclaration() instanceof IASTSimpleDeclaration) {
						CPPASTSimpleDeclaration node = (CPPASTSimpleDeclaration) templateDeclaration.getDeclaration();

						IASTDeclSpecifier declarationSpecifier = node.getDeclSpecifier();

						if (declarationSpecifier instanceof CPPASTEnumerationSpecifier) {
							// Recupera uma declaração de enum
							CPPASTEnumerationSpecifier enumeration = (CPPASTEnumerationSpecifier) declarationSpecifier;
							statementList.add((ASTNode) enumeration);
						}

						if (declarationSpecifier instanceof CPPASTCompositeTypeSpecifier) {
							// Recupera uma declaração de classe, struct ou
							// union
							CPPASTCompositeTypeSpecifier composite = (CPPASTCompositeTypeSpecifier) declarationSpecifier;
							statementList.add((ASTNode) composite);
						}
					}
				}
				
				if(declaration instanceof CPPASTTemplateSpecialization){
					CPPASTTemplateSpecialization templateSpecialization = (CPPASTTemplateSpecialization) declaration;
					
					if (templateSpecialization.getDeclaration() instanceof ICPPASTFunctionDefinition) {
						// Recupera uma declaração de função
						statementList.add((ASTNode) templateSpecialization.getDeclaration());
					}

					if (templateSpecialization.getDeclaration() instanceof IASTSimpleDeclaration) {
						CPPASTSimpleDeclaration node = (CPPASTSimpleDeclaration) templateSpecialization.getDeclaration();

						IASTDeclSpecifier declarationSpecifier = node.getDeclSpecifier();

						if (declarationSpecifier instanceof CPPASTEnumerationSpecifier) {
							// Recupera uma declaração de enum
							CPPASTEnumerationSpecifier enumeration = (CPPASTEnumerationSpecifier) declarationSpecifier;
							statementList.add((ASTNode) enumeration);
						}

						if (declarationSpecifier instanceof CPPASTCompositeTypeSpecifier) {
							// Recupera uma declaração de classe, struct ou
							// union
							CPPASTCompositeTypeSpecifier composite = (CPPASTCompositeTypeSpecifier) declarationSpecifier;
							statementList.add((ASTNode) composite);
						}
					}
				}
			}
		}

		return statementList;
	}
}
