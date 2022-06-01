package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.config.AtConfigurationProperties;
import hu.webuni.hr.akostomschweger.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class SmartEmployeeService extends EmployeeSuperClass {

    private final int DAYS_IN_A_YEAR = 365;

    @Autowired
    AtConfigurationProperties config;

    @Override
    public int getPayRaisePercent(Employee employee) {

        int differenceInDays = (int) Duration.between(employee.getStartDateAtTheCompany(), LocalDateTime.now()).toDays();
        int choice;
/*

        if (differenceInDays < 193) {
            choice = 0;
        } else if (differenceInDays >= 193 && differenceInDays < (5 * 365)) {
            choice = 1;
        } else if (differenceInDays >= (5 * 365) && differenceInDays < 3650) {
            choice = 2;
        } else {
            choice = 3;
        }

 */

        if (differenceInDays < (config.getSalary().getSpecial().getLimit3() * DAYS_IN_A_YEAR)) {
            choice = 0; //0%
        } else if (differenceInDays >=
                (config.getSalary().getSpecial().getLimit3() * DAYS_IN_A_YEAR)
                && differenceInDays <
                (config.getSalary().getSpecial().getLimit2() * DAYS_IN_A_YEAR)) {
            choice = 1; //2%
        } else if (differenceInDays >= (config.getSalary().getSpecial().getLimit2() * DAYS_IN_A_YEAR)
                && differenceInDays < config.getSalary().getSpecial().getLimit1() * DAYS_IN_A_YEAR) {
            choice = 2; //5%
        } else {
            choice = 3; //10%
        }



        switch (choice) {
            case 0:
                return config.getSalary().getSpecial().getPercent4();
            case 1:
                return config.getSalary().getSpecial().getPercent3();
            case 2:
                return config.getSalary().getSpecial().getPercent2();
            case 3:
                return config.getSalary().getSpecial().getPercent1();

        }
        return 0;
    }
}
