/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;
import BD.Database;
import Entities.Classe;
import java.awt.AWTException;
import java.awt.TrayIcon;
import java.net.MalformedURLException;
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
import javafx.event.Event;
/**
 *
 * @author dell
 */
public class ServiceClasse {
     Connection c=Database.getInstance().getConnexion();
     
     
     public List< Classe> afficherClasse (){
        
        ArrayList< Classe> myList = new ArrayList();
        try {
            PreparedStatement pt =c.prepareStatement("select * from classe ");
            ResultSet rs= pt.executeQuery();
            while(rs.next()){
                Classe a = new Classe();
                a.setId(rs.getString(1));
                a.setLibelle(rs.getString(4));
                a.setCapacite(rs.getInt(3));
                a.setNiveau(rs.getInt(2));
                
                myList.add(a);
                
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return myList;
    }
     
public void ajouterClasse(Classe d)
    {
       
        try 
        {
            Statement st=c.createStatement();
            String req="insert into `classe`" + "(`libelle`,`capacite`,`niveau`)" + " values('"+ d.getLibelle()+"','"+d.getCapacite()+"','"+d.getNiveau()+"')";
            String rq2 = "select * from classe where libelle = '"+d.getLibelle()+"'";
            
             ResultSet myResultSet = st.executeQuery (rq2) ;
            if ( myResultSet.absolute (1 )) 
            {
                System.out.println("errooooor");
                 Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Ajout d'une classe !");
             alert.setHeaderText("Information");
             alert.setContentText("Cette classe existe déja !");
              alert.showAndWait();  
            }else{
            st.executeUpdate(req);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Ajout une classe !");
             alert.setHeaderText("Information");
             alert.setContentText("classe bien ajouté ");
             
             alert.showAndWait();
             }
        } catch (SQLException ex)
        {
            Logger.getLogger(ServiceClasse.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Ajout d'une classe!");
             alert.setHeaderText("Information");
             alert.setContentText("classe non ajoutée!!!!!!!! ");
              alert.showAndWait();
        }
  
    }   


public void modifierClasse(String id,String libelle,int capacite,int niveau){
         String requete="UPDATE classe SET libelle=? , capacite=? , niveau=?  WHERE id=?";
         try {
            PreparedStatement pst = c.prepareStatement(requete);
           
            pst.setString(1,libelle);
            pst.setInt(2, capacite );
            pst.setInt(3, niveau );
            pst.setString(4, id);
            pst.executeUpdate();
          
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
    }
  
public void supprimerClasse( String id){
        String requete="DELETE FROM classe WHERE id =?";
       
        try {
            PreparedStatement pst = c.prepareStatement(requete);
            pst.setString(1,id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

public List<Classe> rechercherParLibelle (String libelle){
        
        String requete="select * FROM classe where (libelle LIKE ? )";
      
        String ch="%"+libelle+"%";
        ArrayList<Classe> myList = new ArrayList();
        try {
            
             PreparedStatement pst = c.prepareStatement(requete);
             pst.setString(1,ch);
              
            
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
               Classe a = new Classe();
                a.setId(rs.getString(1));

                a.setNiveau(rs.getInt(2));
                a.setCapacite(rs.getInt(3));
                a.setLibelle(rs.getString(4));
              
                myList.add(a);
              
                
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return myList;
    }




}
