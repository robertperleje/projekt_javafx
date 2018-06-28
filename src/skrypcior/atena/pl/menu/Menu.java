package skrypcior.atena.pl.menu;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import skrypcior.atena.pl.database.DatabaseConnect;

/**
 *
 * @author perlro1
 */
public class Menu extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLMenu.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        DatabaseConnect.getInstance();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
