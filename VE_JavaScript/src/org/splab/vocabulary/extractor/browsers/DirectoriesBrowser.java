package org.splab.vocabulary.extractor.browsers;

import java.io.File;

import org.splab.vocabulary.extractor.util.VxlManager;

/**
 * 
 * @author Tercio de Melo
 */
public class DirectoriesBrowser {

	public static void browse(String projectPath, String projectName, String projectRevision) {
		CompilationUnitToPackageLinker.reset();
		VxlManager.appendVXLFragment("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + VxlManager.startProject(projectName, projectRevision));
		
		File projectFolder = new File(projectPath);
		if (!projectFolder.isDirectory())
			throw new IllegalArgumentException("the given path is not a folder: " + projectPath);

		
		for (File file : projectFolder.listFiles()) {
			browseDirectory(projectFolder.getAbsolutePath(), file);
		}
		
		for (String packageName : CompilationUnitToPackageLinker.getVxlPerPackage().keySet()) {
			System.out.println("Processing Package: " + packageName);
			
			StringBuffer vxlFragment = CompilationUnitToPackageLinker.getVxlPerPackage().get(packageName);
			vxlFragment.append(VxlManager.endPackage());
			VxlManager.appendVXLFragment(vxlFragment);
		}
		
		VxlManager.appendVXLFragment(VxlManager.endProject());
	}
	
	
	private static void browseDirectory(String directory, File file) {
		if (file.isDirectory()) {
			System.out.println(file.getAbsolutePath());
			
			for (File containingFile : file.listFiles()) {
				browseDirectory(directory, containingFile);
			}
		} else if (file.isFile() && file.getAbsolutePath().endsWith(".java")) {
			CompilationUnitToPackageLinker.link(directory, file);
		}
	}
}
