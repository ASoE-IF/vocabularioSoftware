package org.splab.vocabulary.extractor.browsers;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.splab.vocabulary.extractor.util.VxlManager;

/**
 * 
 * @author Tercio de Melo Modificado por: Israel Gomes de Lima Para
 *         funcionamento no presente extrator
 */
public class DirectoriesBrowser {

	public static void browse(String projectPath, String projectName, String projectRevision)
			throws CoreException, IOException {
		VxlManager.appendVXLFragment(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + VxlManager.startProject(projectName, projectRevision));

		File projectFolder = new File(projectPath);

		if (!projectFolder.isDirectory())
			throw new IllegalArgumentException("the given path is not a folder: " + projectPath);

		for (File file : projectFolder.listFiles()) {
			browseDirectory(projectFolder.getAbsolutePath(), file);
		}
		VxlManager.appendVXLFragment(VxlManager.endProject());
	}

	private static void browseDirectory(String directory, File file) {
		if (file.isDirectory()) {
			System.out.println(file.getAbsolutePath());

			for (File containingFile : file.listFiles()) {
				browseDirectory(directory, containingFile);
			}
		} else {
			if (file.isFile() && (file.getAbsolutePath().toLowerCase().endsWith(".cpp") || file.getAbsolutePath().toLowerCase().endsWith(".h"))) {
				VxlManager.appendVXLFragment(CompilationUnitParser.parse(file));
			}
		}
	}
}