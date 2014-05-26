package org.jboss.spring.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jboss.spring.domain.Account;
import org.jboss.spring.util.AccountLookupMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest/accounts")
public class AccountLookupRestController {

	private AccountLookupMap lookupMap;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<String> listAllMembers() {
		return lookupMap.listAllKeys();
	}

	@RequestMapping(value = "/{key}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Account lookupAccountByKey(@PathVariable("key") String key) {
		return lookupMap.lookup(key);
	}

	@RequestMapping(value = "/{key}/value/{id}/{account}/{name}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<String> setNewKey(@PathVariable("key") String key,
			@PathVariable("id") String id,
			@PathVariable("account") String account,
			@PathVariable("name") String name) {

		Account a = new Account(id, account, name);

		lookupMap.addLookup(key, a);
		return lookupMap.listAllKeys();
	}

	@RequestMapping(value = "/initialize", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<String> initializeEnrichments() {
		System.out.println("Initializing keys");
		if( lookupMap == null ){
			System.out.println("MAP IS NULL?!?!?");
			lookupMap = new AccountLookupMap();
		}
		lookupMap.initialize();
		return lookupMap.listAllKeys();
	}

	@RequestMapping(value = "/runLookups", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	String runlookups() {
		ArrayList<Integer> al = new ArrayList<Integer>();
		Random rand = new Random();

		for (int j = 0; j < 10000; j++) {
			int pick = rand.nextInt(2000);
			al.add(pick);
			// System.out.println("Contents of al: " + al);
		}
		Long start = System.currentTimeMillis();
		for (Integer integer : al) {
			lookupMap.lookup(integer.toString());
		}
		System.out.println("Length: " + al.size());
		Long diff = System.currentTimeMillis() - start;
		return diff.toString();

	}

	public AccountLookupMap getLookupMap() {
		return lookupMap;
	}

	public void setLookupMap(AccountLookupMap lookupMap) {
		this.lookupMap = lookupMap;
	}

}
