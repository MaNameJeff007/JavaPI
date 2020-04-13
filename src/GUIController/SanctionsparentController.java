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
import javafx.application.Platform;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

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
    @FXML
    private BarChart<String, Integer> BarChart_punitions;
    @FXML
    private NumberAxis y;
    @FXML
    private CategoryAxis x;
    
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
    
    private void setMaxBarWidth(double maxBarWidth, double minCategoryGap)
    {
    double barWidth=0;
    do{
        double catSpace = x.getCategorySpacing();
        double avilableBarSpace = catSpace - (BarChart_punitions.getCategoryGap() + BarChart_punitions.getBarGap());
        barWidth = (avilableBarSpace / BarChart_punitions.getData().size()) - BarChart_punitions.getBarGap();
        if (barWidth >maxBarWidth){
            avilableBarSpace=(maxBarWidth + BarChart_punitions.getBarGap())* BarChart_punitions.getData().size();
            BarChart_punitions.setCategoryGap(catSpace-avilableBarSpace-BarChart_punitions.getBarGap());
        }
    } while(barWidth>maxBarWidth);

    do{
        double catSpace = x.getCategorySpacing();
        double avilableBarSpace = catSpace - (minCategoryGap + BarChart_punitions.getBarGap());
        barWidth = Math.min(maxBarWidth, (avilableBarSpace / BarChart_punitions.getData().size()) - BarChart_punitions.getBarGap());
        avilableBarSpace=(barWidth + BarChart_punitions.getBarGap())* BarChart_punitions.getData().size();
        BarChart_punitions.setCategoryGap(catSpace-avilableBarSpace-BarChart_punitions.getBarGap());
    } while(barWidth < maxBarWidth && BarChart_punitions.getCategoryGap()>minCategoryGap);
    }
    
    private void afficherBarChart() 
    {      
        BarChart_punitions.getData().clear();
        SanctionService SS=new SanctionService();
        XYChart.Series dataSeries = new XYChart.Series();
        try
        {
           ResultSet rs=SS.getcountSanctioneleve(ideleve);
           while(rs.next())
           {
            int nombre=rs.getInt(2);
            String punition=rs.getString(1);
            dataSeries.getData().add(new XYChart.Data(punition, nombre));  
           }

           BarChart_punitions.getData().add(dataSeries);
        }
        catch(SQLException e){}
        
        setMaxBarWidth(20, 50);
        BarChart_punitions.widthProperty().addListener((obs,b,b1)->{
        Platform.runLater(()->setMaxBarWidth(20, 50));
    });        
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
        afficherBarChart();
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
