import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DialogsController implements Initializable {

    @FXML BorderPane BpDialog;
    @FXML private Label MsgErreur;
    private static String Msg;
    private static String mode;

    /*
    une fonction qui joue le rôle d'initialiser tout ce que doit installer avant l'affichage de la scene
    elle initialise valeur de MsgErreur par le message passé par le page appelant
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MsgErreur.setText(Msg);

    }

/*
setter de la variable Msg
 */
    public static void setMsg(String msg) {
        Msg = msg;
    }


/*

 */
    public static void setMode(String mode) {
        DialogsController.mode = mode;
    }

    /*
    cette fonction pour changer la scène vers la page initiale de l'opération de départ soit Retrait soit Depot
     */
    public void okClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane;
        if(mode.equals("depot")){
            pane= FXMLLoader.load(getClass().getResource("Depot.fxml"));
        }else{
            pane= FXMLLoader.load(getClass().getResource("Retrait.fxml"));
        }

        BpDialog.getChildren().setAll(pane);
    }
}
