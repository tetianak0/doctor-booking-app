package doctorBookingApp.dto;
import doctorBookingApp.entity.TimeSlot;
import doctorBookingApp.entity.enums.TypeOfInsurance;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeSlotDTO {

    private Long doctorId;
    private Long dateTime;
    private TypeOfInsurance insurance;
    private Boolean isBooked;

    // Метод преобразования из сущности в DTO
    public static TimeSlotDTO from(TimeSlot timeSlot) {
        TimeSlotDTO dto = new TimeSlotDTO();
        dto.setDoctorId(timeSlot.getId());
        dto.setDateTime(timeSlot.getDateTime());
        dto.setInsurance(timeSlot.getInsurance());
        dto.setIsBooked(timeSlot.getIsBooked());
        return dto;
    }



}