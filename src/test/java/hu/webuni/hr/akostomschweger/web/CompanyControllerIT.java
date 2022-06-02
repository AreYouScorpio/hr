package hu.webuni.hr.akostomschweger.web;

import hu.webuni.hr.akostomschweger.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles({"prod", "test"}) // profile switcher: both profiles on
public class CompanyControllerIT {

    @Autowired
    CompanyService companyService;


}
