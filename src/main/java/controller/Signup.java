package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.MyDao;
import dto.Customer;

@WebServlet("/signup")
@MultipartConfig
public class Signup extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		long mobile = Long.parseLong(req.getParameter("number"));
		String email = req.getParameter("email");
		String gender = req.getParameter("gen");
		String country = req.getParameter("country");
		String password = req.getParameter("pass");
		LocalDate dob = LocalDate.parse(req.getParameter("dob"));
		int age = Period.between(dob, LocalDate.now()).getYears();

		// logic to recieve image and convert to byte
		Part pic = req.getPart("picture");
		byte[] picture = null;
		picture = new byte[pic.getInputStream().available()];
		pic.getInputStream().read(picture);

		MyDao dao = new MyDao();

		// logic to verify email and phone number

		if (dao.fetchByEmail(email) == null && dao.fetchByMobile(mobile) == null) {
			// loading values inside object
			Customer s = new Customer();
			s.setAge(age);
			s.setCountry(country);
			s.setDob(dob);
			s.setEmail(email);
			s.setFullname(name);
			s.setGender(gender);
			s.setMobile(mobile);
			s.setPassword(password);
			s.setPicture(picture);

			// persisting

			dao.save(s);
			resp.getWriter().print("<h1 style='color:green'>Account created succesfully</h1>");
			req.getRequestDispatcher("Login.html").include(req, resp);
		} else {
			resp.getWriter().print("<h1 style='color:red'>Email and number Should be Unique</h1>");
			req.getRequestDispatcher("Signup.html").include(req, resp);
		}
	}

}
