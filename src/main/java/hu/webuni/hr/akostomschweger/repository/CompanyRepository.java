package hu.webuni.hr.akostomschweger.repository;

import hu.webuni.hr.akostomschweger.model.AverageSalaryByPosition;
import hu.webuni.hr.akostomschweger.model.Company;
import hu.webuni.hr.akostomschweger.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Long countById(Long id);

    @Modifying
    @Query(value = "truncate table company",
            nativeQuery = true)
    void truncate();

    @Query("SELECT DISTINCT c FROM Company c JOIN c.employees e WHERE e.salary> :minSalary")
    public List<Company> findByEmployeeWithSalaryHigherThan(int minSalary);

    @Query("SELECT c from Company c WHERE SIZE(c.employees) > :minEmployeeCount")
    public List<Company> findByEmployeeCountHigherThan(int minEmployeeCount);

    @Query("SELECT e.position AS position, avg(e.salary) as averageSalary "
    + "FROM Company c "
    + "INNER JOIN c.employees e "
    + "WHERE c.id= :companyId "
    + "GROUP BY e.position "
    + "ORDER BY avg(e.salary) DESC ")
    public List<AverageSalaryByPosition> findAverageSalaryByPosition(long companyId);

}
