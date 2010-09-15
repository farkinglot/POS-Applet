// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   sessionClass.java

import java.io.IOException;
import java.io.PrintStream;
import java.sql.*;
import java.util.Properties;

public class sessionClass
{
	Properties _properties;
	String _dbhost;
	String _dbname;
	String _dbuser;
	String _dbpassword;

    public sessionClass()
    {
        value = false;
    }

    public boolean checkSession(String s, String s1, String s2)
    {
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
            long l = System.currentTimeMillis() / 1000L;
            String s3 = "SELECT * from customersession where sessionid='" + s + "' && ipaddress='" + s2 + "' && username='" + s1 + "'";
            ResultSet resultset = statement.executeQuery(s3);
            if(resultset.next())
            {
                long l1 = Long.parseLong(resultset.getString("lastused"));
                if(l - l1 <= 900L)
                {
                    String s4 = "UPDATE customersession set lastused='" + l + "' where sessionid='" + s + "'";
                    statement.executeUpdate(s4);
                    value = true;
                } else
                {
                    value = false;
                }
            } else
            {
                value = false;
            }
            resultset.close();
            statement.close();
            connection.close();
        }
        catch(SQLException sqlexception)
        {
            System.err.println("SQLException: " + sqlexception.getMessage());
        }
        return value;
    }

    boolean value;
}
