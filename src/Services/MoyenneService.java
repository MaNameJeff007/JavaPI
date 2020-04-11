/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Moyenne;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import BD.DbConnection;
import java.lang.Math; 
import Entities.Note;

/**
 *
 * @author rami2
 */
public class MoyenneService 
{
  Connection connexion;
   
  public  MoyenneService() 
  {
     connexion=DbConnection.getInstance().getConnexion();
  }
  
  public boolean validermoyenne(Note N) throws SQLException 
  {
      String req  = "SELECT * FROM moyennes WHERE trimestre = '" + N.getTrimestre() + "' AND eleve_id = '" + N.getEleve_id() + "' AND matiere = '" + N.getMatiere() + "' ";
      PreparedStatement pstm = connexion.prepareStatement(req);
      ResultSet rs = pstm.executeQuery(req);
      
      if(!rs.isBeforeFirst())
      return true;
      
      else
      return false;
  }
  
  public double calculerMoyenne(ResultSet rs) throws SQLException
  {
      double moyenne=0;
      while (rs.next())
      {
        String type = rs.getString("type");
        int idtrimestre = rs.getInt("id_trimestre");
        String eleve_id = rs.getString("eleve_id");
        String matiere = rs.getString("matiere");
        double valeur = rs.getDouble("valeur"); 
        
        if(type.equals("CC"))
        { 
            moyenne=moyenne+(valeur * 0.2);
        }
        
        else if(type.equals("Devoir de controle"))
        { 
            moyenne=moyenne+(valeur * 0.3);
        }
        
        else if(type.equals("Devoir de synthese"))
        { 
            moyenne=moyenne+(valeur * 0.5);
        }     
      }
      moyenne = Math.round(moyenne * 10.0) / 10.0;
      return moyenne;
  }
  
  public void insererMoyenne(Note N, ResultSet rs) throws SQLException
  {
      String req=null;
      if(validermoyenne(N)==true)
      {
        req="INSERT INTO `moyennes` (`trimestre`, `moyenne`, `eleve_id`, `matiere`) VALUES (?, ?, ?, ?)";
        double moyenne=calculerMoyenne(rs);
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setInt(1, N.getTrimestre());
        pstm.setString(3, N.getEleve_id());
        pstm.setString(4, N.getMatiere());
        pstm.setDouble(2, moyenne);
        pstm.executeUpdate();
      }
      
      else
      {
        req = "UPDATE `moyennes` SET moyenne = ? where trimestre=? AND eleve_id=? AND matiere=?";
        double moyenne=calculerMoyenne(rs);
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setInt(2, N.getTrimestre());
        pstm.setString(3, N.getEleve_id());
        pstm.setString(4, N.getMatiere());
        pstm.setDouble(1, moyenne);
        pstm.executeUpdate();
      }     
  }
  
  public void recalculerMoyenne2(Moyenne M) throws SQLException
  {
        String req1  = "SELECT * FROM moyennes WHERE trimestre = '" + M.getTrimestre() + "' AND eleve_id = '" + M.getEleve_id() + "' AND matiere = '" + M.getMatiere() + "' ";
        PreparedStatement pstm = connexion.prepareStatement(req1);
        ResultSet moyenne = pstm.executeQuery(req1);
        
        while(moyenne.next())
        {
          int id = moyenne.getInt("id");
          M.setId(id);
          System.out.print(M.getId());
          System.out.print("\n");
        }
        
        String req2  = "SELECT * FROM notes WHERE id_trimestre = '" + M.getTrimestre() + "' AND eleve_id = '" + M.getEleve_id() + "' AND matiere = '" + M.getMatiere() + "' "; 
        pstm = connexion.prepareStatement(req2);
        ResultSet notes = pstm.executeQuery(req2);
        double valeur=calculerMoyenne(notes);
        valeur = Math.round(valeur * 10.0) / 10.0;
        String req3 = "UPDATE `moyennes` SET moyenne = ? where id = ?";
        
        pstm = connexion.prepareStatement(req3);
        pstm.setInt(2, M.getId());
        pstm.setDouble(1, valeur);
        pstm.executeUpdate();   
  }
  
  public void recalculerMoyenne(Moyenne M, ResultSet rs) throws SQLException
  { 
        String req1  = "SELECT * FROM moyennes WHERE trimestre = '" + M.getTrimestre() + "' AND eleve_id = '" + M.getEleve_id() + "' AND matiere = '" + M.getMatiere() + "' ";
        PreparedStatement pstm = connexion.prepareStatement(req1);
        ResultSet moyenne = pstm.executeQuery(req1);
        
        while(moyenne.next())
        {
          int id = moyenne.getInt("id");
          M.setId(id);
        }
        
        double valeur=calculerMoyenne(rs);
        String req2 = "UPDATE `moyennes` SET moyenne = ? where id = ?";
        
        pstm = connexion.prepareStatement(req2);
        pstm.setInt(2, M.getId());
        pstm.setDouble(1, valeur);
        pstm.executeUpdate();   
  }
  
  public void afficherMoyennes() throws SQLException
  {
      String req = "SELECT * FROM moyennes";
      PreparedStatement pstm = connexion.prepareStatement(req);
      ResultSet rs = pstm.executeQuery(req);
      System.out.print("\n");
      while (rs.next())
      {
          int id = rs.getInt("id");
          int idtrimestre = rs.getInt("trimestre");
          String eleve_id = rs.getString("eleve_id");
          String matiere = rs.getString("matiere");
          double valeur = rs.getDouble("moyenne");
        
          System.out.format("%s, %s, %s, %s, %s\n", id, idtrimestre, valeur, eleve_id, matiere);
      }    
  }
  
  public void effacerMoyenne(Moyenne M) throws SQLException
  {
      System.out.print(M.getId());
      String req = "DELETE FROM `moyennes` WHERE  id= '" + M.getId() + "' ";
      PreparedStatement pstm = connexion.prepareStatement(req);
      pstm.execute(req);
  }
  
  public ResultSet fetchMoyennes() throws SQLException
  {
      String req = "SELECT * FROM moyennes ORDER BY eleve_id ASC";
      PreparedStatement pstm = connexion.prepareStatement(req);
      ResultSet rs = pstm.executeQuery(req);
      return rs;
  }
  
  public ResultSet fetchMoyennesEleve(int ideleve) throws SQLException
  {
      String req = "SELECT moyennes.id, moyennes.trimestre, moyennes.moyenne, moyennes.eleve_id, matiere.nom FROM moyennes INNER JOIN matiere ON moyennes.matiere=matiere.id WHERE moyennes.eleve_id='" + ideleve + "' ORDER BY matiere.nom ASC";
      PreparedStatement pstm = connexion.prepareStatement(req);
      ResultSet rs = pstm.executeQuery(req);
      return rs;
  }
  
  
}
