//package com.hjhotelback.config;
//
//import com.hjhotelback.entity.staff.StaffEntity;
//import com.hjhotelback.mapper.staff.StaffMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class AdminInitializer implements CommandLineRunner {
//
//    private final StaffMapper staffMapper;
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) {
//        String adminUserId = "admin";
//        String encodedPassword = passwordEncoder.encode("1234");
//
//        StaffEntity admin = new StaffEntity();
//        admin.setStaffUserId(adminUserId);
//        admin.setPassword(encodedPassword);
//        admin.setName("Administrator");
//        admin.setRoleId(1); // role_id 1은 ADMIN
//        admin.setPosition("ADMIN");
////        admin.setIsActive(true);
//
//        if (staffMapper.findByStaffUserId(adminUserId) == null) {
//            staffMapper.insertAdmin(admin);
//            System.out.println("Admin account initialized.");
//        } else {
//            System.out.println("Admin account already exists.");
//        }
//    }
//}
