/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.controller;

import com.loctt.app.model.Employee;
import com.loctt.app.model.ProductDetails;
import com.loctt.app.service.IOrderService;
import com.loctt.app.service.impl.EmployeeService;
import com.loctt.app.service.impl.OrderService;
import com.loctt.app.service.impl.ProductService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import javafx.util.Pair;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author ADMIN
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    //Map ProductDetails
    @Autowired
    private ProductService productService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private IOrderService orderService;

    @GetMapping("/findProduct")
    public String findProducts(@RequestParam(required = false) Map<String, String> allParams, Model model) {
        List<ProductDetails> productDetails = new ArrayList<>();
        String searchBy = allParams.get("searchBy");
        String searchValue = allParams.get("searchValue");
        if (searchBy != null && searchValue != null) {
            //search All Products by Category
            if (searchBy.equalsIgnoreCase("category") && this.productService.findByCategoryContaining(searchValue) != null) {
                productDetails = this.productService.findByCategoryContaining(searchValue);
            }
            //search All Products by Name
            if (searchBy.equalsIgnoreCase("name") && this.productService.findByNameContaining(searchValue) != null) {
                productDetails = this.productService.findByNameContaining(searchValue);
            }
            //search All Products by ID
            if (searchBy.equalsIgnoreCase("id") && this.productService.findByProductID(searchValue) != null) {
                productDetails.add(this.productService.findByProductID(searchValue));
            }
            if (productDetails.size() > 0) {
                model.addAttribute("PRODUCTS_RESULT", productDetails);
                model.addAttribute("numPage", productDetails.size() / 6 + 1);
            }
            model.addAttribute("searchValue", searchValue);
            model.addAttribute("searchBy", searchBy);
        }
        return "products_management";
    }

    //add new Product
    @PostMapping("/add-new-product")
    public String addNewProduct(@RequestParam Map<String, String> allParams, Model model, RedirectAttributes redirectAttributes) {
        String productID = allParams.get("id");
        String productName = allParams.get("name");
        String productDes = allParams.get("description");
        String productCategory = allParams.get("category");
        String productImageLink = allParams.get("image");
        String sellPriceAsString = allParams.get("sellPrice");
        if (productService.findByProductID(productID) != null) {
            model.addAttribute("ErrorAction", "Product already have in storage");
            return "products_management";
        }
        for (String param : allParams.keySet()) {
            if (allParams.get(param).trim().isEmpty() && !param.equalsIgnoreCase("lastSearchValue") && !param.equalsIgnoreCase("lastSearchBy")) {
                model.addAttribute("ErrorAction", "Please enter valid values in fields");
                return "products_management";
            }
        }
        float productSellPrice = 0;
        try {
            productSellPrice = Float.parseFloat(sellPriceAsString);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        ProductDetails newProduct = new ProductDetails(productID, productName, productDes, productCategory, productSellPrice, productImageLink);
        newProduct.setStatus(true);
        this.productService.save(newProduct);
        redirectAttributes.addAttribute("searchBy", "id");
        redirectAttributes.addAttribute("searchValue", productID);
        return "redirect:/admin/findProduct";
    }

    @PostMapping("/update-product")
    public String updateProduct(@RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes) {
        String productID = allParams.get("productID");
        String productName = allParams.get("productName");
        if (productName.trim().isEmpty()) {
            productName = productService.findByProductID(productID).getName();
        }
        String sellPriceAsString = allParams.get("sellPrice");
        float sellPrice = 0;
        if (sellPriceAsString.trim().isEmpty()) {
            sellPrice = productService.findByProductID(productID).getSellprice();
        } else {
            try {
                sellPrice = Float.parseFloat(sellPriceAsString);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        ProductDetails updateProduct = new ProductDetails();
        updateProduct.setProductID(productID);
        updateProduct.setName(productName);
        updateProduct.setCategory(productService.findByProductID(productID).getCategory());
        updateProduct.setDescription(productService.findByProductID(productID).getDescription());
        updateProduct.setImageLink(productService.findByProductID(productID).getImageLink());
        updateProduct.setSellprice(sellPrice);
        updateProduct.setStatus(true);
        productService.save(updateProduct);
        String lastSearchValue = allParams.get("lastSearchValue");
        String lastSearchBy = allParams.get("lastSearchBy");
        if (lastSearchBy.isEmpty() || lastSearchValue.isEmpty()) {
            return "redirect:/admin-page";
        } else {
            redirectAttributes.addAttribute("searchValue", lastSearchValue);
            redirectAttributes.addAttribute("searchBy", lastSearchBy);
            return "redirect:/admin/findProduct";
        }
    }

    @GetMapping("/delete-product")
    public String deleteProductById(@RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes) {
        String productID = allParams.get("id");
        if (productID != null && this.productService.findByProductID(productID) != null) {
            this.productService.deleteById(productID);
        }
        String lastSearchValue = allParams.get("lastSearchValue");
        String lastSearchBy = allParams.get("lastSearchBy");
        if (lastSearchBy == null || lastSearchValue == null
                || lastSearchBy.isEmpty()
                || lastSearchValue.isEmpty()) {
            return "redirect:/admin-page";
        } else {
            redirectAttributes.addAttribute("searchValue", lastSearchValue);
            redirectAttributes.addAttribute("searchBy", lastSearchBy);
            return "redirect:/admin/findProduct";
        }
    }

    @GetMapping("/findEmployee")
    public String findEmployees(@RequestParam(required = false) Map<String, String> allParams, Model model, HttpSession session) {
        List<Employee> employeeList = new ArrayList<>();
        String searchBy = allParams.get("searchBy");
        String searchValue = allParams.get("searchValue");
        if (searchBy != null && searchValue != null) {
            //search All Products by Category
            if (searchBy.equalsIgnoreCase("id")) {
                Employee emp = this.employeeService.findByEmployeeIDForSearch(searchValue);
                if (emp != null) {
                    employeeList.add(emp);
                }
            }
            //search All Products by Name
            if (searchBy.equalsIgnoreCase("name") && this.employeeService.findByNameContainingForSearch(searchValue) != null) {
                employeeList = this.employeeService.findByNameContainingForSearch(searchValue);
            }
            if (employeeList.size() > 0) {
                model.addAttribute("EMPLOYEES_RESULT", employeeList);
                model.addAttribute("numPage", employeeList.size() / 6 + 1);
            }
            if (session.getAttribute("ResetPassEmp") != null) {
                model.addAttribute("ResetPassEmp", session.getAttribute("ResetPassEmp"));
                session.removeAttribute("ResetPassEmp");
            }
            model.addAttribute("searchValue", searchValue);
            model.addAttribute("searchBy", searchBy);
        }
        return "employee_management";
    }
    //add new Product

    @PostMapping("/add-new-employee")
    public String addNewEmployee(@RequestParam Map<String, String> allParams, Model model, RedirectAttributes redirectAttributes) {
        String employeeRole = allParams.get("role");
        String employeeName = allParams.get("name");
        String employeePhone = allParams.get("phoneNumber");
        String employeeMail = allParams.get("email");
        String employeeAddress = allParams.get("address");
        String employeeSalaryAsString = allParams.get("salary");
        String username = allParams.get("username");
        if (employeeService.findByUsername(username) != null) {
            model.addAttribute("ErrorAction", "Username is existed");
            return "employee_management";
        }
        for (String param : allParams.keySet()) {
            if (allParams.get(param).trim().isEmpty() && !param.equalsIgnoreCase("lastSearchValue") && !param.equalsIgnoreCase("lastSearchBy")) {
                System.out.println(param);
                model.addAttribute("ErrorAction", "Please enter valid values in fields");
                return "employee_management";
            }
        }
        float employeeSalary = 0;
        try {
            employeeSalary = Float.parseFloat(employeeSalaryAsString);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        employeeService.createNewEmployee(username, employeeRole, employeeName, employeePhone, employeeMail, employeeAddress, employeeSalary);
        redirectAttributes.addAttribute("searchBy", "id");
        redirectAttributes.addAttribute("searchValue", employeeService.findByUsername(username).getEmployeeID());
        return "redirect:/admin/findEmployee";
    }

    @PostMapping("/update-employee")
    public String updateEmployee(@RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes) {
        String employeeRole = allParams.get("role");
        String employeeSalaryAsString = allParams.get("employeeSalary");
        String employeeID = allParams.get("employeeID");
        float employeeSalary = 0;
        if (employeeSalaryAsString.trim().isEmpty()) {
            employeeSalary = employeeService.findByEmployeeID(employeeID).getSalary();
        } else {
            try {
                employeeSalary = Float.parseFloat(employeeSalaryAsString);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        employeeService.updateEmployeeByAdmin(employeeID, employeeRole, employeeSalary);
        String lastSearchValue = allParams.get("lastSearchValue");
        String lastSearchBy = allParams.get("lastSearchBy");
        if (lastSearchBy.trim().isEmpty() || lastSearchValue.trim().isEmpty()) {
            return "redirect:/admin-employee-page";
        } else {
            redirectAttributes.addAttribute("searchValue", lastSearchValue);
            redirectAttributes.addAttribute("searchBy", lastSearchBy);
            return "redirect:/admin/findEmployee";
        }
    }

    @GetMapping("/delete-employee")
    public String deleteEmployeeById(@RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes) {
        String employeeID = allParams.get("id");
        if (employeeID != null && this.employeeService.findByEmployeeID(employeeID) != null) {
            this.employeeService.deleteById(employeeID);
        }
        String lastSearchValue = allParams.get("lastSearchValue");
        String lastSearchBy = allParams.get("lastSearchBy");
        if (lastSearchBy == null || lastSearchValue == null
                || lastSearchBy.isEmpty()
                || lastSearchValue.isEmpty()) {
            return "redirect:/admin-employee-page";
        } else {
            redirectAttributes.addAttribute("searchValue", lastSearchValue);
            redirectAttributes.addAttribute("searchBy", lastSearchBy);
            return "redirect:/admin/findEmployee";
        }
    }

    @GetMapping("/reset-employee-password")
    public String resetEmployeePassword(@RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes, HttpSession session) {
        String employeeID = allParams.get("id");
        if (employeeID != null && this.employeeService.findByEmployeeID(employeeID) != null) {
            this.employeeService.resetPassword(employeeID);
            session.setAttribute("ResetPassEmp", employeeID);
        }
        String lastSearchValue = allParams.get("lastSearchValue");
        String lastSearchBy = allParams.get("lastSearchBy");
        if (lastSearchBy == null || lastSearchValue == null
                || lastSearchBy.isEmpty()
                || lastSearchValue.isEmpty()) {
            return "redirect:/admin-employee-page";
        } else {
            redirectAttributes.addAttribute("searchValue", lastSearchValue);
            redirectAttributes.addAttribute("searchBy", lastSearchBy);
            return "redirect:/admin/findEmployee";
        }
    }

    @GetMapping("/topproducts")
    public String getTopTenProducts(
            @RequestParam(name = "month", required = false, defaultValue = "1") int month,
            @RequestParam(name = "year", required = false, defaultValue = "2023") int year,
            Model model)
            throws Exception {
        List<Object[]> topProducts = orderService.getTotalSumProductsByMonth(month, year);
        List<Pair<ProductDetails, Integer>> map = new ArrayList<Pair<ProductDetails, Integer>>();
        for (int i = 0; i < topProducts.size(); i++) {
            String productID = topProducts.get(i)[0].toString();
            ProductDetails product = productService.findByProductID(productID);
            if (product != null) {
                Pair<ProductDetails, Integer> pair = new Pair<>(product, (int) topProducts.get(i)[1]);
                map.add(pair);
            }
        }
        model.addAttribute("MAP", map);
        model.addAttribute("MONTH", month);
        model.addAttribute("YEAR", year);
        return "top_products";
    }

}
