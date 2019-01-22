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

/**
 * 
 * @author Ivan Padovan
 *
 */
@WebServlet("/similarsinger")
public class SimilarSingerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String auth = req.getParameter("auth").toString();
		String singer = req.getParameter("name").toString();

		if (auth.equals(req.getSession().getAttribute("auth"))) {

			MongoClient mongo = (MongoClient) req.getServletContext()
					.getAttribute("MONGO_CLIENT");
			MongoDAO dao = new MongoDAO(mongo);

			String json = dao.getSingers();
			List<Singer> allSingers = SingerApi.convertToObject(json);

			String similarjson = dao.getSimilarSingers(singer);
			List<SimilarSinger> similarSingers = SimilarSingerApi
					.convertToObject(similarjson);

			List<Singer> singers = new ArrayList<Singer>();

			for (SimilarSinger s : similarSingers) {

				for (Singer sing : allSingers) {

					if (s.foreignSinger.equals(sing.name)) {
						singers.add(sing);
					}

				}

			}

			req.setAttribute("similar", 1);
			req.setAttribute("jsonSingers", similarjson);
			req.setAttribute("singers", singers);

			// req.getRequestDispatcher("/WEB-INF/pages/singer.jsp").forward(req,
			// resp);
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/WEB-INF/pages/singer.jsp");
			rd.forward(req, resp);
		} else {
			resp.sendRedirect(req.getContextPath() + "/start");
		}

	}

}
