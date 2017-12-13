package packageTeste;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.wst.jsdt.core.dom.AST;
import org.eclipse.wst.jsdt.core.dom.ASTNode;
import org.eclipse.wst.jsdt.core.dom.ASTParser;
import org.eclipse.wst.jsdt.core.dom.FunctionDeclaration;
import org.eclipse.wst.jsdt.core.dom.JavaScriptUnit;
import org.eclipse.wst.jsdt.core.dom.SingleVariableDeclaration;
import org.eclipse.wst.jsdt.core.dom.VariableDeclarationStatement;

public class MainTest {
	@SuppressWarnings("unchecked")
	public static void main(String[] args){
		StringBuffer sourceCode = new StringBuffer();
        try {
                InputStream stream = new FileInputStream("src/packageTeste/teste.js");
                BufferedReader in = new BufferedReader(new InputStreamReader(stream));

                String line = "";
                while (line != null) {
                        line = in.readLine();
                        sourceCode.append(line+"\n");
                }
                in.close();
        } catch(IOException ioerr) {
                System.out.println("Erro de I/O: " + ioerr);
        }

        // configurando parser
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setSource(sourceCode.toString().toCharArray());

		// criando AST
		JavaScriptUnit astRoot = (JavaScriptUnit) parser.createAST(new NullProgressMonitor());
		
		// imprimindo informações de cada nó
		for(ASTNode node : astRoot.statements()) {
			if (node.getNodeType() == ASTNode.FUNCTION_DECLARATION) {
				System.out.println("\nFunção encontrada:");
				
				FunctionDeclaration function = (FunctionDeclaration) node;
				
				System.out.println("nome: \n"
				+ "\t"+function.getMethodName());
				
				System.out.println("parâmetros:");
				for(SingleVariableDeclaration parameter : (List<SingleVariableDeclaration>) function.parameters())
					System.out.println("\t"+parameter.getName());
				
				System.out.println("variáveis:");
				for (VariableDeclarationStatement statement : (List<VariableDeclarationStatement>) function.getBody().statements())
					System.out.println("\t"+statement);
				
			} else {
				System.out.println("\nEstrutura ainda não conhecida encontrada");
			}
		}
	}
}
				 
				 

