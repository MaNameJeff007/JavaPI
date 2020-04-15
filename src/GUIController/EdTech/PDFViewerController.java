/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController.EdTech;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utils.PdfModel;

/**
 * FXML Controller class
 *
 * @author 21654
 */
public class PDFViewerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Pagination pagination;

    private PdfModel model;

    private String file;
    @FXML
    private Button exBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("file is : " + System.getProperty("course") + System.lineSeparator());
        Path path = Paths.get("E:\\NetBeans Projects\\sample.pdf");

        if (System.getProperty("course") != null) {
            //path = System.getProperty("user.dir");
            path = Paths.get(System.getProperty("user.dir"), "Cours", "PDF", System.getProperty("course"));
        }
        model = new PdfModel(path);
        pagination.setPageCount(model.numPages(path));
        pagination.setPageFactory(index -> new ImageView(model.getImage(index)));

    }

    @FXML
    void exBtnAction(ActionEvent event) throws IOException {
        //System.setProperty("type", String.valueOf(test.getTypeIntelligence()));
        /*Stage stage = (Stage) mediaview.getScene().getWindow();
         stage.close();
         Stage primaryStage = new Stage();
         // primaryStage.close();

         BorderPane root = FXMLLoader.load(getClass().getResource("/GUIInterface/EdTech/Exercice/ExerciceParCoursFront.fxml"));
         primaryStage.setTitle("Interface de cours");
         primaryStage.setScene(new Scene(root));
         */
        System.setProperty("type", String.valueOf(2));
        Stage stage = (Stage) exBtn.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        primaryStage.close();

        BorderPane root = FXMLLoader.load(getClass().getResource("/GUIInterface/EdTech/Exercice/ExerciceParCoursFront.fxml"));
        primaryStage.setTitle("Interface de cours");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}
