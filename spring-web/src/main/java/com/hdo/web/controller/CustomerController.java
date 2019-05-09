package com.hdo.web.controller;

import com.hdo.web.dao.DepartmentDao;
import com.hdo.web.dao.EmployeeDao;
import com.hdo.web.bean.Department;
import com.hdo.web.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @ClassName CustomerController
 * @Author XWZ
 * @Description
 * @Date 2018-08-23 20:42 星期四 01-SpringBoot-Web
 * @VERSION 1.0.0
 **/
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DepartmentDao departmentDao;

    @GetMapping("/emps")
    public String getEmployees(Model model){
        Collection<Employee> list=employeeDao.getAll();
        model.addAttribute("emps",list);
        return "emp/list";
}

    @GetMapping("/emp")
    public String toAddPage(Model model){
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        return "emp/add";
    }

    @PostMapping("/emp")
    public String addPage(Employee employee){
        employeeDao.save(employee);
        return "redirect:/customer/emps";
    }

    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id")Integer id, Model model){
        Employee employee = employeeDao.get(id);
        model.addAttribute("emp",employee);
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        return "emp/add";
    }

    //员工修改 通过请求方式区分路径映射
    @PutMapping("/emp")
    public String toEdit(Employee employee){
        employeeDao.save(employee);
        return "redirect:/customer/emps";
    }

    @DeleteMapping("/delete/{id}")
    public String toDelete(@PathVariable("id")Integer id){
         employeeDao.delete(id);
         return "redirect:/customer/emps";
    }

}
