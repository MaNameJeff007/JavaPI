/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIInterface;

import Entities.Classe;
import Entities.Coeff;
import Entities.Matiere;
import Services.ServiceClasse;
import Services.ServiceCoeff;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class ModifierCoeffController implements Initializable {

    @FXML
    private Text Title;
    @FXML
    private ComboBox<String> niveau;
    @FXML
    private ComboBox<String> matiere;
    @FXML
    private Text nivLabel;
    @FXML
    private Text matiereLabel;
    @FXML
    private Text valeurLabel;
    @FXML
    private Button Valider;
final ObservableList<String> liste = FXCollections.observableArrayList();
final ObservableList<String> matieres = FXCollections.observableArrayList();
    @FXML
    private TextField valeur;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        valeur.setText(String.valueOf(AfficherCoeffController.Recup.getValeur()));
        
         fillcomboboxNiveau();
       fillcomboboxMatiere();
        niveau.setItems(liste);
       
      ServiceCoeff cs = new ServiceCoeff();
      
       List<Classe> s = cs.ComboxGetLibClasse(AfficherCoeffController.Recup.getNiveau());    
         for (Classe e : s)
    {
        String sa =e.getLibelle();
     
      niveau.setValue(sa);
    }
        // matiere.setPromptText("jjjjj");
        matiere.setItems(matieres);
        matiere.setValue(AfficherCoeffController.Recup.getNomMatiere());
       //niveau.setValue(AfficherCoeffController.Recup.getNiveau());
         
    }    
   
    public void fillcomboboxMatiere()
    {
        ServiceCoeff sr=new   ServiceCoeff();
        List<Matiere> res = sr.fillMatieres();
        
          for (Matiere e:res)
    {
        int idC=e.getId();
        String name=e.getNom();
        matieres.add(name);
    }
 
    }
    public void fillcomboboxNiveau()
    {
        ServiceCoeff sr=new   ServiceCoeff();
        List<Classe> res = sr.fillNiv();
        
          for (Classe e:res)
    {
        int idC=e.getId();
        String name=e.getLibelle();
        liste.add(name);
    }
  
    }
   
    @FXML 
  private void validerModifCoeff(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Modification");
                            alert.setHeaderText(null);
                            alert.setContentText("voulez vous vraiment modifier ce coefficient ? ");
                            Optional<ButtonType> action = alert.showAndWait();
                            if (action.get() == ButtonType.OK)
                            {ServiceCoeff ac=new ServiceCoeff ();
         Coeff r = new Coeff();
         ServiceCoeff sr=new   ServiceCoeff();
         List<Classe> h= sr.ComboxGetIdClasse(niveau.getSelectionModel().getSelectedItem());
         for (Classe e : h)
    {
        int idC =e.getId();
        r.setNiveau(idC); 
    }
          List<Matiere> m= sr.ComboxGetIdMatiere(matiere.getSelectionModel().getSelectedItem());
         for (Matiere e : m)
    {
        int mat =e.getId();
        r.setMatiere(mat); 
    }
         r.setId(AfficherCoeffController.Recup.getId());
         r.setValeur(Integer.parseInt(valeur.getText()));
        ac.modifierCoeff(r); // insertion dans la base de données
          try {
 
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCoeffController.fxml"));
                                Parent root = loader.load();
                              AfficherCoeffController rc = loader.getController();
                               Valider.getScene().setRoot(root);
                               
             
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());

                            }
         
                            }
    
    }  
    
    
    
    
}
