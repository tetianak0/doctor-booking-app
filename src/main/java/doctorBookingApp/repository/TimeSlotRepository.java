package doctorBookingApp.repository;


import doctorBookingApp.entity.TimeSlot;
import doctorBookingApp.entity.enums.TypeOfInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findByDoctorId(Long doctor_id);
    List<TimeSlot> findByInsurance(TypeOfInsurance insurance);
    List<TimeSlot> findByDateTime(Long date_time);
}