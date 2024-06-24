<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<link rel="stylesheet" href="Styles/Home.css">
<link rel="stylesheet"
  href="https://use.fontawesome.com/releases/v5.8.2/css/all.css"
  integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" 
  crossorigin="anonymous"
 />
<html>
<body>
<div class="navbar">
	<h1 id="logo">Sporty Shoes</h1>
	<ul class="navul">
		<a href="/">Home</a>
		<a href="/CartManager">Cart<img src="/Images/Cart_Icon.png" class="imgs"></a>
		<!-- <a id="usrName1" href="/LoginController">Login</a>
		<a id="usrName2" href="#">Login</a>
		<div id="loginopt">
			<a href="/AccountDetails">My Account</a>
			<a href="/SignOut">Signout</a>
		</div>-->
		<a id="usrName1" href="/SignOut">SignOut</a>
	</ul>
</div>

<div class = "firstelem"></div>
<div class="searchbar"><input class ="forminput" id="searchName" name="searchName" type="text"><button class= "formbutton" onclick="searchUser(document.getElementById('searchName').value)">Search</button></div>
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
<title>SportyShoes- Admin</title>
<style>
#usrName1 {
	background-color: crimson;
	font-weight: 800;
}

.innard {
	font-siz: 24px;
}

.usrHead {
	padding: 5px;
	background-color: dodgerblue;
	font-size: 38px;
}

.usrHead:hover~.usrInfo, .usrHead:focus~.usrInfo, .usrHead:active~.usrInfo 
{
	display: block;
}

.usrInfo:hover, .usrInfo:focus
{
	display: block;
}

.usrInfo {display: none;}

.page {
	margin-bottom: 20px;
}

.searchbar {
	width: 75%%;
	margin: 0 12.5%;
	padding: 20px 0;
	text-align: center;
	background-color: var(--color-accent-light-gray);
}
</style>
<script>
const users1 = ${users};
let users= users1;
//console.log(users1);

function allUsersFunc() {
	let html = "";
	for(let usr in users) {
		html+="<div class = \"page\"><a href=\"#\" class=\"usrHead\">"+users[usr]['Name']+"</a><div class=\"usrInfo\"><table><tr><td>Number</td><td>"+users[usr]['Number']+"</td></tr><tr><td>Country</td><td>"+users[usr]['Country']+"</td></tr><tr><td>Zip Code</td><td>"+users[usr]['Zip Code']+"</td></tr></table><a href=\"#\" class = \"usrHead\" style = \"font-size: 24px;\">Buy History</a>";
		if (users[usr]['Prods'] != null) {
		let abc = users[usr]['Prods'];
		for(let i = 0; i < abc.length; i++) {
			html+= ("<div class=\"usrInfo\"><div class= \"proddiv\"><img class= \"prodimg\" src = \"/Images/");
			html+= (abc[i]['Company']+"-"+abc[i]['Type']+"-"+abc[i]['Name']+".jpg\"/>");
			html+= ("<span class=\"pricetag\">"+abc[i]['Price']+"$</span><h2 class= \"prodhead\">"+abc[i]['Name']+"</h2><ul class= \"prodtags\"><p>");
			html+= (abc[i]['Company']+"</p><p>"+abc[i]['Type']+"</p></ul><p class= \"info\">");
			html+= (abc[i]['Info']+"</p></div><div class = \"prodtagscart datetime\">"+abc[i]['Date']+"</div><hr/></div>");
		}
		} else {html +="<h2 class=\"usrHead\">No Buy History</h2>";}
		html+="</div></div><br/><br/><hr/><hr/><br/><br/><br/>";
	}
	document.getElementById("site").innerHTML = html;
}

function searchUser(arg) {
	if(arg != '') {
		users = [];
		for(let i= 0; i < users1.length; i++) {
			if (users1[i]['Name'].includes(arg)) {
				users.push(users1[i]);
			}
		}
		allUsersFunc();
		users= users1;
	} else {return;}
}

window.onload = allUsersFunc();
</script>
</head>
</html>