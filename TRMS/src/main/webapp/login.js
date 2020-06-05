class User{
	constructor(usr,pwd)
	{
		this.usr=usr;
		this.pwd=pwd;
	}
}

function sendRequest(usr)
{
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
		  var valid = JSON.parse(xhttp.responseText);
		  console.log(valid);
	      if(!valid)
	      {
	    	  window.localStorage.setItem('username', usr.usr)
	    	  window.localStorage.setItem('sessionId', false)
	    	  document.getElementById("Message").innerHTML="Invalid credentials unable to login";
	    	  console.log(window.localStorage.getItem('sessionId'));
	      }else
	      {  
	    	  window.localStorage.setItem('username', usr.usr)
	    	  window.localStorage.setItem('sessionId', valid)
	    	  window.location.href = "./EmployeeZone/dashboard.html";
	      }
	    } 
	  };
	  var url = "./LogUsrServ";
	  xhttp.open("POST", url, true);
	  xhttp.setRequestHeader("Content-type", "application/json;charset=UTF-8");
	  var data = JSON.stringify(usr);
	  xhttp.send(JSON.stringify(data));	
}

function login()
{
	var usr= document.getElementById("input-email").value;
	var pwd= document.getElementById("input-password").value;
	usrNow= new User(usr,pwd);
	sendRequest(usrNow);
}

window.onload = function () {
    this.document.getElementById("btnSubmit").addEventListener("click", () => login());
}
