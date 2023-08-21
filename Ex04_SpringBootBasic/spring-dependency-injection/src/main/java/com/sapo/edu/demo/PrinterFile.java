package com.sapo.edu.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Primary
@Component
public class PrinterFile implements Printer {
    @Value("${log.file-path}")
    private String filePath;


    @Override
    public void printCustomer(Customer customer) {
        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            fileWriter.write("Account number: " + customer.getAcctNo() + "\n");
            fileWriter.write("Pin code: " + customer.getPin() + "\n");
            fileWriter.write("Initial balance: " + customer.getBalance() + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printMessage(String message) {
        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            fileWriter.write(message + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
