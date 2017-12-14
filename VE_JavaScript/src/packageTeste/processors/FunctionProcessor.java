package packageTeste.processors;

import org.eclipse.wst.jsdt.core.dom.FunctionDeclaration;
import org.eclipse.wst.jsdt.core.dom.SingleVariableDeclaration;
import org.eclipse.wst.jsdt.core.dom.VariableDeclarationStatement;

import java.util.List;

public class FunctionProcessor {
	
	@SuppressWarnings("unchecked")
	public static void process(FunctionDeclaration functionDeclaration) {
		System.out.println("\nFunção encontrada:");

		System.out.println("nome:"
				+ "\n\t" + functionDeclaration.getMethodName());

		System.out.println("parâmetros:");
		for (SingleVariableDeclaration parameter : (List<SingleVariableDeclaration>) functionDeclaration.parameters())
			System.out.println("\t" + parameter.getName());

		System.out.println("variáveis:");
		for (VariableDeclarationStatement statement : (List<VariableDeclarationStatement>) functionDeclaration.getBody().statements())
			System.out.println("\t" + statement);
	}
}
