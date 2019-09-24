package com.angkorsuntrix.demosynctrix;

import com.angkorsuntrix.demosynctrix.entity.User;
import com.angkorsuntrix.demosynctrix.repository.TopicRepository;
import com.angkorsuntrix.demosynctrix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoSyncTrixApplication {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TopicRepository topicRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoSyncTrixApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                userRepository.save(new User("user", "$2a$04$1.YhMIgNX/8TkCKGFUONWO1waedKhQ5KrnB30fl0Q01QKqmzLf.Zi", "USER"));
                userRepository.save(new User("admin", "$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG", "ADMIN"));
//                topicRepository.save(new Topic("/", "",   "home", "home"));
//                topicRepository.save(new Topic("/android", "",     "blog.post", "Android"));
//                topicRepository.save(new Topic("/kotlin", "",  "blog.post", "Kotlin"));
//                topicRepository.save(new Topic("/java", "",  "blog.post", "Java"));
            }
        };
    }

}
