package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.dto.HolidayRequestFilterDto;
import hu.webuni.hr.akostomschweger.model.Employee;
import hu.webuni.hr.akostomschweger.model.HolidayRequest;
import hu.webuni.hr.akostomschweger.repository.HolidayRequestRepository;
import hu.webuni.hr.akostomschweger.security.HrUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service // - itt ne legyen annotáció, kül bean hiba miatt nem indul el .. vagy mégis kell?
public class HolidayRequestService {

    @Autowired
    HolidayRequestRepository holidayRequestRepository;

    @Autowired
    EmployeeService employeeService;

    public List<HolidayRequest> findAll() {

        return holidayRequestRepository.findAll();
    }

    public Optional<HolidayRequest> findById(long id) {
        return holidayRequestRepository.findById(id);
    }

    public Page<HolidayRequest> findHolidayRequestByExample(HolidayRequestFilterDto example, Pageable pageable) {
        LocalDateTime createDateTimeStart = example.getCreateDateTimeStart();
        LocalDateTime createDateTimeEnd = example.getCreateDateTimeEnd();
        String employeeName = example.getEmployeeName();
        String approvalName = example.getApproverName();
        Boolean approved = example.getApproved();
        LocalDate startOfHolidayRequest = example.getStartDate();
        LocalDate endOfHolidayRequest = example.getEndDate();

        Specification<HolidayRequest> spec = Specification.where(null);

        if (approved != null)
            spec = spec.and(HolidayRequestSpecifications.hasApproved(approved));


        if (createDateTimeStart != null && createDateTimeEnd != null)
            spec = spec.and(HolidayRequestSpecifications.createDateIsBetween(createDateTimeStart, createDateTimeEnd));
        if (StringUtils.hasText(employeeName))
            spec = spec.and(HolidayRequestSpecifications.hasEmployeeName(employeeName));
        if (StringUtils.hasText(approvalName))
            spec = spec.and(HolidayRequestSpecifications.hasApprovalName(approvalName));
        if (startOfHolidayRequest != null)
            spec = spec.and(HolidayRequestSpecifications.isEndDateGreaterThan(startOfHolidayRequest));
        if (endOfHolidayRequest != null)
            spec = spec.and(HolidayRequestSpecifications.isStartDateLessThan(endOfHolidayRequest));




       return holidayRequestRepository.findAll(spec, pageable);



    }


    @Transactional
    public HolidayRequest addHolidayRequest(HolidayRequest holidayRequest, long employeeId) {
        Employee employee = employeeService.findById(employeeId).get();
        employee.addHolidayRequest(holidayRequest);
        holidayRequest.setCreatedAt(LocalDateTime.now());
        return holidayRequestRepository.save(holidayRequest);
    }

    @Transactional
    public HolidayRequest approveHolidayRequest(long id, long approverId, boolean status) {
        HolidayRequest holidayRequest = holidayRequestRepository.findById(id).get();
        holidayRequest.setApprover(employeeService.findById(approverId).get());
        holidayRequest.setApproved(status);
        holidayRequest.setApprovedAt(LocalDateTime.now());
        return holidayRequest;
    }

    @Transactional
    public HolidayRequest modifyHolidayRequest(long id, HolidayRequest newHolidayRequest) {
        HolidayRequest holidayRequest = holidayRequestRepository.findById(id).get();
        if (holidayRequest.getApproved() != null)
            throw new IllegalStateException();
        holidayRequest.setEndDate(newHolidayRequest.getEndDate());
        holidayRequest.setStartDate(newHolidayRequest.getStartDate());
        holidayRequest.setCreatedAt(LocalDateTime.now());
        return holidayRequest;
    }

    @Transactional
    public void deleteHolidayRequest(long id) {
        HolidayRequest holidayRequest = holidayRequestRepository.findById(id).get();
        // holidayRequestController deletemapping-ra nem tudtunk @PreAuth-ot tenni, m csak id utazik, nem úgy, mint a holidayrequest létrehozásánál
        // emiatt itt checkoljuk, hogy a szabadságigényt magának hozza-e létre az employee
        // ---> erre írt metódus:
        checkOwnerOfHolidayRequest(holidayRequest);
        if (holidayRequest.getApproved() != null)
            throw new InvalidParameterException();
        holidayRequest.getEmployee().getHolidayRequests().remove(holidayRequest);
        holidayRequestRepository.deleteById(id);
    }

    private void checkOwnerOfHolidayRequest(HolidayRequest holidayRequest) {
        HrUser user = getCurrentUser();
        if(!holidayRequest.getEmployee().getId().equals(user.getEmployee().getId()))
            throw new AccessDeniedException("Holiday request not owned by current user."); //springes !!
    }

    private HrUser getCurrentUser() {
        return (HrUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}