package org.splab.vocabulary.extractor.processors;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause;
import org.eclipse.cdt.core.dom.ast.IASTInitializerList;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IVariable;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTDesignator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDesignatedInitializer;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTElaboratedTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTEnumerationSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTEqualsInitializer;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFieldDesignator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNamedTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPVariable;
import org.splab.vocabulary.extractor.vloccount.EntityType;

/**
 * @author Israel Gomes de Lima
 **/
public class Declarations {
	private static StringBuffer vxlFragment;
	private static FunctionVocabularyManager functionVocabulary;

	// Extrai a classe de armazenamento da variável
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

	// extrai o acesso da variável
	private static String access(IASTDeclSpecifier decl) {

		String acesso = "";

		if (decl.isConst())
			acesso = "const";
		else if (decl.isVolatile())
			acesso = "volatile";
		else if (decl.isInline())
			acesso = "inline";
		else if (decl.isRestrict())
			acesso = "restrict";

		return acesso;
	}

	// Processa estruturas, uniões e classes
	private static DeclarationsType extractCompositeType(CPPASTCompositeTypeSpecifier compositeType, boolean scope) {
		DeclarationsType valor = null;

		switch (compositeType.getKey()) {
		case 1:
			StructProcessor struct = new StructProcessor((CPPASTCompositeTypeSpecifier) compositeType, scope);
			vxlFragment.append(struct.getVxlFragment());
			valor = DeclarationsType.STRUCT;

			break;

		case 2:
			UnionProcessor union = new UnionProcessor((CPPASTCompositeTypeSpecifier) compositeType, scope);
			vxlFragment.append(union.getVxlFragment());
			valor = DeclarationsType.UNION;

			break;
		case 3:
			ClassProcessor classProcessor = new ClassProcessor(compositeType, false, EntityType.CLASS);
			vxlFragment.append(classProcessor.getVxlFragment());
			valor = DeclarationsType.CLASS;
			break;
		}

		return valor;
	}

	// Extrai declarações
	private static void extractSimpleDeclaration(CPPASTSimpleDeclaration declaration, boolean scope) {
		IASTDeclarator[] simpleDeclarations = declaration.getDeclarators();

		if (simpleDeclarations.length == 0) {
			return;
		}

		for (IASTDeclarator declarator : simpleDeclarations) {
			String storage = storageClass(declarator.getName());
			String access = access(declaration.getDeclSpecifier());
			String name = declarator.getName().toString();

			if (scope == true) {
				if (declarator instanceof CPPASTFunctionDeclarator)
					functionVocabulary.insertFuncPrototipo(name, access, storage);
				else
					functionVocabulary.insertLocalVariable(name, access, storage);
			} else {
				if (declarator instanceof CPPASTFunctionDeclarator)
					FileProcessor.insertGlobalPrttp(name, access, storage);
				else {
					FileProcessor.insertGlobalVariable(name, access, storage);
				}
			}

			// extrai as inicializações na hora da declaração
			if (simpleDeclarations[0].getInitializer() != null)
				extractEqualsInitializer((CPPASTEqualsInitializer) declarator.getInitializer());
		}
	}

	// extrai as inicializações das variáveis
	public static void extractEqualsInitializer(CPPASTEqualsInitializer initializer) {

		if (initializer == null)
			return;

		IASTInitializerClause initializerClause = initializer.getInitializerClause();

		// se for uma expressão
		if (initializerClause instanceof IASTExpression) {
			ExpressionProcessor.extractExpression((IASTExpression) initializerClause);
		} else {
			// se for uma lista de inicializações
			if (initializerClause instanceof IASTInitializerList) {
				IASTInitializerList list = (IASTInitializerList) initializerClause;

				for (IASTInitializerClause clause : list.getClauses()) {
					if (clause instanceof IASTExpression)
						ExpressionProcessor.extractExpression((IASTExpression) clause);
					else {
						if (clause instanceof IASTInitializerClause) {
							if (clause instanceof CPPASTDesignatedInitializer) {
								extractDesignatedInitializer((CPPASTDesignatedInitializer) clause);

							} else {
								extractInitializerList((IASTInitializerList) clause);
							}
						}
					}
				}
			}
		}
	}

	// extrai listas de inicializações, no caso de arrays por exemplo
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

	private static void extractDeclarator(IASTDeclarator declarator, boolean scope) {

		if (declarator == null)
			return;

		String storage = storageClass(declarator.getName());
		String access = "";
		String name = declarator.getName().toString();

		if (scope == true) {
			functionVocabulary.insertLocalVariable(name, access, storage);
		} else {
			FileProcessor.insertGlobalVariable(name, access, storage);
		}

		if (declarator.getInitializer() != null)
			extractEqualsInitializer((CPPASTEqualsInitializer) declarator.getInitializer());
	}

	private static void extractDesignatedInitializer(CPPASTDesignatedInitializer designated) {

		if (designated == null)
			return;

		for (ICPPASTDesignator designator : designated.getDesignators()) {
			CPPASTFieldDesignator fieldDesignator = (CPPASTFieldDesignator) designator;

			String storage = storageClass(fieldDesignator.getName());
			String access = "";
			String name = fieldDesignator.getName().toString();

			if (functionVocabulary != null) {
				functionVocabulary.insertLocalVariable(name, access, storage);
			} else {
				FileProcessor.insertGlobalVariable(name, access, storage);
			}
		}

		ExpressionProcessor.extractExpression((IASTExpression) designated.getOperand());

	}

	public static DeclarationsType declarations(CPPASTSimpleDeclaration simpleDeclaration, FunctionVocabularyManager manager, boolean scope) {
		
		vxlFragment = new StringBuffer();
		functionVocabulary = manager;

		DeclarationsType declarationType = null;

		// extrai a declaração específica
		IASTDeclSpecifier declarations = simpleDeclaration.getDeclSpecifier();

		// processa enums
		if (declarations instanceof CPPASTEnumerationSpecifier) {
			CPPASTEnumerationSpecifier enumeration = (CPPASTEnumerationSpecifier) declarations;
			EnumProcessor enumProcessor = new EnumProcessor(enumeration, scope, true, 0);

			vxlFragment.append(enumProcessor.getVxlFragment());

			declarationType = DeclarationsType.ENUM;
		}

		// processa structs e unions
		if (declarations instanceof CPPASTCompositeTypeSpecifier) {

			CPPASTCompositeTypeSpecifier compositeType = (CPPASTCompositeTypeSpecifier) declarations;
			
			declarationType = extractCompositeType(compositeType, scope);

			IASTDeclarator[] declarators = simpleDeclaration.getDeclarators();
			if (declarators.length > 0) {
				for (IASTDeclarator declarator : declarators) {
					extractDeclarator(declarator, scope);
				}
			}
		}

		if (declarations instanceof CPPASTSimpleDeclSpecifier) {
			extractSimpleDeclaration(simpleDeclaration, scope);

			declarationType = DeclarationsType.VARIABLE;
		}

		if (declarations instanceof CPPASTNamedTypeSpecifier) {
			extractSimpleDeclaration(simpleDeclaration, scope);

			declarationType = DeclarationsType.VARIABLE;
		}

		if (declarations instanceof CPPASTElaboratedTypeSpecifier) {
			extractSimpleDeclaration(simpleDeclaration, scope);

			declarationType = DeclarationsType.VARIABLE;
		}

		return declarationType;
	}

	public static StringBuffer getVxlFragment()// retorna o vxlFragment
	{
		return vxlFragment;
	}
}
