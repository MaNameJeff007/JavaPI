/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Bulletin;
import Entities.Classe;
import Entities.Coeff;
import Entities.User;
import BD.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author dell
 */
public class ServiceBulletin {
 Connection c=Database.getInstance().getConnexion();
     
 
 
     
     public List afficherStat1 (){
        
        ArrayList myList = new ArrayList();
        try {
            PreparedStatement pt =c.prepareStatement("SELECT count(eleve) as nb,eleve,sum(moyenne) / 3 as moy ,"
                    + " CASE WHEN sum(moyenne) / 3 < 10 THEN 'redoublant'"
                    + " WHEN 10 < sum(moyenne) / 3 < 12 THEN 'faible' "
                    + "WHEN 12 < sum(moyenne) / 3 < 14 THEN 'moyen' "
                    + "ELSE 'Excellent' END AS appreciation FROM `bulletin`"
                    + " group by eleve having count(eleve) =3; ");
            ResultSet rs= pt.executeQuery();
            while(rs.next()){
               //Bulletin a = new Bulletin();
               // a.setId(rs.getInt(1));
               // a.setLibelle(rs.getString(4));
               // a.setCapacite(rs.getInt(3));
                //a.setNiveau(rs.getInt(2));
                
               // myList.add(a);
              myList.add(rs.getInt("nb"));
              myList.add(rs.getFloat("moy"));
               myList.add(rs.getString("appreciation"));
               System.out.println(rs.getInt("nb"));
                System.out.println(rs.getFloat("moy"));
                 System.out.println(rs.getString("appreciation"));
                
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return myList;
    } 
     
     
     public List StatMT2 (String classe,int tr){
        
        ArrayList myList = new ArrayList();
        try {
            PreparedStatement pst =c.prepareStatement("SELECT round ( (COUNT(moyenne) /(select count(id) from user where classeeleve_id=? ))*100 ,2) as pourcentage "
                    + "FROM `bulletin` "
                    + "where moyenne BETWEEN 10 and 14 and classe=? and trimestre=?");
       //   
               pst.setString(1,classe);
                pst.setString(2,classe);
                pst.setInt(3,tr);
                ResultSet rs= pst.executeQuery();
            while(rs.next()){
              
              myList.add(rs.getFloat("pourcentage"));
             System.out.println(rs.getFloat("pourcentage"));
                
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return myList;
    } 
     
     
     public List StatMT1 (String classe,int tr){
        
        ArrayList myList = new ArrayList();
        try {
            PreparedStatement pst =c.prepareStatement("SELECT round ( (COUNT(moyenne) /(select count(id) from user where classeeleve_id=? ))*100 ,2) as pourcentage "
                    + "FROM `bulletin` "
                    + "where moyenne < 10 and classe=? and trimestre=?");
       //   
               pst.setString(1,classe);
                pst.setString(2,classe);
                pst.setInt(3,tr);
                ResultSet rs= pst.executeQuery();
            while(rs.next()){
              
              myList.add(rs.getFloat("pourcentage"));
             System.out.println(rs.getFloat("pourcentage"));
                
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return myList;
    } 
     
     public List StatMT3 (String classe,int tr){
        
        ArrayList myList = new ArrayList();
        try {
            PreparedStatement pst =c.prepareStatement("SELECT round ( (COUNT(moyenne) /(select count(id) from user where classeeleve_id=? ))*100 ,2) as pourcentage "
                    + "FROM `bulletin` "
                    + "where moyenne > 14 and classe=? and trimestre=?");
          
               pst.setString(1,classe);
                pst.setString(2,classe);
                pst.setInt(3,tr);
                ResultSet rs= pst.executeQuery();
            while(rs.next()){
              
              myList.add(rs.getFloat("pourcentage"));
             System.out.println(rs.getFloat("pourcentage"));
                
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return myList;
    } 
     
     
      public List StatMGC1 (String classe){
        
        ArrayList myList = new ArrayList();
        try {
            PreparedStatement pst =c.prepareStatement("SELECT round ( (COUNT(moyG) /(select count(id) "
                    + "from moyennesgenerales where classe=? ))*100,2) as pourcentage "
                    + "FROM `moyennesgenerales` where moyG <10 and classe=?");
          
               pst.setString(1,classe);
                pst.setString(2,classe);
                ResultSet rs= pst.executeQuery();
            while(rs.next()){
              
              myList.add(rs.getFloat("pourcentage"));
             System.out.println(rs.getFloat("pourcentage"));
                
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return myList;
    } 
      
      
      
      
  public List StatMGC2 (String classe){
        
        ArrayList myList = new ArrayList();
        try {
            PreparedStatement pst =c.prepareStatement("SELECT round ((COUNT(moyG) /(select count(id) "
                    + "from moyennesgenerales where classe=? ))*100,2) as pourcentage "
                    + "FROM `moyennesgenerales` where  moyG BETWEEN 10 and 12 and classe=?");
       //   
               pst.setString(1,classe);
                pst.setString(2,classe);
                ResultSet rs= pst.executeQuery();
            while(rs.next()){
              
              myList.add(rs.getFloat("pourcentage"));
             System.out.println(rs.getFloat("pourcentage"));
                
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return myList;
    }     
      
      public List StatMGC3 (String classe){
        
        ArrayList myList = new ArrayList();
        try {
            PreparedStatement pst =c.prepareStatement("SELECT round( (COUNT(moyG) /(select count(id) "
                    + "from moyennesgenerales where classe=? ))*100,2) as pourcentage "
                    + "FROM `moyennesgenerales` where moyG BETWEEN 13 and 15 and classe=?");
       //   
               pst.setString(1,classe);
                pst.setString(2,classe);
                ResultSet rs= pst.executeQuery();
            while(rs.next()){
              
              myList.add(rs.getFloat("pourcentage"));
             System.out.println(rs.getFloat("pourcentage"));
                
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return myList;
    } 
      
      
      public List StatMGC4 (String classe){
        
        ArrayList myList = new ArrayList();
        try {
            PreparedStatement pst =c.prepareStatement("SELECT round ( (COUNT(moyG) /(select count(id) "
                    + "from moyennesgenerales where classe=? ))*100,2) as pourcentage "
                    + "FROM `moyennesgenerales` where  moyG > 16 and classe=?");
       //   
               pst.setString(1,classe);
                pst.setString(2,classe);
                ResultSet rs= pst.executeQuery();
            while(rs.next()){
              
              myList.add(rs.getFloat("pourcentage"));
             System.out.println(rs.getFloat("pourcentage"));
                
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return myList;
    } 
      
        public ArrayList< User> GetMaxMoyC (String classe){
        
         ArrayList< User> myList = new ArrayList();
        try {
            PreparedStatement pt =c.prepareStatement("SELECT u.id,u.nom,u.prenom,round(m.moyG,2) FROM `moyennesgenerales` m "
                    + "inner join user u on u.id=m.eleve "
                    + "inner join classe c on c.id= m.classe "
                    + "WHERE c.libelle=? "
                    + " ORDER BY moyG desc limit 3");
             pt.setString(1,classe);
            ResultSet rs= pt.executeQuery();
            while(rs.next()){
               User a = new User();
                
                a.setIdentifiant(rs.getInt(1));
                a.setNom(rs.getString(2));//16
                a.setPrenom(rs.getString(3));//17
                a.setMoyG(rs.getFloat(4));
                 System.out.println(rs.getInt(1));
                 System.out.println(rs.getString(2));
                 System.out.println(rs.getFloat(4));
               // a.setLibC(rs.getString(4));
                
               // a.setDateInscription(rs.getString(5));
               // a.setClasseeleve_id(rs.getInt(6));
              
               
                myList.add(a);
             // myList.add(rs.getFloat("max"));
             // myList.add(rs.getString("classe"));
               
                
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return myList;
    } 
        
        
        
    public ArrayList< User> afficherElevesP (){
        
        ArrayList< User> myList = new ArrayList();
          ArrayList classesId = new ArrayList();
          ArrayList moy = new ArrayList();
        try {
          
               /*tatement st=c.createStatement();
                String moym="select max(moyG) as max ,classe from moyennesgenerales"
                        + " HAVING max(moyG) and classe = ?";*/
                PreparedStatement pt1 =c.prepareStatement("select id from classe ");
            PreparedStatement pt2 =c.prepareStatement("select max(moyG) as max ,classe from moyennesgenerales "
                    + "HAVING max(moyG) and classe = ? ");
             
             PreparedStatement pt =c.prepareStatement("SELECT * FROM `user` "
                     + "inner join moyennesgenerales m on user.id =m.eleve"
                     + " WHERE m.moyG=? ");
             
            ResultSet rs1= pt1.executeQuery();
             while(rs1.next()){
                classesId.add(rs1.getInt(1));
              
             }
             
             for (Iterator it = classesId.iterator(); it.hasNext();) {
            int e1 = (int) it.next();
            System.out.println(e1);
            pt2.setInt(1,e1);
            
            ResultSet rs2= pt2.executeQuery();
            
            while(rs2.next()){
                moy.add(rs1.getFloat(1));
              for (Iterator it1 = moy.iterator(); it.hasNext();) {
            Float m = (Float) it1.next();
            System.out.println('m'+m);
            pt.setFloat(1,m);
            ResultSet rs= pt.executeQuery();
            while(rs.next()){
                User a = new User();
                
                
               a.setIdentifiant(rs.getInt(1));
                a.setNom(rs.getString(2));//16
                a.setPrenom(rs.getString(3));//17
               // a.setLibC(rs.getString(4));
                
                a.setDate_Inscription(rs.getString(5));
                a.setClasseeleve_id(rs.getString(4));
              
               
                myList.add(a);
             
            }
              }
             }
            
             
            }
            
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return myList;
    } 
    
    
    
   public void ajouterMG()
    {
       
        try 
        {
            Statement st=c.createStatement();
             String rq2 = "DELETE FROM `moyennesgenerales`";
             //select eleve from `bulletin` group by eleve having count(eleve) =2
            String reqInsert="insert into moyennesgenerales(eleve,moyG,classe) select eleve , sum(moyenne) / 3 , user.classeeleve_id "
                    + "from `bulletin` inner join user on user.id=bulletin.eleve "
                    + "group by eleve "
                    + "having count(eleve) =3";
            
        /* String reqU ="UPDATE moyennesgenerales set moyG=(select sum(moyenne) / 3 from `bulletin` where eleve = ? ) "
                 + "WHERE eleve = ?"; */
        //ResultSet m = st.executeUpdate(rq2);
          // ResultSet myResultSet = 
                 st.executeUpdate(rq2);
                 st.executeUpdate(reqInsert);
       /*   ArrayList res = new ArrayList();
            if ( myResultSet.next()) 
            {
               while(myResultSet.next()){
                   
               res.add(myResultSet.next());
               res.add(myResultSet.getInt("eleve"));
               System.out.println(myResultSet.getInt("eleve"));
            }
               for (Iterator it = res.iterator(); it.hasNext();) {
               int e = (int) it.next();
               PreparedStatement pst = c.prepareStatement(reqU); 
               pst.setInt(1,e);
                pst.setInt(2,e);
                pst.executeUpdate();
                 System.out.println("up");
               } 
                
            }else{
            st.executeUpdate(reqInsert);
            
          System.out.println("ajout mg ");
            }
            */
        } catch (SQLException ex)
        {
             System.out.println(ex.getMessage());
            
        }
  
    }      
      
      
      
      
      
      
      
      
      
      
}
