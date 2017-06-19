package org.splabs.vocabulary.vxl.extractor;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.splabs.vocabulary.vxl.vloccount.LOCParameters;

/**
 * An abstract implementation of term extraction of a Java Project
 * @author gustavojss / Tercio de Melo
 */
public abstract class ProjectVocabularyExtractor {

	private static final String PROJECT_NAME = "name";
	private static final String PROJECT_PACKAGE = "package000";
	private static final String PROJECT_CLASS = "class000";
	private static final String PROJECT_ENUM = "enum000";
	private static final String PROJECT_FIELD = "attribute000";
	private static final String PROJECT_METHOD = "method000";
	private static final String PROJECT_PARAMETER = "parameter000";
	private static final String PROJECT_LOCVARIABLE = "locvariable000";
	private int packageCount, classCount, enumCount, fieldCount, methodCount, parameterCount, locVariableCount = 0;
	
	protected final List<LOCParameters> locParameters;
	
	/**
	 * The vocabulary content
	 */
	protected StringBuffer vocabulary;
	protected String entitiesLOCContent, compilationUnitLOCContent;
	protected int totalLOC, totalHeader, totalAnnotation, totalInnerEntitiesLines, totalCodeLines, compilationUnitLOC;
	
	private final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	/**
	 * Creates a new vocabulary for the given project
	 * @param projectName the project name
	 * @param projectRevision the project revision note
	 */
	public ProjectVocabularyExtractor(String projectName, String projectRevision, List<LOCParameters> locParameters) {
		this.packageCount = 0;
		this.classCount = 0;
		this.enumCount = 0;
		this.fieldCount = 0;
		this.methodCount = 0;
		this.parameterCount = 0;
		this.locVariableCount = 0;
		
		this.locParameters = locParameters;
		this.entitiesLOCContent = ",Entity's Name,LOC Count,Header Count,Annotation Count,Inner Entities Lines,Total";
		this.compilationUnitLOCContent = "Name, LOC Count";
		
		
		this.vocabulary = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + LINE_SEPARATOR);
		this.startProject(projectName, projectRevision);
	}
	
	
	/**
	 * Returns the vocabulary of the project
	 * @return the project's vocabulary
	 */
	public StringBuffer getVocabulary() {
		return this.vocabulary;
	}
	
	public String getLOCContent() {
		if(locParameters.contains(LOCParameters.ONLY_PHYSICAL_LINES))
			return compilationUnitLOCContent;
		return entitiesLOCContent + "\nTotal,," + totalLOC + "," + totalHeader + "," + totalAnnotation + "," + totalInnerEntitiesLines + "," + totalCodeLines;
	}
	
	public String getCompilationUnitLOCContent() {
		return compilationUnitLOCContent;
	}
	/**
	 * Performs the term extraction of the specified project
	 */
	protected abstract void extractTermsFromProject();
	
	protected void startProject(String projectName, String projectRevision) {
		this.vocabulary.append(encodeToUTF8("<java-project id=\"" + PROJECT_NAME + "\" name=\"" + projectName + "\" revision=\"" + projectRevision + "\">" + LINE_SEPARATOR));
	}
	
	protected void endProject() {
		this.vocabulary.append(encodeToUTF8("</java-project>"));
	}
	
	protected void startPackage(String packageName) {
		this.packageCount++;
		this.vocabulary.append(encodeToUTF8("\t<package id=\"" + PROJECT_PACKAGE + this.packageCount + "\" namespace=\"" + packageName + "\">" + LINE_SEPARATOR));
	}
	
	protected void endPackage() {
		this.vocabulary.append(encodeToUTF8("\t</package>" + LINE_SEPARATOR));
	}
	
	protected void startClass(StringBuffer buffer, String className, String isInterface, String isAbstract, String isInner, String comment) {
		this.classCount++;
		buffer.append(encodeToUTF8("\t\t<class id=\"" + PROJECT_CLASS + this.classCount + "\" name=\"" + className + "\" interface=\"" 
				+ isInterface + "\" abstract=\"" + isAbstract + "\" inner=\"" + isInner + "\" javadoc=\"" + comment + "\">" + LINE_SEPARATOR));
	}
	
	protected void classComment(StringBuffer buffer, List<String> comments) {
		for(String comment : comments)
			buffer.append(encodeToUTF8("\t\t\t<comment content=\"" + comment + "\"/>" + LINE_SEPARATOR ));
	}
	
	protected void endClass(StringBuffer buffer) {
		buffer.append(encodeToUTF8("\t\t</class>" + LINE_SEPARATOR));
	}
	
	protected void startEnum(StringBuffer buffer, String enumName, String enumComment) {
		this.enumCount++;
		buffer.append(encodeToUTF8("\t\t<enum id=\"" + PROJECT_ENUM + this.enumCount + "\" name=\"" + enumName + "\" javadoc=\"" + enumComment + "\">" + LINE_SEPARATOR));
	}
	
	protected void enumComment(StringBuffer buffer, List<String> comments) {
		for(String comment : comments)
			buffer.append(encodeToUTF8("\t\t\t<comment content=\"" + comment + "\"/>" + LINE_SEPARATOR ));
	}
	
	protected void endEnum(StringBuffer buffer) {
		buffer.append(encodeToUTF8("\t\t</enum>" + LINE_SEPARATOR));
	}
	
	protected void constant(StringBuffer buffer, String constantName, String visibility, String comment) {
		this.fieldCount++;
		buffer.append(encodeToUTF8("\t\t\t<constant id=\"" + PROJECT_FIELD + this.fieldCount + "\" name=\"" + constantName 
				+ "\" visibility=\"" + visibility + "\" javadoc=\"" + comment + "\"/>" + LINE_SEPARATOR));
	}
	
	protected void field(StringBuffer buffer, String fieldName, String visibility, String comment) {
		this.fieldCount++;
		buffer.append(encodeToUTF8("\t\t\t<field id=\"" + PROJECT_FIELD + this.fieldCount + "\" name=\"" + fieldName 
				+ "\" visibility=\"" + visibility + "\" javadoc=\"" + comment + "\"/>" + LINE_SEPARATOR));
	}
	
	protected void startMethod(StringBuffer buffer, String methodName, String visibility, String comment) {
		this.methodCount++;
		buffer.append(encodeToUTF8("\t\t\t<method id=\"" + PROJECT_METHOD + this.methodCount + "\" name=\"" + methodName + "\" visibility=\"" 
				+ visibility + "\" javadoc=\"" + comment + "\">" + LINE_SEPARATOR));
	}
	
	protected void methodComment(StringBuffer buffer, List<String> comments) {
		for(String comment : comments)
			buffer.append(encodeToUTF8("\t\t\t\t<comment content=\"" + comment + "\"/>" + LINE_SEPARATOR ));
	}
	
	protected void parameter(StringBuffer buffer, String parameterName) {
		this.parameterCount++;
		buffer.append(encodeToUTF8("\t\t\t\t<param id=\"" + PROJECT_PARAMETER + this.parameterCount + "\" name=\"" + parameterName + "\"/>" + LINE_SEPARATOR));
	}
	
	protected void localVariable(StringBuffer buffer, String localVariableName) {
		this.locVariableCount++;
		buffer.append(encodeToUTF8("\t\t\t\t<local-var id=\"" + PROJECT_LOCVARIABLE + this.locVariableCount + "\" name=\"" + localVariableName + "\"/>" + LINE_SEPARATOR));
	}
	
	protected void endMethod(StringBuffer buffer) {
		buffer.append(encodeToUTF8("\t\t\t</method>" + LINE_SEPARATOR));
	}
	
	private String encodeToUTF8(String aString) {
		try {
			byte[] bytes = aString.getBytes("UTF-8");
			return new String(bytes);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "wrong encoding";
	}
}