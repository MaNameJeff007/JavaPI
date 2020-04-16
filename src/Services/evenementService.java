/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.sql.*;
import java.time.LocalDate;
import BD.Database;
import Entities.Club;
import Entities.Evenement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Mohamed Turki
 */
public class evenementService {

    Connection connexion;

    public evenementService() {
        connexion = Database.getInstance().getConnexion();
    }

    public void ajouterEvenement(Evenement e) throws SQLException {
        String reqc = " select id from club where nomclub = '" + e.getNom_club() + "'";
        Statement stc;

        try {
            stc = connexion.createStatement();
            ResultSet resultat = stc.executeQuery(reqc);

            while (resultat.next()) {
                int id = resultat.getInt("id");
                String req = "INSERT INTO `evenement` (`NomClub` , `club_id`, `nom_evenement`, `heure_debut`, `heure_fin`) VALUES ('" + e.getNom_club() + "','" + id + "','" + e.getNom_evenement() + "','" + e.getHeure_debut() + "','" + e.getHeure_fin() + "')";
                Statement st;
                try {
                    st = connexion.createStatement();
                    st.executeUpdate(req);
                } catch (SQLException ex) {
                    Logger.getLogger(clubService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(clubService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void supprimerEvenement(int id) throws SQLException {
        String req = "DELETE FROM `evenement` WHERE id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }
    //   

    public void modifierEvenement(Evenement e) throws SQLException {
        String reqc = " select id from club where nomclub = '" + e.getNom_club() + "'";
        Statement st;

        try {
            st = connexion.createStatement();
            ResultSet resultat = st.executeQuery(reqc);

            while (resultat.next()) {
                int id = resultat.getInt("id");
                String req = "UPDATE `evenement` SET NomClub = ? , club_id = ? , nom_evenement = ? , heure_debut = ? , heure_fin = ? where id = ?";
                PreparedStatement pstm = connexion.prepareStatement(req);
                pstm.setString(1, e.getNom_club());
                pstm.setInt(2, id);
                pstm.setString(3, e.getNom_evenement());
                pstm.setDate(4, e.getHeure_debut());
                pstm.setDate(5, e.getHeure_fin());
                pstm.setInt(6, e.getId());
                pstm.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(clubService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//SELECT COUNT(*) as x FROM `participantevent` WHERE evenement_id =1
//INSERT INTO `participantevent` (`evenement_id`, `user_id`) VALUES ('1', '2');

    public boolean verif(int ide, int idu) {
        try {
            String req = " select *  from participantevent where evenement_id='" + ide + "' and user_id='" + idu+"'";
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

    public void addParticipant(int ide, int idu) {
        String req = "INSERT INTO `participantevent` (`evenement_id`, `user_id`) VALUES ('" + ide + "','" + idu + "')";
        Statement st;
        try {
            st = connexion.createStatement();
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(clubService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int CountParticipant(int id) {

        String req = " select COUNT(*) as x from participantevent where evenement_id=" + id;
        Statement st;
        int idx = 0;
        try {
            st = connexion.createStatement();
            ResultSet resultat = st.executeQuery(req);

            while (resultat.next()) {
                idx = resultat.getInt("x");

            }

        } catch (SQLException ex) {
            Logger.getLogger(clubService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idx;
    }

    public ObservableList<Evenement> afficherEvenement() {
        ObservableList<Evenement> mylist = FXCollections.observableArrayList();
        String req = " select * from evenement";
        Statement st;

        try {
            st = connexion.createStatement();
            ResultSet resultat = st.executeQuery(req);

            while (resultat.next()) {
                int id = resultat.getInt("id");
                String nom_club = resultat.getString("NomClub");
                String nom_evenement = resultat.getString("nom_evenement");
                Date heure_debut = resultat.getDate("heure_debut");
                Date heure_fin = resultat.getDate("heure_fin");
                Evenement c = new Evenement(id, nom_club, nom_evenement, heure_debut, heure_fin);
                mylist.add(c);
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

    public ObservableList<String> GetEventNameForComboACT() {
        ObservableList<String> mylist = FXCollections.observableArrayList();
        String req = "SELECT nom_evenement FROM evenement";
        Statement st;
        try {
            st = connexion.createStatement();
            ResultSet resultat = st.executeQuery(req);

            while (resultat.next()) {
                String nom_ev = resultat.getString("nom_evenement");

                mylist.add(nom_ev);
            }

        } catch (SQLException ex) {
            Logger.getLogger(clubService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mylist;
    }
}
