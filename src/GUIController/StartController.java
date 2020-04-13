/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class StartController implements Initializable {

    @FXML
    private Pane back;

    /**
     * Initializes the controller class.
     */
    @FXML
    void forum(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUIInterface/Forum.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    void affichage(String x) {
        Parent fxml;

        try {
            fxml = FXMLLoader.load(getClass().getResource(x));
            back.getChildren().removeAll();
            back.getChildren().setAll(fxml);

        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         affichage("/GUIInterface/Forum.fxml");
    }

}
