package org.splab.vocabulary.iR.tests;

import java.util.Map;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

public class IRAbsoluteTermFrequencyTest extends GeneralIRTest {

	protected void setUp() throws Exception {
		
		this.IR_CALCULATOR_PROPERTY_FILE_NAME = "files/IR-tfabsolute.properties";
		super.setUp();
	}
	
	public void testGetTermDocumentMatrix() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		assertEquals(70.0, termDocumentMatrix.zSum());
	}
		
	public void testGetWeightVector() {
		DoubleMatrix1D weightVector = info.getDocumentVector(HOUSE);
		assertEquals(6.0, weightVector.zSum());
		weightVector = info.getDocumentVector(OFFICE);
		assertEquals(16.0, weightVector.zSum());
		weightVector = info.getDocumentVector(EMPLOYEE);
		assertEquals(27.0, weightVector.zSum());
		weightVector = info.getDocumentVector(PERSON);
		assertEquals(21.0, weightVector.zSum());	
	}
	
	public void testGetAllDocumentIds() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allDocumentIds = info.getAllDocumentIdsMap();
		assertEquals(4, info.getAllDocumentIds().size());
		
		Integer index = allDocumentIds.get(HOUSE);
		DoubleMatrix1D weightVector = termDocumentMatrix.viewColumn(index);
		assertEquals(6.0, weightVector.zSum());
		index = allDocumentIds.get(OFFICE);
		weightVector = termDocumentMatrix.viewColumn(index);
		assertEquals(16.0, weightVector.zSum());
		index = allDocumentIds.get(EMPLOYEE);
		weightVector = termDocumentMatrix.viewColumn(index);
		assertEquals(27.0, weightVector.zSum());
		index = allDocumentIds.get(PERSON);
		weightVector = termDocumentMatrix.viewColumn(index);
		assertEquals(21.0, weightVector.zSum());
	}
	
	public void testGetAllTerms() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allTerms = info.getAllTermIdsMap();
		assertEquals(11, info.getAllTerms().size());
		
		Integer index = allTerms.get("address");
		DoubleMatrix1D termVector = termDocumentMatrix.viewRow(index);
		assertEquals(22.0, termVector.zSum());
		
		index = allTerms.get("company");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(4.0, termVector.zSum());
		
		index = allTerms.get("offic");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(8.0, termVector.zSum());
		
		index = allTerms.get("const");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(2.0, termVector.zSum());

		index = allTerms.get("hous");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(2.0, termVector.zSum());
		
		index = allTerms.get("employe");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(2.0, termVector.zSum());
		
		index = allTerms.get("home");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(6.0, termVector.zSum());
		
		index = allTerms.get("name");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(7.0, termVector.zSum());
		
		index = allTerms.get("person");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(10.0, termVector.zSum());
		
		index = allTerms.get("posit");
		termVector = termDocumentMatrix.viewRow(index);
		assertEquals(3.0, termVector.zSum());
	}
	
	public void testGetAllTermsHouse() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allDocumentIds = info.getAllDocumentIdsMap();
		Map<String, Integer> allTerms = info.getAllTermIdsMap();
		
		Integer docIndex = allDocumentIds.get(HOUSE);
		
		Integer termIndex = allTerms.get("address");
		double weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(1D, weight);
		
		termIndex = allTerms.get("const");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(2D, weight);
		
		termIndex = allTerms.get("hous");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(2D, weight);
	}
	
	public void testGetAllTermsOffice() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allDocumentIds = info.getAllDocumentIdsMap();
		Map<String, Integer> allTerms = info.getAllTermIdsMap();
		
		Integer docIndex = allDocumentIds.get(OFFICE);
		
		Integer termIndex = allTerms.get("address");
		System.out.println(termIndex + " " + docIndex);
		double weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(9D, weight);
		
		termIndex = allTerms.get("company");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(4D, weight);
		
		termIndex = allTerms.get("offic");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(2D, weight);
	}
	
	public void testGetAllTermsEmployee() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allDocumentIds = info.getAllDocumentIdsMap();
		Map<String, Integer> allTerms = info.getAllTermIdsMap();
		
		Integer docIndex = allDocumentIds.get(EMPLOYEE);
		
		Integer termIndex = allTerms.get("address");
		double weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(4D, weight);
		
		termIndex = allTerms.get("employe");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(2D, weight);
		
		termIndex = allTerms.get("home");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(3D, weight);
		
		termIndex = allTerms.get("name");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(5D, weight);
		
		termIndex = allTerms.get("offic");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(1D, weight);
		
		termIndex = allTerms.get("person");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(8D, weight);
		
		termIndex = allTerms.get("posit");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(3D, weight);
	}
	
	public void testGetAllTermsPerson() {
		DoubleMatrix2D termDocumentMatrix = info.getTermDocumentMatrix();
		Map<String, Integer> allDocumentIds = info.getAllDocumentIdsMap();
		Map<String, Integer> allTerms = info.getAllTermIdsMap();
		
		Integer docIndex = allDocumentIds.get(PERSON);
		
		Integer termIndex = allTerms.get("address");
		double weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(8D, weight);
		
		termIndex = allTerms.get("home");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(3D, weight);
		
		termIndex = allTerms.get("name");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(2D, weight);
		
		termIndex = allTerms.get("offic");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(5D, weight);
		
		termIndex = allTerms.get("person");
		weight = termDocumentMatrix.get(termIndex, docIndex);
		assertEquals(2D, weight);
	}
}