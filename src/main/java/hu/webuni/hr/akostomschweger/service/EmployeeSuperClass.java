package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.model.Employee;
import hu.webuni.hr.akostomschweger.model.Position;
import hu.webuni.hr.akostomschweger.repository.EmployeeRepository;
import hu.webuni.hr.akostomschweger.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;

// itt ne legyen annotáció (teacher's hint) -- @Service
public abstract class EmployeeSuperClass implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // a Position entitás bevezetése után autowired kell ennek is:
    @Autowired
    private PositionRepository positionRepository;

    /*

        private Map<Long, Employee> employees = new HashMap<>();

        {
            employees.put(1L, new Employee(
                    1L,
                    "Akos",
                    "junior java developer",
                    100000,
                    LocalDateTime.of(2011, 1, 11, 11, 11))

            );
            employees.put(2L, new Employee(
                    2L,
                    "Bkos",
                    "senior java developer",
                    200000,
                    LocalDateTime.of(2012, 2, 22, 22, 22))

            );
        }


         */

    /* új save kell a Position entitás bevezetése után
    @Transactional
    public Employee save(Employee employee) {
        // employees.put(employee.getId(), employee);
        employee.setId(null); // biztos ami biztos ne felülírjon
        return employeeRepository.save(employee);// employee;
    }


    az új save --->
     */

    @Transactional
    public Employee save(Employee employee) {
        // employees.put(employee.getId(), employee);
        employee.setId(null); // biztos ami biztos ne felülírjon
        Position position = employee.getPosition();
        if (position!=null) {
            String positionName = position.getName();
            if(!ObjectUtils.isEmpty(positionName)) {
                Position positionInDb = null;
                Optional<Position> foundPosition = positionRepository.findByName(positionName);
                if(foundPosition.isPresent())
                    positionInDb = foundPosition.get();
                else {
                    positionInDb = positionRepository.save(position);
                }
                employee.setPosition(positionInDb);
            } else {
                employee.setPosition(null);
            }
        }
        return employeeRepository.save(employee);// employee;
    }




    public List<Employee> findAll() {
        //return new ArrayList<>(employees.values());
    return employeeRepository.findAll();
    }


    public Optional<Employee> findById(long id) {
        //return employees.get(id);
        return employeeRepository.findById(id);
    }

    @Transactional
    public Employee update(long id, Employee employee) {
        // employees.put(id, employee);
        // return employee;
    if(employeeRepository.existsById(employee.getId()))
        return employeeRepository.save(employee);
    else throw new NoSuchElementException();
    }

    @Transactional
    public void delete(long id) {
        // employees.remove(id);
    employeeRepository.deleteById(id);
    }

    public List<Employee> findByPosition(String position) {


        return employeeRepository.findByPositionName(position);
    }

    public List<Employee> findByNameStartingWith(String prefix) {

        return employeeRepository.findByNameStartingWith(prefix);
    }


    public List<Employee> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        System.out.println(startDate);
        return employeeRepository.findByStartDateBetween(startDate, endDate);
        //return new ArrayList<Employee>();
    }
}
