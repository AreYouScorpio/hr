package hu.webuni.hr.akostomschweger.repository;

import hu.webuni.hr.akostomschweger.model.PositionDetailsByCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PositionDetailsByCompanyRepository extends JpaRepository<PositionDetailsByCompany, Long> {

    Optional<PositionDetailsByCompany> findByPositionNameAndCompanyId(
            String positionName, long companyId);

}
