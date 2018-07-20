package BDD;

import BDD.Connexion;

import java.io.FileOutputStream;

import java.io.IOException;
import java.sql.Connection;// pour connecter 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Centrale {


    private static  Connection con=null;
    private static ResultSet resultat_Read=null;
    private static ResultSet resultat_Update=null;
    private static Statement state_Read=null;
    private static Statement state_Update=null;
    private static float nouvSomme,nouvSeuil;
    private static String reqExistance,reqVerification,reqDate,reqNumeroClient,reqOperation,reqSolde;
    private static String reqInformations,reqHistorique,reqDepot,reqRetrait,reqRib,reqSeuilHab,reqSeuilQuo,reqSetSeuil;
    static Date date_actuelle = new Date();
    static DateFormat date_op = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    static String date_courrante_op = date_op.format(date_actuelle);
    static DateFormat date_v = new SimpleDateFormat("yyyy-MM-dd");
    static String date_courrante_v = date_v.format(date_actuelle);
    private static String numeroClient=null;
     static StringBuilder tabInfo= new StringBuilder();
   public static ArrayList<String []> tabInfo_his;
    public static ArrayList<String> tabInfo_rib;
    public static void changerBD() {
	Connexion.setUsr("GAB");
	Connexion.setPsd("gab");
	Connexion.setUrl("jdbc:mysql://localhost/banque_interne");
}

