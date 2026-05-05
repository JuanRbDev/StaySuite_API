package com.juanrbdev.staysuite.entity;
import com.juanrbdev.staysuite.emuns.RolEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(unique = true)
    private String dni;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String phone;

    private Boolean state = true;

    @Enumerated(EnumType.STRING)
    private RolEnum role;

    // RELATIONS //

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;

}