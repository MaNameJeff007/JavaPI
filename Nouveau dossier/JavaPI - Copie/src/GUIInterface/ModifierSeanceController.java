/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIInterface;

import Entities.Classe;
import Entities.Coeff;
import Entities.Matiere;
import Entities.Salle;
import Entities.Seance;
import Entities.User;
import Services.ServiceCoeff;
import Services.ServiceSeance;
import Services.ServiceUser;
import Utils.JavaMail;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class ModifierSeanceController implements Initializable {

    @FXML
    private ComboBox<String> jour;
    @FXML
    private ComboBox<String> hdeb;
    @FXML
    private ComboBox<String> enseignant;
    @FXML
    private ComboBox<String> classe;
    @FXML
    private Text title;
    @FXML
    private ComboBox<String> matiere;
    @FXML
    private ComboBox<String> salle;
 
    final ObservableList<String> jours = FXCollections.observableArrayList("lundi","mardi","mercredi","jeudi","vendredi","samedi");
    final ObservableList<String> h = FXCollections.observableArrayList("08:00:00","10:15:00","12:00:00","13:00:00","15:15:00");
   final ObservableList<String> ens = FXCollections.observableArrayList();
    final ObservableList<String> classes = FXCollections.observableArrayList();
     final ObservableList<String> matieres = FXCollections.observableArrayList();
      final ObservableList<String> salles = FXCollections.observableArrayList();
    @FXML
    private Button Valider;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillcomboboxNiveau();
       fillcomboboxMatiere();
       fillcomboboxSalle();
       fillcomboboxEns();
       ServiceCoeff cs = new ServiceCoeff();
       /*
       List<Classe> s = cs.ComboxGetLibClasse(AfficherSeanceController.Recup.getClasse());    
         for (Classe e : s)
    {
        String sa =e.getLibelle();
     
      classe.setValue(sa);
    }
         
        List<Matiere> s1 = cs.ComboxGetNomMatiere(AfficherSeanceController.Recup.getMatiere());    
         for (Matiere e : s1)
    {
        String sa =e.getNom();
        matiere.setValue(sa);
    }*/
       
          classe.setValue(AfficherSeanceController.Recup.getClasse());
         matiere.setValue(AfficherSeanceController.Recup.getMatiere());
         jour.setValue(AfficherSeanceController.Recup.getJour());
         hdeb.setValue(AfficherSeanceController.Recup.getHdeb());
         enseignant.setValue(AfficherSeanceController.Recup.getEnseignant());
          salle.setValue(AfficherSeanceController.Recup.getSalle());
          
        classe.setItems(classes);
        matiere.setItems(matieres);
        jour.setItems(jours);
        hdeb.setItems(h);
        enseignant.setItems(ens);
        salle.setItems(salles);

    
    }    
 
    public void fillcomboboxMatiere()
    {
        ServiceCoeff sr=new   ServiceCoeff();
        List<Matiere> res = sr.fillMatieres();
        
          for (Matiere e:res)
    {
        String idC=e.getId();
        String name=e.getNom();
        matieres.add(name);
    }
 
    }
    
    public void fillcomboboxSalle()
    {
        ServiceSeance sr=new    ServiceSeance();
        List<Salle> res = sr.fillSalles();
        
          for (Salle e:res)
    {
       String idS=e.getId();
        String salle = e.getLibelle();
        salles.add(salle);
    }
 
    }
    
    public void fillcomboboxEns()
    {
        ServiceUser sr=new    ServiceUser();
        List<User> res = sr.afficherEns();
        
          for (User e:res)
    {
        String idS=String.valueOf(e.getIdentifiant());
        String nom = e.getNom();
        String pre = e.getPrenom();
        ens.add(nom);
    }
 
    }
    
    public void fillcomboboxNiveau()
    {
        ServiceCoeff sr=new   ServiceCoeff();
        List<Classe> res = sr.fillNiv();
        
          for (Classe e:res)
    {
      String idC=e.getId();
        String name=e.getLibelle();
        classes.add(name);
    }
}

      @FXML 
  private void validerModifSeance(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Modification");
                            alert.setHeaderText(null);
                            alert.setContentText("voulez vous vraiment modifier cette seance ? ");
                            Optional<ButtonType> action = alert.showAndWait();
                            if (action.get() == ButtonType.OK)
                            {ServiceSeance ac=new ServiceSeance ();
        Seance r = new Seance();
         ServiceCoeff sr=new   ServiceCoeff();
          //ServiceSeance sr1=new   ServiceSeance();
            ServiceUser us=new   ServiceUser();
         List<Classe> h= sr.ComboxGetIdClasse(classe.getSelectionModel().getSelectedItem());
         
         
         for (Classe e : h)
    {
        String idC =e.getId();
        r.setClasse(idC); 
    }
          
         
         List<Matiere> m= sr.ComboxGetIdMatiere(matiere.getSelectionModel().getSelectedItem());
         
         for (Matiere e : m)
    {
        String mat =e.getId();
        r.setMatiere(mat); 
    }
         
     List<Salle> s = ac.ComboxGetIdSalle(salle.getSelectionModel().getSelectedItem());    
         for (Salle e : s)
    {
        String sa =e.getId();
        r.setSalle(sa); 
    }
         
         List<User> uen = us.ComboxGetIdEns(enseignant.getSelectionModel().getSelectedItem());    
         for (User e : uen)
    {
        String ens =String.valueOf(e.getIdentifiant());
        r.setEnseignant(ens); 
    }
         
         r.setId(AfficherSeanceController.Recup.getId());
         r.setJour(jour.getSelectionModel().getSelectedItem());
         r.setHdeb(hdeb.getSelectionModel().getSelectedItem());
         
        
         if (hdeb.getSelectionModel().getSelectedItem().equals("08:00:00"))
         {
              r.setHfin("10:00:00");  
         }
         else if(hdeb.getSelectionModel().getSelectedItem().equals("10:15:00"))
         {
          r.setHfin("12:00:00");   
         }
         else if(hdeb.getSelectionModel().getSelectedItem().equals("12:00:00"))
         {
          r.setHfin("13:00:00");   
         }
         else if(hdeb.getSelectionModel().getSelectedItem().equals("13:00:00"))
         {
          r.setHfin("15:00:00");   
         }
         
         else 
         {
          r.setHfin("17:00:00");   
         }
        
         for (User e : uen)
    {
       String ens =String.valueOf(e.getIdentifiant());
       List<User> emE= us.sEns(ens);
        System.out.println(ens);
                               for ( User u : emE)
                                {
                               String emailEns = u.getEmail();
                               System.out.println(emailEns);
         ac.modifierSeance(r,emailEns); 
    } 
         
        
                                }
       /* ServiceUser ue = new ServiceUser();
                                 
                               List<User> emE= ue.sEns(AfficherSeanceController.Recup.getEnseignant());
                                 System.out.println(AfficherSeanceController.Recup.getEnseignant());
                               for ( User u : emE)
                                {
                               String emailEns = u.getEmail();
                               System.out.println(emailEns);
                                     try {
                                JavaMail.sendMail(emailEns ,"Modification ");
                                } catch (Exception ex) {
                                 Logger.getLogger(AjouterSeanceController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                               
                                }*/
          try {
 
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherSeanceController.fxml"));
                                Parent root = loader.load();
                              AfficherSeanceController rc = loader.getController();
                               Valider.getScene().setRoot(root);
                               
             
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());

                            }
         
                            }
    
    }  
    
    
    
    
    

}