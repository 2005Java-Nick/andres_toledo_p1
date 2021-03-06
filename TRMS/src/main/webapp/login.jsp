<!DOCTYPE html>
<html lang="en">
<head>
  <title>TRSM - Login</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
  <link rel="stylesheet" href="login.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
  <script src="login.js"></script>
</head>
<body>
	<div class="bg" id="background">
		<div class="container-fluid h-80 d-flex justify-content-center align-items-center" id="main-container">
			<div class="row" id="login-div">
			
				<div class="col-lg-12 d-flex justify-content-center align-items-center">
					<img class="logo img-fluid" src="img/logotrms3.png"></img>
				</div>
				
				<div class="col-lg-12 d-flex justify-content-center align-items-center">
					<h1 class="logo-label">Tuition Reimbursement</h1>
				</div>
				
				<div class="col-lg-3"></div>
				
				<div class="col-lg-6 rounded login-container" id="login-panel">  
					
					<h2 class="label-header text-center text-info" id="login-label">Login</h2>
					
					<div class="input-components">
						<form>
						  <div class="form-group ">
						    <label class="text-info login-component" for="input-email">Username</label>
						    <input type="text" class="form-control login-component input-lg" id="input-email" aria-describedby="emailHelp" placeholder="Enter username or email">
						  </div> 
						  <div class="form-group ">
						    <label class="login-component text-info" for="input-password">Password</label>
						    <input type="password" class="form-control login-component input-lg" id="input-password" placeholder="Password">
						  </div>
						  <div class="form-group ">
						    <a class="form-link-label login-component" href="/TRMS/CreateUser/createuser.html">Create Account</a>
						  </div>
						  <div class="form-group ">
							<button type="button" class="btn btn-primary btn-submit login-component" id="btnSubmit">Submit</button>
						  </div>
						  <div class="form-group align-items-center">
						  	<strong class="text-info login-component"><%=request.getAttribute("Message")%></strong>
						  </div>
						</form>
					</div>
				</div>
				<div class="col-lg-3"></div>
			</div>
		</div>
	</div>
</body>
</html>