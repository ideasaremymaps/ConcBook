package hr.fer.dm.mongodb.servlets;

import hr.fer.dm.foursqare.PlacesApi;
import hr.fer.dm.lastfm.ConcertsApi;
import hr.fer.dm.mongodb.dao.MongoDAO;
import hr.fer.dm.mongodb.model.Concert;
import hr.fer.dm.mongodb.model.Place;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoClient;

@WebServlet("/place")
public class PlaceServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String auth=req.getParameter("auth").toString();
		String concert=req.getParameter("name").toString();
		String latitude=req.getParameter("lat").toString();
		String longitude=req.getParameter("lng").toString();
		String adress=req.getParameter("adress").toString();
		String date=req.getParameter("date").toString();
		
        if(auth.equals(req.getSession().getAttribute("auth"))){
			
			MongoClient mongo = (MongoClient) req.getServletContext()
					.getAttribute("MONGO_CLIENT");
			MongoDAO dao = new MongoDAO(mongo);
			
			
			req.setAttribute("name", concert);
			req.setAttribute("lat", latitude);
			req.setAttribute("lng", longitude);
			req.setAttribute("adress", adress);
			req.setAttribute("date", date);
			
			
			req.getRequestDispatcher("/WEB-INF/pages/place.jsp").forward(req, resp);
		}
		else{
			resp.sendRedirect(req.getContextPath() + "/start");			
		}
		
	
	}
	
}
