package org.splab.vocabulary.extractor;

import java.util.LinkedList;

import org.splab.vocabulary.extractor.browsers.DirectoriesBrowser;
import org.splab.vocabulary.extractor.util.ErrorLogManager;
import org.splab.vocabulary.extractor.util.LOCManager;
import org.splab.vocabulary.extractor.util.MemoryRuntimeIFPB;
import org.splab.vocabulary.extractor.util.VxlManager;
import org.splab.vocabulary.extractor.vloccount.LOCParameters;

/**
 * The main class of the Vocabulary Extractor Project. Given a source code
 * project directory and defining some parameters, the project
 * VocabularyExtractor will find the software vocabulary and give it back in an
 * hierarquial VXL file.
 * 
 * @author Catharine Quintans, Tercio de Melo, Katyusco Santos Modificado por:
 *         Israel Gomes de Lima para funcionamento neste extrator
 */

public class VocabularyRunner {
	public static void main(String[] args) {
		final String MANUAL = "Invalid input. You must set the following options:"
				+ "\n\t-d: the project path must be inserted after this option" + "\n\t-n: sets the ProjectName"
				+ "\n\t-r: sets the ProjectRevision" + "\n\t-vxl: the path/name of the resulting VXL file"
				+ "\n\t-csv: the path/name of the resulting CSV loc file"
				+ "\n\t-func: activates the extraction of func's vocabulary"
				+ "\n\t-loc: activates the loc extraction. Options are required as a string:"
				+ "\n\t\t d: sets directives loc counting" + "\n\t\t i: sets inners file counting"
				+ "\n\t\t p: sets physical sloc counting but unsets all the other loc options"
				+ "\n\t-msr: activates the measuring of memory usage for VocabularyExtractor execution"
				+ "\n\t-file: Selects the source file type (C or H or CH), C: cpp file, H: header file"
				+ "\n\n\tEXAMPLE: -n Project_name -r Projetc_revision -d ~/SomeProject/ -loc ih -vxl ~/ProjectVXL.vxl -csv ~/ProjectCSV.csv -func";

		try {
			LOCManager.reset();
			LOCManager.locParameters = new LinkedList<LOCParameters>();

			String projectPath = "default";
			String projectName = "default";
			String projectRevision = "default";
			String resultVXLFileName = "default.vxl";
			String resultLOCFileName = "default.csv";
			boolean measure_enable = false;

			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("-loc")) {
					setParam(args[++i]);
				} else if (args[i].equals("-d")) {
					projectPath = args[++i];
				} else if (args[i].equals("-vxl")) {
					resultVXLFileName = args[++i];
				} else if (args[i].equals("-csv")) {
					resultLOCFileName = args[++i];
				} else if (args[i].equals("-n")) {
					projectName = args[++i];
				} else if (args[i].equals("-r")) {
					projectRevision = args[++i];
				} else if (args[i].equals("-help")) {
					System.out.println(MANUAL);
				} else if (args[i].equals("-func")) {
					setParam("func");
				} else if (args[i].equals("-msr")) {
					measure_enable = true;
				} else if (args[i].equals("-file")) {
					setParam(args[++i]);
				}
			}
			DirectoriesBrowser.browse(projectPath, projectName, projectRevision);

			VxlManager.save(resultVXLFileName);
			ErrorLogManager.save();

			if (LOCManager.locParameters.contains(LOCParameters.LOC)) {
				LOCManager.save(resultLOCFileName);
			}

			System.out.println("Vocabulary Extraction has finished.");
			// Caso esteja habilitado para coletar medidas
			if (measure_enable) {
				MemoryRuntimeIFPB calc = new MemoryRuntimeIFPB();
				calc.CalculateAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(MANUAL);
		}
	}

	public static void setParam(String arg) {
		LOCManager.locParameters.add(LOCParameters.LOC);

		if (arg.equals("func")) {
			LOCManager.locParameters.add(LOCParameters.INTERN_VOCABULARY);
		}
		for (char c : arg.toCharArray()) {
			if (c == 'h') {
				LOCManager.locParameters.add(LOCParameters.HEADERS);
			}
			if (c == 'i') {
				LOCManager.locParameters.add(LOCParameters.INNER_CLASS);
			}

			if (c == 'p') {
				LOCManager.locParameters.clear();
				LOCManager.locParameters.add(LOCParameters.ONLY_PHYSICAL_LINES);
				LOCManager.locParameters.add(LOCParameters.LOC);
				LOCManager.reset();
				break;
			}

			if (c == 'C') {
				LOCManager.locParameters.add(LOCParameters.CPP_FILE);
			}

			if (c == 'H') {
				LOCManager.locParameters.add(LOCParameters.H_FILE);
			}
		}
		LOCManager.reset();
	}
}