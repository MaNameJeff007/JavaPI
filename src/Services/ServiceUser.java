/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Classe;
import Entities.User;
import BD.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author dell
 */
public class ServiceUser {
      Connection c=Database.getInstance().getConnexion();
      
      public ArrayList< User> afficherEns1 (){
        
        ArrayList< User> myList = new ArrayList();
        
        try {
          
                PreparedStatement pt =c.prepareStatement("select * from user u " 
              + "inner join classe c on u.classeenseignant_id = c.id "); 
                
                String reqq="select * from `classe` where id = ? ";
                 PreparedStatement cn =c.prepareStatement(reqq); 
        
            ResultSet rs= pt.executeQuery();
            
               
            while(rs.next()){
                 cn.setInt(1,rs.getInt(4));
                 ResultSet cng=cn.executeQuery();
            while(cng.next()){
                User a = new User();
                
                a.setIdentifiant(rs.getInt(1));
                a.setNom(rs.getString(16));//16
                a.setPrenom(rs.getString(17));//17
               // a.setLibC(cng.getString(4));
                a.setClasseenseignant_id(cng.getString(4));
                a.setEmail(rs.getString(7));
                a.setDate_Embauche(rs.getString(18));
                
                System.out.println(cng.getString(4));
               
                myList.add(a);
             
                
            }
        }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return myList;
    } 
      
   public List<User> rechercherEns1ParClasse (String classe){
        
        String requete="select u.id,u.nom,u.prenom,c.libelle,u.email,u.date_embauche from user u " 
              + "inner join classe c on u.classeenseignant_id = c.id where c.libelle =?";
      
        ArrayList<User> myList = new ArrayList();
        try {
            
             PreparedStatement pst = c.prepareStatement(requete);
             pst.setString(1,classe);
              
            
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                User a = new User();
                
             a.setIdentifiant(rs.getInt(1));
                a.setNom(rs.getString(2));//16
                a.setPrenom(rs.getString(3));//17
               a.setClasseenseignant_id(rs.getString(4));
                a.setEmail(rs.getString(5));
                a.setDate_Embauche(rs.getString(6));
                System.out.println(rs.getString(4));
                myList.add(a);
        
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return myList;
    }
   
   public ArrayList< User> afficherEleves (){
        
        ArrayList< User> myList = new ArrayList();
         
        try {
          
                PreparedStatement pt =c.prepareStatement("select u.id,u.nom,u.prenom,c.libelle,j.nom,j.prenom,j.email,u.date_inscription,u.classeeleve_id from user u " 
                    + "inner join classe c on u.classeeleve_id = c.id inner join user j on u.parent_id = j.id ");    
                    
            ResultSet rs= pt.executeQuery();
            while(rs.next()){
                User a = new User();
                
                
                a.setIdentifiant(rs.getInt(1));
                a.setNom(rs.getString(2));//16
                a.setPrenom(rs.getString(3));//17
                //a.setLibC(rs.getString(4));
                
                a.setDate_Inscription(rs.getString(8));
               a.setClasseeleve_id(rs.getString(4));
               System.out.println(rs.getInt(9));
                 System.out.println(rs.getString(5));
                  System.out.println(rs.getString(6));
               
                myList.add(a);
             
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return myList;
    } 
   
   
   public ArrayList< User> afficherEns (){
        
        ArrayList< User> myList = new ArrayList();
         
        try {
          
                PreparedStatement pt =c.prepareStatement("select * from user u " 
              + "inner join classe c on u.classeenseignant_id = c.id ");    
          
            ResultSet rs= pt.executeQuery();
            while(rs.next()){
                User a = new User();
                
                a.setIdentifiant(rs.getInt(1));
                a.setNom(rs.getString(16));//16
                a.setPrenom(rs.getString(17));//17
               // a.setClasse();
               a.setClasseenseignant_id(rs.getString(4));
              
                myList.add(a);
              
                
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return myList;
    } 
   
  public List< User>  ComboxGetIdEns(String nom){
        ArrayList< User> myList = new ArrayList();
         String requete="select id from `user` where nom = ? and roles = 'a:1:{i:0;s:15:\"ROLE_ENSEIGNANT\";}' ";
         try {
            PreparedStatement pst = c.prepareStatement(requete);
           
            pst.setString(1,nom);
         
            
         ResultSet rs= pst.executeQuery(); 
         
         while(rs.next()){
               
              User a = new User();
                a.setIdentifiant(rs.getInt(1));
                
                myList.add(a);
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    return myList;
    } 
  
  public List< User>  sEns(String ide){
        ArrayList< User> myList = new ArrayList();
         String requete="select email from `user` where id = ?";
         try {
            PreparedStatement pst = c.prepareStatement(requete);
           
            pst.setString(1,ide);
         
            
         ResultSet rs= pst.executeQuery(); 
         
         while(rs.next()){
               
              User a = new User();
                a.setEmail(rs.getString(1));
                
                myList.add(a);
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    return myList;
    } 
   
   public List< User>  ComboxGetMailEns(int id){
        ArrayList< User> myList = new ArrayList();
         String requete="select email from `user` where id = ?";
         try {
            PreparedStatement pst = c.prepareStatement(requete);
           
            pst.setInt(1,id);
         
            
         ResultSet rs= pst.executeQuery(); 
         
         while(rs.next()){
               
              User a = new User();
                //a.setId(rs.getInt(1));
                a.setEmail(rs.getString(1));
                
                myList.add(a);
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    return myList;
    } 
   
   public List<User> rechercherEleveParClasse (String classe){
        
        String requete="select u.id,u.nom,u.prenom,c.libelle,j.nom,j.prenom,j.email,u.date_inscription,u.classeeleve_id  "
                + " FROM user u  inner join classe c on c.id= u.classeeleve_id"
                + " inner join user j on u.parent_id = j.id where c.libelle =?";
      
      
        ArrayList<User> myList = new ArrayList();
        try {
            
             PreparedStatement pst = c.prepareStatement(requete);
             pst.setString(1,classe);
              
            
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                User a = new User();
                
              a.setIdentifiant(rs.getInt(1));
                a.setNom(rs.getString(2));//16
                a.setPrenom(rs.getString(3));//17
               // a.setLibC(rs.getString(4));
               
                 a.setDate_Inscription(rs.getString(8));
               a.setClasseeleve_id(rs.getString(4));
                myList.add(a);
        
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return myList;
    }
   
}
