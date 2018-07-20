import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InternalOperationController implements Initializable{

    @FXML
    private BorderPane BpInternal;


    /*
    une fonction qui joue le rôle d'initialiser tout ce que doit installer avant l'affichage de la scène
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /*pour changer la page vers la page de retrait*/
    public void retraitClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane= FXMLLoader.load(getClass().getResource("Retrait.fxml"));
        BpInternal.getChildren().setAll(pane);
    }

    /*pour changer la page vers la page de Relevé/RIB*/
    public void ReleverClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane gp= FXMLLoader.load(getClass().getResource("Relever.fxml"));
        BpInternal.getChildren().setAll(gp);

    }
    /*pour changer la page vers la page de Solde*/
    public void SoldeClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane gp= FXMLLoader.load(getClass().getResource("Solde.fxml"));
        BpInternal.getChildren().setAll(gp);

    }

    /*pour changer la page vers la page de Depot*/
        public void depotClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane gp= FXMLLoader.load(getClass().getResource("Depot.fxml"));
        BpInternal.getChildren().setAll(gp);
    }


    /*pour changer la page vers la page de Fin*/
    public void FinClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane= FXMLLoader.load(getClass().getResource("CardDrag.fxml"));
        BpInternal.getChildren().setAll(pane);
    }


}
