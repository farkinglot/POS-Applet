<%@page import = "java.sql.*" %>
<%
String sess = request.getParameter("sessid");
deleteSession(sess);
session.invalidate() ;
response.sendRedirect("login.jsp?msg=Cnf");
%>

<%!
String driver = "com.mysql.jdbc.Driver";
//String database = "jdbc:mysql://202.52.162.26/prepaidbiz";
//String access = "pbiz";
//String password = "prepaidbiz";
String database = "jdbc:mysql://172.16.2.190/prepaidbiz";
String access = "caddev";
String password = "c@dd3v";
private void deleteSession(String sessid) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
        Connection Conn = DriverManager.getConnection(database, access, password);
        Statement stmt = Conn.createStatement();
        String query = "DELETE from active_session where sessionid='"+sessid+"'";
        stmt.executeUpdate(query);
        stmt.close();
        Conn.close();
}
%>





