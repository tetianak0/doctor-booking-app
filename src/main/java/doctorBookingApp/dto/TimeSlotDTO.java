package doctorBookingApp.dto;
import doctorBookingApp.entity.enums.TypeOfInsurance;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeSlotDTO {

    private Long doctor_id;
    private Long date_time;
    private TypeOfInsurance insurance;
    private Boolean is_booked;
}