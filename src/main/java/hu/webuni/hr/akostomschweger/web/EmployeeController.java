package hu.webuni.hr.akostomschweger.web;

import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private Map<Long, EmployeeDto> employees = new HashMap<>();

    {
        employees.put(1L, new EmployeeDto(
                1L,
                "Akos",
                "junior java developer",
                100000,
                LocalDateTime.of(2011,1,11,11,11))

        );
        employees.put(2L, new EmployeeDto(
                2L,
                "Bkos",
                "senior java developer",
                200000,
                LocalDateTime.of(2012,2,22,22,22))

        );    }

    @GetMapping
    public List<EmployeeDto> getAll(){
        return new ArrayList<>(employees.values());
    }


    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable long id){
        EmployeeDto employeeDto = employees.get(id);
        if (employeeDto!=null)
            return ResponseEntity.ok(employeeDto);
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {
        employees.put(employeeDto.getId(), employeeDto);
        return employeeDto;
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> modifyEmployee(@PathVariable long id,
                                                    @RequestBody EmployeeDto employeeDto) {
        if(!employees.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        employeeDto.setId(id);
        employees.put(id, employeeDto);
        return ResponseEntity.ok(employeeDto);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable long id){
        employees.remove(id);
    }

    @GetMapping("/list")
    public List<EmployeeDto> getSalaryLimitOver(@RequestParam int minSalary){

        return new ArrayList<>(employees.values().stream()
                .filter(employeeDto -> employeeDto.getSalary()>minSalary)
                .collect(Collectors.toList()));
    }

}



