<%@include file="../lib/projectMethod.jsp" %>
<% 	//check session
String ip_address = request.getRemoteAddr();
String browser = ""; //request.getHeader("USER-AGENT");
java.util.Properties props = System.getProperties();
String osValue = (String)props.get("os.name");

    if (session.getAttribute("sessionId") == null) {
        response.sendRedirect("login.jsp?msg=Plogin");
        log_invalid_access(ip_address, "", "", browser, "Did not login",osValue);	
    } else {
        String sessid = session.getAttribute("sessionId").toString();
        boolean bLoggedIn = checkSession(sessid,ip_address);
        if (!bLoggedIn) {
            response.sendRedirect("login.jsp?msg=InvSess");
            log_invalid_access(ip_address, "", "", browser, "Invalid Session",osValue);	
        }
    }
%>