package hr.fer.dm.mongodb.servlets;

import hr.fer.dm.foursqare.PlacesApi;
import hr.fer.dm.lastfm.ConcertsApi;
import hr.fer.dm.lastfm.SimilarSingerApi;
import hr.fer.dm.lastfm.SingerApi;
import hr.fer.dm.mongodb.dao.MongoDAO;
import hr.fer.dm.mongodb.model.Concert;
import hr.fer.dm.mongodb.model.Place;
import hr.fer.dm.mongodb.model.SimilarSinger;
import hr.fer.dm.mongodb.model.Singer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoClient;

@WebServlet("/save")
public class Save extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		saveData(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		saveData(req, resp);
	}


    public void saveData(HttpServletRequest req, HttpServletResponse resp) throws IOException{
    	if (!req.getSession().getAttribute("auth").toString().isEmpty()) {

    		ServletContext context = req.getServletContext( );
    		
			BufferedReader br = new BufferedReader(new InputStreamReader(
					req.getInputStream()));
			String singersJSON = "";
			if (br != null) {
				singersJSON = br.readLine();
			}

			List<String> list = SingerApi.convertToSimpleArray(singersJSON);

			List<Singer> singers = new ArrayList<Singer>();
			List<Concert> concerts = new ArrayList<Concert>();
			List<Place> places = new ArrayList<Place>();
			List<SimilarSinger> similarSingerList=new ArrayList<SimilarSinger>();

			for (String s : list) {
				Singer singer=SingerApi.loadSinger(s);
				singers.add(singer);
				if(!singer.bio.equals("NULL")){					
					List<SimilarSinger> newSingers=SimilarSingerApi.loadSimilarSingers(s, 3);
					
					//micanje lajkanih pijevača među sličnim
					newSingers=reduceSimilarSingers(newSingers,list);
					//postavljanje za omiljenog pijevača slične pijevače
					similarSingerList.addAll(newSingers);
					
					//dodavanje sličnih prijevača u ostale
					singers.addAll(loadSimilarSingers(similarSingerList,singers));
					
				}				
				
			}
			
			//stavim u mapu slične pijevače bez lajkanih
			//
			
			
			
			for(Singer s:singers){
				
				if(!s.bio.equals("NULL")){
					List<Concert> c=ConcertsApi.loadConcerts(s.name, s.name);
					if(c.size()!=0){
						concerts.addAll(c);
					}
				}
				
			}
			//context.log(String.format("broj koncerta: %d", concerts.size()));
			
			
			

			//context.log(String.format("%d", places.size()));
			
			MongoClient mongo = (MongoClient) req.getServletContext()
					.getAttribute("MONGO_CLIENT");
			MongoDAO dao = new MongoDAO(mongo);

						
			dao.saveSingers(SingerApi.convertToJSON(singers));
			
			dao.saveSimilarSingers(SimilarSingerApi.convertToJSON(similarSingerList));
			
			dao.saveConcerts(ConcertsApi.convertToJSON(concerts));
			
			//dao.savePlaces(PlacesApi.convertToJSON(places));

		}
    }
        
    /**
     * Reduciraju sve slične pijevače koji su već lajkani
     * @param list
     * @param singers
     * @return
     */
    private List<SimilarSinger> reduceSimilarSingers(List<SimilarSinger> list,List<String> singers){
    	List<SimilarSinger> newSingers=new ArrayList<SimilarSinger>();
    	//listaj sve slične pijevače
    	for(SimilarSinger s:list){
    		
    		if(!singerExist(s.foreignSinger, singers)){
    			newSingers.add(s);
    		}
    		
    	}
    	
    	return newSingers;
    }

    
    private boolean singerExist(String name,List<String> singers){
    	
    	for(String s:singers){
    		if(s.equals(name))
    			return true;
    	}
    	
    	return false;
    }
    
    private boolean singerLoadExist(String name,List<Singer> singers){
    	
    	for(Singer s:singers){
    		
    		if(s.name.equals(name))
    			return true;
    	}
    	
    	return false;
    }
    
    /**
     * Skidaju sve slične pijevače koji nisu već skinuti
     * @param newSingers
     * @param singers
     * @return
     */
    private List<Singer> loadSimilarSingers(List<SimilarSinger> newSingers,List<Singer> singers){
    	List<Singer> newLoadSingers=new ArrayList<Singer>();
    	
    	//listaj sve slične pijevače
    	for(SimilarSinger s:newSingers){
    		//ako nije skinut takav pijevač
    		if(!singerLoadExist(s.foreignSinger,singers)){
    			Singer singer=SingerApi.loadSinger(s.foreignSinger);
    			newLoadSingers.add(singer);
    		}
    		
    	}
    	
    	
    	return newLoadSingers;
    }


}
