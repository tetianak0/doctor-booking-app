package doctorBookingApp.entity;

import doctorBookingApp.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "account")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(length = 10)
    private String dateOfBirth;

    @Column(length = 14)
    private Integer numberTelf;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String hashPassword;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;


}
