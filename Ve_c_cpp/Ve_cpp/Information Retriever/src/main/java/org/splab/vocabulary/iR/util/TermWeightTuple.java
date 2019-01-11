package org.splab.vocabulary.iR.util;

/**
 * Utility class to store the weight of a term in one document.
 * 
 * @author gustavojss
 */
public class TermWeightTuple {

	private String term;
	private double weight;
	
	public TermWeightTuple(String term, double weight){
		this.term = term;
		this.weight = weight;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public String toString(){
		return "("+this.term+", "+this.weight+")";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((term == null) ? 0 : term.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TermWeightTuple other = (TermWeightTuple) obj;
		if (term == null) {
			if (other.term != null)
				return false;
		} else if (!term.equals(other.term))
			return false;
		return true;
	}
}