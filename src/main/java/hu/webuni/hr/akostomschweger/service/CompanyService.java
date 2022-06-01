package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.dto.CompanyDto;
import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.model.Company;
import hu.webuni.hr.akostomschweger.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyService {


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
    public Company save(Company company) {
        companies.put(company.getId(), company);
        return company;
    }

    public List<Company> findAll() {
        return new ArrayList<>(companies.values());
    }

    public Company findById(long id) {
        return companies.get(id);
    }

    public void delete(long id) {
        companies.remove(id);
    }


}
