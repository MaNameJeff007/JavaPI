/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Entities.User;
import Services.UserService;

/**
 * FXML Controller class
 *
 * @author rami2
 */
public class DashboardParentController implements Initializable {

    @FXML
    private TableView<User> table_eleves;
    @FXML
    private TableColumn<User, Integer> col_id_eleve;
    @FXML
    private TableColumn<User, String> col_prenom_eleve;
    @FXML
    private TableColumn<User, String> col_nom_eleve;
    @FXML
    private TableColumn<User, Integer> col_classe_eleve;
    @FXML
    private Button boutton_sanctions;
    @FXML
    private Button boutton_moyennes;
    @FXML
    private Button boutton_notes;
    @FXML
    private Button boutton_abs;
    
    
    private ObservableList<User> data_eleves;
    private int id_parent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        id_parent=8;
        data_eleves=FXCollections.observableArrayList();
        showEleves();
        col_id_eleve.setCellValueFactory(new PropertyValueFactory<>("identifiant"));
        col_prenom_eleve.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_nom_eleve.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_classe_eleve.setCellValueFactory(new PropertyValueFactory<>("classeeleve_id"));
        
        table_eleves.setItems(null);
        table_eleves.setItems(data_eleves);
    }


    private void showEleves()
    {
      UserService US=new UserService();
      
      try
      {
       ResultSet rs= US.selectelevesduparent(id_parent);
       
       while(rs.next())
       {
          int id = rs.getInt(1);
          String prenom = rs.getString(2);
          String nom = rs.getString(3);
          String id_classe = rs.getString(4);
          String classe_niveau = rs.getString(5);
          String classe=classe_niveau +" 'annee "+id_classe;
          data_eleves.add(new User(id, prenom, nom, classe));
       }    
      }
      catch(SQLException e)
      {
      }  
    }
    

    @FXML
    private void afficherSanctions(ActionEvent event) 
    {
        int i=(table_eleves.getSelectionModel().getSelectedItem().getIdentifiant());

        GUIController.SanctionsparentController.setIdeleve(i);
        
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sanctionsparent.fxml"));
            Parent root=loader.load();
            SanctionsparentController Controller = loader.getController();

            Stage s = (Stage)boutton_notes.getScene().getWindow();
            s.close();          
            //ouvrir nouvelle info//
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Affichage des sanctions de votre enfant: "+table_eleves.getSelectionModel().getSelectedItem().getPrenom());
            stage.show();            
        }
        catch(IOException e){}
    }

    @FXML
    private void afficherMoyennes(ActionEvent event) 
    {
        int i=(table_eleves.getSelectionModel().getSelectedItem().getIdentifiant());

        GUIController.MoyennesparentController.setIdeleve(i);
        
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIInterface/moyennesparent.fxml"));
            Parent root=loader.load();
            MoyennesparentController Controller = loader.getController();

            Stage s = (Stage)boutton_moyennes.getScene().getWindow();
            s.close();          
            //ouvrir nouvelle info//
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Affichage des moyennes de votre enfant: "+table_eleves.getSelectionModel().getSelectedItem().getPrenom());
            stage.show();            
        }
        catch(IOException e){}      
    }

    @FXML
    public void afficherNotes(ActionEvent event) 
    {     
        int i=(table_eleves.getSelectionModel().getSelectedItem().getIdentifiant());

        GUIController.ParentnotesController.setId(i);
        
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIInterface/parentnotes.fxml"));
            Parent root=loader.load();
            ParentnotesController Controller = loader.getController();

            Stage s = (Stage)boutton_notes.getScene().getWindow();
            s.close();          
            //ouvrir nouvelle info//
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Affichage notes de votre enfant: "+table_eleves.getSelectionModel().getSelectedItem().getPrenom());
            stage.show();            
        }
        catch(IOException e){}
    }

    @FXML
    private void afficherAbs(ActionEvent event) 
    {
        int i=(table_eleves.getSelectionModel().getSelectedItem().getIdentifiant());

        GUIController.AbsencesparentController.setIdeleve(i);
        
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIInterface/absencesparent.fxml"));
            Parent root=loader.load();
            AbsencesparentController Controller = loader.getController();

            Stage s = (Stage)boutton_abs.getScene().getWindow();
            s.close();          
            //ouvrir nouvelle info//
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Affichage absences de votre enfant: "+table_eleves.getSelectionModel().getSelectedItem().getPrenom());
            stage.show();            
        }
        catch(IOException e){}
        
    }
    
}
