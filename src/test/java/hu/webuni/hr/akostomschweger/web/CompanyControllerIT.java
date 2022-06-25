package hu.webuni.hr.akostomschweger.web;

import hu.webuni.hr.akostomschweger.dto.CompanyDto;
import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.model.Company;
import hu.webuni.hr.akostomschweger.model.Employee;
import hu.webuni.hr.akostomschweger.repository.CompanyRepository;
import hu.webuni.hr.akostomschweger.service.CompanyService;
import hu.webuni.hr.akostomschweger.service.EmployeeService;
import hu.webuni.hr.akostomschweger.service.InitDbService;
import hu.webuni.hr.akostomschweger.service.SalaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class CompanyControllerIT {


    private static final String BASE_URI = "/api/companies";


    @Autowired
    WebTestClient webTestClient;

    @Autowired
    InitDbService initDbService;

    @Autowired
    CompanyController companyController;

    @Autowired
    CompanyRepository companyRepository;


    @Autowired
    EmployeeController employeeController;


    @BeforeEach
    public void init() {
        initDbService.clearDB();
        initDbService.insertTestData();
    }

    @Test
    void testEmployeeDeleted() throws Exception {
        List<CompanyDto> companyListBefore = getAllCompanies();
        System.out.println("A céglista mérete törlés előtt: " + companyListBefore.size());
        System.out.println("A 0. elem neve a cégek listájában? " + companyListBefore.get(0).getName());
        assertEquals("X company", companyListBefore.get(0).getName());


        long deleteTestCompanyId = companyListBefore.get(0).getId();
        long deleteTestEmployeeId = getCompanyAndItsEmployeeList(deleteTestCompanyId).getEmployees().get(0).getId();

        System.out.println("A 0. elemű cég aktuális ID-ja: " + deleteTestCompanyId);
        CompanyDto companyDto = getCompanyAndItsEmployeeList(companyListBefore.get(0).getId());
        System.out.println("A 0. elemű cég (ID: " + deleteTestCompanyId + " )  0. elemű dolgozójának neve: " + getCompanyAndItsEmployeeList(deleteTestCompanyId).getEmployees().get(0).getName());
        System.out.println("A 0. elemű cég (ID: " + deleteTestCompanyId + " )  0. elemű dolgozójának ID-ja: " + deleteTestEmployeeId);

        System.out.println("A 0. elemű cég dolgozóinak száma törlés előtt: " +getCompanyAndItsEmployeeList(deleteTestCompanyId).getEmployees().size());


        EmployeeDto employee = employeeController.getById(deleteTestEmployeeId);
        employee.setCompany(null);
        //Employee employee = employeeRepository.findById(employee_id).get();
        //mployee.setCompany(null);
        getCompanyAndItsEmployeeList(deleteTestCompanyId).getEmployees().remove(employee);


        List<CompanyDto> companyListAfter = getAllCompanies();

        System.out.println("A céglista mérete törlés után: " + companyListAfter.size());
        System.out.println("A 0. elemű cég dolgozóinak száma törlés után: " +getCompanyAndItsEmployeeList(deleteTestCompanyId).getEmployees().size());


        assertThat(getCompanyAndItsEmployeeList(deleteTestCompanyId).getEmployees().size()==0);
        assertThat(companyListAfter.size()==companyListBefore.size());


        //System.out.println(companyListBefore.get(0).getEmployees().get(0).getName());
        //companyController.deleteCompany(companyListBefore.size()-1);
        /*
        Company company =
                companyRepository
                        .findById((long)(companyListBefore.size()-1)).get();
        System.out.println(company.getName());
           */
        //Employee employee = employeeRepository.findById(employee_id).get();


        //removeIf(e -> e.getId() == employee_id);
        //employee.setCompany(null);
        //company.getEmployees().remove(employee);


    }


        @Test
    void testEmployeeAdded() throws Exception {

        // !! company-t EAGER-re alakítani, h működjön !!

        companyRepository.deleteAllInBatch();


        // ez nem dto ad vissza, hanem companyt:
        List<CompanyDto> companyListBefore = getAllCompanies();
        //System.out.println(companyListBefore);


        CompanyDto company = new CompanyDto(
                "666", "HelloCegNev", "Kiskunhalas", new ArrayList<EmployeeDto>());

        EmployeeDto employeeX =
                new EmployeeDto("Akos", "springtanuló", 200,
                        LocalDateTime.of(2017, Month.FEBRUARY, 3, 6, 30));

        //company.addNewEmployee(employeeX);
        long savedIdForTesting = createCompany(company);
        companyController.addEmployeeToCompany(savedIdForTesting, employeeX);



        System.out.println("céglista record: " + companyRepository.findAllWithEmployees());
        System.out.println("cég listájából a hozzáadott employee neve: " +
                getCompanyAndItsEmployeeList(savedIdForTesting).getEmployees().get(0).getName());
        //companyController.addEmployeeToCompany(1L, employeeX);

        System.out.println("ez a cég: " + company.getName());
        System.out.println("cég eredeti ID: " + company.getId() + "  új generált id: " + savedIdForTesting);

        company.setId(savedIdForTesting); // hogy a teszt lefusson, ne null legyen a generált ID

        assertEquals("Akos", getCompanyAndItsEmployeeList(savedIdForTesting).getEmployees().get(0).getName());
        assertEquals("springtanuló", getCompanyAndItsEmployeeList(savedIdForTesting).getEmployees().get(0).getPosition());
        assertEquals(200, getCompanyAndItsEmployeeList(savedIdForTesting).getEmployees().get(0).getSalary());
        assertEquals(LocalDateTime.of(2017, Month.FEBRUARY, 3, 6, 30),
                getCompanyAndItsEmployeeList(savedIdForTesting).getEmployees().get(0).getStartDateAtTheCompany());

        /*
        System.out.println("cég employee lista mérete: " +
                companyController
                        .getCompaniesAboveEmployeeNumber(0,true)
                        .get(0)
                        .getEmployees()
                        .size());


         */


        List<CompanyDto> companyListAfter = getAllCompanies();




        assertThat(companyListAfter.subList(0, companyListBefore.size()))
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(companyListBefore);

        assertThat(companyListAfter
                .get(companyListAfter.size() - 1))
                .usingRecursiveComparison()
                .isEqualTo(company);





    }

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


