package org.splab.vocabulary.extractor.browsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.TreeMap;

import org.splab.vocabulary.extractor.processors.CompilationUnitProcessor;
import org.splab.vocabulary.extractor.util.VxlManager;

/**
 * 
 * @author Tercio de Melo
 */
public class CompilationUnitToPackageLinker {
	private static Map<String, StringBuffer> vxlPerPackage;
	
	public static void link(String pathRoot, File javaFile) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(javaFile));
			StringBuffer source = new StringBuffer(); 
			String line = null;
			
			while (null != (line = in.readLine())) {
				source.append(line + "\n");
			}
			in.close();
			CompilationUnitProcessor.setSourceCode(source.toString());
			
			String fileName = javaFile.getAbsolutePath();
			StringBuffer vxlFragment = CompilationUnitParser.parse(source.toString().toCharArray(), fileName, pathRoot);
			String packageName = CompilationUnitParser.getPackage(pathRoot, fileName);

			linkCompilationUnitToPackage(packageName, vxlFragment);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void linkCompilationUnitToPackage(String packageName, StringBuffer vxlFragment) {
		if (!vxlPerPackage.containsKey(packageName)) {
			vxlPerPackage.put(packageName, new StringBuffer(VxlManager.startPackage(packageName)));
		}
		vxlPerPackage.get(packageName).append(vxlFragment);
	}
	
	protected static Map<String, StringBuffer> getVxlPerPackage() {
		return vxlPerPackage;
	}
	
	public static void reset() {
		vxlPerPackage = new TreeMap<String, StringBuffer>();
	}
}
