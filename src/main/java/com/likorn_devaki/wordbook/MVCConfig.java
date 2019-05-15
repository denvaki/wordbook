package com.likorn_devaki.wordbook;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.likorn_devaki.wordbook.controllers"})
public class MVCConfig { }