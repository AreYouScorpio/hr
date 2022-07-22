package hu.webuni.hr.akostomschweger.dto;

import hu.webuni.hr.akostomschweger.model.Employee;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

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

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public EmployeeDto(String name, String position, int salary, LocalDateTime startDateAtTheCompany) {
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.startDateAtTheCompany = startDateAtTheCompany;
    }

    public EmployeeDto(String name, int salary, LocalDateTime startDateAtTheCompany) {
        this.name = name;
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

    /*

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", startDateAtTheCompany=" + startDateAtTheCompany +
                '}';
    }

     */

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", startDateAtTheCompany=" + startDateAtTheCompany +
                ", company=" + company +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
