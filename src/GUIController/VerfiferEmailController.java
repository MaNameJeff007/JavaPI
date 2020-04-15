/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController;

import static GUIController.ReportsController.sendMail;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
 * @author admin
 */
public class VerfiferEmailController implements Initializable {

    @FXML
    private TextField code;
    @FXML
    private Button oki;
    /**
     * Initializes the controller class.
     */
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
        System.out.println("prep...");
        Properties prop = new Properties();

        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");

        String from = "mohamedyassine.ghadhoune@esprit.tn";
        String mdp = "sakerfomek753";
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
        System.out.println("send ok");
    }

    @FXML
    void accepter(ActionEvent event) {
        if (code.getText().equals(System.getProperty("code"))) {
            System.setProperty("verif", "true");
            System.clearProperty("email5");
            System.clearProperty("code");
            Stage stage = (Stage) oki.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String msg = "";
        msg += System.getProperty("code");
        try {
            sendMail(System.getProperty("email5"), "Code activation", msg);
        } catch (MessagingException ex) {
            Logger.getLogger(VerfiferEmailController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
