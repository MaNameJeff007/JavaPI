/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Entities.Attestation;
import Services.AttestationService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Selim Chikh Zaouali
 */
public class AfficherAttestationController implements Initializable {

    @FXML
    private TableView<Attestation> tableview;

    @FXML
    private TableColumn<Attestation, LocalDateTime> date;

    @FXML
    private TableColumn<Attestation, String> etat;

    @FXML
    private TableColumn<Attestation, String> enfant;

    @FXML
    private Button ajouter;

    @FXML
    private Button retour;

    @FXML
    private Button supprimer;

    @FXML
    public void clickItem(MouseEvent event) {
        if (event.getClickCount() == 1) {
            if (tableview.getSelectionModel().getSelectedItem().getEtat().equals("non traitee")) {
                supprimer.setVisible(false);
            } else {
                supprimer.setVisible(true);
            }
        }
    }

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

    @FXML
    void supprimerAttestation(ActionEvent event) throws SQLException {
        AttestationService as = new AttestationService();
        try {
            as.deleteAttestation(tableview.getSelectionModel().getSelectedItem().getId());
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Attestation supprimée");
            info.setContentText("Terminé !");
            info.show();
        } catch (SQLException e) {
        }

        refresh();
    }

    void refresh() {
        supprimer.setVisible(false);
        AttestationService as = new AttestationService();
        try {
            ObservableList obs = as.getOwner(Integer.parseInt(System.getProperty("id")));
            tableview.setItems(obs);
        } catch (SQLException ex) {
            Logger.getLogger(AfficherAttestationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void ajouterAttestation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUIInterface/AjouterAttestation.fxml"));
        Parent root;
        try {
            root = loader.load();
            ajouter.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(AjouterAttestationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AttestationService as = new AttestationService();
        supprimer.setVisible(false);
        try {
            ObservableList obs = as.getOwner(Integer.parseInt(System.getProperty("id")));
            tableview.setItems(obs);
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
            enfant.setCellValueFactory(new PropertyValueFactory<>("enfant"));
        } catch (SQLException ex) {
            Logger.getLogger(AfficherAttestationAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
