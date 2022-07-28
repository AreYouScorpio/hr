package hu.webuni.hr.akostomschweger.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import hu.webuni.hr.akostomschweger.config.AtConfigurationProperties;
import hu.webuni.hr.akostomschweger.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static hu.webuni.hr.akostomschweger.model.HolidayRequest_.employee;

@Service
public class JwtService {


    public static final String AUTH = "auth"; //**

    public static final String FULLNAME = "fullname";
    public static final String ID = "id";
    public static final String MANAGER = "manager";
    public static final String USERNAME = "username";
    public static final String MANAGED_EMPLOYEES = "managedEmployees";

    //yml beolvasáshoz írjuk: (tanári név: HrConfigProperties, nálam AtConfigurationProperties
    @Autowired
    private AtConfigurationProperties config;

    // az nem lenne jó, hogy itt
    // private String issuer = config.getJwt().getIssuer();
    // amikor az eleje meghívódik (a tagváltozó inicializáló), akk még az injektálás nem történt meg AutoWired-el
    // ezért fent írunk egy @PostConstruct annotációs metódust

    private Algorithm alg; //yml.ből , ezért törölve ez alg = Algorithm.HMAC256("mysecret"); //**
    private String issuer; //** //yml beolvasás miatt törölve ez: public static final String ISSUER = "HrApp";

    //így kell, h az injektálás után fusson le:
    @PostConstruct
    public void init() {
        AtConfigurationProperties.Jwt jwt = config.getJwt();
        issuer = jwt.getIssuer();
        try {
            alg = (Algorithm) Algorithm.class.getMethod(jwt.getAlg(), String.class)
                    .invoke(Algorithm.class, jwt.getSecret()); // az a metódus kell, ami stringet fogad el bemenő paraméterként
        }
        catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | IllegalArgumentException | SecurityException exception) {
            exception.printStackTrace();
        }
    }

    public String createJwtToken(UserDetails principal) {

        Employee employee = ((HrUser)principal).getEmployee(); //**
        Employee manager = employee.getManager(); //**
        Collection<Employee> managedEmployees = employee.getManagedEmployees(); //**

        Builder jwtBuilder = JWT.create()
                .withSubject(principal.getUsername())
                .withArrayClaim(AUTH,
                        principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                //egyéb claim-eket is rakunk bele:
                .withClaim(FULLNAME, employee.getName())
                .withClaim(ID, employee.getId());

        if(manager!=null) {
            jwtBuilder.withClaim(MANAGER, createEmployeeData(manager));
        }

        if(managedEmployees != null && !managedEmployees.isEmpty()) {
            jwtBuilder.withClaim(MANAGED_EMPLOYEES,
                    managedEmployees.stream().map(this::createEmployeeData)
                            .collect(Collectors.toList()));
        }


        /* yml beolvasás legyen a token lejárati időre ehelyett
        return jwtBuilder
                .withExpiresAt(new Date(System.currentTimeMillis()+ TimeUnit.MINUTES.toMillis(20))) //JWT token meddig éljen, 2 helyett 20 perc pl
                .withIssuer(issuer)
                .sign(alg);

         */
        return jwtBuilder
                .withExpiresAt(new Date(System.currentTimeMillis()+
                        config.getJwt().getExpiry().toMillis())) //JWT token meddig éljen, 2 helyett 20 perc pl
                .withIssuer(issuer)
                .sign(alg);
    }

    private Map<String, Object> createEmployeeData(Employee employee) {

    return Map.of(ID, employee.getId(),
            USERNAME, employee.getUsername());
    }

    public UserDetails parseJwt(String jwtToken) {

        DecodedJWT decodedJwt = JWT.require(alg)
                .withIssuer(issuer)
                .build()
                .verify(jwtToken);

        Employee employee = new Employee();
        employee.setId(decodedJwt.getClaim(ID).asLong());
        employee.setUsername(decodedJwt.getSubject());
        employee.setName(decodedJwt.getClaim(FULLNAME).asString());
        Claim managerClaim = decodedJwt.getClaim(MANAGER);
        if ( managerClaim != null ) {
            Map<String, Object> managerData = managerClaim.asMap();
            employee.setManager(parseEmployee(managerData));
        }

        Claim managedEmployeesClaim = decodedJwt.getClaim(MANAGED_EMPLOYEES);
        if (managedEmployeesClaim!=  null) {
            employee.setManagedEmployees(new ArrayList<>());
            List<HashMap> managedEmployees = managedEmployeesClaim.asList(HashMap.class);
            if (managedEmployees!= null) {
                for (HashMap employeeMap :managedEmployees) {
                    Employee managedEmployee = parseEmployee(employeeMap);
                    if(managedEmployee!=null) {
                        employee.getManagedEmployees().add(managedEmployee);
                    }
                }
            }
        }

        return new HrUser(decodedJwt.getSubject(),
                "dummy_barmi_lehet",
                decodedJwt
                        .getClaim(AUTH)
                        .asList(String.class)
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()), employee); // user jelszó kéne, de mivel most végezzük az autentikációt, később úgysem használjuk, de am később innét tudná
    }

    private Employee parseEmployee(Map<String, Object> employeeData) {

        if (employeeData !=null) {
            Employee employee = new Employee();
            employee.setId(((Integer) employeeData.get(ID)).longValue());
            employee.setUsername((String) employeeData.get(USERNAME));


            return new Employee();
        }
        return null;
    }
}
