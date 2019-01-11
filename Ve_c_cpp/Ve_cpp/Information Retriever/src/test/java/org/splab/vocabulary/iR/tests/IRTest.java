package org.splab.vocabulary.iR.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;

import org.splab.vocabulary.iR.IR;
import org.splab.vocabulary.iR.util.TermWeightTuple;

public class IRTest extends TestCase {
	private final String PROPERTIES_PATH = System.getProperty("user.dir")
			+ "/files/IR.properties";
	private final String LINE_SEPARATOR = System.getProperty("line.separator");

	private Properties irProperties;
	private IR ir;

	private Map<String, Map<String, Integer>> entitiesAndTerms;

	public void setUp() {
		this.irProperties = new Properties();
		constructEntitiesAndTerms();
	}

	public void testIDFTest() { // IDF NO Case Sensitive Test
		reconstructIR("IR", "ABSOLUTE", "IDF", "NO", "COSINE");
		assertEquals(constructIDFResult(false).toString(), ir.calculate().toString());
	}
	
	public void testIDFCSTest() { // IDF Case Sensitive Test
		reconstructIR("IR", "ABSOLUTE", "IDF", "YES", "COSINE");
		assertEquals(constructIDFResult(true).toString(), ir.calculate().toString());
	}
	
	public void testTFAbsoluteTest() { // TF Absolute NO Case Sensitive Test
		reconstructIR("IR", "ABSOLUTE", "TF", "NO", "COSINE");
		assertEquals(constructTFAbsoluteResult(false).toString(), ir.calculate().toString());
	}
	
	public void testTFAbsoluteCSTest() { // TF Absolute Case Sensitive Test
		reconstructIR("IR", "ABSOLUTE", "TF", "YES", "COSINE");
		assertEquals(constructTFAbsoluteResult(true).toString(), ir.calculate().toString());
	}
	
	public void testTFRelativeTest() {  // TF Relative NO Case Sensitive Test
		reconstructIR("IR", "RELATIVE", "TF", "NO", "COSINE");
		assertEquals(constructTFRelativeResult(false).toString(), ir.calculate().toString());
	}
	
	public void testTFRelativeCSTest() { // TF Relative Case Sensitive Test
		reconstructIR("IR", "RELATIVE", "TF", "YES", "COSINE");
		assertEquals(constructTFRelativeResult(true).toString(), ir.calculate().toString());
	}
	
	public void testTFIDFAbsoluteTest() { // TFIDF Absolute NO Case Sensitive Test
		reconstructIR("IR", "ABSOLUTE", "TFIDF", "NO", "COSINE");
		assertEquals(constructTFIDFAbsoluteResult(false).toString(), ir.calculate().toString());
	}
	
	public void testTFIDFAbsoluteCSTest() { // TFIDF Absolute Case Sensitive Test
		reconstructIR("IR", "ABSOLUTE", "TFIDF", "YES", "COSINE");
		assertEquals(constructTFIDFAbsoluteResult(true).toString(), ir.calculate().toString());
	}
	
	public void testTFIDFRelativeTest() { // TFIDF Relative NO Case Sensitive Test
		reconstructIR("IR", "RELATIVE", "TFIDF", "NO", "COSINE");
		System.out.println(constructTFIDFRelativeResult(true).toString());
		System.out.println(ir.calculate().toString());
		assertEquals(constructTFIDFRelativeResult(false).toString(), ir.calculate().toString());
	}
	
	public void testTFIDFRelativeCSTest() { // TFIDF Relative Case Sensitive Test
		reconstructIR("IR", "RELATIVE", "TFIDF", "YES", "COSINE");
		assertEquals(constructTFIDFRelativeResult(true).toString(), ir.calculate().toString());
	}

