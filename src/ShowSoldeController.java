import BDD.Centrale;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ShowSoldeController implements Initializable {
    @FXML private Label solde;
    @FXML private BorderPane BpShowSolde;
    @Override

 /*
une fonction qui joue le rôle d'initialiser tout ce que doit installer avant l'affichage de la scene et elle initialise
un variable Label soldé avec le retour de la fonction Get Solde() de la class Centrale
 */
    public void initialize(URL location, ResourceBundle resources) {
        Centrale.changerBD();
        try {
            solde.setText(Float.toString(Centrale.getSolde(AcceuilController.getNumeroCarte()))+" €");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*
    changer la page actuelle par InternalOperation page
     */
    public void RetourClicked(javafx.event.ActionEvent actionEvent) throws IOException {
                BorderPane pane= FXMLLoader.load(getClass().getResource("InternalOperation.fxml"));
                BpShowSolde.getChildren().setAll(pane);

    }

    /*
        changer la page actuelle par AutreOperation page après lancer impression du Solde
          */
    public void ImprimerClicked(javafx.event.ActionEvent actionEvent) throws IOException, SQLException {
        Impression.impressionSolde();
        BorderPane pane= FXMLLoader.load(getClass().getResource("AutreOperation.fxml"));
        BpShowSolde.getChildren().setAll(pane);
    }
}
