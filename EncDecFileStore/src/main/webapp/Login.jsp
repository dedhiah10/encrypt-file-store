<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<link rel="stylesheet" href="Styles/Home.css">
<link rel="stylesheet"
  href="https://use.fontawesome.com/releases/v5.8.2/css/all.css"
  integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" 
  crossorigin="anonymous"
 />
<body>
<div class="navbar">
	<h1 id="logo">Sporty Shoes</h1>
	<ul class="navul">
		<a href="/">Home</a>
		<a href="/CartManager">Cart<img src="/Images/Cart_Icon.png" class="imgs"></a>
		<a href="/LoginController">Login</a>
	</ul>
</div>
<div class = "firstelem"></div>
<div id="site">
<!-- -->
	<form class="formy" action="UserChecker" method="post">
		<h2>Login Form</h2>
		<label class="formfield" for="uname">Enter Username:</label>
		<input class="forminput" name="uname" id="uname" type="text" value="admin" required/>
		<label class="formfield" for="pword">Enter Password:</label>
		<input class="forminput" name="pword" id="pword" type="password" value="admin" required/>
		<div class="formField" style="margin: 20px;"><input style="width: 18px; height:18px;" type="checkbox" name="admin" id="admin" value="true" checked/><label for="admin" style="font-size: 24px;"> Login as Admin?</label></div>
		<input class="formbutton" type="submit" value="Login"/><br/><br/><br/>
		<a class="formbutton" href="/NewUser"> Make a New Account? </a>
	</form>  
	<form class="formy" action="EncUpload" method="POST" id="fileForm" enctype="multipart/form-data">
		<h2>Upload:</h2>
		<input class="forminput" name="fileUp" id="fileUp" type="file" required/>
		<input class="formbutton" type="submit" value="Upload"/><br/>
	</form>
</div>
<div class="barul footer">
	<a href="https://www.facebook.com/"><i class="fab fa-facebook-f"></i>&nbsp;&nbsp;Facebook</a>
	<a href="mailto:fakeemailid@fakemail.com"><i class="fas fa-envelope"></i>&nbsp;&nbsp;Mail us!</a>
	<a href="tel:+91 1234567890"><i class="fas fa-mobile-alt"></i>&nbsp;&nbsp;Call us!</a>
	<a href="https://www.instagram.com/"><i class="fab fa-instagram"></i>&nbsp;&nbsp;Instagram</a>
</div>
</body>
<head>
<meta charset="ISO-8859-1">
<script>
/*
const uploadForm = document.querySelector('#fileForm')
uploadForm.addEventListener('submit', function(e) {
   e.preventDefault()
   let file = e.target.fileUp.files[0]
   console.log(file)
   let formData = new FormData()
   formData.append('fileUp', file)
   let req = new XMLHttpRequest()
   req.open("POST",'http://localhost:8080/EncUpload')
   console.log(req.send(formData))
   fetch('http://localhost:8080/EncUpload', {
      method: 'POST',
      body: formData
   })
   .then(resp => resp.json())
   .then(data => {
      if (data.errors) {
         alert(data.errors)
      }
      else {
         console.log(data)
      }
   })   
})*/
</script>
<title>SportyShoes- Login</title>
</head>
</html>