package hr.fer.dm.lastfm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import de.umass.lastfm.Artist;
import de.umass.lastfm.Event;
import de.umass.lastfm.PaginatedResult;
import hr.fer.dm.mongodb.model.Concert;
import hr.fer.dm.mongodb.model.Singer;

public class SingerApi {

	
	public static Singer loadSinger(String singer){
		Artist a=null;
		Singer s=new Singer();
		s.setName(singer);
		try{
			a=Artist.getInfo(singer, "8af5bde596cf8d1af17ad71e008d4404");
			
			s.setBio(a.getWikiSummary());
		}
		catch(Exception ex){
			s.setBio("NULL");
		}
		
		return s;
	}
	
	
	public static String convertToJSON(final List<Singer> concerts) {
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

	
	public static List<Singer> convertToObject(String json){
		ObjectMapper mapper = new ObjectMapper();
		List<Singer> list=null;
		try {
			list = mapper.readValue(json,
					TypeFactory.defaultInstance().constructCollectionType(List.class,  
							Singer.class));
		} catch (JsonParseException e) {} 
		catch (JsonMappingException e) {} 
		catch (IOException e) {}		
		
		return list;
	}
	
	public static List<String> convertToSimpleArray(String json){
		ObjectMapper mapper = new ObjectMapper();
		List<String> list=null;
		try {
			list = mapper.readValue(json,
					TypeFactory.defaultInstance().constructCollectionType(List.class,  
							String.class));
		} catch (JsonParseException e) {} 
		catch (JsonMappingException e) {} 
		catch (IOException e) {}		
		
		return list;
	}
	
	
}
