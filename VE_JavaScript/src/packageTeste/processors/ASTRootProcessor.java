package packageTeste.processors;

import org.eclipse.wst.jsdt.core.dom.ASTNode;
import org.eclipse.wst.jsdt.core.dom.FunctionDeclaration;
import org.eclipse.wst.jsdt.core.dom.JavaScriptUnit;

import packageTeste.processors.FunctionProcessor;

public class ASTRootProcessor {
	public static void process(JavaScriptUnit astRoot) {
		
		for (ASTNode node : astRoot.statements()) {
			switch (node.getNodeType()) {
			case ASTNode.FUNCTION_DECLARATION:
				FunctionProcessor.process((FunctionDeclaration) node);
				break;
			default:
				System.out.println(""
						+ "\nEstrutura ainda não conhecida encontrada");
			}
		}
	}
}
