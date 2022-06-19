package hu.webuni.hr.akostomschweger.web;

import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.model.Employee;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class EmployeeTLController {

    private List<EmployeeDto> employees = new ArrayList<>();
    // Position Entity miatt kivesszük őket:
        /*
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

        employees.add(new EmployeeDto(
                5L,
                "Nyunyusz",
                "senior mouse developer",
                1200000,
                LocalDateTime.of(2012,2,2,22,22)));



        System.out.println(employees);


    }


         */

    @GetMapping("/")
    public String home() {
        System.out.println("hello"); // teszt kiírás frissül-e
        return "index";
    }

    @GetMapping("/employees")
    public String listEmployees(Map<String, Object> model) {
        model.put("employees", employees);
        model.put("newEmployee", new EmployeeDto());
        return "employees";

    }


    @PostMapping("/employees")
    public String addEmployee(EmployeeDto employee) {
        employees.add(employee);
        return "redirect:employees";

    }

    @GetMapping("/modify/{id}")
    public String modifyEmployee(@PathVariable long id, Map<String, Object> model) {
        /*
        model.put("modEmployee", new EmployeeDto());
        model.put("employee", employees.stream()
                .filter(e->e.getId()==id)
                .findFirst()
                .orElseThrow(() ->new IllegalArgumentException("ID not present.")));

         */
        EmployeeDto emp = employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ID not present."));
        model.put("employee", emp);
//        String sd=emp.getStartDateAtTheCompany().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//        model.put("startDate", sd);
        return "modify";

    }

    @PostMapping("/modify/{id}")
    public String modifyEmployeePost(@PathVariable long id, Map<String, Object> model, EmployeeDto employee) {
        for (int i = 0; i < employees.size(); i++)
            if (employees.get(i).getId() == id)
                employees.set(i, employee);
        return "redirect:/employees";
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable long id) {
        for (int i = 0; i < employees.size(); i++)
            if (employees.get(i).getId() == id)
                employees.remove(i);
        return "redirect:/employees";
    }

}
