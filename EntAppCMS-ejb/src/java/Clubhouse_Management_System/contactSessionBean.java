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
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;

/**
 *
 * @author admin
 */
@Stateless
public class contactSessionBean implements contactSessionBeanRemote {

    @Resource(name = "cms")
    private DataSource cms;

    @Override
    public void send(String name, String email, String message) {

        Connection con = null;
        PreparedStatement ps = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        try {
            con = cms.getConnection();
            ps = con.prepareStatement("insert into contact values(?,?,?,?)");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, message);
            ps.setString(4, dateFormat.format(date));
            int count = ps.executeUpdate();

            if (count > 0) {
                con.close();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Have a nice day :)", null));

            }
        } catch (Exception ex) {
            System.out.println("Login error -->" + ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error, please try again later..." + ex.getMessage(), null));
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
