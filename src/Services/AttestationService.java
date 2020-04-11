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
import BD.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Selim Chikh Zaouali
 */
public class AttestationService {
     Connection connexion;
   
    public AttestationService() 
    {
       connexion=DbConnection.getInstance().getConnexion();
    }
    public void ajouterAttestation(Attestation a) throws SQLException 
    {
        String req="INSERT INTO `attestation` (`date`, `etat`, `parent`) VALUES ( '"+a.getDate() + "', ?, ?)";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setString(1, a.getEtat());
        pstm.setInt(2, a.getParent());
        pstm.executeUpdate();
    }
    
    public void deleteAttestation(int id) throws SQLException
    {
        String req = "DELETE FROM `attestation` WHERE id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }
    
   public void modifierAttestation(int id) throws SQLException
    {
        String req = "UPDATE `attestation` SET etat = ? where id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setString(1, "traitee");
        pstm.setInt(2, id);
        pstm.executeUpdate();   
    } 
    public void afficherAttestation() throws SQLException
    {
       String req = "SELECT * FROM attestation";
       PreparedStatement pstm = connexion.prepareStatement(req);
       ResultSet rs = pstm.executeQuery(req);
       System.out.print("\n");
       while (rs.next())
       {
          Date date = rs.getDate("date");
          String etat = rs.getString("etat");
          int parent = rs.getInt("parent");     
        
          System.out.format("%s,  %s, %s\n", date, etat, parent);
        } 
    }
    
    public LocalDate convertToLocalDate(Date dateToConvert) {
    return dateToConvert.toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDate();
    }
    
    public String getNom(int id) throws SQLException{
        String req = "select * from user where id="+id;
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);
        
        return(rst.getString("nom"));
    }
    public String getPrenom(int id) throws SQLException{
        String req = "select * from user where id="+id;
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);
        String prenom="";
        while(rst.next()) {
            prenom = rst.getString("prenom");
        }
        return(prenom);
    }
    public List<Attestation> getAllAttestations() throws SQLException {

        List<Attestation> attestations = new ArrayList<>();
        //String req = "select a.parent,u.nom,u.prenom,a.date,a.etat from attestation a join user u on a.parent=u.id";
        String req = "select * from attestation";
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);
       while (rst.next()) {
          LocalDateTime ldt= rst.getTimestamp("date").toLocalDateTime();
           
            Attestation a = new Attestation(ldt, rst.getString("etat"), rst.getInt("parent"));
            attestations.add(a);
        }
        return attestations;
    }
    
    public ObservableList<Attestation> getOwner(int u) throws SQLException {
        ObservableList<Attestation> Attestation = FXCollections.observableArrayList();

        String req = "select date,etat,parent from attestation where parent=" + u;
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);

        while (rst.next()) {
            Attestation p = new Attestation(rst.getTimestamp("date").toLocalDateTime(), rst.getString("etat"), rst.getInt("parent"));
            Attestation.add(p);
        }

        return Attestation;
    }
    
}
