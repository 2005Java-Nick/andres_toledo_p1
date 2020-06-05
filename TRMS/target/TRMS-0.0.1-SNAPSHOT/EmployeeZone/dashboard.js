
//user class
class User{
	constructor(usr,pwd)
	{
		this.usr=usr;
		this.pwd=pwd;
	}
}

//Validates user credentials and redirects to login on bad attempt

function validateSession(usr)
{
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
		  var valid = JSON.parse(xhttp.responseText);
		  console.log(valid);
		  //console.log(valid["valid"]);
	      if(valid["valid"]=='false')
	      {
	    	  window.location.href = "/TRMS/login.html";
	      }else
	      {
	    	  console.log(valid["isAdmin"]);
	    	  window.localStorage.setItem("isAdmin",valid["isAdmin"]);
	      }
	    }
	  };
	  var url = "/TRMS/VerSesUs";
	  xhttp.open("POST", url, true);
	  xhttp.setRequestHeader("Content-type", "application/json;charset=UTF-8");
	  var data = JSON.stringify(usr);
	  xhttp.send(JSON.stringify(data));	
}

function getUsers()
{
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
		  var data = JSON.parse(xhttp.responseText);
		  var users= document.getElementById("user-direct-manager");
		  for(user of data)
		  {
			  var usOption= document.createElement("option");
			  usOption.setAttribute("value",user[0]);
			  usOption.innerHTML=user[1]+" "+user[2]+" "+user[3];
			  users.appendChild(usOption);
		  }
		  console.log(data);
	    }
	  };
	  var url = "/TRMS/GeUsSe";
	  xhttp.open("POST", url, true);
	  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
	  var mes="usr="+window.localStorage.getItem("username")+"&pwd="+window.localStorage.getItem("sessionId");
	  console.log("before send",mes);
	  xhttp.send(mes);	
}

function createUserAdmin()
{
	console.log("In create user admin");
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
		  console.log(data);
	    }
	  };
	  var url = "/TRMS/CrUsAdSe";
	  xhttp.open("POST", url, true);
	  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
	  var email= document.getElementById("user-email").value;
	  var role=document.getElementById("user-role").value;
	  var direct =document.getElementById("user-direct-manager").value;
	  console.log(email);
	  console.log(role);
	  console.log(direct);
	  var mes="usr="+window.localStorage.getItem("username")+"&pwd="+window.localStorage.getItem("sessionId")+"&em="+String(email)+"&ro="+String(role)+"&dm="+String(direct);
	  console.log("before send",mes);
	  xhttp.send(mes);	
}


function getApplications()
{
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
		  var data = JSON.parse(xhttp.responseText);
		  console.log(data);
		  var oldtbody= document.getElementById("table-body");
		  var tbody = document.createElement('tbody');
		  tbody.setAttribute("id", "table-body");
		  oldtbody.parentNode.replaceChild(tbody, oldtbody);
		  for(var myRows of data)
		  {
			  var tr = document.createElement("tr");
			  var th =document.createElement("th");
			  th.setAttribute("scope","row");
			  th.innerHTML=myRows[0];
			  var td1 =document.createElement("td");
			  var td2 =document.createElement("td");
			  var td3 =document.createElement("td");
			  var td4 =document.createElement("td");
			  var td5 =document.createElement("td");
			  var td6 =document.createElement("td");
			  td1.innerHTML=myRows[1];
			  td2.innerHTML=myRows[2];
			  td3.innerHTML=myRows[3];
			  td4.innerHTML=myRows[4];
			  td5.innerHTML=myRows[5];			  
			  tr.appendChild(th);
			  tr.appendChild(td1);
			  tr.appendChild(td2);
			  tr.appendChild(td3);
			  tr.appendChild(td4);
			  tr.appendChild(td5);
			  tr.appendChild(td6);
			  if(myRows[6]=='f' && myRows[7]=='t')
			  {
				  td6.innerHTML="Denied";
			  }
			  else if(myRows[6]=='f' && myRows[7]=='f')
			  {
				  td6.innerHTML="Pending";
			  }else if(myRows[6]=='t' && myRows[7]=='f')
			  {
				  if(myRows[8].length==0 && myRows[9]=='f')
				  {		  
					  td6.innerHTML="Please submit final grade...";
					  var inpa=document.createElement("input");
					  inpa.setAttribute("type", "button");
					  inpa.setAttribute("value","Submit Grade");
					  inpa.setAttribute("onclick","btnFinalGrade("+myRows[0]+")");
					  inpa.setAttribute("id","btn-final-grade-"+myRows[0]);
					  var inpd=document.createElement("input");
					  inpd.setAttribute("type", "text");
					  inpd.setAttribute("placeholder","If presentation leave blank.");
					  inpd.setAttribute("id","final-grade-"+myRows[0]);
					  tr.appendChild(inpd);
					  tr.appendChild(inpa);
				  }
				  else
				  {
					  td6.innerHTML="Pending final approval";
				  }
			  }else
			  {
				  td6.innerHTML="Approved";
			  }
			  tbody.appendChild(tr);
		  }
	    }
	  };
	  var url = "/TRMS/GeApSe";
	  xhttp.open("POST", url, true);
	  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
	  var mes="usr="+window.localStorage.getItem("username")+"&pwd="+window.localStorage.getItem("sessionId");
	  console.log(mes);
	  xhttp.send(mes);	
}

