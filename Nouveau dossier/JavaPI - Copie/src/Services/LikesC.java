/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import BD.Database;
import Entities.Likes;
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
public class LikesC {

    Connection connexion;
    Statement ste;

    public LikesC() {
        connexion = Database.getInstance().getConnexion();
    }

    public void likeSujet(Likes s) throws SQLException {
        String req = "INSERT INTO `likes` (`id`, `sujet`, `commentaire`, `createur`,`type`) VALUES ( NULL, '"
                + s.getSujet_id() + "', NULL, '" + Integer.parseInt(System.getProperty("id")) + "', '" + s.getType() + "') ";
        Statement stm = connexion.createStatement();
        stm.executeUpdate(req);
        String req2 = "update sujet set score=score+1 where id=" + s.getSujet_id();
        Statement stm2 = connexion.createStatement();
        stm2.executeUpdate(req2);

    }

    public boolean DislikeSujetplus(int id) throws SQLException {
        String req2 = "update sujet set score=score-1 where id=" + id;
        Statement stm2 = connexion.createStatement();
        stm2.executeUpdate(req2);
        PreparedStatement pre = connexion.prepareStatement("delete from likes where sujet='" + id + "' and createur=" + Integer.parseInt(System.getProperty("id")));
        if (pre.executeUpdate() == 1) {
            return true;
        }

        return false;
    }

    public boolean rechercheLikeSujet(int id) throws SQLException {
        ste = connexion.createStatement();
        Likes s = null;
        ResultSet rs = ste.executeQuery("select * from likes where sujet='" + id + "' and createur=" + Integer.parseInt(System.getProperty("id")));
        while (rs.next()) {
            s = new Likes(rs.getInt("sujet"), Integer.parseInt(System.getProperty("id")), "sujet");
        }
        if (s == null) {
           // System.out.println("mahouch mawjoud/true");
            return true;
        } else {
          //  System.out.println("mawjoud/flase");
            return false;
        }

    }

    public void likeComm(Likes s) throws SQLException {
        String req = "INSERT INTO `likes` (`id`, `sujet`, `commentaire`, `createur`,`type`) VALUES ( NULL, NULL ,'"
                + s.getCommentaire_id() + "', '" + Integer.parseInt(System.getProperty("id")) + "', '" + s.getType() + "') ";
        Statement stm = connexion.createStatement();
        stm.executeUpdate(req);
        String req2 = "update commentaire set score=score+1 where id=" + s.getCommentaire_id();
        Statement stm2 = connexion.createStatement();
        stm2.executeUpdate(req2);

    }

    public boolean DislikeCommplus(int id) throws SQLException {
        String req2 = "update commentaire set score=score-1 where id=" + id;
        Statement stm2 = connexion.createStatement();
        stm2.executeUpdate(req2);
        PreparedStatement pre = connexion.prepareStatement("delete from likes where commentaire='" + id + "' and createur=" + Integer.parseInt(System.getProperty("id")));
        if (pre.executeUpdate() == 1) {
            return true;
        }

        return false;
    }

    public boolean deleteSujet(int id) throws SQLException {
        PreparedStatement pre = connexion.prepareStatement("delete from likes where sujet=" + id);
        if (pre.executeUpdate() == 1) {
            return true;
        }

        return false;
    }
    
    public boolean deleteCommentaire(int id) throws SQLException {
        //System.out.println("aaaaaa");
        PreparedStatement pre = connexion.prepareStatement("delete from likes where commentaire=" + id);
        if (pre.executeUpdate() == 1) {
            return true;
        }

        return false;
    }

    public boolean rechercheLikeComm(int id) throws SQLException {
        ste = connexion.createStatement();
        Likes s = null;
        ResultSet rs = ste.executeQuery("select * from likes where commentaire='" + id + "' and createur=" + Integer.parseInt(System.getProperty("id")));
        while (rs.next()) {
            s = new Likes(Integer.parseInt(System.getProperty("id")), "commentaire", rs.getInt("commentaire"));
        }
        if (s == null) {
          //  System.out.println("mahouch mawjoud/true");
            return true;
        } else {
           // System.out.println("mawjoud/flase");
            return false;
        }

    }
}
