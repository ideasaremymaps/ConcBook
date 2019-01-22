package hr.fer.dm.lastfm;

import hr.fer.dm.mongodb.model.Concert;
import hr.fer.dm.mongodb.model.SimilarSinger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import de.umass.lastfm.Artist;
import de.umass.lastfm.Event;
import de.umass.lastfm.PaginatedResult;

public class SimilarSingerApi {

	
	public static List<SimilarSinger> loadSimilarSingers(String name,int maxNumber){
		Collection<Artist> similar=Artist.getSimilar(name,maxNumber, "8af5bde596cf8d1af17ad71e008d4404");
		
		List<SimilarSinger> similarSinger=new ArrayList<SimilarSinger>();
		int i=0;
		
		for(Artist a:similar){
			SimilarSinger s=new SimilarSinger();
			s.setForeignSinger(a.getName());
			s.setPrimarySinger(name);
			
		
			similarSinger.add(s);
			
			

			//spremiš u bazu strane ključeve
			//za svaki like gledaš slične glazbenike
			//metoda koja provjerava dali glazbenik postoji
			      //ako ne postoji skini njegove koncerte
			
			//kad listaš glazbenik,listaš po like 
			//ako tražiš slične tražiš u bazi sve njegove slične glazbenike i
			      //zoveš metodu koja po imenu izvlači koncerte
			                                  
			
			
		}
		
		return similarSinger;
	}
	
	
	
	public static String convertToJSON(final List<SimilarSinger> concerts)  {
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
	
	
	public static List<SimilarSinger> convertToObject(String json){
		ObjectMapper mapper = new ObjectMapper();
		List<SimilarSinger> list=null;
		try {
			list = mapper.readValue(json,
					TypeFactory.defaultInstance().constructCollectionType(List.class,  
							SimilarSinger.class));
		} catch (JsonParseException e) {} 
		catch (JsonMappingException e) {} 
		catch (IOException e) {}		
		
		return list;
	}
}
