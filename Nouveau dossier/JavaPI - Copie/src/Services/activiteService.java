/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.sql.*;
import BD.Database;
import Entities.Activite;
import Entities.Club;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Mohamed Turki
 */
public class activiteService {

    Connection connexion;

    public activiteService() {
        connexion = Database.getInstance().getConnexion();
    }

    public void ajouterActivite(Activite a) throws SQLException {

        String req = "insert into activity ( user_id, NomClub, nomActivite, typeActivite, vote) values ('" + a.getUser_id() + "','" + a.getNomClub() + "','" + a.getNomActivite() + "','" + a.getTypeActivite() + "','" + a.getVote() + "')";
        Statement st;
        try {
            st = connexion.createStatement();
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(clubService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void supprimerActivite(int id) throws SQLException {
        String req = "DELETE FROM `activity` WHERE id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    public void modifierActivite(Activite a) throws SQLException {

        String req = "UPDATE `activity` SET NomClub= ?,nomActivite = ?,typeActivite=? where id = ?";

        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setString(1, a.getNomClub());
        pstm.setString(2, a.getNomActivite());
        pstm.setString(3, a.getTypeActivite());
        pstm.setInt(4, a.getId());
        pstm.executeUpdate();

    }

    public ObservableList<Activite> afficherActivite() {

        ObservableList<Activite> mylist = FXCollections.observableArrayList();
        String req = " select * from activity ";
        Statement st;
        try {
            st = connexion.createStatement();
            ResultSet resultat = st.executeQuery(req);

            while (resultat.next()) {
                int id = resultat.getInt("id");
                int user_id = resultat.getInt("user_id");
                String Nom_Club = resultat.getString("NomClub");
                String nomActivite = resultat.getString("nomActivite");
                String typeActivite = resultat.getString("typeActivite");
                int vote = resultat.getInt("vote");
                Activite c = new Activite(id, user_id, Nom_Club, nomActivite, typeActivite, vote);
                mylist.add(c);
            }

        } catch (SQLException ex) {
            Logger.getLogger(clubService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mylist;
    }

    public ObservableList<String> GetClubNameForComboACT() {
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
    public ObservableList<String> GetEventsNameForComboACT() {
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
