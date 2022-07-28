package hu.webuni.hr.akostomschweger.security;

import hu.webuni.hr.akostomschweger.model.Employee;
import hu.webuni.hr.akostomschweger.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class HrUserDetailsService implements UserDetailsService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(username));

        //return new HrUser(username, emp.getPassword(), // emp passwordjét adja vissza, amit HrUser-be wrappelek
                 //hrUser.getRoles().stream().map(SimpleGrantedAuthority::new)
                   //              .collect(Collectors.toList()));
                // nem volt követelmény, h a userhez/emphez tároljunk dinamikusan szerepköröket, ezért kikomizva
          // ehelyett:     Arrays.asList(new SimpleGrantedAuthority("USER")), emp);

        return new HrUser(username, employee.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority("USER")), employee);

        // ez adja vissza a HolidayRequestController-nek
        // a authentication.principal.employee.employeeId-t
        // ami a SecurityConfig @EnableGlobalMethodSecurity(prePostEnabled = true) -jával
        // van beállítva,h ellenőrizve legyen
        // az itt visszaadott HrUser lesz az authentication.principal
    }
}
