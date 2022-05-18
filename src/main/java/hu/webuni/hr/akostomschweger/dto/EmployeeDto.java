package hu.webuni.hr.akostomschweger.dto;

import java.time.LocalDateTime;

public class EmployeeDto {

    private Long id;
    private String name;
    private String position;
    private int salary;
    private LocalDateTime startDateAtTheCompany;


    public EmployeeDto(Long id, String name, String position, int salary, LocalDateTime startDateAtTheCompany) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.startDateAtTheCompany = startDateAtTheCompany;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDateTime getStartDateAtTheCompany() {
        return startDateAtTheCompany;
    }

    public void setStartDateAtTheCompany(LocalDateTime startDateAtTheCompany) {
        this.startDateAtTheCompany = startDateAtTheCompany;
    }
}
