package org.splab.vocabulary.iR.tests;

import java.util.Map;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

public class IRAbsoluteTFIDFTest extends GeneralIRTest {

	protected void setUp() throws Exception {

		this.IR_CALCULATOR_PROPERTY_FILE_NAME = "files/IR-absolutetfidf.properties";
		super.setUp();
	}

	public void testGetTermDocumentMatrix() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		assertEquals(74.26732054741319, termDocumentMatrix.zSum(), 0.001);
	}

	public void testGetWeightVector() {
		DoubleMatrix1D weightVector = info.getDocumentVector(HOUSE);
		assertEquals(7.0102999566398125, weightVector.zSum(), 0.00001);
		weightVector = info.getDocumentVector(OFFICE);
		assertEquals(16.235019852575363, weightVector.zSum(), 0.00001);
		weightVector = info.getDocumentVector(EMPLOYEE);
		assertEquals(30.019619699012424, weightVector.zSum(), 0.00001);
		weightVector = info.getDocumentVector(PERSON);
		assertEquals(21.002381039185593, weightVector.zSum(), 0.00001);
	}

	public void testGetAllDocumentIds() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allDocumentIds = info.getAllDocumentIdsMap();
		assertEquals(4, info.getAllDocumentIds().size());

		Integer index = allDocumentIds.get(HOUSE);
		DoubleMatrix1D weightVector = termDocumentMatrix.viewColumn(index);
		assertEquals(7.0102999566398125, weightVector.zSum(), 0.00001);
		index = allDocumentIds.get(OFFICE);
		weightVector = termDocumentMatrix.viewColumn(index);
		assertEquals(16.235019852575363, weightVector.zSum(), 0.00001);
		index = allDocumentIds.get(EMPLOYEE);
		weightVector = termDocumentMatrix.viewColumn(index);
		assertEquals(30.019619699012424, weightVector.zSum(), 0.00001);
		index = allDocumentIds.get(PERSON);
		weightVector = termDocumentMatrix.viewColumn(index);
		assertEquals(21.002381039185593, weightVector.zSum(), 0.00001);
	}

	// testar pra todos
	public void testGetAllTerms() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allTerms = info.getAllTermIdsMap();
		assertEquals(11, info.getAllTerms().size());

		Integer index = allTerms.get("address");
		DoubleMatrix1D termVector = termDocumentMatrix.viewRow(index);
		assertEquals(19.86797971382276, termVector.zSum(), 0.00001);

		index = allTerms.get("company");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(5.204119982655925, termVector.zSum(), 0.00001);

		index = allTerms.get("offic");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(8.0, termVector.zSum(), 0.00001);

		index = allTerms.get("const");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(2.6020599913279625, termVector.zSum(), 0.00001);

		index = allTerms.get("hous");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(2.60206, termVector.zSum(), 0.00001);

		index = allTerms.get("employe");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(2.60206, termVector.zSum(), 0.00001);

		index = allTerms.get("home");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(6.749632419649799, termVector.zSum(), 0.00001);

		index = allTerms.get("name");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(7.874571156258099, termVector.zSum(), 0.00001);

		index = allTerms.get("person");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(11.249387366082999, termVector.zSum(), 0.00001);

		index = allTerms.get("posit");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(3.9030899869919438, termVector.zSum(), 0.00001);
	}

	public void testGetAllTermsHouse() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allDocumentIds = info.getAllDocumentIdsMap();
		Map<String, Integer> allTerms = info.getAllTermIdsMap();

		Integer docIndex = allDocumentIds.get(HOUSE);

		Integer termIndex = allTerms.get("address");
		double weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(0.90309, weight, 0.00001);

		termIndex = allTerms.get("const");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(2.6020599913279625, weight, 0.00001);

		termIndex = allTerms.get("hous");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(2.6020599913279625, weight, 0.00001);
	}

	public void testGetAllTermsOffice() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allDocumentIds = info.getAllDocumentIdsMap();
		Map<String, Integer> allTerms = info.getAllTermIdsMap();

		Integer docIndex = allDocumentIds.get(OFFICE);

		Integer termIndex = allTerms.get("address");
		double weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(8.127809882927492, weight, 0.00001);

		termIndex = allTerms.get("company");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(5.204119982655925, weight, 0.00001);

		termIndex = allTerms.get("offic");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(2, weight, 0.00001);
	}

	public void testGetAllTermsEmployee() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allDocumentIds = info.getAllDocumentIdsMap();
		Map<String, Integer> allTerms = info.getAllTermIdsMap();

		Integer docIndex = allDocumentIds.get(EMPLOYEE);

		Integer termIndex = allTerms.get("address");
		double weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(3.6123599479677746, weight, 0.00001);

		termIndex = allTerms.get("employe");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(2.60206, weight, 0.00001);

		termIndex = allTerms.get("home");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(3.3748162098248997, weight, 0.00001);

		termIndex = allTerms.get("name");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(5.6246936830414995, weight, 0.00001);

		termIndex = allTerms.get("offic");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(1.0, weight, 0.00001);

		termIndex = allTerms.get("person");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(8.9995098928664, weight, 0.00001);

		termIndex = allTerms.get("posit");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(3.9030899869919438, weight, 0.00001);
	}

	public void testGetAllTermsPerson() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allDocumentIds = info.getAllDocumentIdsMap();
		Map<String, Integer> allTerms = info.getAllTermIdsMap();

		Integer docIndex = allDocumentIds.get(PERSON);

		Integer termIndex = allTerms.get("address");
		double weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(7.224719895935549, weight, 0.00001);

		termIndex = allTerms.get("home");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(3.3748162098248997, weight, 0.00001);

		termIndex = allTerms.get("name");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(2.2498774732166, weight, 0.00001);

		termIndex = allTerms.get("offic");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(5.0, weight, 0.00001);

		termIndex = allTerms.get("person");
		weight = termDocumentMatrix.get(termIndex, docIndex);

		assertEquals(2.2498774732166, weight, 0.00001);
	}

	// public void testBuildEquivalentVector1() {
	// List<String> terms = new ArrayList<String>();
	// terms.add("cost");
	// terms.add("cost");
	// terms.add("person");
	// DoubleMatrix1D queryVector = info.buildEquivalentVector(terms);
	// assertEquals(0.0, queryVector.get(0));
	// assertEquals(2.60206, queryVector.get(3), 0.0001);
	// assertEquals(1.12494, queryVector.get(7), 0.0001);
	// assertEquals(0.0,
	// queryVector.get(info.getAllTermIdsMap().get("address")));
	// assertEquals(2.60206,
	// queryVector.get(info.getAllTermIdsMap().get("cost")), 0.0001);
	// assertEquals(1.12494,
	// queryVector.get(info.getAllTermIdsMap().get("person")), 0.0001);
	// }
	//
	// public void testBuildEquivalentVector2() {
	// List<String> terms = new ArrayList<String>();
	// terms.add("cost");
	// terms.add("cost");
	// terms.add("person");
	// terms.add("car");
	// DoubleMatrix1D queryVector = info.buildEquivalentVector(terms);
	// assertEquals(0.0, queryVector.get(0));
	// assertEquals(2.60206, queryVector.get(3), 0.0001);
	// assertEquals(1.12494, queryVector.get(7), 0.0001);
	// assertEquals(0.0,
	// queryVector.get(info.getAllTermIdsMap().get("address")));
	// assertEquals(2.60206,
	// queryVector.get(info.getAllTermIdsMap().get("cost")), 0.0001);
	// assertEquals(1.12494,
	// queryVector.get(info.getAllTermIdsMap().get("person")), 0.0001);
	// }
}