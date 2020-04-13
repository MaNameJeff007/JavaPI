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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;

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
    
    @FXML
    private LineChart<Number, Number> Line_Chart;
    @FXML
    private ComboBox<String> Type_List;
    @FXML
    private ComboBox<String> matiere;
    @FXML
    private Button Stats;
        
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
        
        ObservableList<String> types = FXCollections.observableArrayList("CC","Devoir de controle", "Devoir de synthese");
        Type_List.setItems(types);
        Type_List.setValue(types.get(0));
        ObservableList<String> matieres = FXCollections.observableArrayList();
        fillMatieres(matieres);
        matiere.setItems(matieres);
        matiere.setValue(matieres.get(0));    
    }    

    @FXML
    private void retour(ActionEvent event) 
    {
      try
      {  
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIInterface/dashboard-parent.fxml"));
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
      
    private void fillMatieres(ObservableList<String> matieres)
    {
      NoteService NS=new NoteService();
        
      try
      {
        ResultSet resultsmatieres=NS.afficherMatieres();     
        
        while(resultsmatieres.next())
        {
            int id = resultsmatieres.getInt("id"); 
            String nom=resultsmatieres.getString("nom"); 
            String id_matiere = String.valueOf(id); 
            matieres.add(nom);
        }
      }
      catch(SQLException e){}
    }

    @FXML
    private void showLineChart(ActionEvent event) 
    {    
        Line_Chart.getData().clear();
        NoteService NS=new NoteService();
        XYChart.Series dataSeries = new XYChart.Series();
        try
        {
            ResultSet rs=NS.fetchNotesTypeMatiereEleve(Type_List.getValue(), matiere.getValue(), id);
   
            while(rs.next())
            {
              int trimestre=rs.getInt(2);
              Double valeur=rs.getDouble(3);
              dataSeries.getData().add(new XYChart.Data(trimestre, valeur));  
            }
            
            dataSeries.setName(Type_List.getValue()+"-"+matiere.getValue());
            Line_Chart.getData().add(dataSeries);          
        }
        catch(SQLException e){}  
    }   
}
