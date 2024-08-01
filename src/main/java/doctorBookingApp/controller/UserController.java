package doctorBookingApp.controller;

import doctorBookingApp.controller.api.UserApi;
import doctorBookingApp.dto.NewUserDTO;
import doctorBookingApp.dto.UserDTO;
import doctorBookingApp.exeption.RestException;
import doctorBookingApp.service.ConfirmationCodeService;
import doctorBookingApp.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;
    private final ConfirmationCodeService confirmationCodeService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody NewUserDTO newUser) throws MessagingException, RestException {
        userService.registrationUser(newUser);
        return ResponseEntity.ok("Регистрация практически закончена. Проверьте свой адрес электронной почты на наличие кода подтверждения");
    }


    @PostMapping("/confirm")
    public ResponseEntity<?> confirmUser(@RequestParam String confirmationCode) {
        try {
            UserDTO userDTO = confirmationCodeService.confirmation(confirmationCode);
            return ResponseEntity.ok(userDTO);
        } catch (RestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неверный код подтверждения");
        }
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) throws RestException {
        return ResponseEntity.ok(userService.getUserById(userId));
    }


    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) throws RestException {
        return ResponseEntity.ok(userService.editUser(userId, userDTO));
    }


    @Override
    public ResponseEntity<List<UserDTO>> getUsersByFullName(String surName, String firstName) {
        return ResponseEntity.ok(userService.getUserByFullName(firstName, surName));
    }

    @PutMapping("/{phoneNumber}")
    public ResponseEntity<UserDTO> getUserByPhoneNumber(String phoneNumber) throws RestException {
        return ResponseEntity.ok(userService.getUserByPhoneNumber(phoneNumber));
    }

    @PutMapping("/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(String email) throws RestException {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }


    @Override
    public ResponseEntity<Void> deleteUserByPhoneNumber(String phoneNumber) throws RestException {
        userService.deleteUserByPhoneNumber(phoneNumber);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteUserByEmail(String email) throws RestException {
        userService.deleteUserByEmail(email);
        return ResponseEntity.noContent().build();
    }



    //ПОЧЕМУ ИДЕЯ СДЕЛАЛА ЭТО ПЕРЕОПРДЕЛЕНИЕ С return null - НЕПОНЯТНО
    @Override
    public ResponseEntity<String> confirmationUser(String confirmationCode) {
        return null;
    }

    @Override
    public ResponseEntity<UserDTO> getUserById(long userID) {
        return null;
    }

    @Override
    public ResponseEntity<UserDTO> editUser(Long userID, UserDTO userDto) {
        return null;
    }

    @Override
    public ResponseEntity<List<UserDTO>> findAllUserById() {
        return null;
    }

    @Override
    public ResponseEntity<UserDTO> deleteUserById(long userID) {
        return null;
    }
}















