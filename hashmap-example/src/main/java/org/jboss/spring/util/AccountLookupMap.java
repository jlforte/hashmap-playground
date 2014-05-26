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
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.spring.domain.Account;

public class AccountLookupMap {

	private Map<String, Account> map = new ConcurrentHashMap<String, Account>();

	public void addLookup(String lookup, Account value) {
		map.put(lookup, value);
	}

	public Account lookup(String lookup) {
		return map.get(lookup);
	}

	public void removeLookup(String lookup) {
		map.remove(lookup);
	}

	public List<String> listAllKeys() {
		return new ArrayList<String>(map.keySet());

	}

	public Map<String, Account> getMap() {
		return map;
	}

	public void setMap(Map<String, Account> map) {
		this.map = map;
	}

	public void updateMap(Map<String, Account> newLookups,
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
		String splitBy = "";

		try {
			File file = new File(
					"/home/jeff/work/spring-camel/hashmap-example/src/main/resources/test2.csv");
			System.out.println(file.getAbsolutePath());

			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] lookup = line.split(splitBy);
				for (int i = 0; i < lookup.length; i++) {
					System.out.println(lookup[i]);
				}
				Account a = new Account(lookup[0], lookup[1], lookup[2]);

				addLookup(a.getId(), a);

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
