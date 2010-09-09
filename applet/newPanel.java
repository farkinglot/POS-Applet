// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   posApplet.java

import java.awt.*;
import javax.swing.JPanel;

class newPanel extends JPanel
{

    newPanel(Color color, LayoutManager layoutmanager, int i, int j)
    {
        w = i;
        h = j;
        setBackground(color);
        setLayout(layoutmanager);
    }

    public Dimension getMinimumSize()
    {
        return new Dimension(w, h);
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(w, h);
    }

    int w;
    int h;
}
