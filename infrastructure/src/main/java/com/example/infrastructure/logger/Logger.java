package com.example.infrastructure.logger;


import com.example.domain_services.outputports.ILogger;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Logger implements ILogger {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("[OnionArchi - App]");

    @Override
    public void log(String message) {
        logger.debug(message);

    }

    @Override
    public void errorLog(String message) {
        logger.error(message);
    }
}
