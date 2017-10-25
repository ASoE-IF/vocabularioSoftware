package packageTeste;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.wst.jsdt.core.dom.AST;
import org.eclipse.wst.jsdt.core.dom.ASTNode;
import org.eclipse.wst.jsdt.core.dom.ASTParser;
import org.eclipse.wst.jsdt.core.dom.Block;
import org.eclipse.wst.jsdt.core.dom.FunctionDeclaration;
import org.eclipse.wst.jsdt.core.dom.JavaScriptUnit;
import org.eclipse.wst.jsdt.core.dom.SingleVariableDeclaration;
import org.eclipse.wst.jsdt.core.dom.VariableDeclarationStatement;

public class MainTest {
	@SuppressWarnings("unchecked")
	public static void main(String[] args){
		List<VariableDeclarationStatement> functionVariables;
		List<SingleVariableDeclaration> functionParameters = null;
		Block functionBody = null;
		String sourceCode = new String("function hello(argument1,argument2){var1;};");
		
		// configurando parser
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(sourceCode.toCharArray());
		// criando AST
		JavaScriptUnit unit = (JavaScriptUnit) parser.createAST(new NullProgressMonitor());
		
		List<ASTNode> node = unit.statements();
		System.out.println("function name: " + ((FunctionDeclaration) node.get(0)).getMethodName());
		
		functionParameters = ((FunctionDeclaration) node.get(0)).parameters();
		
		// imprimindo parâmetros
		for(int j=0; j<functionParameters.size(); j++) { 
			System.out.println("argument "+j+": " + functionParameters.get(j).getName());
		}

		functionBody = ((FunctionDeclaration) node.get(0)).getBody();
		functionVariables = functionBody.statements();
		// imprimindo variáveis da função
		for (int o=0; o<functionVariables.size(); o++)
			System.out.println("functions variables: " + functionVariables.get(o));	
	}
}
				 
				 

