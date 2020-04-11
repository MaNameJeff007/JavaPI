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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rami2
 */
public class FormmodifiersanctionController implements Initializable {

    @FXML
    private ComboBox<String> punition;
    @FXML
    private ComboBox<String> justification;
    @FXML
    private Button boutton_ajout;
    
    private Sanction S;
    @FXML
    private Button boutton_retour;
    
     public void envoyer(Sanction sanct)
    {
      S=new Sanction(sanct);  
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        ObservableList<String> punitions = FXCollections.observableArrayList("Observation","Retenue","Avertissement");
        ObservableList<String> justifications = FXCollections.observableArrayList("Retard","Travail non fait","Insolence");
        punition.setItems(punitions);
        justification.setItems(justifications);
        
        punition.setValue(punitions.get(0));
        justification.setValue(justifications.get(0));
    }    

    @FXML
    private void modifiersanction(ActionEvent event) 
    {
        SanctionService SS=new SanctionService();
    
        String p=punition.getValue();
        String j=justification.getValue();
        S.setPunition(p);
        S.setRaisonSanction(j);
    
        try
        {
            SS.modifierSanction(S);
        }
        catch(SQLException e){}
        
        try
        {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Sanction modifiée avec succèes.");    
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
