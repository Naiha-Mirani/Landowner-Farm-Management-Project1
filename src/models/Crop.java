package models;

public class Crop {

    private String cropId;
    private String fieldId;       // FK
    private String cropName;
    private String seasonType;    // Rabi or Kharif
    private String plantingDate;
    private String harvestDate;   // can be null (not harvested yet)
    private double yieldKg;

    public Crop(String cropId, String fieldId, String cropName,
                String seasonType, String plantingDate,
                String harvestDate, double yieldKg) {
        this.cropId      = cropId;
        this.fieldId     = fieldId;
        this.cropName    = cropName;
        this.seasonType  = seasonType;
        this.plantingDate= plantingDate;
        this.harvestDate = harvestDate;
        this.yieldKg     = yieldKg;
    }

    public String getCropId()                   { return cropId; }
    public String getFieldId()                  { return fieldId; }
    public String getCropName()                 { return cropName; }
    public void   setCropName(String cropName)  { this.cropName = cropName; }
    public String getSeasonType()               { return seasonType; }
    public String getPlantingDate()             { return plantingDate; }
    public String getHarvestDate()              { return harvestDate; }
    public void   setHarvestDate(String date)   { this.harvestDate = date; }
    public double getYieldKg()                  { return yieldKg; }
    public void   setYieldKg(double yieldKg)    { this.yieldKg = yieldKg; }

    @Override
    public String toString() {
        String harvest = (harvestDate != null) ? harvestDate : "Not harvested";
        return String.format(
            "Crop   [ID=%-6s | Field=%-6s | Crop=%-10s | Season=%-6s | Planted=%s | Harvest=%s | Yield=%.1f kg]",
            cropId, fieldId, cropName, seasonType, plantingDate, harvest, yieldKg
        );
    }
}
