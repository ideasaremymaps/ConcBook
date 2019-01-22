package hr.fer.dm.mongodb.servlets;

import hr.fer.dm.lastfm.ConcertsApi;
import hr.fer.dm.lastfm.SimilarSingerApi;
import hr.fer.dm.mongodb.dao.MongoDAO;
import hr.fer.dm.mongodb.model.Concert;
import hr.fer.dm.mongodb.model.SimilarSinger;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoClient;

@WebServlet("/calendar")
public class CalendarServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String auth=null;
		
		try{
			auth=req.getParameter("auth").toString();
		}catch(Exception ex){
			resp.sendRedirect(req.getContextPath() + "/start");
		}
		
		if(auth.equals(req.getSession().getAttribute("auth"))){
			
			MongoClient mongo = (MongoClient) req.getServletContext()
					.getAttribute("MONGO_CLIENT");
			MongoDAO dao = new MongoDAO(mongo);
			String json=dao.getConcerts();
			
			List<Concert> concerts=ConcertsApi.convertToObject(json);
			String similarJson=dao.getAllSimilarSingers();
			
			List<SimilarSinger> similarSingers=SimilarSingerApi.convertToObject(similarJson);
			
			req.setAttribute("smjer", 1);
			req.setAttribute("similarSingers", similarSingers);
			req.setAttribute("concerts", concerts);
			
			req.getRequestDispatcher("/WEB-INF/pages/calendar.jsp").forward(req, resp);
		}
		else{	
			resp.sendRedirect(req.getContextPath() + "/start");
		}
				
	}
}
