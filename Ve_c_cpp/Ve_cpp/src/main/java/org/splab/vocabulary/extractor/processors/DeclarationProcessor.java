package org.splab.vocabulary.extractor.processors;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause;
import org.eclipse.cdt.core.dom.ast.IASTInitializerList;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTPointerOperator;
import org.eclipse.cdt.core.dom.ast.IVariable;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTDesignator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTArrayDesignator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTArrayRangeDesignator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDesignatedInitializer;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTElaboratedTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTEnumerationSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTEqualsInitializer;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFieldDesignator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNamedTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTPointer;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPVariable;
import org.splab.vocabulary.extractor.processors.vocabulay.manager.FileVocabularyManager;
import org.splab.vocabulary.extractor.processors.vocabulay.manager.FunctionVocabularyManager;
import org.splab.vocabulary.extractor.processors.vocabulay.manager.VocabularyManager;
import org.splab.vocabulary.extractor.util.VxlManager;
import org.splab.vocabulary.extractor.vloccount.EntityType;

/**
 * DeclarationProcessor é responsavel por receber uma declaração qualquer e
 * designar processadores para extrair seu vocabulário ou realizar por sí mesmo
 * tais extrações
 * 
 * @author Israel Gomes de Lima
 */
public class DeclarationProcessor {

	/**
	 * Cria fragmentos de vxl específicos para orgnizar o arquivo final
	 */
	private StringBuffer prttpVXL;
	private StringBuffer enumVXL;
	private StringBuffer structVXL;
	private StringBuffer unionVXL;
	private StringBuffer classVXL;
	private VocabularyManager vocabularyManager;

	/**
	 * Construtor da classe, responsável por criar o processador de declarações
	 * 
	 * @param vocabularyManager
	 */
	public DeclarationProcessor(VocabularyManager vocabularyManager) {
		this.prttpVXL = new StringBuffer();
		this.enumVXL = new StringBuffer();
		this.structVXL = new StringBuffer();
		this.unionVXL = new StringBuffer();
		this.classVXL = new StringBuffer();

		this.vocabularyManager = vocabularyManager;
	}

	/**
	 * Designa métodos ou classes especializadas em extrair variáveis locais ou
	 * globais, protótipos de funções, enum, struct, union e class
	 * 
	 * @param simpleDeclaration
	 * @param indentationLevel
	 */
	public void extractDeclaration(CPPASTSimpleDeclaration simpleDeclaration, int indentationLevel) {

		// extrai a declaração específica
		IASTDeclSpecifier declarations = simpleDeclaration.getDeclSpecifier();

		// processa enums
		if (declarations instanceof CPPASTEnumerationSpecifier) {
			CPPASTEnumerationSpecifier enumeration = (CPPASTEnumerationSpecifier) declarations;
			EnumProcessor enumProcessor = new EnumProcessor(enumeration, indentationLevel);

			IASTDeclarator[] declarators = simpleDeclaration.getDeclarators();
			extractDeclarator(declarators);

			enumVXL.append(enumProcessor.getVxlFragment());

			return;
		}

		// processa structs, unions e class
		if (declarations instanceof CPPASTCompositeTypeSpecifier) {

			CPPASTCompositeTypeSpecifier compositeType = (CPPASTCompositeTypeSpecifier) declarations;
			extractCompositeType(compositeType, false, indentationLevel);

			IASTDeclarator[] declarators = simpleDeclaration.getDeclarators();
			extractDeclarator(declarators);

			return;
		}

		/*
		 * Essa sequencia de ifs, trata objetos diferentes mas que compartilham
		 * do mesmo tipo de vocabulário
		 */
		if (declarations instanceof CPPASTSimpleDeclSpecifier) {
			extractSimpleDeclaration(simpleDeclaration, indentationLevel);

			return;
		}

		if (declarations instanceof CPPASTNamedTypeSpecifier) {
			extractSimpleDeclaration(simpleDeclaration, indentationLevel);

			return;
		}

		if (declarations instanceof CPPASTElaboratedTypeSpecifier) {
			extractSimpleDeclaration(simpleDeclaration, indentationLevel);

			return;
		}
	}

