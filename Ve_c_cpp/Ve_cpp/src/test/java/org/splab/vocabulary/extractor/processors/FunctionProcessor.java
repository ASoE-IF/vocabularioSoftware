package org.splab.vocabulary.extractor.processors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IFunction;
import org.eclipse.cdt.core.dom.ast.IVariable;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompoundStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTDeclarationStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTEnumerationSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.c.CFunction;
import org.eclipse.cdt.internal.core.dom.parser.c.CVariable;
import org.splab.vocabulary.extractor.util.LOCManager;
import org.splab.vocabulary.extractor.util.VxlManager;
import org.splab.vocabulary.extractor.vloccount.EntityLOCKeeper;
import org.splab.vocabulary.extractor.vloccount.EntityType;
import org.splab.vocabulary.extractor.vloccount.LOCCountPerEntity;
import org.splab.vocabulary.extractor.vloccount.LOCParameters;

public class FunctionProcessor {
	private StringBuffer vxlFragment;
	private List<ASTNode> listDeclarations;
	private FunctionVocabularyManager functionVocabulary;

	protected static List<FunctionProcessor> listFuncions;

	public FunctionProcessor() {
		vxlFragment = new StringBuffer();
	}

	public FunctionProcessor(CASTFunctionDefinition functionDefinition, boolean isInner, EntityType entityType) {

		// Lista contendo as funções desde a primeira até a mais interna
		listFuncions = new ArrayList<FunctionProcessor>();
		listFuncions.add(this);

		// Vocabulário interno das funções
		functionVocabulary = new FunctionVocabularyManager();

		// Captura o nome, tipo de classe de armazenamento e o acesso da função
		String name = functionDefinition.getDeclarator().getName().toString();
		String storage = storageClass(functionDefinition.getDeclarator());
		String acesso = access(functionDefinition.getDeclSpecifier());

		// Converte o tipo de ASTTranslationUnit para ASTNode
		ASTNode typeAST = (ASTNode) functionDefinition;

		// Cria um extrator para as informações do corpo da função
		BodyProcessor bodyProcessor = new BodyProcessor(functionVocabulary);

		// Passa o functionVocabulary para acesso na expression processor
		ExpressionProcessor.setFunctionVocabulary(functionVocabulary);

		CASTCompoundStatement body = (CASTCompoundStatement) functionDefinition.getBody();

		// Cria uma lista con todas as declarações do código
		listDeclarations = statementList(body.getStatements());

		LOCCountPerEntity locCounter = new LOCCountPerEntity(typeAST, CompilationUnitProcessor.commentList,
				CompilationUnitProcessor.sourceCode, listDeclarations, 0);

		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter, true);
		locKeeper.setHeadersLOC(0, true, LOCManager.locParameters.contains(LOCParameters.HEADERS));

		vxlFragment = new StringBuffer(VxlManager.startFuntion(name, acesso, storage, isInner, locKeeper.getLOC()));
		vxlFragment
				.append(new CommentsProcessor(listDeclarations, (ASTNode) functionDefinition, true).getVxlFragment());

		// Extrai as informações dos parâmetros
		if (functionDefinition.getDeclarator() instanceof CASTFunctionDeclarator) {
			CASTFunctionDeclarator argumentsOfFunction = (CASTFunctionDeclarator) functionDefinition.getDeclarator();

			IASTParameterDeclaration[] arguments = argumentsOfFunction.getParameters();

			for (IASTParameterDeclaration argument : arguments) {
				if (!argument.getDeclarator().getName().toString().equals("")) {
					String parametroStorage = storageClass(argument.getDeclarator().getName());
					String parametroAccess = access(argument.getDeclSpecifier());
					String parametroName = argument.getDeclarator().getName().toString();

					VxlManager.parameter(vxlFragment, parametroName);

					functionVocabulary.insertParameter(parametroName, parametroAccess, parametroStorage);
				}
			}
		}

		if (functionDefinition.getBody() instanceof CASTCompoundStatement) {
			IASTStatement[] statements = body.getStatements();

			for (IASTStatement statement : statements) {
				bodyProcessor.extractBody(statement);
			}

			// Processa o vocabulário das variáveis globais
			storeInternVocabularyGlobalVar(functionVocabulary.getGlobalVar(), functionVocabulary.getGlobalVarAccess(),
					functionVocabulary.getGlobalVarStorage());
			// Processa o vocabulário das variáveis locais
			storeInternVocabularyLocalVar(functionVocabulary.getLocalVar(), functionVocabulary.getLocalVarAccess(),
					functionVocabulary.getLocalVarStorage());
			// Processa o vocabulário dos protótipos de funções
			storeInternVocabularyFunctionPrttp(functionVocabulary.getFunctionPrttpAccess(),
					functionVocabulary.getFunctionPrttpStorage());
			// Processa o vocabulário das chamadas de funções
			storeInternVocabularyFunctionCall(functionVocabulary.getFunctionCall());
			// Processa o vocabulário das Strings Literais
			storeInternVocabularyLiteral(functionVocabulary.getLiterals());

			vxlFragment.append(bodyProcessor.getVxlFragment());
		}

