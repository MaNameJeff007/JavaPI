/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import BD.Database;
import Entities.User;
import static Entities.User.checkPassword;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author admin
 */
public class UserService {

    Connection connexion;
    Statement ste;

    public UserService() {
        connexion = Database.getInstance().getConnexion();
    }

    public String login(int username) throws SQLException {

        String emailUser = null;
        ste = connexion.createStatement();
        ResultSet rs = ste.executeQuery("select * from user where id=" + username);
        while (rs.next()) {
            emailUser = rs.getString("email");
        }
        return emailUser;
    }

    public String getRole(String username) throws SQLException {
        String Query = "Select roles from fos_user where username=?";
        PreparedStatement prd = connexion.prepareStatement(Query);
        prd.setString(1, username);
        ResultSet res = prd.executeQuery();
        String value = "";
        while (res.next()) {
            value = res.getString(1);
            System.out.println(value);

        }
        return value;
    }

    public ResultSet affichereleves() throws SQLException {
        String req = "SELECT * FROM `user` WHERE `roles` LIKE 'a:1:{i:0;s:10:\"ROLE_ELEVE\";}' ";
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);
        return rs;
    }

    public ResultSet selectidparent(int ideleve) throws SQLException {
        String req = "SELECT * FROM user WHERE id = '" + ideleve + "' ";
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);
        return rs;
    }

    public ResultSet selectelevesduparent(int idparent) throws SQLException {
        String req = "SELECT user.id, user.prenom, user.nom, classe.id, classe.niveau FROM user INNER JOIN classe ON user.classeeleve_id = classe.id WHERE user.parent_id= '" + idparent + "' ";
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);
        return rs;
    }

    public String selectemailparent(int idparent) throws SQLException {
        String email = " ";
        String req = "SELECT email FROM `user` WHERE id = '" + idparent + "' ";
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);
        while (rs.next()) {
            email = rs.getString("email");
        }

        return email;
    }

    public User getUserProfil(int id) throws SQLException {
        User u=new User();
        ste = connexion.createStatement();
        ResultSet rs = ste.executeQuery("select * from user where id=" + id);
        while (rs.next()) {
            u.setNom(rs.getString("nom"));
            u.setPrenom(rs.getString("prenom"));
            u.setEmail(rs.getString("email"));
            u.setUsername(rs.getString("username"));
        }
        return u;
    }
    public boolean update(String nom, String prenom, int id) throws SQLException {
        PreparedStatement pre = connexion.prepareStatement("update user set nom='" + nom + "' ,prenom='" + prenom + "' where id=" + id);
        if (pre.executeUpdate() == 1) {
            return true;
        }

        return false;
    }
    public void ajouterUser(User p) throws SQLException {
        Timestamp ts = p.getLast_login();
        java.util.Date date = new java.util.Date(ts.getTime());
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        Date inscription = Date.valueOf(p.getDate_Inscription());
        String req = "INSERT INTO `user` (`username`, `username_canonical`, `email`, `email_canonical`, `enabled`, `salt`, `password`, `last_login`, `confirmation_token`, `password_requested_at`, `roles`, `nom`, `prenom`, `date_embauche`, `date_inscription`, `parent_id`, `classeeleve_id`, `classeenseignant_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //   VALUES (1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13)//
        //   VALUES (?, ?, ?, ?, ?, N, ?, ?, N, N, ?, ?, ?)//
        PreparedStatement pstm = connexion.prepareStatement(req);
        //User(String 1, String 2, int 3, String 4, Timestamp 5, String 6, String 7, String 8, LocalDate 9, LocalDate 10, String 11, String 12, String 13)
        pstm.setString(1, p.getUsername()); //username//
        pstm.setString(2, p.getUsername()); //username_canonical//
        pstm.setString(3, p.getEmail());    //email//
        pstm.setString(4, p.getEmail());    //email_canonical//
        pstm.setInt(5, p.getEnabled());     //enabled//
        pstm.setString(6, null);             //salt=null//
        pstm.setString(7, p.getPassword()); //password//
        pstm.setDate(8, sqlDate);           //last login//
        pstm.setString(9, null);          //comfirmation token=null//
        pstm.setString(10, null);        //password requested at=null//
        pstm.setString(11, p.getRoles());   //roles//
        pstm.setString(12, p.getNom());   //nom//
        pstm.setString(13, p.getPrenom()); //prenom//
        pstm.setString(14, null); // date embauche//
        pstm.setDate(15, inscription); // date inscription//
        pstm.setString(16, null); //parentid//
        pstm.setString(17, null); //classe_eleve_id//
        pstm.setString(18, null); // classe_enseignant_id//
        pstm.executeUpdate();
    }

    public void addUser(User p) throws SQLException {
        Timestamp ts = p.getLast_login();
        java.util.Date date = new java.util.Date(ts.getTime());
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        System.out.println("1");
        Date inscription = Date.valueOf(p.getDate_Inscription());
        Date embauche = Date.valueOf(p.getDate_Embauche());
        String req = "INSERT INTO `user` (`username`, `username_canonical`, `email`, `email_canonical`, `enabled`, `salt`, `password`, `last_login`, `confirmation_token`, `password_requested_at`, `roles`, `nom`, `prenom`, `date_embauche`, `date_inscription`, `parent_id`, `classeeleve_id`, `classeenseignant_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //   VALUES (1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13)//
        //   VALUES (?, ?, ?, ?, ?, N, ?, ?, N, N, ?, ?, ?)//
        PreparedStatement pstm = connexion.prepareStatement(req);
        //User(String 1, String 2, int 3, String 4, Timestamp 5, String 6, String 7, String 8, LocalDate 9, LocalDate 10, String 11, String 12, String 13)
        pstm.setString(1, p.getUsername()); //username//
        pstm.setString(2, p.getUsername()); //username_canonical//
        pstm.setString(3, p.getEmail());    //email//
        pstm.setString(4, p.getEmail());    //email_canonical//
        pstm.setInt(5, p.getEnabled());     //enabled//
        pstm.setString(6, null);             //salt=null//
        pstm.setString(7, p.getPassword()); //password//
        System.out.println("2");

        pstm.setDate(8, sqlDate);           //last login//
        System.out.println("2");
        pstm.setString(9, null);          //comfirmation token=null//
        pstm.setString(10, null);        //password requested at=null//
        pstm.setString(11, p.getRoles());   //roles//
        pstm.setString(12, p.getNom());   //nom//
        pstm.setString(13, p.getPrenom()); //prenom//
        pstm.setDate(14, embauche); // date embauche//
        System.out.println("3");
        pstm.setDate(15, inscription); // date inscription//
        pstm.setString(16, null); //parentid//
        pstm.setString(17, null); //classe_eleve_id//
        pstm.setString(18, null); // classe_enseignant_id//
        System.out.println("4");
        pstm.executeUpdate();

    }

    public void sendMail(String sender, String recepient, String sujet, String m) throws Exception {
        System.out.println("Preparing to send email");
        Properties properties = new Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Your gmail address
        String myAccountEmail = sender;
        //Your gmail password
        String password = "monsavon123";

        //Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        //Prepare email message
        Message message = prepareMessage(session, myAccountEmail, recepient, sujet, m);

        //Send mail
        Transport.send(message);
        System.out.println("Message sent successfully");
    }

    public Message prepareMessage(Session session, String myAccountEmail, String recepient, String sujet, String m) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject(sujet);
            String htmlCode = m;
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (Exception ex) {

        }
        return null;
    }

    public boolean login(String username, String password) throws SQLException {
        String value = "";
        boolean result = false;
        String query = "Select * from user where username=?";
        PreparedStatement prd = connexion.prepareStatement(query);
        prd.setString(1, username);
        ResultSet res = prd.executeQuery();
        if (res == null) {
            System.out.println("user does not exist");
            return false;
        }
        while (res.next()) {
            value = res.getString("password");
            String role=res.getString("roles");
            
            if(role.contains("ENSEIGNANT"))
            {
             System.setProperty("classeenseignant_id", res.getString("classeenseignant_id"));  
            }
            else if(role.contains("PARENT"))
            {
               
            }
            else
            {
             System.setProperty("classeeleve_id", res.getString("classeeleve_id"));   
            }
            String id=Integer.toString(res.getInt("id"));   
            System.setProperty("id", id);
            System.setProperty("role", role);         
            System.setProperty("email", res.getString("email"));
        }
        result = checkPassword(password, value);
        return result;
    }

    public int getcode() {
        return (int) (Math.random() * (9999 - 1000));

    }
}
