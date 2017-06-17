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

public class StructProcessor//processador de struct
{
	private StringBuffer vxlFragment;
	private int structLoc;
	public StructProcessor(CASTCompositeTypeSpecifier compositeType, boolean scopeLocal)//processa as struct
	{
		vxlFragment = new StringBuffer();
		
//		LOCCountPerEntity locCounter = new LOCCountPerEntity((ASTNode)compositeType, CompilationUnitProcessor.commentList, CompilationUnitProcessor.sourceCode);
//		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter, true);
//		locKeeper.setHeadersLOC(0, true, LOCManager.locParameters.contains(LOCParameters.HEADERS));
//
//		boolean inner = LOCManager.locParameters.contains(LOCParameters.INNER_FILES);
//		
//		if(LOCManager.locParameters.contains(LOCParameters.LOC) && inner)
//				structLoc = locKeeper.getLOC() + locKeeper.getAnnotationsLOC() + locKeeper.getInnerEntitiesLOC();
//		
//		final String NO_COMMENT = ""; // enum constants don't have javadoc associations

		List <IASTDeclaration> members = Arrays.asList(compositeType.getMembers());//obtem um array com os membros da struct

		vxlFragment.append(VxlManager.startStruct(compositeType.getName().toString(), "global", /*locKeeper.getLOC()*/0, scopeLocal));
		
		vxlFragment.append(new CommentsProcessorC((ASTNode)compositeType));
		
		for(IASTDeclaration membersSimple : members)//processa os membros da struct
		{
			if(membersSimple instanceof CASTSimpleDeclaration)
			{
				CASTSimpleDeclaration simpleMembers = (CASTSimpleDeclaration)membersSimple;//converte tipos
			
				IASTDeclarator[] structMembers = simpleMembers.getDeclarators();//obtem a declaração do membro
				if(structMembers.length != 0)//se não existirem declarações deve retornar imediatamente ao chamador
				{
					vxlFragment.append(VxlManager.structMembers(structMembers[0].getName().toString(), scopeLocal));//grava no vxlFragment
				}
			}
			if(membersSimple instanceof CASTProblemDeclaration)
			{
				CASTProblemDeclaration problema = (CASTProblemDeclaration)membersSimple;
				//System.out.println("======================================" + problema.getRawSignature());
			}
		}
		
		vxlFragment.append(VxlManager.endStruct(scopeLocal));
	}

	public StringBuffer getVxlFragment()//retorna o vxlFragment
	{
		return vxlFragment;
	}
}
