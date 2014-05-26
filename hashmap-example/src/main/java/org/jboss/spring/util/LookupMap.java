package org.jboss.spring.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class LookupMap<T> {

	private Map<String, T> map;

	public void addLookup(String lookup, T value) {
		map.put(lookup, value);
	}

	public T lookup(String lookup) {
		return map.get(lookup);
	}

	public void removeLookup(String lookup) {
		map.remove(lookup);
	}

	public List<String> listAllKeys() {
		return new ArrayList<String>(map.keySet());

	}

	public Map<String, T> getMap() {
		return map;
	}

	public void setMap(Map<String, T> map) {
		this.map = map;
	}
	
	public void updateMap(Map<String, T> newLookups,
			List<String> removedLookups) {

		for (String toRemove : removedLookups) {
			removeLookup(toRemove);
		}
		Set<String> keys = newLookups.keySet();
		for (String key : keys) {
			addLookup(key, newLookups.get(key));
		}

	}

	abstract void initialize();

}
