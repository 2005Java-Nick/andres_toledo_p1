package com.revature.andres.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import com.revature.andres.security.Authentication;

@MultipartConfig(maxFileSize = 16177215) // upload file's size up to 16MB
public class SubmitApplication extends HttpServlet{

	private static final long serialVersionUID = -1213748792005719465L;
	public static Authentication authenticator = Authentication.getAuthentication();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
			String startdate=req.getParameter("start-date");
			String enddate=req.getParameter("end-date");
			String cost=req.getParameter("cost");
			String typeCourse=req.getParameter("course-type");
			String description=req.getParameter("description");
			String location=req.getParameter("course-location");
			String gradingFormat=req.getParameter("grading-format");
			String passingGrade=req.getParameter("passing-grade");
			String session=req.getParameter("session");
			String username=req.getParameter("username");
			String filename=req.getParameter("filename");
			long noOfDaysBetween=-1;
			System.out.println("sd:"+startdate);
			System.out.println("ed:"+enddate);
			System.out.println("cost:"+cost);
			System.out.println("ctype:"+typeCourse);
			System.out.println("des:"+description);
			System.out.println("lo:"+location);
			System.out.println("gf:"+gradingFormat);
			System.out.println("pg:"+passingGrade);
			System.out.println("ses:"+session);
			System.out.println("un:"+username);
			System.out.println("fn:"+filename);
			String message="Submition could not be processed";
			ArrayList<String> userdata=authenticator.validateSession(username, session);
			if(!userdata.get(0).equals("false"))
			{
			//Validates dates and counts the number of dates the employee will be absent
			//calculate days missed
			String stemp=startdate.replace("/", "-");
			String etemp=enddate.replace("/", "-");
			LocalDate edate=LocalDate.parse(etemp);
			LocalDate sdate=LocalDate.parse(stemp);
			try {
				noOfDaysBetween = ChronoUnit.DAYS.between(sdate,edate);
				noOfDaysBetween+=1;
				System.out.println("days : "+noOfDaysBetween);
				if (noOfDaysBetween<0)
				{
					message = "Date range was not valid please try again";
			        req.setAttribute("Message", message);
					req.getRequestDispatcher("/CreateUser/message.jsp").forward(req, resp);
				}
			} catch (Exception e) {
				e.printStackTrace();
				req.getRequestDispatcher("/CreateUser/message.jsp").forward(req, resp);
			}
			
			//Validate coursetype return id
			int coursetypeid= Integer.parseInt(typeCourse);
			if (coursetypeid>6 || coursetypeid<1)
			{
				 req.getRequestDispatcher("/CreateUser/message.jsp").forward(req, resp);
			}
			//validate filetype return id
			String [] filetypeExt=filename.split("\\.");
			System.out.println("Extension : "+ filetypeExt[filetypeExt.length-1]);
			int filetype = authenticator.getFileType(filetypeExt[filetypeExt.length-1]);
			System.out.println("Filetype ID : "+filetype);
			if (filetype<1)
			{
				 req.getRequestDispatcher("/CreateUser/message.jsp").forward(req, resp);
			}
			//Upload course return id
			int courseId =authenticator.getCourseId(description);
			if(courseId<1) {
				req.getRequestDispatcher("/CreateUser/message.jsp").forward(req, resp);
			}
			//upload upload return id
			InputStream inputStream = null; // input stream of the upload file
			int uploadId=-1;
		    Part filePart = req.getPart("file");
		    if (filePart != null) {
	            // prints out some information for debugging
	            // obtains input stream of the upload file
	            inputStream = filePart.getInputStream();
	            uploadId=authenticator.uploadFile(filename.replace("\\.[a-zA-Z]+$", ""), filetype, inputStream);
	        }else
	        {
	        	req.getRequestDispatcher("/CreateUser/message.jsp").forward(req, resp);
	        }
			
		    if(uploadId<1)
		    {
		    	req.getRequestDispatcher("/CreateUser/message.jsp").forward(req, resp);
		    }
		    System.out.println("Upload Id: "+uploadId);
		    
			//Validate reimbursement application 
			int userId=Integer.parseInt(userdata.get(2));
			int managerId=Integer.parseInt(userdata.get(3));
			double reimbursementPercent=Double.parseDouble(cost)*authenticator.getReimbursementPercentage(Integer.parseInt(typeCourse));
			int applicationId=authenticator.submitApplication(sdate, edate, location, (int)noOfDaysBetween, courseId, Double.parseDouble(cost), passingGrade, gradingFormat, coursetypeid, reimbursementPercent, userId);
			//link upload to application
			authenticator.linkUpload(uploadId, applicationId);
			//link reviewer to application
			authenticator.linkReviewer(managerId, applicationId);
			}
			else
			{
				req.getRequestDispatcher("/CreateUser/message.jsp").forward(req, resp);
			}
		
			message = "Your application was submited correctly";
			// sets the message in request scope
	        req.setAttribute("Message", message);
	        // forwards to the message page
	        req.getRequestDispatcher("/CreateUser/message.jsp").forward(req, resp);
	        
	        //getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
		
	}

}
