package com.security.dao;

import com.security.config.DatabaseConnection;
import com.security.model.Incident;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IncidentDAO {

    public List<Incident> findAll() {
        List<Incident> list = new ArrayList<>();
        String sql = "SELECT * FROM incidents ORDER BY id DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Incident i = new Incident();
                i.setId(rs.getInt("id"));
                i.setDescription(rs.getString("description"));
                i.setPriority(rs.getInt("priority"));
                i.setSourceOrigin(rs.getString("source_origin"));
                i.setStatus(rs.getString("status"));
                list.add(i);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public void insert(Incident i) {
        String sql = "INSERT INTO incidents (description, priority, source_origin, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, i.getDescription());
            ps.setInt(2, i.getPriority());
            ps.setString(3, i.getSourceOrigin());
            ps.setString(4, "PENDIENTE");
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void updateStatus(int id, String status) {
        String sql = "UPDATE incidents SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void delete(int id) {
        String sql = "DELETE FROM incidents WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}