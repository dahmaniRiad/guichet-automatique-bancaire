import BDD.Centrale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BillSelectionController implements Initializable{


    private int fois10,fois20,fois50,fois100,fois200,fois500;
    @FXML    Label label10,label20,label50,label100,label200,label500;
    @FXML    private BorderPane BpBillSelection;

/*
  une fonction qui joue le rôle d'initialiser tout ce que doit installer avant l'affichage de la scene elle initialise
  tous les compteurs par défaut selon la valeur du montant récupéré du classe Retrait Controller
*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fois10=fois20=fois50=fois100=fois200=fois500=0;

        int i=RetraitController.getMontantRetrait();
        System.out.println("i: "+i);
        fois500=i/500;i%=500;
        label500.setText("× "+fois500);
        fois200=i/200;i%=200;
        label200.setText("× "+fois200);
        fois100=i/100;i%=100;
        label100.setText("× "+fois100);
        fois50=i/50;i%=50;
        label50.setText("× "+fois50);
        fois20=i/20;i%=20;
        label20.setText("× "+fois20);
        fois10=i/10;i%=10;
        label10.setText("× "+fois10);

    }



    /*
lors d'un clic sur un des boutons (+) le contrôleur lance cette fonction qui elle-même détecte le bouton concerné
et incrémenté son compteur
 */

    public void initializeplus(ActionEvent actionEvent){
        int tmp=RetraitController.getMontantRetrait();
        switch(((Control)actionEvent.getSource()).getId()){
            case "plus10":
                if((10*(fois10+1)+20*fois20+50*fois50+100*fois100+200*fois200+500*fois500)<=tmp){
                fois10++;
                label10.setText("× "+fois10);
                }break;
            case "plus20":
                if((10*fois10+20*(fois20+1)+50*fois50+100*fois100+200*fois200+500*fois500)<=tmp){
                fois20++;
                label20.setText("× "+fois20);
                }break;
            case "plus50":
                if((10*fois10+20*fois20+50*(fois50+1)+100*fois100+200*fois200+500*fois500)<=tmp){
                fois50++;
                label50.setText("× "+fois50);
                }break;
            case "plus100":
                if((10*fois10+20*fois20+50*fois50+100*(fois100+1)+200*fois200+500*fois500)<=tmp){
                fois100++;
                label100.setText("× "+fois100);
                }break;
            case "plus200":
                if((10*fois10+20*fois20+50*fois50+100*fois100+200*(fois200+1)+500*fois500)<=tmp){
                fois200++;
                label200.setText("× "+fois200);
                }break;
            case "plus500":
                if((10*fois10+20*fois20+50*fois50+100*fois100+200*fois200+500*(fois500+1))<=tmp){
                fois500++;
                label500.setText("× "+fois500);
                }break;
        }
    }

    /*
   lors d'un clic sur un des boutons (-) le controlleur lance cette fonction qui elle-même détecte
   le bouton concerné et décrémenté son compteur
     */
    public void initializemoins(ActionEvent actionEvent){
        switch(((Control)actionEvent.getSource()).getId()){
            case "moins10":
                if(fois10>0){
                fois10--;
                label10.setText("× "+fois10);}
                break;
            case "moins20":
                if(fois20>0){
                fois20--;
                label20.setText("× "+fois20);}
                break;
            case "moins50":
                if(fois50>0){
                fois50--;
                label50.setText("× "+fois50);}
                break;
            case "moins100":
                if(fois100>0){
                fois100--;
                label100.setText("× "+fois100);}
                break;
            case "moins200":
                if(fois200>0){
                fois200--;
                label200.setText("× "+fois200);}
                break;
            case "moins500":
                if(fois500>0){
                fois500--;
                label500.setText("× "+fois500);}
                break;
        }
    }

    /*
    Lors d'un clic sur le bouton Anuuler le contrôleur lance cette fonction qui change la scène actuelle par Retrait.xml
     */
    public void AnnulerClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane= FXMLLoader.load(getClass().getResource("Retrait.fxml"));
        BpBillSelection.getChildren().setAll(pane);
    }
    /*
    quand le client valide le choix des billets par cliquer sur le bouton Valider cette fonction envoie le client vers
    la page de confirmation pour confirmer le montant veut retéter modifier le message de confirmation de
    la classe Confirmation Controller
     */
    public void ValiderClicked(javafx.event.ActionEvent actionEvent) throws SQLException, IOException {
        ConfirmationController.setModeOperation("Retrait");
        ConfirmationController.setValue("Retrait de "+RetraitController.getMontantRetrait()+" €");
        BorderPane pane= FXMLLoader.load(getClass().getResource("Ticket.fxml"));
        BpBillSelection.getChildren().setAll(pane);

    }
}
