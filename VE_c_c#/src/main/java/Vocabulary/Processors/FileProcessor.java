package Vocabulary.Processors;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTEnumerationSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTProblemDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclaration;
import Vocabulary.Extractor.Util.CommentUnitC;
import Vocabulary.Extractor.Util.LOCManager;
import Vocabulary.Extractor.Util.VxlManager;
import Vocabulary.vloccount.EntityLOCKeeper;
import Vocabulary.vloccount.EntityType;
import Vocabulary.vloccount.LOCCountPerEntity;
import Vocabulary.vloccount.LOCParameters;

public class FileProcessor
{
	protected static List<CommentUnitC> sourceCodeComments;
	static ArrayList<ASTNode> fileDeclarations;//lista de funções
	
	private static StringBuffer vxlFragment;
	private static StringBuffer vxlFragmentDeclarations;
	private static Integer enumLoc = 0;
	
	public FileProcessor()
	{
		vxlFragment = new StringBuffer("");
	}
	
	@SuppressWarnings("unchecked")
	public FileProcessor(IASTTranslationUnit translationUnit, String fileName, boolean isInner, int headerLines)
	{
		vxlFragment = new StringBuffer();
		vxlFragmentDeclarations = new StringBuffer();
		
		sourceCodeComments = new LinkedList<CommentUnitC>();
		
		enumLoc = 0;
		
		IASTDeclaration[] nodesDeclarations = translationUnit.getDeclarations();//extrai todas as declarações do codigo
		
		fileDeclarations = new ArrayList<ASTNode>();
		
		for(IASTDeclaration nodeDeclaration: nodesDeclarations)
		{
			if(nodeDeclaration instanceof IASTFunctionDefinition)
			{
				fileDeclarations.add((ASTNode)nodeDeclaration);
			}
			
			if(nodeDeclaration instanceof CASTProblemDeclaration)
			{
				CASTProblemDeclaration p = (CASTProblemDeclaration) nodeDeclaration;
				fileDeclarations.add((ASTNode)nodeDeclaration);
			}
			
			if(nodeDeclaration instanceof IASTSimpleDeclaration)
			{	
				CASTSimpleDeclaration node = (CASTSimpleDeclaration)nodeDeclaration;
				
				IASTDeclSpecifier declarations = node.getDeclSpecifier();
				
				if((declarations instanceof CASTEnumerationSpecifier)
						|| (declarations instanceof CASTCompositeTypeSpecifier))
				{
					fileDeclarations.add((ASTNode)nodeDeclaration);
				}
			}
		}
		
		for(IASTDeclaration nodeDeclaration: nodesDeclarations)
		{
			if(nodeDeclaration instanceof IASTFunctionDefinition)
			{
				FunctionProcessor functions = new FunctionProcessor((CASTFunctionDefinition) nodeDeclaration);
				vxlFragmentDeclarations.append(functions.getVxlFragment());
			}
			
			if(nodeDeclaration instanceof IASTSimpleDeclaration)
			{
				//se não for declaração de função, é uma declaração simples
				Declarations declarations = new Declarations((CASTSimpleDeclaration)nodeDeclaration, false);
				enumLoc += declarations.getEnumLoc();
				vxlFragmentDeclarations.append(declarations.getVxlFragment());
			}
		}
		
		LOCCountPerEntity locCounter = new LOCCountPerEntity((ASTNode)translationUnit, CompilationUnitProcessor.commentList, CompilationUnitProcessor.sourceCode);
		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter, isInner);
		locKeeper.setHeadersLOC(headerLines, isInner, LOCManager.locParameters.contains(LOCParameters.HEADERS));
		locKeeper.increaseLOC(enumLoc);
		
		CommentsProcessorC comment = new CommentsProcessorC(fileDeclarations.toArray(new ASTNode[fileDeclarations.size()]), (ASTNode)translationUnit, false);
		
		//EntityType saveType = type.isInterface() ? EntityType.INTERFACE : isInner ? EntityType.INNER_CLASS : EntityType.CLASS;
		
		vxlFragment.append(VxlManager.startFile(fileName, locKeeper.getLOC()));
		vxlFragment.append(comment.getVxlFragment());
		vxlFragment.append(vxlFragmentDeclarations);
		
