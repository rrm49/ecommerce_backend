package com.project.ecommerce.componets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class DataLoader implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    @Value("${load.initial.data}")
    private boolean loadInitialData;

    public DataLoader(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        if (loadInitialData) {
            System.out.println("Sample data insertion Started");

            ClassPathResource dataResource = new ClassPathResource("db/data.sql");
            if (dataResource.exists()) {
                String dataSql = new String(Files.readAllBytes(Paths.get(dataResource.getURI())));
                jdbcTemplate.execute(dataSql);
                System.out.println("Data executed from data.sql");
            }

            System.out.println("Sample data insertion Ended");

        } else {
            System.out.println("Skipping data insertion as the flag is not set.");
        }
    }
}
