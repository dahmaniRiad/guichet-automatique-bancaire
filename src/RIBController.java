import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RIBController implements Initializable {
    @FXML
    BorderPane BpRIB;


    /*
  une fonction qui joue le rôle d'initialiser tout ce que doit initialiser avant l'affichage de la scène
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*
        pour changer la page par ShowRIB page
         */
    }
    public void AfficherSurLecranClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane= FXMLLoader.load(getClass().getResource("ShowRIB.fxml"));
        BpRIB.getChildren().setAll(pane);
    }
    /*
    pour lancer l'impression de RIB et changer la page vers AutreOperation page
     */
    public void ImprimerClicked(javafx.event.ActionEvent actionEvent) throws IOException, SQLException {
        Impression.impressionRibPDF();
        BorderPane pane= FXMLLoader.load(getClass().getResource("AutreOperation.fxml"));
        BpRIB.getChildren().setAll(pane);
    }

    /*
    pour changer la scene vers Relevé/RIB page
     */
    public void RetourClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane= FXMLLoader.load(getClass().getResource("Relever.fxml"));
        BpRIB.getChildren().setAll(pane);
    }

}
