package dao;

import database.DBConnection;
import models.Worker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * WorkerDAO (CRUD operations for the WORKER table).
 */
public class WorkerDAO {

    private Connection conn;

    public WorkerDAO() {
        this.conn = DBConnection.getConnection();
    }

    // ── CREATE ───────────────────────────────────────────────────────────────
    public boolean addWorker(Worker w) {
        String sql = "INSERT INTO WORKER (Worker_ID, Landowner_ID, Name, CNIC, " +
                     "Skill_Type, Daily_Wage, Hire_Date) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, w.getWorkerId());
            ps.setString(2, w.getLandownerId());
            ps.setString(3, w.getName());
            ps.setString(4, w.getCnic());
            ps.setString(5, w.getSkillType());
            ps.setDouble(6, w.getDailyWage());
            ps.setString(7, w.getHireDate());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("[OK] Worker added: " + w.getWorkerId() + " — " + w.getName());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("[ERR] addWorker: " + e.getMessage());
        }
        return false;
    }

    // ── READ ALL ─────────────────────────────────────────────────────────────
    public List<Worker> getAllWorkers() {
        List<Worker> list = new ArrayList<>();
        String sql = "SELECT * FROM WORKER ORDER BY Landowner_ID, Worker_ID";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("[ERR] getAllWorkers: " + e.getMessage());
        }
        return list;
    }

    // ── READ BY LANDOWNER ────────────────────────────────────────────────────
    /**
     * Returns all workers hired by a specific Landowner.
     * Demonstrates parameterised query with FK filtering.
     */
    public List<Worker> getWorkersByLandowner(String landownerId) {
        List<Worker> list = new ArrayList<>();
        String sql = "SELECT * FROM WORKER WHERE Landowner_ID = ? ORDER BY Worker_ID";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, landownerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("[ERR] getWorkersByLandowner: " + e.getMessage());
        }
        return list;
    }

    // ── READ ONE ─────────────────────────────────────────────────────────────
    public Worker getWorkerById(String workerId) {
        String sql = "SELECT * FROM WORKER WHERE Worker_ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, workerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.err.println("[ERR] getWorkerById: " + e.getMessage());
        }
        return null;
    }

    // ── UPDATE ───────────────────────────────────────────────────────────────
    public boolean updateWorker(Worker w) {
        String sql = "UPDATE WORKER SET Name=?, Skill_Type=?, Daily_Wage=? WHERE Worker_ID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, w.getName());
            ps.setString(2, w.getSkillType());
            ps.setDouble(3, w.getDailyWage());
            ps.setString(4, w.getWorkerId());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("[OK] Worker updated: " + w.getWorkerId());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("[ERR] updateWorker: " + e.getMessage());
        }
        return false;
    }

    // ── DELETE ───────────────────────────────────────────────────────────────
    public boolean deleteWorker(String workerId) {
        String sql = "DELETE FROM WORKER WHERE Worker_ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, workerId);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("[OK] Worker deleted: " + workerId);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("[ERR] deleteWorker: " + e.getMessage());
            System.err.println("      Tip: Remove Assignments and Payments for this Worker first.");
        }
        return false;
    }

    // ── HELPER ───────────────────────────────────────────────────────────────
    /** Maps one ResultSet row to a Worker object. Avoids code duplication. */
    private Worker mapRow(ResultSet rs) throws SQLException {
        return new Worker(
            rs.getString("Worker_ID"),
            rs.getString("Landowner_ID"),
            rs.getString("Name"),
            rs.getString("CNIC"),
            rs.getString("Skill_Type"),
            rs.getDouble("Daily_Wage"),
            rs.getString("Hire_Date")
        );
    }
}
