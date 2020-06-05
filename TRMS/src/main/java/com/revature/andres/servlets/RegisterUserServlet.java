package com.revature.andres.servlets;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.revature.andres.security.Authentication;
@MultipartConfig(maxFileSize = 16177215) // upload file's size up to 16MB
public class RegisterUserServlet extends HttpServlet{

	private static final long serialVersionUID = -1213748792005719465L;
	public static Authentication authenticator = Authentication.getAuthentication();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
			String firstName=req.getParameter("first-name");
			String lastName=req.getParameter("last-name");
			String  username=req.getParameter("username");
			String  email=req.getParameter("email");
			String password=req.getParameter("password");
			password=authenticator.getEncryption().encryptPassword(password);
			int employeeId = Integer.parseInt(req.getParameter("emp-id"));
			String code=req.getParameter("verification-code");
			String message = authenticator.registerUser(firstName, lastName, email, username, password, employeeId, code);
			// sets the message in request scope
	        req.setAttribute("Message", message);
	        // forwards to the message page
	        System.out.println(getServletContext());
	        req.getRequestDispatcher("/login.jsp").forward(req, resp);
	        //getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
	}

}
