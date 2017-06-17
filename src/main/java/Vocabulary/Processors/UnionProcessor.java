package Vocabulary.Processors;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTProblemDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclaration;

import Vocabulary.Extractor.Util.VxlManager;

public class UnionProcessor//processa de union
{
	private StringBuffer vxlFragment;
	
	public UnionProcessor(CASTCompositeTypeSpecifier compositeType, boolean scopeLocal)//processa union
	{
		vxlFragment = new StringBuffer();
		IASTDeclaration[] members = compositeType.getMembers();//extrai os membros da union

		vxlFragment.append(VxlManager.startUnion(compositeType.getName().toString(), "global", "null", scopeLocal));
		
		vxlFragment.append(new CommentsProcessorC((ASTNode)compositeType));
		
		for(IASTDeclaration membersSimple : members)//processa os membros da union
		{
			if(membersSimple instanceof CASTSimpleDeclaration)
			{
				CASTSimpleDeclaration simpleMembers = (CASTSimpleDeclaration)membersSimple;//converte tipos
				IASTDeclarator[] unionMembers = simpleMembers.getDeclarators();//obtem a declaração do membro da union
			
				if(unionMembers.length != 0)//se não existirem declarações deve retornar imediatamente ao chamador
				{
					vxlFragment.append(VxlManager.structMembers(unionMembers[0].getName().toString(), scopeLocal));//grava no vxlFragment
				}
			}
			
			if(membersSimple instanceof CASTProblemDeclaration)
			{
				CASTProblemDeclaration problema = (CASTProblemDeclaration)membersSimple;
				//System.out.println("======================================" + problema.getRawSignature());
			}
		}
		
		vxlFragment.append(VxlManager.endUnion(scopeLocal));
	}

	public StringBuffer getVxlFragment()//retorna o vxlFragment
	{
		return vxlFragment;
	}
}