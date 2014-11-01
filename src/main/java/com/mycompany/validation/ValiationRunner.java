package com.mycompany.validation;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValiationRunner {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.net.URISyntaxException
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        
        Customer cust = new Customer();
        cust.setEmail("test");
        
        InputStream is = Files.newInputStream(
            Paths.get(ClassLoader.getSystemResource("constraints.xml").toURI()));
        
        ValidatorFactory vf = Validation.
            byDefaultProvider().
            configure().
            addMapping(is).
            buildValidatorFactory();
        
        Validator validator = vf.getValidator();
        
        Set<ConstraintViolation<Customer>> failures = validator.validate(cust);
        failures.stream().forEach(
            failure -> System.out.println(
                String.format("property name: %s, failure: %s", 
                    failure.getPropertyPath(), failure.getMessage())));
    }    
}
