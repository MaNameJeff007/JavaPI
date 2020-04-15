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
import Services.ReclamationService;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class AjouterAttestationController implements Initializable {

    @FXML
    private Button btn_ajout;

    @FXML
    private ComboBox<String> enfant;

    @FXML
    private Button retour;

    @FXML
    void retour(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUIInterface/Start.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Accueil");
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Integer UserConnecte = Integer.parseInt(System.getProperty("id"));
        AttestationService as = new AttestationService();
        ObservableList<String> enfants = FXCollections.observableArrayList();

        try {
            ResultSet res = as.getEnfants(UserConnecte);

            while (res.next()) {
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String idk = nom + " " + prenom;
                enfants.add(idk);
            }
            enfant.setItems(enfants);
        } catch (SQLException ex) {
            Logger.getLogger(AjouterAttestationController.class.getName()).log(Level.SEVERE, null, ex);
        }

        btn_ajout.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    String val = enfant.getSelectionModel().getSelectedItem();
                    //String val="Selim Zaouali";
                    int indexEspace = val.indexOf(" ");
                    String nomenf = val.substring(0, indexEspace);
                    String prenomenf = val.substring(indexEspace + 1);
                    String nomcomplet = prenomenf + " " + nomenf;
                    //int idk2 = as.getId(nomenf, prenomenf);
                    as.ajouterAttestation(new Attestation(LocalDateTime.now(), "non traitee", UserConnecte, nomcomplet));
                } catch (SQLException ex) {
                    Logger.getLogger(AjouterAttestationController.class.getName()).log(Level.SEVERE, null, ex);
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUIInterface/AfficherAttestation.fxml"));
                Parent root;
                try {
                    root = loader.load();
                    btn_ajout.getScene().setRoot(root);
                } catch (IOException ex) {
                    Logger.getLogger(AjouterAttestationController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

}
