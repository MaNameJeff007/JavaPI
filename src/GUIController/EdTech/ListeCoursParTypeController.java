/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController.EdTech;

import Entities.Course;
import Services.CourseService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author oucema
 */
public class ListeCoursParTypeController implements Initializable {

    static class XCell extends ListCell<Course> {

        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Consulter le cours");

        public XCell() {
            super();

            hbox.getChildren().addAll(label, pane, button);
            HBox.setHgrow(pane, Priority.ALWAYS);
            // button.setOnAction(event -> getListView().getItems().remove(getItem()));
            // button.setOnAction(event -> getListView().getItems().remove(getItem()));
            button.setOnAction(event -> {
                try {
                    //Stage stage = (Stage) button.getScene().getWindow();
                    // do what you have to do
                    // stage.close();
                    Course c = getItem();
                    Stage primaryStage = new Stage();
                    primaryStage.close();
                    System.setProperty("course", c.getContenu());
                    String t = System.getProperty("type");
                    System.out.println("t is :" + t);

                    if (t.equals("1")) {
                        System.out.println("t mara o5ra is :" + t);
                        BorderPane root = FXMLLoader.load(getClass().getResource("/GUIInterface/EdTech/PDFViewer.fxml"));
                        primaryStage.setScene(new Scene(root));
                        primaryStage.show();
                    } else {
                        AnchorPane root = FXMLLoader.load(getClass().getResource("/GUIInterface/EdTech/VideoCourseView.fxml"));
                        primaryStage.setScene(new Scene(root));
                        primaryStage.show();
                    }

                } catch (Exception e) {
                    System.out.println("hohohoho " + e.getMessage());
                    System.out.println(e.toString());
                    e.printStackTrace();
                }
            });

        }

        //@Override
        protected void updateItem(Course item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                String co = "Cours : " + item.getNom() + System.lineSeparator()
                        + "Description : " + item.getDescription();
                label.setText(co);
                setGraphic(hbox);
            }
        }
    }
    @FXML
    ListView listView;
    Pane pane = new Pane();
    @FXML
    Button retourBtn;

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        CourseService ser = new CourseService();
        int typeIntell = 1;
        System.out.println(System.getProperty("type"));
        if (System.getProperty("type") != null) {
            typeIntell = Integer.parseInt(System.getProperty("type"));
        }
        List<Course> listCou = ser.getListCoursesByIntellType(typeIntell);
        ObservableList<Course> list = FXCollections.observableArrayList();
        for (Course c : listCou) {
            // String s2 = String.format("Use %%n as a platform independent newline.%n");
            /*list.add("Cours : "+c.getNom()+ System.lineSeparator() 
             + "Description : "+ c.getDescription() );*/
            list.add(c);
        }
        listView.setItems(list);
        listView.setCellFactory(param -> new XCell());
        //pane.getChildren().add(listView);

    }

    @FXML
    private void retourBtnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) retourBtn.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        primaryStage.close();

        AnchorPane root = FXMLLoader.load(getClass().getResource("/GUIInterface/EdTech/FrontQuiz.fxml"));
        primaryStage.setTitle("Interface de cours");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
