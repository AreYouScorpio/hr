package hu.webuni.hr.akostomschweger.web;

import hu.webuni.hr.akostomschweger.dto.CompanyDto;
import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
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
        //initDbService.insertTestData();
    }


    @Test
    void testEmployeeAdded() throws Exception {

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


