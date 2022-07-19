package hu.webuni.hr.akostomschweger.web;

import hu.webuni.hr.akostomschweger.dto.CompanyDto;
import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.dto.HolidayRequestDto;
import hu.webuni.hr.akostomschweger.model.HolidayRequest;
import hu.webuni.hr.akostomschweger.service.EmployeeService;
import hu.webuni.hr.akostomschweger.service.HolidayRequestService;
import hu.webuni.hr.akostomschweger.service.InitDbService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;


import java.time.LocalDate;
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
    public class HolidayRequestsIT {

        private static final String BASE_URI = "/api/employees";
        private static final String BASE_URI_HR = "/api/holidayrequests";

        @Autowired
        WebTestClient webTestClient;

        @Autowired
        InitDbService initDbService;

        @Autowired
        HolidayRequestService holidayRequestService;

        @Autowired
        HolidayRequestController holidayRequestController;

        @BeforeEach
        public void init() {
            initDbService.clearDB();
            initDbService.insertTestData();
        }

        @Test
        void testCreateHolidayRequest() throws Exception {
            // EmployeeController employeeController = new EmployeeController();


            List<EmployeeDto> employeesBefore = getAllEmployees();
            System.out.println(employeesBefore);

            EmployeeDto employee1 =
                    new EmployeeDto( 1L, "Akos", "jsj", 100,
                            LocalDateTime.of(2017, Month.JANUARY, 3, 6, 30));
            EmployeeDto employee2 =
                    new EmployeeDto( 2L, "Bkos", "jxj", 200,
                            LocalDateTime.of(2017, Month.FEBRUARY, 3, 6, 30));
            // employeeController.createEmployee(employee);
            long employee1id = createEmployee(employee1);
            System.out.println("employee1id: " + employee1id);
            createEmployee(employee2);
            long employee2id = createEmployee(employee2);
            System.out.println("employee2id: " + employee2id);


            List<EmployeeDto> employeesAfter = getAllEmployees();
            System.out.println(employeesAfter);


            List<HolidayRequestDto> holidayRequestsBefore = getAllHRs();
            System.out.println("holidayRequestsBefore: " + holidayRequestsBefore);

            HolidayRequestDto holidayRequest = new HolidayRequestDto(
                    1283L, //id
                    LocalDateTime.now(), // "createdAt",
                    employee1id, // "employeeId"
                    employee2id, // "approverId"
                    false,// "approved"
                    null, // "approvedAt"
                    LocalDate.of(2022,07,26), // "startDate"
                    LocalDate.of(2022,07,28) // "endDate"
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
            long id =   webTestClient
                    .post()
                    .uri(BASE_URI_HR)
                    .bodyValue(holidayRequestDto)
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(HolidayRequestDto.class).returnResult().getResponseBody().getId();

            return id;
        }

        private long createEmployee(EmployeeDto employee) {
            long id =  webTestClient
                    .post()
                    .uri(BASE_URI)
                    .bodyValue(employee)
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(EmployeeDto.class).returnResult().getResponseBody().getId();;

                    return id;
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


       private List<HolidayRequestDto> getAllHRs() {
            List<HolidayRequestDto> responseList =
                    webTestClient
                            .get()
                            .uri(BASE_URI_HR)
                            .exchange()
                            .expectStatus()
                            .isOk()
                            .expectBodyList(HolidayRequestDto.class)
                            .returnResult()
                            .getResponseBody();

            Collections.sort(responseList, (a1, a2) -> Long.compare(a1.getId(), a2.getId()));
            return responseList;

        }


    }
