/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Entities.Absence;
import Services.AbsenceService;
import Entities.Moyenne;
import Services.MoyenneService;
import Entities.Note;
import Services.NoteService;
import Entities.Sanction;
import Services.SanctionService;

/**
 * FXML Controller class
 *
 * @author rami2
 */
public class DashboardEnseignantController implements Initializable {

    //tab_abs//
    @FXML
    private TabPane TabPane;
    @FXML
    private Tab tab_abs;
    @FXML
    private TableView<Absence> tableau_abs;
    @FXML
    private TableColumn<Absence, Integer> col_id_abs;
    @FXML
    private TableColumn<Absence, LocalDate> col_date_abs;
    @FXML
    private TableColumn<Absence, Integer> col_heure_debut_abs;
    @FXML
    private TableColumn<Absence, Integer> col_heure_fin_abs;
    @FXML
    private TableColumn<Absence, Integer> col_enseignantid_abs;
    @FXML
    private TableColumn<Absence, Integer> col_eleveid_abs;
    @FXML
    private TableColumn<Absence, String> col_justification_abs;
    @FXML
    private TableColumn<Absence, Boolean> col_etat_abs;
    
    @FXML
    private Button ajouter_abs;
    @FXML
    private Button modifier_abs;
    @FXML
    private Button supprimer_abs;
    
    private ObservableList<Absence> data_abs;
    
    //tab_sanctions//
    @FXML
    private Tab tab_sanctions;
    @FXML
    private TableView<Sanction> tableau_sanctions;
     @FXML
    private TableColumn<Sanction, Integer> col_id_sanction;
    @FXML
    private TableColumn<Sanction, LocalDate> col_date_sanction;
    @FXML
    private TableColumn<Sanction, String> col_raisonsanction_sanction;
    @FXML
    private TableColumn<Sanction, String> col_punition_sanction;
    @FXML
    private TableColumn<Sanction, Integer> col_enseignantid_sanction;
    @FXML
    private TableColumn<Sanction, Integer> col_eleveid_sanction;
    @FXML
    private TableColumn<Sanction, Boolean> col_etat_sanction;
    @FXML
    private Button ajouter_sanction;
    @FXML
    private Button modifier_sanction;
    @FXML
    private Button supprimer_sanction;
    
    private ObservableList<Sanction> data_sanctions;
    
    //tab_notes//
    @FXML
    private Tab tab_notes;
    @FXML
    private TableView<Note> tableau_notes;
    @FXML
    private TableColumn<Note, Integer> col_id_note;
    @FXML
    private TableColumn<Note, String> col_type_note;
    @FXML
    private TableColumn<Note, Integer> col_trimestre_note;
    @FXML
    private TableColumn<Note, Integer> col_enseignantid_note;
    @FXML
    private TableColumn<Note, Integer> col_eleveid_note;
    @FXML
    private TableColumn<Note, Integer> col_matiere_note;
    @FXML
    private TableColumn<Note, Double> col_valeur_note;
    
    
    private ObservableList<Note> data_notes;
    
    @FXML
    private Button modifier_note;
    @FXML
    private Button supprimer_note;
    @FXML
    private Button ajouter_note;
    @FXML
    private Button boutton_moyenne;
    
    //tab_moyennes//
    
    @FXML
    private Tab tab_moyennes;
    @FXML
    private TableView<Moyenne> tableau_moyennes;
    @FXML
    private TableColumn<Moyenne, Integer> col_id_moyenne;
    @FXML
    private TableColumn<Moyenne, Integer> col_trimestre_moyenne;
    @FXML
    private TableColumn<Moyenne, Integer> col_eleveid_moyenne;
    @FXML
    private TableColumn<Moyenne, Integer> col_matiere_moyenne;
    @FXML
    private TableColumn<Moyenne, Double> col_moyenne_moyenne;
    
    private ObservableList<Moyenne> data_moyennes;
    
    @FXML
    private Button recalcul_moyenne;
    @FXML
    private Button supprimer_moyenne;
    
    // fin tableaux //

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        //<----------------------abs---------------------->//
        data_abs=FXCollections.observableArrayList();
        showAbs();
        col_id_abs.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_eleveid_abs.setCellValueFactory(new PropertyValueFactory<>("eleve_id"));
        col_enseignantid_abs.setCellValueFactory(new PropertyValueFactory<>("enseignant_id"));
        col_justification_abs.setCellValueFactory(new PropertyValueFactory<>("justification"));
        col_heure_debut_abs.setCellValueFactory(new PropertyValueFactory<>("HeureDebut"));
        col_heure_fin_abs.setCellValueFactory(new PropertyValueFactory<>("heureFin"));
        col_date_abs.setCellValueFactory(new PropertyValueFactory<>("dateAbsence"));
        col_etat_abs.setCellValueFactory(new PropertyValueFactory<>("etat"));
         
