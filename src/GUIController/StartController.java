/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class StartController implements Initializable {

    @FXML
    private Pane back;
    @FXML
    private ImageView forum;
    @FXML
    private Button bouton1;
    @FXML
    private Button bouton2;
    @FXML
    private Button bouton3;
    @FXML
    private Button bouton4;
    @FXML
    private Button bouton5;
    /**
     * Initializes the controller class.
     */
    private int numero_panel;

    @FXML
    void forum(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUIInterface/Forum.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    void affichage(String x) {
        Parent fxml;

        try {
            fxml = FXMLLoader.load(getClass().getResource(x));
            back.getChildren().removeAll();
            back.getChildren().setAll(fxml);

        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    void gestionForum() {
        forum.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //affichage("/GUIInterface/Forum.fxml");
            bouton1.setVisible(true);
            bouton2.setVisible(true);
            bouton3.setVisible(true);
            bouton4.setVisible(false);
            bouton5.setVisible(false);
            bouton1.setText("Forum");
            numero_panel = 3;
            event.consume();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bouton1.setVisible(false);
        bouton2.setVisible(false);
        bouton3.setVisible(false);
        bouton4.setVisible(false);
        bouton5.setVisible(false);
        gestionForum();
        
        bouton1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (numero_panel == 1) {
                affichage("/GUIInterface/acceuil.fxml");
            }if (numero_panel == 2) {
                affichage("/GUIInterface/eleve.fxml");
            }
            if (numero_panel == 3) {
                affichage("/GUIInterface/Forum.fxml");
            }
            event.consume();
        });
        bouton2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            System.out.println("2");

            event.consume();
        });
        bouton3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            System.out.println("3");

            event.consume();
        });
    }
}
