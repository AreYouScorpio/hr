package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.model.Employee;

import java.util.List;


public interface EmployeeService {

    int getPayRaisePercent(Employee employee);

    public Employee save(Employee employee);

    public List<Employee> findAll();

    public Employee findById(long id);

    public Employee update(long id, Employee employee);

    public void delete(long id);


}
