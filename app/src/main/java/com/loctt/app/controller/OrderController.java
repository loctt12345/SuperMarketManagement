/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.controller;

import com.loctt.app.model.CartObject;
import com.loctt.app.model.CustomOAuth2User;
import com.loctt.app.model.OrderDetails;
import com.loctt.app.model.PrimaryOrder;
import com.loctt.app.model.ProductDetails;
import com.loctt.app.model.UserDetailsPrincipal;
import com.loctt.app.service.IOrderService;
import com.loctt.app.service.IProductManagerService;
import com.loctt.app.service.IUserService;
import com.loctt.app.service.impl.GenerateUUID;
import com.loctt.app.service.impl.ProductService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Administrator
 */
@Controller
public class OrderController {

    @Autowired
    IOrderService orderService;

    @Autowired
    IProductManagerService productManager;

    @Autowired
    private IUserService userService;

    @Autowired
    private ProductService productService;
//    @GetMapping("/test")
//    public OrderStatus getOrderStatus(@RequestParam(name="txtID") String ID) {
//        
//        OrderStatus result = orderService.findorderStatusID(Integer.parseInt(ID));
//        
//        return result;
//    
//    }

    @PostMapping("/paying/order")
    public String savedOrder(@RequestParam(name = "userEmail") String email,
            @RequestParam(name = "userPhone") String phone,
            @RequestParam(name = "userAddress") String address,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Authentication authentication) {

        //Get CART OBJECT
        CartObject cart = (CartObject) session.getAttribute("CART");

        //UPDATE WHEN HAVE NEW PRODUCT
        //productManager.updateAvailableNumber();
        //Check the number of Product is Available??
        boolean isAvailable = productManager.isAvailable(cart);

        //Set Notification about Number of Product Available
        //1. Clear notification before
        String noti = "";
        //2. Set the notification 
        if (isAvailable) {
            noti = "The number of Product is not enough!!! Please check again!!";
        }

        //3. Add this noti to attribute
        session.setAttribute(noti, "notiAvailable");

        //1. Add new Order
        PrimaryOrder orderSaved = new PrimaryOrder();

        String orderID = GenerateUUID.getUUID();

        //Set Shipping information
        orderSaved.setOrderID(orderID);
        orderSaved.setEmail(email);
        orderSaved.setPhone(phone);
        orderSaved.setShippingAddress(address);

        //Set Customer
        String userId = ((UserDetailsPrincipal) authentication.getPrincipal())
                .getUser().getUserID();
        orderSaved.setUserID(userId);
        orderSaved.setStatusID(2);
        orderSaved.setTime(new Date());

        //Set Total For order
        float total = orderService.getTotalOfOrder(cart);
        orderSaved.setTotal(total);
        orderService.saveOrder(orderSaved);

        //2. call Add Order Details Service 
        orderService.saveOrderDetails(cart, orderID);

        //3. remove cart after checkout
        cart.getItems().clear();
        redirectAttributes.addAttribute("orderID", orderID);
        System.out.println("http://localhost:8080/showBill?orderId=" + orderID);
        return "redirect:/paying/orderProgress";
    }

    @GetMapping("/paying/orderProgress")
    public String updateOrderStatus(@RequestParam(value = "orderID") String orderID, Model model) {
        if (orderID != null) {
            PrimaryOrder order = orderService.findByOrderID(orderID);
            Map<ProductDetails, Integer> listProduct = orderService.getListProduct(orderID);
            float total = 0;
            for (Map.Entry<ProductDetails, Integer> product : listProduct.entrySet()) {
                total += (float) product.getValue() * product.getKey().getSellprice();
            }
            model.addAttribute("PRODUCTS_IN_ORDER", listProduct);
            model.addAttribute("TotalOfOrder", total);
            model.addAttribute("order", order);
        }
        return "payingSucess";
    }

    @GetMapping("/orderHistory")
    public String getlistPrimaryOrder(Authentication authentication, Model model) {
        //   String userId = object.getAsString("txtUserId");
        String userId;
        if (authentication.getPrincipal() instanceof CustomOAuth2User) {
            // if user login with Gmail
            String gmail = ((CustomOAuth2User) authentication.getPrincipal())
                    .getEmail();
            userId = userService.findByEmail(gmail).getUserID();
        } else {
            userId = ((UserDetailsPrincipal) authentication.getPrincipal())
                    .getUser().getUserID();
        }
        model.addAttribute("list_order", orderService.getlistPrimaryOrder(userId));
        return "order_history";

    }
}
