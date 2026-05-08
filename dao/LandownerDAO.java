package dao;

import database.DBConnection;
import models.Landowner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * LandownerDAO (Data Access Object for LANDOWNER table).
 */
public class LandownerDAO {

    private Connection conn;

    public LandownerDAO() {
        this.conn = DBConnection.getConnection();
    }

    // ── CREATE ───────────────────────────────────────────────────────────────
    public boolean addLandowner(Landowner lo) {
        String sql = "INSERT INTO LANDOWNER (Landowner_ID, Name, CNIC, Phone, Village, District) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lo.getLandownerId());
            ps.setString(2, lo.getName());
            ps.setString(3, lo.getCnic());
            ps.setString(4, lo.getPhone());
            ps.setString(5, lo.getVillage());
            ps.setString(6, lo.getDistrict());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("OK! Landowner added: " + lo.getLandownerId());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error! addLandowner: " + e.getMessage());
        }
        return false;
    }

    // ── READ ALL ─────────────────────────────────────────────────────────────
    public List<Landowner> getAllLandowners() {
        List<Landowner> list = new ArrayList<>();
        String sql = "SELECT * FROM LANDOWNER ORDER BY Landowner_ID";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Landowner lo = new Landowner(
                    rs.getString("Landowner_ID"),
                    rs.getString("Name"),
                    rs.getString("CNIC"),
                    rs.getString("Phone"),
                    rs.getString("Village"),
                    rs.getString("District")
                );
                list.add(lo);
            }
        } catch (SQLException e) {
            System.err.println("Error! getAllLandowners: " + e.getMessage());
        }
        return list;
    }

    // ── READ ONE ─────────────────────────────────────────────────────────────
    /**
     * Finds and returns one Landowner by ID. Returns null if not found.
     */
    public Landowner getLandownerById(String landownerId) {
        String sql = "SELECT * FROM LANDOWNER WHERE Landowner_ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, landownerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Landowner(
                    rs.getString("Landowner_ID"),
                    rs.getString("Name"),
                    rs.getString("CNIC"),
                    rs.getString("Phone"),
                    rs.getString("Village"),
                    rs.getString("District")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error! getLandownerById: " + e.getMessage());
        }
        return null;
    }

    // ── UPDATE ───────────────────────────────────────────────────────────────
    /**
     * Updates Phone, Village, District of an existing Landowner.
     */
    public boolean updateLandowner(Landowner lo) {
        String sql = "UPDATE LANDOWNER SET Name=?, Phone=?, Village=?, District=? " +
                     "WHERE Landowner_ID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lo.getName());
            ps.setString(2, lo.getPhone());
            ps.setString(3, lo.getVillage());
            ps.setString(4, lo.getDistrict());
            ps.setString(5, lo.getLandownerId());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("OK! Landowner updated: " + lo.getLandownerId());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error! updateLandowner: " + e.getMessage());
        }
        return false;
    }

    // ── DELETE ───────────────────────────────────────────────────────────────
    /**
     * Deletes a Landowner by ID.
     * Note: Will fail if the Landowner has FIELDs or WORKERs (ON DELETE RESTRICT).
     */
    public boolean deleteLandowner(String landownerId) {
        String sql = "DELETE FROM LANDOWNER WHERE Landowner_ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, landownerId);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("OK! Landowner deleted: " + landownerId);
                return true;
            } else {
                System.out.println("WARN! No Landowner found with ID: " + landownerId);
            }
        } catch (SQLException e) {
            System.err.println("Error! deleteLandowner: " + e.getMessage());
            System.err.println("      Tip: Remove all Fields and Workers for this Landowner first.");
        }
        return false;
    }
}
