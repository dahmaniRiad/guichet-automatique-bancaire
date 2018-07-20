import BDD.Centrale;
import BDD.Routage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConfirmationController implements Initializable{
    @FXML private BorderPane BpConfirmation;
    @FXML private Label operationLabel;
    private static String labelValue;
    private static String modeOperation;

/*
une fonction qui joue le rôle d'initialiser tout ce que doit installer avant l'affichage de la scene elle initialise
label de la page Confirmation par le texte passé par la class appelante
 */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        operationLabel.setText(labelValue);

    }


    /*
    setter de la variable LabelValue
    */
    public static void setValue(String textOperation) {
        labelValue=textOperation;
    }


    /*
    getter de la variable modeOperation
     */
    public static String getModeOperation() {
        return modeOperation;
    }


    /*
    setter de la variable modeOperation
    */
    public static void setModeOperation(String modeOperation) {
        ConfirmationController.modeOperation = modeOperation;
    }


    /*
    Alors Dun clic sur le bouton confirmerait le mode d'opération est Depot donc la fonction envoie une requête à
    l'aide de la focntiondepot de Central par le montant entré par l'utilisateur si le mode est Retrait elle lance
    un ensemble des tests elle compare le montant souhait retrai avec le seuil hebdomadaire le seuil quotidien et
    elle génère des boîtes de dialogue selon le cas sinon si tout est normal elle lance la requête de retrait avec
    le montant entrer tout ça c'est pour le client interne pour le client externe on a simplifié l'idée que
    la fonction-test sauf le montant si elle est inférieure de 500 euros pour réaliser l'opération de retrait de
    la classe Routage et rediriger le client vers la page qui demande le client s'il souhaite relisser une Opération
    sinon elle envoie le client vers la page de dialogue qui indique a le client que le montant entré est supérieur
    le montant autorisé alors Dun clic sur le bouton redirigerait le mode d'opération est Depot donc la fonction envoie
    une requête à l'aide de la focntiondepot de Central par le montant entré par l'utilisateur si le mode est Retrait
    elle lance un ensemble des tests elle compare le montant souhait retrai avec le seuil hebdomadaire le seuil
    quotidien et elle génère des boîtes de dialogue selon le cas sinon si tout est normal elle lance la requête
    de retrait avec le montant entrer tout ça c'est pour le client interne pour client externe on a simplifié l'idée
    que la fonction-test sauf le montant si elle est inférieure de 500 euros pour réaliser l'opération de retrait
    du classe Routage et rediriger le client vers la page qui demande le client s'il souhaite relisser une Opération
    sinon elle envoie le client vers la page de dialogue qui indique a le client que le montant entré est supérieur
    le montant autorisé
    */
    public void confirmationClicked(javafx.event.ActionEvent actionEvent) throws IOException, SQLException {

        System.out.println(modeOperation);
        if(modeOperation.equals("Depot")){
            Centrale.changerBD();
            Centrale.depot(AcceuilController.getNumeroCarte(),DepotController.getMontantDepot());
            System.out.println(DepotController.getMontantDepot());
            if(TicketController.isTicket()){
                Impression.impressionTicket(ConfirmationController.getModeOperation(),DepotController.getMontantDepot());
            }
            DepotController.setMontantDepot(0);
            BorderPane pane= FXMLLoader.load(getClass().getResource("AutreOperation.fxml"));
            BpConfirmation.getChildren().setAll(pane);
        }else{
            if(modeOperation.equals("Retrait")){
                switch (AcceuilController.getMode()){
                    case "internal":
                                Centrale.changerBD();
                                System.out.println(RetraitController.getMontantRetrait());
                                if (Centrale.getSeuilHabdomadaire(AcceuilController.getNumeroCarte())<RetraitController.getMontantRetrait()){
                                    DialogsController.setMsg("vous reste que "+Centrale.getSeuilHabdomadaire(AcceuilController.getNumeroCarte())+" € à retirer pour cette semaine");
                                    DialogsController.setMode("retrait");
                                    BorderPane pane= FXMLLoader.load(getClass().getResource("Dialogs.fxml"));
                                    BpConfirmation.getChildren().setAll(pane);
                                }else
                                    if (Centrale.getSeuilQuotidien(AcceuilController.getNumeroCarte())<RetraitController.getMontantRetrait()){
                                        DialogsController.setMsg("vous reste que "+Centrale.getSeuilQuotidien(AcceuilController.getNumeroCarte())+" € à retirer pour aujourd'hui");
                                        DialogsController.setMode("retrait");
                                        BorderPane pane= FXMLLoader.load(getClass().getResource("Dialogs.fxml"));
                                        BpConfirmation.getChildren().setAll(pane);
                                    }
                                        else{
                                            System.out.println(RetraitController.getMontantRetrait());
                                            Centrale.retrait(AcceuilController.getNumeroCarte(),RetraitController.getMontantRetrait());
                                            if(TicketController.isTicket()){
                                                Impression.impressionTicket(ConfirmationController.getModeOperation(),RetraitController.getMontantRetrait());
                                            }
                                            RetraitController.setMontantRetrait(0);
                                            modeOperation="";
                                            BorderPane pane= FXMLLoader.load(getClass().getResource("AutreOperation.fxml"));
                                            BpConfirmation.getChildren().setAll(pane);
                                        }
                                break;
                    case "external":
                        if(RetraitController.getMontantRetrait()<=500){
                                //Routage.changerBD();
                                System.out.println(RetraitController.getMontantRetrait());
                                Routage.retrait(AcceuilController.getNumeroCarte(),RetraitController.getMontantRetrait());
                                if(TicketController.isTicket()){
                                    Impression.impressionTicket(ConfirmationController.getModeOperation(),RetraitController.getMontantRetrait());

                                RetraitController.setMontantRetrait(0);
                                modeOperation="";
                                }
                                BorderPane pane= FXMLLoader.load(getClass().getResource("AutreOperation.fxml"));
                                BpConfirmation.getChildren().setAll(pane);
                                break;
                        }
                        else{
                            DialogsController.setMsg("vous ne pouvez pas retirer plus de 500 € ");
                            DialogsController.setMode("retrait");
                            BorderPane pane= FXMLLoader.load(getClass().getResource("Dialogs.fxml"));
                            BpConfirmation.getChildren().setAll(pane);
                        }

            }
        }
    }
    }
    /*
    quand le client tap sur le bouton annuler la fonction rediriger le client vers la page d'opérations selon le mode de client
    */
    public void annulerClicked(javafx.event.ActionEvent actionEvent) throws IOException{
        RetraitController.setMontantRetrait(0);
        DepotController.setMontantDepot(0);
        BorderPane pane= FXMLLoader.load(getClass().getResource(modeOperation+".fxml"));
        BpConfirmation.getChildren().setAll(pane);
    }
}
