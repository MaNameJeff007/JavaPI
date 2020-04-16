/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Entities.Commentaire;
import Services.CommentaireC;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class CommentaireBackController implements Initializable {

    @FXML
    private Label date;

    @FXML
    private Label titre;

    @FXML
    private Label description;

    @FXML
    private TableView<Commentaire> listecommentaire;

    @FXML
    private TableColumn<Commentaire, String> textecomm;
    @FXML
    private TableColumn<Commentaire, String> likescomm;
    @FXML
    private TableColumn<Commentaire, LocalDateTime> datecomm;

    @FXML
    private Button supprimer;
    @FXML
    private Button left;

    int size = 25;

    /**
     * Initializes the controller class.
     */
    public void clickItem(MouseEvent event) throws SQLException {
        if (event.getClickCount() == 1) //Checking double click
        {
            supprimer.setVisible(true);
        } else {
            supprimer.setVisible(false);
        }

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
        fxmlLoader.setLocation(getClass().getResource("/GUIInterface/SujetBack.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        System.clearProperty("sujet_id");
        System.clearProperty("sujet_titre");
        System.clearProperty("sujet_date");
        System.clearProperty("sujet_description");
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void refresh() {
        CommentaireC comme = new CommentaireC();
        try {
            listecommentaire.setItems(comme.getCommentaires(Integer.parseInt(System.getProperty("sujet_id"))));
            supprimer.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(AfficherSujetController.class.getName()).log(Level.SEVERE, null, ex);
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
        supprimer.setVisible(false);
        titre.setText(System.getProperty("sujet_titre"));
        description.setText(System.getProperty("sujet_description"));
        date.setText(System.getProperty("sujet_date"));

        supprimer.setGraphic(image("src//images//delete.png", size));
        left.setGraphic(image("src//images//left.png", size));

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
