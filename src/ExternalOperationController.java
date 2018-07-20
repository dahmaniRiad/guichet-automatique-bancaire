import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExternalOperationController implements Initializable {
    @FXML
    private BorderPane BpExternal;


    /*
    une fonction qui joue le rôle d'initialiser tout ce que doit installer avant l'affichage de la scène
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /*pour changer la page vers la page de retrait*/
    public void retraitClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane= FXMLLoader.load(getClass().getResource("Retrait.fxml"));
        BpExternal.getChildren().setAll(pane);
    }

    /*
    pour sortir et aller vers la page CardDrag
     */
    public void FinClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        Pane pane1= FXMLLoader.load(getClass().getResource("CardDrag.fxml"));
        BpExternal.getChildren().setAll(pane1);
    }
}
