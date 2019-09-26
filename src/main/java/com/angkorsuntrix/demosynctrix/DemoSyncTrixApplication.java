package com.angkorsuntrix.demosynctrix;

import com.angkorsuntrix.demosynctrix.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class DemoSyncTrixApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoSyncTrixApplication.class, args);
    }
}
