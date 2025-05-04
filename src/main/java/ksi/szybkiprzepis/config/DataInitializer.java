package ksi.szybkiprzepis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ksi.szybkiprzepis.models.User;
import ksi.szybkiprzepis.services.UserService;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
       
        if (userService.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@szybkiprzepis.pl");
            admin.setPassword("admin123");
            admin.setRole("ADMIN");
            userService.save(admin);
            System.out.println("Utworzono konto administratora");
        }
    }
}