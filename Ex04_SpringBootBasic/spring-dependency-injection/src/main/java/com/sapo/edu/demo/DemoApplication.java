package com.sapo.edu.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    @Autowired
    private BidvAtm bidvAtm;

    @Value("${atm.money}")
    private BigDecimal initialBalance;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Customer> customers = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int luaChon;
        do {
            System.out.println("Chọn chức năng");
            System.out.println("1. Nhập thông tin");
            System.out.println("2. Rút tiền");
            System.out.println("3. Gửi tiền");
            System.out.println("4. Số dư hiện tại");
            System.out.println("5. Hiển thị thông tin");
            System.out.println("6. Thoát khởi chương trình");
            luaChon = sc.nextInt();
            sc.nextLine();
            switch (luaChon) {
                case 1:
                    System.out.println("Xin mời nhập thông tin");
                    System.out.println("Nhập số tài khoản: ");
                    String accountNumber = sc.nextLine();
                    System.out.println("Nhập mã pin: ");
                    String pinCode = sc.nextLine();
                    System.out.println("Số tiền khởi tạo: " + "\n" + initialBalance);
                    BigDecimal initialBalance1 = initialBalance;
                    Customer customer = new Customer(accountNumber, pinCode, initialBalance1);
                    customers.add(customer);
                    break;
                case 2:
                    if (!customers.isEmpty()) {
                        System.out.println("Nhập số tiền muốn rút: ");
                        BigDecimal withdrawAmount = sc.nextBigDecimal();
                        sc.nextLine();
                        bidvAtm.withDraw(customers.get(0), withdrawAmount);
                    } else {
                        System.out.println("Vui lòng nhập thông tin trước khi rút tiền");
                    }
                    break;
                case 3:
                    if (!customers.isEmpty()) {
                        System.out.println("Nhập số tiền muốn gửi: ");
                        BigDecimal depositAmount = sc.nextBigDecimal();
                        sc.nextLine();
                        bidvAtm.deposit(customers.get(0), depositAmount);
                    } else {
                        System.out.println("Vui lòng nhập thông tin trước khi rút tiền");
                    }
                    break;
                case 4:
                    bidvAtm.printCurrentMoney();
                    break;
                case 5:
                    if (!customers.isEmpty()) {
                        bidvAtm.displayCustomerInfo(customers.get(0));
                    } else {
                        System.out.println("Vui lòng nhập thông tin trước khi hiển thị.");
                    }
                    break;
                default:
                    System.out.println("Chức năng không tồn tại! vui lòng chọn lại");
                    break;
            }
        } while (luaChon != 6);
    }
}
