package org.splabs.vocabulary.vxl.extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.splabs.vocabulary.vxl.extractor.util.FileUtil;
import org.splabs.vocabulary.vxl.vloccount.LOCParameters;

/**
 * A term extractor for Java Project Folders
 * @author gustavojss
 */
public class JavaFolderVocabularyExtractor extends SourceCodeVocabularyExtractor {

	private String projectPath;
	private static final String DEFAULT_PACKAGE = "default";
	
	private Map<String, List<StringBuffer>> packageHierarchy;
	
	
	/**
	 * Creates a new term extractor for Java Project Folders
	 * @param projectPath the path of the project folder
	 * @param projectName the project's name
	 * @param projectRevision the project's revision name
	 */
	public JavaFolderVocabularyExtractor(String projectPath, String projectName, String projectRevision, List<LOCParameters> locParameters) {
		super(projectName, projectRevision, locParameters);
		this.projectPath = projectPath;
	
		this.packageHierarchy = new HashMap<String, List<StringBuffer>>();
		extractTermsFromProject();
	}

	@Override
	protected void extractTermsFromProject() {
		
		File projectFolder = new File(this.projectPath);
		if (!projectFolder.isDirectory())
			throw new IllegalArgumentException("the given path is not a folder: " + this.projectPath);
		
		// for all files in this project folder, add java components to the map
		for (File file : projectFolder.listFiles())
			extractAllFiles(file);
		
		// extract terms from the package hierarchy map
		extractTermsFromHierarchyMap();
		
		this.endProject();
	}
	
	/**
	 * Extract all java files from a folder, and add to the hierarchy map
	 * @param file
	 */
	private void extractAllFiles(File file) {
		
		// if it's a folder, search all containing files and subfolders
		if (file.isDirectory()) {
			System.out.println(file.getAbsolutePath());
			
			for (File containingFile : file.listFiles()) {
				extractAllFiles(containingFile);
			}
		// if it's a java file, extract AST tree and add to the hierarchy map
		} else if (file.isFile() && isJavaFile(file.getAbsolutePath())) {
			extractTermsFromClass(file);
		}
	}
	
	/**
	 * Extract terms from all compilation units in the hierarchy map
	 */
	private void extractTermsFromHierarchyMap() {
		
		// for all packages
		for (String packageName : this.packageHierarchy.keySet()) {
			
			this.startPackage(packageName);
			System.out.println("Processing Package: " + packageName);
			
			// for all compilation units in this package, extract terms
			for (StringBuffer compUnitBuffer : this.packageHierarchy.get(packageName)) {
				this.vocabulary.append(compUnitBuffer);
			}
			
			
			this.endPackage();
		}
	}
	
	/**
	 * Extracts the java structure of a file, and adds to the hierarchy map
	 * @param classFile the .java file
	 */
	private void extractTermsFromClass(File classFile) {
		try {
			// copying source code from .java file
			BufferedReader in = new BufferedReader(new FileReader(classFile));
			StringBuffer sourceCode = new StringBuffer(); 
			String line = null;
			while (null != (line = in.readLine())) {
				sourceCode.append(line + "\n");
			}
			
			// extract AST tree from source code
			getASTTreeFromSourceCode(sourceCode.toString().toCharArray());
			ASTNode tree = super.astNode;
			CompilationUnit compilationUnit = (CompilationUnit) tree;

			//extractCommentsFromCompilationUnit(compilationUnit);
			// adding the extracted compilation unit to package map
			addCompilationUnitToMap(compilationUnit, classFile.getAbsolutePath());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds the given compilation unit to the hierarchy map
	 * @param compilationUnit the compilation unit to be added
	 */
	private void addCompilationUnitToMap(CompilationUnit compilationUnit, String fileName) {
		
		// extracting package name
		PackageDeclaration pckg = compilationUnit.getPackage();
		String packageName = (pckg == null) ? DEFAULT_PACKAGE : pckg.getName().getFullyQualifiedName();
		
		// extracting terms from compilation unit
		StringBuffer compUnitBuffer = this.extractTermsFromCompilationUnit(compilationUnit, fileName);
		
		// adding the compilation unit in the map
		List<StringBuffer> compilationUnits;
		if (this.packageHierarchy.containsKey(packageName)) {
			compilationUnits = this.packageHierarchy.get(packageName); 
		} else {
			compilationUnits = new LinkedList<StringBuffer>();
		}
		
		compilationUnits.add(compUnitBuffer);
		this.packageHierarchy.put(packageName, compilationUnits);
	}
	
	/**
	 * Verifies if the given file is a java file
	 * @param fileName
	 * @return
	 */
	private static boolean isJavaFile(String fileName) {
		return fileName.endsWith(".java");
	}
	
	public static void main(String[] args) {
		
		String projectPath = args[0];
		
		ProjectVocabularyExtractor termExtractor = new JavaFolderVocabularyExtractor(projectPath, "projetoTeste", "1.0", new LinkedList<LOCParameters>());
		FileUtil.saveVXLFile(termExtractor.vocabulary, "design.vxl");
	}
}