        tableau_abs.setItems(null);
        tableau_abs.setItems(data_abs);
        
        //<----------------------sanctions---------------------->//
        data_sanctions=FXCollections.observableArrayList();
        showSanctions();
        col_id_sanction.setCellValueFactory(new PropertyValueFactory<>("id"));   
        col_date_sanction.setCellValueFactory(new PropertyValueFactory<>("dateSanction"));        
        col_raisonsanction_sanction.setCellValueFactory(new PropertyValueFactory<>("raisonSanction"));        
        col_punition_sanction.setCellValueFactory(new PropertyValueFactory<>("punition"));       
        col_enseignantid_sanction.setCellValueFactory(new PropertyValueFactory<>("enseignant_id"));        
        col_eleveid_sanction.setCellValueFactory(new PropertyValueFactory<>("eleve_id")); 
        col_etat_sanction.setCellValueFactory(new PropertyValueFactory<>("etat")); 
        
        tableau_sanctions.setItems(null);
        tableau_sanctions.setItems(data_sanctions);
        
        //<----------------------notes---------------------->//
        data_notes=FXCollections.observableArrayList();
        showNotes();
        col_id_note.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_trimestre_note.setCellValueFactory(new PropertyValueFactory<>("trimestre"));
        col_type_note.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_eleveid_note.setCellValueFactory(new PropertyValueFactory<>("eleve_id"));
        col_enseignantid_note.setCellValueFactory(new PropertyValueFactory<>("enseignant_id"));
        col_matiere_note.setCellValueFactory(new PropertyValueFactory<>("matiere"));
        col_valeur_note.setCellValueFactory(new PropertyValueFactory<>("valeur"));
        
        tableau_notes.setItems(null);
        tableau_notes.setItems(data_notes);
        
        //<----------------------moyennes---------------------->//
        data_moyennes=FXCollections.observableArrayList();
        showMoyennes();
        col_id_moyenne.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_trimestre_moyenne.setCellValueFactory(new PropertyValueFactory<>("trimestre"));
        col_eleveid_moyenne.setCellValueFactory(new PropertyValueFactory<>("eleve_id"));
        col_matiere_moyenne.setCellValueFactory(new PropertyValueFactory<>("matiere"));
        col_moyenne_moyenne.setCellValueFactory(new PropertyValueFactory<>("moyenne"));
        
