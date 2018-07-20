package BDD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

    public class Routage {

    private static  Connection conRoutage=null;
    private static ResultSet resultat_Read_Routage=null;
    private static ResultSet resultat_Update_Routage=null;
    private static Statement state_Read_Routage=null;
    private static Statement state_Update_Routage=null;
    private static String reqExistance,reqVerification,reqDate,reqNumeroClient,reqOperation;
    static Date date_actuelle = new Date();
    static DateFormat date_op = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    static String date_courrante_op = date_op.format(date_actuelle);
    static DateFormat date_v = new SimpleDateFormat("yyyy-MM-dd");
    static String date_courrante_v = date_v.format(date_actuelle);
    private static String numeroClient=null;
    
  


	public static void changerBD() {

		Connexion.setUsr("GAB");
		Connexion.setPsd("gab");
		Connexion.setUrl("jdbc:mysql://localhost/banque_externe");
	}



//--------------------EXISTANCE----------------------------validé--------------------------

    public static boolean existance(String numeroCarte) throws SQLException {

        try {
            conRoutage=Connexion.connexion();
            state_Read_Routage=(Statement) conRoutage.createStatement(resultat_Read_Routage.TYPE_SCROLL_INSENSITIVE,resultat_Read_Routage.CONCUR_READ_ONLY);

            reqExistance="SELECT num_carte FROM carte WHERE num_carte='"+numeroCarte+"';";
            resultat_Read_Routage=state_Read_Routage.executeQuery(reqExistance);
            resultat_Read_Routage.last();

            return (resultat_Read_Routage.getRow()!=0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.err.println(e);}
        finally {
            if(state_Read_Routage !=null) {state_Read_Routage.close();}
            if(resultat_Read_Routage !=null) {resultat_Read_Routage.close();}
            if(conRoutage !=null) {conRoutage.close();}
        }
        return true;
    }

//-------------------CODE----------------------------------------validé---------------------


    public static String code(String numeroCarte) throws SQLException {
        try {
            conRoutage=Connexion.connexion();
            state_Read_Routage=(Statement) conRoutage.createStatement(resultat_Read_Routage.TYPE_SCROLL_INSENSITIVE,resultat_Read_Routage.CONCUR_READ_ONLY);

            reqVerification="SELECT code FROM carte WHERE num_carte='"+numeroCarte+"';";
            resultat_Read_Routage=state_Read_Routage.executeQuery(reqVerification);
            resultat_Read_Routage.last();
            return resultat_Read_Routage.getString("code");
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.err.println(e);
        }
        finally {
            if(state_Read_Routage !=null) {state_Read_Routage.close();}
            if(resultat_Read_Routage !=null) {resultat_Read_Routage.close();}
            if(conRoutage !=null) {conRoutage.close();}
        }
        return null;

    }


  //------------------VALIDITE-----------------------------------------validé-----------------

      public static boolean validite(String numeroCarte) throws SQLException {
          try {
              conRoutage=Connexion.connexion();
              state_Read_Routage=(Statement) conRoutage.createStatement(resultat_Read_Routage.TYPE_SCROLL_INSENSITIVE,resultat_Read_Routage.CONCUR_READ_ONLY);
              reqDate="select date_exp from carte where num_carte='"+numeroCarte+"';";
              resultat_Read_Routage=state_Read_Routage.executeQuery(reqDate);
              resultat_Read_Routage.last();
              System.out.println(resultat_Read_Routage.getString("date_exp"));
              if(date_courrante_v.compareTo(resultat_Read_Routage.getString("date_exp"))<=0) {
                  return true;
              }else {return false;}
          } catch (Exception e) {
              // TODO Auto-generated catch block
              System.err.println(e);
          }
          finally {
              if(state_Read_Routage !=null) {state_Read_Routage.close();}
              if(resultat_Read_Routage !=null) {resultat_Read_Routage.close();}
              if(conRoutage !=null) {conRoutage.close();}
          }
          return true;
      }

      //---------------------------------RETRAIT---------------------validé-------------------------
      public static void retrait(String numeroCarte,float somme) throws SQLException {
          try {
              conRoutage=Connexion.connexion();
              state_Update_Routage=(Statement)conRoutage.createStatement(resultat_Update_Routage.TYPE_SCROLL_INSENSITIVE,resultat_Update_Routage.CONCUR_UPDATABLE);
              reqNumeroClient="select num from client,carte where num_client=num and num_carte='"+numeroCarte+"';";
              resultat_Update_Routage=state_Update_Routage.executeQuery(reqNumeroClient);
              resultat_Update_Routage.first();
              numeroClient=resultat_Update_Routage.getString("num");
              reqOperation="INSERT INTO operation (jour,oprt,num_client,somme) VALUES ('"+date_courrante_op+"','Retrait','"+numeroClient+"','"+somme+"');";
              state_Update_Routage.execute(reqOperation);

          } catch (Exception e) {
              // TODO Auto-generated catch block
              System.err.println(e);
          }finally {
              if(state_Update_Routage !=null) {state_Update_Routage.close();}
              if(resultat_Update_Routage !=null) {resultat_Update_Routage.close();}
              if(conRoutage !=null) {conRoutage.close();}
          }
      }
}