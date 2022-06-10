package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.dto.CompanyDto;
import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.model.Company;
import hu.webuni.hr.akostomschweger.model.Employee;
import hu.webuni.hr.akostomschweger.repository.CompanyRepository;
import hu.webuni.hr.akostomschweger.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;
    EmployeeRepository employeeRepository;

    /*

    private Map<Long, Company> companies = new HashMap<>();

    {
        companies.put(1L, new Company(
                1L,
                "11111",
                "A company",
                "Budapest",
                new ArrayList<EmployeeDto>()

        ));
        companies.put(2L, new Company(
                2L,
                "22222",
                "B company",
                "Amsterdam",
                new ArrayList<EmployeeDto>()

        ));
    }


     */

    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public Company save(Company company) {
        // companies.put(company.getId(), company);
        // return company;
        return companyRepository.save(company);
    }

    public List<Company> findAll() {
        // return new ArrayList<>(companies.values());
        return companyRepository.findAll();
    }

    public Optional<Company> findById(long id) {
        // return companies.get(id);
        return companyRepository.findById(id);
    }

    /*
    public Company update(long id, Company company) {
        companies.put(id, company);
        return company;
    }
     */

    @Transactional
    public Company update(Company company) {
        if (companyRepository.existsById(company.getId()))
            return companyRepository.save(company);
        else throw new NoSuchElementException();
    }

    @Transactional
    public void delete(long id) {
        //companies.remove(id);
        companyRepository.deleteById(id);
    }

    public Company addNewEmployee(long company_id, EmployeeDto employee) {

        Company company = findById(company_id).get();
        company.getEmployeeDtoList().add(employee);

        return company;
    }

    public Company deleteEmployeeFromCompany(long company_id, long employee_id) {
        Company company = findById(company_id);
        company.getEmployeeDtoList().removeIf(e -> e.getId() == employee_id);

        return company;
    }

    public Company modifyCompany(long company_id, List<EmployeeDto> employees) {
        Company company = findById(company_id);
        company.setEmployeeDtoList(employees);

        return company;
    }


}
