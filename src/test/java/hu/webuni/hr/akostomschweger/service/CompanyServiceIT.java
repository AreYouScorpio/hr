package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.dto.CompanyDto;
import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.model.Company;
import hu.webuni.hr.akostomschweger.model.Employee;
import hu.webuni.hr.akostomschweger.repository.CompanyRepository;
import hu.webuni.hr.akostomschweger.web.CompanyController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class CompanyServiceIT {

    private static final String BASE_URI = "/api/companies";

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    InitDbService initDbService;

    @Autowired
    CompanyController companyController;

    @Autowired
    CompanyService companyService;

    @BeforeEach
    public void init() {
        initDbService.clearDB();
        initDbService.insertTestData();
    }

    @Test
    void testFindCompanyByExample() throws Exception {

        // employeeService.save(new Employee());


        CompanyDto company = new CompanyDto(
                "1000", "TestCompany", "Wurzburg", new ArrayList<EmployeeDto>());

        long savedCompanyIdForTesting = createCompany(company);


        List<CompanyDto> companyList = getAllCompanies();
        long companyIdForModification = companyList.get(1).getId();

        EmployeeDto employeeX =
                new EmployeeDto("V-Man", "tester", 111,
                        LocalDateTime.of(2020, Month.FEBRUARY, 3, 6, 30));

        EmployeeDto employeeY =
                new EmployeeDto("X-Man", "programmer", 222,
                        LocalDateTime.of(2021, Month.FEBRUARY, 3, 6, 30));


        // new Position("spring_student", Qualification.UNIVERSITY)

        //long empId1 = employeeSuperClass.save(employeeX).getId();
        //long empId2 = employeeSuperClass.save(employeeY).getId();

        //company.addNewEmployee(employeeX);
//        long savedCompanyIdForTesting = createCompany(company);


        //     System.out.println("savedCompanyId For Testing: " + savedCompanyIdForTesting);
//        System.out.println("savedEmployeeId1 For Testing: " + empId1);
//        System.out.println("savedEmployeeIdId2 For Testing: " + empId2);


//        long savedEmployeeIdForTesting1 = companyController.addEmployeeToCompany(savedCompanyIdForTesting, employeeX).getId();
        //long savedEmployeeIdForTesting1 =   25L;      //
        //savedEmployeeIdForTesting1 = companyRepository.getById(savedEmployeeIdForTesting1).getEmployees()
//        System.out.println("savedEmployeeIdForTesting1: " + savedEmployeeIdForTesting1);

        //       long savedEmployeeIdForTesting2 = companyController.addEmployeeToCompany(savedCompanyIdForTesting, employeeY).getId();
        //long savedEmployeeIdForTesting2 =    26L; //
        //      System.out.println("savedEmployeeIdForTesting2: " + savedEmployeeIdForTesting2);


        //List<CompanyDto> companyList = getAllCompanies();
//        System.out.println("A céglista mérete törlés előtt: " + companyList.size());
//        System.out.println("A 0. elem neve a cégek listájában? " + companyList.get(0).getName());
        // assertEquals("X company", companyList.get(0).getName());

        // employee lista X companyban:
        // 1. cég 1. employee neve:
        //       System.out.println("1. cég 1. employee neve: " + getCompanyAndItsEmployeeList(savedCompanyIdForTesting).getEmployees().get(0).getName());
        // 1. cég 2. employee neve:
        //       System.out.println("1. cég 2. employee neve: " + getCompanyAndItsEmployeeList(savedCompanyIdForTesting).getEmployees().get(1).getName());
        // 1.cég 1. employee ID-ja:
        //       System.out.println("1.cég 1. employee ID-ja: " + getCompanyAndItsEmployeeList(savedCompanyIdForTesting).getEmployees().get(0).getId());
        //       System.out.println("1.cég 2. employee ID-ja: " + getCompanyAndItsEmployeeList(savedCompanyIdForTesting).getEmployees().get(1).getId());

        Company example = new Company();
        //example.setId(25L);
        example.setName("tE");

        //System.out.println("A 24-es cég alá rögzített employee ID-ja: " + companyController.getById(savedCompanyIdForTesting, true).getEmployees().get(0).getId());

        //long exampleId = employeeRepository.save(example).getId();
        //System.out.println(exampleId);
        //long savedExampleID = companyRepository.(example).getId();

        List<Company> foundCompanies =
                this.companyService.findCompaniesByExample(example);


                        /*
                        System.out.println("Results per ID:");
                        System.out.println(foundEmployees.get(0).getName());
                        System.out.println(foundEmployees.get(0).getId());
                        */

        System.out.println("Results:");
        System.out.println("-size: " + foundCompanies.size());


        for (int i = 0; i < foundCompanies.size(); i++) {

            System.out.println("Name: " + foundCompanies.get(i).getName() + ", details:");
            System.out.println(foundCompanies.get(i).toString());


            /*
            System.out.println(foundEmployees.stream()
                    .map(Employee::getId)
                    .collect(Collectors.toList()));
             */





        /*
        assertThat(foundEmployees.stream()
                .map(Employee::getId)
                .collect(Collectors.toList()))
                .containsExactly(savedEmployeeIdForTesting1, savedEmployeeIdForTesting2);


         */


        }
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