	/**
	 * Designa métodos ou classes especializadas em extrair protótipos de
	 * métodos, atributos, enum, struct, union e class. Ou seja, extrair tudo o
	 * que é da classe mas não do método
	 * 
	 * @param simpleDeclaration
	 * @param classDeclProcess
	 * @param indentationLevel
	 */
	public void extractDeclaration(CPPASTSimpleDeclaration simpleDeclaration, ClassProcessor classDeclProcess,
			int indentationLevel) {
		// extrai a declaração específica
		IASTDeclSpecifier declarations = simpleDeclaration.getDeclSpecifier();

		// processa enums
		if (declarations instanceof CPPASTEnumerationSpecifier) {
			CPPASTEnumerationSpecifier enumeration = (CPPASTEnumerationSpecifier) declarations;
			EnumProcessor enumProcessor = new EnumProcessor(enumeration, indentationLevel);

			IASTDeclarator[] declarators = simpleDeclaration.getDeclarators();
			extractDeclarator(declarators);

			enumVXL.append(enumProcessor.getVxlFragment());

			return;
		}

		// processa structs e unions
		if (declarations instanceof CPPASTCompositeTypeSpecifier) {

			CPPASTCompositeTypeSpecifier compositeType = (CPPASTCompositeTypeSpecifier) declarations;
			extractCompositeType(compositeType, true, indentationLevel);

			IASTDeclarator[] declarators = simpleDeclaration.getDeclarators();
			extractDeclarator(declarators);

			return;
		}

		/*
		 * Essa sequencia de ifs, trata objetos diferentes mas que compartilham
		 * do mesmo tipo de vocabulário
		 */
		if (declarations instanceof CPPASTSimpleDeclSpecifier) {
			extractSimpleDeclaration(simpleDeclaration, classDeclProcess, indentationLevel);

			return;
		}

		if (declarations instanceof CPPASTNamedTypeSpecifier) {
			extractSimpleDeclaration(simpleDeclaration, classDeclProcess, indentationLevel);

			return;
		}

		if (declarations instanceof CPPASTElaboratedTypeSpecifier) {
			extractSimpleDeclaration(simpleDeclaration, classDeclProcess, indentationLevel);

			return;
		}
	}

	/**
	 * Extra as declarações de variáveis padrões no momento da criação da class,
	 * struct, union e enum
	 * 
	 * @param declarators
	 */
	private void extractDeclarator(IASTDeclarator[] declarators) {
		if (declarators.length > 0) {
			for (IASTDeclarator declarator : declarators) {
				if (declarator != null) {
					String storage = storageClass(declarator.getName());
					String access = "";
					String name = declarator.getName().toString();

					vocabularyManager.insertVariable(name, access, storage);

					if (declarator.getInitializer() != null)
						extractEqualsInitializer((CPPASTEqualsInitializer) declarator.getInitializer());
				}
			}
		}
	}

	/**
	 * Responsável por diferenciar as struct's, union's e class's
	 * 
	 * @param compositeType
	 * @param indentationLevel
	 */
	private void extractCompositeType(CPPASTCompositeTypeSpecifier compositeType, boolean isInner,
			int indentationLevel) {

		switch (compositeType.getKey()) {
		case 1:
			StructProcessor struct = new StructProcessor((CPPASTCompositeTypeSpecifier) compositeType,
					vocabularyManager, indentationLevel);
			structVXL.append(struct.getVxlFragment());
			break;

		case 2:
			UnionProcessor union = new UnionProcessor((CPPASTCompositeTypeSpecifier) compositeType, vocabularyManager,
					indentationLevel);
			unionVXL.append(union.getVxlFragment());
			break;
		case 3:
			EntityType type = EntityType.CLASS;

			if (isInner == true)
				type = EntityType.INNER_CLASS;

			ClassProcessor classProcessor = new ClassProcessor(compositeType, isInner, type, indentationLevel);
			classVXL.append(classProcessor.getVxlFragment());
			break;
		}
	}

