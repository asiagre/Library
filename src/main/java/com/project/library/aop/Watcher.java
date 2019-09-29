package com.project.library.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Watcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(Watcher.class);

    private int rentals = 0;
    private int returns = 0;

    @AfterReturning("execution(public * com.project.library.controller.RentalController.rentBook(..))")
    public void countRentals() {
        rentals++;
        LOGGER.info("The book has been rented. Current number of rentals: " + rentals);
    }

    @AfterReturning("execution(public * com.project.library.controller.RentalController.returnBook(..))")
    public void countReturns() {
        returns++;
        LOGGER.info("The book has been returned. Current number of returns " + returns);
    }
}
