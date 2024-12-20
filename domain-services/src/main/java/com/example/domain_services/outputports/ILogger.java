package com.example.domain_services.outputports;

public interface ILogger {
    void log(String message);
    void errorLog(String message);
}