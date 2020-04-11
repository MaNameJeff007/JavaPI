/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Entities.Reclamation;
import Services.ReclamationService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Selim Chikh Zaouali
 */
public class AjouterReclamationController implements Initializable {

    @FXML
    private Button btn_ajout;
    @FXML
    private TextField parent;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ReclamationService rs = new ReclamationService();


        btn_ajout.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                     int int1 = Integer.parseInt(parent.getText());
                    rs.ajouterReclamation(new Reclamation(LocalDateTime.now(),"non traitee",17,int1 ));
                } catch (SQLException ex) {
                    Logger.getLogger(AjouterReclamationController.class.getName()).log(Level.SEVERE, null, ex);
                }
                FXMLLoader loader =new FXMLLoader(getClass().getResource("../GUIInterface/AfficherReclamation.fxml"));
                Parent root;
                try {
                    root=loader.load();
                    btn_ajout.getScene().setRoot(root);
                } catch (IOException ex) {
                    Logger.getLogger(AjouterReclamationController.class.getName()).log(Level.SEVERE, null, ex);
                }
        

            }
        });
        
    }    
    
}
