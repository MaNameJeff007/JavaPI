/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Note;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import BD.DbConnection;

/**
 *
 * @author rami2
 */
public class NoteService  
{
    Connection connexion;
   
    public NoteService() 
    {
       connexion=DbConnection.getInstance().getConnexion();
    }
    
    public boolean ajouterNote(Note n) throws SQLException 
    {
        
        ResultSet rs=selectionnerNotesParEleveMatiereTrimestreType(n);
        if (!rs.isBeforeFirst() ) 
        {    
          String req="INSERT INTO `notes` (`type`, `id_trimestre`, `enseignant_id`, `eleve_id`, `matiere`, `valeur`) VALUES (?, ?, ?, ?, ?, ?)";
          PreparedStatement pstm = connexion.prepareStatement(req);
          pstm.setString(1, n.getType());
          pstm.setInt(2, n.getTrimestre());
          pstm.setString(3, n.getEnseignant_id());
          pstm.setString(4, n.getEleve_id());
          pstm.setString(5, n.getMatiere());
          pstm.setDouble(6, n.getValeur());
          pstm.executeUpdate();
          System.out.println("ajout reussie.");
          return true;
        }
        
        else
        {           
            return false;
        }
    }
    
    public void supprimerNote(Note N) throws SQLException
    {
        String req = "DELETE FROM `notes` WHERE id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setInt(1, N.getId());
        pstm.executeUpdate();
    }
    
    public void modifiervaleurNote(int id, Note n) throws SQLException
    {
        String req = "UPDATE `notes` SET valeur = ? where id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setDouble(1, n.getValeur());
        pstm.setInt(2, id);
        pstm.executeUpdate();   
    }
    
    public void modifierNote(Note n) throws SQLException
    {
      String req = "UPDATE `notes` SET type = ?, id_trimestre = ?, eleve_id = ?, matiere = ?, valeur = ? WHERE id=?";
      PreparedStatement pstm = connexion.prepareStatement(req);
      pstm.setString(1, n.getType());
      pstm.setInt(2, n.getTrimestre());
      pstm.setString(3, n.getEleve_id());
      pstm.setString(4, n.getMatiere());
      pstm.setDouble(5, n.getValeur());
      pstm.setInt(6, n.getId());
      pstm.executeUpdate();  
    }
    
     public void modifierNote2(Note n) throws SQLException
    {
      String req = "UPDATE `notes` SET type = ?, valeur = ? WHERE id=?";
      PreparedStatement pstm = connexion.prepareStatement(req);
      pstm.setString(1, n.getType());
      pstm.setDouble(2, n.getValeur());
      pstm.setInt(3, n.getId());
      pstm.executeUpdate();  
    }
    
    public ResultSet selectionnerNotesParEleveMatiereTrimestre(Note N) throws SQLException
    {
     String req  = "SELECT * FROM notes WHERE id_trimestre = '" + N.getTrimestre() + "' AND eleve_id = '" + N.getEleve_id() + "' AND matiere = '" + N.getMatiere() + "' "; 
     PreparedStatement pstm = connexion.prepareStatement(req);
     ResultSet rs = pstm.executeQuery(req);
     return rs;
    }
    
    public ResultSet selectionnerNotesParEleveMatiereTrimestreType(Note N) throws SQLException
    {
     String req  = "SELECT * FROM notes WHERE id_trimestre = '" + N.getTrimestre() + "' AND eleve_id = '" + N.getEleve_id() + "' AND matiere = '" + N.getMatiere() + "' AND type ='" + N.getType() + "' "; 
     PreparedStatement pstm = connexion.prepareStatement(req);
     ResultSet rs = pstm.executeQuery(req);
     return rs;
    }
    
    public ResultSet afficherMatieres() throws SQLException
    {
        String req = "SELECT id, nom FROM matiere";
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);
        return rs;
    }
    
    public void afficherNotes() throws SQLException
    {
        String req = "SELECT * FROM notes";
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);
        
        System.out.print("\n");
        while (rs.next())
        {
            int id = rs.getInt("id");
            String type = rs.getString("type");
            int idtrimestre = rs.getInt("id_trimestre");
            String enseignant_id = rs.getString("enseignant_id");
            String eleve_id = rs.getString("eleve_id");
            String matiere = rs.getString("matiere");
            double valeur = rs.getDouble("valeur");
        
            System.out.format("%s, %s, %s, %s, %s, %s, %s\n", id, type, idtrimestre, enseignant_id, eleve_id, matiere, valeur);
        }
        
    }
    
    public ResultSet fetchNotes() throws SQLException
    {
        String req = "SELECT * FROM notes ORDER BY eleve_id ASC";
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);  
        return rs;
    }
    
    public ResultSet fetchNotes2() throws SQLException
    {
        String req = "SELECT notes.id, notes.type, notes.id_trimestre, notes.enseignant_id, notes.eleve_id, notes.matiere, notes.valeur, user.prenom, user.nom, matiere.nom FROM `notes` INNER JOIN matiere ON notes.matiere=matiere.id INNER JOIN user ON notes.eleve_id=user.id ORDER BY notes.type ASC";
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);  
        return rs;
    }
    
    public ResultSet fetchNotesEleve(int id) throws SQLException
    {
      String req = "SELECT notes.id, notes.type, notes.id_trimestre, notes.enseignant_id, notes.eleve_id, notes.valeur, matiere.nom FROM notes INNER JOIN matiere on notes.matiere=matiere.id WHERE notes.eleve_id ='" + id + "' "; 
      PreparedStatement pstm = connexion.prepareStatement(req);
      ResultSet rs=pstm.executeQuery(req);
      return rs;   
    }
}
