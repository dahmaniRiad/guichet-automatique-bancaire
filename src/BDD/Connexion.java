package BDD;

import java.sql.Connection;


import java.sql.DriverManager;


import java.sql.SQLException;

public class Connexion {

    private static  String usr;
    private static  String psd;
    private static  String url;

    protected static void setUsr(String usr) {
    	Connexion.usr = usr;
	}


	protected static void setPsd(String psd) {
		Connexion.psd = psd;
	}


	protected static void setUrl(String url) {
		Connexion.url = url;
	}

    
    public static  Connection connexion() throws SQLException {
        return  DriverManager.getConnection(url,usr,psd);
    }
}