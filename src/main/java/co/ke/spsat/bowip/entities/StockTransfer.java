package co.ke.spsat.bowip.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Entity
@Data
public class StockTransfer {
    @Id
    private long transfer_id;                         // Unique identifier for the stock transfer.
    @OneToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    private Warehouse  fromWarehouseId;             //Foreign key linking to the source warehouse.
    @OneToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    private Warehouse toWarehouseId;                // Foreign key linking to the destination warehouse.
    private Date transfer_date;                       // Date the transfer was initiated.
    private String  status;
    @ManyToOne
    @JoinColumn(name = "requested_by")
    private Users requestedBy;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private Users approvedBy;

    private Date requestDate;
    private Date approvalDate;// Current status of the transfer (e.g., pending, completed).
}
