package models;

public class Payment {

    private String paymentId;
    private String workerId;       // FK
    private double amount;
    private String paymentType;    // Advance / Monthly / Bonus
    private String paymentDate;
    private String notes;

    public Payment(String paymentId, String workerId, double amount,
                   String paymentType, String paymentDate, String notes) {
        this.paymentId   = paymentId;
        this.workerId    = workerId;
        this.amount      = amount;
        this.paymentType = paymentType;
        this.paymentDate = paymentDate;
        this.notes       = notes;
    }

    public String getPaymentId()                    { return paymentId; }
    public String getWorkerId()                     { return workerId; }
    public double getAmount()                       { return amount; }
    public void   setAmount(double amount)          { this.amount = amount; }
    public String getPaymentType()                  { return paymentType; }
    public String getPaymentDate()                  { return paymentDate; }
    public String getNotes()                        { return notes; }
    public void   setNotes(String notes)            { this.notes = notes; }

    @Override
    public String toString() {
        return String.format(
            "Payment[ID=%-6s | Worker=%-6s | Amount=Rs %-10.2f | Type=%-8s | Date=%s | Note=%s]",
            paymentId, workerId, amount, paymentType, paymentDate,
            (notes != null ? notes : "-")
        );
    }
}
