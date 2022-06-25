package hu.webuni.hr.akostomschweger.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    //private String position;
    private int salary;

    private LocalDateTime startDateAtTheCompany;


    @ManyToOne
    private Position position; // minden ilyennél eldönthető, h a másik oldalt is felvesszük-e, célszerű

    @ManyToOne
    private Company company;

    public Employee(Long id, String name, int salary, LocalDateTime startDateAtTheCompany) {
        this.id = id;
        this.name = name;
        // this.position = position; -> a Position entity bevezetésével ez már nem kell redundánsan
        this.salary = salary;
        this.startDateAtTheCompany = startDateAtTheCompany;
    }

    public Employee(String name, int salary, LocalDateTime startDateAtTheCompany) {
        this.name = name;
        this.salary = salary;
        this.startDateAtTheCompany = startDateAtTheCompany;
    }

    public Employee() {
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*

    Position entitással a régi gettereit settereit is levesszük

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }


     */
    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }


    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public LocalDateTime getStartDateAtTheCompany() {
        return startDateAtTheCompany;
    }

    public void setStartDateAtTheCompany(LocalDateTime startDateAtTheCompany) {
        this.startDateAtTheCompany = startDateAtTheCompany;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
