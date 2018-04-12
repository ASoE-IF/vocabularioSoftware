package org.splab.vocabulary.extractor.processors;

import java.util.Map;
import java.util.TreeMap;

public class FileVocabularyManager {

	protected static Map<String, Integer> gvar;
	protected static Map<String, String> gvarStorage;
	protected static Map<String, String> gvarAccess;

	protected static Map<String, String> prttpStorage;
	protected static Map<String, String> prttpAccess;
	
	protected static Map<String, Integer> literal;
	
	
	public static void initializerVariables(){
		gvar = new TreeMap<String, Integer>();
		gvarStorage = new TreeMap<String, String>();
		gvarAccess = new TreeMap<String, String>();

		prttpStorage = new TreeMap<String, String>();
		prttpAccess = new TreeMap<String, String>();
		
		literal = new TreeMap<String, Integer>();
	}

	/** Insere o vocabulário das variáveis globais **/
	public static void insertGlobalVariable(String variable, String access, String storage) {

		if (variable != null && !variable.equals("")) {
			if (gvar.containsKey(variable)) {
				gvar.put(variable, gvar.get(variable) + 1);
			} else {
				gvar.put(variable, 1);
				gvarAccess.put(variable, access);
				gvarStorage.put(variable, storage);
			}
		}
	}

	public static void insertGlobalPrttp(String name, String access, String storage) {

		if (name != null && !name.equals("")) {
			if (!prttpStorage.containsKey(name)) {
				prttpAccess.put(name, access);
				prttpStorage.put(name, storage);
			}
		}
	}
	
	public static void insertLiteral(String literalString) {
		if (literalString != null && !literalString.trim().equals("")) {
			if (literal.containsKey(literalString)) {
				literal.put(literalString, literal.get(literalString) + 1);
			} else {
				literal.put(literalString, 1);
			}
		}
	}
	
	public static void clear() {
		gvar.clear();
		gvarStorage.clear();
		gvarAccess.clear();
		prttpStorage.clear();
		prttpAccess.clear();
		literal.clear();
	}
}
