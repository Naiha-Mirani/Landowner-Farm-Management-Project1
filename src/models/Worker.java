package models;

public class Worker extends Person {

    private String workerId;
    private String landownerId;   // FK — which Wadero hired this worker
    private String skillType;
    private double dailyWage;
    private String hireDate;

    // ── Constructor ─────────────────────────────────────────────────────────
    public Worker(String workerId, String landownerId, String name,
                  String cnic, String skillType, double dailyWage, String hireDate) {
        super(name, cnic, null);  // workers may not have a phone on record
        this.workerId    = workerId;
        this.landownerId = landownerId;
        this.skillType   = skillType;
        this.dailyWage   = dailyWage;
        this.hireDate    = hireDate;
    }

    // ── Getters & Setters ────────────────────────────────────────────────────
    public String getWorkerId()                  { return workerId; }
    public void   setWorkerId(String workerId)   { this.workerId = workerId; }

    public String getLandownerId()               { return landownerId; }
    public void   setLandownerId(String id)      { this.landownerId = id; }

    public String getSkillType()                 { return skillType; }
    public void   setSkillType(String skillType) { this.skillType = skillType; }

    public double getDailyWage()                 { return dailyWage; }
    public void   setDailyWage(double dailyWage) { this.dailyWage = dailyWage; }

    public String getHireDate()                  { return hireDate; }
    public void   setHireDate(String hireDate)   { this.hireDate = hireDate; }

    /**
     * Override abstract method from Person.
     * OOP Concept: Method Overriding (Polymorphism)
     */
    @Override
    public String getDetails() {
        return String.format(
            "Worker [ID=%-6s | Name=%-20s | Skill=%-12s | Wage=Rs %-8.2f | HiredBy=%s]",
            workerId, getName(), skillType, dailyWage, landownerId
        );
    }
}
