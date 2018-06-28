/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.listskrypt;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import skrypcior.atena.pl.addskrypt.FXMLDocumentController;
import skrypcior.atena.pl.database.DatabaseConnect;

/**
 * FXML Controller class
 *
 * @author perlro1
 */
public class FXMLSkryptListController implements Initializable {
    
    ObservableList<Skrypt> list = FXCollections.observableArrayList();
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Skrypt> tavleView;
    @FXML
    private TableColumn<Skrypt, Integer> idCol;
    @FXML
    private TableColumn<Skrypt, String> nazwaCol;
    @FXML
    private TableColumn<Skrypt, String> srodowiskoCol;
    @FXML
    private TableColumn<Skrypt, String> data_utwCol;
    @FXML
    private TableColumn<Skrypt, String> operatorCol;
    @FXML
    private TableColumn<Skrypt, String> data_wyslCol;
    @FXML
    private TableColumn<Skrypt, String> statusCol;
    @FXML
    private TableColumn<Skrypt, String> przeladowanieCol;
    @FXML
    private TableColumn<Skrypt, String> zalezy_od_wersjiCol;
    @FXML
    private TableColumn<Skrypt, String> folderCol;
    @FXML
    private TableColumn<Skrypt, String> uwagiCol;
    @FXML
    private TableColumn<Skrypt, String> jiraCol;
    @FXML
    private TableColumn<Skrypt, String> odpowiedzialnyCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        uruchCol();
        DatabaseConnect databaseConnect = DatabaseConnect.getInstance();
        
        zaladuj();
    }    

    private void uruchCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nazwaCol.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        srodowiskoCol.setCellValueFactory(new PropertyValueFactory<>("srodowisko"));
        data_utwCol.setCellValueFactory(new PropertyValueFactory<>("data_utw"));
        operatorCol.setCellValueFactory(new PropertyValueFactory<>("operator"));
        data_wyslCol.setCellValueFactory(new PropertyValueFactory<>("data_wysl"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        przeladowanieCol.setCellValueFactory(new PropertyValueFactory<>("przeladowanie"));
        zalezy_od_wersjiCol.setCellValueFactory(new PropertyValueFactory<>("zalezy_od_wersji"));
        folderCol.setCellValueFactory(new PropertyValueFactory<>("folder"));
        jiraCol.setCellValueFactory(new PropertyValueFactory<>("jira"));
        odpowiedzialnyCol.setCellValueFactory(new PropertyValueFactory<>("odpowiedzialny"));
        uwagiCol.setCellValueFactory(new PropertyValueFactory<>("uwagi"));
    }

    private void zaladuj() {
        DatabaseConnect databaseConnect = DatabaseConnect.getInstance();
        String qu = "SELECT * FROM SKRYPTY";
        ResultSet rs = databaseConnect.execQuery(qu);
        try {
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String nazwa = rs.getString("nazwa");
                String srodowisko = rs.getString("srodowisko");
                String data_utw = rs.getString("data_utw");
                String operator = rs.getString("operator");
                String data_wysl = rs.getString("data_wysl");
                String status = rs.getString("status");
                String przeladowanie = rs.getString("przeladowanie"); 
                String zalezy_od_wersji = rs.getString("od_wersji");
                String folder = rs.getString("folder");
                String jira = rs.getString("jira");
                String odpowiedzialny = rs.getString("odpowiedzialny");
                String uwagi = rs.getString("uwagi");
                
                list.add(new Skrypt(id, nazwa, srodowisko, data_utw, operator, data_wysl, status, przeladowanie, zalezy_od_wersji, folder, jira, odpowiedzialny, uwagi));
                
                System.out.println(nazwa);
            }
        } catch (Exception ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE,null,ex);
        }
        
        tavleView.getItems().setAll(list);
    }
    
    public static class Skrypt{
        
        private final SimpleIntegerProperty  id;
        private final SimpleStringProperty  nazwa;
        private final SimpleStringProperty srodowisko;
        private final SimpleStringProperty data_utw;
        private final SimpleStringProperty operator;
        private final SimpleStringProperty data_wysl;
        private final SimpleStringProperty status;
        private final SimpleStringProperty przeladowanie;
        private final SimpleStringProperty zalezy_od_wersji;
        private final SimpleStringProperty folder;
        private final SimpleStringProperty jira;
        private final SimpleStringProperty odpowiedzialny;
        private final SimpleStringProperty uwagi;
        
        Skrypt(Integer id, String nazwa, String srodowisko, String data_utw, String operator,String data_wysl, String status, String przeladowanie, String zalezy_od_wersji, String folder, String jira, String odpowiedzialny, String uwagi){
            this.id = new SimpleIntegerProperty(id);
            this.nazwa = new SimpleStringProperty(nazwa); 
            this.srodowisko = new SimpleStringProperty(srodowisko);  
            this.data_utw  = new SimpleStringProperty(data_utw);  
            this.operator = new SimpleStringProperty(operator);
            this.data_wysl = new SimpleStringProperty(data_wysl);
            this.status = new SimpleStringProperty(status);
            this.przeladowanie = new SimpleStringProperty(przeladowanie);
            this.zalezy_od_wersji = new SimpleStringProperty(zalezy_od_wersji);
            this.folder = new SimpleStringProperty(folder);
            this.jira = new SimpleStringProperty(jira);
            this.odpowiedzialny = new SimpleStringProperty(odpowiedzialny);  
            this.uwagi = new SimpleStringProperty(uwagi);
        }

        public Integer getId() {
            return id.get();
        }

        public String getNazwa() {
            return nazwa.get();
        }

        public String getSrodowisko() {
            return srodowisko.get();
        }

        public String getData_utw() {
            return data_utw.get();
        }

        public String getOperator() {
            return operator.get();
        }

        public String getData_wysl() {
            return data_wysl.get();
        }

        public String getStatus() {
            return status.get();
        }

        public String getPrzeladowanie() {
            return przeladowanie.get();
        }

        public String getZalezy_od_wersji() {
            return zalezy_od_wersji.get();
        }

        public String getFolder() {
            return folder.get();
        }

        public String getUwagi() {
            return uwagi.get();
        }

        public String getJira() {
            return jira.get();
        }

        public String getOdpowiedzialny() {
            return odpowiedzialny.get();
        }
        
        
    }
}
