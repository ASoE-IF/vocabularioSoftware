package org.splab.vocabulary.extractor.processors;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IBinding;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTemplateDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPClassType;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPField;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPMethod;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTemplateDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPFunction;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPMethod;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPMethodTemplate;
import org.splab.vocabulary.extractor.processors.vocabulay.manager.ClassVocabularyManager;
import org.splab.vocabulary.extractor.util.LOCManager;
import org.splab.vocabulary.extractor.util.VxlManager;
import org.splab.vocabulary.extractor.vloccount.EntityLOCKeeper;
import org.splab.vocabulary.extractor.vloccount.EntityType;
import org.splab.vocabulary.extractor.vloccount.LOCCountPerEntity;
import org.splab.vocabulary.extractor.vloccount.LOCParameters;

/**
 * ClassProcessor é responsavel por receber uma classe, processar seu
 * vocabulário e designar o processador de seu vocabulário interno
 * 
 * @author Israel Gomes de Lima
 */
public class ClassProcessor {
	/**
	 * Cria fragmentos de vxl para guardar vocabulários específicos
	 */
	private StringBuffer allVxlFragment;
	private StringBuffer methodVxlFragment;
	private StringBuffer functionVxlFragment;

	/**
	 * Cria um mapa de atributos, uma lista de funções friend, uma lista
	 * contendo todas as declarações e um vocabulary manager
	 */
	private HashMap<String, ICPPField> fieldList;
	private List<CPPASTFunctionDeclarator> friendDeclarationList;
	private List<ASTNode> allDeclarationList;
	private ClassVocabularyManager vocabularyManager;

	/**
	 * Construtor "padrão"
	 */
	public ClassProcessor() {
		allVxlFragment = new StringBuffer();
	}

	/**
	 * Construtor responsável por criar um processador de classes
	 * 
	 * @param compositeType
	 * @param isInner
	 * @param entityType
	 * @param indentationLevel
	 */
	public ClassProcessor(CPPASTCompositeTypeSpecifier compositeType, boolean isInner, EntityType entityType,
			int indentationLevel) {
		// Inicializa o class vocabulary manager
		this.vocabularyManager = new ClassVocabularyManager();
		this.vocabularyManager.insertInHierarchy();
		ExpressionProcessor.setVocabularyManager(vocabularyManager);

		// Inicializa os fragmentos de VXL
		this.allVxlFragment = new StringBuffer();
		this.methodVxlFragment = new StringBuffer();
		this.functionVxlFragment = new StringBuffer();

		// Recupera o objeto principal da classe
		ICPPClassType classScope = compositeType.getScope().getClassType();

		// Cria lista de ASTNode e membros
		this.allDeclarationList = new LinkedList<ASTNode>();
		IASTDeclaration[] members = compositeType.getMembers();

		// Cria lista de fields para auxiliar na extração
		this.fieldList = new HashMap<String, ICPPField>();
		for (ICPPField field : classScope.getDeclaredFields()) {
			this.fieldList.put(field.getName(), field);
		}

		// Cria lista de métodos
		ICPPMethod[] methods = classScope.getDeclaredMethods();

		/*
		 * Cria lista de funções friends. Obs.: Funções friends não são metodos.
		 */
		friendDeclarationList = new LinkedList<CPPASTFunctionDeclarator>();
		for (IBinding friend : classScope.getFriends()) {

			CPPFunction function = (CPPFunction) friend;
			if (function.getDeclarations() != null && function.getDeclarations().length > 0) {
				for (IASTDeclarator decl : function.getDeclarations()) {
					friendDeclarationList.add((CPPASTFunctionDeclarator) decl);
				}
			} else {
				if (function.getDefinition() != null)
					friendDeclarationList.add((CPPASTFunctionDeclarator) function.getDefinition());
			}
		}

		DeclarationProcessor declProcessor = new DeclarationProcessor(vocabularyManager);
		for (IASTDeclaration declaration : members) {

			if (declaration instanceof ICPPASTFunctionDefinition) {

				CPPASTFunctionDefinition functionDefinition = (CPPASTFunctionDefinition) declaration;
				if (friendDeclarationList.contains(functionDefinition.getDeclarator())) {
					FunctionProcessor functionProcessor = new FunctionProcessor(functionDefinition, EntityType.FUNCTION,
							indentationLevel + 1);
					functionVxlFragment.append(functionProcessor.getVxlFragment());
				}
				allDeclarationList.add((ASTNode) functionDefinition);
			}

			if (declaration instanceof IASTSimpleDeclaration) {
				ExpressionProcessor.setVocabularyManager(vocabularyManager);

				CPPASTSimpleDeclaration simpleDeclaration = (CPPASTSimpleDeclaration) declaration;
				declProcessor.extractDeclaration(simpleDeclaration, this, indentationLevel + 1);
			}

			if (declaration instanceof ICPPASTTemplateDeclaration) {

				CPPASTTemplateDeclaration templateDeclaration = (CPPASTTemplateDeclaration) declaration;

				if (templateDeclaration.getDeclaration() instanceof IASTFunctionDefinition) {
					CPPASTFunctionDefinition functionDefinition = (CPPASTFunctionDefinition) templateDeclaration
							.getDeclaration();
					if (friendDeclarationList.contains(functionDefinition.getDeclarator())) {
						FunctionProcessor functionProcessor = new FunctionProcessor(functionDefinition,
								EntityType.FUNCTION, indentationLevel + 1);
						functionVxlFragment.append(functionProcessor.getVxlFragment());
					}
					allDeclarationList.add((ASTNode) functionDefinition);
				}

				// Processa variáveis, struct, union, enums, protótipos e class
				if (templateDeclaration.getDeclaration() instanceof IASTSimpleDeclaration) {
					ExpressionProcessor.setVocabularyManager(vocabularyManager);

					CPPASTSimpleDeclaration simpleDeclaration = (CPPASTSimpleDeclaration) templateDeclaration
							.getDeclaration();
					declProcessor.extractDeclaration(simpleDeclaration, this, indentationLevel + 1);
				}
			}
		}

		// Extrai os methodos
		for (ICPPMethod method : methods) {
			if (method instanceof CPPMethodTemplate) {
				extractTemplateMethods((CPPMethodTemplate) method, indentationLevel + 1);
			} else {
				extractMethods((CPPMethod) method, indentationLevel + 1);
			}
		}

		String name = classScope.getName();
		String isInnerType = (isInner) ? "y" : "n";
		String isAbstract = isAbstract(methods) ? "y" : "n";

		// Efetua contagem de loc por entidade
		ASTNode classNode = (ASTNode) compositeType;
		LOCCountPerEntity locCounter = new LOCCountPerEntity(classNode, CompilationUnitProcessor.commentList,
				CompilationUnitProcessor.sourceCode);

		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter);
		locKeeper.setHeadersLOC(0, true, LOCManager.locParameters.contains(LOCParameters.HEADERS));

