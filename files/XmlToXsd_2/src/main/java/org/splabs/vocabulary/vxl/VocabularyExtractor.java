package org.splabs.vocabulary.vxl;


import java.util.LinkedList;
import java.util.List;

import org.splabs.vocabulary.vxl.extractor.JavaFolderVocabularyExtractor;
import org.splabs.vocabulary.vxl.extractor.util.FileUtil;
import org.splabs.vocabulary.vxl.vloccount.LOCParameters;

/**
 * A main class for term extraction in Java Projects
 * @author gustavojss / Tercio de Melo
 */
public class VocabularyExtractor {
	private String projectName;
	private String projectRevision;

	public static List<LOCParameters> locParameters;
	
	/**
	 * Creates a new extractor with the given name and revision of a project
	 * @param projectName the name of the project
	 * @param projectRevision the revision note of the project
	 */
	public VocabularyExtractor(String projectName, String projectRevision) {
		this.projectName = projectName;
		this.projectRevision = projectRevision;
	}
	
	/**
	 * Extracts terms from a Java Project Folder
	 * @param projectPath the path for the project folder
	 * @param resultVXLFileName the result file where the vocabulary will be saved
	 */
	public void extractTermsFromJavaFolder(String projectPath, String resultVXLFileName, String resultLOCFileName, List<LOCParameters> locParameters) {
		
		JavaFolderVocabularyExtractor extractor = new JavaFolderVocabularyExtractor(projectPath, this.projectName, this.projectRevision, locParameters);
		FileUtil.saveVXLFile(extractor.getVocabulary(), resultVXLFileName);
		
		if(locParameters.size() != 0)
			FileUtil.saveLOCFile(extractor.getLOCContent(), resultLOCFileName);
		
	}
	
	/**
	 * Verifies if a given String is contained in a given array of Strings
	 * @param args
	 * @param parameter
	 * @return
	 */
	private static boolean isInArgs(String[] args, String parameter) {
		for(String str : args)
			if(str.equals(parameter))
				return true;
		return false;
	}
	
	/**
	 * Sets the LOC extraction configurations based on the information passed through the JVM arguments
	 * @param args
	 * @return
	 */
	private static boolean loadLOCParameters(String[] args) {
		if(isInArgs(args, "-physical") && isInArgs(args, "-loc")) {
			locParameters.add(LOCParameters.ONLY_PHYSICAL_LINES);
			return false;
		} else if (isInArgs(args, "-physical")) return true;
		
		boolean loc;
		if(loc = isInArgs(args, "-loc")) locParameters.add(LOCParameters.LOC);
		
		if(isInArgs(args, "-headers") && loc) locParameters.add(LOCParameters.HEADERS);
		if(isInArgs(args, "-annot") && loc) locParameters.add(LOCParameters.ANNOTATIONS);
		if(isInArgs(args, "-inner") && loc) locParameters.add(LOCParameters.INNER_CLASSES);
		
		return false;
	}
	
	/**
	 * Simple execution for term extraction on a Java Project Folder
	 * @param args USAGE: [project path] [project name] [project revision] [result .vxl file name]
	 */
	public static void main(String[] args) {
		
		locParameters = new LinkedList<LOCParameters>();
		final String LINESEPARATOR = System.getProperty("line.separator");
		final String ERROR_MESSAGE = "USAGE: [project path] [project name] [project revision] [result .vxl file name]" + LINESEPARATOR +
									 "TO SET EXTRA OPTIONS:" + LINESEPARATOR + "[-loc] turn on the VELOCCounter, you must use the following arguments " +
									 "to set the parameters for the counting:" +  LINESEPARATOR + "[-physical] Counts only physical lines, not more" +
									 LINESEPARATOR + "[-headers] includes the headers counting in the ENTITY VELOCCount result file" + LINESEPARATOR +
									 "[-annot] includes framework annotations counting in the ENTITY VELOCCount result file" + LINESEPARATOR +
									 "[-inner] includes inner entities LOC counting in the ENTITY VELOCCount result file" + LINESEPARATOR;
		
				
		if (args.length < 4 || loadLOCParameters(args)) {
			throw new IllegalArgumentException(ERROR_MESSAGE);
		}
		
		int i = (locParameters.size() != 0) ? (args.length - 5) : 0;
		
		String projectPath = args[i++];
		String projectName = args[i++];
		String projectRevision = args[i++];
		String resultVXLFileName = args[i++];
		String resultLOCFileName = locParameters.size() != 0 ? args[i] : "";
		
		VocabularyExtractor vocExtractor = new VocabularyExtractor(projectName, projectRevision);
		vocExtractor.extractTermsFromJavaFolder(projectPath, resultVXLFileName, resultLOCFileName,  locParameters);
		
		System.out.println("Vocabulary Extraction has finished.");
	}
}
 