package hu.webuni.hr.akostomschweger.model;

import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Company {

    @Id
    @GeneratedValue
    private Long id;
    private String regNo;
    private String name;
    private String address;
    @OneToMany(mappedBy = "company")
    private List<Employee> employees = new ArrayList<>();
    @ManyToOne
    private CompanyType companyType;




    public Company() {
    }

    public Company(Long id, String regNo, String name, String address, List<Employee> employees) {
        this.id = id;
        this.regNo = regNo;
        this.name = name;
        this.address = address;
        this.employees = employees;
    }



    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
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

//    public List<EmployeeDto> getEmployeeDtoList() {
    //      return employeeDtoList;
    // }

    //  public void setEmployeeDtoList(List<EmployeeDto> employeeDtoList) {
    //    this.employeeDtoList = employeeDtoList;
    // }




    public void addEmployee(Employee employee) {
        if (this.employees == null)
            this.employees = new ArrayList<>();

        this.employees.add(employee);
        employee.setCompany(this); //másik oldalról is bebiztosítom
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
