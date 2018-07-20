import javafx.beans.property.SimpleStringProperty;

public class Historique {
    public String date;
    public String operation;
    public String montant;


/*
constructeur de la class Historique
 */
    public Historique(String Date, String Operation, String Montant) {
        date = Date;
        operation = Operation;
        montant = Montant;
    }

/*
getter de la variable date
 */
    public String getDate() {
        return date;
    }

    /*
setter de la variable date
 */

    public void setDate(SimpleStringProperty date) {
        date = date;
    }

    /*
    getter de la variable operation
    */

    public String getOperation() {
        return operation;
    }

    /*
    setter de la variable operation
     */

    public void setOperation(String Operation) {
        operation = Operation;
    }

    /*
    getter de la variable montant
     */

    public String getMontant() {
        return montant;
    }
/*
redifintion de la fonction toString
 */
    @Override
    public String toString() {
        return "Historique{" +
                "Date='" + date + '\'' +
                ", Opration='" + operation + '\'' +
                ", Montant=" + montant +
                '}';
    }
/*
setter de la variable montant
 */

    public void setMontant(SimpleStringProperty montant) {
        montant = montant;
    }
}
