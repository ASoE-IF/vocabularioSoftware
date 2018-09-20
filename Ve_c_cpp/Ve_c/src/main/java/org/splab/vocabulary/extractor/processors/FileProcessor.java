package org.splab.vocabulary.extractor.processors;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorStatement;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclaration;
import org.splab.vocabulary.extractor.nodelists.DeclarationList;
import org.splab.vocabulary.extractor.processors.vocabulay.manager.FileVocabularyManager;
import org.splab.vocabulary.extractor.util.CommentUnit;
import org.splab.vocabulary.extractor.util.LOCManager;
import org.splab.vocabulary.extractor.util.VxlManager;
import org.splab.vocabulary.extractor.vloccount.EntityLOCKeeper;
import org.splab.vocabulary.extractor.vloccount.EntityType;
import org.splab.vocabulary.extractor.vloccount.LOCCountPerEntity;
import org.splab.vocabulary.extractor.vloccount.LOCParameters;

/**
 * FileProcessor é responsavel por diferenciar as entidades e designar o
 * processador correto para a operação de extração do vocabulário.
 * 
 * @author Israel Gomes de Lima
 */
public class FileProcessor {
	/**
	 * Cria a lista de comentários e o fragmento de vxl do arquivo
	 */
	protected static List<CommentUnit> sourceCodeComments;
	private StringBuffer vxlFragment;

	/**
	 * Construtor para criar um processador de arquivo
	 * 
	 * @param translationUnit
	 * @param fileName
	 * @param headerLines
	 */
	public FileProcessor(IASTTranslationUnit translationUnit, String fileName, int headerLines) {

		// Configura as partes do vocabulario
		FileVocabularyManager vocabularyManager = new FileVocabularyManager();
		ExpressionProcessor.setVocabularyManager(vocabularyManager);

		// Configura os vxl
		vxlFragment = new StringBuffer();
		StringBuffer functionVXL = new StringBuffer();
		int indentationLevel = 1;

		// Cria lista de comentários, declarações, tipos e nodos
		sourceCodeComments = new LinkedList<CommentUnit>();
		IASTDeclaration[] declarations = translationUnit.getDeclarations();
		ASTNode type = (ASTNode) translationUnit;
		List<ASTNode> listDeclarations = DeclarationList.getTypes(declarations);

		// Processa o vocabulário das directives
		IASTPreprocessorStatement[] directives = translationUnit.getAllPreprocessorStatements();
		DirectivesProcessor.extractDirectives(directives);

		// Contagem de loc por entidade
		LOCCountPerEntity locCounter = new LOCCountPerEntity(type, CompilationUnitProcessor.commentList,
				CompilationUnitProcessor.sourceCode);

		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter);
		locKeeper.setHeadersLOC(headerLines, false, LOCManager.locParameters.contains(LOCParameters.HEADERS));

		vxlFragment.append(VxlManager.startFile(fileName,
				locKeeper.getLOC() + locKeeper.getInnerEntitiesLOC() + locKeeper.getHeaderLOC()));
		vxlFragment.append((new CommentsProcessor(listDeclarations, type, indentationLevel + 1).getVxlFragment()));

		// Incrementa o vxl das directives
		vxlFragment.append(DirectivesProcessor.getVxlFragment());

		DeclarationProcessor declProcessor = new DeclarationProcessor(vocabularyManager);
		for (IASTDeclaration nodeDeclaration : declarations) {
			// Processa as funções
			if (nodeDeclaration instanceof IASTFunctionDefinition) {
				CASTFunctionDefinition functionDefinition = (CASTFunctionDefinition) nodeDeclaration;
				FunctionProcessor functions = new FunctionProcessor(functionDefinition, false, EntityType.FUNCTION,
						indentationLevel + 1);
				functionVXL.append(functions.getVxlFragment());
			}

			// Processa variáveis, struct, union, enums, protótipos e class
			if (nodeDeclaration instanceof IASTSimpleDeclaration) {
				CASTSimpleDeclaration simpleDeclaration = (CASTSimpleDeclaration) nodeDeclaration;
				declProcessor.extractDeclaration(simpleDeclaration, indentationLevel + 1);
			}
		}

		storeInternVocabulary(vocabularyManager.getGlobalVar(), vocabularyManager.getGlobalVarStorage(),
				vocabularyManager.getGlobalVarAccess(), vocabularyManager.getLiterals(), indentationLevel + 1);

		// Junta os fragmentos de vxl
		vxlFragment.append(declProcessor.getVxlFragment());
		vxlFragment.append(functionVXL);
		// Fim do arquivo no vxl

		if (LOCManager.locParameters.contains(LOCParameters.LOC)) {
			LOCManager.appendFileLOCData(fileName, locKeeper, EntityType.FILE);
		}

		vxlFragment.append(VxlManager.endFile());
	}

	/**
	 * Recebe os vocabulários extraidos do arquivo e adiciona-os ao fragmento de
	 * vxl
	 * 
	 * @param gvar
	 * @param gvarStorage
	 * @param gvarAccess
	 * @param literal
	 * @param entityIndentationLevel
	 */
	private void storeInternVocabulary(Map<String, Integer> gvar, Map<String, String> gvarStorage,
			Map<String, String> gvarAccess, Map<String, Integer> literal, int entityIndentationLevel) {

		// Processa gvars
		Set<String> gvariable = gvar.keySet();
		Iterator<String> it_gvar = gvariable.iterator();
		while (it_gvar.hasNext()) {
			String identifier = it_gvar.next();

			String access = gvarAccess.get(identifier);
			String storage = gvarStorage.get(identifier);
			int count = gvar.get(identifier);

			vxlFragment.append(VxlManager.globalVariable(identifier, access, storage, count, entityIndentationLevel));
		}

		// Processa Literais
		Set<String> lit = literal.keySet();
		Iterator<String> it_lit = lit.iterator();
		while (it_lit.hasNext()) {
			String identifier = it_lit.next();
			vxlFragment.append(VxlManager.literal(identifier, literal.get(identifier), entityIndentationLevel));
		}
	}

	/**
	 * Retorna o fragmento de vxl
	 * 
	 * @return
	 */
	public StringBuffer getVxlFragment() {
		return vxlFragment;
	}

	@Override
	public String toString() {
		return vxlFragment.toString();
	}
}