package com.project.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAspectJAutoProxy
//@EnableJpaRepositories(basePackages ="com.project.library.repository")
//public class LibraryApplication {
public class LibraryApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LibraryApplication.class);
    }

}
