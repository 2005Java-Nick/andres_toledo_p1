package com.revature.andres.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.andres.security.Authentication;
import com.revature.andres.util.Mail;

public class CreateUserAdmin extends HttpServlet{

	private static final long serialVersionUID = -361954416134106043L;
	public static Authentication authenticator = Authentication.getAuthentication();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String username=req.getParameter("usr");
		String session = req.getParameter("pwd").replaceAll(" ", "+");
		String email = req.getParameter("em");
		String role = req.getParameter("ro");
		String directManager = req.getParameter("dm");
		ArrayList<String> userdata=authenticator.validateSession(username, session);
		System.out.println("----------Create User Admin---------------\nUser: "+username+"\nPassword:"+session+"\n Email: "+email+"\n Role: "+role+"\n Direct Manager:"+directManager+"\n");
		if(!userdata.get(0).equals("false"))
		{
			String verification=authenticator.getEncryption().randomPassword();
			authenticator.createUserAdmin(email, authenticator.getEncryption().randomPassword(), Integer.parseInt(directManager), Integer.parseInt(role), verification);
			Mail sendMail =new Mail();
			String msg = "Welcome to the Tuition Reimbursement System attached to this email you will find your employee id and password"
					+ "<br><br><br> Use these credentials to log into http://andrestoledotech.com be sure to also have your employee id given to you by the company"
					+ "<br><br><br>  Activation Code: "+verification;
			try {
				sendMail.send(msg,"amtamusic@hotmail.com");
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			PrintWriter writer = resp.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			req.setCharacterEncoding("UTF-8");
			String jsonReturn = mapper.writeValueAsString("");
			System.out.print("getData1");
			writer.write(jsonReturn);
			
		}
		else
		{
			super.doPost(req, resp);
		}
	}


}
