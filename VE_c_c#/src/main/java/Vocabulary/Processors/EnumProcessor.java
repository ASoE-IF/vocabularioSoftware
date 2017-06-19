package Vocabulary.Processors;

import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTEnumerationSpecifier;

import Vocabulary.Extractor.Util.LOCManager;
import Vocabulary.Extractor.Util.VxlManager;
import Vocabulary.vloccount.EntityLOCKeeper;
import Vocabulary.vloccount.EntityType;
import Vocabulary.vloccount.LOCCountPerEntity;
import Vocabulary.vloccount.LOCParameters;

public class EnumProcessor//processador de enums
{
	private StringBuffer vxlFragment;
	private int enumLoc;
	
	public EnumProcessor(CASTEnumerationSpecifier enumDeclaration, boolean scopeLocal, boolean isInner, int headerLines)
	{
		vxlFragment = new StringBuffer();
		
//		LOCCountPerEntity locCounter = new LOCCountPerEntity((ASTNode)enumDeclaration, CompilationUnitProcessor.commentList, CompilationUnitProcessor.sourceCode);
//		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter, isInner);
//		locKeeper.setHeadersLOC(headerLines, isInner, LOCManager.locParameters.contains(LOCParameters.HEADERS));
//
//		boolean inner = (isInner ? LOCManager.locParameters.contains(LOCParameters.INNER_FILES) : true);
//		
//		if(LOCManager.locParameters.contains(LOCParameters.LOC) && inner) {
//			if(isInner) {
//				enumLoc = locKeeper.getLOC() + locKeeper.getAnnotationsLOC() + locKeeper.getInnerEntitiesLOC();
//			} else {
//				enumLoc = 0;
//				LOCManager.appendEntityLOCData(enumDeclaration.getName().toString(), locKeeper, EntityType.ENUM);
//			}
//		}
//		
		final String NO_COMMENT = ""; // enum constants don't have javadoc associations

		//vxlFragment = new StringBuffer();
		
		
		IASTEnumerator[] enumerators = enumDeclaration.getEnumerators();//obtem um array com as constantes da enum

		vxlFragment.append(VxlManager.startEnum(enumDeclaration.getName().toString(), "null", /*locKeeper.getLOC()*/0, scopeLocal));
		vxlFragment.append((new CommentsProcessorC((ASTNode)enumDeclaration)).getVxlFragment());
		
		
		//vxlFragment.append(VxlManager.startEnum(enumDeclaration.getName().toString(), "null", 50, scopeLocal));
		
		//vxlFragment.append(new CommentsProcessorC((ASTNode)enumDeclaration).getVxlFragment());
		
		for(IASTEnumerator constEnumerators : enumerators)//percorre as constantes da enum
		{
			vxlFragment.append(VxlManager.constant(constEnumerators.getName().toString(), "Não disponivel", NO_COMMENT, scopeLocal));////grava no vxlFragment as consantes enum
		}
		
		vxlFragment.append(VxlManager.endEnum(scopeLocal));
		
		FileProcessor.setEnumLoc(enumLoc);
	}

	public int getEnumLoc()
	{
		return enumLoc;
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
}
