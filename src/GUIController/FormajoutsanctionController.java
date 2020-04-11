/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Services.SanctionService;
import Entities.Sanction;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import Services.UserService;

/**
 * FXML Controller class
 *
 * @author rami2
 */
public class FormajoutsanctionController implements Initializable {

    @FXML
    private DatePicker date;
    @FXML
    private Button boutton_ajout;
    @FXML
    private ComboBox<String> punition;
    @FXML
    private ComboBox<String> justification;
    @FXML
    private ComboBox<String> eleve;
    @FXML
    private Button boutton_retour;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        ObservableList<String> eleves = FXCollections.observableArrayList();
        ObservableList<String> punitions = FXCollections.observableArrayList("Observation","Retenue","Avertissement");
        ObservableList<String> justifications = FXCollections.observableArrayList("Retard","Travail non fait","Insolence");
        
        UserService US=new UserService();
        
        try
        {
            ResultSet resultseleves=US.affichereleves();
            while(resultseleves.next())
            {
                int id = resultseleves.getInt("id");
                String nom=resultseleves.getString("nom");
                String prenom=resultseleves.getString("prenom"); 
                String id_eleve = String.valueOf(id);
                String insert=id_eleve+"-"+prenom+" "+nom;
                eleves.add(insert);
            }  
        }
        catch(SQLException e){}
        
        eleve.setItems(eleves);
        punition.setItems(punitions);
        justification.setItems(justifications);
        
        eleve.setValue(eleves.get(0));
        punition.setValue(punitions.get(0));
        justification.setValue(justifications.get(0));
    }    

    @FXML
    private void ajoutersanction(ActionEvent event) 
    {
        SanctionService SS=new SanctionService();
        String id_eleve=eleve.getValue().substring(0,2);
        boolean flag = Character.isDigit(id_eleve.charAt(1));
        
        if(flag) 
        {
            id_eleve=eleve.getValue().substring(0,2);
        }
        else 
        {
            id_eleve=eleve.getValue().substring(0,1);
        }
        
        String p=punition.getValue();
        String j=justification.getValue();
        LocalDate dateAbs=date.getValue();
        String enseignant_id="6";
        Sanction S=new Sanction(j, dateAbs, p, false, enseignant_id, id_eleve);
        
        try
        {
            SS.ajouterSanction(S);       
        }
        catch(SQLException e){}
        
        try
        {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Sanction ajoutée avec succèes.");    
            alert.showAndWait();       
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIInterface/dashboard-enseignant.fxml"));
            Parent root = loader.load();
            DashboardEnseignantController Controller = loader.getController();
            //fermer current stage //
            Stage s = (Stage) boutton_ajout.getScene().getWindow();
            s.close();     
            //ouvrir un nouveau stage//
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Affichage sanctions");
            stage.show();
      }
      catch(IOException e)
      {
                  
      } 
            
       
        
                
        
    }

    @FXML
    private void retour(ActionEvent event) 
    {
      try
      {  
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIInterface/dashboard-enseignant.fxml"));
        Parent root = loader.load();
        //fermer current stage //
        Stage s = (Stage) boutton_retour.getScene().getWindow();
        s.close();
            
        //ouvrir nouvelle info//
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
      }
      catch(IOException e)
      {
                  
      }  
    } 
}
