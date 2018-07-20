import BDD.Centrale;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DepotController implements Initializable {
    @FXML
    private BorderPane BpDepot;
    @FXML
    private TextField valeurDepot;
    private static int montantDepot;


    /*
    une fonction qui joue le rôle d'initialiser tout ce que doit installer avant l'affichage de la scene elle
    initialise valeur de valeur dépôt par le vide pour éviter des problèmes lors de l'exécution
    */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        valeurDepot.setText("");
    }

    /*
    getter de la variable montantDepot
     */
    public static int getMontantDepot() {
        return montantDepot;
    }

    /*
    setter de la variable montantDepot
     */
    public static void setMontantDepot(int montantDepot) {
        DepotController.montantDepot = montantDepot;
    }

    /*
    lors de cliquer la valide cette fonction lance un ensemble des tests d'abord si le champ pour entrer le montant
    est vide après si la valeur entrée est négative malgré que ce n'est pas logique mais l'encadrant à demander ça ;)
    et la rediriger la cliente vers page de dialogues Sui afficher le problème selon le cas sinon elle redirige
    le client vers la page qui demande le ticket pour l'opération
     */
    public void validerClicked(javafx.event.ActionEvent actionEvent) throws SQLException, IOException {

        if(!valeurDepot.getText().equals("")){
            int vaTmp=Integer.parseInt(valeurDepot.getText());
            if(vaTmp<0){
                DialogsController.setMsg("le montant est négatif");
                DialogsController.setMode("depot");
                BorderPane pane= FXMLLoader.load(getClass().getResource("Dialogs.fxml"));
                BpDepot.getChildren().setAll(pane);
            }
            else{
            DepotController.setMontantDepot(vaTmp);
            ConfirmationController.setModeOperation("Depot");
            ConfirmationController.setValue("Dépot de "+vaTmp+" €");
            BorderPane pane= FXMLLoader.load(getClass().getResource("Ticket.fxml"));
            BpDepot.getChildren().setAll(pane);
            }
        }
        else {
            DialogsController.setMsg("la case pour saisir le montant est vide");
            DialogsController.setMode("depot");
            BorderPane pane= FXMLLoader.load(getClass().getResource("Dialogs.fxml"));
            BpDepot.getChildren().setAll(pane);
        }


    }

    /*
   la fonction qui redirige le client qui veut annuler l'opération vers la page des Opération selon le mode de client
     */
    public void AnnulerClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        switch(AcceuilController.getModeClient()){
            case "internal":
                BorderPane pane= FXMLLoader.load(getClass().getResource("InternalOperation.fxml"));
                BpDepot.getChildren().setAll(pane);
                break;
            case "external":
                BorderPane pane1= FXMLLoader.load(getClass().getResource("ExternalOperation.fxml"));
                BpDepot.getChildren().setAll(pane1);
                break;
        }
    }
}
