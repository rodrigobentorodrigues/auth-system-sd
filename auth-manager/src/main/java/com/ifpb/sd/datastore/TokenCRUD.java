package com.ifpb.sd.datastore;

import com.ifpb.sd.domain.Token;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsavel por todas as operações referente ao token no banco de dados
 *
 * @author rodrigobento
 */
public class TokenCRUD {

    private Connection con;

    public TokenCRUD() {
        try {
            this.con = ConFactory.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean insert(Token token) {
        String sql = "INSERT INTO token (value, isValid, id_user, created, appId) "
                + "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, token.getValue());
            stmt.setBoolean(2, token.isValid());
            stmt.setInt(3, token.getUser());
            stmt.setTimestamp(4, Timestamp.valueOf(token.getDateHour()));
            stmt.setString(5, token.getProvider());
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean invalidateToken(String value) {
        String sql = "UPDATE token SET isValid = ? WHERE value = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setBoolean(1, false);
            stmt.setString(2, value);
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<Token> listValid() {
        List<Token> tokens = new ArrayList<>();
        String sql = "SELECT * FROM token WHERE isValid = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setBoolean(1, true);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Token token = new Token();
                token.setValue(rs.getString("value"));
                token.setValid(true);
                token.setDateHour(rs.getTimestamp("created").toLocalDateTime());
                token.setUser(rs.getInt("id_user"));
                token.setProvider(rs.getString("appId"));
                tokens.add(token);
            }
            stmt.close();
            rs.close();
            return tokens;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tokens;
    }

    public Token getToken(int idUser) {
        Token token = null;
        String sql = "SELECT * FROM token WHERE id_user = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                token = new Token();
                token.setValue(rs.getString("value"));
                token.setValid(rs.getBoolean("isValid"));
                token.setDateHour(rs.getTimestamp("created").toLocalDateTime());
                token.setUser(rs.getInt("id_user"));
                token.setProvider(rs.getString("appId"));
            }
            stmt.close();
            rs.close();
            return token;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return token;
    }

    public Token getToken(String value) {
        Token token = null;
        String sql = "SELECT * FROM token WHERE value = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, value);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                token = new Token();
                token.setValue(rs.getString("value"));
                token.setValid(rs.getBoolean("isValid"));
                token.setDateHour(rs.getTimestamp("created").toLocalDateTime());
                token.setUser(rs.getInt("id_user"));
                token.setProvider(rs.getString("appId"));
            }
            stmt.close();
            rs.close();
            return token;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return token;
    }

}
