package doctorBookingApp.service;
import doctorBookingApp.dto.DoctorProfileDTO;
import doctorBookingApp.entity.Department;
import doctorBookingApp.entity.DoctorProfile;
import doctorBookingApp.repository.DepartmentRepository;
import doctorBookingApp.repository.DoctorProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DoctorProfileService {

    @Autowired
    private DoctorProfileRepository doctorProfileRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public DoctorProfileDTO addDoctorProfile(DoctorProfileDTO doctorProfileDTO) {
        Department department = departmentRepository.findById(doctorProfileDTO.getDepartment_id())
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        DoctorProfile doctorProfile = DoctorProfile.builder()
                .firstName(doctorProfileDTO.getFirstName())
                .lastName(doctorProfileDTO.getLastName())
                .department_id(department)
                .specialization(doctorProfileDTO.getSpecialization())
                .experienceYears(doctorProfileDTO.getExperienceYears())
                .review_id(doctorProfileDTO.getReview_id())
                .build();
        doctorProfile = doctorProfileRepository.save(doctorProfile);
        return toDTO(doctorProfile);
    }

    public DoctorProfileDTO updateDoctorProfile(Long id, DoctorProfileDTO doctorProfileDTO) {
        DoctorProfile doctorProfile = doctorProfileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor profile not found"));
        Department department = departmentRepository.findById(doctorProfileDTO.getDepartment_id())
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        doctorProfile.setFirstName(doctorProfileDTO.getFirstName());
        doctorProfile.setLastName(doctorProfileDTO.getLastName());
        doctorProfile.setDepartment(department);
        doctorProfile.setSpecialization(doctorProfileDTO.getSpecialization());
        doctorProfile.setExperienceYears(doctorProfileDTO.getExperienceYears());
        doctorProfile.setReview_id(doctorProfileDTO.getReview_id());
        doctorProfile = doctorProfileRepository.save(doctorProfile);
        return toDTO(doctorProfile);
    }

    public void deleteDoctorProfile(Long id) {
        doctorProfileRepository.deleteById(id);
    }

    public List<DoctorProfileDTO> getAllDoctorProfiles() {
        return doctorProfileRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public DoctorProfileDTO getDoctorProfileById(Long id) {
        DoctorProfile doctorProfile = doctorProfileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor profile not found"));
        return toDTO(doctorProfile);
    }

    private DoctorProfileDTO toDTO(DoctorProfile doctorProfile) {
        return DoctorProfileDTO.builder()
                .id(doctorProfile.getId())
                .firstName(doctorProfile.getFirstName())
                .lastName(doctorProfile.getLastName())
                .department_id(doctorProfile.getDepartment().getId())
                .specialization(doctorProfile.getSpecialization())
                .experienceYears(doctorProfile.getExperienceYears())
                .review_id(doctorProfile.getReview_id())
                .build();
    }
}