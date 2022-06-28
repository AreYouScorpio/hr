package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.model.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;

public class EmployeeSpecifications {
    public static Specification<Employee> hasId(long id) {
                return (root, cq, cb) -> cb.equal(root.get(Employee_.id), id);
    }

    public static Specification<Employee> hasName(String name) {
        String newName = name.toLowerCase();
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Employee_.name)), newName + "%");

    // .as(String.class)
    }



    public static Specification<Employee> hasPosition(String position) {
        return (root, cq, cb) -> cb.equal(root.get(Position_.name.getName()), position);
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

    public static Specification<Employee> hasCompany(String companyName) {
        return (root, cq, cb) -> cb.like(root.get(Employee_.company.getName()), companyName + "%");
    }


}
