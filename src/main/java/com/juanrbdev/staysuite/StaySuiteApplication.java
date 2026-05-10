package com.juanrbdev.staysuite;

import com.juanrbdev.staysuite.emuns.RolEnum;
import com.juanrbdev.staysuite.entity.Hotel;
import com.juanrbdev.staysuite.entity.User;
import com.juanrbdev.staysuite.repository.HotelRepository;
import com.juanrbdev.staysuite.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class StaySuiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaySuiteApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository repo, PasswordEncoder encoder, HotelRepository repohotel) {
        return args -> {

            if (repo.findByEmail("admin@staysuite.com").isEmpty()) {
                User admin = new User();
                admin.setEmail("admin@staysuite.com");
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("123456"));
                admin.setDni("00000001");
                admin.setFirstName("Admin");
                admin.setLastName("System");
                admin.setPhone("999999999");
                admin.setRole(RolEnum.ADMIN);
                admin.setState(true);
                repo.save(admin);
                System.out.println("✅ Admin creado");
            }

            if(repohotel.findByName("Hotel Stay Suite").isEmpty()) {
                Hotel hotel = new Hotel();
                hotel.setName("Hotel Stay Suite");
                hotel.setLocation("LIMA - PERU");
                hotel.setDescription("EXPERIENCE LUXURY");
                repohotel.save(hotel);

                System.out.println("Hotel Stay Suite creado");
            }

        };
    }




}
