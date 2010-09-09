// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   redirectPage.java

import java.io.IOException;
import javax.servlet.http.*;

public class redirectPage extends HttpServlet
{

    public redirectPage()
    {
    }

    public void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws IOException
    {
        httpservletresponse.sendRedirect("http://www.google.com");
    }
}
