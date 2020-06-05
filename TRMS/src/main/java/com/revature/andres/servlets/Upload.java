package com.revature.andres.servlets;

import java.io.File;
import java.io.Serializable;

public class Upload implements Serializable{

	File file;
	
	private static final long serialVersionUID = 1L;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
}
