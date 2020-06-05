package com.revature.andres.security;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class Encryption {

	private StrongPasswordEncryptor passwordEncryptor;
	
	public Encryption() {
		passwordEncryptor=new StrongPasswordEncryptor();
	}
	
	//Encrypts a pass word
	public String encryptPassword(String pwd)
	{
		return passwordEncryptor.encryptPassword(pwd);
	}
	
	//Encrypts a pass word
	public String randomPassword()
	{
			int rand= new java.util.Random().nextInt(1000);
			return passwordEncryptor.encryptPassword(rand+"");
	}
	
	//Compares user input pwd with database pwd
	public boolean comparePassword(String usrInput,String dbPwd) {

		System.out.println(dbPwd);
		if (passwordEncryptor.checkPassword(usrInput, dbPwd)) {
			return true;
		} else {
			return false;
		}
	}
	
	public StrongPasswordEncryptor getPasswordEncryptor() {
		return passwordEncryptor;
	}
	public void setPasswordEncryptor(StrongPasswordEncryptor passwordEncryptor) {
		this.passwordEncryptor = passwordEncryptor;
	}
}
