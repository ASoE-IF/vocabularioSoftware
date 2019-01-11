package org.splab.vocabulary.extractor.processors;

import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPMethod;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompoundStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTemplateDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPMethod;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPMethodTemplate;
import org.splab.vocabulary.extractor.nodelists.DeclarationList;
import org.splab.vocabulary.extractor.processors.vocabulay.manager.FunctionVocabularyManager;
import org.splab.vocabulary.extractor.util.LOCManager;
import org.splab.vocabulary.extractor.util.VxlManager;
import org.splab.vocabulary.extractor.vloccount.EntityLOCKeeper;
import org.splab.vocabulary.extractor.vloccount.EntityType;
import org.splab.vocabulary.extractor.vloccount.LOCCountPerEntity;
import org.splab.vocabulary.extractor.vloccount.LOCParameters;

/**
 * MethodProcessor é responsavel por receber um método, processar seu
 * vocabulário e designar o processador de seu vocabulário interno
 * 
 * @author Israel Gomes de Lima
 */
public class MethodProcessor extends FunctionProcessor {
	/**
	 * Define o modificador e a visibilidade do método
	 */
	private String modificador;
	private String visibilidade;

	/**
	 * Construtor "padrão"
	 */
	public MethodProcessor() {
		vxlFragment = new StringBuffer();
	}

	/**
	 * Construtor responsável por criar um processador de método comúm
	 * 
	 * @param method
	 * @param entityType
	 * @param indentationLevel
	 */
	public MethodProcessor(CPPMethod method, EntityType entityType, int indentationLevel) {
		// Obtem declaração de função do metodo
		CPPASTFunctionDefinition functionDefinition = (CPPASTFunctionDefinition) method.getPrimaryDeclaration();

		// Vocabulário interno das funções
		FunctionVocabularyManager vocabularyManager = new FunctionVocabularyManager();
		vocabularyManager.insertInHierarchy();

		// Captura o nome, tipo de classe de armazenamento e o acesso do método
		this.name = functionDefinition.getDeclarator().getName().toString();
		this.storage = storageClass(method);
		this.access = access(functionDefinition.getDeclSpecifier());
		this.modificador = modifies(((CPPMethod) method).getDefinition());
		this.visibilidade = visibility(method.getVisibility());
		String isVirtual = method.isVirtual() ? "y" : "n";

		if (method.isDeleted())
			modificador = "delete";

		// Converte o tipo de ASTTranslationUnit para ASTNode
		ASTNode typeAST = (ASTNode) functionDefinition;

		// Cria um extrator para as informações do corpo da função
		this.bodyProcessor = new BodyProcessor(vocabularyManager, indentationLevel + 1);

		// Passa o functionVocabulary para acesso na expression processor
		ExpressionProcessor.setVocabularyManager(vocabularyManager);
		this.body = (CPPASTCompoundStatement) functionDefinition.getBody();

		if (this.body == null) {
			vxlFragment = new StringBuffer(VxlManager.methodPrototype(StringProcessor.processString(name), access,
					storage, modificador, visibilidade, "n", indentationLevel));
			return;
		}
		this.allDeclarationList = DeclarationList.getTypes(this.body.getStatements());

		LOCCountPerEntity locCounter = new LOCCountPerEntity(typeAST, CompilationUnitProcessor.commentList,
				CompilationUnitProcessor.sourceCode);

		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter);
		locKeeper.setHeadersLOC(0, true, LOCManager.locParameters.contains(LOCParameters.HEADERS));

		vxlFragment = new StringBuffer(VxlManager.startMethod(StringProcessor.processString(this.name), this.access,
				this.storage, this.modificador, this.visibilidade, isVirtual,
				locKeeper.getLOC() + locKeeper.getInnerEntitiesLOC(), indentationLevel));
		vxlFragment.append(
				new CommentsProcessor(this.allDeclarationList, (ASTNode) functionDefinition, indentationLevel + 1)
						.getVxlFragment());

		extractFunction(functionDefinition, vocabularyManager, entityType, indentationLevel + 1);

		if (LOCManager.locParameters.contains(LOCParameters.INTERN_VOCABULARY)) {
			// Processa o vocabulário de fields
			storeInternVocabularyField(vocabularyManager.getField(), vocabularyManager.getFieldAccess(),
					vocabularyManager.getFieldStorage(), vocabularyManager.getFieldVisibility(), indentationLevel + 1);
			// Processa o vocabulário das variáveis globais
			storeInternVocabularyGlobalVar(vocabularyManager.getGlobalVar(), vocabularyManager.getGlobalVarAccess(),
					vocabularyManager.getGlobalVarStorage(), indentationLevel + 1);
			// Processa o vocabulário das variáveis locais
			storeInternVocabularyLocalVar(vocabularyManager.getLocalVar(), vocabularyManager.getLocalVarAccess(),
					vocabularyManager.getLocalVarStorage(), indentationLevel + 1);
			// Processa o vocabulário das chamadas de funções
			storeInternVocabularyFunctionCall(vocabularyManager.getFunctionCall(), indentationLevel + 1);
			// Processa o vocabulário das Strings Literais
			storeInternVocabularyLiteral(vocabularyManager.getLiterals(), indentationLevel + 1);

			vxlFragment.append(bodyProcessor.getVxlFragment());
		}

