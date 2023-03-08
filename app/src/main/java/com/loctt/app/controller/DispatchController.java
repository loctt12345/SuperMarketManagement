/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.controller;

import com.loctt.app.model.CartObject;
import com.loctt.app.model.ProductDetails;
import com.loctt.app.model.User;
import com.loctt.app.service.impl.CartService;
import com.loctt.app.service.impl.OrderDetailsService;
import com.loctt.app.service.impl.OrderService;
import com.loctt.app.service.impl.ProductService;
import com.loctt.app.service.impl.UserService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
//FOR MAP MAIN PAGE
@Controller
@ControllerAdvice

public class DispatchController {

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailsService orderDetailsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @ModelAttribute
    public void commonAttr(Model model, HttpSession session) {
        CartObject cart = (CartObject) session.getAttribute("CART");
        model.addAttribute("cartSize", this.cartService.getCartSize(cart));
    }

    @GetMapping("/")
    public String startWeb() {
        return "index";
    }

    @GetMapping("/home")
    public String home() {
        return "index";
    }

    //TestAdmin
    @GetMapping("/admin-page")
    public String adminPage(Model model) {
        List<ProductDetails> listProduct = productService.findAll();
        model.addAttribute("PRODUCTS_RESULT", listProduct);
        return "products_management";
    }

    @GetMapping("/product-detail")
    public String showProduct(Model model, @RequestParam(name = "productID") String productID) {
        model.addAttribute("product_id", productID);
        return "product_detail";
    }

    @GetMapping("/showCart")
    public String showCart(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            Model model,
            HttpSession session) {
        model.addAttribute("page", page);
        CartObject cart = (CartObject) session.getAttribute("CART");
        model.addAttribute("numPage", (int) Math.ceil((float) cartService.getCartSize(cart) / 6));
        return "cart_page";
    }

    @GetMapping("/showPaying")
    public String showPaying() {
        return "paying";
    }

    @GetMapping("/repoStaff")
    public String showRepoStaff() {
        return "repo_staff_screen";
    }

    @GetMapping("/shipStaff")
    public String showShipStaff() {
        return "ship_staff_screen";
    }

    @GetMapping("/showBill")
    public String showBill(@RequestParam(name = "orderId", required = false) String orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "bill";
    }

    @GetMapping("/shipper_summary_order")
    public String showSummaryOrder(@RequestParam(name = "orderId", required = false) String orderId, Model model) {
        model.addAttribute("order", orderService.getPrimaryOrder(orderId));
        return "ship_staff_order_summary";
    }

    @GetMapping("/login")
    public String loginPage(
            @RequestParam(name = "error", required = false) boolean error,
            Model model,
            Authentication authentication) {
        if (authentication != null
                && authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {

            return "redirect:/";
        }
        if (authentication != null
                && authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {

            return "redirect:/admin-page";
        }

        if (authentication != null
                && authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STORAGE_MAN"))) {

            return "redirect:/repoStaff";
        }

        if (authentication != null
                && authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_DELIVERY_MAN"))) {

            return "redirect:/shipStaff";
        }

        if (error) {
            model.addAttribute("ErrorAuthorizedMessages", "Invalid username or password");
        }
        return "login_form";
    }

    @GetMapping("/authorize")
    public String authorize(Authentication authentication) {
        if (authentication != null
                && authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {

            return "redirect:/";
        }
        if (authentication != null
                && authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {

            return "redirect:/admin-page";
        }

        if (authentication != null
                && authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STORAGE_MAN"))) {

            return "redirect:/repoStaff";
        }

        if (authentication != null
                && authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_DELIVERY_MAN"))) {

            return "redirect:/shipStaff";
        }
        return "login_form";
    }
    
//    @GetMapping("/crawl") 
//    public String crawl() {
//        List<ProductDetails> productDetails = this.productService.findByNameContaining("");
//        for (ProductDetails x : productDetails) {
//            System.out.println("INSERT INTO [Product_Manager] "
//                    + "VALUES('" + x.getProductID() + "',10000," + "0" + ")");
//        }
//        return "redirect:/admin-page";
//    }

}
