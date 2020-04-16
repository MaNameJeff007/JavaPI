/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Entities.Club;
import Entities.Evenement;
import Services.clubService;
import java.io.File;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Mohamed Turki
 */
public class FrontActiviteViewsController implements Initializable {

    private ObservableList<Club> data;

    @FXML
    private TableView<Club> tableview;
    @FXML
    private TableColumn<Club, String> nom_club;
    @FXML
    private TableColumn<Club, String> nom_club1;
    @FXML
    private TextField rech;
    @FXML
    private Label cheminimage1;
    @FXML
    private Text deco1;
    @FXML
    private ImageView imageviewer1;
    @FXML
    private Button inscrire;
    private clubService cc = new clubService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nom_club.setCellValueFactory(new PropertyValueFactory<>("nomclub"));
        nom_club1.setCellValueFactory(new PropertyValueFactory<>("nom_resp"));
        refreshDataTable();
    }

    private void refreshDataTable() {
        data = FXCollections.observableArrayList();
        for (Club c : cc.afficher()) {
            data.add(c);
        }
        FilteredList<Club> filteredData = new FilteredList<>(data, p -> true);
        rech.textProperty().addListener((Observable, oldValue, newValue) -> {
            filteredData.setPredicate(xx -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (xx.getNomclub().toLowerCase().contains(lowerCaseFilter)) {

                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        tableview.setItems(filteredData);
    }

    @FXML
    private void clikedtableview(MouseEvent event) {
          if (tableview.getSelectionModel().getSelectedIndex() != -1) {
            Club c = tableview.getItems().get(tableview.getSelectionModel().getSelectedIndex());
            File file = new File("D:\\wamp64\\www\\imagesPi\\" + c.getNom_image());
            imageviewer1.setImage(new Image(file.toURI().toString()));
            inscrire.setVisible(cc.verif(c.getId(), 8) ? false : true);
        }
    }

    @FXML
    private void deco(MouseEvent event) {
    }

    @FXML
    private void inscrireBTN(ActionEvent event) {
          if (tableview.getSelectionModel().getSelectedIndex() != -1) {
            Club c = tableview.getItems().get(tableview.getSelectionModel().getSelectedIndex());
// here u need to  set  the   current user id :D 
            cc.addParticipant(c.getId(), Integer.parseInt(System.getProperty("id")));
            inscrire.setVisible(false);
        }
    }

}
