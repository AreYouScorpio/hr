package hu.webuni.hr.akostomschweger.web;

import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.dto.LoginDto;
import hu.webuni.hr.akostomschweger.model.Employee;
import hu.webuni.hr.akostomschweger.repository.EmployeeRepository;
import hu.webuni.hr.akostomschweger.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
//@RunWith(SpringRunner.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ActiveProfiles({"prod", "test"}) // profile switcher: both profiles on
public class EmployeeControllerIT {

    private static final String BASE_URI = "/api/employees";
    private static final String TESTUSER_NAME = "testuser";
    private static final String PASSWORD = "pass";

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

    // security:

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmployeeRepository employeeRepository;


    String username = "user";
    String pass = "pass";

    private String jwt;

    @BeforeEach
    public void init() {
        if (employeeRepository.findByUsername(TESTUSER_NAME).isEmpty()) {
            Employee testuser = new Employee();
            testuser.setUsername(TESTUSER_NAME);
            testuser.setPassword(passwordEncoder.encode(PASSWORD));
            employeeRepository.save(testuser);
        }

        LoginDto body = new LoginDto();
        body.setUsername(username);
        body.setPassword(pass);
        jwt = webTestClient.post()
                .uri("/api/login")
                .headers(headers -> headers.setBasicAuth(TESTUSER_NAME, PASSWORD))
                .bodyValue(body)
                .exchange()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
    }


    @Test
    void testCreateEmployeeValidWithController() throws Exception {
        // EmployeeController employeeController = new EmployeeController();

        List<EmployeeDto> employeesBefore = getAllEmployees();

        System.out.println("Módosításhoz létrehozás és módosítás előtti lista: " + employeesBefore);

        EmployeeDto employee =
                new EmployeeDto(13L, "Akos", "jsj", 200,
                        LocalDateTime.of(2017, Month.FEBRUARY, 3, 6, 30));
        // employeeController.createEmployee(employee);
        createEmployee(employee);


        List<EmployeeDto> employeesAfter = getAllEmployees();



        assertThat(employeesAfter.subList(0, employeesBefore.size()))
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(employeesBefore);

        assertThat(employeesAfter
                .get(employeesAfter.size() - 1))
                .usingRecursiveComparison().ignoringFields("id")
                .isEqualTo(employee);


        // Employee employeeTest = employeeService.findById(21L);
        //assertThat(employeeTest.getSalary()).isEqualTo(200);


    }


    private long createEmployeeWithId(EmployeeDto employee) {
        long idNewX = webTestClient
                .post()
                .uri(BASE_URI)
                .headers(headers -> headers.setBasicAuth(TESTUSER_NAME, PASSWORD))
                .bodyValue(employee)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(EmployeeDto.class).returnResult().getResponseBody().getId();

        return idNewX;
    }

    private void createEmployee(EmployeeDto employee) {
        webTestClient
                .post()
                .uri(BASE_URI)
                .headers(headers -> headers.setBasicAuth(TESTUSER_NAME, PASSWORD))
                .bodyValue(employee)
                .exchange()
                .expectStatus()
                .isOk();
    }

    private void createEmployeeInvalid(EmployeeDto employee) {
        webTestClient
                .post()
                .uri(BASE_URI)
                .headers(headers -> headers.setBasicAuth(TESTUSER_NAME, PASSWORD))
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
                        .headers(headers -> headers.setBasicAuth(TESTUSER_NAME, PASSWORD))
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

        System.out.println("Módosításhoz létrehozás és módosítás előtti lista: " + employeesBefore);

        EmployeeDto employee =
                new EmployeeDto(2L, "X", "jsj", -1,
                        LocalDateTime.of(2017, Month.FEBRUARY, 3, 6, 30));
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

    private WebTestClient.ResponseSpec modifyEmployee(@RequestParam long id, EmployeeDto employee) {

        /*
        WebTestClient.ResponseSpec foundRecord=(this.webTestClient
                .get()
                .uri("/api/employees/"+id)
                .headers(headers -> headers.setBasicAuth(TESTUSER_NAME, PASSWORD))
                //.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isNotFound());


         */

/*
                WebTestClient.ResponseSpec foundRecord=(this.webTestClient
                        .get()
                        .uri("/api/employees/"+id)
                        //.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .exchange()
                        .expectStatus()
                        .isNotFound());



 */


// security-vel:

          return       webTestClient
                .put()
                .uri("/api/employees/" + id)
                .headers(headers -> headers.setBasicAuth(TESTUSER_NAME, PASSWORD))
                .bodyValue(employee)
                .exchange();

                //.expectStatus()
                //.isOk()
                //.expectBody(EmployeeDto.class).returnResult().getResponseBody().getId();
        //;

               /*
                {
                webTestClient
                        .put()
                        .uri("/api/employees/"+id)
                        .bodyValue(employee)
                        .exchange()
                        .expectStatus()
                        .isOk();
        }
  */
        //return idNew;
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

    private WebTestClient.ResponseSpec saveEmployee(EmployeeDto newEmployee) {
        return webTestClient
                .post()
                .uri(BASE_URI)
                .headers(headers -> headers.setBasicAuth(TESTUSER_NAME, PASSWORD))
                //.headers(headers -> headers.setBearerAuth(jwt))
                .bodyValue(newEmployee)
                .exchange();
    }

}

