import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AutreOperationController implements Initializable {
/*
    une fonction qui joue le rôle d'initialiser tout ce que doit installer avant l'affichage de la scene elle initialise
     les valeurs de dépôt de la classe DepotController et de retrait de la classe RetraitControlleret le message
     de confirmation de la classe Confirmation Controller*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RetraitController.setMontantRetrait(0);
        DepotController.setMontantDepot(0);
        ConfirmationController.setValue("");
    }
@FXML
BorderPane BpAutreOperation;


    /*
    quand l'utilisateur appuie sur le button Oui la view lance cette méthode qui a comme rôle Dede changer la scène
    selon le type de client s'il est interne donc vers InternalOperation sinon vers ExternalOpration
    */
    public void ouiClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        System.out.println(AcceuilController.getMode());
        switch(AcceuilController.getMode()){
            case "internal":
                BorderPane pane= FXMLLoader.load(getClass().getResource("InternalOperation.fxml"));
                BpAutreOperation.getChildren().setAll(pane);
                break;
            case "external":
                BorderPane pane1= FXMLLoader.load(getClass().getResource("ExternalOperation.fxml"));
                BpAutreOperation.getChildren().setAll(pane1);
                break;
        }
    }
    /* quand l'utilsateur appuie sur le button Non la view lance cette methde qui a comme role de
    de changer la scene vers CardDrag*/
    public void nonClicked(javafx.event.ActionEvent actionEvent) throws IOException{
        AcceuilController.setMode(null);
        BorderPane pane= FXMLLoader.load(getClass().getResource("CardDrag.fxml"));
        BpAutreOperation.getChildren().setAll(pane);
    }
}
