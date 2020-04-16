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
import Entities.Course;
import DataBase.MyDbConnection;
import javax.swing.text.html.HTMLDocument;

/**
 *
 * @author 21654
 */
public class CourseService {

    Connection connexion;

    public CourseService() {
        connexion = MyDbConnection.getInstance().getConnexion();
    }

    public void AjouterCourse(Course c) throws SQLException {
        String SQL_INSERT = "INSERT INTO Course (nom, description,contenu,type_intelligence ,niveau,user_id) VALUES (?,?,?,?,?,1)";
        PreparedStatement prd = connexion.prepareStatement(SQL_INSERT);
        prd.setString(1, c.getNom());
        prd.setString(2, c.getDescription());
        prd.setString(3, c.getContenu());
        prd.setInt(4, c.getTypeIntelligence());
        prd.setInt(5, c.getNiveau());

        prd.executeUpdate();
    }

    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();

        String req = "select * from Course";
        Statement stm = connexion.createStatement();
        ResultSet result = stm.executeQuery(req);

        while (result.next()) {
            Course c = new Course(result.getInt(1), result.getString("nom"), result.getString("description"),
                    result.getString("contenu"), result.getInt("Type_intelligence"), result.getInt("niveau"));
            courses.add(c);
        }

        return courses;
    }

    public void UpdateCourse(Course c) throws SQLException {
        String SQL_UPDATE = "UPDATE Course SET nom=?, description=?,contenu=?,type_intelligence=? ,niveau=? where id=? ";
        PreparedStatement prd = connexion.prepareStatement(SQL_UPDATE);
        prd.setString(1, c.getNom());
        prd.setString(2, c.getDescription());
        prd.setString(3, c.getContenu());
        prd.setInt(4, c.getTypeIntelligence());
        prd.setInt(5, c.getNiveau());
        prd.setInt(6, c.getId());
        prd.executeUpdate();
    }

    public void DeleteCourse(int id) throws SQLException {
        String sql = "DELETE FROM Course where id=?";
        PreparedStatement stm = connexion.prepareStatement(sql);
        stm.setInt(1, id);
        stm.executeUpdate();
    }

    public ResultSet getAllCoursesResultSet() throws SQLException {
        List<Course> courses = new ArrayList<>();

        String req = "select * from Course";
        Statement stm = connexion.createStatement();
        ResultSet result = stm.executeQuery(req);

        return result;
    }

    public List<Course> getListCoursesByIntellType(int type) {
        List<Course> courses = new ArrayList<>();
        try {
            String sql = "Select * from course where type_intelligence = ?";
            PreparedStatement stm = connexion.prepareStatement(sql);
            stm.setInt(1, type);
            ResultSet result = stm.executeQuery();
            while (result.next()) {
                Course c = new Course(result.getInt(1), result.getString("nom"), result.getString("description"),
                        result.getString("contenu"), result.getInt("Type_intelligence"), result.getInt("niveau"));
                courses.add(c);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return courses;
    }

}
