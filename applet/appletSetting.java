// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   appletSetting.java


class appletSetting
{

    appletSetting()
    {
    }

//    static final String server_name = "https://bizweb.globequest.com.ph:8443/";
//    static final String servlet_path = "pos/servlet/";
	//static final String server_name = "http://192.168.0.112:8080/";
    static final String server_name = "http://192.168.0.112:8080/";
	static final String servlet_path = "oakwood/servlet/";    
    static final String timeout = "3600";
    static final String action1 = "images/gray_edit.gif";
    static final String action2 = "images/void.gif";
    static final String logoutImage = "images/door.gif";
    static final String serviceIcon = "images/money.gif";
    static final String printButton = "images/print.gif";
    static final String buttonImage = "images/home.gif";
    static final String saveImage = "images/disk.gif";
    static final String actionText1 = "Services";
    static final String actionText2 = "Transaction";
    static final String actionText3 = "Void";
    static final String logoutText = "Exit";
    static final String action1Tip = "Vend Account";
    static final String action2Tip = "Reprint/Void Transaction";
    static final String action3Tip = "Logout";
    static final String action4Tip = "Void Transaction";
    static final String action8Tip = "Void Transaction";
    static final String action5Tip = "Print Transaction";
    static final String action7Tip = "Reprint Transaction";
    static final String action6Tip = "Save Remarks";
    static final String buttonTip = "Vend another account";
    static final int actionWidth = 80;
    static final int actionHeight = 80;
//    static final String url_getBrand = "https://bizweb.globequest.com.ph:8443/pos/servlet/getBrand";
//    static final String url_getValue = "https://bizweb.globequest.com.ph:8443/pos/servlet/getValue";
//    static final String url_checkAccount = "https://bizweb.globequest.com.ph:8443/pos/servlet/checkAccount";
//    static final String url_getTransaction = "https://bizweb.globequest.com.ph:8443/pos/servlet/getTransaction";
//    static final String url_transServlet = "https://bizweb.globequest.com.ph:8443/pos/servlet/transServlet";
//    static final String url_validateTransaction = "https://bizweb.globequest.com.ph:8443/pos/servlet/validateTransaction";
//    static final String url_checkUserAccess = "https://bizweb.globequest.com.ph:8443/pos/servlet/checkUserAccess";
//    static final String url_voidTransaction = "https://bizweb.globequest.com.ph:8443/pos/servlet/voidTransaction";
//    static final String url_processVoiding = "https://bizweb.globequest.com.ph:8443/pos/servlet/processVoiding";
//    static final String url_printerFlag = "https://bizweb.globequest.com.ph:8443/pos/servlet/printerFlag";
//    static final String url_checkSession = "https://bizweb.globequest.com.ph:8443/pos/servlet/checkSession";
//    static final String url_saveData = "https://bizweb.globequest.com.ph:8443/pos/servlet/saveData";
//    static final String url_login = "https://bizweb.globequest.com.ph:8443/pos_old/login.jsp?msg=Plogin";
//    static final String url_sessiontimeout = "https://bizweb.globequest.com.ph:8443/pos_old/login.jsp?msg=Stout";
    static final String url_getBrand = "http://192.168.0.112:8080/oakwood/servlet/getBrand";
    static final String url_getValue = "http://192.168.0.112:8080/oakwood/servlet/getValue";
    static final String url_checkAccount = "http://192.168.0.112:8080/oakwood/servlet/checkAccount";
    static final String url_getTransaction = "http://192.168.0.112:8080/oakwood/servlet/getTransaction";
    static final String url_transServlet = "http://192.168.0.112:8080/oakwood/servlet/transServlet";
    static final String url_validateTransaction = "http://192.168.0.112:8080/oakwood/servlet/validateTransaction";
    static final String url_checkUserAccess = "http://192.168.0.112:8080/oakwood/servlet/checkUserAccess";
    static final String url_voidTransaction = "http://192.168.0.112:8080/oakwood/servlet/voidTransaction";
    static final String url_processVoiding = "http://192.168.0.112:8080/oakwood/servlet/processVoiding";
    static final String url_printerFlag = "http://192.168.0.112:8080/oakwood/servlet/printerFlag";
    static final String url_checkSession = "http://192.168.0.112:8080/oakwood/servlet/checkSession";
    static final String url_saveData = "http://192.168.0.112:8080/oakwood/servlet/saveData";
    static final String url_login = "http://192.168.0.112:8080/oakwood/login.jsp?msg=Plogin";
    static final String url_sessiontimeout = "http://192.168.0.112:8080/oakwood/login.jsp?msg=Stout";    
    static final String text1 = "Realm";
    static final String text2 = "Serial Number";
    static final String text3 = "Amount";
    static final String text4 = "Transaction No";
    static final String text5 = "Batch No";
    static final String text6 = "Username";
    static final String text7 = "Password";
    static final String text8 = "Expiration";
    static final String text9 = "Card Expiration";
    static final String text10 = "Sold by";
    static final String text11 = "Date Sold";
    static final String text12 = "Account";
    static final String text13 = "Void Reason";
    static final String text14 = "Remarks";
    static final String receipt_header = "Prepaid";
    static final String receipt_text1 = "Sno#:";
    static final String receipt_text2 = "Amt:";
    static final String receipt_text3 = "Trn#:";
    static final String receipt_text4 = "Batch#:";
    static final String receipt_text5 = "User ID:";
    static final String receipt_text6 = "Password:";
    static final String receipt_text7 = "Expiration:";
    static final String receipt_text8 = "Card expiry:";
    static final String receipt_text9 = "Sold by:";
    static final String receipt_text10 = "Date Sold:";
    static final String receipt_text11 = "Realm:";
    static final String receipt_instruction = "For instruction, pls call ";
    static final int drawString_x = 20;
    static final int imageable_x = 5;
    static final int imageable_y = 5;
    static final int imageable_width = 250;
    static final int imageable_height = 700;
    static final int no_of_copies = 2;
    static final int r = 255;
    static final int g = 255;
    static final int b = 204;
    static final int button_r = 255;
    static final int button_g = 255;
    static final int button_b = 153;
    static final String label1 = "Click 'Void' button to cancel transaction";
    static final String label2 = "Click 'Reprint' button to print transaction";
}
