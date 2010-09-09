// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScreenA.java

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.regex.Pattern;
import javax.swing.*;

public class ScreenA extends JPanel
    implements ActionListener
{

    public ScreenA()
    {
        gd = new getData();
        counter = 0;
        setBackground(new Color(255, 255, 204));
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 204));
        mainPanel.add(denominationPanel(), "Center");
        add(mainPanel);
    }

    public Container denominationPanel()
    {
        //String s = gd.getData("https://bizweb.globequest.com.ph:8443/pos/servlet/getBrand", "");
    	String s = gd.getData("http://192.168.0.112:8080/oakwood/servlet/getBrand", "");
        String as[] = s.split(":");
        cbp = new JPanel();
        cbp.setBackground(new Color(255, 255, 204));
        cbp.setLayout(new GridLayout(10, 3, 3, 3));
        ImageIcon imageicon = createImageIcon("images/money.gif");
        for(int i = 0; i < as.length; i++)
        {
            Dimension dimension = new Dimension(210, 45);
            JButton jbutton = new JButton(as[i], imageicon);
            jbutton.setBackground(new Color(255, 255, 153));
            jbutton.addActionListener(this);
            jbutton.setPreferredSize(dimension);
            cbp.add(jbutton);
        }

        return cbp;
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        String s = gd.getData("http://192.168.0.112:8080/oakwood/servlet/checkSession", "sess");
        if(s.trim().compareTo("true") == 0)
        {
            String s1 = actionevent.getActionCommand();
            if(actionevent.getSource() == homebutton)
            {
                mainPanel.removeAll();
                mainPanel.add(denominationPanel());
                mainPanel.updateUI();
            } else
            if(actionevent.getSource() == savebutton)
            {
                String s2 = textarea.getText();
                textarea.setEnabled(false);
                savebutton.setEnabled(false);
                if(s2.compareTo("") != 0)
                {
                    String s4 = gd.getData("http://192.168.0.112:8080/oakwood/servlet/saveData", transactionNumber, s2);
                    if(s4.trim().compareTo("1") == 0)
                    {
                        JOptionPane.showMessageDialog(null, "Remarks saved", "Message", 1);
                        textarea.setEnabled(true);
                        savebutton.setEnabled(true);
                    }
                }
            } else
            {
                String s3 = " ";
                Pattern pattern = Pattern.compile(s3);
                String as[] = pattern.split(s1);
                String s5 = "You choose " + s1 + ". Continue Transaction?";
                String s6 = gd.getData("http://192.168.0.112:8080/oakwood/servlet/checkAccount", as[0].trim());
                if(s6.compareTo("") != 0)
                {
                    int i = JOptionPane.showConfirmDialog(this, s5, "Confirmation", 0);
                    if(i == 0)
                    {
                        mainPanel.removeAll();
                        mainPanel.add(transactionPanel(as[0].trim()));
                        mainPanel.updateUI();
                    }
                } else
                {
                    JOptionPane.showMessageDialog(this, "No cards available", "Message", 1);
                }
            }
        } else
        {
            JOptionPane.showMessageDialog(null, "Session Timeout, Please exit and login again", "Error", 0);
        }
    }

    public Container transactionPanel(String s)
    {
        resultText = s;
        compiledString = gd.getData("http://192.168.0.112:8080/oakwood/servlet/getTransaction", s, "print");
        printArray = compiledString.split(":");
        JPanel jpanel = new JPanel();
        jpanel.setBackground(new Color(255, 255, 204));
        jpanel.setLayout(new BorderLayout());
        Dimension dimension = new Dimension(400, 280);
        JPanel jpanel1 = new JPanel();
        jpanel1.setBackground(new Color(255, 255, 204));
        jpanel1.setPreferredSize(dimension);
        GridBagLayout gridbaglayout = new GridBagLayout();
        GridBagConstraints gridbagconstraints = new GridBagConstraints();
        jpanel1.setLayout(gridbaglayout);
        gridbagconstraints.insets = new Insets(5, 10, 5, 5);
        if(compiledString.compareTo("") != 0)
        {
            String s1 = gd.getData("http://192.168.0.112:8080/oakwood/servlet/transServlet", "raduser", printArray[3].trim(), "display");
            transactionNumber = printArray[3].trim();
            display = s1.split(":");
            gridbagconstraints.anchor = 13;
            gridbagconstraints.gridx = 0;
            gridbagconstraints.gridy = 0;
            gridbaglayout.setConstraints(L1 = new JLabel("Realm"), gridbagconstraints);
            gridbagconstraints.gridx = 0;
            gridbagconstraints.gridy = 1;
            gridbaglayout.setConstraints(L2 = new JLabel("Serial Number"), gridbagconstraints);
            gridbagconstraints.gridx = 0;
            gridbagconstraints.gridy = 2;
            gridbaglayout.setConstraints(L3 = new JLabel("Amount"), gridbagconstraints);
            gridbagconstraints.gridx = 0;
            gridbagconstraints.gridy = 3;
            gridbaglayout.setConstraints(L4 = new JLabel("Transaction No"), gridbagconstraints);
            gridbagconstraints.gridx = 0;
            gridbagconstraints.gridy = 4;
            gridbaglayout.setConstraints(L5 = new JLabel("Batch No"), gridbagconstraints);
            gridbagconstraints.gridx = 0;
            gridbagconstraints.gridy = 5;
            gridbaglayout.setConstraints(L6 = new JLabel("Expiration"), gridbagconstraints);
            gridbagconstraints.gridx = 0;
            gridbagconstraints.gridy = 6;
            gridbaglayout.setConstraints(L7 = new JLabel("Card Expiration"), gridbagconstraints);
            gridbagconstraints.gridx = 0;
            gridbagconstraints.gridy = 7;
            gridbaglayout.setConstraints(L8 = new JLabel("Sold by"), gridbagconstraints);
            gridbagconstraints.gridx = 0;
            gridbagconstraints.gridy = 8;
            gridbaglayout.setConstraints(L9 = new JLabel("Date Sold"), gridbagconstraints);
            gridbagconstraints.gridx = 0;
            gridbagconstraints.gridy = 9;
            gridbaglayout.setConstraints(L10 = new JLabel("Remarks"), gridbagconstraints);
            jpanel1.add(L1);
            jpanel1.add(L2);
            jpanel1.add(L3);
            jpanel1.add(L4);
            jpanel1.add(L5);
            jpanel1.add(L6);
            jpanel1.add(L7);
            jpanel1.add(L8);
            jpanel1.add(L9);
            jpanel1.add(L10);
            if(display.length > 0)
            {
                for(int i = 0; i < display.length; i++)
                {
                    gridbagconstraints.gridx = 1;
                    gridbagconstraints.gridy = i;
                    gridbagconstraints.anchor = 17;
                    gridbaglayout.setConstraints(value = new JLabel(display[i].trim(), 0), gridbagconstraints);
                    jpanel1.add(value);
                    counter = i;
                }

            }
            gridbagconstraints.gridx = 1;
            gridbagconstraints.gridy = counter + 1;
            gridbagconstraints.anchor = 17;
            gridbaglayout.setConstraints(textarea = new TextField("", 30), gridbagconstraints);
            textarea.addActionListener(this);
            jpanel1.add(textarea);
        }
        JPanel jpanel2 = new JPanel();
        jpanel2.setLayout(new BoxLayout(jpanel2, 1));
        jpanel2.setBackground(new Color(255, 255, 204));
        jpanel2.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        ImageIcon imageicon = posApplet.createImageIcon("images/home.gif");
        homebutton = new JButton(imageicon);
        homebutton.setMnemonic(77);
        homebutton.addActionListener(this);
        homebutton.setToolTipText("Vend another account");
        jpanel2.add(homebutton);
        jpanel2.add(Box.createRigidArea(new Dimension(0, 5)));
        ImageIcon imageicon1 = posApplet.createImageIcon("images/print.gif");
        printbutton = new JButton(imageicon1);
        jpanel2.add(printbutton);
        printbutton.setToolTipText("Print Transaction");
        printbutton.setMnemonic(80);
        printbutton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent)
            {
                String s2 = gd.getData("http://192.168.0.112:8080/oakwood/servlet/checkSession", "sess");
                if(s2.trim().compareTo("true") == 0)
                {
                    if(actionevent.getSource() == printbutton)
                    {
                        new printTransaction(printArray[3].trim(), "A");
                        printbutton.setEnabled(false);
                    }
                } else
                {
                    JOptionPane.showMessageDialog(null, "Session Timeout, Please exit and login again", "Error", 0);
                }
            }

        }
);
        jpanel2.add(Box.createRigidArea(new Dimension(0, 5)));
        ImageIcon imageicon2 = posApplet.createImageIcon("images/disk.gif");
        savebutton = new JButton(imageicon2);
        savebutton.setToolTipText("Save Remarks");
        savebutton.setMnemonic(87);
        savebutton.addActionListener(this);
        jpanel2.add(savebutton);
        jpanel.add(jpanel1, "Center");
        jpanel.add(jpanel2, "East");
        return jpanel;
    }

    protected static ImageIcon createImageIcon(String s)
    {
        java.net.URL url = (posApplet.class).getResource(s);
        if(url != null)
        {
            return new ImageIcon(url);
        } else
        {
            System.err.println("Couldn't find file: " + s);
            return null;
        }
    }

    JLabel labelA;
    JComboBox a;
    getData gd;
    String currentPattern;
    JPanel mainPanel;
    JPanel cbp;
    JPanel resultPanel;
    JPanel backPanel;
    JPanel titlePanel;
    String resultText;
    String compiledString;
    String description;
    String accountLabel2;
    String accountLabel1;
    String transactionNumber;
    JLabel L1;
    JLabel L2;
    JLabel L3;
    JLabel L4;
    JLabel L5;
    JLabel L6;
    JLabel L7;
    JLabel L8;
    JLabel L9;
    JLabel L10;
    JLabel L11;
    JLabel value;
    JButton actionbutton;
    JButton button;
    JButton printbutton;
    JButton homebutton;
    JButton savebutton;
    String display[];
    String printArray[];
    int counter;
    TextField textarea;
}
