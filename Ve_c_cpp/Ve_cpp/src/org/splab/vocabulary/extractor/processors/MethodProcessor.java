package org.splab.vocabulary.extractor.processors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPMethod;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompoundStatement;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPImplicitConstructor;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPMethod;
import org.splab.vocabulary.extractor.nodelists.DeclarationList;
import org.splab.vocabulary.extractor.util.LOCManager;
import org.splab.vocabulary.extractor.util.VxlManager;
import org.splab.vocabulary.extractor.vloccount.EntityLOCKeeper;
import org.splab.vocabulary.extractor.vloccount.EntityType;
import org.splab.vocabulary.extractor.vloccount.LOCCountPerEntity;
import org.splab.vocabulary.extractor.vloccount.LOCParameters;

public class MethodProcessor extends FunctionProcessor {
	private List<ASTNode> listDeclarations;
	private String modificador;
	private String visibilidade;

	public MethodProcessor() {
		vxlFragment = new StringBuffer();
	}

	public MethodProcessor(CPPASTFunctionDefinition functionDefinition, ICPPMethod method, boolean isInner,
			EntityType entityType) {

		// Lista contendo as funções desde a primeira até a mais interna
		listFuncions = new ArrayList<FunctionProcessor>();
		listFuncions.add(this);

		// Vocabulário interno das funções
		functionVocabulary = new FunctionVocabularyManager();

		// Captura o nome, tipo de classe de armazenamento e o acesso da função
		name = functionDefinition.getDeclarator().getName().toString();
		storage = storageClass(functionDefinition.getDeclarator());
		access = access(functionDefinition.getDeclSpecifier());

		name = functionDefinition.getDeclarator().getName().toString();
		storage = storageClass(method);
		access = access(functionDefinition.getDeclSpecifier());

		if (method instanceof CPPImplicitConstructor)
			modificador = "";
		else
			modificador = modifies(((CPPMethod)method).getDefinition());
		
		visibilidade = visibility(method.getVisibility());

		// Converte o tipo de ASTTranslationUnit para ASTNode
		ASTNode typeAST = (ASTNode) functionDefinition;

		// Cria um extrator para as informações do corpo da função
		bodyProcessor = new BodyProcessor(functionVocabulary);

		// Passa o functionVocabulary para acesso na expression processor
		ExpressionProcessor.setFunctionVocabulary(functionVocabulary);

		body = (CPPASTCompoundStatement) functionDefinition.getBody();

		// Cria uma lista con todas as declarações do código
		listDeclarations = DeclarationList.getTypes(body.getStatements());

		LOCCountPerEntity locCounter = new LOCCountPerEntity(typeAST, CompilationUnitProcessor.commentList,
				CompilationUnitProcessor.sourceCode);

		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter, true);
		locKeeper.setHeadersLOC(0, true, LOCManager.locParameters.contains(LOCParameters.HEADERS));

		vxlFragment = new StringBuffer(
				VxlManager.startMethod(name, access, storage, modificador, visibilidade, locKeeper.getLOC()));
		vxlFragment
				.append(new CommentsProcessor(listDeclarations, (ASTNode) functionDefinition, true).getVxlFragment());

		extractFunction(functionDefinition, entityType);

		if ((LOCManager.locParameters.contains(LOCParameters.LOC)) && (entityType == EntityType.METHOD)) {
			LOCManager.appendEntityLOCData(name, locKeeper, EntityType.METHOD);
		}

		if ((LOCManager.locParameters.contains(LOCParameters.LOC)) && (entityType == EntityType.INNER_FUNCTION)
				&& (isInner ? LOCManager.locParameters.contains(LOCParameters.INNER_FUNCTION) : true)) {
			LOCManager.appendEntityLOCData(name, locKeeper, EntityType.INNER_FUNCTION);
		}
		vxlFragment.append(VxlManager.endMethod());

		listFuncions.remove(this);
		if (listFuncions.size() > 0)
			ExpressionProcessor
					.setFunctionVocabulary(listFuncions.get(listFuncions.size() - 1).getFunctionVocabulary());
	}

	/** Processa a classe de armazenamento das funções **/

	private static String visibility(int visibilityKey) {
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

	private static String storageClass(ICPPMethod method) {
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

	String modifies(ICPPASTFunctionDeclarator functionDeclarator) {

		String modificador = "";

		if (functionDeclarator.isConst())
			modificador = "const";
		else if (functionDeclarator.isFinal())
			modificador = "final";

		return modificador;
	}
}
