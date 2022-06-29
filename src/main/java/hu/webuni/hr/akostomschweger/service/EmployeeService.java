package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.model.Employee;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    int getPayRaisePercent(Employee employee);

    public Employee save(Employee employee);

    public List<Employee> findAll();

    public Optional<Employee> findById(long id);
    public List<Employee> findByPosition(String position);
    public List<Employee> findByNameStartingWith(String prefix);
    public List<Employee> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    public Employee update(long id, Employee employee);

    public void delete(long id);

    public List<Employee> findEmployeesByExample(Employee employee);

}
