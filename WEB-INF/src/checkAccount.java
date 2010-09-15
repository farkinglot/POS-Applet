// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   checkAccount.java

import java.io.*;
import java.sql.*;
import java.util.Properties;

import javax.servlet.http.*;

public class checkAccount extends HttpServlet
{
	Properties _properties;
	String _dbhost;
	String _dbname;
	String _dbuser;
	String _dbpassword;

    public checkAccount()
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
            String s2 = "SELECT * FROM raduser WHERE cardvalue='" + as[0].trim() + "' && reseller_id='" + realm + "' && batchstatus='A' && acctstatus='S' && prepaidtype='P' && sold_flag='N'";
            System.out.println(s2);
            ResultSet resultset = statement.executeQuery(s2);
            if(resultset.next())
                printwriter.println("available");
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

    String service_code;
    String realm;
}
