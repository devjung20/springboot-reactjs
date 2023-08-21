package com.sapo.edu.demo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Xin mời nhập: ");
        String string = scanner.nextLine();
        boolean contained1 = StringUtils.containsAny(string, 'k');
        boolean contained2 = StringUtils.containsAnyIgnoreCase(string, "Thế");
        int charNumber = StringUtils.countMatches(string, 'l');
        String stringWithSunffix = StringUtils.appendIfMissing(string, "'S");
        String stringWithSunffix2 = StringUtils.prependIfMissing(string, "1-");
        String upperCaseString = StringUtils.upperCase(string);
        String lowerCaseString = StringUtils.lowerCase(string);
        System.out.println(contained1);
        System.out.println(contained2);
        System.out.println(charNumber);
        System.out.println(stringWithSunffix);
        System.out.println(stringWithSunffix2);
        System.out.println(upperCaseString);
        System.out.println(lowerCaseString);
    }
}
