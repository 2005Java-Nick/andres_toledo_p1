package com.revature.andres.servlets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.andres.security.Authentication;



public class Login extends HttpServlet{

	private static final long serialVersionUID = -3024409063600661289L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("post recieved");
		req.setCharacterEncoding("UTF-8");
	    StringBuilder sb = new StringBuilder();
	    BufferedReader br = req.getReader();
	    String str;
	    while( (str = br.readLine()) != null ){
	        sb.append(str);
	    }    
	    String stream=sb.toString();
	    JSONParser jsonParser = new JSONParser();
	    //Starts credential validation;
        Object obj1;
        boolean validCredentials=false;
        User obj=null;
        Authentication authenticator=new Authentication();
        //Tries to authenticate user
        try {
        	obj1 = jsonParser.parse(stream);
        	String employeeList = (String) obj1;
			ObjectMapper mapper = new ObjectMapper();
			obj = mapper.readValue(employeeList, User.class);
			System.out.println(obj.getUsr());
			System.out.println(obj.getPwd());
			validCredentials=authenticator.loginUser(obj.getUsr(), obj.getPwd());
		} catch (ParseException e) {
			e.printStackTrace();
		}
        //
        PrintWriter writer = resp.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		//creates a new session for the user 
		if(validCredentials) {
			String sessionId=authenticator.createSession(obj.getUsr());
			String jsonReturn = mapper.writeValueAsString(sessionId);
			writer.write(jsonReturn);
			System.out.print("here1");
		}
		else {
			String jsonReturn = mapper.writeValueAsString(validCredentials);
			writer.write(jsonReturn);
			System.out.print("here2");
		}
		
           
	}

}
