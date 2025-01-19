package com.project.ecommerce.componets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Component
public class DataLoader implements CommandLineRunner {

    Logger logger = Logger.getLogger(getClass().getName());

    private final JdbcTemplate jdbcTemplate;

    @Value("${load.initial.data}")
    private boolean loadInitialData;

    public DataLoader(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        if (loadInitialData) {
            logger.info("Sample data insertion Started");

            ClassPathResource dataResource = new ClassPathResource("db/data.sql");
            if (dataResource.exists()) {
                String dataSql = new String(Files.readAllBytes(Paths.get(dataResource.getURI())));
                jdbcTemplate.execute(dataSql);
                logger.info("Data executed from data.sql");
            }

            logger.info("Sample data insertion Ended");

        } else {
            logger.info("Skipping data insertion as the flag is not set.");
        }
    }
}
