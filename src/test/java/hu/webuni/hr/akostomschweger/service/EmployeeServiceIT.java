package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.dto.CompanyDto;
import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.model.Employee;
import hu.webuni.hr.akostomschweger.model.Position;
import hu.webuni.hr.akostomschweger.model.Qualification;
import hu.webuni.hr.akostomschweger.repository.EmployeeRepository;
import hu.webuni.hr.akostomschweger.web.CompanyController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeServiceIT {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeSuperClass employeeSuperClass;

    @Autowired
    InitDbService initDbService;

    @Autowired
    CompanyController companyController;

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

        EmployeeDto employeeX =
                new EmployeeDto("V-Man", "spring_student", 111,
                        LocalDateTime.of(2020, Month.FEBRUARY, 3, 6, 30));

        EmployeeDto employeeY =
                new EmployeeDto("X-Man", "sprinq_student", 222,
                        LocalDateTime.of(2021, Month.FEBRUARY, 3, 6, 30));

        //company.addNewEmployee(employeeX);
        long savedCompanyIdForTesting = createCompany(company);

        long savedEmployeeIdForTesting1 =         companyController.addEmployeeToCompany(savedCompanyIdForTesting, employeeX).getId();

        long savedEmployeeIdForTesting2 =         companyController.addEmployeeToCompany(savedCompanyIdForTesting, employeeY).getId();



        Employee example = new Employee();
        example.setName("A");
        //example.setPosition(new Position("dsjdksj", Qualification.UNIVERSITY));
        //example.setStartDateAtTheCompany(LocalDateTime.of(2002,2,2,1,1));

        List<Employee> foundEmployees=
                this.employeeSuperClass.findEmployeesByExample(example);

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


}
