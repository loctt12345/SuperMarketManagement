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
        String mailSubject = "Xác nhận email của bạn";

        String content = "<p>Xin chào,</p>"
                + "<p>Bạn đã thay đổi hoặc tạo tài khoản mới cho Market With Scanner</p>"
                + "<p>Nhập mã sau để xác minh email của bạn: </p>"
                + "<p><h2>" + verificationCode + "</h2></p>"
                + "<p>Bỏ qua email này nếu bạn chưa thực hiện yêu cầu</p>";
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
        String mailSubject = "Đặt lại mật khẩu của bạn";

        String content = "<p>Xin chào,</p>"
                + "<p>Bạn đã yêu cầu đặt lại mật khẩu của mình.</p>"
                + "<p>Nhấp vào liên kết bên dưới để thay đổi mật khẩu của bạn: </p>"
                + "<p><b><a href=\"" + resetPasswordLink + "\">Thay đổi mật khẩu của tôi</a></b></p>"
                + "<p>Bỏ qua email này nếu bạn nhớ mật khẩu của mình hoặc bạn chưa thực hiện yêu cầu</p>";
        helper.setSubject(mailSubject);
        helper.setText(content, true);
        mailSender.send(message);
    }
}
