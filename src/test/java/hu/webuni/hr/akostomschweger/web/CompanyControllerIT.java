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

import static org.assertj.core.api.Assertions.assertThat;

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

        initDbService.clearDB();
        companyRepository.deleteAllInBatch();




        CompanyDto company = new CompanyDto(1L, "666", "HelloCegNev", "Kiskunhalas", new ArrayList<EmployeeDto>());

        createCompany(company);
        System.out.println("ez a cég: " + company.toString());
        System.out.println("cég ID: " + company.getId());
        System.out.println("employeek: " +companyController.getById(1L, true).getEmployees());

        List<EmployeeDto> employeesListBefore =
                companyController.getById(1L, true).getEmployees().stream().toList();
        System.out.println("Employee lista: " + employeesListBefore);

        EmployeeDto employee =
                new EmployeeDto(21L, "Akos", "jsj", 200,
                        LocalDateTime.of(2017, Month.FEBRUARY, 3, 6, 30));
        employeeController.createEmployee(employee);
        companyController.addEmployeeToCompany(1L, employee);

        List<EmployeeDto> employeesListAfter =
                companyController.getById(1L, true).getEmployees().stream().toList();
        System.out.println("előtte "+employeesListBefore);
        System.out.println("utána "+employeesListAfter);


/*
        assertThat(employeesListAfter.subList(0, employeesListBefore.size()))
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(employeesListBefore);

        assertThat(employeesListAfter
                .get(employeesListAfter.size() - 1))
                .usingRecursiveComparison()
                .isEqualTo(employee);


 */




    }

    private void createCompany(CompanyDto company) {
        webTestClient
                .post()
                .uri(BASE_URI)
                .bodyValue(company)
                .exchange()
                .expectStatus()
                .isOk();
    }


    private List<CompanyDto> getAllCompanies() {
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
