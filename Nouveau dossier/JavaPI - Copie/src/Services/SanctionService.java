/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Sanction;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import BD.Database;

/**
 *
 * @author rami2
 */
public class SanctionService 
{
   Connection connexion;
   
   public SanctionService() 
   {
      connexion=Database.getInstance().getConnexion();
   }
   
   public void ajouterSanction(Sanction s) throws SQLException 
    {
        Date date = Date.valueOf(s.getDateSanction());
        String req="INSERT INTO `sanctions` (`enseignant_id`, `eleve_id`, `date_sanction`, `raisonsanction`, `etat`, `punition`) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setString(1, s.getEnseignant_id());
        pstm.setString(2, s.getEleve_id());
        pstm.setDate(3, date);
        pstm.setString(4, s.getRaisonSanction());
        pstm.setBoolean(5, s.isEtat());
        pstm.setString(6, s.getPunition());
        pstm.executeUpdate();
    }
    
    public void supprimerSanction(Sanction S) throws SQLException
    {
        String req = "DELETE FROM `sanctions` WHERE id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setInt(1, S.getId());
        pstm.executeUpdate();
    }
    
    public void modifierSanction(Sanction s) throws SQLException
    {
        String req = "UPDATE `sanctions` SET raisonsanction = ?, punition = ? WHERE id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setString(1, s.getRaisonSanction());
        pstm.setString(2, s.getPunition());
        pstm.setInt(3, s.getId());
        pstm.executeUpdate();   
    }
    
     public ResultSet afficherSanctions(String idprof) throws SQLException
    {
       String req = "SELECT sanctions.id, sanctions.enseignant_id, sanctions.eleve_id, sanctions.date_sanction, sanctions.raisonsanction, sanctions.etat, sanctions.punition, user.prenom, user.nom FROM `sanctions` INNER JOIN user ON user.id=sanctions.eleve_id WHERE enseignant_id='"+idprof+"' ORDER BY sanctions.eleve_id ASC";
       PreparedStatement pstm = connexion.prepareStatement(req);
       ResultSet rs = pstm.executeQuery(req);
       return rs;
    }
     
    public ResultSet getSanctionseleve(int ideleve) throws SQLException
    {
       String req = "SELECT * FROM sanctions WHERE eleve_id = '" + ideleve + "'";
       PreparedStatement pstm = connexion.prepareStatement(req);
       ResultSet rs = pstm.executeQuery(req); 
       return rs;
    }
    
    public ResultSet getcountSanctioneleve(int ideleve) throws SQLException
    {
      String req="SELECT punition, COUNT(`punition`) AS nombre_punition FROM sanctions WHERE eleve_id='"+ideleve+"' GROUP BY punition";
      PreparedStatement pstm = connexion.prepareStatement(req);
      ResultSet rs = pstm.executeQuery(req); 
      return rs;     
    }
}
