package hu.webuni.hr.akostomschweger.repository;

import hu.webuni.hr.akostomschweger.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Long countById(Long id);

    List<Employee> findBySalaryGreaterThan(int salary);
    Page<Employee> findBySalaryGreaterThan(int salary, Pageable pageable);

    List<Employee> findByPosition(String position);

    @Query(value = "SELECT e from Employee e where upper(e.name) like concat(upper(:prefix),'%')")
    List<Employee> findByNameStartingWith(@Param("prefix") String prefix);

    //@Query(value = "SELECT e from Employee e where e.start_date_at_the_company between :startDate and :endDate")
    // @Query(value = "SELECT e from Employee e where e.start_date_at_the_company > 2020-02-02")
    //@Query(value = "SELECT e from Employee e")
    //@Query(value = "SELECT * from Employee e where e.start_date_at_the_company < now() ", nativeQuery = true)
    @Query(value = "SELECT * from Employee e where e.start_date_at_the_company between :startDate and :endDate ", nativeQuery = true)
    List<Employee> findByStartDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
//  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)


    @Modifying
    @Query(value = "truncate table employee",
            nativeQuery = true)
    void truncate();


}
