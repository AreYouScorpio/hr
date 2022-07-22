package hu.webuni.hr.akostomschweger.web;

import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.dto.HolidayRequestDto;
import hu.webuni.hr.akostomschweger.dto.LoginDto;
import hu.webuni.hr.akostomschweger.model.Employee;
import hu.webuni.hr.akostomschweger.repository.EmployeeRepository;
import hu.webuni.hr.akostomschweger.service.HolidayRequestService;
import hu.webuni.hr.akostomschweger.service.InitDbService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
//@RunWith(SpringRunner.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ActiveProfiles({"prod", "test"}) // profile switcher: both profiles on
public class HolidayRequestsITwithBasicAuth {

    private static final String BASE_URI = "/api/employees";
    private static final String BASE_URI_HR = "/api/holidayrequests";

    private static final String TESTUSER_NAME = "user1";
    private static final String TESTUSER_NAME_2 = "user2";
    private static final String PASSWORD = "pass";
    private static final String PASSWORD_2 = "pass";

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    InitDbService initDbService;

    @Autowired
    HolidayRequestService holidayRequestService;

    @Autowired
    HolidayRequestController holidayRequestController;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    String username = "user1";
    String pass = "pass";

    private String jwt;

    @BeforeEach
    public void init() {
        initDbService.clearDB();
        if (employeeRepository.findByUsername(TESTUSER_NAME).isEmpty()) {
            Employee testuser = new Employee();
            Employee testuser2 = new Employee();
            testuser.setUsername(TESTUSER_NAME);
            testuser.setPassword(passwordEncoder.encode(PASSWORD));
            testuser2.setUsername(TESTUSER_NAME_2);
            testuser2.setPassword(passwordEncoder.encode(PASSWORD_2));
            employeeRepository.save(testuser);
            long id1=testuser.getId();
            employeeRepository.save(testuser2);
            long id2=testuser2.getId();
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
    void testCreateHolidayRequest() throws Exception {
        // EmployeeController employeeController = new EmployeeController();

        /*

        List<EmployeeDto> employeesBefore = getAllEmployees();
        System.out.println(employeesBefore);

        EmployeeDto employee1 =
                new EmployeeDto(1L, "Akos", "jsj", 100,
                        LocalDateTime.of(2017, Month.JANUARY, 3, 6, 30));
        EmployeeDto employee2 =
                new EmployeeDto(2L, "Bkos", "jxj", 200,
                        LocalDateTime.of(2017, Month.FEBRUARY, 3, 6, 30));

        employee1.setUsername(TESTUSER_NAME);
        employee1.setPassword(passwordEncoder.encode(PASSWORD));

        employee2.setUsername(TESTUSER_NAME_2);
        employee2.setPassword(passwordEncoder.encode(PASSWORD_2));

        // employeeController.createEmployee(employee);
        long employee1id = createEmployee(employee1);
        System.out.println("employee1id: " + employee1id);
        long employee2id = createEmployee2(employee2);
        System.out.println("employee2id: " + employee2id);


         */

        List<EmployeeDto> employees = getAllEmployees();
        System.out.println(employees);


        List<HolidayRequestDto> holidayRequestsBefore = getAllHRs();
        System.out.println("holidayRequestsBefore: " + holidayRequestsBefore);

        HolidayRequestDto holidayRequest = new HolidayRequestDto(
                1283L, //id
                LocalDateTime.now(), // "createdAt",
                employees.get(0).getId(), // "employeeId"
                employees.get(1).getId(), // "approverId"
                null,// "approved"
                null, // "approvedAt"
                LocalDate.of(2022, 07, 26), // "startDate"
                LocalDate.of(2022, 07, 28) // "endDate"
        );

        long HR_Id = createHR(holidayRequest);
        holidayRequest.setId(HR_Id);
        System.out.println("HR_details: " + holidayRequest);
        System.out.println("HR_Id: " + HR_Id);
        //holidayRequest.setId(HR_Id);

        List<HolidayRequestDto> holidayRequestsAfter = getAllHRs();
        System.out.println("holidayRequestsAfter: " + holidayRequestsAfter);

        assertThat(holidayRequestsAfter.subList(0, holidayRequestsBefore.size()))
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(holidayRequestsBefore);

        assertThat(holidayRequestsAfter
                .get(holidayRequestsAfter.size() - 1))
                .usingRecursiveComparison().ignoringFields("createdAt")
                .isEqualTo(holidayRequest);


        // Employee employeeTest = employeeService.findById(21L);
        //assertThat(employeeTest.getSalary()).isEqualTo(200);


    }






    private long createHR(HolidayRequestDto holidayRequestDto) {
        long id = webTestClient
                .post()
                .uri(BASE_URI_HR)
                .headers(headers -> headers.setBasicAuth(TESTUSER_NAME, PASSWORD))
                .bodyValue(holidayRequestDto)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(HolidayRequestDto.class).returnResult().getResponseBody().getId();

        return id;
    }





    private List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> responseList =
                webTestClient
                        .get()
                        .uri(BASE_URI)
                        .headers(headers -> headers.setBasicAuth( username, pass))
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBodyList(EmployeeDto.class)
                        .returnResult()
                        .getResponseBody();

        Collections.sort(responseList, (a1, a2) -> Long.compare(a1.getId(), a2.getId()));
        return responseList;

    }




    private void deleteHR(long id) {
        webTestClient
                .delete()
                .uri(BASE_URI_HR + "/" + id)
                .headers(headers -> headers.setBasicAuth(TESTUSER_NAME, PASSWORD))
                .exchange()
                .expectStatus()
                .isOk()
        ;
    }

    private List<HolidayRequestDto> getAllHRs() {
        List<HolidayRequestDto> responseList =
                webTestClient
                        .get()
                        .uri(BASE_URI_HR)
                        .headers(headers -> headers.setBasicAuth(TESTUSER_NAME, PASSWORD))
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBodyList(HolidayRequestDto.class)
                        .returnResult()
                        .getResponseBody();

        Collections.sort(responseList, (a1, a2) -> Long.compare(a1.getId(), a2.getId()));
        return responseList;

    }


    private HolidayRequestDto getHR_ById(long id) {
        HolidayRequestDto response =
                webTestClient
                        .get()
                        .uri(BASE_URI_HR + "/" + id)
                        .headers(headers -> headers.setBasicAuth(TESTUSER_NAME, PASSWORD))
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBodyList(HolidayRequestDto.class)
                        .returnResult()
                        .getResponseBody().get(0);


        return response;


    }


    private void approveHR(long HR_Id, long approverId) {
        //HolidayRequestDto response =
        webTestClient
                .put()
                .uri(BASE_URI_HR + "/" + HR_Id + "/" + "approval?status=true&approverId=" + approverId)
                .headers(headers -> headers.setBasicAuth(TESTUSER_NAME, PASSWORD))
                //.contentType(APPLICATION_JSON)
                //.syncBody(holidayRequestDtoNew)
                //.accept(MediaType.APPLICATION_JSON)
                //.contentType(APPLICATION_JSON)
                //.bodyValue(holidayRequestDtoNew)
                .exchange()
                .expectStatus()
                .isOk();
        //.expectBody(HolidayRequestDto.class)
        //.returnResult().getResponseBody();
//return response;





            /*
        webTestClient
                .put()
                .uri(BASE_URI_HR + "/" + id)
                .bodyValue(holidayRequestDtoNew)
                .exchange()
                .expectStatus()
                .isOk();


             */


    }

    private void modifyHR(long id, HolidayRequestDto holidayRequestDtoNew) {
        //HolidayRequestDto response =
        webTestClient
                .put()
                .uri(BASE_URI_HR + "/" + id)
                .headers(headers -> headers.setBasicAuth(TESTUSER_NAME, PASSWORD))
                //.contentType(APPLICATION_JSON)
                //.syncBody(holidayRequestDtoNew)
                //.accept(MediaType.APPLICATION_JSON)
                //.contentType(APPLICATION_JSON)
                .bodyValue(holidayRequestDtoNew)
                .exchange()
                .expectStatus()
                .isOk();
        //.expectBody(HolidayRequestDto.class)
        //.returnResult().getResponseBody();
//return response;





            /*
        webTestClient
                .put()
                .uri(BASE_URI_HR + "/" + id)
                .bodyValue(holidayRequestDtoNew)
                .exchange()
                .expectStatus()
                .isOk();


             */


    }


    private List<HolidayRequestDto> searchHR(HolidayRequestDto metamodel) {
        List<HolidayRequestDto> responseList =
                webTestClient
                        .post()
                        .uri(BASE_URI_HR + "/search")
                        .headers(headers -> headers.setBasicAuth(TESTUSER_NAME, PASSWORD))
                        //.contentType(APPLICATION_JSON)
                        //.syncBody(holidayRequestDtoNew)
                        //.accept(MediaType.APPLICATION_JSON)
                        //.contentType(APPLICATION_JSON)
                        .bodyValue(metamodel)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBodyList(HolidayRequestDto.class)
                        .returnResult().getResponseBody();

        Collections.sort(responseList, (a1, a2) -> Long.compare(a1.getId(), a2.getId()));

        return responseList;





            /*
        webTestClient
                .put()
                .uri(BASE_URI_HR + "/" + id)
                .bodyValue(holidayRequestDtoNew)
                .exchange()
                .expectStatus()
                .isOk();


             */


    }


}




