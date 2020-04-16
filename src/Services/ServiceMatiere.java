/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;


import BD.Database;
import Entities.Classe;
import Entities.Matiere;




import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author dell
 */
public class ServiceMatiere {
 Connection c=Database.getInstance().getConnexion();
    
    
    public List<Matiere> afficherMatieres (){
        
        ArrayList< Matiere> myList = new ArrayList();
        try {
            PreparedStatement pt =c.prepareStatement("select * from matiere");
            
          //  PreparedStatement pt =c.prepareStatement("select id, libelle "
                  //  +" from seance inner join classe USING(id)");
            ResultSet rs= pt.executeQuery();
            while(rs.next()){
                Matiere a = new Matiere();
                a.setId(rs.getString(1));
                a.setNom(rs.getString(2));
               a.setNbH(rs.getInt(3));
                myList.add(a);
                
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return myList;
    }  
    
  public void ajouterMatiere(Matiere d)
    {
       
        try 
        {
            Statement st=c.createStatement();
            String rq2 = "select * from matiere where nom = '"+d.getNom()+"'";
            String req="insert into `matiere`" + "(`nom`,`nbH`)" + " values('"+ d.getNom()+"','"+d.getNbH()+"')";
             ResultSet myResultSet = st.executeQuery (rq2) ;
             
              if ( myResultSet.absolute (1 )) 
            {
                System.out.println("errooooor");
                 Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Ajout d'une matiere!");
             alert.setHeaderText("Warning");
             alert.setContentText("Cette matière existe déja !");
              alert.showAndWait();  
            }else{
            st.executeUpdate(req);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Ajout d'une matiere !");
             alert.setHeaderText("Information");
             alert.setContentText("Matiere bien ajouté ");
             
             alert.showAndWait();
              }
        } catch (SQLException ex)
        {
            Logger.getLogger(ServiceClasse.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Ajout d'une matiere!");
             alert.setHeaderText("Information");
             alert.setContentText("Matiere non ajoutée!!!!!!!! ");
              alert.showAndWait();
        }
  
    }  
  
 
  public void modifierMatiere(String id,String nom,int nbh){
         String requete="UPDATE matiere SET nom= ? , nbH = ? WHERE id=?";
         try {
             
            PreparedStatement pst = c.prepareStatement(requete);
           
            pst.setString(1,nom);
              pst.setInt(2,nbh);
             pst.setString(3,id);
           
            pst.executeUpdate();
          
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
    }
 
 
  public void supprimerMatiere(String id){
        String requete="DELETE FROM matiere WHERE id =?";
       
        try {
            PreparedStatement pst = c.prepareStatement(requete);
            pst.setString(1,id);
            pst.executeUpdate();
            System.out.println("matiere supprimé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }  
    
 
 public List<Matiere> rechercherParNom (String libelle){
        
        String requete="select * FROM matiere where (nom LIKE ? )";
      
        String ch="%"+libelle+"%";
        ArrayList<Matiere> myList = new ArrayList();
        try {
            
             PreparedStatement pst = c.prepareStatement(requete);
             pst.setString(1,ch);
              
            
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
               Matiere a = new Matiere();
                a.setId(rs.getString(1));

                a.setNom(rs.getString(2));
                a.setNbH(rs.getInt(3));
               
                myList.add(a);
                
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return myList;
    }
  
  
  
    
}
