/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController.EdTech;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author oucema
 */
public class FrontQuizController implements Initializable {

    @FXML
    JFXButton profilBtn;
    @FXML
    JFXButton quizBtn;
    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void profil(ActionEvent event) throws IOException {
        Stage stage = (Stage) profilBtn.getScene().getWindow();
        // do what you have to do
        stage.close();
        Stage primaryStage = new Stage();
        primaryStage.close();
        AnchorPane root = FXMLLoader.load(getClass().getResource("/GUIInterface/EdTech/ProfilView.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    private void quiz(ActionEvent event) throws IOException {
        Stage stage = (Stage) quizBtn.getScene().getWindow();
        // do what you have to do
        stage.close();
        Stage primaryStage = new Stage();
        primaryStage.close();
        AnchorPane root = FXMLLoader.load(getClass().getResource("/GUIInterface/EdTech/Quiz/MainView.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
