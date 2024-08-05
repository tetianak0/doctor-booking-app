package doctorBookingApp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;


@Accessors
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "doctor_profile")
public class DoctorProfile {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(length = 40)
        private String firstName;

        @Column(length = 50)
        private String lastName;

        @ManyToOne
        @JoinColumn(name = "department_id", nullable = false)
        private Department department;

        @Column(length = 255)
        private String specialization;

        @Column(length = 3)
        private Integer experienceYears;

        @Column(length = 50)
        private Integer reviewId;


        public void setDepartment(Department department) {
                this.department = department;
        }

        public Department getDepartment() {
                return department; }
}
