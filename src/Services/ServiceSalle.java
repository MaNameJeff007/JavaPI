/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;


import BD.Database;
import Entities.Salle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author dell
 */
public class ServiceSalle {
    Connection c=Database.getInstance().getConnexion();
    
    
    public List< Salle> afficherSalles (){
        
        ArrayList< Salle> myList = new ArrayList();
        try {
            PreparedStatement pt =c.prepareStatement("select * from salle");
            
         
            ResultSet rs= pt.executeQuery();
            while(rs.next()){
                Salle a = new Salle();
                a.setId(rs.getString(1));
                a.setLibelle(rs.getString(2));
               
                myList.add(a);
                
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return myList;
    }
    
 public void ajouterSalle(Salle d)
    {
       
        try 
        {
            Statement st=c.createStatement();
            String req="insert into `salle`" + "(`libelle`)" + " values('"+ d.getLibelle()+"')";
            String rq2 = "select * from salle where libelle = '"+d.getLibelle()+"'";
            
             ResultSet myResultSet = st.executeQuery (rq2) ;
            if ( myResultSet.absolute (1 )) 
            {
                System.out.println("errooooor");
                 Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Ajout d'une salle !");
             alert.setHeaderText("Information");
             alert.setContentText("Cette salle existe déja !");
              alert.showAndWait();  
            }else if (d.getLibelle() != null ){
            st.executeUpdate(req);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Ajout d'une salle !");
             alert.setHeaderText("Information");
             alert.setContentText("Salle bien ajouté ");
             
             alert.showAndWait();
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(ServiceClasse.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Ajout d'une salle!");
             alert.setHeaderText("Information");
             alert.setContentText("Salle non ajoutée!!!!!!!! ");
              alert.showAndWait();
        }
  
    }   
    
 public void modifierSalle(String id,String libelle){
         String requete="UPDATE salle SET libelle=? WHERE id=?";
         try {
             String rq2 = "select * from salle where libelle = '"+libelle+"'";
            Statement st=c.createStatement();
             ResultSet myResultSet = st.executeQuery (rq2) ;
            if ( myResultSet.absolute (1 )) 
            {
                System.out.println("errooooor");
                 Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Ajout d'une salle !");
             alert.setHeaderText("Information");
             alert.setContentText("Cette salle existe déja !");
              alert.showAndWait();  
            }else {
                PreparedStatement pst = c.prepareStatement(requete);
           
            pst.setString(1,libelle);
             pst.setString(2,id);
           
            pst.executeUpdate(); 
            }
           
          
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
    }
 
 
  public void supprimerSalle(String id){
        String requete="DELETE FROM salle WHERE id =?";
       
        try {
            PreparedStatement pst = c.prepareStatement(requete);
            pst.setString(1,id);
            pst.executeUpdate();
            System.out.println("salle supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }  
    
}
