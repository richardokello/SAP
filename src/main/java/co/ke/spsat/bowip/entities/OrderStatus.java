package co.ke.spsat.bowip.entities;

import jakarta.persistence.Enumerated;


public enum OrderStatus {
    NEW,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELED

}
