package hr.fer.dm.mongodb.servlets;

import hr.fer.dm.lastfm.SingerApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		saveAuth(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		saveAuth(req, resp);
	}

	
	private void saveAuth(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		

			BufferedReader br = new BufferedReader(new InputStreamReader(
					req.getInputStream()));
			String auth = "";
			if (br != null) {
				auth = br.readLine();
			}

			List<String> list = SingerApi.convertToSimpleArray(auth);

			// promjeni atribute u sesiji tako postoje nazivi korisnika
			if (!auth.isEmpty()) {
				req.getSession().setAttribute("auth", list.get(0));
				req.getSession().setAttribute("name",
						list.get(1) + " " + list.get(2));
				req.getSession().setAttribute("email", list.get(3));
			}

		
	}
	
	
	
	
}