package Vocabulary.Processors;

import java.util.LinkedList;

import org.eclipse.cdt.core.dom.ast.IASTArrayDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IVariable;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompositeTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTEnumerationSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTTypedefNameSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CVariable;

import Vocabulary.Extractor.Util.VxlManager;

public class Declarations//processador de declarações
{
	StringBuffer vxlFragment;
	private int enumLoc = 0;

	public String variablesTyes(IASTName variableName)
	{
		IVariable e = new CVariable(variableName);
		String permissao;
		
		if(e.isAuto())
			permissao = "auto";
		else
			if(e.isStatic())
			permissao = "static";
			else
				if(e.isRegister())
					permissao = "register";
				else
					permissao = "auto";
		
		return permissao;
	}
	public Declarations(CASTSimpleDeclaration node, boolean scopeLocal)//processa as declarações
	{
		vxlFragment = new StringBuffer();
		IASTDeclSpecifier declarations = node.getDeclSpecifier();//obtem o tipo especifico de um para diferenciar cada CASTSimpleDeclaration
		
		if(declarations instanceof CASTEnumerationSpecifier)//testa se é um enum
		{
			EnumProcessor enumProcessor = new EnumProcessor((CASTEnumerationSpecifier)declarations, scopeLocal, true, 0);//processa a enum
			vxlFragment.append(enumProcessor.getVxlFragment());//grava as informações da enum
			enumLoc += enumProcessor.getEnumLoc();
			if(scopeLocal == true)
			{
				FunctionProcessor.listDeclarations.add((ASTNode)declarations);
			}
		}
		
		if(declarations instanceof CASTCompositeTypeSpecifier)//testa se é uma struct ou union
		{
			CASTCompositeTypeSpecifier compositeType = (CASTCompositeTypeSpecifier)declarations;//casting de tipos, 
			
			switch(compositeType.getKey())//diferencia struct de union, 1 = struct, 2 = union
			{
				case 1:
					StructProcessor struct = new StructProcessor((CASTCompositeTypeSpecifier)declarations, scopeLocal);//processa structs
					vxlFragment.append(struct.getVxlFragment());//grava no vxlFragment
					break;
				case 2:
					UnionProcessor union = new UnionProcessor((CASTCompositeTypeSpecifier)declarations, scopeLocal);//processa unions
					vxlFragment.append(union.getVxlFragment());//grava no vxlFragment
					break;
			}
			if(scopeLocal == true)
			{
				FunctionProcessor.listDeclarations.add((ASTNode)declarations);
			}
		}
		
		if(declarations instanceof CASTTypedefNameSpecifier)//processa tipos renomeados com typedef
		{
			CASTTypedefNameSpecifier variables = (CASTTypedefNameSpecifier) declarations;
					
			if(scopeLocal == true)//testa se a variavel local ou global
			{
				vxlFragment.append(VxlManager.localVariable(variables.getName().toString(), 5));//grava no vxlFragment as informações da variavel
			}
			else
				vxlFragment.append(VxlManager.globalVariables(variables.getName().toString(), variablesTyes(variables.getName()), "eu"));//grava no vxlFragment as informações da variavel
		}
		
		if(declarations instanceof CASTSimpleDeclSpecifier)//processa os tipos de declarações
		{
			IASTDeclarator[] simpleDeclarations = node.getDeclarators();//array com as declarações
			
			if(simpleDeclarations.length == 0)//se não existirem declarações deve retornar imediatamente ao chamador
			{
				return;//retorna
			}
		
			if(simpleDeclarations[0] instanceof IASTArrayDeclarator)//testa se a variavel é um array
			{
				if(scopeLocal == true)//testa se é local ou global
				{
					vxlFragment.append(VxlManager.localVariable(simpleDeclarations[0].getName().toString(), 5));//grava no vxlFragment
				}
				else
					vxlFragment.append(VxlManager.globalVariables(simpleDeclarations[0].getName().toString(), variablesTyes(simpleDeclarations[0].getName()), "eu"));//grava no vxlFragment
			}
			else
			{
				if(simpleDeclarations[0] instanceof IASTFunctionDeclarator)//testa se é um prototipo de função
				{
					if(scopeLocal == true)//testa se é local ou global
					{
						vxlFragment.append(VxlManager.localVariable(simpleDeclarations[0].getName().toString(), 5));//grava no vxlFragment
					}
					else
						vxlFragment.append(VxlManager.globalVariables(simpleDeclarations[0].getName().toString(), variablesTyes(simpleDeclarations[0].getName()), "eu"));//grava no vxlFragment
				}
				else//se não for nenhum dos tipos acima, ela é uma declaração de variavel comum
				{
					if(scopeLocal == true)//testa se é local ou global
					{
						vxlFragment.append(VxlManager.localVariable(simpleDeclarations[0].getName().toString(), 5));//grava no vxlFragment
					}
					else
					{
						vxlFragment.append(VxlManager.globalVariables(simpleDeclarations[0].getName().toString(), variablesTyes(simpleDeclarations[0].getName()), "eu"));//grava no vxlFragment
					}
				}
			}
		}
	}

	public StringBuffer getVxlFragment()//retorna o vxlFragment
	{
		return vxlFragment;
	}
	
	public int getEnumLoc()
	{
		return enumLoc;
	}
}
