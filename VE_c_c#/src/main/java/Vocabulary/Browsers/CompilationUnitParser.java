package Vocabulary.Browsers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTComment;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.gnu.c.GCCLanguage;
import org.eclipse.cdt.core.parser.DefaultLogService;
import org.eclipse.cdt.core.parser.FileContent;
import org.eclipse.cdt.core.parser.IParserLogService;
import org.eclipse.cdt.core.parser.IScannerInfo;
import org.eclipse.cdt.core.parser.IncludeFileContentProvider;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.core.runtime.CoreException;
import Vocabulary.Extractor.Util.CommentUnit;
import Vocabulary.Processors.CompilationUnitProcessor;

public class CompilationUnitParser
{
	private static IASTTranslationUnit translationUnit;
	
	public static StringBuffer parse(File file) throws CoreException, IOException
	{
		StringBuffer source;
		
		FileContent fileContent = FileContent.createForExternalFileLocation(file.getAbsolutePath());

		Map definedSymbols = new HashMap();
		String[] includePaths = new String[0];
		IScannerInfo info = new ScannerInfo(definedSymbols, includePaths);
		IParserLogService log = new DefaultLogService();

		IncludeFileContentProvider emptyIncludes = IncludeFileContentProvider.getEmptyFilesProvider();

		int opts = 8;
		translationUnit = GCCLanguage.getDefault().getASTTranslationUnit(fileContent, info,
				emptyIncludes, null, opts, log);
		
	    source = new StringBuffer(translationUnit.getRawSignature().toString());
	    
	    CompilationUnitProcessor.setSourceCode(new String(source).toString());
		CompilationUnitProcessor.reset();
		
		setCompilationUnitComments(translationUnit, CompilationUnitProcessor.getSourceCode());
		
		return (new CompilationUnitProcessor(translationUnit, file.getName()).getVxlFragment());
	}
	
	private static void setCompilationUnitComments(IASTTranslationUnit translationUnit, String sourceCode)
	{
		for(IASTComment element : translationUnit.getComments())
		{
			CompilationUnitProcessor.addCommentUnit(new CommentUnit((ASTNode)element, sourceCode));
		}
	}
}
