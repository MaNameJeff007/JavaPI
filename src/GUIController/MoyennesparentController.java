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
import Entities.Moyenne;
import Services.MoyenneService;
import Services.NoteService;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author rami2
 */
public class MoyennesparentController implements Initializable {

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
    @FXML
    private Button boutton_retour;
    @FXML
    private ComboBox<String> liste_matiere;
    @FXML
    private BorderPane box_stats;
    @FXML
    private LineChart<Integer, Double> linechart_moyennes;
    @FXML
    private NumberAxis y;
    @FXML
    private NumberAxis x;
    
    
    private ObservableList<Moyenne> data_moyennes;
    
    static private int ideleve;

    public static int getIdeleve() {
        return ideleve;
    }

    public static void setIdeleve(int ideleve) {
        MoyennesparentController.ideleve = ideleve;
    }


    
    public void showMoyennes()
    {
      MoyenneService MS=new MoyenneService();
      
      try
      {
       ResultSet rs= MS.fetchMoyennesEleve(ideleve);
       while(rs.next())
       {
          int id = rs.getInt(1);
          int idtrimestre = rs.getInt(2);
          String eleve_id = rs.getString(4);
          String matiere = rs.getString(5);
          double valeur = rs.getDouble(3);
          data_moyennes.add(new Moyenne(id, idtrimestre, valeur, eleve_id, matiere));
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
        data_moyennes=FXCollections.observableArrayList();
        showMoyennes();
        col_id_moyenne.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_trimestre_moyenne.setCellValueFactory(new PropertyValueFactory<>("trimestre"));
        col_eleveid_moyenne.setCellValueFactory(new PropertyValueFactory<>("eleve_id"));
        col_matiere_moyenne.setCellValueFactory(new PropertyValueFactory<>("matiere"));
        col_moyenne_moyenne.setCellValueFactory(new PropertyValueFactory<>("moyenne"));
        
        tableau_moyennes.setItems(null);
        tableau_moyennes.setItems(data_moyennes);
                
        ObservableList<String> matieres = FXCollections.observableArrayList();
        fillMatieres(matieres);
        liste_matiere.setItems(matieres);
        liste_matiere.setValue(matieres.get(0));              
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
            //String insert=id_matiere+"-"+nom;
            matieres.add(nom);
        }
      }
      catch(SQLException e){}
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

    @FXML
    private void afficherLineChart(ActionEvent event) 
    {
        linechart_moyennes.getData().clear();
        MoyenneService MS=new MoyenneService();
        XYChart.Series dataSeries = new XYChart.Series();
        try
        {
           ResultSet rs=MS.fetchMoyennesEleveMatiere(ideleve, liste_matiere.getValue());
           while(rs.next())
           {
            int trimestre=rs.getInt(2);
            Double moyenne=rs.getDouble(3);
            dataSeries.getData().add(new XYChart.Data(trimestre, moyenne));  
           }
           dataSeries.setName(liste_matiere.getValue());
           linechart_moyennes.getData().add(dataSeries);
        }
        catch(SQLException e){}     
    }
    
}
