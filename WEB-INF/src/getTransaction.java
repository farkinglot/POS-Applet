// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   getTransaction.java

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.servlet.http.*;

public class getTransaction extends HttpServlet
{
	Properties _properties;
	String _dbhost;
	String _dbname;
	String _dbuser;
	String _dbpassword;

    public getTransaction()
    {
        parse_allocation = 0;
        total = 0;
        trans_count = 0;
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
        realm = (String)httpsession.getAttribute("realm");
        name = (String)httpsession.getAttribute("name");
        reseller = (String)httpsession.getAttribute("reseller");
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
            String s4 = "SELECT counter FROM transaction_counter WHERE transtime=curdate()";
            ResultSet resultset = statement.executeQuery(s4);
            if(resultset.next())
            {
                String s5 = resultset.getString("counter");
                trans_count = Integer.parseInt(s5) + 1;
            } else
            {
                trans_count = 1;
            }
            String s6 = format("yyyyMMdd");
            String s7 = "0000000000";
            String s8 = String.valueOf(trans_count);
            int i = s7.length();
            int j = s8.length();
            int k = i - j;
            transaction_number = replaceCharAt(s7, k, s8);
            transaction_no = s6 + transaction_number;
            statement.executeUpdate("UPDATE transaction_counter SET counter='" + trans_count + "',transtime=curdate()");
            resultset.close();
            String s9 = "SELECT batchnum, batchstatus, serialnum, currency, cardvalue, username, password, accttype, allocated_time, allocated_days, date_add(now(), interval shelf_days day) AS compdate FROM raduser WHERE cardvalue='" + as[0].trim() + "' && batchstatus='A' && acctstatus='S' && prepaidtype='P' && sold_flag='N' && reseller_id='" + realm + "' ORDER BY date_created, serialnum LIMIT 1";
            System.out.println(s9);
            ResultSet resultset1 = statement.executeQuery(s9);
            if(resultset1.next())
            {
                String s10 = resultset1.getString("batchstatus");
                String s11 = resultset1.getString("batchnum");
                String s12 = resultset1.getString("serialnum");
                String s13 = resultset1.getString("currency");
                String s14 = resultset1.getString("cardvalue");
                String s15 = resultset1.getString("username");
                String s16 = resultset1.getString("password");
                String s17 = resultset1.getString("accttype");
                if(s17.trim().compareTo("P") == 0)
                {
                    String s18 = resultset1.getString("allocated_time");
                    parse_allocation = Integer.parseInt(s18);
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
                if(s17.trim().compareTo("L") == 0)
                    expiry = resultset1.getString("allocated_days") + " day/s";
                String s19 = resultset1.getString("compdate");
                date_shelf_format = s19.replaceAll("-", "");
                date_shelf_format = date_shelf_format.replaceAll(":", "");
                String s20 = "UPDATE raduser set sold_transact_no='" + transaction_no + "', sold_by='" + name + "', sold_date='" + s + "', sold_ipaddress='" + ip_add + "', sold_flag='Y', acctstatus='U', date_shelfexpiry='" + s19 + "'  where serialnum='" + s12 + "' && username='" + s15 + "' && wcrealm='" + realm + "' && password='" + s16 + "'";
                statement.executeUpdate(s20);
                String s21 = "INSERT INTO salesreport (trans_dt, trans_no, trans_type, acct_type, trans_realm, acct_batchno, batch_status, acct_serialnum, acct_userid, acct_status, trans_currency, trans_value, trans_by, trans_ip, reseller_id) VALUES ('" + s1 + "', '" + transaction_no + "', 'POS_SALES', '" + s17 + "', '" + realm + "', '" + s11 + "', '" + s10 + "', '" + s12 + "', '" + s15 + "', 'U', '" + s13 + "', '" + s14 + "', '" + name + "', '" + ip_add + "', '" + reseller + "')";
                statement.executeUpdate(s21);
                printwriter.println(realm + ":");
                printwriter.println(s12 + ":");
                printwriter.println(s13 + " " + s14 + " " + expiry + ":");
                printwriter.println(transaction_no + ":");
                printwriter.println(s11 + ":");
                if(as[1].trim().compareTo("print") == 0)
                {
                    printwriter.println(s15 + ":");
                    printwriter.println(s16 + ":");
                }
                printwriter.println(expiry + ":");
                printwriter.println(date_shelf_format + ":");
                printwriter.println(name + ":");
                printwriter.println(s + ":");
                statement.executeUpdate("INSERT INTO poslog (transtd,ipaddress,wcrealm,user,actiontaken,status) VALUES (now(),'" + ip_add + "','" + realm + "','" + name + "','sell account-transaction #" + transaction_no + "','ok')");
            } else
            {
                statement.executeUpdate("INSERT INTO poslog (transtd,ipaddress,wcrealm,user,actiontaken,status) VALUES (now(),'" + ip_add + "','" + realm + "','" + name + "','sell account failed','Failed')");
            }
            resultset1.close();
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

    public String replaceCharAt(String s, int i, String s1)
    {
        return s.substring(0, i) + s1;
    }

    String userid;
    String ip_add;
    String realm;
    String name;
    String transaction_number;
    String reseller;
    int parse_allocation;
    int total;
    int trans_count;
    String expiry;
    String date_shelf_format;
    String transaction_no;
    String ctr;
}
