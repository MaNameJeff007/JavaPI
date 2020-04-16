/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import BD.Database;
import Entities.Signaler;
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
public class SignalerC {

    Connection connexion;
    Statement ste;

    public SignalerC() {
        connexion = Database.getInstance().getConnexion();
    }

    public void SignalerSujet(Signaler s) throws SQLException {

        String req = "INSERT INTO `signaler` (`id`, `sujet`, `commentaire`, `nombre`,`type`) VALUES ( NULL, '"
                + s.getSujet_id() + "', NULL, '" + s.getNombre() + "', '" + s.getType() + "') ";
        Statement stm = connexion.createStatement();
        stm.executeUpdate(req);
    }

    public boolean SignalerSujetplus(int id) throws SQLException {
        PreparedStatement pre = connexion.prepareStatement("update signaler set nombre=nombre+1 where sujet=" + id);
        if (pre.executeUpdate() == 1) {
            return true;
        }

        return false;
    }

    public boolean rechercheSignalerSujet(int id) throws SQLException {
        ste = connexion.createStatement();
        Signaler s = null;
        ResultSet rs = ste.executeQuery("select * from signaler where  sujet=" + id);
        while (rs.next()) {
            s = new Signaler(rs.getInt("sujet"), rs.getInt("nombre"), rs.getString("type"));
        }

        if (s == null) {
            System.out.println("mahouch mawjoud/true");
            return true;
        } else {
            System.out.println("mawjoud/flase");
            return false;
        }

    }

    public void SignalerComm(Signaler s) throws SQLException {

        String req = "INSERT INTO `signaler` (`id`, `sujet`, `commentaire`, `nombre`,`type`) VALUES ( NULL,NULL, '"
                + s.getCommentaire_id() + "',  '" + s.getNombre() + "', '" + s.getType() + "') ";
        Statement stm = connexion.createStatement();
        stm.executeUpdate(req);
    }

    public boolean SignalerCommplus(int id) throws SQLException {
        PreparedStatement pre = connexion.prepareStatement("update signaler set nombre=nombre+1 where commentaire=" + id);
        if (pre.executeUpdate() == 1) {
            return true;
        }

        return false;
    }

    public boolean rechercheSignalerComm(int id) throws SQLException {
        ste = connexion.createStatement();
        Signaler s = null;
        ResultSet rs = ste.executeQuery("select * from signaler where  commentaire=" + id);
        while (rs.next()) {
            s = new Signaler(rs.getInt("sujet"), rs.getInt("nombre"), rs.getString("type"));
        }

        if (s == null) {
            System.out.println("mahouch mawjoud/true");
            return true;
        } else {
            System.out.println("mawjoud/flase");
            return false;
        }

    }

    public ObservableList<Signaler> getAll() throws SQLException {
        ObservableList<Signaler> Signaler = FXCollections.observableArrayList();

        String req = "select * from signaler";
        Statement stm = connexion.createStatement();
        ResultSet result = stm.executeQuery(req);

        while (result.next()) {
            Signaler p = new Signaler(result.getInt("id"), result.getInt("sujet"), result.getInt("commentaire"), result.getInt("nombre"), result.getString("type"));
            Signaler.add(p);
        }

        return Signaler;
    }

    public boolean AcceptSujet(int id, int idd) throws SQLException {
        SujetC s = new SujetC();
        s.delete(idd);
        PreparedStatement pre = connexion.prepareStatement("delete from signaler where id=" + id);
        if (pre.executeUpdate() == 1) {
            
            return true;
        }
        return false;
    }

    public boolean AcceptComm(int id, int idd) throws SQLException {
        CommentaireC c = new CommentaireC();
        c.delete(idd);
        PreparedStatement pre = connexion.prepareStatement("delete from signaler where id=" + id);
        if (pre.executeUpdate() == 1) {
            
            return true;
        }
        return false;
    }

    public boolean deleteComm(int id) throws SQLException {
        PreparedStatement pre = connexion.prepareStatement("delete from signaler where commentaire=" + id);
        if (pre.executeUpdate() == 1) {
            return true;
        }

        return false;
    }
    
    public boolean deleteSujet(int id) throws SQLException {
        PreparedStatement pre = connexion.prepareStatement("delete from signaler where sujet=" + id);
        if (pre.executeUpdate() == 1) {
            return true;
        }

        return false;
    }
    
    public boolean delete(int id) throws SQLException {
        PreparedStatement pre = connexion.prepareStatement("delete from signaler where id=" + id);
        if (pre.executeUpdate() == 1) {
            return true;
        }

        return false;
    }
}
