/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIInterface;


import Entities.Classe;
import Entities.Coeff;
import Entities.Matiere;
import Services.ServiceCoeff;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class AjouterCoeffController implements Initializable {

    @FXML
    private Text AjouterunCoeff;
    @FXML
    private Text nve;
    @FXML
    private Text mat;
    @FXML
    private Text val;
    @FXML
    private Button Valider;
    @FXML
    private Button Retour;
    @FXML
    private ComboBox<String> niveau ;
    
    //ObservableList<String> liste = FXCollections.observableArrayList("13");
    final ObservableList<String> liste = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> matiere;
   
    final ObservableList<String> matieres = FXCollections.observableArrayList();
    @FXML
    private TextField valeur;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fillcomboboxNiveau();
       fillcomboboxMatiere();
        niveau.setItems(liste);
        matiere.setItems(matieres);
        
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
     public void insertCoeff(ActionEvent event)
    {
        
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
       
        r.setValeur(Integer.parseInt(valeur.getText()));
       
    
        sr.ajouterCoeff(r);
        
 }    
 
  @FXML
    private void retourCoeff(ActionEvent event) {
          try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("AfficherCoeff.fxml"));
            Parent root= loader.load();
            AfficherCoeffController rc= loader.getController();
            
            Retour.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());;
        }
        
    }      
}
