/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Entities.Activite;
import Entities.Evenement;
import Services.activiteService;
import Services.evenementService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Mohamed Turki
 */
public class FrontEventViewsController implements Initializable {

    private ObservableList<Evenement> data;
    @FXML
    private TableView<Evenement> tableviewEvent;
    @FXML
    private TableColumn<Evenement, String> nom_club_table;
    @FXML
    private TableColumn<Evenement, String> event_name_table;
    @FXML
    private TableColumn<Evenement, String> event_start_table;
    @FXML
    private TableColumn<Evenement, String> event_end_table;
    @FXML
    private TextField rechEvent;
    @FXML
    private Label cheminimage1;
    @FXML
    private Text deco12;
    private activiteService as = new activiteService();

    private evenementService es = new evenementService();
    private evenementService cs = new evenementService();
    @FXML
    private Button participer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        nom_club_table.setCellValueFactory(new PropertyValueFactory<>("nom_club"));
        event_name_table.setCellValueFactory(new PropertyValueFactory<>("nom_evenement"));
        event_start_table.setCellValueFactory(new PropertyValueFactory<>("heure_debut"));
        event_end_table.setCellValueFactory(new PropertyValueFactory<>("heure_fin"));
        refreshDataTable();

    }

    @FXML
    private void clikedtableview(MouseEvent event) {
        if (tableviewEvent.getSelectionModel().getSelectedIndex() != -1) {
            Evenement c = tableviewEvent.getItems().get(tableviewEvent.getSelectionModel().getSelectedIndex());
            participer.setVisible(es.verif(c.getId(), 8) ? false : true);

        }

    }

    @FXML
    private void deco(MouseEvent event) {
    }

    @FXML
    private void searchBARev(ActionEvent event) {

    }

    private void refreshDataTable() {
        data = FXCollections.observableArrayList();
        for (Evenement c : es.afficherEvenement()) {
            data.add(c);
        }
        FilteredList<Evenement> filteredData = new FilteredList<>(data, p -> true);
        rechEvent.textProperty().addListener((Observable, oldValue, newValue) -> {
            filteredData.setPredicate(xx -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (xx.getNom_evenement().toLowerCase().contains(lowerCaseFilter)) {

                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        tableviewEvent.setItems(filteredData);
    }

    @FXML
    private void participerBtn(ActionEvent event) {
        if (tableviewEvent.getSelectionModel().getSelectedIndex() != -1) {
            Evenement c = tableviewEvent.getItems().get(tableviewEvent.getSelectionModel().getSelectedIndex());
// here u need to  set  the   current user id :D 
            es.addParticipant(c.getId(), Integer.parseInt(System.getProperty("id")));
            participer.setVisible(false);
        }
    }

}
