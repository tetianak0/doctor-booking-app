package doctorBookingApp.service;


import doctorBookingApp.dto.NewUserDTO;
import doctorBookingApp.dto.UserDTO;
import doctorBookingApp.entity.User;
import doctorBookingApp.repository.UserRepository;
import doctorBookingApp.exeption.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import doctorBookingApp.entity.enums.Role;
import doctorBookingApp.entity.enums.State;


import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ConfirmationCodeService confirmationCodeService;
    private final MailService mailService;

    @Transactional
    public UserDTO registrationUser(NewUserDTO newUser) throws RestException {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new RestException(HttpStatus.CONFLICT, "Пользователь с таким email уже существует: " + newUser.getEmail());
        }

        User user = User.builder()
                .name(newUser.getName())
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

    public List<UserDTO> findAll() {
        return UserDTO.from(userRepository.findAll());
    }

    public UserDTO getUserById(Long userId) throws RestException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с ID " + userId + " не найден"));
        return UserDTO.from(user);
    }

    //ПОИСК ПО ПОЧТЕ И ТЕЛЕФОНУ, ПО ИМЕНИ С КОНТРОЛЕМ

    public UserDTO editUser(Long userId, UserDTO userDTO) throws RestException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь с ID " + userId + " не найден"));
        user.setSurName(userDTO.getSurName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEmail(userDTO.getEmail());
        userRepository.save(user);
        return UserDTO.from(user);

    }

    public void deleteUser(Long userId) throws RestException {
        if (!userRepository.existsById(userId)) {
            throw new RestException(HttpStatus.NOT_FOUND, "Пользователь с ID " + userId + " не найден");
        }
        userRepository.deleteById(userId);
    }



}



