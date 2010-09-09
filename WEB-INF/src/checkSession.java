// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   checkSession.java

import java.io.*;
import java.sql.*;
import javax.servlet.http.*;

public class checkSession extends HttpServlet
{

    public checkSession()
    {
    }

    public void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws IOException
    {
        httpservletresponse.setContentType("text/plain");
        PrintWriter printwriter = httpservletresponse.getWriter();
        printwriter.println("Error: this servlet does not support the GET method!");
        printwriter.close();
    }

    public void doPost(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws IOException
    {
        HttpSession httpsession = httpservletrequest.getSession();
        userid = (String)httpsession.getAttribute("id");
        ip_add = (String)httpsession.getAttribute("ip");
        sess = (String)httpsession.getAttribute("sessionId");
        StringBuffer stringbuffer = new StringBuffer();
        BufferedReader bufferedreader = httpservletrequest.getReader();
        String s;
        while((s = bufferedreader.readLine()) != null) 
        {
            if(stringbuffer.length() > 0)
                stringbuffer.append('\n');
            stringbuffer.append(s);
        }
        bufferedreader.close();
        httpservletresponse.setContentType("text/plain");
        String s1 = stringbuffer.toString().trim();
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
            long l = System.currentTimeMillis() / 1000L;
            String s2 = "SELECT * from active_session where sessionid='" + sess + "' && ipaddress='" + ip_add + "' && username='" + userid + "'";
            ResultSet resultset = statement.executeQuery(s2);
            if(resultset.next())
            {
                long l1 = Long.parseLong(resultset.getString("lastused"));
                if(l - l1 <= 900L)
                {
                    String s3 = "UPDATE active_session set lastused='" + l + "' where sessionid='" + sess + "'";
                    statement.executeUpdate(s3);
                    value = "true";
                } else
                {
                    value = "false";
                }
            } else
            {
                value = "false";
            }
            printwriter.println(value);
            resultset.close();
            statement.close();
            connection.close();
        }
        catch(SQLException sqlexception)
        {
            System.err.println("SQLException: " + sqlexception.getMessage());
        }
        printwriter.close();
    }

    String userid;
    String ip_add;
    String value;
    String sess;
}
