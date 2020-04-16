/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Classe;
import Entities.Coeff;
import Entities.Matiere;
import Entities.Salle;
import Entities.Seance;
import Entities.User;
import Utils.ConnexionBD;
import Utils.JavaMail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author dell
 */
public class ServiceSeance {

    Connection c = ConnexionBD.getinstance().getcnx();

    public void modifierSeance(Seance r, String emailEns) {
        String requete = "UPDATE seance SET enseignant_id=? , classe_id=? , salle_id=? , matiere_id=? , jour =? , hdeb=?, hfin =? "
                + " WHERE id=?";

        String CheckSalleSeance = "SELECT * \n"
                + "  FROM seance p \n"
                + " WHERE p.salle_id = '" + r.getSalle() + "' and p.hdeb = '" + r.getHdeb() + "'  and p.jour= '" + r.getJour() + "'";

        String CheckDispoEns = "SELECT * FROM `seance` WHERE enseignant_id='" + r.getEnseignant() + "' and jour ='" + r.getJour() + "' and hdeb='" + r.getHdeb() + "'";

        try {

            Statement st = c.createStatement();
            Statement st1 = c.createStatement();
            ResultSet myResultSetEns = st1.executeQuery(CheckDispoEns);

            ResultSet myResultSet = st.executeQuery(CheckSalleSeance);

            ArrayList res = new ArrayList();

            if (myResultSet.absolute(1)  ) {
                System.out.println("errooooor 1 ");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Modification d'une séance!");
                alert.setHeaderText("Warning");
                alert.setContentText("Cette salle est occupée ! ");
                alert.showAndWait();
            } else if(myResultSetEns.absolute(1))
            {
              System.out.println("errooooor 1 ");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ajout d'une séance!");
                alert.setHeaderText("Warning");
                alert.setContentText("Cet enseignant à deja un cours  ! ");
                alert.showAndWait();  
            }
            else {
                PreparedStatement pst = c.prepareStatement(requete);

                pst.setString(1, r.getEnseignant());
                pst.setString(2, r.getClasse());
                pst.setString(3, r.getSalle());
                pst.setString(4, r.getMatiere());
                pst.setString(5, r.getJour());
                pst.setString(6, r.getHdeb());
                pst.setString(7, r.getHfin());
                pst.setInt(8, r.getId());
                pst.executeUpdate();

                try {
                    JavaMail.sendMail(emailEns, "Modification ");
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public List< Salle> fillSalles() {

        ArrayList< Salle> myList = new ArrayList();
        try {
            PreparedStatement pt = c.prepareStatement("select id,libelle from `salle` ");
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                Salle a = new Salle();
                a.setId(rs.getString(1));
                a.setLibelle(rs.getString(2));

                myList.add(a);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List< Salle> ComboxGetIdSalle(String nom) {
        ArrayList< Salle> myList = new ArrayList();
        String requete = "select id from `salle` where libelle = ? ";
        try {
            PreparedStatement pst = c.prepareStatement(requete);

            pst.setString(1, nom);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Salle a = new Salle();
                a.setId(rs.getString(1));

                myList.add(a);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List< Seance> afficherSeance() {

        ArrayList< Seance> myList = new ArrayList();
        try {

            PreparedStatement pt = c.prepareStatement("select s.id,s.jour,s.hdeb,s.hfin,m.nom,c.libelle,sa.libelle,u.nom,u.prenom,u.id "
                    + " from seance s inner join matiere m on s.matiere_id = m.id "
                    + "inner join classe c on s.classe_id = c.id  "
                    + " inner join salle sa on sa.id = s.salle_id "
                    + " inner join user u on u.id = s.enseignant_id "
            );
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                Seance a = new Seance();

                a.setId(rs.getInt(1));

                a.setEnseignant(rs.getString(8));

                a.setClasse(rs.getString(6));

                a.setMatiere(rs.getString(5));

                a.setSalle(rs.getString(7));

                a.setJour(rs.getString(2));
                a.setHdeb(rs.getString(3));
                a.setHfin(rs.getString(4));

                /* System.out.println(rs.getInt(1));
                System.out.println(rs.getString(5));
                  System.out.println(rs.getString(3));
                   System.out.println(rs.getString(2));
                   System.out.println(rs.getString(4));
                    System.out.println(rs.getString(6));
                     System.out.println(rs.getString(7));
                      System.out.println(rs.getString(8)+rs.getString(9));*/
                myList.add(a);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List< Seance> SelectSeance(String id) {

        ArrayList< Seance> myList = new ArrayList();
        try {
            String requete = ("select * from seance where classe_id = ? ");
            PreparedStatement pst = c.prepareStatement(requete);

            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Seance a = new Seance();
                a.setId(rs.getInt(1));
                a.setJour(rs.getString(6));
                a.setHdeb(rs.getString(7));
                a.setHfin(rs.getString(8));
                a.setMatiere(rs.getString(5));
                a.setSalle(rs.getString(4));
                a.setEnseignant(rs.getString(2));
                myList.add(a);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List< Seance> SelectSeanceEns(int id) {

        ArrayList< Seance> myList = new ArrayList();
        try {
            String requete = ("select s.id,s.jour,s.hdeb,s.hfin,m.nom,c.libelle,sa.libelle,u.nom,u.prenom "
                    + " from seance s inner join matiere m on s.matiere_id = m.id "
                    + "inner join classe c on s.classe_id = c.id  "
                    + " inner join salle sa on sa.id = s.salle_id "
                    + " inner join user u on u.id = s.enseignant_id  "
                    + " where enseignant_id = ? ");
            PreparedStatement pst = c.prepareStatement(requete);

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Seance a = new Seance();
                a.setId(rs.getInt(1));

                a.setEnseignant(rs.getString(8));

                a.setClasse(rs.getString(6));

                a.setMatiere(rs.getString(5));

                a.setSalle(rs.getString(7));

                a.setJour(rs.getString(2));
                a.setHdeb(rs.getString(3));
                a.setHfin(rs.getString(4));
                myList.add(a);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List< Matiere> findM(String classe) {
        ArrayList< Matiere> myList = new ArrayList();
        String requete = "select id,nom from `matiere` where id = ? ";
        try {
            PreparedStatement pst = c.prepareStatement(requete);

            pst.setString(1, classe);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Matiere a = new Matiere();
                a.setId(rs.getString(1));
                a.setNom(rs.getString(2));
                myList.add(a);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List< User> findEns(String ens) {
        ArrayList< User> myList = new ArrayList();
        String requete = "select id,nom,prenom from `user` where id = ? ";
        try {
            PreparedStatement pst = c.prepareStatement(requete);

            pst.setString(1, ens);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                User a = new User();
                a.setIdentifiant(rs.getInt("id"));
                a.setNom(rs.getString("nom"));
                a.setPrenom(rs.getString("prenom"));
                myList.add(a);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List< Salle> findSalleE(String classe) {
        ArrayList< Salle> myList = new ArrayList();
        String requete = "select id,libelle from `salle` where id = ? ";
        try {
            PreparedStatement pst = c.prepareStatement(requete);

            pst.setString(1, classe);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Salle a = new Salle();
                a.setId(rs.getString("id"));
                a.setLibelle(rs.getString("libelle"));

                myList.add(a);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public void ajouterSeance(Seance d, String emailEns) {

        try {
            Statement st = c.createStatement();
            Statement st1 = c.createStatement();
            String CheckNbS = "SELECT count(id) AS total, \n"
                    + " sum(CASE WHEN enseignant_id = '" + d.getEnseignant() + "' then 1 else 0 end) AS sp, \n"
                    + " sum(CASE WHEN classe_id = '" + d.getClasse() + "' then 1 else 0 end) AS sc"
                    + " FROM `seance`  where jour ='" + d.getJour() + "'";

            String CheckSalleSeance = "SELECT * \n"
                    + "  FROM seance p \n"
                    + " WHERE p.salle_id = '" + d.getSalle() + "' and p.hdeb = '" + d.getHdeb() + "'  and p.jour= '" + d.getJour() + "'";

            String req = "insert into `seance`" + "(`enseignant_id`,`classe_id`,`salle_id`,`matiere_id`,`jour`,`hdeb`,`hfin`)" + " values('" + d.getEnseignant() + "','" + d.getClasse()
                    + "','" + d.getSalle() + "','" + d.getMatiere() + "','" + d.getJour() + "','" + d.getHdeb() + "','" + d.getHfin() + "')";

            String CheckDispoEns = "SELECT * FROM `seance` WHERE enseignant_id='" + d.getEnseignant() + "' and jour ='" + d.getJour() + "' and hdeb='" + d.getHdeb() + "'";

            ResultSet myResultSetEns = st1.executeQuery(CheckDispoEns);

            ResultSet myResultSet = st.executeQuery(CheckSalleSeance);

            ArrayList res = new ArrayList();

            if (myResultSetEns.absolute(1)) {
                System.out.println("errooooor 1 ");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ajout d'une séance!");
                alert.setHeaderText("Warning");
                alert.setContentText("Cet enseignant à deja un cours  ! ");
                alert.showAndWait();
            } else if (myResultSet.absolute(1)) {
                System.out.println("errooooor 1 ");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ajout d'une séance!");
                alert.setHeaderText("Warning");
                alert.setContentText("Cette salle est occupée à cette heure ! ");
                alert.showAndWait();
            } else {
                st.executeUpdate(req);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ajout d'Ajout d'une séance !");
                alert.setHeaderText("Information");
                alert.setContentText("Séance cours bien ajouté ");

                alert.showAndWait();
                try {
                    JavaMail.sendMail(emailEns, "Ajout ");
                } catch (Exception ex) {

                }
            }

            /*while(myResultSet.next()){
                    res.add(myResultSet.getInt("id"));*/
            // res.add(myResultSet.next());
            //  res.add(myResultSet.getInt("sp"));
            //  res.add(myResultSet.getInt("sc"));
            //  System.out.println(myResultSet.getInt("sp"));
            // System.out.println(myResultSet.getInt("sc"));
            /*  for (Iterator it = res.iterator(); it.hasNext();) {
               int e = (int) it.next();
              if (e < 2)
              {
                System.out.println("up");  
                st.executeUpdate(req);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Ajout d'Ajout d'une séance !");
             alert.setHeaderText("Information");
             alert.setContentText("Séance cours bien ajouté ");
             
             alert.showAndWait();
             }
                 
               } */
        } catch (SQLException ex) {
            Logger.getLogger(ServiceClasse.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ajout d'une séance!");
            alert.setHeaderText("Information");
            alert.setContentText("Séance cours non ajoutée!!!!!!!! ");
            alert.showAndWait();
        }

    }

    public ArrayList<Seance> rechercherSeanceParClasse(String classe) {

        String requete = "select s.id,s.jour,s.hdeb,s.hfin,m.nom,c.libelle,sa.libelle,u.nom,u.prenom "
                + " from seance s inner join matiere m on s.matiere_id = m.id "
                + "inner join classe c on s.classe_id = c.id  "
                + " inner join salle sa on sa.id = s.salle_id "
                + " inner join user u on u.id = s.enseignant_id "
                + " where c.libelle =?";

        ArrayList<Seance> myList = new ArrayList();
        try {

            PreparedStatement pst = c.prepareStatement(requete);
            pst.setString(1, classe);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Seance a = new Seance();
                a.setId(rs.getInt(1));

                a.setEnseignant(rs.getString(8));

                a.setClasse(rs.getString(6));

                a.setMatiere(rs.getString(5));

                a.setSalle(rs.getString(7));

                a.setJour(rs.getString(2));
                a.setHdeb(rs.getString(3));
                a.setHfin(rs.getString(4));

                myList.add(a);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return myList;
    }

    public void supprimerSeance(int id) {
        String requete = "DELETE FROM seance WHERE id =?";

        try {
            PreparedStatement pst = c.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }

}
