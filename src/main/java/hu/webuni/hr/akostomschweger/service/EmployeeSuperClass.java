package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.model.Employee;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public abstract class EmployeeSuperClass implements EmployeeService{

    private Map<Long, Employee> employees = new HashMap<>();

    {
        employees.put(1L, new Employee(
                1L,
                "Akos",
                "junior java developer",
                100000,
                LocalDateTime.of(2011, 1, 11, 11, 11))

        );
        employees.put(2L, new Employee(
                2L,
                "Bkos",
                "senior java developer",
                200000,
                LocalDateTime.of(2012, 2, 22, 22, 22))

        );
    }

    public Employee save(Employee employee) {
        employees.put(employee.getId(), employee);
        return employee;
    }

    public List<Employee> findAll() {
        return new ArrayList<>(employees.values());
    }

    public Employee findById(long id) {
        return employees.get(id);
    }

    public void delete(long id) {
        employees.remove(id);
    }


}
