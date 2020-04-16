/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import DataBase.MyDbConnection;
import Entities.Course;
import Entities.FosUser;
import utils.FOSJCrypt;

/**
 *
 * @author 21654
 */
public class FosUserService {

    Connection connexion;

    public FosUserService() {
        connexion = MyDbConnection.getInstance().getConnexion();
    }

    public FosUser Login(String username, String password) throws SQLException {
        String value = "";
        FosUser u = new FosUser();
        boolean result = false;
        String query = "Select * from fos_user where username=?";
        PreparedStatement prd = connexion.prepareStatement(query);
        prd.setString(1, username);
        ResultSet res = prd.executeQuery();
        if (res == null) {
            System.out.println("user does not exist");
            return null;
        }
        while (res.next()) {
           // value = res.getString(1);

            u = new FosUser(res.getInt("id"), res.getString("username"), res.getString("username_canonical"),
                    "", "", true, res.getString("password"), "", res.getString("nom"), res.getString("prenom")
            ,res.getInt("type_intelligence"));

        }
        result = utils.FOSJCrypt.checkPassword(password, u.getPassword());
        return u;
    }

    public String getRole(String username) throws SQLException {
        String Query = "Select roles from fos_user where username=?";
        PreparedStatement prd = connexion.prepareStatement(Query);
        prd.setString(1, username);
        ResultSet res = prd.executeQuery();
        String value = "";
        while (res.next()) {
            value = res.getString(1);
            System.out.println(value);

        }
        return value;
    }
}
