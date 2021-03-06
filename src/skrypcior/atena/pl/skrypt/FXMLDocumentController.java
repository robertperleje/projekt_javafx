/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skrypcior.atena.pl.skrypt;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.mail.MessagingException;
import javax.swing.filechooser.FileNameExtensionFilter;
import skrypcior.atena.pl.database2.DbConnect;
import skrypcior.atena.pl.ftp.FTPFunctions;
import skrypcior.atena.pl.menu.FXMLMenuController;
import skrypcior.atena.pl.skrypty.email.CreateSkryptEmail;
import skrypcior.atena.pl.skrypty.stanwgrania.SkryptyStanWgraniaDao;
import skrypcior.atena.pl.skrypty.status.SkryptyStatusDao;
import skrypcior.atena.pl.skrypty.wgranie.FXMLSkryptyWgranieController;
import skrypcior.atena.pl.skrypty.wgranie.SkryptyWgranie;
import skrypcior.atena.pl.tools.OknoWyboru;
import skrypcior.atena.pl.tools.RestrictiveTextField;
import skrypcior.atena.pl.tools.WorkIndicatorDialog;
import skrypcior.atena.pl.tools.dataToString;
import skrypcior.atena.pl.tools.showInfoAlertBox;

/**
 *
 * @author perlro1
 */
public class FXMLDocumentController implements Initializable
{

    Connection conn = DbConnect.createConnection();

    private WorkIndicatorDialog wd = null;
    private String selection;

    @FXML
    private JFXComboBox cmb_lp;
    ObservableList<String> lpList = FXCollections.observableArrayList("01", "02", "03");

    @FXML
    private JFXComboBox cmb_schemat, cmb_przeladowac, cmb_odwersji, cmb_czy_zatrzymac, cmb_srodowisko, cmb_odpowiedzialny, cmb_bazyTestowe, cmb_czaswykonywania;

    ObservableList<String> schematList = FXCollections.observableArrayList();
    ObservableList<String> przeladowacList = FXCollections.observableArrayList("Nie", "Tak");
    ObservableList<String> odWersjiList = FXCollections.observableArrayList("Nie", "Tak");
    ObservableList<String> zatrzymacList = FXCollections.observableArrayList("Nie", "Tak");
    ObservableList<String> srodowiskoList = FXCollections.observableArrayList();
    ObservableList<String> odpList = FXCollections.observableArrayList();
    ObservableList<String> bazyList = FXCollections.observableArrayList("Nie, brak danych", "Tak");
    ObservableList<String> czaswykList = FXCollections.observableArrayList();
    ObservableList<String> listaStatus = FXCollections.observableArrayList();
    ObservableList<Skrypt> list = FXCollections.observableArrayList();

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
    private TableColumn<Skrypt, String> col_bazytestowe;
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
    private TextArea text_uwaga, text_opis;
    @FXML
    private Label label_lp, label_czy_zatrzymac, label_odpowiedzialny, label_od_wersji, label_schemat;
    @FXML
    private Label label_srodowisko, label_przeladowanie, label_jira, label_sciezka, label_wgracbazy, label_czaswykonywania, label_Opis;

    @FXML
    private DatePicker datePickerFaza3, datePickerPrep, datePickerProd;
    @FXML
    private MenuItem MenuItemMainAtena, MenuItemPrepAtena, MenuItemRelAtena;

    SkryptyStatusDao skryptyStatusDao = new SkryptyStatusDao();
    SkryptyStanWgraniaDao skryptyStanWgraniaDao = new SkryptyStanWgraniaDao();
    OknoWyboru oknoWyboru = new OknoWyboru();

    FXMLMenuController menuController = new FXMLMenuController();
    @FXML
    private Menu menuWdrozony;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        przypiszCol();
        zaladuj();

