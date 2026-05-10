package models;

public class Assignment {

    private String assignmentId;
    private String workerId;    // FK
    private String fieldId;     // FK
    private String startDate;
    private String endDate;     // null = ongoing

    public Assignment(String assignmentId, String workerId,
                      String fieldId, String startDate, String endDate) {
        this.assignmentId = assignmentId;
        this.workerId     = workerId;
        this.fieldId      = fieldId;
        this.startDate    = startDate;
        this.endDate      = endDate;
    }

    public String getAssignmentId()             { return assignmentId; }
    public String getWorkerId()                 { return workerId; }
    public String getFieldId()                  { return fieldId; }
    public String getStartDate()                { return startDate; }
    public String getEndDate()                  { return endDate; }
    public void   setEndDate(String endDate)    { this.endDate = endDate; }

    @Override
    public String toString() {
        String end = (endDate != null) ? endDate : "Ongoing";
        return String.format(
            "Assignment [ID=%-6s | Worker=%-6s | Field=%-6s | From=%s | To=%s]",
            assignmentId, workerId, fieldId, startDate, end
        );
    }
}
