// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScreenC.java

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Properties;

import javax.swing.*;

public class ScreenC extends JPanel
    implements ActionListener
{
	Properties _properties;
	String _dbhost;
	String _dbname;
	String _dbuser;
	String _dbpassword;
	String _serverName;
	String _serverPath;
	String _projectName;
	
    public ScreenC()
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
        
        gd = new getData();
        totalRows = 0;
        setLayout(new BorderLayout());
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 204));
        mainPanel.setLayout(new BoxLayout(mainPanel, 1));
        Dimension dimension = new Dimension(160, 80);
        buttonA = new JButton("Void Transaction     ");
        buttonA.setMnemonic(73);
        buttonA.setToolTipText("Void Transaction");
        buttonA.setPreferredSize(dimension);
        buttonA.setBackground(new Color(255, 255, 153));
        buttonA.addActionListener(this);
        buttonB = new JButton("Reprint Transaction");
        buttonB.setMnemonic(80);
        buttonB.setToolTipText("Reprint Transaction");
        buttonB.setPreferredSize(dimension);
        buttonB.setBackground(new Color(255, 255, 153));
        buttonB.addActionListener(this);
        mainPanel.add(Box.createRigidArea(new Dimension(5, 5)));
        mainPanel.add(buttonA);
        mainPanel.add(Box.createRigidArea(new Dimension(5, 5)));
        mainPanel.add(buttonB);
        resultPanel = new JPanel();
        Dimension dimension1 = new Dimension(390, 350);
        resultPanel.setBackground(new Color(255, 255, 204));
        resultPanel.setPreferredSize(dimension1);
        JLabel jlabel = new JLabel("");
        resultPanel.add(jlabel);
        add(mainPanel, "East");
        add(resultPanel, "Center");
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        String s = gd.getData(_serverPath + "/" + _serverName + "/checkSession", "sess");
        if(s.trim().compareTo("true") == 0)
        {
            if(actionevent.getSource() == buttonA)
            {
                inputValue2 = JOptionPane.showInputDialog("Void transaction number:");
                Object obj = null;
                if(inputValue2.compareTo("") != 0)
                {
                    String s1 = gd.getData(_serverPath + "/" + _serverName + "/voidTransaction", "raduser", inputValue2, "3600");
                    if(s1.trim().compareTo("1") == 0)
                        JOptionPane.showMessageDialog(null, "User is currently online, account cannot be voided", "Error", 0);
                    else
                    if(s1.trim().compareTo("2") == 0)
                    {
                        resultPanel.removeAll();
                        resultPanel.add(displayTransaction(inputValue2, "1"));
                        resultPanel.updateUI();
                    } else
                    if(s1.trim().compareTo("3") == 0)
                        JOptionPane.showMessageDialog(null, "Card was purchased more than half an hour, account cannot be voided", "Error", 0);
                    else
                    if(s1.trim().compareTo("4") == 0)
                        JOptionPane.showMessageDialog(null, "Card is already used, voiding is not allowed", "Error", 0);
                    else
                    if(s1.trim().compareTo("5") == 0)
                        JOptionPane.showMessageDialog(null, "Batch status is inactive, voiding is not allowed", "Error", 0);
                    else
                        JOptionPane.showMessageDialog(null, "Invalid Transaction #", "Error", 0);
                }
            } else
            if(actionevent.getSource() == buttonB)
            {
                inputValue = JOptionPane.showInputDialog("Reprint transaction number:");
                Object obj1 = null;
                if(inputValue.compareTo("") != 0)
                {
                    String s2 = gd.getData(_serverPath + "/" + _serverName + "/validateTransaction", "raduser", inputValue);
                    if(s2.compareTo("true") == 0)
                    {
                        resultPanel.removeAll();
                        resultPanel.add(displayTransaction(inputValue, "2"));
                        resultPanel.updateUI();
                    } else
                    {
                        if(s2.compareTo("D") == 0)
                            message = "Reprint Denied. Account is deactivated";
                        else
                        if(s2.compareTo("Y") == 0)
                            message = "Reprint Denied. Account is emptied";
                        else
                        if(s2.compareTo("T") == 0)
                            message = "Reprint Denied. Account is terminated";
                        else
                        if(s2.compareTo("K") == 0)
                            message = "Reprint Denied. Account is mark for deletion";
                        else
                        if(s2.compareTo("H") == 0)
                            message = "Reprint Denied. Account is on hold";
                        else
                        if(s2.compareTo("E") == 0)
                            message = "Reprint Denied. Account is emptied";
                        else
                        if(s2.compareTo("V") == 0)
                            message = "Reprint Denied. Account is voided";
                        else
                            message = "Invalid Transaction number";
                        JOptionPane.showMessageDialog(null, message, "Error", 0);
                    }
                }
            } else
            if(actionevent.getSource() == button)
            {
                resultPanel.removeAll();
                resultPanel.add(new ScreenC());
                resultPanel.updateUI();
            }
        } else
        {
            JOptionPane.showMessageDialog(null, "Session Timeout, Please exit and login again", "Error", 0);
        }
    }

    public Container displayTransaction(String s, String s1)
    {
        transaction = s;
        String s2 = gd.getData(_serverPath + "/" + _serverName + "/transServlet", "raduser", s, "display");
        result = gd.getData(_serverPath + "/" + _serverName + "/getValue", "raduser", "cardvalue", "sold_transact_no", s.trim());
        JPanel jpanel = new JPanel();
        if(s1.compareTo("1") == 0)
            label = new JLabel("Click 'Void' button to cancel transaction");
        else
        if(s1.compareTo("2") == 0)
            label = new JLabel("Click 'Reprint' button to print transaction");
        jpanel.add(label);
        JPanel jpanel1 = new JPanel();
        jpanel1.setBackground(new Color(255, 255, 204));
        jpanel1.setLayout(new BorderLayout());
        JPanel jpanel2 = new JPanel();
        jpanel2.setBackground(new Color(255, 255, 204));
        GridBagLayout gridbaglayout = new GridBagLayout();
        GridBagConstraints gridbagconstraints = new GridBagConstraints();
        jpanel2.setLayout(gridbaglayout);
        gridbagconstraints.insets = new Insets(5, 10, 5, 5);
        if(s2.compareTo("") != 0)
        {
            display = s2.split(":");
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
            jpanel2.add(L1);
            jpanel2.add(L2);
            jpanel2.add(L3);
            jpanel2.add(L4);
            jpanel2.add(L5);
            jpanel2.add(L6);
            jpanel2.add(L7);
            jpanel2.add(L8);
            jpanel2.add(L9);
            if(display.length > 0)
            {
                for(int i = 0; i < display.length; i++)
                {
                    gridbagconstraints.gridx = 1;
                    gridbagconstraints.gridy = i;
                    gridbagconstraints.anchor = 17;
                    gridbaglayout.setConstraints(value = new JLabel(display[i].trim(), 0), gridbagconstraints);
                    jpanel2.add(value);
                    totalRows = i;
                }

            }
            if(s1.compareTo("1") == 0)
            {
                gridbagconstraints.gridx = 0;
                gridbagconstraints.gridy = 9;
                gridbaglayout.setConstraints(L10 = new JLabel("Void Reason"), gridbagconstraints);
                jpanel2.add(L10);
                gridbagconstraints.gridx = 1;
                gridbagconstraints.gridy = totalRows + 1;
                gridbagconstraints.anchor = 17;
                gridbaglayout.setConstraints(textarea = new TextField("", 30), gridbagconstraints);
                textarea.addActionListener(this);
                jpanel2.add(textarea);
            }
        }
        JPanel jpanel3 = new JPanel();
        jpanel3.setBackground(new Color(255, 255, 204));
        if(s1.compareTo("2") == 0)
        {
            printbutton = new JButton("Reprint");
            printbutton.setBackground(new Color(255, 255, 153));
            printbutton.setMnemonic(82);
            printbutton.setToolTipText("Print Transaction");
            jpanel3.add(printbutton);
            printbutton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent actionevent)
                {
                    String s3 = gd.getData(_serverPath + "/" + _serverName + "/checkSession", "sess");
                    if(s3.trim().compareTo("true") == 0)
                    {
                        if(actionevent.getSource() == printbutton)
                        {
                        	//JOptionPane.showMessageDialog(null, "Print Button clicked", "Error", 0);
                            new printTransaction(inputValue, "B");
                            printbutton.setEnabled(false);
                        }
                    } else
                    {
                        JOptionPane.showMessageDialog(null, "Session Timeout, Please exit and login again", "Error", 0);
                    }
                }

            }
);
        } else
        if(s1.compareTo("1") == 0)
        {
            printbutton = new JButton("Void");
            printbutton.setBackground(new Color(255, 255, 153));
            printbutton.setToolTipText("Void Transaction");
            printbutton.setMnemonic(86);
            jpanel3.add(printbutton);
            printbutton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent actionevent)
                {
                    String s3 = gd.getData(_serverPath + "/" + _serverName + "/checkSession", "sess");
                    if(s3.trim().compareTo("true") == 0)
                    {
                        if(actionevent.getSource() == printbutton)
                        {
                            String s4 = textarea.getText();
                            if(s4.compareTo("") == 0)
                            {
                                JOptionPane.showMessageDialog(null, "Please state reason for voiding the transaction", "Error", 0);
                            } else
                            {
                                textarea.setEnabled(false);
                                printbutton.setEnabled(false);
                                String s5 = gd.getData(_serverPath + "/" + _serverName + "/processVoiding", transaction, "1800", s4.trim());
                                if(s5.trim().compareTo("2") == 0)
                                {
                                    label.setText("Transaction voided");
                                    JOptionPane.showMessageDialog(null, "Transaction Voided", "information", 1);
                                } else
                                if(s5.trim().compareTo("1") == 0)
                                {
                                    label.setText("User currently online, account cannot be voided");
                                    JOptionPane.showMessageDialog(null, "User currently online, account cannot be voided", "Error", 0);
                                } else
                                if(s5.trim().compareTo("3") == 0)
                                {
                                    label.setText("Account was purchased more than half an hour, account cannot be voided");
                                    JOptionPane.showMessageDialog(null, "Account was purchased more than half an hour, account cannot be voided", "Error", 0);
                                } else
                                if(s5.trim().compareTo("4") == 0)
                                {
                                    label.setText("Account was already used. account cannot be voided");
                                    JOptionPane.showMessageDialog(null, "Account was already used, account cannot be voided", "Error", 0);
                                } else
                                {
                                    label.setText("Transaction Invalid");
                                }
                            }
                        }
                    } else
                    {
                        JOptionPane.showMessageDialog(null, "Session Timeout, Please exit and login again", "Error", 0);
                    }
                }

            }
);
        }
        jpanel1.add(jpanel, "North");
        jpanel1.add(jpanel3, "South");
        jpanel1.add(jpanel2, "Center");
        return jpanel1;
    }

    getData gd;
    JPanel mainPanel;
    JPanel resultPanel;
    JButton buttonA;
    JButton buttonB;
    String inputValue;
    String inputValue2;
    JButton button;
    String display[];
    String printDisplay[];
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
    JLabel value;
    JLabel label;
    JButton printbutton;
    String result;
    String transaction;
    String message;
    int totalRows;
    TextField textarea;
}
