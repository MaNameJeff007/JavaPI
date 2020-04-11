/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;


import Entities.Reclamation;
import Services.ReclamationService;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Selim Chikh Zaouali
 */
public class AfficherReclamationAdminController implements Initializable {

    
    
    
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ReclamationService rs = new ReclamationService();
        try {
            ArrayList<Reclamation> arrayList = (ArrayList<Reclamation>) rs.getAllReclamations();
            ObservableList obs = FXCollections.observableArrayList(arrayList);
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
