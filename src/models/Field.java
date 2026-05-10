package models;

public class Field {

    private String  fieldId;
    private String  landownerId;   // FK
    private String  location;
    private double  areaAcres;
    private String  soilType;

    // ── Constructor ─────────────────────────────────────────────────────────
    public Field(String fieldId, String landownerId,
                 String location, double areaAcres, String soilType) {
        this.fieldId     = fieldId;
        this.landownerId = landownerId;
        this.location    = location;
        this.areaAcres   = areaAcres;
        this.soilType    = soilType;
    }

    // ── Getters & Setters ────────────────────────────────────────────────────
    public String getFieldId()                   { return fieldId; }
    public void   setFieldId(String fieldId)     { this.fieldId = fieldId; }

    public String getLandownerId()               { return landownerId; }
    public void   setLandownerId(String id)      { this.landownerId = id; }

    public String getLocation()                  { return location; }
    public void   setLocation(String location)   { this.location = location; }

    public double getAreaAcres()                 { return areaAcres; }
    public void   setAreaAcres(double areaAcres) { this.areaAcres = areaAcres; }

    public String getSoilType()                  { return soilType; }
    public void   setSoilType(String soilType)   { this.soilType = soilType; }

    @Override
    public String toString() {
        return String.format(
            "Field  [ID=%-6s | Owner=%-6s | Location=%-15s | Area=%-8.2f acres | Soil=%s]",
            fieldId, landownerId, location, areaAcres, soilType
        );
    }
}
