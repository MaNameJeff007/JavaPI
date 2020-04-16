/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

//import com.teknikindustries.bulksms.SMS;
import Entities.Permutation;
import Services.PermutationService;
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
public class AfficherPermutationAdminController implements Initializable {

    @FXML
    private TableView<Permutation> tableview;

    @FXML
    private TableColumn<Permutation, String> parent;

    @FXML
    private TableColumn<Permutation, String> nomprenom;

    @FXML
    private TableColumn<Permutation, String> classe_s;

    @FXML
    private TableColumn<Permutation, LocalDateTime> date;

    @FXML
    private TableColumn<Permutation, String> etat;

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
    void supprimerPermutation(ActionEvent event) throws SQLException {
        PermutationService ps = new PermutationService();
        try {
            ps.deletePermutation(tableview.getSelectionModel().getSelectedItem().getId());
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Permutation supprimée");
            info.setContentText("Terminé !");
            info.show();
        } catch (SQLException e) {
        }
        refresh();
    }

    @FXML
    void traiterPermutation(ActionEvent event) throws SQLException, MessagingException {
        PermutationService ps = new PermutationService();
        sendMail("mohamedyassine.ghadhoune@esprit.tn", "Permutation traitée", "Votre permutation a bien été traitée !");
        ps.permuter(tableview.getSelectionModel().getSelectedItem().getClasse_s(), tableview.getSelectionModel().getSelectedItem().getEleve_id());
        ps.changerEtat(tableview.getSelectionModel().getSelectedItem().getId());
        refresh();
        //SMS sms = new SMS();
        //sms.SendSMS("selimczaouali", "CCitsjinzu1","Votre permutation a été traitée", "93425430", "https://bulksms.vsms.net/eapi/submission/send_sms/2/2.0");
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
        PermutationService rs = new PermutationService();
        try {
            ArrayList<Permutation> arrayList = (ArrayList<Permutation>) rs.getAllPermutations();
            ObservableList obs = FXCollections.observableArrayList(arrayList);
            tableview.setItems(obs);
            nomprenom.setCellValueFactory(new PropertyValueFactory<>("enfant"));
            classe_s.setCellValueFactory(new PropertyValueFactory<>("classe_s"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
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
        PermutationService ps = new PermutationService();
        traiter.setVisible(false);
        supprimer.setVisible(false);
        try {
            ArrayList<Permutation> arrayList = (ArrayList<Permutation>) ps.getAllPermutations();
            ObservableList obs = FXCollections.observableArrayList(arrayList);
            tableview.setItems(obs);
            nomprenom.setCellValueFactory(new PropertyValueFactory<>("enfant"));
            classe_s.setCellValueFactory(new PropertyValueFactory<>("classe_s"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
            parent.setCellValueFactory(new PropertyValueFactory<>("parent"));
        } catch (SQLException ex) {
            Logger.getLogger(AfficherPermutationAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
