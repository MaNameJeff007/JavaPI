/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Absence;
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
public class AbsenceService 
{
    Connection connexion;
   
    public AbsenceService() 
    {
       connexion=DbConnection.getInstance().getConnexion();
    }
    
    public boolean verifierAbsence(Absence a) throws SQLException
    {
        Date abs = Date.valueOf(a.getDateAbsence());
        String req  = "SELECT * FROM absences WHERE dateabs = '" + abs + "' AND heuredebut = '" + a.getHeureDebut() + "' AND heurefin = '" + a.getHeureFin() + "' AND eleve_id ='" + a.getEleve_id() + "' "; 
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);
        
        if(!rs.isBeforeFirst())
            return true;
        
        else
            return false;   
    }
    
    public void ajouterAbsence(Absence a) throws SQLException 
    { 
            Date abs = Date.valueOf(a.getDateAbsence());
            String req="INSERT INTO `absences` (`dateabs`, `justification`, `heuredebut`, `heurefin`, `etat`, `enseignant_id`, `eleve_id`) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstm = connexion.prepareStatement(req);
            pstm.setDate(1, abs);
            pstm.setString(2, a.getJustification());
            pstm.setInt(3, a.getHeureDebut());
            pstm.setInt(4, a.getHeureFin());
            pstm.setBoolean(5, a.isEtat());
            pstm.setString(6, a.getEnseignant_id());
            pstm.setString(7, a. getEleve_id());
            pstm.executeUpdate();
            System.out.println("ajout reussie.");        
    }
    
    public void supprimerAbsence(Absence A) throws SQLException
    {
        String req = "DELETE FROM `absences` WHERE id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setInt(1, A.getId());
        pstm.executeUpdate();
        }

    public void modifierAbsence(Absence a) throws SQLException
    {
        String req = "UPDATE `absences` SET heuredebut=?, heurefin=?, justification = ?, etat=? where id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setInt(1, a.getHeureDebut());
        pstm.setInt(2, a.getHeureFin());
        pstm.setString(3, a.getJustification());
        pstm.setBoolean(4, a.isEtat());
        pstm.setInt(5, a.getId());
        pstm.executeUpdate();   
    }
    
    public void afficherAbsences() throws SQLException
    {
       String req = "SELECT * FROM absences";
       PreparedStatement pstm = connexion.prepareStatement(req);
       ResultSet rs = pstm.executeQuery(req);
       System.out.print("\n");
       while (rs.next())
       {
          int id = rs.getInt("id");
          Date date = rs.getDate("dateabs");
          String justification = rs.getString("justification");
          int heuredebut = rs.getInt("heuredebut");
          int heurefin = rs.getInt("heurefin");
          boolean etat = rs.getBoolean("etat");
          String enseignant_id = rs.getString("enseignant_id");
          String eleve_id = rs.getString("eleve_id");       
        
          System.out.format("%s, %s, %s, %s, %s, %s, %s, %s\n", id, date, justification, heuredebut, heurefin, etat, enseignant_id, eleve_id);
        } 
    }
    
    public ResultSet fetchAbsences() throws SQLException
    {
       String req = "SELECT * FROM absences ORDER BY eleve_id ASC";
       PreparedStatement pstm = connexion.prepareStatement(req);
       ResultSet rs = pstm.executeQuery(req); 
       return rs;
    }
    
    public ResultSet getAbsenceseleve(int ideleve) throws SQLException
    {
       String req = "SELECT * FROM absences WHERE eleve_id = '" + ideleve + "'";
       PreparedStatement pstm = connexion.prepareStatement(req);
       ResultSet rs = pstm.executeQuery(req); 
       return rs;
    }
    
}
