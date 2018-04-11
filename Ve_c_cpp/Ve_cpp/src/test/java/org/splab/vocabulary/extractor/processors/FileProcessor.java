package org.splab.vocabulary.extractor.processors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorStatement;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTEnumerationSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclaration;
import org.splab.vocabulary.extractor.util.CommentUnit;
import org.splab.vocabulary.extractor.util.LOCManager;
import org.splab.vocabulary.extractor.util.VxlManager;
import org.splab.vocabulary.extractor.vloccount.EntityLOCKeeper;
import org.splab.vocabulary.extractor.vloccount.EntityType;
import org.splab.vocabulary.extractor.vloccount.LOCCountPerEntity;
import org.splab.vocabulary.extractor.vloccount.LOCParameters;

/**
 * This class is responsible for extracting informations from the files's body.
 * FileProcessor extract informations such as: file, extern declarations
 * 
 * @author Israel Gomes de Lima
 */

public class FileProcessor {
	protected static List<CommentUnit> sourceCodeComments;
	private StringBuffer vxlFragment;

	private StringBuffer functionVXL;
	private StringBuffer enumVXL;
	private StringBuffer structVXL;
	private StringBuffer unionVXL;
	private StringBuffer simpleDeclarationVXL;

	protected static Map<String, Integer> gvar;
	protected static Map<String, String> gvarStorage;
	protected static Map<String, String> gvarAccess;
	
	protected static Map<String, String> prttpStorage;
	protected static Map<String, String> prttpAccess;

	public FileProcessor() {
		vxlFragment = new StringBuffer("");
	}

	public FileProcessor(IASTTranslationUnit translationUnit, String fileName, int headerLines) {
		vxlFragment = new StringBuffer();
		sourceCodeComments = new LinkedList<CommentUnit>();

		// Mantem fragmentos de vxl para as entidades abaixo
		functionVXL = new StringBuffer();
		enumVXL = new StringBuffer();
		structVXL = new StringBuffer();
		unionVXL = new StringBuffer();
		simpleDeclarationVXL = new StringBuffer();

		// mantem as informações das varáveis globais
		gvar = new TreeMap<String, Integer>();
		gvarStorage = new TreeMap<String, String>();
		gvarAccess = new TreeMap<String, String>();
		
		prttpStorage = new TreeMap<String, String>();
		prttpAccess = new TreeMap<String, String>();

		// Retorna as declarations
		IASTDeclaration[] declarations = translationUnit.getDeclarations();
		ASTNode type = (ASTNode) translationUnit;
		List<ASTNode> listDeclarations = declarationList(declarations);

		// Contagem de loc por entidade
		LOCCountPerEntity locCounter = new LOCCountPerEntity(type, CompilationUnitProcessor.commentList,
				CompilationUnitProcessor.sourceCode, listDeclarations, headerLines);
		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter, false);
		locKeeper.setHeadersLOC(headerLines, false, LOCManager.locParameters.contains(LOCParameters.HEADERS));

		vxlFragment.append(VxlManager.startFile(fileName, locKeeper.getLOC()));
		vxlFragment.append((new CommentsProcessor(listDeclarations, type, false).getVxlFragment()));

		// Processa as Directives
		IASTPreprocessorStatement[] directives = translationUnit.getAllPreprocessorStatements();
		DirectivesProcessor.extractDirectives(directives);
		vxlFragment.append(DirectivesProcessor.getVxlFragment());

		for (IASTDeclaration nodeDeclaration : declarations) {

			// Processa as funções
			if (nodeDeclaration instanceof IASTFunctionDefinition) {
				FunctionProcessor functions = new FunctionProcessor((CASTFunctionDefinition) nodeDeclaration, false,
						EntityType.FUNCTION);
				functionVXL.append(functions.getVxlFragment());
			}

			// Processa variáveis, struct, union, enums, protótipos
			if (nodeDeclaration instanceof IASTSimpleDeclaration) {
				DeclarationsType valor = Declarations.declarations((CASTSimpleDeclaration) nodeDeclaration, null,
						false);

				if (valor == DeclarationsType.ENUM)
					enumVXL.append(Declarations.getVxlFragment());
				else if (valor == DeclarationsType.STRUCT)
					structVXL.append(Declarations.getVxlFragment());
				else if (valor == DeclarationsType.UNION)
					unionVXL.append(Declarations.getVxlFragment());

			}
		}

