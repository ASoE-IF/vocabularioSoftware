package org.splab.vocabulary.iR.tests;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;

import org.splab.vocabulary.iR.IR;
import org.splab.vocabulary.iR.info.IRInfo;
import org.splab.vocabulary.iR.util.TermWeightTuple;
import org.splab.vocabulary.filter.IdentifierFilter;
import org.splab.vocabulary.vxl.VXLReader;
import org.splab.vocabulary.vxl.iterator.VXLIterator;
import org.splab.vocabulary.vxl.iterator.javamodel.ContainerEntity;

import ptstemmer.exceptions.PTStemmerException;

public abstract class GeneralIRTest extends TestCase {

	private final String FILTER_PROPERTY_FILE_NAME = "files/identifierfilter.properties";
	private final String TERM_EXTRACTION_PROPERTY_FILE_NAME = "files/termscounter.properties";
	protected String IR_CALCULATOR_PROPERTY_FILE_NAME = ""; // extensible

	private final String VXL_FILE_NAME = "files/IRTest1-2.vxl";

	private IdentifierFilter identifierFilter;

	protected IRInfo info;

	protected final String PERSON = "Person.c";
	protected final String OFFICE = "Office.c";
	protected final String EMPLOYEE = "Employee.c";
	protected final String HOUSE = "House.c";

	protected void setUp() throws Exception {
		super.setUp();

		Properties vxlReaderProps = validateProperties(TERM_EXTRACTION_PROPERTY_FILE_NAME);
		VXLReader vxlReader = new VXLReader(vxlReaderProps);
		vxlReader.load(VXL_FILE_NAME);

		Properties props = new Properties();
		props.load(new FileInputStream(FILTER_PROPERTY_FILE_NAME));

		this.identifierFilter = new IdentifierFilter(props);

		this.info = generateIRInfo(vxlReader);
	}

	private IRInfo generateIRInfo(VXLReader vxlReader) throws PTStemmerException {
		Map<String, List<TermWeightTuple>> frequencyMap;
		Map<String, Map<String, Integer>> termsByEntity = new HashMap<String, Map<String, Integer>>();

		VXLIterator entityIterator = vxlReader.iterator();
		// for all entities, extract all identifiers and count terms
		while (entityIterator.hasNext()) {
			ContainerEntity entity = entityIterator.next();
			Map<String, Integer> identifiers = entity.getIdentifiers();

			Map<String, Integer> entityTerms = new HashMap<String, Integer>();
			for (String idtf : identifiers.keySet()) {
				int frequency = identifiers.get(idtf);
				String[] identifierArray = { idtf };
				String[] terms = this.identifierFilter.filterIdentifiers(identifierArray);

				for (String t : terms) {
					if (entityTerms.containsKey(t)) {
						entityTerms.put(t, entityTerms.get(t) + frequency);
					} else {
						entityTerms.put(t, frequency);
					}
				}
			}
			termsByEntity.put(entity.getEntityIdentifier(), entityTerms);
		}

		Properties irProps = validateProperties(IR_CALCULATOR_PROPERTY_FILE_NAME);
		IR ir = new IR(termsByEntity, irProps);

		frequencyMap = ir.calculate();
		return new IRInfo(frequencyMap, irProps);
	}

	private Properties validateProperties(String propertyFileName) {

		Properties props = new Properties();
		try {
			props.load(new BufferedInputStream(new FileInputStream(propertyFileName)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return props;
	}
}