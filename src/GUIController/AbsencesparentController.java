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
import Entities.Absence;
import Services.AbsenceService;

/**
 * FXML Controller class
 *
 * @author rami2
 */
public class AbsencesparentController implements Initializable {

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
    
    private ObservableList<Absence> data_abs;
    
    @FXML
    private Button boutton_retour;
    
    static private int ideleve;

    public static int getIdeleve() {
        return ideleve;
    }

    public static void setIdeleve(int ideleve) {
        AbsencesparentController.ideleve = ideleve;
    }
    
    
    private void showAbs() 
    {
      AbsenceService AS=new AbsenceService();
      
      try
      {
       System.out.print(ideleve);
       ResultSet rs= AS.getAbsenceseleve(ideleve);
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
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
