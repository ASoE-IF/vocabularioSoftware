package org.splab.vocabulary.termscounter.types;

/**
 * Encapsulates, for a Term, its identifier and its frequency.
 * 
 * @author Katyusco de Farias Santos / Samuel de Medeiros Queiroz
 * 
 */
public class TermTuple {
	private String termId; 	// The term identifier.
	private int	frequency; 	// The term frequency.

	/**
	 * The constructor method, that instantiates a TermTuple object from the term identifier and its frequency.
	 * @param Id The term identifier.
	 * @param freq The term frequency.
	 */
	public TermTuple(String Id, int freq) {
		this.termId = Id;
		this.frequency = freq;
	}

	/**
	 * Return the term identifier.
	 * @return The identifier of the term.
	 */
	public String getTermId() {
		return termId;
	}
	
	/**
	 * Change the term identifier.
	 * @param termId The new term identifier.
	 */
	public void setTermId(String termId) {
		this.termId = termId;
	}

	/**
	 * Returns the term frequency.
	 * @return The term frequency.
	 */
	public int getFrequency() {
		return frequency;
	}

	/**
	 * Change the term frequency.
	 * @param frequency The new term frequency.
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}	
	
	/**
		 * Adds the frequency parameter to the frequency field value.
		 * @param frequency The frequency that will added.
	 */
	public void addFrequency(int frequency) {
		this.frequency += frequency;
	}	
}
