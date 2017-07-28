package com.gft.employee.configuration;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * TODO Document this!
 *
 * @author Ruben Jimenez
 */
@Profile({"dev", "local"})
@EnableDiscoveryClient
@Configuration
public class EurekaConfiguration {
}
