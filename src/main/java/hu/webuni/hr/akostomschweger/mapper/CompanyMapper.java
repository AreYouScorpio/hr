package hu.webuni.hr.akostomschweger.mapper;

import hu.webuni.hr.akostomschweger.dto.CompanyDto;
import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.model.Company;
import hu.webuni.hr.akostomschweger.model.Employee;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyDto companyToDto(Company company); //
    List<CompanyDto> companiesToDtos(List<Company> companies); //

    Company dtoToCompany(CompanyDto companyDto);
    List<Company> dtosToCompanies(List<CompanyDto> companyDtoList);

    // tanari video kod:
    //@Mapping(target = "entryDate", source = "dateOfStartWork");
    //@Mapping(target = "id", source = "employeeId");

    //@Mapping(target = "title", source = "jobTitle");
    // ---> position entitás bevezetésével ez is változna:
    //@Mapping(target = "title", source = "position.name");


    // tanari github kod:
    // @Mapping(target = "id", source = "id")
    @Mapping(target = "position", source = "position.name")
    // @Mapping(target = "startDateAtTheCompany", source = "startDateAtTheCompany")
    @Mapping(target = "company", ignore = true)
    EmployeeDto employeeToDto(Employee employee);

    @InheritInverseConfiguration
    Employee dtoToEmployee(EmployeeDto employeeDto);

    List<Employee> dtosToEmployees(List<EmployeeDto> employeeDtoList);


    @Named("summary")
    @Mapping(target = "employees", ignore = true)
    CompanyDto companyToDtoWithNoEmployees(Company company);

    @IterableMapping(qualifiedByName = "summary")
    List<CompanyDto> companiesToDtosWithNoEmployees(List<Company> companies);



}