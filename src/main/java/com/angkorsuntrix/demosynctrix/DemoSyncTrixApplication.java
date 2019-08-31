package com.angkorsuntrix.demosynctrix;

import com.angkorsuntrix.demosynctrix.domain.Car;
import com.angkorsuntrix.demosynctrix.domain.Owner;
import com.angkorsuntrix.demosynctrix.domain.User;
import com.angkorsuntrix.demosynctrix.repository.CarRepository;
import com.angkorsuntrix.demosynctrix.repository.OwnerRepository;
import com.angkorsuntrix.demosynctrix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoSyncTrixApplication {

	@Autowired
	private CarRepository repository;
	@Autowired
    private OwnerRepository ownerRepository;
	@Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoSyncTrixApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                final Owner owner1 = new Owner("John", "Johnson");
                final Owner owner2 = new Owner("Mary", "Robinson");
                ownerRepository.save(owner1);
                ownerRepository.save(owner2);
				repository.save(new Car("Ford", "Mustang", "Red", "ADF-1121", 2017, 5900, owner1));
				repository.save(new Car("Ford", "Mustang", "Red", "ADF-1121", 2017, 5900, owner1));
				repository.save(new Car("Ford", "Mustang", "Red", "ADF-1121", 2017, 5900, owner2));
				repository.save(new Car("Ford", "Mustang", "Red", "ADF-1121", 2017, 5900, owner2));

				userRepository.save(new User("user", "$2a$04$1.YhMIgNX/8TkCKGFUONWO1waedKhQ5KrnB30fl0Q01QKqmzLf.Zi", "USER"));
				userRepository.save(new User("admin", "$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG", "ADMIN"));
            }
        };
    }

}
