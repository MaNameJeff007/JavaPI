/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUIController;

import Services.UserService;
import Entities.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author admin
 */

public class LoginController implements Initializable {

    @FXML private TextField username;
    @FXML private PasswordField mdp;
    @FXML private Label fLabel;
    
    
    @FXML
    void Login(ActionEvent event) throws IOException, SQLException 
    {
       // try {
            UserService ser = new UserService();
           if(ser.login(username.getText(), mdp.getText())){
                FXMLLoader fxmlLoader = new FXMLLoader();
                if(System.getProperty("role").contains("ELEVE")){
                fxmlLoader.setLocation(getClass().getResource("/GUIInterface/Start.fxml"));
                }
                else{
                    fxmlLoader.setLocation(getClass().getResource("/GUIInterface/BacK.fxml"));
                }
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("New Window");
                stage.setScene(scene);
                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            } else {
               Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("mdp");
        info.setContentText("ghalet");
        info.show();
            }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
