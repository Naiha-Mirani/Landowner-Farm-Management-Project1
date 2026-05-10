import dao.*;
import database.DBConnection;
import models.*;

import java.util.List;
import java.util.Scanner;

public class Mainnn {

    // ── DAO objects (one per table) ─────────────────────────────────────────
    static LandownerDAO landownerDAO = new LandownerDAO();
    static WorkerDAO    workerDAO    = new WorkerDAO();
    static FieldDAO     fieldDAO     = new FieldDAO();
    static PaymentDAO   paymentDAO   = new PaymentDAO();
    static ReportDAO    reportDAO    = new ReportDAO();
    static Scanner      sc           = new Scanner(System.in);

    // ════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║        LANDOWNER FARM MANAGEMENT SYSTEM              ║");
        System.out.println("║                                                      ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1  -> landownerMenu();
                case 2  -> workerMenu();
                case 3  -> fieldMenu();
                case 4  -> paymentMenu();
                case 5  -> reportsMenu();
                case 0  -> running = false;
                default -> System.out.println("[!] Invalid choice. Try again.");
            }
        }

        DBConnection.closeConnection();
        System.out.println("\nGoodbye! System closed.");
    }

    // ════════════════════════════════════════════════════════════════════════
    // ── MAIN MENU
    // ════════════════════════════════════════════════════════════════════════
    static void printMainMenu() {
        System.out.println("\n┌──────────────────────────────────┐");
        System.out.println("│          MAIN MENU               │");
        System.out.println("├──────────────────────────────────┤");
        System.out.println("│  1. Landowner Manager            │");
        System.out.println("│  2. Worker Manager               │");
        System.out.println("│  3. Field Manager                │");
        System.out.println("│  4. Payment Manager              │");
        System.out.println("│  5. Reports & Analytics          │");
        System.out.println("│  0. Exit                         │");
        System.out.println("└──────────────────────────────────┘");
    }

    // ════════════════════════════════════════════════════════════════════════
    // ── LANDOWNER MENU
    // ════════════════════════════════════════════════════════════════════════
    static void landownerMenu() {
        System.out.println("\n── Landowner Manager ──────────────────");
        System.out.println("  1. View all Landowners");
        System.out.println("  2. View Landowner by ID");
        System.out.println("  3. Add new Landowner");
        System.out.println("  4. Update Landowner");
        System.out.println("  5. Delete Landowner");
        System.out.println("  0. Back");

        int ch = readInt("Choice: ");
        switch (ch) {
            case 1 -> {
                List<Landowner> all = landownerDAO.getAllLandowners();
                System.out.println("\n── All Landowners (" + all.size() + ") ──");
                all.forEach(System.out::println);
            }
            case 2 -> {
                String id = readStr("Enter Landowner ID (e.g. LO-001): ");
                Landowner lo = landownerDAO.getLandownerById(id);
                System.out.println(lo != null ? "\n" + lo.getDetails() : "[!] Not found: " + id);
            }
            case 3 -> {
                System.out.println("\n── Add New Landowner ──");
                String id  = readStr("Landowner ID  : ");
                String nm  = readStr("Name          : ");
                String cn  = readStr("CNIC          : ");
                String ph  = readStr("Phone         : ");
                String vl  = readStr("Village       : ");
                String dt  = readStr("District      : ");
                Landowner lo = new Landowner(id, nm, cn, ph, vl, dt);
                landownerDAO.addLandowner(lo);
            }
            case 4 -> {
                String id = readStr("Landowner ID to update: ");
                Landowner lo = landownerDAO.getLandownerById(id);
                if (lo == null) { System.out.println("[!] Not found."); break; }
                System.out.println("Current: " + lo.getDetails());
                String nm = readStr("New Name (Enter to keep '" + lo.getName() + "'): ");
                String ph = readStr("New Phone: ");
                String vl = readStr("New Village: ");
                String dt = readStr("New District: ");
                if (!nm.isBlank()) lo.setName(nm);
                if (!ph.isBlank()) lo.setPhone(ph);
                if (!vl.isBlank()) lo.setVillage(vl);
                if (!dt.isBlank()) lo.setDistrict(dt);
                landownerDAO.updateLandowner(lo);
            }
            case 5 -> {
                String id = readStr("Landowner ID to delete: ");
                System.out.print("Confirm delete " + id + "? (yes/no): ");
                if (sc.nextLine().equalsIgnoreCase("yes"))
                    landownerDAO.deleteLandowner(id);
            }
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // ── WORKER MENU
    // ════════════════════════════════════════════════════════════════════════
    static void workerMenu() {
        System.out.println("\n── Worker Manager ──────────────────");
        System.out.println("  1. View all Workers");
        System.out.println("  2. View Workers by Landowner");
        System.out.println("  3. View Worker by ID");
        System.out.println("  4. Add new Worker");
        System.out.println("  5. Update Worker");
        System.out.println("  6. Delete Worker");
        System.out.println("  0. Back");

        int ch = readInt("Choice: ");
        switch (ch) {
            case 1 -> {
                List<Worker> all = workerDAO.getAllWorkers();
                System.out.println("\n── All Workers (" + all.size() + ") ──");
                all.forEach(System.out::println);
            }
            case 2 -> {
                String id = readStr("Landowner ID (e.g. LO-001): ");
                List<Worker> list = workerDAO.getWorkersByLandowner(id);
                System.out.println("\n── Workers under " + id + " (" + list.size() + ") ──");
                list.forEach(System.out::println);
            }
            case 3 -> {
                String id = readStr("Worker ID (e.g. W-001): ");
                Worker w = workerDAO.getWorkerById(id);
                System.out.println(w != null ? "\n" + w.getDetails() : "[!] Not found.");
            }
            case 4 -> {
                System.out.println("\n── Add New Worker ──");
                String wid  = readStr("Worker ID      : ");
                String lo   = readStr("Landowner ID   : ");
                String nm   = readStr("Name           : ");
                String cn   = readStr("CNIC           : ");
                String sk   = readStr("Skill Type     : ");
                double wage = readDbl("Daily Wage (Rs): ");
                String dt   = readStr("Hire Date (YYYY-MM-DD): ");
                Worker w = new Worker(wid, lo, nm, cn, sk, wage, dt);
                workerDAO.addWorker(w);
            }
            case 5 -> {
                String id = readStr("Worker ID to update: ");
                Worker w = workerDAO.getWorkerById(id);
                if (w == null) { System.out.println("[!] Not found."); break; }
                System.out.println("Current: " + w.getDetails());
                String sk  = readStr("New Skill Type (or Enter to keep): ");
                String wg  = readStr("New Daily Wage (or Enter to keep): ");
                if (!sk.isBlank()) w.setSkillType(sk);
                if (!wg.isBlank()) w.setDailyWage(Double.parseDouble(wg));
                workerDAO.updateWorker(w);
            }
            case 6 -> {
                String id = readStr("Worker ID to delete: ");
                System.out.print("Confirm delete " + id + "? (yes/no): ");
                if (sc.nextLine().equalsIgnoreCase("yes"))
                    workerDAO.deleteWorker(id);
            }
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // ── FIELD MENU
    // ════════════════════════════════════════════════════════════════════════
    static void fieldMenu() {
        System.out.println("\n── Field Manager ──────────────────");
        System.out.println("  1. View all Fields");
        System.out.println("  2. View Fields by Landowner");
        System.out.println("  3. Add new Field");
        System.out.println("  4. Update Field");
        System.out.println("  5. Delete Field");
        System.out.println("  0. Back");

        int ch = readInt("Choice: ");
        switch (ch) {
            case 1 -> {
                List<Field> all = fieldDAO.getAllFields();
                System.out.println("\n── All Fields (" + all.size() + ") ──");
                all.forEach(System.out::println);
            }
            case 2 -> {
                String id = readStr("Landowner ID: ");
                List<Field> list = fieldDAO.getFieldsByLandowner(id);
                list.forEach(System.out::println);
            }
            case 3 -> {
                String fid  = readStr("Field ID       : ");
                String lo   = readStr("Landowner ID   : ");
                String loc  = readStr("Location       : ");
                double area = readDbl("Area (Acres)   : ");
                String soil = readStr("Soil Type      : ");
                fieldDAO.addField(new Field(fid, lo, loc, area, soil));
            }
            case 4 -> {
                String id = readStr("Field ID to update: ");
                Field f = fieldDAO.getFieldById(id);
                if (f == null) { System.out.println("[!] Not found."); break; }
                System.out.println("Current: " + f);
                String loc  = readStr("New Location (or Enter): ");
                String area = readStr("New Area Acres (or Enter): ");
                String soil = readStr("New Soil Type (or Enter): ");
                if (!loc.isBlank())  f.setLocation(loc);
                if (!area.isBlank()) f.setAreaAcres(Double.parseDouble(area));
                if (!soil.isBlank()) f.setSoilType(soil);
                fieldDAO.updateField(f);
            }
            case 5 -> {
                String id = readStr("Field ID to delete: ");
                System.out.print("Confirm? (yes/no): ");
                if (sc.nextLine().equalsIgnoreCase("yes"))
                    fieldDAO.deleteField(id);
            }
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // ── PAYMENT MENU
    // ════════════════════════════════════════════════════════════════════════
    static void paymentMenu() {
        System.out.println("\n── Payment Manager ──────────────────");
        System.out.println("  1. View all Payments");
        System.out.println("  2. View Payments for a Worker");
        System.out.println("  3. Total paid to a Worker");
        System.out.println("  4. Add new Payment");
        System.out.println("  5. Delete Payment");
        System.out.println("  0. Back");

        int ch = readInt("Choice: ");
        switch (ch) {
            case 1 -> {
                List<Payment> all = paymentDAO.getAllPayments();
                System.out.println("\n── All Payments (" + all.size() + ") ──");
                all.forEach(System.out::println);
            }
            case 2 -> {
                String wid = readStr("Worker ID: ");
                List<Payment> list = paymentDAO.getPaymentsByWorker(wid);
                System.out.println("\n── Payments for " + wid + " ──");
                list.forEach(System.out::println);
            }
            case 3 -> {
                String wid = readStr("Worker ID: ");
                double total = paymentDAO.getTotalPaidToWorker(wid);
                System.out.printf("\n Total paid to %s = Rs %.2f%n", wid, total);
            }
            case 4 -> {
                String pid  = readStr("Payment ID     : ");
                String wid  = readStr("Worker ID      : ");
                double amt  = readDbl("Amount (Rs)    : ");
                String type = readStr("Type (Advance/Monthly/Bonus): ");
                String date = readStr("Date (YYYY-MM-DD): ");
                String note = readStr("Notes (optional) : ");
                paymentDAO.addPayment(new Payment(pid, wid, amt, type, date,
                                                  note.isBlank() ? null : note));
            }
            case 5 -> {
                String pid = readStr("Payment ID to delete: ");
                System.out.print("Confirm? (yes/no): ");
                if (sc.nextLine().equalsIgnoreCase("yes"))
                    paymentDAO.deletePayment(pid);
            }
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // ── REPORTS MENU
    // ════════════════════════════════════════════════════════════════════════
    static void reportsMenu() {
        System.out.println("\n── Reports & Analytics ──────────────────");
        System.out.println("  1. Workers assigned to a Landowner's fields");
        System.out.println("  2. Payment summary per Worker");
        System.out.println("  3. Crop yield report");
        System.out.println("  4. Fields with worker count");
        System.out.println("  0. Back");

        int ch = readInt("Choice: ");
        switch (ch) {
            case 1 -> {
                String id = readStr("Landowner ID: ");
                reportDAO.reportWorkersByLandowner(id);
            }
            case 2 -> reportDAO.reportPaymentSummary();
            case 3 -> reportDAO.reportCropYields();
            case 4 -> reportDAO.reportFieldWorkerCount();
        }
    }
    // ════════════════════════════════════════════════════════════════════════
    // ── INPUT HELPERS
    // ════════════════════════════════════════════════════════════════════════
    static String readStr(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    static int readInt(String prompt) {
        System.out.print(prompt);
        try {
            int v = Integer.parseInt(sc.nextLine().trim());
            return v;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static double readDbl(String prompt) {
        System.out.print(prompt);
        try {
            return Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
