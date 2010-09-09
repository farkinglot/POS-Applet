// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   posApplet.java

import java.applet.AppletContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;

public class posApplet extends JApplet
    implements ActionListener
{

    public posApplet()
    {
        gd = new getData();
    }

    public void init()
    {
        sess = getParameter("sessid");
        //String s = gd.getData("https://bizweb.globequest.com.ph:8443/oakwood/servlet/checkSession", sess);
        String s = gd.getData("http://192.168.0.112:8080/oakwood/servlet/checkSession", sess);
        System.out.println(s);
        if(s.trim().compareTo("true") == 0)
            setContentPane(makeContentPane());
        else
            try
            {
                //getAppletContext().showDocument(new URL("https://bizweb.globequest.com.ph:8443/oakwood/login.jsp?msg=Plogin"));
            	getAppletContext().showDocument(new URL("http://192.168.0.112:8080/oakwood/login.jsp?msg=Plogin"));
            }
            catch(MalformedURLException malformedurlexception) { }
    }

    public Container makeContentPane()
    {
        Container container = getContentPane();
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new BoxLayout(jpanel, 0));
        jpanel.setBackground(new Color(255, 255, 204));
        Dimension dimension = new Dimension(80, 80);
        ImageIcon imageicon = createImageIcon("images/gray_edit.gif");
        buttonA = new JButton("Services", imageicon);
        buttonA.setVerticalTextPosition(3);
        buttonA.setHorizontalTextPosition(0);
        buttonA.setToolTipText("Vend Account");
        buttonA.setMnemonic(83);
        buttonA.addActionListener(this);
        buttonA.setPreferredSize(dimension);
        ImageIcon imageicon1 = createImageIcon("images/void.gif");
        buttonB = new JButton("Transaction", imageicon1);
        buttonB.setVerticalTextPosition(3);
        buttonB.setHorizontalTextPosition(0);
        buttonB.setToolTipText("Reprint/Void Transaction");
        buttonB.setMnemonic(84);
        buttonB.addActionListener(this);
        buttonB.setPreferredSize(dimension);
        ImageIcon imageicon2 = createImageIcon("images/door.gif");
        logoutButton = new JButton("Exit", imageicon2);
        logoutButton.setVerticalTextPosition(3);
        logoutButton.setHorizontalTextPosition(0);
        logoutButton.setToolTipText("Logout");
        logoutButton.setMnemonic(88);
        logoutButton.addActionListener(this);
        logoutButton.setPreferredSize(dimension);
        getData getdata = new getData();
        //String s = getdata.getData("https://bizweb.globequest.com.ph:8443/oakwood/servlet/checkUserAccess");
        String s = getdata.getData("http://192.168.0.112:8080/oakwood/servlet/checkUserAccess");        
        if(s.compareTo("1") == 0)
            buttonB.setEnabled(true);
        else
            buttonB.setEnabled(false);
        jpanel.add(buttonA);
        jpanel.add(Box.createRigidArea(new Dimension(5, 0)));
        jpanel.add(buttonB);
        jpanel.add(Box.createRigidArea(new Dimension(5, 0)));
        jpanel.add(logoutButton);
        container.add(jpanel, "North");
        cards = new JPanel();
        cards.setBackground(new Color(255, 255, 204));
        cards.setLayout(new CardLayout());
        JPanel jpanel1 = new JPanel();
        jpanel1.setBackground(new Color(255, 255, 204));
        jpanel1.add(new ScreenA());
        JPanel jpanel2 = new JPanel();
        jpanel2.setBackground(new Color(255, 255, 204));
        jpanel2.add(new ScreenC());
        cards.add(jpanel1, "1");
        cards.add(jpanel2, "2");
        ScrollPane scrollpane = new ScrollPane(0);
        scrollpane.add(cards);
        container.add(scrollpane, "Center");
        return container;
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        //String s = gd.getData("https://bizweb.globequest.com.ph:8443/oakwood/servlet/checkSession", "sess");
    	String s = gd.getData("http://192.168.0.112:8080/oakwood/servlet/checkSession", "sess");    	
        if(s.trim().compareTo("true") == 0)
        {
            CardLayout cardlayout = (CardLayout)cards.getLayout();
            if(actionevent.getSource() == buttonA)
                cardlayout.show(cards, "1");
            else
            if(actionevent.getSource() == buttonB)
                cardlayout.show(cards, "2");
            else
            if(actionevent.getSource() == logoutButton)
            {
                logoutButton.setEnabled(false);
                try
                {
                    //getAppletContext().showDocument(new URL((new StringBuilder()).append("https://bizweb.globequest.com.ph:8443/oakwood/exit.jsp?sessid=").append(sess).toString()));
                	getAppletContext().showDocument(new URL((new StringBuilder()).append("http://192.168.0.112:8080/oakwood/exit.jsp?sessid=").append(sess).toString()));
                }
                catch(MalformedURLException malformedurlexception1) { }
            }
        } else
        {
            try
            {
                //getAppletContext().showDocument(new URL("https://bizweb.globequest.com.ph:8443/oakwood/login.jsp?msg=Stout"));
            	getAppletContext().showDocument(new URL("http://192.168.0.112:8080/oakwood/login.jsp?msg=Stout"));
            }
            catch(MalformedURLException malformedurlexception) { }
        }
    }

    protected static ImageIcon createImageIcon(String s)
    {
        URL url = (posApplet.class).getResource(s);
        if(url != null)
        {
            return new ImageIcon(url);
        } else
        {
            System.err.println((new StringBuilder()).append("Couldn't find file: ").append(s).toString());
            return null;
        }
    }

    JPanel cards;
    JButton buttonA;
    JButton buttonB;
    JButton logoutButton;
    String sess;
    getData gd;
}
