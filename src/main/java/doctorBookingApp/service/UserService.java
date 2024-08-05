package doctorBookingApp.service;


import doctorBookingApp.dto.NewUserDTO;
import doctorBookingApp.dto.UserDTO;
import doctorBookingApp.entity.ConfirmationCode;
import doctorBookingApp.entity.DoctorProfile;
import doctorBookingApp.entity.User;
import doctorBookingApp.repository.DoctorProfileRepository;
import doctorBookingApp.repository.UserRepository;
import doctorBookingApp.exeption.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import doctorBookingApp.entity.enums.Role;
import doctorBookingApp.entity.enums.State;

import doctorBookingApp.repository.ConfirmationCodeRepository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
                            
@Service
public class UserService {

   

    private final UserRepository userRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final ConfirmationCodeService confirmationCodeService;
    
    private final MailService mailService;


    @Transactional
    public UserDTO registrationUser(NewUserDTO newUser) throws RestException {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new RestException(HttpStatus.CONFLICT, "Пользователь с таким email уже существует: " + newUser.getEmail());
        }

        User user = User.builder()
                .firstname(newUser.getFirstname())
                .surName(newUser.getSurName())
                .birthDate(newUser.getBirthDate())
                .phoneNumber(newUser.getPhoneNumber())
                .email(newUser.getEmail())
                .hashPassword(newUser.getPassword())
                .role(Role.PATIENT)
                .state(State.NOT_CONFIRMED)
                .build();
        userRepository.save(user);

        String codeValue = confirmationCodeService.createAndSaveConfirmationCode(user);
        mailService.sendConfirmationEmail(user, codeValue);

        return UserDTO.from(user);

    }


    public List<UserDTO> findAllUsers() {
        return UserDTO.from(userRepository.findAll());
    }

    public UserDTO getUserById(Long userId) throws RestException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с ID " + userId + " не найден."));
        return UserDTO.from(user);
    }

//    public List<UserDTO> getUserByFullName(String surName, String firstname) {
//        return UserDTO.from(userRepository.findByFullName(surName, firstname));
//    }


    public UserDTO getUserByEmail(String email) throws RestException {
        return UserDTO.from(userRepository.findByEmail(email)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с email " + email + " не найден")));
    }




    public UserDTO getUserByPhoneNumber(String phoneNumber) throws RestException {
        return UserDTO.from(userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с номером телефона " + phoneNumber + " не найден.")));
    }



    public UserDTO editUser(Long userId, UserDTO userDTO) throws RestException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с ID " + userId + " не найден."));
        user.setSurName(userDTO.getSurName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEmail(userDTO.getEmail());
        userRepository.save(user);
        return UserDTO.from(user);

    }

    public void deleteUserById(Long userId) throws RestException {
        if (!userRepository.existsById(userId)) {
            throw new RestException(HttpStatus.NOT_FOUND, "Пользователь с ID " + userId + " не найден.");
        }
        userRepository.deleteById(userId);
    }



    public void deleteUserByEmail(String email) throws RestException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с Email " + email + " не найден."));
        userRepository.deleteByEmail(user.getEmail());

    }


    public void deleteUserByPhoneNumber(String phoneNumber) throws RestException {
        User user = userRepository.findByEmail(phoneNumber)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с Email " + phoneNumber + " не найден."));
        userRepository.deleteByPhoneNumber(user.getPhoneNumber());

    }
    @Autowired
    private ConfirmationCodeRepository confirmationCodeRepository;


    @Transactional
    public boolean confirm(String confirmCode) throws RestException {
        ConfirmationCode code = confirmationCodeRepository
                .findByCodeAndExpiredDateTimeAfter(confirmCode, LocalDateTime.now())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Код не найден или срок его действия истек"));

       
        User user = code.getUser();

       
        user.setState(State.CONFIRMED);

       
        userRepository.save(user);

        return true;
    }

    // добавила методы, которых недоставало для AdminController

    @Transactional
    public List<UserDTO> findUsersByDoctor(Long doctorId) throws RestException {
        DoctorProfile doctor = doctorProfileRepository.findById(doctorId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Доктор с ID " + doctorId + " не найден."));
        List<User> users = userRepository.findByDoctor(doctor);
        return UserDTO.from(users);
    }

    @Transactional
    public List<UserDTO> findUsersByDate(LocalDate date) {
        List<User> users = userRepository.findByBirthDate(date);
        return UserDTO.from(users);
    }


    @Transactional
    public List<UserDTO> findUsersByInsurance(String insurance) {
        List<User> users = userRepository.findByInsurance(insurance);
        return UserDTO.from(users);
    }

}