	private void constructEntitiesAndTerms() {
		String entity = "GenericClass";
		Map<String, Integer> entityTerms = new HashMap<String, Integer>();
		for (String entityTerm : new String[] { "PUBLIC", "CONSTANTE",
				"PRIVATE", "CONSTANTE", "public", "atributo", "private",
				"atributo", "public", "metodo", "parametro1", "parametro2",
				"local", "variavel", "private", "metodo", "parametro1",
				"parametro2", "GenericEnum", "RED", "GREEN", "BLUE" }) {
			if (entityTerms.containsKey(entityTerm)) {
				entityTerms.put(entityTerm, entityTerms.get(entityTerm));
			} else {
				entityTerms.put(entityTerm, 1);
			}
		}

		String otherEntity = "Interna";
		Map<String, Integer> otherEntityTerms = new HashMap<String, Integer>();
		for (String entityTerm : new String[] { "public", "atributo",
				"interna", "private", "atributo", "interna" }) {
			if (otherEntityTerms.containsKey(entityTerm)) {
				otherEntityTerms.put(entityTerm, otherEntityTerms.get(entityTerm));
			} else {
				otherEntityTerms.put(entityTerm, 1);
			}
		}

		this.entitiesAndTerms = new HashMap<String, Map<String, Integer>>();
		this.entitiesAndTerms.put(entity, entityTerms);
		this.entitiesAndTerms.put(otherEntity, otherEntityTerms);
	}

	private Map<String, List<TermWeightTuple>> constructIDFResult(boolean isCaseSensitive) {
		Map<String, List<TermWeightTuple>> idfResult = new HashMap<String, List<TermWeightTuple>>();

		String entity = "GenericClass";
		String otherEntity = "Interna";
		List<TermWeightTuple> entityTermsTuples = new ArrayList<TermWeightTuple>();
		List<TermWeightTuple> otherEntityTermsTuples = new ArrayList<TermWeightTuple>();
		
		if (isCaseSensitive) {
			for (TermWeightTuple entityTermTuple : new TermWeightTuple[] {
					new TermWeightTuple("PUBLIC", calculateIDF(1, 2)),
					new TermWeightTuple("CONSTANTE", calculateIDF(1, 2)),
					new TermWeightTuple("PRIVATE", calculateIDF(1, 2)),
					new TermWeightTuple("public", calculateIDF(2, 2)),
					new TermWeightTuple("atributo", calculateIDF(2, 2)),
					new TermWeightTuple("private", calculateIDF(2, 2)),
					new TermWeightTuple("metodo", calculateIDF(1, 2)),
					new TermWeightTuple("parametro1", calculateIDF(1, 2)),
					new TermWeightTuple("parametro2", calculateIDF(1, 2)),
					new TermWeightTuple("local", calculateIDF(1, 2)),
					new TermWeightTuple("variavel", calculateIDF(1, 2)),
					new TermWeightTuple("GenericEnum", calculateIDF(1, 2)),
					new TermWeightTuple("RED", calculateIDF(1, 2)),
					new TermWeightTuple("GREEN", calculateIDF(1, 2)),
					new TermWeightTuple("BLUE", calculateIDF(1, 2))}) {
				entityTermsTuples.add(entityTermTuple);
			}
			
			for (TermWeightTuple otherEntityTermTuple : new TermWeightTuple[] {
					new TermWeightTuple("public", calculateIDF(2, 2)),
					new TermWeightTuple("atributo", calculateIDF(2, 2)),
					new TermWeightTuple("interna", calculateIDF(1, 2)),
					new TermWeightTuple("private", calculateIDF(2, 2))}) {
				otherEntityTermsTuples.add(otherEntityTermTuple);
			}
		} else {
			for (TermWeightTuple entityTermTuple : new TermWeightTuple[] {
					new TermWeightTuple("PUBLIC", calculateIDF(2, 2)),
					new TermWeightTuple("CONSTANTE", calculateIDF(1, 2)),
					new TermWeightTuple("PRIVATE", calculateIDF(2, 2)),
					new TermWeightTuple("atributo", calculateIDF(2, 2)),
					new TermWeightTuple("metodo", calculateIDF(1, 2)),
					new TermWeightTuple("parametro1", calculateIDF(1, 2)),
					new TermWeightTuple("parametro2", calculateIDF(1, 2)),
					new TermWeightTuple("local", calculateIDF(1, 2)),
					new TermWeightTuple("variavel", calculateIDF(1, 2)),
					new TermWeightTuple("GenericEnum", calculateIDF(1, 2)),
					new TermWeightTuple("RED", calculateIDF(1, 2)),
					new TermWeightTuple("GREEN", calculateIDF(1, 2)),
					new TermWeightTuple("BLUE", calculateIDF(1, 2))}) {
				entityTermsTuples.add(entityTermTuple);
			}
			
			for (TermWeightTuple otherEntityTermTuple : new TermWeightTuple[] {
					new TermWeightTuple("public", calculateIDF(2, 2)),
					new TermWeightTuple("atributo", calculateIDF(2, 2)),
					new TermWeightTuple("interna", calculateIDF(1, 2)),
					new TermWeightTuple("private", calculateIDF(2, 2))}) {
				otherEntityTermsTuples.add(otherEntityTermTuple);
			}
		}
		
		idfResult.put(entity, entityTermsTuples);
		idfResult.put(otherEntity, otherEntityTermsTuples);
		
		return idfResult;
	}

