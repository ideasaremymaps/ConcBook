package hr.fer.dm.mongodb.servlets;

import hr.fer.dm.lastfm.ConcertsApi;
import hr.fer.dm.lastfm.SingerApi;
import hr.fer.dm.mongodb.dao.MongoDAO;
import hr.fer.dm.mongodb.model.Concert;
import hr.fer.dm.mongodb.model.Singer;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoClient;


@WebServlet("/concert")
public class ConcertServlet extends HttpServlet{

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		String auth=req.getParameter("auth").toString();
		String singer=req.getParameter("name").toString();
		
      if(auth.equals(req.getSession().getAttribute("auth"))){
			
			MongoClient mongo = (MongoClient) req.getServletContext()
					.getAttribute("MONGO_CLIENT");
			MongoDAO dao = new MongoDAO(mongo);
			
			String json=dao.getSingerConcerts(singer);
			List<Concert> concerts=ConcertsApi.convertToObject(json);
			
			req.setAttribute("singer",singer );
			req.setAttribute("concertJson", json);
			req.setAttribute("concerts", concerts);

           if(concerts==null) req.setAttribute("exists", "no"); else req.setAttribute("exists","yes");
			
			req.getRequestDispatcher("/WEB-INF/pages/concert.jsp").forward(req, resp);
		}
		else{
			resp.sendRedirect(req.getContextPath() + "/start");
		}
		

	}

}
