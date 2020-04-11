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
import Entities.Note;
import Services.NoteService;

/**
 * FXML Controller class
 *
 * @author rami2
 */
public class ParentnotesController implements Initializable {

    @FXML
    private TableView<Note> table;
    @FXML
    private TableColumn<Note, Integer> col_Id;
    @FXML
    private TableColumn<Note, String> col_type;
    @FXML
    private TableColumn<Note, Integer> col_trimestre;
    @FXML
    private TableColumn<Note, Integer> col_enseignant_id;
    @FXML
    private TableColumn<Note, Integer> col_eleve_id;
    @FXML
    private TableColumn<Note, Integer> col_matiere_id;
    @FXML
    private TableColumn<Note, Double> col_valeur;
    
    @FXML
    private Button boutton_retour;
    
    private ObservableList<Note> data;
        
    static private int id;
    
    static public int getId() {
        return id;
    }

    static public void setId(int ideleve) {
        id = ideleve;
    }

   
    public void showNotes()
    {
      NoteService NS=new NoteService();     
      try
      {     
       ResultSet rs= NS.fetchNotesEleve(getId());
       //ResultSet rs= NS.fetchNotesEleve(projet.Projet.id);
       while(rs.next())
       {
          int id = rs.getInt(1);
          String type=rs.getString(2);
          int idtrimestre = rs.getInt(3);
          String enseignant_id = rs.getString(4);
          String eleve_id = rs.getString(5);
          double valeur = rs.getDouble(6);
          String matiere = rs.getString(7);
          data.add(new Note(id, type, idtrimestre, enseignant_id, eleve_id, matiere, valeur));
       }    
      }
      catch(SQLException e)
      {
      }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        data=FXCollections.observableArrayList();
        showNotes();
        col_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_trimestre.setCellValueFactory(new PropertyValueFactory<>("trimestre"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_eleve_id.setCellValueFactory(new PropertyValueFactory<>("eleve_id"));
        col_enseignant_id.setCellValueFactory(new PropertyValueFactory<>("enseignant_id"));
        col_matiere_id.setCellValueFactory(new PropertyValueFactory<>("matiere"));
        col_valeur.setCellValueFactory(new PropertyValueFactory<>("valeur"));
        
        table.setItems(null);
        table.setItems(data);     
    }    

    @FXML
    private void retour(ActionEvent event) 
    {
      try
      {  
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard-parent.fxml"));
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