        cmb_lp.setItems(lpList);
        czasWyk();
        wczytajSchemat();
        cmb_bazyTestowe.setItems(bazyList);
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
        col_date_utw.setCellValueFactory(new PropertyValueFactory<>("datautw"));
        col_operator.setCellValueFactory(new PropertyValueFactory<>("operator"));
        col_date_wysl.setCellValueFactory(new PropertyValueFactory<>("datawysl"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        col_przelad.setCellValueFactory(new PropertyValueFactory<>("przeladowanie"));
        col_bazytestowe.setCellValueFactory(new PropertyValueFactory<>("bazytestowe"));
        col_wersja.setCellValueFactory(new PropertyValueFactory<>("odwersji"));
        col_folder.setCellValueFactory(new PropertyValueFactory<>("folder"));
        col_jira.setCellValueFactory(new PropertyValueFactory<>("jira"));
        col_odp.setCellValueFactory(new PropertyValueFactory<>("odpowiedzialny"));
        col_uwagi.setCellValueFactory(new PropertyValueFactory<>("uwagi"));

        table_skrypty.setEditable(true);
        //edycja 

        /*
        col_status.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), listaStatus));
        col_status.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Skrypt, String>>()
        {

            private String selection;

            @Override
            public void handle(TableColumn.CellEditEvent<Skrypt, String> event)
            {
                //ButtonType yesButtonType = new ButtonType("Tak");
                //ButtonType noButtonType = new ButtonType("Nie");

                //czy napewno zmieniamy status
                //Alert alert = new Alert(AlertType.CONFIRMATION, selection, yesButtonType, noButtonType);
                //alert.setTitle("Zmiana statusu");
                //alert.setHeaderText("Czy napewno chcesz zmienić status skryptu?");
                //alert.showAndWait();
                //if (alert.getResult() == yesButtonType)
                //{
                ((Skrypt) event.getTableView().getItems().get(event.getTablePosition().getRow())).setStatus(event.getNewValue());
                event.getTableView().getColumns().get(0).setVisible(false);
                event.getTableView().getColumns().get(0).setVisible(true);

                try
                {
                    
                    
                    
                    switch (event.getNewValue())
                    {
                        case "Przygotowany":
                            //zmieniamy status na bazie
                            dao.SkryptUpdate(event.getNewValue(), event.getRowValue().getId());
                            //wysyłamy maila zgodnie z nowym statusem
                            CreateSkryptEmail.createSkryptEmail(event.getNewValue(), event.getRowValue().getId());
                            break;
                        
                        case "Wysłany":
                            FTPFunctions.uploadFtp(event.getRowValue().getId(),modul);
                            //zmieniamy status na bazie
                            dao.SkryptUpdate(event.getNewValue(), event.getRowValue().getId());
                            //wysyłamy maila zgodnie z nowym statusem
                            CreateSkryptEmail.createSkryptEmail(event.getNewValue(), event.getRowValue().getId());
                            
                            break;
                        
                        default:
                            break;
                    }
                    

                } catch (MessagingException ex)
                {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex)
                {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex)
                {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex)
                {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                showInfoAlertBox.showInformationAlertBox("Status Zmieniono");
                //}
                //Przywrócenie starych
                event.getTableView().refresh();
            }

        });
         */
        //Dodanie koloru do wiersza
        table_skrypty.setRowFactory(row -> new TableRow<Skrypt>()
        {
            @Override
            public void updateItem(Skrypt item, boolean empty)
            {
                super.updateItem(item, empty);

                if (!empty)
                {
                    Skrypt obj = this.getTableView().getItems().get(getIndex());

                    setText(obj.getStatus());
                    switch (obj.getStatus())
                    {
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
                } else
                {
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
            table_skrypty.refresh();
            ResultSet rs = conn.createStatement().executeQuery("SELECT sk.id, sk.nazwa, srd.nazwa, sk.datautw, sk.operator, sk.datawysl, sks.nazwa, sk.hurtprzelad, sk.bazytestur, sk.odwersji, sk.folder, sk.jira, k.login, sk.uwagi "
                    + " FROM skrypty sk, skrypty_status sks, srodowisko srd, konta k "
                    + " WHERE sk.Statusid = sks.id and sk.srodowiskoid = srd.id and sk.opodp = k.id order by sk.id asc");
            while (rs.next())
            {
                list.add(new Skrypt(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14)));
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
        String oznSrod = (String) cmb_srodowisko.getSelectionModel().getSelectedItem();
        String skryptSchemat = (String) cmb_schemat.getSelectionModel().getSelectedItem();
        String czaswykonywania = (String) cmb_czaswykonywania.getSelectionModel().getSelectedItem();
        String skryptZatrzymac = (String) cmb_czy_zatrzymac.getSelectionModel().getSelectedItem();
        String skryptPrzeladowanie = (String) cmb_przeladowac.getSelectionModel().getSelectedItem();
        String bazytestowe = (String) cmb_bazyTestowe.getSelectionModel().getSelectedItem();
        String opis = text_opis.getText();
        String osobaOdp = (String) cmb_odpowiedzialny.getSelectionModel().getSelectedItem();
        String skryptCzyWersja = (String) cmb_odwersji.getSelectionModel().getSelectedItem();
        String skryptUwagi = text_uwaga.getText();
        String skryptJira = jira.getText().toUpperCase().replace("-", "_");
        String sciezka = sciezkaDoPliku.getText();

        boolean lpIsEmpty = RestrictiveTextField.StringIsEmpty(skryptlp, label_lp, "Wybierz wartość");
        boolean srodtIsEmpty = RestrictiveTextField.StringIsEmpty(oznSrod, label_srodowisko, "Wybierz wartość");
        boolean schematIsEmpty = RestrictiveTextField.StringIsEmpty(skryptSchemat, label_schemat, "Wybierz wartość");
        boolean zatrzymacIsEmpty = RestrictiveTextField.StringIsEmpty(skryptZatrzymac, label_czy_zatrzymac, "Wybierz wartość");
        boolean przeladIsEmpty = RestrictiveTextField.StringIsEmpty(skryptPrzeladowanie, label_przeladowanie, "Wybierz wartość");
        boolean wgracnabazyIsEmpty = RestrictiveTextField.StringIsEmpty(skryptPrzeladowanie, label_wgracbazy, "Wybierz wartość");
        boolean czasIsEmpty = RestrictiveTextField.StringIsEmpty(czaswykonywania, label_czaswykonywania, "Wybierz wartość");
        boolean odpIsEmpty = RestrictiveTextField.StringIsEmpty(osobaOdp, label_odpowiedzialny, "Wybierz wartość");
        boolean wersjaIsEmpty = RestrictiveTextField.StringIsEmpty(skryptCzyWersja, label_od_wersji, "Wybierz wartość");
        boolean jiraIsEmpty = RestrictiveTextField.StringIsEmpty(skryptJira, label_jira, "Wypełnij pole");
        boolean opissEmpty = RestrictiveTextField.StringIsEmpty(opis, label_Opis, "Wypełnij pole");
        boolean sciezkaIsEmpty = RestrictiveTextField.StringIsEmpty(sciezka, label_sciezka, "Wczytaj plik");

        if (!lpIsEmpty || !zatrzymacIsEmpty || !odpIsEmpty || !wersjaIsEmpty || !schematIsEmpty || !srodtIsEmpty || !przeladIsEmpty || !jiraIsEmpty || !sciezkaIsEmpty || !wgracnabazyIsEmpty || !czasIsEmpty || !opissEmpty)
        {
            return;
        }

        //Weryfikacja pobranego stringa względem ilości znaków(ograniczenie kolumny na bazie)
        boolean jiraTextField = RestrictiveTextField.textLenght(jira.getText(), label_jira, "Maksymalna ilość znaków 50", "50");
        boolean opisTextField = RestrictiveTextField.textLenght(opis, label_Opis, "Maksymalna ilość znaków 500", "500");
        /*
        if (!jiraTextField || !opisTextField)
        {
            return;
        }
         */
        //Pobieramy id na potrzeby insertu do bazy
        SkryptyDao dao = new SkryptyDao();
        int idSrod = dao.pobierzIdSrod(oznSrod);
        int idOsobaOdp = dao.pobierzIdOpekuna(osobaOdp);

        String skryptDataUtw = dataToString.dataBezMysln();
        String skryptFolder = dataToString.dataZMysln();
        String skryptNazwa = skryptlp + "-" + skryptSchemat + "-" + skryptZatrzymac.substring(0, skryptZatrzymac.length() - 2) + "-" + skryptDataUtw + "_" + skryptJira;

        //Operator chwilowo jeden póxniej z tego kto się zalogojue
        String skryptOperator = "ROBERT1";

        String qu = "INSERT INTO SKRYPTY (nazwa, srodowiskoid, schemat,czaswykonywania,zatrzymac_serwer,opis ,datautw, operator, datawysl, statusid, hurtprzelad, bazytestur, odwersji, folder, uwagi, jira, opodp, plik) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try
        {

            preparedStatement = (PreparedStatement) conn.prepareStatement(qu);

            preparedStatement.setString(1, skryptNazwa);
            preparedStatement.setInt(2, idSrod);
            preparedStatement.setString(3, skryptSchemat);
            preparedStatement.setString(4, czaswykonywania);
            preparedStatement.setString(5, skryptZatrzymac);
            preparedStatement.setString(6, opis);
            preparedStatement.setTimestamp(7, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(8, skryptOperator);
            preparedStatement.setNull(9, java.sql.Types.DATE);
            preparedStatement.setString(10, "1");
            preparedStatement.setString(11, skryptPrzeladowanie);
            preparedStatement.setString(12, bazytestowe);
            preparedStatement.setString(13, skryptCzyWersja);
            preparedStatement.setString(14, skryptFolder + "_" + oznSrod + "/");
            preparedStatement.setString(15, skryptUwagi);
            preparedStatement.setString(16, skryptJira);
            preparedStatement.setInt(17, idOsobaOdp);

            InputStream inputStream = new FileInputStream(new File(sciezka));
            preparedStatement.setBlob(18, inputStream);

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

    private void czasWyk()
    {
        try
        {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM SKRYPTY_SL_CZAS_WYKONYWANIA");
            while (rs.next())
            {
                czaswykList.add(rs.getString(2));
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cmb_czaswykonywania.getItems().setAll(czaswykList);
    }

    private void wykonanieSkryptu(Integer id, String srodowisko) throws SQLException, UnsupportedEncodingException
    {

        wd = new WorkIndicatorDialog(rootPane.getScene().getWindow(), "Czekaj, trwa wykonywanie skryptu na środowisku: " + srodowisko);

        wd.addTaskEndNotification(result ->
        {
            System.out.println(result);

            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("/skrypcior/atena/pl/skrypty/wgranie/FXMLSkryptyWgranie.fxml"));
            try
            {
                Loader.load();
            } catch (IOException ex)
            {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            FXMLSkryptyWgranieController display = Loader.getController();

            String wynik = null;
            try
            {
                wynik = SkryptyWgranie.uruchomSkrypt(id, srodowisko);
            } catch (SQLException ex)
            {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex)
            {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex)
            {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

            display.setText(wynik, id.toString(), srodowisko);

            Parent p = Loader.getRoot();
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Wynik wgrania skryptu");
            stage.setScene(new Scene(p));
            stage.showAndWait();

        });

        wd.exec(Boolean.FALSE, inputParam ->
        {
            for (int i = 0; i < 40; i++)
            {
                try
                {
                    Thread.sleep(500);
                    //String wynik = SkryptyWgranie.uruchomSkrypt(id, srodowisko);
                } catch (InterruptedException ex)
                {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return new Integer(1);
        });

    }

    @FXML
    private void znianaStatusPrzygotowany(ActionEvent event) throws MessagingException, SQLException
    {
        String nowyStatus = "Przygotowany";
        Skrypt rows = (Skrypt) table_skrypty.getSelectionModel().getSelectedItem();
        Boolean zmiana = oknoWyboru.oknoWyboruTakNie("Zmiana statusu", "Czy napewno chcesz zmienić status skryptu?");
        if (zmiana)
        {
            //zmieniamy status na bazie
            skryptyStatusDao.SkryptUpdate(nowyStatus, rows.getId());
            //wysyłamy maila zgodnie z nowym statusem
            CreateSkryptEmail.createSkryptEmail(nowyStatus, rows.getId());
        }
        zaladuj();
    }

    @FXML
    private void znianaStatusWyslany(ActionEvent event) throws SQLException, IOException, MessagingException, Exception
    {
        String nowyStatus = "Wysłany";
        String modul = "Skrypty";
        Skrypt rows = (Skrypt) table_skrypty.getSelectionModel().getSelectedItem();
        Boolean zmiana = oknoWyboru.oknoWyboruTakNie("Zmiana statusu", "Czy napewno chcesz zmienić status skryptu?");
        if (zmiana)
        {
            FTPFunctions.uploadFtp(rows.getId(), modul);
            //zmieniamy status na bazie
            skryptyStatusDao.SkryptUpdate(nowyStatus, rows.getId());
            //wysyłamy maila zgodnie z nowym statusem
            CreateSkryptEmail.createSkryptEmail(nowyStatus, rows.getId());
        }
    }

    @FXML
    private void potwierdzenieWdrozenia(ActionEvent event) throws MessagingException, SQLException
    {
        String nowyStatus = "Wdrożony";
        String srod = null;
        String data_wdr = null;
        Skrypt rows = (Skrypt) table_skrypty.getSelectionModel().getSelectedItem();
        
        //menuWdrozony.
        
        if (datePickerFaza3.getValue() != null)
        {
            srod = datePickerFaza3.promptTextProperty().getValue();
            data_wdr = datePickerFaza3.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        if (datePickerPrep.getValue() != null)
        {
            srod = datePickerPrep.promptTextProperty().getValue();
            data_wdr = datePickerPrep.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        if (datePickerProd.getValue() != null)
        {
            srod = datePickerProd.promptTextProperty().getValue();
            data_wdr = datePickerProd.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        int wgrany = skryptyStanWgraniaDao.selectCzyUruchomiony(rows.getId(), srod);
        if (wgrany != 0)
        {
            Boolean zmiana = oknoWyboru.oknoWyboruTakNie("Zmiana statusu", "Skrypt był wdrożony na tym środowisku. Czy chcesz ponownie zaznaczyć skrypt jako wykonany?");
            if (zmiana)
            {
                skryptyStatusDao.SkryptUpdate(nowyStatus, rows.getId());
                skryptyStanWgraniaDao.insertRows(rows.getId(), srod, "OK", "OK");
                skryptyStanWgraniaDao.updateRowsDate(rows.getId(), srod, data_wdr);
                zaladuj();
            } else
            {
                return;
            }
        } else
        {
            skryptyStatusDao.SkryptUpdate(nowyStatus, rows.getId());
            skryptyStanWgraniaDao.insertRows(rows.getId(), srod, "OK", "OK");
            skryptyStanWgraniaDao.updateRowsDate(rows.getId(), srod, data_wdr);
            zaladuj();
        }

    }

    @FXML
    private void uruchomSkryptNaSrod(ActionEvent event) throws SQLException, UnsupportedEncodingException
    {
        Skrypt selectedForRecord = table_skrypty.getSelectionModel().getSelectedItem();
        String srod = null;
        
        

        if (selectedForRecord == null)
        {
            showInfoAlertBox.showInformationAlertBox("Nie wybrano żadnego rekordu");
            return;
        }
        //czy byl juz uruchomiony
        if (MenuItemMainAtena.getText() != null)
        {
            srod = MenuItemMainAtena.getText();
        }
        if (MenuItemPrepAtena.getText() != null)
        {
            srod = MenuItemPrepAtena.getText();
        }
        if (MenuItemRelAtena.getText() != null)
        {
            srod = MenuItemRelAtena.getText();
        }

        int wgrany = skryptyStanWgraniaDao.selectCzyUruchomiony(selectedForRecord.getId(), srod);

        if (wgrany != 0)
        {
            Boolean zmiana = oknoWyboru.oknoWyboruTakNie("Status wgrania skryptu", "Skrypt był już uruchomiony na tym środowisku. Ponowne wykonanie może zwrócić błąd. Czy uruchmić ponownie... ?");
            if (zmiana)
            {
                wykonanieSkryptu(selectedForRecord.getId(), srod);
            } else
            {
                return;
            }
        } else
        {
            wykonanieSkryptu(selectedForRecord.getId(), srod);
        }
    }

}
