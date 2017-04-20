/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clubhouse_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
public class bookingSessionBean implements bookingSessionBeanRemote {

    @Resource(name = "cms")
    private DataSource cms;

    public String findByService(String service) {
        Connection con = null;
        PreparedStatement ps = null;
        String output = "";
        try {
            con = cms.getConnection();
            ps = con.prepareStatement("select id,date,start,end,facility from booking where service = ? and quota > 0");
            ps.setString(1, service);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String startTime;
                String endTime;
                startTime = rs.getString("start");
                startTime = startTime.substring(0,5);
                endTime = rs.getString("end");
                endTime = endTime.substring(0,5);
                output +=  "<tr><td><input type='radio'/></td><td>"+rs.getString("date")+"</td><td>"+startTime + " - " +endTime+ "</td><td>" + rs.getString("facility")  + "</td></tr>";
            }
            con.close();
            return output;
        } catch (Exception ex) {
            System.out.println("Login error -->" + ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error, please try again later..." + ex.getMessage(), null));
        }
        return "";
    }

    public String findByTime(String date, String time) {
        return "";
    }

    public List<String> service() {
        Connection con = null;
        PreparedStatement ps = null;
        List<String> availableItems = availableItems = new ArrayList<>();
        try {
            con = cms.getConnection();
            ps = con.prepareStatement("select name from  service ");
            ResultSet rs = ps.executeQuery();
            availableItems = new ArrayList<>();
            while (rs.next()) {
                    availableItems.add(rs.getString("name"));
                }
            con.close();
            return availableItems;
        } catch (Exception ex) {
            System.out.println("Login error -->" + ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error, please try again later..." + ex.getMessage(), null));
        }
        return null;
    }

    public void send() {

    }
}