function btnApprove(index)
{
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
	if (this.readyState == 4 && this.status == 200) {
		var data = JSON.parse(xhttp.responseText);
		console.log(data);
		getApplicationsAdmin();
	}
	};
	var url = "/TRMS/ApApSe";
	xhttp.open("POST", url, true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
	var mes="usr="+window.localStorage.getItem("username")+"&pwd="+window.localStorage.getItem("sessionId")+"&aId="+String(index)+"&ad=a";
	console.log(mes);
	xhttp.send(mes);	
}

function btnDeny(index)
{
	console.log("In deny button");
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
	if (this.readyState == 4 && this.status == 200) {
		var data = JSON.parse(xhttp.responseText);
		console.log(data);
		getApplicationsAdmin();
	}
	};
	var url = "/TRMS/ApApSe";
	xhttp.open("POST", url, true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
	var mes="usr="+window.localStorage.getItem("username")+"&pwd="+window.localStorage.getItem("sessionId")+"&aId="+String(index)+"&ad=d";
	console.log(mes);
	xhttp.send(mes);	
}

function btnFinalGrade(index)
{
	var a = document.getElementById("final-grade-"+index).value;
	console.log(String(index)," works :",a);
	
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
	if (this.readyState == 4 && this.status == 200) {
		var data = JSON.parse(xhttp.responseText);
		console.log(data);
		getApplications();
	}
	};
	var url = "/TRMS/SuGrSe";
	xhttp.open("POST", url, true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
	var mes="usr="+window.localStorage.getItem("username")+"&pwd="+window.localStorage.getItem("sessionId")+"&aId="+String(index)+"&grade="+String(a);
	console.log(mes);
	xhttp.send(mes);	
}


function getApplicationsAdmin()
{
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
		  var data = JSON.parse(xhttp.responseText);
		  var oldtbody= document.getElementById("table-body-admin");
		  var tbody = document.createElement('tbody');
		  tbody.setAttribute("id", "table-body-admin");
		  oldtbody.parentNode.replaceChild(tbody, oldtbody);
		  for(var myRows of data)
		  {
			  console.log(myRows);
			  var tr = document.createElement("tr");
			  var th =document.createElement("th");
			  th.setAttribute("scope","row");
			  th.innerHTML=myRows[0];
			  var td1 =document.createElement("td");
			  var td2 =document.createElement("td");
			  var td3 =document.createElement("td");
			  var td4 =document.createElement("td");
			  var td5 =document.createElement("td");
			  var td6 =document.createElement("td");
			  var td7 =document.createElement("td");
			  var td8 =document.createElement("td");
			  var td9 =document.createElement("td");
			  var td10 =document.createElement("td");
			  var td11 =document.createElement("td");
			  td1.innerHTML=myRows[1];
			  td2.innerHTML=myRows[2];
			  td3.innerHTML=myRows[3];
			  td4.innerHTML=myRows[4];
			  td5.innerHTML=myRows[5];
			  td6.innerHTML=myRows[6];
			  td7.innerHTML=myRows[7];
			  td9.innerHTML=myRows[10];
			  td10.innerHTML=myRows[11];
			  td11.innerHTML=myRows[12];
			  if (myRows[8]=='f' &&myRows[9]=='t' )
			  {
				  td8.innerHTML="Denied";
			  }else if(myRows[8]=='f')
			  {
				  td8.innerHTML="Pending";
			  }else if(myRows[9]=='f')
			  {
				  td8.innerHTML="Pending";
			  }else
			  {
				  td8.innerHTML="Approved";
			  }
			  
			  tr.appendChild(th);
			  tr.appendChild(td1);
			  tr.appendChild(td2);
			  tr.appendChild(td3);
			  tr.appendChild(td4);
			  tr.appendChild(td5);
			  tr.appendChild(td6);
			  tr.appendChild(td7); 
			  tr.appendChild(td8); 
			  if(myRows[10].length==0 && myRows[11]=='f')
			  {
				  td9.innerHTML="N/A";  
				  td10.innerHTML="N/A";
			  }else if(myRows[10].length==0)
			  {
				  td9.innerHTML="N/A";  
				  td10.innerHTML="True";
			  }else if(myRows[11]=='f')
			  {
				  td10.innerHTML="False";
			  }
			  tr.appendChild(td11); 
			  tr.appendChild(td9); 
			  tr.appendChild(td10); 
			  if (myRows[8]=='f' &&myRows[9]=='t' )
			  {
				  
			  }
			  else if(myRows[8]=='f')
			  {
				  var inpa=document.createElement("input");
				  inpa.setAttribute("type", "button");
				  inpa.setAttribute("value","Approve");
				  inpa.setAttribute("onclick","btnApprove("+myRows[0]+")");
				  var inpd=document.createElement("input");
				  inpd.setAttribute("type", "button");
				  inpd.setAttribute("value","Deny");
				  inpd.setAttribute("onclick","btnDeny("+myRows[0]+")");
				  tr.appendChild(inpa);
				  tr.appendChild(inpd);
			  }
			  tbody.appendChild(tr);
		  }
		  
	    }
	  };
	  var url = "/TRMS/GeApSe";
	  xhttp.open("POST", url, true);
	  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
	  var mes="usr="+window.localStorage.getItem("username")+"&pwd="+window.localStorage.getItem("sessionId")+"&adm="+window.localStorage.getItem("isAdmin");
	  console.log(mes);
	  xhttp.send(mes);	
}


