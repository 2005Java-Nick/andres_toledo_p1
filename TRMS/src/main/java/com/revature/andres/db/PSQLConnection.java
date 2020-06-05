package com.revature.andres.db;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;


public class PSQLConnection {
	//project run settings set env variables
	private String url="jdbc:postgresql://andres-toledo-db.cbqhietgzmel.us-east-2.rds.amazonaws.com/andres_toledo_db";
	private String user="postgres";
	private String password="1992Andres_";
	//One Logger for entire Application
	//private static Logger log=Logger.getRootLogger();
	
	//Establish connection to database
	public Connection connect() {
	        Connection conn = null;
	        try {
	        	Class.forName("org.postgresql.Driver");
	            conn = DriverManager.getConnection(url, user, password);
	            //log.info("PSQLConnection:connect: Connected to the PostgreSQL server successfully.");
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            //log.error("PSQLConnection:connect: Could not connect to database");
	            return null;
	        } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return conn;
	 }
	
	 
	 //Verifies user credentials 
	 public String getPassword(String u)
	 {
		 try { 
			 Connection conn = connect();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("select * from login('"+u+"')");
			 rs.next();
			 String pwd = rs.getString("pwd");	
			 return pwd;
	        }catch(NullPointerException e1)
		 	{
	        	//log.error("PSQLConnection:verifyUserCredentials: No user found");
	        	return "";
		 	}catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	            //log.error("PSQLConnection:verifyUserCredentials: Could not validate sql statement "+u.getUsername());
	            return "";
	        } 
	 }
	 
	 public boolean createSession(String username,String sessionId)
	 {
		 try {
			 //log.info("PSQLConnection:deletePage:Atempting to delete page from database");
			 Connection conn = connect();
			 CallableStatement updPage = conn.prepareCall("call createSession('"+username+"','"+sessionId+"')");
			 updPage.execute();
			 updPage.close();
			 return true;
			 //log.info("PSQLConnection:saveNotebookData:Page "+pgID+" was deleted successully");
		 }catch (Exception e){
			 e.printStackTrace();
			 return false;
		 }
	 }
	  
	 
	 //Verifies user credentials 
	 public ArrayList<String> getSession(String u)
	 {
		 try { 
			 Connection conn = connect();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("select * from getSession('"+u+"')");
			 rs.next();
			 ArrayList<String>permissions=new ArrayList<String>();
			 permissions.add(rs.getString("sessionu"));
			 permissions.add(rs.getString("roleu"));
			 permissions.add(String.valueOf(rs.getInt("emp")));
			 permissions.add(String.valueOf(rs.getInt("dm")));
			 return permissions;
	        }catch(NullPointerException e1)
		 	{
	        	//log.error("PSQLConnection:verifyUserCredentials: No user found");
	        	e1.printStackTrace();
	        	return new ArrayList<String>();
		 	}catch (SQLException ex) {
		 		ex.printStackTrace();
	            System.out.println(ex.getMessage());
	            //log.error("PSQLConnection:verifyUserCredentials: Could not validate sql statement "+u.getUsername());
	            return new ArrayList<String>();
	        } 
	 }
	 
	 //Registers new user
	 public boolean registerUser(String fn,String ln, String email,String un,String pwd,int empid,String ver)
	 {
		 try { 
			 Connection conn = connect();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("select * from registerUser('"+fn+"','"+ln+"','"+email+"','"+un+"','"+pwd+"',"+empid+",'"+ver+"')");
			 rs.next();
			 int success = rs.getInt("registeruser");	
			 if(success==1)
			 {
				 return true;
			 }
			 else
			 {
				 return false;
			 }
	        }catch(NullPointerException e1)
		 	{
	        	e1.printStackTrace();
	        	//log.error("PSQLConnection:verifyUserCredentials: No user found");
	        	return false;
		 	}catch (SQLException ex) {
		 		ex.printStackTrace();
	            System.out.println(ex.getMessage());
	            //log.error("PSQLConnection:verifyUserCredentials: Could not validate sql statement "+u.getUsername());
	            return false;
	        } 
	 }
	 
	 public int getFileType(String ft)
	 {
		 try { 
			 Connection conn = connect();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("select * from getFileType('"+ft+"')");
			 rs.next();
			 int id = rs.getInt("getfiletype");
			 return id;
	        }catch(NullPointerException e1)
		 	{
	        	//log.error("PSQLConnection:verifyUserCredentials: No user found");
	        	return -1;
		 	}catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	            //log.error("PSQLConnection:verifyUserCredentials: Could not validate sql statement "+u.getUsername());
	            return -1;
	        } 
	 }
	 
	 public int getCourseId(String cDes)
	 {
		 try { 
			 Connection conn = connect();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("select * from addCourse('"+cDes+"')");
			 rs.next();
			 int id = rs.getInt("addcourse");
			 return id;
	        }catch(NullPointerException e1)
		 	{
	        	//log.error("PSQLConnection:verifyUserCredentials: No user found");
	        	return -1;
		 	}catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	            //log.error("PSQLConnection:verifyUserCredentials: Could not validate sql statement "+u.getUsername());
	            return -1;
	        } 
	 }
	 
	 public int uploadFile(String uDesc,int fType,InputStream stream)
	 {
		 try { 
			 Connection conn = connect();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("select * from uploadFile('"+uDesc+"',"+fType+",'"+stream+"')");
			 rs.next();
			 int id = rs.getInt("uploadFile");
			 return id;
	        }catch(NullPointerException e1)
		 	{
	        	e1.printStackTrace();
	        	//log.error("PSQLConnection:verifyUserCredentials: No user found");
	        	return -1;
		 	}catch (SQLException ex) {
		 		ex.printStackTrace();
	            //log.error("PSQLConnection:verifyUserCredentials: Could not validate sql statement "+u.getUsername());
	            return -1;
	        }
	 }
	 
	 public double getReimbursementPercentage(int tId)
	 {
		 try { 
			 Connection conn = connect();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("select * from getreimbursementpercentage("+tId+")");
			 rs.next();
			 double percent = rs.getDouble("getreimbursementpercentage");
			 return percent/100;
	        }catch(NullPointerException e1)
		 	{
	        	e1.printStackTrace();
	        	//log.error("PSQLConnection:verifyUserCredentials: No user found");
	        	return -1;
		 	}catch (SQLException ex) {
		 		ex.printStackTrace();
	            //log.error("PSQLConnection:verifyUserCredentials: Could not validate sql statement "+u.getUsername());
	            return -1;
	        }
	 }
	 
	 public int submitApplication(LocalDate sd,LocalDate ed,String location, int missedDays, int course,double cost,String passG,String gradingF,int courseT,double rAmount,int eId)
	 {
		 try { 
			 Connection conn = connect();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("select * from submitApplication('"+java.sql.Date.valueOf(sd)+"','"+java.sql.Date.valueOf(ed)+"','"+location+"',"+missedDays+","+course+","+cost+",'"+passG+"','"+gradingF+"',"+courseT+","+rAmount+","+eId+")");
			 rs.next();
			 int id = rs.getInt("submitapplication");
			 return id;
	        }catch(NullPointerException e1)
		 	{
	        	e1.printStackTrace();
	        	//log.error("PSQLConnection:verifyUserCredentials: No user found");
	        	return -1;
		 	}catch (SQLException ex) {
		 		ex.printStackTrace();
	            //log.error("PSQLConnection:verifyUserCredentials: Could not validate sql statement "+u.getUsername());
	            return -1;
	        }
	 }	 

	 
	 public void linkUpload(int uId,int aId)
	 {
		 try {
			 //log.info("PSQLConnection:deletePage:Atempting to delete page from database");
			 Connection conn = connect();
			 CallableStatement link = conn.prepareCall("call linkUpload("+uId+","+aId+")");
			 link.execute();
			 link.close();
			 //log.info("PSQLConnection:sharePage:Page "+pageID+" was shared successully");	 
		 }catch (Exception e){
			 e.printStackTrace();
			 //log.info("PSQLConnection:sharePage: Unable to share page");
		 }
	 }
	 
	 public void linkReviewer(int eId,int aId)
	 {
		 try {
			 //log.info("PSQLConnection:deletePage:Atempting to delete page from database");
			 Connection conn = connect();
			 CallableStatement link = conn.prepareCall("call linkReviewer("+eId+","+aId+")");
			 link.execute();
			 link.close();
			 //log.info("PSQLConnection:sharePage:Page "+pageID+" was shared successully");	 
		 }catch (Exception e){
			 e.printStackTrace();
			 //log.info("PSQLConnection:sharePage: Unable to share page");
		 }
	 }
	 
	 public ArrayList<ArrayList<String>> getApplications(String u,String ses)
	 {
		 try { 
			 Connection conn = connect();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("select * from getApplications('"+u+"','"+ses+"')");
			 ArrayList<ArrayList<String>>applications=new ArrayList<ArrayList<String>>();
			 while(rs.next()) 
			 {
				 ArrayList<String>temp=new ArrayList<String>();
				 temp.add(rs.getString("aid"));
				 temp.add(rs.getString("cdescription"));
				 temp.add(rs.getString("subdate").split(" ")[0]);
				 temp.add(rs.getString("cstart"));
				 temp.add(rs.getString("ccost"));
				 temp.add(rs.getString("creamount"));
				 temp.add(rs.getString("cstatus"));
				 temp.add(rs.getString("cfinal"));
				 temp.add(rs.getString("fgrade"));
				 temp.add(rs.getString("isPresentation"));
				 applications.add(temp);
			 }
			 return applications;
	        }catch(NullPointerException e1)
		 	{
	        	e1.printStackTrace();
	        	//log.error("PSQLConnection:verifyUserCredentials: No user found");
	        	return new ArrayList<ArrayList<String>>();
		 	}catch (SQLException ex) {
		 		ex.printStackTrace();
	            System.out.println(ex.getMessage());
	            //log.error("PSQLConnection:verifyUserCredentials: Could not validate sql statement "+u.getUsername());
	            return new ArrayList<ArrayList<String>>();
	        } 
	 }

	 public ArrayList<ArrayList<String>> getApplicationsAdmin(String u,String ses)
	 {
		 try { 
			 Connection conn = connect();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("select * from getApplicationsAdmin('"+u+"','"+ses+"')");
			 ArrayList<ArrayList<String>>applications=new ArrayList<ArrayList<String>>();
			 while(rs.next()) 
			 {
				 ArrayList<String>temp=new ArrayList<String>();
				 temp.add(rs.getString("aid"));
				 temp.add(rs.getString("empname"));
				 temp.add(rs.getString("emplastname"));
				 temp.add(rs.getString("cdescription"));
				 temp.add(rs.getString("subdate").split(" ")[0]);
				 temp.add(rs.getString("cstart"));
				 temp.add(rs.getString("ccost"));
				 temp.add(rs.getString("creamount"));
				 temp.add(rs.getString("cstatus"));
				 temp.add(rs.getString("cfinal"));
				 temp.add(rs.getString("fgrade"));
				 temp.add(rs.getString("isPresentation"));
				 temp.add(rs.getString("pgrade"));
				 applications.add(temp);
			 }
			 return applications;
	        }catch(NullPointerException e1)
		 	{
	        	e1.printStackTrace();
	        	//log.error("PSQLConnection:verifyUserCredentials: No user found");
	        	return new ArrayList<ArrayList<String>>();
		 	}catch (SQLException ex) {
		 		ex.printStackTrace();
	            System.out.println(ex.getMessage());
	            //log.error("PSQLConnection:verifyUserCredentials: Could not validate sql statement "+u.getUsername());
	            return new ArrayList<ArrayList<String>>();
	        } 
	 }
	 
	 public void approveApplication(String uname,String ses,int aId,String notes)
	 {
		 try {
			 //log.info("PSQLConnection:deletePage:Atempting to delete page from database");
			 Connection conn = connect();
			 CallableStatement link = conn.prepareCall("call approveApplication('"+uname+"','"+ses+"',"+aId+",'"+notes+"')");
			 link.execute();
			 link.close();
			 //log.info("PSQLConnection:sharePage:Page "+pageID+" was shared successully");	 
		 }catch (Exception e){
			 e.printStackTrace();
			 //log.info("PSQLConnection:sharePage: Unable to share page");
		 }
	 }
	 
	 public void denyApplication(int aId)
	 {
		 try {
			 //log.info("PSQLConnection:deletePage:Atempting to delete page from database");
			 Connection conn = connect();
			 CallableStatement link = conn.prepareCall("call denyApplication("+aId+")");
			 link.execute();
			 link.close();
			 //log.info("PSQLConnection:sharePage:Page "+pageID+" was shared successully");	 
		 }catch (Exception e){
			 e.printStackTrace();
			 //log.info("PSQLConnection:sharePage: Unable to share page");
		 }
	 }
	 
	 public void submitFinalGrade(String username,String session,int aId,boolean isPresentation,String grade)
	 {
		 try {
			 //log.info("PSQLConnection:deletePage:Atempting to delete page from database");
			 Connection conn = connect();
			 CallableStatement link = conn.prepareCall("call submitFinalGrade('"+username+"','"+session+"',"+aId+","+isPresentation+",'"+grade+"')");
			 link.execute();
			 link.close();
			 //log.info("PSQLConnection:sharePage:Page "+pageID+" was shared successully");	 
		 }catch (Exception e){
			 e.printStackTrace();
			 //log.info("PSQLConnection:sharePage: Unable to share page");
		 }
	 }
	 
	 public ArrayList<ArrayList<String>> getUsers()
	 {
		 try { 
			 Connection conn = connect();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("select * from getUsers()");
			 ArrayList<ArrayList<String>>applications=new ArrayList<ArrayList<String>>();
			 while(rs.next()) 
			 {
				 ArrayList<String>temp=new ArrayList<String>();
				 temp.add(rs.getString("uId"));
				 temp.add(rs.getString("ufn"));
				 temp.add(rs.getString("uln"));
				 temp.add(rs.getString("ue"));
				 applications.add(temp);
			 }
			 return applications;
	        }catch(NullPointerException e1)
		 	{
	        	e1.printStackTrace();
	        	//log.error("PSQLConnection:verifyUserCredentials: No user found");
	        	return new ArrayList<ArrayList<String>>();
		 	}catch (SQLException ex) {
		 		ex.printStackTrace();
	            System.out.println(ex.getMessage());
	            //log.error("PSQLConnection:verifyUserCredentials: Could not validate sql statement "+u.getUsername());
	            return new ArrayList<ArrayList<String>>();
	        } 
	 }

	 
	 public void createUserAdmin(String email,String password,int directManager,int role,String verification)
	 {
		 try { 
			 Connection conn = connect();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("select * from createuseradmin('"+email+"','"+password+"',"+directManager+","+role+",'"+verification+"')");
			 rs.next();
		 }catch(NullPointerException e1)
		 	{

		 	}catch (SQLException ex) {

	        }
	 }
	 
}

