package org.splab.vocabulary.extractor.vloccount;

import org.splab.vocabulary.extractor.vloccount.LOCCountPerEntity;

/**
 * All this class does is keep LOC Counting information, no need for tests
 * @author Tercio de Melo
 */
public class EntityLOCKeeper {
	private int loc, headers, innerEntitiesLOC;
	
	public EntityLOCKeeper(LOCCountPerEntity locCounter, boolean isInner) {
		this.loc = locCounter.getLOC(); this.innerEntitiesLOC = locCounter.getInner();
	}
	
	
	/**
	 * Increases loc by a given value. Used to append inner enums' loc.
	 * @param increase
	 */
	public void increaseLOC(Integer increase) {
		loc += increase;
	}
	
	
	/**
	 * Returns the LOC Counting of a an entity
	 * @return
	 */
	public int getLOC() {
		return loc;
	}
	
	/**
	 * Returns the sum LOC Counts of the inner entities of an entity
	 * @return
	 */
	public int getInnerEntitiesLOC() {
		return innerEntitiesLOC;
	}
	
	/**
	 * Returns the Headers counting if it isn't an inner entity
	 * @return
	 */
	public int getHeaderLOC() {
		return headers;
	}
	
	/**
	 * Set the headers loc based on an information that tells if an entity is an inner entity or not and if
	 * header count is desired.
	 * @param headersLOC Externally provided headers count
	 * @param isInner Information of being inner entity
	 * @param extractHeader Information about header extraction
	 */
	public void setHeadersLOC(int headersLOC, boolean isInner, boolean extractHeader) {
		this.headers =  isInner ? 0 : extractHeader ? headersLOC : 0;
	}
}