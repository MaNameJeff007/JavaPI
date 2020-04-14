/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Services.SujetC;
import Entities.Sujet;
import static com.mysql.jdbc.StringUtils.isNullOrEmpty;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class AjouterSujetController implements Initializable {

    @FXML
    private TextField titre;
    @FXML
    private TextArea description;
    @FXML
    private Button ajouter;
    @FXML
    private Button modifier;

    /**
     * Initializes the controller class.
     */

    @FXML
    void ajouterSujet(ActionEvent event) throws IOException, SQLException {

        SujetC sujet = new SujetC();
        Sujet s = new Sujet(Integer.parseInt(System.getProperty("id")), titre.getText(), description.getText(), LocalDateTime.now(), 0, 0);
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Add");
        info.setContentText(" Done");
        info.show();
        sujet.ajouterSujet(s);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUIInterface/Start.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void modifierSujet(ActionEvent event) throws IOException, SQLException {

        SujetC sujet = new SujetC();
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Add");
        info.setContentText(" Done");
        info.show();
        sujet.update(titre.getText(), description.getText(), Integer.parseInt(System.getProperty("sujet_id")));
        System.clearProperty("sujet_id");
        System.clearProperty("sujet_titre");
        System.clearProperty("sujet_description");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUIInterface/Start.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void cancel(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUIInterface/Start.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        System.clearProperty("sujet_id");
        System.clearProperty("sujet_titre");
        System.clearProperty("sujet_description");
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(System.getProperty("sujet_titre"));
        if (!isNullOrEmpty(System.getProperty("sujet_titre"))) {
            System.out.println("aa");
            titre.setText(System.getProperty("sujet_titre"));
            description.setText(System.getProperty("sujet_description"));
            ajouter.setVisible(false);
            modifier.setVisible(true);
        } else {
            System.out.println("bb");
            ajouter.setVisible(true);
            modifier.setVisible(false);
        }

    }

}
