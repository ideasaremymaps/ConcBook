package hr.fer.dm.foursqare;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;
import hr.fer.dm.mongodb.model.Concert;
import hr.fer.dm.mongodb.model.Place;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

//DEPRICATED
public class PlacesApi {

	public static List<Place> loadPlaces(String location,String concert){
		FoursquareApi foursquareApi = new FoursquareApi("HZCRZ5KKEQYK2PP2KGH0A1DLWAQDN3MH1LXISJBPICVH4LA5", "YPGMILZKZNVCTT0R5BOQPYHBM11A5O5O2J25BTLMV0IMVS2B", "http://www.ConcertBook.com/redirect");
		Result<VenuesSearchResult> result=null;
		 try {
			result = foursquareApi.venuesSearch(location, null, null, null, null, null, null, null, null, null, null);
		} catch (FoursquareApiException e) {}
		
		 List<Place> places=new ArrayList<Place>();
		 
		 if (result.getMeta().getCode() == 200) {
		     
		      for (CompactVenue venue : result.getResult().getVenues()) {
		    	  Place p=new Place();
		    	  p.setId(venue.getId());
		    	  p.setAdress(venue.getLocation().getAddress());
		    	  p.setLatitude(venue.getLocation().getLat());
		    	  p.setLongitude(venue.getLocation().getLng());
		    	  p.setName(venue.getName());		    	  
		    	  if(venue.getCategories().length!=0){
		    		  p.setType(venue.getCategories()[0].getName());
		    	  }
		    	  else{
		    		  p.setType("nepoznata");
		    	  }
		    	  p.setConcertId(concert);
		    	  
		    	  places.add(p);
		    	  
		      }
		    }
		 
		return places;
	}
	
	
    public static String convertToJSON(final List<Place> places) {
    	 
         final OutputStream out = new ByteArrayOutputStream();
         final ObjectMapper mapper = new ObjectMapper();

         try {
			mapper.writeValue(out, places);
		} catch (JsonGenerationException e) {} 
         catch (JsonMappingException e) {} 
         catch (IOException e) {}

         final byte[] data = ((ByteArrayOutputStream) out).toByteArray();
         
		 return new String(data);
	}
	
       
    
	public static List<Place> convertToObject(String json) {
		ObjectMapper mapper = new ObjectMapper();
		List<Place> list=null;
		try {
			list = mapper.readValue(json,
					TypeFactory.defaultInstance().constructCollectionType(List.class,  
							Place.class));
		} catch (JsonParseException e) {} 
		catch (JsonMappingException e) {} 
		catch (IOException e) {}		
		
		return list;
	}
	
	
	//dohvat lokacija po koordinatama
}
