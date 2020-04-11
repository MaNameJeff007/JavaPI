/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Services.AbsenceService;
import Entities.Absence;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Services.UserService;

/**
 * FXML Controller class
 *
 * @author rami2
 */
public class FormajoutabsencesController implements Initializable {

    @FXML
    private DatePicker date;
    @FXML
    private ComboBox<String> justification;
    @FXML
    private ComboBox<String> eleve;
    @FXML
    private TextField heuredebut;
    @FXML
    private TextField heurefin;
    @FXML
    private Button ajouter;
    @FXML
    private Button boutton_retour;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        ObservableList<String> eleves = FXCollections.observableArrayList();
        ObservableList<String> justifications = FXCollections.observableArrayList("A Ajouter");
        
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
             
            eleve.setItems(eleves);
            justification.setItems(justifications);
            eleve.setValue(eleves.get(0));
            justification.setValue(justifications.get(0));
        }
        
        catch(SQLException e){}
    }    

    @FXML
    private void ajouterabs(ActionEvent event) 
    {
        String id_eleve=eleve.getValue().substring(0,2);
        Boolean flag = Character.isDigit(id_eleve.charAt(1));
        AbsenceService AS=new AbsenceService();
        
        if(flag) 
        {
            id_eleve=eleve.getValue().substring(0,2);
        }
        else 
        {
            id_eleve=eleve.getValue().substring(0,1);
        }
        
        String j=justification.getValue();
        LocalDate dateAbs=date.getValue();
        int hd=Integer.valueOf(heuredebut.getText());
        int hf=Integer.valueOf(heurefin.getText());
        boolean etat=true;
        String enseignant_id="6";
        Absence A=new Absence(j, dateAbs, hd, hf, etat, enseignant_id, id_eleve);
        boolean b=false;
        try
        {
            b=AS.verifierAbsence(A);
            if(b)
                AS.ajouterAbsence(A);
        }
        catch(SQLException e){}
        
        try
        {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            if(b)
            {
              alert.setContentText("Absence ajoutée avec succèes.");
            }
            else
            {alert.setContentText("Absence identique existe deja.");}
                
            alert.showAndWait();       
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIInterface/dashboard-enseignant.fxml"));
            Parent root = loader.load();
            DashboardEnseignantController Controller = loader.getController();
            //fermer current stage //
            Stage s = (Stage) ajouter.getScene().getWindow();
            s.close();
       
            //ouvrir un nouveau stage//
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Affichage absences");
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
