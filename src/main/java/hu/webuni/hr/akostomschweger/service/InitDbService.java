package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.model.Company;
import hu.webuni.hr.akostomschweger.model.Employee;
import hu.webuni.hr.akostomschweger.repository.CompanyRepository;
import hu.webuni.hr.akostomschweger.repository.EmployeeRepository;
// import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InitDbService  {

    @Autowired
    CompanyRepository companyRepository;
    EmployeeRepository employeeRepository;

    @Transactional
    public void clearDB(){
        //employeeRepository.truncate();

        for (Employee e : employeeRepository.findAll()) {
            employeeRepository.delete(e);
        }
        /*
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/employees?useUnicode=true");
        dataSource.setUser("root");
        dataSource.setPassword("root");

         */

/*
        String url = "jdbc:postgresql://localhost/test";
        Properties props = new Properties();
        props.setProperty("user","fred");
        props.setProperty("password","secret");
        props.setProperty("ssl","true");
        Connection conn = DriverManager.getConnection(url, props);

        String url = "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true";
        Connection conn = DriverManager.getConnection(url);


 */
        /*
                Flyway flyway = Flyway.configure().dataSource(dataSource).load();

        flyway.clean();
        flyway.migrate();
    */
    }




    public void insertTestData(){

        Employee employeeA = new Employee(
                1L,
                "Akos",
                "junior java developer",
                100000,
                LocalDateTime.of(2011, 1, 11, 11, 11));

        Employee employeeB = new Employee(
                2L,
                "Bkos",
                "senior java developer",
                200000,
                LocalDateTime.of(2012, 2, 22, 22, 22));


        Company companyA =  new Company(
                1L,
                "11111",
                "A company",
                "Budapest",
                 List.of(employeeA));

        Company companyB =  new Company(
                2L,
                "22222",
                "B company",
                "Amsterdam",
                List.of(employeeA, employeeB));



    }


}
