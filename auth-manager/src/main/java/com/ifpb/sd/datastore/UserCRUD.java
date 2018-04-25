package com.ifpb.sd.datastore;

import com.ifpb.sd.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe responsavel por todas as operações referente ao user
 *
 * @author rodrigobento
 */
public class UserCRUD {

    private Connection con;

    public UserCRUD() {
        try {
            this.con = ConFactory.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int create(User user) {
        String sql = "INSERT INTO user (appId, email, senha) VALUES (?, ?, ?)";
        int id = 0;
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getAppId());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getSenha());
            stmt.executeUpdate();
            stmt.close();
            String queryKey = "SELECT max (id) FROM user";
            PreparedStatement stmtKey = con.prepareStatement(queryKey);
            ResultSet rs = stmtKey.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            stmtKey.close();
            rs.close();
            return id;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public boolean update(User user) {
        String sql = "UPDATE user SET email = ?, senha = ? WHERE id = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getSenha());
            stmt.setInt(3, user.getId());
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean delete(User user) {
        String sql = "DELETE FROM user WHERE id = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, user.getId());
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public User getUser(String email, String senha) {
        String sql = "SELECT * FROM user WHERE email = ? AND senha = ?";
        User user = null;
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setEmail(email);
                user.setSenha(senha);
                user.setAppId(rs.getString("appId"));
                user.setId(rs.getInt("id"));
            }
            stmt.close();
            rs.close();
            return user;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

}
