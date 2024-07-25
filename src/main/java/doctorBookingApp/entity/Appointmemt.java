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
@Table(name = "calendar")

public class Appointmemt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}



