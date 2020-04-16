/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import BD.Database;
import Entities.Sujet;
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
public class SujetC {

    Connection connexion;
    Statement ste;

    public SujetC() {
        connexion = Database.getInstance().getConnexion();
    }

    public int Createur(int id) throws SQLException {
        int iduser = 0;
        ste = connexion.createStatement();
        ResultSet rs = ste.executeQuery("select * from sujet where id=" + id);
        // System.out.println(id);
        while (rs.next()) {
            iduser = rs.getInt("createur");
            System.out.println(iduser);
        }
        return iduser;
    }

    public void ajouterSujet(Sujet s) throws SQLException {
        String req = "INSERT INTO `sujet` (`id`, `createur`, `titre`, `description`, `date`, `score`, `vues`) VALUES ( NULL, '"
                + s.getCreateur_id() + "', '" + s.getTitre() + "', '" + s.getDescription() + "', '" + s.getDate() + "', '"
                + s.getScore() + "', '" + s.getVues() + "') ";

        Statement stm = connexion.createStatement();
        stm.executeUpdate(req);
    }

    public ObservableList<Sujet> recherche(String s) throws SQLException {
        ObservableList<Sujet> Sujet = FXCollections.observableArrayList();

        String req = "select * from sujet where titre like '%" + s + "%' or description like '%" + s + "%' order by vues,score desc";
        Statement stm = connexion.createStatement();
        ResultSet result = stm.executeQuery(req);

        while (result.next()) {

            Sujet p = new Sujet(result.getInt("id"), result.getInt("createur"), result.getString("titre"), result.getString("description"), result.getTimestamp("date").toLocalDateTime(), result.getInt("score"), result.getInt("vues"));
            Sujet.add(p);
        }

        return Sujet;
    }

    public boolean delete(int id) throws SQLException {
        LikesC l = new LikesC();
        l.deleteSujet(id);
        CommentaireC c = new CommentaireC();
        c.deleteSujet(id);
        SignalerC sc = new SignalerC();
        sc.deleteSujet(id);
        PreparedStatement pre = connexion.prepareStatement("delete from sujet where id=" + id);
        if (pre.executeUpdate() == 1) {
            return true;
        }
        return false;
    }

    public boolean update(String titre, String Description, int id) throws SQLException {
        PreparedStatement pre = connexion.prepareStatement("update sujet set titre='" + titre + "' ,description='" + Description + "' where id=" + id);
        if (pre.executeUpdate() == 1) {
            return true;
        }

        return false;
    }

    public boolean updateVues(int id) throws SQLException {
        PreparedStatement pre = connexion.prepareStatement("update sujet set vues=vues+1 where id=" + id);
        if (pre.executeUpdate() == 1) {
            return true;
        }

        return false;
    }

    public ObservableList<Sujet> getAll() throws SQLException {
        ObservableList<Sujet> Sujet = FXCollections.observableArrayList();

        String req = "select * from sujet order by vues desc";
        Statement stm = connexion.createStatement();
        ResultSet result = stm.executeQuery(req);

        while (result.next()) {

            Sujet p = new Sujet(result.getInt("id"), result.getInt("createur"), result.getString("titre"), result.getString("description"), result.getTimestamp("date").toLocalDateTime(), result.getInt("score"), result.getInt("vues"));
            Sujet.add(p);
        }

        return Sujet;
    }

    public ObservableList<Sujet> getOwner(int u) throws SQLException {
        ObservableList<Sujet> Sujet = FXCollections.observableArrayList();

        String req = "select * from sujet where createur=" + u;
        Statement stm = connexion.createStatement();
        ResultSet result = stm.executeQuery(req);

        while (result.next()) {
            Sujet p = new Sujet(result.getInt("id"), result.getInt("createur"), result.getString("titre"), result.getString("description"), result.getTimestamp("date").toLocalDateTime(), result.getInt("score"), result.getInt("vues"));
            Sujet.add(p);
        }

        return Sujet;
    }

    public String gettitre(int u) throws SQLException {
        String Sujet = "";

        String req = "select * from sujet where id=" + u;
        Statement stm = connexion.createStatement();
        ResultSet result = stm.executeQuery(req);
        while (result.next()) {
            Sujet = result.getString("titre");
        }
        return Sujet;
    }

    public ArrayList<String> getList() throws SQLException {
        ObservableList<Sujet> Sujet = FXCollections.observableArrayList();
        ArrayList<String> sujet = new ArrayList<String>();
        String req = "select * from sujet";
        Statement stm = connexion.createStatement();
        ResultSet result = stm.executeQuery(req);

        while (result.next()) {
            sujet.add(result.getString("titre"));
        }
        return sujet;
    }
}
