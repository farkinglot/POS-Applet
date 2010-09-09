<%@include file="./lib/checkSession.jsp" %>
<html>
	<head>
	<title>::Globe Quest::</title>
	<link rel="stylesheet" href="./lib/style.css" type="text/css"> 
	</head>
	<body>
	<center>
	<center><img src="./images/top_banner.jpg" border='0'><center><br>	
	<APPLET NAME="posApplet" CODE="posApplet.class" CODEBASE="/oakwood/applet/" WIDTH="590" HEIGHT="450">
	<PARAM NAME="sessid" VALUE="<%=request.getParameter("sessid")%>">
	</APPLET>
	<!--
	java plug-in
	http://java.sun.com/j2se/1.4.2/download.html
	-->
	</center>
	<br><br>
	<%@include file="./lib/footer.htm"%>
	</body>
	</html>
