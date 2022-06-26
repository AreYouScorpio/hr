package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.model.Employee;
import hu.webuni.hr.akostomschweger.model.Employee_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class EmployeeSpecifications {
    public static Specification<Employee> hasId(long id) {
        return (root, cq, cb) -> cb.equal(root.get(Employee_.id), id);
    }

    public static Specification<Employee> hasName(String name) {
        return (root, cq, cb) -> cb.like(root.get(Employee_.name), name + "%");
    }

    public static Specification<Employee> hasPosition(String position) {
        return (root, cq, cb) -> cb.equal(root.get(Employee_.position), position + "%");
    }

    public static Specification<Employee> hasSalary(int salary) {
        return (root, cq, cb) -> cb.greaterThan(root.get(Employee_.salary), salary);
    }


    public static Specification<Employee> hasStartDateTime(LocalDateTime startDateTime) {
        LocalDateTime startOfDay =
                LocalDateTime.of(startDateTime.toLocalDate(),
                        LocalTime.of(0, 0));
        return (root, cq, cb) ->
                cb.between(root.get(Employee_.startDateAtTheCompany),
                        startOfDay, startOfDay.plusDays(1));
    }

}