		allVxlFragment.append(VxlManager.startClass(name, isAbstract, isInnerType,
				locKeeper.getLOC() + locKeeper.getInnerEntitiesLOC(), indentationLevel));
		allVxlFragment.append(new CommentsProcessor(allDeclarationList, (ASTNode) compositeType, indentationLevel + 1));

		if ((LOCManager.locParameters.contains(LOCParameters.LOC)) && (entityType == EntityType.CLASS)) {
			LOCManager.appendEntityLOCData(name, locKeeper, EntityType.CLASS);
		}

		if ((LOCManager.locParameters.contains(LOCParameters.LOC)) && (entityType == EntityType.INNER_CLASS)
				&& (isInner ? LOCManager.locParameters.contains(LOCParameters.INNER_CLASS) : true)) {
			LOCManager.appendEntityLOCData(name, locKeeper, EntityType.INNER_CLASS);
		}

		// Processa o vocabulário dos fields
		storeInternVocabularyField(vocabularyManager.getField(), vocabularyManager.getFieldAccess(),
				vocabularyManager.getFieldStorage(), vocabularyManager.getFieldVisibility(), indentationLevel + 1);
		// Processa o vocabulário das variáveis globais
		storeInternVocabularyGlobalVar(vocabularyManager.getGlobalVar(), vocabularyManager.getGlobalVarAccess(),
				vocabularyManager.getGlobalVarStorage(), indentationLevel + 1);
		// Processa o vocabulário das chamadas de funções
		storeInternVocabularyFunctionCall(vocabularyManager.getFunctionCall(), indentationLevel + 1);
		// Processa o vocabulário das Strings Literais
		storeInternVocabularyLiteral(vocabularyManager.getLiterals(), indentationLevel + 1);

		// Uni os fragmentos de VXL
		allVxlFragment.append(declProcessor.getVxlFragment());

		allVxlFragment.append(methodVxlFragment);
		allVxlFragment.append(functionVxlFragment);
		allVxlFragment.append(VxlManager.endClass(indentationLevel));

