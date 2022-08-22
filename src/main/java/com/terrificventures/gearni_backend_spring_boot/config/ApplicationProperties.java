package com.terrificventures.gearni_backend_spring_boot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Gearni Backend Spring Boot.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {}
