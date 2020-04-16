/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.sql.*;
import BD.DbConnection;
import Entities.Activite;

/**
 *
 * @author Mohamed Turki
 */
public class activiteService {

    Connection connexion;

    public activiteService() {
        connexion = DbConnection.getInstance().getConnexion();
    }

    public void ajouterActivite(Activite a) throws SQLException {
        String req = "INSERT INTO `activity` (`id`, `user_id`, `nomActivite`, `typeActivite`, `vote`) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setInt(1, a.getId());
        pstm.setInt(2, a.getUser_id());
        pstm.setString(3, a.getNomActivite());
        pstm.setString(4, a.getTypeActivite());
        pstm.setInt(5, a.getVote());
        pstm.executeUpdate();
    }

    public void supprimerActivite(int id) throws SQLException {
        String req = "DELETE FROM `activity` WHERE id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    public void modifierActivite(int id, Activite a) throws SQLException {
        String req = "UPDATE `activity` SET vote = ? where id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setString(1, a.getNomActivite());
        pstm.setInt(2, a.getVote());
        pstm.executeUpdate();
    }

    public void afficherActivite() throws SQLException {
        String req = "SELECT * FROM activity";
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);
        System.out.print("\n");
        while (rs.next()) {
            int id = rs.getInt("id");
            int user_id = rs.getInt("user_id");
            String nomActivite = rs.getString("nomActivite");
            String typeActivite = rs.getString("typeActivite");
            int vote = rs.getInt("vote");

            System.out.format("%s, %s, %s, %s, %s=\n", id, user_id, nomActivite, typeActivite, vote);
        }
    }
}
