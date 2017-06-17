package org.splabs.vocabulary.vxl.extractor.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * A set of file utilities
 * @author gustavojss / Tercio de Melo
 */
public class FileUtil {

	/**
	 * Saves the vocabulary content in the specified file
	 * @param fileContent the vocabulary
	 * @param vxlFileName the file name where this vocabulary will be saved
	 */
	public static void saveVXLFile(StringBuffer fileContent, String vxlFileName) {
		try {
			PrintStream out = new PrintStream(new FileOutputStream(vxlFileName, false));
			out.print(fileContent.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public static void saveLOCFile(String locFileContent, String locFileName) {
		try {
			PrintStream out = new PrintStream(new FileOutputStream(locFileName, false));
			out.print(locFileContent+"\n");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}