package com.revature.andres.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.andres.security.Authentication;

public class Session extends HttpServlet{

	private static final long serialVersionUID = -2497022520956136872L;

	@SuppressWarnings("unchecked")
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
        

        Object obj1;
        ArrayList<String> validSession = null;
        User obj=null;
        Authentication authenticator=new Authentication();
        try {
        	obj1 = jsonParser.parse(stream);
        	String employeeList = (String) obj1;
			ObjectMapper mapper = new ObjectMapper();
			obj = mapper.readValue(employeeList, User.class);
			System.out.println("---------------------------------------");
			System.out.println(obj.getUsr());
			System.out.println(obj.getPwd());
			validSession=authenticator.validateSession(obj.getUsr(),obj.getPwd());
		} catch (ParseException e) {
				// TODO Auto-generated catch block
			//super.doPost(req, resp);
			e.printStackTrace();
		}
        
        PrintWriter writer = resp.getWriter();
		
        @SuppressWarnings("unused")
		ObjectMapper mapper = new ObjectMapper();
		try {
	        JSONObject myObject = new JSONObject();
	        System.out.println(validSession.get(0));
	        System.out.println(validSession.get(1));
	        myObject.put("valid",validSession.get(0));
	        myObject.put("isAdmin", validSession.get(1));
			writer.write(myObject.toString());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
	}

	
}
