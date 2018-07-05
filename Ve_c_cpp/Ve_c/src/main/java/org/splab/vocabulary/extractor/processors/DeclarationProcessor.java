package org.splab.vocabulary.extractor.processors;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause;
import org.eclipse.cdt.core.dom.ast.IASTInitializerList;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTPointerOperator;
import org.eclipse.cdt.core.dom.ast.IVariable;
import org.eclipse.cdt.core.dom.ast.c.ICASTDesignator;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTArrayDesignator;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTArrayRangeDesignator;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTDesignatedInitializer;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTElaboratedTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTEnumerationSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTEqualsInitializer;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFieldDesignator;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTPointer;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTTypedefNameSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CVariable;
import org.splab.vocabulary.extractor.processors.vocabulay.manager.FileVocabularyManager;
import org.splab.vocabulary.extractor.processors.vocabulay.manager.FunctionVocabularyManager;
import org.splab.vocabulary.extractor.processors.vocabulay.manager.VocabularyManager;
import org.splab.vocabulary.extractor.util.VxlManager;

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

		this.vocabularyManager = vocabularyManager;
	}

	/**
	 * Designa métodos ou classes especializadas em extrair variáveis locais ou
	 * globais, protótipos de funções, enum, struct e union
	 * 
	 * @param simpleDeclaration
	 * @param indentationLevel
	 */
	public void extractDeclaration(CASTSimpleDeclaration simpleDeclaration, int indentationLevel) {

		// extrai a declaração específica
		IASTDeclSpecifier declarations = simpleDeclaration.getDeclSpecifier();

		// processa enums
		if (declarations instanceof CASTEnumerationSpecifier) {
			CASTEnumerationSpecifier enumeration = (CASTEnumerationSpecifier) declarations;
			EnumProcessor enumProcessor = new EnumProcessor(enumeration, indentationLevel);

			IASTDeclarator[] declarators = simpleDeclaration.getDeclarators();
			extractDeclarator(declarators);

			enumVXL.append(enumProcessor.getVxlFragment());

			return;
		}

		// processa structs, unions e class
		if (declarations instanceof CASTCompositeTypeSpecifier) {

			CASTCompositeTypeSpecifier compositeType = (CASTCompositeTypeSpecifier) declarations;
			extractCompositeType(compositeType, indentationLevel);

			IASTDeclarator[] declarators = simpleDeclaration.getDeclarators();
			extractDeclarator(declarators);

			return;
		}

		/*
		 * Essa sequencia de ifs, trata objetos diferentes mas que compartilham
		 * do mesmo tipo de vocabulário
		 */
		if (declarations instanceof CASTTypedefNameSpecifier) {
			extractSimpleDeclaration(simpleDeclaration, indentationLevel);

			return;
		}

		if (declarations instanceof CASTSimpleDeclSpecifier) {
			extractSimpleDeclaration(simpleDeclaration, indentationLevel);

			return;
		}

		if (declarations instanceof CASTElaboratedTypeSpecifier) {
			extractSimpleDeclaration(simpleDeclaration, indentationLevel);

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
						extractEqualsInitializer((CASTEqualsInitializer) declarator.getInitializer());
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
	private void extractCompositeType(CASTCompositeTypeSpecifier compositeType, int indentationLevel) {

		switch (compositeType.getKey()) {
		case 1:
			StructProcessor struct = new StructProcessor((CASTCompositeTypeSpecifier) compositeType, vocabularyManager,
					indentationLevel);
			structVXL.append(struct.getVxlFragment());
			break;

		case 2:
			UnionProcessor union = new UnionProcessor((CASTCompositeTypeSpecifier) compositeType, vocabularyManager,
					indentationLevel);
			unionVXL.append(union.getVxlFragment());
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
	private void extractSimpleDeclaration(CASTSimpleDeclaration declaration, int indentationLevel) {
		IASTDeclarator[] simpleDeclarations = declaration.getDeclarators();

		if (simpleDeclarations.length == 0) {
			return;
		}

		for (IASTDeclarator declarator : simpleDeclarations) {

			String storage = storageClass(declarator.getName());
			String access = access(declaration.getDeclSpecifier());
			String name = nameFormatter(declarator.getName().toString());

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
			CASTDeclarator ast_declarator = (CASTDeclarator) declarator;
			if (ast_declarator.getPointerOperators().length > 0) {
				for (IASTPointerOperator pointerOperator : ast_declarator.getPointerOperators()) {

					if (pointerOperator instanceof CASTPointer) {
						CASTPointer pointer = (CASTPointer) declarator.getPointerOperators()[0];

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
			if (declarator instanceof CASTFunctionDeclarator)
				prttpVXL.append(VxlManager.funtionPrototype(name, access, storage, indentationLevel));

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
				extractEqualsInitializer((CASTEqualsInitializer) declarator.getInitializer());
		}
	}

	/**
	 * Processa as inicializações de variáveis na hora da declaração
	 * 
	 * @param initializer
	 */
	public void extractEqualsInitializer(CASTEqualsInitializer initializer) {
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
							if (clause instanceof CASTDesignatedInitializer) {
								extractDesignatedInitializer((CASTDesignatedInitializer) clause);

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
				if (clause instanceof CASTDesignatedInitializer) {
					extractDesignatedInitializer((CASTDesignatedInitializer) clause);

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
	private void extractDesignatedInitializer(CASTDesignatedInitializer designated) {

		if (designated == null)
			return;

		for (ICASTDesignator designator : designated.getDesignators()) {
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
	private void extractDesignators(ICASTDesignator designator) {

		if (designator instanceof CASTArrayDesignator) {
			CASTArrayDesignator arrayDesignator = (CASTArrayDesignator) designator;
			ExpressionProcessor.extractExpression(arrayDesignator.getSubscriptExpression());
		}

		if (designator instanceof CASTFieldDesignator)
			extractFieldDesignator((CASTFieldDesignator) designator);

		if (designator instanceof CASTArrayRangeDesignator) {
			CASTArrayRangeDesignator arrayRangeDesignator = (CASTArrayRangeDesignator) designator;
			extractArrayRangeDesignator(arrayRangeDesignator);
		}

		if (designator instanceof CASTDesignatedInitializer)
			extractDesignatedInitializer((CASTDesignatedInitializer) designator);
	}

	/**
	 * Extrai array designators
	 * 
	 * @param arrayRangeDesignator
	 */
	private void extractArrayRangeDesignator(CASTArrayRangeDesignator arrayRangeDesignator) {

		if (arrayRangeDesignator.getRangeCeiling() instanceof IASTExpression)
			ExpressionProcessor.extractExpression(arrayRangeDesignator.getRangeCeiling());

		else if (arrayRangeDesignator.getRangeFloor() instanceof CASTArrayDesignator)
			extractArrayRangeDesignator((CASTArrayRangeDesignator) arrayRangeDesignator.getRangeCeiling());

		if (arrayRangeDesignator.getRangeFloor() instanceof IASTExpression)
			ExpressionProcessor.extractExpression(arrayRangeDesignator.getRangeFloor());

		else if (arrayRangeDesignator.getRangeFloor() instanceof CASTArrayDesignator)
			extractArrayRangeDesignator((CASTArrayRangeDesignator) arrayRangeDesignator.getRangeFloor());
	}

	/**
	 * Extrai field designators
	 * 
	 * @param fieldDesignator
	 */
	private void extractFieldDesignator(CASTFieldDesignator fieldDesignator) {

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
		IVariable var = new CVariable(variableName);
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

		return allVxlFragment;
	}
}