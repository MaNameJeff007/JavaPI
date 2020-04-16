/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Entities.Reclamation;
import Services.ReclamationService;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Selim Chikh Zaouali
 */
public class AjouterReclamationController implements Initializable {

    @FXML
    private Button btn_ajout;

    @FXML
    private ComboBox<String> matiere;
    @FXML
    private ComboBox<String> enfant;

    @FXML
    void onClick(ActionEvent event) throws SQLException {

        ObservableList<String> matieres = FXCollections.observableArrayList();
        ReclamationService rs = new ReclamationService();
        String val = enfant.getSelectionModel().getSelectedItem();
        //String val="Selim Zaouali";
        int indexEspace = val.indexOf(" ");
        String nomenf = val.substring(0, indexEspace);
        String prenomenf = val.substring(indexEspace + 1);
        String idk2 = rs.getMatiere(nomenf, prenomenf);
        matieres.add(idk2);
        matiere.setItems(matieres);
    }
    @FXML
    private Button retour;

    @FXML
    void retour(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUIInterface/Start.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Accueil");
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ReclamationService rs = new ReclamationService();
        Integer UserConnecte = Integer.parseInt(System.getProperty("id"));

        ObservableList<String> enfants = FXCollections.observableArrayList();

        try {
            ResultSet res = rs.getEnfants(UserConnecte);

            while (res.next()) {
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String idk = nom + " " + prenom;
                enfants.add(idk);
            }
            enfant.setItems(enfants);
        } catch (SQLException ex) {
            Logger.getLogger(AjouterReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //matiere.setItems(matieres);

        btn_ajout.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String det = matiere.getSelectionModel().getSelectedItem();
                try {
                    rs.ajouterReclamation(new Reclamation(LocalDateTime.now(), "non traitee", 25, UserConnecte, det));
                } catch (SQLException ex) {
                    Logger.getLogger(AjouterReclamationController.class.getName()).log(Level.SEVERE, null, ex);
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUIInterface/AfficherReclamation.fxml"));
                Parent root;
                try {
                    root = loader.load();
                    btn_ajout.getScene().setRoot(root);
                } catch (IOException ex) {
                    Logger.getLogger(AjouterReclamationController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }

}
