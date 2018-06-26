package skrypcior.atena.pl.skrypt.status;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import skrypcior.atena.pl.database.DatabaseConnect;


public class FXMLSkryptStatusController implements Initializable {
    @FXML
    private JFXTextField status;
    @FXML
    private JFXButton statusZapiszButton;
    @FXML
    private JFXButton statusAnulujButton;

   
    private DatabaseConnect databaseConnect;


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseConnect = new DatabaseConnect();
    }    

    @FXML
    private void zapiszStatus(ActionEvent event) {
        String nazwaStatus = status.getText();
        //chwilowo pozniej z clasy ktto się zalogowal
        String operatorStatus = "Rob1";
        
        if (nazwaStatus.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Wypełnij wszystkie pola");
            alert.showAndWait();
            return;
        }
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        String sqlDateUtw = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        
        String qu = "INSERT INTO SKRYPTY_STATUS (status, data_utw,operator) VALUES ("+
                    "'" + nazwaStatus + "' ," +
                    "'" + sqlDateUtw + "' ," +
                    "'" + "ROB1" + "'" +                               
                ")";
        System.out.println(qu);
        
        if (databaseConnect.execAction(qu)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Zapisano");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Błąd");
            alert.showAndWait();
        }
    }

    @FXML
    private void anulujStatus(ActionEvent event) {
    }
    
    
    
}