	// As Lucene ...
	private double calculateIDF(double termFrequency, int numIdentities) {
		return (termFrequency == 0) ? 0 : Math.log10(numIdentities
				/ (termFrequency + 1)) + 1;
	}

	private Map<String, List<TermWeightTuple>> constructTFAbsoluteResult(boolean isCaseSensitive) {
		Map<String, List<TermWeightTuple>> tfAbsoluteResult = new HashMap<String, List<TermWeightTuple>>();

		String entity = "GenericClass";
		String otherEntity = "Interna";
		List<TermWeightTuple> entityTermsTuples = new ArrayList<TermWeightTuple>();
		List<TermWeightTuple> otherEntityTermsTuples = new ArrayList<TermWeightTuple>();
		
		if (isCaseSensitive) {
			for (TermWeightTuple entityTermTuple : new TermWeightTuple[] {
					new TermWeightTuple("PUBLIC", 1),
					new TermWeightTuple("CONSTANTE", 2),
					new TermWeightTuple("PRIVATE", 1),
					new TermWeightTuple("public", 2),
					new TermWeightTuple("atributo", 2),
					new TermWeightTuple("private", 2),
					new TermWeightTuple("metodo", 2),
					new TermWeightTuple("parametro1", 2),
					new TermWeightTuple("parametro2", 2),
					new TermWeightTuple("local", 1),
					new TermWeightTuple("variavel", 1),
					new TermWeightTuple("GenericEnum", 1),
					new TermWeightTuple("RED", 1),
					new TermWeightTuple("GREEN", 1),
					new TermWeightTuple("BLUE", 1)}) {
				entityTermsTuples.add(entityTermTuple);
			}
			
			for (TermWeightTuple otherEntityTermTuple : new TermWeightTuple[] {
					new TermWeightTuple("public", 1),
					new TermWeightTuple("atributo", 2),
					new TermWeightTuple("interna", 2),
					new TermWeightTuple("private", 1)}) {
				otherEntityTermsTuples.add(otherEntityTermTuple);
			}
		} else {
			for (TermWeightTuple entityTermTuple : new TermWeightTuple[] {
					new TermWeightTuple("PUBLIC", 3),
					new TermWeightTuple("CONSTANTE", 2),
					new TermWeightTuple("PRIVATE", 3),
					new TermWeightTuple("atributo", 2),
					new TermWeightTuple("metodo", 2),
					new TermWeightTuple("parametro1", 2),
					new TermWeightTuple("parametro2", 2),
					new TermWeightTuple("local", 1),
					new TermWeightTuple("variavel", 1),
					new TermWeightTuple("GenericEnum", 1),
					new TermWeightTuple("RED", 1),
					new TermWeightTuple("GREEN", 1),
					new TermWeightTuple("BLUE", 1)}) {
				entityTermsTuples.add(entityTermTuple);
			}
			
			for (TermWeightTuple otherEntityTermTuple : new TermWeightTuple[] {
					new TermWeightTuple("public", 1),
					new TermWeightTuple("atributo", 2),
					new TermWeightTuple("interna", 2),
					new TermWeightTuple("private", 1)}) {
				otherEntityTermsTuples.add(otherEntityTermTuple);
			}
		}
		
		tfAbsoluteResult.put(entity, entityTermsTuples);
		tfAbsoluteResult.put(otherEntity, otherEntityTermsTuples);
		
		return tfAbsoluteResult;
	}

