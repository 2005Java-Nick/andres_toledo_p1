package com.revature.andres.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.andres.security.Authentication;

public class GetUsers extends HttpServlet{

	private static final long serialVersionUID = -9026615470708969830L;
	public static Authentication authenticator = Authentication.getAuthentication();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String username=req.getParameter("usr");
		String session = req.getParameter("pwd").replaceAll(" ", "+");
		System.out.println("----------Get Users---------------\nUsername: "+username+"\nSession: "+session);
		ArrayList<String> userdata=authenticator.validateSession(username, session);
		
		if(!userdata.get(0).equals("false"))
		{
			ArrayList<ArrayList<String>> lines=authenticator.getUsers();
			PrintWriter writer = resp.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			//creates a new session for the user 
			req.setCharacterEncoding("UTF-8");
			String jsonReturn = mapper.writeValueAsString(lines);
			System.out.print("Got User Data");
			writer.write(jsonReturn);
		}
		else
		{
			super.doPost(req, resp);
		}	
	}
	
	
	
}
