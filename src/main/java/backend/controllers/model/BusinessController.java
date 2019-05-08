package backend.controllers.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;

import backend.models.Business;
import backend.repository.BusinessRepo;

@Controller
@RequestMapping(value="/model/business")
public class BusinessController {

	@RequestMapping(value="/getAllBusinesses", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Business> getAllBusinesses() {
		List<Business> businesses = BusinessRepo.getAll();
		return businesses;
	}
	
	@RequestMapping(value="/getBusiness/{businessId}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Business getBusiness(@PathVariable int businessId) {
		Business business = BusinessRepo.get(businessId);
		return business;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public Boolean addBusiness(@RequestBody Business business) {
		
		setLocation(business);
		
		
		return BusinessRepo.insert(business);
	}
	
	@RequestMapping(value="/remove/{businessId}", method=RequestMethod.POST)
	@ResponseBody
	public Boolean removeBusiness(@PathVariable Integer businessId) {
		Business business = BusinessRepo.get(businessId);
		return BusinessRepo.delete(business);
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	@ResponseBody
	public Boolean updateBusiness(@RequestBody Business business) {
		return BusinessRepo.update(business);
	}
	
	private void setLocation(Business business) {

		String address=business.getAddress().replaceAll(" ", "+");
		String city=business.getCity().replaceAll(" ", "+");
		String state=business.getState();
//		String address="207 Chippewa".replaceAll(" ", "+");
//		String city="Eau Claire".replaceAll(" ", "+");
//		String state="WI";
		String authToken="eLVzp37Rr1LpMUEbcCcH";
		String authId="9d2cf790-3931-96df-7e04-af540e94162b";
		
		String urlString = "https://us-street.api.smartystreets.com/street-address?auth-id="+authId+"&auth-token="+authToken+"&street="+address+"&city="+city+"&state="+state+"&candidates=1";
		
		try {
			URL url = new URL(urlString);
			HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			BufferedReader br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));
			String output = br.readLine().replace("[", "").replace("]", "");
			JsonNode metadata = new ObjectMapper().readTree(output).get("metadata");
			business.setLongitude(metadata.get("longitude").asDouble());
			business.setLatitude(metadata.get("latitude").asDouble());
		} catch ( IOException e) {
			e.printStackTrace();
		}
		
	}
}
