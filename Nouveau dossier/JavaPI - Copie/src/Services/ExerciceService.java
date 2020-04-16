/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Entities.Exercice;
import DataBase.MyDbConnection;

/**
 *
 * @author 21654
 */
public class ExerciceService {
    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */

    Connection connexion;

    public ExerciceService() {
        connexion = MyDbConnection.getInstance().getConnexion();
    }

    public void ajouterExercice(Exercice e) throws SQLException {
        String SQL_INSERT = "INSERT INTO Exercice ( question,reponse,score,course_id) VALUES (?,?,?,4)";
        PreparedStatement prd = connexion.prepareStatement(SQL_INSERT);
        prd.setString(1, e.getQuestion());
        prd.setString(2, e.getReponse());

        prd.setString(3, e.getScore());

        prd.executeUpdate();
    }

    public List<Exercice> getAllExercices() throws SQLException {
        List<Exercice> exercices = new ArrayList<>();

        String req = "select * from Exercice";
        Statement stm = connexion.createStatement();
        ResultSet result = stm.executeQuery(req);

        while (result.next()) {
            Exercice e = new Exercice(result.getInt(1), result.getString("question"), result.getString("reponse"),
                    result.getString("score"), result.getString("option1"), result.getString("option2"), result.getString("option3"));
            exercices.add(e);
        }

        return exercices;
    }

    public void UpdateExercice(Exercice e) throws SQLException {
        String SQL_UPDATE = "UPDATE Exercice SET question=?, reponse=?,score=? where id =?";
        PreparedStatement prd = connexion.prepareStatement(SQL_UPDATE);
        prd.setString(1, e.getQuestion());
        prd.setString(2, e.getReponse());
        prd.setString(3, e.getScore());
        prd.setInt(4, e.getId());
        prd.executeUpdate();
    }

    public void DeleteExercice(int id) throws SQLException {
        String sql = "DELETE FROM Exercice where id=?";
        PreparedStatement stm = connexion.prepareStatement(sql);
        stm.setInt(1, id);
        stm.executeUpdate();
    }

    public ResultSet getAllExercicesResultSet() throws SQLException {
        List<Exercice> exercices = new ArrayList<>();

        String req = "select * from Exercice";
        Statement stm = connexion.createStatement();
        ResultSet result = stm.executeQuery(req);

        return result;
    }

    public List<Exercice> getAllExercicesParType(int id) throws SQLException {
        List<Exercice> exercices = new ArrayList<>();

        String req = "select * from Exercice where course_id=?";
        PreparedStatement stm = connexion.prepareStatement(req);
        stm.setInt(1, id);
        ResultSet result = stm.executeQuery(req);

        while (result.next()) {
            Exercice e = new Exercice(result.getInt(1), result.getString("question"), result.getString("reponse"),
                    result.getString("score"), result.getString("option1"), result.getString("option2"), result.getString("option3"));
            System.err.println(e);
            exercices.add(e);
        }
        return exercices;
    }

}
