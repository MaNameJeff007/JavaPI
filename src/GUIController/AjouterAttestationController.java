/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
import Entities.Attestation;
import Services.AttestationService;
import Services.AttestationService;
import java.time.LocalDateTime;


public class AjouterAttestationController implements Initializable {

    @FXML
    private Button btn_ajout;
    @FXML
    private TextField nomprenom;
    @FXML
    private TextField parent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        AttestationService as = new AttestationService();


        btn_ajout.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                     //LocalDate date = LocalDate.now(); 
                     int int1 = Integer.parseInt(parent.getText());
                     //LocalDateTime ldt= rst.getTimestamp("date").toLocalDateTime();
                     System.out.println("1");
                    as.ajouterAttestation(new Attestation(LocalDateTime.now(),"non traitee",int1 ));
                } catch (SQLException ex) {
                    Logger.getLogger(AjouterAttestationController.class.getName()).log(Level.SEVERE, null, ex);
                }
                FXMLLoader loader =new FXMLLoader(getClass().getResource("../GUIInterface/AfficherAttestation.fxml"));
                Parent root;
                try {
                    root=loader.load();
                    btn_ajout.getScene().setRoot(root);
                } catch (IOException ex) {
                    Logger.getLogger(AjouterAttestationController.class.getName()).log(Level.SEVERE, null, ex);
                }
        

            }
        });
    }

}
