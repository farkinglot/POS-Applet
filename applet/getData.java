// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   getData.java

import java.io.*;
import java.net.*;

public class getData
{

    public getData()
    {
    	System.out.println("Blank");
    }

    public String getData(String s)
    {
        String s1 = null;
        try
        {
            URL url = new URL(s);
            URLConnection urlconnection = url.openConnection();
            urlconnection.setDoOutput(true);
            urlconnection.setUseCaches(false);
            InputStream inputstream = urlconnection.getInputStream();
            StringBuffer stringbuffer = new StringBuffer();
            int i;
            while((i = inputstream.read()) != -1) 
                stringbuffer.append((char)i);
            inputstream.close();
            s1 = stringbuffer.toString().trim();
        }
        catch(MalformedURLException malformedurlexception)
        {
            System.err.println(malformedurlexception.getMessage());
            System.err.println(malformedurlexception.toString());
        }
        catch(ProtocolException protocolexception)
        {
            System.err.println(protocolexception.getMessage());
            System.err.println(protocolexception.toString());
        }
        catch(IOException ioexception)
        {
            System.err.println(ioexception.getMessage());
            System.err.println(ioexception.toString());
        }
        catch(Exception exception)
        {
            System.err.println(exception.getMessage());
            System.err.println(exception.toString());
        }
        
        System.out.println("S1" + s1);
        return s1;
    }

    public String getData(String s, String s1)
    {
        String s2 = null;
        try
        {
            URL url = new URL(s);
            URLConnection urlconnection = url.openConnection();
            urlconnection.setDoOutput(true);
            urlconnection.setUseCaches(false);
            PrintStream printstream = new PrintStream(urlconnection.getOutputStream());
            printstream.println(s1);
            printstream.close();
            InputStream inputstream = urlconnection.getInputStream();
            StringBuffer stringbuffer = new StringBuffer();
            int i;
            while((i = inputstream.read()) != -1) 
                stringbuffer.append((char)i);
            inputstream.close();
            s2 = stringbuffer.toString().trim();
        }
        catch(MalformedURLException malformedurlexception)
        {
            System.err.println(malformedurlexception.getMessage());
            System.err.println(malformedurlexception.toString());
        }
        catch(ProtocolException protocolexception)
        {
            System.err.println(protocolexception.getMessage());
            System.err.println(protocolexception.toString());
        }
        catch(IOException ioexception)
        {
            System.err.println(ioexception.getMessage());
            System.err.println(ioexception.toString());
        }
        catch(Exception exception)
        {
            System.err.println(exception.getMessage());
            System.err.println(exception.toString());
        }
        System.out.println("S2" + s2);
        return s2;
    }

    public String getData(String s, String s1, String s2)
    {
        String s3 = null;
        try
        {
            URL url = new URL(s);
            URLConnection urlconnection = url.openConnection();
            urlconnection.setDoOutput(true);
            urlconnection.setUseCaches(false);
            PrintStream printstream = new PrintStream(urlconnection.getOutputStream());
            printstream.println(s1 + ":" + s2);
            printstream.close();
            InputStream inputstream = urlconnection.getInputStream();
            StringBuffer stringbuffer = new StringBuffer();
            int i;
            while((i = inputstream.read()) != -1) 
                stringbuffer.append((char)i);
            inputstream.close();
            s3 = stringbuffer.toString().trim();
        }
        catch(MalformedURLException malformedurlexception)
        {
            System.err.println(malformedurlexception.getMessage());
            System.err.println(malformedurlexception.toString());
        }
        catch(ProtocolException protocolexception)
        {
            System.err.println(protocolexception.getMessage());
            System.err.println(protocolexception.toString());
        }
        catch(IOException ioexception)
        {
            System.err.println(ioexception.getMessage());
            System.err.println(ioexception.toString());
        }
        catch(Exception exception)
        {
            System.err.println(exception.getMessage());
            System.err.println(exception.toString());
        }
        System.out.println("S3" + s3);
        return s3;
    }

    public String getData(String s, String s1, String s2, String s3)
    {
        String s4 = null;
        try
        {
            URL url = new URL(s);
            URLConnection urlconnection = url.openConnection();
            urlconnection.setDoOutput(true);
            urlconnection.setUseCaches(false);
            PrintStream printstream = new PrintStream(urlconnection.getOutputStream());
            printstream.println(s1 + ":" + s2 + ":" + s3);
            printstream.close();
            InputStream inputstream = urlconnection.getInputStream();
            StringBuffer stringbuffer = new StringBuffer();
            int i;
            while((i = inputstream.read()) != -1) 
                stringbuffer.append((char)i);
            inputstream.close();
            s4 = stringbuffer.toString().trim();
        }
        catch(MalformedURLException malformedurlexception)
        {
            System.err.println(malformedurlexception.getMessage());
            System.err.println(malformedurlexception.toString());
        }
        catch(ProtocolException protocolexception)
        {
            System.err.println(protocolexception.getMessage());
            System.err.println(protocolexception.toString());
        }
        catch(IOException ioexception)
        {
            System.err.println(ioexception.getMessage());
            System.err.println(ioexception.toString());
        }
        catch(Exception exception)
        {
            System.err.println(exception.getMessage());
            System.err.println(exception.toString());
        }
        
        System.out.println("S4" + s4);
        return s4;
    }

    public String getData(String s, String s1, String s2, String s3, String s4)
    {
        String s5 = null;
        try
        {
            URL url = new URL(s);
            URLConnection urlconnection = url.openConnection();
            urlconnection.setDoOutput(true);
            urlconnection.setUseCaches(false);
            PrintStream printstream = new PrintStream(urlconnection.getOutputStream());
            printstream.println(s1 + ":" + s2 + ":" + s3 + ":" + s4);
            printstream.close();
            InputStream inputstream = urlconnection.getInputStream();
            StringBuffer stringbuffer = new StringBuffer();
            int i;
            while((i = inputstream.read()) != -1) 
                stringbuffer.append((char)i);
            inputstream.close();
            s5 = stringbuffer.toString().trim();
        }
        catch(MalformedURLException malformedurlexception)
        {
            System.err.println(malformedurlexception.getMessage());
            System.err.println(malformedurlexception.toString());
        }
        catch(ProtocolException protocolexception)
        {
            System.err.println(protocolexception.getMessage());
            System.err.println(protocolexception.toString());
        }
        catch(IOException ioexception)
        {
            System.err.println(ioexception.getMessage());
            System.err.println(ioexception.toString());
        }
        catch(Exception exception)
        {
            System.err.println(exception.getMessage());
            System.err.println(exception.toString());
        }
        System.out.println("S5" + s5);
        return s5;
    }
}
