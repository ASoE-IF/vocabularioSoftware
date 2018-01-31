package org.splab.vocabulary.extractor.browsers;

import java.io.File;
import java.io.IOException;

import org.splab.vocabulary.extractor.util.VxlManager;

/**
 * 
 * @author Tercio de Melo Modificado por: Israel Gomes de Lima Para
 *         funcionamento no presente extrator
 */
public class DirectoriesBrowser {

	public static void browse(String projectPath, String projectName, String projectRevision)
			throws IOException {
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
			if (file.isFile() && (file.getAbsolutePath().endsWith(".c") || file.getAbsolutePath().endsWith(".C")
					|| file.getAbsolutePath().endsWith(".h") || file.getAbsolutePath().endsWith(".H"))) {
				VxlManager.appendVXLFragment(CompilationUnitParser.parse(file));
			}
		}
	}
}