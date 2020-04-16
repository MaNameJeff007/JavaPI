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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class BacKController implements Initializable {

    @FXML
    private Pane back;
    @FXML
    private Pane Scolarite;
    @FXML
    private Pane demandes;
    @FXML
    private Pane club;
    @FXML
    private Pane forum;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private Button btn4;
    @FXML
    private Button btn5;
    @FXML
    private Button btn6;
    @FXML
    private Button btn7;
    @FXML
    private Button btn8;
    private int numero_panel;

    /**
     * Initializes the controller class.
     */
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

    void Forum() {
        forum.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //affichage("/GUIInterface/Forum.fxml");
            btn1.setVisible(true);
            btn2.setVisible(true);
            btn3.setVisible(false);
            btn4.setVisible(false);
            btn5.setVisible(false);
            btn6.setVisible(false);
            btn7.setVisible(false);
            btn8.setVisible(false);

            btn1.setText("GESTION DES sujet");
            btn2.setText("GESTION DES reports");

            numero_panel = 3;
            event.consume();
        });
    }

    void club() {
        club.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //affichage("/GUIInterface/Forum.fxml");
            btn1.setVisible(true);
            btn2.setVisible(true);
            btn3.setVisible(true);
            btn4.setVisible(false);
            btn5.setVisible(false);
            btn6.setVisible(false);
            btn7.setVisible(false);
            btn8.setVisible(false);

            btn1.setText("GESTION DES club");
            btn2.setText("GESTION DES events");
            btn3.setText("GESTION DES activities");

            numero_panel = 6;
            event.consume();
        });
    }

    void gestionScolarite() {
        Scolarite.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //affichage("/GUIInterface/Forum.fxml");
            btn1.setVisible(true);
            btn2.setVisible(true);
            btn3.setVisible(true);
            btn4.setVisible(true);
            btn5.setVisible(true);
            btn6.setVisible(true);
            btn7.setVisible(true);
            btn8.setVisible(true);

            btn1.setText("GESTION DES CLASSES");
            btn2.setText("GESTION DES SALLES");
            btn3.setText("GESTION DES MATIERES");
            btn4.setText("GESTION DES COEFFICIENTS");
            btn5.setText("GESTION DES SEANCES");
            btn6.setText("ELEVES ET BULLETINS");
            btn7.setText("EMPLOIS DES ENSEIGANTS");
            btn8.setText("CONSULTER LES STATISTIQUES");

            numero_panel = 5;
            event.consume();
        });
    }

    void gestionDemandes() {
        demandes.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            btn1.setVisible(true);
            btn2.setVisible(true);
            btn3.setVisible(true);
            btn4.setVisible(false);
            btn5.setVisible(false);
            btn6.setVisible(false);
            btn7.setVisible(false);
            btn8.setVisible(false);
            btn1.setText("Attestation");
            btn2.setText("Reclamation");
            btn3.setText("Permutation");
            numero_panel = 4;
            event.consume();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btn1.setVisible(false);
        btn2.setVisible(false);
        btn3.setVisible(false);
        btn4.setVisible(false);
        btn5.setVisible(false);
        btn6.setVisible(false);
        btn7.setVisible(false);
        btn8.setVisible(false);
        gestionScolarite();
        gestionDemandes();
        Forum();
        club();
        btn1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (numero_panel == 5) {
                affichage("/GUIInterface/AfficherClasses.fxml");
            }
            if (numero_panel == 2) {
                System.out.println("2");
                // affichage("/GUIInterface/eleve.fxml");
            }
            if (numero_panel == 3) {
                affichage("/GUIInterface/SujetBack.fxml");
            }
            if (numero_panel == 4) {
                affichage("/GUIInterface/AfficherAttestationAdmin.fxml");
            }
            if (numero_panel == 6) {
                affichage("/GUIInterface/clubViews.fxml");
            }
            event.consume();
        });

        btn2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (numero_panel == 5) {
                affichage("/GUIInterface/AjouterSalle.fxml");
            }
            if (numero_panel == 3) {
                affichage("/GUIInterface/Reports.fxml");
            }
            if (numero_panel == 4) {
                affichage("/GUIInterface/AfficherReclamationAdmin.fxml");
            }
            if (numero_panel == 6) {
                affichage("/GUIInterface/EventViews.fxml");
            }
            event.consume();
        });

        btn3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (numero_panel == 5) {
                affichage("/GUIInterface/AjouterMatiere.fxml");
            }
            if (numero_panel == 4) {
                affichage("/GUIInterface/AfficherPermutationAdmin.fxml");
            }
            if (numero_panel == 6) {
                affichage("/GUIInterface/ActiviteViews.fxml");
            }
            event.consume();
        });

        btn4.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (numero_panel == 5) {
                affichage("/GUIInterface/AfficherCoeff.fxml");
            }
            event.consume();
        });

        btn5.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (numero_panel == 5) {
                affichage("/GUIInterface/AfficherSeance.fxml");
            }
            event.consume();
        });

        btn6.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (numero_panel == 5) {
                affichage("/GUIInterface/BulletinsElv.fxml");
            }
            event.consume();
        });

        btn7.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (numero_panel == 5) {
                affichage("/GUIInterface/EmploisEns.fxml");
            }
            event.consume();
        });

        btn8.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (numero_panel == 5) {
                affichage("/GUIInterface/MoyGStat.fxml");
            }
            event.consume();
        });
    }

    @FXML
    private void forum(ActionEvent event) {
    }

}
