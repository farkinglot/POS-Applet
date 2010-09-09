// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   getBrand.java

import java.io.*;
import java.sql.*;
import javax.servlet.http.*;

public class getBrand extends HttpServlet
{

    public getBrand()
    {
        parse_allocation = 0;
        total = 0;
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
            String s2 = "SELECT distinct accounttype, allocated_time, allocated_days, currency, cardamount FROM ratetable WHERE wcrealm='" + realm + "' && enabled='Y' ORDER BY cardamount ASC";
            ResultSet resultset;
            String s4;
            for(resultset = statement.executeQuery(s2); resultset.next(); printwriter.println(resultset.getString("cardamount") + " " + s4 + " (" + expiry + ")" + ":"))
            {
                db_accttype = resultset.getString("accounttype");
                if(db_accttype.trim().compareTo("P") == 0)
                {
                    String s3 = resultset.getString("allocated_time");
                    parse_allocation = Integer.parseInt(s3);
                    total = parse_allocation / 3600;
                    if(total < 24)
                    {
                        expiry = String.valueOf(total) + "hr/s";
                    } else
                    {
                        total = total / 24;
                        expiry = String.valueOf(total) + "day/s";
                    }
                } else
                if(db_accttype.trim().compareTo("L") == 0)
                    expiry = resultset.getString("allocated_days") + "day/s";
                s4 = resultset.getString("currency");
            }

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

    String realm;
    String expiry;
    String db_accttype;
    int parse_allocation;
    int total;
}
