package hu.webuni.hr.akostomschweger.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private List<HolidayRequest> holidayRequests;

    @ManyToOne
    private Employee manager;

    @OneToMany(mappedBy = "manager") //mappedBy adja meg, ki az ő párja
    private Collection<Employee> managedEmployees;

    private String username;
    private String password;

    public Collection<Employee> getManagedEmployees() {
        return managedEmployees;
    }

    public void setManagedEmployees(List<Employee> managedEmployees) {
        this.managedEmployees = managedEmployees;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Employee(Long id, String name, int salary, LocalDateTime startDateAtTheCompany) {
        this.id = id;
        this.name = name;
        // this.position = position; -> a Position entity bevezetésével ez már nem kell redundánsan
        this.salary = salary;
        this.startDateAtTheCompany = startDateAtTheCompany;
    }


    public Employee(String name, String position, int salary, LocalDateTime startDateAtTheCompany) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", startDateAtTheCompany=" + startDateAtTheCompany +
                ", position=" + position +
                ", company=" + company +
                ", holidayRequests=" + holidayRequests +
                ", manager=" + manager +
                ", managedEmployees=" + managedEmployees +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setManagedEmployees(Collection<Employee> managedEmployees) {
        this.managedEmployees = managedEmployees;
    }

    public List<HolidayRequest> getHolidayRequests() {
        return holidayRequests;
    }

    public void setHolidayRequests(List<HolidayRequest> holidayRequests) {
        this.holidayRequests = holidayRequests;
    }

    public void addHolidayRequest(HolidayRequest holidayRequest) {
        if(this.holidayRequests == null)
            this.holidayRequests = new ArrayList<>();

        this.holidayRequests.add(holidayRequest);
        holidayRequest.setEmployee(this);
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }
}
