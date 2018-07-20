import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HistoriqueController implements Initializable{
    @FXML
    BorderPane BpHistorique;



    /*
    une fonction qui joue le rôle d'initialiser tout ce que doit installer avant l'affichage de la scène
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    /*
    pour afficher l'historique par change la page vers ShowHistorique
     */
    }
    public void AfficherSurLecranClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane= FXMLLoader.load(getClass().getResource("ShowHistorique.fxml"));
        BpHistorique.getChildren().setAll(pane);
    }
    /*
    pour lancer l'impression de l'historique par faire un appel de la fonction impressionReleverBnacaire()de
    la classe Impression
     */
    public void ImprimerClicked(javafx.event.ActionEvent actionEvent) throws IOException, SQLException {
        Impression.impressionReleverBancaire();
        BorderPane pane= FXMLLoader.load(getClass().getResource("AutreOperation.fxml"));
        BpHistorique.getChildren().setAll(pane);
    }

    /*
    pour retourner vers la page Relever.fxml
     */
    public void RetourClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane= FXMLLoader.load(getClass().getResource("Relever.fxml"));
        BpHistorique.getChildren().setAll(pane);
    }
}
