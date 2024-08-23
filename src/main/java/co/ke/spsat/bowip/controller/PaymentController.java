package co.ke.spsat.bowip.controller;

import co.ke.spsat.bowip.entities.Payment;
import co.ke.spsat.bowip.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/card")
    public ResponseEntity<Payment> processCardPayment(@RequestParam Long orderId,
                                                      @RequestParam String cardNumber,
                                                      @RequestParam String cardExpiry,
                                                      @RequestParam String cardCVV,
                                                      @RequestParam Double amount,
                                                      @RequestParam String currency) {
        Payment payment = paymentService.processCardPayment(orderId, cardNumber, cardExpiry, cardCVV, amount, currency);
        return ResponseEntity.ok(payment);
    }

    @PostMapping("/paypal")
    public ResponseEntity<Payment> processPayPalPayment(@RequestParam Long orderId,
                                                        @RequestParam String payPalAccount,
                                                        @RequestParam Double amount,
                                                        @RequestParam String currency) {
        Payment payment = paymentService.processPayPalPayment(orderId, payPalAccount, amount, currency);
        return ResponseEntity.ok(payment);
    }

    @PostMapping("/digital-wallet")
    public ResponseEntity<Payment> processDigitalWalletPayment(@RequestParam Long orderId,
                                                               @RequestParam String walletProvider,
                                                               @RequestParam String walletId,
                                                               @RequestParam Double amount,
                                                               @RequestParam String currency) {
        Payment payment = paymentService.processDigitalWalletPayment(orderId, walletProvider, walletId, amount, currency);
        return ResponseEntity.ok(payment);
    }

    @PostMapping("/bank-transfer")
    public ResponseEntity<Payment> processBankTransfer(@RequestParam Long orderId,
                                                       @RequestParam String bankAccountNumber,
                                                       @RequestParam String bankRoutingNumber,
                                                       @RequestParam Double amount,
                                                       @RequestParam String currency) {
        Payment payment = paymentService.processBankTransfer(orderId, bankAccountNumber, bankRoutingNumber, amount, currency);
        return ResponseEntity.ok(payment);
    }
}
