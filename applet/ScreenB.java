// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScreenB.java

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ScreenB extends JPanel
    implements ActionListener
{

    public ScreenB()
    {
        setBackground(new Color(255, 255, 204));
        cbp = new JPanel();
        cbp.setBackground(new Color(255, 255, 204));
        cbp.setLayout(new GridLayout(4, 4, 5, 5));
        Dimension dimension = new Dimension(130, 50);
        JButton jbutton = new JButton("Test");
        jbutton.setBackground(new Color(255, 255, 153));
        jbutton.addActionListener(this);
        jbutton.setPreferredSize(dimension);
        cbp.add(jbutton);
        add(cbp, "Center");
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getSource() == button)
            button.setText("hello");
    }

    JPanel cbp;
    JButton button;
}
