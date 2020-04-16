/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import BD.Database;
import Entities.Commentaire;
import Entities.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author admin
 */
public class CommentaireC {

    Connection connexion;
    Statement ste;

    public CommentaireC() {
        connexion = Database.getInstance().getConnexion();
    }

    public void ajouterCommentaire(Commentaire c) throws SQLException {
        String req = "INSERT INTO `commentaire` (`id`, `sujet`, `createur`, `texte`, `date`, `score`) VALUES ( NULL, '"
                + c.getSujet_id() + "', '" + c.getCreateur_id() + "', '" + c.getTexte() + "', '" + c.getDate() + "', '"
                + c.getScore() + "') ";

        Statement stm = connexion.createStatement();
        stm.executeUpdate(req);
    }

    public int Createur(int id) throws SQLException {
        int iduser = 0;
        ste = connexion.createStatement();
        ResultSet rs = ste.executeQuery("select * from commentaire where id=" + id);
        while (rs.next()) {
            iduser = rs.getInt("createur");
        }
        return iduser;
    }

    public boolean delete(int id) throws SQLException {
        LikesC l = new LikesC();
        l.deleteCommentaire(id);
        SignalerC sc = new SignalerC();
        sc.deleteComm(id);
        PreparedStatement pre = connexion.prepareStatement("delete from commentaire where id=" + id);
        if (pre.executeUpdate() == 1) {
            return true;
        }

        return false;
    }

    public boolean deleteSujet(int id) throws SQLException {
        LikesC l = new LikesC();
        l.deleteCommentaire(id);
        PreparedStatement pre = connexion.prepareStatement("delete from commentaire where sujet=" + id);
        if (pre.executeUpdate() == 1) {
            return true;
        }

        return false;
    }

    public boolean update(String s, int id) throws SQLException {
        PreparedStatement pre = connexion.prepareStatement("update commentaire set texte='" + s + "' where id=" + id);
        if (pre.executeUpdate() == 1) {
            return true;
        }

        return false;
    }

    public ObservableList<Commentaire> getCommentaires(int s) throws SQLException {
        ObservableList<Commentaire> Commentaire = FXCollections.observableArrayList();

        String req = "select * from commentaire where sujet=" + s;
        Statement stm = connexion.createStatement();
        ResultSet result = stm.executeQuery(req);

        while (result.next()) {
            Commentaire p = new Commentaire(result.getInt("id"), result.getInt("sujet"), result.getInt("createur"), result.getString("texte"), result.getTimestamp("date").toLocalDateTime(), result.getInt("score"));
            Commentaire.add(p);
        }

        return Commentaire;
    }

}
