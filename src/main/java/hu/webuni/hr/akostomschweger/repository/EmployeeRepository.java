package hu.webuni.hr.akostomschweger.repository;

import hu.webuni.hr.akostomschweger.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Long countById(Long id);

    List<Employee> findByPosition(String position);

    @Query(value = "SELECT e from Employee e where upper(e.name) like concat(upper(:prefix),'%')")
    List<Employee> findByNameStartingWith(@Param("prefix") String prefix);

    // List<Employee> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);




}
