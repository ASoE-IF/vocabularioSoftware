package Vocabulary.Processors;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
import org.eclipse.cdt.internal.core.dom.parser.c.CASTProblemDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclaration;
import Vocabulary.Extractor.Util.CommentUnit;
import Vocabulary.Extractor.Util.LOCManager;
import Vocabulary.Extractor.Util.VxlManager;
import Vocabulary.vloccount.EntityLOCKeeper;
import Vocabulary.vloccount.EntityType;
import Vocabulary.vloccount.LOCCountPerEntity;
import Vocabulary.vloccount.LOCParameters;

public class FileProcessor
{
	protected static List<CommentUnit> sourceCodeComments;
	private StringBuffer vxlFragment;
	
	public FileProcessor()
	{
		vxlFragment = new StringBuffer("");
	}
	
	public FileProcessor(IASTTranslationUnit translationUnit, String fileName, int headerLines)
	{
		vxlFragment = new StringBuffer();
		sourceCodeComments = new LinkedList<CommentUnit>();
		
		Integer enumLoc = 0;
		
		IASTDeclaration[] declarations = translationUnit.getDeclarations();

		ASTNode type = (ASTNode) translationUnit;
		
		LOCCountPerEntity locCounter = new LOCCountPerEntity(type, CompilationUnitProcessor.commentList, CompilationUnitProcessor.sourceCode);
		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter, true);
		locKeeper.setHeadersLOC(headerLines, true, LOCManager.locParameters.contains(LOCParameters.HEADERS));
		locKeeper.increaseLOC(enumLoc);
		
		vxlFragment.append(VxlManager.startFile(fileName, locKeeper.getLOC()));
		
		IASTPreprocessorStatement[] directives = translationUnit.getAllPreprocessorStatements();
		DirectivesProcessor.extractDirectives(directives);
		vxlFragment.append(DirectivesProcessor.getVxlFragment());
		
		
		vxlFragment.append((new CommentsProcessor(declarationList(declarations), type, false).getVxlFragment()));
		
		for(IASTDeclaration nodeDeclaration: declarations)
		{
			if(nodeDeclaration instanceof IASTFunctionDefinition)
			{
				FunctionProcessor functions = new FunctionProcessor((CASTFunctionDefinition) nodeDeclaration);
				vxlFragment.append(functions.getVxlFragment());
			}
			
			if(nodeDeclaration instanceof IASTSimpleDeclaration)
			{
				Declarations.declarations((CASTSimpleDeclaration)nodeDeclaration, false);

				vxlFragment.append(Declarations.getVxlFragment());
			}
		}
	
		if(LOCManager.locParameters.contains(LOCParameters.LOC) && (true ? LOCManager.locParameters.contains(LOCParameters.INNER_FILES) : true))
		{
			LOCManager.appendEntityLOCData("sem tipo", locKeeper, EntityType.INNER_CLASS);
		}
		
		vxlFragment.append(VxlManager.endFile());
	}
	
	public StringBuffer getVxlFragment()
	{
		return vxlFragment;
	}
		
	@Override
	public String toString()
	{
		return vxlFragment.toString();
	}
	

	public static List<ASTNode> declarationList(IASTDeclaration[] declarations)
	{
		List<ASTNode> fileDeclarations = new ArrayList<ASTNode>();
		
		for(IASTDeclaration declaration: declarations)
		{
			if(declaration instanceof IASTFunctionDefinition)
			{
				fileDeclarations.add((ASTNode)declaration);
			}
			
			if(declaration instanceof CASTProblemDeclaration)
			{
				CASTProblemDeclaration p = (CASTProblemDeclaration) declaration;
				fileDeclarations.add((ASTNode)declaration);
			}
			
			if(declaration instanceof IASTSimpleDeclaration)
			{	
				CASTSimpleDeclaration node = (CASTSimpleDeclaration)declaration;
				
				IASTDeclSpecifier declarationSpecifier = node.getDeclSpecifier();
				
				if((declarationSpecifier instanceof CASTEnumerationSpecifier) || (declarationSpecifier instanceof CASTCompositeTypeSpecifier))
				{
					fileDeclarations.add((ASTNode)declaration);
				}
			}
		}
		
		return fileDeclarations;
	}
}			 
				 
