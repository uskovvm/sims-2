package com.carddex.sims2.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.carddex.sims2.service" })
public class ServiceConfig {
}
