package doctorBookingApp.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@ToString
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


//        @Column(length = 50)
//        private String department;

        @Column(length = 255)
        private String specialization;

        @Column(length = 3)
        private Integer experienceYears;

        @Column(length = 50)
        private String languageLevel;

        @Column(nullable = false)
        private String schedul;


        //ОТЗІВВІ



}