	/**
	 * Processa as declarações das variáveis e os protótipos de funções, sendo
	 * essas fora de classes
	 * 
	 * @param declaration
	 * @param indentationLevel
	 */
	private void extractSimpleDeclaration(CPPASTSimpleDeclaration declaration, int indentationLevel) {
		IASTDeclarator[] simpleDeclarations = declaration.getDeclarators();

		if (simpleDeclarations.length == 0) {
			return;
		}

		for (IASTDeclarator declarator : simpleDeclarations) {

			String storage = storageClass(declarator.getName());
			String access = access(declaration.getDeclSpecifier());
			String name = nameFormatter(declarator.getName().toString());

			// Capitura coisas como: int (*(*(* ptr)))
			if (declarator.getNestedDeclarator() != null) {
				IASTDeclarator nestedDecl = declarator;
				while (nestedDecl != null) {
					if (!nestedDecl.getName().toString().equals("")) {
						name = nameFormatter(nestedDecl.getName().toString());

						declarator = nestedDecl;
					}

					nestedDecl = nestedDecl.getNestedDeclarator();
				}
			}

			/*
			 * Se a variável for ponteiro, extrai o access a posição de memória
			 * do ponteiro
			 */
			CPPASTDeclarator ast_declarator = (CPPASTDeclarator) declarator;
			if (ast_declarator.getPointerOperators().length > 0) {
				for (IASTPointerOperator pointerOperator : ast_declarator.getPointerOperators()) {

					if (pointerOperator instanceof CPPASTPointer) {
						CPPASTPointer pointer = (CPPASTPointer) declarator.getPointerOperators()[0];

						if (pointer.isConst())
							access = "const";
						else if (pointer.isVolatile())
							access = "volatile";
						else if (pointer.isRestrict())
							access = "restrict";
						else
							access = "";
					}
				}
			}

			// Diferencia a declaração em variável e protótipo de função
			if (declarator instanceof CPPASTFunctionDeclarator) {
				CPPASTFunctionDeclarator functionDeclarator = (CPPASTFunctionDeclarator) declarator;
				IASTParameterDeclaration[] parameters = functionDeclarator.getParameters();

				for (IASTParameterDeclaration parameter : parameters) {
					if (!parameter.getDeclarator().getName().toString().equals("")) {
						if (parameter.getDeclarator().getInitializer() != null)
							extractEqualsInitializer(
									(CPPASTEqualsInitializer) parameter.getDeclarator().getInitializer());
					}
				}
				prttpVXL.append(VxlManager.funtionPrototype(name, access, storage, indentationLevel));
			}

			else {
				if (vocabularyManager instanceof FileVocabularyManager) {
					FileVocabularyManager vocManager = (FileVocabularyManager) vocabularyManager;
					vocManager.insertGlobalVariable(name, access, storage);
				} else {
					if (vocabularyManager instanceof FunctionVocabularyManager) {
						FunctionVocabularyManager vocManager = (FunctionVocabularyManager) vocabularyManager;
						vocManager.insertLocalVariable(name, access, storage);
					}
				}
			}

			// extrai as inicializações na hora da declaração
			if (simpleDeclarations[0].getInitializer() != null)
				extractEqualsInitializer((CPPASTEqualsInitializer) declarator.getInitializer());
		}
	}

	/**
	 * Processa declarações que estão na classe, mas fora de qualquer método ou
	 * função interna a classe
	 * 
	 * @param declaration
	 * @param classDeclProcess
	 * @param indentationLevel
	 */
	private void extractSimpleDeclaration(CPPASTSimpleDeclaration declaration, ClassProcessor classDeclProcess,
			int indentationLevel) {

		IASTDeclarator[] simpleDeclarations = declaration.getDeclarators();

		if (simpleDeclarations.length == 0) {
			return;
		}

		for (IASTDeclarator declarator : simpleDeclarations) {

			String storage = storageClass(declarator.getName());
			String access = access(declaration.getDeclSpecifier());
			String name = nameFormatter(declarator.getName().toString());

			// Se a variável for ponteiro, extrai o access a posição de memória
			// do ponteiro
			CPPASTDeclarator ast_declarator = (CPPASTDeclarator) declarator;
			if (ast_declarator.getPointerOperators().length > 0) {
				for (IASTPointerOperator pointerOperator : ast_declarator.getPointerOperators()) {

					if (pointerOperator instanceof CPPASTPointer) {
						CPPASTPointer pointer = (CPPASTPointer) pointerOperator;

						if (pointer.isConst())
							access = "const";
						else if (pointer.isVolatile())
							access = "volatile";
						else if (pointer.isRestrict())
							access = "restrict";
						else
							access = "";
					}
				}
			}

			// Diferencia a declaração em variável e protótipo de função
			if (declarator instanceof CPPASTFunctionDeclarator) {

				CPPASTFunctionDeclarator functionDeclarator = (CPPASTFunctionDeclarator) declarator;
				IASTParameterDeclaration[] parameters = functionDeclarator.getParameters();

				for (IASTParameterDeclaration parameter : parameters) {
					if (!parameter.getDeclarator().getName().toString().equals("")) {
						if (parameter.getDeclarator().getInitializer() != null)
							extractEqualsInitializer(
									(CPPASTEqualsInitializer) parameter.getDeclarator().getInitializer());
					}
				}

				if (classDeclProcess.getFriendDeclarationList().contains(functionDeclarator)) {
					prttpVXL.append(VxlManager.funtionPrototype(name, access, storage, indentationLevel));
				}
			}

			else {
				if (classDeclProcess.getFieldList().containsKey(name)) {
					new FieldProcessor(declaration.getDeclSpecifier(), classDeclProcess.getFieldList().get(name),
							indentationLevel + 1, classDeclProcess.getVocabularyManager());
				}
			}

			// extrai as inicializações na hora da declaração
			if (simpleDeclarations[0].getInitializer() != null)
				extractEqualsInitializer((CPPASTEqualsInitializer) declarator.getInitializer());
		}
	}

