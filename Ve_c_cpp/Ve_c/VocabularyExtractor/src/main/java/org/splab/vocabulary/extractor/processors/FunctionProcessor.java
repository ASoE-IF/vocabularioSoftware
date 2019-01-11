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
import org.eclipse.cdt.core.dom.ast.IFunction;
import org.eclipse.cdt.core.dom.ast.IVariable;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompoundStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTEqualsInitializer;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.c.CFunction;
import org.eclipse.cdt.internal.core.dom.parser.c.CVariable;
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
	 * Cria uma lista de declarações
	 */
	protected List<ASTNode> allDeclarationList;

	/**
	 * Construtor "padrão"
	 */
	public FunctionProcessor() {
		vxlFragment = new StringBuffer();
	}

	public FunctionProcessor(CASTFunctionDefinition functionDefinition, boolean isInner, EntityType entityType,
			int indentationLevel) {
		// Vocabulário interno das funções
		FunctionVocabularyManager vocabularyManager = new FunctionVocabularyManager();

		// Captura o nome, tipo de classe de armazenamento e o acesso da função
		String name = functionDefinition.getDeclarator().getName().toString();
		String storage = storageClass(functionDefinition.getDeclarator());
		String acesso = access(functionDefinition.getDeclSpecifier());

		// Converte o tipo de ASTTranslationUnit para ASTNode
		ASTNode typeAST = (ASTNode) functionDefinition;

		// Cria um extrator para as informações do corpo da função
		BodyProcessor bodyProcessor = new BodyProcessor(vocabularyManager, indentationLevel + 1);

		// Passa o functionVocabulary para acesso na expression processor
		ExpressionProcessor.setVocabularyManager(vocabularyManager);
		CASTCompoundStatement body = (CASTCompoundStatement) functionDefinition.getBody();

		// Cria uma lista con todas as declarações internas a presente entidade
		allDeclarationList = DeclarationList.getTypes(body.getStatements());

		LOCCountPerEntity locCounter = new LOCCountPerEntity(typeAST, CompilationUnitProcessor.commentList,
				CompilationUnitProcessor.sourceCode);

		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter);
		locKeeper.setHeadersLOC(0, true, LOCManager.locParameters.contains(LOCParameters.HEADERS));

		vxlFragment = new StringBuffer(VxlManager.startFunction(StringProcessor.processString(name), acesso, storage,
				isInner, locKeeper.getLOC() + locKeeper.getInnerEntitiesLOC(), indentationLevel));
		vxlFragment.append(
				new CommentsProcessor(this.allDeclarationList, (ASTNode) functionDefinition, indentationLevel + 1)
						.getVxlFragment());

		// Extrai as informações dos parâmetros
		if (functionDefinition.getDeclarator() instanceof CASTFunctionDeclarator) {
			CASTFunctionDeclarator functionDeclarator = (CASTFunctionDeclarator) functionDefinition.getDeclarator();
			IASTParameterDeclaration[] parameters = functionDeclarator.getParameters();

			DeclarationProcessor paramProcess = new DeclarationProcessor(vocabularyManager);
			for (IASTParameterDeclaration parameter : parameters) {
				if (!parameter.getDeclarator().getName().toString().equals("")) {

					if (parameter.getDeclarator().getInitializer() != null)
						paramProcess.extractEqualsInitializer(
								(CASTEqualsInitializer) parameter.getDeclarator().getInitializer());

					String parametroStorage = storageClass(parameter.getDeclarator().getName());
					String parametroAccess = access(parameter.getDeclSpecifier());
					String parametroName = parameter.getDeclarator().getName().toString();

					VxlManager.parameter(vxlFragment, StringProcessor.processString(parametroName),
							(indentationLevel + 1));
					vocabularyManager.insertParameter(parametroName, parametroAccess, parametroStorage);
				}
			}
		}

		if (LOCManager.locParameters.contains(LOCParameters.INTERN_VOCABULARY)) {

			if (functionDefinition.getBody() instanceof CASTCompoundStatement) {
				IASTStatement[] statements = body.getStatements();

				for (IASTStatement statement : statements) {
					ExpressionProcessor.setVocabularyManager(vocabularyManager);
					bodyProcessor.extractBody(statement);
				}
			}

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

		if ((LOCManager.locParameters.contains(LOCParameters.LOC)) && (entityType == EntityType.INNER_FUNCTION)
				&& (isInner ? LOCManager.locParameters.contains(LOCParameters.INNER_FUNCTION) : true)) {
			LOCManager.appendEntityLOCData(name, locKeeper, EntityType.INNER_FUNCTION);
		}

		vxlFragment.append(VxlManager.endFuntion(indentationLevel));
	}

	/**
	 * Processa a classe de armazenamento da função
	 * 
	 * @param declaration
	 * @return
	 */
	private String storageClass(IASTFunctionDeclarator declaration) {
		IFunction function = (IFunction) new CFunction(declaration);
		String storage;

		if (function.isAuto())
			storage = "auto";
		else if (function.isStatic())
			storage = "static";
		else if (function.isRegister())
			storage = "register";
		else if (function.isExtern())
			storage = "extern";
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

	/**
	 * Processo o tipo de acesso da função
	 * 
	 * @param decl
	 * @return
	 */
	private String access(IASTDeclSpecifier decl) {

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

				vxlFragment.append(VxlManager.localVariable(StringProcessor.processString(identifier), access, storage,
						count, indentationLevel));
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

			vxlFragment.append(VxlManager.globalVariable(StringProcessor.processString(identifier), access, storage,
					count, indentationLevel));
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
			vxlFragment.append(VxlManager.functionCall(StringProcessor.processString(identifier),
					functionCall.get(identifier), indentationLevel));
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
	 * Retorn o fragmento de vxl
	 * 
	 * @return
	 */
	public StringBuffer getVxlFragment() {
		return vxlFragment;
	}
}