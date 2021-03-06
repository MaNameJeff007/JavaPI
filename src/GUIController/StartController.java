/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Pane demandes;
    @FXML
    private Pane club;
    @FXML
    private ImageView profil;
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

    @FXML
    void logout(ActionEvent event) throws IOException {
FXMLLoader fxmlLoader = new FXMLLoader();
              fxmlLoader.setLocation(getClass().getResource("/GUIInterface/login.fxml"));
          System.clearProperty("role");
          System.clearProperty("id");
          System.clearProperty("email");
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("New Window");
            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    void gestionForum() {
        forum.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //affichage("/GUIInterface/Forum.fxml");
            bouton1.setVisible(true);
            bouton2.setVisible(false);
            bouton3.setVisible(false);
            bouton4.setVisible(false);
            bouton5.setVisible(false);
            bouton1.setText("Forum");
            numero_panel = 3;
            event.consume();
        });
    }

    void gestionclub() {
        club.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //affichage("/GUIInterface/Forum.fxml");
            bouton1.setVisible(true);
            bouton2.setVisible(true);
            bouton3.setVisible(false);
            bouton4.setVisible(false);
            bouton5.setVisible(false);
            bouton1.setText("club");
            bouton2.setText("evenement");
            numero_panel = 6;
            event.consume();
        });
    }

    void gestionDemandes() {
        demandes.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //affichage("/GUIInterface/Forum.fxml");
            bouton1.setVisible(true);
            bouton2.setVisible(true);
            bouton3.setVisible(true);
            bouton4.setVisible(false);
            bouton5.setVisible(false);
            bouton1.setText("Attestation");
            bouton2.setText("Reclamation");
            bouton3.setText("Permutation");
            numero_panel = 5;
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
        gestionclub();
        gestionDemandes();
        profil.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/GUIInterface/Profil.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("New Window");
                stage.setScene(scene);
                stage.show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
                event.consume();
            } catch (IOException ex) {
                Logger.getLogger(StartController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        bouton1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (numero_panel == 1) {
                affichage("/GUIInterface/acceuil.fxml");
            }
            if (numero_panel == 2) {
                affichage("/GUIInterface/eleve.fxml");
            }
            if (numero_panel == 3) {
                affichage("/GUIInterface/Forum.fxml");
            }
            if (numero_panel == 5) {
                affichage("/GUIInterface/AfficherAttestation.fxml");
            }
            if (numero_panel == 6) {
                affichage("/GUIInterface/FrontEventViews.fxml");
            }
            event.consume();
        });
        bouton2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            if (numero_panel == 5) {
                affichage("/GUIInterface/AfficherReclamation.fxml");
            }
            if (numero_panel == 6) {
                affichage("/GUIInterface/FrontActiviteViews.fxml");
            }
            event.consume();
        });
        bouton3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            if (numero_panel == 5) {
                affichage("/GUIInterface/AfficherPermutation.fxml");
            }
            event.consume();
        });
    }
}
