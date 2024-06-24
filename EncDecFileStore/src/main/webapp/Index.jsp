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
		<a id="usrName1" href="/LoginController">Login</a>
		<a id="usrName2" href="#">Login</a>
		<div id="loginopt">
			<a href="/AccountDetails">My Account</a>
			<a href="/SignOut">Signout</a>
		</div>
	</ul>
</div>

<div class = "firstelem"></div>
<div id="usrAdd"></div>
<div class="bar">
	<ul class="barul">
		<a href="#" onClick="allShoes()">All</a>
		<a href="#" onClick="selectedShoes('Football')">Football</a>
		<a href="#" onClick="selectedShoes('Basketball')">Basketball</a>
		<a href="#" onClick="selectedShoes('Running')">Running</a>
	</ul>
</div>
<div id="site"></div>
<div class="barul footer">
	<a href="https://www.facebook.com/"><i class="fab fa-facebook-f"></i>&nbsp;&nbsp;Facebook</a>
	<a href="mailto:fakeemailid@fakemail.com"><i class="fas fa-envelope"></i>&nbsp;&nbsp;Mail us!</a>
	<a href="tel:+91 1234567890"><i class="fas fa-mobile-alt"></i>&nbsp;&nbsp;Call us!</a>
	<a href="https://www.instagram.com/"><i class="fab fa-instagram"></i>&nbsp;&nbsp;Instagram</a>
</div>
</body>
<head>
<meta charset="ISO-8859-1">
<title>SportyShoes- Home</title>
<script>
const abc = ${abc};
const usrAdd = ${usrAdd};
const usrFound = ${usrFound};
const adminRole = ${adminRole};

console.log(adminRole);
console.log(usrAdd);
console.log(usrFound);

function usrAdded() {
	switch(usrAdd[0]) {
		case 0:
			break;
			
		case true:
			document.getElementById("usrAdd").innerHTML = "User Already Exists!";
			document.getElementById("usrAdd").style.color= "red";
			document.getElementById("usrAdd").style.display= "block";
			setTimeout(function(){ document.getElementById("usrAdd").style.display= "none"; }, 5000);
			break;
			
		case false:
			document.getElementById("usrAdd").innerHTML = "New User Added!";
			document.getElementById("usrAdd").style.display= "block";
			setTimeout(function(){ document.getElementById("usrAdd").style.display= "none"; }, 5000);
			break;
	}
}

function usrFoundFunc() {
	switch(usrFound[0]) {
		case 0:
			break;
			
		case true:
			document.getElementById("usrName1").style.display = "none";
			document.getElementById("usrName2").style.display = "block";
			document.getElementById("usrName2").innerHTML = usrFound[1];
			break;
			
		case false:
			document.getElementById("usrName1").innerHTML = "User Not Found!!";
			document.getElementById("usrName1").style.color = "red";
			setTimeout(function(){ 
				document.getElementById("usrName").innerHTML= "Login"; 
				document.getElementById("usrName").style.color = "";
			}, 3000);
			break;
	}
}


function allShoes() {
	let html = "";
	for (let i = 0; i < abc.length; i++) {
		html+= ("<div class= \"proddiv\"><img class= \"prodimg\" src = \"/Images/");
		html+= (abc[i]['Company']+"-"+abc[i]['Type']+"-"+abc[i]['Name']+".jpg\"/>");
		html+= ("<span class=\"pricetag\">"+abc[i]['Price']+"$</span><h2 class= \"prodhead\">"+abc[i]['Name']+"</h2><ul class= \"prodtags\"><p>");
		html+= (abc[i]['Company']+"</p><p>"+abc[i]['Type']+"</p></ul><p class= \"info\">");
		html+= (abc[i]['Info']+"</p><div class= \"prodtags\"><a href=\"/CartAdder?pID="+abc[i]['pID']+"\">Add to Cart</a><a href=\"/BuyAdder?pID="+abc[i]['pID']+"\">Buy Now</a></div></div><hr/>");
	}
	html+="<h2 style=\"text-align: center; font-size: 48px; padding: 10px;\">More Coming Soon!</h2></div>";
	//console.log(html);
	document.getElementById("site").innerHTML = html;
};


function selectedShoes(arg) {
	let html = "";	
	for (let i = 0; i < abc.length; i++) {
		if(abc[i]['Type'] == arg) {
			html+= ("<div class= \"proddiv\"><img class= \"prodimg\" src = \"/Images/");
			html+= (abc[i]['Company']+"-"+abc[i]['Type']+"-"+abc[i]['Name']+".jpg\"/>");
			html+= ("<span class=\"pricetag\">"+abc[i]['Price']+"$</span><h2 class= \"prodhead\">"+abc[i]['Name']+"</h2><ul class= \"prodtags\"><p>");
			html+= (abc[i]['Company']+"</p><p>"+abc[i]['Type']+"</p></ul><p class= \"info\">");
			html+= (abc[i]['Info']+"</p><div class= \"prodtags\"><a href=\"/CartAdder?pID="+abc[i]['pID']+"\">Add to Cart</a><a href=\"/BuyAdder?pID="+abc[i]['pID']+"\">Buy Now</a></div></div><hr/>");
		}
	}
	html+="<h2 style=\"text-align: center; font-size: 48px; padding: 10px;\">More Coming Soon!</h2></div>";
	//console.log(html);
	document.getElementById("site").innerHTML = html;
};

function adminRoleFunc() {
		switch(adminRole[0]) {
		case 0:
			break;
		
		case true:
			document.getElementById("loginopt").innerHTML = "<a href = \"/AdminRights\">Admin Page</a><a href=\"/AccountDetails\">My Account</a><a href=\"/SignOut\">Signout</a>";
			document.getElementById("usrAdd").innerHTML = "Admin Role Acquired!";
			document.getElementById("usrAdd").style.color = "lawngreen";
			document.getElementById("usrAdd").style.display= "block";
			setTimeout(function(){ document.getElementById("usrAdd").style.display= "none"; }, 5000);
			break;
			
		case false:			
			document.getElementById("usrAdd").innerHTML = "Normal User Mode!";
			document.getElementById("usrAdd").style.color = "crimson";
			document.getElementById("usrAdd").style.display= "block";
			setTimeout(function(){ document.getElementById("usrAdd").style.display= "none"; }, 5000);
			break;
		}
	
}

window.onload = adminRoleFunc();
window.onload = usrAdded();
window.onload = allShoes();
window.onload = usrFoundFunc();
</script>
</head>
</html>