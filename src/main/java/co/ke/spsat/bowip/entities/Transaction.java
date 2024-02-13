package co.ke.spsat.bowip.entities;

import java.util.Date;

public class Transaction {
    private Long transactionId;
    private Double amount;
    private Date timestamp;
    private PaymentMethod paymentMethod;
    private Order orderId;
    private Customers customerId;

}
