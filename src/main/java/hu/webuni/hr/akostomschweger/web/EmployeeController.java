package hu.webuni.hr.akostomschweger.web;

import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.mapper.EmployeeMapper;
import hu.webuni.hr.akostomschweger.model.Employee;
import hu.webuni.hr.akostomschweger.service.EmployeeService;
import hu.webuni.hr.akostomschweger.service.EmployeeSuperClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {


    @Autowired
    private EmployeeSuperClass employeeSuperClass;

    @Autowired
    EmployeeMapper employeeMapper;


    @GetMapping
    public List<EmployeeDto> getAll() {
        return employeeMapper.employeesToDtos(employeeSuperClass.findAll());
        //return new ArrayList<>(employees.values());
    }


    @GetMapping("/{id}")
    public EmployeeDto getById(@PathVariable long id) {
        Employee employee = employeeSuperClass.findById(id);
        //EmployeeDto employeeDto = employeeSuperClass.get(id);
        // if (employeeDto != null)
        //    return ResponseEntity.ok(employeeDto);
        // else
        //    return ResponseEntity.notFound().build();
        if (employee!=null)
            return employeeMapper.employeeToDto(employee);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    /*
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable long id) {
        EmployeeDto employeeDto = employees.get(id);
        if (employeeDto != null)
            return ResponseEntity.ok(employeeDto);
        else
            return ResponseEntity.notFound().build();
    }

     */


    @PostMapping
    public EmployeeDto createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
       Employee employee = employeeSuperClass.save(employeeMapper.dtoToEmployee(employeeDto));
        return employeeMapper.employeeToDto(employee);
    }

    /*
    @PostMapping
    public EmployeeDto createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        employees.put(employeeDto.getId(), employeeDto);
        return employeeDto;
    }
    */

    /* !!!
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> modifyEmployee(@PathVariable long id,
                                                      @RequestBody EmployeeDto employeeDto) {
        if (!employees.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        employeeDto.setId(id);
        employees.put(id, employeeDto);
        return ResponseEntity.ok(employeeDto);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable long id) {
        employees.remove(id);
    }

    @GetMapping("/list")
    public List<EmployeeDto> getSalaryLimitOver(@RequestParam int minSalary) {

        return new ArrayList<>(employees.values().stream()
                .filter(employeeDto -> employeeDto.getSalary() > minSalary)
                .collect(Collectors.toList()));
    }

    @PostMapping("/payRaise")
    public int getPayRaisePercent(@RequestBody Employee employee) {
        return employeeService.getPayRaisePercent(employee);
    }

     */
}



