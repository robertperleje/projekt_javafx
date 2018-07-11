/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.addskrypt;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import javax.swing.filechooser.FileNameExtensionFilter;
import skrypcior.atena.pl.database2.DbConnect;
import skrypcior.atena.pl.tools.RestrictiveTextField;
import skrypcior.atena.pl.tools.dataToString;
import skrypcior.atena.pl.tools.showInfoAlertBox;

/**
 *
 * @author perlro1
 */
public class FXMLDocumentController implements Initializable
{

    ObservableList<Skrypt> list = FXCollections.observableArrayList();
    Connection conn = DbConnect.createConnection();

    @FXML
    private JFXComboBox cmb_lp;
    ObservableList<String> lpList = FXCollections.observableArrayList("01", "02", "03");
    @FXML
    private JFXComboBox cmb_schemat;
    ObservableList<String> schematList = FXCollections.observableArrayList();
    @FXML
    private JFXComboBox cmb_przeladowac;
    ObservableList<String> przeladowacList = FXCollections.observableArrayList("Nie", "Tak");
    @FXML
    private JFXComboBox cmb_odwersji;
    ObservableList<String> odWersjiList = FXCollections.observableArrayList("Nie", "Tak");
    @FXML
    private JFXComboBox cmb_czy_zatrzymac;
    ObservableList<String> zatrzymacList = FXCollections.observableArrayList("Nie", "Tak");
    @FXML
    private JFXComboBox cmb_srodowisko;
    ObservableList<String> srodowiskoList = FXCollections.observableArrayList();
    @FXML
    private JFXComboBox cmb_odpowiedzialny;
    ObservableList<String> odpList = FXCollections.observableArrayList();
    @FXML
    private JFXTextField jira;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button btn_plik;
    @FXML
    private TextField sciezkaDoPliku;
    @FXML
    private TableView<Skrypt> table_skrypty;
    @FXML
    private TableColumn<Skrypt, Integer> col_id;
    @FXML
    private TableColumn<Skrypt, String> col_nazwa;
    @FXML
    private TableColumn<Skrypt, String> col_srodowisko;
    @FXML
    private TableColumn<Skrypt, String> col_date_utw;
    @FXML
    private TableColumn<Skrypt, String> col_operator;
    @FXML
    private TableColumn<Skrypt, String> col_date_wysl;
    @FXML
    private TableColumn<Skrypt, String> col_status;
    @FXML
    private TableColumn<Skrypt, String> col_przelad;
    @FXML
    private TableColumn<Skrypt, String> col_wersja;
    @FXML
    private TableColumn<Skrypt, String> col_folder;
    @FXML
    private TableColumn<Skrypt, String> col_jira;
    @FXML
    private TableColumn<Skrypt, String> col_odp;
    @FXML
    private TableColumn<Skrypt, String> col_uwagi;
    @FXML
    private TextArea text_uwaga;
    @FXML
    private Label l_jira;

    private ObservableList<String> listaStatus = FXCollections.observableArrayList();

    @FXML
    private Label label_lp;
    @FXML
    private Label label_czy_zatrzymac;
    @FXML
    private Label label_odpowiedzialny;
    @FXML
    private Label label_od_wersji;
    @FXML
    private Label label_schemat;
    @FXML
    private Label label_srodowisko;
    @FXML
    private Label label_przeladowanie;
    @FXML
    private Label label_jira;
    @FXML
    private Label label_sciezka;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        przypiszCol();
        zaladuj();

        cmb_lp.setItems(lpList);

        wczytajSchemat();

