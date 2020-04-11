/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIInterface;

import GUIInterface.AfficherClassesController;
import Entities.Classe;
import Services.ServiceClasse;
import Utils.JavaMail;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;


/**
 * FXML Controller class
 *
 * @author dell
 */
public class AjouterClasseController implements Initializable {

    @FXML
    private Text ajC;
    @FXML
    private Text lib;
    @FXML
    private TextField libelle;
    @FXML
    private TextField capacite;
    @FXML
    private TextField niveau;
    @FXML
    private Text cap;
    @FXML
    private Text niv;
    @FXML
    private Button Valider;
    @FXML
    private Button Retour;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
  
     public TextField getLibelle() {
        return libelle;
    }

    public void setLibelle(TextField nom) {
        this.libelle = nom;
    }
    
    public TextField getCapacite() {
        return capacite;
    }
    public void setCapacite(TextField c)
    {
        this.capacite=c;
    }
    
      public TextField getNiveau() {
        return niveau;
    }
    public void setNiveau(TextField c)
    {
        this.niveau=c;
    }
    
    public Button getAjouter() {
        return Valider;
    }

    public void setAjouter(Button ajouter) {
        this.Valider = ajouter;
    }
    
    
    public void insert(ActionEvent event)
    {
        
        Classe r = new Classe();
        r.setLibelle(libelle.getText());
        r.setCapacite(Integer.parseInt(capacite.getText()));
        r.setNiveau(Integer.parseInt(niveau.getText()));
       
        ServiceClasse sr=new   ServiceClasse();
        sr.ajouterClasse(r);
        
        
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
    
    
}
