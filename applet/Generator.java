// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Generator.java

import java.util.Random;

public class Generator
{

    public Generator()
    {
        rand = new Random(System.currentTimeMillis());
    }

    public String generatePWD(int i, int j)
    {
        String s = "";
        String s1 = "1234567890";
        char ac[] = new char[s1.length()];
        for(int k = 0; k < s1.length(); k++)
            ac[k] = s1.charAt(k);

        ac = shuffleCharachters(ac);
        int l = randomInt(i, j);
        for(int i1 = 0; i1 < l; i1++)
            s = s + ac[rand.nextInt(ac.length - 1)];

        return s;
    }

    public int randomInt(int i, int j)
    {
        int k = Math.abs(j - i);
        return i + rand.nextInt(k);
    }

    public char[] shuffleCharachters(char ac[])
    {
        for(int i = 0; i < ac.length; i++)
        {
            int j = rand.nextInt(ac.length - 1);
            char c = ac[i];
            ac[i] = ac[j];
            ac[j] = c;
        }

        return ac;
    }

    Random rand;
}