//--------------------EXISTANCE-------------------------------Validé-----------------------

    public static boolean existance(String numeroCarte) throws SQLException {

        try {
            con=Connexion.connexion();
            state_Read=(Statement) con.createStatement(resultat_Read.TYPE_SCROLL_INSENSITIVE,resultat_Read.CONCUR_READ_ONLY);
            reqExistance="SELECT num_carte FROM carte WHERE num_carte='"+numeroCarte+"';";
            resultat_Read=state_Read.executeQuery(reqExistance);
            resultat_Read.last();
            return (resultat_Read.getRow()!=0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.err.println(e);}
        finally {
            if(state_Read !=null) {state_Read.close();}
            if(resultat_Read !=null) {resultat_Read.close();}
            if(con !=null) {con.close();}
        }
        return true;
    }

//-------------------CODE--------------------------------validé-----------------------------


    public static String code(String numeroCarte) throws SQLException {
        try {
            con=Connexion.connexion();
            state_Read=(Statement) con.createStatement(resultat_Read.TYPE_SCROLL_INSENSITIVE,resultat_Read.CONCUR_READ_ONLY);
            reqVerification="SELECT code FROM carte WHERE num_carte='"+numeroCarte+"';";
            resultat_Read=state_Read.executeQuery(reqVerification);
            resultat_Read.last();
            return resultat_Read.getString("code");
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.err.println(e);
        }
        finally {
            if(state_Read !=null) {state_Read.close();}
            if(resultat_Read !=null) {resultat_Read.close();}
            if(con !=null) {con.close();}
        }
        return null;

    }


//------------------VALIDITE-----------------------------------validé-----------------------

    public static boolean validite(String numeroCarte) throws SQLException {
        try {
            con=Connexion.connexion();
            state_Read=(Statement) con.createStatement(resultat_Read.TYPE_SCROLL_INSENSITIVE,resultat_Read.CONCUR_READ_ONLY);
            reqDate="select date_exp from carte where num_carte='"+numeroCarte+"';";
            resultat_Read=state_Read.executeQuery(reqDate);
            resultat_Read.last();
            System.out.println(resultat_Read.getString("date_exp"));
            if(date_courrante_v.compareTo(resultat_Read.getString("date_exp"))<=0) {
                return true;
            }else {return false;}
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.err.println(e);
        }
        finally {
            if(state_Read !=null) {state_Read.close();}
            if(resultat_Read !=null) {resultat_Read.close();}
            if(con !=null) {con.close();}
        }
        return true;
    }


//-------------------OPERATION------------------------------------------validé----


    public static void operation(String numeroCarte,float somme,String oprt) throws SQLException {

        try {
            con=Connexion.connexion();
            state_Update=(Statement)con.createStatement(resultat_Update.TYPE_SCROLL_INSENSITIVE,resultat_Update.CONCUR_UPDATABLE);
            reqNumeroClient="select num from client,carte where num_client=num and num_carte='"+numeroCarte+"';";
            resultat_Update=state_Update.executeQuery(reqNumeroClient);
            resultat_Update.last();
            numeroClient=resultat_Update.getString("num");
            reqOperation="INSERT INTO operation VALUES('"+date_courrante_op+"','"+oprt+"','"+numeroClient+"','"+somme+"');";
            state_Update.execute(reqOperation);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.err.println(e);
        }finally {
            if(state_Update !=null) {state_Update.close();}
            if(resultat_Update !=null) {resultat_Update.close();}
            if(con !=null) {con.close();}
        }

    }


//------------------------getSolde-------------------------------validé-----------

    public static float getSolde(String numeroCarte) throws SQLException {
        try {
            con=Connexion.connexion();
            state_Read=(Statement) con.createStatement(resultat_Read.TYPE_SCROLL_INSENSITIVE,resultat_Read.CONCUR_READ_ONLY);
            reqSolde="SELECT * FROM compte WHERE num_client=( SELECT num_client from carte where num_carte='"+numeroCarte+"' );";
            resultat_Read=state_Read.executeQuery(reqSolde);
            resultat_Read.last();
            return resultat_Read.getFloat("solde");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.err.println(e);
        }finally {
            if(state_Read !=null) {state_Read.close();}
            if(resultat_Read !=null) {resultat_Read.close();}
            if(con !=null) {con.close();}
        }
        return 0;
    }


//--------------------------HISTORIQUE--------------------------------presque validé------

    public static ArrayList<String[]> historique(String numeroCarte) throws SQLException{
        try {
            con=Connexion.connexion();
            tabInfo_his = new ArrayList<String[]>();
            state_Read=(Statement) con.createStatement(resultat_Read.TYPE_SCROLL_INSENSITIVE,resultat_Read.CONCUR_READ_ONLY);
            reqInformations="SELECT nom,prenom, adresse, naissance FROM client, carte where num=num_client AND num_carte='"+numeroCarte+"';";
            reqHistorique="SELECT * FROM operation WHERE num_client=( SELECT num_client from carte where num_carte='"+numeroCarte+"');";

            resultat_Read=state_Read.executeQuery(reqInformations);

            resultat_Read.first();

            resultat_Read=state_Read.executeQuery(reqHistorique);
            resultat_Read.first();


            while(resultat_Read.next()) {
                tabInfo_his.add(new String[]{resultat_Read.getString("jour"),resultat_Read.getString("oprt"),resultat_Read.getString("somme")});

            }
            operation(numeroCarte,0,"Imp_Historique");
            return tabInfo_his;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.err.println(e);
        }finally {
            if(state_Read !=null) {state_Read.close();}
            if(resultat_Read !=null) {resultat_Read.close();}
            if(con !=null) {con.close();}
        }
        return null;
    }


    //-----------------------------------DEPOT-----------------------------------validé-------
    public static void depot(String numeroCarte,float somme) throws SQLException {
        try {
            con= Connexion.connexion();
            state_Update=(Statement)con.createStatement(resultat_Update.TYPE_SCROLL_INSENSITIVE,resultat_Update.CONCUR_UPDATABLE);
            nouvSomme = getSolde(numeroCarte)+somme;
            reqDepot="UPDATE compte set solde='"+nouvSomme+"' where num_client=( SELECT num_client from carte where num_carte='"+numeroCarte+"' );" ;
            state_Update.execute(reqDepot);
            operation(numeroCarte,somme,"Depot");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.err.println(e);
        }finally {
            if(state_Update !=null) {state_Update.close();}
            if(resultat_Update !=null) {resultat_Update.close();}
            if(con !=null) {con.close();}
        }
    }

    //---------------------------------RETRAIT-----------------------------------------validé-----
    public static void retrait(String numeroCarte,float somme) throws SQLException {
        try {

            con=Connexion.connexion();
            state_Update=(Statement)con.createStatement(resultat_Update.TYPE_SCROLL_INSENSITIVE,resultat_Update.CONCUR_UPDATABLE);
            nouvSomme=getSolde(numeroCarte)-somme;
            reqRetrait="UPDATE compte set solde='"+nouvSomme+"' where num_client=( SELECT num_client from carte where num_carte='"+numeroCarte+"' );" ;
            state_Update.execute(reqRetrait);
            setSeuil(numeroCarte, somme);
            operation(numeroCarte,somme,"Retrait");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.err.println(e);
        }finally {
            if(state_Update !=null) {state_Update.close();}
            if(resultat_Update !=null) {resultat_Update.close();}
            if(con !=null) {con.close();}
        }
    }

//---------------------------------GET-RIB------------------------------------validé----------


    public static ArrayList<String> getRIB(String numeroCarte) throws SQLException{
        try {
			con=Connexion.connexion();
			tabInfo_rib = new ArrayList<String>();
			state_Read=(Statement) con.createStatement(resultat_Read.TYPE_SCROLL_INSENSITIVE,resultat_Read.CONCUR_READ_ONLY);

			reqRib="Select c.num,c.nom,c.prenom,c.adresse,c.naissance,c.tele,c.profession,co.solde,co.RIB,co.IBAN\r\n" +
			        "from client as c,carte as ca, compte as co\r\n" +
			        "where ca.num_carte='"+numeroCarte+"' and c.num=ca.num_client and co.num_client=ca.num_client;";
			resultat_Read=state_Read.executeQuery(reqRib);
			resultat_Read.last();
				
			
			tabInfo_rib.add(resultat_Read.getString("c.nom"));
			tabInfo_rib.add(resultat_Read.getString("c.prenom"));
			tabInfo_rib.add(resultat_Read.getString("c.adresse"));
			tabInfo_rib.add(resultat_Read.getString("c.tele"));
			tabInfo_rib.add(resultat_Read.getString("co.IBAN"));
			tabInfo_rib.add(resultat_Read.getString("co.RIB"));
            
			
			operation(numeroCarte,0,"Imp_RIB");
			return tabInfo_rib;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.print(e);
		}finally {
	        if(state_Read !=null) {state_Read.close();}
	        if(resultat_Read !=null) {resultat_Read.close();}
	        if(con !=null) {con.close();}
	    }
		return null;
}


//---------------------------------SEUIL----------------------------validé------------------

public static float getSeuilHabdomadaire(String numeroCarte) throws SQLException {
    try {
        con=Connexion.connexion();
        state_Read=(Statement) con.createStatement(resultat_Read.TYPE_SCROLL_INSENSITIVE,resultat_Read.CONCUR_READ_ONLY);
        reqSeuilHab="SELECT seuil_h FROM compte WHERE num_client=( SELECT num_client from carte where num_carte='"+numeroCarte+"' );";
        resultat_Read=state_Read.executeQuery(reqSeuilHab);
        resultat_Read.last();
        return resultat_Read.getFloat("seuil_h");
    } catch (Exception e) {
        // TODO Auto-generated catch block
        System.err.println(e);
    }finally {
        if(state_Read !=null) {state_Read.close();}
        if(resultat_Read !=null) {resultat_Read.close();}
        if(con !=null) {con.close();}
    }
    return 0;
}


public static float getSeuilQuotidien(String numeroCarte) throws SQLException {
    try {
        con=Connexion.connexion();
        state_Read=(Statement) con.createStatement(resultat_Read.TYPE_SCROLL_INSENSITIVE,resultat_Read.CONCUR_READ_ONLY);
        reqSeuilQuo="SELECT seuil_q FROM compte WHERE num_client=( SELECT num_client from carte where num_carte='"+numeroCarte+"' );";
        resultat_Read=state_Read.executeQuery( reqSeuilQuo);
        resultat_Read.last();
        return resultat_Read.getFloat("seuil_q");
    } catch (Exception e) {
        // TODO Auto-generated catch block
        System.err.println(e);
    }finally {
        if(state_Read !=null) {state_Read.close();}
        if(resultat_Read !=null) {resultat_Read.close();}
        if(con !=null) {con.close();}
    }
    return 0;
}


//-------------------------------setSeuil---------------------------------validé------------------

public static void setSeuil(String numeroCarte,float somme) throws SQLException {
    try {

        con=Connexion.connexion();
        state_Update=(Statement)con.createStatement(resultat_Update.TYPE_SCROLL_INSENSITIVE,resultat_Update.CONCUR_UPDATABLE);
        nouvSeuil=getSeuilQuotidien(numeroCarte)-somme;
        reqSetSeuil="UPDATE compte set seuil_q='"+nouvSeuil+"' where num_client=( SELECT num_client from carte where num_carte='"+numeroCarte+"' );" ;
        state_Update.execute(reqSetSeuil);
        nouvSeuil=getSeuilHabdomadaire(numeroCarte)-somme;
        reqSetSeuil="UPDATE compte set seuil_h='"+nouvSeuil+"' where num_client=( SELECT num_client from carte where num_carte='"+numeroCarte+"' );" ;
        state_Update.execute(reqSetSeuil);
        
    } catch (Exception e) {
        // TODO Auto-generated catch block
        System.err.println(e);
    }finally {
        if(state_Update !=null) {state_Update.close();}
        if(resultat_Update !=null) {resultat_Update.close();}
        if(con !=null) {con.close();}
    }
}

}

