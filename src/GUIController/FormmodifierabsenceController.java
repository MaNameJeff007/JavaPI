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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rami2
 */
public class FormmodifierabsenceController implements Initializable {

    @FXML
    private TextField justification;
    @FXML
    private ChoiceBox<Boolean> etat;
    @FXML
    private Button modifier;
    
    private Absence A;
    @FXML
    private Button boutton_retour;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       ObservableList<Boolean> etats = FXCollections.observableArrayList(false, true); 
       etat.setItems(etats);
       etat.setValue(Boolean.FALSE);
       //heuredebut.setText(String.valueOf(A.getHeureDebut()));
       //heurefin.setText(String.valueOf(A.getHeureFin()));
       //justification.setText(A.getJustification());
    }
    
    public void envoyer(Absence Abs)
    {
      A=new Absence(Abs);  
    }

    @FXML
    private void modifierabs(ActionEvent event) 
    {
        AbsenceService AS=new AbsenceService();
        String j=justification.getText();
        Boolean e=etat.getValue();
        
        A.setJustification(j);
        A.setEtat(e);
        
        try
        {
           AS.modifierAbsence(A);  
        }
       
        catch(SQLException E)
        {
                
        }
        
        try
        {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIInterface/dashboard-enseignant.fxml"));
         Parent root = loader.load();
         DashboardEnseignantController Controller = loader.getController();
         //fermer current stage //
         Stage s = (Stage) modifier.getScene().getWindow();
         s.close();
       
         //ouvrir un nouveau stage//
         Stage stage = new Stage();
         stage.setScene(new Scene(root));
         stage.setTitle("Affichage absences");
         stage.show();
        }
        
        catch(IOException E){} 
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
