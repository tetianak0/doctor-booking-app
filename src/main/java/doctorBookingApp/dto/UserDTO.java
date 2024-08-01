package doctorBookingApp.dto;


import doctorBookingApp.entity.User;
import doctorBookingApp.entity.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Data //Генерирует геттеры, сеттеры, toString(), equals(), hashCode().
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "User", description = "Данные пользователя")
//@Schema: Используется для генерации документации API
// с помощью OpenAPI (Swagger). Указывает название и описание схемы.
public class UserDTO {

/*
Этот код представляет собой Data Transfer Object (DTO) для класса User в приложении.
Он используется для передачи данных о пользователях между различными слоями приложения,
особенно при работе с REST API.
 */
    @Schema(description = "Идентификатор пользователя", example = "1")
    private Long id;

    @Schema(description = "Имя пользователя", example = "Thomas")
    private String firstname;

    @Schema(description = "Фамилия пользователя", example = "Mann")
    private String surName;

    @Schema(description = "Дата рождения пользователя", example = "01/01/1950")
    private String birthDate;

    @Schema(description = "Номер телефона пользователя", example = "+4912345678909")
    private String phoneNumber;

    @Schema(description = "Email пользователя", example = "user@mail.com")
    private String email;

    @Schema(description = "Роль пользователя", example = "USER")
    private Role role;


    /*
    Этот метод создает объект UserDto из объекта User.
    Используется паттерн строитель (Builder), чтобы упростить создание объекта UserDto.
     */
    public static UserDTO from(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .surName(user.getSurName())
                .birthDate(user.getBirthDate())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

    }


    /*
    Этот метод преобразует коллекцию объектов User в список объектов UserDto.
Используется Stream API для обработки коллекций и преобразования каждого объекта User в UserDto.
     */
    public static List<UserDTO> from(Collection<User> users) {

        return users.stream()
                .map(UserDTO::from)
                .collect(Collectors.toList());

    }


}
