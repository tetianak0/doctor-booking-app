package doctorBookingApp.repository;

import doctorBookingApp.dto.UserDTO;
import doctorBookingApp.entity.ConfirmationCode;
import doctorBookingApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/*
Репозиторий определяет методы для поиска пользователей по различным полям и проверки их существования.
 */

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);


    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    void deleteByPhoneNumber(String phoneNumber);


    Optional<User> findFirstByCodesContains(ConfirmationCode confirmationCode);

    List<User> findByFullName(String surName, String firstName);


    List<User> findAll (Long id);









}
