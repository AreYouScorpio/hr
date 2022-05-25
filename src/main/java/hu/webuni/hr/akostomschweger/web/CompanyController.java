package hu.webuni.hr.akostomschweger.web;

import hu.webuni.hr.akostomschweger.dto.CompanyDto;
import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private Map<Long, CompanyDto> companies = new HashMap<>();

    {
        companies.put(1L, new CompanyDto(
                1L,
                "11111",
                "A company",
                "Budapest",
                new ArrayList<EmployeeDto>()

        ));
        companies.put(2L, new CompanyDto(
                2L,
                "22222",
                "B company",
                "Amsterdam",
                new ArrayList<EmployeeDto>()

        ));    }

    @GetMapping
    public List<CompanyDto> getAll(){
        return new ArrayList<>(companies.values());
    }


    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getById(@PathVariable long id){
        CompanyDto companyDto = companies.get(id);
        if (companyDto!=null)
            return ResponseEntity.ok(companyDto);
        else
            return ResponseEntity.notFound().build();
    }




    @PostMapping
    public CompanyDto createCompany(@RequestBody CompanyDto companyDto) {
        companies.put(companyDto.getId(), companyDto);
        return companyDto;
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> modifyCompany(@PathVariable long id,
                                                    @RequestBody CompanyDto companyDto) {
        if(!companies.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        companyDto.setId(id);
        companies.put(id, companyDto);
        return ResponseEntity.ok(companyDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable long id){
        companies.remove(id);
    }


    @PostMapping("/addemployeetocompany/{company_id}")
    public CompanyDto addEmployeeToCompany(@PathVariable long company_id , @RequestBody EmployeeDto employeeDto) {
        if(!companies.containsKey(company_id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        CompanyDto company=companies.get(company_id);
        company.getEmployeeDtoList().add(employeeDto);
        return company;
    }


}



