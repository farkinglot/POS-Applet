// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   sessionClass.java

import java.io.PrintStream;
import java.sql.*;

public class sessionClass
{

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
        try
        {
            //Connection connection = DriverManager.getConnection("jdbc:mysql://bizdb.globequest.com.ph/prepaidbiz", "fortknox", "f0rtkn0x");
        	Connection connection = DriverManager.getConnection("jdbc:mysql://172.16.2.190/prepaidbiz", "caddev", "c@dd3v");
            Statement statement = connection.createStatement();
            long l = System.currentTimeMillis() / 1000L;
            String s3 = "SELECT * from active_session where sessionid='" + s + "' && ipaddress='" + s2 + "' && username='" + s1 + "'";
            ResultSet resultset = statement.executeQuery(s3);
            if(resultset.next())
            {
                long l1 = Long.parseLong(resultset.getString("lastused"));
                if(l - l1 <= 900L)
                {
                    String s4 = "UPDATE active_session set lastused='" + l + "' where sessionid='" + s + "'";
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
