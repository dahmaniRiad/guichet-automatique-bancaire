import BDD.Centrale;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static BDD.Centrale.*;

public class ShowHistoriqueController implements Initializable {
    @FXML private BorderPane BpShowHistorique;
    @FXML private TableView<Historique> tableHistoriqueTableView;
    @FXML private TableColumn<Historique, String> DATEColumn;
    @FXML private TableColumn<Historique, String> OPERATIONSColumn;
    @FXML private TableColumn<Historique, String> MONTANTColumn;


    /*
    une fonction qui joue le rôle d'initialiser tout ce que doit installer avant l'affichage de la scene elle
    initialise la table avec les noms de chaque columen avec son omet aussi d'adapter la taille du tableau avec
    le volume d'historique */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
         //set up the columns in the table
         DATEColumn.setCellValueFactory(new PropertyValueFactory<Historique,String>("date"));
         OPERATIONSColumn.setCellValueFactory(new PropertyValueFactory<Historique,String>("operation"));
         MONTANTColumn.setCellValueFactory(new PropertyValueFactory<Historique,String>("montant"));
         //load dummy data
         //    System.out.println(getHistorique());
        ObservableList<Historique> tmp=getHistorique();
        if(tmp.size()<=16)
         tableHistoriqueTableView.setPrefHeight(30*tmp.size());
        else{
            tableHistoriqueTableView.setPrefHeight(400);
        }
         tableHistoriqueTableView.setItems(tmp);


         }

         /*
         changer la page actuelle par Historique page
          */
    public void RetourClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        BorderPane pane= FXMLLoader.load(getClass().getResource("Historique.fxml"));
        BpShowHistorique.getChildren().setAll(pane);
    }
    /*
         changer la page actuelle par AutreOperation page apres lancer limpression du releve bancaire
          */
    public void ImprimerClicked(javafx.event.ActionEvent actionEvent) throws IOException, SQLException {
        Impression.impressionReleverBancaire();
        BorderPane pane= FXMLLoader.load(getClass().getResource("AutreOperation.fxml"));
        BpShowHistorique.getChildren().setAll(pane);
    }
    //this methode will return an observableListe of historique objects
    public ObservableList<Historique> getHistorique(){
            changerBD();
            try {
                Centrale.historique(AcceuilController.getNumeroCarte());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            ObservableList<Historique> tmp=FXCollections.observableArrayList();
            int i=0;
            while (i< Centrale.tabInfo_his.size()){
                tmp.add(new Historique(tabInfo_his.get(i)[0], tabInfo_his.get(i)[1], tabInfo_his.get(i)[2]));
                i++;
            }
            System.out.println(i);

        return tmp;

    }


        }

