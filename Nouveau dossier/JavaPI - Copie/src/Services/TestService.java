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
import DataBase.MyDbConnection;
import Entities.Test;

/**
 *
 * @author 21654
 */
public class TestService {

    Connection connexion;

    public TestService() {
        connexion = MyDbConnection.getInstance().getConnexion();
    }

    public void ajouterTest(Test t) throws SQLException {
        System.out.println("the type is" + t.getTypeIntell());
        String SQL_INSERT = "INSERT INTO Test ( question,reponse,type_intell) VALUES (?,?,?)";
        PreparedStatement prd = connexion.prepareStatement(SQL_INSERT);
        prd.setString(1, t.getQuestion());
        prd.setString(2, t.getReponse());
        prd.setInt(3, t.getTypeIntell());
        prd.executeUpdate();
    }

    public List<Test> getAllTests() throws SQLException {
        List<Test> tests = new ArrayList<>();

        String req = "select * from test";
        Statement stm = connexion.createStatement();
        ResultSet result = stm.executeQuery(req);

        while (result.next()) {
            Test t = new Test(result.getInt(1), result.getString("Question"), result.getString("reponse"), result.getInt("type_intell"));
            tests.add(t);
        }

        return tests;
    }

    public void UpdateTest(Test t) throws SQLException {
        String SQL_UPDATE = "UPDATE Test SET question=?, reponse=?,type_intell=? where id =? ";
        PreparedStatement prd = connexion.prepareStatement(SQL_UPDATE);
        prd.setString(1, t.getQuestion());
        prd.setString(2, t.getReponse());
        prd.setInt(3, t.getTypeIntell());
        prd.setInt(4, t.getId());
        prd.executeUpdate();
    }

    public void DeleteTest(int id) throws SQLException {
        String sql = "DELETE FROM Test where id=?";
        PreparedStatement stm = connexion.prepareStatement(sql);
        stm.setInt(1, id);
        stm.executeUpdate();
    }

    public ResultSet getAllTestResultSet() throws SQLException {
        List<Test> tests = new ArrayList<>();

        String req = "select * from Test";
        Statement stm = connexion.createStatement();
        ResultSet result = stm.executeQuery(req);

        return result;
    }
}
