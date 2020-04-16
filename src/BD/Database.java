/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author admin
 */
public class Database {
    
    private static Database instance;
    private Connection connexion;
    private String url = "JDBC:mysql://localhost/projet";
    private String user = "root";
    private String password = "";

    private Database() {
        try {
            connexion = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            System.out.println("Probleme de connexion");
        }
    }

    public static Database getInstance() {
        if (Database.instance == null) {
            Database.instance = new Database();
        }
        return Database.instance;
    }

    public Connection getConnexion() {
        return this.connexion;
    }

}
