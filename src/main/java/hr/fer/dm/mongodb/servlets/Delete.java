package hr.fer.dm.mongodb.servlets;

import hr.fer.dm.mongodb.dao.MongoDAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoClient;

@WebServlet("/delete")
public class Delete extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			
		req.getRequestDispatcher("/WEB-INF/pages/start.jsp").forward(req, resp);
			
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		MongoClient mongo=(MongoClient) req.getServletContext().getAttribute("MONGO_CLIENT");
		MongoDAO dao=new MongoDAO(mongo);
		
		dao.deleteAllCollections();
		req.getSession().removeAttribute("auth");
        req.getSession().removeAttribute("name");
        req.getSession().removeAttribute("email");
		
	}
}
