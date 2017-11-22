package org.splab.vocabulary.extractor.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import org.splab.vocabulary.extractor.vloccount.EntityLOCKeeper;
import org.splab.vocabulary.extractor.vloccount.EntityType;
import org.splab.vocabulary.extractor.vloccount.LOCParameters;
import org.splab.vocabulary.extractor.vloccount.PhysicalLOCCount;

public class LOCManager {

	public static List<LOCParameters> locParameters;
	public static StringBuffer entitiesLOCContent;
	private static StringBuffer compilationUnitLOCContent;
	public static int totalLOC;
	public static int totalHeader;
	public static int totalAnnotation;
	public static int totalInnerEntitiesLines;
	public static int totalCodeLines;
	
	public static void reset() {
		entitiesLOCContent = new StringBuffer(",Entity's Name,LOC Count,Header Count,Annotation Count,Inner Entities Lines,Total,Entity Type");
		compilationUnitLOCContent = new StringBuffer("Name,LOC Count");
		totalLOC = totalHeader = totalAnnotation = totalInnerEntitiesLines = totalCodeLines = 0;
	}

	/**
	 * Increments the LOC per Entity metrics with the given information
	 * @param typeName Name of the analyzed entity
	 * @param entity Metrics keeper
	 */
	public static void appendEntityLOCData(String typeName, EntityLOCKeeper entity, EntityType entityType) {
		int annotations = entity.getAnnotationsLOC();
		int total = entity.getLOC() + entity.getHeaderLOC() + annotations + entity.getInnerEntitiesLOC();
		entitiesLOCContent.append("\n," + typeName + "," +	entity.getLOC() + "," + entity.getHeaderLOC() +
										"," + annotations + "," + entity.getInnerEntitiesLOC() + "," + total + "," + entityType.name());
		
		totalLOC += entity.getLOC();
		totalHeader += entity.getHeaderLOC();
		totalAnnotation += annotations;
		totalInnerEntitiesLines += entity.getInnerEntitiesLOC();
		totalCodeLines += total;
	}

	/**
	 * Increments the LOC per file with the given information
	 * @param name File name
	 * @param compilationUnit Loc information keeper
	 */
	public static void appendCompilationUnitLOCData(String name, PhysicalLOCCount compilationUnit) {
		compilationUnitLOCContent.append("\n" + name + "," + compilationUnit.getLOC());
		totalCodeLines += compilationUnit.getLOC();
	}
	
	public static void save(String fileName) {
		compilationUnitLOCContent.append("\nTotal," + compilationUnitLOCContent);
		entitiesLOCContent.append(String.format("\nTotal,,%d,%d,%d,%d,%d",
				totalLOC, totalHeader, totalAnnotation, totalInnerEntitiesLines, totalCodeLines));
		
		try { 
			PrintStream file = new PrintStream(new FileOutputStream(fileName, false));
			file.print(locParameters.contains(LOCParameters.ONLY_PHYSICAL_LINES) ?
					compilationUnitLOCContent.toString() : entitiesLOCContent.toString());
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}