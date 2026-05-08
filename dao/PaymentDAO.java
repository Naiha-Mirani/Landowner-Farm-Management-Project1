package dao;

import database.DBConnection;
import models.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    private Connection conn;

    public PaymentDAO() {
        this.conn = DBConnection.getConnection();
    }

    // ── CREATE ───────────────────────────────────────────────────────────────
    public boolean addPayment(Payment p) {
        String sql = "INSERT INTO PAYMENT (Payment_ID, Worker_ID, Amount, " +
                     "Payment_Type, Payment_Date, Notes) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getPaymentId());
            ps.setString(2, p.getWorkerId());
            ps.setDouble(3, p.getAmount());
            ps.setString(4, p.getPaymentType());
            ps.setString(5, p.getPaymentDate());
            ps.setString(6, p.getNotes());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Payment added: " + p.getPaymentId()
                    + " (Rs " + p.getAmount() + " to " + p.getWorkerId() + ")");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error! addPayment: " + e.getMessage());
        }
        return false;
    }

    // ── READ ALL ─────────────────────────────────────────────────────────────
    public List<Payment> getAllPayments() {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM PAYMENT ORDER BY Payment_Date, Worker_ID";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("Error! getAllPayments: " + e.getMessage());
        }
        return list;
    }

    // ── READ BY WORKER ───────────────────────────────────────────────────────
    public List<Payment> getPaymentsByWorker(String workerId) {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM PAYMENT WHERE Worker_ID = ? ORDER BY Payment_Date";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, workerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("Error! getPaymentsByWorker: " + e.getMessage());
        }
        return list;
    }

    // ── AGGREGATE QUERY ──────────────────────────────────────────────────────
    /**
     * Returns the total amount paid to a specific worker using SUM().
     * Demonstrates SQL aggregate function in Java.
     */
    public double getTotalPaidToWorker(String workerId) {
        String sql = "SELECT SUM(Amount) AS Total FROM PAYMENT WHERE Worker_ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, workerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble("Total");
        } catch (SQLException e) {
            System.err.println("Error! getTotalPaidToWorker: " + e.getMessage());
        }
        return 0.0;
    }

    // ── DELETE ───────────────────────────────────────────────────────────────
    public boolean deletePayment(String paymentId) {
        String sql = "DELETE FROM PAYMENT WHERE Payment_ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, paymentId);
            int rows = ps.executeUpdate();
            if (rows > 0) { System.out.println("[OK] Payment deleted: " + paymentId); return true; }
        } catch (SQLException e) {
            System.err.println("Error! deletePayment: " + e.getMessage());
        }
        return false;
    }

    private Payment mapRow(ResultSet rs) throws SQLException {
        return new Payment(
            rs.getString("Payment_ID"),
            rs.getString("Worker_ID"),
            rs.getDouble("Amount"),
            rs.getString("Payment_Type"),
            rs.getString("Payment_Date"),
            rs.getString("Notes")
        );
    }
}
