package hu.webuni.hr.akostomschweger.web;

import hu.webuni.hr.akostomschweger.dto.CompanyDto;
import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.mapper.CompanyMapper;
import hu.webuni.hr.akostomschweger.mapper.EmployeeMapper;
import hu.webuni.hr.akostomschweger.model.AverageSalaryByPosition;
import hu.webuni.hr.akostomschweger.model.Company;
import hu.webuni.hr.akostomschweger.repository.CompanyRepository;
import hu.webuni.hr.akostomschweger.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {


    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyMapper companyMapper;


    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    CompanyRepository companyRepository;

    private CompanyDto mapCompanyWithoutEmployees(CompanyDto c) {
        return new CompanyDto(c.getId(), c.getRegNo(), c.getName(), c.getAddress(), null);
    }




 /*

    ha a full boolean-os getAll megy, akk ez nyilvan kommentben legyen
    @GetMapping
    public List<CompanyDto> getAll(){
        return new ArrayList<>(companies.values());
    }


     */

    /*
    public List<CompanyDto> getAll(){
        return companyMapper
                .companiesToDtos(companyService.findAll());
    }


     */

    /*
    @GetMapping
    public List<CompanyDto> getAllFull(@RequestParam(required = false) Boolean full) {
        if (full != null && full)
            return companyMapper.companiesToDtos(companyService.findAll());
        else
            return companyMapper.companiesToDtos(companyService.findAll())
                    .stream()
                    .map(c -> new CompanyDto(c.getId(), c.getRegNo(), c.getName(), c.getAddress(), null))
                    .collect(Collectors.toList());
    }
*/


    @GetMapping
    public List<CompanyDto> getAllFull(@RequestParam(required = false) Boolean full) {
        List<Company> companies = companyService.findAll();
        //System.out.println(companies.stream().findFirst().get().getEmployees().get(0).getName());
        //System.out.println(companyMapper.companiesToDtos(companies).get(0).getEmployees().isEmpty());
        //System.out.println("CCCCC" + companyMapper.companiesToDtos(companies).get(0).getRegNo());

        //return companies;

        if (full != null && full)
            return companyMapper.companiesToDtos(companies);
        else
            //return companyMapper.companiesToDtos(companies);
            return companyMapper.companiesToDtosWithNoEmployees(companies);
            /*
            return companyMapper.companiesToDtos(companyService.findAll())
                    .stream()
                    .map(c -> new CompanyDto(c.getId(), c.getRegNo(), c.getName(), c.getAddress(), null))
                    .collect(Collectors.toList());


             */
    }

/*
    @GetMapping("/{id}")
    public CompanyDto getById(@PathVariable long id) {
        Company company = companyService.findById(id).get();
        if (company != null)
            return companyMapper.companyToDto(company);
        else
            // return ResponseEntity.notFound().build();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    */

    private List<CompanyDto> mapCompanies(List<Company> companies, Boolean full) {
        if (full != null && full)
            return companyMapper.companiesToDtos(companies);
        else
            return companyMapper.companiesToDtosWithNoEmployees(companies);
    }

    @GetMapping("/{id}")
    public CompanyDto getById(@PathVariable long id, @RequestParam(required = false) Boolean full) {
        Company company = companyService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (full != null && full)
            return companyMapper.companyToDto(company);
        else
            // return ResponseEntity.notFound().build();
            //throw new ResponseStatusException(HttpStatus.NOT_FOUND);


            return companyMapper.companyToDtoWithNoEmployees(company);
    }



/*
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getById(@PathVariable long id) {
        CompanyDto companyDto = companies.get(id);
        if (companyDto != null)
            return ResponseEntity.ok(companyDto);
        else
            return ResponseEntity.notFound().build();
    }
    */

    @PostMapping
    public CompanyDto createCompany(@RequestBody CompanyDto companyDto) {
        //Company company = companyService.save(companyMapper.dtoToCompany(companyDto));
        // companies.put(companyDto.getId(), companyDto);
        // return companyDto;
        return companyMapper.companyToDto(
                companyService.save(
                        companyMapper.dtoToCompany(companyDto)));
    }

/* old putmapping before mapstruct

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> modifyCompany(@PathVariable long id,
                                                    @RequestBody CompanyDto companyDto) {
        if (!companies.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        companyDto.setId(id);
        companies.put(id, companyDto);
        return ResponseEntity.ok(companyDto);
    }

    */

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> modifyCompany(@PathVariable long id,
                                                    @RequestBody CompanyDto companyDto) {

        companyDto.setId(id);
        Company updatedCompany = companyService.update(companyMapper.dtoToCompany(companyDto));


        if (updatedCompany == null) {
            return ResponseEntity.notFound().build();
        }

        //    companyService.update(id, companyMapper.dtoToCompany(companyDto));
        // else throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(companyMapper.companyToDto(updatedCompany));
//        return companyMapper.companyToDto(company);
    }



    /*

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable long id) {
        companies.remove(id);
    }


     */

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable long id) {
        companyService.delete(id);
    }

    /*
        @PostMapping("/addemployeetocompany/{company_id}")
        public CompanyDto addEmployeeToCompany(@PathVariable long company_id,
                                               @RequestBody EmployeeDto employeeDto) {
            if (!companies.containsKey(company_id))
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            CompanyDto company = companies.get(company_id);
            company.getEmployeeDtoList().add(employeeDto);
            return company;
        }

    new version after MapStruct --->
     */
    @PostMapping("/addemployeetocompany/{company_id}")
    public CompanyDto addEmployeeToCompany(@PathVariable long company_id,
                                           @RequestBody EmployeeDto employeeDto) {

        //  Company company = companyService.findById(company_id);

        // if (company != null)
        return companyMapper.companyToDto(
                companyService.addNewEmployee(
                        company_id, companyMapper.dtoToEmployee(employeeDto)));
        //?? employeeMapper.dtoToEmployee(employeeDto)));
        //?? companyMapper.dtoToEmployee(employeeDto)));
        //else
        //  throw new ResponseStatusException(HttpStatus.NOT_FOUND);


        // return companyMapper.companyToDto(company);


    }


    @DeleteMapping("/{id}/employees/{employeeId}")
    public CompanyDto deleteEmployeeFromCompany(@PathVariable long id, @PathVariable long employeeId) {

        /*
        Company company = companyService.findById(id);

        if (company != null)
            companyService.deleteEmployeeFromCompany(id, employeeId);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // company.getEmployeeDtoList().removeIf(e -> e.getId() == employeeId);


         */
        return companyMapper.companyToDto(
                companyService.deleteEmployeeFromCompany(id, employeeId));


    }

    /* before MapStruct:
        @DeleteMapping("/{id}/employees/{employeeId}")
    public CompanyDto deleteEmployeeFromCompany(@PathVariable long id, @PathVariable long employeeId) {
        if (!companies.containsKey(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        CompanyDto company = companies.get(id);
        company.getEmployeeDtoList().removeIf(e -> e.getId() == employeeId);
        return company;
    }
     */


    @PutMapping("/{id}/employees")
    public CompanyDto replaceEmployees(@PathVariable long id,
                                       @RequestBody List<EmployeeDto> employees) {


        return companyMapper.companyToDto(
                companyService.replaceEmployees(
                        id, companyMapper.dtosToEmployees(employees)));

        /*
        if (company != null)
            companyService.modifyCompany(id, employees);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return companyMapper.companyToDto(company);
         */

    }

    @GetMapping(params = "aboveSalary")
    public List<CompanyDto> getCompaniesAboveSalary(@RequestParam int aboveSalary,
                                                    @RequestParam(required = false)
                                                            Boolean full) {
        List<Company> filteredCompanies = companyRepository.findByEmployeeWithSalaryHigherThan(aboveSalary);
        return mapCompanies(filteredCompanies, full);
    }

    @GetMapping(params = "aboveEmployeeNumber")
    public List<CompanyDto> getCompaniesAboveEmployeeNumber(@RequestParam int aboveEmployeeNumber,
                                                            @RequestParam(required = false)
                                                                    Boolean full) {
        List<Company> filteredCompanies = companyRepository.findByEmployeeCountHigherThan(aboveEmployeeNumber);
        return mapCompanies(filteredCompanies, full);
    }

    @GetMapping("/{id}/salaryStats")
    public List<AverageSalaryByPosition> getSalaryStatsById(@PathVariable long id,
                                                            @RequestParam(required = false)
                                                                    Boolean full) {
        return companyRepository.findAverageSalaryByPosition(id);
    }


}



    /* old version employee list change
        @PutMapping("/{id}/employees")
    public ResponseEntity<CompanyDto> modifyCompany(@PathVariable long id,
                                                    @RequestBody List<EmployeeDto> employees) {
        if (!companies.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        CompanyDto company = companies.get(id);
        company.setEmployeeDtoList(employees);
        return ResponseEntity.ok(company);
    }


     */





/*
   TeacherSolutions (https://github.com/imre-gabor/webuni-spring)
   from here ---->


   kiszervezni cleancode szerint jobb

   private Employee findByIdOrThrow(long id) {
		return employeeService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}



	//1. megoldás

	*/










/*


	2. megoldás
	@JsonView(View.BaseData.class)  !!!! atnezni, videon !!!!

*/






