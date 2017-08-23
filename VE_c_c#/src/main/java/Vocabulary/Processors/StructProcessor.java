package Vocabulary.Processors;

import java.util.Arrays;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTProblemDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclaration;

import Vocabulary.Extractor.Util.LOCManager;
import Vocabulary.Extractor.Util.VxlManager;
import Vocabulary.vloccount.EntityLOCKeeper;
import Vocabulary.vloccount.EntityType;
import Vocabulary.vloccount.LOCCountPerEntity;
import Vocabulary.vloccount.LOCParameters;

public class StructProcessor
{
	private StringBuffer vxlFragment;
	private int structLoc;
	public StructProcessor(CASTCompositeTypeSpecifier compositeType, boolean scopeLocal)
	{
		vxlFragment = new StringBuffer();
		
		LOCCountPerEntity locCounter = new LOCCountPerEntity((ASTNode)compositeType, CompilationUnitProcessor.commentList, CompilationUnitProcessor.sourceCode);
		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter, true);
		locKeeper.setHeadersLOC(0, true, LOCManager.locParameters.contains(LOCParameters.HEADERS));

		boolean inner = LOCManager.locParameters.contains(LOCParameters.INNER_FILES);
		
		if(LOCManager.locParameters.contains(LOCParameters.LOC) && inner)
				structLoc = locKeeper.getLOC() + locKeeper.getAnnotationsLOC() + locKeeper.getInnerEntitiesLOC();
		
		final String NO_COMMENT = "";

		List <IASTDeclaration> members = Arrays.asList(compositeType.getMembers());

		vxlFragment.append(VxlManager.startStruct(compositeType.getName().toString(), "global", locKeeper.getLOC(), scopeLocal));
		
		vxlFragment.append(new CommentsProcessor((ASTNode)compositeType));

		for(IASTDeclaration membersSimple : members)
		{
			if(membersSimple instanceof CASTSimpleDeclaration)
			{
				CASTSimpleDeclaration simpleMembers = (CASTSimpleDeclaration)membersSimple;
			
				IASTDeclarator[] structMembers = simpleMembers.getDeclarators();
				if(structMembers.length != 0)
				{
					vxlFragment.append(VxlManager.structMembers(structMembers[0].getName().toString(), scopeLocal));
				}
			}
			if(membersSimple instanceof CASTProblemDeclaration)
			{
				CASTProblemDeclaration problema = (CASTProblemDeclaration)membersSimple;
			}
		}
		
		vxlFragment.append(VxlManager.endStruct(scopeLocal));
	}

	public StringBuffer getVxlFragment()
	{
		return vxlFragment;
	}
}
