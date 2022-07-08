package hu.webuni.hr.akostomschweger.repository;

import hu.webuni.hr.akostomschweger.model.HolidayRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface HolidayRequestRepository extends JpaRepository<HolidayRequest, Long>, JpaSpecificationExecutor<HolidayRequest> {
}
