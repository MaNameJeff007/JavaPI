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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Services.UserService;
import javafx.scene.control.Alert.AlertType;

/**
 * FXML Controller class
 *
 * @author rami2
 */
public class NotesFormController implements Initializable {

    @FXML
    private TextField valeur;
    @FXML
    private ComboBox<Integer> trimestre;
    @FXML
    private ComboBox<String> type;
    @FXML
    private ComboBox<String> matiere;
    @FXML
    private ComboBox<String> eleve;
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
      ObservableList<Integer> tab_trimestres=FXCollections.observableArrayList(1,2,3);
      ObservableList<String> types=FXCollections.observableArrayList("CC","Devoir de controle","Devoir de synthese");
      ObservableList<String> matieres = FXCollections.observableArrayList();
      ObservableList<String> eleves = FXCollections.observableArrayList();
                                 
      NoteService NS=new NoteService();
      UserService US=new UserService();
      
      try
      {
        ResultSet resultsmatieres=NS.afficherMatieres();
        ResultSet resultseleves=US.affichereleves();
        
        
        while(resultsmatieres.next())
        {
            int id = resultsmatieres.getInt("id"); 
            String nom=resultsmatieres.getString("nom"); 
            String id_matiere = String.valueOf(id); 
            String insert=id_matiere+"-"+nom;
            matieres.add(insert);
        }
        
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
      
      trimestre.setItems(tab_trimestres);
      matiere.setItems(matieres);
      type.setItems(types);
      eleve.setItems(eleves);
          
      trimestre.setValue(1);
      matiere.setValue(matieres.get(0));
      type.setValue("CC");
      eleve.setValue(eleves.get(0));
    }    

    @FXML
    public void ajouterNote(ActionEvent event) 
    {
        String id_eleve=eleve.getValue().substring(0,2);
        String mt=matiere.getValue().substring(0,2);
        String nom_matiere;
        String nom_eleve;
        String email_parent=null;
        String email_prof="mohamedaminejrad1@gmail.com";
        
        Boolean flag = Character.isDigit(mt.charAt(1));
        if(flag) 
        {
            mt=matiere.getValue().substring(0,2);
            nom_matiere=matiere.getValue().substring(2);
        }
        else 
        {
            mt=matiere.getValue().substring(0,1);
            nom_matiere=matiere.getValue().substring(1);
        }
        
        flag = Character.isDigit(id_eleve.charAt(1));
        
        if(flag) 
        {
            id_eleve=eleve.getValue().substring(0,2);
            nom_eleve=eleve.getValue().substring(2);
        }
        else 
        {
            id_eleve=eleve.getValue().substring(0,1);
            nom_eleve=eleve.getValue().substring(1);
        }
        
        String t=type.getValue();
        int tri=trimestre.getValue();
        double v=Double.valueOf(valeur.getText());
        
        UserService US=new UserService();
        NoteService NS=new NoteService();
        Note N=new Note(t, tri, "6", id_eleve, mt, v);
        boolean b=false;
        String sujet="Mise a jour note:";
        String m="Note de type "+t+" pour la matiere "+nom_matiere+" ajoutee a votre enfant: "+nom_eleve;
        int id_parent=0;
        
        try
        {
           b=NS.ajouterNote(N);
           ResultSet rs=US.selectidparent(Integer.valueOf(id_eleve));
           while(rs.next())
           {
             id_parent = rs.getInt(17);
           }
           
           email_parent=US.selectemailparent(id_parent);
        }
        catch(SQLException e)
        {
            
        }
        
                  
        try
        {
                Alert a = new Alert(AlertType.NONE);
                a.setAlertType(AlertType.INFORMATION);
                if(b)
                {
                    a.setContentText("Note ajoutée avec succèes.");
                    try{US.sendMail(email_prof, email_parent, sujet,  m);}
                    catch(Exception e){}
                }
                else
                {a.setContentText("Note de type pour la matiere "+N.getMatiere()+" du trimestre "+N.getTrimestre()+" pour l'eleve d'id "+N.getEleve_id()+" existe deja.");}
                
                a.showAndWait();
                // show the dialog
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIInterface/dashboard-enseignant.fxml"));
                Parent root = loader.load();
                DashboardEnseignantController Controller = loader.getController();
                //fermer current stage //
                Stage s = (Stage) ajouter.getScene().getWindow();
                s.close();
       
                //ouvrir nouvelle info//
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Affichage notes");
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
