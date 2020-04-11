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
import BD.DbConnection;
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
   
    public ReclamationService() 
    {
       connexion=DbConnection.getInstance().getConnexion();
    }
    public void ajouterReclamation(Reclamation r) throws SQLException 
    {
        
        String req = "INSERT INTO reclamation (date, etat, note, parent) VALUES ('"+r.getDate() + "', '" + r.getEtat() + "', '" + r.getNote() + "', '" + r.getParent()+ "') ";
        Statement stm = connexion.createStatement();
        stm.executeUpdate(req);
    
    }
    
   
    public void deleteReclamation(int id) throws SQLException
    {
        String req = "DELETE FROM `reclamation` WHERE id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }
    
   public void modifierReclamation(int id) throws SQLException
    {
        String req = "UPDATE `reclamation` SET etat = ? where id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setString(1, "traitee");
        pstm.setInt(2, id);
        pstm.executeUpdate();   
    } 
    public void afficherReclamation() throws SQLException
    {
       String req = "SELECT * FROM reclamation";
       PreparedStatement pstm = connexion.prepareStatement(req);
       ResultSet rs = pstm.executeQuery(req);
       System.out.print("\n");
       while (rs.next())
       {
         
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
          LocalDateTime ldt= rst.getTimestamp("date").toLocalDateTime();
           
            Reclamation r = new Reclamation(ldt, rst.getString("etat"), rst.getInt("note"), rst.getInt("parent"));
            reclamations.add(r);
        }
        return reclamations;
    }
        
        public ObservableList<Reclamation> getOwner(int u) throws SQLException {
        ObservableList<Reclamation> Reclamation = FXCollections.observableArrayList();

        String req = "select date,etat,note,parent from reclamation where parent=" + u;
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);

        while (rst.next()) {
            Reclamation r = new Reclamation(rst.getTimestamp("date").toLocalDateTime(), rst.getString("etat"),rst.getInt("note"), rst.getInt("parent"));
            Reclamation.add(r);
        }

        return Reclamation;
    }
    
}
