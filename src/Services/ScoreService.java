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
import Entities.Score;
import DataBase.MyDbConnection;

/**
 *
 * @author 21654
 */
public class ScoreService {

    Connection connexion;

    public ScoreService() {
        connexion = MyDbConnection.getInstance().getConnexion();
    }

    void ajouterScore(Score s) throws SQLException {
        String SQL_INSERT = "INSERT INTO Score ( score,date) VALUES (?,?)";
        PreparedStatement prd = connexion.prepareStatement(SQL_INSERT);
        prd.setInt(1, s.getScore());
        prd.setDate(2, new java.sql.Date(s.getDate().getTime()));
        prd.executeUpdate();
    }

    List<Score> getAllScores() throws SQLException {
        List<Score> scores = new ArrayList<>();

        String req = "select * from Score";
        Statement stm = connexion.createStatement();
        ResultSet result = stm.executeQuery(req);

        while (result.next()) {
            Score s = new Score(result.getInt(1), result.getInt("score"), result.getDate("date")
            );
            scores.add(s);
        }

        return scores;
    }

    void UpdateScore(Score s) throws SQLException {
        String SQL_UPDATE = "UPDATE Score SET score=?, date=? where id =? ";
        PreparedStatement prd = connexion.prepareStatement(SQL_UPDATE);
        prd.setInt(1, s.getScore());
        prd.setDate(2, new java.sql.Date(s.getDate().getTime()));
        prd.setInt(3, s.getId());
        prd.executeUpdate();
    }

    void DeleteScore(int id) throws SQLException {
        String sql = "DELETE FROM Score where id=?";
        PreparedStatement stm = connexion.prepareStatement(sql);
        stm.setInt(1, id);
        stm.executeUpdate();
    }
    
    public List<Score> GetAllScoresByUserID(int id)throws SQLException{
        List<Score> scores = new ArrayList<>();
        String sql = "Select * From score where user_id=?";
        PreparedStatement stm = connexion.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet result = stm.executeQuery();
        while (result.next()) {
            Score s = new Score(result.getInt(1), result.getInt("score"), result.getDate("date")
            );
            scores.add(s);
        }

        return scores;
    }

}
