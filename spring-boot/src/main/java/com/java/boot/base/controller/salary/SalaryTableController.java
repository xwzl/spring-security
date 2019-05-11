package com.java.boot.base.controller.salary;

import com.java.boot.base.model.Department;
import com.java.boot.base.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by sang on 2018/1/26.
 */
@RestController
@RequestMapping("/salary/table")
public class SalaryTableController {
    @Autowired
    DepartmentService departmentService;
    @RequestMapping("/deps")
    public List<Department> departments() {
        return departmentService.getAllDeps();
    }
}
