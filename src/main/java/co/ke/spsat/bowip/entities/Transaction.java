package co.ke.spsat.bowip.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
@Data
@Entity
@Table(name = "TRANSACTION")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "TRANSACTION_SEQ")
    @SequenceGenerator(sequenceName = "Transaction_seq",allocationSize = 1,name = "TRANSACTION_SEQ")
    @Column(name = "TRANSACTION_ID")
    private Long transactionId;
    private Double amount;
    private Date timestamp;
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "PAYMENT_METHOD", referencedColumnName = "METHOD_ID")
    private PaymentMethod paymentMethod;
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL, optional = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ORDERS_ID", referencedColumnName = "ORDERS_ID")
    private Order orderId;
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
   // @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "CUSTOMERS", referencedColumnName = "CUSTOMER_ID")
    private Customers customerId;

}
