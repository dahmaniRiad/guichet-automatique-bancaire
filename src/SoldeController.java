import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SoldeController implements Initializable {

    @FXML
    BorderPane BpSolde;

    /*
        une fonction qui joue le rôle d'initialiser tout ce que doit initialiser avant l'affichage de la scène    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /*
    changer la page actuelle par ShowSolde page
     */
    public void AfficherSurLecranClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane= FXMLLoader.load(getClass().getResource("ShowSolde.fxml"));
        BpSolde.getChildren().setAll(pane);

    }

     /*
         changer la page actuelle par AutreOperation page apres lancer limpression du Solde
          */

    public void ImprimerClicked(javafx.event.ActionEvent actionEvent) throws IOException, SQLException {
        Impression.impressionSolde();
        BorderPane pane= FXMLLoader.load(getClass().getResource("AutreOperation.fxml"));
        BpSolde.getChildren().setAll(pane);
    }

    /*
    changer la page actuelle par InternalOperation page
     */
    public void AnnulerClicked(javafx.event.ActionEvent actionEvent) throws IOException {
                BorderPane pane= FXMLLoader.load(getClass().getResource("InternalOperation.fxml"));
                BpSolde.getChildren().setAll(pane);
        }
}
