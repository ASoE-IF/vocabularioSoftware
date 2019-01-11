package org.splab.vocabulary.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.splab.vocabulary.termscounter.TermsCounter;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;

/**
 * This class tests the main operations made in Terms Counter. Are they:
 * Calculate the number of entities, number of distinct terms, number total of
 * terms, the Term x Entity (document) matrix and its marginals.
 * 
 * @author Samuel de Medeiros Queiroz / Catharine Quintans
 * 
 */
public class TermsCounterTests {
	private TermsCounter termsCounter;

	/*
	 * The entities of the matrix.
	 */
	private final String[] entities = new String[] { ".\\dict\\Arquivo Genérico.c", };
	// See termEntityMatrix

	/*
	 * The distinct terms of the matrix, repeated terms are ignored.
	 */
	/*private final String[] distinctTerms = new String[] { "struct", "de", "stdio", "const", "erro", "global", "stdlib",
			"local", "genérico", "isto", "param", "um", "arquivo", "macro", "menssagem", "c", "liter", "h",
			"comentário", "é", "union", "paramt", "enum", "pragma", "variacel", "call", "prttp", "na", "func", "field",
			"name", "style", "interna", "present" }; */// See
														// termEntityMatrix

	/*
	 * The total of terms, repeated or not.
	 */
	private final int totalOfTerms = 543; // See termEntityMatrix

	/*
	 * The matrix as DoubleMatrix2D object, for comparison with the matrix
	 * generated in Terms Counter.
	 */
	private DoubleMatrix2D termDocumentMatrix = new SparseDoubleMatrix2D(
			new double[][] { { 12 }, { 4 }, { 1 }, { 12 }, { 2 }, { 20 }, { 1 }, { 11 }, { 1 }, { 28 }, { 2 }, { 28 },
					{ 91 }, { 8 }, { 4 }, { 1 }, { 91 }, { 2 }, { 28 }, { 28 }, { 12 }, { 4 }, { 12 }, { 2 }, { 31 },
					{ 12 }, { 4 }, { 29 }, { 9 }, { 16 }, { 6 }, { 2 }, { 1 }, { 28 } });
	// See termEntityMatrix

	/**
	 * The constructor method. Instantiates an object of type TermsCounter to
	 * release the tests.
	 */
	public TermsCounterTests() {
		Properties termsCounterProps = generateTermsCounterProperties(
				System.getProperty("user.dir") + "/files/termsCounter.properties");

		this.termsCounter = new TermsCounter(termsCounterProps);
		this.termsCounter.run("./files/testNewVXL.vxl", "./files/teste.txt", "./files/teste.csv");
	}

	/**
	 * Test the calculation of number of entities in Terms Counter.
	 */
	@Test
	public void numberOfEntitiesTest() {
		Assert.assertEquals(entities.length, termsCounter.getNumberOfEntities());
	}

	/**
	 * Test the calculation of number of distinct terms in Terms Counter.
	 */
	@Test
	public void numberOfDistinctTermsTest() {
		Assert.assertEquals(34, termsCounter.getNumberOfDistinctTerms());
	}

	/**
	 * Test the calculation of number total of terms in Terms Counter.
	 */
	@Test
	public void numberOfTotalOfTermsTest() {
		Assert.assertEquals(totalOfTerms, termsCounter.getTotalNumberOfTerms());
	}

	/**
	 * Test the calculation of Term x Entity matrix in Terms Counter.
	 */
	@Test
	public void termDocumentMatrixTest() {
		Assert.assertEquals(termDocumentMatrix, termsCounter.getTermDocumentMatrix());
	}

	/**
	 * Test the calculation of the marginals of the Term x Entity matrix.
	 */
	@Test
	public void marginalsTest() {
		// Assert.assertEquals(mTermFrequency,
		// termsCounter.getMTermFrequency());
		// Assert.assertEquals(mEntityFrequency,
		// termsCounter.getMEntityFrequency());
		// Assert.assertEquals(mEntityTermsFrequencies,
		// termsCounter.getMEntityTermsFrequencies());
		// Assert.assertEquals(mEntityDistinctTerms,
		// termsCounter.getMEntityDistinctTerms());
	}

	/**
	 * Create an Properties object from an .properties address.
	 * 
	 * @param termsCounterPropsFilePath
	 *            The address of the .properties file.
	 * @return An Prooperties object that contains the properties of the
	 *         .properties file.
	 */
	private Properties generateTermsCounterProperties(String termsCounterPropsFilePath) {
		Properties properties = new Properties();

		try {
			properties.load(new FileInputStream(new File(termsCounterPropsFilePath)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return properties;
	}
}