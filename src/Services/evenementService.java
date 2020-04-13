/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.sql.*;
import java.time.LocalDate;
import BD.DbConnection;
import Entities.Evenement;

/**
 *
 * @author Mohamed Turki
 */
public class evenementService {

    Connection connexion;

    public evenementService() {
        connexion = DbConnection.getInstance().getConnexion();
    }

    public void ajouterEvenement(Evenement e) throws SQLException {
        Date date_d = Date.valueOf(e.getHeure_debut());
        Date date_f = Date.valueOf(e.getHeure_fin());
        String req = "INSERT INTO `evenement` (`id`, `club_id`, `nom_evenement`, `heure_debut`, `heure_fin`) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setInt(1, e.getId());
        pstm.setInt(2, e.getClub_id());
        pstm.setString(3, e.getNom_evenement());
        pstm.setDate(4, date_d);
        pstm.setDate(5, date_f);
        pstm.executeUpdate();
    }

    public void supprimerEvenement(int id) throws SQLException {
        String req = "DELETE FROM `evenement` WHERE id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    public void modifierEvenement(int id, Evenement e) throws SQLException {
        String req = "UPDATE `evenement` SET justification = ? where id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setString(1, e.getNom_evenement());
        pstm.setInt(2, id);
        pstm.executeUpdate();
    }

    public void afficherEvenement() throws SQLException {
        String req = "SELECT * FROM evenement";
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);
        System.out.print("\n");
        while (rs.next()) {
            int id = rs.getInt("id");
            int club_id = rs.getInt("club_id");
            String nom_evenement = rs.getString("nom_evenement");
            Date date_d = rs.getDate("heure_debut");
            Date date_f = rs.getDate("heure_fin");
            System.out.format("%s, %s, %s, %s, %s=\n", id, club_id, nom_evenement, date_d, date_f);
        }
    }
}
