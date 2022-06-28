package hu.webuni.hr.akostomschweger.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Position {

    @Id
    @GeneratedValue
    private int id;

    private String name;
    private Qualification qualification;

    @OneToMany(mappedBy = "position", fetch = FetchType.EAGER)
    private List<Employee> employees;

    public Position() {
    }

    public Position(String name, Qualification qualification) {
        this.name = name;
        this.qualification = qualification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", qualification=" + qualification +
                '}';
    }
}
