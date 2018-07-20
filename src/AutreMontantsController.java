import BDD.Centrale;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AutreMontantsController implements Initializable{
    @FXML
    private BorderPane BpAutre;
    @FXML
    private   TextField valeurEntrer;

    //une fonction qui joue le role d'initailiser tout ce que doit intailiser avant l'affichage de la scene
    //la elle initialise la valeur Entrer par l'utilisateur par vide pour eviter le programme d'exécution
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        valeurEntrer.setText("");
    }


    /* quand l'utilisateur appuie sur le button valider la vie lance cette méthode qui a comme rôle Dede changer
    la scène selon le test ,si l'utilisateur tap valider sans entrer aucune valeur le contrôleur transfère le client
    vers une page de dialogue qui indique que le case pour entrer le montant est si vide ce n'est pas le cas
    elle vérifie si le montant est négatif le contrôleur envoie l'utilisateur vers une autre page de dialogue qui indique
    que le montant entrer est négative sinon si redirige l'utilisateur vers une notre page pour choisir
    les billets à retirer selon le montant entré
     */
    public void valideClicked(javafx.event.ActionEvent actionEvent) throws IOException, SQLException {
        int vaTmp=Integer.parseInt(valeurEntrer.getText());
        if(!valeurEntrer.getText().equals("")){
            if(vaTmp<0){
                DialogsController.setMsg("le montant est negative");
                DialogsController.setMode("retrait");
                BorderPane pane= FXMLLoader.load(getClass().getResource("Dialogs.fxml"));
                BpAutre.getChildren().setAll(pane);
            }
            else{
                    RetraitController.setMontantRetrait(vaTmp);
                    ConfirmationController.setModeOperation("Retrait");
                    ConfirmationController.setValue("Retrait de "+RetraitController.getMontantRetrait()+" €");
                    BorderPane pane= FXMLLoader.load(getClass().getResource("BillSelection.fxml"));
                    BpAutre.getChildren().setAll(pane);
            }
        }
         else{
            DialogsController.setMsg("la case pour saiser le montant est vide");
            DialogsController.setMode("retrait");
            BorderPane pane= FXMLLoader.load(getClass().getResource("Dialogs.fxml"));
            BpAutre.getChildren().setAll(pane);
            }

    }
    /* quand l'utilisateur appuie sur le button valider la vie lance cette méthode qui a comme rôle de changer la scène vers la page de retrait
     */
    public void AnnulerClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane= FXMLLoader.load(getClass().getResource("Retrait.fxml"));
        BpAutre.getChildren().setAll(pane);
    }



}


