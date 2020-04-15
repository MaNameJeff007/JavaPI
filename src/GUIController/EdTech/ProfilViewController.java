/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController.EdTech;

import Entities.Score;
import Services.ScoreService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author wafa
 */
public class ProfilViewController implements Initializable {

    @FXML
    private LineChart lineChart;
    @FXML
    private Button retourBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");

        lineChart.setTitle("Evolution du score ");

        /* XYChart.Series series = new XYChart.Series();
         series.setName("My portfolio");

         series.getData().add(new XYChart.Data("Jan", 23));
         series.getData().add(new XYChart.Data("Feb", 14));
         series.getData().add(new XYChart.Data("Mar", 15));
         series.getData().add(new XYChart.Data("Apr", 24));
         series.getData().add(new XYChart.Data("May", 34));
         series.getData().add(new XYChart.Data("Jun", 36));
         series.getData().add(new XYChart.Data("Jul", 22));
         series.getData().add(new XYChart.Data("Aug", 45));
         series.getData().add(new XYChart.Data("Sep", 43));
         series.getData().add(new XYChart.Data("Oct", 17));
         series.getData().add(new XYChart.Data("Nov", 29));
         series.getData().add(new XYChart.Data("Dec", 25));
         */
        Series series = populateSeries();
        lineChart.getData().add(series);
    }

    public Series populateSeries() {
        ScoreService s = new ScoreService();
        String id;
        id = System.getProperty("UserID");
        System.out.println(id);
        Integer id1 = 1;
        List<Score> ScoresList = new ArrayList<Score>();
        try {
            id1 = Integer.parseInt(id);
            ScoresList = s.GetAllScoresByUserID(id1);
        } catch (Exception e) {
            System.out.println(e);
        }
        XYChart.Series series = new XYChart.Series();
        series.setName("Evolution du score");
        for (Score sc : ScoresList) {
            series.getData().add(new XYChart.Data(sc.getDate().toString(), sc.getScore()));
            System.out.println(sc.getDate());
        }
        return series;

    }
    
    @FXML
    private void handleRetour(ActionEvent event) throws IOException {
          Stage stage = (Stage) retourBtn.getScene().getWindow();
                stage.close();
                Stage primaryStage = new Stage();
                primaryStage.close();
                
                AnchorPane root = FXMLLoader.load(getClass().getResource("/GUIInterface/EdTech/FrontQuiz.fxml"));
                primaryStage.setTitle("Interface de cours");
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
    }
    
    @FXML
    private void listeCoursAction(ActionEvent event)throws IOException{
         retourBtn.getScene().getWindow().hide();

        Stage Result = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/GUIInterface/EdTech/ListeCoursParType.fxml"));
        Scene scene = new Scene(root);
        Result.setScene(scene);
        Result.show();
        Result.setResizable(false);
    }
}
