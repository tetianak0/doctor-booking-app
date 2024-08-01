package doctorBookingApp.service;


import doctorBookingApp.dto.UserDTO;
import doctorBookingApp.entity.ConfirmationCode;
import doctorBookingApp.entity.User;
import doctorBookingApp.entity.enums.State;
import doctorBookingApp.exeption.RestException;
import doctorBookingApp.repository.ConfirmationCodeRepository;
import doctorBookingApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ConfirmationCodeService {
    private final ConfirmationCodeRepository confirmationCodeRepository;
    private final UserRepository userRepository;

    public String createAndSaveConfirmationCode(User user) {
        String codeValue = UUID.randomUUID().toString(); //Генерируется код подтверждения (UUID) и сохраняется.
        ConfirmationCode confirmationCode = ConfirmationCode.builder() //Создается объект кода подтверждения
                .confirmationCode(codeValue)
                .user(user)
                .expiredDateTime(LocalDateTime.now().plusHours(12)) // и сохраняется в базу данных с установленным временем истечения
                .build();
        confirmationCodeRepository.save(confirmationCode);
        return codeValue;
    }


    @Transactional
    public UserDTO confirmation(String confirmationCode) throws RestException {
        ConfirmationCode confirmCode = confirmationCodeRepository
                .findByCodeAndExpiredDateTimeAfter(confirmationCode, LocalDateTime.now())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Код не найден или срок его действия истек"));
        User user = confirmCode.getUser();
        user.setState(State.CONFIRMED);
        userRepository.save(user);

        return UserDTO.from(user);

    }


    @Transactional
    public boolean confirmationUser(String confirmationCode) {
        try {
            confirmation(confirmationCode);
            return true;
        } catch (RestException e) {
            return false;
        }
    }


}
