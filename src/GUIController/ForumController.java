/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Entities.Likes;
import Entities.Signaler;
import Services.SujetC;
import Entities.Sujet;
import Services.LikesC;
import Services.SignalerC;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class ForumController implements Initializable {

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
    private Button modifier;
    @FXML
    private Button comment;
    @FXML
    private Button like;
    @FXML
    private Button report;
    @FXML
    private Button neww;
    @FXML
    private Button mine;
    @FXML
    private Button home;
    @FXML
    private TextField recherche;

    int size = 25;

    /**
     * Initializes the controller class.
     */
    @FXML

    void comment(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUIInterface/AfficherSujet.fxml"));

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
    public void addSujet(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUIInterface/AjouterSujet.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    public void like(ActionEvent event) {
        LikesC lc = new LikesC();
        try {
            if (lc.rechercheLikeSujet(listesujet.getSelectionModel().getSelectedItem().getSujet_id())) {
                Likes l = new Likes(listesujet.getSelectionModel().getSelectedItem().getSujet_id(), Integer.parseInt(System.getProperty("id")), "sujet");
                lc.likeSujet(l);
            } else {
                lc.DislikeSujetplus(listesujet.getSelectionModel().getSelectedItem().getSujet_id());
            }

        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("report");
        info.setContentText(" Done");
        info.show();
        refresh();
    }

    @FXML
    void rechercher(KeyEvent event) {
        SujetC s = new SujetC();
        System.out.println(recherche.getText());
        try {
            listesujet.setItems(s.recherche(recherche.getText()));
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void report(ActionEvent event) {
        SignalerC sc = new SignalerC();
        try {
            if (sc.rechercheSignalerSujet(listesujet.getSelectionModel().getSelectedItem().getSujet_id())) {
                Signaler sign = new Signaler(listesujet.getSelectionModel().getSelectedItem().getSujet_id(), 1, "sujet");
                sc.SignalerSujet(sign);
            } else {
                sc.SignalerSujetplus(listesujet.getSelectionModel().getSelectedItem().getSujet_id());
            }

        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("report");
        info.setContentText(" Done");
        info.show();
    }

    @FXML
    public void modifSujet(ActionEvent event) throws IOException {
        System.setProperty("sujet_id", Integer.toString(listesujet.getSelectionModel().getSelectedItem().getSujet_id()));
        System.setProperty("sujet_titre", listesujet.getSelectionModel().getSelectedItem().getTitre());
        System.setProperty("sujet_description", listesujet.getSelectionModel().getSelectedItem().getDescription());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUIInterface/AjouterSujet.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    public void clickItem(MouseEvent event) throws SQLException {
        if (event.getClickCount() == 1) //Checking double click
        {
            LikesC lc = new LikesC();
            comment.setVisible(true);
            //  System.out.println(listesujet.getSelectionModel().getSelectedItem().getCreateur_id());
            if (listesujet.getSelectionModel().getSelectedItem().getCreateur_id() != Integer.parseInt(System.getProperty("id"))) {
                supprimer.setVisible(false);
                modifier.setVisible(false);
                report.setVisible(true);

                like.setVisible(true);
                if (!lc.rechercheLikeSujet(listesujet.getSelectionModel().getSelectedItem().getSujet_id())) {
                    like.setStyle("-fx-background-color: #77B5FE");
                    like.setGraphic(image("src//images//dislike.png", size));
                } else {
                    like.getStyleClass().add("round-red");
                    like.setGraphic(image("src//images//like.png", size));
                }
            } else {
                supprimer.setVisible(true);
                modifier.setVisible(true);
                like.setVisible(true);
                report.setVisible(false);
                if (!lc.rechercheLikeSujet(listesujet.getSelectionModel().getSelectedItem().getSujet_id())) {
                    like.setStyle("-fx-background-color: #77B5FE");
                    like.setGraphic(image("src//images//dislike.png", size));
                } else {
                    like.setStyle(null);
                    like.setGraphic(image("src//images//like.png", size));
                }
            }
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
        modifier.setVisible(false);
        comment.setVisible(false);
        report.setVisible(false);
        like.setVisible(false);
        like.setStyle(null);
        SujetC sujet = new SujetC();
        try {
            listesujet.setItems(sujet.getAll());
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void mine(ActionEvent event) {

        SujetC sujet = new SujetC();
        titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        vues.setCellValueFactory(new PropertyValueFactory<>("vues"));
        //button.setCellValueFactory(new PropertyValueFactory<>("vues"));
        SujetC s = new SujetC();
        System.out.println(Integer.parseInt(System.getProperty("id")));
        try {
            listesujet.setItems(s.getOwner(Integer.parseInt(System.getProperty("id"))));
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void home(ActionEvent event) {

        SujetC sujet = new SujetC();
        titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        vues.setCellValueFactory(new PropertyValueFactory<>("vues"));
        SujetC s = new SujetC();
        try {
            listesujet.setItems(s.getAll());
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        vues.setCellValueFactory(new PropertyValueFactory<>("vues"));

        supprimer.setGraphic(image("src//images//delete.png", size));
        modifier.setGraphic(image("src//images//edit.png", size));
        comment.setGraphic(image("src//images//comment.png", size));
        report.setGraphic(image("src//images//report.png", size));
        like.setGraphic(image("src//images//like.png", size));
        neww.setGraphic(image("src//images//new.png", size));
        mine.setGraphic(image("src//images//mine.png", size));
        home.setGraphic(image("src//images//home.png", size));

        supprimer.setVisible(false);
        modifier.setVisible(false);
        comment.setVisible(false);
        report.setVisible(false);
        like.setVisible(false);
        SujetC s = new SujetC();
        try {
            listesujet.setItems(s.getAll());
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
