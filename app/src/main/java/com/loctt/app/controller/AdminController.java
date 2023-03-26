/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.controller;

import com.loctt.app.model.Employee;
import com.loctt.app.model.PrimaryOrder;
import com.loctt.app.model.ProductDetails;
import com.loctt.app.service.IOrderService;
import com.loctt.app.service.IOrderStatusService;
import com.loctt.app.service.IProductRecommendationService;
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
    @Autowired
    private IProductRecommendationService productRecommendationService;
    @Autowired
    private IOrderStatusService orderStatusService;

    @GetMapping("/findProduct")
    public String findProducts(@RequestParam(required = false) Map<String, String> allParams, Model model) {
        List<ProductDetails> productDetails = new ArrayList<>();
        String searchBy = allParams.get("searchBy");
        String searchValue = allParams.get("searchValue");
        String errorFields = allParams.get("ErrorFields");
        String errorExisted = allParams.get("ErrorExisted");
        if (errorFields != null) {
            model.addAttribute("ErrorAction", "Vui lòng nhập giá trị hợp lệ!!!");
        }
        if (errorExisted != null) {
            model.addAttribute("ErrorAction", "Sản phẩm đã tồn tại!!!");
        }
        if (searchBy.trim().isEmpty()) {
            List<ProductDetails> listProduct = productService.findAll();
            model.addAttribute("PRODUCTS_RESULT", listProduct);
            return "products_management";
        }
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
        String lastSearchBy = allParams.get("lastSearchBy");
        String lastSearchValue = allParams.get("lastSearchValue");

        for (String param : allParams.keySet()) {
            if (allParams.get(param).trim().isEmpty() && !param.equalsIgnoreCase("lastSearchValue") && !param.equalsIgnoreCase("lastSearchBy")) {
                redirectAttributes.addAttribute("ErrorFields", true);
//                if (lastSearchBy.trim().isEmpty()) {
//                    return "redirect:/admin-page";
//                }
                redirectAttributes.addAttribute("searchBy", lastSearchBy);
                redirectAttributes.addAttribute("searchValue", lastSearchValue);
                return "redirect:/admin/findProduct";
            }
        }
        if (productService.findByProductID(productID) != null) {
            redirectAttributes.addAttribute("ErrorExisted", true);
//            if (lastSearchBy.trim().isEmpty()) {
//                return "redirect:/admin-page";
//            }
            redirectAttributes.addAttribute("searchBy", lastSearchBy);
            redirectAttributes.addAttribute("searchValue", lastSearchValue);
            return "redirect:/admin/findProduct";
        }
        float productSellPrice = 0;
        try {
            productSellPrice = Float.parseFloat(sellPriceAsString);
        } catch (NumberFormatException ex) {
            redirectAttributes.addAttribute("ErrorFields", true);
//            if (lastSearchBy.trim().isEmpty()) {
//                return "redirect:/admin-page";
//            }
            redirectAttributes.addAttribute("searchBy", lastSearchBy);
            redirectAttributes.addAttribute("searchValue", lastSearchValue);
            return "redirect:/admin/findProduct";
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
        String lastSearchValue = allParams.get("lastSearchValue");
        String lastSearchBy = allParams.get("lastSearchBy");

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
                redirectAttributes.addAttribute("ErrorFields", true);
//                if (lastSearchBy.trim().isEmpty()) {
//                    return "redirect:/admin-page";
//                }
                redirectAttributes.addAttribute("searchBy", lastSearchBy);
                redirectAttributes.addAttribute("searchValue", lastSearchValue);
                return "redirect:/admin/findProduct";
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
        String errorFields = allParams.get("ErrorFields");
        String errorExisted = allParams.get("ErrorExisted");
        if (errorFields != null) {
            model.addAttribute("ErrorAction", "Vui lòng nhập giá trị hợp lệ!!!");
        }
        if (errorExisted != null) {
            model.addAttribute("ErrorAction", "ID Nhân viên đã tồn tại!!!");
        }
        if (searchBy.trim().isEmpty()) {
            List<Employee> listEmployee = employeeService.findAllForSearch();
            model.addAttribute("EMPLOYEES_RESULT", listEmployee);
            return "employee_management";
        }
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
        String lastSearchBy = allParams.get("lastSearchBy");
        String lastSearchValue = allParams.get("lastSearchValue");
        if (employeeService.findByUsername(username) != null) {
            redirectAttributes.addAttribute("ErrorExisted", true);
//            if (lastSearchBy.trim().isEmpty()) {
//                return "redirect:/admin-employee-page";
//            }
            redirectAttributes.addAttribute("searchBy", lastSearchBy);
            redirectAttributes.addAttribute("searchValue", lastSearchValue);
            return "redirect:/admin/findEmployee";
        }
        for (String param : allParams.keySet()) {
            if (allParams.get(param).trim().isEmpty() && !param.equalsIgnoreCase("lastSearchValue") && !param.equalsIgnoreCase("lastSearchBy")) {
                //System.out.println(param);
                model.addAttribute("ErrorFields", true);
                return "employee_management";
            }
        }
        float employeeSalary = 0;
        try {
            employeeSalary = Float.parseFloat(employeeSalaryAsString);
        } catch (NumberFormatException ex) {
            redirectAttributes.addAttribute("ErrorFields", true);
//            if (lastSearchBy.trim().isEmpty()) {
//                return "redirect:/admin-employee-page";
//            }
            redirectAttributes.addAttribute("searchBy", lastSearchBy);
            redirectAttributes.addAttribute("searchValue", lastSearchValue);
            return "redirect:/admin/findEmployee";
        }
        employeeService.createNewEmployee(username, employeeRole, employeeName, employeePhone, employeeMail, employeeAddress, employeeSalary);
        redirectAttributes.addAttribute("searchBy", "id");
        redirectAttributes.addAttribute("searchValue", employeeService.findByUsername(username).getEmployeeID());
        return "redirect:/admin/findEmployee";
    }

    @PostMapping("/update-employee")
    public String updateEmployee(@RequestParam Map<String, String> allParams,
            RedirectAttributes redirectAttributes,
            Model model) {
        String employeeRole = allParams.get("role");
        String employeeSalaryAsString = allParams.get("salary");
        String employeeID = allParams.get("employeeID");
        String employeeName = allParams.get("name");
        String employeePhone = allParams.get("phoneNumber");
        String employeeMail = allParams.get("email");
        String employeeAddress = allParams.get("address");
        String username = allParams.get("username");
        String lastSearchValue = allParams.get("lastSearchValue");
        String lastSearchBy = allParams.get("lastSearchBy");
        float employeeSalary = 0;
        for (String param : allParams.keySet()) {
            if (allParams.get(param).trim().isEmpty() && !param.equalsIgnoreCase("lastSearchValue") && !param.equalsIgnoreCase("lastSearchBy")) {
                //System.out.println(param);
                model.addAttribute("ErrorFields", true);
                return "employee_management";
            }
        }

        if (employeeSalaryAsString.trim().isEmpty()) {
            employeeSalary = employeeService.findByEmployeeID(employeeID).getSalary();
        } else {
            try {
                employeeSalary = Float.parseFloat(employeeSalaryAsString);
            } catch (NumberFormatException ex) {
                redirectAttributes.addAttribute("ErrorFields", true);
//                if (lastSearchBy.trim().isEmpty()) {
//                    return "redirect:/admin-employee-page";
//                }
                redirectAttributes.addAttribute("searchBy", lastSearchBy);
                redirectAttributes.addAttribute("searchValue", lastSearchValue);
                return "redirect:/admin/findEmployee";
            }
        }

        employeeService
                .updateEmployeeByAdmin(employeeID, username, employeeRole, employeeName, employeePhone, employeeMail, employeeAddress, employeeSalary);

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

    @GetMapping("/product_recommendation")
    public String showProductRecommendation(Model model) {
        model.addAttribute("LIST", productRecommendationService.getAll());
        return "product_recommendation";
    }

    @GetMapping("/order_management")
    public String showOrder(Model model, @RequestParam(name = "page") int page) {
        List<PrimaryOrder> list = orderService.getAllOrderByStatus();
        int maxResult = 10;
        int fromItemIndex = (page - 1) * maxResult + 1;
        int maxItemIndex = fromItemIndex + maxResult - 1;
        int totalPage = list.size() / maxResult + 1;
        if (list.size() % maxResult == 0) {
            totalPage--;
        }
        List<PrimaryOrder> resultList = new ArrayList<>();
        for (int i = fromItemIndex; i <= maxItemIndex; i++) {
            list.get(i).setStatus(orderStatusService.findById(list.get(i).getStatusID()).getName());
            resultList.add(list.get(i));
            
        }
        model.addAttribute("PAGE_NUM", page);
        model.addAttribute("ORDERS_NUMBER", totalPage);
        model.addAttribute("LIST", resultList);
        model.addAttribute("LIST_STATUS", orderStatusService.findAll());
        return "order_management";
    }

    @GetMapping("/order_detail")
    public String showOrderDetail(
            @RequestParam(value = "orderID") String orderID,
            Model model) {
        PrimaryOrder order = orderService.findByOrderID(orderID);
        if (order != null) {
            Map<ProductDetails, Integer> listProduct = orderService.getListProduct(orderID);
            float total = 0;
            for (Map.Entry<ProductDetails, Integer> product : listProduct.entrySet()) {
                total += (float) product.getValue() * product.getKey().getSellprice();
            }
            model.addAttribute("PRODUCTS_IN_ORDER", listProduct);
            model.addAttribute("TotalOfOrder", total);
            model.addAttribute("order", order);
        }
        return "admin_order_detail";
    }

    @GetMapping("/update_status")
    public String updateStatus(
            @RequestParam(value = "txtOrderId") String orderID,
            @RequestParam(value = "txtStatus") int statusID
    ) {
        orderService.updateOrderStatus(orderID, statusID);
        return "redirect:/admin/order_management";
    }

}
