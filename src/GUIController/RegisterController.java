/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Entities.User;
import Services.UserService;
import java.io.IOException;
import static java.lang.System.getProperty;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class RegisterController implements Initializable {

    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField email;
    @FXML
    private TextField emailv;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField passwordv;
    @FXML
    private ComboBox<String> rolev;
    @FXML
    private Label emaillabel;
    @FXML
    private Label passwordlabel;
    @FXML
    private Button addb;
    @FXML
    private Button done;
    @FXML
    private Pane back;
    int i = 0;
    User u = new User();

    /**
     * Initializes the controller class.
     */
    @FXML
    void verifyemail(KeyEvent event) {
        if (!email.getText().equals(emailv.getText())) {
            emaillabel.setVisible(true);
            emaillabel.setStyle("-fx-text-fill: red");
        } else {
            emaillabel.setVisible(false);
            i++;
        }
        if (i == 2) {
            addb.setVisible(true);
        }
    }

    @FXML
    void verifypassword(KeyEvent event) {
        if (!password.getText().equals(passwordv.getText())) {
            passwordlabel.setVisible(true);
            passwordlabel.setStyle("-fx-text-fill: red");
        } else {
            passwordlabel.setVisible(false);
            i++;
        }
        if (i == 2) {
            addb.setVisible(true);
        }
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUIInterface/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void add(ActionEvent event) throws SQLException, IOException {
        String msg = "";
        msg += "a:1:{i:0;s:10:\"ROLE_";
        msg += rolev.getSelectionModel().getSelectedItem();
        msg += "\";}";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        u.setNom(nom.getText());
        u.setPrenom(prenom.getText());
        u.setRoles(msg);
        u.setUsername(username.getText());
        u.setDate_Inscription(LocalDate.now().toString());
        u.setDate_Embauche(LocalDate.now().toString());
        u.setEnabled(1);
        u.setEmail(email.getText());
        u.setLast_login(timestamp);
        u.setPassword(u.hashPassword(password.getText()));
        UserService us = new UserService();

        int code = us.getcode();
        u.setCode(code);
        System.setProperty("code", Integer.toString(code));
        System.setProperty("email5", email.getText());
        if (getProperty("verif") != null) {
            System.out.println("1");
            if (System.getProperty("verif").equals("true")) {
                us.addUser(u);
               done.setVisible(true);
  
            }
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/GUIInterface/VerfiferEmail.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("New Window");
            stage.setScene(scene);
            stage.show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserService us = new UserService();
        rolev.getItems().add("ROLE_ELEVE");
        rolev.getItems().add("ROLE_PARENT");
        rolev.getItems().add("ROLE_ENSEIGNANT");
        emaillabel.setVisible(false);
        passwordlabel.setVisible(false);
        addb.setVisible(false);
        done.setVisible(false);
        done.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/GUIInterface/Login.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                stage.toBack();
                ((Node) (event.getSource())).getScene().getWindow().hide();
                event.consume();
            } catch (IOException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }
}