		if ((LOCManager.locParameters.contains(LOCParameters.LOC)) && (entityType == EntityType.METHOD)) {
			LOCManager.appendEntityLOCData(name, locKeeper, EntityType.METHOD);
		}

		vxlFragment.append(VxlManager.endMethod(indentationLevel));
		vocabularyManager.removeFromHierarchy();
	}

	/**
	 * Construtor responsável por criar um processador de método para extrair
	 * métodos de templates
	 * 
	 * @param method
	 * @param entityType
	 * @param indentationLevel
	 */
	public MethodProcessor(CPPMethodTemplate method, EntityType entityType, int indentationLevel) {
		// Obtem declaração de função do metodo e de metodo
		CPPASTTemplateDeclaration templateDeclaration = (CPPASTTemplateDeclaration) method.getPrimaryDeclaration();
		CPPASTFunctionDefinition functionDefinition = (CPPASTFunctionDefinition) templateDeclaration.getDeclaration();

		// Vocabulário interno das funções
		FunctionVocabularyManager vocabularyManager = new FunctionVocabularyManager();
		vocabularyManager.insertInHierarchy();

		// Captura o nome, tipo de classe de armazenamento e o acesso do método
		this.name = functionDefinition.getDeclarator().getName().toString();
		this.storage = storageClass(method);
		this.access = access(functionDefinition.getDeclSpecifier());
		this.modificador = modifies((ICPPASTFunctionDeclarator) functionDefinition.getDeclarator());
		this.visibilidade = visibility(method.getVisibility());
		String isVirtual = method.isVirtual() ? "y" : "n";
		if (method.isDeleted())
			modificador = "delete";

		// Converte o tipo de ASTTranslationUnit para ASTNode
		ASTNode typeAST = (ASTNode) functionDefinition;

		// Cria um extrator para as informações do corpo da função
		this.bodyProcessor = new BodyProcessor(vocabularyManager, indentationLevel + 1);

		// Passa o functionVocabulary para acesso na expression processor
		ExpressionProcessor.setVocabularyManager(vocabularyManager);
		this.body = (CPPASTCompoundStatement) functionDefinition.getBody();

		if (this.body == null) {
			vxlFragment = new StringBuffer(VxlManager.methodPrototype(StringProcessor.processString(name), access,
					storage, modificador, visibilidade, "n", indentationLevel));
			return;
		}

		// Cria uma lista con todas as declarações do código
		this.allDeclarationList = DeclarationList.getTypes(this.body.getStatements());

		LOCCountPerEntity locCounter = new LOCCountPerEntity(typeAST, CompilationUnitProcessor.commentList,
				CompilationUnitProcessor.sourceCode);

		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter);
		locKeeper.setHeadersLOC(0, true, LOCManager.locParameters.contains(LOCParameters.HEADERS));

		vxlFragment = new StringBuffer(VxlManager.startMethod(StringProcessor.processString(this.name), this.access,
				this.storage, this.modificador, this.visibilidade, isVirtual,
				locKeeper.getLOC() + locKeeper.getInnerEntitiesLOC(), indentationLevel));
		vxlFragment.append(
				new CommentsProcessor(this.allDeclarationList, (ASTNode) functionDefinition, indentationLevel + 1)
						.getVxlFragment());

		extractFunction(functionDefinition, vocabularyManager, entityType, indentationLevel + 1);

		if (LOCManager.locParameters.contains(LOCParameters.INTERN_VOCABULARY)) {
			// Processa o vocabulário de fields
			storeInternVocabularyField(vocabularyManager.getField(), vocabularyManager.getFieldAccess(),
					vocabularyManager.getFieldStorage(), vocabularyManager.getFieldVisibility(), indentationLevel + 1);
			// Processa o vocabulário das variáveis globais
			storeInternVocabularyGlobalVar(vocabularyManager.getGlobalVar(), vocabularyManager.getGlobalVarAccess(),
					vocabularyManager.getGlobalVarStorage(), indentationLevel + 1);
			// Processa o vocabulário das variáveis locais
			storeInternVocabularyLocalVar(vocabularyManager.getLocalVar(), vocabularyManager.getLocalVarAccess(),
					vocabularyManager.getLocalVarStorage(), indentationLevel + 1);
			// Processa o vocabulário das chamadas de funções
			storeInternVocabularyFunctionCall(vocabularyManager.getFunctionCall(), indentationLevel + 1);
			// Processa o vocabulário das Strings Literais
			storeInternVocabularyLiteral(vocabularyManager.getLiterals(), indentationLevel + 1);
		}

		vxlFragment.append(bodyProcessor.getVxlFragment());
		if ((LOCManager.locParameters.contains(LOCParameters.LOC)) && (entityType == EntityType.METHOD)) {
			LOCManager.appendEntityLOCData(this.name, locKeeper, EntityType.METHOD);
		}

		vxlFragment.append(VxlManager.endMethod(indentationLevel));
		vocabularyManager.removeFromHierarchy();
	}

	/**
	 * Construtor para processar protótipos de métodos comuns
	 * 
	 * @param method
	 * @param indentationLevel
	 */
	public MethodProcessor(CPPMethod method, int indentationLevel) {
		// Obtem declaração de função do metodo
		CPPASTSimpleDeclaration simpleDeclaration = (CPPASTSimpleDeclaration) method.getPrimaryDeclaration();
		if (simpleDeclaration.getDeclarators().length <= 0)
			return;

		CPPASTDeclarator declarator = (CPPASTDeclarator) simpleDeclaration.getDeclarators()[0];

		// Captura o nome, tipo de classe de armazenamento e o acesso do método
		this.name = method.getName();
		this.storage = storageClass(method);
		this.access = "";
		this.modificador = "";
		if (simpleDeclaration.getDeclSpecifier() instanceof CPPASTFunctionDeclarator) {
			this.access = access(simpleDeclaration.getDeclSpecifier());
		}
		if (declarator instanceof CPPASTFunctionDeclarator) {
			this.modificador = modifies((CPPASTFunctionDeclarator) declarator);
		}

		this.visibilidade = visibility(method.getVisibility());
		if (method.isDeleted())
			modificador = "delete";

		if (method.isPureVirtual())
			vxlFragment = new StringBuffer(VxlManager.methodPureVirtual(StringProcessor.processString(name), access,
					storage, modificador, visibilidade, indentationLevel));
		else if (method.isVirtual())
			vxlFragment = new StringBuffer(VxlManager.methodPrototype(StringProcessor.processString(name), access,
					storage, modificador, visibilidade, "y", indentationLevel));
		else
			vxlFragment = new StringBuffer(VxlManager.methodPrototype(StringProcessor.processString(name), access,
					storage, modificador, visibilidade, "n", indentationLevel));
	}

	/**
	 * Construtor para processar protótipos de métodos de template
	 * 
	 * @param method
	 * @param indentationLevel
	 */
	public MethodProcessor(CPPMethodTemplate method, int indentationLevel) {
		// Obtem declaração de função do metodo
		CPPASTSimpleDeclaration simpleDeclaration = (CPPASTSimpleDeclaration) method.getPrimaryDeclaration();
		if (simpleDeclaration.getDeclarators().length <= 0)
			return;

		CPPASTDeclarator declarator = (CPPASTDeclarator) simpleDeclaration.getDeclarators()[0];

		// Captura o nome, tipo de classe de armazenamento e o acesso do método
		this.name = method.getName();
		this.storage = storageClass(method);
		this.access = access(simpleDeclaration.getDeclSpecifier());
		this.modificador = modifies((CPPASTFunctionDeclarator) declarator);
		this.visibilidade = visibility(method.getVisibility());
		if (method.isDeleted())
			modificador = "delete";

		if (method.isPureVirtual())
			vxlFragment = new StringBuffer(VxlManager.methodPureVirtual(StringProcessor.processString(name), access,
					storage, modificador, visibilidade, indentationLevel));
		else if (method.isVirtual())
			vxlFragment = new StringBuffer(VxlManager.methodPrototype(StringProcessor.processString(name), access,
					storage, modificador, visibilidade, "y", indentationLevel));
		else
			vxlFragment = new StringBuffer(VxlManager.methodPrototype(StringProcessor.processString(name), access,
					storage, modificador, visibilidade, "n", indentationLevel));
	}

	/**
	 * Extrai a visibilidade do método
	 * 
	 * @param visibilityKey
	 * @return
	 */
	public static String visibility(int visibilityKey) {
		String visibilityField;

		switch (visibilityKey) {
		case 1:
			visibilityField = "pub";
			break;
		case 2:
			visibilityField = "prot";
			break;
		case 3:
			visibilityField = "priv";
			break;
		default:
			visibilityField = "pub";
			break;
		}

		return visibilityField;
	}

	/**
	 * Extrai a classe de armazenamento do método
	 * 
	 * @param method
	 * @return
	 */
	private String storageClass(ICPPMethod method) {
		String storage;

		if (method.isAuto())
			storage = "auto";
		else if (method.isStatic())
			storage = "static";
		else if (method.isRegister())
			storage = "register";
		else if (method.isExtern())
			storage = "extern";
		else if (method.isMutable())
			storage = "mutable";
		else
			storage = "auto";

		return storage;

	}

	/**
	 * Extrai o modificador do método
	 * 
	 * @param functionDeclarator
	 * @return
	 */
	private String modifies(ICPPASTFunctionDeclarator functionDeclarator) {

		String modificador = "";

		if (functionDeclarator.isConst())
			modificador = "const";
		else if (functionDeclarator.isFinal())
			modificador = "final";

		return modificador;
	}
}
