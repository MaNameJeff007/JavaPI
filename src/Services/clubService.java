/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Club;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import BD.DbConnection;
import java.util.function.Predicate;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

/**
 *
 * @author Mohamed Turki
 *
 */
public class clubService {

    Connection connexion;

    public clubService() {
        connexion = DbConnection.getInstance().getConnexion();
    }

//    public void ajouterClub(Club c) throws SQLException {
//        
//        String req = "INSERT INTO `Club` (`id`, `user_id`, `nomclub`, `nom_image`) VALUES (?, ?, ?, ?)";
//        PreparedStatement pstm = connexion.prepareStatement(req);
//        pstm.setInt(1, c.getId());
//        pstm.setInt(2, 1);
//        pstm.setString(3, c.getNomclub());
//        pstm.setString(4, c.getNom_image());
//        pstm.executeUpdate();
//        
//       
//    }
    public void ajouterClub(Club c) throws SQLException {

//        String nomClub = nomClub(c.getNomclub());
        String req = "insert into club ( user_id, nomclub, nom_image) values (1,'" + c.getNomclub() + "','" + c.getNom_image()+ "')";
        Statement st;
        try {
            st = connexion.createStatement();
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(clubService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void supprimerClub(int id) throws SQLException {
        String req = "DELETE FROM `Club` WHERE id = ?";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    public void modifierNomClub(Club c) throws SQLException {
        String req = "UPDATE `Club` SET nomclub = ?,nom_image = ?,user_id = ? where id = ?";

        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setString(1, c.getNomclub());

        pstm.setString(2, c.getNom_image());

        pstm.setInt(3, c.getUser_id());

        pstm.setInt(4, c.getId());
        pstm.executeUpdate();
    }

    public ObservableList<Club> afficher() {
        ObservableList<Club> mylist = FXCollections.observableArrayList();
        String req = " select * from Club ";
        Statement st;
        try {
            st = connexion.createStatement();
            ResultSet resultat = st.executeQuery(req);

            while (resultat.next()) {
                int id = resultat.getInt("id");
                int user_id = resultat.getInt("user_id");
                String nomclub = resultat.getString("nomclub");
                String nom_image = resultat.getString("nom_image");
                Club c = new Club(id, user_id, nomclub, nom_image);
                mylist.add(c);
            }

        } catch (SQLException ex) {
            Logger.getLogger(clubService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mylist;
    }


}
