package org.jboss.spring.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jboss.spring.util.EnrichmentLookupMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest/enrichments")
public class EnrichmentLookupRestController {

	@Autowired
	private EnrichmentLookupMap lookupMap;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<String> listAllMembers() {
		return lookupMap.listAllKeys();
	}

	@RequestMapping(value = "/{key}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	String lookupEnrichmentByKey(@PathVariable("key") String key) {
		return lookupMap.lookup(key);
	}

	@RequestMapping(value = "/{key}/value/{value}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<String> setNewKey(@PathVariable("key") String key,
			@PathVariable("value") String value) {

		lookupMap.addLookup(key, value);
		return lookupMap.listAllKeys();
	}

	@RequestMapping(value = "/initialize", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<String> initializeEnrichments() {
		System.out.println("Initializing keys");
		lookupMap.initialize();
		return lookupMap.listAllKeys();
	}
	
	@RequestMapping(value = "/runLookups", method = RequestMethod.GET, produces ="application/json")
	public @ResponseBody String
		runlookups(){
		ArrayList<Integer> al = new ArrayList<Integer>(); 
        Random rand = new Random();

        for (int j = 0; j<10000; j++)
        {
        	int pick = rand.nextInt(2000);
        	al.add(pick);
        //	System.out.println("Contents of al: " + al);
        }
		Long start = System.currentTimeMillis();
		for(Integer integer : al ){
			lookupMap.lookup(integer.toString());
		}
		System.out.println("Length: " + al.size() );
		Long diff = System.currentTimeMillis() - start;
		return diff.toString();
	}

	public EnrichmentLookupMap getLookupMap() {
		return lookupMap;
	}

	public void setLookupMap(EnrichmentLookupMap lookupMap) {
		this.lookupMap = lookupMap;
	}
	
	
	
	

	/*@RequestMapping(value = "/initializeDB", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	void initializeEnrichmentDB() {
	
			Connection connection = null;
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = DriverManager
						.getConnection("jdbc:mysql://localhost:3306/enrichment?" +
				                       "user=jboss&password=pass");
			
			System.out.println(connection.toString());
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if( connection != null ){
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		System.out.println("Made DB Connection");
	}*/
}
