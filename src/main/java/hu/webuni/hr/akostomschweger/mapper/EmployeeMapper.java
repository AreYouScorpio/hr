package hu.webuni.hr.akostomschweger.mapper;

import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.model.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    List<EmployeeDto> employeesToDtos(List<Employee> employees);

    EmployeeDto employeeToDto(Employee employee);

    Employee dtoToEmployee(EmployeeDto employeeDto);
}