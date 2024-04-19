package com.example.webflux.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@EnableR2dbcRepositories
@EnableR2dbcAuditing
public class R2dbcConfig implements ApplicationListener<ApplicationReadyEvent> {
    private final DatabaseClient databaseClient;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        // reactor subscriber
        databaseClient.sql("SELECT 1").fetch().one()
                .subscribe(
                        success -> {
                            log.info("Initialize R2DBC DB Connection Success");
                        },
                        error -> {
                            log.error("Initialize R2DBC DB Connection Failed");
                            SpringApplication.exit(applicationReadyEvent.getApplicationContext(), () -> -110);
                        }
                );
    }
}
