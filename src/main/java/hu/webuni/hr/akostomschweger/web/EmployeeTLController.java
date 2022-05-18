package hu.webuni.hr.akostomschweger.web;

import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeTLController {

    private List<EmployeeDto> allEmployees = new ArrayList<>();

    {
        allEmployees.add(new EmployeeDto(1, "Akos", "java developer",
                100000, LocalDateTime.of(2020-1-15)));
    }


    @GetMapping("/")
    public String home() {

        return "index";
    }

    @GetMapping("/employees")
    public String listEmployees(Map<String,Object> model){
        model.put("employees", allEmployees);
        return "employees";

    }


}
