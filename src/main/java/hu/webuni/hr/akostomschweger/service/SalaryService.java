package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaryService {


    private EmployeeService employeeService;

    public SalaryService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public int getPayRaisePercent(Employee employee) {
        return (int) (employee.getSalary()*(1+employeeService.getPayRaisePercent(employee)/100.00));
    }

}
