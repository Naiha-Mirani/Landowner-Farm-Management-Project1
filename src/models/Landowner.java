package models;

public class Landowner extends Person {

    // ── Own fields (not in Person) ──────────────────────────────────────────
    private String landownerId;
    private String village;
    private String district;

    // ── Constructor ─────────────────────────────────────────────────────────
    public Landowner(String landownerId, String name, String cnic,
                     String phone, String village, String district) {
        super(name, cnic, phone);   // call parent constructor
        this.landownerId = landownerId;
        this.village     = village;
        this.district    = district;
    }

    // ── Getters & Setters ────────────────────────────────────────────────────
    public String getLandownerId()                   { return landownerId; }
    public void   setLandownerId(String landownerId) { this.landownerId = landownerId; }

    public String getVillage()                       { return village;  }
    public void   setVillage(String village)         { this.village = village; }

    public String getDistrict()                      { return district; }
    public void   setDistrict(String district)       { this.district = district; }

    /**
     * Override abstract method from Person.
     * OOP Concept: Method Overriding (Polymorphism)
     */
    @Override
    public String getDetails() {
        return String.format(
            "Landowner[ID=%-6s | Name=%-20s | Village=%-10s | District=%-10s | CNIC=%s]",
            landownerId, getName(), village, district, getCnic()
        );
    }
}
