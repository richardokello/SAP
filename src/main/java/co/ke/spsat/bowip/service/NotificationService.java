package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.entities.Order;
import co.ke.spsat.bowip.entities.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {


    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderConfirmation(String email, Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Order Confirmation");
        message.setText("Your order has been placed successfully. Order ID: " + order.getOrderId());
        mailSender.send(message);
    }

    public void sendOrderUpdate(String email, Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Order Update");
        message.setText("Your order has been updated. Order ID: " + order.getOrderId());
        mailSender.send(message);
    }

    public void sendPaymentConfirmation(String email, Payment payment) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Payment Confirmation");
        message.setText("Your payment has been processed successfully. Payment ID: " + payment.getId());
        mailSender.send(message);
    }

    public void sendShippingUpdate(String email, Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Shipping Update");
        message.setText("Your order is now being shipped. Order ID: " + order.getOrderId());
        mailSender.send(message);
    }

    public void sendNotification(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Shipping Update");
        message.setText("product of this batch is expiring");
        mailSender.send(message);
    }
    }

