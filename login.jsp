<%@include file="../lib/projectMethod.jsp"%>
<%
    java.util.Properties props = System.getProperties();
    String osValue = (String)props.get("os.name");
    String ipAddress = request.getRemoteAddr();
    String browser = "";  //request.getHeader("USER-AGENT");
    String action = request.getParameter("action");
    String resellerId = request.getParameter("resellerid");
    String username = request.getParameter("username");
    String password = request.getParameter("password");    
    String companyID = null;
    boolean isValidUser = false;
    boolean isAdmin = false;  

    if (username != null && password != null) 
    {
        username = username.replaceAll("\'", " ");
        password = password.replaceAll("\'", " ");
        resellerId = resellerId.replaceAll("\'", " ");
    
        try 
        {
            isValidUser = CheckLogin(username, password, resellerId, ipAddress); 
        } 
        catch (Exception e) 
        {
            throw new ServletException(e);
        }

        if (isValidUser) 
        { 
            String userId = getUserId(username, password, resellerId);
            String sessionId = insertSession(userId, ipAddress, resellerId);
            String reseller = getValue("posuser", "username", "reseller_id", resellerId);
            session.setAttribute("sessionId", sessionId);
            session.setAttribute("id", userId);
            session.setAttribute("name", username);
            session.setAttribute("ip", ipAddress);
            session.setAttribute("realm", resellerId);
            session.setAttribute("reseller", reseller);
            response.sendRedirect("posHome.jsp?sessid="+sessionId);
        }
        else 
        {
            response.sendRedirect("login.jsp?msg=Err");   
            log_invalid_access(ipAddress, username, password, browser, "login error", osValue);  
        }
    } 
    else 
    { 
        //Validate the IP address of the client
       boolean hasAccess = false;
       hasAccess = CheckIpAccess(ipAddress);
       String msg = request.getParameter("msg");			
	   
        if (!hasAccess) 
        {
            log_invalid_access(ipAddress, username, password, browser, "IP invalid", osValue);
            out.println("Unauthorized Access.");
            System.exit(0);
        }
    } 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>WIZ POS Login</title>
<link type="text/css" rel="stylesheet" href="css/core_style.css">
<link type="text/css" rel="stylesheet" href="css/login.css">
<link type="text/css" rel="stylesheet" href="css/960_fluid/960_16_fluid.css">
<script type="text/javascript">
	if (navigator.userAgent.indexOf("Firefox")!=-1){
<!--		alert('Mozilla Firefox'); -->
	document.write('<link rel="stylesheet" href="css/styles_ff.css" type="text/css" media="all" />');
	}
	else if (navigator.userAgent.indexOf("MSIE")!=-1){
<!--		alert('Internet Explorer'); -->
	document.write('<link rel="stylesheet" href="css/styles_ie.css" type="text/css" media="all" />');
	}
	else if (navigator.userAgent.indexOf("Chrome")!=-1){
<!--		alert('Google Chrome'); -->
	document.write('<link rel="stylesheet" href="css/styles_ch.css" type="text/css" media="all" />');
	}
	else if(navigator.userAgent.indexOf("Safari")!=-1){
<!--		alert('Apple Safari'); -->
	document.write('<link rel="stylesheet" href="css/styles_sf.css" type="text/css" media="all" />');
	}
</script>
</head>
<body style="background: #FFFFFF url(img/APL_bkgd.jpg) repeat-x top">
<!--<div class="bg"><img src="css/960_fluid/guide.png" width="100%" height="100%" /></div> -->

<!--Sub Header Control-->
<!--960 grid - 16 columns-->
<div class="container_16">
<div class="grid_16"><br/><br/><br/><br/><br/></div>
<div class="grid_9">&nbsp;</div><div class="grid_7" align="left">

<!--<img src="img/apl_logo.png" width="302" height="100">-->
<h1>Wireless Internet Zone (WIZ) <br /> POS System</h1>

</div> 

<div class="clear"></div>
<div class="grid_9" align="right">
<img src="img/icon.png" width="458" height="407"></div>
<div class="grid_7">
    <div class="login_container"><br/><br/>
        <form name="login_form" method="post" action="login.jsp">
        <table width="200" border="0" cellspacing="1" class="login_box">
            <tr>
              <td width="150">Reseller ID</td>  
            </tr> 
            <tr>
              <td><input id="text_resellerid" name="resellerid" type="text" class="login_input" value="" ></td>
            </tr>
            <tr>
              <td>Username</td>
            </tr>
            <tr>
              <td><input id="text_username" name="username" type="text" class="login_input" value="" ></td>
            </tr>
        	<tr>
              <td>Password</td>
            </tr>
            <tr>
              <td><input id="text_password" name="password" type="password" class="login_input" value="" ></td>
            </tr>
            <tr>
              <td align="right"><br/><input id="btn_submit"  type="submit" class="button" value="Login"></td>
            </tr>
      </table>
      </form>
    </div>
    <div align="center">
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
    %>    
    </div>
</div>
<div class="grid_7">
<br/><br/><br/><br/><br/><br/><br/><br/><br/>
</div>
<div class="grid_7">
<img src="img/powered.png" width="167" height="51" style="margin-top: 50px; padding-left: 170px"></div>
<div class="clear"></div>
<!--Footer-->
<div class="grid_16" align="center"><br/>Copyright © Globe Telecoms, Inc. All rights reserved.<br/></div>  
</div>
</body>
</html>