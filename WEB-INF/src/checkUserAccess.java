// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   checkUserAccess.java

import java.io.*;
import java.sql.*;
import javax.servlet.http.*;

public class checkUserAccess extends HttpServlet
{

    public checkUserAccess()
    {
    }

    public void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws IOException
    {
        HttpSession httpsession = httpservletrequest.getSession();
        userid = (String)httpsession.getAttribute("id");
        realm = (String)httpsession.getAttribute("realm");
        httpservletresponse.setContentType("text/plain");
        PrintWriter printwriter = httpservletresponse.getWriter();
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            System.err.print("ClassNotFoundException: ");
            System.err.println(classnotfoundexception.getMessage());
        }
        try
        {
            //Connection connection = DriverManager.getConnection("jdbc:mysql://bizdb.globequest.com.ph/prepaidbiz", "fortknox", "f0rtkn0x");
        	Connection connection = DriverManager.getConnection("jdbc:mysql://172.16.2.190/prepaidbiz", "caddev", "c@dd3v");        	
            Statement statement = connection.createStatement();
            String s = "SELECT * from posuser where userid='" + userid + "' && wcrealm='" + realm + "'";
            ResultSet resultset = statement.executeQuery(s);
            if(resultset.next())
                access = resultset.getString("accesstype");
            resultset.close();
            statement.close();
            connection.close();
            if(access.compareTo("M") == 0 || access.compareTo("S") == 0)
                printwriter.println("1");
            else
                printwriter.println("0");
        }
        catch(SQLException sqlexception)
        {
            System.err.println("SQLException: " + sqlexception.getMessage());
        }
        printwriter.close();
    }

    String access;
    String userid;
    String realm;
}
