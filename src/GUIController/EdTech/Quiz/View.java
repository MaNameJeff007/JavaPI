package GUIController.EdTech.Quiz;

import GUIController.EdTech.JavaQuizController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author GENISYS-X
 */
public class View implements Initializable {

    @FXML
    private Label Marks;

    @FXML
    private Label typeIntell;

    @FXML
    private JFXButton home;

    /**
     *
     */
    public static int langno;

    private static View instance;

    /**
     *
     */
    public View() {
        instance = this;
    }

    /**
     *
     * @return
     */
    public static View getInstance() {
        return instance;
    }

    @FXML
    void goToHome(ActionEvent event) throws IOException {

        home.getScene().getWindow().hide();

        Stage Result = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/GUIInterface/EdTech/FrontQuiz.fxml"));
        Scene scene = new Scene(root);
        Result.setScene(scene);
        Result.show();
        Result.setResizable(false);

    }
    int no = 0;

    @FXML
    void goToCourse(ActionEvent event) throws IOException {

        if (no >= 0 && no < 10) {
            System.setProperty("type", "1");
            //goToC("/GUIInterface/EdTech/ListeCoursParType.fxml");

        } else if (no > 10 && no < 20) {
            System.setProperty("type", "2");
            //goToC("/GUIInterface/EdTech/ListeCoursParType.fxml");
        } else if (no > 20 && no < 31) {
            System.setProperty("type", "3");
           // goToC("/GUIInterface/EdTech/ListeCoursParType.fxml");
            
        }
          home.getScene().getWindow().hide();
        Stage Result = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/GUIInterface/EdTech/ListeCoursParType.fxml"));
        Scene scene = new Scene(root);
        Result.setScene(scene);
        Result.show();
        Result.setResizable(false);

    }

    public void goToC(String s) throws IOException {
      
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //Get Final Score
        no = JavaQuizController.getInstance().calCorrectAnswer();
        //Set Final Score
        Marks.setText(no + "/30");

        if (no >= 0 && no < 10) {
            typeIntell.setText("verbale");
            System.setProperty("type", "1");
        } else if (no >= 10 && no < 20) {
            typeIntell.setText("visuelle");
            System.setProperty("type", "2");
        } else if (no >= 20 && no < 31) {
            typeIntell.setText("auditive");
            System.setProperty("type", "3");
        }
    }

}
