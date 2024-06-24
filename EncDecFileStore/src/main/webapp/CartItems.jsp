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
<title>SportyShoes- CartMan</title>
<script>
const abc = ${abc};
const usrFound = ${usrFound};
const adminRole = ${adminRole};
//const admin = ${admin};

//console.log(admin);
console.log(adminRole[0]);
function allShoes() {
	let html = "<h2>Your Cart:</h2><br/>";
	let price = 0;
	for (let i = 0; i < abc.length; i++) {
		price+=abc[i]['Price'];
		html+= ("<div class= \"proddiv\"><img class= \"prodimg\" src = \"/Images/");
		html+= (abc[i]['Company']+"-"+abc[i]['Type']+"-"+abc[i]['Name']+".jpg\"/>");
		html+= ("<span class=\"pricetag\">"+abc[i]['Price']+"$</span><h2 class= \"prodhead\">"+abc[i]['Name']+"</h2><ul class= \"prodtags\"><p>");
		html+= (abc[i]['Company']+"</p><p>"+abc[i]['Type']+"</p></ul><p class= \"info\">");
		html+= (abc[i]['Info']+"</p><div class= \"prodtagscart\"><a href=\"/CartRemover?rpID="+abc[i]['pID']+"\">Remove</a></div></div><hr/>");
	}
	html+="<h2>Total:  "+price+"$</h2><br/><a id=\"checkout\" href=\"/CheckoutCart\">Checkout Cart?</a></div>";
	document.getElementById("site").innerHTML = html;
};

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

window.onload = allShoes();
window.onload = usrFoundFunc();
//window.onload = adminRoleFunc();
</script>
<style>
.prodtagscart {
	max-width: 40%;
	margin: auto;
	display: grid;
	grid-template-columns: 1fr;
	text-align: center;
	word-wrap: wrap;
}

.prodtagscart a {
	font-size: 16px;
	text-align: center;
	background-color: red;
	color: white;
	padding: 7px;
	border: 1px solid black;
	border-radius: 7px;
}
.prodtagscart a:active {
	background-color: var(--color-accent-dark-gray);
	color: var(--color-accent-active-border);
	border: 2px solid var(--color-accent-active-border);
}

.prodtagscart p {
	font-size: 16px;
	padding: 7px;
	margin: 1px 1px;
}

#checkout {
	max-width: 40%;
	margin: auto;
	font-size: 16px;
	text-align: center;
	background-color: lawngreen;
	border: 1px solid black;
	border-radius: 12px;
}

#checkout:active {
	background-color: var(--color-accent-dark-gray);
	color: var(--color-accent-active-border);
	border: 2px solid var(--color-accent-active-border);
}
</style>
</head>
</html>