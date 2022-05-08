package hu.webuni.hr.akostomschweger.config;

import hu.webuni.hr.akostomschweger.service.DefaultEmployeeService;
import hu.webuni.hr.akostomschweger.service.EmployeeService;
import hu.webuni.hr.akostomschweger.service.SmartEmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SalaryIncreaseConfiguration {
    @Bean
    public EmployeeService employeeService() {
        return new SmartEmployeeService();
    }
}
