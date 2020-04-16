/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Entities.Club;
import Entities.User;
import Services.clubService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * FXML Controller class
 *
 * @author Mohamed Turki
 */
public class ClubViewsController implements Initializable {

    FileChooser fc1 = new FileChooser();
    File selectedFile1 = new File("");
    @FXML
    private Button retour;
    @FXML
    private TextField id_club_d;

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
    

    @FXML
    private Label cheminimage1;
    @FXML
    private ImageView imageviewer1;
    private clubService cs = new clubService();

    /**
     * Initializes the controller class.
     */
    Connection connexion;
    @FXML
    private Button ajoutClub;
    @FXML
    private VBox clubBTN;
    @FXML
    private Label eventBTN;
    @FXML
    private Label activiteBTN;
    @FXML
    private Text deco1;
    @FXML
    private Button FrontEventBTN;
    @FXML
    private Button FrontActiviterBTN;

    @FXML
    private void ajoutClub(ActionEvent Club) throws SQLException, IOException, MessagingException {
        controleClub();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Vouler vous vraiment ajouter ce club ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            String str = Responsable__d.getValue();
            String[] nomprenom = str.split(" ");
            //  String responsableBox = nom_Respondable.getText();
            String nom_club = nom_club_d.getText();
            String nomImage = nom_image.getText();
            System.out.println(cs.getUserPerNomEtPrenom(nomprenom[0], nomprenom[1]));
            int responsable = cs.getUserPerNomEtPrenom(nomprenom[0], nomprenom[1]);
            Club c = new Club();
            refreshDataTable();

            c.setUser_id(1);
            c.setNomclub(nom_club);
            c.setNom_image(nomImage);
            cs.ajouterClub(c);
           refreshDataTable();
        }
        String mailmsg = "We are " + nom_club_d.getText() + " club managed by " + Responsable__d.getValue() + " stay tuned for our events and activities !";
        String mailtitle = "Join our new Club" + nom_club_d.getText();
        sendMail("mohamedturki125@gmail.com", mailtitle, mailmsg);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id_club.setCellValueFactory(new PropertyValueFactory<>("id"));
        Id_User.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        nom_club.setCellValueFactory(new PropertyValueFactory<>("nomclub"));
        image_club.setCellValueFactory(new PropertyValueFactory<>("nom_image"));
 
        
       refreshDataTable();

        try {
            fillComboBox();
        } catch (SQLException ex) {
            Logger.getLogger(ClubViewsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void fillComboBox() throws SQLException {
        clubService as = new clubService();
        ObservableList<String> user = as.GetEnsForCombo();
        Responsable__d.setItems(user);
    }

    @FXML
    private void image1(ActionEvent event) throws FileNotFoundException, IOException {
        File dest = new File("D:\\wamp64\\www\\imagesPi");
        fc1.setInitialDirectory(new File("C:\\Users\\Mohamed Turki\\Pictures"));
        selectedFile1 = fc1.showOpenDialog(null);
        FileUtils.copyFileToDirectory(selectedFile1, dest);

        File newFile1 = new File("D:\\wamp64\\www\\imagesPi\\" + selectedFile1.getName());

        FileInputStream input1 = new FileInputStream(newFile1);
        Image image1 = new Image(input1);
        nom_image.setText(newFile1.getName());
        imageviewer1.setImage(image1);
    }

    private void controleClub() {
        try {
            if (Responsable__d.getValue().equals("Responsable")) {
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
//            else if (cheminimage1.getText().equals("")) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Erreur de Saisie");
//                alert.setHeaderText("Erreur");
//                alert.setContentText("Veuillez selectionner une image");
//                Optional<ButtonType> result = alert.showAndWait();
//            }
        } catch (Exception e) {
            System.out.println("Verifier vos champs");
        }
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
    private void clikedtableview(MouseEvent event) throws SQLException, FileNotFoundException {
        clubService as = new clubService();

        if (tableview.getSelectionModel().getSelectedIndex() != -1) {
            Club c = tableview.getItems().get(tableview.getSelectionModel().getSelectedIndex());
            User u = as.getUserPerId(c.getUser_id());
            id_club_d.setText(String.valueOf(c.getId()));
            Responsable__d.setValue(u.getNom() + " " + u.getPrenom());
            nom_club_d.setText(String.valueOf(c.getNomclub()));
            nom_image.setText(c.getNom_image());
            File file = new File("D:\\wamp64\\www\\imagesPi\\" + c.getNom_image());
            imageviewer1.setImage(new Image(file.toURI().toString()));
        }

    }

    @FXML
    private void modifierClub(ActionEvent event) throws SQLException {
        String str = Responsable__d.getValue();
        String[] xx = str.split(" ");
        int responsable = cs.getUserPerNomEtPrenom(xx[0], xx[1]);
        Club c = new Club();
        c.setId(Integer.parseInt(id_club_d.getText()));
        c.setUser_id(responsable);
        c.setNomclub(nom_club_d.getText());
        c.setNom_image(nom_image.getText());
        cs.modifierNomClub(c);
        ClearALLInput();
        refreshDataTable();
    }

    private void ClearALLInput() {
        id_club_d.setText("");
        Responsable__d.setValue("Responsable");
        nom_club_d.setText("");
        nom_image.setText("");
    }

    private void refreshDataTable() {
        ObservableList<Club> data = FXCollections.observableArrayList();
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
                clubService cc = new clubService();
                cc.supprimerClub(Integer.valueOf(id));
                clubService cp = new clubService();
                Club c = new Club();
                cp.supprimerClub(c.getId());
                id_club_d.setText("");
                Responsable__d.setValue("Responsable");

                nom_club_d.setText("");
                nom_image.setText("");
                ClearALLInput();
                refreshDataTable();
            }
        }
    }

    @FXML
    private void clubbtnAc(MouseEvent event) {

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
    private void eventBTNAc(MouseEvent event) {
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
    private void activiteBTNac(MouseEvent event) {
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

    private static Message prepareMessage(Session session, String from, String recepient, String subj, String desc) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject(subj);
            message.setText(desc);
            return message;
        } catch (Exception ex) {
            System.out.println("error send");
        }
        return null;
    }

    public static void sendMail(String recepient, String subj, String desc) throws MessagingException {
        System.out.println("Préparation du mail");
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");

        String from = "kastishm@gmail.com";
        String mdp = "med852med852";

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, mdp);
            }
        });

        Message message = prepareMessage(session, from, recepient, subj, desc);
        try {
            Transport.send(message);;
        } catch (MessagingException ex) {
            System.out.println(ex);
        }
        System.out.println("Mail envoyé");
    }

    @FXML
    private void FrontEventBTNAction(ActionEvent event) {
         try {
            Parent root = FXMLLoader.load(getClass().getResource("/GUIInterface/FrontEventViews.fxml"));
            Scene scene = new Scene(root);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(scene);
            app_stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ClubViewsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void FrontActiviterBTNAction(ActionEvent event) {
         try {
            Parent root = FXMLLoader.load(getClass().getResource("/GUIInterface/FrontActiviteViews.fxml"));
            Scene scene = new Scene(root);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(scene);
            app_stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ClubViewsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