        cmb_czy_zatrzymac.setItems(zatrzymacList);
        wczytajSrod();
        wczytajUzyt();
        cmb_przeladowac.setItems(przeladowacList);
        cmb_odwersji.setItems(odWersjiList);
        wczytajStatus();

    }

    private void przypiszCol()
    {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        col_srodowisko.setCellValueFactory(new PropertyValueFactory<>("srodowisko"));
        col_date_utw.setCellValueFactory(new PropertyValueFactory<>("data_utw"));
        col_operator.setCellValueFactory(new PropertyValueFactory<>("operator"));
        col_date_wysl.setCellValueFactory(new PropertyValueFactory<>("data_wysl"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        col_przelad.setCellValueFactory(new PropertyValueFactory<>("przeladowanie"));
        col_wersja.setCellValueFactory(new PropertyValueFactory<>("zalezy_od_wersji"));
        col_folder.setCellValueFactory(new PropertyValueFactory<>("folder"));
        col_jira.setCellValueFactory(new PropertyValueFactory<>("jira"));
        col_odp.setCellValueFactory(new PropertyValueFactory<>("odpowiedzialny"));
        col_uwagi.setCellValueFactory(new PropertyValueFactory<>("uwagi"));

        table_skrypty.setEditable(true);
        //edycja 
        col_status.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), listaStatus));
        col_status.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Skrypt, String>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<Skrypt, String> event)
            {
                System.out.println("tak");
                
                ((Skrypt) event.getTableView().getItems().get(event.getTablePosition().getRow())).setStatus(event.getNewValue());
                //tu chyba update 
                //Skrypt skrypt = (Skrypt) table_skrypty.getSelectionModel().getSelectedItem();
                //SkryptyStany.SkryptUpdate(skrypt.getId());
                SkryptUpdate(event.getNewValue(),event.getRowValue().getId());
                
            }

        });
        //Dodanie koloru do wiersza
        table_skrypty.setRowFactory(row -> new TableRow<Skrypt>()
        {
            @Override
            public void updateItem(Skrypt item, boolean empty)
            {
                super.updateItem(item, empty);
                
             if (!empty) {
                            Skrypt obj = this.getTableView().getItems().get(getIndex());    

                            setText(obj.getStatus());                        
                            switch (obj.getStatus()) {
                                case "Utworzony":
                                    setStyle("-fx-background-color: #b6c5a3");
                                    break;
                                case "Przygotowany":
                                    setStyle("-fx-background-color: #eab30b");
                                    break;
                                case "Wysłany":
                                    setStyle("-fx-background-color: #6786da");
                                    break;
                                case "Wdrożony":
                                    setStyle("-fx-background-color: #07e74e");
                                    break;
                                case "Błąd":
                                    setStyle("-fx-background-color: #ea1c3e");
                                    break;
                                default:  
                                    break;
                            }
                        } else {
                            setText(null);
                        }
            }
        });

    }

    private void zaladuj()
    {
        try
        {
            list.clear();
            ResultSet rs = conn.createStatement().executeQuery("SELECT sk.Id,sk.Nazwa, w.nazwa, sk.Data_utw, sk.Operator, sk.Data_wysl, sks.nazwa, sk.Przeladowanie, sk.Od_wersji, sk.Folder, sk.Jira, k.login, sk.Uwagi "
                    + " FROM skrypty sk, skrypty_status sks, srodowisko w, konta k "
                    + " WHERE sk.Status = sks.id and sk.srodowisko = w.id and sk.odpowiedzialny = k.id ");
            while (rs.next())
            {
                list.add(new Skrypt(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13)));
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        table_skrypty.getItems().setAll(list);
    }

    @FXML
    private void addSkrypt(ActionEvent event) throws IOException, SQLException
    {

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        //Z czytujemy wszytskie wartości z formatki
        String skryptlp = (String) cmb_lp.getSelectionModel().getSelectedItem();
        String skryptSchemat = (String) cmb_schemat.getSelectionModel().getSelectedItem();
        String skryptZatrzymac = (String) cmb_czy_zatrzymac.getSelectionModel().getSelectedItem();
        String oznSrod = (String) cmb_srodowisko.getSelectionModel().getSelectedItem();
        String osobaOdp = (String) cmb_odpowiedzialny.getSelectionModel().getSelectedItem();
        String skryptPrzeladowanie = (String) cmb_przeladowac.getSelectionModel().getSelectedItem();
        String skryptCzyWersja = (String) cmb_odwersji.getSelectionModel().getSelectedItem();
        String skryptUwagi = text_uwaga.getText();
        String skryptJira = jira.getText().toUpperCase();
        String sciezka = sciezkaDoPliku.getText();

        boolean lpIsEmpty = RestrictiveTextField.StringIsEmpty(skryptlp, label_lp, "Wypełnij pole");
        boolean zatrzymacIsEmpty = RestrictiveTextField.StringIsEmpty(skryptZatrzymac, label_czy_zatrzymac, "Wypełnij pole");
        boolean odpIsEmpty = RestrictiveTextField.StringIsEmpty(osobaOdp, label_odpowiedzialny, "Wypełnij pole");
        boolean wersjaIsEmpty = RestrictiveTextField.StringIsEmpty(skryptCzyWersja, label_od_wersji, "Wypełnij pole");
        boolean schematIsEmpty = RestrictiveTextField.StringIsEmpty(skryptSchemat, label_schemat, "Wypełnij pole");
        boolean srodtIsEmpty = RestrictiveTextField.StringIsEmpty(oznSrod, label_srodowisko, "Wypełnij pole");
        boolean przeladIsEmpty = RestrictiveTextField.StringIsEmpty(skryptPrzeladowanie, label_przeladowanie, "Wypełnij pole");
        boolean jiraIsEmpty = RestrictiveTextField.StringIsEmpty(skryptJira, label_jira, "Wypełnij pole");
        boolean sciezkaIsEmpty = RestrictiveTextField.StringIsEmpty(sciezka, label_sciezka, "Wypełnij pole");

        if (!lpIsEmpty || !zatrzymacIsEmpty || !odpIsEmpty || !wersjaIsEmpty || !schematIsEmpty || !srodtIsEmpty || !przeladIsEmpty || !jiraIsEmpty || !sciezkaIsEmpty)
        {
            return;
        }

        //Weryfikacja pobranego stringa względem ilości znaków(ograniczenie kolumny na bazie)
        boolean nazwJira = RestrictiveTextField.textLenght(jira.getText(), l_jira, "Maksymalna ilość znaków 50", "50");
        if (!nazwJira)
        {
            return;
        }

        //Pobieramy id na potrzeby insertu do bazy
        int idSrod = pobierzIdSrod(oznSrod);
        int idOsobaOdp = pobierzIdOpekuna(osobaOdp);

        if (skryptZatrzymac.equals("Tak"))
        {
            skryptZatrzymac = "T";
        } else
        {
            skryptZatrzymac = "N";
        }

        String skryptDataUtw = dataToString.dataBezMysln();
        String skryptFolder = dataToString.dataZMysln();
        String skryptNazwa = skryptlp + "-" + skryptSchemat + "-" + skryptZatrzymac + "-" + skryptDataUtw + "_" + skryptJira;

        //Operator chwilowo jeden póxniej z tego kto się zalogojue
        String skryptOperator = "ROBERT1";

        String qu = "INSERT INTO SKRYPTY (nazwa,srodowisko,data_utw,operator,data_wysl,status,przeladowanie,od_wersji,folder,jira,odpowiedzialny,uwagi ,plik) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try
        {

            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);

            preparedStatement.setString(1, skryptNazwa);
            preparedStatement.setInt(2, idSrod);
            preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(4, skryptOperator);
            preparedStatement.setNull(5, java.sql.Types.DATE);
            preparedStatement.setString(6, "1");
            preparedStatement.setString(7, skryptPrzeladowanie);
            preparedStatement.setString(8, skryptCzyWersja);
            preparedStatement.setString(9, skryptFolder + "_" + oznSrod + "/' ,");
            preparedStatement.setString(10, skryptJira);
            preparedStatement.setInt(11, idOsobaOdp);
            preparedStatement.setString(12, skryptUwagi);
            InputStream inputStream = new FileInputStream(new File(sciezka));
            preparedStatement.setBlob(13, inputStream);

            System.out.println(qu);

        } catch (Exception ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            preparedStatement.execute();
            preparedStatement.close();
            showInfoAlertBox.showInformationAlertBox("Skrypt zapisano");
        }

        zaladuj();
    }

    @FXML
    private void anuluj(ActionEvent event)
    {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void loadWindowsFile(ActionEvent event)
    {

        FileChooser fileChooser = new FileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Skrypty", "txt");
        File selecFile = fileChooser.showOpenDialog(null);
        if (selecFile != null)
        {
            sciezkaDoPliku.setText(selecFile.getAbsolutePath());
        } else
        {
            System.out.println("Nie znaleziono pliku");
        }
    }

    @FXML
    private void usunWiersz(ActionEvent event)
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Skrypt skrypt = (Skrypt) table_skrypty.getSelectionModel().getSelectedItem();
        String qu = "DELETE FROM SKRYPTY where id = ?";
        try
        {
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setInt(1, skrypt.getId());
            System.out.println(skrypt.getNazwa());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        showInfoAlertBox.showInformationAlertBox("Rekord usunięto");
        zaladuj();
    }

    private void wczytajSchemat()
    {
        try
        {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM SKRYPTY_SCHEMAT");
            while (rs.next())
            {
                schematList.add(rs.getString(2));
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cmb_schemat.getItems().setAll(schematList);
    }

    private void wczytajUzyt()
    {
        try
        {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM KONTA");
            while (rs.next())
            {
                odpList.add(rs.getString(2));
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cmb_odpowiedzialny.getItems().setAll(odpList);
    }

    private void wczytajSrod()
    {
        try
        {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM SRODOWISKO");
            while (rs.next())
            {
                srodowiskoList.add(rs.getString(2));
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cmb_srodowisko.getItems().setAll(srodowiskoList);
    }

    private void wczytajStatus()
    {
        try
        {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM SKRYPTY_STATUS");
            while (rs.next())
            {
                listaStatus.add(rs.getString(2));
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Integer pobierzIdSrod(String oznSrod) throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try
        {
            String qu = ("SELECT * FROM SRODOWISKO WHERE NAZWA = ?");
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setString(1, oznSrod);
            System.out.println(qu);
            rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                return rs.getInt(1);
            }
            preparedStatement.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Integer pobierzIdOpekuna(String osobaOdp) throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try
        {
            String qu = ("SELECT * FROM KONTA WHERE login = ?");
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setString(1, osobaOdp);
            System.out.println(qu);
            rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                return rs.getInt(1);
            }
            preparedStatement.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /*@FXML
    public void zmienStatus(CellEditEvent edittedCell)
    {
        Skrypt skrypt = (Skrypt) table_skrypty.getSelectionModel().getSelectedItems();
        skrypt.setStatus(edittedCell.getNewValue().toString());
        SkryptUpdate((String) edittedCell.getNewValue(), skrypt.getId());
        showInfoAlertBox.showInformationAlertBox("Wchodzi?");
        
        
        
    }
    */
    
    public void SkryptUpdate(String newStatus, Integer idStatus){
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
        String qu = "UPDATE SKRYPTY SET STATUS = (SELECT id FROM SKRYPTY_STATUS WHERE NAZWA = ? ) where id = ?";
        try
        {
            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);
            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, idStatus);
            System.out.println(qu);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        showInfoAlertBox.showInformationAlertBox("Status Zminiono");
        
    }
}
