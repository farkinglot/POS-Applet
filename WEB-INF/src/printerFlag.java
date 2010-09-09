// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   printerFlag.java

import java.io.*;
import java.sql.*;

import javax.servlet.http.*;

public class printerFlag extends HttpServlet
{

    public printerFlag()
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
        name = (String)httpsession.getAttribute("name");
        realm = (String)httpsession.getAttribute("realm");
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
        String as[] = s1.split(":");
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
            String s2 = "";
            if(as[1].trim().compareTo("1") == 0)
            {
                s2 = "UPDATE raduser SET printed_flag='Y' where sold_transact_no='" + as[0].trim() + "' && sold_flag='Y'";
                String s3 = "INSERT INTO pos_log (transaction_time,ip_address,wcrealm,user,action_taken,status) VALUES (now(),'" + ip_add + "','" + realm + "','" + name + "','print sold transaction " + as[0].trim() + "','ok') ";
                statement.executeUpdate(s3);
            } else
            if(as[1].trim().compareTo("2") == 0)
                s2 = "INSERT INTO pos_log (transaction_time,ip_address,wcrealm,user,action_taken,status) VALUES (now(),'" + ip_add + "','" + realm + "','" + name + "','reprint transaction " + as[0].trim() + "','ok') ";
            statement.executeUpdate(s2);
            statement.close();
            connection.close();
        }
        catch(SQLException sqlexception)
        {
            System.err.println("SQLException: " + sqlexception.getMessage());
        }
        printwriter.close();
    }

    String service_code;
    String userid;
    String ip_add;
    String name;
    String realm;
}
