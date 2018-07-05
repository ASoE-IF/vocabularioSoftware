package org.splab.vocabulary.extractor.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Classe responsável por armazenar erros de extração gerados e por salva-los em
 * um arquivo de log.
 * 
 * @author Israel Gomes de Lima
 *
 */
public class ErrorLogManager {
	/**
	 * Armazena os logs
	 */
	private static StringBuffer logMenssage = new StringBuffer();

	/**
	 * Adiciona uma mensagem de erro ao log
	 * 
	 * @param filePath
	 */
	public static void appendErro(String filePath, String errorManssage) {
		logMenssage.append(String.format("Erro no arquivo: %s\n", filePath));
		logMenssage.append(
				"Por favor, contacte os desenvolvedores e envie o arquivo supracitado com a mensagem abaixo:\n");
		logMenssage.append(errorManssage + "\n");
		logMenssage.append("Obs.: Geralmente esses erros pode ser calsados por sintaxes não reconhecidas pela ast. "+
		"Uma vez que o erro não interrompeu a execução do extrator.\n");
		logMenssage.append(
				"***********************************************************************************************\n\n");
	}

	/**
	 * Apaga todas as mensagens de erro.
	 */
	private static void clear() {
		logMenssage = new StringBuffer();
	}

	/**
	 * Salva a mensagem de log em um arquivo na pasta do software
	 */
	public static void save() {
		String menssage = "**********************************Log de Erros na Execução************************************\n\n";

		try {
			PrintStream file = new PrintStream(new FileOutputStream("./log.txt", false));
			file.print(menssage + logMenssage.toString());
			file.close();
			clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
