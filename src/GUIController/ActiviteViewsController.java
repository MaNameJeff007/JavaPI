/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Entities.*;
import Services.activiteService;
import Services.clubService;
import Services.evenementService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mohamed Turki
 */
public class ActiviteViewsController implements Initializable {

    private ObservableList<Activite> data = FXCollections.observableArrayList();

    @FXML
    private Button ajoutActivite;
    @FXML
    private Button retour;
    @FXML
    private Label clubBTNact;
    @FXML
    private Text deco;
    @FXML
    private Label idE;
    @FXML
    private Label eventBTNact;
    @FXML
    private TableView<Activite> tableviewActivite;
    @FXML
    private TableColumn<Activite, String> id_club;
    @FXML
    private TableColumn<Activite, String> nom_club;
    @FXML
    private TableColumn<Activite, String> image_club;
    @FXML
    private ComboBox<String> club_d_act;
    @FXML
    private TextField nom_Activite;
    @FXML
    private Button modifierActivite;
    @FXML
    private Button supprimerActivite;
    @FXML
    private TextField rechactivite;
    @FXML
    private Label cheminimage1;
    @FXML
    private TableColumn<Activite, String> image_club1;
    @FXML
    private TableColumn<Activite, String> image_club11;
    @FXML
    private ComboBox<String> type_activite;
    @FXML
    private Text deco12;
    private activiteService as = new activiteService();
    @FXML
    private TextField id_act;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            fillComboBox();
            fillComboBoxClub();
            image_club.setCellValueFactory(new PropertyValueFactory<>("NomActivite"));
            image_club1.setCellValueFactory(new PropertyValueFactory<>("TypeActivite"));
            nom_club.setCellValueFactory(new PropertyValueFactory<>("NomClub"));
            image_club11.setCellValueFactory(new PropertyValueFactory<>("Vote"));
            for (Activite a : as.afficherActivite()) {
                data.add(a);
            }
            ClearALLInput();
            refreshDataTable();
        } catch (SQLException ex) {
            Logger.getLogger(ActiviteViewsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        FilteredList<Activite> filteredData = new FilteredList<>(data, p -> true);
        rechactivite.textProperty().addListener((Observable, oldValue, newValue) -> {
            filteredData.setPredicate(xx -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                System.out.println(xx.getNomActivite().toLowerCase().contains(lowerCaseFilter));
                if (xx.getNomActivite().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        tableviewActivite.setItems(filteredData);
    }

    @FXML
    private void ajoutActivite(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Vouler vous vraiment ajouter cette activite ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            String typeActivite = type_activite.getSelectionModel().getSelectedItem();
            String nom_Act = nom_Activite.getText();
            String nom_club = club_d_act.getSelectionModel().getSelectedItem();

            Activite act = new Activite();
            act.setTypeActivite(typeActivite);
            act.setNomActivite(nom_Act);
            act.setUser_id(1);
            act.setNomClub(nom_club);
            act.setVote(0);
            as.ajouterActivite(act);
            for (Activite cb : as.afficherActivite()) {
                data.add(cb);
                data = as.afficherActivite();
                tableviewActivite.setItems(data);
            }
        }
    }

    @FXML
    private void retour(ActionEvent event) {
    }

    private void refreshDataTable() {
        activiteService cp = new activiteService();
        for (Activite cb : as.afficherActivite()) {
            data.add(cb);
            data = cp.afficherActivite();
            tableviewActivite.setItems(data);
        }
    }

    @FXML
    private void clubBTNAct(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GUIInterface/clubViews.fxml"));
            Scene scene = new Scene(root);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(scene);
            app_stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ClubViewsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void ClearALLInput() {
        club_d_act.setValue("Club");
        type_activite.setValue("type d'activite");
        nom_Activite.setText("");
    }

    @FXML
    private void clubbtnAc(MouseEvent event) {
    }

    @FXML
    private void deco(MouseEvent event) {
    }

    @FXML
    private void eventBTNAct(MouseEvent event) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/GUIInterface/EventViews.fxml"));
            Scene scene = new Scene(root);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(scene);
            app_stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ClubViewsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void clikedtableview(MouseEvent event) {
        activiteService as = new activiteService();

        if (tableviewActivite.getSelectionModel().getSelectedIndex() != -1) {
            Activite c = tableviewActivite.getItems().get(tableviewActivite.getSelectionModel().getSelectedIndex());

            type_activite.setValue(c.getNomClub());
            club_d_act.setValue(c.getTypeActivite());

            nom_Activite.setText(String.valueOf(c.getNomActivite()));
            id_act.setText(String.valueOf(c.getId()));
        }
    }

    @FXML
    private void modifierActivite(ActionEvent event) throws SQLException {
        String str = club_d_act.getValue();
        String type_act = type_activite.getValue();
        Activite a = new Activite();
        a.setId(Integer.parseInt(id_act.getText()));
        a.setNomActivite(nom_Activite.getText());
        a.setNomClub(str);
        a.setTypeActivite(type_act);
        as.modifierActivite(a);

        ClearALLInput();
        refreshDataTable();

    }

    @FXML
    private void supprimerActivite(ActionEvent event) throws SQLException {
        String id = id_act.getText();
        if (id.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Aucun objet selectionnée");
            alert.setContentText("S'il vous plait selectionner un produit à supprimer.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Suppression");
            alert.setContentText("Vouler vous vraiment supprimer cette activite ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                activiteService as2 = new activiteService();
                as2.supprimerActivite(Integer.valueOf(id));
                Activite cp = new Activite();
                as2.supprimerActivite(cp.getId());
                ClearALLInput();
                data.clear();
                for (Activite cb : as2.afficherActivite()) {
                    data.add(cb);
                    data = as2.afficherActivite();
                    tableviewActivite.setItems(data);
                }
            }
        }
    }

    @FXML
    private void eventBTNAc(MouseEvent event) {
    }

    public void fillComboBox() throws SQLException {
        ObservableList<String> clubName = as.GetClubNameForComboACT();
        club_d_act.setItems(clubName);
        System.out.println(clubName);
    }

    @FXML
    private void getACTTYPE(ActionEvent event) {
        String typeActivite = type_activite.getSelectionModel().getSelectedItem();
    }

    public void fillComboBoxClub() throws SQLException {
        type_activite.getItems().add("Activite scientifique");
        type_activite.getItems().add("Activite Sportif");
        type_activite.getItems().add("Activite Artistique");

    }
}
