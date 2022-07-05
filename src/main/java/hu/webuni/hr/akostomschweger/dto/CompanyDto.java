package hu.webuni.hr.akostomschweger.dto;

import java.util.ArrayList;
import java.util.List;

public class CompanyDto {

    private Long id;
    private String regNo;
    private String name;
    private String address;

    private List<EmployeeDto> employees = new ArrayList<>();


    public CompanyDto() {
    }

    public CompanyDto(Long id, String regNo, String name, String address, List<EmployeeDto> employees) {
        this.id = id;
        this.regNo = regNo;
        this.name = name;
        this.address = address;
        this.employees = employees;
    }

    public CompanyDto(String regNo, String name, String address, List<EmployeeDto> employees) {
        this.regNo = regNo;
        this.name = name;
        this.address = address;
        this.employees = employees;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }

    public void addNewEmployee(EmployeeDto employeeDto) {
        employees.add(employeeDto);
    }

    @Override
    public String toString() {
        return "CompanyDto{" +
                "id=" + id +
                ", regNo='" + regNo + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", employees=" + employees +
                '}';
    }
}
