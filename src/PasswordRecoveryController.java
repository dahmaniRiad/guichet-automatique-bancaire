import BDD.Centrale;
import BDD.Routage;
import Security.Securite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import static Security.Securite.getMd5;
import static Security.Securite.getSha256;

public class PasswordRecoveryController implements Initializable {
    @FXML
    PasswordField passwordField ;
    @FXML
    BorderPane Bpassword;
    @FXML
    Label passwordEtat;
    private int cnt;


    /*
    une fonction qui joue le rôle d'initialiser tout ce que doit installer avant l'affichage de la scene elle
    initialise valeur de passwordEtat par le vide pour éviter les problèmes lors de l'"exécution et elle initialise
    le compteur cnt par 1
    */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cnt=1;
        passwordEtat.setText("");

    }

    /*
    c'est la fonction responsable de tester le mode de passe lors de clics sur valider si le mode passe est incorrect
    elle affiche un MSG dessous du passer Field en rouge de dire que le mode de passe est erroné et elle donne
    la possibilité de 3 essie sinon si elle est rassemblée avec le mode de passe qui existe dans la base de données
    après appliquer s1256 sur les deux mots de passe , qui est récupérer dans la base de données et celui-ci entre par
    l'utilisateur si sont égaux elle change la page vers la page des opérations selon le type de client(externe ou interne)
     */
    public void validerClicked(javafx.event.ActionEvent actionEvent) throws IOException, SQLException, InterruptedException {
        Date date1=new Date();
        String tmp =String.valueOf(date1.getHours())+String.valueOf(date1.getDate())+String.valueOf(date1.getMonth())+String.valueOf(date1.getYear());
        String pass=passwordField.getText();
        if (AcceuilController.getMode().equals("internal")){
            Centrale.changerBD();
            System.out.println("Centrale : "+passwordField.getText());
            System.out.println(tmp);
            System.out.println(Securite.getSha256(Centrale.code(AcceuilController.getNumeroCarte()),tmp));
            System.out.println(Securite.getSha256(getMd5(pass),tmp));

            if(Securite.getSha256(Centrale.code(AcceuilController.getNumeroCarte()),tmp).equals(Securite.getSha256(Securite.getMd5(pass),tmp))){
                System.out.println("le code est correct");
                passwordEtat.setText("Your password has been confirmed");
                passwordEtat.setTextFill(Color.rgb(21,117,40));
                passwordField.setText("");
                BorderPane pane= FXMLLoader.load(getClass().getResource("InternalOperation.fxml"));
                Centrale.changerBD();
                Centrale.historique(AcceuilController.getNumeroCarte());
                int i=0;
                ObservableList<String[]> tab= FXCollections.observableArrayList();

                passwordEtat.setText("Your password has been confirmed");
                passwordEtat.setTextFill(Color.rgb(21,117,40));

                Bpassword.getChildren().setAll(pane);
            }
            else{
                System.out.println("le code est incorrect");
                passwordField.setText("");
                passwordEtat.setText("Your password is incorrect!");
                passwordEtat.setTextFill(Color.rgb(210,39,30));
            }

        }
        else {
            if(AcceuilController.getMode().equals("external")){
                System.out.println("Routage : "+passwordField.getText());
                Routage.changerBD();
                System.out.println(tmp);
                System.out.println(Securite.getSha256(Routage.code(AcceuilController.getNumeroCarte()),tmp));
                System.out.println(Securite.getSha256(getMd5(pass),tmp));
                if(Securite.getSha256(Routage.code(AcceuilController.getNumeroCarte()),tmp).equals(Securite.getSha256(Securite.getMd5(pass),tmp))){

                    System.out.println("le code est correct");
                    passwordEtat.setText("Your password has been confirmed");
                    passwordEtat.setTextFill(Color.rgb(21,117,40));
                    passwordField.setText("");
                    BorderPane pane= FXMLLoader.load(getClass().getResource("ExternalOperation.fxml"));
                    Centrale.changerBD();
                    Centrale.historique(AcceuilController.getNumeroCarte());
                    int i=0;
                    ObservableList<String[]> tab= FXCollections.observableArrayList();

                    passwordEtat.setText("Your password has been confirmed");
                    passwordEtat.setTextFill(Color.rgb(21,117,40));

                    Bpassword.getChildren().setAll(pane);
                }
                else{
                    System.out.println("le code est incorrect");
                    passwordField.setText("");
                    passwordEtat.setText("Your password is incorrect!");
                    passwordEtat.setTextFill(Color.rgb(210,39,30));
                }
            }
        }
        if(cnt>=3){
            cnt=0;
            BorderPane pane= FXMLLoader.load(getClass().getResource("CardDrag.fxml"));
            Bpassword.getChildren().setAll(pane);
        }
        else{
            cnt++;
        }
    }


    /*
   c'est la fonction responsable de tester le mode de passe lors de clics sur Entrer si le mode passe est incorrect
   elle affiche un MSG dessous du passer Field en rouge de dire que le mode de passe est erroné et elle donne
   la possibilité de 3 essie sinon si elle est rassemblée avec le mode de passe qui existe dans la base de données
   après appliquer s1256 sur les deux mots de passe , qui est récupérer dans la base de données et celui-ci entre par
   l'utilisateur si sont égaux elle change la page vers la page des opérations selon le type de client
   (externe ou interne)
     */
