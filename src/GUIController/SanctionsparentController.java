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
import Entities.Sanction;
import Services.SanctionService;

/**
 * FXML Controller class
 *
 * @author rami2
 */
public class SanctionsparentController implements Initializable {
    
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
    
    private ObservableList<Sanction> data_sanctions;
    
    @FXML
    private Button boutton_retour;
    
    static private int ideleve;

    public static int getIdeleve() {
        return ideleve;
    }

    public static void setIdeleve(int ideleve) {
        SanctionsparentController.ideleve = ideleve;
    }
    
    public void showSanctions()
    {
      SanctionService SS=new SanctionService();
      
      try
      {
          ResultSet rs=SS.getSanctionseleve(ideleve);
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
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
    
}
