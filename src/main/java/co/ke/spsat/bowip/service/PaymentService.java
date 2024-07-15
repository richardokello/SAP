package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.entities.Order;
import co.ke.spsat.bowip.entities.Payment;
import co.ke.spsat.bowip.repositories.OrderRepository;
import co.ke.spsat.bowip.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private NotificationService notificationService;
//
//    public Payment processPayment(Long orderId, String paymentMethod) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//
//        Payment payment = new Payment();
//        payment.setOrder(order);
//        payment.setPaymentMethod(paymentMethod);
//        payment.setPaymentStatus("SUCCESS");
//
//        // Integrate with actual payment gateway here
//
//
//        Payment savedPayment = paymentRepository.save(payment);
//        // Send notification
//        notificationService.sendPaymentConfirmation(order.getCustomer().getBusinessEmail(), savedPayment);
//
//        return savedPayment;
//    }

    // Method to process credit/debit card payment
    public Payment processCardPayment(Long orderId, String cardNumber, String cardExpiry, String cardCVV, Double amount, String currency) {
        // Simulate payment processing
        String transactionId = UUID.randomUUID().toString();

        Payment payment = new Payment();
        payment.setOrder(orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found")));
        payment.setPaymentMethod("Credit/Debit Card");
        payment.setPaymentStatus("SUCCESS");
        payment.setTransactionId(transactionId);
        payment.setAmount(amount);
        payment.setCurrency(currency);

        Payment savedPayment = paymentRepository.save(payment);

        // Send notification
        notificationService.sendPaymentConfirmation(payment.getOrder().getCustomer().getBusinessEmail(), savedPayment);

        return savedPayment;
    }

    // Method to process PayPal payment
    public Payment processPayPalPayment(Long orderId, String payPalAccount, Double amount, String currency) {
        // Simulate payment processing
        String transactionId = UUID.randomUUID().toString();

        Payment payment = new Payment();
        payment.setOrder(orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found")));
        payment.setPaymentMethod("PayPal");
        payment.setPaymentStatus("SUCCESS");
        payment.setTransactionId(transactionId);
        payment.setAmount(amount);
        payment.setCurrency(currency);

        Payment savedPayment = paymentRepository.save(payment);

        // Send notification
        notificationService.sendPaymentConfirmation(payment.getOrder().getCustomer().getBusinessEmail(), savedPayment);

        return savedPayment;
    }

    // Method to process digital wallet payment
    public Payment processDigitalWalletPayment(Long orderId, String walletProvider, String walletId, Double amount, String currency) {
        // Simulate payment processing
        String transactionId = UUID.randomUUID().toString();

        Payment payment = new Payment();
        payment.setOrder(orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found")));
        payment.setPaymentMethod(walletProvider);
        payment.setPaymentStatus("SUCCESS");
        payment.setTransactionId(transactionId);
        payment.setAmount(amount);
        payment.setCurrency(currency);

        Payment savedPayment = paymentRepository.save(payment);

        // Send notification
        notificationService.sendPaymentConfirmation(payment.getOrder().getCustomer().getBusinessEmail(), savedPayment);

        return savedPayment;
    }

    // Method to process bank transfer payment
    public Payment processBankTransfer(Long orderId, String bankAccountNumber, String bankRoutingNumber, Double amount, String currency) {
        // Implement payment processing
        String transactionId = UUID.randomUUID().toString();

        Payment payment = new Payment();
        payment.setOrder(orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found")));
        payment.setPaymentMethod("Bank Transfer");
        payment.setPaymentStatus("SUCCESS");
        payment.setTransactionId(transactionId);
        payment.setAmount(amount);
        payment.setCurrency(currency);

        Payment savedPayment = paymentRepository.save(payment);

        // Send notification
        notificationService.sendPaymentConfirmation(payment.getOrder().getCustomer().getBusinessEmail(), savedPayment);

        return savedPayment;
    }
}
