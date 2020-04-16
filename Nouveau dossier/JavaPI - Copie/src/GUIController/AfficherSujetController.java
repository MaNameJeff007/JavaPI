/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Services.CommentaireC;
import Entities.Commentaire;
import Entities.Likes;
import Entities.Signaler;
import Services.LikesC;
import Services.SignalerC;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class AfficherSujetController implements Initializable {

    @FXML
    private Label date;

    @FXML
    private Label titre;

    @FXML
    private Label description;

    @FXML
    private TableView<Commentaire> listecommentaire;

    @FXML
    private TextArea comm;
    @FXML
    private TableColumn<Commentaire, String> textecomm;
    @FXML
    private TableColumn<Commentaire, String> likescomm;
    @FXML
    private TableColumn<Commentaire, LocalDateTime> datecomm;

    @FXML
    private Button supprimer;
    @FXML
    private Button modifier;
    @FXML
    private Button like;
    @FXML
    private Button report;
    @FXML
    private Button home;
    @FXML
    private Button post;
    int size = 25;

    /**
     * Initializes the controller class.
     */
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

    @FXML
    public void like(ActionEvent event) {
        LikesC lc = new LikesC();
        try {
            if (lc.rechercheLikeComm(listecommentaire.getSelectionModel().getSelectedItem().getCommentaire_id())) {
                Likes l = new Likes(Integer.parseInt(System.getProperty("id")), "commentaire", listecommentaire.getSelectionModel().getSelectedItem().getCommentaire_id());
                lc.likeComm(l);
            } else {
                lc.DislikeCommplus(listecommentaire.getSelectionModel().getSelectedItem().getCommentaire_id());
            }

        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("like");
        info.setContentText(" Done");
        info.show();
        refresh();
    }

    @FXML
    public void report(ActionEvent event) {
        SignalerC sc = new SignalerC();
        try {
            if (sc.rechercheSignalerComm(listecommentaire.getSelectionModel().getSelectedItem().getCommentaire_id())) {
                Signaler sign = new Signaler(1, "commentaire", listecommentaire.getSelectionModel().getSelectedItem().getCommentaire_id());
                sc.SignalerComm(sign);
            } else {
                sc.SignalerCommplus(listecommentaire.getSelectionModel().getSelectedItem().getSujet_id());
            }

        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("report");
        info.setContentText(" Done");
        info.show();
    }

    public void clickItem(MouseEvent event) throws SQLException {
        if (event.getClickCount() == 1) //Checking double click
        {
            LikesC lc = new LikesC();
            if (listecommentaire.getSelectionModel().getSelectedItem().getCreateur_id() != Integer.parseInt(System.getProperty("id"))) {
                supprimer.setVisible(false);
                modifier.setVisible(false);
                like.setVisible(true);
                report.setVisible(true);
                like.setVisible(true);
                if (!lc.rechercheLikeComm(listecommentaire.getSelectionModel().getSelectedItem().getCommentaire_id())) {
                    like.setStyle("-fx-background-color: #77B5FE");
                } else {
                    like.setStyle(null);
                }
            } else {
                supprimer.setVisible(true);
                modifier.setVisible(true);
                like.setVisible(true);
                report.setVisible(false);
                if (!lc.rechercheLikeComm(listecommentaire.getSelectionModel().getSelectedItem().getCommentaire_id())) {
                    like.setStyle("-fx-background-color: #77B5FE");
                } else {
                    like.setStyle(null);
                }
            }
        }
    }

    @FXML
    public void modifComm(ActionEvent event) throws IOException {
        System.setProperty("comm_id", Integer.toString(listecommentaire.getSelectionModel().getSelectedItem().getCreateur_id()));
        System.setProperty("comm_texte", listecommentaire.getSelectionModel().getSelectedItem().getTexte());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUIInterface/ModifierCommentaire.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void PostComm(ActionEvent event) throws SQLException {

        titre.setText(System.getProperty("sujet_titre"));
        description.setText(System.getProperty("sujet_description"));
        date.setText(System.getProperty("sujet_date"));
        Commentaire c = new Commentaire(Integer.parseInt(System.getProperty("sujet_id")), Integer.parseInt(System.getProperty("id")),
                comm.getText(), LocalDateTime.now(), 0);
        CommentaireC cc = new CommentaireC();
        cc.ajouterCommentaire(c);
        refresh();
    }

    @FXML
    void supprimer() throws SQLException {
        CommentaireC comme = new CommentaireC();
        comme.delete(listecommentaire.getSelectionModel().getSelectedItem().getCommentaire_id());
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("deleted");
        info.setContentText(" Done");
        info.show();
        refresh();
    }

    @FXML
    void home(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUIInterface/Start.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        System.clearProperty("sujet_id");
        System.clearProperty("sujet_titre");
        System.clearProperty("sujet_date");
        System.clearProperty("sujet_description");
        System.setProperty("sujet", "1");
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();
        stage.toBack();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void refresh() {
        CommentaireC comme = new CommentaireC();
        try {
            listecommentaire.setItems(comme.getCommentaires(Integer.parseInt(System.getProperty("sujet_id"))));
            supprimer.setVisible(false);
            modifier.setVisible(false);
            report.setVisible(false);
            like.setVisible(false);
            like.setStyle(null);
            comm.clear();
        } catch (SQLException ex) {
            Logger.getLogger(AfficherSujetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        supprimer.setVisible(false);
        modifier.setVisible(false);
        like.setVisible(false);
        report.setVisible(false);
        titre.setText(System.getProperty("sujet_titre"));
        description.setText(System.getProperty("sujet_description"));
        date.setText(System.getProperty("sujet_date"));

        supprimer.setGraphic(image("src//images//delete.png", size));
        modifier.setGraphic(image("src//images//edit.png", size));
        report.setGraphic(image("src//images//report.png", size));
        like.setGraphic(image("src//images//like.png", size));
        home.setGraphic(image("src//images//home.png", size));
        post.setGraphic(image("src//images//post.png", size));

        CommentaireC comme = new CommentaireC();
        datecomm.setCellValueFactory(new PropertyValueFactory<>("date"));
        likescomm.setCellValueFactory(new PropertyValueFactory<>("score"));
        textecomm.setCellValueFactory(new PropertyValueFactory<>("texte"));
        try {
            listecommentaire.setItems(comme.getCommentaires(Integer.parseInt(System.getProperty("sujet_id"))));
        } catch (SQLException ex) {
            Logger.getLogger(AfficherSujetController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
