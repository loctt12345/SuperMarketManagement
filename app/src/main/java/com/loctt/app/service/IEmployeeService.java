/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.service;

import com.loctt.app.model.Employee;

/**
 *
 * @author ADMIN
 */
public interface IEmployeeService {
    public Employee findByUsername(String username);
}
