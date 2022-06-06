package hu.webuni.hr.akostomschweger.repository;

import hu.webuni.hr.akostomschweger.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Long countById(Long id);

    List<Employee> findByPosition(String position);

    List<Employee> findByFirstnameStartingWithIgnorecase(String prefix);

    List<Employee> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);




}
