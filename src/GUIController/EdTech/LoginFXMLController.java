/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController.EdTech;

import Entities.FosUser;
import com.qoppa.pdfViewer.PDFViewerBean;
import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import Services.FosUserService;
import utils.mail;

/**
 * FXML Controller class
 *
 * @author 21654
 */
public class LoginFXMLController implements Initializable {

    @FXML
    private Button loginButton;
    @FXML
    private TextField UsernameText;
    @FXML
    private PasswordField PasswordText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String username = UsernameText.getText();
        String password = PasswordText.getText();
        FosUserService fus = new FosUserService();

        FosUser test = new FosUser();
        String roles = "";
        try {
            test = fus.Login(username, password);
            roles = fus.getRole(username);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        if (test != null) {
            System.setProperty("UserID", Integer.toString(test.getId()));
            if (roles.contains("ROLE_ADMIN")) {
                Stage stage = (Stage) loginButton.getScene().getWindow();
                // do what you have to do
                stage.close();
                Stage primaryStage = new Stage();
                primaryStage.close();
                AnchorPane root = FXMLLoader.load(getClass().getResource("/GUIInterface/Start.fxml"));
                primaryStage.setScene(new Scene(root));
                primaryStage.show();

            } else if (roles.contains("ROLE_AGENT") | roles.contains("ROLE_CLIENT")) {
               System.setProperty("type", String.valueOf(test.getTypeIntelligence()));
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();
                Stage primaryStage = new Stage();
                primaryStage.close();
                
                AnchorPane root = FXMLLoader.load(getClass().getResource("/GUIInterface/EdTech/FrontQuiz.fxml"));
                primaryStage.setTitle("Interface de cours");
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            }

        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Erreur d'authentification");
            alert.setHeaderText("Le nom d'utilisateur ou le mot de passe est incorrecte!");
            alert.setContentText("Vous pouvez reéssayer");

            alert.showAndWait();
        }
    }

}
