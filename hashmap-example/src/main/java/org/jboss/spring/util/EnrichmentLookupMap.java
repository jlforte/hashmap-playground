package org.jboss.spring.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EnrichmentLookupMap {

	public EnrichmentLookupMap() {

	}
	
	private Map<String, String> map;

	public void addLookup(String lookup, String value) {
		map.put(lookup, value);
	}

	public String lookup(String lookup) {
		return map.get(lookup);
	}

	public void removeLookup(String lookup) {
		map.remove(lookup);
	}

	public List<String> listAllKeys() {
		return new ArrayList<String>(map.keySet());

	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	
	public void updateMap(Map<String, String> newLookups,
			List<String> removedLookups) {

		for (String toRemove : removedLookups) {
			removeLookup(toRemove);
		}
		Set<String> keys = newLookups.keySet();
		for (String key : keys) {
			addLookup(key, newLookups.get(key));
		}

	}

	public void initialize() {
		// String csvFile = "test.csv";
		BufferedReader br = null;
		String line = "";
		String splitBy = ",";

		try {
			File file = new File(
					"/home/jeff/work/spring-camel/hashmap-example/src/main/resources/test.csv");
			System.out.println(file.getAbsolutePath());

			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] lookup = line.split(splitBy);

				System.out.println("Lookup [code= " + lookup[0] + " , name="
						+ lookup[1] + "]");
				addLookup(lookup[0], lookup[1]);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Done initializing");
	}
	
	
	

}
