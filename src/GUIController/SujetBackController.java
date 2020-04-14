/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Entities.Sujet;
import Services.SujetC;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class SujetBackController implements Initializable {

    @FXML
    private TableView<Sujet> listesujet;
    @FXML
    private TableColumn<Sujet, String> titre;
    @FXML
    private TableColumn<Sujet, String> score;
    @FXML
    private TableColumn<Sujet, String> vues;
    @FXML
    private TableColumn<Sujet, String> description;
    @FXML
    private TableColumn<Sujet, LocalDateTime> date;
    @FXML
    private Button supprimer;
    @FXML
    private Button comment;
    @FXML
    private Button back;
    @FXML
    private TextField recherche;

    int size = 25;

    /**
     * Initializes the controller class.
     */
    @FXML
    void comment(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUIInterface/CommentaireBack.fxml"));

        System.setProperty("sujet_id", Integer.toString(listesujet.getSelectionModel().getSelectedItem().getSujet_id()));
        System.setProperty("sujet_titre", listesujet.getSelectionModel().getSelectedItem().getTitre());
        System.setProperty("sujet_description", listesujet.getSelectionModel().getSelectedItem().getDescription());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = listesujet.getSelectionModel().getSelectedItem().getDate().format(formatter);

        SujetC s = new SujetC();
        s.updateVues(listesujet.getSelectionModel().getSelectedItem().getSujet_id());
        System.setProperty("sujet_date", formatDateTime);
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void rechercher(KeyEvent event) {
        SujetC s = new SujetC();
        try {
            listesujet.setItems(s.recherche(recherche.getText()));
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void clickItem(MouseEvent event) throws SQLException {
        if (event.getClickCount() == 1) //Checking double click
        {
            comment.setVisible(true);
            supprimer.setVisible(true);
        } else {
            supprimer.setVisible(false);
            comment.setVisible(false);
        }
    }

    @FXML
    void supprimer() throws SQLException {
        SujetC s = new SujetC();
        s.delete(listesujet.getSelectionModel().getSelectedItem().getSujet_id());
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("deleted");
        info.setContentText(" Done");
        info.show();
        refresh();
    }

    void refresh() {
        supprimer.setVisible(false);
        comment.setVisible(false);
        SujetC sujet = new SujetC();
        try {
            listesujet.setItems(sujet.getAll());

        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void home(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUIInterface/BacK.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public ImageView image(String s, int w) {
        BufferedImage b = null;
        try {
            b = ImageIO.read(new File(s));
        } catch (IOException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
        WritableImage i = SwingFXUtils.toFXImage(b, null);
        ImageView v = new ImageView(i);
        v.setPreserveRatio(true);
        v.setFitWidth(w);
        return v;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SujetC sc = new SujetC();
        try {
            TextFields.bindAutoCompletion(recherche, sc.getList());
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }

        supprimer.setGraphic(image("src//images//delete.png", size));
        comment.setGraphic(image("src//images//comment.png", size));
        back.setGraphic(image("src//images//left.png", size));

        titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        vues.setCellValueFactory(new PropertyValueFactory<>("vues"));
        supprimer.setVisible(false);
        comment.setVisible(false);
        SujetC s = new SujetC();
        try {
            listesujet.setItems(s.getAll());

        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
