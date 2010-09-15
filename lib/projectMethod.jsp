<%@page import = "java.sql.*" %>
<%! 
	java.util.Random rand = new java.util.Random(System.currentTimeMillis());
	String driver = "com.mysql.jdbc.Driver";
	//String database = "jdbc:mysql://202.52.162.26/prepaidbiz";
	//String access = "pbiz";
	//String password = "prepaidbiz";
	String database = "jdbc:mysql://172.16.2.190/prepaidwiz";
	String access = "caddev";
	String password = "c@dd3v";
	int sessionValidity = 900;
	
	private boolean CheckIpAccess(String ip_add) throws ClassNotFoundException, SQLException {
	    boolean value=false;
        Class.forName(driver);
        Connection Conn = DriverManager.getConnection(database, access, password);
        Statement stmt = Conn.createStatement();
        String sql = "SELECT * FROM posvalidip WHERE ip_address = '" + ip_add + "' && enabled='Y'";
        System.out.println(sql);
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()){
            value=true;
        } else {
            value=false;
        }
        rs.close();
        stmt.close();
        Conn.close();
        return value;
    }
    
    private boolean CheckLogin(String strUsername, String strPassword, String strRealm, String ipadd) throws ClassNotFoundException, SQLException {
        boolean value=false;
		Class.forName(driver);
        Connection Conn = DriverManager.getConnection(database, access, password);
        Statement stmt = Conn.createStatement();
        String sql = "SELECT a.* FROM posuser as a INNER JOIN posvalidip as b USING(reseller_id) WHERE binary a.username='"+strUsername+"' && binary a.password='"+strPassword+"' && a.enabled='Y' && a.reseller_id='"+strRealm+"' && b.ip_address='"+ipadd+"'";
        System.out.println(sql);
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()){
            value=true;
        } else {
            value=false;
        }
        rs.close();
        stmt.close();
        Conn.close();
        return value;
    }
    
    private String getUserId(String strUsername, String strPassword, String strRealm) throws ClassNotFoundException, SQLException {
        String username=null;
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(database, access, password);
        Statement stmt = conn.createStatement();
        String query = "SELECT username FROM posuser WHERE binary username = '" + strUsername + "' && binary password='" + strPassword + "' && reseller_id='"+strRealm+"'";
        System.out.println(query);
        ResultSet rs = stmt.executeQuery(query);
        while(rs.next()) {
          username=rs.getString("username");
        }
        rs.close();
        stmt.close();
        conn.close();
      return username;
    }
    
     private String getValue(String tableName, String fieldName, String keyName, String keyValue) throws ClassNotFoundException, SQLException {
        String value=null;
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(database, access, password);
        Statement stmt = conn.createStatement();
        String query = "SELECT "+fieldName+" FROM "+tableName+" WHERE binary "+keyName+" = '" + keyValue + "'";
        ResultSet rs = stmt.executeQuery(query);
        while(rs.next()) {
          value=rs.getString(fieldName);
        }
        rs.close();
        stmt.close();
        conn.close();
      return value;
    }
    
	
	private String generatePWD(int minLength, int maxLength) {				
		String pass="";		
		String pwd = "abcdefghijklmnopqrstuvwxyz";		
		pwd += pwd.toUpperCase();		
		pwd += "1234567890";	
		char[] pwdChars = new char[pwd.length()];				
		for(int i=0;i<pwd.length();i++) {			
			pwdChars[i]=pwd.charAt(i);		
		}				
		pwdChars = shuffleCharachters(pwdChars);				
		int length = randomInt(minLength, maxLength);		
		for(int i=0;i<length;i++) {			
			pass+=pwdChars[rand.nextInt(pwdChars.length-1)];		
		}				
		return pass;	
	}	
	
	private int randomInt(int min, int max) {		
		int diff = Math.abs(max-min);		
		return min+rand.nextInt(diff);	
	}		
	
	private char[] shuffleCharachters(char[] characters) {		
		for(int j=0;j<characters.length;j++) {			
		int from = rand.nextInt(characters.length-1);			
		char old = characters[j];			
		characters[j]=characters[from];			
		characters[from]=old;		
	}				
	
	return characters;	
	}	
	
	private String insertSession(String username, String ip_address, String realm) throws ClassNotFoundException, SQLException{
		String sessid = generatePWD(20,22);
		long lastused = System.currentTimeMillis()/1000;
		Class.forName(driver);
        Connection Conn = DriverManager.getConnection(database, access, password);
        Statement stmt = Conn.createStatement();
        String query = "SELECT * from customersession where username='"+username+"'";
        ResultSet rs = stmt.executeQuery(query);
        if(rs.next()) {
          String sql = "UPDATE customersession set sessionid='"+sessid+"', ipaddress='"+ip_address+"', wcrealm='"+realm+"', lastused='"+lastused+"' where username='"+username+"'";
          stmt.executeUpdate(sql);
        } else {
	      String sql = "INSERT INTO customersession (sessionid, ipaddress, username, lastused, wcrealm) VALUES ('"+sessid+"','"+ip_address+"','"+username+"','"+lastused+"','"+realm+"')";    
	      stmt.executeUpdate(sql);
        }
        rs.close();
        stmt.close();
        Conn.close();
		return sessid;
	}
	
	private boolean checkSession(String sessionId, String ip_address) throws ClassNotFoundException, SQLException {
		boolean value=false;
		long time = System.currentTimeMillis()/1000;	
		Class.forName(driver);
        Connection Conn = DriverManager.getConnection(database, access, password);
        Statement stmt = Conn.createStatement();
        String query = "SELECT * from customersession where sessionid='"+sessionId+"' && ipaddress='"+ip_address+"'";
        ResultSet rs = stmt.executeQuery(query);
        if(rs.next()) {
           long lastused=Long.parseLong(rs.getString("lastused"));
           if((time-lastused) <= sessionValidity) {
	           String statement = "UPDATE customersession set lastused='"+time+"' where sessionid='"+sessionId+"'";
	           stmt.executeUpdate(statement);
	           value=true;
           } else {
	           value=false;
           }
        } else {
	      value=false;
        }
        rs.close();
        stmt.close();
        Conn.close();
        return value;
	}
	
	
	private void log_invalid_access(String ip, String username, String pwd, String browser, String remarks, String osValue) throws ClassNotFoundException, SQLException{
		Class.forName(driver);
        Connection Conn = DriverManager.getConnection(database, access, password);
        Statement stmt = Conn.createStatement();
        String sql = "INSERT INTO posinvalidaccess (transaction_time, ip_address,username,password,browser,remarks,ostype) values (now(),'"+ip+"','"+username+"','"+pwd+"','"+browser+"','"+remarks+"','"+osValue+"')";
        stmt.executeUpdate(sql);
        stmt.close();
        Conn.close();
	}
%>
