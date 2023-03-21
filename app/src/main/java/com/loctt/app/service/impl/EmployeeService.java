/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.service.impl;

import com.loctt.app.model.Employee;
import com.loctt.app.model.Role;
import com.loctt.app.repository.IEmployeeRepository;
import com.loctt.app.service.IEmployeeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private IEmployeeRepository employeeRepository;

    @Override
    public Employee findByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    @Override
    public Employee findByEmployeeIDForSearch(String employeeID) {
        return employeeRepository.findByEmployeeIDAndRoleRoleNameNotAndStatus(employeeID, "ADMIN", true);
    }

    @Override
    public List<Employee> findByNameContainingForSearch(String fullName) {
        return employeeRepository.findByFullNameContainingAndRoleRoleNameNotAndStatus(fullName, "ADMIN", true);
    }

    @Override
    public List<Employee> findAllForSearch() {
        return employeeRepository.findByStatusAndRoleRoleNameNot(true, "ADMIN");
    }

    @Override
    public Employee findByEmployeeID(String employeeID) {
        return employeeRepository.findByEmployeeID(employeeID);
    }

    @Override
    public void createNewEmployee(String username, String employeeRole, String employeeName, String employeePhone, String employeeMail, String employeeAddress, float employeeSalary) {
        String password = "123456";
        BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
        String encodedPassword = pwdEncoder.encode(password);
        Role role = new Role();
        if (employeeRole.equalsIgnoreCase("admin")) {
            role.setRoleID("1");
            role.setRoleName("ADMIN");
        }
        if (employeeRole.equalsIgnoreCase("shipper")) {
            role.setRoleID("3");
            role.setRoleName("DELIVERY_MAN");
        }
        if (employeeRole.equalsIgnoreCase("storage")) {
            role.setRoleID("2");
            role.setRoleName("STORAGE_MAN");
        }
        Employee newEmployee = new Employee("Default", username, encodedPassword, employeeName,
                employeePhone, employeeMail, employeeAddress, employeeSalary, true, role);
        employeeRepository.save(newEmployee);
    }

    @Override
    public void updateEmployeeByAdmin(String employeeID, String employeeRole, float employeeSalary) {
        Employee employee = employeeRepository.findByEmployeeID(employeeID);
        if (!employeeRole.trim().isEmpty()) {
            Role role = new Role();
            if (employeeRole.equalsIgnoreCase("admin")) {
                role.setRoleID("1");
                role.setRoleName("ADMIN");
            }
            if (employeeRole.equalsIgnoreCase("shipper")) {
                role.setRoleID("3");
                role.setRoleName("DELIVERY_MAN");
            }
            if (employeeRole.equalsIgnoreCase("storage")) {
                role.setRoleID("2");
                role.setRoleName("STORAGE_MAN");
            }
            employee.setRole(role);
        }
        employee.setSalary(employeeSalary);
        employeeRepository.save(employee);
    }

    @Override
    public void deleteById(String employeeID) {
        Employee employee = employeeRepository.findByEmployeeID(employeeID);
        employee.setStatus(false);
        employeeRepository.save(employee);
    }

    @Override
    public void resetPassword(String employeeID) {
        Employee employee = employeeRepository.findByEmployeeID(employeeID);
        String password = "123456";
        BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
        String encodedPassword = pwdEncoder.encode(password);
        employee.setPassword(encodedPassword);
        employeeRepository.save(employee);
    }
    
    @Override
    public void updatePassword(Employee emp, String newPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String newPassEncode = encoder.encode(newPassword);
        emp.setPassword(newPassEncode);
        employeeRepository.save(emp);
    }
}
