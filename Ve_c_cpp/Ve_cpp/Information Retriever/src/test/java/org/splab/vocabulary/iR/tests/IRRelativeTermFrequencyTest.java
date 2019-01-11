package org.splab.vocabulary.iR.tests;

import java.util.Map;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

public class IRRelativeTermFrequencyTest extends GeneralIRTest {

	protected void setUp() throws Exception {

		this.IR_CALCULATOR_PROPERTY_FILE_NAME = "files/IR-tfrelative.properties";
		super.setUp();
	}

	public void testGetTermDocumentMatrix() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		assertEquals(4.0, termDocumentMatrix.zSum(), 0.01);
	}

	public void testGetWeightVector() {
		DoubleMatrix1D weightVector = info.getDocumentVector(HOUSE);
		assertEquals(1.0, weightVector.zSum(), 0.01);
		weightVector = info.getDocumentVector(OFFICE);
		assertEquals(1.0, weightVector.zSum(), 0.01);
		weightVector = info.getDocumentVector(EMPLOYEE);
		assertEquals(1.0, weightVector.zSum(), 0.01);
		weightVector = info.getDocumentVector(PERSON);
		assertEquals(1.0, weightVector.zSum(), 0.01);
	}

	public void testGetAllDocumentIds() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allDocumentIds = info.getAllDocumentIdsMap();
		assertEquals(4, info.getAllDocumentIds().size());

		Integer index = allDocumentIds.get(HOUSE);
		DoubleMatrix1D weightVector = termDocumentMatrix.viewColumn(index);
		assertEquals(1.0, weightVector.zSum(), 0.01);
		index = allDocumentIds.get(OFFICE);
		weightVector = termDocumentMatrix.viewColumn(index);
		assertEquals(1.0, weightVector.zSum(), 0.01);
		index = allDocumentIds.get(EMPLOYEE);
		weightVector = termDocumentMatrix.viewColumn(index);
		assertEquals(1.0, weightVector.zSum(), 0.01);
		index = allDocumentIds.get(PERSON);
		weightVector = termDocumentMatrix.viewColumn(index);
		assertEquals(1.0, weightVector.zSum(), 0.01);
	}

	// testar pra todos
	public void testGetAllTerms() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allTerms = info.getAllTermIdsMap();
		assertEquals(11, info.getAllTerms().size());

		Integer index = allTerms.get("address");
		DoubleMatrix1D termVector = termDocumentMatrix.viewRow(index);
		assertEquals(1.2582671957671958, termVector.zSum(), 0.01);

		index = allTerms.get("company");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(0.25, termVector.zSum(), 0.01);

		index = allTerms.get("offic");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(0.4001322751322751, termVector.zSum(), 0.01);

		index = allTerms.get("const");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(0.3333333333333333, termVector.zSum(), 0.01);

		index = allTerms.get("hous");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(0.3333333333333333, termVector.zSum(), 0.01);

		index = allTerms.get("employe");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(0.07407407407407407, termVector.zSum(), 0.01);

		index = allTerms.get("home");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(0.25396825396825395, termVector.zSum(), 0.01);

		index = allTerms.get("name");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(0.2804232804232804, termVector.zSum(), 0.01);

		index = allTerms.get("person");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(0.39682, termVector.zSum(), 0.01);

		index = allTerms.get("posit");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(0.11111, termVector.zSum(), 0.01);
	}

	public void testGetAllTermsHouse() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allDocumentIds = info.getAllDocumentIdsMap();
		Map<String, Integer> allTerms = info.getAllTermIdsMap();

		Integer docIndex = allDocumentIds.get(HOUSE);

		Integer termIndex = allTerms.get("address");
		double weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.16666666666666666, weight);

		termIndex = allTerms.get("const");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.3333333333333333, weight);

		termIndex = allTerms.get("hous");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.3333333333333333, weight);
	}

	public void testGetAllTermsOffice() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allDocumentIds = info.getAllDocumentIdsMap();
		Map<String, Integer> allTerms = info.getAllTermIdsMap();

		Integer docIndex = allDocumentIds.get(OFFICE);

		Integer termIndex = allTerms.get("address");
		double weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.5625, weight);

		termIndex = allTerms.get("company");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.25, weight);

		termIndex = allTerms.get("offic");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.125, weight);
	}

	public void testGetAllTermsEmployee() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allDocumentIds = info.getAllDocumentIdsMap();
		Map<String, Integer> allTerms = info.getAllTermIdsMap();

		Integer docIndex = allDocumentIds.get(EMPLOYEE);

		Integer termIndex = allTerms.get("address");
		double weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.14814814814814814, weight, 0.00001);

		termIndex = allTerms.get("employe");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.07407407407407407, weight, 0.00001);

		termIndex = allTerms.get("home");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.11111, weight, 0.00001);

		termIndex = allTerms.get("name");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.18518518518518517, weight, 0.00001);

		termIndex = allTerms.get("offic");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.037037037037037035, weight, 0.00001);

		termIndex = allTerms.get("person");
		weight = termDocumentMatrix.get(termIndex, docIndex);

		assertEquals(0.2962962962962963, weight, 0.00001);

		termIndex = allTerms.get("posit");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.11111, weight, 0.00001);
	}

	public void testGetAllTermsPerson() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allDocumentIds = info.getAllDocumentIdsMap();
		Map<String, Integer> allTerms = info.getAllTermIdsMap();

		Integer docIndex = allDocumentIds.get(PERSON);

		Integer termIndex = allTerms.get("address");
		double weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.38095238095238093, weight, 0.00001);

		termIndex = allTerms.get("home");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.14285, weight, 0.00001);

		termIndex = allTerms.get("name");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.09523809523809523, weight, 0.00001);

		termIndex = allTerms.get("offic");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.23809523809523808, weight, 0.00001);

		termIndex = allTerms.get("person");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.09523809523809523, weight, 0.00001);
	}

	// public void testBuildEquivalentVector() {
	// List<String> terms = new ArrayList<String>();
	// terms.add("cost");
	// terms.add("cost");
	// terms.add("person");
	// DoubleMatrix1D queryVector = info.buildEquivalentVector(terms);
	// assertEquals(0.0, queryVector.get(0));
	// assertEquals(0.66666, queryVector.get(3), 0.00001);
	// assertEquals(0.33333, queryVector.get(7), 0.00001);
	// assertEquals(0.0,
	// queryVector.get(info.getAllTermIdsMap().get("address")));
	// assertEquals(0.66666,
	// queryVector.get(info.getAllTermIdsMap().get("cost")), 0.00001);
	// assertEquals(0.33333,
	// queryVector.get(info.getAllTermIdsMap().get("person")), 0.00001);
	// }
}