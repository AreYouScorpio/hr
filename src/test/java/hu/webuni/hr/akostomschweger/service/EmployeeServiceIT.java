package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.model.Employee;
import hu.webuni.hr.akostomschweger.model.Position;
import hu.webuni.hr.akostomschweger.model.Qualification;
import hu.webuni.hr.akostomschweger.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
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

    @BeforeEach
    public void init() {
        initDbService.clearDB();
        initDbService.insertTestData();
    }

    @Test
    void testFindEmployeeByExample() throws Exception{

    // employeeService.save(new Employee());

        Employee example = new Employee();
        example.setName("A");
        example.setPosition(new Position("dsjdksj", Qualification.UNIVERSITY));
        example.setStartDateAtTheCompany(LocalDateTime.of(2002,2,2,1,1));

        List<Employee> foundFlights =
                this.employeeSuperClass.findEmployeesByExample(example);

        assertThat(foundFlights.stream()
                .map(Flight::getId)
                .collect(Collectors.toList()))
                .containsExactly(flight1, flight2);


    }


}
