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
import BD.Database;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Selim Chikh Zaouali
 */
public class PermutationService {

    Connection connexion;

    public PermutationService() {
        connexion = Database.getInstance().getConnexion();
    }

    public ObservableList<Permutation> getOwner(int u) throws SQLException {
        ObservableList<Permutation> Permutation = FXCollections.observableArrayList();

        String req = "select * from permutation where parent=" + u;
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);

        while (rst.next()) {
            LocalDateTime ldt = rst.getTimestamp("date").toLocalDateTime();
            Permutation p = new Permutation(rst.getInt("id"),rst.getString("classe_s"), rst.getString("raison"), ldt, rst.getString("etat"), rst.getInt("eleve_id"), rst.getInt("parent"), rst.getString("enfant"));
            Permutation.add(p);
        }

        return Permutation;
    }

    public ResultSet getEnfants(int id) throws SQLException {
        String req = "SELECT nom,prenom FROM user where parent_id=" + id;
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);
        return (rs);
    }

    public int getNiveau(String nom, String prenom) throws SQLException {
        String req = "SELECT c.niveau from classe c join user u on c.id=u.classeeleve_id where u.nom like '%" + nom + "%' and u.prenom like '%" + prenom + "%'";
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);
        int niveau = 0;
        while (rs.next()) {
            niveau = rs.getInt("c.niveau");
        }
        return (niveau);
    }

    public int getIDeleve(int id) throws SQLException {
        String req = "SELECT id from user where parent_id=" + id;
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);
        int ideleve = 0;
        while (rs.next()) {
            ideleve = rs.getInt("id");
        }
        return (ideleve);
    }

    public void changerEtat(int id) throws SQLException {
        String req = "UPDATE permutation SET etat='traitee' where id=" + id;
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.executeUpdate(req);
    }

    public ResultSet getClasse(int niveau) throws SQLException {
        String req = "SELECT libelle from classe where niveau=" + niveau;
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);
        return (rs);
    }

    public List<Permutation> getAllPermutations() throws SQLException {

        List<Permutation> permutations = new ArrayList<>();
        String req = "select * from permutation";
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);
        while (rst.next()) {
            LocalDateTime ldt = rst.getTimestamp("date").toLocalDateTime();

            Permutation p = new Permutation(rst.getInt("id"), rst.getString("classe_s"), rst.getString("raison"), ldt, rst.getString("etat"), rst.getInt("eleve_id"), rst.getInt("parent"), rst.getString("enfant"));
            permutations.add(p);
        }
        return permutations;
    }

    /*public void ajouterPermutatin(Permutation p) throws SQLException 
    {
        String req="INSERT INTO `permutation` (`classe_s`, `raison`,`etat`, `date`, `eleve_id`, `parent`) VALUES (?, ?, ?, '"+p.getDate() + "', ?, ?)";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setString(1, p.getClasse_s());
        pstm.setString(2, p.getRaison());
        pstm.setString(3, p.getEtat());
        pstm.setInt(5, p.getEleve_id());
        pstm.setInt(6, p.getParent());
        pstm.executeUpdate();
    }*/
    public void ajouterPermutation(Permutation p) throws SQLException {

        String req = "INSERT INTO permutation (`classe_s`, `raison`,`etat`, `date`, `eleve_id`, `parent`,`enfant`) VALUES ('" + p.getClasse_s() + "','" + p.getRaison() + "','" + p.getEtat() + "', '" + p.getDate() + "', '" + p.getEleve_id() + "', '" + p.getParent() + "', '" + p.getEnfant() + "') ";
        Statement stm = connexion.createStatement();
        stm.executeUpdate(req);

    }

    public int getClasseId(String classeS) throws SQLException {
        String req = "SELECT * from classe where libelle like '%"+classeS+"%'";
        Statement stm = connexion.createStatement();
        ResultSet rs = stm.executeQuery(req);

        int idclasse = 0;
        while (rs.next()) {
            idclasse = rs.getInt("id");
        }
        return (idclasse);
    }

    public void permuter(String classe, int enf) throws SQLException {
        int id = getClasseId(classe);
        String req = "UPDATE user SET classeeleve_id = ? where id= ?";
        PreparedStatement pstm1 = connexion.prepareStatement(req);
        pstm1.setInt(1, id);
        pstm1.setInt(2, enf);
        pstm1.executeUpdate();

    }

    public void deletePermutation(int id) throws SQLException {
        String req = "DELETE FROM `permutation` WHERE id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    public void traiterPermutation(int eleve_id, String classe_s, int id) throws SQLException {
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

    public void afficherPermuation() throws SQLException {
        String req = "SELECT * FROM permutation";
        PreparedStatement pstm = connexion.prepareStatement(req);
        ResultSet rs = pstm.executeQuery(req);
        System.out.print("\n");
        while (rs.next()) {
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
