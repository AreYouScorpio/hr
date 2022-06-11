package hu.webuni.hr.akostomschweger.mapper;

import hu.webuni.hr.akostomschweger.dto.CompanyDto;
import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.model.Company;
import hu.webuni.hr.akostomschweger.model.Employee;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyDto companyToDto(Company company);
    List<CompanyDto> companiesToDtos(List<Company> companies);

    Company dtoToCompany(CompanyDto companyDto);

    // tanari video kod:
    //@Mapping(target = "entryDate", source = "dateOfStartWork");
    //@Mapping(target = "id", source = "employeeId");
    //@Mapping(target = "title", source = "jobTitle");

    // tanari github kod:
    //@Mapping(target = "id", source = "employeeId")
    //@Mapping(target = "title", source = "position.name")
    //@Mapping(target = "entryDate", source = "dateOfStartWork")
    //@Mapping(target = "company", ignore = true)

    //@Mapping(target = "entryDate", source = "dateOfStartWork");
    //@Mapping(target = "id", source = "employeeId");
    //@Mapping(target = "title", source = "jobTitle");
    EmployeeDto employeeToDto(Employee employee);
    @InheritInverseConfiguration
    Employee dtoToEmployee(EmployeeDto employeeDto);

    List<Employee> dtosToEmployees(List<EmployeeDto> employees);


    @Named("summary")
    @Mapping(target = "employeeDtoList", ignore = true)

    CompanyDto companyToDtoWithNoEmployees(Company company);

    @IterableMapping(qualifiedByName = "summary")
    List<CompanyDto> companiesToDtosWithNoEmployees(List<Company> companies);

}