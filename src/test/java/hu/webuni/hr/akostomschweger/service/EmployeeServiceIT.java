package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.dto.CompanyDto;
import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.model.Employee;
import hu.webuni.hr.akostomschweger.model.Position;
import hu.webuni.hr.akostomschweger.model.Qualification;
import hu.webuni.hr.akostomschweger.repository.CompanyRepository;
import hu.webuni.hr.akostomschweger.repository.EmployeeRepository;
import hu.webuni.hr.akostomschweger.web.CompanyController;
import hu.webuni.hr.akostomschweger.web.EmployeeController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@Bean({"smartEmployeeService"})// , "defaultEmployeeService", "employeeService"})
//@Service
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class EmployeeServiceIT {

    private static final String BASE_URI = "/api/companies";

    @Autowired
    WebTestClient webTestClient;

    //@Autowired
    //EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    SmartEmployeeService smartEmployeeService;

    @Autowired
    InitDbService initDbService;

    @Autowired
    CompanyController companyController;

    @Autowired
    EmployeeController employeeController;

    @BeforeEach
    public void init() {
        initDbService.clearDB();
        initDbService.insertTestData();
    }

    @Test
    void testFindEmployeeByExample() throws Exception{

        // employeeService.save(new Employee());


        CompanyDto company = new CompanyDto(
                "1000", "TestCompany", "Wurzburg", new ArrayList<EmployeeDto>());

        List<CompanyDto> companyList = getAllCompanies();
        long companyIdForModification = companyList.get(1).getId();

        EmployeeDto employeeX =
                new EmployeeDto("V-Man", "spring_student", 111,
                        LocalDateTime.of(2020, Month.FEBRUARY, 3, 6, 30));

        EmployeeDto employeeY =
                new EmployeeDto("X-Man", "sprinq_student", 222,
                        LocalDateTime.of(2021, Month.FEBRUARY, 3, 6, 30));

        company.addNewEmployee(employeeX);
           long savedCompanyIdForTesting = createCompany(company);

        long savedEmployeeIdForTesting1 =         companyController.addEmployeeToCompany(savedCompanyIdForTesting, employeeX).getId();

        long savedEmployeeIdForTesting2 =         companyController.addEmployeeToCompany(savedCompanyIdForTesting, employeeY).getId();

        //List<CompanyDto> companyList = getAllCompanies();
        System.out.println("A céglista mérete törlés előtt: " + companyList.size());
        System.out.println("A 0. elem neve a cégek listájában? " + companyList.get(0).getName());
        // assertEquals("X company", companyList.get(0).getName());


        Employee example = new Employee();
        example.setId(1L);
        example.setName("V");
        example.setPosition(new Position("spri", Qualification.UNIVERSITY));
        example.setStartDateAtTheCompany(LocalDateTime.of(2002,2,2,1,1));

        //long exampleId = employeeRepository.save(example).getId();
        //System.out.println(exampleId);
        //long savedExampleID = companyRepository.(example).getId();

        List<Employee> foundEmployees=
                this.smartEmployeeService.findEmployeesByExample(example);

        assertThat(foundEmployees.stream()
                .map(Employee::getId)
                .collect(Collectors.toList()))
                .containsExactly(savedEmployeeIdForTesting1, savedEmployeeIdForTesting2);




    }

    /*
    private long createEmployee
            (String flightNumber, long takeoff, long landing, LocalDateTime dateTime) {
        return employeeSuperClass.(
                flightNumber,
                takeoff,
                landing,
                dateTime).getId();

    }

     */

    private long createCompany(CompanyDto company) {

        /*
        webTestClient
                .post()
                .uri(BASE_URI)
                .bodyValue(company)
                .exchange()
                .expectStatus()
                .isOk();

         */
        return companyController.createCompany(company).getId();
    }

    private CompanyDto getCompanyAndItsEmployeeList(long id) {

        /*
        List<CompanyDto> responseList = webTestClient
                .get()
                .uri(BASE_URI)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(CompanyDto.class)
                .returnResult().getResponseBody();

        Collections.sort(responseList, (a1, a2) -> Long.compare(a1.getId(), a2.getId()));

        return responseList;

         */

        return companyController.getById(id, true);
    }

    private List<CompanyDto> getAllCompanies() {

        // átalakítani, ez még a companykat kérdezi csak le


        List<CompanyDto> responseList = webTestClient
                .get()
                .uri(BASE_URI)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(CompanyDto.class)
                .returnResult().getResponseBody();

        Collections.sort(responseList, (a1, a2) -> Long.compare(a1.getId(), a2.getId()));

        return responseList;
    }

}

