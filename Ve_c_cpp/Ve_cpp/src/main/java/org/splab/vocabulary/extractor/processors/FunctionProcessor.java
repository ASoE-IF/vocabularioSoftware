package org.splab.vocabulary.extractor.processors;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IVariable;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPFunction;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompoundStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTEqualsInitializer;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPFunction;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPVariable;
import org.splab.vocabulary.extractor.nodelists.DeclarationList;
import org.splab.vocabulary.extractor.processors.vocabulay.manager.FunctionVocabularyManager;
import org.splab.vocabulary.extractor.util.LOCManager;
import org.splab.vocabulary.extractor.util.VxlManager;
import org.splab.vocabulary.extractor.vloccount.EntityLOCKeeper;
import org.splab.vocabulary.extractor.vloccount.EntityType;
import org.splab.vocabulary.extractor.vloccount.LOCCountPerEntity;
import org.splab.vocabulary.extractor.vloccount.LOCParameters;

/**
 * FunctionProcessor é responsavel por receber uma função, processar seu
 * vocabulário e designar o processador de seu vocabulário interno
 * 
 * @author Israel Gomes de Lima
 */
public class FunctionProcessor {
	/**
	 * Cria o fragmento de vxl
	 */
	protected StringBuffer vxlFragment;

	/**
	 * Cria atributos usados para auxiliar a extração
	 */
	protected String name;
	protected String storage;
	protected String access;
	protected BodyProcessor bodyProcessor;
	protected CPPASTCompoundStatement body;

	/**
	 * Cria uma lista de declarações
	 */
	protected List<ASTNode> allDeclarationList;

	/**
	 * Construtor "padrão"
	 */
	public FunctionProcessor() {
		vxlFragment = new StringBuffer();
	}

	/**
	 * Processa a função e seu respectivo vocabulário ao designar os
	 * processadores de vocabulário correto
	 * 
	 * @param functionDefinition
	 * @param entityType
	 * @param indentationLevel
	 */
	public FunctionProcessor(CPPASTFunctionDefinition functionDefinition, EntityType entityType, int indentationLevel) {
		// Vocabulário interno das funções
		FunctionVocabularyManager vocabularyManager = new FunctionVocabularyManager();
		vocabularyManager.insertInHierarchy();

		// Captura o nome, tipo de classe de armazenamento e o acesso da função
		this.name = nameFormatter(functionDefinition.getDeclarator().getName().toString());
		this.storage = storageClass(functionDefinition.getDeclarator());
		this.access = access(functionDefinition.getDeclSpecifier());

		// Converte o tipo de ASTTranslationUnit para ASTNode
		ASTNode typeAST = (ASTNode) functionDefinition;

		// Cria um extrator para as informações do corpo da função
		this.bodyProcessor = new BodyProcessor(vocabularyManager, indentationLevel + 1);

		// Passa o functionVocabulary para acesso na expression processor
		ExpressionProcessor.setVocabularyManager(vocabularyManager);
		this.body = (CPPASTCompoundStatement) functionDefinition.getBody();

		if (this.body == null) {
			vxlFragment = new StringBuffer(VxlManager.funtionPrototype(name, access, storage, indentationLevel));
			return;
		}

		// Cria uma lista con todas as declarações internas a presente entidade
		allDeclarationList = DeclarationList.getTypes(this.body.getStatements());

		// Contagem de loc por entidade
		LOCCountPerEntity locCounter = new LOCCountPerEntity(typeAST, CompilationUnitProcessor.commentList,
				CompilationUnitProcessor.sourceCode);

		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter);
		locKeeper.setHeadersLOC(0, true, LOCManager.locParameters.contains(LOCParameters.HEADERS));

