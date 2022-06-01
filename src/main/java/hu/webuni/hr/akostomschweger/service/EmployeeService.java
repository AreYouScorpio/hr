package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.model.Employee;

import java.util.List;


public interface EmployeeService {

    int getPayRaisePercent(Employee employee);

    public Employee save(Employee employee);

    public List<Employee> findAll();

    public Employee findById(long id);

    // TBD public Employee update(Employee employee);

    // TBD public void delete(long id);


}
