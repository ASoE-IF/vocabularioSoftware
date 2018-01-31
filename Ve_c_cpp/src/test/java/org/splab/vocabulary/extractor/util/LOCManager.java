package org.splab.vocabulary.extractor.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import org.splab.vocabulary.extractor.vloccount.EntityLOCKeeper;
import org.splab.vocabulary.extractor.vloccount.EntityType;
import org.splab.vocabulary.extractor.vloccount.LOCParameters;
import org.splab.vocabulary.extractor.vloccount.PhysicalLOCCount;

/**
 * 
 * Modificado por: Israel Gomes de Lima
 * Para funcionamento no presente extrator
 */
public class LOCManager {

	public static List<LOCParameters> locParameters;
	public static StringBuffer entitiesLOCContent;
	private static StringBuffer compilationUnitLOCContent;
	public static int totalLOC; //total de linhas de código desconciderando readers
	public static int totalHeader;//total de linhas de cabeçalhos
	public static int totalInnerEntitiesLines;//total de linhas em entidades internas
	public static int totalCodeLines; //total de linhas de código
	
	public static void reset() {
		entitiesLOCContent = new StringBuffer(",Entity's Name,LOC Count,Header Count,Inner Entities Lines,Total,Entity Type");
		compilationUnitLOCContent = new StringBuffer("Name,LOC Count");
		totalLOC = totalInnerEntitiesLines = totalCodeLines = 0;
	}

	/**
	 * Increments the LOC per Entity metrics with the given information
	 * @param typeName Name of the analyzed entity
	 * @param entity Metrics keeper
	 */
	public static void appendEntityLOCData(String typeName, EntityLOCKeeper entity, EntityType entityType) {
		int total = entity.getLOC() + entity.getHeaderLOC();
		entitiesLOCContent.append("\n," + typeName + "," +	entity.getLOC() + "," +  entity.getHeaderLOC() + "," + entity.getInnerEntitiesLOC() + "," + total + "," + entityType.name());
		
		totalLOC = entity.getLOC();
		totalHeader = entity.getHeaderLOC();
		totalInnerEntitiesLines = entity.getInnerEntitiesLOC();
		totalCodeLines = total;
	}

	/**
	 * Increments the LOC per file with the given information
	 * @param name File name
	 * @param compilationUnit Loc information keeper
	 */
	public static void appendCompilationUnitLOCData(String name, PhysicalLOCCount compilationUnit) {
		compilationUnitLOCContent.append("\n" + name + "," + compilationUnit.getLOC());
		totalCodeLines = compilationUnit.getLOC();
	}
	
	public static void save(String fileName) {
		compilationUnitLOCContent.append("\nTotal," + compilationUnitLOCContent);
		entitiesLOCContent.append(String.format("\nTotal,,%d,%d,%d,%d",
				totalLOC, totalHeader, totalInnerEntitiesLines, totalCodeLines));
		
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