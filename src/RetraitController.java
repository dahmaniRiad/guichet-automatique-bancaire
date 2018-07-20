import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RetraitController implements Initializable{

    private static int montantRetrait;
    @FXML
    private BorderPane BpRetrait;

    /*
        une fonction qui joue le rôle d'initialiser tout ce que doit initialiser avant l'affichage de la scène
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    /*
    getter de la variable montantRetrait
     */
    public static int getMontantRetrait() {
        return montantRetrait;
    }

    /*
    setter de la variable montantRetrait
     */
    public static void setMontantRetrait(int montantRetrait) {
        RetraitController.montantRetrait = montantRetrait;
    }

    /*
    pour changer la page par AutreMontants page
     */
    public void AutreClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane= FXMLLoader.load(getClass().getResource("AutreMontants.fxml"));
        BpRetrait.getChildren().setAll(pane);
    }

        /*
    pour changer la page par selon le mode client
    si il interne donc vers InternalOperation page
    sinon si il est externe donc vers ExternalOperation page
     */

    public void RetourClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        switch(AcceuilController.getMode()){
            case "internal":
                BorderPane pane= FXMLLoader.load(getClass().getResource("InternalOperation.fxml"));
                BpRetrait.getChildren().setAll(pane);
                break;
            case "external":
                BorderPane pane1= FXMLLoader.load(getClass().getResource("ExternalOperation.fxml"));
                BpRetrait.getChildren().setAll(pane1);
                break;
        }
    }
/*
 lors de clic sur la bouton de 20 € on change la scene vers BillSelection
 apres initialiser la variable motantRetrait à 20
 */
    public void clicked20(javafx.event.ActionEvent actionEvent) throws IOException {
        montantRetrait=20;
        BorderPane pane= FXMLLoader.load(getClass().getResource("BillSelection.fxml"));
        BpRetrait.getChildren().setAll(pane);
    }

    /*
     lors de clic sur la bouton de 40 € on change la scene vers BillSelection
     apres initialiser la variable motantRetrait à 40
     */
    public void clicked40(javafx.event.ActionEvent actionEvent) throws IOException {
        montantRetrait=40;
        BorderPane pane= FXMLLoader.load(getClass().getResource("BillSelection.fxml"));
        BpRetrait.getChildren().setAll(pane);
    }

    /*
 lors de clic sur la bouton de 60 € on change la scene vers BillSelection
 apres initialiser la variable motantRetrait à 60
 */
    public void clicked60(javafx.event.ActionEvent actionEvent) throws IOException {
        montantRetrait=60;
        BorderPane pane= FXMLLoader.load(getClass().getResource("BillSelection.fxml"));
        BpRetrait.getChildren().setAll(pane);
    }

    /*
 lors de clic sur la bouton de 80 € on change la scene vers BillSelection
 apres initialiser la variable motantRetrait à 80
 */
    public void clicked80(javafx.event.ActionEvent actionEvent) throws IOException {
        montantRetrait=80;
        BorderPane pane= FXMLLoader.load(getClass().getResource("BillSelection.fxml"));
        BpRetrait.getChildren().setAll(pane);
    }

    /*
 lors de clic sur la bouton de 100 € on change la scene vers BillSelection
 apres initialiser la variable motantRetrait à 100
 */
    public void clicked100(javafx.event.ActionEvent actionEvent) throws IOException {
        montantRetrait=100;
        BorderPane pane= FXMLLoader.load(getClass().getResource("BillSelection.fxml"));
        BpRetrait.getChildren().setAll(pane);
    }
    /*
 lors de clic sur la bouton de 150 € on change la scene vers BillSelection
 apres initialiser la variable motantRetrait à 150
 */
    public void clicked150(javafx.event.ActionEvent actionEvent) throws IOException {
        montantRetrait=150;
        BorderPane pane= FXMLLoader.load(getClass().getResource("BillSelection.fxml"));
        BpRetrait.getChildren().setAll(pane);
    }
}