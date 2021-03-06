package org.splab.vocabulary.extractor.browsers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTComment;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage;
import org.eclipse.cdt.core.parser.DefaultLogService;
import org.eclipse.cdt.core.parser.FileContent;
import org.eclipse.cdt.core.parser.IParserLogService;
import org.eclipse.cdt.core.parser.IScannerInfo;
import org.eclipse.cdt.core.parser.IncludeFileContentProvider;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.core.runtime.CoreException;
import org.splab.vocabulary.extractor.processors.CompilationUnitProcessor;
import org.splab.vocabulary.extractor.util.CommentUnit;
import org.splab.vocabulary.extractor.util.ErrorLogManager;

/**
 * Baseado e modificado da Versão do extrator original feita por Tercio de Melo
 * Para funcionamento no presente extrator
 * 
 * @author Tercio de Melo, Israel Gomes de Lima
 */
public class CompilationUnitParser {
	private static IASTTranslationUnit translationUnit;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static StringBuffer parse(File file) {
		StringBuffer source;

		FileContent fileContent = FileContent.createForExternalFileLocation(file.getAbsolutePath());

		Map definedSymbols = new HashMap();
		String[] includePaths = new String[0];
		IScannerInfo info = new ScannerInfo(definedSymbols, includePaths);
		IParserLogService log = new DefaultLogService();

		IncludeFileContentProvider emptyIncludes = IncludeFileContentProvider.getEmptyFilesProvider();

		int opts = 8;

		// Gera a AST de um Codigo codificado na linguagem C
		try {
			translationUnit = GPPLanguage.getDefault().getASTTranslationUnit(fileContent, info, emptyIncludes, null,
					opts, log);
		} catch (CoreException core) {
			ErrorLogManager.appendErro(file.getAbsolutePath(), core.getMessage());
			core.printStackTrace();
			return new StringBuffer();
		} catch (ClassCastException cast) {
			ErrorLogManager.appendErro(file.getAbsolutePath(), cast.getMessage());
			return new StringBuffer();
		} catch (NullPointerException nullPointer) {
			ErrorLogManager.appendErro(file.getAbsolutePath(), nullPointer.getMessage());
			return new StringBuffer();
		}

		source = new StringBuffer(translationUnit.getRawSignature().toString());

		CompilationUnitProcessor.setSourceCode(new String(source).toString());
		CompilationUnitProcessor.reset();

		setCompilationUnitComments(translationUnit, CompilationUnitProcessor.getSourceCode());

		return (new CompilationUnitProcessor(translationUnit, file.getPath()).getVxlFragment());
	}

	private static void setCompilationUnitComments(IASTTranslationUnit translationUnit, String sourceCode) {
		for (IASTComment element : translationUnit.getComments()) {
			CompilationUnitProcessor.addCommentUnit(new CommentUnit((ASTNode) element, sourceCode));
		}
	}
}
