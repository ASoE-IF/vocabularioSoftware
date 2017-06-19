package Vocabulary.Processors;

import java.util.Map;
import java.util.TreeMap;

/**
 * Manage the vocabulary of methods.
 * @author Catharine Quintans
 * @since November 22, 2012
 */
public class FunctionVocabularyManager {
	static Map<String, Integer> lvar = new TreeMap<String, Integer>();
	static Map<String, Integer> mthInv = new TreeMap<String, Integer>();
	static Map<String, Integer> literal = new TreeMap<String, Integer>();
	
	/**
	 * Insert the locals variables.
	 * @param variable The local variable.
	 */
	public static void insertLocalVariable(String variable) {
		if (variable != null && !variable.equals("")) {
			if (lvar.containsKey(variable)) {
				lvar.put(variable, lvar.get(variable)+1);
			} else {
				lvar.put(variable, 1);
			}
		}
	}
	
	/**
	 * Insert the methods invocations.
	 * @param methodInvocation The method invocation.
	 */
	public static void insertMethodInvocation(String methodInvocation) {
		if (methodInvocation != null && !methodInvocation.equals("")) {
			if (mthInv.containsKey(methodInvocation)) {
				mthInv.put(methodInvocation, mthInv.get(methodInvocation)+1);
			} else {
				mthInv.put(methodInvocation, 1);
			}
		}
	}
	
	/**
	 * Insert the literals.
 	 * @param literalString The literal.
	 */
	public static void insertLiteral(String literalString) {
		if (literalString != null && !literalString.trim().equals("")) {
			if (literal.containsKey(literalString)) {
				literal.put(literalString, literal.get(literalString)+1);
			} else {
				literal.put(literalString, 1);
			}
		}
	}
	
	/***
	 * Return the map contain all the locals variables of the method.
	 * @return
	 */
	public static Map<String,Integer> getLocalVariables() {
		return lvar;
	}
	
	/**
	 * Return the map contain all the locals variables of the method.
	 * @return
	 */
	public static Map<String,Integer> getMethodsInvocation() {
		return mthInv;
	}
	
	/**
	 * Return the map contain all the locals variables of the method.
	 * @return
	 */
	public static Map<String,Integer> getLiterals() {
		return literal;
	}
	
	/**
	 * Clear all the map that store informations about the method vocabulary.	
	 */
	public static void clear() {
		lvar.clear();
		mthInv.clear();
		literal.clear();
	}
}