	private Map<String, List<TermWeightTuple>> constructTFRelativeResult(boolean isCaseSensitive) {
		Map<String, List<TermWeightTuple>> tfRelativeResult = new HashMap<String, List<TermWeightTuple>>();

		String entity = "GenericClass";
		String otherEntity = "Interna";
		List<TermWeightTuple> entityTermsTuples = new ArrayList<TermWeightTuple>();
		List<TermWeightTuple> otherEntityTermsTuples = new ArrayList<TermWeightTuple>();
		final int ENTITY_NUMBER_OF_TERMS = 22;
		final int OTHER_ENTITY_NUMBER_OF_TERMS = 6;

		if (isCaseSensitive) {
			for (TermWeightTuple entityTermTuple : new TermWeightTuple[] {
					new TermWeightTuple("PUBLIC", 1.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("CONSTANTE", 2.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("PRIVATE", 1.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("public", 2.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("atributo", 2.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("private", 2.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("metodo", 2.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("parametro1", 2.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("parametro2", 2.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("local", 1.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("variavel", 1.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("GenericEnum", 1.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("RED", 1.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("GREEN", 1.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("BLUE", 1.0/ENTITY_NUMBER_OF_TERMS)}) {
				entityTermsTuples.add(entityTermTuple);
			}
			
			for (TermWeightTuple otherEntityTermTuple : new TermWeightTuple[] {
					new TermWeightTuple("public", 1.0/OTHER_ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("atributo", 2.0/OTHER_ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("interna", 2.0/OTHER_ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("private", 1.0/OTHER_ENTITY_NUMBER_OF_TERMS)}) {
				otherEntityTermsTuples.add(otherEntityTermTuple);
			}
		} else {
			for (TermWeightTuple entityTermTuple : new TermWeightTuple[] {
					new TermWeightTuple("PUBLIC", 3.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("CONSTANTE", 2.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("PRIVATE", 3.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("atributo", 2.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("metodo", 2.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("parametro1", 2.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("parametro2", 2.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("local", 1.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("variavel", 1.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("GenericEnum", 1.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("RED", 1.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("GREEN", 1.0/ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("BLUE", 1.0/ENTITY_NUMBER_OF_TERMS)}) {
				entityTermsTuples.add(entityTermTuple);
			}
			
			for (TermWeightTuple otherEntityTermTuple : new TermWeightTuple[] {
					new TermWeightTuple("public", 1.0/OTHER_ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("atributo", 2.0/OTHER_ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("interna", 2.0/OTHER_ENTITY_NUMBER_OF_TERMS),
					new TermWeightTuple("private", 1.0/OTHER_ENTITY_NUMBER_OF_TERMS)}) {
				otherEntityTermsTuples.add(otherEntityTermTuple);
			}
		}
		
		tfRelativeResult.put(entity, entityTermsTuples);
		tfRelativeResult.put(otherEntity, otherEntityTermsTuples);
		
		return tfRelativeResult;
	}

	private Map<String, List<TermWeightTuple>> constructTFIDFAbsoluteResult(boolean isCaseSensitive) {
		Map<String, List<TermWeightTuple>> tfidfAbsoluteResult = new HashMap<String, List<TermWeightTuple>>();

		String entity = "GenericClass";
		String otherEntity = "Interna";
		List<TermWeightTuple> entityTermsTuples = new ArrayList<TermWeightTuple>();
		List<TermWeightTuple> otherEntityTermsTuples = new ArrayList<TermWeightTuple>();
		
		if (isCaseSensitive) {
			for (TermWeightTuple entityTermTuple : new TermWeightTuple[] {
					new TermWeightTuple("PUBLIC", calculateIDF(1, 2) * 1),
					new TermWeightTuple("CONSTANTE", calculateIDF(1, 2) * 2),
					new TermWeightTuple("PRIVATE", calculateIDF(1, 2) * 1),
					new TermWeightTuple("public", calculateIDF(2, 2) * 2),
					new TermWeightTuple("atributo", calculateIDF(2, 2) * 2),
					new TermWeightTuple("private", calculateIDF(2, 2) * 2),
					new TermWeightTuple("metodo", calculateIDF(1, 2) * 2),
					new TermWeightTuple("parametro1", calculateIDF(1, 2) * 2),
					new TermWeightTuple("parametro2", calculateIDF(1, 2) * 2),
					new TermWeightTuple("local", calculateIDF(1, 2) * 1),
					new TermWeightTuple("variavel", calculateIDF(1, 2) * 1),
					new TermWeightTuple("GenericEnum", calculateIDF(1, 2) * 1),
					new TermWeightTuple("RED", calculateIDF(1, 2) * 1),
					new TermWeightTuple("GREEN", calculateIDF(1, 2) * 1),
					new TermWeightTuple("BLUE", calculateIDF(1, 2) * 1)}) {
				entityTermsTuples.add(entityTermTuple);
			}
			
			for (TermWeightTuple otherEntityTermTuple : new TermWeightTuple[] {
					new TermWeightTuple("public", calculateIDF(2, 2) * 1),
					new TermWeightTuple("atributo", calculateIDF(2, 2) * 2),
					new TermWeightTuple("interna", calculateIDF(1, 2) * 2),
					new TermWeightTuple("private", calculateIDF(2, 2) * 1)}) {
				otherEntityTermsTuples.add(otherEntityTermTuple);
			}
		} else {
			for (TermWeightTuple entityTermTuple : new TermWeightTuple[] {
					new TermWeightTuple("PUBLIC", calculateIDF(2, 2) * 3),
					new TermWeightTuple("CONSTANTE", calculateIDF(1, 2) * 2),
					new TermWeightTuple("PRIVATE", calculateIDF(2, 2) * 3),
					new TermWeightTuple("atributo", calculateIDF(2, 2) * 2),
					new TermWeightTuple("metodo", calculateIDF(1, 2) * 2),
					new TermWeightTuple("parametro1", calculateIDF(1, 2) * 2),
					new TermWeightTuple("parametro2", calculateIDF(1, 2) * 2),
					new TermWeightTuple("local", calculateIDF(1, 2) * 1),
					new TermWeightTuple("variavel", calculateIDF(1, 2) * 1),
					new TermWeightTuple("GenericEnum", calculateIDF(1, 2) * 1),
					new TermWeightTuple("RED", calculateIDF(1, 2) * 1),
					new TermWeightTuple("GREEN", calculateIDF(1, 2) * 1),
					new TermWeightTuple("BLUE", calculateIDF(1, 2) * 1)}) {
				entityTermsTuples.add(entityTermTuple);
			}
			
			for (TermWeightTuple otherEntityTermTuple : new TermWeightTuple[] {
					new TermWeightTuple("public", calculateIDF(2, 2) * 1),
					new TermWeightTuple("atributo", calculateIDF(2, 2) * 2),
					new TermWeightTuple("interna", calculateIDF(1, 2) * 2),
					new TermWeightTuple("private", calculateIDF(2, 2) * 1)}) {
				otherEntityTermsTuples.add(otherEntityTermTuple);
			}
		}
		
		tfidfAbsoluteResult.put(entity, entityTermsTuples);
		tfidfAbsoluteResult.put(otherEntity, otherEntityTermsTuples);
		
		return tfidfAbsoluteResult;
	}

	private Map<String, List<TermWeightTuple>> constructTFIDFRelativeResult(boolean isCaseSensitive) {
		Map<String, List<TermWeightTuple>> tfidfRelativeResult = new HashMap<String, List<TermWeightTuple>>();

		String entity = "GenericClass";
		String otherEntity = "Interna";
		List<TermWeightTuple> entityTermsTuples = new ArrayList<TermWeightTuple>();
		List<TermWeightTuple> otherEntityTermsTuples = new ArrayList<TermWeightTuple>();
		final int ENTITY_NUMBER_OF_TERMS = 22;
		final int OTHER_ENTITY_NUMBER_OF_TERMS = 6;
		
		if (isCaseSensitive) {
			for (TermWeightTuple entityTermTuple : new TermWeightTuple[] {
					new TermWeightTuple("PUBLIC", calculateIDF(1, 2) * (1.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("CONSTANTE", calculateIDF(1, 2) * (2.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("PRIVATE", calculateIDF(1, 2) * (1.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("public", calculateIDF(2, 2) * (2.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("atributo", calculateIDF(2, 2) * (2.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("private", calculateIDF(2, 2) * (2.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("metodo", calculateIDF(1, 2) * (2.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("parametro1", calculateIDF(1, 2) * (2.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("parametro2", calculateIDF(1, 2) * (2.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("local", calculateIDF(1, 2) * (1.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("variavel", calculateIDF(1, 2) * (1.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("GenericEnum", calculateIDF(1, 2) * (1.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("RED", calculateIDF(1, 2) * (1.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("GREEN", calculateIDF(1, 2) * (1.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("BLUE", calculateIDF(1, 2) * (1.0 / ENTITY_NUMBER_OF_TERMS))}) {
				entityTermsTuples.add(entityTermTuple);
			}
			
			for (TermWeightTuple otherEntityTermTuple : new TermWeightTuple[] {
					new TermWeightTuple("public", calculateIDF(2, 2) * (1.0 / OTHER_ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("atributo", calculateIDF(2, 2) * (2.0 / OTHER_ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("interna", calculateIDF(1, 2) * (2.0 / OTHER_ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("private", calculateIDF(2, 2) * (1.0 / OTHER_ENTITY_NUMBER_OF_TERMS))}) {
				otherEntityTermsTuples.add(otherEntityTermTuple);
			}
		} else {
			for (TermWeightTuple entityTermTuple : new TermWeightTuple[] {
					new TermWeightTuple("PUBLIC", calculateIDF(2, 2) * (3.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("CONSTANTE", calculateIDF(1, 2) * (2.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("PRIVATE", calculateIDF(2, 2) * (3.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("atributo", calculateIDF(2, 2) * (2.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("metodo", calculateIDF(1, 2) * (2.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("parametro1", calculateIDF(1, 2) * (2.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("parametro2", calculateIDF(1, 2) * (2.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("local", calculateIDF(1, 2) * (1.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("variavel", calculateIDF(1, 2) * (1.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("GenericEnum", calculateIDF(1, 2) * (1.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("RED", calculateIDF(1, 2) * (1.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("GREEN", calculateIDF(1, 2) * (1.0 / ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("BLUE", calculateIDF(1, 2) * (1.0 / ENTITY_NUMBER_OF_TERMS))}) {
				entityTermsTuples.add(entityTermTuple);
			}
			
			for (TermWeightTuple otherEntityTermTuple : new TermWeightTuple[] {
					new TermWeightTuple("public", calculateIDF(2, 2) * (1.0 / OTHER_ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("atributo", calculateIDF(2, 2) * (2.0 / OTHER_ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("interna", calculateIDF(1, 2) * (2.0 / OTHER_ENTITY_NUMBER_OF_TERMS)),
					new TermWeightTuple("private", calculateIDF(2, 2) * (1.0 / OTHER_ENTITY_NUMBER_OF_TERMS))}) {
				otherEntityTermsTuples.add(otherEntityTermTuple);
			}
		}
		
		tfidfRelativeResult.put(entity, entityTermsTuples);
		tfidfRelativeResult.put(otherEntity, otherEntityTermsTuples);
		
		return tfidfRelativeResult;
	}
	
	private void reconstructIR(String irFunctionType, String tfVariant,
			String scoreCalculator, String caseSensitive, String distanceFunction) {
		writeNewProperties(irFunctionType, tfVariant, scoreCalculator, caseSensitive,
				distanceFunction);

		try {
			irProperties.load(new FileInputStream(new File(PROPERTIES_PATH)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ir = new IR(entitiesAndTerms, irProperties);
	}

	private void writeNewProperties(String irFunctionType, String tfVariant,
			String scoreCalculator, String caseSensitive, String distanceFunction) {
		try {
			FileWriter fileWriter = new FileWriter(new File(PROPERTIES_PATH));

			String[] parameters = new String[] { irFunctionType, tfVariant,
					scoreCalculator, caseSensitive, distanceFunction };
			String[] keysOfParameters = new String[] { "irFunctionType",
					"tfVariant", "scoreCalculator", "caseSensitive", "distanceFunction" };

			for (int parameterIndex = 0; parameterIndex < parameters.length; parameterIndex++) {
				fileWriter.write(keysOfParameters[parameterIndex] + "="
						+ parameters[parameterIndex] + LINE_SEPARATOR);
			}

			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}