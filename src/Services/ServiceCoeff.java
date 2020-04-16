/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Classe;
import Entities.Matiere;
import Entities.Coeff;
import BD.Database;
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
public class ServiceCoeff {

    Connection c = Database.getInstance().getConnexion();

    public List< Classe> ComboxGetLibClasse(String id) {
        ArrayList< Classe> myList = new ArrayList();
        String requete = "select libelle from `classe` where id = ? ";
        try {
            PreparedStatement pst = c.prepareStatement(requete);

            pst.setString(1, id);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Classe a = new Classe();
                a.setLibelle(rs.getString(1));

                myList.add(a);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List< Matiere> ComboxGetNomMatiere(String id) {
        ArrayList< Matiere> myList = new ArrayList();
        String requete = "select nom  from `matiere` where id = ? ";
        try {
            PreparedStatement pst = c.prepareStatement(requete);

            pst.setString(1, id);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Matiere a = new Matiere();
                // a.setId(rs.getInt(1));
                a.setNom(rs.getString(1));

                myList.add(a);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List< Classe> ComboxGetIdClasse(String libelle) {
        ArrayList< Classe> myList = new ArrayList();
        String requete = "select id from `classe` where libelle = ? ";
        try {
            PreparedStatement pst = c.prepareStatement(requete);

            pst.setString(1, libelle);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Classe a = new Classe();
                a.setId(rs.getString(1));

                myList.add(a);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List< Matiere> ComboxGetIdMatiere(String nom) {
        ArrayList< Matiere> myList = new ArrayList();
        String requete = "select id , nom from `matiere` where nom = ? ";
        try {
            PreparedStatement pst = c.prepareStatement(requete);

            pst.setString(1, nom);

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

    public List< Classe> fillNiv() {

        ArrayList< Classe> myList = new ArrayList();
        try {
            PreparedStatement pt = c.prepareStatement("select id,libelle from `classe` ");
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                Classe a = new Classe();
                a.setId(rs.getString(1));
                a.setLibelle(rs.getString(2));

                myList.add(a);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public List< Matiere> fillMatieres() {

        ArrayList< Matiere> myList = new ArrayList();
        try {
            PreparedStatement pt = c.prepareStatement("select id,nom from `matiere` ");
            ResultSet rs = pt.executeQuery();
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

    public void ajouterCoeff(Coeff d) {

        try {

            Statement st = c.createStatement();
            String rq1 = "select * from coeff co inner join classe c on c.id=co.niveau_id "
                    + "where c.niveau = (select niveau from classe where id= '" + d.getNiveau() + "') and co.matiere_id ='" + d.getMatiere() + "'";

            String rq2 = "SELECT c.niveau FROM coeff co inner join classe c on c.id=co.niveau_id where co.niveau_id = '" + d.getNiveau() + "' and co.matiere_id='" + d.getMatiere() + "'  ";
            String req = "insert into `coeff`" + "(`niveau_id`,`matiere_id`,`valeur`)" + " values('" + d.getNiveau() + "','" + d.getMatiere() + "','" + d.getValeur() + "')";

            ResultSet myResultSet = st.executeQuery(rq1);
            if (myResultSet.absolute(1)) {
                System.out.println("errooooor");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ajout d'un coefficient!");
                alert.setHeaderText("Warning");
                alert.setContentText(" Le coefficient de matière  est déjà affecté pour ce niveau ! ");
                alert.showAndWait();
            } else {
                st.executeUpdate(req);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ajout d'un coefficient !");
                alert.setHeaderText("Information");
                alert.setContentText("coefficient bien ajouté ");

                alert.showAndWait();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServiceClasse.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ajout d'un coefficient!");
            alert.setHeaderText("Information");
            alert.setContentText("coefficient non ajoutée!! ");
            alert.showAndWait();
        }

    }

    public List< Coeff> afficherCoeff() {

        ArrayList< Coeff> myList = new ArrayList();
        try {
            PreparedStatement pt = c.prepareStatement("select c.id,c.valeur,cl.niveau,m.nom "
                    + " from coeff c inner join matiere m on m.id=c.matiere_id "
                    + " inner join classe cl on cl.id = c.niveau_id ");
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                Coeff a = new Coeff();
                a.setId(rs.getInt(1));
                a.setNiveau(rs.getString(3));
                a.setMatiere(rs.getString(4));
                a.setValeur(rs.getInt(2));

                myList.add(a);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public void modifierCoeff(Coeff co) {
        String rq1 = "select * from coeff co inner join classe c on c.id=co.niveau_id "
                + "where c.niveau = (select niveau from classe where id= '" + co.getNiveau() + "') and co.matiere_id ='" + co.getMatiere() + "'";

        String requete = "UPDATE coeff SET niveau_id=? , matiere_id=? , valeur=?  WHERE id=?";
        try {
            Statement st = c.createStatement();
            ResultSet myResultSet = st.executeQuery(rq1);
            if (myResultSet.absolute(1)) {
                System.out.println("errooooor");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ajout d'un coefficient!");
                alert.setHeaderText("Warning");
                alert.setContentText("Le coefficient de matière  est déjà affecté pour ce niveau! ");
                alert.showAndWait();
            } else {

                PreparedStatement pst = c.prepareStatement(requete);

                pst.setString(1, co.getNiveau());
                pst.setString(2, co.getMatiere());
                pst.setInt(3, co.getValeur());
                pst.setInt(4, co.getId());
                pst.executeUpdate();
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void supprimerCoeff(int id) {
        String requete = "DELETE FROM coeff WHERE id =?";

        try {
            PreparedStatement pst = c.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
