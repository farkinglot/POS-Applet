// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   printTransaction.java

import java.awt.*;
import java.awt.print.*;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

public class printTransaction
    implements Printable
{
	Properties _properties;
	String _dbhost;
	String _dbname;
	String _dbuser;
	String _dbpassword;
	String _serverName;
	String _serverPath;
	String _projectName;
	
    public printTransaction(String s, String s1)
    {
        try
        {
        	_properties = new Properties();
        	_properties.load(this.getClass().getClassLoader().getResourceAsStream("../lib/pos.properties"));
        	_dbhost = _properties.getProperty("DBHOST");
        	_dbname = _properties.getProperty("DBNAME");
        	_dbuser = _properties.getProperty("DBUSER");
        	_projectName = _properties.getProperty("PROJECT_NAME");
        	_dbpassword = _properties.getProperty("DBPASSWORD");
        	_projectName = _properties.getProperty("PROJECT_NAME");
        	_serverName = _properties.getProperty("SERVER_NAME");
        	_serverPath = _properties.getProperty("SERVER_PATH");
        	
        }
        catch (IOException e)
        {
        	e.printStackTrace();
        }
        
        i = 0;
        counter = 0;
        gd = new getData();
        transaction = s;
        //String s2 = gd.getData("https://bizweb.globequest.com.ph:8443/pos/servlet/transServlet", "raduser", transaction, "print");
        String s2 = gd.getData(_serverPath + "/" + _serverName + "/transServlet", "raduser", transaction, "print");
        displayed = s2.split(":");
        log = s1;
        if(log.compareTo("A") == 0)
            //gd.getData("https://bizweb.globequest.com.ph:8443/pos/servlet/printerFlag", transaction, "1");
        	gd.getData(_serverPath + "/" + _serverName + "/printerFlag", transaction, "1");
        printIt();
    }

    private void printIt()
    {
        PrinterJob printerjob = PrinterJob.getPrinterJob();
        Paper paper = new Paper();
        PageFormat pageformat = printerjob.defaultPage();
        //paper.setSize(180D, 500D);
        //paper.setImageableArea(5D, 95D, 250D, 700D);
        paper.setSize(612, 792);
        paper.setImageableArea(0,0,612,792);          
        pageformat.setPaper(paper);
        printerjob.setPrintable(this, pageformat);
        try
        {
            //for(; i < 2; i++)
            //{
             //   print_switch = i;
                printerjob.print();
            //}

        }
        catch(PrinterException printerexception)
        {
            System.err.println(printerexception);
        }
        if(log.compareTo("A") != 0)
            //gd.getData("https://bizweb.globequest.com.ph:8443/pos/servlet/printerFlag", transaction, "2");
        	gd.getData(_serverPath + "/" + _serverName + "/printerFlag", transaction, "2");
    }

    public int print(Graphics g, PageFormat pageformat, int j)
        throws PrinterException
    {
    	
    	Font font = new Font("SansSerif", 0, 10);
        Font font1 = new Font("SansSerif", 0, 11);
		g.setColor(Color.black);
        g.setFont(font);
        if(j > 0)
            return 1;
        g.translate((int)pageformat.getImageableX(), (int)pageformat.getImageableY());
        
    	for(int i = 0; i < 2; i++)
    	{	
    		print_switch = i;    	

	        g.setFont(font);

	        if(i == 0)
	            counter = 75 + 36;
	        else
	            counter = 500 + 24;
	        
	        if(displayed.length > 0)
	        {
	            for(int k = 0; k <= 4; k++)
	            {
	                if(k == 4 && log.compareTo("B") == 0)
	                {
	                    String s1 = (new StringBuilder()).append(description[k]).append(" ").append(displayed[k].trim()).append(" (Duplicate)").toString();
	                    g.drawString(s1, 206, counter);
	                } else
	                {
	                    String s2 = (new StringBuilder()).append(description[k]).append(" ").append(displayed[k].trim()).toString();
	                    g.drawString(s2, 206, counter);
	                }
	                counter += 12;
	            }
	
	        }
	        counter = counter + 8;
	        if(displayed.length > 0)
	        {
	            for(int l = 7; l <= 8; l++)
	            {
	                String s3 = (new StringBuilder()).append(description[l]).append(" ").append(displayed[l].trim()).toString();
	                g.drawString(s3, 206, counter);
	                counter += 12;
	            }
	
	        }
	        
            g.setFont(font1);
            //String s = gd.getData("https://bizweb.globequest.com.ph:8443/pos/servlet/getValue", "wscustomer", "display_username", "wcrealm", displayed[0].trim());
            //String s4 = gd.getData("https://bizweb.globequest.com.ph:8443/pos/servlet/getValue", "wscustomer", "display_password", "wcrealm", displayed[0].trim());
            String s = gd.getData(_serverPath + "/" + _serverName + "/getValue", "wscustomer", "display_username", "wcrealm", displayed[0].trim());
            String s4 = gd.getData(_serverPath + "/" + _serverName + "/getValue", "wscustomer", "display_password", "wcrealm", displayed[0].trim());
	        
	        if(print_switch == 1)
	        {
	            if(s.trim().compareTo("Y") == 0 || s4.trim().compareTo("Y") == 0)
	            {
	                counter = counter + 8;
	                if(displayed.length > 0)
	                {
	                    if(s.trim().compareTo("Y") == 0)
	                    {
	                        String s8 = (new StringBuilder()).append(description[5]).append(" ").append(displayed[5].trim()).toString();
	                        g.drawString(s8, 206, counter);
	                        counter += 12;
	                    }
	                    if(s4.trim().compareTo("Y") == 0)
	                    {
	                        String s9 = (new StringBuilder()).append(description[6]).append(" ").append(displayed[6].trim()).toString();
	                        g.drawString(s9, 206, counter);
	                        counter += 12;
	                    }
	                }
	            }
	        } else
	        {
	            g.setFont(font1);
	            counter = counter + 8;
	            if(displayed.length > 0)
	            {
	                for(int i1 = 5; i1 <= 6; i1++)
	                {
	                    String s5 = (new StringBuilder()).append(description[i1]).append(" ").append(displayed[i1].trim()).toString();
	                    g.drawString(s5, 206, counter);
	                    counter += 12;
	                }
	
	            }
	        }
	        g.setFont(font);
	        counter = counter + 8;
	        if(displayed.length > 0)
	        {
	            for(int j1 = 9; j1 <= 10; j1++)
	            {
	                String s6 = (new StringBuilder()).append(description[j1]).append(" ").append(displayed[j1].trim()).toString();
	                g.drawString(s6, 206, counter);
	                counter += 12;
	            }
	
	        }
	        Font font2 = new Font("SansSerif", 0, 8);
	        g.setFont(font2);
	        //String s7 = gd.getData("https://bizweb.globequest.com.ph:8443/pos/servlet/getValue", "wscustomer", "customer_service", "wcrealm", displayed[0].trim());
	        String s7 = gd.getData(_serverPath + "/" + _serverName + "/getValue", "wscustomer", "customer_service", "wcrealm", displayed[0].trim());        
	        String s10 = (new StringBuilder()).append("For instruction, pls call ").append(s7).toString();
	        counter = counter + 5;
	        g.drawString(s10, 206, counter);
	        
	        if(print_switch == 0)
	        {
	        	String s11 = (new StringBuilder()).append("To logout from your account, please go to").toString();
	        	String s12 = (new StringBuilder()).append("http://logout.globe.com.ph").toString();
	        	counter = counter + 16;
	        	g.drawString(s11, 206, counter);
	        	counter = counter + 8;
	        	g.drawString(s12, 206, counter);
	        }
    	}    
        return 0;
    }

    String displayed[];
    int i;
    int counter;
    String account;
    String brand_id;
    String service_plan;
    String log;
    String transaction;
    getData gd;
    int print_switch;
    private final String description[] = {
        "Realm:", "Sno#:", "Amt:", "Trn#:", "Batch#:", "User ID:", "Password:", "Expiration:", "Card expiry:", "Sold by:", 
        "Date Sold:"
    };
}
