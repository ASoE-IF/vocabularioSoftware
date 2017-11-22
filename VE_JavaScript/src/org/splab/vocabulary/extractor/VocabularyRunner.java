package org.splab.vocabulary.extractor;

import java.util.LinkedList;

import org.splab.vocabulary.extractor.browsers.DirectoriesBrowser;
import org.splab.vocabulary.extractor.util.LOCManager;
import org.splab.vocabulary.extractor.util.MemoryRuntimeIFPB;
import org.splab.vocabulary.extractor.util.VxlManager;
import org.splab.vocabulary.extractor.vloccount.LOCParameters;

/**
 * The main class of the Vocabulary Extractor Project.
 * Given a source code project directory and defining some parameters, 
 * the project VocabularyExtractor will find the software vocabulary
 * and give it back in an hierarquial VXL file.
 * @author Catharine Quintans, Tercio de Melo, Katyusco Santos
 */
public class VocabularyRunner {
		
	private static void setParam(String arg) {
		LOCManager.locParameters.add(LOCParameters.LOC);
		if (arg.equals("mth")) {
			LOCManager.locParameters.add(LOCParameters.INTERN_VOCABULARY);
		}
		for (char c : arg.toCharArray()) {
			if (c == 'h') {
				LOCManager.locParameters.add(LOCParameters.HEADERS);
			}
			if (c == 'a') {
				LOCManager.locParameters.add(LOCParameters.ANNOTATIONS);
			}
			if (c == 'i') {
				LOCManager.locParameters.add(LOCParameters.INNER_CLASSES);
			}
			if (c == 'p') {
				LOCManager.locParameters.clear();
				LOCManager.locParameters.add(LOCParameters.ONLY_PHYSICAL_LINES);
				LOCManager.locParameters.add(LOCParameters.LOC);
				LOCManager.reset();
				break;
			}
		}
		LOCManager.reset();	
	}
	
	/**
	 * The main class of the project.
	 * @param args The arguments should have a list of parameters for the tool. 
	 * 			   You must set the following options:
				 		-d: the project path must be inserted after this option
						-n: sets the ProjectName
						-vxl: the path/name of the resulting VXL file
					 	-csv: the path/name of the resulting CSV loc file
						-mth: activates the extraction of method's vocabulary
						-loc: activates the loc extraction. Options are required as a string:
						 	h: sets headers loc counting
							i: sets inners classes counting
							a: sets annotations counting"
							p: sets physical sloc counting but unsets all the other loc options 
	 */
	public static void main(String[] args) {
		final String MANUAL = "Invalid input. You must set the following options:"
				+ "\n\t-d: the project path must be inserted after this option"
				+ "\n\t-n: sets the ProjectName"
				+ "\n\t-r: sets the ProjectRevision"
				+ "\n\t-vxl: the path/name of the resulting VXL file"
				+ "\n\t-csv: the path/name of the resulting CSV loc file"
				+ "\n\t-mth: activates the extraction of method's vocabulary"
				+ "\n\t-loc: activates the loc extraction. Options are required as a string:"
				+ "\n\t\t h: sets headers loc counting"
				+ "\n\t\t i: sets inners classes counting"
				+ "\n\t\t a: sets annotations counting"
				+ "\n\t\t p: sets physical sloc counting but unsets all the other loc options"
				+ "\n\t-msr: activates the measuring of memory usage for VocabularyExtractor execution"
				+ "\n\n\tEXAMPLE: -n Project_name -r Projetc_revision -d ~/SomeProject/ -loc iah -vxl ~/ProjectVXL.vxl -csv ~/ProjectCSV.csv -mth";

		try {
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
					System.exit(0);
				} else if (args[i].equals("-mth")) {
					setParam("mth");
				} else if (args[i].equals("-msr")) {
					measure_enable = true;
				}
			}

			DirectoriesBrowser.browse(projectPath, projectName, projectRevision);
			
			VxlManager.save(resultVXLFileName);
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
			System.out.println(MANUAL);
		}
	}
}