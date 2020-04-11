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
import BD.DbConnection;

/**
 *
 * @author rami2
 */
public class SanctionService 
{
   Connection connexion;
   
   public SanctionService() 
   {
      connexion=DbConnection.getInstance().getConnexion();
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
    
     public ResultSet afficherSanctions() throws SQLException
    {
       String req = "SELECT * FROM sanctions";
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
}
