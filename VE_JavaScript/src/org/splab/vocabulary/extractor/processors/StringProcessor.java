package org.splab.vocabulary.extractor.processors;

/**
 * Normalize the string to save correctly at the VXL file.
 * @author Catharine Quintans
 * @since November 30, 2012
 */
public class StringProcessor {
	
	/**
	 * Processes the comment, removing undesirable characters and returning the extracted content
	 * @param literalString the comment with undesirable characters
	 * @return
	 */
	public static String processString(String literalString) {
		//read: http://www.xmlnews.org/docs/xml-basics.html
		
		literalString = removeInvalidCharacters(literalString);
		

		while (literalString.contains("*"))
			literalString = literalString.replace("*", " ");
		
		while (literalString.contains("\n"))
			literalString = literalString.replace("\n", " ");
		
		while (literalString.contains("/"))
			literalString = literalString.replace("/", " ");
		
		while (literalString.contains("\"")) 
			literalString = literalString.replace("\"", "&quot;");

		while (literalString.contains("'")) 
			literalString = literalString.replace("'", "&apos;");
		
		while (literalString.contains("<"))
			literalString = literalString.replace("<", "&lt;");
		
		while (literalString.contains(">"))
			literalString = literalString.replace(">", "&gt;");
		
		if (literalString.contains("&"))
			literalString = literalString.replace("&", "&amp;");
		
		
		literalString = literalString.trim();
		literalString = literalString.replaceAll("\\s+", " ");
		
		return literalString;
	}
	
	/**
	 * Removes invalid characters for UTF-8 encoding
	 * @param text
	 * @return
	 */
	public static String removeInvalidCharacters(String text) {
		
		StringBuffer out = new StringBuffer(); // Used to hold the output.
        char current; // Used to reference the current character.

        if (text == null || ("".equals(text))) return ""; // vacancy test.
        for (int i = 0; i < text.length(); i++) {
            current = text.charAt(i); // NOTE: No IndexOutOfBoundsException caught here; it should not happen.
            if ((current == 0x9) ||
                (current == 0xA) ||
                (current == 0xD) ||
                ((current >= 0x20) && (current <= 0xD7FF)) ||
                ((current >= 0xE000) && (current <= 0xFFFD)) ||
                ((current >= 0x10000) && (current <= 0x10FFFF)))
                out.append(current);
        }
        return out.toString();
	}
}
