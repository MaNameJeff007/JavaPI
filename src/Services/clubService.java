/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Club;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import BD.Database;
import Entities.User;
import java.util.function.Predicate;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

/**
 *
 * @author Mohamed Turki
 *
 */
public class clubService {

    Connection connexion;

    public clubService() {
        connexion = Database.getInstance().getConnexion();
    }

//    public void ajouterClub(Club c) throws SQLException {
//        
//        String req = "INSERT INTO `Club` (`id`, `user_id`, `nomclub`, `nom_image`) VALUES (?, ?, ?, ?)";
//        PreparedStatement pstm = connexion.prepareStatement(req);
//        pstm.setInt(1, c.getId());
//        pstm.setInt(2, 1);
//        pstm.setString(3, c.getNomclub());
//        pstm.setString(4, c.getNom_image());
//        pstm.executeUpdate();
//        
//       
//    }
    public void ajouterClub(Club c) throws SQLException {

        String req = "insert into club ( user_id, nomclub, nom_image) values (1,'" + c.getNomclub() + "','" + c.getNom_image() + "')";
        Statement st;
        try {
            st = connexion.createStatement();
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(clubService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void supprimerClub(int id) throws SQLException {
        String req = "DELETE FROM `Club` WHERE id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    
     public boolean verif(int idc, int idu) {
        try {
            String req = " select *  from participantclub where club_id='" + idc + "' and user_id='" + idu+"'";
            Statement st;

            st = connexion.createStatement();
            ResultSet resultat = st.executeQuery(req);

            if (resultat.next()) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(clubService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void addParticipant(int idc, int idu) {
        String req = "INSERT INTO `participantclub` (`club_id`, `user_id`) VALUES ('" + idc + "','" + idu + "')";
        Statement st;
        try {
            st = connexion.createStatement();
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(clubService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void modifierNomClub(Club c) throws SQLException {
        String req = "UPDATE `Club` SET nomclub = ?,nom_image = ?,user_id = ? where id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setString(1, c.getNomclub());
        pstm.setString(2, c.getNom_image());
        pstm.setInt(3, c.getUser_id());
        pstm.setInt(4, c.getId());
        pstm.executeUpdate();
    }

    public ObservableList<Club> afficher() {
        ObservableList<Club> mylist = FXCollections.observableArrayList();
        String req = " select * from Club ";
        Statement st;
        Statement st2;
        try {
            st = connexion.createStatement();
            
            ResultSet resultat = st.executeQuery(req);

            while (resultat.next()) {
                int id = resultat.getInt("id");
                int user_id = resultat.getInt("user_id");
                String nomclub = resultat.getString("nomclub");
                String nom_image = resultat.getString("nom_image");
                String Userdata="";
                String reqq = " select nom,prenom from user where id = '"+user_id+"'";
                st2 = connexion.createStatement();
                ResultSet resultat2 = st2.executeQuery(reqq);
                if(resultat2.next())
                {
                    Userdata=resultat2.getString("nom")+"  "+resultat2.getString("prenom");
                }
                
                
                
                
                Club c = new Club(id, user_id, nomclub, nom_image,Userdata);
                mylist.add(c);
            }

        } catch (SQLException ex) {
            Logger.getLogger(clubService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mylist;
    }

    public User getUserPerId(int id) throws SQLException {
        String req = "SELECT nom,prenom FROM user where id=" + id;
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);
        User s = new User();
        while (rs.next()) {
            s.setNom(rs.getString("nom"));
            s.setPrenom(rs.getString("prenom"));
        }
        return (s);
    }

    public int getUserPerNomEtPrenom(String nom, String Prenom) throws SQLException {
        String req = "SELECT id FROM user where nom='" + nom + "' and prenom ='" + Prenom + "'";
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);
        while (rs.next()) {
            return rs.getInt("id");
        }
        return -1;
    }

    public ObservableList<String> GetEnsForCombo() {
        ObservableList<String> mylist = FXCollections.observableArrayList();
        String req = "SELECT * FROM user where roles LIKE \"%ROLE_ENSEIGNANT%\"";
        Statement st;
        try {
            st = connexion.createStatement();
            ResultSet resultat = st.executeQuery(req);
            while (resultat.next()) {
                int id = resultat.getInt("id");
                String nom = resultat.getString("nom");
                String prenom = resultat.getString("prenom");

                mylist.add(nom + " " + prenom);
            }
        } catch (SQLException ex) {
            Logger.getLogger(clubService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mylist;
    }

    public ObservableList<String> GetClubNameForCombo() {
        ObservableList<String> mylist = FXCollections.observableArrayList();
        String req = "SELECT nomclub FROM club";
        Statement st;
        try {
            st = connexion.createStatement();
            ResultSet resultat = st.executeQuery(req);

            while (resultat.next()) {
                String nomClub = resultat.getString("nomclub");

                mylist.add(nomClub);
            }

        } catch (SQLException ex) {
            Logger.getLogger(clubService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mylist;
    }

}
