package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.config.AtConfigurationProperties;
import hu.webuni.hr.akostomschweger.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
public class DefaultEmployeeService extends EmployeeSuperClass {

    @Autowired
    AtConfigurationProperties config;

    @Override
    public int getPayRaisePercent(Employee employee) {
        return config.getSalary().getDef().getPercent();
    }

}