	/**
	 * Processa as inicializações de variáveis na hora da declaração
	 * 
	 * @param initializer
	 */
	public void extractEqualsInitializer(CPPASTEqualsInitializer initializer) {

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
								if (clause instanceof IASTInitializerList)
									extractInitializerList((IASTInitializerList) clause);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Extrai listas de inicializações, no caso de arrays por exemplo
	 * 
	 * @param initializerList
	 */
	private void extractInitializerList(IASTInitializerList initializerList) {
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
	private void extractDesignatedInitializer(CPPASTDesignatedInitializer designated) {

		if (designated == null)
			return;

		for (ICPPASTDesignator designator : designated.getDesignators()) {
			extractDesignators(designator);
		}

		if (designated.getOperand() instanceof IASTExpression)
			ExpressionProcessor.extractExpression((IASTExpression) designated.getOperand());

		if (designated.getOperand() instanceof IASTInitializerList)
			extractInitializerList((IASTInitializerList) designated.getOperand());
	}

	/**
	 * Extrai as designators
	 * 
	 * @param designator
	 */
	private void extractDesignators(ICPPASTDesignator designator) {

		if (designator instanceof CPPASTArrayDesignator) {
			CPPASTArrayDesignator arrayDesignator = (CPPASTArrayDesignator) designator;
			ExpressionProcessor.extractExpression(arrayDesignator.getSubscriptExpression());
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
	private void extractArrayRangeDesignator(CPPASTArrayRangeDesignator arrayRangeDesignator) {

		if (arrayRangeDesignator.getRangeCeiling() instanceof IASTExpression)
			ExpressionProcessor.extractExpression(arrayRangeDesignator.getRangeCeiling());

		else if (arrayRangeDesignator.getRangeCeiling() instanceof CPPASTArrayDesignator)
			extractArrayRangeDesignator((CPPASTArrayRangeDesignator) arrayRangeDesignator.getRangeCeiling());

		if (arrayRangeDesignator.getRangeFloor() instanceof IASTExpression)
			ExpressionProcessor.extractExpression(arrayRangeDesignator.getRangeFloor());

		else if (arrayRangeDesignator.getRangeFloor() instanceof CPPASTArrayDesignator)
			extractArrayRangeDesignator((CPPASTArrayRangeDesignator) arrayRangeDesignator.getRangeFloor());
	}

	/**
	 * Extrai field designators
	 * 
	 * @param fieldDesignator
	 */
	private void extractFieldDesignator(CPPASTFieldDesignator fieldDesignator) {

		String storage = storageClass(fieldDesignator.getName());
		String access = "";
		String name = fieldDesignator.getName().toString();

		if (vocabularyManager != null)
			vocabularyManager.insertVariable(name, access, storage);
	}

	/**
	 * Processa o tipo de classe de armazenamento da variável
	 * 
	 * @param variableName
	 * @return
	 */
	private static String storageClass(IASTName variableName) {
		IVariable var = new CPPVariable(variableName);
		String storage;

		if (var.isAuto())
			storage = "auto";
		else if (var.isStatic())
			storage = "static";
		else if (var.isRegister())
			storage = "register";
		else if (var.isExtern())
			storage = "extern";
		else
			storage = "auto";

		return storage;
	}

	/**
	 * Processa o tipo de acesso da variável
	 * 
	 * @param decl
	 * @return
	 */
	private static String access(IASTDeclSpecifier decl) {
		String access = "";

		if (decl.isConst())
			access = "const";
		else if (decl.isVolatile())
			access = "volatile";
		else if (decl.isInline())
			access = "inline";
		else if (decl.isRestrict())
			access = "restrict";

		return access;
	}

	private String nameFormatter(String text) {

		String nome = "";
		for (char character : text.toCharArray()) {
			if (character != '<' && character != '>' && character != ' ')
				nome += character;
		}

		return nome;
	}

	/**
	 * Retorna os fragmentos de vxl organizados
	 * 
	 * @return
	 */
	public StringBuffer getVxlFragment() {
		StringBuffer allVxlFragment = new StringBuffer();
		allVxlFragment.append(prttpVXL);
		allVxlFragment.append(enumVXL);
		allVxlFragment.append(structVXL);
		allVxlFragment.append(unionVXL);
		allVxlFragment.append(classVXL);

		return allVxlFragment;
	}
}