		vxlFragment = new StringBuffer(VxlManager.startFuntion(this.name, this.access, this.storage,
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

		if ((LOCManager.locParameters.contains(LOCParameters.LOC)) && (entityType == EntityType.FUNCTION)) {
			LOCManager.appendEntityLOCData(name, locKeeper, EntityType.FUNCTION);
		}

		vxlFragment.append(VxlManager.endFuntion(indentationLevel));

		vocabularyManager.removeFromHierarchy();
	}

	/**
	 * Extrai o vocabulário interno da função
	 * 
	 * @param functionDefinition
	 * @param vocabularyManager
	 * @param entityType
	 * @param indentationLevel
	 */
	protected void extractFunction(CPPASTFunctionDefinition functionDefinition,
			FunctionVocabularyManager vocabularyManager, EntityType entityType, int indentationLevel) {

		// Extrai as informações dos parâmetros
		if (functionDefinition.getDeclarator() instanceof CPPASTFunctionDeclarator) {

			CPPASTFunctionDeclarator functionDeclarator = (CPPASTFunctionDeclarator) functionDefinition.getDeclarator();
			IASTParameterDeclaration[] parameters = functionDeclarator.getParameters();
			DeclarationProcessor paramProcess = new DeclarationProcessor(vocabularyManager);
			for (IASTParameterDeclaration parameter : parameters) {
				if (!parameter.getDeclarator().getName().toString().equals("")) {

					if (parameter.getDeclarator().getInitializer() != null)
						paramProcess.extractEqualsInitializer(
								(CPPASTEqualsInitializer) parameter.getDeclarator().getInitializer());

					String parametroStorage = storageClass(parameter.getDeclarator().getName());
					String parametroAccess = access(parameter.getDeclSpecifier());
					String parametroName = parameter.getDeclarator().getName().toString();

					VxlManager.parameter(vxlFragment, parametroName, (indentationLevel));
					vocabularyManager.insertParameter(parametroName, parametroAccess, parametroStorage);
				}
			}
		}
		if (LOCManager.locParameters.contains(LOCParameters.INTERN_VOCABULARY)) {
			if (functionDefinition.getBody() instanceof CPPASTCompoundStatement) {
				IASTStatement[] statements = body.getStatements();

				for (IASTStatement statement : statements) {
					bodyProcessor.extractBody(statement);
				}
			}
		}
	}

	/**
	 * Processa a classe de armazenamento da função
	 * 
	 * @param declaration
	 * @return
	 */
	protected String storageClass(IASTFunctionDeclarator declaration) {
		ICPPFunction function = (ICPPFunction) new CPPFunction(declaration);
		String storage;

		if (function.isAuto())
			storage = "auto";
		else if (function.isStatic())
			storage = "static";
		else if (function.isRegister())
			storage = "register";
		else if (function.isExtern())
			storage = "extern";
		else if (function.isMutable())
			storage = "mutable";
		else
			storage = "auto";

		return storage;
	}

	/**
	 * Processa a classe de armazenamento de uma declaração qualquer
	 * 
	 * @param variableName
	 * @return
	 */
	private String storageClass(IASTName variableName) {
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
	 * Processo o tipo de acesso da função
	 * 
	 * @param decl
	 * @return
	 */
	protected String access(IASTDeclSpecifier decl) {

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

	/**
	 * Adiciona o vocabulário de variáveis locais da função ao fragmento de vxl
	 * 
	 * @param localVariables
	 * @param localVariablesAccess
	 * @param localVariablesStorage
	 * @param indentationLevel
	 */
	public void storeInternVocabularyLocalVar(Map<String, Integer> localVariables,
			Map<String, String> localVariablesAccess, Map<String, String> localVariablesStorage, int indentationLevel) {

		Set<String> lvar = localVariables.keySet();
		Iterator<String> it_lvar = lvar.iterator();

		while (it_lvar.hasNext()) {
			String identifier = it_lvar.next();
			if (localVariables.get(identifier) > 0) {
				String access = localVariablesAccess.get(identifier);
				String storage = localVariablesStorage.get(identifier);
				int count = localVariables.get(identifier);

				vxlFragment.append(VxlManager.localVariable(identifier, access, storage, count, indentationLevel));
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

			vxlFragment.append(VxlManager.globalVariable(identifier, access, storage, count, indentationLevel));
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
			vxlFragment.append(VxlManager.functionCall(identifier, functionCall.get(identifier), indentationLevel));
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
			vxlFragment.append(VxlManager.literal(identifier, literals.get(identifier), indentationLevel));
		}
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
	public void storeInternVocabularyField(Map<String, Integer> fields, Map<String, String> fieldAccess,
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

				vxlFragment.append(VxlManager.field(identifier, access, storage, visib, count, indentationLevel));
			}
		}
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
	 * Retorn o fragmento de vxl
	 * 
	 * @return
	 */
	public StringBuffer getVxlFragment() {
		return vxlFragment;
	}
}
