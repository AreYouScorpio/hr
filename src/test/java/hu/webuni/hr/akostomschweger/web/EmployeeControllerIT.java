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
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles({"prod", "test"}) // profile switcher: both profiles on
public class EmployeeControllerIT {

        private static final String BASE_URI = "/api/employees";

        @Autowired
        WebTestClient webTestClient;

        @Autowired
        EmployeeService employeeService;

        @Autowired
        EmployeeController employeeController;

        // ez csak egy plusz teszt a Service-en át --->

        /*
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


         */


        @Test
        void testCreateEmployeeValidWithController() throws Exception {
                // EmployeeController employeeController = new EmployeeController();

                List<EmployeeDto> employeesBefore = getAllEmployees();

                EmployeeDto employee =
                        new EmployeeDto(21L, "Akos", "jsj", 200,
                                LocalDateTime.of(2017, Month.FEBRUARY, 3, 6, 30));
                // employeeController.createEmployee(employee);

                createEmployee(employee);

                List<EmployeeDto> employeesAfter = getAllEmployees();

                assertThat(employeesAfter.subList(0, employeesBefore.size()))
                        .usingRecursiveFieldByFieldElementComparator()
                        .containsExactlyElementsOf(employeesBefore);

                assertThat(employeesAfter
                        .get(employeesAfter.size() - 1))
                        .usingRecursiveComparison()
                        .isEqualTo(employee);


                // Employee employeeTest = employeeService.findById(21L);
                //assertThat(employeeTest.getSalary()).isEqualTo(200);


        }

        private void createEmployee(EmployeeDto employee) {
                webTestClient
                        .post()
                        .uri(BASE_URI)
                        .bodyValue(employee)
                        .exchange()
                        .expectStatus()
                        .isOk();
        }

        private void createEmployeeInvalid(EmployeeDto employee) {
                webTestClient
                        .post()
                        .uri(BASE_URI)
                        .bodyValue(employee)
                        .exchange()
                        .expectStatus()
                        .isBadRequest();
        }

        private List<EmployeeDto> getAllEmployees() {
                List<EmployeeDto> responseList =
                        webTestClient
                                .get()
                                .uri(BASE_URI)
                                .exchange()
                                .expectStatus()
                                .isOk()
                                .expectBodyList(EmployeeDto.class)
                                .returnResult()
                                .getResponseBody();

                Collections.sort(responseList, (a1, a2) -> Long.compare(a1.getId(), a2.getId()));
                return responseList;

        }



        @Test
        void testCreateEmployeeInvalidWithController() throws Exception {
                // EmployeeController employeeController = new EmployeeController();

                List<EmployeeDto> employeesBefore = getAllEmployees();

                EmployeeDto employee=
                        new EmployeeDto(21L,"", "jsj", -1,
                                LocalDateTime.of(2017, Month.FEBRUARY,3,6,30));
                // employeeController.createEmployee(employee);

                createEmployeeInvalid(employee);

                List<EmployeeDto> employeesAfter = getAllEmployees();

                assertThat(employeesAfter.subList(0, employeesBefore.size()))
                        .usingRecursiveFieldByFieldElementComparator()
                        .containsExactlyElementsOf(employeesBefore);

                assertThat(employeesAfter.size()).isEqualTo(employeesBefore.size());


                // Employee employeeTest = employeeService.findById(21L);
                //assertThat(employeeTest.getSalary()).isEqualTo(200);


        }













/*
        @Test // controllerrel gyártva
        void testCreateEmployeeInvalidWith_Controller() throws Exception {
                // EmployeeController employeeController = new EmployeeController();
                EmployeeDto employee=
                        new EmployeeDto(21L,"", "jsj", -1,
                                LocalDateTime.of(2017, Month.FEBRUARY,3,6,30,40,50000));
                employeeController.createEmployee(employee);
                Employee employeeTestFromModell = employeeService.findById(21L);
                assertThat(employeeTestFromModell.getSalary()).isNotEqualTo(-1);

        }


 */

        @Test
        void testModifyEmployeeValidWithController() throws Exception {
                // EmployeeController employeeController = new EmployeeController();
                List<EmployeeDto> employeesBefore = getAllEmployees();

                EmployeeDto employee =
                        new EmployeeDto(1L, "Xkos", "jsj", 200,
                                LocalDateTime.of(2017, Month.FEBRUARY, 3, 6, 30));
                // employeeController.createEmployee(employee);

                modifyEmployee(1L, employee);

                List<EmployeeDto> employeesAfter = getAllEmployees();

                assertThat(employeesAfter.get(0).getName()).isEqualTo("Xkos");

                assertThat(employeesAfter.size()).isEqualTo(employeesBefore.size());




/*
                EmployeeDto employee=
                        new EmployeeDto(1L,"Dkos", "jsj", 200,
                                LocalDateTime.of(2017, Month.FEBRUARY,3,6,30,40,50000));
                employeeController.modifyEmployee(1L, employee);
                Employee employeeTestFromModell = employeeService.findById(1L);
                assertThat(employeeTestFromModell.getName()).isEqualTo("Dkos");


 */
        }

        private void modifyEmployee( long id, EmployeeDto employee) {
                webTestClient
                        .put()
                        .uri("/api/employees/"+"id")
                        .bodyValue(employee)
                        .exchange()
                        .expectStatus()
                        .isOk();
        }


        /*
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


         */
}
