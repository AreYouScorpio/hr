package hu.webuni.hr.akostomschweger.web;

import hu.webuni.hr.akostomschweger.dto.EmployeeDto;
import hu.webuni.hr.akostomschweger.mapper.EmployeeMapper;
import hu.webuni.hr.akostomschweger.model.Employee;
import hu.webuni.hr.akostomschweger.repository.EmployeeRepository;
import hu.webuni.hr.akostomschweger.service.EmployeeService;
import hu.webuni.hr.akostomschweger.service.EmployeeSuperClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

//import static com.sun.beans.introspect.PropertyInfo.Name.required;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {


    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    /* old, before Pageable ---->
    @GetMapping
    public List<EmployeeDto> getAll() {
        return employeeMapper.employeesToDtos(employeeService.findAll());
        //return new ArrayList<>(employees.values());
    }
    */


    // new--- after Pageable and including minSalary:
    @GetMapping
    public List<EmployeeDto> getAll(@RequestParam(required = false) Integer minSalary,
                                     @PageableDefault(sort = {"id"})
                                     Pageable pageable) {
        // már nem List, hanem Page of Employees lesz a típusa:
        // ehelyett:
        // List<Employee> employees = null;
        Page<Employee> employeesPage = null; // employees helyett employeesPage nevet kap:
        if (minSalary == null) {
            // már inkább employeerepositoryn hívjuk ehelyett, átadva neki a pageable-t:
            // employees = employeeService.findAll();
            // névváltoztatás: employees = employeeRepository.findAll(pageable);
            employeesPage = employeeRepository.findAll(pageable);
        } else {
            // névváltoztatás: employees = employeeRepository.findBySalaryGreaterThan(minSalary);
            employeesPage = employeeRepository.findBySalaryGreaterThan(minSalary, pageable);
            //ehhez a repoba is beletenni egy page-eset is
        }
        // infokat kiiratjuk:
        System.out.println(employeesPage.getTotalElements());
        System.out.println(employeesPage.getTotalPages());
        System.out.println(employeesPage.isLast()); // ő-e az utolsó oldal
        System.out.println(employeesPage.isFirst()); // ő-e az utolsó oldal

        //return employeeMapper.employeesToDtos(employeeService.findAll());
        //névváltoztatás: return employeeMapper.employeesToDtos(employees);
        // már tartalmat adunk vissza: return employeeMapper.employeesToDtos(employeesPage);
        return employeeMapper.employeesToDtos(employeesPage.getContent());
        //return new ArrayList<>(employees.values());
    }




    @GetMapping("/position/{position}")
    public List<EmployeeDto> findByPosition(@PathVariable String position) {
        List<Employee> employees = employeeService.findByPosition(position);
        return employeeMapper.employeesToDtos(employees);

    }

    @GetMapping("/prefix/{prefix}")
    public List<EmployeeDto> findByNameStartingWith(@PathVariable String prefix) {
        List<Employee> employees = employeeService
                .findByNameStartingWith(prefix);
        return employeeMapper.employeesToDtos(employees);

    }

    @GetMapping("/date/{startDate}/{endDate}")
    public List<EmployeeDto> findByStartDateBetween(@PathVariable String startDate, @PathVariable String endDate) {
        List<Employee> employees = employeeService
                .findByStartDateBetween(LocalDateTime.parse(startDate), LocalDateTime.parse(endDate));
//                        LocalDateTime.of(2000,1,1,10,10)
//                        , LocalDateTime.of(2002,1,1,10,10));
        return employeeMapper.employeesToDtos(employees);

    }





    @GetMapping("/{id}")
    public EmployeeDto getById(@PathVariable long id) {
        Employee employee = employeeService.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        //EmployeeDto employeeDto = employeeSuperClass.get(id);
        // if (employeeDto != null)
        //    return ResponseEntity.ok(employeeDto);
        // else
        //    return ResponseEntity.notFound().build();
        /*
        if (employee!=null)
            return employeeMapper.employeeToDto(employee);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    */
        return employeeMapper.employeeToDto(employee);

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
       Employee employee = employeeService.save(employeeMapper.dtoToEmployee(employeeDto));
        return employeeMapper.employeeToDto(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable long id) {
        employeeService.delete(id);

    }
    /*
    @PostMapping
    public EmployeeDto createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        employees.put(employeeDto.getId(), employeeDto);
        return employeeDto;
    }
    */

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> modifyEmployee(@PathVariable long id,
                                    @RequestBody @Valid EmployeeDto employeeDto) {

        //Employee employee = employeeService.findById(id);

        Employee employee = employeeMapper.dtoToEmployee(employeeDto);
        employee.setId(id); // hogy tudjunk módosítani azonos iata-jút a uniqecheck ellenére
        try {
            EmployeeDto savedEmployeeDto = employeeMapper.employeeToDto(employeeService.update(id, employee));
            return ResponseEntity.ok(savedEmployeeDto);
        }
        catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        /*
        if (employee != null)
            employeeService.update(id, employeeMapper.dtoToEmployee(employeeDto));
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
         */

        // return employeeMapper.employeeToDto(employee);


    }



    /* !!! ----> old version before MapStruct added
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

    */

    /*

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
*/
    @PostMapping("/payRaise")
    public int getPayRaisePercent(@RequestBody Employee employee) {
        return employeeService.getPayRaisePercent(employee);
    }


}



