/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Reclamation;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import BD.Database;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Selim Chikh Zaouali
 */
public class ReclamationService {

    Connection connexion;

    public ReclamationService() {
        connexion = Database.getInstance().getConnexion();
    }

    public void ajouterReclamation(Reclamation r) throws SQLException {

        String req = "INSERT INTO reclamation (date, etat, note, parent,details) VALUES ('" + r.getDate() + "', '" + r.getEtat() + "', '" + r.getNote() + "', '" + r.getParent() + "', '" + r.getDetails() + "') ";
        Statement stm = connexion.createStatement();
        stm.executeUpdate(req);

    }
    
    public void changerEtat(int id) throws SQLException {
        String req = "UPDATE reclamation SET etat='traitee' where id=" + id;
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.executeUpdate(req);
    }

    public void deleteReclamation(int id) throws SQLException {
        String req = "DELETE FROM `reclamation` WHERE id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    public int getIDParent(String nom, String prenom) throws SQLException {
        String req = "SELECT * FROM user where nom like '%" + nom + "%' and prenom like '%" + prenom + "%'";
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);
        int id = 0;
        while (rs.next()) {
            id = rs.getInt("parent_id");
        }

        return (id);
    }

    public String getMatiere(String nom, String prenom) throws SQLException {
        String req2 = "SELECT * from user where nom like '%" + nom + "%' and prenom like '%" + prenom + "%'";
        PreparedStatement pstm = connexion.prepareStatement(req2);
        ResultSet rs = pstm.executeQuery(req2);
        int s1 = 0;
        while (rs.next()) {
            s1 = rs.getInt("id");
        }
        String req3 = "SELECT * FROM notes where eleve_id=" + s1;
        PreparedStatement pstm1 = connexion.prepareStatement(req3);
        ResultSet rs1 = pstm1.executeQuery(req3);
        int s2 = 0;
        int s3 = 0;
        while (rs1.next()) {
            s2 = rs1.getInt("matiere");
            s3 = rs1.getInt("valeur");
        }
        String req4 = "SELECT * from matiere WHERE id=" + s2;
        PreparedStatement pstm2 = connexion.prepareStatement(req4);
        ResultSet rs2 = pstm2.executeQuery(req4);
        String s4 = "";
        while (rs2.next()) {
            s4 = rs2.getString("nom");
        }
        String idkk = String.valueOf(s3) + " " + s4 + " " + nom + " " + prenom;
        return (idkk);
    }

    public ResultSet getEnfants(int id) throws SQLException {
        String req = "SELECT nom,prenom FROM user where parent_id=" + id;
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);
        return (rs);
    }

    public void modifierReclamation(int id) throws SQLException {
        String req = "UPDATE `reclamation` SET etat = ? where id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setString(1, "traitee");
        pstm.setInt(2, id);
        pstm.executeUpdate();
    }

    public void afficherReclamation() throws SQLException {
        String req = "SELECT * FROM reclamation";
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);
        System.out.print("\n");
        while (rs.next()) {

            Date date = rs.getDate("date");
            String etat = rs.getString("etat");
            int note = rs.getInt("note");
            int parent = rs.getInt("parent");

            System.out.format(" %s, %s, %s, %s\n", date, etat, note, parent);
        }
    }

    public List<Reclamation> getAllReclamations() throws SQLException {

        List<Reclamation> reclamations = new ArrayList<>();
        //String req = "select a.parent,u.nom,u.prenom,a.date,a.etat from reclamation a join user u on a.parent=u.id";
        String req = "select * from reclamation";
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);
        while (rst.next()) {
            LocalDateTime ldt = rst.getTimestamp("date").toLocalDateTime();

            Reclamation r = new Reclamation(rst.getInt("id"),ldt, rst.getString("etat"), rst.getInt("note"), rst.getInt("parent"), rst.getString("details"));
            reclamations.add(r);
        }
        return reclamations;
    }

    public ObservableList<Reclamation> getOwner(int u) throws SQLException {
        ObservableList<Reclamation> Reclamation = FXCollections.observableArrayList();

        String req = "select * from reclamation where parent=" + u;
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);

        while (rst.next()) {
            Reclamation r = new Reclamation(rst.getInt("id"),rst.getTimestamp("date").toLocalDateTime(), rst.getString("etat"), rst.getInt("note"), rst.getInt("parent"), rst.getString("details"));
            Reclamation.add(r);
        }

        return Reclamation;
    }

}
