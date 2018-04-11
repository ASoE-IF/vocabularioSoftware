package test.java.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class VXLHandler extends DefaultHandler {
	
	private Map<String, List<String>> entities;
	
	public VXLHandler() {
		this.entities = new HashMap<String, List<String>>();
	}
	
	// http://www.java2s.com/Code/JavaAPI/org.xml.sax.helpers/extendsDefaultHandler.htm
	// http://www.saxproject.org/quickstart.html
	// http://tutorials.jenkov.com/java-xml/sax-defaulthandler.html
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		
		String entityName = "";
		List<String> entityProperties = new LinkedList<String>();
		
	    for(int i = 0 ; i<attributes.getLength() ; i++) {
	    	
	    	String attributeName = attributes.getQName(i);
	    	String attributeValue = attributes.getValue(i);
	    	
	    	if (attributeName.equals("name") || attributeName.equals("namespace")) {
	    		entityName = attributeValue;
	    	} else if (!attributeName.equals("id")) {
	    		entityProperties.add(attributeName);
	    		entityProperties.add(attributeValue);
	    	}
	    }
	    
	    this.entities.put(entityName, entityProperties);
	}
	
	public Map<String, List<String>> getEntityProps() {
		return this.entities;
	}
	
	public static void main(String[] args) throws SAXException, IOException {
		
		String vxlFileName = "C:\\workspace\\eclipse-sourceBuild-srcIncluded-3.0\\eclipse-sourceBuild-srcIncluded-3.0.vxl";
		XMLReader xr = XMLReaderFactory.createXMLReader();
		VXLHandler handler = new VXLHandler();
		xr.setContentHandler(handler);
		xr.setErrorHandler(handler);
		
		FileReader r = new FileReader(vxlFileName);
	    xr.parse(new InputSource(r));
	}
}