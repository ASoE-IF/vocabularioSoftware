package org.splab.vocabulary.vxl.iterator.javamodel;

import java.util.HashMap;
import java.util.Map;

import org.splab.vocabulary.vxl.iterator.util.VXLReaderPropertyKeys;
import org.splab.vocabulary.vxl.iterator.util.VXLReaderPropertyKeys.ContainerType;

public class ContainerEntity {

	private String entityIdentifier;
	private VXLReaderPropertyKeys.ContainerType entityType;
	
	private Map<String, Integer> identifiers;
	private Object entity;
	
	public ContainerEntity(String identifier, Map<String, Integer> identifiers, Object entity, ContainerType type) {
		this.entityIdentifier = identifier;
		this.entityType = type;
		this.identifiers = removeEmptyKeys(identifiers);
		this.entity = entity;
	}

	private Map<String, Integer> removeEmptyKeys(Map<String, Integer> map) {
		Map<String, Integer> output = new HashMap<String, Integer>();
		for (String s : map.keySet()) {
			if (!s.equals("")) {
				output.put(s, map.get(s));
			}
		}
		
		return output;
	}
	
	public Map<String, Integer> getIdentifiers() {
		return identifiers;
	}

	public String getEntityIdentifier() {
		return this.entityIdentifier;
	}
	
	public ContainerType getEntityType() {
		return this.entityType;
	}

	public Object getEntity() {
		return entity;
	}
}