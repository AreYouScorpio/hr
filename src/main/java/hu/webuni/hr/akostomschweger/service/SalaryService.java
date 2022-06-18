package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.model.Employee;
import hu.webuni.hr.akostomschweger.model.PositionDetailsByCompany;
import hu.webuni.hr.akostomschweger.repository.EmployeeRepository;
import hu.webuni.hr.akostomschweger.repository.PositionDetailsByCompanyRepository;
import hu.webuni.hr.akostomschweger.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalaryService {


    private EmployeeService employeeService;
    private PositionRepository positionRepository;
    private PositionDetailsByCompanyRepository positionDetailsByCompanyRepository;
    private EmployeeRepository employeeRepository;

    /* old constructor

    public SalaryService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    ... and the new one -->
     */

    public SalaryService(EmployeeService employeeService, PositionRepository positionRepository, PositionDetailsByCompanyRepository positionDetailsByCompanyRepository, EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.positionRepository = positionRepository;
        this.positionDetailsByCompanyRepository = positionDetailsByCompanyRepository;
        this.employeeRepository = employeeRepository;
    }

    public int getPayRaisePercent(Employee employee) {
        return (int) (employee.getSalary()*(1+employeeService.getPayRaisePercent(employee)/100.00));
    }



    @Transactional
    public void raiseMinSalary(long companyId, String positionName, int minSalary) {
    PositionDetailsByCompany pd =             positionDetailsByCompanyRepository
                        .findByPositionNameAndCompanyId(positionName, companyId)
                .get();

    pd.setMinSalary(minSalary);
    //nem hatékony, sok SQL update utasítás
        /*
    pd.getCompany().getEmployees().forEach(e->{
        if(e.getPosition().getName().equals(positionName)&&e.getSalary()<minSalary)
            e.setSalary(minSalary);
    });

         */
        // 2. megoldás --->

                employeeRepository.updateSalaries(positionName, minSalary, companyId);


    }


}
