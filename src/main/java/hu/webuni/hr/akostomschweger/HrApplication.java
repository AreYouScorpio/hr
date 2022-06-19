package hu.webuni.hr.akostomschweger;

import hu.webuni.hr.akostomschweger.model.Employee;
import hu.webuni.hr.akostomschweger.service.DefaultEmployeeService;
import hu.webuni.hr.akostomschweger.service.EmployeeService;
import hu.webuni.hr.akostomschweger.service.InitDbService;
import hu.webuni.hr.akostomschweger.service.SalaryService;
import org.hibernate.type.descriptor.sql.TinyIntTypeDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Scanner;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

    @Autowired
    SalaryService salaryService;

    @Autowired
    InitDbService initDbService;

    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

// Scanner scanner = new Scanner(System.in);
// String a = scanner.nextLine();
        //	InitDbService initDbService = new InitDbService();
        //	initDbService.clearDB();


        Employee employee1 =
                new Employee(1L, "Akos", /*"Tester1",*/ 100,
                        LocalDateTime.of(2000, 1, 1, 0, 0, 0, 0));

        Employee employee2 =
                new Employee(2L, "Bkos", /*"Tester2",*/ 200,
                        LocalDateTime.of(2016, 1, 1, 0, 0, 0, 0));

        Employee employee3 =
                new Employee(3L, "Ckos", /*"Tester3",*/ 300,
                        LocalDateTime.of(2018, 1, 1, 0, 0, 0, 0));

        Employee employee4 =
                new Employee(4L, "Dkos", /*"Tester4",*/ 400,
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0, 0));

        System.out.println(salaryService.getPayRaisePercent(employee1));
        System.out.println(salaryService.getPayRaisePercent(employee2));
        System.out.println(salaryService.getPayRaisePercent(employee3));
        System.out.println(salaryService.getPayRaisePercent(employee4));


        initDbService.clearDB();
        initDbService.insertTestData();

    }

}
