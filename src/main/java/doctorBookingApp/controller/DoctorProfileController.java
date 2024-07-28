package doctorBookingApp.controller;
import doctorBookingApp.dto.DoctorProfileDTO;
import doctorBookingApp.service.DoctorProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/doctorProfiles")
@Validated
public class DoctorProfileController {

    @Autowired
    private DoctorProfileService doctorProfileService;

    @PostMapping
    public ResponseEntity<DoctorProfileDTO> addDoctorProfile(@RequestBody @Valid DoctorProfileDTO doctorProfileDTO) {
        DoctorProfileDTO createdDoctorProfile = doctorProfileService.addDoctorProfile(doctorProfileDTO);
        return new ResponseEntity<>(createdDoctorProfile, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorProfileDTO> updateDoctorProfile(@PathVariable Long id, @RequestBody @Valid DoctorProfileDTO doctorProfileDTO) {
        DoctorProfileDTO updatedDoctorProfile = doctorProfileService.updateDoctorProfile(id, doctorProfileDTO);
        return new ResponseEntity<>(updatedDoctorProfile, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctorProfile(@PathVariable Long id) {
        doctorProfileService.deleteDoctorProfile(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<DoctorProfileDTO>> getAllDoctorProfiles() {
        List<DoctorProfileDTO> doctorProfiles = doctorProfileService.getAllDoctorProfiles();
        return new ResponseEntity<>(doctorProfiles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorProfileDTO> getDoctorProfileById(@PathVariable Long id) {
        DoctorProfileDTO doctorProfile = doctorProfileService.getDoctorProfileById(id);
        return new ResponseEntity<>(doctorProfile, HttpStatus.OK);
    }
}