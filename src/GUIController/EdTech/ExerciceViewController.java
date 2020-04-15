/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController.EdTech;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import Entities.Course;
import Entities.Exercice;
import Services.CourseService;
import Services.ExerciceService;

/**
 * FXML Controller class
 *
 * @author 21654
 */
public class ExerciceViewController implements Initializable {

    @FXML
    private ObservableList data;
    @FXML
    private TableView<Exercice> tableview;
    @FXML
    private Button newBtn;
    @FXML
    private TextField QuestionText;
    @FXML
    private TextField ReponseText;
    @FXML
    private TextField ScoreText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buildData();
    }

    @FXML
    public void buildData() {
        data = FXCollections.observableArrayList();
        try {

            //ResultSet
            ExerciceService c = new ExerciceService();
            ResultSet rs = c.getAllExercicesResultSet();

            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY * ********************************
             */
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableview.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");
                  if (i==0){
                    col.setVisible(false);
                }
            }

            /**
             * ******************************
             * Data added to ObservableList * ******************************
             */
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }
            /*          CourseService cs = new CourseService();
             List<Course> ca = cs.getAllCourses();
             for(Course b : ca){
             data.add(b);
             }
             */
            //FINALLY ADDED TO TableView
            tableview.setItems(data);

            tableview.setRowFactory(tv -> {
                TableRow<Exercice> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        Object rowData = row.getItem();
                        System.out.println(rowData);
                        String[] exercices;
                        exercices = rowData.toString().split(",");

                        for (int i = 0; i < exercices.length; i++) {
                            System.out.println(i + " : " + exercices[i]);
                        }

                        QuestionText.setText(exercices[2]);
                        ReponseText.setText(exercices[3]);
                        ScoreText.setText(exercices[4].substring(0, 2));
                    }
                });
                return row;
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data : " + e.getMessage());
        }
    }

    @FXML
    private void handleButtonClick(ActionEvent event) {
        // I really don't recommend using a single handler like this,
        // but it will work
        int type;
        type = Integer.parseInt(ScoreText.getText().trim());
        Exercice c = new Exercice(QuestionText.getText(), ReponseText.getText(), ScoreText.getText());
        ExerciceService cs = new ExerciceService();
        try {
            cs.ajouterExercice(c);
            System.out.println("here");
        } catch (Exception e) {
            System.out.println(e);
        }
        RefreshTable();
    }

    @FXML
    private void handleDeleteClick(ActionEvent event) {
        Object ex = tableview.getSelectionModel().getSelectedItem();
        //   Course course = (Course)person;
        String[] s;
        s = ex.toString().split(",");
        String id;
        id = s[0].substring(1);
        ExerciceService cs = new ExerciceService();
        int id2;
        id2 = Integer.parseInt(id);

        try {
            cs.DeleteExercice(id2);
        } catch (Exception e) {
            System.out.println(e);
        }
        RefreshTable();
    }

    @FXML
    private void handleUpdateClick(ActionEvent event) {
        Object person = tableview.getSelectionModel().getSelectedItem();
        //   Course course = (Course)person;
        String[] s;
        int type;
        s = person.toString().split(",");
        String id;
        id = s[0].substring(1).trim();
        ExerciceService cs = new ExerciceService();
        int id2;
        id2 = Integer.parseInt(id);
        type = Integer.parseInt(ScoreText.getText().trim());

        Exercice c = new Exercice(id2, QuestionText.getText(), ReponseText.getText(), ScoreText.getText());

        try {
            cs.UpdateExercice(c);
        } catch (Exception e) {
            System.out.println(e);
        }
        RefreshTable();
    }

    private void RefreshTable() {
        data.clear();
        buildData();
    }
}
