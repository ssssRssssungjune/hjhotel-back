package com.hjhotelback.controller.staff;

import com.hjhotelback.service.staff.StaffService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }
}
