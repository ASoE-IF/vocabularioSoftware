package packageTeste.processors;

import org.eclipse.wst.jsdt.core.dom.FunctionDeclaration;
import org.eclipse.wst.jsdt.core.dom.SingleVariableDeclaration;

import java.util.List;

public class FunctionProcessor {
	
	@SuppressWarnings("unchecked")
	public static void process(FunctionDeclaration funcDec) {
		System.out.println("\nFunção encontrada:");

		System.out.println("\t" + extractName(funcDec));

		System.out.println("parâmetros:");
		for (String parameter : extractParameters(funcDec))
			System.out.println("\t" + parameter);
	}

	private static String extractName(FunctionDeclaration funcDec) {
		return funcDec.getMethodName().toString();
	}

	private static String[] extractParameters(FunctionDeclaration funcDec) {
		List<SingleVariableDeclaration> parameters = (List<SingleVariableDeclaration>) funcDec.parameters();
	    String[] stringParameters = new String[parameters.size()];

	    for (int i=0; i < parameters.size(); i++) {
	    	stringParameters[i] = parameters.get(i).toString();
		}

		return stringParameters;
	}
}
