/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.loctt.app.controller;

import com.loctt.app.config.PaypalPaymentIntent;
import com.loctt.app.config.PaypalPaymentMethod;
import com.loctt.app.model.CartObject;
import com.loctt.app.model.PrimaryOrder;
import com.loctt.app.service.IOrderService;
import com.loctt.app.service.IProductManagerService;
import com.loctt.app.service.impl.CartService;
import com.loctt.app.service.impl.GenerateUUID;
import com.loctt.app.service.impl.PaypalService;
import com.loctt.app.utils.Utils;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PaymentController {

    public static final String URL_PAYPAL_SUCCESS = "pay/success";
    public static final String URL_PAYPAL_CANCEL = "pay/cancel";
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private PaypalService paypalService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private IProductManagerService productManager;

    @PostMapping("/pay")
    public String pay(HttpServletRequest request,
            @RequestParam("price") double price,
            @RequestParam("userEmail") String email,
            @RequestParam("userAddress") String address,
            @RequestParam("userPhone") String phone,
            HttpSession session) {
        String cancelUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
        String successUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS;
        String[] x = {email, address, phone};
        session.setAttribute("payment_info", x);
        try {
            Payment payment = paypalService.createPayment(
                    price,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    cancelUrl,
                    successUrl);
            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return "redirect:" + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping(URL_PAYPAL_CANCEL)
    public String cancelPay(HttpSession session) {
        session.removeAttribute("payment_info");
        return "redirect:/";
    }

    @GetMapping(URL_PAYPAL_SUCCESS)
    public String successPay(@RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId, HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                String[] x = (String[]) session.getAttribute("payment_info");
                String email = x[0];
                String address = x[1];
                String phone = x[2];
                session.removeAttribute("payment_info");
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
                orderSaved.setUserID("CUS011");
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
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }

        return "redirect:/";
    }
}
