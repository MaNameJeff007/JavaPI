/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Entities.Permutation;
import Services.AttestationService;
import Services.PermutationService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import zzzzzzzzz.ForumController;

/**
 * FXML Controller class
 *
 * @author Selim Chikh Zaouali
 */
public class AfficherPermutationController implements Initializable {

    @FXML
    private TableView<Permutation> tableview;

    @FXML
    private TableColumn<Permutation, String> nomprenom;

    @FXML
    private TableColumn<Permutation, String> classe_s;

    @FXML
    private TableColumn<Permutation, LocalDateTime> date;

    @FXML
    private TableColumn<Permutation, String> etat;

    @FXML
    private Button ajouter;

    @FXML
    private Button supprimer;

    @FXML
    private Button retour;

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

    void refresh() {
        supprimer.setVisible(false);
        PermutationService ps = new PermutationService();
        try {
            ObservableList obs = ps.getOwner(Integer.parseInt(System.getProperty("id")));
            tableview.setItems(obs);
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void ajouterPermutation(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUIInterface/AjouterPermutation.fxml"));
        Parent root;
        try {
            root = loader.load();
            ajouter.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(AjouterPermutationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void supprimerPermutation(ActionEvent event) throws SQLException {
        PermutationService ps = new PermutationService();
        try {
            ps.deletePermutation(tableview.getSelectionModel().getSelectedItem().getId());
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Permutation supprimée");
            info.setContentText("Terminé !");
            info.show();
        } catch (SQLException e) {
        }
        refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PermutationService ps = new PermutationService();
        supprimer.setVisible(false);
        try {
            ObservableList obs = ps.getOwner(Integer.parseInt(System.getProperty("id")));
            tableview.setItems(obs);
            classe_s.setCellValueFactory(new PropertyValueFactory<>("classe_s"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
            nomprenom.setCellValueFactory(new PropertyValueFactory<>("enfant"));
        } catch (SQLException ex) {
            Logger.getLogger(AfficherAttestationAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
