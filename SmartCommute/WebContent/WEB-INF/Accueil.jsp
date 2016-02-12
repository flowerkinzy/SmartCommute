<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Smart transport</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<META HTTP-EQUIV="Refresh" CONTENT="30">
		<!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/asserts/css/main.css" />
		
		<!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
		<!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
	</head>
	<body class="landing">
	
		<%@ page 	
			import="java.util.*"
			import="pwa.companycar.Car"
			import="pwa.sncf.Train"
			import="pwa.velib.Station"
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
						<span class="image"><img src="images/car.jpg" alt="Cars" /></span>
						<div class="content">
							<h2>Véhicules disponibles </h2>
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
					
					<%
		
		List<Station> Vstations=(List<Station>)request.getAttribute("listOfStations");
		%>
					<article class="feature right">
						<span class="image"><img src="images/velib.jpg" alt="Velibs" /></span>
						<div class="content">
							<h2>Points Vélib à proximité</h2>
							<ul>
								  <%
								  if(Vstations!=null){
								for(Station station:Vstations){
									out.write("<li>"+station+"</li>");
								}
								  }
							%>
								  

  								  
							</ul>							
						</div>
					</article>
			<%
		
		HashSet<Train> trains=(HashSet<Train>)request.getAttribute("nextTrains");
		%>		
					<article class="feature left">
						<span class="image"><img src="images/sncf.jpg" alt="SNCF" /></span>
						<div class="content">
							<h2>SNCF </h2>
							<ul>
							    <%
							    if(trains!=null){
									for(Train train:trains){
										out.write("<li>"+train+"</li>");
									}
							    }
							    else{
							    	out.write("<p>Aucune information</p>");
							    }
							    
								%>
  								  
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
			<script src="/asserts/js/jquery.min.js"></script>
			<script src="/asserts/js/jquery.min.js"></script>
			<script src="/asserts/js/skel.min.js"></script>
			<script src="/asserts/js/util.js"></script>
			<!--[if lte IE 8]><script src="asserts/js/ie/respond.min.js"></script><![endif]-->
			<script src="asserts/js/main.js"></script>

	</body>
</html>