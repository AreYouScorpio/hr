package hu.webuni.hr.akostomschweger.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public class EmployeeDto {

    private Long id;
    // @Size(min=3,max=20)
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String position;
    //@Min (value = 0)
    @Positive
    private int salary;
    @Past
    private LocalDateTime startDateAtTheCompany;

    private CompanyDto company;

    public CompanyDto getCompany() {
        return company;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }

    public EmployeeDto(Long id, String name, String position, int salary, LocalDateTime startDateAtTheCompany) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.startDateAtTheCompany = startDateAtTheCompany;
    }

    public EmployeeDto() {
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
