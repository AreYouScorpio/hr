package hu.webuni.hr.akostomschweger.mapper;

import hu.webuni.hr.akostomschweger.dto.CompanyDto;
import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.model.Company;
import hu.webuni.hr.akostomschweger.model.Employee;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    List<EmployeeDto> employeesToDtos(List<Employee> employees);

    //@Mapping(target = "id", source = "id");

    // @Mapping(target = "name", source = "jobTitle");
    // --->
    // ehelyett a Position entitással már így nézne ki:
    // @Mapping(target = "name", source = "position.name");

    // @Mapping(target = "startDateAtTheCompany", source = "startDateAtTheCompany");
    // @Mapping(target = "company.employees", ignore = true);


    //@Mapping(target = "id", source = "id")
    @Mapping(target = "position", source = "position.name")
    //@Mapping(target = "startDateAtTheCompany", source = "startDateAtTheCompany")
    //@Mapping(target = "company.employees", ignore = true)
    EmployeeDto employeeToDto(Employee employee);

    @Mapping(target = "employees", ignore = true)
    CompanyDto companyToDto(Company c);

    @InheritInverseConfiguration
    Employee dtoToEmployee(EmployeeDto employeeDto);

    List<Employee> dtosToEmployees(List<EmployeeDto> employeeDtoList);
}