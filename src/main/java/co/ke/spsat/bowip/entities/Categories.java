package co.ke.spsat.bowip.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;

@Table(name = "CATEGORY")
@Data
@Entity
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long categoryId;
    @Column(name = "CATEGORY_NAME")
    @Nonnull
    private String categoryName;
    @Nonnull
    @Column(name = "DESCRIPTION")
    private String description;

}
