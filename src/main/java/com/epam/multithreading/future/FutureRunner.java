package com.epam.multithreading.future;

import com.epam.multithreading.future.service.EmployeeService;
import com.epam.multithreading.future.service.PrintService;

public class FutureRunner {
    public static void main(String[] args) {
        PrintService printService = new PrintService(new EmployeeService());
        printService.printEmployeesSalary();
    }
}
