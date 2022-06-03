package hu.webuni.hr.akostomschweger.web;

import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.model.Company;
import hu.webuni.hr.akostomschweger.model.Employee;
import hu.webuni.hr.akostomschweger.service.CompanyService;
import hu.webuni.hr.akostomschweger.service.EmployeeService;
import hu.webuni.hr.akostomschweger.service.EmployeeSuperClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@ActiveProfiles({"prod", "test"}) // profile switcher: both profiles on
public class EmployeeControllerIT {

        @Autowired
        EmployeeService employeeService;

        @Autowired
        EmployeeController employeeController;


        @Test
        void testCreateEmployeeValidwithEmployeeService() throws Exception {
                Employee employee=
                        new Employee(21L,"Akos", "jsj", 200,
                                LocalDateTime.of(2017, Month.FEBRUARY,3,6,30,40,50000));
                employeeService.save(employee);
                Employee employeeTest = employeeService.findById(21L);
                assertThat(employeeTest.getSalary()).isEqualTo(200);

        }

        @Test
        void testCreateEmployeeValidWithController() throws Exception {
                EmployeeController employeeController = new EmployeeController();
                EmployeeDto employee=
                        new EmployeeDto(21L,"Akos", "jsj", 200,
                                LocalDateTime.of(2017, Month.FEBRUARY,3,6,30,40,50000));
                employeeController.createEmployee(employee);
                EmployeeDto employeeTest = employeeController.getById(21L);
                assertThat(employeeTest.getSalary()).isEqualTo(200);

        }

}
