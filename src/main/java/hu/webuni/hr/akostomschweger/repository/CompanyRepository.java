package hu.webuni.hr.akostomschweger.repository;

import hu.webuni.hr.akostomschweger.model.Company;
import hu.webuni.hr.akostomschweger.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Long countById(Long id);

    @Modifying
    @Query(value = "truncate table company",
            nativeQuery = true)
    void truncate();

}
