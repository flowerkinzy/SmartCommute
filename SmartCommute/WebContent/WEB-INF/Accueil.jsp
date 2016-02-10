<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Smart transport</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/asserts/css/main.css" />
		
		<!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
		<!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
	</head>
	<body class="landing">
	
		<%@ page 	
			import="java.util.*"
			import="pwa.companycar.Car"
			
		 %>

		<!-- Header -->
			<header id="header" class="alt">
			</header>


		<!-- Banner -->
			<section id="banner">
				<i class="icon fa-diamond"></i>
				<h2>RymFlora entreprise</h2>
				
			</section>

		<!-- One -->
		<%
		
		List<Car> cars=(List<Car>)request.getAttribute("availablecars");
		%>
			<section id="one" class="wrapper style1">
				<div class="inner">
					<article class="feature left">
						<span class="image"><img src="images/car.jpg" alt="" /></span>
						<div class="content">
							<h2>Available Cars </h2>
							<ul>
							<%
								for(Car car:cars){
									out.write("<li>"+car+"</li>");
								}
							%>
								<!--<li>Peugeot 206+ bleue</li>
  								  <li>BMW M6 noire</li>
  								  <li></li>-->
							</ul>
							
						</div>
					</article>
					<article class="feature right">
						<span class="image"><img src="images/velib.jpg" alt="" /></span>
						<div class="content">
							<h2>Nierest Velib Stations</h2>
							<ul>
								  <li>Sation XX 
								  	<ul>
								  		<li>Etat</li>
  								  		<li>Nom</li>
  								  		<li>adresse</li>
  								  		<li>Station bonus</li>
  								  		<li>Etat du terminal de paiement par carte</li>
									</ul>
								  
								  </li>
								  <li>Sation YY 
								  	<ul>
								  		<li>Etat</li>
  								  		<li>Nom</li>
  								  		<li>adresse</li>
  								  		<li>Station bonus</li>
  								  		<li>Etat du terminal de paiement par carte</li>
									</ul>
								  
							      </li>
  								  
							</ul>							
						</div>
					</article>
					
					<article class="feature left">
						<span class="image"><img src="images/sncf.jpg" alt="" /></span>
						<div class="content">
							<h2>SNCF </h2>
							<ul>
								  <li>Nom du train1--terminus--Datedepart</li>
  								  <li>Nom du train2--terminus--Datedepart</li>
  								  
							</ul>							
						</div>
					</article>
				</div>
			</section>

		
		<!-- Footer -->
			<footer id="footer">
				<div class="inner">
					
				</div>
			</footer>

		<!-- Scripts -->
			<script src="assets/js/jquery.min.js"></script>
			<script src="assets/js/skel.min.js"></script>
			<script src="assets/js/util.js"></script>
			<!--[if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif]-->
			<script src="assets/js/main.js"></script>

	</body>
</html>