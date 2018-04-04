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

			} else if (statement instanceof TypeDeclaration) {
				TypeProcessor.process((TypeDeclaration) statement);

			} else if (statement instanceof VariableDeclarationStatement) {
				process((VariableDeclarationStatement) statement);

			} else {
				System.out.println(
						"\nASTNode desconhecida encontrada (tipo "+statement.getNodeType()+"):"+
								"\n" + statement.toString()
				);
			}
		}
	}

	private static void process(VariableDeclarationStatement varDeclStmt) {
		System.out.println("\nVariableDeclarationStatement encontrado:");

		System.out.println("\tfragmentos:");
		List<VariableDeclarationFragment> fragments = varDeclStmt.fragments();
		for (VariableDeclarationFragment fragment : fragments) {
			System.out.println("\t\t" + fragment.getName());
//
//			VariableDeclarationExpression varDeclInitExp = (VariableDeclarationExpression) fragment.getInitializer();
//			List<VariableDeclarationFragment> varDeclInitExpFragments = varDeclInitExp.fragments();
//			for (VariableDeclarationFragment expression_fragment : varDeclInitExpFragments) {
//				System.out.println("\t\t\t" + expression_fragment.toString());
//			}
		}
	}
}
