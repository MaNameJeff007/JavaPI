/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Entities.Club;
import Entities.Evenement;
import Entities.User;
import Services.activiteService;
import Services.clubService;
import Services.evenementService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mohamed Turki
 */
public class EventViewsController implements Initializable {

    private activiteService as = new activiteService();
    private evenementService es = new evenementService();

    Connection connexion;

    @FXML
    private Button retour;
    @FXML
    private VBox clubBTN;
    @FXML
    private Text deco;
    @FXML
    private Label idE;
    @FXML
    private Label eventBTN;
    @FXML
    private TableColumn<Evenement, String> id_club;
    @FXML
    private Label cheminimage1;
    @FXML
    private Button ajoutEvent;
    @FXML
    private TableView<Evenement> tableviewEvent;
    @FXML
    private ComboBox<String> Club_d;
    @FXML
    private TextField id_event_d;
    @FXML
    private TextField nom_event;
    @FXML
    private Button modifierEvent;
    @FXML
    private Button supprimerEvent;
    @FXML
    private Label activiteBTNAc;
    @FXML
    private TableColumn<Evenement, String> nom_club_table;
    @FXML
    private TableColumn<Evenement, String> event_name_table;
    @FXML
    private TableColumn<Evenement, String> event_start_table;
    @FXML
    private TableColumn<Evenement, String> event_end_table;
    @FXML
    private DatePicker event_start;
    @FXML
    private DatePicker event_end;
    @FXML
    private Text deco1;
    @FXML
    private Text deco11;
    //   private ObservableList<Evenement> data;

    @FXML
    private Text deco12;
    @FXML
    private ComboBox<String> events_Comb;
    @FXML
    private TextField rechEvent;
    private VBox statBOX;
    @FXML
    private BorderPane BorderPaneForStat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fillComboBox();
            fillComboBoxevents();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        nom_club_table.setCellValueFactory(new PropertyValueFactory<>("nom_club"));
        event_name_table.setCellValueFactory(new PropertyValueFactory<>("nom_evenement"));
        event_start_table.setCellValueFactory(new PropertyValueFactory<>("heure_debut"));
        event_end_table.setCellValueFactory(new PropertyValueFactory<>("heure_fin"));

        refreshDataTable();
        InitStat();
    }

    private void InitStat() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Events");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Parts..");

        BarChart barChart = new BarChart(xAxis, yAxis);
        for (Evenement e : es.afficherEvenement()) {
            XYChart.Series dataSeries1 = new XYChart.Series();
            dataSeries1.setName(e.getNom_evenement());
            dataSeries1.getData().add(new XYChart.Data(e.getNom_evenement(), es.CountParticipant(e.getId())));
            barChart.getData().add(dataSeries1);
        }

        VBox vbox = new VBox(barChart);
        BorderPaneForStat.setCenter(vbox);
    }

    private void ClearALLInput() {
        event_start.setValue(LocalDate.now());
        event_end.setValue(LocalDate.now());
        nom_event.setText("");
        events_Comb.setValue("Evenement");
        Club_d.setValue("Club");
    }

    private void refreshDataTable() {
        ObservableList<Evenement> data = FXCollections.observableArrayList();
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
    private void ajoutEvent(ActionEvent event) throws SQLException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Vouler vous vraiment ajouter ce club ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Evenement c = new Evenement();
            c.setId(Integer.parseInt(id_event_d.getText()));
            c.setNom_evenement(nom_event.getText());
            c.setNom_club(Club_d.getValue());

            c.setHeure_debut(Date.valueOf(event_start.getValue()));
            c.setHeure_fin(Date.valueOf(event_end.getValue()));
            es.ajouterEvenement(c);

            ClearALLInput();
            refreshDataTable();
        }

    }

    @FXML
    private void retour(ActionEvent event
    ) {
    }

    @FXML
    private void clubbtnAc(MouseEvent event
    ) {
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

    @FXML
    private void deco(MouseEvent event
    ) {
    }

    @FXML
    private void eventBTNAc(MouseEvent event
    ) {

    }

    @FXML
    private void clikedtableview(MouseEvent event
    ) {

        if (tableviewEvent.getSelectionModel().getSelectedIndex() != -1) {
            Evenement c = tableviewEvent.getItems().get(tableviewEvent.getSelectionModel().getSelectedIndex());

            id_event_d.setText(String.valueOf(c.getId()));
            nom_event.setText(String.valueOf(c.getNom_evenement()));
            event_start.setValue(c.getHeure_debut().toLocalDate());
            event_end.setValue(c.getHeure_fin().toLocalDate());
            Club_d.setValue(c.getNom_club());

        }
    }

    @FXML
    private void modifierEvent(ActionEvent event) throws SQLException {

        Evenement c = new Evenement();
        c.setId(Integer.parseInt(id_event_d.getText()));
        c.setNom_evenement(nom_event.getText());
        c.setNom_club(Club_d.getValue());

        c.setHeure_debut(Date.valueOf(event_start.getValue()));
        c.setHeure_fin(Date.valueOf(event_end.getValue()));
        es.modifierEvenement(c);
        ClearALLInput();
        refreshDataTable();
    }

    @FXML
    private void supprimerEvent(ActionEvent event) throws SQLException {
        String id = id_event_d.getText();
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
            alert.setContentText("Vouler vous vraiment supprimer ce club ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                es.supprimerEvenement(Integer.valueOf(id));
                ClearALLInput();
                refreshDataTable();
            }

        }

    }

    @FXML
    private void activiteBTNAc(MouseEvent event) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/GUIInterface/ActiviteViews.fxml"));
            Scene scene = new Scene(root);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(scene);
            app_stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ClubViewsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fillComboBox() throws SQLException {
        ObservableList<String> clubName = as.GetClubNameForComboACT();
        Club_d.setItems(clubName);
    }

    public void fillComboBoxevents() throws SQLException {
        ObservableList<String> eventName = es.GetEventNameForComboACT();
        events_Comb.setItems(eventName);
    }

}
