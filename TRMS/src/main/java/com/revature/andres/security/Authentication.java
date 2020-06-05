package com.revature.andres.security;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;

import com.revature.andres.db.PSQLConnection;

public class Authentication {

	private Encryption encryption;
	private PSQLConnection connection;
	private static Authentication authenticator ;
	
	
	public Authentication(){
		encryption=new Encryption();
		connection=new PSQLConnection();
	}
	
	public static Authentication getAuthentication() {
		if(authenticator==null)
		{
			return new Authentication();
		}
		else
		{
			return authenticator;
		}
	};
	
	//checks if input password is the same as db
	public boolean loginUser(String username,String password)
	{
		String userPasswordDB=connection.getPassword(username);
		if(userPasswordDB.length()!=0)
		{
			return encryption.comparePassword(password, userPasswordDB);
		}
		return false;
	}
	
	public String createSession(String username)
	{
		int randomSeed = new java.util.Random().nextInt(1000000);
		String sessionId=encryption.encryptPassword(randomSeed+"");
		if(connection.createSession(username, sessionId))
		{
			return sessionId;
		}else
		{
			return "false";
		}
		
	}

	public ArrayList<String> validateSession(String usr, String ses) {
		ArrayList<String> permissions = connection.getSession(usr);
		if(permissions.size()==0)
		{
			ArrayList<String>deniedAccess=new ArrayList<String>();
			deniedAccess.add("false");
			deniedAccess.add("false");
			return deniedAccess;
		}
		
		if(permissions.get(0).equals(ses))
		{
			return permissions ;
		}
		else
		{
			ArrayList<String>deniedAccess=new ArrayList<String>();
			deniedAccess.add("false");
			deniedAccess.add("false");
			return deniedAccess;
		}
	}
	
	public String registerUser(String fn,String ln, String email,String un,String pwd,int empid,String ver)
	{
		if(connection.registerUser(fn, ln, email, un, pwd, empid, ver))
		{
			return "Your account has been registered correctly";
		}
		else {
			return "There was an error registering your account";
		}
	}
	
	public int getFileType(String typeN)
	{
		return connection.getFileType(typeN);
	}
	
	public int getCourseId(String cDesc)
	{
		return connection.getCourseId(cDesc);
	}

	public int uploadFile(String fDesc,int fType,InputStream data)
	{
		return connection.uploadFile(fDesc,fType,data);
	}
	
	public double getReimbursementPercentage(int tId)
	{
		return connection.getReimbursementPercentage(tId);
	}
	
	public int submitApplication(LocalDate sd,LocalDate ed,String location, int missedDays, int course,double cost,String passG,String gradingF,int courseT,double rAmount,int eId)
	{
		return connection.submitApplication(sd, ed, location, missedDays, course, cost, passG, gradingF, courseT, rAmount, eId);
	}
	
	public void linkUpload(int uId,int aId)
	{
		connection.linkUpload(uId,aId);
	}
	
	public void linkReviewer(int eId,int aId)
	{
		connection.linkReviewer(eId,aId);
	}
	
	public Encryption getEncryption() {
		return encryption;
	}

	public void setEncryption(Encryption encryption) {
		this.encryption = encryption;
	}
	
	public ArrayList<ArrayList<String>> getApplications(String username,String session,String isAdmin){
		if(isAdmin==null)
		{
			return connection.getApplications(username, session);
		}else
		{
			return connection.getApplicationsAdmin(username, session);
		}
	} 
	
	public void approveApplication(String username,String session, int appId, String notes)
	{
		connection.approveApplication(username, session, appId, notes);
	}
	
	public void submitFinalGrade(String username,String session,int aId,boolean isPresentation,String grade)
	{
		connection.submitFinalGrade(username,session,aId,isPresentation,grade);
	}
	
	public void denyApplication(int aId){
		connection.denyApplication(aId);
	}
	
	public ArrayList<ArrayList<String>> getUsers(){
		return connection.getUsers();
	}
	
	public void createUserAdmin(String email,String password,int directManager,int role,String verification) {
		connection.createUserAdmin(email, password, directManager, role, verification);
	}
	
}
