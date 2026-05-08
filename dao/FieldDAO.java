package dao;

import database.DBConnection;
import models.Field;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FieldDAO (CRUD for FIELD table).
 */
public class FieldDAO {

    private Connection conn;

    public FieldDAO() {
        this.conn = DBConnection.getConnection();
    }

    public boolean addField(Field f) {
        String sql = "INSERT INTO FIELD (Field_ID, Landowner_ID, Location, Area_Acres, Soil_Type) " +
                     "VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, f.getFieldId());
            ps.setString(2, f.getLandownerId());
            ps.setString(3, f.getLocation());
            ps.setDouble(4, f.getAreaAcres());
            ps.setString(5, f.getSoilType());
            int rows = ps.executeUpdate();
            if (rows > 0) { System.out.println("OK! Field added: " + f.getFieldId()); return true; }
        } catch (SQLException e) {
            System.err.println("Error! addField: " + e.getMessage());
        }
        return false;
    }

    public List<Field> getAllFields() {
        List<Field> list = new ArrayList<>();
        String sql = "SELECT * FROM FIELD ORDER BY Landowner_ID, Field_ID";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("Error! getAllFields: " + e.getMessage());
        }
        return list;
    }

    public List<Field> getFieldsByLandowner(String landownerId) {
        List<Field> list = new ArrayList<>();
        String sql = "SELECT * FROM FIELD WHERE Landowner_ID = ? ORDER BY Field_ID";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, landownerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("Error! getFieldsByLandowner: " + e.getMessage());
        }
        return list;
    }

    public Field getFieldById(String fieldId) {
        String sql = "SELECT * FROM FIELD WHERE Field_ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fieldId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.err.println("Error! getFieldById: " + e.getMessage());
        }
        return null;
    }

    public boolean updateField(Field f) {
        String sql = "UPDATE FIELD SET Location=?, Area_Acres=?, Soil_Type=? WHERE Field_ID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, f.getLocation());
            ps.setDouble(2, f.getAreaAcres());
            ps.setString(3, f.getSoilType());
            ps.setString(4, f.getFieldId());
            int rows = ps.executeUpdate();
            if (rows > 0) { System.out.println("[OK] Field updated: " + f.getFieldId()); return true; }
        } catch (SQLException e) {
            System.err.println("Error! updateField: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteField(String fieldId) {
        String sql = "DELETE FROM FIELD WHERE Field_ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fieldId);
            int rows = ps.executeUpdate();
            if (rows > 0) { System.out.println("[OK] Field deleted: " + fieldId); return true; }
        } catch (SQLException e) {
            System.err.println("Error! deleteField: " + e.getMessage());
        }
        return false;
    }

    private Field mapRow(ResultSet rs) throws SQLException {
        return new Field(
            rs.getString("Field_ID"),
            rs.getString("Landowner_ID"),
            rs.getString("Location"),
            rs.getDouble("Area_Acres"),
            rs.getString("Soil_Type")
        );
    }
}