		// monta o vxl de simpleDeclaration
		storeInternVocabulary();

		// Junta os fragmentos de vxl
		vxlFragment.append(simpleDeclarationVXL);
		vxlFragment.append(enumVXL);
		vxlFragment.append(structVXL);
		vxlFragment.append(unionVXL);
		vxlFragment.append(functionVXL);

		if (LOCManager.locParameters.contains(LOCParameters.LOC)) {
			LOCManager.appendEntityLOCData(fileName, locKeeper, EntityType.FILE);
		}

		vxlFragment.append(VxlManager.endFile());
	}

	public StringBuffer getVxlFragment() {
		return vxlFragment;
	}

	@Override
	public String toString() {
		return vxlFragment.toString();
	}

	/** Cria um lista com as declarações que podem conter comentários **/
	private static List<ASTNode> declarationList(IASTDeclaration[] declarations) {
		List<ASTNode> fileDeclarations = new ArrayList<ASTNode>();

		for (IASTDeclaration declaration : declarations) {
			if (declaration instanceof IASTFunctionDefinition) {
				fileDeclarations.add((ASTNode) declaration);
			}

			if (declaration instanceof IASTSimpleDeclaration) {
				CASTSimpleDeclaration node = (CASTSimpleDeclaration) declaration;

				IASTDeclSpecifier declarationSpecifier = node.getDeclSpecifier();

				if (declarationSpecifier instanceof CASTEnumerationSpecifier) {
					CASTEnumerationSpecifier enumeration = (CASTEnumerationSpecifier) declarationSpecifier;
					fileDeclarations.add((ASTNode) enumeration);
				}

				if (declarationSpecifier instanceof CASTCompositeTypeSpecifier) {
					CASTCompositeTypeSpecifier composite = (CASTCompositeTypeSpecifier) declarationSpecifier;
					fileDeclarations.add((ASTNode) composite);
				}
			}
		}

		return fileDeclarations;
	}

	/** Insere o vocabulário das variáveis globais **/
	public static void insertGlobalVariable(String variable, String access, String storage) {

		if (variable != null && !variable.equals("")) {
			if (gvar.containsKey(variable)) {
				gvar.put(variable, gvar.get(variable) + 1);
			} else {
				gvar.put(variable, 1);
				gvarAccess.put(variable, access);
				gvarStorage.put(variable, storage);
			}
		}
	}
	
	public static void insertGlobalPrttp(String name, String access, String storage) {

		if (name != null && !name.equals("")) {
			if (!prttpStorage.containsKey(name)) {
				prttpAccess.put(name, access);
				prttpStorage.put(name, storage);
			}
		}
	}

	/** Processa o vocabulário das variáveis globais **/
	private void storeInternVocabulary() {
		Set<String> gvariable = gvar.keySet();
		Iterator<String> it_gvar = gvariable.iterator();

		while (it_gvar.hasNext()) {
			String identifier = it_gvar.next();

			String access = gvarAccess.get(identifier);
			String storage = gvarStorage.get(identifier);
			int count = gvar.get(identifier);

			vxlFragment.append(VxlManager.globalVariables(identifier, access, storage, count));
		}
		
		Set<String> prototipo = prttpAccess.keySet();
		Iterator<String> it_prototipo = prototipo.iterator();

		while (it_prototipo.hasNext()) {
			String identifier = it_prototipo.next();

			String access = prttpAccess.get(identifier);
			String storage = prttpStorage.get(identifier);

			vxlFragment.append(VxlManager.globalPrototip(identifier, access, storage));
		}
	}
}
