package hu.webuni.hr.akostomschweger.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HolidayRequestFilterDto {

    private LocalDateTime createDateTimeStart;
    private LocalDateTime createDateTimeEnd;
    private String employeeName;
    private String approverName;
    private Boolean approved;
    private LocalDate startDate;
    private LocalDate endDate;

    public HolidayRequestFilterDto() {
    }

    public LocalDateTime getCreateDateTimeStart() {
        return createDateTimeStart;
    }

    public void setCreateDateTimeStart(LocalDateTime createDateTimeStart) {
        this.createDateTimeStart = createDateTimeStart;
    }

    public LocalDateTime getCreateDateTimeEnd() {
        return createDateTimeEnd;
    }

    public void setCreateDateTimeEnd(LocalDateTime createDateTimeEnd) {
        this.createDateTimeEnd = createDateTimeEnd;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
