import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TicketController implements Initializable {


    private static boolean ticket;
    @FXML private BorderPane BpTicket;

    /*
        une fonction qui joue le rôle d'initialiser tout ce que doit initialiser avant l'affichage de la scène    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
     ticket=false;
    }

    /*
    getter de la variable ticket
     */
    public static boolean isTicket() {
        return ticket;
    }


    /*
    setter de la variable ticket
     */
    public static void setTicket(boolean ticket) {
        TicketController.ticket = ticket;
    }


    /*
    changer la page actuelle par Confirmation page apres initialiser la variable a True
     */
    public void ouiClicked(javafx.event.ActionEvent actionEvent) throws IOException, SQLException {
        TicketController.setTicket(true);
        BorderPane pane= FXMLLoader.load(getClass().getResource("Confirmation.fxml"));
        BpTicket.getChildren().setAll(pane);
    }

    /*
    changer la page actuelle par Confirmation page apres initialiser la variable a false
     */

    public void nonClicked(javafx.event.ActionEvent actionEvent) throws IOException, SQLException {
        TicketController.setTicket(false);
        BorderPane pane= FXMLLoader.load(getClass().getResource("Confirmation.fxml"));
        BpTicket.getChildren().setAll(pane);
    }
}
