// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   transServlet.java

import java.io.*;
import java.sql.*;
import java.util.Properties;

import javax.servlet.http.*;

public class transServlet extends HttpServlet
{
	Properties _properties;
	String _dbhost;
	String _dbname;
	String _dbuser;
	String _dbpassword;

    public transServlet()
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
        
        // 2010-09-15: Added by Ridvan Baluyos
        _properties = new Properties();
        try
        {        	
        	_properties.load(this.getClass().getClassLoader().getResourceAsStream("../../lib/pos.properties"));
        	_dbhost = _properties.getProperty("DBHOST");
        	_dbname = _properties.getProperty("DBNAME");
        	_dbuser = _properties.getProperty("DBUSER");
        	_dbpassword = _properties.getProperty("DBPASSWORD");     	
        }
        catch (IOException e)
        {
        	e.printStackTrace();
        }
        
        try
        {
            //Connection connection = DriverManager.getConnection("jdbc:mysql://bizdb.globequest.com.ph/prepaidbiz", "fortknox", "f0rtkn0x");
        	Connection connection = DriverManager.getConnection("jdbc:mysql://" + _dbhost + "/" + _dbname, _dbuser, _dbpassword);
            Statement statement = connection.createStatement();
            String s2 = "SELECT wcrealm, batchnum, serialnum, currency, cardvalue, username, password, accttype, sold_by, sold_date, allocated_time, allocated_days, date_shelfexpiry FROM raduser WHERE reseller_id='" + realm + "' && sold_transact_no = '" + as[1].trim() + "'";
            System.out.println(s2);
            ResultSet resultset = statement.executeQuery(s2);
            if(resultset.next())
            {
                String s3 = resultset.getString("wcrealm");
                String s4 = resultset.getString("batchnum");
                String s5 = resultset.getString("serialnum");
                String s6 = resultset.getString("currency");
                String s7 = resultset.getString("cardvalue");
                String s8 = resultset.getString("username");
                String s9 = resultset.getString("password");
                String s10 = resultset.getString("accttype");
                if(s10.trim().compareTo("P") == 0)
                {
                    String s11 = resultset.getString("allocated_time");
                    parse_allocation = Integer.parseInt(s11);
                    total = parse_allocation / 3600;
                    if(total < 24)
                    {
                        expiry = String.valueOf(total) + " hr/s";
                    } else
                    {
                        total = total / 24;
                        expiry = String.valueOf(total) + " day/s";
                    }
                } else
                if(s10.trim().compareTo("L") == 0)
                    expiry = resultset.getString("allocated_days") + " day/s";
                String s12 = resultset.getString("date_shelfexpiry");
                date_shelf_format = s12.replaceAll("-", "");
                date_shelf_format = date_shelf_format.replaceAll(":", "");
                printwriter.println(s3 + ":");
                printwriter.println(s5 + ":");
                printwriter.println(s6 + " " + s7 + " " + expiry + ":");
                printwriter.println(as[1].trim() + ":");
                printwriter.println(s4 + ":");
                if(as[2].trim().compareTo("print") == 0)
                {
                    printwriter.println(s8 + ":");
                    printwriter.println(s9 + ":");
                }
                printwriter.println(expiry + ":");
                printwriter.println(date_shelf_format + ":");
                printwriter.println(resultset.getString("sold_by") + ":");
                printwriter.println(resultset.getString("sold_date") + ":");
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

    int parse_allocation;
    int total;
    String expiry;
    String date_shelf_format;
    String realm;
}
