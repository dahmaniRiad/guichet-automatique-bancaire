import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReleverController implements Initializable   {
    @FXML
    BorderPane BpRelever;


    /*
    une fonction qui joue le rôle d'initialiser tout ce que doit initialiser avant l'affichage de la scène
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /*pour changer la page vers la page de RIB*/
    public void RIBClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane= FXMLLoader.load(getClass().getResource("RIB.fxml"));
        BpRelever.getChildren().setAll(pane);
    }
    /*pour changer la page vers la page de Historique*/
    public void HistoriqueClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane= FXMLLoader.load(getClass().getResource("Historique.fxml"));
        BpRelever.getChildren().setAll(pane);
    }
    /*pour changer la page vers la page de InternalOpration*/
    public void AnnulerClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane= FXMLLoader.load(getClass().getResource("InternalOperation.fxml"));
        BpRelever.getChildren().setAll(pane);
    }
}
