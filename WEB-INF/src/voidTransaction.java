// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   voidTransaction.java

import java.io.*;
import java.sql.*;

import javax.servlet.http.*;

public class voidTransaction extends HttpServlet
{

    public voidTransaction()
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
        ip_add = (String)httpsession.getAttribute("ip");
        name = (String)httpsession.getAttribute("name");
        wcrealm = (String)httpsession.getAttribute("realm");
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
            String s2 = "SELECT username,wcrealm,batchstatus,acctstatus,unix_timestamp(sold_date) FROM raduser where wcrealm='" + wcrealm + "' && sold_transact_no='" + as[1].trim() + "' && sold_flag='Y'";
            ResultSet resultset = statement.executeQuery(s2);
            if(resultset.next())
            {
                username = resultset.getString("username");
                realm = resultset.getString("wcrealm");
                acctstatus = resultset.getString("acctstatus");
                batchstatus = resultset.getString("batchstatus");
                dateSold = resultset.getString("unix_timestamp(sold_date)");
                time = System.currentTimeMillis() / 1000L;
                lastused = Long.parseLong(dateSold);
                expiration = Long.parseLong(as[2].trim());
                if(batchstatus.compareTo("A") == 0)
                {
                    if(acctstatus.compareTo("U") == 0 && batchstatus.compareTo("A") == 0)
                    {
                        if(time - lastused <= expiration)
                            flag = "1";
                        else
                            flag = "3";
                    } else
                    {
                        flag = "2";
                    }
                } else
                {
                    flag = "5";
                }
                result = flag;
            } else
            {
                result = "0";
            }
            resultset.close();
            if(result.compareTo("1") == 0)
            {
                String s3 = "SELECT a.* FROM online as a INNER JOIN raduser as b on a.username=b.username WHERE b.wcrealm='" + realm + "' && b.username='" + username + "' && a.wcrealm='" + realm + "' && sold_transact_no='" + as[1].trim() + "'";
                ResultSet resultset1 = statement.executeQuery(s3);
                if(resultset1.next())
                {
                    printwriter.println("1");
                    statement.executeUpdate("INSERT INTO pos_log (transaction_time,ip_address,wcrealm,user,action_taken,status) VALUES (now(),'" + ip_add + "','" + wcrealm + "','" + name + "','void account " + as[1].trim() + " failed-user is online','Failed') ");
                } else
                {
                    printwriter.println("2");
                }
                resultset1.close();
            } else
            if(result.compareTo("3") == 0)
            {
                printwriter.println("3");
                statement.executeUpdate("INSERT INTO pos_log (transaction_time,ip_address,wcrealm,user,action_taken,status) VALUES (now(),'" + ip_add + "','" + wcrealm + "','" + name + "','void account " + as[1].trim() + " failed-time expired','Failed' )");
            } else
            if(result.compareTo("2") == 0)
            {
                printwriter.println("4");
                statement.executeUpdate("INSERT INTO pos_log (transaction_time,ip_address,wcrealm,user,action_taken,status) VALUES (now(),'" + ip_add + "','" + wcrealm + "','" + name + "','void account " + as[1].trim() + " failed-account already used','Failed' )");
            } else
            if(result.compareTo("5") == 0)
            {
                printwriter.println("5");
                statement.executeUpdate("INSERT INTO pos_log (transaction_time,ip_address,wcrealm,user,action_taken,status) VALUES (now(),'" + ip_add + "','" + wcrealm + "','" + name + "','void account " + as[1].trim() + "failed-batchstatus not active','Failed') ");
            } else
            {
                printwriter.println("0");
                statement.executeUpdate("INSERT INTO pos_log (transaction_time,ip_address,wcrealm,user,action_taken,status) VALUES (now(),'" + ip_add + "','" + wcrealm + "','" + name + "','void account " + as[1].trim() + " failed-invalid transaction number','Failed') ");
            }
            statement.close();
            connection.close();
        }
        catch(SQLException sqlexception)
        {
            System.err.println("SQLException: " + sqlexception.getMessage());
        }
        printwriter.close();
    }

    String username;
    String realm;
    String result;
    String dateSold;
    String flag;
    String acctstatus;
    String batchstatus;
    long time;
    long lastused;
    long expiration;
    String ip_add;
    String name;
    String wcrealm;
}
