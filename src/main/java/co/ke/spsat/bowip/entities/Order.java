package co.ke.spsat.bowip.entities;


import java.util.Date;
import java.util.List;

public class Order {    private String orderId;
    private Customers customer;
    private List<OrderItem> orderItems;
    private Date orderDate;
    private OrderStatus orderStatus;
    private ShoppingCart shoppingCart;

}
