package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.model.Employee;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class SmartEmployeeService implements EmployeeService {


    @Override
    public int getPayRaisePercent(Employee employee) {

        int differenceInDays = (int) Duration.between(employee.getStartDateAtTheCompany(), LocalDateTime.now()).toDays();
        int choice;

        if (differenceInDays < 193) {
            choice = 0;
        } else if (differenceInDays >= 193 && differenceInDays < (5 * 365)) {
            choice = 1;
        } else if (differenceInDays >= (5 * 365) && differenceInDays < 3650) {
            choice = 2;
        } else {
            choice = 3;
        }

        switch (choice) {
            case 0:
                return 0;
            case 1:
                return 2;
            case 2:
                return 5;
            case 3:
                return 10;

        }
        return 0;
    }
}