		vocabularyManager.removeFromHierarchy();
	}

	/**
	 * Recebe o vocabulário de atriutos e os insere no fragmento de vxl
	 * 
	 * @param fields
	 * @param fieldAccess
	 * @param fieldStorage
	 * @param visibility
	 * @param indentationLevel
	 */
	private void storeInternVocabularyField(Map<String, Integer> fields, Map<String, String> fieldAccess,
			Map<String, String> fieldStorage, Map<String, String> visibility, int indentationLevel) {

		Set<String> fieldSet = fields.keySet();
		Iterator<String> it_fields = fieldSet.iterator();

		while (it_fields.hasNext()) {
			String identifier = it_fields.next();
			if (fields.get(identifier) > 0) {
				String access = fieldAccess.get(identifier);
				String storage = fieldStorage.get(identifier);
				int count = fields.get(identifier);
				String visib = visibility.get(identifier);

				allVxlFragment.append(VxlManager.field(identifier, access, storage, visib, count, indentationLevel));
			}
		}
	}

	/**
	 * Adiciona o vocabulário de variáveis globais ao fragmento de vxl
	 * 
	 * @param globalVariables
	 * @param globalVariablesAccess
	 * @param globalVariablesStorage
	 * @param indentationLevel
	 */
	public void storeInternVocabularyGlobalVar(Map<String, Integer> globalVariables,
			Map<String, String> globalVariablesAccess, Map<String, String> globalVariablesStorage,
			int indentationLevel) {

		Set<String> gvar = globalVariables.keySet();
		Iterator<String> it_gvar = gvar.iterator();

		while (it_gvar.hasNext()) {
			String identifier = it_gvar.next();

			String access = globalVariablesAccess.get(identifier);
			String storage = globalVariablesStorage.get(identifier);
			int count = globalVariables.get(identifier);

			allVxlFragment.append(VxlManager.globalVariable(identifier, access, storage, count, indentationLevel));
		}
	}

	/**
	 * Insere o vocaulario de chamadas a funções no fragmento de vxl
	 * 
	 * @param functionCall
	 * @param indentationLevel
	 */
	public void storeInternVocabularyFunctionCall(Map<String, Integer> functionCall, int indentationLevel) {

		Set<String> funcCall = functionCall.keySet();
		Iterator<String> it_funcCall = funcCall.iterator();
		while (it_funcCall.hasNext()) {
			String identifier = it_funcCall.next();
			allVxlFragment.append(VxlManager.functionCall(identifier, functionCall.get(identifier), indentationLevel));
		}
	}

	/**
	 * Insere no fragmento de vxl o vocabulário de strings literais
	 * 
	 * @param literals
	 * @param indentationLevel
	 */
	public void storeInternVocabularyLiteral(Map<String, Integer> literals, int indentationLevel) {

		Set<String> lit = literals.keySet();
		Iterator<String> it_lit = lit.iterator();
		while (it_lit.hasNext()) {
			String identifier = it_lit.next();
			allVxlFragment.append(VxlManager.literal(identifier, literals.get(identifier), indentationLevel));
		}
	}

	/**
	 * Identifica e solicita a extração do vocabulário de um método ou de um
	 * protótipo de método comum
	 * 
	 * @param method
	 * @param indentationLevel
	 */
	private void extractMethods(CPPMethod method, int indentationLevel) {
		if (method.getPrimaryDeclaration() instanceof IASTFunctionDefinition) {
			MethodProcessor methodProcessor = new MethodProcessor(method, EntityType.METHOD, indentationLevel);
			methodVxlFragment.append(methodProcessor.getVxlFragment());
		} else {
			if (method.getPrimaryDeclaration() instanceof CPPASTSimpleDeclaration) {
				MethodProcessor methodProcessor = new MethodProcessor(method, indentationLevel);
				methodVxlFragment.append(methodProcessor.getVxlFragment());
			}
		}
	}

	/**
	 * Identifica e solicita a extração do vocabulário de um método ou de um
	 * protótipo de método de template
	 * 
	 * @param method
	 * @param indentationLevel
	 */
	private void extractTemplateMethods(CPPMethodTemplate method, int indentationLevel) {

		CPPASTTemplateDeclaration templateDeclaration = (CPPASTTemplateDeclaration) method.getPrimaryDeclaration();
		if (templateDeclaration.getDeclaration() instanceof IASTFunctionDefinition) {
			MethodProcessor methodProcessor = new MethodProcessor(method, EntityType.METHOD, indentationLevel);
			methodVxlFragment.append(methodProcessor.getVxlFragment());
		} else {
			if (templateDeclaration.getDeclaration() instanceof CPPASTSimpleDeclaration) {
				MethodProcessor methodProcessor = new MethodProcessor(method, indentationLevel);
				methodVxlFragment.append(methodProcessor.getVxlFragment());
			}
		}
	}

	/**
	 * Testa se a classe é abstrata Obs.: Para que uma classe seja abstrata,
	 * basta que ela contenha pelo menos um método virtual puro (PureVirtual). A
	 * sintaxe do método virtual puro é: virtual void methodPureVirtual() = 0;
	 * 
	 * @param methods
	 * @return
	 */
	private boolean isAbstract(ICPPMethod[] methods) {

		for (ICPPMethod method : methods) {
			if (method.isPureVirtual()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Retorna uma lista contendo os atributos da classe
	 * 
	 * @return
	 */
	public HashMap<String, ICPPField> getFieldList() {
		return fieldList;
	}

	/**
	 * Retorna uma lista contendo todas as declarações friend da classe
	 * 
	 * @return
	 */
	public List<CPPASTFunctionDeclarator> getFriendDeclarationList() {
		return friendDeclarationList;
	}

	/**
	 * Retorna o vocabulary manager da classe
	 * 
	 * @return
	 */
	public ClassVocabularyManager getVocabularyManager() {
		return vocabularyManager;
	}

	/**
	 * Retorn o fragmento de vxl
	 * 
	 * @return
	 */
	public StringBuffer getVxlFragment() {
		return allVxlFragment;
	}
}
