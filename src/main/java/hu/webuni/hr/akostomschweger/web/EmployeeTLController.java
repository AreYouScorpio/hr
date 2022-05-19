package hu.webuni.hr.akostomschweger.web;

import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeTLController {

    private List<EmployeeDto> employees = new ArrayList<>();

    {
        employees.add(new EmployeeDto(
                1L,
                "Akos",
                "junior java developer",
                100000,
                LocalDateTime.of(2011,1,11,11,11)));

        employees.add(new EmployeeDto(
                2L,
                "Bkos",
                "senior java developer",
                200000,
                LocalDateTime.of(2012,2,2,22,22)));



        System.out.println(employees);
    }


    @GetMapping("/")
    public String home() {
        System.out.println("hello"); // teszt kiírás frissül-e
        return "index";
    }

    @GetMapping("/employees")
    public String listEmployees(Map<String,Object> model){
        model.put("employees", employees);
        model.put("newEmployee", new EmployeeDto());
        return "employees";

    }

    @PostMapping("/employees")
    public String addEmployee(EmployeeDto employee){
        employees.add(employee);
        return "redirect:employees";

    }

}
