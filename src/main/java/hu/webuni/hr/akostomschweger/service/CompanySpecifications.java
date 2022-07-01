package hu.webuni.hr.akostomschweger.service;

import hu.webuni.hr.akostomschweger.model.Company;
import hu.webuni.hr.akostomschweger.model.Company_;
import hu.webuni.hr.akostomschweger.model.Employee_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class CompanySpecifications {

    public static Specification<Company> hasId(long id) {
        return (root, cq, cb) -> cb.equal(root.get(Company_.id), id);
    }


    public static Specification<Company> hasCompanyName(String companyName) {
        String newName = companyName.toLowerCase();
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Company_.name)), newName + "%");

    }


}
