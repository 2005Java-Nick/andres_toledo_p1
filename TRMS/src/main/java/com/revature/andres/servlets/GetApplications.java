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

public class GetApplications extends HttpServlet{
	
	private static final long serialVersionUID = -6680526377602412454L;
	public static Authentication authenticator = Authentication.getAuthentication();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String username=req.getParameter("usr");
		String session = req.getParameter("pwd").replaceAll(" ", "+");
		String isAdmin = req.getParameter("adm");
		System.out.println("----------Get Applications---------------\nUsername: "+username+"\nSession: "+session);
		ArrayList<String> userdata=authenticator.validateSession(username, session);
		
		if(!userdata.get(0).equals("false"))
		{
			System.out.println(isAdmin);
			ArrayList<ArrayList<String>> lines=authenticator.getApplications(username, session,isAdmin);
			PrintWriter writer = resp.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			//creates a new session for the user 
			req.setCharacterEncoding("UTF-8");
			String jsonReturn = mapper.writeValueAsString(lines);
			System.out.print("getData1");
			writer.write(jsonReturn);
			
		}
		else
		{
			super.doPost(req, resp);
		}
	}

	
}