@FXML
    public void handleEnterPressed(KeyEvent keyEvent) throws SQLException, IOException {
        if(keyEvent.getCode()==KeyCode.ENTER){
            Date date1=new Date();
            String tmp =String.valueOf(date1.getHours())+String.valueOf(date1.getDate())+String.valueOf(date1.getMonth())+String.valueOf(date1.getYear());
            String pass=passwordField.getText();
            if (AcceuilController.getMode().equals("internal")){
                Centrale.changerBD();
                System.out.println("Centrale : "+passwordField.getText());
                System.out.println(tmp);
                System.out.println(Securite.getSha256(Centrale.code(AcceuilController.getNumeroCarte()),tmp));
                System.out.println(Securite.getSha256(getMd5(pass),tmp));

                if(Securite.getSha256(Centrale.code(AcceuilController.getNumeroCarte()),tmp).equals(Securite.getSha256(Securite.getMd5(pass),tmp))){
                    System.out.println("le code est correct");
                    passwordEtat.setText("Your password has been confirmed");
                    passwordEtat.setTextFill(Color.rgb(21,117,40));
                    passwordField.setText("");
                    BorderPane pane= FXMLLoader.load(getClass().getResource("InternalOperation.fxml"));
                    Centrale.changerBD();
                    Centrale.historique(AcceuilController.getNumeroCarte());
                    int i=0;
                    ObservableList<String[]> tab= FXCollections.observableArrayList();

                    passwordEtat.setText("Your password has been confirmed");
                    passwordEtat.setTextFill(Color.rgb(21,117,40));

                    Bpassword.getChildren().setAll(pane);
                }
                else{
                    System.out.println("le code est incorrect");
                    passwordField.setText("");
                    passwordEtat.setText("Your password is incorrect!");
                    passwordEtat.setTextFill(Color.rgb(210,39,30));
                }

            }
            else {
                if(AcceuilController.getMode().equals("external")){
                    System.out.println("Routage : "+passwordField.getText());
                    Routage.changerBD();
                    System.out.println(tmp);
                    System.out.println(Securite.getSha256(Routage.code(AcceuilController.getNumeroCarte()),tmp));
                    System.out.println(Securite.getSha256(getMd5(pass),tmp));
                    if(Securite.getSha256(Routage.code(AcceuilController.getNumeroCarte()),tmp).equals(Securite.getSha256(Securite.getMd5(pass),tmp))){

                        System.out.println("le code est correct");
                        passwordEtat.setText("Your password has been confirmed");
                        passwordEtat.setTextFill(Color.rgb(21,117,40));
                        passwordField.setText("");
                        BorderPane pane= FXMLLoader.load(getClass().getResource("ExternalOperation.fxml"));
                        Centrale.changerBD();
                        Centrale.historique(AcceuilController.getNumeroCarte());
                        int i=0;
                        ObservableList<String[]> tab= FXCollections.observableArrayList();

                        passwordEtat.setText("Your password has been confirmed");
                        passwordEtat.setTextFill(Color.rgb(21,117,40));

                        Bpassword.getChildren().setAll(pane);
                    }
                    else{
                        System.out.println("le code est incorrect");
                        passwordField.setText("");
                        passwordEtat.setText("Your password is incorrect!");
                        passwordEtat.setTextFill(Color.rgb(210,39,30));
                    }
                }
            }
                if(cnt>=3){
                    cnt=0;
                    BorderPane pane= FXMLLoader.load(getClass().getResource("CardDrag.fxml"));
                    Bpassword.getChildren().setAll(pane);
                }
                else{
                    cnt++;
            }

        }
}
/*
pour annuler l'etape de la eécuperation de la mot de passe et aller vers la page cardDrag.fxml
 */
    public void annulerClicked(javafx.event.ActionEvent actionEvent) throws IOException, SQLException, InterruptedException {
        BorderPane pane= FXMLLoader.load(getClass().getResource("CardDrag.fxml"));
        Bpassword.getChildren().setAll(pane);
    }
}
