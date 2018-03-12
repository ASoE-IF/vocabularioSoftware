package packageTeste.processors;

import org.eclipse.wst.jsdt.core.dom.*;

import packageTeste.processors.FunctionProcessor;

import java.util.List;

public class StatementProcessor {

    public static void process(JavaScriptUnit jSUnit) {
		List<ASTNode> statements = jSUnit.statements();
		process(statements);
	}

	private static void process(List<ASTNode> statements) {

		for (ASTNode statement : statements) {

			if (statement instanceof FunctionDeclaration) {
			    FunctionProcessor.process((FunctionDeclaration) statement);

			} else {
				System.out.println(
						"Elemento não conhecido encontrado:" +
								"\n" + statement.toString()
				);
			}
		}
	}
}
