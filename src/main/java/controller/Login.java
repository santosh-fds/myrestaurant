
package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MyDao;
import dto.Customer;

@WebServlet("/login")
public class Login extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("pass");
		// verify if email exists
		MyDao dao = new MyDao();
		if (email.equals("admin@jsp.com") && password.equals("admin")) {
			resp.getWriter().print("<h1 style='color:green'>Login Success</h1>");
			// this is logic to send next page
			req.getRequestDispatcher("AdminHome.html").include(req, resp);
		} else {
			Customer customer = dao.fetchByEmail(email);
			if (customer == null) {
				resp.getWriter().print("<h1 style='color:red'>Invalid email</h1>");
				req.getRequestDispatcher("Login.html").include(req, resp);
			} else {
				if (password.equals(customer.getPassword())) {
					resp.getWriter().print("<h1 style='color:green'>Login Success</h1>");
					req.getRequestDispatcher("CustomerHome.html").include(req, resp);

				} else {
					resp.getWriter().print("<h1 style='color:red'>Invalid Password</h1>");
					req.getRequestDispatcher("Login.html").include(req, resp);
				}
			}

		}
	}
}
