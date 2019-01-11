package org.splab.vocabulary.iR.tests;

import java.util.Map;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

public class IRRelativeTFIDFTest extends GeneralIRTest {

	protected void setUp() throws Exception {

		this.IR_CALCULATOR_PROPERTY_FILE_NAME = "files/IR-relativetfidf.properties";
		super.setUp();
	}

	public void testGetTermDocumentMatrix() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		assertEquals(4.295023216341047, termDocumentMatrix.zSum(), 0.001);
	}

	public void testGetWeightVector() {
		DoubleMatrix1D weightVector = info.getDocumentVector(HOUSE);
		assertEquals(1.1683833261066354, weightVector.zSum(), 0.0001);
		weightVector = info.getDocumentVector(OFFICE);
		assertEquals(1.0146887407859602, weightVector.zSum(), 0.0001);
		weightVector = info.getDocumentVector(EMPLOYEE);
		assertEquals(1.1118377666300896, weightVector.zSum(), 0.0001);
		weightVector = info.getDocumentVector(PERSON);
		assertEquals(1.0001133828183615, weightVector.zSum(), 0.0001);
	}

	public void testGetAllDocumentIds() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allDocumentIds = info.getAllDocumentIdsMap();
		assertEquals(4, info.getAllDocumentIds().size());

		Integer index = allDocumentIds.get(HOUSE);
		DoubleMatrix1D weightVector = termDocumentMatrix.viewColumn(index);
		assertEquals(1.1683833261066354, weightVector.zSum(), 0.0001);
		index = allDocumentIds.get(OFFICE);
		weightVector = termDocumentMatrix.viewColumn(index);
		assertEquals(1.0146887407859602, weightVector.zSum(), 0.0001);
		index = allDocumentIds.get(EMPLOYEE);
		weightVector = termDocumentMatrix.viewColumn(index);
		assertEquals(1.1118377666300896, weightVector.zSum(), 0.0001);
		index = allDocumentIds.get(PERSON);
		weightVector = termDocumentMatrix.viewColumn(index);
		assertEquals(1.0001133828183615, weightVector.zSum(), 0.0001);
	}

	// testar pra todos
	public void testGetAllTerms() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allTerms = info.getAllTermIdsMap();
		assertEquals(11, info.getAllTerms().size());

		Integer index = allTerms.get("address");
		DoubleMatrix1D termVector = termDocumentMatrix.viewRow(index);
		assertEquals(1.136328505457786, termVector.zSum(), 0.0001);

		index = allTerms.get("company");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(0.32525, termVector.zSum(), 0.0001);

		index = allTerms.get("offic");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(0.4001322751322751, termVector.zSum(), 0.0001);

		index = allTerms.get("const");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(0.4336766652213271, termVector.zSum(), 0.0001);

		index = allTerms.get("hous");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(0.4336766652213271, termVector.zSum(), 0.0001);

		index = allTerms.get("employe");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(0.09637259227140602, termVector.zSum(), 0.0001);

		index = allTerms.get("home");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(0.28568, termVector.zSum(), 0.0001);

		index = allTerms.get("name");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(0.31545901079492006, termVector.zSum(), 0.0001);

		index = allTerms.get("person");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(0.4404522037513978, termVector.zSum(), 0.0001);

		index = allTerms.get("posit");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(0.144558888407109, termVector.zSum(), 0.0001);
	}

	public void testGetAllTermsHouse() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allDocumentIds = info.getAllDocumentIdsMap();
		Map<String, Integer> allTerms = info.getAllTermIdsMap();

		Integer docIndex = allDocumentIds.get(HOUSE);

		Integer termIndex = allTerms.get("address");
		double weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.1505149978319906, weight, 0.0001);

		termIndex = allTerms.get("const");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.4336766652213271, weight, 0.0001);

		termIndex = allTerms.get("hous");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.4336766652213271, weight, 0.0001);
	}

	public void testGetAllTermsOffice() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allDocumentIds = info.getAllDocumentIdsMap();
		Map<String, Integer> allTerms = info.getAllTermIdsMap();

		Integer docIndex = allDocumentIds.get(OFFICE);

		Integer termIndex = allTerms.get("address");
		double weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.5079881176829683, weight, 0.0001);

		termIndex = allTerms.get("company");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.32525, weight, 0.0001);

		termIndex = allTerms.get("offic");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.125, weight, 0.0001);
	}

	public void testGetAllTermsEmployee() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allDocumentIds = info.getAllDocumentIdsMap();
		Map<String, Integer> allTerms = info.getAllTermIdsMap();

		Integer docIndex = allDocumentIds.get(EMPLOYEE);

		Integer termIndex = allTerms.get("address");
		double weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.13379110918399165, weight, 0.0001);

		termIndex = allTerms.get("employe");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.09637259227140602, weight, 0.0001);

		termIndex = allTerms.get("home");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.12499, weight, 0.0001);

		termIndex = allTerms.get("name");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.20832198826079626, weight, 0.0001);

		termIndex = allTerms.get("offic");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.037037037037037035, weight, 0.0001);

		termIndex = allTerms.get("person");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.33331518121727405, weight, 0.0001);

		termIndex = allTerms.get("posit");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.14455, weight, 0.0001);
	}

	public void testGetAllTermsPerson() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allDocumentIds = info.getAllDocumentIdsMap();
		Map<String, Integer> allTerms = info.getAllTermIdsMap();

		Integer docIndex = allDocumentIds.get(PERSON);

		Integer termIndex = allTerms.get("address");
		double weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.34403428075883563, weight, 0.0001);

		termIndex = allTerms.get("home");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.16069, weight, 0.0001);

		termIndex = allTerms.get("name");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.1071370225341238, weight, 0.0001);

		termIndex = allTerms.get("offic");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.23809523809523808, weight, 0.0001);

		termIndex = allTerms.get("person");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.1071370225341238, weight, 0.0001);
	}

	// public void testBuildEquivalentVector() {
	// List<String> terms = new ArrayList<String>();
	// terms.add("cost");
	// terms.add("cost");
	// terms.add("person");
	// DoubleMatrix1D queryVector = info.buildEquivalentVector(terms);
	// assertEquals(0.0, queryVector.get(0));
	// assertEquals(0.86734, queryVector.get(3), 0.0001);
	// assertEquals(0.37497, queryVector.get(7), 0.0001);
	// assertEquals(0.0,
	// queryVector.get(info.getAllTermIdsMap().get("address")));
	// assertEquals(0.86734,
	// queryVector.get(info.getAllTermIdsMap().get("cost")), 0.0001);
	// assertEquals(0.37497,
	// queryVector.get(info.getAllTermIdsMap().get("person")), 0.0001);
	// }
}