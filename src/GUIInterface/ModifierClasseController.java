/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIInterface;

import GUIInterface.AfficherClassesController;
import Services.ServiceClasse;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class ModifierClasseController implements Initializable {

    @FXML
    private Text ModC;
    @FXML
    private Text libC;
    @FXML
    private Text capC;
    @FXML
    private Text nivC;
    @FXML
    private Button Valider;
    @FXML
    private Button Retour;
    @FXML
    private TextField libelle;
    @FXML
    private TextField capacite;

    public Button getValider() {
        return Valider;
    }

    public void setValider(Button Valider) {
        this.Valider = Valider;
    }

    public Button getRetour() {
        return Retour;
    }

    public void setRetour(Button Retour) {
        this.Retour = Retour;
    }

    public TextField getLibelle() {
        return libelle;
    }

    public void setLibelle(TextField libelle) {
        this.libelle = libelle;
    }

    public TextField getCapacite() {
        return capacite;
    }

    public void setCapacite(TextField capacite) {
        this.capacite = capacite;
    }

    public TextField getNiveau() {
        return niveau;
    }

    public void setNiveau(TextField niveau) {
        this.niveau = niveau;
    }
    @FXML
    private TextField niveau;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          libelle.setText(AfficherClassesController.Recup.getLibelle());
        capacite.setText(String.valueOf(AfficherClassesController.Recup.getCapacite())); 
        niveau.setText(String.valueOf(AfficherClassesController.Recup.getNiveau()));
       
    }    
 
    @FXML
    private void retourafficher(ActionEvent event) {
          try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("AfficherClasses.fxml"));
            Parent root= loader.load();
            AfficherClassesController rc= loader.getController();            
            Retour.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());;
        }
        
    } 
    
@FXML
    private void validerModifClasse(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Modification");
                            alert.setHeaderText(null);
                            alert.setContentText("voulez vous vraiment modifier cette classe ? ");
                            Optional<ButtonType> action = alert.showAndWait();
                            if (action.get() == ButtonType.OK)
                            {ServiceClasse ac=new ServiceClasse ();
        
      
        ac.modifierClasse(AfficherClassesController.Recup.getId(),libelle.getText(), Integer.parseInt(capacite.getText()), Integer.parseInt(niveau.getText())); // insertion dans la base de données
          try {
 
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherClassesController.fxml"));
                                Parent root = loader.load();
                               AfficherClassesController rc = loader.getController();
                               libelle.getScene().setRoot(root);
                               
             
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());

                            }
         
                            }
    
    }

}
