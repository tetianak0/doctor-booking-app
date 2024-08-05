package doctorBookingApp.repository;


import doctorBookingApp.dto.TimeSlotDTO;
import doctorBookingApp.entity.DoctorProfile;
import doctorBookingApp.entity.TimeSlot;
import doctorBookingApp.entity.enums.TypeOfInsurance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimeSlotServiceImpl implements TimeSlotService {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private DoctorProfileRepository doctorProfileRepository;

    @Override
    public TimeSlot addTimeSlot(TimeSlotDTO timeSlotDTO) {
        DoctorProfile doctor = doctorProfileRepository.findById(timeSlotDTO.getDoctor_id())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        TimeSlot timeSlot = TimeSlot.builder()
                .doctor(doctor)
                .date_time(timeSlotDTO.getDate_time())
                .insurance(timeSlotDTO.getInsurance())
                .is_booked(timeSlotDTO.getIs_booked())
                .build();

        return timeSlotRepository.save(timeSlot);
    }

    @Override
    public TimeSlot updateTimeSlot(Long id, TimeSlotDTO timeSlotDTO) {
        TimeSlot existingTimeSlot = timeSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TimeSlot not found"));

        DoctorProfile doctor = doctorProfileRepository.findById(timeSlotDTO.getDoctor_id())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        existingTimeSlot.setDoctor(doctor);
        existingTimeSlot.setDate_time(timeSlotDTO.getDate_time());
        existingTimeSlot.setInsurance(timeSlotDTO.getInsurance());
        existingTimeSlot.setIs_booked(timeSlotDTO.getIs_booked());

        return timeSlotRepository.save(existingTimeSlot);
    }

    @Override
    public void deleteTimeSlot(Long id) {
        timeSlotRepository.deleteById(id);
    }

    @Override
    public List<TimeSlot> getTimeSlotsByDoctor(Long doctor_id) {
        return timeSlotRepository.findByDoctorId(doctor_id);
    }

    @Override
    public List<TimeSlot> getTimeSlotsByInsurance(TypeOfInsurance insurance) {
        return timeSlotRepository.findByInsurance(insurance);
    }

    @Override
    public List<TimeSlot> getTimeSlotsByDateTime(Long date_time) {
        return timeSlotRepository.findByDateTime(date_time);
    }

    @Override
    public Optional<TimeSlot> getTimeSlotById(Long id) {
        return timeSlotRepository.findById(id);
    }
}