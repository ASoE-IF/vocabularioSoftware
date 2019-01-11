package org.splab.vocabulary.vxl.iterator.javamodel;

/**
 * Normalize the string read from VXL file.
 * @author Catharine Quintans
 * @date December 11, 2012
 */
public class StringProcessor {
	
	/**
	 * Processes the comment and literals, converting some characters and returning the correct content.
	 * @param literalString the comment with undesirable characters
	 * @return
	 */
	public static String processString(String literalString) {
		//read: http://www.xmlnews.org/docs/xml-basics.html
		
//		literalString = removeInvalidCharacters(literalString);

		while (literalString.contains("&quot;")) 
			literalString = literalString.replace("&quot;", "\"");

		while (literalString.contains("&apos;")) 
			literalString = literalString.replace("&apos;", "'");
		
		while (literalString.contains("&lt;"))
			literalString = literalString.replace("&lt;", "<");
		
		while (literalString.contains( "&gt;"))
			literalString = literalString.replace( "&gt;", ">");
		
		if (literalString.contains("&amp;"))
			literalString = literalString.replace("&amp;", "&");
		
		literalString = literalString.trim();
		literalString = literalString.replaceAll("\\s+", " ");
		
		return literalString;
	}
}