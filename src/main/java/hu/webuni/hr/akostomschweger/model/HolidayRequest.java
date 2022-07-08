package hu.webuni.hr.akostomschweger.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class HolidayRequest {

    @Id
    @GeneratedValue
    private long id;
    private LocalDateTime createdAt;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Employee approver;

    private Boolean approved;
    private LocalDateTime approvedAt;

    private LocalDate startDate;
    private LocalDate endDate;

    public HolidayRequest() {
    }

    public HolidayRequest(LocalDateTime createdAt, Employee employee, Employee approver, Boolean approved, LocalDateTime approvedAt, LocalDate startDate, LocalDate endDate) {
        this.createdAt = createdAt;
        this.employee = employee;
        this.approver = approver;
        this.approved = approved;
        this.approvedAt = approvedAt;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getApprover() {
        return approver;
    }

    public void setApprover(Employee approver) {
        this.approver = approver;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
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