		if(LOCManager.locParameters.contains(LOCParameters.LOC) &&
				(isInner ? LOCManager.locParameters.contains(LOCParameters.INNER_FILES) : true)) {
			LOCManager.appendEntityLOCData("sem tipo", locKeeper, EntityType.INNER_CLASS);
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

	public static void setEnumLoc(Integer loc) {
		enumLoc += loc;
	}
	
	
	/*public static void extrator(File file) throws CoreException, IOException
	{
		StringBuilder content = new StringBuilder();
		String line;
		sourceCodeComments2 = new LinkedList<CommentUnitC>();
		
		FileInputStream fi = new FileInputStream(file);
		InputStreamReader is = new InputStreamReader(fi);
		BufferedReader br = new BufferedReader(is) ;
	
		while ((line = br.readLine()) != null)
		{
			content.append(line).append('\n');
		}
	       
		VxlManager.appendVXLFragment(VxlManager.startFile(file.getName(), "", 50));
		
		FileContent reader = FileContent.create(file.getAbsolutePath(), content.toString().toCharArray());
	     
		//gera a AST de um codigo fonte em C
		IASTTranslationUnit unit =  GCCLanguage.getDefault().getASTTranslationUnit(
				reader, new ScannerInfo(), IncludeFileContentProvider.getSavedFilesProvider(), 
				null, ILanguage.OPTION_IS_SOURCE_UNIT, new DefaultLogService());
		
		IASTPreprocessorStatement[] include = unit.getAllPreprocessorStatements();//extrai dados do preprocessador
		
		//verifica a existencia de alguma diretiva de preprocessador
		for(IASTPreprocessorStatement in : include)
		{
			if(in instanceof IASTPreprocessorIncludeStatement)
			{
				IASTPreprocessorIncludeStatement a = (IASTPreprocessorIncludeStatement)in;
				System.out.println("ifs PreprocessorIncludeStatement: " + include.length + " texto : " + a.getName());
			}
			
			if(in instanceof IASTPreprocessorElifStatement)
			{
				System.out.println("ifsPreprocessorElifStatement" + include.length + in.getRawSignature());
			}
			if(in instanceof IASTPreprocessorElseStatement)
			{
				System.out.println("ifsPreprocessorElseStatement" + include.length + in.getRawSignature());
			}
			if(in instanceof IASTPreprocessorEndifStatement)
			{
				System.out.println("ifsPreprocessorEndifStatement" + include.length + in.getRawSignature());
			}
			
			if(in instanceof IASTPreprocessorFunctionStyleMacroDefinition)
			{
				System.out.println("IASTPreprocessorFunctionStyleMacroDefinition" + include.length + in.getRawSignature());
			}
			if(in instanceof IASTPreprocessorIfStatement)
			{
				System.out.println("ifsPreprocessorIfStatement" + include.length + in.getRawSignature());
			}
			if(in instanceof IASTPreprocessorIfdefStatement)
			{
				System.out.println("ifsPreprocessorIfStatement" + include.length + in.getRawSignature());
			}
			if(in instanceof IASTPreprocessorIfndefStatement)
			{
				System.out.println("ifsPreprocessorIfndefStatement" + include.length + in.getRawSignature());
			}
			if(in instanceof IASTPreprocessorMacroDefinition)
			{
				System.out.println("ifsPreprocessorMacroDefinition" + include.length + in.getRawSignature());
			}
			
			if(in instanceof IASTPreprocessorMacroExpansion)
			{
				System.out.println("ifsPreprocessorMacroExpansion" + include.length + in.getRawSignature());
			}
			if(in instanceof IASTPreprocessorObjectStyleMacroDefinition)
			{
				System.out.println("ifsPreprocessorObjectStyleMacroDefinition" + include.length + in.getRawSignature());
			}
			if(in instanceof IASTPreprocessorPragmaStatement)
			{
				System.out.println("ifsPreprocessorPragmaStatement" + include.length + in.getRawSignature());
			}
			if(in instanceof IASTPreprocessorUndefStatement)
			{
				System.out.println("ifsPreprocessorUndefStatement" + include.length + in.getRawSignature());
			}
		}
		
		
		IASTDeclaration[] nodesDeclarations = unit.getDeclarations();//extrai todas as declarações do codigo
		
		commentList = Arrays.asList(unit.getComments());//extrai os comentarios do codigo
		
		fileDeclarations = new ArrayList<ASTNode>();
		
		//extrai as declarações
		Comentarios(unit, content.toString());
		
		for(IASTDeclaration nodeDeclaration: nodesDeclarations)
		{
			if(nodeDeclaration instanceof IASTFunctionDefinition)
			{
				fileDeclarations.add((ASTNode)nodeDeclaration);
			}
			
			if(nodeDeclaration instanceof IASTSimpleDeclaration)
			{	
				CASTSimpleDeclaration node = (CASTSimpleDeclaration)nodeDeclaration;
				
				IASTDeclSpecifier declarations = node.getDeclSpecifier();
				
				if((declarations instanceof CASTEnumerationSpecifier)
						|| (declarations instanceof CASTCompositeTypeSpecifier))
				{
					fileDeclarations.add((ASTNode)nodeDeclaration);
				}
			}
		}
		
		ASTNode type = (ASTNode) unit;
		
		CommentsProcessorC comment = new CommentsProcessorC(fileDeclarations.toArray(new ASTNode[fileDeclarations.size()]), type, false);
		
		VxlManager.appendVXLFragment(comment.getVxlFragment());
		
		for(IASTDeclaration nodeDeclaration: nodesDeclarations)
		{
			if(nodeDeclaration instanceof IASTFunctionDefinition)
			{
				FunctionProcessor functions = new FunctionProcessor((CASTFunctionDefinition) nodeDeclaration);
				VxlManager.appendVXLFragment(functions.getVxlFragment());//grava no vxl o vxl|Fragment
			}
			
			if(nodeDeclaration instanceof IASTSimpleDeclaration)
			{
				//se não for declaração de função, é uma declaração simples
				Declarations declarations = new Declarations((CASTSimpleDeclaration)nodeDeclaration, false);
				VxlManager.appendVXLFragment(declarations.getVxlFragment());
			}
		}
		
		VxlManager.appendVXLFragment(VxlManager.endFile());
		
		br.close();
	}
	
	public static void Comentarios(IASTTranslationUnit unit, String sourceCode)//processa os comentarios
	{
		sourceCodeComments = new LinkedList<CommentUnitC>();
		for(IASTComment element : (List<IASTComment>) Arrays.asList(unit.getComments()))
		{
			sourceCodeComments.add(new CommentUnitC((ASTNode)element, sourceCode));
		}
	}*/
}			 
				 