		if ((LOCManager.locParameters.contains(LOCParameters.LOC)) && (entityType == EntityType.FUNCTION)) {
			LOCManager.appendEntityLOCData(name, locKeeper, EntityType.FUNCTION);
		}

		if ((LOCManager.locParameters.contains(LOCParameters.LOC)) && (entityType == EntityType.INNER_FUNCTION)
				&& (isInner ? LOCManager.locParameters.contains(LOCParameters.INNER_FUNCTION) : true)) {
			LOCManager.appendEntityLOCData(name, locKeeper, EntityType.INNER_FUNCTION);
		}
		vxlFragment.append(VxlManager.endFuntion());

		listFuncions.remove(this);
		if (listFuncions.size() > 0)
			ExpressionProcessor
					.setFunctionVocabulary(listFuncions.get(listFuncions.size() - 1).getFunctionVocabulary());
	}

	/** Processa a classe de armazenamento das funções **/
	private static String storageClass(IASTFunctionDeclarator declaration) {
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

	/** processa a classe de armazenamento de uma declaração qualquer **/
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

	/** Processa o acesso de qualquer declaração **/
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

	/** A partir daqui inserimos os vocabulários no vxl **/
	private void storeInternVocabularyLocalVar(Map<String, Integer> localVariables,
			Map<String, String> localVariablesAccess, Map<String, String> localVariablesStorage) {

		Set<String> lvar = localVariables.keySet();
		Iterator<String> it_lvar = lvar.iterator();

		while (it_lvar.hasNext()) {
			String identifier = it_lvar.next();
			if (localVariables.get(identifier) > 0) {
				String access = localVariablesAccess.get(identifier);
				String storage = localVariablesStorage.get(identifier);
				int count = localVariables.get(identifier);

				vxlFragment.append(VxlManager.localVariables(identifier, access, storage, count));
			}
		}
	}

	private void storeInternVocabularyGlobalVar(Map<String, Integer> globalVariables,
			Map<String, String> globalVariablesAccess, Map<String, String> globalVariablesStorage) {

		Set<String> gvar = globalVariables.keySet();
		Iterator<String> it_gvar = gvar.iterator();

		while (it_gvar.hasNext()) {
			String identifier = it_gvar.next();

			String access = globalVariablesAccess.get(identifier);
			String storage = globalVariablesStorage.get(identifier);
			int count = globalVariables.get(identifier);

			vxlFragment.append(VxlManager.globalVariables(identifier, access, storage, count));
		}
	}

	private void storeInternVocabularyFunctionPrttp(Map<String, String> prttpAccess, Map<String, String> prttpStorage) {

		Set<String> prttp = prttpAccess.keySet();

		Iterator<String> it_prttp = prttp.iterator();

		while (it_prttp.hasNext()) {
			String identifier = it_prttp.next();
			String access = prttpAccess.get(identifier);
			String storage = prttpStorage.get(identifier);

			vxlFragment.append(VxlManager.localPrototip(identifier, access, storage));
		}
	}

	private void storeInternVocabularyFunctionCall(Map<String, Integer> functionCall) {

		Set<String> funcCall = functionCall.keySet();
		Iterator<String> it_funcCall = funcCall.iterator();
		while (it_funcCall.hasNext()) {
			String identifier = it_funcCall.next();
			vxlFragment.append(VxlManager.functionCall(identifier, functionCall.get(identifier)));
		}
	}

	private void storeInternVocabularyLiteral(Map<String, Integer> literals) {

		Set<String> lit = literals.keySet();
		Iterator<String> it_lit = lit.iterator();
		while (it_lit.hasNext()) {
			String identifier = it_lit.next();
			vxlFragment.append(VxlManager.literal(identifier, literals.get(identifier)));
		}
	}

	public StringBuffer getVxlFragment() {
		return vxlFragment;
	}

	/**
	 * Cria uma lista de statements para poder extrair os comentários
	 * corretamente
	 **/
	public List<ASTNode> statementList(IASTStatement[] declarations) {
		List<ASTNode> fileDeclarations = new ArrayList<ASTNode>();

		for (IASTStatement declaration : declarations) {
			if (declaration instanceof CASTDeclarationStatement) {

				CASTDeclarationStatement decl = (CASTDeclarationStatement) declaration;
				if (decl.getDeclaration() instanceof IASTFunctionDefinition) {
					fileDeclarations.add((ASTNode) decl.getDeclaration());
				}

				if (decl.getDeclaration() instanceof IASTSimpleDeclaration) {
					CASTSimpleDeclaration node = (CASTSimpleDeclaration) decl.getDeclaration();

					IASTDeclSpecifier declarationSpecifier = node.getDeclSpecifier();

					if ((declarationSpecifier instanceof CASTEnumerationSpecifier)
							|| (declarationSpecifier instanceof CASTCompositeTypeSpecifier)) {
						fileDeclarations.add((ASTNode) decl.getDeclaration());
					}
				}
			}
		}

		return fileDeclarations;
	}

	public FunctionVocabularyManager getFunctionVocabulary() {
		return functionVocabulary;
	}
}
