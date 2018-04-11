package org.splab.vocabulary.extractor.processors;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause;
import org.eclipse.cdt.core.dom.ast.IASTInitializerList;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IVariable;
import org.eclipse.cdt.core.dom.ast.c.ICASTDesignator;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTDesignatedInitializer;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTElaboratedTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTEnumerationSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTEqualsInitializer;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFieldDesignator;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTTypedefNameSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CVariable;

/**
 * @author Israel Gomes de Lima
 **/
public class Declarations {
	private static StringBuffer vxlFragment;
	private static FunctionVocabularyManager functionVocabulary;

	// Extrai a classe de armazenamento da variável
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

	// Processa estruturas e uniões
	private static DeclarationsType extractCompositeType(CASTCompositeTypeSpecifier compositeType, boolean scope) {
		DeclarationsType valor = null;

		switch (compositeType.getKey()) {
		case 1:
			StructProcessor struct = new StructProcessor((CASTCompositeTypeSpecifier) compositeType, scope);
			vxlFragment.append(struct.getVxlFragment());
			valor = DeclarationsType.STRUCT;

			break;

		case 2:
			UnionProcessor union = new UnionProcessor((CASTCompositeTypeSpecifier) compositeType, scope);
			vxlFragment.append(union.getVxlFragment());
			valor = DeclarationsType.UNION;

			break;
		}

		return valor;
	}

	// Extrai declarações
	private static void extractSimpleDeclaration(CASTSimpleDeclaration declaration, boolean scope) {
		IASTDeclarator[] simpleDeclarations = declaration.getDeclarators();

		if (simpleDeclarations.length == 0) {
			return;
		}

		for (IASTDeclarator declarator : simpleDeclarations) {
			String storage = storageClass(declarator.getName());
			String access = access(declaration.getDeclSpecifier());
			String name = declarator.getName().toString();

			if (scope == true) {
				if (declarator instanceof CASTFunctionDeclarator)
					functionVocabulary.insertFuncPrototipo(name, access, storage);
				else
					functionVocabulary.insertLocalVariable(name, access, storage);
			} else {
				if (declarator instanceof CASTFunctionDeclarator)
					FileProcessor.insertGlobalPrttp(name, access, storage);
				else {
					FileProcessor.insertGlobalVariable(name, access, storage);
				}
			}

			// extrai as inicializações na hora da declaração
			if (declarator.getInitializer() != null)
				extractEqualsInitializer((CASTEqualsInitializer) declarator.getInitializer());
		}
	}

	// extrai as inicializações das variáveis
	private static void extractEqualsInitializer(CASTEqualsInitializer initializer) {
		IASTInitializerClause initializerClause = initializer.getInitializerClause();

		// se for uma expressão
		if (initializerClause != null) {
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
							if (clause instanceof IASTInitializerClause)
								extractInitializerList((IASTInitializerList) clause);
						}
					}
				}
			}
		}
	}

	// extrai as fields
	private static void extractDesignatedInitializer(CASTDesignatedInitializer designatedInitializer) {

		if (designatedInitializer == null)
			return;

		if (designatedInitializer.getDesignators().length > 0) {

			ICASTDesignator[] designators = designatedInitializer.getDesignators();
			for (ICASTDesignator designator : designators) {
				if (designator instanceof CASTFieldDesignator) {
					CASTFieldDesignator fieldDesignator = (CASTFieldDesignator) designator;

					String storage = storageClass(fieldDesignator.getName());
					String access = "";
					String name = fieldDesignator.getName().toString();

					functionVocabulary.insertLocalVariable(name, access, storage);
				}
			}
		}

		if (designatedInitializer.getOperand() != null)
			if (designatedInitializer.getOperand() instanceof IASTExpression)
				ExpressionProcessor.extractExpression((IASTExpression) designatedInitializer.getOperand());
	}

	// extrai listas de inicializações, no caso de arrays por exemplo
	private static void extractInitializerList(IASTInitializerList initializerList) {
		IASTInitializerClause[] initializerClauses = initializerList.getClauses();

		for (IASTInitializerClause clause : initializerClauses) {
			if (clause instanceof IASTExpression) {
				ExpressionProcessor.extractExpression((IASTExpression) clause);
			} else {
				if (clause instanceof IASTInitializerClause) {
					if (clause instanceof CASTDesignatedInitializer) {
						CASTDesignatedInitializer designatedInitializer = (CASTDesignatedInitializer) clause;

						extractDesignatedInitializer(designatedInitializer);
					}
					
					if (clause instanceof IASTInitializerList)
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
			extractEqualsInitializer((CASTEqualsInitializer) declarator.getInitializer());
	}

	public static DeclarationsType declarations(CASTSimpleDeclaration simpleDeclaration,
			FunctionVocabularyManager manager, boolean scope) {
		vxlFragment = new StringBuffer();
		functionVocabulary = manager;

		DeclarationsType valor = null;

		// extrai a declaração específica
		IASTDeclSpecifier declarations = simpleDeclaration.getDeclSpecifier();

		// processa enums
		if (declarations instanceof CASTEnumerationSpecifier) {
			CASTEnumerationSpecifier enumeration = (CASTEnumerationSpecifier) declarations;
			EnumProcessor enumProcessor = new EnumProcessor(enumeration, scope, true, 0);

			vxlFragment.append(enumProcessor.getVxlFragment());

			valor = DeclarationsType.ENUM;
		}

		// processa structs e unions
		if (declarations instanceof CASTCompositeTypeSpecifier) {
			CASTCompositeTypeSpecifier compositeType = (CASTCompositeTypeSpecifier) declarations;
			valor = extractCompositeType(compositeType, scope);

			IASTDeclarator[] declarators = simpleDeclaration.getDeclarators();

			if (declarators.length > 0) {
				for (IASTDeclarator declarator : declarators) {
					extractDeclarator(declarator, scope);
				}
			}
		}

		if (declarations instanceof CASTTypedefNameSpecifier) {
			extractSimpleDeclaration(simpleDeclaration, scope);

			valor = DeclarationsType.SIMPLEDECLARATION;
		}

		if (declarations instanceof CASTSimpleDeclSpecifier) {
			extractSimpleDeclaration(simpleDeclaration, scope);

			valor = DeclarationsType.SIMPLEDECLARATION;
		}

		if (declarations instanceof CASTElaboratedTypeSpecifier) {
			extractSimpleDeclaration(simpleDeclaration, scope);

			valor = DeclarationsType.SIMPLEDECLARATION;
		}

		return valor;
	}

	public static StringBuffer getVxlFragment()// retorna o vxlFragment
	{
		return vxlFragment;
	}
}
