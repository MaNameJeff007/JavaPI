/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController.EdTech;

import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Callback;
import static jdk.nashorn.internal.objects.NativeArray.map;
import Entities.Course;
import Services.CourseService;
import java.util.ArrayList;

/**
 * FXML Controller class
 *
 * @author 21654
 */
public class CourseViewController implements Initializable {

    @FXML
    private ObservableList data;
    @FXML
    private TableView<Course> tableview;
    @FXML
    private Button newBtn;
    @FXML
    private TextField nomText;
    @FXML
    private TextField descText;
    @FXML
    private TextField typeText;
    @FXML
    private TextField contenuText;
    @FXML
    private Button BrowseButton;
    @FXML
    private AnchorPane anchorPane;

    String fileName = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //   tableview = new TableView();
        getCol();
        buildData();

    }

    public void getCol() {
        try {

            //ResultSet
            CourseService c = new CourseService();
            ResultSet rs = c.getAllCoursesResultSet();

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
                if (i == 0) {
                    col.setVisible(false);
                }
                /*
                if (i == rs.getMetaData().getColumnCount()-1) {
                    col.setVisible(false);
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data : " + e.getMessage());
        }
    }

    public void buildData() {
        data = FXCollections.observableArrayList();
        try {
            CourseService c = new CourseService();
            List<Course> rs = c.getAllCourses();
            /**
             * ******************************
             * Data added to ObservableList * ******************************
             */
            {
                //Iterate Row
                ObservableList<Course> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.size(); i++) {
                    //Iterate Column
                   // System.out.println("test : "+rs.getString(i));
                    /* 
                    String course = String.format("%d,%s,%s,%s,%s",rs.getInt("id"),rs.getString("nom")
                     ,rs.getString("description"),rs.getString("contenu"),rs.getInt("type_intelligence")
                     ,rs.getInt("niveau"));*/
                    //row.add(course);
                    row.add(rs.get(i));
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
                TableRow<Course> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        Object rowData = row.getItem();
                        System.out.println(rowData);
                        String[] cours;
                        cours = rowData.toString().split(",");
                        for (String s : cours) {
                            System.out.println(s);
                        }
                        nomText.setText(cours[1]);
                        descText.setText(cours[2]);
                        typeText.setText(cours[4]);
                        contenuText.setText(cours[3]);

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
        type = Integer.parseInt(typeText.getText());
        if (fileName == "") {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez choisir un fichier SVP");
            alert.setContentText("Le contenu est obligatoire");

            alert.showAndWait();
        }
        Course c = new Course(nomText.getText(), descText.getText(), fileName, type);
        CourseService cs = new CourseService();
        try {
            cs.AjouterCourse(c);
            System.out.println("here");
        } catch (Exception e) {
            System.out.println(e);
        }
        RefreshTable();
    }

    @FXML
    private void handleDeleteClick(ActionEvent event) {
        Object person = tableview.getSelectionModel().getSelectedItem();
        //   Course course = (Course)person;
        String[] s;
        s = person.toString().split(",");
        String id;
        id = s[0].substring(1);
        CourseService cs = new CourseService();
        int id2;
        id2 = Integer.parseInt(id);

        try {
            cs.DeleteCourse(id2);
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
        CourseService cs = new CourseService();
        int id2;
        id2 = Integer.parseInt(id);
        type = Integer.parseInt(typeText.getText().trim());

        Course c = new Course(id2, nomText.getText(), descText.getText(), contenuText.getText(), type, 2);

        try {
            cs.UpdateCourse(c);
        } catch (Exception e) {
            System.out.println(e);
        }
        RefreshTable();
    }

    @FXML
    public void clickItem(MouseEvent event) {
        if (event.getClickCount() == 2) //Checking double click
        {
            System.out.println(tableview.getSelectionModel().getSelectedItem());

        }
    }

    private void RefreshTable() {
        data.clear();
        buildData();
    }

    @FXML
    private void handleBrowseClick(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setInitialFileName("Exported.txt");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word Files (*.Doc)", "*.doc"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files (*.*)", "*"));
        File file = fc.showSaveDialog(anchorPane.getScene().getWindow());
        try {
            if (file != null) {
                File f;
                String tempPath = file.getCanonicalPath().toLowerCase();
                if (!(tempPath.endsWith(".txt") || tempPath.endsWith(".xml"))) {
                    String extension = fc.selectedExtensionFilterProperty().get().getExtensions().get(0).substring(1);
                    // default to .txt, if the user had *.* selected
                    if (extension.length() == 0) {
                        extension = ".pdf";
                    }
                    f = new File(file.getCanonicalPath() + extension);
                } else {
                    f = file.getCanonicalFile();
                }

                System.out.println(f);
                fileName = f.getPath();
                /* if (f.exists()) {
                 System.err.println("You will overwrite!");
                 }*/

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
