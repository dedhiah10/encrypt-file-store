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
<head>
<meta charset="ISO-8859-1">
<title>SportyShoes- NewUser</title>
</head>
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
	<form class="formy" action="UserAdder" method="post">
		<h2>Registration Form</h2>
		<label class="formfield" for="uname">Enter Username:</label>
		<input class="forminput" name="name" id="name" type="text" required/>		
		<label class="formfield" for="country">Enter Country:</label>
		<input class="forminput" name="country" id="country" type="text" required/>		
		<label class="formfield" for="zipCode">Enter ZipCode:</label>
		<input class="forminput" name="zipCode" id="zipCode" type="number" min="100000" max = "999999" required/>		
		<label class="formfield" for="number">Enter Number:</label>
		<input class="forminput" name="number" id="number" type="number" min="1000000000" max = "9999999999" required/>
		<label class="formfield" for="dateOfBirth">Enter Number:</label>
		<input class="forminput" name="dateOfBirth" id="dateOfBirth" type="date" required/>
		<label class="formfield" for="password">Enter Password:</label>
		<input class="forminput" name="password" id="password" type="password" required/><br/><br/>
		<input style="width: 18px; height:18px;" type="checkbox" name="roleID" id="roleID" value="Truly"/>
		<label for="roleID" style="font-size: 24px;"> Login as Admin?</label>
		<br/><br/>
		<input class="formbutton" type="submit" value="Register"/>
	</form>
</div>
<div class="barul footer">
	<a href="https://www.facebook.com/"><i class="fab fa-facebook-f"></i>&nbsp;&nbsp;Facebook</a>
	<a href="mailto:fakeemailid@fakemail.com"><i class="fas fa-envelope"></i>&nbsp;&nbsp;Mail us!</a>
	<a href="tel:+91 1234567890"><i class="fas fa-mobile-alt"></i>&nbsp;&nbsp;Call us!</a>
	<a href="https://www.instagram.com/"><i class="fab fa-instagram"></i>&nbsp;&nbsp;Instagram</a>
</div>
</body>
</html>