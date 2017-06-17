package Vocabulary.Processors;


import java.io.IOException;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTAmbiguousStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTBreakStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCaseStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompoundStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTCompoundStatementExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTContinueStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTDeclarationStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTDefaultStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTDoStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTExpressionStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTForStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTIfStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTReturnStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclaration;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSwitchStatement;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTWhileStatement;
import org.eclipse.core.runtime.CoreException;

public class BodyProcessor
{
	private StringBuffer vxlFragment;
	
	public BodyProcessor()
	{
		vxlFragment = new StringBuffer();
	}
	
	public BodyProcessor(CASTCompoundStatement node)//processa o corpo do codigo
	{
		vxlFragment = new StringBuffer();
		IASTStatement[] statements = node.getStatements();//extrair as informações do corpo
		
		for(IASTStatement state: statements )//percorre as informaçõs do corpo do codigo
		{
			if(state instanceof CASTDeclarationStatement)//captura declarações de variaveis ou algo do tipo
			{
				DeclarationStatement((CASTDeclarationStatement) state);
			}
			
//			if(state instanceof CASTExpressionStatement)//capitura expressões
//			{
//				CASTExpressionStatement exp = (CASTExpressionStatement)state;
//				ExpressionProcessor.extractExpression((IASTExpression)exp.getExpression());
//				//vxlFragment.append(ExpressionStatement((IASTExpression) state));
//			}
//			
			if (state instanceof CASTExpressionStatement)
			{
				CASTExpressionStatement state2 = (CASTExpressionStatement)state;

				ExpressionProcessor.extractExpression(state2.getExpression());
			}
			
			if(state instanceof CASTAmbiguousStatement)
			{
				//System.out.println("Ambiguos : ");
			}
		
			if(state instanceof CASTBreakStatement)//capitura "break" no codigo
			{
				//System.out.println("Break : ");
			}
		
			if(state instanceof CASTCaseStatement)//capitura "case" no codigo
			{
				//System.out.println("Case : ");
			}
			
			if(state instanceof CASTCompoundStatementExpression)
			{
				System.out.println("Compounds : ");
			}
	
			if(state instanceof CASTContinueStatement)
			{
				//System.out.println("Continue : ");//capitura "continue" no codigo
			}
			
			if(state instanceof CASTDefaultStatement)//capitura "default" no codigo
			{
				//System.out.println("Default : ");
			}
			
			if(state instanceof CASTDoStatement)
			{
				//System.out.println("Do : ");
			}
		
			if(state instanceof CASTForStatement)//capitura laços for
			{
				forStatement((CASTForStatement) state);
			}
		
			if(state instanceof CASTIfStatement)//capitura comandos if
			{
				try
				{
					IfStatement((CASTIfStatement) state);//captura possiveis exceções
				}
				catch(CoreException e)
				{
					
				}
				catch(IOException e)
				{
					
				}
			}
		
			if(state instanceof CASTReturnStatement)//capitura "return" no codigo
			{
				//System.out.println("Return : ");
			}
		
			if(state instanceof CASTSwitchStatement)//capitura "switch" no codigo
			{
				SwitchStatement((CASTSwitchStatement)state);
			}
		
			if(state instanceof CASTWhileStatement)//capitura comandos while no codigo
			{
				WhileStatement((CASTWhileStatement) state);
			}
		} 
	}
	
	public void WhileStatement(CASTWhileStatement node)//processa os comandos while encontrados
	{	
		if(node.getBody() instanceof CASTCompoundStatement)//processa o corpo do while
		{
			BodyProcessor body = new BodyProcessor((CASTCompoundStatement) node.getBody());//envia o corpo do while para extração de seus dados
			vxlFragment.append(body.getVxlFragment());//adiciona o vocabulario extraido ao vxl fragment
		}
	}

	public void forStatement(CASTForStatement node)//processa laços for
	{
	
		if(node.getBody() instanceof CASTCompoundStatement)//processa o corpo do for
		{
			BodyProcessor body = new BodyProcessor((CASTCompoundStatement)node.getBody());//extrai o vocabulario do corpo do for
			
			ExpressionProcessor.extractExpression(node.getConditionExpression());
			vxlFragment.append(body.getVxlFragment());//adiciona ao vxlFragment o vocabulario extraido
		}
	}
	
	public void IfStatement(CASTIfStatement node) throws CoreException, IOException//processa comandos if
	{
		BodyProcessor body;//variavel do para processao o corpo do if
		
		if(node.getThenClause() instanceof CASTCompoundStatement)//processa o corpo do if
		{
			body = new BodyProcessor((CASTCompoundStatement)node.getThenClause());//extrai as informações do corpo do if
			vxlFragment.append(body.getVxlFragment());//adiciona ao vxlFragment
			ExpressionProcessor.extractExpression(node.getConditionExpression());
			
		}
	
		/*if(node.getElseClause() != null)//processa else se existir
		{
			System.out.println(node.getElseClause() instanceof CASTCompoundStatement);
			
			body = new BodyProcessor((CASTCompoundStatement)node.getElseClause());//extrai vocabulario do corpo do else
			vxlFragment.append(body.getVxlFragment());//adiciona vocabulario ao vxlfragment
		}*/
	}
	
	public void SwitchStatement(CASTSwitchStatement node)//processa comandos switch
	{
		if(node.getBody() instanceof CASTCompoundStatement)//processa o corpo do switch
		{
			BodyProcessor body = new BodyProcessor((CASTCompoundStatement)node.getBody());//envia o corpo para processamento
			vxlFragment.append(body.getVxlFragment());//grava nop vxlFragment as informações extraidas do switch
		}
	}
	
	public void DeclarationStatement(CASTDeclarationStatement node)//processa declarações de variaveis ou algo do tipo
	{
		IASTDeclaration nodesDeclared = node.getDeclaration();//obtem a declaração
		
		if(nodesDeclared instanceof CASTSimpleDeclaration)//processa a declaração
		{
			Declarations declarations = new Declarations((CASTSimpleDeclaration)nodesDeclared, true);//extrai as informações correspondentes as declarações
			
			vxlFragment.append(declarations.getVxlFragment());//grava as informações extraidas no vxlFragmet
		}
	}
	
	public StringBuffer getVxlFragment()//retorna o vxlFragment
	{
		return vxlFragment;
	}
}
