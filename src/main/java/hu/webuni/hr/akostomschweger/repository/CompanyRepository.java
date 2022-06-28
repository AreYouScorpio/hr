package hu.webuni.hr.akostomschweger.repository;

import hu.webuni.hr.akostomschweger.model.AverageSalaryByPosition;
import hu.webuni.hr.akostomschweger.model.Company;
import hu.webuni.hr.akostomschweger.model.Employee;
import org.springframework.data.jpa.repository.*;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> ,
        JpaSpecificationExecutor<Company> {

    Long countById(Long id);

    @Modifying
    @Query(value = "truncate table company",
            nativeQuery = true)
    void truncate();

    @Query("SELECT DISTINCT c FROM Company c JOIN c.employees e WHERE e.salary> :minSalary")
    public List<Company> findByEmployeeWithSalaryHigherThan(int minSalary);

    @Query("SELECT c from Company c WHERE SIZE(c.employees) > :minEmployeeCount")
    public List<Company> findByEmployeeCountHigherThan(int minEmployeeCount);

    // open-in-view: false az application.yml-ben, másképp biztosítjuk a memóriába töltődést:
    // v1: @Query("SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.employees")
    // v2: @EntityGraph(attributePaths = "employees")
    // v2: @Query("SELECT c FROM Company c")
    @EntityGraph("Company.full") //v3, ha a Company-ra NamedEntityGraph-ot tettünk !
    @Query("SELECT c FROM Company c") //v3
    public List<Company> findAllWithEmployees();

    //e.position -> e.position.name a position entitás bevezetésével:
    @Query("SELECT e.position.name AS position, avg(e.salary) as averageSalary "
            + "FROM Company c "
            + "INNER JOIN c.employees e "
            + "WHERE c.id= :companyId "
            + "GROUP BY e.position.name "
            + "ORDER BY avg(e.salary) DESC ")
    public List<AverageSalaryByPosition> findAverageSalaryByPosition(long companyId);

}
