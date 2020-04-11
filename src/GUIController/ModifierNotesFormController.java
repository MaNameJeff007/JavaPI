/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Services.NoteService;
import Entities.Note;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rami2
 */
public class ModifierNotesFormController implements Initializable {

    @FXML
    private TextField valeur;
    @FXML
    private ComboBox<String> type;
    @FXML
    private Button modifier;
    
    @FXML
    private Label label_id;
    
    private Note N;
    @FXML
    private Button boutton_retour;
   
    
    
    public void envoyer(Note a)
    {
      N=new Note(a);
    }
    
    @FXML
    public void modifiernote(ActionEvent event)
    {
        boolean b=true;
        NoteService NS=new NoteService();
        String t=type.getValue();
        double v=Integer.valueOf(valeur.getText());
        if((v<0)||(v>20))
        {
          try
          {      
            b=false;
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("la note ne peut pas etre negative ou superieure a 20.");
            a.showAndWait();
            Stage stage = (Stage) modifier.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIInterface/ModifierNotesForm.fxml"));
            Parent root = loader.load();
            Stage s = new Stage();
            s.setScene(new Scene(root));
            s.show();
          }
          catch(IOException e){}
        }
        
            
        if(b)
        {
            N.setValeur(v);        
            N.setType(t);
            String message=" ";
           
            try
            {
                ResultSet rs=NS.selectionnerNotesParEleveMatiereTrimestreType(N);
                if(!rs.isBeforeFirst() ) 
                {
                    NS.modifierNote2(N);
                    message="Note modifiee avec succèes.";
                }
                else
                {
                    boolean m=false;
                    while(rs.next())
                    {   
                        int id=rs.getInt("id");
                   
                        if(id==N.getId())
                        {
                            NS.modifierNote2(N);
                            message="Note modifiee avec succèes.";
                            m=true;
                            break;
                        }
                    }
                    if(m==false)
                    message="Note identique existe deja.";
                }
            }
            catch(SQLException e)
            {
            
            }
        
            try
            {
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText(message);
                a.showAndWait();
                Stage stage = (Stage) modifier.getScene().getWindow();
                stage.close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIInterface/dashboard-enseignant.fxml"));
                Parent root = loader.load();
                DashboardEnseignantController Controller = loader.getController();
                Stage s = new Stage();
                s.setScene(new Scene(root));
                s.setTitle("Affichage Notes");
                s.show();
            }
            catch(IOException e){}
        }    
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
      ObservableList<String> types=FXCollections.observableArrayList("CC","Devoir de controle","Devoir de synthese");                                    
      type.setItems(types);
      type.setValue(types.get(0));
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
