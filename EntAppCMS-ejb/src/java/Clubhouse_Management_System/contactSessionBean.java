/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clubhouse_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author admin
 */
@Stateless
public class contactSessionBean implements contactSessionBeanRemote {

    @Override
    public String send(String name, String email, String message) {

        Connection con = null;
        PreparedStatement ps = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:app/jdbc/cms");
            con = ds.getConnection();
            ps = con.prepareStatement("insert into contact values(?,?,?,?)");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, message);
            ps.setString(4, dateFormat.format(date));
            int count = ps.executeUpdate();

            if (count > 0) {
                con.close();
                return "Have a nice day :)";
            }
        } catch (Exception ex) {
            System.out.println("Login error -->" + ex.getMessage());
            return "Error, please try again later...";
        }
        return "Error, please try again later...";
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
