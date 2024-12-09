package co.ke.spsat.bowip.entities;

import co.ke.spsat.bowip.user.Users;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.time.LocalDateTime;
@Entity
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "CART_ITEM_SEQ")
    private Integer report_id; //(Primary Ke
private String report_type; //(e.g., sales, inventory, employee performance)
    @ManyToOne
private Users generated_by;// (Foreign Key to Employee)
private LocalDateTime date_generated;
private String report_content;// (JSON or serialized data){
}
