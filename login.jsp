<%@include file="../lib/projectMethod.jsp"%>
<% 
    java.util.Properties props = System.getProperties();
    String osValue = (String)props.get("os.name");
    String ip_address = request.getRemoteAddr();
    String browser = "";  //request.getHeader("USER-AGENT");
    String action = request.getParameter("action");
    String strRealm = request.getParameter("strrealm");
    String strUsername = request.getParameter("strusername");
    String strPassword = request.getParameter("strpassword");    
    String companyID = null;
    boolean isValidUser = false;
    boolean isAdmin = false;  

    if (strUsername != null && strPassword != null) 
    {
        strUsername = strUsername.replaceAll("\'", " ");
        strPassword = strPassword.replaceAll("\'", " ");
        strRealm = strRealm.replaceAll("\'", " ");
	
        try 
        {
            isValidUser = CheckLogin(strUsername, strPassword, strRealm, ip_address); 
        } 
        catch (Exception e) 
        {
            throw new ServletException(e);
        }

        if (true) 
        { 
	    	String userid = getUserId(strUsername, strPassword, strRealm);
	    	String sessionID = insertSession(userid,ip_address,strRealm);
	    	String reseller = getValue("wscustomer", "reseller_id", "wcrealm", strRealm);
	    	session.setAttribute("sessionId", sessionID);
		   	session.setAttribute("id",userid);
		   	session.setAttribute("name",strUsername);
		   	session.setAttribute("ip",ip_address);
		   	session.setAttribute("realm",strRealm);
			session.setAttribute("reseller",reseller);
	        response.sendRedirect("posHome.jsp?sessid="+sessionID);
        }
        else 
        {
            response.sendRedirect("login.jsp?msg=Err");   
            log_invalid_access(ip_address, strUsername, strPassword, browser, "login error", osValue);	
        }
    } 
    else 
    { 
	    //Validate the IP address of the client
	   boolean hasAccess = false;
	   hasAccess = CheckIpAccess(ip_address);
%>
	<html>
	<head>
	<title>::Globe Quest::</title>
	<link rel="stylesheet" href="./lib/style.css" type="text/css">
	</head>
	<body bgcolor="#ffffff">
<%
        if (hasAccess) 
        {
%>
		<center><img src="./images/top_banner.jpg" border='0'><center><br>	
		<form name='login' method='post' action='login.jsp'>
		<input type='hidden' name='action' value='log'>
		
		<table cellpadding='3' cellspacing='0' border='0' align='center'>
		<tr><td colspan='2' class='body' align='center'><b>POS Login</b></td></tr>
		<tr><td colspan='2' class='error' align='center'>
<% 
            String msg = request.getParameter("msg");			
			if (msg != null) 
            {
				if (msg.compareTo("Err") == 0) 
                {
					out.println("Login Error");
				} 
                else if (msg.compareTo("Cnf") == 0) 
                {
					out.println("Logout Successfully");
				} 
                else if (msg.compareTo("Plogin") == 0) 
                {
					out.println("Please login");
				} 
                else if (msg.compareTo("Dbl") == 0) 
                {
					out.println("Account in use");	
				} 
                else if(msg.compareTo("InvSess") == 0) 
                {
					out.println("Invalid Session");	
				} 
                else if (msg.compareTo("Coe") == 0) 
                {
					out.println("Company name invalid");	
				}
        } 
        else 
        {
				out.println("<br>");	
		}
%>
		</td></tr>
		<tr><td class='body'>Realm ID</td><td><input type='text' name='strrealm' value='' size='15'></td></tr>
		<tr><td class='body'>Username</td><td><input type='text' name='strusername' value='' size='15'></td></tr>
		<tr><td class='body'>Password</td><td><input type='password' name='strpassword' value='' size='15'></td></tr>
		<tr><td colspan='2' align='center'><input type='submit' value='Login'></td></tr>
		</table>	
		</form>
<%
	} 
    else 
    {
		log_invalid_access(ip_address, strUsername, strPassword, browser, "IP invalid",osValue);	
	}
%>	
	<br>	
	<table cellpadding='1' cellspacing='0' border='0' width='60%' align='center' bgcolor='#990000'>
	<tr><td class='bodyw' align='center' height='25'><b>Innove Communication Legal Notice</b>
		<table cellpadding='2' cellspacing='1' border='0' width='100%' bgcolor='#ffffff'>
		<tr><td class='error'>WARNING: Only authorized users are allowed to access this system<br><br>The programs and data stored in this system are licensed, private property of Innove Communication. All login attemps, access and system activities are recorded and verified. If you are not an authorized user<br><br><center>Do not Attempt to Login</center><br></td></tr>
		</table>
	</td></tr>
	</table>	
	</body>
	</html>
<% 
} 
%>