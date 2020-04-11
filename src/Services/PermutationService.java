/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Permutation;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import BD.DbConnection;

/**
 *
 * @author Selim Chikh Zaouali
 */
public class PermutationService {
    Connection connexion;
   
    public PermutationService() 
    {
       connexion=DbConnection.getInstance().getConnexion();
    }
    public void ajouterPermutation(Permutation p) throws SQLException 
    {
        
        
        String req="INSERT INTO `permutation` (`classe_s`, `raison`,`etat`, `date`, `eleve_id`, `parent`) VALUES (?, ?, ?, '"+p.getDate() + "', ?, ?)";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setString(1, p.getClasse_s());
        pstm.setString(2, p.getRaison());
        pstm.setString(3, p.getEtat());
        pstm.setInt(5, p.getEleve_id());
        pstm.setInt(6, p.getParent());
        pstm.executeUpdate();
    }
    
    public void supprimerPermutation(int id) throws SQLException
    {
        String req = "DELETE FROM `permutation` WHERE id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }
    
    public void traiterPermutation(int eleve_id, String classe_s, int id) throws SQLException
    {
        //MCH KEMLA
       
        String req1 = "UPDATE `User` SET classeeleve_id = ? where identifiant = ?";
        PreparedStatement pstm1 = connexion.prepareStatement(req1);
        pstm1.setString(1, classe_s);
        pstm1.setInt(2, eleve_id);
        pstm1.executeUpdate();   
         // ne9sa test 3al capacité max mte3 el classe
        String req2 = "UPDATE `permutation` SET etat = ? where id = ?";
        PreparedStatement pstm2 = connexion.prepareStatement(req2);
        pstm2.setString(1, "traitee");
        pstm2.setInt(2, id);
        pstm2.executeUpdate();   
    }
    
        public void afficherPermuation() throws SQLException
    {
       String req = "SELECT * FROM permutation";
       PreparedStatement pstm = connexion.prepareStatement(req);
       ResultSet rs = pstm.executeQuery(req);
       System.out.print("\n");
       while (rs.next())
       {
          String classe_s = rs.getString("classe_s");
          String raison = rs.getString("raison");
          Date date = rs.getDate("date");
          String etat = rs.getString("etat");
          int eleve_id = rs.getInt("eleve_id");
          int parent = rs.getInt("parent");     
        
          System.out.format("%s, %s, %s, %s, %s, %s\n", classe_s, raison, date, etat, eleve_id, parent);
        } 
    }
}
