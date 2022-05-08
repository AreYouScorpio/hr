package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.model.Employee;
import org.springframework.stereotype.Service;

//@Service
public class DefaultEmployeeService implements EmployeeService{

    @Override
    public int getPayRaisePercent(Employee employee) {
        return 5;
    }

}
