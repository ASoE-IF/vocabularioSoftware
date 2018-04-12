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

	public FileProcessor() {
		vxlFragment = new StringBuffer("");
	}

	public FileProcessor(IASTTranslationUnit translationUnit, String fileName, int headerLines) {

		FileVocabularyManager.initializerVariables();
		
		vxlFragment = new StringBuffer();
		sourceCodeComments = new LinkedList<CommentUnit>();

		// Mantem fragmentos de vxl para as entidades abaixo
		functionVXL = new StringBuffer();
		enumVXL = new StringBuffer();
		structVXL = new StringBuffer();
		unionVXL = new StringBuffer();

		// Retorna as declarations
		IASTDeclaration[] declarations = translationUnit.getDeclarations();
		ASTNode type = (ASTNode) translationUnit;
		List<ASTNode> listDeclarations = DeclarationList.getTypes(declarations);

		//Processa o vocabulário das directives
		IASTPreprocessorStatement[] directives = translationUnit.getAllPreprocessorStatements();
		DirectivesProcessor.extractDirectives(directives);
		
		// Contagem de loc por entidade
		LOCCountPerEntity locCounter = new LOCCountPerEntity(type, CompilationUnitProcessor.commentList,
				CompilationUnitProcessor.sourceCode);

		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter);
		locKeeper.setHeadersLOC(headerLines, false, LOCManager.locParameters.contains(LOCParameters.HEADERS));

		vxlFragment.append(VxlManager.startFile(fileName, locKeeper.getLOC() + locKeeper.getInnerEntitiesLOC() + locKeeper.getHeaderLOC()));
		vxlFragment.append((new CommentsProcessor(listDeclarations, type, false).getVxlFragment()));
		//Adiciona o vocabulário das directives ao vxl
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

		// monta o vxl de variáveis
		storeInternVocabulary(FileVocabularyManager.gvar, FileVocabularyManager.gvarStorage,
				FileVocabularyManager.gvarAccess, FileVocabularyManager.prttpStorage, FileVocabularyManager.prttpAccess,
				FileVocabularyManager.literal);
		// Junta os fragmentos de vxl
		vxlFragment.append(enumVXL);
		vxlFragment.append(structVXL);
		vxlFragment.append(unionVXL);
		vxlFragment.append(functionVXL);

		if (LOCManager.locParameters.contains(LOCParameters.LOC)) {
			LOCManager.appendEntityLOCData(fileName, locKeeper, EntityType.FILE);
		}

		vxlFragment.append(VxlManager.endFile());
	}

	/** Processa o vocabulário das variáveis globais **/
	private void storeInternVocabulary(Map<String, Integer> gvar, Map<String, String> gvarStorage,
			Map<String, String> gvarAccess, Map<String, String> prttpStorage, Map<String, String> prttpAccess,
			Map<String, Integer> literal) {
		
		//Processa gvars
		Set<String> gvariable = gvar.keySet();
		Iterator<String> it_gvar = gvariable.iterator();
		while (it_gvar.hasNext()) {
			String identifier = it_gvar.next();

			String access = gvarAccess.get(identifier);
			String storage = gvarStorage.get(identifier);
			int count = gvar.get(identifier);

			vxlFragment.append(VxlManager.globalVariables(identifier, access, storage, count));
		}

		//Processar protótipos de funções
		Set<String> prototipo = prttpAccess.keySet();
		Iterator<String> it_prototipo = prototipo.iterator();
		while (it_prototipo.hasNext()) {
			String identifier = it_prototipo.next();

			String access = prttpAccess.get(identifier);
			String storage = prttpStorage.get(identifier);

			vxlFragment.append(VxlManager.globalPrototip(identifier, access, storage));
		}

		//Processa Literais
		Set<String> lit = literal.keySet();
		Iterator<String> it_lit = lit.iterator();
		while (it_lit.hasNext()) {
			String identifier = it_lit.next();
			vxlFragment.append(VxlManager.literal(identifier, literal.get(identifier)));
		}
	}

	public StringBuffer getVxlFragment() {
		return vxlFragment;
	}

	@Override
	public String toString() {
		return vxlFragment.toString();
	}
}
