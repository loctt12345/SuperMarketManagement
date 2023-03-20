/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.service.impl;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 *
 * @author ADMIN
 */
public class SendMailService {
    public void sendEmailVerification(String email, String verificationCode, JavaMailSender mailSender) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("marketwithscanner@gmail.com", "Market With Scanner");
        helper.setTo(email);
        String mailSubject = "Verify your email";

        String content = "<p>Hello,</p>"
                + "<p>You have change or create new account for Market With Scanner</p>"
                + "<p>Enter the following code to verify your email: </p>"
                + "<p><h2>" + verificationCode + "</h2></p>"
                + "<p>Ignore this email if you have not made the request</p>";
        helper.setSubject(mailSubject);
        helper.setText(content, true);
        mailSender.send(message);
    }
    //Setup reset password mail
    public void setUpResetPasswordEmail(String email, String resetPasswordLink, JavaMailSender mailSender) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("marketwithscanner@gmail.com", "Market With Scanner");
        helper.setTo(email);
        String mailSubject = "Reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password: </p>"
                + "<p><b><a href=\"" + resetPasswordLink + "\">Change my password</a></b></p>"
                + "<p>Ignore this email if you do remember your password, or you have not made the request</p>";
        helper.setSubject(mailSubject);
        helper.setText(content, true);
        mailSender.send(message);
    }
}
