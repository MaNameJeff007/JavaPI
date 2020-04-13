/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Entities.Club;
import Services.clubService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mohamed Turki
 */
public class ClubViewsController implements Initializable {

    FileChooser fc1 = new FileChooser();
    File selectedFile1 = new File("");

    @FXML
    private Button addclub;
    @FXML
    private Button retour;

    @FXML
    private TextField id_club_d;
    @FXML
    private TextField nom_Respondable;
    @FXML
    private TextField nom_club_d;
    @FXML
    private TextField nom_image;

    @FXML
    private Text deco;
    @FXML
    private Label idE;
    @FXML
    private TableView<Club> tableview;
    @FXML
    private ComboBox<String> affcat;
    @FXML
    private TableColumn<Club, String> id_club;
    @FXML
    private TableColumn<Club, String> Id_User;
    @FXML
    private TableColumn<Club, String> nom_club;
    @FXML
    private TableColumn<Club, String> image_club;

    @FXML
    private ComboBox<String> Responsable__d;
    @FXML
    private Button image1;

    @FXML
    private Button modifierclub;
    @FXML
    private Button supprimer;
    @FXML
    private TextField rech;
    private ObservableList<Club> data = FXCollections.observableArrayList();
    @FXML
    private Label cheminimage1;
    @FXML
    private ImageView imageviewer1;
    clubService cs = new clubService();

    /**
     * Initializes the controller class.
     */
    Connection connexion;

    @FXML
    private void ajoutClub(ActionEvent Club) throws SQLException, IOException {
        controleClub();
        ObservableList<Club> club = FXCollections.observableArrayList();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Vouler vous vraiment ajouter ce club ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            //   String clubBox = id_club_d.getText();
            String responsableBox = nom_Respondable.getText();
            String nom_club = nom_club_d.getText();
            String nomImage = nom_image.getText();
            //  int id_club = Integer.parseInt(clubBox);
            int responsable = Integer.parseInt(responsableBox);
//            int idClub = 0;
//            List<Club> list = ClubType.AfficherCategorie();
//            String image1 = cheminimage1.getText();
            clubService cp = new clubService();
            Club c = new Club();
            // c.setId(12);
            c.setUser_id(1);
            c.setNomclub(nom_club);
            c.setNom_image(nomImage);
            cp.ajouterClub(c);
            for (Club cb : cs.afficher()) {
                club.add(cb);
                data = cp.afficher();
                tableview.setItems(club);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id_club.setCellValueFactory(new PropertyValueFactory<>("id"));
        Id_User.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        nom_club.setCellValueFactory(new PropertyValueFactory<>("nomclub"));
        image_club.setCellValueFactory(new PropertyValueFactory<>("nom_image"));

        for (Club c : cs.afficher()) {
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
                System.out.println(xx.getNomclub().toLowerCase().contains(lowerCaseFilter));
                if (xx.getNomclub().toLowerCase().contains(lowerCaseFilter)) {
                    System.out.println("cc");

                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        tableview.setItems(filteredData);
    }

//    public void fillComboBox() {
//        final ObservableList options = FXCollections.observableArrayList();
//        ComboBox comboBox = new ComboBox(options);
//        comboBox.setMaxHeight(30);
//        HBox hbox = new HBox(5);
//        options.clear();
//        try {
//            ResultSet cb = connexion.createStatement().executeQuery("SELECT nom,prenom FROM user");
//            while (cb.next()) {
//                options.add(cb.getString("nom")+" "+cb.getString("prenom"));
//            }
//
//            cb.close();
//        } catch (SQLException ex) {
//            System.out.println("error");
////            Logger.getLogger(UserInfoApp.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    @FXML
    private void image1(ActionEvent event) throws FileNotFoundException, IOException {
        File dest = new File("G:\\xamppp\\htdocs\\PIDEV\\WEB\\PIDEV\\web\\devis\\");

        fc1.setInitialDirectory(new File("C:\\"));
        selectedFile1 = fc1.showOpenDialog(null);
//        FileUtils.copyFileToDirectory(selectedFile1, dest);

        File newFile1 = new File("G:\\xamppp\\htdocs\\PIDEV\\WEB\\PIDEV\\web\\devis\\" + selectedFile1.getName());

        FileInputStream input1 = new FileInputStream(newFile1);
        Image image1 = new Image(input1);
        cheminimage1.setText(newFile1.getName());
        imageviewer1.setImage(image1);
    }

    @FXML
    private void controleClub() {

        if (nom_Respondable.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de Saisie");
            alert.setHeaderText("Erreur");
            alert.setContentText("Veuillez saisir une description valide");
            Optional<ButtonType> result = alert.showAndWait();
        } else if (nom_club_d.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de Saisie");
            alert.setHeaderText("Erreur");
            alert.setContentText("Veuillez saisir une description valide");
            Optional<ButtonType> result = alert.showAndWait();
        } else if (nom_image.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de Saisie");
            alert.setHeaderText("Erreur");
            alert.setContentText("Veuillez saisir une description valide");
            Optional<ButtonType> result = alert.showAndWait();
        }

//else if (cheminimage1.getText().equals("")) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Erreur de Saisie");
//            alert.setHeaderText("Erreur");
//            alert.setContentText("Veuillez selectionner une image");
//            Optional<ButtonType> result = alert.showAndWait();
//        }
    }

    @FXML
    private void retour(ActionEvent event
    ) {
    }

    @FXML
    private void deco(MouseEvent event
    ) {
    }

    @FXML
    private void clikedtableview(MouseEvent event) {
        if (tableview.getSelectionModel().getSelectedIndex() != -1) {
            Club c = tableview.getItems().get(tableview.getSelectionModel().getSelectedIndex());
            id_club_d.setText(String.valueOf(c.getId()));
            nom_Respondable.setText(String.valueOf(c.getUser_id()));
            nom_club_d.setText(String.valueOf(c.getNomclub()));
            nom_image.setText(String.valueOf(c.getNom_image()));
        }

    }

    @FXML
    private void modifierClub(ActionEvent event) throws SQLException {
        clubService cp = new clubService();
        Club c = new Club();
        c.setId(Integer.parseInt(id_club_d.getText()));
        c.setUser_id(Integer.parseInt(nom_Respondable.getText()));
        c.setNomclub(nom_club_d.getText());
        c.setNom_image(nom_image.getText());
        cp.modifierNomClub(c);
        ClearALLInput();
        refreshDataTable();
    }

    private void ClearALLInput() {
        id_club_d.setText("");
        nom_Respondable.setText("");
        nom_club_d.setText("");
        nom_image.setText("");
    }

    private void refreshDataTable() {
        clubService cp = new clubService();
        ObservableList<Club> club = FXCollections.observableArrayList();
        for (Club cb : cs.afficher()) {
            club.add(cb);
            data = cp.afficher();
            tableview.setItems(club);
        }
    }

    @FXML
    private void supprimer(ActionEvent event) throws SQLException {
        String id = id_club_d.getText();
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
                ObservableList<Club> club = FXCollections.observableArrayList();
                clubService cc = new clubService();
                cc.supprimerClub(Integer.valueOf(id));
                clubService cp = new clubService();
                Club c = new Club();
                cp.supprimerClub(c.getId());
                id_club_d.setText("");
                nom_Respondable.setText("");
                nom_club_d.setText("");
                nom_image.setText("");
                data.clear();
                for (Club cb : cs.afficher()) {
                    club.add(cb);
                    data = cp.afficher();
                    tableview.setItems(club);
                }
            }
        }
    }

}
