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
		<a id="usrName1" href="/SignOut">SignOut</a>
	</ul>
</div>

<div class = "firstelem"></div>

<div class="bar">
	<ul class="barul">
		<a href="#" onClick="byDate(1)">By Date Asc</a>
		<a href="#" onClick="byDate(2)">By Date Desc</a>
		<a href="#" onClick="byPrice(1)">By Price Asc</a>
		<a href="#" onClick="byPrice(2)">By Price Desc</a>
	</ul>
</div>
<div id="usrDet"></div>
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
<title>SportyShoes- MyAccount</title>
<script>
const usrbuy = ${usrbuy};
const UserDetails = ${UserDetails}[0];
console.log(UserDetails);
let abc = usrbuy.slice();
//console.log(usrbuy);

function loaddets() {
	let html="<h2>Your Account Details:</h2><br/>";
	html+=("<table><tr><td>UserName</td><td>"+UserDetails['uname']+"</td></tr><tr><td>Number</td><td>"+UserDetails['Number']+"</td></tr><tr><td>Country</td><td>"+UserDetails['Country']+"</td></tr><td>Zip Code</td><td>"+UserDetails['ZipCode']+"</td></tr></table>");
	console.log(html);
	document.getElementById("usrDet").innerHTML = html;
}

function loadelems(arg) {
	let html="";
	if (arg == 1) {
		for(let i = 0; i < usrbuy.length; i++) {
			html+= ("<div class= \"proddiv\"><img class= \"prodimg\" src = \"/Images/");
			html+= (abc[i]['Company']+"-"+abc[i]['Type']+"-"+abc[i]['Name']+".jpg\"/>");
			html+= ("<span class=\"pricetag\">"+abc[i]['Price']+"$</span><h2 class= \"prodhead\">"+abc[i]['Name']+"</h2><ul class= \"prodtags\"><p>");
			html+= (abc[i]['Company']+"</p><p>"+abc[i]['Type']+"</p></ul><p class= \"info\">");
			html+= (abc[i]['Info']+"</p></div><div class = \"prodtagscart datetime\">"+abc[i]['Date']+"</div><hr/>");
		} 
	} else if (arg == 2) {
		for(let i = usrbuy.length-1; i >= 0 ; i--) {
			html+= ("<div class= \"proddiv\"><img class= \"prodimg\" src = \"/Images/");
			html+= (abc[i]['Company']+"-"+abc[i]['Type']+"-"+abc[i]['Name']+".jpg\"/>");
			html+= ("<span class=\"pricetag\">"+abc[i]['Price']+"$</span><h2 class= \"prodhead\">"+abc[i]['Name']+"</h2><ul class= \"prodtags\"><p>");
			html+= (abc[i]['Company']+"</p><p>"+abc[i]['Type']+"</p></ul><p class= \"info\">");
			html+= (abc[i]['Info']+"</p></div><div class = \"prodtagscart datetime\">"+abc[i]['Date']+"</div><hr/>");
		}
	}
	//html+="</div>";
	document.getElementById("site").innerHTML = html;
}

function byDate(arg) {
	abc = usrbuy.slice();
	loadelems(arg);
	abc = usrbuy.slice();
}

function byPrice(arg) {
	abc = usrbuy.slice();
	//Asc
	if(arg == 1) {
		abc.sort(function(a, b) {
			return a['Price'] - b['Price'];
		});
	//Desc
	} else if (arg == 2) {
		abc.sort(function(a, b) {
			return b['Price'] - a['Price'];
		});
	}
	//console.log(abc);
	loadelems(1);
	abc = usrbuy.slice();
}


window.onload = loadelems(1);
window.onload = loaddets();
</script>
<Style>
#usrName1 {
	background-color: crimson;
	font-weight: 800;
}
</Style>
</head>
</html>