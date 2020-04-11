/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import zzzzzzzzz.ForumController;
import Services.ReclamationService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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

/**
 * FXML Controller class
 *
 * @author Selim Chikh Zaouali
 */
public class AfficherReclamationController implements Initializable {

    
    @FXML
    private TableView<?> tableview;

    @FXML
    private TableColumn<?, ?> date;

    @FXML
    private TableColumn<?, ?> etat;

    @FXML
    private TableColumn<?, ?> note;

    @FXML
    private TableColumn<?, ?> parent;

    @FXML
    private Button ajouter;

    @FXML
    private Button supprimer;

    @FXML
    void ajouterReclamation(ActionEvent event) {
        FXMLLoader loader =new FXMLLoader(getClass().getResource("../GUIInterface/AjouterReclamation.fxml"));
        Parent root;
            try {
                root=loader.load();
                ajouter.getScene().setRoot(root);
            } catch (IOException ex) {
                Logger.getLogger(AjouterReclamationController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    void clickItem(MouseEvent event) {
        if (event.getClickCount() == 1)
        {
            supprimer.setVisible(true);
        }
    }

    @FXML
    void retour(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUIInterface/Accueil.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Accueil");
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void supprimerReclamation(ActionEvent event) {
        ReclamationService rs=new ReclamationService();
        //as.deleteReclamation(tableview.getSelectionModel().getSelectedItem().getId());
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Reclamation supprimée");
        info.setContentText(" Done");
        info.show();
        refresh();
    }
    
    void refresh() {
        supprimer.setVisible(false);
        ReclamationService as= new ReclamationService();
        try {
            ObservableList obs = as.getOwner(Integer.parseInt(System.getProperty("id")));
            tableview.setItems(obs);
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ReclamationService rs = new ReclamationService();
        supprimer.setVisible(false);
        try {
            ObservableList obs = rs.getOwner(Integer.parseInt(System.getProperty("id")));
            tableview.setItems(obs);
            //System.out.println(as.getPrenom(101));
            //nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            //prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
            note.setCellValueFactory(new PropertyValueFactory<>("note"));
            parent.setCellValueFactory(new PropertyValueFactory<>("parent"));
        } catch (SQLException ex) {
            Logger.getLogger(AfficherReclamationAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
