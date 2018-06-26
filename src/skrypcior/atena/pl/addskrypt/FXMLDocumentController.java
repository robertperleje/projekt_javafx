/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.addskrypt;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.naming.spi.DirStateFactory;
import skrypcior.atena.pl.database.DatabaseConnect;
import skrypcior.atena.pl.tools.dataToString;


/**
 *
 * @author perlro1
 */
public class FXMLDocumentController implements Initializable {
   
    
    
    private Label label;
    @FXML
    private JFXComboBox cmb_lp;
    ObservableList<String> lpList = FXCollections.observableArrayList("01","02","03");
    @FXML
    private JFXComboBox cmb_schemat;
    ObservableList<String> schematList = FXCollections.observableArrayList("PART","OLIMP","DBM","DWHARCHITECT","HURT","APEX_REPORTS_APP");
    @FXML
    private JFXComboBox cmb_przeladowac;
    ObservableList<String> przeladowacList = FXCollections.observableArrayList("Nie","Tak");
    @FXML
    private JFXComboBox cmb_odwersji;
    ObservableList<String> odWersjiList = FXCollections.observableArrayList("Nie","Tak");
    @FXML
    private JFXComboBox cmb_czy_zatrzymac;
    ObservableList<String> zatrzymacList = FXCollections.observableArrayList("Nie","Tak");
    @FXML
    private JFXComboBox cmb_srodowisko;
    ObservableList<String> srodowiskoList = FXCollections.observableArrayList("FAZA3","PREP","PROD");
    @FXML
    private JFXComboBox cmb_odpowiedzialny;
    ObservableList<String> odpowiedzialnyList = FXCollections.observableArrayList("J. Sztokinger","","K. Derkowski","K. Bławat");
    @FXML
    private JFXTextField jira;
    @FXML
    private JFXTextField tresc;
    
    @FXML
    private JFXComboBox cmb_test;
    ObservableList<String> testList = FXCollections.observableArrayList();
    
    @FXML
    private JFXButton zapiszButton;
    @FXML
    private JFXButton anulujButton;
    
    
    private DatabaseConnect databaseConnect;
    //private Date data;
    
    @FXML
    private AnchorPane rootPane;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseConnect = new DatabaseConnect();
        test();
        cmb_lp.setItems(lpList);
        cmb_schemat.setItems(schematList);
        cmb_czy_zatrzymac.setItems(zatrzymacList);
        cmb_srodowisko.setItems(srodowiskoList);
        cmb_odpowiedzialny.setItems(odpowiedzialnyList);
        cmb_przeladowac.setItems(przeladowacList);
        cmb_odwersji.setItems(odWersjiList);
        
        cmb_test.setItems(testList);
        sprawdzNazwe();
        
    }    

    @FXML
    private void addSkrypt(ActionEvent event) {
        
               
        String skryptlp = (String) cmb_lp.getSelectionModel().getSelectedItem();
        String skryptSchemat = (String) cmb_schemat.getSelectionModel().getSelectedItem();
        String skryptZatrzymac = (String) cmb_czy_zatrzymac.getSelectionModel().getSelectedItem();
        String skryptSrodowisko = (String) cmb_srodowisko.getSelectionModel().getSelectedItem();
        String skryptOdpowiedzialny = (String) cmb_odpowiedzialny.getSelectionModel().getSelectedItem();
        String skryptPrzeladowanie = (String) cmb_przeladowac.getSelectionModel().getSelectedItem();
        String skryptCzyWersja = (String) cmb_odwersji.getSelectionModel().getSelectedItem();
        
        String skryptTresc = tresc.getText();
        String skryptJira = jira.getText();
        
        if(skryptlp.isEmpty() || skryptSchemat.isEmpty() || skryptZatrzymac.isEmpty() || skryptSrodowisko.isEmpty() ||  skryptOdpowiedzialny.isEmpty() || skryptTresc.isEmpty() || skryptJira.isEmpty()
                || skryptPrzeladowanie.isEmpty() || skryptCzyWersja.isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Wypełnij wszystkie pola");
            alert.showAndWait();
            return;
        }
        
        if (skryptZatrzymac.equals("Tak")) {
            skryptZatrzymac = "T";
        } else {
            skryptZatrzymac = "N";
        }
        
        String skryptDataUtw = dataToString.dataBezMysln();
        String skryptFolder = dataToString.dataZMysln();
        String skryptNazwa = skryptlp + "-" + skryptSchemat + "-" + skryptZatrzymac + "-" + skryptDataUtw + "_" + skryptJira ;
        
       // Date sqlDateUtw = new Date(data.getDate());
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        String sqlDateUtw = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        
        
        
        //Operator chwilowo jeden póxniej z tego kto się zalogojue
        String skryptOperator = "ROBERT1";
        Date sqlDataWys = null;
        
        String qu = "INSERT INTO SKRYPTY (nazwa,srodowisko,data_utw,operator,data_wysl,status,przeladowanie,od_wersji,folder,tresc,jira,odpowiedzialny) VALUES ("+
                //"'" + skryptId + "'," +
                "'" + skryptNazwa + "' ," +
                "'" + skryptSrodowisko + "' ," +
                "'" + sqlDateUtw + "' ," +
                "'" + skryptOperator + "'" +
                "," + sqlDataWys + "," +
                "'" + "Utworzony" + "' ," +
                "'" + skryptPrzeladowanie + "' ," +
                "'" + skryptCzyWersja + "' ," +
                "'" + skryptFolder + "_" + skryptSrodowisko + "/' ," +
                "'" + skryptTresc + "' ," +
                "'" + skryptJira + "' ," +
                "'" + skryptOdpowiedzialny + "'" +
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
    private void anuluj(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
        
    }
    
   

    private void sprawdzNazwe() {
      String qu = "SELECT nazwa FROM SKRYPTY";
        ResultSet rs = databaseConnect.execQuery(qu);
        try {
            while (rs.next()) {
                String nazwa = rs.getString("nazwa");
                System.out.println(nazwa);
            }
        } catch (Exception ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    private void test() {
       String qu = "SELECT status FROM SKRYPTY_STATUS";
       ResultSet rs = databaseConnect.execQuery(qu);
       try {
            while (rs.next()) {
                testList.add(rs.getString("status"));
                System.out.println(testList);
            }
        } catch (Exception ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
}

    