        tableau_moyennes.setItems(null);
        tableau_moyennes.setItems(data_moyennes);
    }
    
    // affichage//
    
    //<-- affichage abs -->
    private void showAbs() 
    {
      AbsenceService AS=new AbsenceService();
      
      try
      {
       ResultSet rs= AS.fetchAbsences();
       while(rs.next())
       {
          int identifiant = rs.getInt("id");
          Date dte = rs.getDate("dateabs");
          String j = rs.getString("justification");
          int heured = rs.getInt("heuredebut");
          int heuref = rs.getInt("heurefin");
          boolean e = rs.getBoolean("etat");
          String ens_id = rs.getString("enseignant_id");
          String elv_id = rs.getString("eleve_id");
          LocalDate d=dte.toLocalDate();
          data_abs.add(new Absence(identifiant, j, d, heured, heuref, e, ens_id, elv_id));
       }    
      }
      catch(SQLException e)
      {
      }  
    }
    //<-- affichage sanctions -->//
    public void showSanctions()
    {
      SanctionService SS=new SanctionService();
      
      try
      {
          ResultSet rs=SS.afficherSanctions();
          while (rs.next())
          {
           int id = rs.getInt("id");
           String enseignant_id = rs.getString("enseignant_id");
           String eleve_id = rs.getString("eleve_id");
           Date date = rs.getDate("date_sanction");
           String raisonsanction = rs.getString("raisonsanction");
           boolean etat = rs.getBoolean("etat");
           String punition = rs.getString("punition");
           LocalDate d=date.toLocalDate();
           data_sanctions.add(new Sanction(id, raisonsanction, d, punition, etat, enseignant_id, eleve_id));
          }
      }
      catch(SQLException e){}                
    }
           
    //<-- affichage note -->//
    public void showNotes()
    {
      NoteService NS=new NoteService();
      
      try
      {
       ResultSet rs=NS.fetchNotes2();
       while(rs.next())
       {
           int id=rs.getInt(1);
           String type=rs.getString(2);
           int trimestre=rs.getInt(3);
           String enseignant_id=rs.getString(4);
           String eleve_id=rs.getString(5);
           String matiere_id=rs.getString(6);
           Double valeur=rs.getDouble(7);
           String prenom=rs.getString(8);
           String nom=rs.getString(9);
           String matiere_nom=rs.getString(10);
           
           String eleve=eleve_id+"-"+prenom+" "+nom;
           String matiere=matiere_id+"-"+matiere_nom;
           data_notes.add(new Note(id, type, trimestre, enseignant_id, eleve, matiere, valeur));
       }
          
       /*
       ResultSet rs= NS.fetchNotes();
       while(rs.next())
       {
          int id = rs.getInt("id");
          int idtrimestre = rs.getInt("id_trimestre");
          String type=rs.getString("type");
          String eleve_id = rs.getString("eleve_id");
          String enseignant_id = rs.getString("enseignant_id");
          String matiere = rs.getString("matiere");
          double valeur = rs.getDouble("valeur");
          String v=String.valueOf(valeur);
          data_notes.add(new Note(id, type, idtrimestre, enseignant_id, eleve_id, matiere, valeur));
       }
      */
      }
      catch(SQLException e)
      {
      }
    }
    
    //<-- affichage moyennes -->//
    public void showMoyennes()
    {
      MoyenneService MS=new MoyenneService();
      
      try
      {
       ResultSet rs= MS.fetchMoyennes();
       while(rs.next())
       {
          int id = rs.getInt("id");
          int idtrimestre = rs.getInt("trimestre");
          String eleve_id = rs.getString("eleve_id");
          String matiere = rs.getString("matiere");
          double valeur = rs.getDouble("moyenne");
          String v=String.valueOf(valeur);
          data_moyennes.add(new Moyenne(id, idtrimestre, valeur, eleve_id, matiere));
       }    
      }
      catch(SQLException e)
      {
      }
    }
    
    
    // ajout //
    
    //<-- ajout abs -->
    @FXML
    public void ajouterAbsence(ActionEvent event) 
    {
      try
      {    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIInterface/formajoutabsences.fxml"));
        Parent root = loader.load();
        FormajoutabsencesController Controller = loader.getController();
        //fermer current stage //
        Stage s = (Stage)ajouter_abs.getScene().getWindow();
        s.close();
       
        //ouvrir nouvelle info//
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Ajouter Absence");
        stage.show();
      }
      catch(IOException e)
      {
                  
      }   
    }
     
    //<-- ajout notes -->
    @FXML
    private void AjouterNote(ActionEvent event) 
    {
      try
      {   
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIInterface/AjouterNotesForm.fxml"));
        Parent root = loader.load();
        NotesFormController Controller = loader.getController();
        //fermer current stage //
        Stage s = (Stage) ajouter_note.getScene().getWindow();
        s.close();
       
        //ouvrir nouvelle info//
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Ajouter Note");
        stage.show();
      }
      catch(IOException e)
      {
                  
      }   
    }
    
    //<-- ajout moyennes -->
    @FXML
    private void AjouterMoyenne(ActionEvent event) 
    {
        NoteService NS=new NoteService();
        MoyenneService MS=new MoyenneService();
        Note N=new Note(tableau_notes.getSelectionModel().getSelectedItem());  
        
        try
        {
            ResultSet rs=NS.selectionnerNotesParEleveMatiereTrimestre(N);
            MS.insererMoyenne(N, rs);
        }
        catch(SQLException e)
        {
            
        }
              
        try
        {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIInterface/dashboard-enseignant.fxml"));
          Parent root = loader.load();
          DashboardEnseignantController Controller= loader.getController();
          
          Stage s=(Stage)boutton_moyenne.getScene().getWindow();
          s.close();
          
          Stage stage = new Stage();
          stage.setScene(new Scene(root));
          stage.setTitle("Affichage Moyennes");
          stage.show();  
       }
        
       catch(IOException e)
       {
                  
       }         
    }
    
  
    @FXML
    public void ModifierAbsence(ActionEvent event) 
    {
      try
      {    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIInterface/formmodifierabsence.fxml"));
        Absence A=(tableau_abs.getSelectionModel().getSelectedItem());  
        Parent root = loader.load();
        FormmodifierabsenceController Controller = loader.getController();
        //fermer current stage //
        Stage s = (Stage)modifier_abs.getScene().getWindow();
        s.close();
       
        //envoyer info//
        Controller.envoyer(A);
        
        //ouvrir nouvelle info//
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Modification");
        stage.show();
      }
      catch(IOException e)
      {
                  
      }  
    }

    @FXML
    private void SupprimerAbsence(ActionEvent event) 
    {
      AbsenceService AS=new AbsenceService();
      Absence A=new Absence(tableau_abs.getSelectionModel().getSelectedItem());  

      try
      {
        AS.supprimerAbsence(A);
        tableau_abs.getItems().removeAll(tableau_abs.getSelectionModel().getSelectedItem());
        data_abs.clear();
        showAbs();  
      }
      catch(SQLException e){}
      
    }

    @FXML
    private void ModifierNote(ActionEvent event) 
    {
      try
      {
        NoteService MS=new NoteService();
        Note N=new Note(tableau_notes.getSelectionModel().getSelectedItem()); 
        
        String eleve_id=N.getEleve_id();
        String matiere_id=N.getMatiere();
        
        for(int i=0;i<eleve_id.length();i++)
        {
            if(eleve_id.charAt(i)=='-'){N.setEleve_id(eleve_id.substring(i-1, i));}
        }
        
        for(int i=0;i<matiere_id.length();i++)
        {
            if(matiere_id.charAt(i)=='-'){N.setMatiere(matiere_id.substring(i-1, i));}
        }
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIInterface/ModifierNotesForm.fxml"));
        Parent root = loader.load();
        ModifierNotesFormController Controller = loader.getController();
        //fermer current stage //
        Stage s = (Stage) modifier_note.getScene().getWindow();
        s.close();
        
        //envoyer info//
        Controller.envoyer(N);
        
        //ouvrir nouvelle info//
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Modification");
        stage.show();
      }
      catch(IOException e)
      {
                  
      }  
    }

   @FXML 
    public void SupprimerNote(ActionEvent Event)
    {
      NoteService MS=new NoteService();
      Note N=new Note(tableau_notes.getSelectionModel().getSelectedItem());  

      try
      {
        MS.supprimerNote(N);
        tableau_notes.getItems().removeAll(tableau_notes.getSelectionModel().getSelectedItem());
        data_notes.clear();
        showNotes();
      }
      catch(SQLException e)
      {
          
      }
    }

    @FXML
    private void recalculerMoyenne(ActionEvent event) 
    {
        MoyenneService MS=new MoyenneService();
        Moyenne M=new Moyenne();
        M=tableau_moyennes.getSelectionModel().getSelectedItem();
        data_moyennes.clear();
        try
        {    
            MS.recalculerMoyenne2(M);
            showMoyennes();
        }
        catch(SQLException e)
        {
        }
        
    }

    @FXML
    private void effacerMoyenne(ActionEvent event) 
    {
      MoyenneService MS=new MoyenneService();
      Moyenne M=new Moyenne();
      M=tableau_moyennes.getSelectionModel().getSelectedItem();

      try
      {
        MS.effacerMoyenne(M);
        tableau_moyennes.getItems().removeAll(tableau_moyennes.getSelectionModel().getSelectedItem());
        data_moyennes.clear();
        showMoyennes();
      }
      catch(SQLException e)
      {
          
      }
    }

    @FXML
    private void AjouterSanction(ActionEvent event) 
    {
      try
      {   
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIInterface/formajoutsanction.fxml"));
        Parent root = loader.load();
        FormajoutsanctionController Controller = loader.getController();
        //fermer current stage //
        Stage s = (Stage) ajouter_sanction.getScene().getWindow();
        s.close();
       
        //ouvrir nouvelle info//
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Ajouter Sanction");
        stage.show();
      }
      catch(IOException e)
      {
                  
      }     
    }

    @FXML
    private void ModifierSanction(ActionEvent event) 
    {
        try
        {
          SanctionService SS=new SanctionService();
          Sanction S=new Sanction(tableau_sanctions.getSelectionModel().getSelectedItem());
      
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIInterface/Formmodifiersanction.fxml"));
          Parent root = loader.load();
          FormmodifiersanctionController Controller = loader.getController();
          //fermer current stage //
          Stage s = (Stage) modifier_note.getScene().getWindow();
          s.close();
        
          //envoyer info//
          Controller.envoyer(S);
        
          //ouvrir nouvelle info//
          Stage stage = new Stage();
          stage.setScene(new Scene(root));
          stage.setTitle("Modification");
          stage.show();
        }
       catch(IOException e){}      
    }

    @FXML
    private void SupprimerSanction(ActionEvent event) 
    {
      SanctionService SS=new SanctionService();
      Sanction S=new Sanction(tableau_sanctions.getSelectionModel().getSelectedItem());  

      try
      {
        SS.supprimerSanction(S);
        tableau_sanctions.getItems().removeAll(tableau_sanctions.getSelectionModel().getSelectedItem());
        data_sanctions.clear();
        showSanctions();  
      }
      catch(SQLException e)
      {
          
      }  
    }
}
