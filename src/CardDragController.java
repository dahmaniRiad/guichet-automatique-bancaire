import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class CardDragController extends Thread implements Initializable {
    @FXML private BorderPane BpDrag;
    private int second;

    /*
   une fonction qui joue le rôle d'initialiser tout ce que doit installer avant l'affichage de la scene elle initialise
   la variable seconde avec le second lors de l'appelet aussi lance le thread la classe elle-même
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        second=new Date().getSeconds();
        this.start();
    }

    /*
   c'est la fonction qui est responsable de lancer le thread qui compare la différance entre la seconde actuelle est
   la seconde de lancement de la page si elle égale 10 elle change la scène vers la scène de départ (la page Acceuil)
   et arrête le processus de thread
     */

    public void run(){
        while (!Thread.currentThread().isInterrupted()){
            if(new Date().getSeconds()==second+10){
                try {
                    BorderPane pane= FXMLLoader.load(getClass().getResource("Acceuil.fxml"));
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            BpDrag.getChildren().setAll(pane);
                        }
                    });
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    Thread.currentThread().interrupt();
                }
            }
            else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
