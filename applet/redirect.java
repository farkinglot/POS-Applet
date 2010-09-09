// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   redirect.java

import java.applet.AppletContext;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JApplet;

public class redirect extends JApplet
{

    public redirect()
    {
    }

    public void init()
    {
        try
        {
            getAppletContext().showDocument(new URL("http://203.177.84.27:8080/pos/login.jsp?msg=Stout"));
        }
        catch(MalformedURLException malformedurlexception) { }
    }
}
