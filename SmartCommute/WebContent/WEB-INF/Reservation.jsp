<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<html>
	<head>
		<title>Reservation de voitures</title>
		<meta name="viewport" content="width=device-width, initial-scale=1" charset="utf-8"/>
		<!--[if lte IE 8]><script src="${pageContext.servletContext.contextPath}/asserts/js/ie/html5shiv.js"></script><![endif]-->
		<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/asserts/css/main.css" />
		<!--[if lte IE 8]><link rel="stylesheet" href="${pageContext.servletContext.contextPath}/asserts/css/ie8.css" /><![endif]-->
		<!--[if lte IE 9]><link rel="stylesheet" href="${pageContext.servletContext.contextPath}/asserts/css/ie9.css" /><![endif]-->
	</head>
<body>
<td width="254" valign="top" style="padding-top:35px;">
<form id="form1" name="form1" method="post" action="booking">
<input type="hidden" name="action" value="listavailablecars">
              <table width="250" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td colspan="3" style="padding-bottom:15px;" class="wrapper style1" id="banner"><h2 align="center">Book your car</h2> </td>
                </tr>
                <tr>
                  <td width="89" height="25" class="body">First Name : </td>
				  <td height="25" colspan="2"><input type="text" name="firstname" style="width:155px;"></td>
				</tr>
				<tr>
                  <td width="89" height="25" class="body">Last Name : </td>
				  <td height="25" colspan="2"><input type="text" name="lastname" style="width:155px;"></td>
				</tr>
                 
                <tr>
                  <td height="25" class="body">Start Date : </td>
                  <td width="64" height="25"><input type="date" name="start" style="width:155px;" value="dd/MM/YYYY HH:mm"></td>
                </tr>
                <tr>
                  <td height="25" class="body">End Date : </td>
                  <td width="64" height="25"><input type="date" name="end" style="width:155px;" value="dd/MM/YYYY HH:mm" ></td>
                </tr>
                <tr>
                  <td width="89" height="25" class="body">Reason : </td>
				  <td height="25" colspan="2"><TEXTAREA name="nom" rows=4 cols=20 style="width:155px;">Why do you want to use this car?</TEXTAREA></td>
				</tr>
				
				<tr>
                  <td height="25" class="body">&nbsp;</td>
                  <td height="35" colspan="2" class="body">
				  <div align="right" style="padding-right:6px;">
                    <button onclick="return confirm('Are you sure you want to book this car ?');">book</button>
				  </div>
				  </td>
                </tr>
                       
			</table>
	 </form>
	</td>
</body>
</html>