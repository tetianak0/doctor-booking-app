package doctorBookingApp.service;


import doctorBookingApp.dto.NewUserDTO;
import doctorBookingApp.dto.UserDTO;
import doctorBookingApp.entity.ConfirmationCode;
import doctorBookingApp.entity.User;
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


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor// Lombok-аннотация, которая создает конструктор
                            // с параметрами для всех final полей.
@Service
public class UserService {

    /*
    Этот код представляет собой сервис для управления пользователями в приложении, реализованный с использованием Spring Boot.
    В частности, он обрабатывает регистрацию пользователей, подтверждение регистрации, а также предоставляет
     базовые CRUD операции (создание, чтение, обновление, удаление).
     */

    private final UserRepository userRepository;
    private final ConfirmationCodeService confirmationCodeService;
    //UsersRepository, ConfirmationCodesRepository: Репозитории для работы с базой данных.
    private final MailService mailService;


    @Transactional //Обозначает, что методы должны выполняться в транзакции.
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

    public List<UserDTO> getUserByFullName(String surName, String firstname) {
        return UserDTO.from(userRepository.findByFullName(surName, firstname));
    }


    public UserDTO getUserByEmail(String email) throws RestException {
        return UserDTO.from(userRepository.findByEmail(email)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с email " + email + " не найден")));
    }
//    public UserDTO getUserByEmail(String email) {
//        Optional<User> user = userRepository.findByEmail(email);
//        return user.map(UserDTO::from).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с таким email не найден: " + email));
//    }



    public UserDTO getUserByPhoneNumber(String phoneNumber) throws RestException {
        return UserDTO.from(userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с номером телефона " + phoneNumber + " не найден.")));
    }


//    public UserDTO getUserByPhoneNumber(String phoneNumber) throws RestException {
//        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
//        return user.map(UserDTO::from).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с номером телефона " + phoneNumber + " не найден."));
//    }

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

        // Retrieve the user associated with the confirmation code
        User user = code.getUser();

        // Update the state of the user using the fully qualified enum value
        user.setState(State.CONFIRMED);

        // Save the updated user
        userRepository.save(user);

        return true;
    }
}



