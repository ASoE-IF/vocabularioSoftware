package org.splab.vocabulary.extractor.processors;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration;
import org.splab.vocabulary.extractor.util.LOCManager;
import org.splab.vocabulary.extractor.util.VxlManager;
import org.splab.vocabulary.extractor.vloccount.EntityLOCKeeper;
import org.splab.vocabulary.extractor.vloccount.EntityType;
import org.splab.vocabulary.extractor.vloccount.LOCCountPerEntity;
import org.splab.vocabulary.extractor.vloccount.LOCParameters;

/**
 * This class is responsible for extracting informations from the structs's
 * body. All the informations extract should be Statement type.
 * 
 * StructProcessor extract informations such as: struct
 * 
 * @author Israel Gomes de Lima
 */

public class StructProcessor
{
	private StringBuffer vxlFragment;
	public StructProcessor(CPPASTCompositeTypeSpecifier compositeType, boolean scope)
	{
		vxlFragment = new StringBuffer();
		
		//Efetua contagem de loc por entidade
		LOCCountPerEntity locCounter = new LOCCountPerEntity((ASTNode)compositeType, CompilationUnitProcessor.commentList, CompilationUnitProcessor.sourceCode);
		EntityLOCKeeper locKeeper = new EntityLOCKeeper(locCounter, true);
		
		if(LOCManager.locParameters.contains(LOCParameters.LOC))
			LOCManager.appendEntityLOCData(compositeType.getName().toString(), locKeeper, EntityType.STRUCT);
		

		//Cria lista de membros
		List <IASTDeclaration> members = Arrays.asList(compositeType.getMembers());
			
		vxlFragment.append(VxlManager.startStruct(compositeType.getName().toString(), locKeeper.getLOC(), scope));
		
		vxlFragment.append(new CommentsProcessor((ASTNode)compositeType));

		//Extrai os membros das structs
		for(IASTDeclaration membersSimple : members)
		{
			if(membersSimple instanceof CPPASTSimpleDeclaration)
			{
				CPPASTSimpleDeclaration simpleMembers = (CPPASTSimpleDeclaration)membersSimple;
			
				IASTDeclarator[] structMembers = simpleMembers.getDeclarators();
				if(structMembers.length != 0)
				{
					vxlFragment.append(VxlManager.structMembers(structMembers[0].getName().toString(), scope));
				}
			}
		}
		
		vxlFragment.append(VxlManager.endStruct(scope));
	}

	public StringBuffer getVxlFragment()
	{
		return vxlFragment;
	}
}
