package hr.fer.dm.lastfm;

import hr.fer.dm.foursqare.PlacesApi;
import hr.fer.dm.mongodb.model.Concert;
import hr.fer.dm.mongodb.model.Place;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.*;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import de.umass.lastfm.Artist;
import de.umass.lastfm.Event;
import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Track;



public class ConcertsApi {

	
	//dohvat koncerata po glazbenicima	
	//bitno je stvoriti kolekiju koja će reči da postoje i ne postoje koncerti
	
	public static List<Concert> loadConcerts(String name,String singer){
		PaginatedResult<Event> artists = Artist.getEvents(name, "8af5bde596cf8d1af17ad71e008d4404");
		
		List<Concert> concerts=new ArrayList<Concert>();

		for(Event e:artists.getPageResults()){
            Date date = e.getStartDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateNew="";
            dateNew = sdf.format(date).toString();
            Concert c =new Concert();
			c.setId(Integer.toString(e.getId()));
			c.setAdress(e.getVenue().getStreet());
			c.setCity(e.getVenue().getCity());
			c.setCountry(e.getVenue().getCountry());
			c.setName(e.getTitle());
			c.setLatitude(e.getVenue().getLatitude());
			c.setLongitude(e.getVenue().getLongitude());
			c.setDate(dateNew);
			c.setSingerId(singer);
			
			concerts.add(c);
//			try{
//			c.setPlaces(PlacesApi.loadPlaces(e.getVenue().getLatitude()+","+e.getVenue().getLongitude()));
//			}catch(Exception ex){}
			
		}
		
		return concerts;
	}
	
	
	
	public static String convertToJSON(final List<Concert> concerts)  {
		 final OutputStream out = new ByteArrayOutputStream();
         final ObjectMapper mapper = new ObjectMapper();

         try {
			mapper.writeValue(out, concerts);
		} catch (JsonGenerationException e) {} 
         catch (JsonMappingException e) {} 
         catch (IOException e) {}

         final byte[] data = ((ByteArrayOutputStream) out).toByteArray();
         
		 return new String(data);
	}
	
	
	public static List<Concert> convertToObject(String json){
		ObjectMapper mapper = new ObjectMapper();
		List<Concert> list=null;
		try {
			list = mapper.readValue(json,
					TypeFactory.defaultInstance().constructCollectionType(List.class,  
							Concert.class));
		} catch (JsonParseException e) {} 
		catch (JsonMappingException e) {} 
		catch (IOException e) {}		
		
		return list;
	}
	
	//UČITAJ LASTFM U KLASE
	//UČITAJ MJESTA U KLASE
	//PRETVORI U JSON
	//SPREMI U MONGO
	
	//PROVJERI LIMIT UPITE i probaj imati upit koji samo jedan koncert(lokacije) uzima 
	//i samo jednog pjevača(koncerti) uzima
}
