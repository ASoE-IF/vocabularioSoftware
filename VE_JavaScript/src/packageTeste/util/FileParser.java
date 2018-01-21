package packageTeste.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.wst.jsdt.core.dom.AST;
import org.eclipse.wst.jsdt.core.dom.ASTParser;
import org.eclipse.wst.jsdt.core.dom.JavaScriptUnit;

import packageTeste.processors.ASTRootProcessor;

public class FileParser {
	
	public static char[] read(String filePath) {
		StringBuffer sourceCode = new StringBuffer();
		
		try {
			InputStream stream = new FileInputStream(filePath);
			BufferedReader in = new BufferedReader(new InputStreamReader(stream));
			String line = "";
			
			while (null != (line = in.readLine()))
				sourceCode.append(line + "\n");
			
			in.close();
			
		} catch (IOException ioerr) {
			System.out.println(ioerr);
		}
		
		return sourceCode.toString().toCharArray();
	}
	
	public static void parse(String filePath) {
		char[] sourceCode = read(filePath);
		
		// configurando parser
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(sourceCode);
	
		// criando AST
		JavaScriptUnit astRoot = (JavaScriptUnit) parser.createAST(new NullProgressMonitor());
		
		ASTRootProcessor.process(astRoot);
	}
}
