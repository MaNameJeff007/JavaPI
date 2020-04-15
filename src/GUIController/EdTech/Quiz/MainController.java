package GUIController.EdTech.Quiz;

import GUIController.application.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author GENISYS-X
 */
public class MainController implements Initializable {

    @FXML
    private AnchorPane anPane;

    @FXML
    private JFXButton round1;

    @FXML
    private JFXButton roundtwo;

    private static MainController instance;

    private double xOffset;
    private double yOffset;

    static int random_no;

    /**
     *
     */
    public MainController() {
        // instance = this;
    }

    /**
     *
     * @return
     */
    public static MainController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //Draggeable Screen
        anPane.setOnMousePressed(event -> {
            xOffset = Main.getPrimaryStage().getX() - event.getScreenX();
            yOffset = Main.getPrimaryStage().getY() - event.getScreenY();
            anPane.setCursor(Cursor.CLOSED_HAND);
        });

        anPane.setOnMouseDragged(event -> {
            Main.getPrimaryStage().setX(event.getScreenX() + xOffset);
            Main.getPrimaryStage().setY(event.getScreenY() + yOffset);

        });

        anPane.setOnMouseReleased(event -> {
            anPane.setCursor(Cursor.DEFAULT);
        });

    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void roundOneAction(ActionEvent event) throws IOException {
        /* String sceneFile = "../Style/MainQuizView.fxml";
         Parent root = null;
         URL url = null;
         try {
         url = getClass().getResource(sceneFile);
         root = FXMLLoader.load(url);
         System.out.println("  fxmlResource = " + sceneFile);
         } catch (Exception ex) {
         System.out.println("Exception on FXMLLoader.load()");
         System.out.println("  * url: " + url);
         System.out.println("  * " + ex);
         System.out.println("    ----------------------------------------\n");
         throw ex;
         }*/

        round1.getScene().getWindow().hide();

        //Stage signup = new Stage();
        //Parent root2 = FXMLLoader.load(getClass().getClassLoader().getResource("../Style/MainQuizView.fxml"));
        //   Parent root = FXMLLoader.load(getClass().getResource("/projet/GUI/quiz/Style/MainQuizView.fxml"));
        AnchorPane root = FXMLLoader.load(getClass().getResource("/GUIInterface/EdTech/Quiz/MainQuizView.fxml"));
        Stage signup = new Stage();
        //URL url = new File("./src/projet/GUI/quiz/Style/MainQuizView.fxml").toURI().toURL();
        //Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        signup.setScene(scene);
        signup.show();
        signup.setResizable(false);

        random_no = RandomNumbers.randomFunction(); //get random no 
    }

    @FXML
    void closeApp(ActionEvent event) {

        System.exit(0); //exit from application

    }

    /**
     *
     * @return
     */
    public int getRandomNo() {

        return random_no; //return rand no 

    }

}
