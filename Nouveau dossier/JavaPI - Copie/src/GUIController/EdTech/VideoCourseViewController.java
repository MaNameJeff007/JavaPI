/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController.EdTech;

import java.awt.event.WindowAdapter;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import static javafx.scene.media.MediaPlayer.Status.PLAYING;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import static sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte0.runnable;

/**
 * FXML Controller class
 *
 * @author oucema
 */
public class VideoCourseViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    MediaView mediaview;
    @FXML
    AnchorPane anchor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Path path = Paths.get("C:\\Users\\21654\\Documents\\NetBeansProjects\\ProjetPI\\Cours\\Video\\cours-danglaismp4.mp4");

         if (System.getProperty("course") != null) {
            //path = System.getProperty("user.dir");
            path = Paths.get(System.getProperty("user.dir"), "Cours", "Video", System.getProperty("course"));
            System.out.println(path.getFileName());
        }
        MediaPlayer player = new MediaPlayer(new Media(getClass().getResource("cours-danglaismp4.mp4").toExternalForm()));
        player.setOnError(() -> System.out.println("Error : " + player.getError().toString()));

        player.setAutoPlay(true);
        player.play();

        mediaview.setMediaPlayer(player);

    }

    @FXML
    private void retourBtnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) mediaview.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        primaryStage.close();

        AnchorPane root = FXMLLoader.load(getClass().getResource("/GUIInterface/EdTech/FrontQuiz.fxml"));
        primaryStage.setTitle("Interface de cours");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
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
        Stage stage = (Stage) mediaview.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        primaryStage.close();

        BorderPane root = FXMLLoader.load(getClass().getResource("/GUIInterface/EdTech/Exercice/ExerciceParCoursFront.fxml"));
        primaryStage.setTitle("Interface de cours");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}
