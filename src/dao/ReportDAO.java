package dao;
import database.DBConnection;
import java.sql.*;

/**
 * ReportDAO (Complex multi-table JOIN queries).
 * These queries span multiple tables using the FK relationships from the ERD.
 */
public class ReportDAO {

    private Connection conn;

    public ReportDAO() {
        this.conn = DBConnection.getConnection();
    }

    // ── REPORT 1: All workers for a given Landowner ──────────────────────────
    /**
     * JOIN chain: LANDOWNER → FIELD → ASSIGNMENT → WORKER
     */
    public void reportWorkersByLandowner(String landownerId) {
        String sql =
            "SELECT w.Worker_ID, w.Name AS Worker_Name, w.Skill_Type, w.Daily_Wage, " +
            "       f.Field_ID, f.Location, a.Start_Date, a.End_Date, " +
            "       lo.Name AS Landowner_Name " +
            "FROM LANDOWNER lo " +
            "JOIN FIELD f      ON f.Landowner_ID = lo.Landowner_ID " +
            "JOIN ASSIGNMENT a ON a.Field_ID      = f.Field_ID " +
            "JOIN WORKER w     ON w.Worker_ID     = a.Worker_ID " +
            "WHERE lo.Landowner_ID = ? " +
            "ORDER BY f.Field_ID, w.Name";

        System.out.println("\n======== Workers assigned to Landowner: " + landownerId + " ========");
        System.out.printf("%-8s %-20s %-12s %-10s %-8s %-15s %-12s %-12s%n",
            "Wkr_ID","Worker Name","Skill","Wage(Rs)","Field","Location","Start","End");
        System.out.println("-".repeat(100));

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, landownerId);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
                String end = rs.getString("End_Date") != null ? rs.getString("End_Date") : "Ongoing";
                System.out.printf("%-8s %-20s %-12s %-10.0f %-8s %-15s %-12s %-12s%n",
                    rs.getString("Worker_ID"),
                    rs.getString("Worker_Name"),
                    rs.getString("Skill_Type"),
                    rs.getDouble("Daily_Wage"),
                    rs.getString("Field_ID"),
                    rs.getString("Location"),
                    rs.getString("Start_Date"),
                    end);
            }
            System.out.println("-".repeat(100));
            System.out.println("Total rows: " + count);
        } catch (SQLException e) {
            System.err.println("[ERR] reportWorkersByLandowner: " + e.getMessage());
        }
    }

    // ── REPORT 2: Payment summary per worker ─────────────────────────────────
    /**
     * JOIN: WORKER → PAYMENT with GROUP BY and SUM aggregate.
     */
    public void reportPaymentSummary() {
        String sql =
            "SELECT w.Worker_ID, w.Name, lo.Name AS Landowner, " +
            "       COUNT(p.Payment_ID) AS Num_Payments, " +
            "       SUM(p.Amount)       AS Total_Paid, " +
            "       MAX(p.Payment_Date) AS Last_Payment " +
            "FROM WORKER w " +
            "JOIN LANDOWNER lo ON lo.Landowner_ID = w.Landowner_ID " +
            "LEFT JOIN PAYMENT p ON p.Worker_ID = w.Worker_ID " +
            "GROUP BY w.Worker_ID, w.Name, lo.Name " +
            "ORDER BY Total_Paid DESC";

        System.out.println("\n=================== Payment Summary per Worker ===================");
        System.out.printf("%-8s %-20s %-20s %-8s %-14s %-12s%n",
            "Wkr_ID","Worker Name","Landowner","# Pays","Total Paid(Rs)","Last Date");
        System.out.println("-".repeat(88));

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("%-8s %-20s %-20s %-8d %-14.2f %-12s%n",
                    rs.getString("Worker_ID"),
                    rs.getString("Name"),
                    rs.getString("Landowner"),
                    rs.getInt("Num_Payments"),
                    rs.getDouble("Total_Paid"),
                    rs.getString("Last_Payment") != null ? rs.getString("Last_Payment") : "None");
            }
        } catch (SQLException e) {
            System.err.println("[ERR] reportPaymentSummary: " + e.getMessage());
        }
        System.out.println("-".repeat(88));
    }

    // ── REPORT 3: Crop yield report per Landowner ────────────────────────────
    /**
     * JOIN: LANDOWNER → FIELD → CROP — shows harvest data.
     */
    public void reportCropYields() {
        String sql =
            "SELECT lo.Name AS Landowner, f.Field_ID, f.Location, " +
            "       c.Crop_Name, c.Season_Type, c.Planting_Date, " +
            "       c.Harvest_Date, c.Yield_Kg " +
            "FROM LANDOWNER lo " +
            "JOIN FIELD f ON f.Landowner_ID = lo.Landowner_ID " +
            "JOIN CROP  c ON c.Field_ID     = f.Field_ID " +
            "ORDER BY lo.Name, f.Field_ID, c.Planting_Date";

        System.out.println("\n=================== Crop Yield Report ===================");
        System.out.printf("%-20s %-8s %-15s %-10s %-7s %-12s %-12s %-10s%n",
            "Landowner","Field","Location","Crop","Season","Planted","Harvested","Yield(Kg)");
        System.out.println("-".repeat(100));

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String harvest = rs.getString("Harvest_Date") != null
                    ? rs.getString("Harvest_Date") : "Pending";
                double yield = rs.getDouble("Yield_Kg");
                String yieldStr = (yield == 0) ? "Pending" : String.format("%.1f", yield);
                System.out.printf("%-20s %-8s %-15s %-10s %-7s %-12s %-12s %-10s%n",
                    rs.getString("Landowner"),
                    rs.getString("Field_ID"),
                    rs.getString("Location"),
                    rs.getString("Crop_Name"),
                    rs.getString("Season_Type"),
                    rs.getString("Planting_Date"),
                    harvest, yieldStr);
            }
        } catch (SQLException e) {
            System.err.println("[ERR] reportCropYields: " + e.getMessage());
        }
        System.out.println("-".repeat(100));
    }

    // ── REPORT 4: Fields with worker count ───────────────────────────────────
    public void reportFieldWorkerCount() {
        String sql =
            "SELECT lo.Name AS Landowner, f.Field_ID, f.Location, f.Area_Acres, " +
            "       COUNT(a.Assignment_ID) AS Workers_Assigned " +
            "FROM LANDOWNER lo " +
            "JOIN FIELD f          ON f.Landowner_ID = lo.Landowner_ID " +
            "LEFT JOIN ASSIGNMENT a ON a.Field_ID = f.Field_ID " +
            "GROUP BY lo.Name, f.Field_ID, f.Location, f.Area_Acres " +
            "ORDER BY Workers_Assigned DESC";

        System.out.println("\n========= Field Summary (with worker count) =========");
        System.out.printf("%-20s %-8s %-15s %-10s %-15s%n",
            "Landowner","Field_ID","Location","Acres","Workers_Assigned");
        System.out.println("-".repeat(74));

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("%-20s %-8s %-15s %-10.2f %-15d%n",
                    rs.getString("Landowner"),
                    rs.getString("Field_ID"),
                    rs.getString("Location"),
                    rs.getDouble("Area_Acres"),
                    rs.getInt("Workers_Assigned"));
            }
        } catch (SQLException e) {
            System.err.println("[ERR] reportFieldWorkerCount: " + e.getMessage());
        }
        System.out.println("-".repeat(74));
    }
}
