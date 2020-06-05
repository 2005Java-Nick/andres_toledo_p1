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

public class ApproveApplication extends HttpServlet{

	private static final long serialVersionUID = 1390220765313058546L;
	public static Authentication authenticator = Authentication.getAuthentication();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String username=req.getParameter("usr");
		String session = req.getParameter("pwd").replaceAll(" ", "+");
		String aId = req.getParameter("aId");
		String approveordeny = req.getParameter("ad");
		System.out.println("----------ApproveApplication---------------\nUsername: "+username+"\nSession: "+session+"\nApp Id: "+aId);
		ArrayList<String> userdata=authenticator.validateSession(username, session);
		if(!userdata.get(0).equals("false"))
		{
			if(approveordeny.equals("a")) {
				authenticator.approveApplication(username, session,Integer.parseInt(aId),"");
			}else
			{
				authenticator.denyApplication(Integer.parseInt(aId));
			}
			PrintWriter writer = resp.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			//creates a new session for the user 
			req.setCharacterEncoding("UTF-8");
			String jsonReturn = mapper.writeValueAsString("works");
			System.out.print("getData1");
			writer.write(jsonReturn);
			
		}
		else
		{
			super.doPost(req, resp);
		}
	
	}



}
