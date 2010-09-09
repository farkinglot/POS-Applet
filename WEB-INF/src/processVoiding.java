// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   processVoiding.java

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.*;

public class processVoiding extends HttpServlet
{

    public processVoiding()
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
        String s = format("yyyyMMdd HHmmss");
        String s1 = format("yyyy-MM-dd HH:mm:ss");
        HttpSession httpsession = httpservletrequest.getSession();
        userid = (String)httpsession.getAttribute("id");
        ip_add = (String)httpsession.getAttribute("ip");
        name = (String)httpsession.getAttribute("name");
        wcrealm = (String)httpsession.getAttribute("realm");
        StringBuffer stringbuffer = new StringBuffer();
        BufferedReader bufferedreader = httpservletrequest.getReader();
        String s2;
        while((s2 = bufferedreader.readLine()) != null) 
        {
            if(stringbuffer.length() > 0)
                stringbuffer.append('\n');
            stringbuffer.append(s2);
        }
        bufferedreader.close();
        httpservletresponse.setContentType("text/plain");
        String s3 = stringbuffer.toString().trim();
        String as[] = s3.split(":");
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
            String s4 = "SELECT *, unix_timestamp(sold_date) FROM raduser where wcrealm='" + wcrealm + "' && sold_transact_no='" + as[0].trim() + "' && sold_flag='Y'";
            ResultSet resultset = statement.executeQuery(s4);
            if(resultset.next())
            {
                username = resultset.getString("username");
                realm = resultset.getString("wcrealm");
                acctstatus = resultset.getString("acctstatus");
                dateSold = resultset.getString("unix_timestamp(sold_date)");
                time = System.currentTimeMillis() / 1000L;
                lastused = Long.parseLong(dateSold);
                expiration = Long.parseLong(as[1].trim());
                if(acctstatus.compareTo("U") == 0)
                {
                    if(time - lastused <= expiration)
                        flag = "1";
                    else
                        flag = "3";
                } else
                {
                    flag = "2";
                }
                result = flag;
            } else
            {
                result = "0";
            }
            resultset.close();
            if(result.compareTo("1") == 0)
            {
                String s5 = "SELECT a.* FROM online as a INNER JOIN raduser as b on a.username=b.username WHERE b.wcrealm='" + realm + "' && b.username='" + username + "' && a.wcrealm='" + realm + "' && b.sold_transact_no='" + as[0].trim() + "'";
                ResultSet resultset1 = statement.executeQuery(s5);
                if(resultset1.next())
                {
                    printwriter.println("1");
                    statement.executeUpdate("INSERT INTO pos_log (transaction_time,ip_address,wcrealm,user,action_taken,status) VALUES (now(),'" + ip_add + "','" + wcrealm + "','" + name + "','void account " + as[0].trim() + " failed-user is online','Failed') ");
                } else
                {
                    statement.executeUpdate("UPDATE raduser SET acctstatus='V',sold_flag='V', void_ipaddress='" + ip_add + "', voided_by='" + userid + "',void_date='" + s + "',voidreason='" + as[2].trim() + "' where sold_transact_no='" + as[0].trim() + "' && sold_flag='Y' && username='" + username + "'");
                    statement.executeUpdate("UPDATE sales_log SET trans_dt='" + s1 + "',trans_type='POS_VOID', acct_status='V', trans_by='" + name + "', trans_ip='" + ip_add + "', remarks='" + as[2].trim() + "' where trans_no='" + as[0].trim() + "' && acct_userid='" + username + "' && trans_realm='" + realm + "'");
                    statement.executeUpdate("INSERT INTO pos_log (transaction_time,ip_address,wcrealm,user,action_taken,status) VALUES (now(),'" + ip_add + "','" + wcrealm + "','" + name + "','void account " + as[0].trim() + "','ok' )");
                    printwriter.println("2");
                }
                resultset1.close();
            } else
            if(result.compareTo("2") == 0)
            {
                printwriter.println("4");
                statement.executeUpdate("INSERT INTO pos_log (transaction_time,ip_address,wcrealm,user,action_taken,status) VALUES (now(),'" + ip_add + "','" + wcrealm + "','" + name + "','void account " + as[0].trim() + " failed-account already used','Failed' )");
            } else
            if(result.compareTo("3") == 0)
            {
                printwriter.println("3");
                statement.executeUpdate("INSERT INTO pos_log (transaction_time,ip_address,wcrealm,user,action_taken,status) VALUES (now(),'" + ip_add + "','" + wcrealm + "','" + name + "','void account " + as[0].trim() + " failed-time expired','Failed' )");
            } else
            {
                printwriter.println("0");
                statement.executeUpdate("INSERT INTO pos_log (transaction_time,ip_address,wcrealm,user,action_taken,status) VALUES (now(),'" + ip_add + "','" + wcrealm + "','" + name + "','void account " + as[0].trim() + " failed-invalid transaction number','Failed') ");
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

    public String format(String s)
    {
        String s1 = "";
        Date date = new Date();
        SimpleDateFormat simpledateformat = new SimpleDateFormat(s);
        try
        {
            s1 = simpledateformat.format(date);
        }
        catch(IllegalArgumentException illegalargumentexception) { }
        return s1;
    }

    String username;
    String realm;
    String dateSold;
    String result;
    String flag;
    String userid;
    String ip_add;
    String acctstatus;
    String name;
    String wcrealm;
    long time;
    long lastused;
    long expiration;
}
