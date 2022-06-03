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

        // ez csak egy plusz teszt a Service-en át --->

        @Test
        void testCreateEmployeeValidwithEmployeeService() throws Exception {
                // service-vel gyártva, Service teszt
                Employee employee=
                        new Employee(21L,"Akos", "jsj", 200,
                                LocalDateTime.of(2017, Month.FEBRUARY,3,6,30,40,50000));
                employeeService.save(employee);
                Employee employeeTest = employeeService.findById(21L);
                assertThat(employeeTest.getSalary()).isEqualTo(200);

        }

        // itt a post / create tesztek Controlleren át --->

        @Test // controllerrel gyártva
        void testCreateEmployeeValidWithController() throws Exception {
                // EmployeeController employeeController = new EmployeeController();
                EmployeeDto employee=
                        new EmployeeDto(21L,"Akos", "jsj", 200,
                                LocalDateTime.of(2017, Month.FEBRUARY,3,6,30,40,50000));
                employeeController.createEmployee(employee);
                Employee employeeTest = employeeService.findById(21L);
                assertThat(employeeTest.getSalary()).isEqualTo(200);

        }

        @Test // controllerrel gyártva
        void testCreateEmployeeInvalidWithController() throws Exception {
                // EmployeeController employeeController = new EmployeeController();
                EmployeeDto employee=
                        new EmployeeDto(21L,"", "jsj", 200,
                                LocalDateTime.of(2017, Month.FEBRUARY,3,6,30,40,50000));
                employeeController.createEmployee(employee);
                Employee employeeTestFromModell = employeeService.findById(21L);
                assertThat(employeeTestFromModell.getSalary()).isEqualTo(-1);

        }

        // itt a put / Modify tesztek Controlleren át--->

        @Test // controllerrel gyártva
        void testModifyEmployeeValidWithController() throws Exception {
                // EmployeeController employeeController = new EmployeeController();
                EmployeeDto employee=
                        new EmployeeDto(1L,"Dkos", "jsj", 200,
                                LocalDateTime.of(2017, Month.FEBRUARY,3,6,30,40,50000));
                employeeController.modifyEmployee(1L, employee);
                Employee employeeTestFromModell = employeeService.findById(1L);
                assertThat(employeeTestFromModell.getName()).isEqualTo("Dkos");

        }

        @Test // controllerrel gyártva
        void testModifyEmployeeInvalidWithController() throws Exception {
                // EmployeeController employeeController = new EmployeeController();
                EmployeeDto employee=
                        new EmployeeDto(1L,"Dkos", "jsj", -1,
                                LocalDateTime.of(2017, Month.FEBRUARY,3,6,30,40,50000));
                employeeController.modifyEmployee(1L, employee);
                Employee employeeTestFromModell = employeeService.findById(1L);
                System.out.println(employeeTestFromModell.getName());
                assertThat(employeeTestFromModell.getName()).isEqualTo("Akos");

        }

}
