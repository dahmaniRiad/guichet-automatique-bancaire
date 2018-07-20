package Security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

public class Securite {
	
	
	// les methodes:
	
	// cette methodes sert a transform�e les bytes en hexa decimale 


	 private static String bytesToHex(byte[] bytes)
	 {
		  //convertir le tableau de bits en une format hexad�cimal 
	       StringBuffer hexString = new StringBuffer();
	     for (int i=0;i<bytes.length;i++)
	     {
		      String hex=Integer.toHexString(0xff & bytes[i]);
		          if(hex.length()==1) hexString.append('0');
		          hexString.append(hex);
		       
	     }
		 
	     return hexString.toString();
	 }
	 
	
	public static String getMd5 (String password ) 
	{
	
        MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
        md.update(password.getBytes());
        
        byte byteData[] = md.digest();
        
        
        return bytesToHex(byteData);
     
	}
	
	 public static String getSha256(String password ,String date) {
		 

		    try{
		        MessageDigest md = MessageDigest.getInstance("SHA-256");
		        password+=date;
		        md.update(password.getBytes());
		        return bytesToHex(md.digest());
		    } catch(Exception ex){
		        throw new RuntimeException(ex);
		    }
		 }
	
	 
	 public static boolean comparePassword(String password1 ,String password2,String date) 
	 {
		 
		 return getSha256(getMd5(password1),date).equals(getSha256(password2,date));
	 }
	
	
	
	
	
	
	
	
	

}
