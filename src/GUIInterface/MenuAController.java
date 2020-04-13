/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIInterface;

import GUIInterface.AfficherClassesController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class MenuAController implements Initializable {

    @FXML
    private AnchorPane m1;
    @FXML
    private Button GestionDesClasses;
    @FXML
    private Button GestionDesSalles;
    @FXML
    private Button GestionDesCoeff;
    @FXML
    private Button GestionDesM;
    @FXML
    private Button GestionDesSeances;
    @FXML
    private Button BulletinEleves;
    @FXML
    private Button EnsEmplois;
    @FXML
    private Button stat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    
     @FXML
        public void stat(ActionEvent event)
     {
                try {
                     System.out.println("hhh stat ");
            FXMLLoader loader=new FXMLLoader(getClass().getResource("MoyGStat.fxml"));
            Parent root= loader.load();
            MoyGStatController rc = loader.getController();
            
            stat.getScene().setRoot(root);
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());;
        }
       
     }
        
    @FXML
        public void gestionDesMatieres(ActionEvent event)
     {
                try {
                     System.out.println("hhh mat ");
            FXMLLoader loader=new FXMLLoader(getClass().getResource("AjouterMatiere.fxml"));
            Parent root= loader.load();
            AjouterMatiereController rc = loader.getController();
            
            GestionDesM.getScene().setRoot(root);
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());;
        }
       
     }
      
        
         @FXML
        public void gestionDesEmpEns(ActionEvent event)
     {
                try {
                     System.out.println("hhh emp ens ");
            FXMLLoader loader=new FXMLLoader(getClass().getResource("EmploisEns.fxml"));
            Parent root= loader.load();
            EmploisEnsController rc = loader.getController();
            
            EnsEmplois.getScene().setRoot(root);
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());;
        }
       
     }
        
    @FXML
        public void gestionClasses(ActionEvent event)
     {
                try {
                     System.out.println("hhhhhhhh");
            FXMLLoader loader=new FXMLLoader(getClass().getResource("AfficherClasses.fxml"));
            Parent root= loader.load();
            AfficherClassesController rc = loader.getController();
            
            GestionDesClasses.getScene().setRoot(root);
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());;
        }
       
     }
        
        
        @FXML
        public void gestionCoeff(ActionEvent event)
     {
                try {
                     System.out.println("hhh coeff ");
            FXMLLoader loader=new FXMLLoader(getClass().getResource("AfficherCoeff.fxml"));
            Parent root= loader.load();
            AfficherCoeffController rc = loader.getController();
            
            GestionDesCoeff.getScene().setRoot(root);
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());;
        }
       
     }
       
     @FXML
        public void gestionDesBullentins(ActionEvent event)
     {
                try {
                     System.out.println("hhh bulletin ");
            FXMLLoader loader=new FXMLLoader(getClass().getResource("BulletinsElv.fxml"));
            Parent root= loader.load();
            BulletinsElvController rc = loader.getController();
            
            BulletinEleves.getScene().setRoot(root);
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());;
        }
       
     }
        
        @FXML
        public void gestionDesSeances(ActionEvent event)
     {
                try {
                     System.out.println("hhh seances ");
            FXMLLoader loader=new FXMLLoader(getClass().getResource("AfficherSeance.fxml"));
            Parent root= loader.load();
            AfficherSeanceController rc = loader.getController();
            
            GestionDesSeances.getScene().setRoot(root);
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());;
        }
       
     }
        
         @FXML
        public void gestionDesSalles(ActionEvent event)
     {
                try {
                     System.out.println("hhh salles ");
            FXMLLoader loader=new FXMLLoader(getClass().getResource("AjouterSalle.fxml"));
            Parent root= loader.load();
            AjouterSalleController rc = loader.getController();
            
            GestionDesSalles.getScene().setRoot(root);
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());;
        }
       
     }
}
