import java.util.Properties;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   servletSetting.java


class servletSetting
{
	Properties _properties;
	String _dbhost;
	String _dbname;
	String _dbuser;
	String _dbpassword;

    servletSetting()
    {
    }

//    static final String database_url = "jdbc:mysql://bizdb.globequest.com.ph/prepaidbiz";
//    static final String database_name = "fortknox";
//    static final String database_pwd = "f0rtkn0x";
    static final String database_url = "jdbc:mysql://172.16.2.190/prepaidbiz";
    static final String database_name = "caddev";
    static final String database_pwd = "c@dd3v";    
    static final String database_driver = "com.mysql.jdbc.Driver";
    static final int sessionValidity = 900;
}
