package hr.fer.dm.mongodb.servlets;

import hr.fer.dm.lastfm.SimilarSingerApi;
import hr.fer.dm.lastfm.SingerApi;
import hr.fer.dm.mongodb.dao.MongoDAO;
import hr.fer.dm.mongodb.model.SimilarSinger;
import hr.fer.dm.mongodb.model.Singer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoClient;

@WebServlet("/singer")
public class SingerServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		
		String auth=req.getParameter("auth").toString();
		
		
		if(auth.equals(req.getSession().getAttribute("auth"))){
			
			MongoClient mongo = (MongoClient) req.getServletContext()
					.getAttribute("MONGO_CLIENT");
			MongoDAO dao = new MongoDAO(mongo);
			
			String json=dao.getSingers();	
			String similarJson=dao.getAllSimilarSingers();
			
 			List<Singer> singers=SingerApi.convertToObject(json);
 			List<SimilarSinger> similarSingers=SimilarSingerApi.convertToObject(similarJson);
			
 			Set<String> chosenSingers=new HashSet<String>();
 			
 			for(SimilarSinger s:similarSingers){
 				chosenSingers.add(s.primarySinger); 				 				
 			}
			
 			List<Singer> likedSingers=new ArrayList<Singer>();
 			
 			for(String s:chosenSingers){
 				likedSingers.add( findSinger(singers, s));
 				
 			}
			
 			req.setAttribute("similar", 0);	
			req.setAttribute("jsonSingers", SingerApi.convertToJSON(likedSingers));
			req.setAttribute("singers", likedSingers);
			
			
			//req.getRequestDispatcher("/WEB-INF/pages/singer.jsp").forward(req, resp);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/pages/singer.jsp");
	    	rd.forward(req, resp);
		}
		else{
			resp.sendRedirect(req.getContextPath() + "/start");
		}
		
	}
	
	
	public Singer findSinger(List<Singer> singers,String name){
		
		for(Singer s:singers){
			
			if(s.name.equals(name)){
				return s;
			}
		}
		
		
		return null;
	}
	
}
