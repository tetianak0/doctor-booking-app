package doctorBookingApp.dto;
import doctorBookingApp.entity.enums.TypeOfInsurance;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeSlotDTO {

    private Long doctorId;
    private LocalDateTime dateTime;
    private TypeOfInsurance insurance;
    private Boolean isBooked;
}