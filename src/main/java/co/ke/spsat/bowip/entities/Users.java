package co.ke.spsat.bowip.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "USERS")
@AllArgsConstructor
public class Users {
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
    @SequenceGenerator(sequenceName = "users_seq", allocationSize = 1, name = "USERS_SEQ")
    private Long userId;
    @NotNull
  //  @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;



    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "EMAIL")
    @Nonnull
    @Email(message = "email invalid")
    private String email;
    @Pattern(regexp = "(^[0-9]+$|^$)", message = "number only")
    @Column(name = "PHONE")
    @Nonnull
    private String phone;
    @Nonnull
    @Column(name = "USERNAME")
    private String username;
    @Nonnull
    @Column(name = "PASWORD")
    private String password;
    @Column(name = "CREATED_AT")
    private Date createdAt;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "DELETED_BY")
    private String deletedBy;
    @Column(name = "UPDATED_BY")
    private String updatedBy;
    private Instant modifiedAt;
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "DEPARTMENT", referencedColumnName = "ID")
    private Department department;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id")
    @JsonIgnore
    @BatchSize(size = 10)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Roles> roles;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;
    @Column(name = "sales_target")
    private Double salesTarget;

    @Column(name = "commission_rate")
    private Double commissionRate;
    @NotNull
    @Column(nullable = false)
    private boolean activated = false;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Users manager;
    public Users() {

    }
}

