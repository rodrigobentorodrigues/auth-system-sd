package com.ifpb.sd.repository;

import com.ifpb.sd.domain.Provider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author rodrigobento
 */
public class RemoteRefCRUD {
    
    private Connection con;
    
    public RemoteRefCRUD(){
        try {
            this.con = ConFactory.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean create(Provider provider){
        String sqlInsert = "INSERT INTO provider (appId, host, name, port) "
                + "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = con.prepareStatement(sqlInsert);
            stmt.setString(1, provider.getAppId());
            stmt.setString(2, provider.getHost());
            stmt.setString(3, provider.getName());
            stmt.setInt(4, provider.getPort());
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public boolean delete(Provider provider){
        String sqlInsert = "DELETE FROM provider WHERE appId = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sqlInsert);
            stmt.setString(1, provider.getAppId());
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public boolean update(Provider provider){
        String sqlInsert = "UPDATE provider SET name = ?, host = ?,"
                + " port = ? WHERE appId = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sqlInsert);
            stmt.setString(1, provider.getName());
            stmt.setString(2, provider.getHost());
            stmt.setInt(3, provider.getPort());
            stmt.setString(4, provider.getAppId());
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public Provider readRemote(String appId){
        String sqlInsert = "SELECT * FROM provider WHERE appId = ?";
        Provider provider = null;
        try {
            PreparedStatement stmt = con.prepareStatement(sqlInsert);
            stmt.setString(1, appId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                provider = new Provider();
                provider.setAppId(appId);
                provider.setHost(rs.getString("host"));
                provider.setName(rs.getString("name"));
                provider.setPort(rs.getInt("port"));
            }
            stmt.close();
            rs.close();
            return provider;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return provider;
    }
    
}
