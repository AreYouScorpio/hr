package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.model.Employee;

import java.util.List;
import java.util.Optional;


public interface EmployeeService {

    int getPayRaisePercent(Employee employee);

    public Employee save(Employee employee);

    public List<Employee> findAll();

    public Optional<Employee> findById(long id);
//    public List<Employee> findByPosition(String position);

    public Employee update(long id, Employee employee);

    public void delete(long id);




}
