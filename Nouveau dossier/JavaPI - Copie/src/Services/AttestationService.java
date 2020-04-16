/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Attestation;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import BD.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Selim Chikh Zaouali
 */
public class AttestationService {

    Connection connexion;

    public AttestationService() {
        connexion = Database.getInstance().getConnexion();
    }

    public void ajouterAttestation(Attestation a) throws SQLException {
        String req = "INSERT INTO `attestation` (`date`, `etat`, `parent`,`enfant`) VALUES ( '" + a.getDate() + "', ?, ?, ?)";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setString(1, a.getEtat());
        pstm.setInt(2, a.getParent());
        pstm.setString(3, a.getEnfant());
        pstm.executeUpdate();
    }

    public int getId(String nom, String prenom) throws SQLException {
        String req = "SELECT * FROM user where nom like '%" + nom + "%' and prenom like '%" + prenom + "%'";
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);
        int id = 0;
        while (rs.next()) {
            id = rs.getInt("id");
        }

        return (id);
    }
    
    public void changerEtat(int id) throws SQLException {
        String req = "UPDATE attestation SET etat='traitee' where id=" + id;
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.executeUpdate(req);
    }

    public void deleteAttestation(int id) throws SQLException {
        String req = "DELETE FROM `attestation` WHERE id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    public void modifierAttestation(int id) throws SQLException {
        String req = "UPDATE `attestation` SET etat = ? where id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setString(1, "traitee");
        pstm.setInt(2, id);
        pstm.executeUpdate();
    }

    public ResultSet afficherAttestation() throws SQLException {
        String req = "SELECT * FROM attestation";
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);
        return (rs);
        /* while (rs.next())
       {
          Date date = rs.getDate("date");
          String etat = rs.getString("etat");
          int parent = rs.getInt("parent");     
        
          String parent1 = String.valueOf(parent); 
          String idk=date+" - "+etat+" - "+parent1;
          
        } */
    }

    public ResultSet getEnfants(int id) throws SQLException {
        String req = "SELECT nom,prenom FROM user where parent_id=" + id;
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);
        return (rs);
    }

    public LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public String getNom(int id) throws SQLException {
        String req = "select * from user where id=" + id;
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);

        return (rst.getString("nom"));
    }

    public String getPrenom(int id) throws SQLException {
        String req = "select * from user where id=" + id;
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);
        String prenom = "";
        while (rst.next()) {
            prenom = rst.getString("prenom");
        }
        return (prenom);
    }

    public List<Attestation> getAllAttestations() throws SQLException {

        List<Attestation> attestations = new ArrayList<>();
        String req = "select * from attestation";
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);
        while (rst.next()) {
            LocalDateTime ldt = rst.getTimestamp("date").toLocalDateTime();

            Attestation a = new Attestation(rst.getInt("id"),ldt, rst.getString("etat"), rst.getInt("parent"), rst.getString("enfant"));
            attestations.add(a);
        }
        return attestations;
    }

    public ObservableList<Attestation> getOwner(int u) throws SQLException {
        ObservableList<Attestation> Attestation = FXCollections.observableArrayList();

        String req = "select * from attestation where parent=" + u;
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);

        while (rst.next()) {
            Attestation p = new Attestation(rst.getInt("id"), rst.getTimestamp("date").toLocalDateTime(), rst.getString("etat"), rst.getInt("parent"), rst.getString("enfant"));
            Attestation.add(p);
        }

        return Attestation;
    }

}
