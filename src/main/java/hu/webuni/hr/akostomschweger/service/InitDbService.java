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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.Position;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InitDbService  {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    EmployeeRepository employeeRepository;


    @Transactional
    public void clearDB(){
        employeeRepository.truncate();
        //companyRepository.truncate();

        /*

        vagy végig iterálva, de lassabb lenne

        for (Employee e : employeeRepository.findAll()) {
            employeeRepository.delete(e);
        }


         */
        for (Company c : companyRepository.findAll()) {
            companyRepository.delete(c);
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
                1001L,
                "Xkos",
                "junior java developer",
                111111,
                LocalDateTime.of(2018, 1, 11, 11, 11));

        Employee employeeB = new Employee(
                1002L,
                "Ykos",
                "senior java developer",
                222222,
                LocalDateTime.of(2019, 2, 22, 22, 22));


        Company companyA =  new Company(
                9991L,
                "88888",
                "X company",
                "Weert",
                 new ArrayList<Employee>());

        //new ArrayList<Employee>(List.of(employeeA,employeeB))

        Company companyB =  new Company(
                9992L,
                "99999",
                "Y company",
                "Best",
                new ArrayList<Employee>());


        employeeRepository.save(employeeA);
        employeeRepository.save(employeeB);
        companyRepository.save(companyA);
        companyRepository.save(companyB);
        companyA.addEmployee(employeeA);
        companyB.addEmployee(employeeA);
        companyB.addEmployee(employeeB);

    }


}
