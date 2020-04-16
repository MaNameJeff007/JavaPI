/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import Entities.Reclamation;
import Services.ReclamationService;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
 * @author Selim Chikh Zaouali
 */
public class AfficherReclamationAdminController implements Initializable {

    @FXML
    private TableView<Reclamation> tableview;

    @FXML
    private TableColumn<Reclamation, LocalDateTime> date;

    @FXML
    private TableColumn<Reclamation, String> etat;

    @FXML
    private TableColumn<Reclamation, String> details;

    @FXML
    private TableColumn<Reclamation, String> parent;

    @FXML
    private Button traiter;
    @FXML
    private Button supprimer;

    @FXML
    public void clickItem(MouseEvent event) {
        if (event.getClickCount() == 1) {
            if (tableview.getSelectionModel().getSelectedItem().getEtat().equals("non traitee")) {
                traiter.setVisible(true);
                supprimer.setVisible(false);
            } else {
                traiter.setVisible(false);
                supprimer.setVisible(true);
            }
        }
    }

    @FXML
    void supprimerReclamation(ActionEvent event) {
        ReclamationService rs = new ReclamationService();
        try {
            rs.deleteReclamation(tableview.getSelectionModel().getSelectedItem().getId());
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Reclamation supprimée");
            info.setContentText("Terminé !");
            info.show();
        } catch (SQLException e) {
        }
        refresh();
    }

    @FXML
    void traiterReclamation(ActionEvent event) throws SQLException, MessagingException {
        ReclamationService rs = new ReclamationService();
        rs.changerEtat(tableview.getSelectionModel().getSelectedItem().getId());
        sendMail("mohamedyassine.ghadhoune@esprit.tn", "Reclamation traitée", "Votre réclamation a bien été traitée !");
        refresh();

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

        String from = "topisland123@gmail.com";
        String mdp = "ccitsjinzu";
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

    void refresh() {
        supprimer.setVisible(false);
        traiter.setVisible(false);
        ReclamationService rs = new ReclamationService();
        try {
            ArrayList<Reclamation> arrayList = (ArrayList<Reclamation>) rs.getAllReclamations();
            ObservableList obs = FXCollections.observableArrayList(arrayList);
            tableview.setItems(obs);
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
            details.setCellValueFactory(new PropertyValueFactory<>("details"));
            parent.setCellValueFactory(new PropertyValueFactory<>("parent"));
        } catch (SQLException ex) {
            Logger.getLogger(AfficherReclamationAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ReclamationService rs = new ReclamationService();
        traiter.setVisible(false);
        supprimer.setVisible(false);
        try {
            ArrayList<Reclamation> arrayList = (ArrayList<Reclamation>) rs.getAllReclamations();
            ObservableList obs = FXCollections.observableArrayList(arrayList);
            tableview.setItems(obs);
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
            details.setCellValueFactory(new PropertyValueFactory<>("details"));
            parent.setCellValueFactory(new PropertyValueFactory<>("parent"));
        } catch (SQLException ex) {
            Logger.getLogger(AfficherReclamationAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
