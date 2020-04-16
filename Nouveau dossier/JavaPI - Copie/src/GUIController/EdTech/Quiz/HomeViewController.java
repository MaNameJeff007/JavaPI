/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController.EdTech.Quiz;

import GUIController.EdTech.CourseViewController;
import GUIController.EdTech.TestViewController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * FXML Controller class
 *
 * @author 21654
 */
public class HomeViewController implements Initializable {

    @FXML
    private TabPane tabPane;
// Inject tab content.
    @FXML
    private Tab courseView;
// Inject controller
    @FXML
    private CourseViewController courseViewController;

// Inject tab content.
    @FXML
    private Tab testView;
// Inject controller
    @FXML
    private TestViewController TestViewController;

    @FXML
    private Tab exerciceView;
// Inject controller
    @FXML
    private TestViewController exerciceViewController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
