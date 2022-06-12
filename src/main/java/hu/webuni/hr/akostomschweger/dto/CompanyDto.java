package hu.webuni.hr.akostomschweger.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CompanyDto {

    private Long id;
    private String regNo;
    private String name;
    private String address;

    private List<EmployeeDto> employeeDtoList= new ArrayList<>();


    public CompanyDto() {
    }

    public CompanyDto(Long id, String regNo, String name, String address, List<EmployeeDto> employeeDtoList) {
        this.id = id;
        this.regNo = regNo;
        this.name = name;
        this.address = address;
        this.employeeDtoList = employeeDtoList;
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





    public List<EmployeeDto> getEmployeeDtoList() {
        return employeeDtoList;
    }

    public void setEmployeeDtoList(List<EmployeeDto> employeeDtoList) {
        this.employeeDtoList = employeeDtoList;
    }

    public void addNewEmployee(EmployeeDto employeeDto) {
        employeeDtoList.add(employeeDto);
    }
}
