package com.sapo.edu.demo;

import org.springframework.stereotype.Component;

@Component
public class PrinterConsole implements Printer {
    @Override
    public void printCustomer(Customer customer) {
        System.out.println("Account number: " + customer.getAcctNo() + ", Pin code: " + customer.getPin()
                + ", balance: " + customer.getBalance().toString());
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }
}
