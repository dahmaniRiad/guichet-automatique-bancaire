import BDD.Centrale;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShowRIBController implements Initializable {
    @FXML private BorderPane BpShowRIB;
    @FXML private Label nom;
    @FXML private Label prenom;
    @FXML private Label adresse;
    @FXML private Label IBAN;
    @FXML private Label BIC;
    @FXML private Label RIB;
    @Override

    /*
une fonction qui joue le rôle d'initialiser tout ce que doit installer avant l'affichage de la scene elle initialise
une variable TMP avec le retour de la fonction Get RIB() de la class Centraleapres elle initialise chaque label par
le contenu de du variable Tmp */
    public void initialize(URL location, ResourceBundle resources) {
        Centrale.changerBD();
        ArrayList<String> tmp=null;
        try {
           tmp=Centrale.getRIB(AcceuilController.getNumeroCarte());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        nom.setText(tmp.get(0));
        prenom.setText(tmp.get(1));
        adresse.setText(tmp.get(2));
        IBAN.setText(tmp.get(4));
        BIC.setText("RADA2018PARISDESCARTES");
        RIB.setText(tmp.get(5));
    }

      /*
         changer la page actuelle par AutreOperation page apres lancer limpression du RIB
          */
    public void ImprimerClicked(javafx.event.ActionEvent actionEvent) throws IOException, SQLException {
        Impression.impressionRibPDF();
        BorderPane pane= FXMLLoader.load(getClass().getResource("AutreOperation.fxml"));
        BpShowRIB.getChildren().setAll(pane);
    }
      /*
         changer la page actuelle par RIB page
          */
    public void RetourClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane= FXMLLoader.load(getClass().getResource("RIB.fxml"));
        BpShowRIB.getChildren().setAll(pane);
    }
}
