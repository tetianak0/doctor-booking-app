package doctorBookingApp.entity;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class PatientProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40)
    private Time aheadTermin;


    //private List<String> historyOfVisits;




}