//Logs user out

function signOut(){
	window.localStorage.setItem('sessionId',false);
	window.location.href = "/TRMS/login.html";
}

//Gets state data
function reqListener() {
	  var data = JSON.parse(this.responseText);
	  var s= document.getElementById("course-location");
	  var i=1;
	  for(var state of data)
	  {
		  //console.log(state);
		  s.options[s.options.length] = new Option(state,i);
		  i++;
	  }
}

function reqError(err) {
	console.log('Fetch Error :-S', err);
}
function initLocations(url)
{
	var oReq = new XMLHttpRequest();
	oReq.onload = reqListener;
	oReq.onerror = reqError;
	oReq.open('get', url, true);
	oReq.send();
}


//Get todays date


//On page load

window.onload = function () {
    user=new User(window.localStorage.getItem('username'), window.localStorage.getItem('sessionId'));
    validateSession(user);
    initLocations('/../TRMS/res/state-names.json');
    document.getElementById("session").value=String(window.localStorage.getItem("sessionId"));
    document.getElementById("username").value=String(window.localStorage.getItem("username"));
    console.log(window.localStorage.getItem("sessionId"));
    document.getElementById("welcome").innerHTML="Welcome : "+window.localStorage.getItem("username");
}

//Verify administrator

function verifyAdmin(){
	var admin = window.localStorage.getItem('isAdmin');
	if(admin!='Employee'&&admin!='false')
	{
		return true;
	}else
	{
		return false;
	}
}

//Window logic

const fadeControl=100;

function fadeOutDivs(){
	$("#status-div-general").fadeOut(fadeControl);
	$("#inbox-div-general").fadeOut(fadeControl);
	$("#new-application-div-general").fadeOut(fadeControl);
	$("#admin-div-general").fadeOut(fadeControl);
	$("#error-div-general").fadeOut(fadeControl);
}

function setFileName(){
	var name=document.getElementById("upload").value;
	name=name.split("\\");
	document.getElementById("filename").value=name[2];
}

$(document).ready(function(){
	  $("#dashboard-new-application").click(function(){
		fadeOutDivs();
	    $("#new-application-div-general").fadeIn(fadeControl);
	  });
	});

$(document).ready(function(){
	  $("#dashboard-status").click(function(){
		fadeOutDivs();
		getApplications();
	    $("#status-div-general").fadeIn(fadeControl);
	  });
	});

$(document).ready(function(){
	  $("#dashboard-inbox").click(function(){
		fadeOutDivs();
	    $("#inbox-div-general").fadeIn(fadeControl);
	  });
	});

$(document).ready(function(){
	  $("#dashboard-admin").click(function(){
		fadeOutDivs();
		if(verifyAdmin())
		{
		    if(window.localStorage.getItem("isAdmin")=='SystemAdmin')
		    {
		    	getUsers();
		    	$("#new-user-form").fadeIn(fadeControl);
		    }else
		    {
		    	$("#admin-div-general").fadeIn(fadeControl);
		    	getApplicationsAdmin();
		    }
		    
		}else 
		{
			fadeOutDivs();
		    $("#error-div-general").fadeIn(fadeControl);
		}
	  });
	});

$( function() {
    $( "#start-date" ).datepicker({
    	dateFormat: "yy/mm/dd",
    	changeMonth: true,
    	changeYear: true,
    	yearRange: "-0:+5",
    	minDate: "+8" 
    });
    
  } );

$( function() {
    $( "#end-date" ).datepicker({
    	dateFormat: "yy/mm/dd",
    	changeMonth: true,
    	changeYear: true,
    	yearRange: "-20:+0",
    	minDate: "9"
    });
  } );

$(document).on('change', '#upload',function(){ 
	setFileName();
});
