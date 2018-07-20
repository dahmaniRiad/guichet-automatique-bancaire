
import BDD.Centrale;
import BDD.Routage;
import Carte.NumeroCardException;
import Carte.SmartCard;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;

import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import javax.smartcardio.CardException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;

public class AcceuilController extends Thread implements Initializable  {




    private static String numeroCarte;
    private static String modeClient;
    private boolean stopThread=false;


    @FXML private BorderPane BpAcceuil;
    @FXML private ImageView carte;
    @FXML private Rectangle rectangle;
    private static String mode;

    /*
    une fonction qui joue le rôle d'initialiser tout ce que doit initialiser avant l'affichage de la scene
    la le role est de lancer l'animation
    et lancer le thread qui vérifer a chaque 3s s'il y a une carte insérer
    */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Line line= new Line();
        line.setStartX(86);
        line.setStartY(40);
        line.setEndX(86);
        line.setEndY(60);
        PathTransition tran = new PathTransition();
        tran.setNode(carte);
        tran.setDuration(Duration.seconds(1));
        tran.setPath(line);
        tran.setCycleCount(PathTransition.INDEFINITE);
        tran.autoReverseProperty().setValue(true);
        tran.play();
        this.start();
    }

    /*
    fonction statique qui peut utiliser n'importe où dans le programme qu'a un rôle de retourner-le mode de client en forme
    d'un String depuis la variable mode Client donc soit "interne" pour un client interne "externe" pour un client externe
    */


    public static String getMode() {
        return mode;
    }

    //modifier le mode de client par le String passer comme paramètre qui va utiliser partout dans le programme
    public static void setMode(String mode) {
        AcceuilController.mode = mode;
    }


    //retourne le numéro de la carte insérer sous forme d'une String qui est stocké dans la variable numéroCarte
    public static String getNumeroCarte() {
        return numeroCarte;
    }

    //modifier le numéro de carte par le String passer comme paramètre qui va utiliser partout dans le programme
    public static void setNumeroCarte(String numeroCarte) {
        AcceuilController.numeroCarte = numeroCarte;
    }

    /*
    fonction statique qui peut utiliser n'importe où dans le programme qu'a un rôle de retourner-le mode de client en forme
     d'un String depuis la variable mode Client donc soit "interne" pour un client interne soit "externe" pour un client externe
     */

    public static String getModeClient() {
        return modeClient;
    }

    //modifier le mode de client par le String passer comme parametre qui va utiliser partout dans le programme
    public static void setModeClient(String modeClient) {
        AcceuilController.modeClient = modeClient;
    }

    /*
    c'est la fonction qui lance le thread pour tester s'il y a une carte insérée chacun 3s et si c'est le cas elle va lancer
    un ensemble de tests-elle vérifier si la carte existe dans la base interne après si elle est valide si c'est le cas
    elle va changer la scène vers PasswordRecovery sinon elle vérifie dans la base externe l'éxsistance et la validité
    et change la scène vers PasswordRecovery si c'est le cas sinon elle lance un message d'"erreur corespendant à l'erreur
    détecter sinon s'il n'y a pas une carte insérée elle fait une pause pour le processus de test durant 3s après elle relance le processus
    */
    public void run(){
        while (!Thread.currentThread().isInterrupted()){
            if(stopThread){
                Thread.currentThread().interrupt();
            }
            else{
                System.out.println("test d'insertion de la carte ;)");
            try {
                if(SmartCard.attenteCartePresent()){
                    stopThread=true;
                    System.out.println(SmartCard.numeroCarte());
                    AcceuilController.setNumeroCarte(SmartCard.numeroCarte());
                    System.out.println(AcceuilController.getNumeroCarte());
                    Centrale.changerBD();
                    if(Centrale.existance(AcceuilController.getNumeroCarte())){
                    if(Centrale.validite(AcceuilController.getNumeroCarte()))
                    {
                        AcceuilController.setMode("internal");
                        AcceuilController.setModeClient("internal");
                        System.out.println("interne : "+ AcceuilController.getNumeroCarte());
                        BorderPane pane= FXMLLoader.load(getClass().getResource("PasswordRecovery.fxml"));
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println(stopThread);
                                BpAcceuil.getChildren().setAll(pane);
                            }
                        });
                    }else
                    {
                            System.out.println("interne n'est pas valide : "+ AcceuilController.getNumeroCarte());
                            BorderPane pane= FXMLLoader.load(getClass().getResource("ExpiredCard.fxml"));
                            Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        BpAcceuil.getChildren().setAll(pane);
                                        Thread.currentThread().interrupt();
                                    }
                                });
                    }
                    }
                    else{
                            Routage.changerBD();
                            if(Routage.existance(AcceuilController.getNumeroCarte()))
                            {if(Routage.validite(AcceuilController.getNumeroCarte())){
                                AcceuilController.setMode("external");
                                AcceuilController.setModeClient("external");
                                System.out.println("externe : "+ AcceuilController.getNumeroCarte());
                                BorderPane pane= FXMLLoader.load(getClass().getResource("PasswordRecovery.fxml"));
                                try{
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            BpAcceuil.getChildren().setAll(pane);
                                            Thread.currentThread().interrupt();
                                        }
                                    });
                                }catch (Exception e){
                                    System.out.println(e.getMessage());
                                }
                            }
                            else
                            {
                                System.out.println("interne n'est pas valide : "+ AcceuilController.getNumeroCarte());
                                BorderPane pane= FXMLLoader.load(getClass().getResource("ExpiredCard.fxml"));
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        BpAcceuil.getChildren().setAll(pane);
                                        Thread.currentThread().interrupt();
                                    }
                                });
                            }}
                            else{
                                try{
                                    BorderPane pane= FXMLLoader.load(getClass().getResource("CardDrag.fxml"));
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            BpAcceuil.getChildren().setAll(pane);
                                            Thread.currentThread().interrupt();
                                        }
                                    });
                                }catch (Exception e){
                                    System.out.println(e.getMessage());
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NumeroCardException e) {
                e.printStackTrace();
            } finally {
                // Update UI here.
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        }
}
}
