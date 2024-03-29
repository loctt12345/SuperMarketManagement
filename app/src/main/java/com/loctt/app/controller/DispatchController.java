/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.controller;

import com.loctt.app.model.AuthenticationProvider;
import com.loctt.app.model.CartObject;
import com.loctt.app.model.CustomOAuth2User;
import com.loctt.app.model.Employee;
import com.loctt.app.model.PrimaryOrder;
import com.loctt.app.model.ProductDetails;
import com.loctt.app.model.ProductRecommendation;
import com.loctt.app.model.User;
import com.loctt.app.model.UserDetailsPrincipal;
import com.loctt.app.service.IOrderStatusService;
import com.loctt.app.service.IProductRecommendationService;
import com.loctt.app.service.impl.CartService;
import com.loctt.app.service.impl.EmployeeService;
import com.loctt.app.service.impl.GenerateUUID;
import com.loctt.app.service.impl.OrderDetailsService;
import com.loctt.app.service.impl.OrderService;
import com.loctt.app.service.impl.ProductService;
import com.loctt.app.service.impl.SendMailService;
import com.loctt.app.service.impl.UserService;
import com.loctt.app.utils.Utils;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.bytebuddy.utility.RandomString;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private EmployeeService employeeService;
    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private IProductRecommendationService productRecommendationService;

    @Autowired
    private UserService userService;

    @Bean
    public SendMailService sendMailService() {
        return new SendMailService();
    }
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private IOrderStatusService orderStatusService;

    @ModelAttribute
    public void commonAttr(Model model, HttpSession session) {
        CartObject cart = (CartObject) session.getAttribute("CART");
        model.addAttribute("cartSize", this.cartService.getCartSize(cart));
    }

    @GetMapping("/home")
    public String startWeb() {
        return "index";
    }

    @GetMapping("/")
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
    @GetMapping("/admin-employee-page")
    public String adminStaffPage(Model model, HttpSession session){
        List<Employee> listEmployee = employeeService.findAllForSearch();
        if(session.getAttribute("ResetPassEmp") != null){
            model.addAttribute("ResetPassEmp", session.getAttribute("ResetPassEmp"));
            session.removeAttribute("ResetPassEmp");
        }
        model.addAttribute("EMPLOYEES_RESULT", listEmployee);
        return "employee_management";
    }
    @GetMapping("/product-detail")
    public String showProduct(Model model, @RequestParam(name = "productID", required = false) String productID) {
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
    public String showBill(Model model){
        List<PrimaryOrder> list = orderService.getAllOrderByStatus();
        for (PrimaryOrder order : list) {
            order.setStatus(orderStatusService.findById(order.getStatusID()).getName());
        }
        model.addAttribute("LIST", list);
        return "bill";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
    @GetMapping("/shipper_summary_order")
    public String showSummaryOrder(@RequestParam(name = "orderId", required = false) String orderId, Model model) {
        model.addAttribute("order", orderService.getPrimaryOrder(orderId));
        return "ship_staff_order_summary";
    }

    @GetMapping("/showRecommendation")
    public String showRecommendation(
            @RequestParam(name = "txtProductId", required = false) String productId,
            @RequestParam(name = "txtComment", required = false) String comment) {
        ProductRecommendation productRe
                = new ProductRecommendation(GenerateUUID.getUUID(),
                        comment, productId, true);
        productRecommendationService.createNewRecommendation(productRe);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage(
            @RequestParam(name = "error", required = false) boolean error,
            @RequestParam(name = "errorVerify", required = false) boolean errorVerify,
            @RequestParam(name = "errorEmail", required = false) boolean errorGoogleEmail,
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
            model.addAttribute("ErrorAuthorizedMessages", "Sai tên đăng nhập hoặc mật khẩu");
        }
        if (errorVerify) {
            model.addAttribute("ErrorAuthorizedMessages", "Không thể xác minh email");
        }
        if(errorGoogleEmail){
            model.addAttribute("ErrorAuthorizedMessages","Email đã tồn tại");
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

    @GetMapping("/accessDenied")
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public String acccessDenied() {
        return "access_denied_page";
    }

    @GetMapping("/register")
    public String register(@RequestParam(name = "username", required = false) String usernameError,
            @RequestParam(name = "email", required = false) String emailError,
            @RequestParam(name = "emailVerify", required = false) String emailVerifyError,
            Model model) {
        if (usernameError != null) {
            model.addAttribute("username_error", "Tên người dùng đã tồn tại!!!");
        }
        if (emailError != null) {
            model.addAttribute("email_error", "Email đã tồn tại!!!");
        }
        if (emailVerifyError != null) {
            model.addAttribute("email_error", "Email không thể xác minh!!!");
        }
        return "register";
    }

    @PostMapping("/addUser")
    public String addUser(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "fullname") String fullname,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "confirmPassword") String confirmPassword,
            @RequestParam(name = "phone") String phone,
            @RequestParam(name = "address") String address,
            Model model,
            HttpSession session
    ) {
        User user = userService.findByUsername(username);
        if (user != null) {
            return "redirect:/register?username";
        }

        user = userService.findByEmail(email);
        if (user != null) {
            return "redirect:/register?email";
        }
        String verificationCode = RandomString.make(6);
        try {
            sendMailService().sendEmailVerification(email, verificationCode, mailSender);
        } catch (UnsupportedEncodingException | MessagingException ex) {
            return "redirect:/register?emailVerify";
        }
        userService.createUser(new User("das", username, password, fullname,
                phone, email, address, verificationCode, false));
        session.setAttribute("VerificationRightFlow", true);
        return "redirect:/verifyMail";
    }
    

    @GetMapping("/verifyMail")
    public String verifyPage(@RequestParam(name = "ErrorVerification", required = false) boolean errorVerify, Model model, HttpSession session) {
        //vallidate whether user request /verifyMail in right way or not
        if (session.getAttribute("VerificationRightFlow") == null) {
            return "redirect:/login";
        }
        //clear unused attribute
        session.removeAttribute("VerificationRightFlow");
        if (errorVerify) {
            model.addAttribute("ErrorVerification", "Mã xác minh không hợp lệ");
        }
        return "verify_code_page";
    }

    @PostMapping("/registerMail")
    public String registerMail(@RequestParam(name = "verification_code") String verificationCode, RedirectAttributes redirectAttributes, HttpSession session) {
        if(verificationCode.isEmpty()){
            redirectAttributes.addAttribute("ErrorVerification", true);
            session.setAttribute("VerificationRightFlow", true);
            return "redirect:/verifyMail";
        }
        User customer = userService.findByVerificationCode(verificationCode);
        if (customer == null) {
            redirectAttributes.addAttribute("ErrorVerification", true);
            session.setAttribute("VerificationRightFlow", true);
            return "redirect:/verifyMail";
        }
        customer.setStatus(true);
        customer.setVerificationCode(null);
        userService.updateUser(customer);
        return "redirect:/login";

    }

    @PostMapping("/forgot_password/sendMail")
    public String sendMailForResetPwd(@RequestParam(name = "username") String username, Model model, HttpServletRequest request) {
        if (username.isEmpty() || userService.findByUsername(username) == null) {
            model.addAttribute("ErrorUsername", "Vui lòng nhập tên người dùng hợp lệ");
            return "forgot_password_form";
        }
        if (employeeService.findByUsername(username) != null
                || userService.findByUsername(username).getAuthenticationProvider() == AuthenticationProvider.GOOGLE) {
            model.addAttribute("ErrorUsername", "Tài khoản của bạn không có quyền đặt lại mật khẩu");
            return "forgot_password_form";
        }
        String token = RandomString.make(45);
        try {
            userService.updateResetPassword(token, username);
            // Generate reset password link
            String resetPasswordLink = Utils.getBaseURL(request) + "/forgot_password/enterNewPwd?token=" + token;
            //Send email
            String email = userService.findByUsername(username).getEmail();
            sendMailService().setUpResetPasswordEmail(email, resetPasswordLink, mailSender);
            model.addAttribute("SuccessSendMail", "Chúng tôi đã gửi một email có hướng dẫn tới " + email.split("@")[0].substring(0, 4) + "*******@" + email.split("@")[1]);

        } catch (IllegalArgumentException e) {
            model.addAttribute("ErrorUsername", e.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("ErrorUsername", "Lỗi khi gửi Email");
        }
        return "forgot_password_form";
    }

    @GetMapping("/forgot_password")
    public String forgotPasswordPage() {
        return "forgot_password_form";
    }

    @GetMapping("/forgot_password/enterNewPwd")
    public String resetPasswordPage(@RequestParam(name = "token") String token, Model model) {
        if (token.isEmpty()) {
            model.addAttribute("ErrorToken", "Mã không hợp lệ");
            return "reset_password_form";
        }
            User customer = userService.findByResetPasswordToken(token);
            if (customer == null) {
                model.addAttribute("ErrorToken", "Mã không hợp lệ");
            }
            model.addAttribute("token", token);
            return "reset_password_form";
    }

    @PostMapping("/forgot_password/reset")
    public String resetPassword(@RequestParam(name = "password") String newPassword,
            @RequestParam(name = "token") String token, Model model) {
        User customer = userService.findByResetPasswordToken(token);
        System.out.println(customer.getAddress());
        if (customer == null) {
            model.addAttribute("ErrorToken", "Mã không hợp lệ");
            return "reset_password_form";
        }
        userService.updatePassword(customer, newPassword);
        return "redirect:/login";
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
