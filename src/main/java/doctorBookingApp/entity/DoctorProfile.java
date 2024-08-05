package doctorBookingApp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "doctorProfiles")
public class DoctorProfile {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(length = 40)
        private String firstName;

        @Column(length = 50)
        private String lastName;

        @ManyToOne
        @JoinColumn(name = "department", nullable = false)
        private Department department;

        @Column(length = 255)
        private String specialization;

        @Column(length = 3)
        private Integer experienceYears;

        @Column(length = 50)
        private Integer reviewId;



}
